package com.capsule.domain.attraction.controller;

import com.capsule.domain.attraction.service.AttractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user")
public class AttractionController {
    private final AttractionService attractionService;

    @GetMapping("/place")
    public ResponseEntity<?> attractionSortedByPlace(
            Authentication authentication,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam String mapX,
            @RequestParam String mapY) throws UnsupportedEncodingException, ParseException {
        log.info("place check : {} {} {} {}", page, size, mapX, mapY);

        // 페이지와 사이즈 유효성 검증
        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body("Invalid page or size parameters");
        }

        return ResponseEntity.ok().body(attractionService.attractionSortedByPlace(authentication, page, size, mapX, mapY));
    }

    @GetMapping("/favorite")
    public ResponseEntity<?> attractionSortedByFavorite() {
        return ResponseEntity.ok().body(attractionService.attractionSortedByFavorite());
    }

    @GetMapping("/favorite/{email}")
    public ResponseEntity<?> attractionMyFavorite(@PathVariable String email) {
        return ResponseEntity.ok().body(attractionService.attractionMyFavorite(email));
    }
}
