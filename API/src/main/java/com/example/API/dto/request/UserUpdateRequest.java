package com.example.API.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {

    @Size(min=8, message = "password must be 8 characters")
    private String password;
    private String name;

    private String email;
    private LocalDate dob;

}
