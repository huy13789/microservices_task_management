package com.mtm.userservice.controller;

import com.mtm.userservice.dto.UserDTO;
import com.mtm.userservice.entity.CreateUserForm;
import com.mtm.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    @PostMapping("/")
    public UserDTO sign_Up(@RequestBody CreateUserForm form) {
        return userService.create(form);
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable String id,
                          @RequestBody CreateUserForm form) {
        return userService.update(id, form);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        userService.delete(id);
        return "Deleted user with id: " + id;
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable String id) {
        return userService.findById(id);
    }

    /*
    GET http://localhost:8081/api/v1/user/?page=0&size=10&sort=username,asc --local version
    GET http://localhost:8080/user/?page=0&size=10&sort=username,asc -- gateway version
     */
    @GetMapping("/")
    public Page<UserDTO> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

}
