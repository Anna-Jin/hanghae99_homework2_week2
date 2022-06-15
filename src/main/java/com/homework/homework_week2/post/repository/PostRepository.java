package com.homework.homework_week2.post.repository;

import com.homework.homework_week2.post.domain.Post;
import com.homework.homework_week2.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByLikesCountDesc();
    void deletePostByUserAndId(User user, Long postId);
}
