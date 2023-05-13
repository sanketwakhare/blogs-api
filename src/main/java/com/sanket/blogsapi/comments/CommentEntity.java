package com.sanket.blogsapi.comments;

import com.sanket.blogsapi.articles.ArticleEntity;
import com.sanket.blogsapi.common.BaseEntity;
import com.sanket.blogsapi.users.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Entity(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity extends BaseEntity {

    @Column(name = "title", length = 150)
    private String title;

    @Column(name = "body", nullable = false, length = 1000)
    private String body;

    @ManyToOne
    private UserEntity author;

    @ManyToOne
    private ArticleEntity article;
}
