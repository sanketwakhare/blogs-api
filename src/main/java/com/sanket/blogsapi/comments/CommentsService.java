package com.sanket.blogsapi.comments;

import com.sanket.blogsapi.articles.ArticleEntity;
import com.sanket.blogsapi.articles.ArticlesService;
import com.sanket.blogsapi.articles.exceptions.ArticleNotFoundException;
import com.sanket.blogsapi.comments.exceptions.*;
import com.sanket.blogsapi.users.UserEntity;
import com.sanket.blogsapi.users.UsersService;
import com.sanket.blogsapi.users.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final UsersService usersService;
    private final ArticlesService articlesService;

    public CommentsService(@Autowired CommentsRepository commentsRepository,
                           @Autowired UsersService usersService,
                           @Autowired ArticlesService articlesService) {
        this.commentsRepository = commentsRepository;
        this.usersService = usersService;
        this.articlesService = articlesService;
    }

    /**
     * Create a comment
     *
     * @param commentEntity Comment details
     * @param commenterName Username of the commenter (currently logged-in user)
     * @param articleId     Article ID of the article
     * @return newly created comment
     * @throws ArticleNotFoundException if article is not found
     * @throws UserNotFoundException    if user is not found
     */
    public CommentEntity createComment(CommentEntity commentEntity, String commenterName, UUID articleId) {

        UserEntity commenter = usersService.findByUsername(commenterName);
        ArticleEntity article = articlesService.getArticleById(articleId);

        CommentEntity newComment = CommentEntity.builder()
                .title(commentEntity.getTitle())
                .body(commentEntity.getBody())
                .author(commenter)
                .article(article)
                .build();

        try {
            return commentsRepository.save(newComment);
        } catch (Exception e) {
            throw new CommentCreationFailedException();
        }
    }

    /**
     * Get all comments for an article
     *
     * @param articleId Article ID of the article
     * @return List of comments
     * @throws ArticleNotFoundException if article is not found
     */
    public List<CommentEntity> getAllCommentsForArticle(UUID articleId) {
        ArticleEntity article = articlesService.getArticleById(articleId);
        return commentsRepository.findAllByArticle(article);
    }

    /**
     * Update a comment with body and title
     *
     * @param commentId     comment id
     * @param commentEntity comment entity
     * @param commenterName commenter username (currently logged-in user)
     * @return updated comment
     * @throws CommentNotFoundException      if comment is not found
     * @throws CommentNotModifiableException if comment is not modifiable
     * @throws UserNotFoundException         if user is not found
     */
    public CommentEntity updateComment(UUID commentId, CommentEntity commentEntity, String commenterName) {
        // check if comment exists
        CommentEntity comment = findById(commentId);
        UserEntity commenter = usersService.findByUsername(commenterName);

        // only comment author can modify the comment
        if (commenter.getId() != comment.getAuthor().getId()) {
            throw new CommentNotModifiableException(commentId);
        }

        // use builder to invoke validations
        CommentEntity updatedComment = CommentEntity.builder().title(commentEntity.getTitle())
                .body(commentEntity.getBody())
                .author(commenter)
                .article(comment.getArticle())
                .build();

        // set the values to the existing comment
        comment.setTitle(updatedComment.getTitle());
        comment.setBody(updatedComment.getBody());

        try {
            return commentsRepository.save(comment);
        } catch (Exception e) {
            throw new CommentsUpdateFailedException();
        }
    }

    /**
     * Find a comment by ID
     *
     * @param commentId Comment ID
     * @return Comment
     * @throws CommentNotFoundException if comment is not found
     */
    public CommentEntity findById(UUID commentId) {
        Optional<CommentEntity> comment = commentsRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new CommentNotFoundException(commentId);
        }
        return comment.get();
    }

    /**
     * Delete a comment by id
     *
     * @param commentId     comment id
     * @param commenterName username of the commenter (currently logged-in user)
     * @throws CommentNotFoundException if comment is not found
     */
    public void deleteComment(UUID commentId, String commenterName) {
        CommentEntity comment = findById(commentId);
        UserEntity commenter = usersService.findByUsername(commenterName);

        // only comment author can delete the comment
        if (commenter.getId() != comment.getAuthor().getId()) {
            throw new CommentNotModifiableException(commentId);
        }

        try {
            commentsRepository.delete(comment);
        } catch (Exception e) {
            throw new CommentsDeletionFailedException();
        }
    }
}
