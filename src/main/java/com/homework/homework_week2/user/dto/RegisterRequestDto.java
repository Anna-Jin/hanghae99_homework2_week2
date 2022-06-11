package com.homework.homework_week2.user.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class RegisterRequestDto {
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String introduce = "자기소개를 입력해주세요";
    private MultipartFile file;
}