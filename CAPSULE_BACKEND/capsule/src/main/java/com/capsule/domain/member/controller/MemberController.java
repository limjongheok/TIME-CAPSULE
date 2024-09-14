package com.capsule.domain.member.controller;

import com.capsule.domain.member.message.responseDto.FindResponseDto;
import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.message.SuccessMessage;
import com.capsule.domain.member.message.responseDto.DetailResponseDto;
import com.capsule.domain.member.requestDto.FindRequestDto;
import com.capsule.domain.member.requestDto.JoinDto;
import com.capsule.domain.member.requestDto.UpdateRequestDto;
import com.capsule.domain.member.service.MemberService;
import com.capsule.global.security.auth.MemberDetails;
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
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinDto joinDto){
        memberService.signUpMember(joinDto);
        return ResponseEntity.ok().body(SuccessMessage.SUCCESS_SIGNUP_MEMBER);
    }

    @GetMapping
    public ResponseEntity<?> detail(Authentication authentication){
        Member member = ((MemberDetails)authentication.getPrincipal()).getMember();
        DetailResponseDto response = new DetailResponseDto(member.getEmail(), member.getName(), member.getImgUrl(), member.getPhoneNumber());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> findMember(@RequestBody FindRequestDto findRequestDto){
        List<FindResponseDto> results = memberService.findMemberByEmail(findRequestDto);
        return ResponseEntity.ok().body(results);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findMembers(Authentication authentication){
        List<FindResponseDto> results = memberService.findMembers(authentication);
        return ResponseEntity.ok().body(results);
    }

    @PostMapping("/find")
    public ResponseEntity<?> findMembers(Authentication authentication ,@RequestBody FindRequestDto findRequestDto){
        List<FindResponseDto> results = memberService.findMemberNoFriend(authentication,findRequestDto);
        return ResponseEntity.ok().body(results);
    }

    @PostMapping("/find/friend")
    public ResponseEntity<?> findFriends(@RequestBody FindRequestDto findRequestDto) {
        List<FindResponseDto> results = memberService.findFriends(findRequestDto);
        return ResponseEntity.ok().body(results);
    }
    @GetMapping("/auth")
    public ResponseEntity<?> authMember(){
        return ResponseEntity.ok().body(SuccessMessage.SUCCESS_AUTH);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMember(Authentication authentication){
        memberService.deleteMember(authentication);
        return ResponseEntity.ok().body(SuccessMessage.SUCCESS_DELETE_MEMBER);
    }

    @PutMapping
    public ResponseEntity<?> updateMember(Authentication authentication, @RequestBody UpdateRequestDto requestDto){
        DetailResponseDto responseDto = memberService.updateMember(authentication, requestDto);
        return ResponseEntity.ok().body(responseDto);

    }
}
