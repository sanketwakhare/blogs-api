package com.sanket.blogsapi.users.exceptions;

import com.sanket.blogsapi.users.constants.UsersErrorMessages;

public class DuplicateUserNameException extends RuntimeException {
    public DuplicateUserNameException(String username) {
        super(String.format(UsersErrorMessages.DUPLICATE_USER_NAME, username));
    }
}
