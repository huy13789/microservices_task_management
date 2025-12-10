package com.mtm.userservice.mapper;

import com.mtm.userservice.dto.UserDTO;
import com.mtm.userservice.entity.CreateUserForm;
import com.mtm.userservice.entity.UpdateUserForm;
import com.mtm.userservice.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static User map(CreateUserForm createUserForm) {
        var user = new User();
        List<String> roles = new ArrayList<>();
        roles.add("User");
        user.setMail(createUserForm.getMail());
        user.setFirstName(createUserForm.getFirstName());
        user.setLastName(createUserForm.getLastName());
        user.setRoles(new ArrayList<>(List.of("User")));
        //user.setRoles(List.of("User"));
        return user;
    }

    public static UserDTO map(User user) {
        var dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getMail());
        dto.setLastName(user.getLastName());
        dto.setFirstName(user.getFirstName());
        dto.setCreateAt(user.getCreatedAt());
        dto.setUpdateAt(user.getUpdateAt());
        return dto;
    }

    public static void map(UpdateUserForm updateUserForm, User user) {
        user.setMail(updateUserForm.getMail());
        user.setLastName(updateUserForm.getLastName());
        user.setFirstName(updateUserForm.getFirstName());
    }


}
