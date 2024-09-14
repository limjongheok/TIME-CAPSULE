package com.capsule.domain.email.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class SendAuthCodeRequestDto {

    @NotBlank
    @Email
    private String email;

}
