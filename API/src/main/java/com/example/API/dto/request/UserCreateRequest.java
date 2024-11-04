package com.example.API.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserCreateRequest {

    @Size(min=3, message = "username must be at 3 characters")
    private String username;

    @Size(min=8, message = "password must be 8 characters")
    private String password;
    private String name;

    private String email;
    private LocalDate dob;
}
