package com.mtm.userservice.service.impl;

import com.mtm.userservice.dto.UserDTO;
import com.mtm.userservice.entity.CreateUserForm;
import com.mtm.userservice.mapper.UserMapper;
import com.mtm.userservice.repository.UserRepository;
import com.mtm.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserServiceimpl implements UserService {
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDTO create(CreateUserForm form) {
        var user = UserMapper.map(form);
        String id = UUID.randomUUID().toString();
        user.setId(id);
        user.setRoles(new ArrayList<>(List.of("User")));
        var saveuser = userRepository.save(user);
        return UserMapper.map(saveuser);
    }
    @Override
    public UserDTO update(String id, CreateUserForm form) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setLastName(form.getLastName());
        user.setFirstName(form.getFirstName());
        user.setMail(form.getMail());

        var saved = userRepository.save(user);
        return UserMapper.map(saved);
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
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.map(user);
    }

    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository
                .findAll(pageable)
                .map(UserMapper::map);
    }
}
