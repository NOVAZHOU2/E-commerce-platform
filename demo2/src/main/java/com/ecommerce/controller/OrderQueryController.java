package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-query")
public class OrderQueryController {

    @Autowired
    private OrderService orderService;

    // 查询订单
    @GetMapping("/query")
    public List<Order> queryOrders(@RequestParam(required = false) Long userId,
                                   @RequestParam(required = false) String status,
                                   @RequestParam(required = false) String startDate,
                                   @RequestParam(required = false) String endDate) {
        return orderService.queryOrders(userId, status, startDate, endDate);
    }
}
