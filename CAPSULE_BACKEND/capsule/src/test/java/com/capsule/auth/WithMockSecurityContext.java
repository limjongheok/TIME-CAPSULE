package com.capsule.auth;

import com.capsule.domain.member.model.Member;
import com.capsule.global.security.auth.MemberDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockSecurityContext implements WithSecurityContextFactory<WithMockAuthMember> {

    @Override
    public SecurityContext createSecurityContext(WithMockAuthMember mockMember) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Member member = Member.createMember(mockMember.email(), "test","test","010-1111-1111",mockMember.roles());

        MemberDetails memberDetails = new MemberDetails(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetails,"test",memberDetails.getAuthorities());
        context.setAuthentication(authentication);
        return null;
    }
}
