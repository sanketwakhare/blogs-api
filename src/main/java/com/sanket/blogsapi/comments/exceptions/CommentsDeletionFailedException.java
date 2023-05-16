package com.sanket.blogsapi.comments.exceptions;

import com.sanket.blogsapi.comments.constants.CommentsErrorMessages;

public class CommentsDeletionFailedException extends RuntimeException {
    public CommentsDeletionFailedException() {
        super(CommentsErrorMessages.COMMENT_DELETE_FAILED);
    }

    public CommentsDeletionFailedException(String message) {
        super(message);
    }
}
