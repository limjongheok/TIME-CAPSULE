package com.capsule.domain.AI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompletionRequestDto {
    private String model;
    private List<Message> messages;
    private float temperature;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Message {
        private String role;
        private String content;
    }
}
