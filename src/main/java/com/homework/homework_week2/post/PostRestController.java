package com.homework.homework_week2.post;

import com.homework.homework_week2.post.dto.PostRequestDto;
import com.homework.homework_week2.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostRestController {

    private PostService postService;

    @PostMapping("/posts")
    public boolean addPost(@RequestBody PostRequestDto postRequestDto) {
        return false;
    }

}
