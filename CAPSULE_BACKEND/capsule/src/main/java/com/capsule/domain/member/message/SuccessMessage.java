package com.capsule.domain.member.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SuccessMessage {

    SUCCESS_SIGNUP_MEMBER("회원가입 성공"),
    SUCCESS_LOGIN_MEMBER("로그인 성공"),
    SUCCESS_AUTH("허가"),
    SUCCESS_DELETE_MEMBER("회원가입 성공");

    private final String message;
}