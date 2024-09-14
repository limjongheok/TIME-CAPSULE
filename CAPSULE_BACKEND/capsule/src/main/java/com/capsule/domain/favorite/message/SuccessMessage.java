package com.capsule.domain.favorite.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SuccessMessage {
    SUCCESS_GET_FAVORITE_BY_EMAIL("좋아요 리스트 조회 성공"),
    SUCCESS_DELETE_FAVORITE("좋아요 삭제 성공"),
    SUCCESS_ADD_FAVORITE("좋아요 입력 성공");

    private final String message;
}
