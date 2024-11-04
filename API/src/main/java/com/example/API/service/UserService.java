package com.example.API.service;

import com.example.API.dto.request.AuthRequest;
import com.example.API.entity.User;
import com.example.API.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final Map<String, Boolean> userLoginStatus = new HashMap<>();

    // Hàm đăng ký người dùng
    @Transactional
    public User registerUser(AuthRequest authRequest) {
        String username = authRequest.getUsername();

        // Kiểm tra người dùng đã tồn tại
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists: " + username);
        }

        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(authRequest.getPassword());

        return userRepository.save(user);
    }

    // Hàm tìm kiếm người dùng
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Hàm xác thực người dùng
    public boolean validateUser(String username, String password) {
        Optional<User> userOpt = findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found: " + username);
        }
        return userOpt.get().getPassword().equals(password);
    }

    // Hàm tạo token cho người dùng
    public String createToken(String username) {
        return "token-" + username; // Giả lập token
    }

    // Hàm xử lý đăng nhập
    public String loginUser(String username, String password) {
        if (validateUser(username, password)) {
            userLoginStatus.put(username, true); // Đánh dấu người dùng là đã đăng nhập
            return createToken(username);
        }
        throw new RuntimeException("Unauthorized");
    }

    // Hàm xử lý đăng xuất
    public void logoutUser(String username) {
        if (!userLoginStatus.getOrDefault(username, false)) {
            throw new RuntimeException("User not logged in: " + username);
        }
        userLoginStatus.put(username, false); // Đánh dấu người dùng là đã đăng xuất
    }
}
