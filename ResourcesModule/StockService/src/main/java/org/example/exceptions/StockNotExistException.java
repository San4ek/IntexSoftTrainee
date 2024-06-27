package org.example.exceptions;

public class StockNotExistException extends RuntimeException {
    public StockNotExistException() {
        super("Stock not exist");
    }
    public StockNotExistException(String message) {
        super(message);
    }
}
