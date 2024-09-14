package com.capsule.domain.AI.controller;

import com.capsule.domain.AI.message.SuccessMessage;
import com.capsule.domain.AI.requestDto.CreateAIImgRequestDto;
import com.capsule.domain.AI.service.AIImgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/img")
@RequiredArgsConstructor
@Slf4j
public class AIImgController {

    private final AIImgService aiImgService;

    @PostMapping("/generate")
    public ResponseEntity<?> createAIImg(@RequestBody CreateAIImgRequestDto requestDto){
        aiImgService.saveAIImg(requestDto);
        return ResponseEntity.ok().body(SuccessMessage.SUCCESS_CREATE_AI_IMG);
    }

    @GetMapping("/generate/{capsuleId}")
    public ResponseEntity<?> getAIImg(@PathVariable Long capsuleId){
        return ResponseEntity.ok().body(aiImgService.getAIImg(capsuleId));
    }
}
