package com.capsule.global.redis;

import com.capsule.domain.notification.Notification;
import com.capsule.global.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RedisMessageService {

    private final RedisMessageListenerContainer container;
    private final RedisSubscriber subscriber; // 따로 구현한 Subscriber
    private final RedisTemplate<String, Object> redisTemplate;

    public void subscribe(String channel) {
        container.addMessageListener(subscriber, ChannelTopic.of(channel));
    }

    public void publish(String channel, List<Notification> notifications) {
        redisTemplate.convertAndSend(channel, notifications);
    }

    public void removeSubscribe(String channel) {
        container.removeMessageListener(subscriber, ChannelTopic.of(channel));
    }

}
