package org.example.controllers.handlers;

import lombok.extern.slf4j.Slf4j;
import org.example.dtos.ErrorResponse;
import org.example.exceptions.EndpointNotImplementedException;
import org.example.exceptions.InvalidObjectException;
import org.example.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Global exception handler for the application.
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {

    /**
     * Handles EndpointNotImplementedException.
     *
     * @param exception the caught EndpointNotImplementedException
     * @return an ErrorResponse with a generic error message
     */
    @ExceptionHandler(EndpointNotImplementedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public @ResponseBody ErrorResponse onEndpointNotImplementedException(EndpointNotImplementedException exception) {
        log.error("Handled EndpointNotImplementedException: {}", exception.getMessage());
        return new ErrorResponse("Something went wrong");
    }

    /**
     * Handles InvalidObjectException.
     *
     * @param exception the caught InvalidObjectException
     * @return an ErrorResponse with a generic error message
     */
    @ExceptionHandler(InvalidObjectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleInvalidObjectException(InvalidObjectException exception) {
        log.error("Handled InvalidObjectException: {}", exception.getMessage());
        return new ErrorResponse("Something went wrong");
    }

    /**
     * Handles ObjectNotFoundException.
     *
     * @param exception the caught ObjectNotFoundException
     * @return an ErrorResponse with a generic error message
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleObjectNotFoundException(ObjectNotFoundException exception) {
        log.error("Handled ObjectNotFoundException: {}", exception.getMessage());
        return new ErrorResponse("Something went wrong");
    }

    /**
     * Handles HttpMessageNotReadableException
     *
     * @param exception the caught HttpMessageNotReadableException
     * @return an ErrorResponse with a generic error message
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("Handled HttpMessageNotReadableException: {}", exception.getMessage());
        return new ErrorResponse("Missing request body");
    }

    /**
     * Handles MissingRequestHeaderException
     *
     * @param exception the caught MissingRequestHeaderException
     * @return an ErrorResponse with a generic error message
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleMissingRequestHeaderException(MissingRequestHeaderException exception) {
        log.error("Handled MissingRequestHeaderException: {}", exception.getMessage());
        return new ErrorResponse("Missing request header");
    }

    /**
     * Handles MethodArgumentTypeMismatchException.
     *
     * @param exception the caught MethodArgumentTypeMismatchException
     * @return an ErrorResponse with a generic error message
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        log.error("Handled MethodArgumentTypeMismatchException: {}", exception.getMessage());
        return new ErrorResponse("Something went wrong");
    }

    /**
     * Handles all other exceptions.
     *
     * @param exception the caught Exception
     * @return an ErrorResponse with a generic error message
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handleAllExceptions(Exception exception) {
        log.error("Unhandled exception: {}", exception.getMessage());
        return new ErrorResponse("Something went wrong");
    }
}
