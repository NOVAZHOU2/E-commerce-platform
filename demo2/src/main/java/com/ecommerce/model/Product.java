package com.ecommerce.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@Table(name = "products", schema = "ecp")
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 使用自增长策略生成ID
    private Long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;  // 使用 BigDecimal 来表示价格，避免浮动误差

    @Column(name = "stock")
    private int stock;

    @Column(name = "category")
    private String category;  // 添加 category 属性

    @Column(name = "description")
    private String description;

    // 如果需要自定义toString方法，保留
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
