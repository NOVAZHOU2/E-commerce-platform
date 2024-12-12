-- 插入角色数据
INSERT INTO roles (role_name) VALUES ('admin');
INSERT INTO roles (role_name) VALUES ('customer');

-- 插入用户数据
INSERT INTO users (username, password_hash, email) VALUES
                                                       ('小瀑布', 'hashed_password_admin', 'admin@example.com'),
                                                       ('洲浩', 'hashed_password_customer', 'customer@example.com');

-- 插入商品数据
INSERT INTO products (product_name, price, stock, description) VALUES
                                                                   ('电脑', 500, 50, '高性能的游戏电脑'),
                                                                   ('智能手机', 400, 100, '自研芯片'),
                                                                   ('手机壳', 200, 150, '高质感，轻巧，防摔'),
                                                                   ('平板电脑', 300, 30, '学习娱乐');

-- 插入分类数据
INSERT INTO categories (category_name) VALUES
                                           ('Electronics'),
                                           ('Accessories'),
                                           ('Home Appliances');

-- 插入商品与分类关系数据
INSERT INTO product_categories (product_id, category_id) VALUES
                                                             (1, 1),  -- 电脑属于电子产品
                                                             (2, 1),  -- 智能手机属于电子产品
                                                             (3, 2),  -- 手机壳属于配件
                                                             (4, 1);  -- 平板电脑属于电子产品

-- 插入订单数据（需要计算总价）
-- 假设订单1包含：1个 "电脑" 和 1个 "智能手机"；订单2包含：1个 "平板电脑"
INSERT INTO orders (user_id, status, total) VALUES
                                                (2, 'Pending', 900),  -- 订单1，总价 500 + 400 = 900
                                                (2, 'Completed', 300);      -- 订单2，总价 300

-- 插入订单项数据（具体商品和数量）
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
                                                                    (1, 1, 1, 500),  -- 订单1，购买1个 "电脑" 价格 500
                                                                    (1, 2, 1, 400),  -- 订单1，购买1个 "智能手机" 价格 400
                                                                    (2, 4, 1, 300);  -- 订单2，购买1个 "平板电脑" 价格 300

-- 插入支付信息数据（需要计算支付金额，与订单总额一致）
-- 这里支付金额与订单总额一致
INSERT INTO payments (order_id, amount, payment_method, payment_status) VALUES
                                                                            (1, 900, 'Credit Card', 'Paid'),  -- 订单1，支付 900
                                                                            (2, 300, 'Paypal', 'Paid');      -- 订单2，支付 300

-- 插入库存信息数据
INSERT INTO inventory (product_id, stock) VALUES
                                              (1, 50),  -- 商品 "电脑" 库存 50
                                              (2, 100),  -- 商品 "智能手机" 库存 100
                                              (3, 150),  -- 商品 "手机壳" 库存 150
                                              (4, 30);  -- 商品 "平板电脑" 库存 30
