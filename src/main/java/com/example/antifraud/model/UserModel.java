package com.example.antifraud.model;

import com.example.antifraud.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "user_access")
public class UserModel {

    public UserModel(Long id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "The name cannot be empty")
    private String name;
    @NotBlank(message = "The username cannot be empty")
    private String username;
    @NotBlank(message = "The password cannot be empty")
    private String password;

    @JsonIgnore
    private Role role;

    @JsonIgnore
    private boolean isAccountNonLocked = false;

    public UserModel(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public UserModel() {

    }

}
