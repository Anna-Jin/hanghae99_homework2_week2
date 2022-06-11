package com.homework.homework_week2.user.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Getter @Setter
public class RegisterRequestDto {
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)가 포함되어야 합니다.")
    private String nickname;

    @Email(message = "이메일 서식을 올바르게 맞춰주세요.")
    private String email;

    @Min(value = 4)
    private String password;

    private String introduce = "자기소개를 입력해주세요";

    private MultipartFile file;
}