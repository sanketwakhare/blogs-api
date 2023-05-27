package com.sanket.blogsapi.roles.exceptions;

import com.sanket.blogsapi.roles.constants.RolesErrorMessages;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String role) {
        super(String.format(RolesErrorMessages.ROLE_NOT_FOUND, role));
    }
}
