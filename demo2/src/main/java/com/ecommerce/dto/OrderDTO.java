package com.ecommerce.dto;

import java.time.LocalDateTime;

public class OrderDTO {

    private Long id;
    private Long userId; // User ID as a reference
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String status;

    // Constructors
    public OrderDTO() {}

    public OrderDTO(Long id, Long userId, LocalDateTime orderDate, Double totalAmount, String status) {
        this.id = id;
        this.userId = userId;
        System.out.println(orderDate);
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
