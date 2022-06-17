package com.homework.homework_week2.comment;

import com.homework.homework_week2.comment.service.CommentService;
import com.homework.homework_week2.comment.dto.CommentRequestDto;
import com.homework.homework_week2.exception.errorCode.CustomErrorCode;
import com.homework.homework_week2.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentRestController {
    private final CommentService commentService;

    /**
     * 댓글 생성
     * @param userDetails
     * @param postId
     * @param commentRequestDto
     * @return
     */
    @PostMapping("/comments/{postId}")
    public boolean addComment(
            @AuthenticationPrincipal User userDetails,
            @PathVariable(value = "postId", required = false) Long postId,
            @RequestBody CommentRequestDto commentRequestDto
            ) {

        return commentService.addComment(userDetails, postId, commentRequestDto);
    }

    /**
     * 댓글 수정
     * @param commentId
     * @param commentRequestDto
     * @return
     */
    @PutMapping("/comments/{commentId}")
    public boolean updateComment(
            @AuthenticationPrincipal User userDetails,
            @PathVariable(required = false) Long commentId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        if (userDetails == null) {
            throw new IllegalArgumentException(CustomErrorCode.ERROR_LOGIN_NECESSARY.getMessage());
        }

        commentService.updateComment(userDetails, commentId, commentRequestDto);
        return true;
    }

    /**
     * 댓글 삭제
     * @param commentId
     * @return
     */
    @DeleteMapping("/comments/{commentId}")
    public boolean deleteComment(
            @AuthenticationPrincipal User userDetails,
            @PathVariable(required = false) Long commentId
    ) {
        if (userDetails == null) {
            throw new IllegalArgumentException(CustomErrorCode.ERROR_LOGIN_NECESSARY.getMessage());
        }

        commentService.deleteComment(userDetails, commentId);
        return true;
    }
}
