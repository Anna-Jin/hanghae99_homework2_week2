package com.homework.homework_week2.comment.dto;

import com.homework.homework_week2.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CommentResponseDto {
    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private String createdAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getId();
        this.userId = comment.getUser().getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
