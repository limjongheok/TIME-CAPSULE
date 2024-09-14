package com.capsule.domain.capsule.service;

import com.capsule.domain.capsule.message.CreateCapsuleResponseDto;
import com.capsule.domain.capsule.message.AbleResponseDto;
import com.capsule.domain.friend.repository.FriendRepository;
import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.repository.MemberRepository;
import com.capsule.domain.memberCapsule.model.MemberCapsule;
import com.capsule.domain.memberCapsule.model.Read;
import com.capsule.domain.memberCapsule.repository.MemberCapsuleRepository;
import com.capsule.domain.capsule.model.Capsule;
import com.capsule.domain.capsule.repository.CapsuleRepository;
import com.capsule.domain.capsule.requestDto.CreateCapsuleRequestDto;
import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import com.capsule.global.security.auth.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CapsuleService {

    private final CapsuleRepository capsuleRepository;
    private final MemberRepository memberRepository;
    private final MemberCapsuleRepository memberCapsuleRepository;
    private final FriendRepository friendRepository;

    @Transactional
    public CreateCapsuleResponseDto createCapsule(Authentication authentication, CreateCapsuleRequestDto dto){
        Capsule createCapsule = Capsule.createCapsule(dto.getTitle(),dto.getEndTime(),dto.getLatitude(), dto.getLongitude(),false,true);
        capsuleRepository.save(createCapsule);

        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        if(memberCapsuleRepository.existsByMemberAndCapsule(member,createCapsule))
            throw new ErrorResponse(ExceptionMessage.EXIST_ROOM_MEMBER);

        memberCapsuleRepository.save(MemberCapsule.createMemberRoom(member,createCapsule, Read.READ_DISABLE,false));

        List<String> membersEmail = dto.getMembersEmail();
        for(String memberEmail : membersEmail){
            Member frinedMember = memberRepository.findByEmailAndState(memberEmail,true).orElseThrow(() ->
                    new ErrorResponse(ExceptionMessage.NOT_FOUND_MEMBER));

            // 나랑 맺은 친구인지 확인
            if(!friendRepository.existsByFriendIdAndMember_Id(frinedMember.getId(),member.getId()))
                throw new ErrorResponse(ExceptionMessage.NOT_FOUND_FRIEND);

            if(memberCapsuleRepository.existsByMemberAndCapsule(frinedMember,createCapsule))
                throw new ErrorResponse(ExceptionMessage.EXIST_ROOM_MEMBER);

            MemberCapsule memberCapsule = MemberCapsule.createMemberRoom(frinedMember,createCapsule,Read.READ_DISABLE,false);
            memberCapsuleRepository.save(memberCapsule);
        }
        return new CreateCapsuleResponseDto(createCapsule.getId());
    }

    public AbleResponseDto readCheckCapsule(Authentication authentication, long capsuleId){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        MemberCapsule memberCapsule = memberCapsuleRepository.findByMemberAndCapsule_Id(member,capsuleId).orElseThrow(() ->
                new ErrorResponse(ExceptionMessage.CANNOT_READ));

        Capsule capsule = memberCapsule.getCapsule();
        if(memberCapsule.getReadCheck().equals(Read.READ_DISABLE)){
            throw new ErrorResponse(ExceptionMessage.CANNOT_READ);
        }

        if(!memberCapsule.getCapsule().isReadAble())
            throw new ErrorResponse(ExceptionMessage.CANNOT_READ);
        return new AbleResponseDto(capsule.getTitle());
    }

    public AbleResponseDto writeCheckCapsule(Authentication authentication, long capsuleId){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        MemberCapsule memberCapsule = memberCapsuleRepository.findByMemberAndCapsule_Id(member,capsuleId).orElseThrow(() ->
                new ErrorResponse(ExceptionMessage.CANNOT_WRITE));
        Capsule capsule = memberCapsule.getCapsule();

        if(memberCapsule.isWriteCheck()){
            throw new ErrorResponse(ExceptionMessage.CANNOT_WRITE);
        } // 이미 써진것

        if(!memberCapsule.getCapsule().isWriteAble()){
            throw new ErrorResponse(ExceptionMessage.CANNOT_WRITE);
        }
        return new AbleResponseDto(capsule.getTitle());
    }
}
