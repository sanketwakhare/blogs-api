package com.sanket.blogsapi.security;

import com.sanket.blogsapi.services.tokens.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

/**
 * This class is responsible for configuring the spring security
 */
@Configuration
@EnableWebSecurity // to enable web security
public class SecurityConfig {

    private final TokenService tokenService;

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // security config which allows all requests
//        http.csrf().disable();
//        http.authorizeHttpRequests().anyRequest().permitAll();
//        return http.build();

        // security config which allows only authenticated requests
        http.csrf().disable();
        http.authorizeHttpRequests()
                // permit public urls
                .requestMatchers(HttpMethod.GET, "/articles/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/articles/search").permitAll()
                .requestMatchers(HttpMethod.GET, "/comments/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/signup", "/users/login").permitAll()
                // authenticate all others requests
                .anyRequest().authenticated();
        // add the user authentication filter before the anonymous authentication filter
        http.addFilterBefore(new UserAuthenticationFilter(tokenService), AnonymousAuthenticationFilter.class);
        return http.build();
    }
}
