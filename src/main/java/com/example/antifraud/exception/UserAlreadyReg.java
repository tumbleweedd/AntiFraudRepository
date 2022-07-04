package com.example.antifraud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserAlreadyReg extends RuntimeException {

    public UserAlreadyReg() {
        super("Code snippet not found");
    }

    public UserAlreadyReg(String message) {
        super(message);
    }
}