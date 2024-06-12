package me.inquis1tor.userservice.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailAlreadyExistsException extends ParameteredRuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    public EmailAlreadyExistsException(String parameter, String message) {
        super(parameter, message);
    }
}
