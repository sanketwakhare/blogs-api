package com.sanket.blogsapi.articles;

import com.sanket.blogsapi.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ArticlesRepository extends JpaRepository<ArticleEntity, UUID> {

//    @Query("SELECT a FROM articles a WHERE a.id = :id AND a.status <> 'DELETED'")
//    Optional<ArticleEntity> findByIdAndNotDeleted(UUID id);

    Optional<ArticleEntity> findById(UUID id);

    List<ArticleEntity> findAllByAuthors(UserEntity author);

    @Query("SELECT a.likedBy FROM articles a WHERE a.id =:articleId")
    Set<UserEntity> findArticleLikesById(UUID articleId);

}
