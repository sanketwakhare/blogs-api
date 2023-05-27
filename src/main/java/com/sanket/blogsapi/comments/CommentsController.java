package com.sanket.blogsapi.comments;

import com.sanket.blogsapi.comments.constants.CommentsSuccessMessages;
import com.sanket.blogsapi.comments.dtos.CommentResponseDTO;
import com.sanket.blogsapi.comments.dtos.CommentsListResponseDTO;
import com.sanket.blogsapi.comments.dtos.CreateCommentRequestDTO;
import com.sanket.blogsapi.comments.dtos.UpdateCommentRequestDTO;
import com.sanket.blogsapi.common.dtos.ResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    private final CommentsService commentsService;
    private final ModelMapper modelMapper;

    public CommentsController(@Autowired CommentsService commentsService,
                              @Autowired ModelMapper modelMapper) {
        this.commentsService = commentsService;
        this.modelMapper = modelMapper;
    }

    /**
     * Create a new comment
     *
     * @param requestDTO Comment details
     * @return newly created comment
     */
    @PostMapping("")
    // allow only admins and logged-in user to create a comment
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.equals(#username)")
    public ResponseEntity<CommentResponseDTO> createComment(
            @RequestBody CreateCommentRequestDTO requestDTO,
            @AuthenticationPrincipal String username) {

        UUID articleId = requestDTO.getArticleId();

        CommentEntity commentEntity = modelMapper.map(requestDTO, CommentEntity.class);
        CommentEntity comment = commentsService.createComment(commentEntity, username, articleId);

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
     * @param commentId  Comment ID
     * @param requestDTO Comment details
     * @param username   username of the commenter
     * @return Updated comment
     */
    @PatchMapping("/{commentId}")
    // allow only admins and comment author to update a comment
    @PreAuthorize("hasRole('ADMIN') or @commentsService.findById(#commentId).author.username.equals(#username)")
    public ResponseEntity<CommentResponseDTO> updateComment(
            @PathVariable("commentId") UUID commentId,
            @RequestBody UpdateCommentRequestDTO requestDTO,
            @AuthenticationPrincipal String username) {

        CommentEntity commentEntity = modelMapper.map(requestDTO, CommentEntity.class);
        CommentEntity comment = commentsService.updateComment(commentId, commentEntity, username);

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
     * @param commentId Comment ID
     * @param username  username of the commenter
     * @return Deleted comment
     */
    @DeleteMapping("/{commentId}")
    // allow only admins and comment author to delete a comment
    @PreAuthorize("hasRole('ADMIN') or @commentsService.findById(#commentId).author.username.equals(#username)")
    public ResponseEntity<ResponseDTO> deleteComment(
            @PathVariable("commentId") UUID commentId,
            @AuthenticationPrincipal String username) {

        commentsService.deleteComment(commentId, username);

        ResponseDTO responseDTO = new ResponseDTO(CommentsSuccessMessages.COMMENT_DELETED);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }
}
