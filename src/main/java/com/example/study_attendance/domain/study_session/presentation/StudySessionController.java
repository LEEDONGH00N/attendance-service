package com.example.study_attendance.domain.study_session.presentation;

import com.example.study_attendance.domain.study_session.application.StudySessionService;
import com.example.study_attendance.domain.study_session.presentation.dto.request.StudySessionCreateReqDto;
import com.example.study_attendance.domain.study_session.presentation.dto.response.StudySessionInfoResDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studies/{studyCode}")
public class StudySessionController {

    private final StudySessionService studySessionService;

    @PostMapping("/sessions")
    public ResponseEntity<Void> createStudySession(@PathVariable String studyCode, @Valid @RequestBody StudySessionCreateReqDto request){
        studySessionService.createStudySession(studyCode, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<StudySessionInfoResDto>> getStudySessions(@PathVariable String studyCode) {
        return ResponseEntity.status(HttpStatus.OK).body(studySessionService.getStudySessionsInfo(studyCode));
    }
}
