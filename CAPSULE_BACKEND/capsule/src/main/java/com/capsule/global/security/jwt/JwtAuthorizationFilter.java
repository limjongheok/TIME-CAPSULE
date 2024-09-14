package com.capsule.global.security.jwt;

import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.repository.MemberRepository;
import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import com.capsule.global.security.auth.MemberDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = resolveToken(request);
        if(token!= null && memberRepository.existsByEmailAndState(getTokenPayLoad(token,request),true)){
            String memberEmail = getTokenPayLoad(token,request);
            Member member = memberRepository.findByEmailAndState(memberEmail,true).orElseThrow(() -> new ErrorResponse(ExceptionMessage.NOT_EXIST_MEMBERBYEMAIL));
            generatedAuthentication(member);
        }
        chain.doFilter(request,response);

    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.info("bearerToken : {}" ,bearerToken);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getTokenPayLoad(String token, HttpServletRequest request){
        if(jwtTokenProvider.validateToken(token,request)){
            return jwtTokenProvider.getPayload(token,request);
        }
        return null;
    }

    private void generatedAuthentication(Member member){
        MemberDetails userPrincipalDetails = new MemberDetails(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipalDetails,null, userPrincipalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
