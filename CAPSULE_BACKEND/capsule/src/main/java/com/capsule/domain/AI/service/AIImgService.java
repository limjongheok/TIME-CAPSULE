package com.capsule.domain.AI.service;

import com.capsule.domain.AI.AIImgRepository.AIImgRepository;
import com.capsule.domain.AI.config.ChatGPTConfig;
import com.capsule.domain.AI.message.responseDto.OpenAIResponseDto;
import com.capsule.domain.AI.model.AIImg;
import com.capsule.domain.AI.requestDto.CreateAIImgRequestDto;
import com.capsule.domain.AI.requestDto.OpenAIRequestDto;
import com.capsule.domain.capsule.model.Capsule;
import com.capsule.domain.capsule.repository.CapsuleRepository;
import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIImgService {

    private final ChatGPTConfig chatGPTConfig;
    private final AIImgRepository aiImgRepository;
    private final CapsuleRepository capsuleRepository;

    public void saveAIImg(CreateAIImgRequestDto requestDto){
        Capsule capsule = capsuleRepository.findById(requestDto.getCapsuleId()).orElseThrow(() ->{
            throw new ErrorResponse(ExceptionMessage.NOT_FOUND_ROOM);
        });

        if(aiImgRepository.existsByCapsule(capsule)) throw new ErrorResponse(ExceptionMessage.EXIST_AIIMG_REQUEST);

        String text = requestDto.getText();
        String prompt = "아래 글을 보고 (이름)수 만큼 이미지에 다른 사람들이 나왔으면 좋겠어.no text and letters in the image. " +
                "여행을 다녀온것을 하나의 그림으로 보여주는 그림. 사람들은 무조건 옷을 입고 있어야해. 그림풍은 귀여운 그림체. " +
                "나머지 : 뒤의 단어들로 이미지를 만들어줘\n" + text;

        OpenAIRequestDto openAIRequestDto = new OpenAIRequestDto("dall-e-3", prompt, 1, "1024x1024");

        HttpHeaders headers = chatGPTConfig.httpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<OpenAIRequestDto> entity = new HttpEntity<>(openAIRequestDto, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<OpenAIResponseDto> responseEntity = restTemplate.exchange(
                "https://api.openai.com/v1/images/generations",
                HttpMethod.POST,
                entity,
                OpenAIResponseDto.class
        );

        OpenAIResponseDto response = responseEntity.getBody();
        String imageUrl = response.getData().get(0).getUrl();

        AIImg aiImg = new AIImg(capsule, imageUrl);
        aiImgRepository.save(aiImg);
    }

    public String getAIImg(Long capsuleId){
        AIImg aiImg = aiImgRepository.findByCapsuleId(capsuleId);
        return aiImg.getImgUrl();
    }
}
