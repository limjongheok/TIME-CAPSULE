package com.capsule.domain.friendRequest.service;

import com.capsule.domain.fcm.service.FcmService;
import com.capsule.domain.friend.model.Friend;
import com.capsule.domain.friend.repository.FriendRepository;
import com.capsule.domain.friendRequest.message.responseDto.FriendRequestResponseDto;
import com.capsule.domain.friendRequest.model.FriendRequest;
import com.capsule.domain.friendRequest.repository.FriendRequestRepository;
import com.capsule.domain.friendRequest.requestDto.FriendRequestRequestDto;
import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.repository.MemberRepository;
import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import com.capsule.global.security.auth.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;
    private final FcmService fcmService;

    @Transactional
    public void friendRequest(Authentication authentication, FriendRequestRequestDto requestDto){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        Member friend = memberRepository.findByEmailAndState(requestDto.getEmail(),true).orElseThrow(() ->
                new ErrorResponse(ExceptionMessage.NOT_FOUND_MEMBER));

        // 현재 내가 요청한 해당 친구에 대해 친구 요청은 없는지
        if(friendRequestRepository.existsByFriendIdAndMember_Id(friend.getId(),member.getId()))
            throw new ErrorResponse(ExceptionMessage.EXIST_FRIEND_REQUEST);

        //친구가 나한테한 요청은 없는지
        if(friendRequestRepository.existsByFriendIdAndMember_Id(member.getId(),friend.getId()))
            throw new ErrorResponse(ExceptionMessage.EXIST_FRIEND_REQUEST);

        // 현재 나의 친구로 등록 되어 있는지는 않는지 검증
        if(friendRepository.existsByFriendIdAndMember_Id(friend.getId(),member.getId()))
            throw new ErrorResponse(ExceptionMessage.EXIST_FRIEND);

        FriendRequest friendRequest = FriendRequest.createFriendRequest(friend.getId(),member,false);
        friendRequestRepository.save(friendRequest);

        fcmService.sendFriend(member, friend);
    }

    public List<FriendRequestResponseDto> receivedRequest(Authentication authentication){
        Member receivedMember = ((MemberDetails) authentication.getPrincipal()).getMember();
        List<FriendRequest> requestMembers = friendRequestRepository.findByFriendIdAndSuccess(receivedMember.getId(),false);

        List<FriendRequestResponseDto> results = new ArrayList();
        for(FriendRequest requestMember : requestMembers){
            if(!requestMember.getMember().isState()) continue;
            FriendRequestResponseDto receivedRequestResponseDto = new FriendRequestResponseDto(requestMember.getMember().getEmail(),requestMember.getMember().getName(),requestMember.getMember().getImgUrl());
            results.add(receivedRequestResponseDto);
        }
        return results;
    }

    public List<FriendRequestResponseDto> sendRequest(Authentication authentication){
        Member receivedMember = ((MemberDetails) authentication.getPrincipal()).getMember();
        List<Member> requestMembers = friendRequestRepository.findSendRequestMemberSuccess(receivedMember.getId(),false);

        List<FriendRequestResponseDto> results = new ArrayList();
        for(Member member : requestMembers){
            if(!member.isState()) continue;
            FriendRequestResponseDto sendRequestResponseDto = new FriendRequestResponseDto(member.getEmail(),member.getName(),member.getImgUrl());
            results.add(sendRequestResponseDto);
        }
        return results;
    }

    @Transactional
    public void deleteFriendRequest(Authentication authentication, FriendRequestRequestDto requestDto){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        Member sendFriend = memberRepository.findByEmailAndState(requestDto.getEmail(),true).orElseThrow(() -> {
            throw  new ErrorResponse(ExceptionMessage.NOT_FOUND_MEMBER);
        });

        FriendRequest friendRequest = friendRequestRepository.findByFriendIdAndMember_IdAndSuccess(member.getId(),sendFriend.getId(),false).orElseThrow(() ->{
            throw new ErrorResponse(ExceptionMessage.NOT_FOUND_FRIEND_REQUEST);
        });
       friendRequestRepository.delete(friendRequest);
    }

}
