package com.capsule.domain.capsule.controller;

import com.capsule.domain.capsule.message.CreateCapsuleResponseDto;
import com.capsule.domain.capsule.message.SuccessMessage;
import com.capsule.domain.capsule.message.AbleResponseDto;
import com.capsule.domain.capsule.requestDto.CreateCapsuleRequestDto;
import com.capsule.domain.capsule.service.CapsuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class CapsuleController {

    private final CapsuleService capsuleService;

    @PostMapping("/capsule")
    public ResponseEntity<?> createCapsule(Authentication authentication, @RequestBody @Valid CreateCapsuleRequestDto dto){
        CreateCapsuleResponseDto responseDto = capsuleService.createCapsule(authentication,dto);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/capsule/read/check/{capsuleId}")
    public ResponseEntity<?> readCheckCapsule(Authentication authentication, @PathVariable long capsuleId){
        AbleResponseDto responseDto = capsuleService.readCheckCapsule(authentication,capsuleId);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/capsule/write/check/{capsuleId}")
    public ResponseEntity<?> writeCheckCapsule(Authentication authentication, @PathVariable long capsuleId){
        AbleResponseDto responseDto = capsuleService.writeCheckCapsule(authentication,capsuleId);
        return ResponseEntity.ok().body(responseDto);
    }


}
