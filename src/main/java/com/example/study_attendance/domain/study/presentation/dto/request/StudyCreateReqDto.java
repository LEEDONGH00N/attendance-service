package com.example.study_attendance.domain.study.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StudyCreateReqDto {
    @NotBlank(message = "스터디 이름은 필수입니다.")
    private String name;
    private String description;
    @NotBlank(message = "리더 이름은 필수입니다.")
    private String leaderName;
    @NotBlank(message = "리더 연락처는 필수입니다.")
    private String leaderPhone;
    private String leaderPassword;
    private Integer latePenalty;
    private Integer absentPenalty;
}
