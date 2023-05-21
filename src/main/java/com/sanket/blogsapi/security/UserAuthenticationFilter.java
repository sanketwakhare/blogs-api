package com.sanket.blogsapi.security;

import com.sanket.blogsapi.services.tokens.TokenService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFilter;

/**
 * This Filter class is responsible for authenticating the requests.
 * The request first goes through the Authentication converter to convert the request to an authentication object.
 * Then the authentication object is passed to the Authentication manager to authenticate the user.
 */
public class UserAuthenticationFilter extends AuthenticationFilter {

    public UserAuthenticationFilter(TokenService tokenService) {
        super(new UserAuthenticationManager(tokenService), new BearerTokenAuthenticationConverter());

        /*
          The success handler to set the authentication object in the spring security context.
          This will help to directly access principal object in the controller
         */
        this.setSuccessHandler(((request, response, authentication) ->
                SecurityContextHolder.getContext().setAuthentication(authentication)));
    }
}
