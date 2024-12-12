package com.ecommerce.service;

import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 添加新商品
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // 获取所有商品
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 根据商品ID获取商品信息
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }


    // 更新商品
    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();

            // 更新所有字段
            existingProduct.setName(productDetails.getName());
            existingProduct.setPrice(productDetails.getPrice());
            existingProduct.setStock(productDetails.getStock());
            existingProduct.setCategory(productDetails.getCategory());  // 更新分类
            existingProduct.setDescription(productDetails.getDescription());  // 更新描述

            // 保存更新后的商品
            return productRepository.save(existingProduct);
        } else {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
    }


    // 删除商品
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // 查找库存大于指定值的商品
    public List<Product> findByStockGreaterThan(int stock) {
        return productRepository.findByStockGreaterThan(stock);
    }
}
