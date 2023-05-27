package com.sanket.blogsapi.roles;

public enum RolesEnum {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role_name;

    RolesEnum(String role) {
        this.role_name = role;
    }

    public String getRole_name() {
        return role_name;
    }
}
