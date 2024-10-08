package me.inqu1sitor.authservice.configs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * {@link Configuration} for OAuth 2.0 Resource Server support.
 *
 * @author Alexander Sankevich
 */
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        name = "bearerAuth")
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class ResourceServerConfig {

    private final JwtAuthorizationManager jwtAuthorizationManager;
    private final AdminJwtAuthorizationManager adminJwtAuthorizationManager;

    @Bean
    @Order(0)
    public SecurityFilterChain restApiSecurityFilterChain(HttpSecurity http, final JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
        RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/**");
        return http.
                securityMatcher(requestMatcher).
                csrf(csrf -> csrf.ignoringRequestMatchers(requestMatcher)).
                authorizeHttpRequests(authorize ->
                        authorize.
                                requestMatchers(HttpMethod.POST, "/api/accounts/user").permitAll().
                                requestMatchers(HttpMethod.PUT, "/api/accounts").access(jwtAuthorizationManager).
                                requestMatchers(HttpMethod.POST, "/api/accounts/moder", "/api/accounts/admin").access(adminJwtAuthorizationManager).
                                requestMatchers("/api/accounts/block", "/api/accounts/unblock").hasAuthority("ROLE_ADMIN").
                                anyRequest().authenticated()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter))).
                build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("role");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}

@Primary
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

@Component
class AdminJwtAuthorizationManager extends JwtAuthorizationManager {

    public AdminJwtAuthorizationManager(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        final AuthorizationDecision jwtAuthorizationManagerDecision = super.check(authentication, object);
        return new AuthorizationDecision(jwtAuthorizationManagerDecision != null &&
                jwtAuthorizationManagerDecision.isGranted() &&
                authentication.get().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
