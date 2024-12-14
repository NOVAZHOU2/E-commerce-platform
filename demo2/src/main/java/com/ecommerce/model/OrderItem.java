package com.ecommerce.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "order_items", schema = "ecp")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    private Integer productId;
    private Integer quantity;
    private Double price;

    @Column(name = "status")
    private String status;  // 新增状态字段

    @Column(name = "merchant_id")
    private Long merchantId;  // 新增商家ID字段
}
