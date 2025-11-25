package com.example.study_attendance.domain.study.infrastructure;

import com.example.study_attendance.domain.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StudyRepository extends JpaRepository<Study, Long> {
    Optional<Study> findByCode(String code);
}
