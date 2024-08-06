package com.example.demo.service;

import com.example.demo.entity.Contacts;
import com.example.demo.entity.Users;
import com.example.demo.model.ContactResponse;
import com.example.demo.model.CreateContactRequest;
import com.example.demo.model.UpdateContactRequest;
import com.example.demo.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ValidatorService validatorService;

    @Transactional
    public ContactResponse create(Users user, CreateContactRequest request) {
        validatorService.validate(request);
        Contacts contact= new Contacts();
        contact.setId(UUID.randomUUID().toString());
        contact.setFirst_name(request.getFirstName());
        contact.setLast_name(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setUser(user);
        contactRepository.save(contact);
        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirst_name())
                .lastName(contact.getLast_name())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }

    public ContactResponse get(String id) {
        Contacts contact= contactRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contact not found!"));
        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirst_name())
                .lastName(contact.getLast_name())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }

    private ContactResponse toContactResponse(Contacts contact) {
        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirst_name())
                .lastName(contact.getLast_name())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }


    @Transactional
    public ContactResponse update(Users user, UpdateContactRequest request) {
        validatorService.validate(request);

        Contacts contact = contactRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        contact.setFirst_name(request.getFirstName());
        contact.setLast_name(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contactRepository.save(contact);

        return toContactResponse(contact);
    }
}
