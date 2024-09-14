package com.capsule.global.config;

import com.capsule.global.security.jwt.JwtAuthenticationFilter;
import com.capsule.global.security.jwt.JwtAuthorizationFilter;
import com.capsule.global.security.jwt.JwtTokenProvider;
import com.capsule.global.security.oauth.OauthFailHandler;
import com.capsule.global.security.oauth.OauthService;
import com.capsule.global.security.oauth.OauthSuccessHandler;
import com.capsule.global.securityExceptionEntryPoint.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OauthService oauthService;
    private final OauthSuccessHandler oauthSuccessHandler;
    private final OauthFailHandler oauthFailHandler;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtTokenProvider);
        filter.setFilterProcessesUrl("/api/login");
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf((csrf) -> csrf.disable());
        http.cors(Customizer.withDefaults());
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.formLogin((formLogin) -> formLogin.disable());
        http.httpBasic((httpBasic) -> httpBasic.disable());
        http.authorizeHttpRequests((authorizeHttpRquests) ->authorizeHttpRquests
                .requestMatchers("/api/user/join").permitAll()
                .requestMatchers("/api/user/**").hasRole("USER")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll());
        http.exceptionHandling((exceptionConfig) ->
                exceptionConfig.authenticationEntryPoint(customAuthenticationEntryPoint));
        http.oauth2Login((oauth) ->
                oauth.userInfoEndpoint(c -> c.userService(oauthService))
                        .successHandler(oauthSuccessHandler)
                        .failureHandler(oauthFailHandler)
                        .redirectionEndpoint((redirectionEndpointConfig )-> redirectionEndpointConfig.baseUri(("/api/login/oauth2/code/*")))
                        .authorizationEndpoint((authorizationEndpointConfig) ->
                                authorizationEndpointConfig.baseUri("/api/oauth2/authorization"))
            );
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
