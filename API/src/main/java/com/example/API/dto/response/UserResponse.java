package com.example.API.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserResponse {
    Long   id;
    String username;
    String password;
    String name;
    String email;
    LocalDate dob;
    Set<String> roles;
}
