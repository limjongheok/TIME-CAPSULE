package com.capsule.global.security.jwt;

import com.capsule.domain.member.model.Member;
import com.capsule.global.config.ValueConfig;
import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import com.capsule.global.security.auth.MemberDetails;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final ValueConfig valueConfig;

    public String generateAccessToken(Authentication authentication){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        return Jwts.builder()
                .setSubject(member.getEmail())
                .claim("id",member.getEmail())
                .setExpiration(tokenExpiresIn(Long.parseLong(valueConfig.getAccessTokenExpire())))
                .signWith(valueConfig.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, HttpServletRequest request){
        try{
            Jwts.parserBuilder().setSigningKey(valueConfig.getKey()).build().parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e){
            request.setAttribute("exception",ExceptionMessage.EXPIREDJWTEXCEPTION);
        }
        catch (JwtException | IllegalArgumentException exception){
            request.setAttribute("exception",ExceptionMessage.NOTVALIDJWTEXCEPTION);
        }
        return false;
    }

    public String getPayload(String token , HttpServletRequest request){
        try{
            return Jwts.parser()
                    .setSigningKey(valueConfig.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch (ExpiredJwtException e){
            request.setAttribute("exception",ExceptionMessage.EXPIREDJWTEXCEPTION);
        }catch (JwtException e){
            request.setAttribute("exception",ExceptionMessage.NOTVALIDJWTEXCEPTION);
        }
        return null;
    }

    private Date tokenExpiresIn(long expires){
        long now = (new Date()).getTime();
        Date dateExpiresIn = new Date(now + expires);
        return dateExpiresIn;
    }
}
