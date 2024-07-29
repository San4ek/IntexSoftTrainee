package me.inqu1sitor.authservice.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.entities.AccountRole;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.util.UUID;

/**
 * This class is responsible for holding the current logged {@link AccountEntity} details values.
 *
 * @author Alexander Sankevich
 */
@Data
@NoArgsConstructor
public class LoggedAccountDetailsProvider {

    /**
     * The current logged {@link AccountEntity} id.
     */
    private UUID accountId;

    /**
     * The current logged {@link AccountEntity} {@link AccountRole role}.
     */
    private AccountRole accountRole;

    /**
     * The current logged {@link AccountEntity} {@link OAuth2AccessToken OAuth 2.0 Access Token} value.
     */
    private String tokenValue;
}
