package com.sanket.blogsapi.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequestDTO {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
