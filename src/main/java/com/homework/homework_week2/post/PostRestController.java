package com.homework.homework_week2.post;

import com.homework.homework_week2.common.ResponseMessage;
import com.homework.homework_week2.post.dto.PostRequestDto;
import com.homework.homework_week2.post.dto.PostResponseDto;
import com.homework.homework_week2.post.service.PostService;
import com.homework.homework_week2.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostRestController {

    private final PostService postService;

    /**
     * 게시물 생성
     * @param userDetails
     * @param postRequestDto
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/posts")
    public ResponseEntity<ResponseMessage> addPost(
            @AuthenticationPrincipal User userDetails,
            @ModelAttribute PostRequestDto postRequestDto
            ) {
        postService.addPost(userDetails, postRequestDto);

        return ResponseEntity.ok().body(new ResponseMessage("게시물 등록 성공"));
    }

    /**
     * 게시물 목록 조회
     * @return
     */
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(@AuthenticationPrincipal User userDetails) {
        if (userDetails == null) {
            return postService.getPosts();
        }

        return postService.getPostsByUser(userDetails);
    }

    /**
     * 게시물 단건 조회
     * @param postId
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
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
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/posts/{postId}")
    public ResponseEntity<ResponseMessage> updatePost(
            @AuthenticationPrincipal User userDetails,
            @PathVariable(value = "postId", required = false) Long postId,
            @ModelAttribute PostRequestDto postRequestDto
    ) {
        postService.updatePost(userDetails, postId, postRequestDto);
        return ResponseEntity.ok().body(new ResponseMessage("게시물 수정 성공"));
    }

    /**
     * 게시물 삭제
     * @param userDetails
     * @param postId
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ResponseMessage> deletePost(
            @AuthenticationPrincipal User userDetails,
            @PathVariable(value = "postId", required = false) Long postId) {
        postService.deletePost(userDetails, postId);
        return ResponseEntity.ok().body(new ResponseMessage("게시물 삭제 성공"));
    }
}
