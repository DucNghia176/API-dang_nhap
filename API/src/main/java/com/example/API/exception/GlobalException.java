package com.example.API.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<String> handlerRuntimeException(RuntimeException exception){
        return  ResponseEntity.badRequest().body(exception.getMessage());
    }
}
