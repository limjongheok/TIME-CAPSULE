package com.capsule.domain.member.message.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DetailResponseDto {

    private String email;
    private String name;
    private String imgUrl;
    private String phoneNumber;
}
