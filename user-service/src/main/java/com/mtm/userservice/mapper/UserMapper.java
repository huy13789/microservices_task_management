package com.mtm.userservice.mapper;

import com.mtm.userservice.dto.UserDTO;
import com.mtm.userservice.entity.CreateUserForm;
import com.mtm.userservice.entity.UpdateUserForm;
import com.mtm.userservice.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static User map(CreateUserForm form) {
        User user = new User();
        user.setMail(form.getMail());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        return user;
    }

    public static UserDTO map(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setMail(user.getMail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }

    public static void map(UpdateUserForm updateUserForm, User user) {
        user.setMail(updateUserForm.getMail());
        user.setLastName(updateUserForm.getLastName());
        user.setFirstName(updateUserForm.getFirstName());
    }


}
