package com.example.atcs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MyException extends RuntimeException {
    private HttpStatus status;
    private String memberId;
    protected MyException(HttpStatus status, String message, String memberId) {
        super(message);
        this.status = status;
        this.memberId = memberId;
    }
}
