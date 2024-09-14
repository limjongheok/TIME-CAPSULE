package com.capsule.domain.email.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CheckAuthCodeRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String authCode;

}
