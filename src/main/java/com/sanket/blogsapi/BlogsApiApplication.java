package com.sanket.blogsapi;

import org.modelmapper.ModelMapper;
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

}
