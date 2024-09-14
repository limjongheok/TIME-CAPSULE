package com.capsule.domain.AI.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    SUCCESS_CREATE_AI_IMG("이미지 생성 중"),
    SUCCESS_SEND_IMG("이미지 URL 송신");

    private final String message;
}
