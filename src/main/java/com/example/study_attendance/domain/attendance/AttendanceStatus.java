package com.example.study_attendance.domain.attendance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석"),
    ;

    private final String state;
}
