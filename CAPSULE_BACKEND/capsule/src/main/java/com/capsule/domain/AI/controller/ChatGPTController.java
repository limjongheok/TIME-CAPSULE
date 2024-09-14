package com.capsule.domain.AI.controller;

import com.capsule.domain.AI.requestDto.SplitNounRequestDto;
import com.capsule.domain.AI.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    @PostMapping("/check-profanity")
    public ResponseEntity<Boolean> checkProfanity(@RequestBody String text) {
        boolean isProfane = chatGPTService.isProfanity(text);
        return ResponseEntity.ok(isProfane);
    }

    @PostMapping("/split-noun")
    public ResponseEntity<String> splitNoun(@RequestBody SplitNounRequestDto requestDto) {
        String context = chatGPTService.splitNoun(requestDto.getText());
        return ResponseEntity.ok(context);
    }
}