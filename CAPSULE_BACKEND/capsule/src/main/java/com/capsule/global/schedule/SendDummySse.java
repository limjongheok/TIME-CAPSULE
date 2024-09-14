package com.capsule.global.schedule;

import com.capsule.global.sse.SseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendDummySse {

    private final SseRepository sseRepository;
    @Async
    @Scheduled(cron = "0/40 * * * * *")
    public void sendDummySse(){
        Map<String, SseEmitter> emitters = sseRepository.findAllEmitters();
        if(emitters.size() !=0){
            for(Map.Entry<String, SseEmitter> entry : emitters.entrySet()){
                String key = entry.getKey();
                SseEmitter emitter =entry.getValue();
                try {

                    log.info("send to client {}:[{}]", key, "dummy");
                    // 이벤트 데이터 전송
                    emitter.send(SseEmitter.event()
                            .id(key)
                            .data("dummy", MediaType.APPLICATION_JSON));
                } catch (IOException | IllegalStateException e) {
                    log.error("IOException | IllegalStateException is occurred. ", e.getMessage());
                    sseRepository.deleteById(key);
                }
            }
        }


    }
}
