package com.sanket.blogsapi.comments.exceptions;

import com.sanket.blogsapi.common.dtos.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommentsExceptionHandler {

    @ExceptionHandler({
            CommentNotFoundException.class,
            CommentNotModifiableException.class,
    })
    public ResponseEntity<ErrorResponseDTO> handleCommentsBadRequestsException(Exception exception) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler({
            CommentCreationFailedException.class,
            CommentsDeletionFailedException.class,
            CommentsUpdateFailedException.class,
    })
    public ResponseEntity<ErrorResponseDTO> handleCommentsServerErrorException(Exception exception) {
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO(exception.getMessage()));
    }
}
