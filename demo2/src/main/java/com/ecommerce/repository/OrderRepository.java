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

    // 根据订单日期范围查询订单
    @Query(value = "SELECT * FROM orders WHERE order_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Order> findByOrderDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // 根据 userId 和订单日期范围查询订单
    @Query(value = "SELECT * FROM orders WHERE user_id = :userId AND order_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Order> findByUserIdAndOrderDateBetween(@Param("userId") Long userId,
                                                @Param("startDate") Date startDate,
                                                @Param("endDate") Date endDate);

    // 查询订单
    @Query("SELECT o FROM Order o WHERE o.id IN :orderIds")
    List<Order> findByIdIn(@Param("orderIds") List<Long> orderIds);

}
