package com.ecommerce.service;

import com.ecommerce.dto.LoginDTO;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.model.User;
import com.ecommerce.model.Role;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.vo.UserVO;
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
    private RoleRepository roleRepository;  // 注入 RoleRepository

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

        // 查找角色
        Optional<Role> role = roleRepository.findByRoleName(userDTO.getRoleName());  // 通过 roleName 查找角色
        if (!role.isPresent()) {
            throw new IllegalArgumentException("Invalid role name");
        }

        // 创建用户对象并设置角色
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setRole(role.get());  // 将角色与用户关联

        // 保存用户
        return userRepository.save(user);
    }

    // 用户登录
    public String login(LoginDTO user) {
        User existingUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 验证密码
        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return "Login successful for user: " + user.getUsername();
    }

    // 获取用户信息
    public UserVO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + id));

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setRole(user.getRole().getRoleName());  // 获取角色名
        return userVO;
    }
}
