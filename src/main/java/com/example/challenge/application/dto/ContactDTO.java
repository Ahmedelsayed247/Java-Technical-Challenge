package com.example.challenge.application.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


public class ContactDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private LocalDate birthdate;

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }
}
