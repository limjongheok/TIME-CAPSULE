package com.capsule.global.vaild;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class FutureDateTimeValidator implements ConstraintValidator<FutureDateTime, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.isAfter(LocalDateTime.now()); // 현재 시간보다 이후인지 검증
    }
}