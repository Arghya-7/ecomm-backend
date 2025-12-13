package com.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(UniEducareException.class)
//    public ResponseEntity<EcommException> handleUniEducareException(UniEducareException uniEducareException){
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EcommException(uniEducareException.getMessage()));
//    }
//}