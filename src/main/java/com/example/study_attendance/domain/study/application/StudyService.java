package com.example.study_attendance.domain.study.application;

import com.example.study_attendance.domain.participant.Participant;
import com.example.study_attendance.domain.participant.ParticipantRole;
import com.example.study_attendance.domain.participant.infrastructure.ParticipantRepository;
import com.example.study_attendance.domain.participant.presentation.dto.request.ParticipantJoinReqDto;
import com.example.study_attendance.domain.study.Study;
import com.example.study_attendance.domain.study.infrastructure.StudyRepository;
import com.example.study_attendance.domain.study.presentation.dto.request.StudyCreateReqDto;
import com.example.study_attendance.domain.study.presentation.dto.response.StudyCreateResDto;
import com.example.study_attendance.domain.study.presentation.dto.response.StudyInfoResDto;
import com.example.study_attendance.global.exception.ConflictException;
import com.example.study_attendance.global.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final ParticipantRepository participantRepository;

    @Transactional
    public StudyCreateResDto createStudy(StudyCreateReqDto request) {
        Study study = Study.from(request.getName(), request.getDescription(), request.getLatePenalty(), request.getAbsentPenalty());
        Participant leader = Participant.from(study, request.getLeaderName(), request.getLeaderPhone(), ParticipantRole.LEADER, request.getLeaderPassword());
        participantRepository.save(leader);
        study.assignLeader(leader);
        studyRepository.save(study);
        return StudyCreateResDto.from(study);
    }

    public StudyInfoResDto searchStudyByCode(String code){
        Study study = loadStudyOrThrow(code);
        List<Participant> participants = participantRepository.findAllByStudy(study);
        return StudyInfoResDto.from(study, participants);
    }

    public void joinStudy(String code, ParticipantJoinReqDto request){
        Study study = loadStudyOrThrow(code);
        validateExistingParticipant(request, study);
        Participant participant = Participant.from(study, request.getName(), request.getPhone(), ParticipantRole.MEMBER, request.getPassword());
        participantRepository.save(participant);
    }

    private void validateExistingParticipant(ParticipantJoinReqDto request, Study study) {
        participantRepository.findByStudyAndPhone(study, request.getPhone())
                .ifPresent(p -> {
                    throw new ConflictException("Already existing participant");
                });
    }

    private Study loadStudyOrThrow(String code) {
        return studyRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Study with code " + code + " not found"));
    }
}
