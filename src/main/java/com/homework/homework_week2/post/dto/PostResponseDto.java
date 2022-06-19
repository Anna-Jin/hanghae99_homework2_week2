package com.homework.homework_week2.post.dto;

import com.homework.homework_week2.comment.dto.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder @AllArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private int template;
    private String imageUrl;
    private String nickname;
    private String email;
    private Long viewCount;
    private List<CommentResponseDto> comments;
    private String createdAt;
    private boolean likeByMe;
    private Long likeCount;

}