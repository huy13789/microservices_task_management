package com.mtm.userservice.controller;

import com.mtm.userservice.dto.TokenResponse;
import com.mtm.userservice.dto.UserDTO;
import com.mtm.userservice.entity.CreateUserForm;
import com.mtm.userservice.entity.LoginRequest;
import com.mtm.userservice.model.User;
import com.mtm.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@PreAuthorize("hasRole('User')") // Only role User can use this controller
public class UserController {

    private UserService userService;
    @PreAuthorize("permitAll()")
    @PostMapping("/signup")
    public UserDTO sign_Up(@RequestBody CreateUserForm form) {
        return userService.create(form);
    }
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
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
    GET http://localhost:8080/api/v1/user/?page=0&size=10&sort=firstName,asc --local version
    GET http://localhost:8081/user/?page=0&size=10&sort=firstName,asc -- gateway version
     */
    @GetMapping
    public Page<UserDTO> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        User user = userService.syncUserFromToken(jwt);
        return ResponseEntity.ok(user);
    }
}
