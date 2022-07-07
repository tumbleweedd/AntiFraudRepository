package com.example.antifraud.service;

import com.example.antifraud.DTO.UserAccessDTO;
import com.example.antifraud.DTO.UserDTO;
import com.example.antifraud.DTO.UserRoleDTO;
import com.example.antifraud.enums.Lock;
import com.example.antifraud.enums.Role;
import com.example.antifraud.exception.UserNotFound;
import com.example.antifraud.model.UserModel;
import com.example.antifraud.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> registerUser(UserModel user) {
        if (user.getUsername() == null || user.getName() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findUserEntityByUsernameIgnoreCase(user.getUsername()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        setUserRole(user);
        userRepository.save(user);
        UserModel newUser = userRepository.findUserEntityByUsernameIgnoreCase(user.getUsername()).get();
        return new ResponseEntity<>(new UserDTO(newUser), HttpStatus.CREATED);
    }

    public void setUserRole(UserModel user) {
        if (userRepository.findAll().size() == 0) {
            user.setAccountNonLocked(true);
            user.setRole(Role.ADMINISTRATOR);
        } else {
            user.setRole(Role.MERCHANT);
            user.setAccountNonLocked(false);
        }
    }

    public ResponseEntity<?> getAuthUserList() {
        List<UserModel> authUserList = userRepository.findAll();

        if (!authUserList.isEmpty()) {
            return new ResponseEntity<>(userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList()),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteUser(String username) {
        if (userRepository.findUserEntityByUsernameIgnoreCase(username).isPresent()) {
            userRepository.delete(userRepository.findUserEntityByUsernameIgnoreCase(username).get());
            Map<String, String> deleteUserMapInfo = new LinkedHashMap<>();
            deleteUserMapInfo.put("username", username);
            deleteUserMapInfo.put("status", "Deleted successfully!");
            return new ResponseEntity<>(deleteUserMapInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> takeRole(UserRoleDTO userRole) {
        UserModel user = userRepository.findUserEntityByUsernameIgnoreCase(userRole.getUsername())
                .orElseThrow(UserNotFound::new);
        Role giveRole = userRole.getRole();
        if (giveRole == Role.MERCHANT || giveRole == Role.SUPPORT) {
            if (giveRole == user.getRole()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            user.setRole(giveRole);
            userRepository.save(user);
            return new ResponseEntity<>(new UserRoleDTO(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> giveAccessToUser(UserAccessDTO usernameAndStatus) {
        UserModel user = userRepository.findUserEntityByUsernameIgnoreCase(usernameAndStatus.getUsername())
                .orElseThrow(UserNotFound::new);

        if (user.getRole() == Role.ADMINISTRATOR) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String lockResult = null;
        if (usernameAndStatus.getOperation() == Lock.LOCK) {
            user.setAccountNonLocked(false);
            userRepository.save(user);
            lockResult = String.valueOf(Lock.LOCK);
        } else if (usernameAndStatus.getOperation() == Lock.UNLOCK) {
            user.setAccountNonLocked(true);
            userRepository.save(user);
            lockResult = String.valueOf(Lock.UNLOCK);
        }

        return new ResponseEntity<>(Map.of(
                "status",
                "User " + usernameAndStatus.getUsername() + " " + lockResult + "!"), HttpStatus.OK);

    }


}

