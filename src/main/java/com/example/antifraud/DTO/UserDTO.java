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
public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private Role role;

    public UserDTO(UserModel user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
