package com.sanket.blogsapi.articles;

import com.sanket.blogsapi.articles.exceptions.ArticleNotFoundException;
import com.sanket.blogsapi.services.slugs.SlugsService;
import com.sanket.blogsapi.users.UserEntity;
import com.sanket.blogsapi.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticlesService {

    private final ArticlesRepository articlesRepository;
    private final UsersService usersService;
    private final SlugsService slugsService;

    public ArticlesService(@Autowired ArticlesRepository articlesRepository,
                           @Autowired UsersService usersService,
                           @Autowired SlugsService slugsService) {
        this.articlesRepository = articlesRepository;
        this.usersService = usersService;
        this.slugsService = slugsService;
    }

    /**
     * Create an article
     *
     * @param article Article to be created
     * @param userId  User ID of the user creating the article
     * @return Created article
     */
    public ArticleEntity createArticle(ArticleEntity article, UUID userId) {
        // get author details
        UserEntity author = usersService.findById(userId);
        Set<UserEntity> authors = new HashSet<>();
        authors.add(author);

        // generate slug from title
        String slug = slugsService.generateSlug(article.getTitle());

        // create new article
        ArticleEntity newArticle = ArticleEntity.builder()
                .title(article.getTitle())
                .subtitle(article.getSubtitle())
                .body(article.getBody())
                .slug(slug)
                .authors(authors)
                .tags(article.getTags())
                .status(ArticleStatus.DRAFT)
                .build();

        // save article
        return articlesRepository.save(newArticle);
    }

    /**
     * Get an article by ID
     * Throws ArticleNotFoundException if article is not found
     *
     * @param id Article ID
     * @return Article
     */
    public ArticleEntity getArticleById(UUID id) {
        Optional<ArticleEntity> articleEntity = articlesRepository.findById(id);
        if (articleEntity.isEmpty()) {
            throw new ArticleNotFoundException(id);
        }
        return articleEntity.get();
    }

    /**
     * Publish an article
     *
     * @param id Article ID
     * @return Published article
     */
    public ArticleEntity publishArticle(UUID id) {
        ArticleEntity article = getArticleById(id);
        article.setStatus(ArticleStatus.PUBLISHED);
        return articlesRepository.save(article);
    }

    /**
     * Delete an article
     *
     * @param id Article ID
     * @return Deleted article
     */
    public ArticleEntity deleteArticle(UUID id) {
        ArticleEntity article = getArticleById(id);
        article.setStatus(ArticleStatus.DELETED);
        return articlesRepository.save(article);
    }

    /**
     * Move an article to draft
     *
     * @param id Article ID
     * @return drafted article
     */
    public ArticleEntity moveArticleToDraft(UUID id) {
        ArticleEntity article = getArticleById(id);
        article.setStatus(ArticleStatus.DRAFT);
        return articlesRepository.save(article);
    }

    /**
     * Get all articles by author ID
     *
     * @param authorId Author ID
     * @return List of articles
     */
    public List<ArticleEntity> getAllArticlesByAuthorId(UUID authorId) {
        UserEntity author = usersService.findById(authorId);
        return articlesRepository.findAllByAuthors(author);
    }

    /**
     * Update an article
     *
     * @param id            Article ID
     * @param articleEntity Article to be updated
     * @return Updated article
     */
    public ArticleEntity updateArticle(UUID id, ArticleEntity articleEntity) {
        ArticleEntity article = getArticleById(id);

        // by creating an object from builder, we are invoking validations defined at field level
        ArticleEntity newArticle = ArticleEntity.builder()
                .title(articleEntity.getTitle())
                .subtitle(articleEntity.getSubtitle())
                .body(articleEntity.getBody())
                .slug(slugsService.generateSlug(articleEntity.getTitle()))
                .authors(article.getAuthors())
                .status(article.getStatus())
                .tags(articleEntity.getTags())
                .build();

        // if object creation is successful, update the existing article
        article.setTitle(newArticle.getTitle());
        article.setSubtitle(newArticle.getSubtitle());
        article.setBody(newArticle.getBody());
        article.setSlug(newArticle.getSlug());
        // override the existing tags
        article.setTags(newArticle.getTags());

        // save the updated article
        return articlesRepository.save(article);
    }

    /**
     * Like an article
     *
     * @param articleId Article ID
     * @param userId    User ID
     * @return ArticleReactionType
     */
    public ArticleReactionType likeArticle(UUID articleId, UUID userId) {
        ArticleEntity article = getArticleById(articleId);
        UserEntity user = usersService.findById(userId);

        Set<UserEntity> likedByUsers = articlesRepository.findArticleLikesById(articleId);
        if (!likedByUsers.contains(user)) {
            likedByUsers.add(user);
            article.setLikedBy(likedByUsers);
            articlesRepository.save(article);
        }

        return ArticleReactionType.LIKED;
    }

    /**
     * Dislike an article
     *
     * @param articleId Article ID
     * @param userId    User ID
     * @return ArticleReactionType
     */
    public ArticleReactionType dislikeArticle(UUID articleId, UUID userId) {
        ArticleEntity article = getArticleById(articleId);
        UserEntity user = usersService.findById(userId);

        Set<UserEntity> likedByUsers = articlesRepository.findArticleLikesById(articleId);
        if (likedByUsers.contains(user)) {
            likedByUsers.remove(user);
            article.setLikedBy(likedByUsers);
            articlesRepository.save(article);
        }

        return ArticleReactionType.DISLIKED;
    }

    /**
     * Get all likes for an article
     *
     * @param articleId Article ID
     * @return Set of users who liked the article
     */
    public Set<UserEntity> getArticleLikes(UUID articleId) {
        ArticleEntity article = getArticleById(articleId);
        return articlesRepository.findArticleLikesById(article.getId());
    }
}
