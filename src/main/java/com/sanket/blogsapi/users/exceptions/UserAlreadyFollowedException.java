package com.sanket.blogsapi.users.exceptions;

import com.sanket.blogsapi.users.constants.UsersErrorMessages;

public class UserAlreadyFollowedException extends RuntimeException {

    public UserAlreadyFollowedException() {
        super(UsersErrorMessages.USER_ALREADY_FOLLOWED);
    }

    public UserAlreadyFollowedException(String message) {
        super(message);
    }

}
