package com.fakelsmile.bookshelf.service.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handle the exceptions and return custom error responses.
 */
@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    /**
     * Handle and return list of errors in the response body.
     * @param e - error to handle
     * @return list of errors
     */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorListResponse onConstraintValidationException(ConstraintViolationException e ) {
        final List<ErrorDto> errors = e.getConstraintViolations().stream()
                .map(
                        error -> new ErrorDto(
                                error.getPropertyPath().toString(),
                                error.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ErrorListResponse(errors);
    }

    /**
     * Handle and return list of errors in the response body.
     * @param e - error to handle
     * @return list of errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorListResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<ErrorDto> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorDto(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErrorListResponse(errors);
    }
}
