package com.ecommerce.repository;

import com.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 根据用户名查找用户
    Optional<User> findByUsername(String username);

    // 根据邮箱查找用户
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT r.role_name FROM roles r " +
            "JOIN user_roles ur ON r.role_id = ur.role_id " +
            "WHERE ur.user_id = :userId", nativeQuery = true)
    String findRoleByUserId(@Param("userId") Long userId);


    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET " +
            "u.username =:#{#user.username}, " +
            "u.email =:#{#user.email}, " +
            "u.password =:#{#user.password},"+
            "u.phoneNumber =:#{#user.phoneNumber} "+
            "WHERE u.id =:#{#user.id}")
    void updateUser(@Param("user") User user);
}
