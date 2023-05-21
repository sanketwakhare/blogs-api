package com.sanket.blogsapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * This Filter class is responsible for authenticating the requests.
 * The request first goes through the Authentication converter to convert the request to an authentication object.
 * Then the authentication object is passed to the Authentication manager to authenticate the user.
 */
@Component(value = "userAuthenticationFilter")
public class UserAuthenticationFilter extends AuthenticationFilter {

    public UserAuthenticationFilter(@Autowired AuthenticationManager userAuthenticationManager,
                                    @Autowired AuthenticationConverter bearerTokenAuthenticationConverter) {
        super(userAuthenticationManager, bearerTokenAuthenticationConverter);

        /*
          The success handler to set the authentication object in the spring security context.
          This will help to directly access principal object in the controller
         */
        this.setSuccessHandler(((request, response, authentication) ->
                SecurityContextHolder.getContext().setAuthentication(authentication)));
    }
}
