package me.inquis1tor.userservice.exceptions;

public class EndpointNotImplementedException extends Exception {
    public EndpointNotImplementedException() {
        super("Endpoint not implemented");
    }

    public EndpointNotImplementedException(String message) {
        super(message);
    }
}
