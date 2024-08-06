package com.example.demo.repository;

import com.example.demo.entity.Contacts;
import com.example.demo.entity.Users;
import com.example.demo.model.UpdateContactRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contacts, String>, JpaSpecificationExecutor<Contacts> {
    Optional<Contacts> findFirstByUserAndId(Users user, String id);
}
