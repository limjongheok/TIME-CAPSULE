package com.capsule.domain.favorite.repository;

import com.capsule.domain.favorite.model.Favorite;
import com.capsule.domain.favorite.requestDto.FavoriteDto;
import com.capsule.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> , FavoriteRepositoryCustom{

    List<Favorite> findFavoritesByEmail(String email);

    List<Favorite> findFavoritesByMember(Member member);

    Favorite findFavoriteById(Long favoriteId);
    Optional<Favorite> findByTripName(String tripName);

    boolean duplicateFavoriteByTripAndName(Member member, String tripName);

    List<FavoriteDto> sortedFavoriteByCountTripName();
}
