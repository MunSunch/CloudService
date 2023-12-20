package com.munsun.cloud_disk.controllers;

import com.munsun.cloud_disk.dto.out.Error;
import com.munsun.cloud_disk.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRestControllerAdvice {
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Error> userNotFoundExceptionHandler(UserNotFoundException e) {
        return ResponseEntity
                .badRequest()
                .body(new Error(e.getMessage(), 0));
    }
}
