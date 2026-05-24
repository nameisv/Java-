
-- 创建数据库
CREATE DATABASE IF NOT EXISTS bt_carnie_mgr DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE bt_carnie_mgr;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ============================
-- 1. 管理员表
-- ============================
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

INSERT INTO `t_admin` VALUES (1, 'admin', 'admin', '超级管理员', '2023-12-01 10:00:00', '2023-12-01 10:00:00');

-- ============================
-- 2. 员工表
-- ============================
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '员工ID',
  `work_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工号',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` int NULL DEFAULT NULL COMMENT '性别：0女 1男',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `phone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `entry_date` date NULL DEFAULT NULL COMMENT '入职日期',
  `dept_id` int NULL DEFAULT NULL COMMENT '部门ID',
  `address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住址',
  `status` int NULL DEFAULT 0 COMMENT '状态：0正常 1禁用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `role` int NULL DEFAULT 1 COMMENT '角色：1普通员工 3高级员工 4维修员',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `work_id`(`work_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '员工表' ROW_FORMAT = Dynamic;

INSERT INTO `t_employee` VALUES (1, 'E0001', '123456', '张小青', 1, '1999-05-12', '12111111111', '1211@qq.com', '2021-09-01', 1, '广东深圳', 0, '2023-12-01 16:05:47', '2023-12-06 17:58:26', 1);
INSERT INTO `t_employee` VALUES (2, 'E0002', '123456', '李晓梅', 1, '1998-05-20', '12111111112', '1212@qq.com', '2022-09-01', 2, '广东深圳', 0, '2023-12-04 11:12:07', '2023-12-06 17:31:04', 1);
INSERT INTO `t_employee` VALUES (3, 'E0003', '123456', '王姗姗', 1, '1999-12-28', '12111111113', '1213@qq.com', '2021-09-01', 6, '广东深圳', 0, '2023-12-06 17:31:58', '2023-12-06 17:58:48', 1);
INSERT INTO `t_employee` VALUES (4, 'SE0002', '123456', '李高级', 1, '2000-01-01', '13888888888', 'gaoji@qq.com', '2024-01-01', NULL, '北京朝阳', 0, '2025-11-29 00:59:19', '2025-11-29 00:59:19', 3);
INSERT INTO `t_employee` VALUES (5, 'senior001', '123456', '高级员工测试', NULL, NULL, NULL, NULL, NULL, 1, NULL, 0, '2025-11-29 01:05:43', '2025-11-29 01:05:43', 3);
INSERT INTO `t_employee` VALUES (6, 'SE0003', '123456', '张高级', 0, '1995-08-15', '13900000001', 'senior@qq.com', '2024-01-01', NULL, '广东广州', 0, '2025-12-02 21:50:00', '2025-12-02 21:50:00', 3);
INSERT INTO `t_employee` VALUES (7, 'M0001', '123456', '李维修', 0, '1992-03-20', '13900000002', 'repair@qq.com', '2024-01-01', NULL, '广东深圳', 0, '2025-12-02 21:50:00', '2025-12-02 21:50:00', 4);

-- ============================
-- 3. 用户表
-- ============================
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `idno` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `sex` int NULL DEFAULT NULL COMMENT '性别：0女 1男',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `telephone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `profession` int NULL DEFAULT NULL COMMENT '职业',
  `address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `amount` double NULL DEFAULT 0.00 COMMENT '账户余额',
  `status` int NULL DEFAULT 0 COMMENT '状态：0正常 1禁用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

INSERT INTO `t_user` VALUES (1, '2025001', '123456', '张三', NULL, 1, NULL, NULL, NULL, NULL, NULL, 1000.00, 0, '2025-11-25 00:27:35', '2025-11-25 00:27:35');
INSERT INTO `t_user` VALUES (2, '2025002', '123456', '李四', NULL, 0, NULL, NULL, NULL, NULL, NULL, 500.00, 0, '2025-11-25 00:27:35', '2025-11-25 00:27:35');
INSERT INTO `t_user` VALUES (3, '2025003', '123456', '王五', NULL, 1, NULL, NULL, NULL, NULL, NULL, 0.00, 0, '2025-11-25 00:27:35', '2025-11-25 00:27:35');
INSERT INTO `t_user` VALUES (4, '2025004', '123456', '赵六', NULL, 0, NULL, NULL, NULL, NULL, NULL, 0.00, 0, '2025-11-25 00:27:35', '2025-11-25 00:27:35');
INSERT INTO `t_user` VALUES (5, '2025005', '123456', '元木分', NULL, 0, NULL, NULL, NULL, NULL, NULL, 0.00, 0, '2025-11-25 00:27:35', '2025-11-25 00:27:35');

