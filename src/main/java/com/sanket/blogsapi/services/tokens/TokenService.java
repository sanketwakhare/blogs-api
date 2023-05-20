package com.sanket.blogsapi.services.tokens;

import org.springframework.stereotype.Service;

@Service("tokenService")
public interface TokenService {
    /**
     * Creates a token for the given username
     *
     * @param username username of the user
     * @return token string
     */
    String createAuthToken(String username);

    /**
     * Retrieves the username from the token string
     *
     * @param token token string
     * @return username
     */
    String getUsernameFromToken(String token);

    // String createRefreshToken(String username);
    // boolean validateToken(String token);
}
