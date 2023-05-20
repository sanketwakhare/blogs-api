package com.sanket.blogsapi.users;

import com.sanket.blogsapi.common.dtos.ResponseDTO;
import com.sanket.blogsapi.services.tokens.TokensService;
import com.sanket.blogsapi.users.constants.UsersSuccessMessages;
import com.sanket.blogsapi.users.dtos.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService userService;
    private final ModelMapper modelMapper;
    private final TokensService tokensService;

    public UsersController(@Autowired UsersService userService,
                           @Autowired ModelMapper modelMapper,
                           @Autowired TokensService tokensService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.tokensService = tokensService;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    /**
     * Login an existing user
     *
     * @param requestDTO User details
     * @return logged in user
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(
            @RequestBody LoginUserRequestDTO requestDTO) {
        UserEntity user = modelMapper.map(requestDTO, UserEntity.class);
        UserEntity loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
        UserResponseDTO userResponseDTO = modelMapper.map(loggedInUser, UserResponseDTO.class);
         return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponseDTO);
    }

    /**
     * Update user bio
     *
     * @param id         user id
     * @param requestDTO user details
     * @return updated user
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserBio(
            @PathVariable("id") UUID id,
            @RequestBody UpdateUserBioRequestDTO requestDTO) {
        UserEntity user = modelMapper.map(requestDTO, UserEntity.class);
        UserEntity updatedUser = userService.updateBio(id, user.getBio());
        UserResponseDTO userResponseDTO = modelMapper.map(updatedUser, UserResponseDTO.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponseDTO);
    }

    /**
     * Get user by id
     *
     * @param id user id
     * @return user
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable("id") UUID id) {
        UserEntity user = userService.findById(id);
        UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    /**
     * Follow a user
     *
     * @param requestDTO user id to follow
     * @param token      authentication token of a logged-in user
     * @return success message if user followed
     * @throws com.sanket.blogsapi.users.exceptions.UserNotFoundException        if user not found
     * @throws com.sanket.blogsapi.users.exceptions.UserAlreadyFollowedException if user already followed
     * @throws com.sanket.blogsapi.users.exceptions.UserCannotFollowException    if user tries to follow a user who has blocked him or any invalid user
     */
    @PostMapping("/follow")
    public ResponseEntity<ResponseDTO> followUser(
            @RequestBody FollowUserRequestDTO requestDTO,
            @RequestHeader("Authorization") String token) {
        UUID userId = tokensService.getUserIdFromToken(token);
        UUID userIdToFollow = requestDTO.getUserIdToFollow();
        userService.followUser(userId, userIdToFollow);
        ResponseDTO responseDTO = new ResponseDTO(UsersSuccessMessages.USER_FOLLOWED);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    /**
     * Unfollow a user
     *
     * @param requestDTO user id to unfollow
     * @param token      authentication token of a logged-in user
     * @return success message if user unfollowed
     * @throws com.sanket.blogsapi.users.exceptions.UserNotFoundException       if user not found
     * @throws com.sanket.blogsapi.users.exceptions.UserCannotUnfollowException if user not followed
     * @throws com.sanket.blogsapi.users.exceptions.UserCannotUnfollowException if user tries to unfollow a user who has blocked him or any invalid user
     */
    @PostMapping("/unfollow")
    public ResponseEntity<ResponseDTO> unfollowUser(
            @RequestBody UnfollowUserRequestDTO requestDTO,
            @RequestHeader("Authorization") String token) {
        UUID userId = tokensService.getUserIdFromToken(token);
        UUID userIdToUnfollow = requestDTO.getUserIdToUnfollow();
        userService.unfollowUser(userId, userIdToUnfollow);
        ResponseDTO responseDTO = new ResponseDTO(UsersSuccessMessages.USER_UNFOLLOWED);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    /**
     * get all followings of a user
     *
     * @param token authentication token of a logged-in user
     * @return list of followings
     * @throws com.sanket.blogsapi.users.exceptions.UserNotFoundException if user not found
     */
    @GetMapping("/followings")
    public ResponseEntity<FollowingsResponseDTO> getFollowings(
            @RequestHeader("Authorization") String token) {
        UUID userId = tokensService.getUserIdFromToken(token);
        Set<UserEntity> followings = userService.getFollowingsForUserId(userId);

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
     * @param token authentication token of a logged-in user
     * @return list of followers
     * @throws com.sanket.blogsapi.users.exceptions.UserNotFoundException if user not found
     */
    @GetMapping("/followers")
    public ResponseEntity<FollowersResponseDTO> getFollowers(
            @RequestHeader("Authorization") String token) {
        UUID userId = tokensService.getUserIdFromToken(token);
        Set<UserEntity> followings = userService.getFollowersForUserId(userId);

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
