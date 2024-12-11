package com.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;

}
