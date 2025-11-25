package com.example.study_attendance.domain.attendance.presentation;

import com.example.study_attendance.domain.attendance.application.AttendanceService;
import com.example.study_attendance.domain.attendance.presentation.dto.request.AttendanceSelfCheckInReqDto;
import com.example.study_attendance.domain.attendance.presentation.dto.request.AttendanceUpdateReqDto;
import com.example.study_attendance.domain.attendance.presentation.dto.response.AttendanceBySessionResDto;
import com.example.study_attendance.domain.study_session.presentation.dto.response.AttendanceCodeResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studies/{studyCode}/sessions/{sessionId}/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<List<AttendanceBySessionResDto>> getAttendances(@PathVariable String studyCode, @PathVariable Long sessionId) {
        return ResponseEntity.status(HttpStatus.OK).body(attendanceService.getAttendancesBySession(studyCode, sessionId));
    }

    @PutMapping("/{participantId}")
    public ResponseEntity<Void> updateAttendance(@PathVariable String studyCode,
                                              @PathVariable Long sessionId,
                                              @PathVariable Long participantId,
                                              @Valid @RequestBody AttendanceUpdateReqDto request){
        attendanceService.updateAttendance(studyCode, sessionId, participantId, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/code")
    public ResponseEntity<AttendanceCodeResDto> generateAttendanceCode(@PathVariable String studyCode, @PathVariable Long sessionId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.generateAttendanceCode(studyCode, sessionId));
    }

    @PostMapping("/self-check-in")
    public ResponseEntity<Void> selfCheckIn(
            @PathVariable String studyCode,
            @PathVariable Long sessionId,
            @Valid @RequestBody AttendanceSelfCheckInReqDto request
    ) {
        attendanceService.selfCheckIn(studyCode, sessionId, request);
        return ResponseEntity.ok().build();
    }
}
