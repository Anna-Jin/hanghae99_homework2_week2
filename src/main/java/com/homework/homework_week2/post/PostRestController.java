package com.homework.homework_week2.post;

import com.homework.homework_week2.post.dto.PostDto;
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

    @PostMapping("/posts")
    public boolean addPost(
            @AuthenticationPrincipal User userDetails,
            @ModelAttribute PostRequestDto postRequestDto
            ) {

        boolean result = postService.addPost(userDetails.getId(), postRequestDto);

        return result;
    }

    @GetMapping("/posts")
    public List<PostDto> getPosts() {
        return postService.getPosts();
    }

}
