package com.example.demo.service;

import com.example.demo.entity.Users;

import com.example.demo.model.LoginUserRequest;
import com.example.demo.model.RegisterUserRequest;
import com.example.demo.model.UpdateUserRequest;
import com.example.demo.model.UserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.BCrypt;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.beans.Transient;
import java.util.Objects;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidatorService validator;

    @Transactional
    public void register(RegisterUserRequest request){
        validator.validate(request);

        if(userRepository.existsById(request.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already in use");
        }

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setName(request.getName());

        userRepository.save(user);

    }

    public UserResponse get(Users user){
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }


    @Transactional
    public UserResponse update(Users user, UpdateUserRequest request){
        validator.validate(request);
        if(Objects.nonNull(request.getName())){
            user.setName(request.getName());
        }

        if(Objects.nonNull(request.getPassword())){
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        userRepository.save(user);
        return UserResponse
                .builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();

    }


}
