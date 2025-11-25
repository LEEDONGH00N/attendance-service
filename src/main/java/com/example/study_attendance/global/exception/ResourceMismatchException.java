package com.example.study_attendance.global.exception;

import org.springframework.http.HttpStatus;

public class ResourceMismatchException extends BaseException {
    public ResourceMismatchException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
