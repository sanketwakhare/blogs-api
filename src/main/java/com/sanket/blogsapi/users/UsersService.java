package com.sanket.blogsapi.users;

import com.sanket.blogsapi.users.constants.UsersErrorMessages;
import com.sanket.blogsapi.users.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(@Autowired UsersRepository usersRepository,
                        @Autowired PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create a new user with unique username and email. and password
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

        // encode password
        String encodedPassword = passwordEncoder.encode(password);

        // create a new user
        UserEntity newUserEntity = UserEntity.builder()
                .username(username)
                .email(email)
                .password(encodedPassword)
                .build();
        return usersRepository.save(newUserEntity);
    }

    /**
     * Perform Login with email and password
     *
     * @param email    email
     * @param password password
     * @return UserEntity
     */
    public UserEntity loginUser(String email, String password) {
        Optional<UserEntity> user = usersRepository.findByEmail(email);
        if (user.isPresent()) {
            // validate password
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                return user.get();
            }
        }
        throw new InvalidCredentialsException();
    }

    /**
     * Update user bio
     *
     * @param id  user id
     * @param bio bio
     * @return UserEntity
     */
    public UserEntity updateBio(UUID id, String bio) {
        UserEntity userEntity = findById(id);
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

    /**
     * Follow a user
     *
     * @param userId         user id
     * @param userIdToFollow user id to follow
     */
    public void followUser(UUID userId, UUID userIdToFollow) {
        // check if user is trying to follow himself
        if (userId.equals(userIdToFollow)) {
            throw new UserCannotFollowException(UsersErrorMessages.CANNOT_FOLLOW_ITSELF);
        }
        UserEntity user = findById(userId);
        UserEntity userToFollow = findById(userIdToFollow);

        // get existing followings
        Set<UserEntity> followings = usersRepository.getFollowingsForUserId(userId);
        if (!followings.contains(userToFollow)) {
            followings.add(userToFollow);
            user.setFollowing(followings);
            usersRepository.save(user);
        } else {
            // user is already followed
            throw new UserAlreadyFollowedException();
        }
    }

    /**
     * Unfollow a user
     *
     * @param userId           user id
     * @param userIdToUnfollow user id to unfollow
     */
    public void unfollowUser(UUID userId, UUID userIdToUnfollow) {
        if (userId.equals(userIdToUnfollow)) {
            throw new UserCannotUnfollowException(UsersErrorMessages.CANNOT_UNFOLLOW_ITSELF);
        }
        UserEntity user = findById(userId);
        UserEntity userToUnfollow = findById(userIdToUnfollow);

        // get existing followings
        Set<UserEntity> followings = usersRepository.getFollowingsForUserId(userId);
        if (followings.contains(userToUnfollow)) {
            followings.remove(userToUnfollow);
            user.setFollowing(followings);
            usersRepository.save(user);
        } else {
            // user is not following
            throw new UserCannotUnfollowException(UsersErrorMessages.USER_NOT_FOLLOWED);
        }
    }

    /**
     * Get followings for a user
     *
     * @param userId user id
     * @return set of user followings
     */
    public Set<UserEntity> getFollowingsForUserId(UUID userId) {
        UserEntity user = findById(userId);
        return usersRepository.getFollowingsForUserId(user.getId());
    }

    /**
     * Get followers for a user
     *
     * @param userId user id
     * @return set of user followers
     */
    public Set<UserEntity> getFollowersForUserId(UUID userId) {
        UserEntity user = findById(userId);
        return usersRepository.getFollowersForUserId(user.getId());
    }
}
