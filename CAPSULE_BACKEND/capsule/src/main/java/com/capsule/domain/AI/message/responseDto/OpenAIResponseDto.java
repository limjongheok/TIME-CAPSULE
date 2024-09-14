package com.capsule.domain.AI.message.responseDto;

import java.util.List;

public class OpenAIResponseDto {
    private long created;
    private List<Data> data;

    public static class Data {
        private String revised_prompt;
        private String url;

        // Getters and Setters
        public String getRevised_prompt() {
            return revised_prompt;
        }

        public void setRevised_prompt(String revised_prompt) {
            this.revised_prompt = revised_prompt;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    // Getters and Setters
    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}