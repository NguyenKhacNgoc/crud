package com.example.crud.Exeption;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExeption {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<?> handlingRuntimeExeption(RuntimeException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

}
