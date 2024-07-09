package me.inqu1sitor.authservice.services.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.services.LogoutService;
import me.inqu1sitor.authservice.services.ext.OAuth2AuthorizationServiceExt;
import me.inqu1sitor.authservice.utils.LoggedAccountDetailsHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * An implementation of an {@link LogoutService}.
 *
 * @author Alexander Sankevich
 */
@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final LoggedAccountDetailsHolder loggedAccountDetailsHolder;
    private final OAuth2AuthorizationServiceExt authorizationService;

    /**
     * Logout {@link OAuth2Authorization authorization} identified by the provided parameter
     * {@code tokenValue}.
     *
     * @param tokenValue the {@link OAuth2Authorization authorizations}
     *                   {@link OAuth2AccessToken OAuth 2.0 Access Token} value
     */
    @Override
    public void logout(final String tokenValue) {
        authorizationService.removeByTokenValue(tokenValue);
    }

    /**
     * Logout current {@link LoggedAccountDetailsHolder logged} {@link OAuth2Authorization authorization}.
     */
    @Override
    public void logout() {
        logout(loggedAccountDetailsHolder.getTokenValue());
    }

    /**
     * Logout {@link OAuth2Authorization authorization} identified by the provided parameter
     * {@code accountId} from all devices.
     *
     * @param accountId the {@link OAuth2Authorization authorizations} principal name
     */
    @Override
    public void logoutAll(final UUID accountId) {
        authorizationService.removeByPrincipalName(accountId.toString());
    }

    /**
     * Logout current {@link LoggedAccountDetailsHolder logged} {@link OAuth2Authorization authorization}
     * from all devices.
     */
    @Override
    public void logoutAll() {
        logoutAll(loggedAccountDetailsHolder.getAccountId());
    }
}
