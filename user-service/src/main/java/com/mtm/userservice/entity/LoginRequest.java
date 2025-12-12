package com.mtm.userservice.entity;

import lombok.Data;

@Data
public class LoginRequest {
    private String mail;
    private String password;
}
