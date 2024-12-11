package com.ecommerce.controller;

import com.ecommerce.dto.OrderRequestDTO;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
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
        orderService.createOrder(order);
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

    // 更新订单状态
    @PutMapping("/update/{id}")
    public Result<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return Result.success(orderService.updateOrder(id, order));
    }

    @PutMapping("/orderItems/update")
    public Result<List<OrderItem>> updateOrderItems(@RequestBody List<OrderItem> ordersItem) {
        log.info("update order items{}", ordersItem);
        return Result.success(orderItemService.updateOrderItems(ordersItem));
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
