package com.homework.homework_week2.post;

import com.homework.homework_week2.post.dto.PostDto;
import com.homework.homework_week2.post.dto.PostRequestDto;
import com.homework.homework_week2.post.service.PostService;
import com.homework.homework_week2.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
            @ModelAttribute PostRequestDto postRequestDto
            ) {

        boolean result = postService.addPost(userDetails, postRequestDto);

        return result;
    }

    /**
     * 게시물 목록 조회
     * @return
     */
    @GetMapping("/posts")
    public List<PostDto> getPosts() {
        return postService.getPosts();
    }

    /**
     * 게시물 단건 조회
     * @param postId
     * @return
     */
    @GetMapping("/posts/{postId}")
    public PostDto getPost(
            @PathVariable(value = "postId", required = false) Long postId
    ) {
        return postService.getPost(postId);
    }
}
