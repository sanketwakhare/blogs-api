package com.sanket.blogsapi.services.tokens;

import com.sanket.blogsapi.services.tokens.exceptions.InvalidTokenException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class TokensService {

    public UUID getUserIdFromToken(String bearerToken) {
        try {
            // read Bearer token
            String userToken = bearerToken.split("Bearer ")[1];
            return UUID.fromString(userToken);
        } catch(Exception e) {
            throw new InvalidTokenException(bearerToken);
        }
    }

}