-- ============================
-- 4. 分类表
-- ============================
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `location` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地点',
  `begin_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结束时间',
  `cover` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面图',
  `detail` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详情',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分类表' ROW_FORMAT = Dynamic;

-- ============================
-- 5. 门票表
-- ============================
DROP TABLE IF EXISTS `t_ticket`;
CREATE TABLE `t_ticket`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '门票ID',
  `category_id` int NULL DEFAULT NULL COMMENT '分类ID',
  `ticket_date` date NULL DEFAULT NULL COMMENT '门票日期',
  `type` int NULL DEFAULT NULL COMMENT '类型：1成人票 2儿童票 3学生票',
  `price` double NULL DEFAULT NULL COMMENT '价格',
  `total_num` int NULL DEFAULT NULL COMMENT '总数量',
  `rest_num` int NULL DEFAULT NULL COMMENT '剩余数量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '门票表' ROW_FORMAT = Dynamic;

-- ============================
-- 6. 订单表
-- ============================
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `ticket_id` int NULL DEFAULT NULL COMMENT '门票ID',
  `ticket_num` int NULL DEFAULT NULL COMMENT '门票数量',
  `price` double NULL DEFAULT NULL COMMENT '总价',
  `status` int NULL DEFAULT 0 COMMENT '状态：0待支付 1已支付 2已取消',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ============================
-- 7. 公告表
-- ============================
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '公告内容',
  `category_id` int NULL DEFAULT NULL COMMENT '项目ID（NULL表示整体公告，有值表示针对某项目的公告）',
  `target_type` int NULL DEFAULT 0 COMMENT '接收对象类型：0全体 1特定部门 2特定角色',
  `target_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标ID列表，逗号分隔',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '公告表' ROW_FORMAT = Dynamic;


-- ============================
-- 8. 反馈表
-- ============================
DROP TABLE IF EXISTS `t_feedback`;
CREATE TABLE `t_feedback`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '反馈ID',
  `title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '反馈标题',
  `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '反馈内容',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `category_id` int NULL DEFAULT NULL COMMENT '分类ID',
  `reply` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复内容',
  `status` int NULL DEFAULT 0 COMMENT '状态：0待处理 1已处理',
  `is_read` tinyint NULL DEFAULT 0 COMMENT '是否已读',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '反馈表' ROW_FORMAT = Dynamic;

