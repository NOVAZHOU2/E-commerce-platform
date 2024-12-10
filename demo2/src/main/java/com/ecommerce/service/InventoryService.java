package com.ecommerce.service;

import com.ecommerce.model.Inventory;
import com.ecommerce.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // 获取所有库存
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    // 获取某个商品的库存
    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product ID: " + productId));
    }

    // 更新库存
    public Inventory updateInventory(Long id, Inventory inventory) {
        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for ID: " + id));

        existingInventory.setProductId(inventory.getProductId());
        existingInventory.setQuantity(inventory.getQuantity());
        existingInventory.setWarehouseLocation(inventory.getWarehouseLocation());

        return inventoryRepository.save(existingInventory);
    }

    // 添加库存
    public Inventory addInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    // 删除库存
    public void deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for ID: " + id));
        inventoryRepository.delete(inventory);
    }
}
