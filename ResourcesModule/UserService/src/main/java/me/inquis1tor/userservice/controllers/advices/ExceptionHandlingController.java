package me.inquis1tor.userservice.controllers.advices;

import jakarta.validation.ConstraintViolationException;
import me.inquis1tor.userservice.dtos.errors.ErrorDto;
import me.inquis1tor.userservice.exceptions.AccountNotExistsException;
import me.inquis1tor.userservice.exceptions.AdminRequiredException;
import me.inquis1tor.userservice.exceptions.EmailAlreadyExistsException;
import me.inquis1tor.userservice.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

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
    @ExceptionHandler(AccountNotExistsException.class)
    public @ResponseBody ErrorDto onAccountNotExistsException(AccountNotExistsException e) {
        return null/*new ErrorDto(e.getMessage())*/;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public @ResponseBody ErrorDto onEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return null/*new ErrorDto(e.getMessage())*/;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public @ResponseBody List<ErrorDto> onConstraintViolationsException(ConstraintViolationException e) {
        System.out.println(e.getConstraintViolations().toString());

        return null/*e.getConstraintViolations().stream().map(val -> new ErrorDto(val.getMessage())).toList()*/;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AdminRequiredException.class, BindException.class})
    public @ResponseBody ErrorDto onAdminRequiredException(AdminRequiredException e) {
        return null/*new ErrorDto(e.getMessage())*/;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody List<ErrorDto> onMethodArgumentNoValidException(MethodArgumentNotValidException e) {
        return null/*e.getBindingResult().getFieldErrors().stream().
                map(val -> new ErrorDto(val.getDefaultMessage())).toList()*/;
    }
}
