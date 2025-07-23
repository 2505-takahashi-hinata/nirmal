package com.example.nirmal.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AttendanceValidator implements ConstraintValidator<AttendanceAnnotation, String> {
    @Override
    public boolean isValid(String time, ConstraintValidatorContext context) {
        if (time == null || time.isEmpty()) {
            return true; // nullと空文字はそのまま登録
        }
        return time.matches("^([01][0-9]|2[0-3]):[0-5][0-9]$");
    }
}
