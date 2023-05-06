package com.sanket.blogsapi;

import com.sanket.blogsapi.users.UserEntity;
import com.sanket.blogsapi.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogsApiApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testCreateUser() {
        UserEntity userEntity = new UserEntity("Sanket1234", "sanket1234@example.com", "password", "bio", null, null);
        UserEntity savedUser = userRepository.save(userEntity);
        System.out.println(savedUser);
    }

}
