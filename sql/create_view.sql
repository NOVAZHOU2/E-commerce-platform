CREATE VIEW order_details AS
SELECT
    o.order_id,
    o.order_date,
    o.total,
    u.username AS user_name,
    u.email AS user_email,
    oi.order_item_id,
    oi.product_id,
    p.product_name,
    oi.quantity,
    oi.price,
    oi.status,
    oi.merchant_id,
    m.username AS merchant_name
FROM
    orders o
        JOIN
    users u ON o.user_id = u.user_id
        JOIN
    order_items oi ON o.order_id = oi.order_id
        JOIN
    products p ON oi.product_id = p.product_id
        JOIN
    users m ON oi.merchant_id = m.user_id;