package me.inqu1sitor.authservice.utils;

import lombok.Getter;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;

/**
 * Redis prefixes for {@link OAuth2Authorization} persistence.
 *
 * @author Alexander Sankevich
 */
@Getter
public enum RedisPrefixes {

    /**
     * Redis prefix for {@link OAuth2Authorization} persistence
     * using its id in key.
     */
    ID_FOR_AUTH("id_for_auth:"),
    /**
     * Redis prefixes for {@link OAuth2Authorization} persistence
     * using its tokens in key and id in value.
     */
    ACCESS_FOR_ID("access_for_id:"),
    REFRESH_FOR_ID("refresh_for_id:"),
    CODE_FOR_ID("code_for_id:"),
    /**
     * Redis prefix for {@link OAuth2Authorization} persistence
     * using its principal name in key and id in value.
     */
    PRINCIPAL_FOR_ID("principal_for_id:");

    private final String prefix;

    RedisPrefixes(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return getPrefix();
    }
}
