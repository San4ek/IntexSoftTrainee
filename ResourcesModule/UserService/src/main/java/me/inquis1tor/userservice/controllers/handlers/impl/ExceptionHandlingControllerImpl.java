package me.inquis1tor.userservice.controllers.handlers.impl;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import me.inquis1tor.userservice.controllers.handlers.ExceptionHandlingController;
import me.inquis1tor.userservice.dtos.ErrorResponseDto;
import me.inquis1tor.userservice.exceptions.AccountNotFoundException;
import me.inquis1tor.userservice.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * An implementation of {@link ExceptionHandlingController}.
 *
 * @author Alexander Sankevich
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlingControllerImpl implements ExceptionHandlingController {

    /**
     * Handles an {@link AccountNotFoundException}
     *
     * @param e the thrown {@link AccountNotFoundException}
     * @return the {@link ErrorResponseDto}
     */
    @Override
    public ErrorResponseDto onAccountNotFound(final AccountNotFoundException e) {
        logWarn(e.getMessage());
        return new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
    }

    /**
     * Handles an {@link EndpointNotImplementedException}
     */
    @Override
    public void onEndpointNotImplementedException() {
    }

    /**
     * Handles an {@link ConstraintViolationException}
     *
     * @param e the thrown {@link ConstraintViolationException}
     * @return a {@link List} of the {@link ErrorResponseDto}
     */
    @Override
    public List<ErrorResponseDto> onConstraintViolationsException(final ConstraintViolationException e) {
        return e.getConstraintViolations().stream().
                map(val -> {
                    logWarn(e.getMessage());
                    return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), val.getMessage());
                }).toList();
    }

    /**
     * Handles an {@link MethodArgumentNotValidException}
     *
     * @param e the thrown {@link MethodArgumentNotValidException}
     * @return a {@link List} of the {@link ErrorResponseDto}
     */
    @Override
    public List<ErrorResponseDto> onMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream().
                map(val -> {
                    logWarn(val.getDefaultMessage());
                    return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), val.getDefaultMessage());
                }).toList();
    }

    /**
     * Handles an {@link HttpMessageNotReadableException} and {@link MissingServletRequestParameterException}
     *
     * @param e the thrown {@link MethodArgumentNotValidException} or {@link MissingServletRequestParameterException}
     * @return a {@link List} of the {@link ErrorResponseDto}
     */
    @Override
    public ErrorResponseDto onHttpMessageException(Exception e) {
        logWarn(e.getMessage());
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
    }

    /**
     * Handles any {@link Exception}
     *
     * @param e the thrown {@link Exception}
     * @return the {@link ErrorResponseDto}
     * @throws AccessDeniedException or {@link AuthenticationException}
     *                               if {@code e} is an instance of this exceptions
     */
    @Override
    public ErrorResponseDto onAnyException(final Exception e) throws Exception {
        if (e instanceof AccessDeniedException || e instanceof AuthenticationException) {
            throw e;
        }
        logError(e);
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Unhandled error");
    }

    private void logWarn(final String message) {
        log.warn("Forbidden: {}", message);
    }

    private void logError(final Exception e) {
        log.error(e.getMessage(), e);
    }
}
