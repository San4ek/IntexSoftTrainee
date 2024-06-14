package me.inquis1tor.userservice.controllers.advices;

import jakarta.validation.ConstraintViolationException;
import me.inquis1tor.userservice.dtos.errors.ErrorDto;
import me.inquis1tor.userservice.exceptions.AdminRequiredException;
import me.inquis1tor.userservice.exceptions.EndpointNotImplementedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
    public @ResponseBody List<ErrorDto> onConstraintViolationsException(ConstraintViolationException e) {
        return e.getConstraintViolations().stream().
                map(val -> new ErrorDto(3, "Not valid data", val.getMessage())).toList();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AdminRequiredException.class, BindException.class})
    public @ResponseBody ErrorDto onAdminRequiredException(AdminRequiredException e) {
        return new ErrorDto(1, "Admin required", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody List<ErrorDto> onMethodArgumentNoValidException(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream().
                map(val -> new ErrorDto(2, "Incorrect data format", val.getDefaultMessage())).toList();
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorDto onDataIntegrityViolationException(SQLException e) {
        System.out.println(e.getSQLState());
        return null;
    }
}
