package com.example.antifraud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFound extends RuntimeException {

    public UserNotFound() {
        super("Code snippet not found");
    }

    public UserNotFound(String message) {
        super(message);
    }
}
