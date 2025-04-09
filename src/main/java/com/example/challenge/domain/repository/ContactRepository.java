package com.example.challenge.domain.repository;

import com.example.challenge.domain.model.Contact;
import com.example.challenge.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {
    List<Contact> findByUser(User user);

    Optional<Contact> findByContactIdAndUser(Long contactId, User user);


    void deleteByContactIdAndUser(Long contactId, User user);
}
