package com.ecommerce.controller;

import com.ecommerce.model.Inventory;
import com.ecommerce.result.Result;
import com.ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // 获取库存列表
    @GetMapping
    public Result<List<Inventory>> getAllInventory() {
        return Result.success(inventoryService.getAllInventory());
    }

    // 获取商品库存
    @GetMapping("/product/{productId}")
    public Result<Inventory> getInventoryByProductId(@PathVariable Long productId) {
        return Result.success(inventoryService.getInventoryByProductId(productId));
    }

    // 更新库存
    @PutMapping("/{id}")
    public  Result<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        return Result.success(inventoryService.updateInventory(id, inventory));
    }

    // 添加库存
    @PostMapping
    public Result<Inventory> addInventory(@RequestBody Inventory inventory) {
        return Result.success(inventoryService.addInventory(inventory));
    }

    // 删除库存
    @DeleteMapping("/{id}")
    public Result<String> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return Result.success();
    }
}
