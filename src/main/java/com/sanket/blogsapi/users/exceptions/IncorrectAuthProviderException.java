package com.sanket.blogsapi.users.exceptions;

public class IncorrectAuthProviderException extends RuntimeException {

    public IncorrectAuthProviderException(String authProvider) {
        super("Please use " + authProvider + " option to login");
    }
}
