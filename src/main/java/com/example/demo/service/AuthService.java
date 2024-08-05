package com.example.demo.service;

import com.example.demo.entity.Users;
import com.example.demo.model.LoginUserRequest;
import com.example.demo.model.TokenResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.BCrypt;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidatorService validator;

    public TokenResponse login(LoginUserRequest request){
        validator.validate(request);

        Users user= userRepository.findById(request.getUsername())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is wrong!"));


        if(BCrypt.checkpw(request.getPassword(), user.getPassword())){
            user.setToken(UUID.randomUUID().toString());
            user.setToken_expired_at(next30Days());
            userRepository.save(user);
            return TokenResponse.builder().token(user.getToken()).expiredAt(user.getToken_expired_at()).build();
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is wrong!");
        }

//        return null;
    }

    @Transactional
    public void logout(Users user){
        user.setToken(null);
        user.setToken_expired_at(null);
        userRepository.save(user);
    }

    private Long next30Days(){
        return System.currentTimeMillis() + (1000*16*24*30);
    }
}
