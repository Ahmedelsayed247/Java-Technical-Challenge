package com.example.challenge.application.exception.UserExceptionHandling;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}