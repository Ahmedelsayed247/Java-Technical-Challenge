package com.example.challenge.web.controller;
import com.example.challenge.domain.model.Contact;
import com.example.challenge.domain.model.User;
import com.example.challenge.application.service.ContactService;
import com.example.challenge.application.service.UserService;
import com.example.challenge.web.dto.ContactDTO;
import com.example.challenge.web.dto.ContactListRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService, UserService userService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<?> addContact(@Valid @RequestBody ContactDTO contactDTO, Authentication authentication) {
        // Delegate to the service layer to add the contact
        contactService.save(contactDTO, authentication.getName());

        // Return success response
        return ResponseEntity.ok(Collections.singleton("Contact added successfully!"));
    }
    @PostMapping("/list")
    public ResponseEntity<List<Contact>> listContacts(
            Authentication authentication,
            @RequestBody ContactListRequest request) {

        User user = contactService.getUserByUsername(authentication.getName());
        Page<Contact> contacts = contactService.findByUser(user, request);

        // Returning only the content (list of contacts) without metadata
        return ResponseEntity.ok(contacts.getContent());
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
