package com.sanket.blogsapi.services.tokens.exceptions;

import com.sanket.blogsapi.services.tokens.constants.TokensErrorConstants;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super(TokensErrorConstants.INVALID_AUTHORIZATION_TOKEN);
    }

    public InvalidTokenException(String token) {
        super(String.format(TokensErrorConstants.INVALID_AUTHORIZATION_TOKEN_ID, token));
    }

}
