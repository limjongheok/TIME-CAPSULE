package com.capsule.domain.friend.service;

import com.capsule.domain.friend.message.responseDto.FindFriendResponseDto;
import com.capsule.domain.friend.model.Friend;
import com.capsule.domain.friend.repository.FriendRepository;
import com.capsule.domain.friend.requestDto.AcceptFriendRequestDto;
import com.capsule.domain.friend.requestDto.FindFriendByEmailRequestDto;
import com.capsule.domain.friendRequest.model.FriendRequest;
import com.capsule.domain.friendRequest.repository.FriendRequestRepository;
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
public class FriendService {

    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void friendAccept(Authentication authentication, AcceptFriendRequestDto dto){
        Member acceptor = ((MemberDetails) authentication.getPrincipal()).getMember();
        Member subject = memberRepository.findByEmailAndState(dto.getFriendEmail(),true).orElseThrow(() ->
                new ErrorResponse(ExceptionMessage.NOT_FOUND_MEMBER));
        log.info("acceptor: {}", acceptor.getEmail());
        log.info("subject: {}", subject.getEmail());

        FriendRequest friendRequest = friendRequestRepository.findByFriendIdAndMember_IdAndSuccess(acceptor.getId(), subject.getId(),false).orElseThrow(() ->
                new ErrorResponse(ExceptionMessage.NOT_FOUND_FRIEND_REQUEST));

        friendRequest.acceptRequest();
        friendRequestRepository.save(friendRequest);

        Friend acceptorFriend = Friend.createFriend(subject.getId(),acceptor);
        Friend subjectFriend = Friend.createFriend(acceptor.getId(),subject);

        friendRepository.save(acceptorFriend);
        friendRepository.save(subjectFriend);
    }

    public List<FindFriendResponseDto> findFriends(Authentication authentication){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        List<Member> findFriends = friendRepository.findAllByFriends(member.getId());

        List<FindFriendResponseDto> results = new ArrayList();
        for(Member friend : findFriends){
            log.info("friendEamil: {}", friend.getEmail());
            if(!friend.isState()) continue;
            FindFriendResponseDto findFriendResponseDto = new FindFriendResponseDto(friend.getEmail(),friend.getName(),friend.getImgUrl());
            results.add(findFriendResponseDto);
        }
        return results;
    }

    public List<FindFriendResponseDto> findFriendsByEmail(Authentication authentication, FindFriendByEmailRequestDto dto){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        List<Member> findFriends = friendRepository.findFriendsByEmail(member.getId(),dto.getFriendEmail());

        List<FindFriendResponseDto> results = new ArrayList();
        for(Member friend : findFriends){
            log.info("friendEamil: {}", friend.getEmail());
            if(!friend.isState())continue;
            FindFriendResponseDto findFriendResponseDto = new FindFriendResponseDto(friend.getEmail(),friend.getName(),friend.getImgUrl());
            results.add(findFriendResponseDto);
        }
        return results;
    }


}
