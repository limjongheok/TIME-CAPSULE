package com.capsule.domain.AI.requestDto;

public class OpenAIRequestDto {
    private String model;
    private String prompt;
    private int n;
    private String size;

    public OpenAIRequestDto(String model, String prompt, int n, String size) {
        this.model = model;
        this.prompt = prompt;
        this.n = n;
        this.size = size;
    }

    // Getters and Setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}