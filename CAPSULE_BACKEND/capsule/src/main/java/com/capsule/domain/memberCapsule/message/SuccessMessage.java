package com.capsule.domain.memberCapsule.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {
    SUCCESS_CAN_READ("방 읽기 가능"),
    SUCCESS_CAN_WRITE("방 수정 가능");

    private final String message;
}
