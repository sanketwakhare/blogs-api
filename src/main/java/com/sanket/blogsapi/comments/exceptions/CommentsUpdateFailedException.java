package com.sanket.blogsapi.comments.exceptions;

import com.sanket.blogsapi.comments.constants.CommentsErrorMessages;

public class CommentsUpdateFailedException extends RuntimeException {

    public CommentsUpdateFailedException() {
        super(CommentsErrorMessages.COMMENT_UPDATE_FAILED);
    }

    public CommentsUpdateFailedException(String message) {
        super(message);
    }

}
