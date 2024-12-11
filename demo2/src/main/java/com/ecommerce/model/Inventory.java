package com.ecommerce.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Inventory {

    @Id
    @GeneratedValue
    private Long id;

    private Long productId; // 商品ID

    private Integer quantity; // 库存数量

    private String warehouseLocation; // 仓库位置

    private String stock;

}
