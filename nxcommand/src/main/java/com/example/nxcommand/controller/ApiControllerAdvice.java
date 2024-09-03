package com.example.nxcommand.controller;

import com.example.nxcommand.dto.ErrorResponse;
import com.example.nxcommand.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiControllerAdvice {
    @ExceptionHandler(MyException.class)
    public ResponseEntity<ErrorResponse> myExceptionHandler(MyException exception) {
        log.error(String.format("exception MemberId: %s, Message: %s", exception.getMemberId(), exception.getMessage()));
        return ResponseEntity.status(exception.getStatus()).body(ErrorResponse.of(exception.getMessage()));
    }
}
