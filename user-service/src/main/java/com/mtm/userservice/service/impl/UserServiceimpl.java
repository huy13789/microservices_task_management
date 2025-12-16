package com.mtm.userservice.service.impl;

import com.mtm.userservice.dto.TokenResponse;
import com.mtm.userservice.dto.UserDTO;
import com.mtm.userservice.entity.CreateUserForm;
import com.mtm.userservice.entity.LoginRequest;
import com.mtm.userservice.mapper.UserMapper;
import com.mtm.userservice.model.User;
import com.mtm.userservice.repository.UserRepository;
import com.mtm.userservice.service.UserService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceimpl implements UserService {

    private final UserRepository userRepository;
    private final Keycloak keycloak;

    // --- CONFIG VALUES ---
    @Value("${keycloak.admin.realm}")
    private String realm;

    @Value("${keycloak.admin.server-url}")
    private String serverUrl;

    @Value("${keycloak.app-client-id}")
    private String appClientId;

    @Value("${keycloak.client-secret}")
    private String appClientSecret;

    @Override
    @Transactional
    public UserDTO create(CreateUserForm form) {
        // 1. Chuẩn bị User Representation
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(form.getMail());
        kcUser.setEmail(form.getMail());
        kcUser.setFirstName(form.getFirstName());
        kcUser.setLastName(form.getLastName());
        kcUser.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(form.getPassword());
        credential.setTemporary(false);
        kcUser.setCredentials(Collections.singletonList(credential));

        // 2. Tạo User trên Keycloak
        UsersResource usersResource = keycloak.realm(realm).users();
        Response response = usersResource.create(kcUser);

        if (response.getStatus() == 409) {
            throw new RuntimeException("Email này đã được đăng ký!");
        }
        if (response.getStatus() != 201) {
            log.error("Lỗi Keycloak: Status {}", response.getStatus());
            throw new RuntimeException("Lỗi hệ thống xác thực.");
        }

        String keycloakUserId = CreatedResponseUtil.getCreatedId(response);
        log.info("Created Keycloak User ID: {}", keycloakUserId);

        // 3. Lưu DB Local (Kèm cơ chế Rollback)
        try {
            User localUser = UserMapper.map(form);
            localUser.setId(keycloakUserId);

            User savedUser = userRepository.save(localUser);
            return UserMapper.map(savedUser);

        } catch (Exception e) {
            log.error("Lỗi lưu DB. Đang rollback Keycloak User...", e);
            usersResource.get(keycloakUserId).remove();
            throw new RuntimeException("Đăng ký thất bại. Đã hoàn tác.");
        }
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        try {
            String tokenUrl = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("client_id", appClientId);
            map.add("client_secret", appClientSecret);
            map.add("grant_type", "password");
            map.add("username", request.getMail());
            map.add("password", request.getPassword());

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                    tokenUrl, entity, TokenResponse.class
            );

            return response.getBody();

        } catch (Exception e) {
            log.error("Login Error: {}", e.getMessage());
            throw new RuntimeException("Đăng nhập thất bại.");
        }
    }

    @Override
    @Transactional
    public UserDTO update(String id, CreateUserForm form) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setLastName(form.getLastName());
        user.setFirstName(form.getFirstName());
        user.setMail(form.getMail());
        return UserMapper.map(userRepository.save(user));
    }

    @Override
    @Transactional
    public void delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO findById(String id) {
        return userRepository.findById(id)
                .map(UserMapper::map)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserMapper::map);
    }

    @Override
    @Transactional
    public User syncUserFromToken(Jwt jwt) {
        String keycloakId = jwt.getSubject();
        User user = userRepository.findById(keycloakId).orElse(new User());

        if (user.getId() == null) {
            user.setId(keycloakId);
            user.setCreatedAt(LocalDateTime.now());
        }

        user.setMail(jwt.getClaim("email"));
        user.setFirstName(jwt.getClaim("given_name"));
        user.setLastName(jwt.getClaim("family_name"));;

        return userRepository.save(user);
    }
}