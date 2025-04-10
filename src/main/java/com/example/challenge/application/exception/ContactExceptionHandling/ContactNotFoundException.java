package com.example.challenge.application.exception.ContactExceptionHandling;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String message) {
        super(message);
    }
}
