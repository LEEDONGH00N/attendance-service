package com.example.study_attendance.domain.participant.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParticipantPenaltyResDto {
    private Long participantId;
    private String name;
    private String phone;
    private Integer attendCount;
    private Integer lateCount;
    private Integer absentCount;
    private Integer totalPenalty;
}
