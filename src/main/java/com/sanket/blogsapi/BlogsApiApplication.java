package com.sanket.blogsapi;

import com.sanket.blogsapi.services.jwt.JwtTokenService;
import com.sanket.blogsapi.services.tokens.TokenService;
import com.sanket.blogsapi.services.tokens.sst.UserTokenRepository;
import com.sanket.blogsapi.services.tokens.sst.UserTokenService;
import com.sanket.blogsapi.users.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing() // to enable auditing
public class BlogsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogsApiApplication.class, args);
    }

    @Bean(name = "modelMapper")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean(name = "passwordEncoder")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // TODO: configure this in properties file
    private final static String TOKEN_SERVICE_TYPE = "JWT"; // "SST" or "JWT"

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public TokenService tokenService(@Autowired UsersService usersService,
                                     @Autowired UserTokenRepository userTokenRepository) {
        if (TOKEN_SERVICE_TYPE.equals("JWT")) {
            return new JwtTokenService();
        } else if (TOKEN_SERVICE_TYPE.equals("SST")) {
            return new UserTokenService(usersService, userTokenRepository);
        } else {
            throw new RuntimeException("Invalid token service type");
        }
    }

}
