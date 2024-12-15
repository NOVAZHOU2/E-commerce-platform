/*
Navicat MySQL Data Transfer

Source Server         : exper
Source Server Version : 80035
Source Host           : localhost:3306
Source Database       : experiment

Target Server Type    : MYSQL
Target Server Version : 80035
File Encoding         : 65001

Date: 2024-12-15 10:54:47
*/
create database ecp;
use ecp;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
                          `order_id` bigint NOT NULL AUTO_INCREMENT,
                          `order_date` datetime(6) DEFAULT NULL,
                          `total` decimal(10,2) DEFAULT NULL,
                          `user_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for order_items
-- ----------------------------
DROP TABLE IF EXISTS `order_items`;
CREATE TABLE `order_items` (
                               `order_item_id` int NOT NULL AUTO_INCREMENT,
                               `order_id` bigint NOT NULL,
                               `price` double NOT NULL,
                               `product_id` int NOT NULL,
                               `quantity` int NOT NULL,
                               `merchant_id` bigint NOT NULL,
                               `status` varchar(50) DEFAULT 'PENDING',
                               PRIMARY KEY (`order_item_id`),
                               KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
                               KEY `fk_orderitem_merchantId` (`merchant_id`),
                               CONSTRAINT `fk_orderitem_merchantId` FOREIGN KEY (`merchant_id`) REFERENCES `users` (`user_id`),
                               CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
                            `product_id` bigint NOT NULL AUTO_INCREMENT,
                            `product_name` varchar(255) DEFAULT NULL,
                            `price` decimal(10,2) DEFAULT NULL,
                            `stock` int DEFAULT NULL,
                            `category` varchar(255) DEFAULT NULL,
                            `description` text,
                            `merchant_id` bigint NOT NULL,
                            PRIMARY KEY (`product_id`),
                            KEY `fk_product_merchantId` (`merchant_id`),
                            CONSTRAINT `fk_product_merchantId` FOREIGN KEY (`merchant_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
                         `role_id` int NOT NULL,
                         `role_name` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `user_id` bigint NOT NULL AUTO_INCREMENT,
                         `email` varchar(255) DEFAULT NULL,
                         `password_hash` varchar(255) DEFAULT NULL,
                         `phone_number` varchar(255) DEFAULT NULL,
                         `username` varchar(255) DEFAULT NULL,
                         `role_id` int DEFAULT NULL,
                         PRIMARY KEY (`user_id`),
                         KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
                         CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
                              `user_id` bigint NOT NULL,
                              `role_id` int NOT NULL,
                              PRIMARY KEY (`user_id`,`role_id`),
                              KEY `FK_user_roles_role_id` (`role_id`),
                              CONSTRAINT `FK_user_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE CASCADE,
                              CONSTRAINT `FK_user_roles_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
DROP TRIGGER IF EXISTS `after_user_insert`;
DELIMITER ;;
CREATE TRIGGER `after_user_insert` AFTER INSERT ON `users` FOR EACH ROW BEGIN
    -- 获取用户注册时指定的 role_id
    DECLARE role_id BIGINT;
    SET role_id = NEW.role_id;

    -- 如果 role_id 存在，插入关联数据到 user_roles 表
    IF role_id IS NOT NULL THEN
        -- 先检查 user_roles 中是否已有相同的 user_id 和 role_id 组合
        IF NOT EXISTS (
            SELECT 1 FROM user_roles
            WHERE user_id = NEW.user_id AND role_id = role_id
        ) THEN
            INSERT INTO user_roles (user_id, role_id)
            VALUES (NEW.user_id, role_id);
        END IF;
    ELSE
        -- 如果 role_id 为 null，则可以抛出错误或记录日志
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Role ID is missing';
    END IF;
END
;;
DELIMITER ;

DROP TRIGGER IF EXISTS `after_user_delete`;
DELIMITER ;;

CREATE TRIGGER `after_user_delete` AFTER DELETE ON `users` FOR EACH ROW
BEGIN
    -- 检查删除的用户是否在 users 表中存在
    IF OLD.user_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '用户不存在';
    ELSE
        -- 删除与被删除用户相关的所有 user_roles 记录
        DELETE FROM user_roles
        WHERE user_id = OLD.user_id;
    END IF;
END
;;

DELIMITER ;

