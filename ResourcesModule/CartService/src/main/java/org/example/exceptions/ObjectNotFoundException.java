package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException() {
        super("Object not found");
    }
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
