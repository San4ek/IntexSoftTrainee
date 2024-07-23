package me.inqu1sitor.authservice.configs;

import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.utils.LoggedAccountDetailsHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

/**
 * {@link Configuration} for {@link LoggedAccountDetailsHolder} support.
 *
 * @author Alexander Sankevich
 */
@Configuration
public class LoggedAccountDetailsProviderConfig {

    @Bean
    @RequestScope
    public Jwt jwt() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Bean
    @RequestScope
    public LoggedAccountDetailsHolder loggedAccountHolder(Jwt jwt) {
        LoggedAccountDetailsHolder loggedAccountDetailsHolder = new LoggedAccountDetailsHolder();
        loggedAccountDetailsHolder.setAccountId(UUID.fromString(jwt.getSubject()));
        loggedAccountDetailsHolder.setAccountRole(AccountEntity.Role.valueOf(jwt.getClaimAsStringList("role").get(0)));
        loggedAccountDetailsHolder.setTokenValue(jwt.getTokenValue());
        return loggedAccountDetailsHolder;
    }
}
