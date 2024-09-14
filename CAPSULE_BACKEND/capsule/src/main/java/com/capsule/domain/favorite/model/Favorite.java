package com.capsule.domain.favorite.model;

import com.capsule.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Favorite {
    @Id
    @Column(name="favorite_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;

    @ManyToOne
    @JoinColumn(name= "member_id")
    private Member member;

    @Column(name="trip_name")
    private String tripName;

    @Column(name="map_x")
    private String mapX;

    @Column(name="map_y")
    private String mapY;

    @Column(name="first_image")
    private String firstImage;

    @Column(name = "state")
    private boolean state;

    @Builder
    public Favorite(Member member, String tripName, String mapX, String mapY, String firstImage, boolean state) {
        this.member = member;
        this.tripName = tripName;
        this.mapX = mapX;
        this.mapY = mapY;
        this.firstImage = firstImage;
        this.state = state;
    }

    public static Favorite createFavorite(Member member, String tripName, String mapX, String mapY, String firstImage, boolean state) {
        return Favorite.builder()
                .member(member)
                .tripName(tripName)
                .mapX(mapX)
                .mapY(mapY)
                .firstImage(firstImage)
                .state(state)
                .build();
    }
}
