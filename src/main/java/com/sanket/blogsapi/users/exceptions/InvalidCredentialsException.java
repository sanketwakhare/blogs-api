package com.sanket.blogsapi.users.exceptions;

import com.sanket.blogsapi.users.constants.UsersErrorMessages;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super(UsersErrorMessages.INVALID_CREDENTIALS);
    }
}
