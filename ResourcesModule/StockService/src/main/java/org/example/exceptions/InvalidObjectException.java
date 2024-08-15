package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidObjectException extends RuntimeException {
    public InvalidObjectException() {
        super("Invalid Object");
    }
    public InvalidObjectException(String message) {
        super(message);
    }
}
