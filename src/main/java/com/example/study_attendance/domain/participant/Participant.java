package com.example.study_attendance.domain.participant;

import com.example.study_attendance.domain.study.Study;
import com.example.study_attendance.global.exception.UnauthorizedException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String password;

    @Enumerated(EnumType.STRING)
    private ParticipantRole participantRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    protected Participant(Study study, String name, String phone, ParticipantRole role, String password) {
        this.study = study;
        this.name = name;
        this.phone = phone;
        this.participantRole = role;
        this.password = password;
    }

    public boolean isOwnedBy(Study study) {
        return this.study.getId().equals(study.getId());
    }

    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }
    }

    public static Participant from(Study study, String name, String phone, ParticipantRole role, String password) {
        return new Participant(study, name, phone, role, password);
    }
}