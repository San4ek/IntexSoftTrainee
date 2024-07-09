package me.inqu1sitor.authservice.services.ext;

import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;

/**
 * Implementations of this interface are responsible for the advanced management
 * of {@link OAuth2Authorization OAuth 2.0 Authorization(s)}.
 */
public interface OAuth2AuthorizationServiceExt extends OAuth2AuthorizationService {

    void removeByTokenValue(String tokenValue);

    void removeByPrincipalName(String principalName);
}
