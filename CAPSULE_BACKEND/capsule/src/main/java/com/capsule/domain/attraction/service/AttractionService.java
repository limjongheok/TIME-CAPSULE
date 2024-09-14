package com.capsule.domain.attraction.service;

import com.capsule.domain.attraction.model.Attraction;
import com.capsule.domain.favorite.model.Favorite;
import com.capsule.domain.favorite.repository.FavoriteRepository;
import com.capsule.domain.favorite.requestDto.FavoriteDto;
import com.capsule.domain.member.model.Member;
import com.capsule.global.security.auth.MemberDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final FavoriteRepository favoriteRepository;

    private String makeUrl(int page, int size, String mapX, String mapY) throws UnsupportedEncodingException {

        log.info("MakeUrl mapX : {}", mapX);
        log.info("MakeUrl mapY : {}", mapY);

        return AttractionURL.BASE_URL.getUrl() +
                AttractionURL.API_URL.getUrl() +
                AttractionURL.serviceKey.getUrl() +
                AttractionURL.defaultQueryParam.getUrl() +
                AttractionURL.contentTypeId.getUrl() +
                "&numOfRows=" + size +
                "&pageNo=" + page +
                "&mapX=" + mapX +
                "&mapY=" + mapY +
                AttractionURL.radius.getUrl() +
                AttractionURL.arrange.getUrl() +
                AttractionURL.numOfRows.getUrl();
    }

    public Map<String, List<Attraction>> attractionSortedByFavorite() {
        List<FavoriteDto> favorites = favoriteRepository.sortedFavoriteByCountTripName();

        List<Attraction> attractions = new ArrayList<>();

        for(FavoriteDto favorite: favorites) {
            Attraction attraction = new Attraction();
            attraction.setMapy(favorite.getMapY());
            attraction.setMapx(favorite.getMapX());
            attraction.setTitle(favorite.getTripName());
            attraction.setFirstimage(favorite.getFirstImage());
            attraction.setState(favorite.isState());

            attractions.add(attraction);
        }
        Map<String, List<Attraction>> result = new HashMap<>();
        result.put("data",attractions);

        return result;
    }

    public Map<String, List<Attraction>> attractionSortedByPlace(
            Authentication authentication,
            int page,
            int size,
            String mapX,
            String mapY) throws UnsupportedEncodingException, ParseException {
        log.info("공공데이터 API 여행지 데이터 요청 : {} ", makeUrl(page, size, mapX, mapY));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();

        List<Favorite> favorites = favoriteRepository.findFavoritesByMember(member);
        Map<String, List<Attraction>> result = new HashMap<>();

        RestTemplate restTemplate = createRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> requsetMap = restTemplate.exchange(makeUrl(page, size, mapX, mapY), HttpMethod.GET, entity, Map.class);
        List<Attraction> attractions = new ArrayList<>();

        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(requsetMap.getBody());
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(jsonStr);
            JSONObject response = (JSONObject) object.get("response");
            JSONObject body = (JSONObject) response.get("body");
            // 반활할 값이 없다.
            if(body.get("items") == "") {
                return (Map<String, List<Attraction>>) result.put("data", attractions);
            }
            JSONObject items = (JSONObject) body.get("items");
            JSONArray itemArr = (JSONArray) items.get("item");
            for(int i=0; i<itemArr.size(); i++) {
                object = (JSONObject) itemArr.get(i);
                Attraction attraction = objectMapper.readValue(object.toString(), Attraction.class);
                for(Favorite favorite: favorites) {
                    if(favorite.getTripName().equals(attraction.getTitle())) {
                        attraction.setFavoriteId(favorite.getFavoriteId());
                        attraction.setState(true);
                        log.info("attraction check : {}", attraction);
                        break;
                    }
                }
                attractions.add(attraction);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        result.put("data", attractions);
        return result;
    }

    public Map<String, List<Attraction>> attractionMyFavorite(String email) {
        log.info("여행지 조회 전 이메일 확인 : {}", email);

        List<Favorite> favorites = favoriteRepository.findFavoritesByEmail(email);

        List<Attraction> attractions = new ArrayList<>();

        for(Favorite favorite : favorites) {
            log.info("Favorite 좋아요 순으로 조회 : {}", favorite);
            attractions.add(new Attraction(favorite.getTripName(), favorite.getFirstImage(), favorite.getMapX(), favorite.getMapY(), favorite.isState()));
        }

        Map<String, List<Attraction>> resultMap = new HashMap<>();
        resultMap.put("data", attractions);
        log.info("좋아요 리스트 요청 : {} ", resultMap);
        return resultMap;
    }

    private RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setDefaultCharset(StandardCharsets.UTF_8);
        messageConverters.add(jsonConverter);

        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        messageConverters.add(stringConverter);

        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }
}