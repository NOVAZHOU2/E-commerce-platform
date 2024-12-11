package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.result.Result;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 创建订单
    @PostMapping
    public Result<Order> createOrder(@RequestBody Order order) {
        return Result.success(orderService.createOrder(order));
    }

    // 获取订单详情
    @GetMapping("/{id}")
    public Result<Order> getOrderById(@PathVariable Long id) {
        return Result.success(orderService.getOrderById(id));
    }

    // 获取用户订单列表
    @GetMapping("/user/{userId}")
    public Result<List<Order>> getUserOrders(@PathVariable Long userId) {
        return Result.success(orderService.getOrdersByUserId(userId));
    }

    // 更新订单状态
    @PutMapping("/{id}")
    public Result<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return Result.success(orderService.updateOrder(id, order));
    }

    // 删除订单
    @DeleteMapping("/{id}")
    public Result<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return Result.success();
    }
}
