package com.mtm.userservice.service;

import com.mtm.userservice.dto.UserDTO;
import com.mtm.userservice.entity.CreateUserForm;
import com.mtm.userservice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    UserDTO create(CreateUserForm createUserForm);
    UserDTO update(String id, CreateUserForm form);
    void delete(String id);
    UserDTO findById(String id);
    Page<UserDTO> findAll(Pageable pageable);

}
