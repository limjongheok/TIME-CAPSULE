package com.capsule.domain.favorite.message.responseDto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FavoriteResponseDto {
    private Long favoriteId;
    private Long memberId;
    private String tripName;
    private String mapX;
    private String mapY;
    private String firstImage;
    private boolean state;
}
