package com.homework.homework_week2.likes;

import com.homework.homework_week2.common.ResponseMessage;
import com.homework.homework_week2.likes.service.LikesService;
import com.homework.homework_week2.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LikesRestController {

    private final LikesService likesService;

    /**
     * 좋아요 등록
     * @param userDetails
     * @param postId
     * @return
     */
    @RequestMapping(value = "/posts/{postId}/like", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
    public ResponseEntity<ResponseMessage> addLikes(
            @AuthenticationPrincipal User userDetails,
            @PathVariable(required = false) Long postId
    ) {
        if (userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        return ResponseEntity.ok().body(likesService.likes(userDetails, postId));
    }

//    /**
//     * 좋아요 취소
//     * @param userDetails
//     * @param postId
//     * @return
//     */
//    @RequestMapping("/posts/{postId}/like")
//    public boolean deleteLikes(
//            @AuthenticationPrincipal User userDetails,
//            @PathVariable(required = false) Long postId
//    ) {
//        likesService.deletelikes(userDetails, postId);
//        return true;
//    }
}
