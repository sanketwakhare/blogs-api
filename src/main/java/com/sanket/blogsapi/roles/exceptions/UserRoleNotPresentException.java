package com.sanket.blogsapi.roles.exceptions;

import com.sanket.blogsapi.roles.constants.RolesErrorMessages;

public class UserRoleNotPresentException extends RuntimeException {
    public UserRoleNotPresentException(String username, String roleName) {
        super(String.format(RolesErrorMessages.USER_DOES_NOT_HAVE_ROLE, username, roleName));
    }
}
