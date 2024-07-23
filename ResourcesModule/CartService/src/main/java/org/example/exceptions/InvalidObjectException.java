package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidObjectException extends Exception {
    public InvalidObjectException() {
        super("Invalid Object");
        log.error("Invalid Object");
    }
    public InvalidObjectException(String message) {
        super(message);
        log.error(message);
    }
}
