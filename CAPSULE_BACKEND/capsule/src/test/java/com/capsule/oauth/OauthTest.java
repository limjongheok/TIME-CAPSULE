package com.capsule.oauth;

import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.model.Role;
import com.capsule.domain.member.repository.MemberRepository;
import com.capsule.global.security.auth.MemberDetails;
import com.capsule.global.security.oauth.OauthAttribute;
import com.capsule.global.security.oauth.OauthAttributeService;
import com.capsule.global.security.oauth.OauthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

//@ExtendWith(MockitoExtension.class)
//public class OauthTest {
//
//    @InjectMocks
//    private OauthService oauthService;
//
//    @Mock
//    private MemberRepository memberRepository;
//    @Mock
//    private DefaultOAuth2UserService delegate;
//    @Mock
//    private OauthAttributeService oauthAttributeService;
//    @Mock
//    private OAuth2UserRequest request;
//    @Mock
//    private OAuth2User oAuth2User;
//
//    private Member member;
//    private Map<String, Object> kakaoAccount;
//    private OauthAttribute attribute;
//
//    @BeforeEach
//    public void createMember(){
//        member = Member.createMember("test","test","test","010-1111-1111", Role.ROLE_USER);
//
//        kakaoAccount = new HashMap<>();
//        Map<String, Object> data = new HashMap<>();
//        data.put("email","test");
//        data.put("name","test");
//        data.put("phone_number","010-1111-1111");
//        kakaoAccount.put("kakao_account",data);
//
//        attribute = OauthAttribute.createOauthAttribute(kakaoAccount,"kakao");
//    }
//
//    @Test
//    @DisplayName("로그인 테스트")
//    public void 로그인_테스트(){
//        //given
//        given(delegate.loadUser(request)).willReturn(oAuth2User);
//        given(oauthAttributeService.getRegistrationId(request)).willReturn("kakao");
//        given(oauthAttributeService.getOauthAttribute(oAuth2User,"kakao")).willReturn(attribute);
//        given(memberRepository.existsByEmail(any())).willReturn(false);
//        given(memberRepository.save(any())).willReturn(member);
//
//        //when
//        OAuth2User result = oauthService.loadUser(request);
//
//        //then
//        Assertions.assertThat(result).isInstanceOf(MemberDetails.class);
//    }
//
//    @Test
//    @DisplayName("로그인_유저_존재 테스트")
//    public void 로그인_유저_존재(){
//        given(delegate.loadUser(request)).willReturn(oAuth2User);
//        given(oauthAttributeService.getRegistrationId(request)).willReturn("kakao");
//        given(oauthAttributeService.getOauthAttribute(oAuth2User,"kakao")).willReturn(attribute);
//        given(memberRepository.existsByEmail(any())).willReturn(true);
//        given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));
//
//        //when
//        OAuth2User result = oauthService.loadUser(request);
//
//        //then
//        Assertions.assertThat(result).isInstanceOf(MemberDetails.class);
//    }
//}