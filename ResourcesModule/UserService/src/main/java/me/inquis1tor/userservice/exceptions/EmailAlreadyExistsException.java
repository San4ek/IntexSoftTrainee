package me.inquis1tor.userservice.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
