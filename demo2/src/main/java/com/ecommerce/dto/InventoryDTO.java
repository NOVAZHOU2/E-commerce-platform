package com.ecommerce.dto;

public class InventoryDTO {

    private Long productId; // Product ID as a reference
    private Integer quantity;

    // Constructors
    public InventoryDTO() {}

    public InventoryDTO(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
