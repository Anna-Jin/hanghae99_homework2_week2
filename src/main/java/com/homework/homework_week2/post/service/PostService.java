package com.homework.homework_week2.post.service;

import com.homework.homework_week2.comment.dto.CommentResponseDto;
import com.homework.homework_week2.common.S3UploadManager;
import com.homework.homework_week2.likes.repository.LikesRepository;
import com.homework.homework_week2.post.domain.Post;
import com.homework.homework_week2.post.dto.PostRequestDto;
import com.homework.homework_week2.post.dto.PostResponseDto;
import com.homework.homework_week2.post.repository.PostRepository;
import com.homework.homework_week2.user.domain.User;
import com.homework.homework_week2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final S3UploadManager s3UploadManager;


    /**
     * 게시물 생성
     * @param userDetails
     * @param postRequestDto
     * @return
     */
    public void addPost(User userDetails, PostRequestDto postRequestDto) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .template(postRequestDto.getTemplate())
                .imageUrl(s3UploadManager.uploadFile(postRequestDto.getImage()))
                .user(user)
                .build();

        postRepository.save(post);
    }

    /**
     * 게시물 목록 조회 By user
     * @return
     */
    public List<PostResponseDto> getPostsByUser(User userDetails, Pageable pageable) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        List<Post> foundPosts = postRepository.findAllByOrderByLikeCountDesc(pageable);

        List<PostResponseDto> posts = new ArrayList<>();

        // entity -> dto
        for (Post post : foundPosts) {
            // 좋아요 개수 업데이트
            Long likesCount = likesRepository.countLikesByPost(post);
            post.updateLikesCount(likesCount);

            PostResponseDto postResponseDto = mapEntityToDto(user, post, likesCount);

            posts.add(postResponseDto);
        }

        return posts;
    }

    /**
     * 게시물 목록 조회 without user
     * @return
     */
    public List<PostResponseDto> getPosts(Pageable pageable) {
        List<Post> foundPosts = postRepository.findAllByOrderByLikeCountDesc(pageable);

        List<PostResponseDto> posts = new ArrayList<>();

        // entity -> dto
        for (Post post : foundPosts) {
            // 좋아요 개수 업데이트
            Long likesCount = likesRepository.countLikesByPost(post);
            post.updateLikesCount(likesCount);

            // entity -> dto without likeByMe
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .template(post.getTemplate())
                    .imageUrl(post.getImageUrl())
                    .nickname(post.getUser().getNickname())
                    .email(post.getUser().getEmail())
                    .comments(post.getComments().stream()
                            .map(CommentResponseDto::new)
                            .collect(Collectors.toList()))
                    .viewCount(post.getViewCount())
                    .likeCount(likesCount)
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

        // 좋아요 개수 업데이트
        Long likesCount = likesRepository.countLikesByPost(post);
        post.updateLikesCount(likesCount);

        // entity -> dto
        return mapEntityToDto(user, post, likesCount);
    }

    /**
     * 게시물 수정
     * @param postId
     * @param postRequestDto
     * @return
     */
    public void updatePost(User userDetails, Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당하는 게시물이 존재하지 않습니다."));

        if (userDetails.getId() != post.getUser().getId()) {
            throw new IllegalArgumentException("자신의 게시글만 수정 가능합니다.");
        }

        // 게시글 수정
        // 기존에 있던 이미지를 삭제하고 새롭게 추가해야함
        s3UploadManager.deleteFile(post.getImageUrl());

        post.update(postRequestDto.getTitle(),
                postRequestDto.getContent(),
                s3UploadManager.uploadFile(postRequestDto.getImage()));
    }

    /**
     * 게시글 삭제
     * @param userDetails
     * @param postId
     * @return
     */
    public void deletePost(User userDetails, Long postId) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 존재하지 않습니다."));

        // 게시물 삭제 전에 s3에 있는 이미지부터 삭제해야함
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당하는 게시물이 존재하지 않습니다."));
        s3UploadManager.deleteFile(post.getImageUrl());

        // 게시물 삭제
        if (user == post.getUser()) {
            postRepository.deleteById(postId);
        }
    }


    public PostResponseDto mapEntityToDto(User user, Post post, Long likesCount) {

        // entity -> dto
        PostResponseDto postResponseDto = PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .template(post.getTemplate())
                .imageUrl(post.getImageUrl())
                .nickname(post.getUser().getNickname())
                .email(post.getUser().getEmail())
                .comments(post.getComments().stream()
                        .map(CommentResponseDto::new)
                        .collect(Collectors.toList()))
                .viewCount(post.getViewCount())
                .likeByMe(likesRepository.existsLikesByUserAndPost(user, post))
                .likeCount(likesCount)
                .createdAt(post.getCreatedAt())
                .build();

        return postResponseDto;
    }
}
