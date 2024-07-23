package me.inqu1sitor.controllers.handlers.impl;

import lombok.extern.slf4j.Slf4j;
import me.inqu1sitor.controllers.handlers.ExceptionHandlingController;
import me.inqu1sitor.dtos.ErrorResponseDto;
import me.inqu1sitor.exceptions.ClientException;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * An implementation of {@link ExceptionHandlingController}.
 *
 * @author Alexander Sankevich
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlingControllerImpl implements ExceptionHandlingController {

    /**
     * Handles an {@link EndpointNotImplementedException}
     */
    @Override
    public void onEndpointNotImplementedException() {
    }

    @Override
    public ErrorResponseDto onHttpMessageException(Exception e) {
        logWarn(e.getMessage());
        return new ErrorResponseDto(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage());
    }

    /**
     * Handles any {@link Throwable}
     *
     * @param e the thrown {@link Throwable}
     * @return the {@link ErrorResponseDto}
     */
    @Override
    public ErrorResponseDto onAnyException(final Exception e) {
        logError(e);
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Unhandled error");
    }

    @Override
    public ResponseEntity<String> onClientException(ClientException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getDescription());
    }

    private void logWarn(final String message) {
        log.warn("Forbidden: {}", message);
    }

    private void logError(final Exception e) {
        e.printStackTrace();
    }
}
