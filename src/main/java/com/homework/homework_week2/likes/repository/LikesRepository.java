package com.homework.homework_week2.likes.repository;

import com.homework.homework_week2.likes.domain.Likes;
import com.homework.homework_week2.post.domain.Post;
import com.homework.homework_week2.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    boolean countLikesByUserAndPost(User user, Post post);
    void deleteByUserAndPost(User user, Post post);

    Long countLikesByPost(Post post);
}
