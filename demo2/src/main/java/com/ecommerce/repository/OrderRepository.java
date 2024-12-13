package com.ecommerce.repository;

import com.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // 根据 userId 查询订单
    @Query(value = "SELECT * FROM orders WHERE user_id = :userId", nativeQuery = true)
    List<Order> findByUserId(@Param("userId") Long userId);

    // 根据 status 查询订单
    @Query(value = "SELECT * FROM orders WHERE status = :status", nativeQuery = true)
    List<Order> findByStatus(@Param("status") String status);

    // 根据订单日期范围查询订单
    @Query(value = "SELECT * FROM orders WHERE order_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Order> findByOrderDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // 根据 userId 和 status 查询订单
    @Query(value = "SELECT * FROM orders WHERE user_id = :userId AND status = :status", nativeQuery = true)
    List<Order> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);

    // 根据 userId、status 和订单日期范围查询订单
    @Query(value = "SELECT * FROM orders WHERE user_id = :userId AND status = :status AND order_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Order> findByUserIdAndStatusAndOrderDateBetween(@Param("userId") Long userId,
                                                         @Param("status") String status,
                                                         @Param("startDate") Date startDate,
                                                         @Param("endDate") Date endDate);
}
