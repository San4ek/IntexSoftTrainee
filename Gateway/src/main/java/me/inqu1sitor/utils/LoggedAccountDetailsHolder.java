package me.inqu1sitor.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is responsible for holding
 * the current logged account details values.
 *
 * @author Alexander Sankevich
 */
@Data
@NoArgsConstructor
public class LoggedAccountDetailsHolder {

    /**
     * The current logged account OAuth2AccessToken OAuth 2.0 Access Token value.
     */
    private String tokenValue;
}
