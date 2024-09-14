package com.capsule.auth;

import com.capsule.domain.member.model.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockSecurityContext.class)
public @interface WithMockAuthMember {
    String email();
    Role roles();
}
