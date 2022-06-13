package com.homework.homework_week2.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDto {
    private String title;
    private String content;
    private String imageUrl;
    private int viewCount = 0;
    private String createdAt;

    @Builder
    public PostDto(String title, String content, String imageUrl, int viewCount, String createdAt) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.viewCount = viewCount + 1;
        this.createdAt = createdAt;
    }
}
