package com.ecommerce.controller;

import com.ecommerce.dto.LoginDTO;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.model.User;
import com.ecommerce.result.Result;
import com.ecommerce.service.UserService;
import com.ecommerce.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 用户注册
    @PostMapping("/register")
    public Result<User> register(@RequestBody UserDTO userDTO) {
        log.info("user register {}", userDTO);
        User user = userService.register(userDTO);
        return Result.success(user);
    }

    // 用户登录
    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody LoginDTO user) {
        log.info("user login{}", user);
        return Result.success(userService.login(user));
    }

    // 获取用户信息
    @GetMapping("/{id}")
    public Result<UserVO> getUser(@PathVariable Long id) {
        return Result.success(userService.getUserById(id));
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody User user) {
        log.info("user update{}", user);
        userService.update(user);
        return Result.success();
    }

}
