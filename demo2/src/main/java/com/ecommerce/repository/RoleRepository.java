package com.ecommerce.repository;

import com.ecommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // 根据角色名称查找角色
    Optional<Role> findByRoleName(String roleName);
}
