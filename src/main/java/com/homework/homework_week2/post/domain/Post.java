package com.homework.homework_week2.post.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.homework.homework_week2.post.dto.PostDto;
import com.homework.homework_week2.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 25)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @Column(name = "view_count")
    private int viewCount;


    // 연관관계 매핑
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "USER_ID", nullable = false)
//    private User user;



//    @OrderBy(value = "createdAt DESC")
//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    private List<Comment> comments;

    @Builder
    public Post(String title, String content, String imageUrl, int viewCount, User user) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.viewCount = viewCount;
        this.user = user;
    }

    public void update(PostDto postDto) {
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
        this.imageUrl = postDto.getImageUrl();
        this.viewCount = postDto.getViewCount();
    }
}
