package com.ecommerce.service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    // 创建订单
    public Order createOrder(Order order) {
        // 设置初始状态为 'Pending' 或其他逻辑
        order.setStatus("Pending");
        return orderRepository.save(order);
    }


    // 获取用户的所有订单
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // 更新订单信息
    public Order updateOrder(Long orderId, Order orderDetails) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found for ID: " + orderId));

        // 更新订单的相关字段
        existingOrder.setStatus(orderDetails.getStatus());
        existingOrder.setTotal(orderDetails.getTotal());
        // 根据需要更新其他字段
        return orderRepository.save(existingOrder);
    }

    // 删除订单
    public Long deleteOrder(Long orderId) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found for ID: " + orderId));

        orderRepository.delete(existingOrder);
        return orderId;
    }

    public List<Order> queryOrders(Long userId, String status, String startDate, String endDate) {
        // 将日期字符串转换为 Date 对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;

        try {
            if (startDate != null) {
                start = sdf.parse(startDate);
            }
            if (endDate != null) {
                end = sdf.parse(endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace(); // 这里可以根据需要做更好的错误处理
        }
        System.out.println(start);
        System.out.println(end);;
        // 调用 Repository 根据条件查询
        if (userId != null && status != null && start != null && end != null) {
            return orderRepository.findByUserIdAndStatusAndOrderDateBetween(userId, status, start, end);
        } else if (userId != null && status != null) {
            return orderRepository.findByUserIdAndStatus(userId, status);
        } else if (userId != null) {
            return orderRepository.findByUserId(userId);
        } else if (status != null) {
            return orderRepository.findByStatus(status);
        } else if (start != null && end != null) {
            return orderRepository.findByOrderDateBetween(start, end);
        } else {
            return orderRepository.findAll(); // 如果没有提供任何过滤条件，返回所有订单
        }
    }
}

