package com.homework.homework_week2.comment;

import com.homework.homework_week2.comment.service.CommentService;
import com.homework.homework_week2.post.dto.CommentDto;
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
     * @param commentDto
     * @return
     */
    @PostMapping("/comments/{postId}")
    public boolean addComment(
            @AuthenticationPrincipal User userDetails,
            @PathVariable(value = "postId", required = false) Long postId,
            @RequestBody CommentDto commentDto
            ) {

        return commentService.addComent(userDetails, postId, commentDto);
    }

    
}
