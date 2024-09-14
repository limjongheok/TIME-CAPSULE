package com.capsule.domain.member.service;

import com.capsule.domain.email.repository.EmailRepository;

import com.capsule.domain.favorite.repository.FavoriteRepository;
import com.capsule.domain.fcm.repository.FcmRepository;
import com.capsule.domain.friend.repository.FriendRepository;
import com.capsule.domain.friendRequest.repository.FriendRequestRepository;
import com.capsule.domain.member.message.responseDto.DetailResponseDto;
import com.capsule.domain.member.message.responseDto.FindResponseDto;
import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.model.Role;
import com.capsule.domain.member.repository.MemberRepository;
import com.capsule.domain.member.requestDto.FindRequestDto;
import com.capsule.domain.member.requestDto.JoinDto;
import com.capsule.domain.member.requestDto.UpdateRequestDto;
import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import com.capsule.global.security.auth.MemberDetails;
import com.capsule.global.sse.SseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailRepository emailRepository;
    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FcmRepository fcmRepository;
    private final SseRepository sseRepository;

    @Transactional
    public void signUpMember(JoinDto joinDto){
        String email = joinDto.getEmail();
        if(!emailRepository.existsBySuccessEmail(email)){
            throw new ErrorResponse(ExceptionMessage.NOT_SUCCESS_EMAIL);
        }
        if(memberRepository.existsByEmailAndState(joinDto.getEmail(),true)){
            throw new ErrorResponse(ExceptionMessage.DUPLICATED_EMAIL_EXCEPTION);
        }
        Member member = Member.createMember(email, passwordEncoder.encode(joinDto.getPassword()), joinDto.getName(), joinDto.getPhoneNumber(), Role.ROLE_USER);
        memberRepository.save(member);
    }

    public List<FindResponseDto> findMemberByEmail(FindRequestDto findRequestDto){
        List<Member> members = memberRepository.findMembersEmailAndState(findRequestDto.getEmail());
        List<FindResponseDto> results = new ArrayList<>();

        for(Member member : members){
            FindResponseDto response = new FindResponseDto(member.getEmail(),member.getName(),member.getImgUrl());
            results.add(response);
        }
        return results;
    }

    public List<FindResponseDto> findMembers(Authentication authentication){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        List<Member> members = memberRepository.findAll();
        List<FindResponseDto> results = new ArrayList<>();

        for(Member findMember : members){
            if(friendRepository.existsByFriendIdAndMember_Id(findMember.getId(),member.getId())) continue;
            if(friendRequestRepository.existsByFriendIdAndMember_Id(findMember.getId(),member.getId())) continue;
            if(findMember.getId() == member.getId()) continue;
            if(friendRequestRepository.existsByFriendIdAndMember_Id(member.getId(),findMember.getId()))continue;
            FindResponseDto response = new FindResponseDto(findMember.getEmail(),findMember.getName(),findMember.getImgUrl());
            results.add(response);
        }
        return results;
    }

    public List<FindResponseDto> findMemberNoFriend(Authentication authentication ,FindRequestDto findRequestDto){

        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        List<Member> members = memberRepository.findMembersEmailAndState(findRequestDto.getEmail());
        List<FindResponseDto> results = new ArrayList<>();


        for(Member findMember : members){
            if(friendRepository.existsByFriendIdAndMember_Id(findMember.getId(),member.getId())) continue;
            if(findMember.getId() == member.getId()) continue;
            if(friendRequestRepository.existsByFriendIdAndMember_Id(findMember.getId(),member.getId())) continue;
            if(friendRequestRepository.existsByFriendIdAndMember_Id(member.getId(),findMember.getId()))continue;
            FindResponseDto response = new FindResponseDto(findMember.getEmail(),findMember.getName(),findMember.getImgUrl());
            results.add(response);
        }
        return results;
    }


    public List<FindResponseDto> findFriends(FindRequestDto findRequestDto) {
        Optional<Member> member = memberRepository.findByEmailAndState(findRequestDto.getEmail(), true);
        List<FindResponseDto> results = new ArrayList<>();

        List<Member> findFriends = friendRepository.findAllByFriends(member.get().getId());
        log.info("find Friends check : {}", member.get().getId());
        for (Member friend : findFriends) {
            log.info("find Friends : {} ", friend);
            FindResponseDto response = new FindResponseDto(friend.getEmail(), friend.getName(), friend.getImgUrl());
            results.add(response);
        }
        return results;
    }

    @Transactional
    public void deleteMember(Authentication authentication){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        member.deleteMember();
        fcmRepository.deleteByMember(member);
        sseRepository.deleteById(member.getEmail());
        memberRepository.save(member);
    }

    @Transactional
    public DetailResponseDto updateMember(Authentication authentication, UpdateRequestDto requestDto){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();

        System.out.println(requestDto.getName());
        System.out.println(requestDto.getPhoneNumber());
        if(requestDto.getName() != "" && member.getName() !=null){
            member.changeName(requestDto.getName());
        }
        if(requestDto.getPhoneNumber() != "" && member.getPhoneNumber() !=null){
            member.changePhoneNumber(requestDto.getPhoneNumber());
        }
        if(requestDto.getImgUrl() != "" && member.getImgUrl() !=null){
            member.changeImgUrl(requestDto.getImgUrl());
        }
        memberRepository.save(member);
        return new DetailResponseDto(member.getEmail(),member.getName(),member.getImgUrl(),member.getPhoneNumber());

    }

}
