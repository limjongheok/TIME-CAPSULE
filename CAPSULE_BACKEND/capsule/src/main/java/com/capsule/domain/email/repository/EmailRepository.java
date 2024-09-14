package com.capsule.domain.email.repository;

import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailRepository {

    private final RedisTemplate redisTemplate;

    public void saveAuth(String key, Object value){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value, Duration.ofMinutes(5));
    }

    public boolean checkAuth(String key, String authCode){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String value = (String)valueOperations.get(key);
        if(!value.equals(authCode)){
           return false;
        }
        return  true;
    }

    public void successAuth(String key){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key + "Auth","Success", Duration.ofMinutes(5));
    }

    public boolean existsBySuccessEmail(String key){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String value = (String)valueOperations.get(key+"Auth");
        if(value == null){
            return false;
        }
        return true;
    }
}
