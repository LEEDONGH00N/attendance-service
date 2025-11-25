package com.example.study_attendance.domain.study.presentation;


import com.example.study_attendance.domain.participant.presentation.dto.request.ParticipantJoinReqDto;
import com.example.study_attendance.domain.study.application.StudyService;
import com.example.study_attendance.domain.study.presentation.dto.request.StudyCreateReqDto;
import com.example.study_attendance.domain.study.presentation.dto.response.StudyCreateResDto;
import com.example.study_attendance.domain.study.presentation.dto.response.StudyInfoResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studies")
public class StudyController {

    private final StudyService studyService;

    @PostMapping
    public ResponseEntity<StudyCreateResDto> createStudy(@Valid @RequestBody StudyCreateReqDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studyService.createStudy(request));
    }

    @GetMapping("/{studyCode}")
    public ResponseEntity<StudyInfoResDto> getStudy(@PathVariable String studyCode) {
        return ResponseEntity.status(HttpStatus.OK).body(studyService.searchStudyByCode(studyCode));
    }

    @PostMapping("/{studyCode}/join")
    public ResponseEntity<Void> joinStudy(@PathVariable String studyCode, @Valid @RequestBody ParticipantJoinReqDto request) {
        studyService.joinStudy(studyCode, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
