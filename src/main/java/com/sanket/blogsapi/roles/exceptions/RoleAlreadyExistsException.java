package com.sanket.blogsapi.roles.exceptions;

import com.sanket.blogsapi.roles.constants.RolesErrorMessages;

public class RoleAlreadyExistsException extends RuntimeException {

    public RoleAlreadyExistsException(String role) {
        super(String.format(RolesErrorMessages.ROLE_ALREADY_EXISTS, role));
    }
}
