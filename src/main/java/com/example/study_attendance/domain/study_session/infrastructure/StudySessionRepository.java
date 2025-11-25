package com.example.study_attendance.domain.study_session.infrastructure;

import com.example.study_attendance.domain.study.Study;
import com.example.study_attendance.domain.study_session.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
    List<StudySession> findAllByStudy(Study study);
}
