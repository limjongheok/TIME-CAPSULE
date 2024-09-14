package com.capsule.domain.AI.requestDto;

import lombok.Getter;

@Getter
public class CreateAIImgRequestDto {
    private long capsuleId;
    private String text;
}
