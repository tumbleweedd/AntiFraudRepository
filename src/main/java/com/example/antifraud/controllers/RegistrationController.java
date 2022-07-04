package com.example.antifraud.controllers;

import com.example.antifraud.DTO.UserAccessDTO;
import com.example.antifraud.DTO.UserRoleDTO;
import com.example.antifraud.model.UserModel;
import com.example.antifraud.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/auth/user")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        if (user.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.registerUser(user);
    }

    @GetMapping("/api/auth/list")
    public ResponseEntity<?> getAuthUserList() {
        return userService.getAuthUserList();
    }

    @DeleteMapping("/api/auth/user/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        return userService.deleteUser(username);
    }

    @PutMapping("/api/auth/role")
    public ResponseEntity<?> takeNewRole(@RequestBody UserRoleDTO userRoleDTO) {
        return userService.takeRole(userRoleDTO);
    }

    @PutMapping("/api/auth/access")
    public ResponseEntity<?> getAccess(@RequestBody UserAccessDTO userAccessDTO) {
        return userService.giveAccessToUser(userAccessDTO);
    }
}
