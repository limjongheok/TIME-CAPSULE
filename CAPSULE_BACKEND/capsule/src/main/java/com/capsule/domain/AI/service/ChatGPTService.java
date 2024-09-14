package com.capsule.domain.AI.service;

import org.springframework.stereotype.Service;

@Service
public interface ChatGPTService {

    public boolean isProfanity(String text);

    public String splitNoun(String text);
}