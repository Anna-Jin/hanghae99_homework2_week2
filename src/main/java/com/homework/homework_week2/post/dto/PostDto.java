package com.homework.homework_week2.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private String title;
    private String content;
    private String imageUrl;
    private int viewCount;
    private LocalDateTime createdAt;
}
