package com.example.antifraud.DTO;

import com.example.antifraud.enums.Lock;
import com.example.antifraud.model.UserModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserAccessDTO {
    private String username;
    private Lock operation;

    UserAccessDTO(UserModel user, Lock lock) {
        this.username = user.getUsername();
        this.operation = lock;
    }
}
