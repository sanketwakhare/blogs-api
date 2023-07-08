package com.sanket.blogsapi.security;

import com.sanket.blogsapi.roles.RolesEnum;
import com.sanket.blogsapi.security.oauth.CustomOAuth2UserService;
import com.sanket.blogsapi.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.sanket.blogsapi.security.oauth.OAuth2AuthenticationFailureHandler;
import com.sanket.blogsapi.security.oauth.OAuth2AuthenticationSuccessHandler;
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

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(@Autowired AuthenticationEntryPoint delegatedAuthenticationEntryPoint,
                          @Autowired AccessDeniedHandler restAccessDeniedHandler,
                          @Autowired AuthenticationFilter userAuthenticationFilter,
                          @Autowired OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
                          @Autowired OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler,
                          @Autowired HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository,
                          @Autowired CustomOAuth2UserService customOAuth2UserService) {
        this.delegatedAuthenticationEntryPoint = delegatedAuthenticationEntryPoint;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
        this.userAuthenticationFilter = userAuthenticationFilter;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // security config which allows all requests
//        http.csrf().disable();
//        http.authorizeHttpRequests().anyRequest().permitAll();
//        return http.build();

        // security config which allows only authenticated requests
        http.csrf().disable();
        // enable cors configuration
        http.cors();
        http.authorizeHttpRequests()

                // permit public urls
                .requestMatchers(HttpMethod.GET, "/articles/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/articles/search").permitAll()
                .requestMatchers(HttpMethod.GET, "/comments/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/signup", "/users/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/followers/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/followings/**").permitAll()

//                .requestMatchers(HttpMethod.GET, "/login/*").permitAll()

                // authenticate role specific requests
                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole(RolesEnum.ADMIN.name())

                // this configuration is not required as we are using @PreAuthorize annotation in the controller
                // .requestMatchers(HttpMethod.POST, "/roles/**").hasRole(RolesEnum.ADMIN.name())
                // .requestMatchers(HttpMethod.DELETE, "/roles/**").hasRole(RolesEnum.ADMIN.name())

                .requestMatchers(
                        "/error",
                        "/favicon.ico",
                        "/*/*.png",
                        "/*/*.gif",
                        "/*/*.svg",
                        "/*/*.jpg",
                        "/*/*.html",
                        "/*/*.css",
                        "/*/*.js").permitAll()
                .requestMatchers("/oauth/**","/auth/**", "/oauth2/**").permitAll()
                // authenticate all others requests
                .anyRequest().authenticated()
                .and()

                // configure exception handlers
                .exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler)
                .authenticationEntryPoint(delegatedAuthenticationEntryPoint)

                // oauth2 configuration
                .and()
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                        .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                        .and()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler);

        // add the user authentication filter before the anonymous authentication filter
        http.addFilterBefore(userAuthenticationFilter, AnonymousAuthenticationFilter.class);

        return http.build();
    }
}
