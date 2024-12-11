package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.result.Result;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // 添加新商品
    @PostMapping("/add")
    public Result<Product> addProduct(@RequestBody Product product) {
        return Result.success(productService.addProduct(product));
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
