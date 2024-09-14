package com.capsule.domain.favorite.controller;

import com.capsule.domain.favorite.message.SuccessMessage;
import com.capsule.domain.favorite.message.responseDto.FavoriteResponseDto;
import com.capsule.domain.favorite.requestDto.FavoriteDto;
import com.capsule.domain.favorite.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/like")
    public ResponseEntity<?> favoriteFindById (Authentication authentication) {
        List<FavoriteResponseDto> results = favoriteService.findFavoriteByMember(authentication);
        return ResponseEntity.ok().body(results);
    }

    @PostMapping("/like")
    public ResponseEntity<?> favoriteAdd (Authentication authentication, @RequestBody @Valid FavoriteDto favoriteDto) {
        FavoriteResponseDto result = favoriteService.favoriteAdd(authentication, favoriteDto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/like/{title}")
    public ResponseEntity<?> favoriteDelete (@PathVariable String title) {
        favoriteService.favoriteDelete(title);
        return ResponseEntity.ok().body(SuccessMessage.SUCCESS_DELETE_FAVORITE);
    }

}
