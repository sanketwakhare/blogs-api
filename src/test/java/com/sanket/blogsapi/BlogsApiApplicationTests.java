package com.sanket.blogsapi;

import com.sanket.blogsapi.users.UserEntity;
import com.sanket.blogsapi.users.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class BlogsApiApplicationTests {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testCreateUser() {
        UserEntity userEntity = new UserEntity("sanket", "sanket@example.com", "password", "bio", null, null);
        UserEntity savedUser = usersRepository.save(userEntity);
        System.out.println(savedUser);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testFetchUser() {
        int random = (int) (Math.random() * 100000);
        String username = "gatha" + random;
        // create a user first
        UserEntity userEntity = new UserEntity(username, username + "@example.com",
                "password", "bio", null, null);
        UserEntity savedUser = usersRepository.save(userEntity);
        System.out.println(savedUser);
        // find by username
        UserEntity user = usersRepository.findByUsername(username);
        System.out.println(user);
    }

}
