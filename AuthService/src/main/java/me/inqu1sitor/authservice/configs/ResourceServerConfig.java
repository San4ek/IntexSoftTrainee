package me.inqu1sitor.authservice.configs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


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

    @Bean
    @Order(0)
    SecurityFilterChain restApiSecurityFilterChain(HttpSecurity http, JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
        RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/**");
        return http.
                securityMatcher(requestMatcher).
                csrf(csrf -> csrf.ignoringRequestMatchers(requestMatcher)).
                authorizeHttpRequests(authorize ->
                        authorize.
                                requestMatchers(HttpMethod.POST, "api/accounts/user").permitAll().
                                requestMatchers("/api/accounts/moder", "/api/accounts/admin", "/api/accounts/block", "/api/accounts/unblock").hasAuthority("ROLE_ADMIN").
                                anyRequest().authenticated()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter))).
                build();
    }

    @Bean
    public static JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("role");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
