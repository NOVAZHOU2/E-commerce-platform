package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 根据商品名称查询
    List<Product> findByNameContaining(String name);

    // 根据分类查询
    List<Product> findByCategory(String category);

    // 根据库存量查询
    List<Product> findByStockGreaterThan(int stock);
}
