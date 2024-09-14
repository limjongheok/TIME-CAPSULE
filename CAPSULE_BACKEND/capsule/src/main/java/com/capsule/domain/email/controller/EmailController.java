package com.capsule.domain.email.controller;

import com.capsule.domain.email.message.SuccessMessage;
import com.capsule.domain.email.requestDto.CheckAuthCodeRequestDto;
import com.capsule.domain.email.requestDto.SendAuthCodeRequestDto;
import com.capsule.domain.email.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send/auth")
    public ResponseEntity<?> sendAuthCodeEmail(@RequestBody @Valid SendAuthCodeRequestDto sendAuthCodeRequestDto) throws MessagingException, UnsupportedEncodingException {
        emailService.sendAuthenticationCode(sendAuthCodeRequestDto);
        return ResponseEntity.ok().body(SuccessMessage.SUCCESS_SEND_AUTH);
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkAuthCode(@RequestBody @Valid CheckAuthCodeRequestDto checkAuthCodeRequestDto) throws MessagingException, UnsupportedEncodingException {
        emailService.checkAuthCode(checkAuthCodeRequestDto);
        return ResponseEntity.ok().body(SuccessMessage.SUCCESS_SEND_AUTH);
    }
}
