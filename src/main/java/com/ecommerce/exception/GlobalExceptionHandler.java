package com.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Date;

@RestControllerAdvice(basePackages = {"com.ecommerce.controllers"})
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionEntity> handleUniEducareException(Exception er){
        return new ResponseEntity<>(
                new ExceptionEntity(er.getMessage(), new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value())
                ,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}