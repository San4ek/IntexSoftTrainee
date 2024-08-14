package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EndpointNotImplementedException extends Exception {
    public EndpointNotImplementedException() {
        super("Endpoint not implemented");
    }
}
