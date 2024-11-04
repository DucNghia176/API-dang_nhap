package com.example.API.dto.request;

import jakarta.validation.constraints.Size;

public class AuthRequest {
    private String username;

    @Size(min = 8, message = "password must be at least 8 character")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
