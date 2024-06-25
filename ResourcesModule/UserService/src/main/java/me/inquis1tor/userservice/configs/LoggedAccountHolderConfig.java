package me.inquis1tor.userservice.configs;

import me.inquis1tor.userservice.entities.Account;
import me.inquis1tor.userservice.utils.LoggedAccountHolder;
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
