package com.sanket.blogsapi.security;

import com.sanket.blogsapi.services.tokens.TokenService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Component;

@Component(value = "userAuthenticationFilter")
public class UserAuthenticationFilter extends AuthenticationFilter {

    public UserAuthenticationFilter(TokenService tokenService) {
        super(new UserAuthenticationManager(tokenService), new BearerTokenAuthenticationConverter());

        // the success handler to set the authentication object in the spring security context
        // this will help to directly access principal object in the controller
        this.setSuccessHandler(((request, response, authentication) ->
                SecurityContextHolder.getContext().setAuthentication(authentication)));
    }
}
