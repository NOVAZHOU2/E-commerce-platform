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
    @PostMapping("/addOrder/{id}")
    public Result<String> createOrder(@RequestBody List<OrderItem> orderItemList,@PathVariable Long id) {
        log.info("orderList {}", orderItemList);
        Long orderId = orderService.createOrder(orderItemList,id);
        orderItemService.createOrderItems(orderItemList,orderId);
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
