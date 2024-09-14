package com.capsule.domain.memberCapsule.controller;

import com.capsule.domain.memberCapsule.message.SuccessMessage;
import com.capsule.domain.memberCapsule.message.responseDto.ReadCompleteResponseDto;
import com.capsule.domain.memberCapsule.service.MemberCapsuleService;
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
public class MemberCapsuleController {

    private final MemberCapsuleService memberCapsuleService;

    @GetMapping("/read/complete/capsule")
    public ResponseEntity<?> completeReadCapsule(Authentication authentication){
        List<ReadCompleteResponseDto> results = memberCapsuleService.checkReadCapsule(authentication);
        return ResponseEntity.ok().body(results);
    }

    @GetMapping("/write/capsule/member/{capsuleId}")
    public ResponseEntity<?> possibleWriteCapsule(Authentication authentication, @PathVariable long capsuleId){
        memberCapsuleService.checkWriteCapsule(authentication,capsuleId);
        return ResponseEntity.ok().body(SuccessMessage.SUCCESS_CAN_WRITE);
    }
}
