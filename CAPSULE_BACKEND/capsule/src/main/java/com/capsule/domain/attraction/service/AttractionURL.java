package com.capsule.domain.attraction.service;

import lombok.Getter;

@Getter
public enum AttractionURL {
    BASE_URL("http://apis.data.go.kr/B551011/KorService1"),
    API_URL("/locationBasedList1"),
    serviceKey("?serviceKey=na3iwesVOVvtIRoukeMOJ5UYjS7OI90SVAayaiHrLII1ZRnN8tnNS5cQlGpCEwdDUYK4dkwtquo8jlHaesj50w=="),
    defaultQueryParam("&MobileOS=ETC&MobileApp=AppTest&_type=json"),
    numOfRows("&numOfRows=100"),
    contentTypeId("&contentTypeId=12"),
    radius("&radius=10000"),
    pageNo("&pageNo=1"),
    arrange("&arrange=O");

    String url;

    AttractionURL(String url) {
        this.url = url;
    }
}
