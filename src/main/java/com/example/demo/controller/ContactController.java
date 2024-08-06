package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.model.ContactResponse;
import com.example.demo.model.CreateContactRequest;
import com.example.demo.model.UserResponse;
import com.example.demo.model.WebResponse;
import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping(path = "/api/contacts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ContactResponse> create(Users user, @RequestBody CreateContactRequest request) {
        ContactResponse response= contactService.create(user,request);
        return WebResponse.<ContactResponse>builder().data(response).build();
    }

    @GetMapping(value = "/api/contacts/{idContact}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ContactResponse> get(@PathVariable("idContact") String idContact) {
        ContactResponse response= contactService.get(idContact);
        return WebResponse.<ContactResponse>builder().data(response).build();
    }
}
