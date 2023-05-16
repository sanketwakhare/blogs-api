package com.sanket.blogsapi.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowersResponseDTO {
    private Set<BasicUserDetailsResponseDTO> followers;
}
