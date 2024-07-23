package me.inqu1sitor.controllers.handlers;

import me.inqu1sitor.dtos.ErrorResponseDto;
import me.inqu1sitor.exceptions.ClientException;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Implementations of this interface are responsible
 * for handling the thrown exceptions during HTTP
 * requests processing and should be annotated with
 * {@link RestControllerAdvice}
 *
 * @author Alexander Sankevich
 */
public interface ExceptionHandlingController {

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(EndpointNotImplementedException.class)
    void onEndpointNotImplementedException();

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    ErrorResponseDto onHttpMessageException(Exception e);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ErrorResponseDto onAnyException(Exception e) throws Exception;

    @ExceptionHandler(ClientException.class)
    ResponseEntity<String> onClientException(ClientException e);
}
