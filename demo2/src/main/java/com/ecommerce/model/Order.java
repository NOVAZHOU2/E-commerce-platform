package com.ecommerce.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders", schema = "ecp")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;

    private Long userId;

    @Column(name = "order_date")
    private Instant orderDate;

    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;

    @PrePersist
    public void prePersist() {
        if (this.orderDate == null) {
            this.orderDate = Instant.now();  // 使用 Instant.now() 获取当前时区的时间
        }
    }

}