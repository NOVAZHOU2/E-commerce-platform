package com.ecommerce.dto;

public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String role;
    private String password;  // 新增密码字段

    // Constructors
    public UserDTO() {}

    public UserDTO(Long id, String username, String email, String phoneNumber, String role, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.password = password;  // 初始化密码字段
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {  // 新增获取密码的方法
        return password;
    }

    public void setPassword(String password) {  // 新增设置密码的方法
        this.password = password;
    }
}
