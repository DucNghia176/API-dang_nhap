package com.example.API.service;

import com.example.API.dto.request.UserCreateRequest;
import com.example.API.dto.request.UserUpdateRequest;
import com.example.API.entity.User;
import com.example.API.exception.AppException;
import com.example.API.exception.ErrorCode;
import com.example.API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreateRequest request){
        User user = new User();

        if (userRepository.existsByUsername(request.getUsername())){
            throw  new AppException(ErrorCode.USER_EXISTED);
        }

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setDob(request.getDob());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long userId, UserUpdateRequest request){
        User user = getUser(userId);

        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public User getUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }
}
