package com.capsule.domain.memberCapsule.eventListener;

import com.capsule.domain.capsule.model.Capsule;
import com.capsule.domain.memberCapsule.model.MemberCapsule;
import com.capsule.domain.memberCapsule.model.Read;
import com.capsule.domain.memberCapsule.repository.MemberCapsuleRepository;
import com.capsule.domain.notification.Notification;
import com.capsule.global.event.Event;
import com.capsule.global.redis.RedisMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberCapsuleEventListener {

    private final MemberCapsuleRepository memberCapsuleRepository;
    private final RedisMessageService redisMessageService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEvent(Event event){
        if(!event.getMember().isState()) return;

        List<MemberCapsule> memberCapsules = memberCapsuleRepository.findAllByMemberAndReadCheck(event.getMember(), Read.READ_ABLE);

        List<Notification> notifications = new ArrayList<>();
        for (MemberCapsule memberCapsule : memberCapsules){
            Capsule capsule = memberCapsule.getCapsule();
            Notification notification = new Notification(capsule.getId(),capsule.getTitle(),capsule.getCreateDate().toLocalDate().toString(),capsule.getLatitude(), capsule.getLongitude());
            notifications.add(notification);
        }
        log.info("안읽은 캡슐 주인: {} ",event.getMember().getEmail());
        redisMessageService.publish(event.getMember().getEmail(), notifications);
    }
}
