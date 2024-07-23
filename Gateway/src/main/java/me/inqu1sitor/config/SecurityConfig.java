package me.inqu1sitor.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        name = "bearerAuth")
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthorizationManager jwtAuthorizationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/**");

        return http.securityMatcher(requestMatcher).
                csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(authorize -> authorize.
                        anyRequest().access(jwtAuthorizationManager)).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                build();
    }
}

@Component
@RequiredArgsConstructor
class JwtAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        String authStr = object.getRequest().getHeader("Authorization");
        return new AuthorizationDecision(authStr != null &&
                Boolean.TRUE.equals(redisTemplate.hasKey("access_for_id:" + authStr.replace("Bearer ", ""))));
    }
}
