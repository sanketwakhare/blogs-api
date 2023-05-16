package com.sanket.blogsapi.users.exceptions;

import com.sanket.blogsapi.users.constants.UsersErrorMessages;

import java.util.UUID;

public class UserCannotUnfollowException extends RuntimeException {

    public UserCannotUnfollowException() {
        super(UsersErrorMessages.USER_CANNOT_UNFOLLOW);
    }

    public UserCannotUnfollowException(String message) {
        super(message);
    }

    public UserCannotUnfollowException(UUID userId, UUID userIdToUnfollow) {
        this(String.format(UsersErrorMessages.USER_CANNOT_UNFOLLOW_USER_ID_FOLLOWING_ID, userId, userIdToUnfollow));
    }
}
