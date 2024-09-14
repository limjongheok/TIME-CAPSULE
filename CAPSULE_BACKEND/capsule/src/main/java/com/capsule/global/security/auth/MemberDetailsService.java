package com.capsule.global.security.auth;

import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.repository.MemberRepository;
import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByEmailAndState(email,true).orElseThrow(() ->
                new ErrorResponse(ExceptionMessage.NOT_EXIST_MEMBERBYEMAIL));

        return new MemberDetails(findMember);
    }
}
