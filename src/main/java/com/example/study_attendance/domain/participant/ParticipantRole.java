package com.example.study_attendance.domain.participant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ParticipantRole {
    MEMBER("멤버"),
    LEADER("리더"),
    ;

    private final String role;
}
