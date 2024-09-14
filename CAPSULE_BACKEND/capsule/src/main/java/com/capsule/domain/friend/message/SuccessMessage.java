package com.capsule.domain.friend.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    SUCCESS_ACCEPT_FRIEND("친구 수락 성공");

    private final String message;
}
