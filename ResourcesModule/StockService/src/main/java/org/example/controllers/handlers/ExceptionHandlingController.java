package org.example.controllers.handlers;

import org.example.dtos.ErrorResponse;
import org.example.exceptions.EndpointNotImplementedException;
import org.example.exceptions.InvalidObjectException;
import org.example.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(EndpointNotImplementedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public @ResponseBody ErrorResponse onEndpointNotImplementedException(EndpointNotImplementedException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(InvalidObjectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleInvalidObjectException(InvalidObjectException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleObjectNotFoundException(ObjectNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        return new ErrorResponse("Failed to convert data");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handleAllExceptions(Exception ex) {
        return new ErrorResponse("An unexpected error occurred: " + ex.getMessage());
    }
}
