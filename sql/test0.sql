use ecp;
-- 创建用户信息表
CREATE TABLE users (
                       user_id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建角色信息表
CREATE TABLE roles (
                       role_id INT PRIMARY KEY AUTO_INCREMENT,
                       role_name VARCHAR(255) NOT NULL UNIQUE
);

-- 创建用户角色关系表
CREATE TABLE user_roles (
                            user_id INT,
                            role_id INT,
                            FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                            FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role_id)
);

-- 创建商品信息表
CREATE TABLE products (
                          product_id INT PRIMARY KEY AUTO_INCREMENT,
                          product_name VARCHAR(255) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL,
                          stock INT NOT NULL,
                          description TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建索引
CREATE INDEX idx_product_name ON products(product_name);

-- 创建分类信息表
CREATE TABLE categories (
                            category_id INT PRIMARY KEY AUTO_INCREMENT,
                            category_name VARCHAR(255) NOT NULL UNIQUE
);

-- 创建商品分类关系表
CREATE TABLE product_categories (
                                    product_id INT,
                                    category_id INT,
                                    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
                                    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE,
                                    PRIMARY KEY (product_id, category_id)
);

-- 创建订单信息表
CREATE TABLE orders (
                        order_id INT PRIMARY KEY AUTO_INCREMENT,
                        user_id INT,
                        order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        status VARCHAR(50),
                        total DECIMAL(10, 2),
                        FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 创建订单项表
CREATE TABLE order_items (
                             order_item_id INT PRIMARY KEY AUTO_INCREMENT,
                             order_id INT,
                             product_id INT,
                             quantity INT,
                             price DECIMAL(10, 2),
                             FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
                             FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

-- 创建支付信息表
CREATE TABLE payments (
                          payment_id INT PRIMARY KEY AUTO_INCREMENT,
                          order_id INT,
                          payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          amount DECIMAL(10, 2),
                          payment_method VARCHAR(50),
                          payment_status VARCHAR(50),
                          FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
);

-- 创建库存信息表
CREATE TABLE inventory (
                           product_id INT PRIMARY KEY,
                           stock INT NOT NULL,
                           FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

-- 创建触发器：更新库存信息
DELIMITER //
CREATE TRIGGER update_inventory_after_order
    AFTER INSERT ON order_items
    FOR EACH ROW
BEGIN
    UPDATE inventory SET stock = stock - NEW.quantity WHERE product_id = NEW.product_id;
END;
//
DELIMITER ;

-- 创建存储过程：添加新商品
DELIMITER //
CREATE PROCEDURE AddProduct(
    IN p_name VARCHAR(255),
    IN p_price DECIMAL(10, 2),
    IN p_stock INT,
    IN p_description TEXT
)
BEGIN
INSERT INTO products (product_name, price, stock, description)
VALUES (p_name, p_price, p_stock, p_description);
END;
//
DELIMITER ;

-- 创建数据库备份存储过程
DELIMITER //
CREATE PROCEDURE BackupDatabase(
    IN backup_path VARCHAR(255)
)
BEGIN
    SET @backup_cmd = CONCAT('mysqldump -u root -p my_database > ', backup_path);
PREPARE stmt FROM @backup_cmd;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
END;
//
DELIMITER ;

-- 多用户权限管理
-- 创建管理员角色并赋予权限
INSERT INTO roles (role_name) VALUES ('admin');
INSERT INTO roles (role_name) VALUES ('customer');
