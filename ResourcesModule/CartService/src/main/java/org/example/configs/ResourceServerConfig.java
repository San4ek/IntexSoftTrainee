package org.example.configs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@EnableWebSecurity
@EnableMethodSecurity
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        name = "bearerAuth")
@Configuration(proxyBeanMethods = false)
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, final JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
        RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/**");
        return http.securityMatcher(requestMatcher).
                csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/addresses").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/addresses").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.PUT, "/api/addresses/*").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.DELETE, "/api/addresses/*").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.GET, "/api/carts/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/carts").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/carts/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/carts/item").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/carts/item").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/carts/items/*").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.GET, "/api/orders/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/orders").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/orders/address/*").hasAuthority("ROLE_MODER")
                        .anyRequest().authenticated()).
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
