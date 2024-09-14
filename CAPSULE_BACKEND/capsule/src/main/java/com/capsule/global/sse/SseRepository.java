package com.capsule.global.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
public class SseRepository {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(String eventId, SseEmitter sseEmitter) {
        emitters.put(eventId, sseEmitter);
        return sseEmitter;
    }

    public Optional<SseEmitter> findById(String memberId) {
        return Optional.ofNullable(emitters.get(memberId));
    }

    public Map<String, SseEmitter> findAllEmitters(){
        return emitters;
    }
    public void deleteById(String eventId) {
        emitters.remove(eventId);
    }
}
