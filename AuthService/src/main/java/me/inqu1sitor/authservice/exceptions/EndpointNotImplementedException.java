package me.inqu1sitor.authservice.exceptions;

import me.inqu1sitor.authservice.controllers.AccountController;

/**
 * Thrown if not implemented {@link AccountController} endpoint requested.
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
