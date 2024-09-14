package com.capsule.domain.capsule.eventListener;

import com.capsule.global.event.Event;
import com.capsule.domain.memberCapsule.model.MemberCapsule;
import com.capsule.domain.memberCapsule.repository.MemberCapsuleRepository;
import com.capsule.domain.capsule.model.Capsule;
import com.capsule.domain.capsule.repository.CapsuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CapsuleEventListener {

    private final CapsuleRepository capsuleRepository;
    private final MemberCapsuleRepository memberCapsuleRepository;
    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    @Async
    @Transactional
    public void handleCapsuleEvent(CapsuleEvent event){
        List<Capsule> capsules = event.getRooms();
        for(Capsule capsule : capsules){
            capsule.readAbleCapsule();
            capsule.writeDisableCapsule();
            capsuleRepository.save(capsule);

            List<MemberCapsule> memberCapsules = memberCapsuleRepository.findAllByCapsule(capsule);
            for(MemberCapsule memberCapsule : memberCapsules){
                memberCapsule.readAble();
                memberCapsuleRepository.save(memberCapsule);
                eventPublisher.publishEvent(new Event(memberCapsule.getMember(), memberCapsule.getCapsule()));
            }
        }
    }
}
