package com.sanket.blogsapi.users.exceptions;

import com.sanket.blogsapi.users.constants.UsersErrorMessages;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super(String.format(UsersErrorMessages.USER_NOT_FOUND, id));
    }

    public UserNotFoundException(String usernameOrEmail) {
        super(String.format(UsersErrorMessages.USER_NOT_FOUND_BY_USERNAME_OR_EMAIL, usernameOrEmail));
    }
}
