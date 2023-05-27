package com.sanket.blogsapi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This is Authentication object represents the currently authenticated user
 */
public class UserAuthentication implements Authentication {

    private String username;
    private final String token;

    private Set<GrantedAuthority> authorities = new HashSet<>();

    public UserAuthentication(String token) {
        this.token = token;
    }

    /**
     * This method sets the username of the currently authenticated user
     *
     * @param username username of the currently authenticated user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method returns the roles of the currently authenticated user
     *
     * @return the roles of the currently authenticated user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * This method returns the credentials typically token of the currently authenticated user
     *
     * @return the credentials typically token of the currently authenticated user
     */
    @Override
    public String getCredentials() {
        return this.token;
    }

    /**
     * This method returns the additional details of the currently authenticated user
     *
     * @return the additional details of the currently authenticated user
     */
    @Override
    public Object getDetails() {
        // TODO: Implement this method
        return null;
    }

    /**
     * This method returns the name of the currently authenticated user
     *
     * @return the name of the currently authenticated user
     */
    @Override
    public String getPrincipal() {
        return this.username;
    }

    /**
     * This method returns the authentication status of the user
     *
     * @return <code>true</code> if the token should be trusted (which may result in an exception)
     * or <code>false</code> if the token should not be trusted
     */
    @Override
    public boolean isAuthenticated() {
        return !Objects.isNull(username);
    }

    /**
     * This method sets the authentication status of the user
     *
     * @param isAuthenticated <code>true</code> if the token should be trusted (which may
     *                        result in an exception) or <code>false</code> if the token should not be trusted
     * @throws IllegalArgumentException if an invalid authentication status is set (typically if the principal is null)
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (!isAuthenticated) {
            this.username = null;
        }
    }

    /**
     * This method returns the name of the currently authenticated user
     *
     * @return the name of the currently authenticated user
     */
    @Override
    public String getName() {
        return this.username;
    }
}
