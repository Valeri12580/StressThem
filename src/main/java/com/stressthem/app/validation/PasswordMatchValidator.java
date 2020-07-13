package com.stressthem.app.validation;

import com.stressthem.app.domain.models.binding.PasswordWrapper;;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, PasswordWrapper> {



    @Override
    public boolean isValid(PasswordWrapper value, ConstraintValidatorContext context) {
        String firstPassword = value.getPassword();
        String secondPassword = value.getConfirmPassword();

        return firstPassword.equals(secondPassword);
    }
}
