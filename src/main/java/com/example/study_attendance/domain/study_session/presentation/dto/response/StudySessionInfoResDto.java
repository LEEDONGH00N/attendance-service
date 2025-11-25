package com.example.study_attendance.domain.study_session.presentation.dto.response;

import com.example.study_attendance.domain.study_session.StudySession;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class StudySessionInfoResDto {
    private Long sessionId;
    private String sessionName;
    private Integer sessionWeek;
    private LocalDateTime sessionDate;

    public static StudySessionInfoResDto from(StudySession studySession) {
        return new StudySessionInfoResDto(studySession.getId(), studySession.getName(), studySession.getWeek(), studySession.getStartAt());
    }

    public static List<StudySessionInfoResDto> fromEntities(List<StudySession> studySessions) {
        return studySessions.stream().map(StudySessionInfoResDto::from).toList();
    }
}
