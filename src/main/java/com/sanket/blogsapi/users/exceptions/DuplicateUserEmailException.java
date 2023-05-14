package com.sanket.blogsapi.users.exceptions;

import com.sanket.blogsapi.users.constants.UsersErrorMessages;

public class DuplicateUserEmailException extends RuntimeException {
    public DuplicateUserEmailException(String email) {
        super(String.format(UsersErrorMessages.DUPLICATE_USER_EMAIL, email));
    }
}
