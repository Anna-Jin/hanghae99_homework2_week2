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
@Transactional
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    /**
     * 댓글 등록
     * @param userDetails
     * @param postId
     * @param commentRequestDto
     * @return
     */
    public void addComment(User userDetails, Long postId, CommentRequestDto commentRequestDto) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .user(user)
                .post(post)
                .build();

        commentRepository.save(comment);
    }

    /**
     * 댓글 수정
     * @param commentId
     * @param commentRequestDto
     * @return
     */
    @Transactional
    public void updateComment(User userDetails, Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (userDetails.getId() != comment.getUser().getId()) {
            throw new IllegalArgumentException("자신의 댓글만 수정 가능합니다.");
        }

        comment.update(commentRequestDto.getContent());
    }

    /**
     * 댓글 삭제
     * @param commentId
     * @return
     */
    public void deleteComment(User userDetails, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (userDetails.getId() != comment.getUser().getId()) {
            throw new IllegalArgumentException("자신의 댓글만 삭제 가능합니다.");
        }

        commentRepository.deleteById(commentId);
    }
}
