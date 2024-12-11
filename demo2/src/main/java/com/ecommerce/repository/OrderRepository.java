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
    List<Order> findByUserId(Long userId);
    List<Order> findByStatus(@Param("status") String status);
    List<Order> findByOrderDateBetween(Date startDate, Date endDate);
    List<Order> findByUserIdAndStatus(Long userId, String status);
    List<Order> findByUserIdAndStatusAndOrderDateBetween(Long userId, String status, Date startDate, Date endDate);

}
