package com.homework.homework_week2.comment.service;

import com.homework.homework_week2.comment.domain.Comment;
import com.homework.homework_week2.comment.repository.CommentRepository;
import com.homework.homework_week2.post.domain.Post;
import com.homework.homework_week2.comment.dto.CommentRequestDto;
import com.homework.homework_week2.post.repository.PostRepository;
import com.homework.homework_week2.user.domain.User;
import com.homework.homework_week2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;


    public boolean addComment(User userDetails, Long postId, CommentRequestDto commentRequestDto) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .user(user)
                .post(post)
                .build();

        commentRepository.save(comment);

        return true;
    }

    @Transactional
    public boolean updateComment(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        comment.update(commentRequestDto.getContent());

        return true;
    }
}
