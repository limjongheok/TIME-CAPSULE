package com.capsule.domain.friendRequest.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FriendRequestRequestDto {

    @NotNull
    private String email;
}
