package com.capsule.domain.notification;

import com.capsule.global.sse.SseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final SseRepository sseRepository;

    public void sendNotificationToClient(String id, List<Notification> notifications) {
        sseRepository.findById(id)
                .ifPresent(emitter -> send(notifications, id, emitter));
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
