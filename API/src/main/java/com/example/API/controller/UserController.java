package com.example.API.controller;

import com.example.API.dto.request.ApiResponse;
import com.example.API.dto.request.UserCreateRequest;
import com.example.API.dto.request.UserUpdateRequest;
import com.example.API.entity.User;
import com.example.API.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreateRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createUser(request));

        return apiResponse;
    }

    @PutMapping("/userId")
    User updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/userId")
    String deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return "user has been delete";
    }

}
