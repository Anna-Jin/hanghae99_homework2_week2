package com.homework.homework_week2.comment;

import com.homework.homework_week2.comment.service.CommentService;
import com.homework.homework_week2.comment.dto.CommentRequestDto;
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
            @PathVariable(required = false) Long commentId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        commentService.updateComment(commentId, commentRequestDto);
        return true;
    }

    @DeleteMapping("/comment/{commentId}")
    public boolean deleteComment(
            @PathVariable(required = false) Long commentId
    ) {

        return true;
    }
}
