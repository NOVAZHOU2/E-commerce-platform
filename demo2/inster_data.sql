-- 插入 roles 表数据
INSERT INTO roles (`role_id`, `role_name`) VALUES
                                                 (1, 'Admin'),
                                                 (2, 'User'),
                                                 (3, 'Merchant');

-- 插入 users 表数据
INSERT INTO users (`user_id`, `email`, `password_hash`, `phone_number`, `username`, `role_id`) VALUES
                                                                                                     (1, 'admin@example.com', 'hashed_password_1', '1234567890', 'admin_user', 1),
                                                                                                     (2, 'user@example.com', 'hashed_password_2', '0987654321', 'regular_user', 2),
                                                                                                     (3, 'merchant@example.com', 'hashed_password_3', '1122334455', 'merchant_user', 3);

-- 插入 products 表数据
INSERT INTO `products` (`product_id`, `product_name`, `price`, `stock`, `category`, `description`, `merchant_id`) VALUES
                                                                                                                      (1, 'Product A', 19.99, 100, 'Electronics', 'High quality electronic product', 3),
                                                                                                                      (2, 'Product B', 29.99, 50, 'Home', 'Comfortable home product', 3),
                                                                                                                      (3, 'Product C', 9.99, 200, 'Toys', 'Fun toy for kids', 3);

-- 插入 orders 表数据
INSERT INTO `orders` (`order_id`, `order_date`, `total`, `user_id`) VALUES
                                                                        (1, '2024-12-15 10:00:00', 49.98, 2),
                                                                        (2, '2024-12-15 11:00:00', 29.99, 2);

-- 插入 order_items 表数据
INSERT INTO `order_items` (`order_item_id`, `order_id`, `price`, `product_id`, `quantity`, `merchant_id`, `status`) VALUES
                                                                                                                        (1, 1, 19.99, 1, 2, 3, 'PENDING'),
                                                                                                                        (2, 1, 29.99, 2, 1, 3, 'PENDING'),
                                                                                                                        (3, 2, 29.99, 2, 1, 3, 'PENDING');
