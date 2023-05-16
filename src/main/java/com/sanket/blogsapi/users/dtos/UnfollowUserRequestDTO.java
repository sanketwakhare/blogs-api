package com.sanket.blogsapi.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnfollowUserRequestDTO {
    private UUID userIdToUnfollow;
}
