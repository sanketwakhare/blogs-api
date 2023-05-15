package com.sanket.blogsapi.services.tokens.exceptions;

import com.sanket.blogsapi.services.tokens.constants.TokensErrorMessages;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super(TokensErrorMessages.INVALID_AUTHORIZATION_TOKEN);
    }

    public InvalidTokenException(String token) {
        super(String.format(TokensErrorMessages.INVALID_AUTHORIZATION_TOKEN_ID, token));
    }

}
