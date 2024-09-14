package com.capsule.global.vaild;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FutureDateTimeValidator.class) // 유효성 검사기 지정
@Target({ ElementType.FIELD, ElementType.METHOD }) // 적용 대상 지정
@Retention(RetentionPolicy.RUNTIME) // 런타임 동안 유지
public @interface FutureDateTime {
    String message() default "Date and time must be in the future"; // 기본 메시지
    Class<?>[] groups() default {}; // 그룹 지정
    Class<? extends Payload>[] payload() default {}; // 페이로드 지정
}
