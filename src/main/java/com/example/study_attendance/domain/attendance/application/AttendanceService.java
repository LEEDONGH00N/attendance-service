package com.example.study_attendance.domain.attendance.application;

import com.example.study_attendance.domain.attendance.Attendance;
import com.example.study_attendance.domain.attendance.AttendanceStatus;
import com.example.study_attendance.domain.attendance.infrastructure.AttendanceRepository;
import com.example.study_attendance.domain.attendance.presentation.dto.request.AttendanceSelfCheckInReqDto;
import com.example.study_attendance.domain.attendance.presentation.dto.request.AttendanceUpdateReqDto;
import com.example.study_attendance.domain.attendance.presentation.dto.response.AttendanceBySessionResDto;
import com.example.study_attendance.domain.participant.Participant;
import com.example.study_attendance.domain.participant.infrastructure.ParticipantRepository;
import com.example.study_attendance.domain.study.Study;
import com.example.study_attendance.domain.study.infrastructure.StudyRepository;
import com.example.study_attendance.domain.study_session.StudySession;
import com.example.study_attendance.domain.study_session.infrastructure.StudySessionRepository;
import com.example.study_attendance.domain.study_session.presentation.dto.response.AttendanceCodeResDto;
import com.example.study_attendance.global.exception.EntityNotFoundException;
import com.example.study_attendance.global.exception.ResourceMismatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final StudyRepository studyRepository;
    private final ParticipantRepository participantRepository;
    private final StudySessionRepository studySessionRepository;
    private final AttendanceRepository attendanceRepository;

    public List<AttendanceBySessionResDto> getAttendancesBySession(String studyCode, Long sessionId) {
        Study study = loadStudyOrThrow(studyCode);
        StudySession session = loadStudySessionOrThrow(sessionId);
        ensureStudySessionBelongsToStudy(session, study);
        List<Attendance> attendances = attendanceRepository.findByStudySession(session);
        return AttendanceBySessionResDto.fromEntities(attendances);
    }

    public void updateAttendance(String studyCode, Long sessionId, Long participantId, AttendanceUpdateReqDto request) {
        Study study = loadStudyOrThrow(studyCode);
        StudySession session = loadStudySessionOrThrow(sessionId);
        ensureStudySessionBelongsToStudy(session, study);
        Participant participant = loadParticipantOrThrow(participantId);
        ensureParticipantBelongsToStudy(participant, study);
        Attendance attendance = loadAttendanceOrThrow(sessionId, participantId);
        Participant leader = study.getLeader();
        leader.checkPassword(request.getPassword());
        attendance.updateStatus(request.getStatus());
        attendanceRepository.save(attendance);
    }

    public void selfCheckIn(String studyCode, Long sessionId, AttendanceSelfCheckInReqDto request) {
        Study study = loadStudyOrThrow(studyCode);
        StudySession session = loadStudySessionOrThrow(sessionId);
        ensureStudySessionBelongsToStudy(session, study);
        Participant participant = loadParticipantOrThrow(request, study);
        validateCheckInCode(session, request.getCode());
        Attendance attendance = getOrCreateAttendance(session, participant);
        attendance.updateStatus(AttendanceStatus.ATTEND); // TODO : 일단은 출석으로 고정 (지각/결석 로직은 이후 확장)
        attendanceRepository.save(attendance);
    }

    public AttendanceCodeResDto generateAttendanceCode(String studyCode, Long sessionId) {
        loadStudyOrThrow(studyCode);
        StudySession session = loadSessionOrThrow(sessionId);
        String code = String.format("%04d", ThreadLocalRandom.current().nextInt(0, 10000));
        session.updateAttendanceCode(code);
        studySessionRepository.save(session);
        return new AttendanceCodeResDto(code);
    }

    private void validateCheckInCode(StudySession session, String inputCode) {
        if (session.getAttendanceCode() == null) {
            throw new IllegalStateException("이 세션에는 출석 코드가 설정되지 않았습니다.");
        }
        if (!session.getAttendanceCode().equals(inputCode)) {
            throw new IllegalArgumentException("출석 코드가 올바르지 않습니다.");
        }
    }

    private StudySession loadSessionOrThrow(Long sessionId) {
        return studySessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("StudySession with code " + sessionId + " not found"));
    }

    private Participant loadParticipantOrThrow(AttendanceSelfCheckInReqDto request, Study study) {
        return participantRepository
                .findByStudyIdAndNameAndPhone(study.getId(), request.getName(), request.getPhone())
                .orElseThrow(() -> new IllegalArgumentException("해당 이름/전화번호의 참가자를 찾을 수 없습니다."));
    }

    private Participant loadParticipantOrThrow(Long participantId) {
        return participantRepository.findById(participantId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 참가자입니다."));
    }

    private Study loadStudyOrThrow(String studyCode) {
        return studyRepository.findByCode(studyCode)
                .orElseThrow(() -> new EntityNotFoundException("해당 스터디 코드를 가진 스터디가 없습니다."));
    }

    private Attendance loadAttendanceOrThrow(Long sessionId, Long participantId) {
        return attendanceRepository.findByStudySessionIdAndParticipantId(sessionId, participantId)
                .orElseThrow(() -> new EntityNotFoundException("출석 기록이 없습니다."));
    }

    private StudySession loadStudySessionOrThrow(Long sessionId) {
        return studySessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("세션이 없습니다."));
    }

    private void ensureStudySessionBelongsToStudy(StudySession session, Study study) {
        if (!session.isOwnedBy(study)) {
            throw new ResourceMismatchException("해당 스터디에 속한 세션이 아닙니다.");
        }
    }

    private Attendance getOrCreateAttendance(StudySession session, Participant participant) {
        return attendanceRepository
                .findByStudySessionIdAndParticipantId(session.getId(), participant.getId())
                .orElseGet(() ->
                        Attendance.create(session, participant)
                );
    }

    private void ensureParticipantBelongsToStudy(Participant participant, Study study) {
        if (!participant.isOwnedBy(study)) {
            throw new ResourceMismatchException("해당 스터디에 속한 참여자가 아닙니다.");
        }
    }
}
