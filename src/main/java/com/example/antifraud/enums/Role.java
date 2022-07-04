package com.example.antifraud.enums;

public enum Role {
    ADMINISTRATOR("ROLE_ADMINISTRATOR"),
    MERCHANT("ROLE_MERCHANT"),
    SUPPORT("ROLE_SUPPORT");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String toString() {
        return this.role;
    }
}
