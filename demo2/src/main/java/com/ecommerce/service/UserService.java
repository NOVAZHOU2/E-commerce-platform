package com.ecommerce.service;

import com.ecommerce.dto.LoginDTO;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 用户注册
    public User register(UserDTO userDTO) {
        // 检查用户名是否已存在
        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        log.info("register user: {}", userDTO);
        // 密码加密
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return userRepository.save(user);
    }

    // 用户登录
    public String login(LoginDTO user) {
        // 查找用户
        User existingUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 验证密码
        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        // 返回登录成功的信息
        return "Login successful for user: " + user.getUsername();
    }

    // 获取用户信息
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + id));
    }
}
