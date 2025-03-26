package com.example.Enterprise.Resource.Suite.ERS.Exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.lang.reflect.Field;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        List<String> errors = bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> getFieldErrorMessage(fieldError, bindingResult))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("error", "Validation failed");
        response.put("messages", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParseException(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof MismatchedInputException mismatchedInputException) {
            String fieldPath = mismatchedInputException.getPath().isEmpty() ? "Unknown field" :
                    mismatchedInputException.getPath().get(0).getFieldName();

            Map<String, Object> response = new HashMap<>();
            response.put("error", "Invalid data format");
            response.put("message", "Field '" + fieldPath + "' has an incorrect data type");

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("error", "Invalid request body");
        response.put("message", "Request could not be parsed");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String getFieldErrorMessage(FieldError fieldError, BindingResult bindingResult) {
        String jsonPropertyName = getJsonPropertyName(fieldError, bindingResult);
        String fieldName = jsonPropertyName != null ? jsonPropertyName : fieldError.getField();
        return fieldName + " should be " + fieldError.getDefaultMessage();
    }

    private String getJsonPropertyName(FieldError fieldError, BindingResult bindingResult) {
        Object targetObject = bindingResult.getTarget();
        if (targetObject == null) {
            return null;
        }

        Class<?> targetClass = targetObject.getClass();
        try {
            String[] fieldParts = fieldError.getField().split("\\.");
            Field field = targetClass.getDeclaredField(fieldParts[0]);
            for (int i = 1; i < fieldParts.length; i++) {
                field = field.getType().getDeclaredField(fieldParts[i]);
            }
            if (field.isAnnotationPresent(JsonProperty.class)) {
                return field.getAnnotation(JsonProperty.class).value();
            }
        } catch (NoSuchFieldException ignored) {
        }
        return null;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Invalid API Request");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Database Error");
        response.put("message", "A database constraint violation occurred.");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("error", "Validation failed");
        response.put("messages", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Authorization Failed");
        response.put("message", "Invalid username or password.");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message", "Something went wrong, please try again later.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
