package com.homework.homework_week2.post.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.homework.homework_week2.comment.domain.Comment;
import com.homework.homework_week2.likes.domain.Likes;
import com.homework.homework_week2.timestamp.Timestamped;
import com.homework.homework_week2.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 25)
    private String title;

    @Column(nullable = false, length = 3000)
    private String content;

    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "like_count")
    private Long likeCount;

    @Column
    private int template;

    // 연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OrderBy(value = "createdAt DESC")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes;

    @Builder
    public Post(String title, String content, int template, String imageUrl, User user) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.viewCount = 0L;
        this.user = user;
        this.template = template;
    }

    public void update(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public void updateViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public void updateLikesCount(Long likeCount) {
        this.likeCount = likeCount;
    }
}
