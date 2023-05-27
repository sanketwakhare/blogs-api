package com.sanket.blogsapi.security;

import com.sanket.blogsapi.roles.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;

/**
 * This class is responsible for configuring the spring security
 */
@Configuration
@EnableWebSecurity // to enable web security
public class SecurityConfig {

    private final AuthenticationEntryPoint delegatedAuthenticationEntryPoint;

    private final AccessDeniedHandler restAccessDeniedHandler;

    private final AuthenticationFilter userAuthenticationFilter;

    public SecurityConfig(@Autowired AuthenticationEntryPoint delegatedAuthenticationEntryPoint,
                          @Autowired AccessDeniedHandler restAccessDeniedHandler,
                          @Autowired AuthenticationFilter userAuthenticationFilter) {
        this.delegatedAuthenticationEntryPoint = delegatedAuthenticationEntryPoint;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
        this.userAuthenticationFilter = userAuthenticationFilter;
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
                .requestMatchers(HttpMethod.GET, "/users/followers/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/followings/**").permitAll()

                // authenticate role specific requests
                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole(RolesEnum.ADMIN.name())

                // this configuration is not required as we are using @PreAuthorize annotation in the controller
                // .requestMatchers(HttpMethod.POST, "/roles/**").hasRole(RolesEnum.ADMIN.name())
                // .requestMatchers(HttpMethod.DELETE, "/roles/**").hasRole(RolesEnum.ADMIN.name())

                // authenticate all others requests
                .anyRequest().authenticated()
                .and()

                // configure exception handlers
                .exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler)
                .authenticationEntryPoint(delegatedAuthenticationEntryPoint);

        // add the user authentication filter before the anonymous authentication filter
        http.addFilterBefore(userAuthenticationFilter, AnonymousAuthenticationFilter.class);

        return http.build();
    }
}
