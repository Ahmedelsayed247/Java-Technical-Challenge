package com.example.challenge.domain.repository;
import com.example.challenge.domain.model.Contact;
import com.example.challenge.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByUser(User user);

    Optional<Contact> findByContactIdAndUser(Long contactId, User user);
    Optional<Contact> findByEmailAddressAndUser(String email , User user);


}
