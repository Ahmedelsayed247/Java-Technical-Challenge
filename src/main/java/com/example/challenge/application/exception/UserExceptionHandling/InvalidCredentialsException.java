package com.example.challenge.application.exception.UserExceptionHandling;


public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
