package me.inqu1sitor.authservice.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.inqu1sitor.authservice.entities.AccountEntity;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.util.UUID;

/**
 * This class is responsible for holding the current logged {@link AccountEntity} details values.
 *
 * @author Alexander Sankevich
 */
@Data
@NoArgsConstructor
public class LoggedAccountDetailsHolder {

    /**
     * The current logged {@link AccountEntity} id.
     */
    private UUID accountId;

    /**
     * The current logged {@link AccountEntity} {@link AccountEntity.Role role}.
     */
    private AccountEntity.Role accountRole;

    /**
     * The current logged {@link AccountEntity} {@link OAuth2AccessToken OAuth 2.0 Access Token} value.
     */
    private String tokenValue;
}
