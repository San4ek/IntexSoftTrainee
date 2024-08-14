package org.example.configs;

import org.example.dtos.LoggedAccountDetailsProvider;
import org.example.enums.RoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@Configuration
public class LoggedAccountProviderConfig {

    @Bean
    @RequestScope
    public Jwt jwt() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Bean
    @RequestScope
    public LoggedAccountDetailsProvider userInfoHolder(Jwt jwt) {
        LoggedAccountDetailsProvider loggedAccountDetailsProvider = new LoggedAccountDetailsProvider();
        loggedAccountDetailsProvider.setAccountId(UUID.fromString(jwt.getSubject()));
        loggedAccountDetailsProvider.setAccountRole(RoleEnum.valueOf(jwt.getClaimAsStringList("role").get(0)));
        return loggedAccountDetailsProvider;
    }
}
