package me.inqu1sitor.exceptions;

import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;

/**
 * Thrown if a {@link FeignClient} returned 4xx or 5xx status.
 *
 * @author Alexander Sankevich
 */
@Getter
public class ClientException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String description;

    public ClientException(final HttpStatus httpStatus, final String description) {
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
