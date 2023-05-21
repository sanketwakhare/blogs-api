package com.sanket.blogsapi.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for converting the request to an authentication object
 */
@Component(value = "bearerTokenAuthenticationConverter")
public class BearerTokenAuthenticationConverter implements AuthenticationConverter {

    /**
     * This method converts the request to an authentication object
     *
     * @param request the request object
     * @return authentication object
     */
    @Override
    public Authentication convert(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null) {
            String token = bearerToken.replace("Bearer ", "");
            return new UserAuthentication(token);
        }
        return null;
    }
}