-- ============================
-- 9. 报修表
-- ============================
DROP TABLE IF EXISTS `t_repair`;
CREATE TABLE `t_repair`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '报修ID',
  `title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报修标题',
  `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报修内容',
  `area` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报修区域',
  `employee_id` int NULL DEFAULT NULL COMMENT '申请人ID',
  `employee_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人姓名',
  `repair_id` int NULL DEFAULT NULL COMMENT '维修员ID',
  `repair_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维修员姓名',
  `status` int NULL DEFAULT 0 COMMENT '状态：0待受理 1已受理 2已完成',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '受理时间',
  `complete_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_employee`(`employee_id`) USING BTREE,
  INDEX `idx_repair`(`repair_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '报修表' ROW_FORMAT = Dynamic;

-- 报修表测试数据
INSERT INTO `t_repair` (id, title, area, content, employee_id, employee_name, status, create_time, update_time)
VALUES (1, '过山车安全扣故障', '东区游乐场', '3号座位的安全扣无法正常扣紧，需要及时维修', 1, '张小青', 0, NOW(), NOW());

INSERT INTO `t_repair` (id, title, area, content, employee_id, employee_name, status, create_time, update_time)
VALUES (2, '摩天轮照明灯不亮', '西区观景区', '顶部的装饰灯有一半不亮了', 3, '张高级', 0, NOW(), NOW());



-- ============================
-- 10. 交易流水表
-- ============================
DROP TABLE IF EXISTS `t_transaction`;
CREATE TABLE `t_transaction`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '流水ID',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `type` int NULL DEFAULT NULL COMMENT '类型：1充值 2购票 3退票',
  `amount` double NULL DEFAULT NULL COMMENT '金额',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '交易流水表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ============================
-- 测试数据：游乐设施
-- ============================
INSERT INTO `t_category` VALUES (1, '过山车', '东区游乐场', '09:00', '18:00', '/files/category/rollercoaster.jpg', '惊险刺激的过山车体验', NOW(), NOW());
INSERT INTO `t_category` VALUES (2, '摩天轮', '西区观景区', '09:00', '21:00', '/files/category/ferriswheel.jpg', '浪漫的摩天轮观景', NOW(), NOW());
INSERT INTO `t_category` VALUES (3, '旋转木马', '中央广场', '09:00', '20:00', '/files/category/carousel.jpg', '适合全家游玩的旋转木马', NOW(), NOW());
INSERT INTO `t_category` VALUES (4, '海盗船', '南区冒险乐园', '10:00', '18:00', '/files/category/pirateship.jpg', '体验海盗船的刺激摇摆', NOW(), NOW());
INSERT INTO `t_category` VALUES (5, '碰碰车', '北区竞技场', '09:00', '19:00', '/files/category/bumpercars.jpg', '趣味碰碰车游戏', NOW(), NOW());

-- ============================
-- 测试数据：门票
-- ============================
INSERT INTO `t_ticket` VALUES (1, 1, '2025-12-10', 1, 120.00, 500, 500, NOW(), NOW());
INSERT INTO `t_ticket` VALUES (2, 1, '2025-12-10', 0, 60.00, 300, 300, NOW(), NOW());
INSERT INTO `t_ticket` VALUES (3, 2, '2025-12-10', 1, 80.00, 400, 400, NOW(), NOW());
INSERT INTO `t_ticket` VALUES (4, 2, '2025-12-10', 0, 40.00, 200, 200, NOW(), NOW());
INSERT INTO `t_ticket` VALUES (5, 3, '2025-12-10', 1, 50.00, 600, 600, NOW(), NOW());
INSERT INTO `t_ticket` VALUES (6, 3, '2025-12-10', 0, 30.00, 400, 400, NOW(), NOW());

-- ============================
-- 测试数据：交易流水
-- ============================
INSERT INTO `t_transaction` VALUES (1, 1, 1, 1000.00, '账户充值', '2025-12-01 10:00:00');
INSERT INTO `t_transaction` VALUES (2, 2, 1, 500.00, '账户充值', '2025-12-01 11:00:00');
INSERT INTO `t_transaction` VALUES (3, 1, 2, -120.00, '购买过山车成人票', '2025-12-02 14:00:00');
INSERT INTO `t_transaction` VALUES (4, 2, 2, -40.00, '购买摩天轮儿童票', '2025-12-02 15:00:00');

USE bt_carnie_mgr;

-- 插入报修测试数据
INSERT INTO `t_repair` (title, area, content, employee_id, employee_name, status, create_time, update_time)
VALUES
    ('过山车安全扣故障', '东区游乐场', '3号座位的安全扣无法正常扣紧，需要及时维修', 1, '张小青', 0, NOW(), NOW()),
    ('摩天轮照明灯不亮', '西区观景区', '顶部的装饰灯有一半不亮了', 3, '张高级', 0, NOW(), NOW()),
    ('旋转木马音响故障', '中央广场', '旋转木马的背景音乐播放不出来', 1, '张小青', 1, NOW(), NOW()),
    ('碰碰车地板破损', '北区竞技场', '碰碰车场地有几块地板翘起来了，存在安全隐患', 3, '张高级', 0, NOW(), NOW());

-- 验证插入结果
SELECT * FROM t_repair;