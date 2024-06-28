package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StockNotExistException extends RuntimeException {
    public StockNotExistException() {
        super("Stock not exist");
        log.error("Stock not exist");
    }
    public StockNotExistException(String message) {
        super(message);
        log.error(message);
    }
}
