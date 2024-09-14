package com.capsule.domain.favorite.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class FavoriteDto {
    @NotBlank
    private String tripName;

    @NotBlank
    private String mapX;

    @NotBlank
    private String mapY;

    private String firstImage;

    @NotNull
    private boolean state;

    private long count;
}
