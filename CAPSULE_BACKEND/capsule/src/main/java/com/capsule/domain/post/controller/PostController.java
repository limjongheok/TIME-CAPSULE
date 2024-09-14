package com.capsule.domain.post.controller;

import com.capsule.domain.post.message.ResponseDto.PostsResponseDto;
import com.capsule.domain.post.message.SuccessMessage;
import com.capsule.domain.post.requestDto.WritePostRequestDto;
import com.capsule.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping("/write/{capsuleId}")
    public ResponseEntity<?> writePost(Authentication authentication, @RequestBody WritePostRequestDto writePostRequestDto, @PathVariable long capsuleId){
        postService.writePost(authentication,writePostRequestDto,capsuleId);
        return ResponseEntity.ok().body(SuccessMessage.SUCCESS_WRITE);
    }

    @GetMapping("/read/{capsuleId}")
    public ResponseEntity<?> lookCapsulePost(Authentication authentication, @PathVariable long capsuleId){
        List<PostsResponseDto> results  =postService.readCapsulePost(authentication,capsuleId);
        return ResponseEntity.ok().body(results);
    }
}
