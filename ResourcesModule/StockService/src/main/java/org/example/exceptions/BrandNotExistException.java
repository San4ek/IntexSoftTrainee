package org.example.exceptions;

public class BrandNotExistException extends RuntimeException {
    public BrandNotExistException() {
        super("Brand not exist");
    }
    public BrandNotExistException(String message) {
        super(message);
    }
}
