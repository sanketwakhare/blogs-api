package com.sanket.blogsapi.users;

import com.sanket.blogsapi.users.exceptions.DuplicateUserEmailException;
import com.sanket.blogsapi.users.exceptions.DuplicateUserNameException;
import com.sanket.blogsapi.users.exceptions.InvalidCredentialsException;
import com.sanket.blogsapi.users.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(@Autowired UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Create a new user
     *
     * @param username unique username
     * @param email    unique email
     * @param password password
     * @return UserEntity
     */
    public UserEntity createUser(String username, String email, String password) {

        Optional<UserEntity> user;
        // check if user with same username already exists
        user = usersRepository.findByUsername(username);
        if (user.isPresent()) {
            throw new DuplicateUserNameException(username);
        }
        // check if user with same email already exists
        user = usersRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new DuplicateUserEmailException(email);
        }

        // create a new user
        UserEntity newUserEntity = UserEntity.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
        System.out.println(newUserEntity);
        return usersRepository.save(newUserEntity);
    }

    /**
     * Login a user
     *
     * @param email    email
     * @param password password
     * @return UserEntity
     */
    public UserEntity loginUser(String email, String password) {
        Optional<UserEntity> user = usersRepository.findByEmailAndPassword(email, password);
        if (user.isEmpty()) {
            throw new InvalidCredentialsException();
        }
        return user.get();
    }

    /**
     * Update user bio
     *
     * @param id  user id
     * @param bio bio
     * @return UserEntity
     */
    public UserEntity updateBio(UUID id, String bio) {
        Optional<UserEntity> user = usersRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        UserEntity userEntity = user.get();
        userEntity.setBio(bio);
        return usersRepository.save(userEntity);
    }

    /**
     * Find user by username
     *
     * @param username username
     * @return UserEntity
     */
    public UserEntity findByUsername(String username) {
        Optional<UserEntity> user = usersRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException(username);
        }
        return user.get();
    }

    /**
     * Find user by email
     *
     * @param email email
     * @return UserEntity
     */
    public UserEntity findByEmail(String email) {
        Optional<UserEntity> user = usersRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException(email);
        }
        return user.get();
    }

    /**
     * Find user by id
     *
     * @param id id
     * @return UserEntity
     */
    public UserEntity findById(UUID id) {
        Optional<UserEntity> user = usersRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return user.get();
    }
}
