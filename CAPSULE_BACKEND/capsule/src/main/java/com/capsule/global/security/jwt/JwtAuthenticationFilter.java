package com.capsule.global.security.jwt;

import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.message.SuccessMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;


@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        log.info("accessToken : {}",accessToken);
        response.addHeader("Authorization","Bearer "+accessToken);
        response.getWriter().write(SuccessMessage.SUCCESS_LOGIN_MEMBER.getMessage());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            Member loginMember = objectMapper.readValue(request.getInputStream(), Member.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginMember.getEmail(),loginMember.getPassword());
            return getAuthenticationManager().authenticate(authenticationToken);
        }catch (IOException e){
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
}
