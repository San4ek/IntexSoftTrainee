package me.inqu1sitor.config;

import jakarta.servlet.http.HttpServletRequest;
import me.inqu1sitor.utils.LoggedAccountDetailsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

/**
 * {@link Configuration} for {@link LoggedAccountDetailsProvider} support.
 *
 * @author Alexander Sankevich
 */
@Configuration
public class LoggedAccountDetailsProviderConfig {

    @Bean
    @RequestScope
    public LoggedAccountDetailsProvider loggedAccountHolder(final HttpServletRequest httpServletRequest) {
        LoggedAccountDetailsProvider loggedAccountDetailsProvider = new LoggedAccountDetailsProvider();
        String authStr = httpServletRequest.getHeader("Authorization");
        if (authStr != null) {
            loggedAccountDetailsProvider.setTokenValue(authStr.replace("Bearer ", ""));
        }
        return loggedAccountDetailsProvider;
    }
}
