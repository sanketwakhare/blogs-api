package com.sanket.blogsapi.users.constants;

public class UsersErrorMessages {
    public static final String DUPLICATE_USER_EMAIL = "email %s already registered";
    public static final String DUPLICATE_USER_NAME = "username %s already registered";
    public static final String USER_NOT_FOUND = "User with id %s not found";
    public static final String USER_NOT_FOUND_BY_USERNAME_OR_EMAIL = "User %s not found";
    public static final String INVALID_CREDENTIALS = "Invalid email or password";
    public static final String INVALID_USER_EMAIL = "Invalid email";
    public static final String USERNAME_SIZE_CONSTRAINT_VIOLATION_ERROR = "Username must be between 3 and 30 characters";
    public static final String PASSWORD_SIZE_CONSTRAINT_VIOLATION_ERROR = "Password must be between 6 and 30 characters";

    /**
     * Follow/Unfollow user error messages
     */
    public static final String CANNOT_FOLLOW_ITSELF = "Users cannot follow itself";
    public static final String USER_ALREADY_FOLLOWED = "User is already followed, cannot be followed again";
    public static final String USER_CANNOT_FOLLOW = "User cannot follow";
    public static final String USER_CANNOT_FOLLOW_USER_ID_FOLLOWING_ID = "User with id %s cannot follow user with id %s";
    public static final String CANNOT_UNFOLLOW_ITSELF = "Users cannot unfollow itself";
    public static final String USER_NOT_FOLLOWED = "User is not followed, cannot be unfollowed";
    public static final String USER_CANNOT_UNFOLLOW = "User cannot unfollow";
    public static final String USER_CANNOT_UNFOLLOW_USER_ID_FOLLOWING_ID = "User with id %s cannot unfollow user with id %s";

}
