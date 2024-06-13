package me.inquis1tor.userservice.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AdminRequiredException extends RuntimeException {
    public AdminRequiredException(String message) {
        super(message);
    }
}
