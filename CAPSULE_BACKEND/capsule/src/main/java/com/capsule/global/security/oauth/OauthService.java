package com.capsule.global.security.oauth;

import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.model.Role;
import com.capsule.domain.member.repository.MemberRepository;
import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import com.capsule.global.security.auth.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OauthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final DefaultOAuth2UserService delegate;
    private final OauthAttributeService oauthAttributeService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String id = oauthAttributeService.getRegistrationId(userRequest);
        OauthAttribute oauthAttribute = oauthAttributeService.getOauthAttribute(oAuth2User,id);
        Member member = !memberRepository.existsByEmailAndState(oauthAttribute.getEmail(),true) ? createMember(oauthAttribute) : findMember(oauthAttribute);
        return new MemberDetails(member,oAuth2User.getAttributes());
    }

    private Member createMember(OauthAttribute oauthAttribute){
        if(memberRepository.existsByEmailAndState(oauthAttribute.getEmail(),false)) throw new OAuth2AuthenticationException(ExceptionMessage.EXIST_MAIL.getErrorCode());
        Member member = Member.createMember(oauthAttribute.getEmail(),oauthAttribute.getName(),oauthAttribute.getPhoneNumber(), Role.ROLE_USER);

        memberRepository.save(member);
        return member;
    }

    private Member findMember(OauthAttribute oauthAttribute){
        return memberRepository.findByEmailAndState(oauthAttribute.getEmail(),true).orElseThrow(()-> new ErrorResponse(ExceptionMessage.NOT_EXIST_MEMBERBYEMAIL));
    }
}
