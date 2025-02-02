package com.capsule.global.security.auth;

import com.capsule.domain.member.model.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 인증 객체 만들기
 * **/
public class MemberDetails implements OAuth2User, UserDetails {

    @Getter
    private Member member;
    private  Map<String, Object> attributes;

    public MemberDetails(Member member, Map<String, Object> attributes){
        this.member = member;
        this.attributes = attributes;
    }

    public MemberDetails(Member member){
        this.member = member;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        member.getMemberRoles().forEach((r)->{
            authorities.add(()->r.getRole());
        });
        return authorities;
    }

    //일반 로그인
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return this.member.getName();
    }
}