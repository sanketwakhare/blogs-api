package com.sanket.blogsapi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;

public class UserAuthentication implements Authentication {

    private String username;
    private final String token;

    public void setUsername(String username) {
        this.username = username;
    }

    public UserAuthentication(String token) {
        this.token = token;
    }

    /**
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String getCredentials() {
        return this.token;
    }

    /**
     * @return
     */
    @Override
    public Object getDetails() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String getPrincipal() {
        return this.username;
    }

    /**
     * @return
     */
    @Override
    public boolean isAuthenticated() {
        return !Objects.isNull(username);
    }

    /**
     * @param isAuthenticated <code>true</code> if the token should be trusted (which may
     *                        result in an exception) or <code>false</code> if the token should not be trusted
     * @throws IllegalArgumentException
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (!isAuthenticated) {
            this.username = null;
        }
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return this.username;
    }
}
