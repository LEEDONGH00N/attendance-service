package com.example.study_attendance.global.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException{
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
