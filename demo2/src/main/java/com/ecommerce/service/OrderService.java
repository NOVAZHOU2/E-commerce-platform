package com.ecommerce.service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.service.OrderItemService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemService orderItemService;

    // 创建订单
    public Order createOrder(Order order) {
        // 设置初始状态为 'Pending' 或其他逻辑
        return orderRepository.save(order);
    }

    public Long createOrder(List<OrderItem> orderItems,Long id) {
        Order order = new Order();
        double total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getPrice() * orderItem.getQuantity();
        }
        order.setTotal(BigDecimal.valueOf(total));
        order.setUserId(id);
        order.setOrderDate(Instant.from(LocalDateTime.now()));
        return orderRepository.save(order).getId();
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

    // 查询订单：根据用户 ID、状态、时间范围等条件
    public List<Order> queryOrders(Long userId, String startDate, String endDate) {
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

        // 调用 Repository 根据条件查询
        if (userId != null && start != null && end != null) {
            return orderRepository.findByUserIdAndOrderDateBetween(userId, start, end);
        } else if (userId != null) {
            return orderRepository.findByUserId(userId);
        } else if (start != null && end != null) {
            return orderRepository.findByOrderDateBetween(start, end);
        } else {
            return orderRepository.findAll(); // 如果没有提供任何过滤条件，返回所有订单
        }
    }

    // 根据商户 ID 查询所有相关订单
    public List<Order> queryOrdersByMerchantId(Long merchantId) {
        // 获取该商户所有的订单项
        List<OrderItem> orderItems = orderItemService.getOrderItemsByMerchantId(merchantId);

        // 获取所有与商户相关的订单 ID
        Set<Long> orderIds = orderItems.stream()
                .map(OrderItem::getOrderId)
                .collect(Collectors.toSet());

        // 查询所有与这些订单 ID 相关的订单
        return orderRepository.findByIdIn(new ArrayList<>(orderIds)); // 调用 findByIdIn
    }
}
