package com.example.nirmal.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AttendanceValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AttendanceAnnotation {
    String message() default"時刻はhh:mm形式(00:00〜23:59)で入力してください。";
    Class<?>[]groups()default {};
    Class<? extends Payload>[]payload() default {};

}
