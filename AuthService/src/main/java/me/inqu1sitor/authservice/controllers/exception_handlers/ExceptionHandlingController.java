package me.inqu1sitor.authservice.controllers.exception_handlers;

import jakarta.validation.ConstraintViolationException;
import me.inqu1sitor.authservice.dtos.ErrorResponseDto;
import me.inqu1sitor.authservice.exceptions.AccountNotFoundException;
import me.inqu1sitor.authservice.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Implementations of this interface are responsible
 * for handling the thrown exceptions during HTTP
 * requests processing and should be annotated with
 * {@link RestControllerAdvice}
 *
 * @author Alexander Sankevich
 */
public interface ExceptionHandlingController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    ErrorResponseDto onAccountNotFound(AccountNotFoundException e);

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(EndpointNotImplementedException.class)
    void onEndpointNotImplementedException();

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    List<ErrorResponseDto> onConstraintViolationsException(ConstraintViolationException e);

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<ErrorResponseDto> onMethodArgumentNotValidException(MethodArgumentNotValidException e);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    ErrorResponseDto onAnyException(Exception e);
}
