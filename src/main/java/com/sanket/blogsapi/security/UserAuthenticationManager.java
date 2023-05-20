package com.sanket.blogsapi.security;

import com.sanket.blogsapi.services.tokens.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * This class is responsible for authenticating the user
 */
@Component(value = "userAuthenticationManager")
public class UserAuthenticationManager implements AuthenticationManager {

    private final TokenService tokenService;

    public UserAuthenticationManager(@Autowired TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * This method authenticates the user
     *
     * @param authentication the authentication request object
     * @return authentication object
     * @throws AuthenticationException if the authentication fails
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UserAuthentication userAuthentication) {
            String username = tokenService.getUsernameFromToken(userAuthentication.getCredentials());
            if (!Objects.isNull(username)) {
                userAuthentication.setUsername(username);
                userAuthentication.setAuthenticated(true);
                return userAuthentication;
            }
        }
        return null;
    }
}
