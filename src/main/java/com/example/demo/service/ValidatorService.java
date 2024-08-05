package com.example.demo.service;

import com.example.demo.model.LoginUserRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ValidatorService {

    @Autowired
    private Validator validator;

    public void validate(Object request) throws ValidationException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
        if(constraintViolations.size() > 0){
            System.out.println("constraintViolations: " + constraintViolations);
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
