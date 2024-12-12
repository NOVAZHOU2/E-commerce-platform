package com.ecommerce.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "order_items",schema = "ecp")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;
    @Column(name = "order_id", nullable = false)
    private Long orderId;
    private int productId;
    private int quantity;
    private double price;
}
