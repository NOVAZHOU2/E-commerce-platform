package com.ecommerce.repository;

import com.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderItemReposity extends JpaRepository<OrderItem, Long> {

    //@Query("select o from OrderItem o where o.orderId =: orderId")
    List<OrderItem> findByOrderId(Long orderId);

    @Modifying
    @Transactional
    @Query("delete from OrderItem o where o.orderId =: orderId")
    void deleteByOrderId(Long orderId);

}
