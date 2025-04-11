package com.example.challenge.web.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ContactDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
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
