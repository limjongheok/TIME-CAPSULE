package com.capsule.domain.friendRequest.message.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FriendRequestResponseDto {

    private String friendEmail;
    private String friendName;
    private String friendImgUrl;
}
