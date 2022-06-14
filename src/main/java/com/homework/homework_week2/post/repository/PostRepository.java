package com.homework.homework_week2.post.repository;

import com.homework.homework_week2.post.domain.Post;
import com.homework.homework_week2.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    boolean deletePostByUserAndId(User user, Long postId);
}
