package com.sanket.blogsapi.services.tokens.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sanket.blogsapi.services.tokens.TokenService;
import com.sanket.blogsapi.services.tokens.exceptions.InvalidTokenException;
import org.springframework.stereotype.Service;

@Service("jwtTokenService")
public class JwtTokenService implements TokenService {

    private final Algorithm algorithm;
    // TODO: configure this as environment variable
    private final String ISSUER = "blogs-api";
    // TODO: configure this as environment variable
    private final String secretKey = "this is signing key";
    // TODO: configure this as environment variable
    // 60 minutes validity
    private final Long tokenExpirationInMillis = 1000L * 60 * 60;

    public JwtTokenService() {
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    /**
     * Creates a JWT token for the given username
     *
     * @param username username of the user
     * @return JWT token string
     */
    @Override
    public String createAuthToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuer(ISSUER)
                .withIssuedAt(java.util.Date.from(java.time.Instant.now()))
                .withExpiresAt(java.util.Date.from(java.time.Instant.now().plusMillis(tokenExpirationInMillis)))
                .sign(algorithm);
    }

    /**
     * Retrieves the username from the JWT token string
     *
     * @param token JWT token string
     * @return username
     */
    @Override
    public String getUsernameFromToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
        try {
            DecodedJWT decodedToken = verifier.verify(token); // throws exception if token is invalid
            return decodedToken.getSubject();
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException(token);
        }
    }
}
