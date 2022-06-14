package com.homework.homework_week2.post.dto;

import com.homework.homework_week2.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PostDto {
    private Long postId;
    private String title;
    private String content;
    private String imageUrl;
    private int viewCount = 0;

    private List<Comment> comments;
    private String createdAt;

    @Builder
    public PostDto(Long postId, String title, String content, String imageUrl, int viewCount, List<Comment> comments, String createdAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.viewCount = viewCount + 1;
        this.comments = comments;
        this.createdAt = createdAt;
    }
}
