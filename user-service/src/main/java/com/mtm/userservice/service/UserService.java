package com.mtm.userservice.service;

import com.mtm.userservice.dto.TokenResponse;
import com.mtm.userservice.dto.UserDTO;
import com.mtm.userservice.entity.CreateUserForm;
import com.mtm.userservice.entity.LoginRequest;
import com.mtm.userservice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;

public interface UserService {
    UserDTO create(CreateUserForm createUserForm);
    UserDTO update(String id, CreateUserForm form);
    void delete(String id);
    UserDTO findById(String id);
    Page<UserDTO> findAll(Pageable pageable);
    User syncUserFromToken(Jwt jwt);
    TokenResponse login(LoginRequest request);
}
