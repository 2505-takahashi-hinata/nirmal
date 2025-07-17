package com.example.nirmal.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomPasswordValidator implements ConstraintValidator<PasswordValidator, String> {

    @Override
    public boolean isValid(String input, ConstraintValidatorContext con) {
        if (input == null || input.isEmpty()) {
            return true;
        }
        if (input.matches("^[a-z]{6,20}$")) {
            return true;
        }
        return false;
    }
}
