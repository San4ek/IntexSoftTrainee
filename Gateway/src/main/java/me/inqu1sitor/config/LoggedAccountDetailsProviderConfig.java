package me.inqu1sitor.config;

import jakarta.servlet.http.HttpServletRequest;
import me.inqu1sitor.utils.LoggedAccountDetailsHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

/**
 * {@link Configuration} for {@link LoggedAccountDetailsHolder} support.
 *
 * @author Alexander Sankevich
 */
@Configuration
public class LoggedAccountDetailsProviderConfig {

    @Bean
    @RequestScope
    public LoggedAccountDetailsHolder loggedAccountHolder(final HttpServletRequest httpServletRequest) {
        LoggedAccountDetailsHolder loggedAccountDetailsHolder = new LoggedAccountDetailsHolder();
        String authStr = httpServletRequest.getHeader("Authorization");
        if (authStr != null) {
            loggedAccountDetailsHolder.setTokenValue(authStr.replace("Bearer ", ""));
        }
        return loggedAccountDetailsHolder;
    }
}
