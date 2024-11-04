package com.example.API.controller;

import com.example.API.dto.request.ApiResponse;
import com.example.API.dto.request.AuthRequest;
import com.example.API.dto.response.AuthResponse;
import com.example.API.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;

    @PostMapping("/login")
    ApiResponse<AuthResponse> authResponseApiResponse(@RequestBody AuthRequest request){
        boolean result = authService.authenticate(request);
        return com.example.API.dto.request.ApiResponse.<AuthResponse>builder()
                .result(AuthResponse.builder()
                        .authenticated(result)
                        .build())
                .build();
    }
}
