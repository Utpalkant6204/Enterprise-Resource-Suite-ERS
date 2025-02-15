package com.example.Enterprise.Resource.Suite.ERS.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> exceptions = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + " should be " + fieldError.getDefaultMessage())
                .toList();

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(exceptions);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
