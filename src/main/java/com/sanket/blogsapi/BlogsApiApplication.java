package com.sanket.blogsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing() // to enable auditing
public class BlogsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogsApiApplication.class, args);
    }

}
