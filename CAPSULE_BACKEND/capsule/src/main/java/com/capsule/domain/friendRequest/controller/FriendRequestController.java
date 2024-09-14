package com.capsule.domain.friendRequest.controller;

import com.capsule.domain.friendRequest.message.SuccessMessage;
import com.capsule.domain.friendRequest.message.responseDto.FriendRequestResponseDto;
import com.capsule.domain.friendRequest.requestDto.FriendRequestRequestDto;
import com.capsule.domain.friendRequest.service.FriendRequestService;
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
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    @PostMapping("/friend/request")
    public ResponseEntity<?> requestFriend(Authentication authentication, @RequestBody @Valid FriendRequestRequestDto requestDto){
        friendRequestService.friendRequest(authentication,requestDto);
        return ResponseEntity.ok().body(SuccessMessage.SUCCESS_REQUEST_FRIEND);
    }

    @GetMapping("/friend/request/received")
    public ResponseEntity<?> friendsRequestReceived(Authentication authentication){
        List<FriendRequestResponseDto> results = friendRequestService.receivedRequest(authentication);
        return ResponseEntity.ok().body(results);
    }

    @GetMapping("/friend/request/send")
    public ResponseEntity<?> friendsRequestSend(Authentication authentication){
        List<FriendRequestResponseDto> results = friendRequestService.sendRequest(authentication);
        return ResponseEntity.ok().body(results);
    }

    @PostMapping("/friend/request/delete")
    public ResponseEntity<?> deleteFriendRequest(Authentication authentication, @RequestBody @Valid FriendRequestRequestDto requestDto){
        friendRequestService.deleteFriendRequest(authentication,requestDto);
        return ResponseEntity.ok().body(SuccessMessage.SUCCESS_DELETE_REQUEST_FRIEND);
    }
}
