package com.capsule.domain.post.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {
    SUCCESS_WRITE("쪽지 생성");

    private final String message;
}