package com.sanket.blogsapi.articles.exceptions;

public class UnauthorizedUserException extends RuntimeException {

    public UnauthorizedUserException(String message) {
        super(message);
    }
}
