package com.capsule.domain.friend.message.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindFriendResponseDto {
    private String friendEmail;
    private String friendName;
    private String friendImgUrl;
}
