package com.capsule.global.sse;

import com.capsule.domain.capsule.model.Capsule;
import com.capsule.domain.member.model.Member;
import com.capsule.domain.memberCapsule.model.MemberCapsule;
import com.capsule.domain.memberCapsule.model.Read;
import com.capsule.domain.memberCapsule.repository.MemberCapsuleRepository;
import com.capsule.domain.notification.Notification;
import com.capsule.global.redis.RedisMessageService;
import com.capsule.global.security.auth.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SseService {

    private final SseRepository sseRepository;
    private final RedisMessageService redisMessageService;
    private final MemberCapsuleRepository memberCapsuleRepository;
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    public SseEmitter connect(Authentication authentication){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();

        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        sseRepository.save(member.getEmail(),emitter);
        redisMessageService.subscribe(member.getEmail());
        List<Notification> notifications = notifications(member);

        emitter.onTimeout(
                () -> {
                    sseRepository.deleteById(member.getEmail());
                    redisMessageService.removeSubscribe(member.getEmail()); // 구독한 채널 삭제
                });
        emitter.onError((e) -> emitter.complete());
        emitter.onCompletion(() -> {
            sseRepository.deleteById(member.getEmail());
            redisMessageService.removeSubscribe(member.getEmail()); // 구독한 채널 삭제
        });

        send(notifications, member.getEmail(), emitter);
        return emitter;

    }

    private List<Notification> notifications(Member member){
        List<MemberCapsule> memberCapsules = memberCapsuleRepository.findAllByMemberAndReadCheck(member, Read.READ_ABLE);

        List<Notification> notifications = new ArrayList<>();
        for(MemberCapsule memberCapsule : memberCapsules){
            Capsule capsule  = memberCapsule.getCapsule();
            Notification notification = new Notification(capsule.getId(),capsule.getTitle(),capsule.getCreateDate().toLocalDate().toString(),capsule.getLatitude(),capsule.getLongitude());
            notifications.add(notification);
        }
        return notifications;

    }


    private void send(Object data, String emitterKey, SseEmitter sseEmitter) {
        try {
            log.info("send to client {}:[{}]", emitterKey, data);
            // 이벤트 데이터 전송
            sseEmitter.send(SseEmitter.event()
                    .id(emitterKey)
                    .data(data, MediaType.APPLICATION_JSON));
        } catch (IOException | IllegalStateException e) {
            log.error("IOException | IllegalStateException is occurred. ", e);
            sseRepository.deleteById(emitterKey);
        }
    }
}
