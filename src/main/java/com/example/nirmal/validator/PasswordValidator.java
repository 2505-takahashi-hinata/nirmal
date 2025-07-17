package com.example.nirmal.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CustomPasswordValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidator {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
