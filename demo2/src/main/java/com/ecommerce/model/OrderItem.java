package com.ecommerce.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "order_items",schema = "ecp")
public class OrderItem {
    @Id
    private int orderItemId;
    @Column(name = "order_id", nullable = false)
    private int orderId;
    private int productId;
    private int quantity;
    private double price;
}
