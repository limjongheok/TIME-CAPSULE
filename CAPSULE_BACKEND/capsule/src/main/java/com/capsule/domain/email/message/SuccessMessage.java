package com.capsule.domain.email.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SuccessMessage {

    SUCCESS_SEND_AUTH("인증번호 보내기 성공"),
    SUCCESS_AUT("인증 코드 인증 성공");

    private final String message;
}