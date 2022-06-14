package com.homework.homework_week2.post.dto;

import com.homework.homework_week2.comment.dto.CommentResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String imageUrl;
    private Long viewCount;

    private List<CommentResponseDto> comments;
    private String createdAt;

    @Builder
    public PostResponseDto(Long postId, String title, String content, String imageUrl, Long viewCount, List<CommentResponseDto> comments, String createdAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.viewCount = viewCount;
        this.comments = comments;
        this.createdAt = createdAt;
    }
}
