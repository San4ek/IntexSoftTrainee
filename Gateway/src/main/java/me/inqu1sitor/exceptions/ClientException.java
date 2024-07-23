package me.inqu1sitor.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String description;

    public ClientException(final HttpStatus httpStatus, final String description) {
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
