package com.homework.homework_week2.likes.domain;

import com.homework.homework_week2.post.domain.Post;
import com.homework.homework_week2.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    public Likes(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
