package com.mtm.userservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class UserDTO {
    private String id;
    private String mail;
    private String firstName;
    private String lastName;
}