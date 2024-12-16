DELIMITER //
DROP TRIGGER IF EXISTS update_inventory_after_order_complete;
CREATE TRIGGER update_inventory_after_order_complete
    AFTER UPDATE ON order_items
    FOR EACH ROW
BEGIN
        -- 处理订单状态为 "Finished" 时，减少库存（已实现逻辑）
        IF NEW.status = 'Finished' AND OLD.status != 'Finished' THEN
            UPDATE products i
                JOIN order_items oi ON oi.product_id = i.product_id
            SET i.stock = i.stock - oi.quantity
            WHERE oi.order_id = NEW.order_id;
        END IF;

        -- 处理订单状态从非 "Cancel" 更新为 "Cancel" 时，恢复库存
        IF NEW.status = 'Cancel' AND OLD.status != 'Cancel' THEN
            UPDATE products i
                JOIN order_items oi ON oi.product_id = i.product_id
            SET i.stock = i.stock + oi.quantity
            WHERE oi.order_id = NEW.order_id;
        END IF;

        -- 防止从 "Cancel" 状态切换到其他状态时重复减库存
        IF OLD.status = 'Cancel' AND NEW.status != 'Cancel' THEN
            UPDATE products i
                JOIN order_items oi ON oi.product_id = i.product_id
            SET i.stock = i.stock - oi.quantity
            WHERE oi.order_id = NEW.order_id;
        END IF;

        -- 对 "Prepare"、"Ready" 和 "Transportation" 不操作库存，仅记录状态变化
        -- 可以选择插入日志表或执行其他操作（如通知系统）

END;
//

DELIMITER ;

DELIMITER //

CREATE PROCEDURE CreateOrder (
    IN user_id INT,
    IN input_product_id INT,
    IN order_quantity INT,
    OUT new_order_id INT
)
BEGIN
    DECLARE stock INT;
    DECLARE product_price DECIMAL(10,2);

    -- 检查产品库存是否足够
    SELECT stock, price INTO stock, product_price FROM products WHERE product_id = input_product_id;

    IF stock >= order_quantity THEN
        -- 创建订单
        INSERT INTO orders (user_id, order_date, total)
        VALUES (user_id, NOW(), 'pending', product_price * order_quantity);

        -- 获取新创建的订单ID
        SET new_order_id = LAST_INSERT_ID();

        -- 创建订单项
        INSERT INTO order_items (order_id, product_id, quantity, price)
        VALUES (new_order_id, input_product_id, order_quantity, product_price);
    ELSE
        -- 库存不足，返回错误信息
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '库存不足';
    END IF;
END;
//

DELIMITER ;
