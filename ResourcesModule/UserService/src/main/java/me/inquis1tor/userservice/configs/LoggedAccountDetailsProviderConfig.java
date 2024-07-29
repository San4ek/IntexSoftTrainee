package me.inquis1tor.userservice.configs;

import me.inquis1tor.userservice.entities.AccountRole;
import me.inquis1tor.userservice.utils.LoggedAccountDetailsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

/**
 * {@link Configuration} for {@link LoggedAccountDetailsProvider} support.
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
    public LoggedAccountDetailsProvider userInfoHolder(Jwt jwt) {
        LoggedAccountDetailsProvider loggedAccountDetailsProvider = new LoggedAccountDetailsProvider();
        loggedAccountDetailsProvider.setAccountId(UUID.fromString(jwt.getSubject()));
        loggedAccountDetailsProvider.setAccountRole(AccountRole.valueOf(jwt.getClaimAsStringList("role").get(0)));

        return loggedAccountDetailsProvider;
    }
}
