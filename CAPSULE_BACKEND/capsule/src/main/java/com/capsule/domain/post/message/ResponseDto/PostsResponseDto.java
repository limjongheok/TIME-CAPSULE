package com.capsule.domain.post.message.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostsResponseDto {

    private String name;
    private String imgUrl;
    private String memo;
}
