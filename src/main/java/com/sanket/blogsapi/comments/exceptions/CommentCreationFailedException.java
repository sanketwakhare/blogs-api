package com.sanket.blogsapi.comments.exceptions;

import com.sanket.blogsapi.comments.constants.CommentsErrorMessages;

public class CommentCreationFailedException extends RuntimeException {

    public CommentCreationFailedException() {
        super(CommentsErrorMessages.COMMENT_CREATION_FAILED);
    }

    public CommentCreationFailedException(String message) {
        super(message);
    }

}
