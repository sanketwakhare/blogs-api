package com.sanket.blogsapi.roles.exceptions;

import com.sanket.blogsapi.roles.constants.RolesErrorMessages;

public class RoleAlreadyAssignedException extends RuntimeException {
    public RoleAlreadyAssignedException(String username, String roleName) {
        super(String.format(RolesErrorMessages.ROLE_ALREADY_ASSIGNED_TO_USER, roleName, username));
    }
}
