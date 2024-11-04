package com.example.API.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {

    @Size(min=3, message = "username must be at 3 characters")
    String username;

    @Size(min=8, message = "password must be 8 characters")
    String password;
    String name;
    String email;
    LocalDate dob;
}
