package com.ecommerce.repository;

import com.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 根据用户名查找用户
    Optional<User> findByUsername(String username);

    // 根据邮箱查找用户
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT ro.roleName FROM Role ro WHERE ro.roleId = " +
            "(SELECT us.userId FROM UserRole us WHERE us.userId =: userId)")
    String findRoleByUserId(Long userId);

}
