package com.ecommerce.dto;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Order order;
    private List<OrderItem> orderItems;
}
