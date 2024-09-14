package com.capsule.domain.email.eventListener;

import com.capsule.domain.email.service.EmailService;
import com.capsule.domain.member.model.Member;
import com.capsule.domain.capsule.model.Capsule;
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
public class EmailEventListener {

    private final EmailService emailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMailEvent(Event event) throws MessagingException {
        Member member = event.getMember();
        Capsule capsule = event.getCapsule();
        if(member.isState()){
            emailService.sendRoomMessage(member,capsule);
        }

    }
}
