package com.stressthem.app.validation;

import com.stressthem.app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@Component
public class UniqueUserValidator implements ConstraintValidator<UniqueUser,String> {
    private UserService userService;
    private String type;


    @Autowired
    public UniqueUserValidator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void initialize(UniqueUser constraintAnnotation) {
        this.type=constraintAnnotation.fieldType();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       if(type.equals("email")){
           return this.userService.getUserByEmail(value)==null;
       }
        return this.userService.getUserByUsername(value)==null;
    }
}
