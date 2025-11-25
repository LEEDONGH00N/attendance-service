package com.example.study_attendance.domain.participant.presentation.dto.response;

import com.example.study_attendance.domain.participant.Participant;
import com.example.study_attendance.domain.participant.ParticipantRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParticipantInfoResDto {
    private Long id;
    private String name;
    private String phone;
    private ParticipantRole role;

    public static ParticipantInfoResDto from(Participant participant) {
        return new ParticipantInfoResDto(participant.getId(), participant.getName(), participant.getPhone(), participant.getParticipantRole());
    }
}
