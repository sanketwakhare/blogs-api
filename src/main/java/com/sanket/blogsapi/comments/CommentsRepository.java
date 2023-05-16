package com.sanket.blogsapi.comments;

import com.sanket.blogsapi.articles.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, UUID> {

    @Override
    Page<CommentEntity> findAll(Pageable pageable);

    @Query("SELECT c FROM comments c WHERE c.article = :article")
    List<CommentEntity> findAllByArticle(ArticleEntity article);
}
