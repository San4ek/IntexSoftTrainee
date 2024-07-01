package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BrandNotExistException extends RuntimeException {
    public BrandNotExistException() {
        super("Brand not exist");
        log.error("Brand not exist");
    }
    public BrandNotExistException(String message) {
        super(message);
        log.error(message);
    }
}
