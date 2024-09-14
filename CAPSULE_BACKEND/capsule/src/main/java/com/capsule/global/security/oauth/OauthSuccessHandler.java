package com.capsule.global.security.oauth;

import com.capsule.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OauthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = jwtTokenProvider.generateAccessToken(authentication);
        log.info( "generateToken : {}" , token);
        response.sendRedirect(makeRedirectUrl(token));
    }

    private String makeRedirectUrl(String token){
        return UriComponentsBuilder.fromUriString("https://capsuletalk.site/oauth/" + token)
                .build()
                .toString();
    }
}
