package com.capsule.domain.capsule.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    SUCCESS_CREATE_CAPSULE("캡슐 생성 성공"),
    SUCCESS_READ_CAPSULE("캡슐 읽을 수 있음"),
    SUCCESS_WRITE_CAPSULE("캡슐 쓸 수 있음");

    private final String message;
}
