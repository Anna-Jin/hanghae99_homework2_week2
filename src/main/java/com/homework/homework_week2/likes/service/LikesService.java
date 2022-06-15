package com.homework.homework_week2.likes.service;

import com.homework.homework_week2.likes.domain.Likes;
import com.homework.homework_week2.likes.repository.LikesRepository;
import com.homework.homework_week2.post.domain.Post;
import com.homework.homework_week2.post.repository.PostRepository;
import com.homework.homework_week2.user.domain.User;
import com.homework.homework_week2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;

    /**
     * 좋아요 등록
     * @param userDetails
     * @param postId
     * @return
     */
    public boolean addlikes(User userDetails, Long postId) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        boolean isLikesExist = likesRepository.existsLikesByUserAndPost(user, post);
        if (!isLikesExist) {
            likesRepository.save(new Likes(user, post));
        }

        return true;
    }

    /**
     * 좋아요 취소
     * @param userDetails
     * @param postId
     * @return
     */
    @Transactional
    public boolean deletelikes(User userDetails, Long postId) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        boolean isLikesExist = likesRepository.existsLikesByUserAndPost(user, post);
        if (isLikesExist) {
            likesRepository.deleteByUserAndPost(user, post);
        }

        return true;
    }
}
