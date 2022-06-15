package com.homework.homework_week2.likes;

import com.homework.homework_week2.likes.service.LikesService;
import com.homework.homework_week2.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikesRestController {

    private final LikesService likesService;

    /**
     * 좋아요 등록
     * @param userDetails
     * @param postId
     * @return
     */
    @PostMapping("/posts/{postId}/like")
    public boolean addlikes(
            @AuthenticationPrincipal User userDetails,
            @PathVariable(required = false) Long postId
    ) {
        likesService.addlikes(userDetails, postId);
        return true;
    }

    /**
     * 좋아요 취소
     * @param userDetails
     * @param postId
     * @return
     */
    @DeleteMapping("/posts/{postId}/like")
    public boolean deletelikes(
            @AuthenticationPrincipal User userDetails,
            @PathVariable(required = false) Long postId
    ) {
        likesService.deletelikes(userDetails, postId);
        return true;
    }
}
