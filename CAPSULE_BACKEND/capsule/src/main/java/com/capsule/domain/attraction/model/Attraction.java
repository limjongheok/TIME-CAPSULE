package com.capsule.domain.attraction.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Attraction {
    private Long favoriteId;
    private String title;
    private String firstimage;
    private String mapx;
    private String mapy;
    private boolean state;

    public Attraction(String tripName, String firstImage, String mapX, String mapY, boolean state) {
        this.favoriteId = (long) -1;
        this.title = tripName;
        this.firstimage = firstImage;
        this.mapx = mapX;
        this.mapy = mapY;
        this.state = state;
    }
}
