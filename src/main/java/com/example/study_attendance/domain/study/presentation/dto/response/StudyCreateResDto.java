package com.example.study_attendance.domain.study.presentation.dto.response;

import com.example.study_attendance.domain.study.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudyCreateResDto {
    private String code;
    private String studyName;
    private String studyDescription;
    private Integer latePenalty;
    private Integer absencePenalty;

    public static StudyCreateResDto from(Study study) {
        return new StudyCreateResDto(study.getCode(), study.getName(), study.getDescription(), study.getLatePenalty(), study.getAbsentPenalty());
    }
}
