package com.example.study_attendance.domain.study_session;

import com.example.study_attendance.domain.study.Study;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class StudySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer week;
    private String attendanceCode;
    private LocalDateTime startAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    protected StudySession(Study study, String name, Integer week, LocalDateTime startAt) {
        this.study = study;
        this.name = name;
        this.week = week;
        this.startAt = startAt;
    }

    public boolean isCodeValid(String input) {
        return attendanceCode != null && attendanceCode.equals(input);
    }

    public void updateAttendanceCode(String code) {
        this.attendanceCode = code;
    }

    public boolean isOwnedBy(Study study) {
        return this.study.getId().equals(study.getId());
    }

    public static StudySession from(Study study, String name, Integer week, LocalDateTime startAt) {
        return new StudySession(study, name, week, startAt);
    }
}