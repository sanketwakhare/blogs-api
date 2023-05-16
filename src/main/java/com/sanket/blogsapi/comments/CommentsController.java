package com.sanket.blogsapi.comments;

import com.sanket.blogsapi.comments.constants.CommentsSuccessMessages;
import com.sanket.blogsapi.comments.dtos.CommentResponseDTO;
import com.sanket.blogsapi.comments.dtos.CommentsListResponseDTO;
import com.sanket.blogsapi.comments.dtos.CreateCommentRequestDTO;
import com.sanket.blogsapi.comments.dtos.UpdateCommentRequestDTO;
import com.sanket.blogsapi.common.dtos.ResponseDTO;
import com.sanket.blogsapi.services.tokens.TokensService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    private final CommentsService commentsService;
    private final ModelMapper modelMapper;
    private final TokensService tokensService;

    public CommentsController(@Autowired CommentsService commentsService,
                              @Autowired ModelMapper modelMapper,
                              @Autowired TokensService tokensService) {
        this.commentsService = commentsService;
        this.modelMapper = modelMapper;
        this.tokensService = tokensService;
    }

    /**
     * Create a new comment
     *
     * @param requestDTO Comment details
     * @return newly created comment
     */
    @PostMapping("")
    public ResponseEntity<CommentResponseDTO> createComment(
            @RequestBody CreateCommentRequestDTO requestDTO,
            @RequestHeader("Authorization") String bearerToken) {

        UUID commenterId = tokensService.getUserIdFromToken(bearerToken);
        UUID articleId = requestDTO.getArticleId();

        CommentEntity commentEntity = modelMapper.map(requestDTO, CommentEntity.class);
        CommentEntity comment = commentsService.createComment(commentEntity, commenterId, articleId);

        CommentResponseDTO responseDTO = modelMapper.map(comment, CommentResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Get all comments for an article
     *
     * @param articleId Article ID
     * @return List of comments
     */
    @GetMapping("/article/{articleId}")
    public ResponseEntity<CommentsListResponseDTO> getAllCommentsForArticle(
            @PathVariable UUID articleId) {

        List<CommentEntity> comments = commentsService.getAllCommentsForArticle(articleId);

        CommentsListResponseDTO responseDTO = new CommentsListResponseDTO();
        List<CommentResponseDTO> commentsList = new ArrayList<>();
        for (CommentEntity comment : comments) {
            commentsList.add(modelMapper.map(comment, CommentResponseDTO.class));
        }
        responseDTO.setComments(commentsList);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Update a comment
     *
     * @param commentId   Comment ID
     * @param requestDTO  Comment details
     * @param bearerToken Bearer token of the commenter
     * @return Updated comment
     */
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(
            @PathVariable("commentId") UUID commentId,
            @RequestBody UpdateCommentRequestDTO requestDTO,
            @RequestHeader("Authorization") String bearerToken) {

        UUID commenterId = tokensService.getUserIdFromToken(bearerToken);

        CommentEntity commentEntity = modelMapper.map(requestDTO, CommentEntity.class);
        CommentEntity comment = commentsService.updateComment(commentId, commentEntity, commenterId);

        CommentResponseDTO responseDTO = modelMapper.map(comment, CommentResponseDTO.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    /**
     * Get a comment by ID
     *
     * @param commentId Comment ID
     * @return Comment details
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> getCommentById(
            @PathVariable("commentId") UUID commentId) {

        CommentEntity comment = commentsService.findById(commentId);

        CommentResponseDTO responseDTO = modelMapper.map(comment, CommentResponseDTO.class);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Delete a comment
     *
     * @param commentId   Comment ID
     * @param bearerToken Bearer token of the commenter
     * @return Deleted comment
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDTO> deleteComment(
            @PathVariable("commentId") UUID commentId,
            @RequestHeader("Authorization") String bearerToken) {

        UUID commenterId = tokensService.getUserIdFromToken(bearerToken);

        commentsService.deleteComment(commentId, commenterId);

        ResponseDTO responseDTO = new ResponseDTO(CommentsSuccessMessages.COMMENT_DELETED);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }
}
