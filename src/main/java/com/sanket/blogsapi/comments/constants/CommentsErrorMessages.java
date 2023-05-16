package com.sanket.blogsapi.comments.constants;

public class CommentsErrorMessages {
    public static final String COMMENT_NOT_FOUND = "Comment not found";
    public static final String COMMENT_WITH_ID_NOT_FOUND = "Comment with id %s not found";
    public static final String COMMENT_CREATION_FAILED = "Comment creation failed";
    public static final String COMMENT_UPDATE_FAILED = "Comment update failed";
    public static final String COMMENT_DELETE_FAILED = "Comment delete failed";
    public static final String COMMENT_WITH_ID_NOT_MODIFIABLE = "comment with id %s is not modifiable";
    public static final String COMMENT_TITLE_LENGTH_CONSTRAINT_VIOLATION_ERROR = "Title must be between 3 and 150 characters";
    public static final String COMMENT_BODY_LENGTH_CONSTRAINT_VIOLATION_ERROR = "Body must be between 10 and 1000 characters";
}
