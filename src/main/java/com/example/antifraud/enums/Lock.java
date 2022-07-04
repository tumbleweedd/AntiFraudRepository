package com.example.antifraud.enums;

public enum Lock {
    LOCK("locked"), UNLOCK("unlocked");

    private final String lock;

    Lock(String lock) {
        this.lock = lock;
    }

    public String toString() {
        return this.lock;
    }
}
