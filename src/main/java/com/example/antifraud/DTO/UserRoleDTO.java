package com.example.antifraud.DTO;

import com.example.antifraud.enums.Role;
import com.example.antifraud.model.UserModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserRoleDTO {
    private String username;
    private Role role;

    public UserRoleDTO(UserModel user) {
        this.username = user.getUsername();
        this.role = user.getRole();
    }

}
