package com.ecommerce.repository;

import com.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // 使用原生 SQL 查询，根据 orderId 查找所有订单项
    @Query(value = "SELECT * FROM order_items WHERE order_id = ?1", nativeQuery = true)
    List<OrderItem> findByOrderId(Long orderId);

    // 使用原生 SQL 删除指定 orderId 的所有订单项
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM order_items WHERE order_id = ?1", nativeQuery = true)
    void deleteByOrderId(Long orderId);
}
