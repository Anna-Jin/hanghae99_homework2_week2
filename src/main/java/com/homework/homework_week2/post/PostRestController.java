package com.homework.homework_week2.post;

import com.homework.homework_week2.post.dto.PostResponseDto;
import com.homework.homework_week2.post.dto.PostRequestDto;
import com.homework.homework_week2.post.service.PostService;
import com.homework.homework_week2.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostRestController {

    private final PostService postService;

    /**
     * 게시물 생성
     * @param userDetails
     * @param postRequestDto
     * @return
     */
    @PostMapping("/posts")
    public boolean addPost(
            @AuthenticationPrincipal User userDetails,
            @RequestBody PostRequestDto postRequestDto
            ) {
        return postService.addPost(userDetails, postRequestDto);
    }

    /**
     * 게시물 목록 조회
     * @return
     */
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(@AuthenticationPrincipal User userDetails) {
        return postService.getPosts(userDetails);
    }

    /**
     * 게시물 단건 조회
     * @param postId
     * @return
     */
    @GetMapping("/posts/{postId}")
    public PostResponseDto getPost(
            @AuthenticationPrincipal User userDetails,
            @PathVariable(value = "postId", required = false) Long postId
    ) {
        return postService.getPost(userDetails, postId);
    }

    /**
     * 게시물 수정
     * @param postId
     * @param postRequestDto
     * @return
     */
    @PutMapping("/posts/{postId}")
    public boolean updatePost(
            @PathVariable(value = "postId", required = false) Long postId,
            @ModelAttribute PostRequestDto postRequestDto
    ) {
        postService.updatePost(postId, postRequestDto);
        return true;
    }

    /**
     * 게시물 삭제
     * @param userDetails
     * @param postId
     * @return
     */
    @DeleteMapping("/posts/{postId}")
    public boolean deletePost(
            @AuthenticationPrincipal User userDetails,
            @PathVariable(value = "postId", required = false) Long postId) {
        postService.deletePost(userDetails, postId);
        return true;
    }
}
