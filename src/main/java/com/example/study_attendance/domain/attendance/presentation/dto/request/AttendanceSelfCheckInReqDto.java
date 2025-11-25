package com.example.study_attendance.domain.attendance.presentation.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AttendanceSelfCheckInReqDto {
    private String name;
    private String phone;
    private String code;

    public AttendanceSelfCheckInReqDto(String name, String phone, String code) {
        this.name = name;
        this.phone = phone;
        this.code = code;
    }
}