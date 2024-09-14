package com.capsule.domain.favorite.repository;

import com.capsule.domain.favorite.model.Favorite;
import com.capsule.domain.favorite.requestDto.FavoriteDto;
import com.capsule.domain.member.model.Member;

import java.util.List;

public interface FavoriteRepositoryCustom {

    List<Favorite> findFavoritesByEmail(String email);

    List<Favorite> findFavoritesByMember(Member member);

    Favorite findFavoriteById(Long favoriteId);

    boolean duplicateFavoriteByTripAndName(Member member, String tripName);

    List<FavoriteDto> sortedFavoriteByCountTripName();
}
