package com.capsule.domain.capsule.requestDto;

import com.capsule.global.vaild.FutureDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreateCapsuleRequestDto {

    @NotBlank
    private String title;

    @NotNull
    @FutureDateTime
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    @NotBlank
    private String latitude;

    @NotBlank
    private String longitude;
    List<String> membersEmail;
}
