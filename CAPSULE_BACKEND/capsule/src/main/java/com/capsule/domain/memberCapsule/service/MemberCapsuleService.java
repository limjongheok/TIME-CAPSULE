package com.capsule.domain.memberCapsule.service;

import com.capsule.domain.member.model.Member;
import com.capsule.domain.memberCapsule.message.responseDto.ReadCompleteResponseDto;
import com.capsule.domain.memberCapsule.model.MemberCapsule;
import com.capsule.domain.memberCapsule.model.Read;
import com.capsule.domain.memberCapsule.repository.MemberCapsuleRepository;
import com.capsule.domain.capsule.model.Capsule;
import com.capsule.domain.capsule.repository.CapsuleRepository;
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
public class MemberCapsuleService {

    private final MemberCapsuleRepository memberCapsuleRepository;
    private final CapsuleRepository capsuleRepository;

    public List<ReadCompleteResponseDto> checkReadCapsule(Authentication authentication){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();

        List<MemberCapsule> memberCapsules = memberCapsuleRepository.findAllByMemberAndReadCheck(member,Read.READ_COMPLETE);

        List<ReadCompleteResponseDto> results = new ArrayList<>();
        for(MemberCapsule memberCapsule : memberCapsules){
            Capsule capsule = memberCapsule.getCapsule();
            ReadCompleteResponseDto responseDto = new ReadCompleteResponseDto(capsule.getId(),capsule.getLatitude(),capsule.getLongitude());
            results.add(responseDto);
        }
        return results;
    }

    public void checkWriteCapsule(Authentication authentication, long roomId){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        Capsule capsule = capsuleRepository.findById(roomId).orElseThrow(() ->
                new ErrorResponse(ExceptionMessage.NOT_FOUND_ROOM));
        MemberCapsule memberCapsule = memberCapsuleRepository.findByMemberAndCapsule(member,capsule);
        if(!memberCapsule.isWriteCheck()){
            throw new ErrorResponse(ExceptionMessage.CANNOT_READ);
        }
    }


}
