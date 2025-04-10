package com.example.challenge.domain.service;

import com.example.challenge.application.dto.ContactDTO;
import com.example.challenge.application.exception.ContactExceptionHandling.ContactNotFoundException;
import com.example.challenge.application.exception.ContactExceptionHandling.DuplicateContactException;
import com.example.challenge.application.exception.ContactExceptionHandling.UserNotFoundException;
import com.example.challenge.domain.model.Contact;
import com.example.challenge.domain.model.User;
import com.example.challenge.domain.repository.ContactRepository;
import com.example.challenge.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }


    public void save(ContactDTO contactDTO, String username) {
        // Get the user by username
        User user = getUserByUsername(username);

        // Check if the email already exists for the user
        checkIfEmailExistsOrThrow(contactDTO.getEmailAddress(), user);

        // Create and save the new contact
        Contact contact = new Contact(
                contactDTO.getFirstName(),
                contactDTO.getLastName(),
                contactDTO.getPhoneNumber(),
                contactDTO.getEmailAddress(),
                contactDTO.getBirthdate(),
                user
        );
        contactRepository.save(contact);
    }

    public List<Contact> findByUser(User user) {
        return contactRepository.findByUser(user);
    }

    public Contact findByContactIdAndUser(Long contactId, User user) {
        return contactRepository.findByContactIdAndUser(contactId, user)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found"));
    }

    public void deleteByContactIdAndUser(Long contactId, User user) {
        Contact contact = findByContactIdAndUser(contactId, user);
        contactRepository.delete(contact);
    }

    public void checkIfEmailExistsOrThrow(String email, User user) {
        contactRepository.findByEmailAddressAndUser(email, user)
                .ifPresent(contact -> {
                    throw new DuplicateContactException("A contact with this email already exists.");
                });
    }


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
