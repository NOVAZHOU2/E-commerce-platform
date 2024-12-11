package com.ecommerce.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "products",schema = "ecp")
public class Product {

    @Id
    @Column(name = "product_id")
    private Long id;
    @Column(name = "product_name")
    private String name;
    private double price;
    private int stock;
    private String category;  // 添加 category 属性

    private String description;


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", category='" + category + '\'' +
                '}';
    }
}
