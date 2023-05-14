package com.sanket.blogsapi.users.constants;

public class UsersErrorMessages {
    public static final String DUPLICATE_USER_EMAIL = "email %s already registered";
    public static final String DUPLICATE_USER_NAME = "username %s already registered";
    public static final String USER_NOT_FOUND = "User with id %s not found";
    public static final String USER_NOT_FOUND_BY_USERNAME_OR_EMAIL = "User %s not found";
    public static final String INVALID_CREDENTIALS = "Invalid email or password";
    public static final String INVALID_USER_EMAIL = "Invalid email";
    public static final String USERNAME_SIZE_CONSTRAINT_VIOLATION_ERROR = "Username must be between 3 and 30 characters";
    public static final String PASSWORD_SIZE_CONSTRAINT_VIOLATION_ERROR = "Password must be between 6 and 30 characters";
}
