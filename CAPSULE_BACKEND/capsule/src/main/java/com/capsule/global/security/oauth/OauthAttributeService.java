package com.capsule.global.security.oauth;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class OauthAttributeService {

    public String getRegistrationId(OAuth2UserRequest userRequest){
        return userRequest.getClientRegistration().getRegistrationId();
    }

    public OauthAttribute getOauthAttribute(OAuth2User oAuth2User , String registrationId){
        return  OauthAttribute.createOauthAttribute(oAuth2User.getAttributes(),registrationId);
    }
}
