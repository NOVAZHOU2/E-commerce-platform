package com.ecommerce.service;

import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 添加新商品
    @Transactional
    public Product addProduct(Product product) {
        try {
            return productRepository.save(product);
        }catch (Exception e) {
            throw new ProductNotFoundException(e.getMessage());
        }
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

    // 根据商户ID获取商品
    public List<Product> getProductsByMerchantId(Long merchantId) {
        return productRepository.findByMerchantId(merchantId);
    }

    // 更新商品
    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();

            // 仅更新提供的字段
            if (productDetails.getName() != null) {
                existingProduct.setName(productDetails.getName());
            }
            if (productDetails.getPrice() != null) {
                existingProduct.setPrice(productDetails.getPrice());
            }
            if (productDetails.getStock() != null) {
                existingProduct.setStock(productDetails.getStock());
            }
            if (productDetails.getCategory() != null) {
                existingProduct.setCategory(productDetails.getCategory());
            }
            if (productDetails.getDescription() != null) {
                existingProduct.setDescription(productDetails.getDescription());
            }
            if (productDetails.getMerchantId() != null) {
                existingProduct.setMerchantId(productDetails.getMerchantId());
            }

            // 保存并返回更新后的商品
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
