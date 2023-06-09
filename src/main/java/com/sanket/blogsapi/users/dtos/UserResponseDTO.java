package com.sanket.blogsapi.users.dtos;

import com.sanket.blogsapi.users.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    @NonNull
    private UUID id;
    @NonNull
    private String username;
    @NonNull
    private String email;
    private String bio;
    private Date createdAt;
    private Date updatedAt;
    private Set<String> authorities;
    private String name;
    private String imageUrl;
    private AuthProvider authProvider;
}
