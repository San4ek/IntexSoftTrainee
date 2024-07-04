package me.inqu1sitor.authservice.configs;

import me.inqu1sitor.authservice.entities.Account;
import me.inqu1sitor.authservice.utils.LoggedAccountHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@Configuration
public class LoggedAccountHolderConfig {

    @Bean
    @RequestScope
    public Jwt jwt() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Bean
    @RequestScope
    public LoggedAccountHolder userInfoHolder(Jwt jwt) {
        LoggedAccountHolder loggedAccountHolder =new LoggedAccountHolder();
        loggedAccountHolder.setId(UUID.fromString(jwt.getSubject()));
        loggedAccountHolder.setRole(Account.Role.valueOf(jwt.getClaimAsStringList("role").get(0)));

        return loggedAccountHolder;
    }
}
