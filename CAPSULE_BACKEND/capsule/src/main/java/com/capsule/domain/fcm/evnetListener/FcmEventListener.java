package com.capsule.domain.fcm.evnetListener;

import com.capsule.domain.capsule.model.Capsule;
import com.capsule.domain.fcm.message.FcmMessage;
import com.capsule.domain.fcm.service.FcmService;
import com.capsule.domain.member.model.Member;
import com.capsule.global.event.Event;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmEventListener {

    private final FcmService fcmService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleFcmEvent(Event event) throws MessagingException {
        Member member = event.getMember();
        Capsule capsule = event.getCapsule();

        if (!member.isState()) return;

        String token = fcmService.getFcmToken(member);
        FcmMessage fcmMessage = FcmMessage.createFcmMessage(capsule,token);
        log.info("fcm {}:"  , member.getEmail());
        fcmService.sendFcm(fcmMessage);


    }
}
