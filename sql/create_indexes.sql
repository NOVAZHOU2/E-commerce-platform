-- 创建用户表索引
CREATE UNIQUE INDEX idx_username ON users(username);

-- 创建商品表索引
CREATE INDEX idx_product_name ON products(product_name);

-- 创建订单表索引
CREATE INDEX idx_user_order ON orders(user_id, order_date);

-- 创建商品分类关系表索引
CREATE INDEX idx_product_category ON product_categories(product_id, category_id);

-- 创建支付信息表索引
CREATE INDEX idx_payment_order ON payments(order_id);

-- 商品id索引商品表
CREATE FULLTEXT INDEX idx_product_id ON products(product_id);

-- 用户ID索引购物车表
CREATE INDEX idx_user_id ON shopping_cart(user_id);