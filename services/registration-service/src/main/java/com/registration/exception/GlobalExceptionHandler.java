package com.registration.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, Object> filedErrors=new HashMap<>();

        ex.getBindingResult()
            .getFieldErrors()
            .forEach(error ->
                filedErrors.put(error.getField(), error.getDefaultMessage())
            );

        Map<String, Object> responses=new HashMap<>();

        responses.put("status", HttpStatus.BAD_REQUEST.value());
        responses.put("message", "Validation failed");
        responses.put("error", filedErrors);
        responses.put("timestamp", LocalDateTime.now());


        return ResponseEntity.badRequest().body(responses);
    }

        @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<Map<String, Object>> handleRegistrationException(RegistrationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", ex.getMessage());
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", "Internal Server Error");
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.internalServerError().body(response);

    }
}