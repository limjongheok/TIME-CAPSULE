package com.capsule.global.schedule;

import com.capsule.domain.capsule.eventListener.CapsuleEvent;
import com.capsule.domain.capsule.model.Capsule;
import com.capsule.domain.capsule.repository.CapsuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckCapsule {

    private final CapsuleRepository capsuleRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Scheduled(cron = "0 * * * * *")
    public void checkEndTime(){
        List<Capsule> capsules = capsuleRepository.findByMinuteEqualsToNowEndTime();

        if(capsules.size() !=0){
            log.info("roomSize : {} ",capsules.size());
            eventPublisher.publishEvent(new CapsuleEvent(capsules));
        }
    }
}
