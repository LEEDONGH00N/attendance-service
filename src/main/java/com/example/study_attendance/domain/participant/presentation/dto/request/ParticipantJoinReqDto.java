package com.example.study_attendance.domain.participant.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ParticipantJoinReqDto {
    @NotBlank(message = "이름은 필수로 입력해주세요")
    private String name;
    @NotBlank(message = "전화번호는 필수로 입력해주세요")
    private String phone;
    @NotBlank(message = "비밀번호는 필수로 입력해주세요")
    private String password;
}
