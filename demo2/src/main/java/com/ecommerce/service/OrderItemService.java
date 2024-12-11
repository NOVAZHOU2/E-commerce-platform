package com.ecommerce.service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.OrderItem;
import com.ecommerce.repository.OrderItemReposity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderItemService {
    @Autowired
    private OrderItemReposity orderItemReposity;

    // 获取订单详情
    public List<OrderItem> getOrderItemsById(Long orderId) throws ResourceNotFoundException {
        List<OrderItem> byOrderId = orderItemReposity.findByOrderId(orderId);
        if (byOrderId.isEmpty()) {
            throw new ResourceNotFoundException("Order not found");
        }
        return byOrderId;
    }


    public List<OrderItem> updateOrderItems(List<OrderItem> orderItems) throws ResourceNotFoundException {
        orderItemReposity.saveAll(orderItems);
        orderItemReposity.flush();
        return orderItems;
    }

    public void deleteByOrderId(Long id) {
        orderItemReposity.deleteByOrderId(id);
    }

    public void createOrderItems(List<OrderItem> orderItems) {
        orderItemReposity.saveAll(orderItems);
    }
}
