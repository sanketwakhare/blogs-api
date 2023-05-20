package com.sanket.blogsapi.users.exceptions;

import com.sanket.blogsapi.users.constants.UsersErrorMessages;

public class PasswordLengthException extends RuntimeException {

    public PasswordLengthException() {
        super(UsersErrorMessages.PASSWORD_SIZE_CONSTRAINT_VIOLATION_ERROR);
    }
}
