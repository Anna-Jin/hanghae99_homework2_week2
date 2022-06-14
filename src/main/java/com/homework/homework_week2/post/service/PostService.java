package com.homework.homework_week2.post.service;

import com.homework.homework_week2.comment.dto.CommentResponseDto;
import com.homework.homework_week2.common.FileManagerService;
import com.homework.homework_week2.exception.errorCode.CustomErrorCode;
import com.homework.homework_week2.likes.repository.LikesRepository;
import com.homework.homework_week2.post.domain.Post;
import com.homework.homework_week2.post.dto.PostResponseDto;
import com.homework.homework_week2.post.dto.PostRequestDto;
import com.homework.homework_week2.post.repository.PostRepository;
import com.homework.homework_week2.user.domain.User;
import com.homework.homework_week2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final FileManagerService fileManagerService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;


    /**
     * 게시물 생성
     * @param userDetails
     * @param postRequestDto
     * @return
     */
    public boolean addPost(User userDetails, PostRequestDto postRequestDto) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

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
    public List<PostResponseDto> getPosts(User userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        List<Post> foundPosts = postRepository.findAll();

        List<PostResponseDto> posts = new ArrayList<>();

        // entity -> dto
        for (Post post : foundPosts) {
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .imageUrl(post.getImageUrl())
                    .comments(post.getComments().stream()
                            .map(CommentResponseDto::new)
                            .collect(Collectors.toList()))
                    .viewCount(post.getViewCount())
                    .isLike(likesRepository.countLikesByUserAndPost(user, post))
                    .likeCount(likesRepository.countLikesByPost(post))
                    .createdAt(post.getCreatedAt())
                    .build();

            posts.add(postResponseDto);
        }

        return posts;
    }

    /**
     * 게시물 단건 조회
     * @param postId
     * @return
     */
    public PostResponseDto getPost(User userDetails, Long postId) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당하는 게시물이 존재하지 않습니다."));

        // view count 올리기
        post.updateViewCount(post.getViewCount() + 1L);

        // entity -> dto
        PostResponseDto postResponseDto = PostResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .comments(post.getComments().stream()
                        .map(CommentResponseDto::new)
                        .collect(Collectors.toList()))
                .viewCount(post.getViewCount())
                .isLike(likesRepository.countLikesByUserAndPost(user, post))
                .likeCount(likesRepository.countLikesByPost(post))
                .createdAt(post.getCreatedAt())
                .build();

        return postResponseDto;
    }

    /**
     * 게시물 수정
     * @param postId
     * @param postRequestDto
     * @param userDetails
     * @return
     */
    public boolean updatePost(Long postId, PostRequestDto postRequestDto, User userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당하는 게시물이 존재하지 않습니다."));

        // 게시글 이미지 수정
        String imageUrl = post.getImageUrl();

        String imagePath = null;
        if (postRequestDto.getFile().getOriginalFilename().isBlank()) {
            throw new RuntimeException(CustomErrorCode.NULL_FILE.getMessage());
        } else {
            imagePath = fileManagerService.savaFile(userDetails.getEmail(), postRequestDto.getFile());

            if (imageUrl != null && imagePath != null) {
                fileManagerService.deleteFile(imageUrl);
            }
        }

        // 게시글 수정
        post.update(postRequestDto.getTitle(), postRequestDto.getContent(), imagePath);

        return true;
    }

    /**
     * 게시글 삭제
     * @param userDetails
     * @param postId
     * @return
     */
    @Transactional
    public boolean deletePost(User userDetails, Long postId) {
        // 게시글 이미지 삭제
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당하는 게시물이 존재하지 않습니다."));

        String imagePath = post.getImageUrl();
        if (imagePath != null) {
            fileManagerService.deleteFile(imagePath);
        }

        // 글 삭제
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 존재하지 않습니다."));
        postRepository.deletePostByUserAndId(user, postId);
        return true;
    }
}
