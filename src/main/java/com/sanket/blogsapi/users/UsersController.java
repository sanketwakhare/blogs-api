package com.sanket.blogsapi.users;

import com.sanket.blogsapi.common.dtos.ResponseDTO;
import com.sanket.blogsapi.services.tokens.TokenService;
import com.sanket.blogsapi.users.constants.UsersSuccessMessages;
import com.sanket.blogsapi.users.dtos.*;
import com.sanket.blogsapi.users.exceptions.UserAlreadyFollowedException;
import com.sanket.blogsapi.users.exceptions.UserCannotFollowException;
import com.sanket.blogsapi.users.exceptions.UserCannotUnfollowException;
import com.sanket.blogsapi.users.exceptions.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService userService;
    private final ModelMapper modelMapper;
    private final TokenService tokenService;

    public UsersController(@Autowired UsersService userService,
                           @Autowired ModelMapper modelMapper,
                           @Autowired TokenService tokenService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
    }

    /**
     * Signup/ Register a new user
     *
     * @param requestDTO User details
     * @return newly created user
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signupUser(
            @RequestBody CreateUserRequestDTO requestDTO) {
        UserEntity user = modelMapper.map(requestDTO, UserEntity.class);
        UserEntity savedUser = userService.createUser(user.getUsername(),
                user.getEmail(), user.getPassword());
        UserResponseDTO userResponseDTO = modelMapper.map(savedUser, UserResponseDTO.class);
        mapAuthorities(savedUser, userResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    /**
     * Login an existing user
     *
     * @param requestDTO User details
     * @return logged in user
     */
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> loginUser(
            @RequestBody LoginUserRequestDTO requestDTO) {
        UserEntity user = modelMapper.map(requestDTO, UserEntity.class);
        UserEntity loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
        LoginUserResponseDTO loginUserResponseDTO = modelMapper.map(loggedInUser, LoginUserResponseDTO.class);
        loginUserResponseDTO.setToken(tokenService.createAuthToken(loggedInUser.getUsername()));
        mapAuthorities(loggedInUser, loginUserResponseDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginUserResponseDTO);
    }

    /**
     * Update user bio
     *
     * @param username   username
     * @param requestDTO user details
     * @return updated user
     */
    @PatchMapping("/{username}")
    // only admin or user itself can update bio
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.equals(#username)")
    public ResponseEntity<UserResponseDTO> updateUserBio(
            @PathVariable("username") String username,
            @RequestBody UpdateUserBioRequestDTO requestDTO) {
        UserEntity user = modelMapper.map(requestDTO, UserEntity.class);
        UserEntity updatedUser = userService.updateBio(username, user.getBio());
        UserResponseDTO userResponseDTO = modelMapper.map(updatedUser, UserResponseDTO.class);
        mapAuthorities(updatedUser, userResponseDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponseDTO);
    }

    /**
     * Get user by id
     *
     * @param username username
     * @return user
     */
    @GetMapping("/profile/{username}")
    // only admin or user itself can get profile
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.equals(#username)")
    public ResponseEntity<UserResponseDTO> getUserProfileByUsername(
            @PathVariable("username") String username) {
        UserEntity user = userService.findByUsername(username);
        UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
        mapAuthorities(user, userResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    /**
     * Get user by username
     *
     * @param username username of a logged-in user
     * @return user
     */
    @GetMapping("/me")
    // only user itself can get profile
    @PreAuthorize("authentication.principal.equals(#username)")
    public ResponseEntity<UserResponseDTO> getMyProfile(
            @AuthenticationPrincipal String username) {
        UserEntity user = userService.findByUsername(username);
        UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
        mapAuthorities(user, userResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    private void mapAuthorities(UserEntity user, UserResponseDTO userResponseDTO) {
        // set authorities
        Set<String> authorities = new HashSet<>();
        user.getRoles().forEach(role -> authorities.add(role.getRole().name()));
        userResponseDTO.setAuthorities(authorities);
    }

    /**
     * Follow a user
     *
     * @param requestDTO user id to follow
     * @param username   username of a logged-in user
     * @return success message if user followed
     * @throws UserNotFoundException        if user not found
     * @throws UserAlreadyFollowedException if user already followed
     * @throws UserCannotFollowException    if user tries to follow a user who has blocked him or any invalid user
     */
    @PostMapping("/follow")
    public ResponseEntity<ResponseDTO> followUser(
            @RequestBody FollowUserRequestDTO requestDTO,
            @AuthenticationPrincipal String username) {
        String usernameToFollow = requestDTO.getUsernameToFollow();
        userService.followUser(username, usernameToFollow);
        ResponseDTO responseDTO = new ResponseDTO(UsersSuccessMessages.USER_FOLLOWED);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    /**
     * Unfollow a user
     *
     * @param requestDTO user id to unfollow
     * @param username   username of a logged-in user
     * @return success message if user unfollowed
     * @throws UserNotFoundException       if user not found
     * @throws UserCannotUnfollowException if user not followed
     * @throws UserCannotUnfollowException if user tries to unfollow a user who has blocked him or any invalid user
     */
    @PostMapping("/unfollow")
    public ResponseEntity<ResponseDTO> unfollowUser(
            @RequestBody UnfollowUserRequestDTO requestDTO,
            @AuthenticationPrincipal String username) {
        String usernameToUnfollow = requestDTO.getUsernameToUnfollow();
        userService.unfollowUser(username, usernameToUnfollow);
        ResponseDTO responseDTO = new ResponseDTO(UsersSuccessMessages.USER_UNFOLLOWED);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    /**
     * get all followings of a user
     *
     * @param username username of a logged-in user
     * @return list of followings
     * @throws UserNotFoundException if user not found
     */
    @GetMapping("/followings/{username}")
    public ResponseEntity<FollowingsResponseDTO> getFollowings(@PathVariable("username") String username) {

        Set<UserEntity> followings = userService.getFollowingsForUserId(username);

        // convert followings to response dto
        FollowingsResponseDTO followingsResponseDTO = new FollowingsResponseDTO();
        followingsResponseDTO.setFollowings(new LinkedHashSet<>());
        for (UserEntity following : followings) {
            BasicUserDetailsResponseDTO userResponseDTO = modelMapper.map(following, BasicUserDetailsResponseDTO.class);
            followingsResponseDTO.getFollowings().add(userResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(followingsResponseDTO);
    }

    /**
     * get all followers of a user
     *
     * @param username username of a logged-in user
     * @return list of followers
     * @throws UserNotFoundException if user not found
     */
    @GetMapping("/followers/{username}")
    public ResponseEntity<FollowersResponseDTO> getFollowers(@PathVariable("username") String username) {

        Set<UserEntity> followings = userService.getFollowersForUser(username);

        // convert followings to response dto
        FollowersResponseDTO followersResponseDTO = new FollowersResponseDTO();
        followersResponseDTO.setFollowers(new LinkedHashSet<>());
        for (UserEntity following : followings) {
            BasicUserDetailsResponseDTO userResponseDTO = modelMapper.map(following, BasicUserDetailsResponseDTO.class);
            followersResponseDTO.getFollowers().add(userResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(followersResponseDTO);
    }
}
