package com.ecommerce.controller;

import com.ecommerce.dto.OrderRequestDTO;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.User;
import com.ecommerce.result.Result;
import com.ecommerce.service.OrderItemService;
import com.ecommerce.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    // 创建订单
    @PostMapping("/addOrder")
    public Result<String> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        Order order = orderRequestDTO.getOrder();
        List<OrderItem> orderItems = orderRequestDTO.getOrderItems();
        Order savedOrder = orderService.createOrder(order);
        // 获取 userId
        Long userId = order.getUserId();

        // 根据 userId 获取对应的 User 对象
       // User user = userService.getUserById(userId);

        // 设置 User 到 Order 对象中
        order.setUserId(userId);

        // 确保 orderItems 中的每个 OrderItem 都是新的对象，且没有重复 ID
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(order.getId());  // 关联 Order 到 OrderItem
            orderItem.setOrderItemId(null);  // 清除 OrderItem 的 ID，以确保它是新对象
        }

        // 创建订单项
        orderItemService.createOrderItems(orderItems);

        return Result.success();
    }


    // 获取订单详情
    @GetMapping("/search/{id}")
    public Result<List<OrderItem>> getOrderById(@PathVariable Long id) {
        return Result.success(orderItemService.getOrderItemsById(id));
    }

    // 获取用户订单列表
    @GetMapping("/user/{userId}")
    public Result<List<Order>> getUserOrders(@PathVariable Long userId) {
        return Result.success(orderService.getOrdersByUserId(userId));
    }

    @PutMapping("/{orderId}")
    public Result<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order orderDetails) {
        Order updatedOrder = orderService.updateOrder(orderId, orderDetails);
        return Result.success(updatedOrder);
    }

    @PutMapping("/orderItems/update/{orderItemId}")
    public Result<OrderItem> updateOrderItem(@PathVariable Long orderItemId, @RequestBody OrderItem orderItem) {
        log.info("Updating order item with id: {}", orderItemId);

        // 调用 Service 层的更新方法
        return Result.success(orderItemService.updateOrderItem(orderItemId, orderItem));
    }

    // 删除订单
    @Transactional
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        orderItemService.deleteByOrderId(id);
        return Result.success();
    }
}
