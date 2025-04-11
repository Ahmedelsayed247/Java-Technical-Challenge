package com.example.challenge.domain.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String emailAddress;

    private LocalDate birthdate;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference
    private User user;

    public Contact() {}

    public Contact(String firstName, String lastName, String phoneNumber, String emailAddress, LocalDate birthdate, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.birthdate = birthdate;
        this.user = user;
    }
}