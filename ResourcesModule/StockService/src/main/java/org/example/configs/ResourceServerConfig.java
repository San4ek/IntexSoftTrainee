package org.example.configs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

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
                        .requestMatchers(HttpMethod.GET, "/api/brands/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/brands").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.PUT, "/api/brands/*").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.DELETE, "/api/brands/*").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.GET, "/api/products/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/products").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.PUT, "/api/products/*").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.DELETE, "/api/products/*").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.GET, "/api/stock/*").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.POST, "/api/stock/*").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.PUT, "/api/stock/*").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.PUT, "/api/stock/*/utilization").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.DELETE, "/api/stock/*").hasAuthority("ROLE_MODER")
                        .requestMatchers(HttpMethod.GET, "/api/user-stock/name/*").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/user-stock/attributes").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/user-stock/*").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/user-stock/*").authenticated()
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
