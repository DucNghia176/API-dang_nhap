package com.example.API.controller;

import com.example.API.dto.request.AuthRequest;
import com.example.API.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService userService;
    private String token = null;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

        try {
            token = userService.loginUser(username, password);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Login failed: Invalid username or password.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();

        try {
            // Kiểm tra xem người dùng đã tồn tại hay chưa
            if (userService.findByUsername(username).isPresent()) {
                return ResponseEntity.status(409).body("User already exists");
            }
            userService.registerUser(authRequest); // Đăng ký người dùng
            return ResponseEntity.status(201).body("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Registration failed: " + e.getMessage());
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        if (token == null) { // Kiểm tra xem người dùng đã đăng nhập chưa
            return ResponseEntity.status(400).body("You are not logged in.");
        }
        token = null; // Đăng xuất
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/test-token")
    public ResponseEntity<?> testToken(@RequestHeader(value = "Authorization", required = false) String authToken) {
        if (authToken == null || !authToken.equals(token)) {
            return ResponseEntity.status(401).body("Unauthorized - No token");
        }
        return ResponseEntity.ok("Token is valid - Success");
    }
}
