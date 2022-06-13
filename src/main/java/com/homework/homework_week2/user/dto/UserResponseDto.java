package com.homework.homework_week2.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder @AllArgsConstructor
public class UserResponseDto {

    private String name;
    private String nickname;
    private String email;
    private String introduce;
    private String profileImageUrl;
    private String createdAt;
}
