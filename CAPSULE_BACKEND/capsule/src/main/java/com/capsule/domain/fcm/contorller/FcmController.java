package com.capsule.domain.fcm.contorller;

import com.capsule.domain.fcm.requestDto.FcmRequestDto;
import com.capsule.domain.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class FcmController {

    private final FcmService fcmService;

    @PostMapping("/fcm")
    public void saveFcm(Authentication authentication,  @RequestBody FcmRequestDto requestDto){
        fcmService.saveFcm(authentication,requestDto);
    }

    @DeleteMapping("/fcm")
    public void deleteFcm(Authentication authentication){
        fcmService.deleteToken(authentication);
    }
}
