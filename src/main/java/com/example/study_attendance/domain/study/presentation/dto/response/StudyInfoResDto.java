package com.example.study_attendance.domain.study.presentation.dto.response;

import com.example.study_attendance.domain.participant.Participant;
import com.example.study_attendance.domain.participant.presentation.dto.response.ParticipantInfoResDto;
import com.example.study_attendance.domain.study.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StudyInfoResDto {
    private String name;
    private String description;
    private Integer latePenalty;
    private Integer absentPenalty;
    private List<ParticipantInfoResDto> participants;

    public static StudyInfoResDto from(Study study, List<Participant> participants) {
        return new StudyInfoResDto(
                study.getName(),
                study.getDescription(),
                study.getLatePenalty(),
                study.getAbsentPenalty(),
                participants.stream().map(ParticipantInfoResDto::from).toList()
        );
    }
}
