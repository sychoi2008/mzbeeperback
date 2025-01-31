package com.example.mzbeeperback.global.exception;

public class IllegalLoginException extends RuntimeException {
    public IllegalLoginException(String message) {
        super(message);
    }
}
