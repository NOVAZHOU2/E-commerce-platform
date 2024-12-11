package com.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles",schema = "ecp")
public class UserRole {
    @Id
    @Column(name = "role_id")
    private int roleId;
    @Column(name = "user_id")
    private int userId;
}
