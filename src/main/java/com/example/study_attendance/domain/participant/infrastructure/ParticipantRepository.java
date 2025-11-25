package com.example.study_attendance.domain.participant.infrastructure;

import com.example.study_attendance.domain.participant.Participant;
import com.example.study_attendance.domain.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findAllByStudy(Study study);
    Optional<Participant> findByStudyAndPhone(Study study, String phone);
    Optional<Participant> findByStudyIdAndNameAndPhone(Long studyId, String name, String phone);
}
