package com.capsule.domain.favorite.service;

import com.capsule.domain.attraction.service.AttractionService;
import com.capsule.domain.favorite.message.responseDto.FavoriteResponseDto;
import com.capsule.domain.favorite.model.Favorite;
import com.capsule.domain.favorite.repository.FavoriteRepository;
import com.capsule.domain.favorite.requestDto.FavoriteDto;
import com.capsule.domain.member.model.Member;
import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import com.capsule.global.security.auth.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final AttractionService attractionService;

    public List<FavoriteResponseDto> findFavoriteByMember(Authentication authentication) {
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();

        List<Favorite> favoriteList = favoriteRepository.findFavoritesByMember(member);

        List<FavoriteResponseDto> results = new ArrayList<>();
        for(Favorite favorite : favoriteList) {
            FavoriteResponseDto favoriteResponseDto = new FavoriteResponseDto(favorite.getFavoriteId(),
                    favorite.getMember().getId(),
                    favorite.getTripName(),
                    favorite.getMapY(),
                    favorite.getMapX(),
                    favorite.getFirstImage(),
                    favorite.isState());
            results.add(favoriteResponseDto);
        }

        return results;
    }

    @Transactional
    public FavoriteResponseDto favoriteAdd(Authentication authentication, FavoriteDto favoriteDto) {
        log.info("좋아요 추가 객체 확인 : {}",  favoriteDto);
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();

        if(favoriteRepository.duplicateFavoriteByTripAndName(member, favoriteDto.getTripName())) {
            throw new ErrorResponse(ExceptionMessage.DUPLICATED_FAVORITE);
        }

        Favorite favorite = Favorite.createFavorite(
                member,
                favoriteDto.getTripName(),
                favoriteDto.getMapX(),
                favoriteDto.getMapY(),
                favoriteDto.getFirstImage(),
                favoriteDto.isState());
        favoriteRepository.save(favorite);

        FavoriteResponseDto result = new FavoriteResponseDto();
        result.setFavoriteId(favorite.getFavoriteId());
        result.setTripName(favorite.getTripName());
        result.setMapX(favorite.getMapX());
        result.setMapY(favorite.getMapY());
        result.setFirstImage(favorite.getFirstImage());
        result.setState(favorite.isState());
        result.setMemberId(favorite.getMember().getId());

        log.info("add like response : {}",  result);

        return result;
    }

    @Transactional
    public void favoriteDelete(String tripName) {
        Favorite favorite = favoriteRepository.findByTripName(tripName).orElseThrow(() ->{
            throw new ErrorResponse(ExceptionMessage.NOT_FOUND_FAVORITE);
        });
        log.info("좋아요 제거 객체 확인 : {}",  favorite);
        favoriteRepository.delete(favorite);
    }

}
