package com.sanket.blogsapi.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * This class is used to enable method level security
 * We can use @Secured, @PreAuthorize, @PostAuthorize annotations
 * to secure our methods
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class MethodSecurityConfig {
}

// This is deprecated usage of @EnableGlobalMethodSecurity
//@Configuration
//@EnableGlobalMethodSecurity(
//        prePostEnabled = true,
//        securedEnabled = true,
//        jsr250Enabled = true)
//public class MethodSecurityConfig
//        extends GlobalMethodSecurityConfiguration {
//}