package com.example.study_attendance.domain.attendance;

import com.example.study_attendance.domain.participant.Participant;
import com.example.study_attendance.domain.study.Study;
import com.example.study_attendance.domain.study_session.StudySession;
import com.example.study_attendance.global.generator.AttendanceIds;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.study_attendance.domain.attendance.AttendanceStatus.ABSENT;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status = AttendanceStatus.ABSENT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_session_id")
    private StudySession studySession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant participant;

    private Attendance(StudySession session, Participant participant) {
        this.studySession = session;
        this.participant = participant;
    }

    public void updateStatus(AttendanceStatus newStatus) {
        switch (newStatus) {
            case ATTEND -> this.status = AttendanceStatus.ATTEND;
            case LATE -> this.status = AttendanceStatus.LATE;
            case ABSENT -> this.status = AttendanceStatus.ABSENT;
        }
    }

    public static Attendance create(StudySession session, Participant participant) {
        return new Attendance(session, participant);
    }
}