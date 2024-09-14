package com.capsule.domain.friend.controller;

import com.capsule.domain.friend.message.SuccessMessage;
import com.capsule.domain.friend.message.responseDto.FindFriendResponseDto;
import com.capsule.domain.friend.requestDto.AcceptFriendRequestDto;
import com.capsule.domain.friend.requestDto.FindFriendByEmailRequestDto;
import com.capsule.domain.friend.service.FriendService;
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
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/friend")
    public ResponseEntity<?> friendAccept(Authentication authentication, @RequestBody @Valid AcceptFriendRequestDto dto){
        friendService.friendAccept(authentication,dto);
        return ResponseEntity.ok().body(SuccessMessage.SUCCESS_ACCEPT_FRIEND);
    }

    @GetMapping("/friends")
    public ResponseEntity<?> findFriends(Authentication authentication){
        List<FindFriendResponseDto> result = friendService.findFriends(authentication);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/friend/find")
    public ResponseEntity<?> findFriendsByNickName(Authentication authentication, @RequestBody FindFriendByEmailRequestDto dto){
        List<FindFriendResponseDto> result = friendService.findFriendsByEmail(authentication,dto);
        return ResponseEntity.ok().body(result);
    }

}
