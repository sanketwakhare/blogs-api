package com.sanket.blogsapi.comments.exceptions;

import com.sanket.blogsapi.comments.constants.CommentsErrorMessages;

import java.util.UUID;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException() {
        super(CommentsErrorMessages.COMMENT_NOT_FOUND);
    }

    public CommentNotFoundException(String message) {
        super(message);
    }

    public CommentNotFoundException(UUID commentId) {
        this(String.format(CommentsErrorMessages.COMMENT_WITH_ID_NOT_FOUND, commentId));
    }
}
