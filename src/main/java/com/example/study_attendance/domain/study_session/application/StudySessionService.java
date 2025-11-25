package com.example.study_attendance.domain.study_session.application;

import com.example.study_attendance.domain.attendance.Attendance;
import com.example.study_attendance.domain.attendance.infrastructure.AttendanceRepository;
import com.example.study_attendance.domain.participant.Participant;
import com.example.study_attendance.domain.participant.infrastructure.ParticipantRepository;
import com.example.study_attendance.domain.study.Study;
import com.example.study_attendance.domain.study.infrastructure.StudyRepository;
import com.example.study_attendance.domain.study_session.StudySession;
import com.example.study_attendance.domain.study_session.infrastructure.StudySessionRepository;
import com.example.study_attendance.domain.study_session.presentation.dto.request.StudySessionCreateReqDto;
import com.example.study_attendance.domain.study_session.presentation.dto.response.StudySessionInfoResDto;
import com.example.study_attendance.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudySessionService {

    private final StudySessionRepository studySessionRepository;
    private final StudyRepository studyRepository;
    private final ParticipantRepository participantRepository;
    private final AttendanceRepository attendanceRepository;

    public void createStudySession(String code, StudySessionCreateReqDto request) {
        Study study = loadStudyOrThrow(code);
        log.info("startAt = {}", request.getStartAt());
        StudySession studySession = StudySession.from(study, request.getName(), request.getWeek(), request.getStartAt());
        studySessionRepository.save(studySession);
        List<Participant> participants = participantRepository.findAllByStudy(study);
        assignSessionsToParticipants(participants, studySession);
    }

    public List<StudySessionInfoResDto> getStudySessionsInfo(String code) {
        Study study = loadStudyOrThrow(code);
        List<StudySession> studySessions = studySessionRepository.findAllByStudy(study);
        return StudySessionInfoResDto.fromEntities(studySessions);
    }

    private void assignSessionsToParticipants(List<Participant> participants, StudySession studySession) {
        participants.forEach(participant -> {
                    Attendance attendance = Attendance.create(studySession, participant);
                    attendanceRepository.save(attendance);
                }
        );
    }

    private Study loadStudyOrThrow(String code) {
        return studyRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Study with code " + code + " not found"));
    }
}
