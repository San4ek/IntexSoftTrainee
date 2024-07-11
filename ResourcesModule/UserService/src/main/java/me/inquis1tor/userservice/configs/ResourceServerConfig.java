package me.inquis1tor.userservice.configs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link Configuration} for OAuth 2.0 Resource Server support.
 *
 * @author Alexander Sankevich
 */
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
    public SecurityFilterChain filterChain(HttpSecurity http, JwtRoleConverter jwtRoleConverter) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtRoleConverter);

        RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/**");

        return http.securityMatcher(requestMatcher).
                csrf(csfr -> csfr.ignoringRequestMatchers(requestMatcher)).
                authorizeHttpRequests(authorize -> authorize.
                        requestMatchers(HttpMethod.POST, "/api/accounts").permitAll().
                        requestMatchers(HttpMethod.PUT, "/api/accounts/block", "api/accounts/unblock", "api/accounts/credentials").permitAll().
                        anyRequest().authenticated()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter))).
                build();
    }
}

@Component
class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        List<String> roles = source.getClaimAsStringList("role");
        if (roles == null || roles.isEmpty()) {
            return new ArrayList<>();
        }
        return roles.stream().map(arg -> new SimpleGrantedAuthority("ROLE_" + arg)).collect(Collectors.toUnmodifiableList());
    }
}
