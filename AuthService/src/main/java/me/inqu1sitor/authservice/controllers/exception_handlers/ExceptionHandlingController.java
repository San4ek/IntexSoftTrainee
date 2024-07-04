package me.inqu1sitor.authservice.controllers.exception_handlers;

import jakarta.validation.ConstraintViolationException;
import me.inqu1sitor.authservice.dtos.ErrorResponseDto;
import me.inqu1sitor.authservice.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public void onNotFoundException() {

    }

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(EndpointNotImplementedException.class)
    public void onEndpointNotImplementedException() {

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public @ResponseBody List<ErrorResponseDto> onConstraintViolationsException(ConstraintViolationException e) {
        return e.getConstraintViolations().stream().
                map(val -> new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(),"Not valid data", val.getMessage())).toList();
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody List<ErrorResponseDto> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream().
                map(val -> new ErrorResponseDto(HttpStatus.EXPECTATION_FAILED.value(), "Incorrect data format", val.getDefaultMessage())).toList();
    }
}
