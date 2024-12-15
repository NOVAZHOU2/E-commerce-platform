package com.ecommerce.controller;

import com.ecommerce.dto.OrderQueryRequestDTO;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.result.Result;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/order-query")
public class OrderQueryController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    // 查询订单，接收 JSON 请求体
    @PostMapping("/query")  // 使用 @PostMapping
    public Result<List<Order>> queryOrders(@RequestBody OrderQueryRequestDTO requestDTO) {
        log.info("Received query with userId={}, status={}, startDate={}, endDate={}",
                requestDTO.getUserId(), requestDTO.getStatus(), requestDTO.getStartDate(), requestDTO.getEndDate());

        // 调用 service 层处理查询逻辑
        return Result.success(orderService.queryOrders(requestDTO.getUserId(),
                requestDTO.getStartDate(), requestDTO.getEndDate()));
    }

    // 根据商家ID查询订单项
    @GetMapping("/merchant/{merchantId}")
    public Result<List<OrderItem>> queryOrdersByMerchantId(@PathVariable Long merchantId) {
        log.info("Received query for merchantId={}", merchantId);

        // 调用 OrderItemService 层根据 merchantId 获取订单
        List<OrderItem> orders = orderItemService.getOrderItemsByMerchantId(merchantId);
        log.info("orderItems = {}",orders);
        return Result.success(orders);
    }
}

