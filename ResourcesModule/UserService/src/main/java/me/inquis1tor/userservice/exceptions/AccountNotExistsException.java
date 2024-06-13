package me.inquis1tor.userservice.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountNotExistsException extends RuntimeException {

    public AccountNotExistsException(String message) {
        super(message);
    }
}
