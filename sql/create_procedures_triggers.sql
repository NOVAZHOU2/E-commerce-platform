DELIMITER //

CREATE TRIGGER update_inventory_after_order_complete
    AFTER UPDATE ON orders
    FOR EACH ROW
BEGIN
    -- 只有订单状态从非 'Completed' 更新为 'Completed' 时，才需要更新库存
    IF NEW.status = 'Completed' AND OLD.status != 'Completed' THEN
        -- 更新库存：遍历订单中的所有商品，减少对应商品的库存
        UPDATE inventory i
            JOIN order_items oi ON oi.product_id = i.product_id
        SET i.stock = i.stock - oi.quantity
        WHERE oi.order_id = NEW.order_id;
    END IF;
END;
//

DELIMITER ;
