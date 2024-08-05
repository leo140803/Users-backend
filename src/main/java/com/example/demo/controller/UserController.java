package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.model.RegisterUserRequest;
import com.example.demo.model.UpdateUserRequest;
import com.example.demo.model.UserResponse;
import com.example.demo.model.WebResponse;
import com.example.demo.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/api/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@RequestBody RegisterUserRequest registerUserRequest) {
        userService.register(registerUserRequest);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = "/api/users/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse> get(Users user) {
        UserResponse response= userService.get(user);
        return WebResponse.<UserResponse>builder().data(response).build();
    }

    @PatchMapping(path = "/api/users/current", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse> update(Users user, @RequestBody UpdateUserRequest updateUserRequest) {
        UserResponse response= userService.update(user,updateUserRequest);
        return WebResponse.<UserResponse>builder().data(response).build();
    }


}
