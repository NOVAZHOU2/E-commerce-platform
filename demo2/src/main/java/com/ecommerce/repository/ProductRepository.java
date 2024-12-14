package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 根据商品名称查询
    @Query(value = "SELECT * FROM products WHERE product_name LIKE %?1%", nativeQuery = true)
    List<Product> findByNameContaining(String name);

    // 根据分类查询
    @Query(value = "SELECT * FROM products p JOIN product_categories pc ON p.product_id = pc.product_id " +
            "JOIN categories c ON pc.category_id = c.category_id WHERE c.category_name = ?1", nativeQuery = true)
    List<Product> findByCategory(String category);

    // 根据库存量查询
    @Query(value = "SELECT * FROM products WHERE stock > ?1", nativeQuery = true)
    List<Product> findByStockGreaterThan(int stock);

    @Query(value = "SELECT * FROM products WHERE merchant_id = ?1", nativeQuery = true)
    List<Product> findByMerchantId(Long merchantId);
}
