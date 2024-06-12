package me.inquis1tor.userservice.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountNotExistsException extends ParameteredRuntimeException {
    public AccountNotExistsException(String message) {
        super(message);
    }

    public AccountNotExistsException(String parameter, String message) {
        super(parameter, message);
    }
}
