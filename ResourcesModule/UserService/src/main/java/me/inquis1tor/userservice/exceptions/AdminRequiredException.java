package me.inquis1tor.userservice.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AdminRequiredException extends ParameteredRuntimeException {
    public AdminRequiredException(String message) {
        super(message);
    }

    public AdminRequiredException(String parameter, String message) {
        super(parameter, message);
    }
}
