package com.sanket.blogsapi.users;

import com.sanket.blogsapi.users.dtos.CreateUserRequestDTO;
import com.sanket.blogsapi.users.dtos.LoginUserRequestDTO;
import com.sanket.blogsapi.users.dtos.UpdateUserBioRequestDTO;
import com.sanket.blogsapi.users.dtos.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService userService;
    private final ModelMapper modelMapper;

    public UsersController(@Autowired UsersService userService,
                           @Autowired ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signupUser(
            @RequestBody CreateUserRequestDTO requestDTO) {
        UserEntity user = modelMapper.map(requestDTO, UserEntity.class);
        UserEntity savedUser = userService.createUser(user.getUsername(),
                user.getEmail(), user.getPassword());
        UserResponseDTO userResponseDTO = modelMapper.map(savedUser, UserResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(
            @RequestBody LoginUserRequestDTO requestDTO) {
        UserEntity user = modelMapper.map(requestDTO, UserEntity.class);
        UserEntity loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
        UserResponseDTO userResponseDTO = modelMapper.map(loggedInUser, UserResponseDTO.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserBio(
            @PathVariable("id") UUID id,
            @RequestBody UpdateUserBioRequestDTO requestDTO) {
        UserEntity user = modelMapper.map(requestDTO, UserEntity.class);
        UserEntity updatedUser = userService.updateBio(id, user.getBio());
        UserResponseDTO userResponseDTO = modelMapper.map(updatedUser, UserResponseDTO.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponseDTO);
    }
}
