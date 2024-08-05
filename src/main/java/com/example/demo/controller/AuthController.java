package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.model.LoginUserRequest;
import com.example.demo.model.RegisterUserRequest;
import com.example.demo.model.TokenResponse;
import com.example.demo.model.WebResponse;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping(value = "/api/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TokenResponse> register(@RequestBody LoginUserRequest loginUserRequest) {
        TokenResponse tokenResponse= authService.login(loginUserRequest);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();
    }


    @DeleteMapping(path = "/api/auth/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> logout(Users user){
        authService.logout(user);
        return WebResponse.<String>builder().data("OK").build();

    }
}
