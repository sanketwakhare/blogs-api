package com.sanket.blogsapi.articles;

import com.sanket.blogsapi.articles.constants.ArticlesErrorMessages;
import com.sanket.blogsapi.common.BaseEntity;
import com.sanket.blogsapi.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;

@Getter
@Setter
@Entity(name = "articles")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ArticleEntity extends BaseEntity {

    @NonNull
    @Size(min = 10, max = 150, message = ArticlesErrorMessages.TITLE_SIZE_CONSTRAINT_VIOLATION_ERROR)
    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @NonNull
    @Column(name = "slug", nullable = false, length = 100)
    private String slug;

    @Column(name = "subtitle", length = 150)
    private String subtitle;

    @NonNull
    @Column(name = "body", nullable = false, length = 3000)
    private String body;

//    // if only single author is allowed
//    @ManyToOne
//    private UserEntity author;

    @ManyToMany(targetEntity = UserEntity.class)
    @JoinTable(
            name = "article_authors",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<UserEntity> authors;

    @ManyToMany(targetEntity = UserEntity.class)
    @JoinTable(
            name = "article_likes",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private List<UserEntity> likedBy;

//    // TODO: see how to implement this (without making other tables)
//    @ManyToMany
//    @Column(name = "tags", length = 100)
//    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
//    private Set<String> tags = new HashSet<>();

}
