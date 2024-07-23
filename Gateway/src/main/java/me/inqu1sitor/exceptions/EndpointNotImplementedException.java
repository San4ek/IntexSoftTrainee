package me.inqu1sitor.exceptions;

import me.inqu1sitor.controllers.AccountsController;
import me.inqu1sitor.controllers.PersonalInfosController;

/**
 * Thrown if not implemented {@link AccountsController} and
 * {@link PersonalInfosController} endpoints requested.
 *
 * @author Alexander Sankevich
 */
public class EndpointNotImplementedException extends Exception {

    /**
     * Constructs an <code>EndpointNotImplementedException</code> with basic message.
     */
    public EndpointNotImplementedException() {
        this("Endpoint not implemented");
    }

    /**
     * Constructs an <code>EndpointNotImplementedException</code> with the specified message.
     *
     * @param message the detail message
     */
    public EndpointNotImplementedException(String message) {
        super(message);
    }
}
