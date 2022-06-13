package com.homework.homework_week2.post.dto;

import com.homework.homework_week2.timestamp.Timestamped;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private String title;
    private String content;
    private String imageUrl;
    private int viewCount = 0;
//    private LocalDateTime createdAt;

    @Builder
    public PostDto(String title, String content, String imageUrl, int viewCount) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.viewCount = viewCount + 1;
    }
}
