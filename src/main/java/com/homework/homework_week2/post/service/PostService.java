package com.homework.homework_week2.post.service;

import com.homework.homework_week2.common.FileManagerService;
import com.homework.homework_week2.post.domain.Post;
import com.homework.homework_week2.post.dto.PostDto;
import com.homework.homework_week2.post.dto.PostRequestDto;
import com.homework.homework_week2.post.repository.PostRepository;
import com.homework.homework_week2.user.domain.User;
import com.homework.homework_week2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final FileManagerService fileManagerService;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    /**
     * 게시물 생성
     * @param id
     * @param postRequestDto
     * @return
     */
    public boolean addPost(Long id, PostRequestDto postRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

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


    /**
     * 게시물 목록 조회
     * @return
     */
    public List<PostDto> getPosts() {
        List<Post> foundPosts = postRepository.findAll();

        List<PostDto> posts = new ArrayList<>();
        for (Post post : foundPosts) {
            PostDto postDto = PostDto.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .imageUrl(post.getImageUrl())
                    .createdAt(post.getCreatedAt())
                    .build();

            posts.add(postDto);
        }

        return posts;
    }

}
