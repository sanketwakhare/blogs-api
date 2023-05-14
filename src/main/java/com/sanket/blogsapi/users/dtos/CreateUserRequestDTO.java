package com.sanket.blogsapi.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDTO {
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;
}
