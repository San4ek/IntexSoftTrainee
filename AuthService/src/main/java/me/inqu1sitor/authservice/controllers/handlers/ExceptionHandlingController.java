package me.inqu1sitor.authservice.controllers.handlers;

import jakarta.validation.ConstraintViolationException;
import me.inqu1sitor.authservice.dtos.ErrorResponseDto;
import me.inqu1sitor.authservice.exceptions.AccountNotFoundException;
import me.inqu1sitor.authservice.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<ErrorResponseDto> onMethodArgumentNotValidException(MethodArgumentNotValidException e);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    ErrorResponseDto onHttpMessageException(Exception e);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AuthenticationServiceException.class)
    ErrorResponseDto onAuthenticationServiceException(final AuthenticationServiceException e);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ErrorResponseDto onAnyException(Exception e) throws Exception;
}
