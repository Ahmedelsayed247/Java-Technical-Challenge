package com.example.challenge.web.controller;

import com.example.challenge.application.dto.ContactDTO;
import com.example.challenge.application.exception.ContactExceptionHandling.DuplicateContactException;
import com.example.challenge.domain.model.Contact;
import com.example.challenge.domain.model.User;
import com.example.challenge.domain.service.ContactService;
import com.example.challenge.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;

    public ContactController(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> addContact(@RequestBody ContactDTO contactDTO, Authentication authentication) {
        // Delegate to the service layer to add the contact
        contactService.save(contactDTO, authentication.getName());

        // Return success response
        return ResponseEntity.ok(Collections.singleton("Contact added successfully!"));
    }


    @GetMapping
    public ResponseEntity<List<Contact>> listContacts(Authentication authentication) {
        User user = contactService.getUserByUsername(authentication.getName());
        return ResponseEntity.ok(contactService.findByUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id, Authentication authentication) {
        User user = contactService.getUserByUsername(authentication.getName());
        Contact contact = contactService.findByContactIdAndUser(id, user);
        return ResponseEntity.ok(contact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteByContactIdAndUser(@PathVariable Long id, Authentication authentication) {
        User user = contactService.getUserByUsername(authentication.getName());
        contactService.deleteByContactIdAndUser(id, user);
        return ResponseEntity.ok(Collections.singleton("Contact deleted successfully!"));
    }
}
