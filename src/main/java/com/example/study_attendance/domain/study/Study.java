package com.example.study_attendance.domain.study;

import com.example.study_attendance.domain.attendance.AttendanceStatus;
import com.example.study_attendance.domain.participant.Participant;
import com.example.study_attendance.global.generator.StudyCodeGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String code;
    private Integer latePenalty;
    private Integer absentPenalty;

    @OneToOne
    @JoinColumn(name = "leader_id")
    private Participant leader;

    private Study(String name, String description, Integer latePenalty, Integer absentPenalty) {
        this.name = name;
        this.description = description;
        this.code = StudyCodeGenerator.generateCode();
        this.latePenalty = latePenalty;
        this.absentPenalty = absentPenalty;
    }

    public int calculatePenalty(AttendanceStatus status) {
        return switch (status) {
            case ATTEND -> 0;
            case LATE -> latePenalty != null ? latePenalty : 0;
            case ABSENT -> absentPenalty != null ? absentPenalty : 0;
        };
    }

    public void assignLeader(Participant leader) {
        this.leader = leader;
    }

    public static Study from(String name, String description, Integer latePenalty, Integer absentPenalty) {
        return new Study(name, description, latePenalty, absentPenalty);
    }
}