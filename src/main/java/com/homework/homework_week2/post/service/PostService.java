package com.homework.homework_week2.post.service;

import com.homework.homework_week2.common.FileManagerService;
import com.homework.homework_week2.post.domain.Post;
import com.homework.homework_week2.post.dto.PostRequestDto;
import com.homework.homework_week2.post.repository.PostRepository;
import com.homework.homework_week2.user.domain.User;
import com.homework.homework_week2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final FileManagerService fileManagerService;

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public boolean addPost(Long userId, PostRequestDto postRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .imageUrl(
                        fileManagerService.savaFile(user.getEmail(), postRequestDto.getFile())
                )
                .user(user)
                .build();

        postRepository.save(post);

        return true;
    }
}
