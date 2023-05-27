package com.sanket.blogsapi.roles.dtos;

import com.sanket.blogsapi.roles.RolesEnum;
import lombok.Data;

@Data
public class AssignRoleRequestDTO {
    private String username;
    private RolesEnum role;
}
