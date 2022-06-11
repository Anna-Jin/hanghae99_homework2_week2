package com.homework.homework_week2.post.repository;

import com.homework.homework_week2.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
