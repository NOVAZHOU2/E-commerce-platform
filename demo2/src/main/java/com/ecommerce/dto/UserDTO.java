package com.ecommerce.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;  // 留给数据库自动生成
    private String username;
    private String email;
    private String phoneNumber;
    private String roleName;  // 使用 roleName 来传递角色名称
    private String password;  // 新增密码字段

    // Constructors
    public UserDTO() {}

    public UserDTO(Long id, String username, String email, String phoneNumber, String roleName, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleName = roleName;  // 初始化 roleName
        this.password = password;  // 初始化密码字段
    }
}
