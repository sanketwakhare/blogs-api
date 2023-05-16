package com.sanket.blogsapi.comments.exceptions;

import com.sanket.blogsapi.comments.constants.CommentsErrorMessages;

import java.util.UUID;

public class CommentNotModifiableException extends RuntimeException {
    public CommentNotModifiableException(UUID commentId) {
        super(String.format(CommentsErrorMessages.COMMENT_WITH_ID_NOT_MODIFIABLE, commentId));
    }
}
