package com.example.API.controller;

import com.example.API.dto.request.ApiResponse;
import com.example.API.dto.request.AuthRequest;
import com.example.API.dto.request.IntrospectRequest;
import com.example.API.dto.response.AuthResponse;
import com.example.API.dto.response.IntrospectResponse;
import com.example.API.service.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authServiced;

    @PostMapping("/login")
    ApiResponse<AuthResponse> authenticate(@RequestBody AuthRequest request){
        var result = authServiced.authenticate(request);
        return ApiResponse.<AuthResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authServiced.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
