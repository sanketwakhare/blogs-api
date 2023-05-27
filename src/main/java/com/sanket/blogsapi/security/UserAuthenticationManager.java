package com.sanket.blogsapi.security;

import com.sanket.blogsapi.roles.RoleEntity;
import com.sanket.blogsapi.roles.RolesService;
import com.sanket.blogsapi.services.tokens.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class is responsible for authenticating the user
 */
@Component(value = "userAuthenticationManager")
public class UserAuthenticationManager implements AuthenticationManager {

    private final TokenService tokenService;
    private final RolesService rolesService;

    public UserAuthenticationManager(@Autowired TokenService tokenService, RolesService rolesService) {
        this.tokenService = tokenService;
        this.rolesService = rolesService;
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
                // set username
                userAuthentication.setUsername(username);
                // set authorities
                Set<RoleEntity> authorities = rolesService.getAllRolesForUser(username);
                Set<GrantedAuthority> validAuthorities = new HashSet<>();
                authorities.forEach(roleEntity -> validAuthorities.add(new SimpleGrantedAuthority(roleEntity.getRole().getRole_name())));
                userAuthentication.setAuthorities(validAuthorities);
                return userAuthentication;
            }
        }
        return null;
    }
}
