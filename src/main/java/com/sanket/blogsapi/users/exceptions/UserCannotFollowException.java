package com.sanket.blogsapi.users.exceptions;

import com.sanket.blogsapi.users.constants.UsersErrorMessages;

import java.util.UUID;

public class UserCannotFollowException extends RuntimeException {

    public UserCannotFollowException() {
        super(UsersErrorMessages.USER_CANNOT_FOLLOW);
    }

    public UserCannotFollowException(String message) {
        super(message);
    }

    public UserCannotFollowException(UUID userId, UUID userIdToFollow) {
        super(String.format(UsersErrorMessages.USER_CANNOT_FOLLOW_USER_ID_FOLLOWING_ID, userId, userIdToFollow));
    }
}
