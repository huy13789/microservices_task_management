package com.mtm.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserDTO {
    private String id;
    private String email;
    private String lastName;
    private  String firstName;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
