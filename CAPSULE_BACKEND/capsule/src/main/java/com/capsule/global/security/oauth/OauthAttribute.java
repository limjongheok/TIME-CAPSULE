package com.capsule.global.security.oauth;

import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

@Getter
@Slf4j
public class OauthAttribute {

    private String email;
    private String name;
    private String phoneNumber;
    private Map<String, Object> attributes;

    private OauthAttribute(String email, String name, String phoneNumber, Map<String, Object> attributes){
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.attributes = attributes;
    }

    public static OauthAttribute createOauthAttribute(Map<String, Object> attributes , String registrationId){
        if(registrationId.equals("kakao")) return makeKakao(attributes);
        if(registrationId.equals("naver")) return makeNaver(attributes);
        if(registrationId.equals("google")) return makeGoogle(attributes);
        throw new ErrorResponse(ExceptionMessage.NOT_EXIST_REGISTRATIONID);
    }

    private static OauthAttribute makeKakao(Map<String, Object> attributes){
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String,Object> profile = (Map<String,Object>) kakaoAccount.get("profile");

        log.info((String)kakaoAccount.get("email"));
        log.info((String)profile.get("nickname"));

        return new OauthAttribute((String)kakaoAccount.get("email"),(String)profile.get("nickname"),"010-1111-1111",kakaoAccount);
    }

    private static OauthAttribute makeNaver(Map<String, Object> attributes){
        Map<String, Object> naverAccount = (Map<String, Object>) attributes.get("response");
        log.info((String)naverAccount.get("email"));
        log.info((String)naverAccount.get("name"));
        //log.info((String)naverAccount.get("mobile"));
        return new OauthAttribute((String)naverAccount.get("email"),(String)naverAccount.get("name"),"010-1111-1111",naverAccount);
    }

    private static OauthAttribute makeGoogle(Map<String,Object> attributes){
        String email =  (String) attributes.get(("email"));
       String name =  (String) attributes.get(("name"));
        return new OauthAttribute(email,name,"010-1111-1111",attributes);
    }
// 카카오 권한 허락시 폰 번호 받기
//    private static String getPhoneNumber(Map<String, Object> kakaoAccount){
//        String phoneNumber = (String)kakaoAccount.get("phone_number");
//        phoneNumber = phoneNumber.substring(4,phoneNumber.length());
//        return 0+phoneNumber;
//    }
}
