package me.inquis1tor.userservice.configs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.utils.PrivatePropertiesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
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

    private final AuthCodeAuthorizationManager authCodeAuthorizationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, final JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
        RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/**");
        return http.securityMatcher(requestMatcher).
                csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(authorize -> authorize.
                        requestMatchers(HttpMethod.POST, "/api/accounts").access(authCodeAuthorizationManager).
                        requestMatchers(HttpMethod.PUT, "/api/accounts/block", "/api/accounts/unblock", "/api/accounts/credentials").access(authCodeAuthorizationManager).
                        requestMatchers(HttpMethod.GET, "/api/accounts/all").hasAuthority("ROLE_ADMIN").
                        anyRequest().authenticated()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                oauth2ResourceServer(oauth2 -> oauth2.
                        jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)).
                        accessDeniedHandler((request, response, accessDeniedException) -> response.setStatus(HttpServletResponse.SC_FORBIDDEN))).
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

@Component
@RequiredArgsConstructor
class AuthCodeAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final PrivatePropertiesProvider privatePropertiesProvider;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        String authCode = object.getRequest().getHeader("Auth-Code");
        return new AuthorizationDecision(authCode != null && authCode.equals(privatePropertiesProvider.getAuthCode()));
    }
}
