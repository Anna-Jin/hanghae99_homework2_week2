package com.homework.homework_week2.user.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
public class RegisterRequestDto {
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{3,20}$", message = "닉네임은 최소 3자 이상, 알파벳 대소문자와, 숫자가 포함되어야 합니다.")
    private String nickname;

    @Email(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", message = "형식에 맞지 않은 이메일 주소입니다.")
    private String email;

    @Size(min = 4, max = 20, message = "비밀번호는 최소 4자 이상이어야합니다.")
    private String password;

    private String introduce = "자기소개를 입력해주세요";
}