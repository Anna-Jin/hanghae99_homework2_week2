package com.homework.homework_week2.comment.repository;

import com.homework.homework_week2.comment.domain.Comment;
import com.homework.homework_week2.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentByUserAndId(User user, Long id);
    void deleteCommentByUserAndId(User user, Long id);
}
