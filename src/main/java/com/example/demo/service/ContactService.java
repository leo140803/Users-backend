package com.example.demo.service;

import com.example.demo.entity.Contacts;
import com.example.demo.model.ContactResponse;
import com.example.demo.model.CreateContactRequest;
import com.example.demo.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ValidatorService validatorService;

    @Transactional
    public ContactResponse create(CreateContactRequest request) {
        validatorService.validate(request);
        Contacts contact= new Contacts();
        contact.setId(UUID.randomUUID().toString());
        contact.setFirst_name(request.getFirstName());
        contact.setLast_name(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contactRepository.save(contact);
        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirst_name())
                .lastName(contact.getLast_name())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }
}
