package com.paurush.finsight.exception;

import com.paurush.finsight.dto.ValidationErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.paurush.finsight.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(
            EmailAlreadyExistsException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ValidationErrorResponse response = new ValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(
            InvalidCredentialsException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }
}