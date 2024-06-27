package org.example.exceptions;

public class InvalidObjectException extends Exception {
    public InvalidObjectException() {
        super("Invalid Object");
    }
    public InvalidObjectException(String message) {
        super(message);
    }
}
