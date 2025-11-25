package com.example.study_attendance.global.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException{
    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
