package me.inqu1sitor.authservice.exceptions;

public class EndpointNotImplementedException extends Exception {

    public EndpointNotImplementedException() {
        super("Endpoint not implemented");
    }

    public EndpointNotImplementedException(String message) {
        super(message);
    }
}
