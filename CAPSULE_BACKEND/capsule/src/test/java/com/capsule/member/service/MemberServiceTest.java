package com.capsule.member.service;

import com.capsule.domain.email.repository.EmailRepository;
import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.model.Role;
import com.capsule.domain.member.repository.MemberRepository;
import com.capsule.domain.member.requestDto.JoinDto;
import com.capsule.domain.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailRepository emailRepository;

    private JoinDto joinDto;

//    @BeforeEach
//    public void createJoinDto(){
//        joinDto = new JoinDto("test@test",passwordEncoder.encode("test","yrdy","test","test","010-1111-1111");
//        joinDto = new JoinDto("test@test",passwordEncoder.encode("test"),"test","test","010-1111-1111");
//    }

//    @Test
//    @DisplayName("유저 회원 가입 테스트")
//    public void 유저_회원_가입_테스트(){
//        //given
//        Member member = Member.createMember(joinDto.getEmail(), joinDto.getPassword(), joinDto.getName(), joinDto.getPassword(), Role.ROLE_USER);
//
//        // when
//        memberRepository.save(member);
//
//        // then
//        Assertions.assertThat(member.getEmail()).isEqualTo("test@test");
//    }




}
