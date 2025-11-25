package com.example.study_attendance.domain.attendance.presentation.dto.request;

import com.example.study_attendance.domain.attendance.AttendanceStatus;
import lombok.Getter;

@Getter
public class AttendanceUpdateReqDto {
    private String password;
    private AttendanceStatus status;
}
