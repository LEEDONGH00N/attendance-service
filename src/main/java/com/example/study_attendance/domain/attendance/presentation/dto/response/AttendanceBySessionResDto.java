package com.example.study_attendance.domain.attendance.presentation.dto.response;

import com.example.study_attendance.domain.attendance.Attendance;
import com.example.study_attendance.domain.attendance.AttendanceStatus;
import com.example.study_attendance.domain.participant.Participant;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AttendanceBySessionResDto {

    private Long participantId;
    private String name;
    private String phone;
    private AttendanceStatus status;

    public static AttendanceBySessionResDto from(Attendance attendance) {
        Participant participant = attendance.getParticipant();
        return new AttendanceBySessionResDto(
                participant.getId(),
                participant.getName(),
                participant.getPhone(),
                attendance.getStatus()
        );
    }

    public static List<AttendanceBySessionResDto> fromEntities(List<Attendance> attendances) {
        return attendances.stream().map(AttendanceBySessionResDto::from).toList();
    }
}
