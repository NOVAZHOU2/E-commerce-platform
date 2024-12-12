package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.result.Result;
import com.ecommerce.service.ProductService;
import lombok.extern.slf4j.Slf4j;  // 添加这一行来引入 SLF4J 日志
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j  // 通过 @Slf4j 注解自动生成 log 对象
@RestController
@RequestMapping("/api/products")
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // 添加新商品
    @PostMapping("/add")
    public Result<Product> addProduct(@RequestBody Product product) {
        //log.info("Received product: {}", product);
        // 检查字段值是否正确
        //log.info("Product details: name={}, price={}, stock={}, category={}, description={}",
         //       product.getName(), product.getPrice(), product.getStock(), product.getCategory(), product.getDescription());

        Product savedProduct = productService.addProduct(product);
        //log.info("Product details: name={}, price={}, stock={}, category={}, description={}",
       //         savedProduct.getName(), savedProduct.getPrice(), savedProduct.getStock(), savedProduct.getCategory(), savedProduct.getDescription());

        return Result.success(savedProduct);
    }


    // 获取商品列表
    @GetMapping("/products")
    public Result<List<Product>> getAllProducts() {
        return Result.success(productService.getAllProducts());
    }

    // 获取商品详情
    @GetMapping("/{id}")
    public Result<Product> getProductById(@PathVariable Long id) {
        return Result.success(productService.getProductById(id));
    }

    // 更新商品
    @PutMapping("/{id}")
    public Result<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return Result.success(productService.updateProduct(id, product));
    }

    // 删除商品
    @DeleteMapping("/{id}")
    public Result<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return Result.success();
    }
}
