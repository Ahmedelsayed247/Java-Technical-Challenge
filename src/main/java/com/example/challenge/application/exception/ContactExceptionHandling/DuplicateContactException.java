package com.example.challenge.application.exception.ContactExceptionHandling;

public class DuplicateContactException extends RuntimeException {
    public DuplicateContactException(String message) {
        super(message);
    }
}
