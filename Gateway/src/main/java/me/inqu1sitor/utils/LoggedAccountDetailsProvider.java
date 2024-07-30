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
public class LoggedAccountDetailsProvider {

    /**
     * The current logged account OAuth 2.0 Access Token value.
     */
    private String tokenValue;
}
