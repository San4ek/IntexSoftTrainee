package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductNotExistException extends RuntimeException {
    public ProductNotExistException() {
        super("Product not exist");
        log.error("Product not exist");
    }
    public ProductNotExistException(String message) {
        super(message);
        log.error(message);
    }
}
