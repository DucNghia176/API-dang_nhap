package com.example.API.controller;

import com.example.API.dto.request.UserRequest;
import com.example.API.entity.User;
import com.example.API.service.UserService;
import jakarta.validation.constraints.Past;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("create")
    User createUser(@RequestBody UserRequest request){
        return userService.createUser(request);
    }

    @PutMapping("/update")
    User updateUser(@PathVariable Long userId, @RequestBody UserRequest request){
        return userService.updateUser(userId, request);
    }
}
