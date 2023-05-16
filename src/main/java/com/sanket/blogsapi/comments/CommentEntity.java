package com.sanket.blogsapi.comments;

import com.sanket.blogsapi.articles.ArticleEntity;
import com.sanket.blogsapi.common.BaseEntity;
import com.sanket.blogsapi.users.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity extends BaseEntity {

    @NonNull
    @Size(min = 3, max = 150, message = "Title must be between 3 and 150 characters")
    @Column(name = "title", length = 150)
    private String title;

    @NonNull
    @Size(min = 10, max = 1000, message = "Body must be between 10 and 1000 characters")
    @Column(name = "body", nullable = false, length = 1000)
    private String body;

    @NonNull
    @ManyToOne
    private UserEntity author;

    @NonNull
    @ManyToOne
    private ArticleEntity article;
}
