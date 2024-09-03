package com.example.nxcommand.dto;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private String error;
    private ErrorResponse(String error) {
        this.error = error;
    }
    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }
}
