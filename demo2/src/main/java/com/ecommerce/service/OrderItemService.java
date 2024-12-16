package com.ecommerce.service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.utils.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;

    // 获取订单详情
    public List<OrderItem> getOrderItemsById(Long Id) throws ResourceNotFoundException {
        List<Order> orderItems = orderRepository.findByUserId(Id);
        List<Long> orderItemIds = new ArrayList<>();
        for (Order order : orderItems) {
            orderItemIds.add(order.getId());
        }
        List<OrderItem> orderItemList = orderItemRepository.findAllByOrderId(orderItemIds);
        if (orderItemList.isEmpty()) {
            throw new ResourceNotFoundException("Order not found");
        }
        return orderItemList;
    }

    public List<OrderItem> getOrderItemsByMerchantId(Long merchantId) {
        return orderItemRepository.findByMerchantId(merchantId);  // 通过商户 ID 查询订单项
    }

    // 更新订单项状态
    public OrderItem updateOrderItemStatus(Integer orderItemId, String status) throws ResourceNotFoundException {
        OrderItem orderItem = orderItemRepository.findById(Long.valueOf(orderItemId))
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found"));
        orderItem.setStatus(status);
        return orderItemRepository.save(orderItem);
    }

    // 更新商家ID
    public OrderItem updateMerchantId(Integer orderItemId, Long merchantId) throws ResourceNotFoundException {
        OrderItem orderItem = orderItemRepository.findById(Long.valueOf(orderItemId))
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found"));
        orderItem.setMerchantId(merchantId);
        return orderItemRepository.save(orderItem);
    }

    // 创建订单项
    public void createOrderItems(List<OrderItem> orderItems,Long id) {
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(id);
            orderItem.setStatus(Status.prepare);
        }
        log.info("创建订单项:{}",orderItems);
        orderItemRepository.saveAll(orderItems);
    }

    // 更新订单项
    public List<OrderItem> updateOrderItems(List<OrderItem> orderItems) {
        return orderItemRepository.saveAll(orderItems);
    }

    // 删除订单项
    public void deleteByOrderId(Long orderId) {
        orderItemRepository.deleteByOrderId(orderId);
    }

    public OrderItem updateOrderItem(Long orderItemId, OrderItem orderItem) {
        // 查找要更新的订单项
        OrderItem existingOrderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found for ID: " + orderItemId));

        // 只更新不为 null 的字段
        if (orderItem.getProductId() != null) {
            existingOrderItem.setProductId(orderItem.getProductId());
        }
        if (orderItem.getQuantity() != null) {
            existingOrderItem.setQuantity(orderItem.getQuantity());
        }
        if (orderItem.getPrice() != null) {
            existingOrderItem.setPrice(orderItem.getPrice());
        }
        // 对 merchantId 做特殊处理，如果传递了 null，就不更新它
        if (orderItem.getMerchantId() != null) {
            existingOrderItem.setMerchantId(orderItem.getMerchantId());
        }
        if (orderItem.getStatus() != null) {
            existingOrderItem.setStatus(orderItem.getStatus());
        }

        // 如果需要，可以更新其他字段

        // 保存更新后的订单项并返回
        return orderItemRepository.save(existingOrderItem);
    }


    public List<OrderItem> queryOrdersByMerchantId(Long merchantId) {
        return orderItemRepository.findByMerchantId(merchantId);
    }
}
