package com.capsule.domain.friend.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AcceptFriendRequestDto {

    @NotNull
    private String friendEmail;
}
