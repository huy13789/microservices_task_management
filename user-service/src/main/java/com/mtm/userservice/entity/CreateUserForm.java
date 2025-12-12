package com.mtm.userservice.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CreateUserForm {
    private String mail;
    private String firstName;
    private String lastName;
    private String password;
}
