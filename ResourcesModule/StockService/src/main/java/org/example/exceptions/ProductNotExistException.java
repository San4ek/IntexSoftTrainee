package org.example.exceptions;

public class ProductNotExistException extends RuntimeException {
    public ProductNotExistException() {
        super("Product not exist");
    }
    public ProductNotExistException(String message) {
        super(message);
    }
}
