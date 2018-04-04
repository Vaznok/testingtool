package com.epam.rd.dto;

public class TokenRoleDto {
    private String token;
    private String userRole;

    public TokenRoleDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
