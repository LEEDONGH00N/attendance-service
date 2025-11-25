package com.example.study_attendance.domain.attendance.infrastructure;

import com.example.study_attendance.domain.attendance.Attendance;
import com.example.study_attendance.domain.study_session.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByStudySessionIdAndParticipantId(Long sessionId, Long participantId);
    List<Attendance> findByStudySession(StudySession studySession);
}
