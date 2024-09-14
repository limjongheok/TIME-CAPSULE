package com.capsule.domain.friendRequest.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SuccessMessage {

    SUCCESS_REQUEST_FRIEND("친구 요청 성공"),
    SUCCESS_DELETE_REQUEST_FRIEND("친구 요청 삭제 성공");

    private final String message;
}
