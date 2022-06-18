package com.homework.homework_week2.comment;

import com.amazonaws.Response;
import com.homework.homework_week2.comment.service.CommentService;
import com.homework.homework_week2.comment.dto.CommentRequestDto;
import com.homework.homework_week2.common.ResponseMessage;
import com.homework.homework_week2.exception.errorCode.CustomErrorCode;
import com.homework.homework_week2.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<ResponseMessage> addComment(
            @AuthenticationPrincipal User userDetails,
            @PathVariable(value = "postId", required = false) Long postId,
            @RequestBody CommentRequestDto commentRequestDto
            ) {
        if (userDetails == null) {
            throw new IllegalArgumentException(CustomErrorCode.ERROR_USER_NOT_EXISTS.getMessage());
        }
        commentService.addComment(userDetails, postId, commentRequestDto);

        return ResponseEntity.ok().body(new ResponseMessage("댓글 등록 성공"));
    }

    /**
     * 댓글 수정
     * @param commentId
     * @param commentRequestDto
     * @return
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ResponseMessage> updateComment(
            @AuthenticationPrincipal User userDetails,
            @PathVariable(required = false) Long commentId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        commentService.updateComment(userDetails, commentId, commentRequestDto);
        return ResponseEntity.ok().body(new ResponseMessage("댓글 수정 성공"));
    }

    /**
     * 댓글 삭제
     * @param commentId
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ResponseMessage> deleteComment(
            @AuthenticationPrincipal User userDetails,
            @PathVariable(required = false) Long commentId
    ) {
        commentService.deleteComment(userDetails, commentId);
        return ResponseEntity.ok().body(new ResponseMessage("댓글 삭제 성공"));
    }
}
