package com.sanket.blogsapi;

import com.sanket.blogsapi.users.UserEntity;
import com.sanket.blogsapi.users.UsersService;
import com.sanket.blogsapi.users.constants.UsersErrorMessages;
import com.sanket.blogsapi.users.exceptions.InvalidCredentialsException;
import com.sanket.blogsapi.users.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UsersTests {
    @Autowired
    private UsersService usersService;

    @Test
    @Transactional
    @Rollback
    public void testCreateUser() {
        int random = (int) (Math.random() * 1000000000);
        String username = "sanket" + random;
        UserEntity savedUser = usersService.createUser(username, username + "@example.com", "password", username);
        Assertions.assertEquals(savedUser.getUsername(), username);
    }

    @Test
    @Transactional
    @Rollback
    public void testFindUserByUsername_success() {
        int random = (int) (Math.random() * 1000000000);
        String username = "gatha" + random;
        // create a user first
        UserEntity savedUser = usersService.createUser(username, username + "@example.com", "password", username);
        // find by username
        UserEntity user = usersService.findByUsername(username);
        Assertions.assertEquals(savedUser.getId(), user.getId());
    }

    @Test
    public void testFindUserByUsername_error() {
        int random = (int) (Math.random() * 1000000000);
        String username = "gatha" + random;
        // find by username
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            usersService.findByUsername(username);
        });
        Assertions.assertEquals(
                exception.getMessage(),
                String.format(UsersErrorMessages.USER_NOT_FOUND_BY_USERNAME_OR_EMAIL, username));
    }

    @Test
    @Transactional
    @Rollback
    public void testFindUserByEmail_success() {
        int random = (int) (Math.random() * 1000000000);
        String username = "gatha" + random;
        String email = username + "@example.com";
        // create a user first
        UserEntity savedUser = usersService.createUser(username, email, "password", username);
        // find by email
        UserEntity user = usersService.findByEmail(email);
        Assertions.assertEquals(savedUser.getId(), user.getId());
    }

    @Test
    public void testFindUserByEmail_error() {
        int random = (int) (Math.random() * 1000000000);
        String email = "gatha" + random + "@example.com";
        // find by email
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            usersService.findByEmail(email);
        });
        Assertions.assertEquals(
                exception.getMessage(),
                String.format(UsersErrorMessages.USER_NOT_FOUND_BY_USERNAME_OR_EMAIL, email));
    }

    @Test
    @Transactional
    @Rollback
    public void testFindUserById_success() {
        int random = (int) (Math.random() * 1000000000);
        String username = "gatha" + random;
        String email = username + "@example.com";
        // create a user first
        UserEntity savedUser = usersService.createUser(username, email, "password", username);
        // find by id
        UserEntity user = usersService.findById(savedUser.getId());
        Assertions.assertEquals(savedUser.getId(), user.getId());
    }

    @Test
    public void testFindUserById_error() {
        UUID id = UUID.randomUUID();
        // find by id
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            usersService.findById(id);
        });
        Assertions.assertEquals(
                exception.getMessage(),
                String.format(UsersErrorMessages.USER_NOT_FOUND, id));
    }

    @Test
    @Transactional
    @Rollback
    public void testLogin_success() {
        int random = (int) (Math.random() * 1000000000);
        String username = "gatha" + random;
        String email = username + "@example.com";
        String password = "password";
        // create a user first
        UserEntity savedUser = usersService.createUser(username, email, password, username);
        // login
        UserEntity user = usersService.loginUser(email, password);
        Assertions.assertEquals(savedUser.getId(), user.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void testLogin_failure() {
        int random = (int) (Math.random() * 1000000000);
        String username = "gatha" + random;
        String email = username + "@example.com";
        String password = "password";
        String wrongPassword = "wrongPassword";
        // create a user first
        UserEntity savedUser = usersService.createUser(username, email, password, username);

        // expect invalid credential exception by entering invalid password
        Exception exception = assertThrows(InvalidCredentialsException.class, () -> {
            // login
            usersService.loginUser(savedUser.getEmail(), wrongPassword);
        });
        Assertions.assertEquals(
                exception.getMessage(),
                UsersErrorMessages.INVALID_CREDENTIALS);

        // expect invalid credential exception by entering invalid email
        String wrongEmail = "test@123";
        exception = assertThrows(InvalidCredentialsException.class, () -> {
            // login
            usersService.loginUser(wrongEmail, savedUser.getPassword());
        });
        Assertions.assertEquals(
                exception.getMessage(),
                UsersErrorMessages.INVALID_CREDENTIALS);
    }

    @Test
    @Rollback
    public void testUpdateUserBio() {
        int random = (int) (Math.random() * 1000000000);
        String username = "gatha" + random;
        String email = username + "@example.com";
        String password = "password";
        String bio = "This is my bio";
        // create a user first
        UserEntity savedUser = usersService.createUser(username, email, password, username);
        // update bio
        UserEntity user = usersService.updateBio(savedUser.getUsername(), bio);
        Assertions.assertEquals(savedUser.getId(), user.getId());
        Assertions.assertEquals(bio, user.getBio());
    }
}
