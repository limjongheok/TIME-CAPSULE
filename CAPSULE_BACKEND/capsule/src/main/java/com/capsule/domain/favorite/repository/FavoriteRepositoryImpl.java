package com.capsule.domain.favorite.repository;

import com.capsule.domain.favorite.model.Favorite;
import com.capsule.domain.favorite.model.QFavorite;
import com.capsule.domain.favorite.requestDto.FavoriteDto;
import com.capsule.domain.member.model.Member;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class FavoriteRepositoryImpl implements FavoriteRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private QFavorite favorite = QFavorite.favorite;

    public FavoriteRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Favorite> findFavoritesByEmail(String email) {
        return (List<Favorite>) jpaQueryFactory.from(favorite).where(favorite.member.email.eq(email)).fetch();
    }

    @Override
    public List<Favorite> findFavoritesByMember(Member member) {
        return (List<Favorite>) jpaQueryFactory.from(favorite).where(favorite.member.eq(member)).fetch();
    }

    @Override
    public Favorite findFavoriteById(Long favoriteId) {
        return (Favorite) jpaQueryFactory.from(favorite).where(favorite.favoriteId.eq(favoriteId)).fetchOne();
    }

    @Override
    public boolean duplicateFavoriteByTripAndName(Member member, String tripName) {
        return jpaQueryFactory.from(favorite).where(favorite.member.eq(member).and(favorite.tripName.eq(tripName))).fetchOne() != null;
    }

    @Override
    public List<FavoriteDto> sortedFavoriteByCountTripName() {

        return jpaQueryFactory
                .select(Projections.constructor(FavoriteDto.class,
                        favorite.tripName,
                        favorite.mapX,
                        favorite.mapY,
                        favorite.firstImage,
                        favorite.state,
                        favorite.tripName.count().as("count"))
                )
                .from(favorite)
                .groupBy(favorite.tripName, favorite.mapX, favorite.mapY, favorite.firstImage, favorite.state)
                .orderBy(Expressions.numberPath(Long.class, "count").desc())
                .fetch();
    }
}
