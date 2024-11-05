package com.example.API.service;

import com.example.API.dto.request.UserCreateRequest;
import com.example.API.dto.request.UserUpdateRequest;
import com.example.API.dto.response.UserResponse;
import com.example.API.entity.User;
import com.example.API.enums.Role;
import com.example.API.exception.AppException;
import com.example.API.exception.ErrorCode;
import com.example.API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Đảm bảo PasswordEncoder được khởi tạo

    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Mã hóa mật khẩu
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setDob(request.getDob());

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        user = userRepository.save(user);
        return convertToUserResponse(user);
    }

    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword())); // Mã hóa mật khẩu mới nếu có
        }
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setDob(request.getDob());

        user = userRepository.save(user);
        return convertToUserResponse(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToUserResponse(user);
    }

    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .dob(user.getDob())
                .roles(user.getRoles())
                .build();
    }
}
