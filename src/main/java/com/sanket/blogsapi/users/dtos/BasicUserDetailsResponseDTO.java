package com.sanket.blogsapi.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicUserDetailsResponseDTO {
    @NonNull
    private UUID id;
    @NonNull
    private String username;
    @NonNull
    private String email;
}
