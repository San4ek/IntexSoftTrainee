package me.inquis1tor.userservice.exceptions;

import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.services.AccountFinderService;

import java.util.UUID;

/**
 * Thrown if an {@link AccountFinderService} implementation cannot locate an {@link AccountEntity} by
 * its id.
 *
 * @author Alexander Sankevich
 */
public class AccountNotFoundException extends RuntimeException {

    /**
     * Constructs an <code>AccountNotFoundException</code> with basic message and the specified id.
     *
     * @param accountId the detail id.
     */
    public AccountNotFoundException(final UUID accountId) {
        this("Account '" + accountId + "' not found or not meet the requirements");
    }

    /**
     * Constructs an <code>AccountNotFoundException</code> with the specified message.
     *
     * @param message the detail message.
     */
    public AccountNotFoundException(final String message) {
        super(message);
    }
}
