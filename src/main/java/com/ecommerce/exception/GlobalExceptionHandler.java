package com.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice(basePackages = {"com.ecommerce.controllers"})
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleUniEducareException(Exception er){
        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "message", er.getMessage()
        ), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}