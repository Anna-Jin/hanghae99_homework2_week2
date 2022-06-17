package com.homework.homework_week2.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String content;
    private Long template;

    @NotNull(message = "이미지를 등록해주세요.")
    private MultipartFile file;
}
