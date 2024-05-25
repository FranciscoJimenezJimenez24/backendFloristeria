package com.backend.floristeria.exception;

import org.springframework.http.HttpStatus;

public class JwtException extends RuntimeException {

    private HttpStatus httpStatus;

    public JwtException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
