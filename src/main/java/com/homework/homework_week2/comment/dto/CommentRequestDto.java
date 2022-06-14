package com.homework.homework_week2.comment.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class CommentRequestDto {

    @NotBlank(message = "댓글 내용을 작성해주세요.")
    private String content;
}
