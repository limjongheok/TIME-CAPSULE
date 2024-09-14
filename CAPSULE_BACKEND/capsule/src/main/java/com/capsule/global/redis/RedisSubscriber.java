package com.capsule.global.redis;

import com.capsule.domain.notification.Notification;
import com.capsule.domain.notification.NotificationService;
import com.capsule.global.sse.SseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisSubscriber implements MessageListener {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String channel = new String(message.getChannel());
            String body = new String(message.getBody());
            List<Notification> notifications = objectMapper.readValue(body, new TypeReference<List<Notification>>(){});
            notificationService.sendNotificationToClient(channel, notifications);
            log.info("Received message: {} from channel: {}", body, channel);
        } catch (Exception e) {
            log.error("Exception occurred while processing message. ", e);
        }
    }
}
