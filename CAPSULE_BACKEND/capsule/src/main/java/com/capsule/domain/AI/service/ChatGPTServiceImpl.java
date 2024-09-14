package com.capsule.domain.AI.service;

import com.capsule.domain.AI.config.ChatGPTConfig;
import com.capsule.domain.AI.model.CompletionRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vane.badwordfiltering.BadWordFiltering;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ChatGPTServiceImpl implements ChatGPTService {

    private final ChatGPTConfig chatGPTConfig;
    private final ObjectMapper objectMapper;

    public ChatGPTServiceImpl(ChatGPTConfig chatGPTConfig) {
        this.chatGPTConfig = chatGPTConfig;
        this.objectMapper = new ObjectMapper();
    }

    @Value("${openai.model}")
    private String model;

    @Override
    public boolean isProfanity(String text) {
        log.debug("[+] 욕설 판별을 수행합니다. 입력 텍스트: {}", text);

        BadWordFiltering badWordFiltering = new BadWordFiltering();
        log.info("욕설 확인 : {}, {} ",badWordFiltering.blankCheck(text) , badWordFiltering.blankCheck(text));
        return !badWordFiltering.blankCheck(text) && !badWordFiltering.blankCheck(text);
    }

    @Override
    public String splitNoun(String text) {
        log.info("[+] 명사 나누기 수행합니다. 입력 텍스트: {}", text);

        StringBuilder result = new StringBuilder();

        String[] people = text.split("_,_");

        StringBuilder chatString = new StringBuilder();
        for (int i=0; i<people.length; i++) {
            if(i%2 == 1) {
                chatString.append(people[i]);
            }
            else {
                result.append("(").append(people[i]).append(")");
            }
        }
        result.append(": ");

        // ChatGPT API 요청을 위한 데이터 구성
        CompletionRequestDto.Message message = CompletionRequestDto.Message.builder()
                .role("user")
                .content("뒤의 말에서 모든 명사들을 추출해서 ','로 나눠서 출력해 : " + chatString.toString())
                .build();

        CompletionRequestDto requestDto = CompletionRequestDto.builder()
                .model(model)
                .messages(Collections.singletonList(message))
                .temperature(0.0f)
                .build();

        // HTTP 헤더 설정
        HttpHeaders headers = chatGPTConfig.httpHeaders();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(requestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize request", e);
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
            // ChatGPT API 호출
            ResponseEntity<String> response = chatGPTConfig.restTemplate()
                    .exchange(
                            "https://api.openai.com/v1/chat/completions",
                            HttpMethod.POST,
                            requestEntity,
                            String.class
                    );

            // 응답 데이터 처리
            Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");

            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> messageMap = (Map<String, Object>) choices.get(0).get("message");
                String completion = messageMap.get("content").toString().trim();
                log.info("chatGpt answer : {}",completion);

                String[] nouns = completion.split(",");

                for(String noun: nouns) {
                    result.append(noun).append(",");
                }

                return result.toString();
            } else {
                throw new RuntimeException("No choices found in the response");
            }
        } catch (HttpClientErrorException | JsonProcessingException e) {
            log.error("API 호출 중 오류 발생", e);
            return null;
        }
    }
}
