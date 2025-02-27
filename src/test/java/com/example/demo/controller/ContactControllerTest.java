package com.example.demo.controller;

import com.example.demo.entity.Contacts;
import com.example.demo.entity.Users;
import com.example.demo.model.ContactResponse;
import com.example.demo.model.CreateContactRequest;
import com.example.demo.model.UpdateContactRequest;
import com.example.demo.model.WebResponse;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.BCrypt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
        userRepository.deleteAll();
        Users user= new Users();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("test");
        user.setToken("tEst");
        user.setToken_expired_at(System.currentTimeMillis() + 100000L);
        userRepository.save(user);
    }

    @Test
    void createContactBadRequest() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Leonardo");
        request.setEmail("salah");

        mockMvc.perform(
                post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "tEst")

        ).andExpectAll(
              status().isBadRequest()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<ContactResponse>>() {});
            assertNotNull(response.getError());
            System.out.println(response);
        });
    }

    @Test
    void createContactSuccess() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Leonardo");
        request.setLastName("Salah");
        request.setPhone("012313");
        request.setEmail("salah@gmail.com");

        mockMvc.perform(
                post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "tEst")

        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<ContactResponse>>() {});
            assertNull(response.getError());
            assertTrue(contactRepository.existsById(response.getData().getId()));
        });

    }

    @Test
    void getContactByIdSuccess() throws Exception {
        Users user = new Users();
        user.setUsername("lalala");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("lalala");
        user.setToken("blabla");
        userRepository.save(user);
        Contacts contact = new Contacts();
        contact.setId("1");
        contact.setFirst_name("Leonardo");
        contact.setLast_name("Muh");
        contact.setPhone("012313");
        contact.setEmail("salah@gmail.com");
        contact.setUser(user);
        contactRepository.save(contact);
        mockMvc.perform(
                get("/api/contacts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "tEst")

        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<ContactResponse>>() {});
            assertNull(response.getError());
        });
    }
    @Test
    void getContactByIdNotFound() throws Exception {
        Users user = new Users();
        user.setUsername("lalala");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("lalala");
        user.setToken("blabla");
        userRepository.save(user);
        Contacts contact = new Contacts();
        contact.setId("1");
        contact.setFirst_name("Leonardo");
        contact.setLast_name("Muh");
        contact.setPhone("012313");
        contact.setEmail("salah@gmail.com");
        contact.setUser(user);
        contactRepository.save(contact);
        mockMvc.perform(
                get("/api/contacts/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "tEst")

        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<ContactResponse>>() {});
            assertNotNull(response.getError());
        });
    }

    @Test
    void updateContactBadRequest() throws Exception {
        UpdateContactRequest request = new UpdateContactRequest();
        request.setFirstName("");
        request.setEmail("salah");

        mockMvc.perform(
                put("/api/contacts/1234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "tEst")

        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<ContactResponse>>() {});
            assertNotNull(response.getError());
            System.out.println(response);
        });
    }

    @Test
    void updateContactSuccess() throws Exception {
        Contacts contact = new Contacts();
        contact.setId("1");
        contact.setFirst_name("Leonardo");
        contact.setLast_name("Muh");
        contact.setPhone("012313");
        contact.setEmail("salah@gmail.com");
//        contact.setUser(user);
        contactRepository.save(contact);

        UpdateContactRequest request = new UpdateContactRequest();
        request.setFirstName("Budy Nugraha");
        request.setLastName("Lele");
        request.setPhone("908536");
        request.setEmail("salah123@gmail.com");

        System.out.println(objectMapper.writeValueAsString(request));

        mockMvc.perform(
                put("/api/contacts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "tEst")

        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<ContactResponse>>() {});
            assertNull(response.getError());
            System.out.println(response);
//            assertTrue(contactRepository.existsById(response.getData().getId()));
        });

    }
}