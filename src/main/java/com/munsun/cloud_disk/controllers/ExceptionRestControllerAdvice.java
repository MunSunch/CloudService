package com.munsun.cloud_disk.controllers;

import com.munsun.cloud_disk.dto.out.Error;
import com.munsun.cloud_disk.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@RestControllerAdvice
public class ExceptionRestControllerAdvice {
    private final AtomicInteger counterErrors = new AtomicInteger();

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Error> exceptionHandler(Exception e) {
        return ResponseEntity
                .badRequest()
                .body(new Error(e.getMessage(), counterErrors.getAndIncrement()));
    }
}
