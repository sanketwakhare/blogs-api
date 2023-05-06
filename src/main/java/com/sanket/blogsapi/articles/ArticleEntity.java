package com.sanket.blogsapi.articles;

import com.sanket.blogsapi.common.BaseEntity;
import com.sanket.blogsapi.users.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;

@Getter
@Setter
@Entity(name = "articles")
public class ArticleEntity extends BaseEntity {

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "slug", nullable = false, length = 100)
    private String slug;

    @Column(name = "subtitle", length = 150)
    private String subtitle;

    @Column(name = "body", nullable = false, length = 3000)
    private String body;

//    // if only single author is allowed
//    @ManyToOne
//    private UserEntity author;

    @ManyToMany
    @JoinTable(
            name = "article_authors",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<UserEntity> authors;

    @ManyToMany
    @JoinTable(
            name = "article_likes",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private List<UserEntity> likedBy;
}
