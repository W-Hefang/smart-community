/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 90500 (9.5.0)
 Source Host           : localhost:3306
 Source Schema         : smart_community

 Target Server Type    : MySQL
 Target Server Version : 90500 (9.5.0)
 File Encoding         : 65001

 Date: 15/05/2026 16:54:57
*/
CREATE DATABASE IF NOT EXISTS smart_community CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE smart_community;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement`  (
  `announcement_id` int NOT NULL AUTO_INCREMENT COMMENT '公告编号',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '公告内容',
  `announcement_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告类型',
  `priority` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '优先级',
  `publisher_id` int NOT NULL COMMENT '发布人 ID',
  `publisher_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发布人姓名',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '状态',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '截止时间',
  `view_count` int NULL DEFAULT NULL COMMENT '浏览次数',
  `attachment_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '附件 URL',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`announcement_id`) USING BTREE,
  INDEX `idx_publisher_id`(`publisher_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of announcement
-- ----------------------------

-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community`  (
  `community_id` int NOT NULL AUTO_INCREMENT COMMENT '社区编号',
  `community_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社区名称',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社区地址',
  `scale` int NULL DEFAULT NULL COMMENT '社区规模',
  `leader` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '社区负责人',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '描述信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`community_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社区信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of community
-- ----------------------------
INSERT INTO `community` VALUES (4, '山水社区', '杭州市西湖区山水路66号', 1800, '新负责人', '18888888888', '依山傍水，风景秀丽', '2026-05-09 16:33:51', '2026-05-09 16:55:20');
INSERT INTO `community` VALUES (6, '宁静家园', '上海市浦东新区宁静路12号', 800, '李四', '13900139002', '环境优美，安静宜居', '2026-05-09 16:38:00', '2026-05-09 16:38:00');
INSERT INTO `community` VALUES (7, '繁华里', '广州市天河区繁华大道99号', 2000, '王五', '13700137003', '市中心高端社区，交通便利', '2026-05-09 16:38:00', '2026-05-09 16:38:00');
INSERT INTO `community` VALUES (36, '阳光花园', '北京市朝阳区阳光路88号', 1200, '张三', '13800138001', '大型现代化居住社区，配套设施完善', '2026-05-09 16:55:20', '2026-05-09 16:55:20');
INSERT INTO `community` VALUES (41, '智慧城', '深圳市南山区科技园路1号', 3000, '钱七', '13500135005', '智慧化社区，科技感十足', '2026-05-15 15:59:16', '2026-05-15 15:59:16');
INSERT INTO `community` VALUES (47, '清风雅苑', '苏州市工业园区星湖街 123 号', 100, '周经理', '13912345678', '园区新建小区，自带幼儿园和社区医院，环境安静宜居', NULL, NULL);

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device`  (
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `device_id` int NOT NULL AUTO_INCREMENT COMMENT '设备 ID',
  `device_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备名称',
  `device_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备类型',
  `device_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备编号',
  `location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '安装位置',
  `community_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社区 ID',
  `status` int NULL DEFAULT NULL COMMENT '设备状态',
  `last_maintenance_time` datetime NULL DEFAULT NULL COMMENT '上次维护时间',
  PRIMARY KEY (`device_id`) USING BTREE,
  INDEX `idx_community_id`(`community_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of device
-- ----------------------------

-- ----------------------------
-- Table structure for fee
-- ----------------------------
DROP TABLE IF EXISTS `fee`;
CREATE TABLE `fee`  (
  `fee_id` int NOT NULL AUTO_INCREMENT COMMENT '费用编号',
  `fee_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '费用单号',
  `fee_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '费用类型',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用描述',
  `amount` decimal(10, 0) NOT NULL COMMENT '费用金额',
  `house_id` int NULL DEFAULT NULL COMMENT '房屋编号',
  `house_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '房屋地址',
  `owner_id` int NULL DEFAULT NULL COMMENT '业主编号',
  `owner_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '业主姓名',
  `owner_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '业主电话',
  `billing_month` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用所属月份',
  `due_date` datetime NULL DEFAULT NULL COMMENT '截止日期',
  `payment_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付状态',
  `payment_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `payment_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付方式',
  `late_fee` decimal(10, 0) NULL DEFAULT NULL COMMENT '滞纳金',
  `paid_amount` decimal(10, 0) NULL DEFAULT NULL COMMENT '实付金额',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`fee_id`) USING BTREE,
  UNIQUE INDEX `idx_fee_no`(`fee_no` ASC) USING BTREE,
  INDEX `idx_house_id`(`house_id` ASC) USING BTREE,
  INDEX `idx_owner_id`(`owner_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '费用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fee
-- ----------------------------

-- ----------------------------
-- Table structure for house
-- ----------------------------
DROP TABLE IF EXISTS `house`;
CREATE TABLE `house`  (
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `id` int NOT NULL AUTO_INCREMENT COMMENT '房屋编号',
  `community_id` int NOT NULL COMMENT '社区编号',
  `building` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '楼栋号',
  `unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单元号',
  `room` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '房间号',
  `area` decimal(10, 0) NULL DEFAULT NULL COMMENT '房屋面积',
  `owner_id` int NULL DEFAULT NULL COMMENT '业主编号',
  `status` int NULL DEFAULT NULL COMMENT '房屋状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_community_id`(`community_id` ASC) USING BTREE,
  INDEX `idx_owner_id`(`owner_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '房屋表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of house
-- ----------------------------

-- ----------------------------
-- Table structure for repair_order
-- ----------------------------
DROP TABLE IF EXISTS `repair_order`;
CREATE TABLE `repair_order`  (
  `repair_id` int NOT NULL AUTO_INCREMENT COMMENT '报修单ID',
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '报修单编号',
  `repair_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '报修类型',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '报修描述',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '报修地址',
  `user_id` int NOT NULL COMMENT '报修人ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '报修人姓名',
  `user_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '报修人电话',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '状态',
  `handler_id` int NULL DEFAULT NULL COMMENT '处理人ID',
  `handler_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理人姓名',
  `handler_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理人电话',
  `appointment_time` datetime NULL DEFAULT NULL COMMENT '预约时间',
  `complete_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `cost` decimal(10, 0) NULL DEFAULT NULL COMMENT '维修费用',
  `rating` int NULL DEFAULT NULL COMMENT '评分',
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '用户评价',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`repair_id`) USING BTREE,
  UNIQUE INDEX `idx_order_no`(`order_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_handler_id`(`handler_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '报修信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of repair_order
-- ----------------------------

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `role_id` int NOT NULL COMMENT '角色编号',
  `menu_id` int NOT NULL COMMENT '菜单权限的编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单的分配表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 1, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 2, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 3, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 4, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 5, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 6, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 7, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 8, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 9, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 10, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 11, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 12, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 13, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 14, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 15, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 16, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 17, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 18, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 19, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 20, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 21, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 22, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 23, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 24, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 25, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 26, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 27, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 28, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 29, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (1, 30, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 1, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 2, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 3, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 4, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 5, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 6, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 7, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 8, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 9, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 10, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 11, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 12, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 13, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 14, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 15, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 16, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 17, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 18, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 19, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 20, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 21, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 22, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 23, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 24, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 25, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 26, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 27, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 28, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 29, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (2, 30, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (3, 21, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (3, 22, '2026-05-12 11:22:18', NULL);
INSERT INTO `role_menu` VALUES (3, 25, '2026-05-12 11:22:18', NULL);

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu`  (
  `menu_id` int NOT NULL AUTO_INCREMENT COMMENT '菜单自增主键',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `menu_remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单中文名称',
  `menu_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单类型（menu/button）',
  `menu_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单的访问路由',
  `menu_icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单的图标',
  `pid` int NULL DEFAULT NULL COMMENT '父菜单ID（0代表一级菜单）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单访问权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES (1, 'userManager', '用户管理', 'menu', '/user', 'UserFilled', 0, NULL, NULL);
INSERT INTO `tb_menu` VALUES (2, 'userList', '住户列表', 'menu', '/user/list', 'List', 1, NULL, NULL);
INSERT INTO `tb_menu` VALUES (3, 'userAdd', '住户新增', 'button', '', 'Plus', 1, NULL, NULL);
INSERT INTO `tb_menu` VALUES (4, 'userEdit', '住户编辑', 'button', '', 'Edit', 1, NULL, NULL);
INSERT INTO `tb_menu` VALUES (5, 'userDel', '住户删除', 'button', '', 'Delete', 1, NULL, NULL);
INSERT INTO `tb_menu` VALUES (6, 'userAuth', '住户授权', 'menu', '/user/auth', 'Key', 1, NULL, NULL);
INSERT INTO `tb_menu` VALUES (7, 'deviceManager', '设备管理', 'menu', '/device', 'Monitor', 0, NULL, NULL);
INSERT INTO `tb_menu` VALUES (8, 'deviceList', '设备列表', 'menu', '/device/list', 'ElementPlus', 6, NULL, NULL);
INSERT INTO `tb_menu` VALUES (9, 'deviceStatus', '设备状态', 'menu', '/device/status', 'Watch', 6, NULL, NULL);
INSERT INTO `tb_menu` VALUES (10, 'deviceRepair', '设备报修', 'button', '', 'Warning', 6, NULL, NULL);
INSERT INTO `tb_menu` VALUES (11, 'deviceMaintain', '设备维护', 'menu', '/device/maintain', 'Tools', 6, NULL, NULL);
INSERT INTO `tb_menu` VALUES (12, 'deviceLog', '设备日志', 'menu', '/device/log', 'Document', 6, NULL, NULL);
INSERT INTO `tb_menu` VALUES (13, 'communityManager', '社区管理', 'menu', '/community/list', 'House', 0, NULL, NULL);
INSERT INTO `tb_menu` VALUES (14, 'communityInfo', '社区信息', 'menu', '/community/info', 'InfoFilled', 11, NULL, NULL);
INSERT INTO `tb_menu` VALUES (15, 'communityNotice', '社区公告', 'menu', '/community/notice', 'Bell', 11, NULL, NULL);
INSERT INTO `tb_menu` VALUES (16, 'communityActivity', '社区活动', 'menu', '/community/activity', 'Star', 11, NULL, NULL);
INSERT INTO `tb_menu` VALUES (17, 'communityVisitor', '访客管理', 'menu', '/community/visitor', 'User', 11, NULL, NULL);
INSERT INTO `tb_menu` VALUES (18, 'communityPark', '停车管理', 'menu', '/community/park', 'Wallet', 11, NULL, NULL);
INSERT INTO `tb_menu` VALUES (19, 'houseManager', '房屋管理', 'menu', '/house', 'HomeFilled', 0, NULL, NULL);
INSERT INTO `tb_menu` VALUES (20, 'houseList', '房屋列表', 'menu', '/house/list', 'Reading', 16, NULL, NULL);
INSERT INTO `tb_menu` VALUES (21, 'houseBuild', '楼栋管理', 'menu', '/house/build', 'OfficeBuilding', 16, NULL, NULL);
INSERT INTO `tb_menu` VALUES (22, 'houseUnit', '单元管理', 'menu', '/house/unit', 'Connection', 16, NULL, NULL);
INSERT INTO `tb_menu` VALUES (23, 'houseOwner', '业主绑定', 'button', '', 'Link', 16, NULL, NULL);
INSERT INTO `tb_menu` VALUES (24, 'houseRent', '房屋租赁', 'menu', '/house/rent', 'Ticket', 16, NULL, NULL);
INSERT INTO `tb_menu` VALUES (25, 'repairManager', '维修管理', 'menu', '/repair', 'Tools', 0, NULL, NULL);
INSERT INTO `tb_menu` VALUES (26, 'repairOrder', '维修工单', 'menu', '/repair/order', 'MessageBox', 21, NULL, NULL);
INSERT INTO `tb_menu` VALUES (27, 'repairAssign', '工单指派', 'button', '', 'UserFilled', 21, NULL, NULL);
INSERT INTO `tb_menu` VALUES (28, 'repairHandle', '工单处理', 'button', '', 'Check', 21, NULL, NULL);
INSERT INTO `tb_menu` VALUES (29, 'repairEvaluate', '维修评价', 'menu', '/repair/evaluate', 'StarFilled', 21, NULL, NULL);
INSERT INTO `tb_menu` VALUES (30, 'repairStatis', '维修统计', 'menu', '/repair/statis', 'DataAnalysis', 21, NULL, NULL);
INSERT INTO `tb_menu` VALUES (31, 'communityManager', '社区管理', 'menu', '/community/list', 'House', 0, '2026-05-15 15:42:27', '2026-05-15 15:42:27');
INSERT INTO `tb_menu` VALUES (32, 'communityList', '社区列表', 'menu', '/community/list', 'List', 31, '2026-05-15 15:42:27', '2026-05-15 15:42:27');
INSERT INTO `tb_menu` VALUES (33, 'communityStatistics', '社区统计', 'menu', '/community/statistics', 'DataAnalysis', 31, '2026-05-15 15:42:27', '2026-05-15 15:42:27');
INSERT INTO `tb_menu` VALUES (34, 'communityAdd', '社区新增', 'button', '', 'Plus', 31, '2026-05-15 15:42:27', '2026-05-15 15:42:27');
INSERT INTO `tb_menu` VALUES (35, 'communityEdit', '社区编辑', 'button', '', 'Edit', 31, '2026-05-15 15:42:27', '2026-05-15 15:42:27');
INSERT INTO `tb_menu` VALUES (36, 'communityDelete', '社区删除', 'button', '', 'Delete', 31, '2026-05-15 15:42:27', '2026-05-15 15:42:27');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `role_id` int NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色名称:ADMIN,PROPERTY,RESIDENT',
  `role_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色的中文名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1, 'ADMIN', '系统管理员', '2026-05-12 11:20:21', NULL);
INSERT INTO `tb_role` VALUES (2, 'PROPERTY', '物业工作人员', '2026-05-12 11:20:21', NULL);
INSERT INTO `tb_role` VALUES (3, 'RESIDENT', '住户', '2026-05-12 11:20:21', NULL);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户姓名',
  `user_phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户手机号',
  `user_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户密码',
  `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '身份证号',
  `user_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户类型',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `user_status` int NULL DEFAULT NULL COMMENT '用户状态',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表，包括住户，物业工作人员，系统管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'admin', '18888888888', '$2a$10$zI5szprFHUSMQQkTVP1OtuOoexWArYlYH6d0gvnntQbyGfNeTrmqy', NULL, '系统管理员', '2026-05-08 09:19:58', '2026-05-09 10:32:54', 1);
INSERT INTO `tb_user` VALUES (2, 'property', '17777777778', '$2a$10$bNk6ePeQgrsTgY0Ddpjf1u6wKucMkEyl7FzmQVnEoaHA7yd3P/WUO', NULL, '物业工作人员', '2026-05-08 09:23:57', '2026-05-09 10:32:57', 1);
INSERT INTO `tb_user` VALUES (4, 'resident1', '13512345679', '$2a$10$dk71eBQs4N/X0AOx1Cw9oOajw4CApK08H7.M.S9VIEkvnzVfuFDAG', NULL, '住户', '2026-05-09 10:31:54', '2026-05-09 10:31:54', 1);
INSERT INTO `tb_user` VALUES (5, 'resident2', '13512345676', '$2a$10$f4o7.U6EWrXQeaYUi1Hhpeji0mxZIPORfHxbJHrsyTZ1oJ4NDhDNm', NULL, '住户', '2026-05-09 10:37:37', '2026-05-09 10:37:37', 1);
INSERT INTO `tb_user` VALUES (6, 'resident3', '19999999999', '$2a$10$64Oc1WfA3d5Tfiq5Og7z6u1Th6qV5QlWofAP9Mdi2T6CZzh7ESqwC', '312000200010203456', '住户', NULL, NULL, 1);
INSERT INTO `tb_user` VALUES (7, 'resident4', '16666666666', '$2a$10$.78tqwmsnsLZORIrUwqa.ulzLIjuvoVW/5Mi2Wa2SSJaLx8p3C3Ca', '123456222223451234', '住户', NULL, NULL, 1);
INSERT INTO `tb_user` VALUES (8, 'resident5', '15920451249', '$2a$10$qlMkbi2ZL6M1p445.KLQy.2X2i3rDKt31q8Y8sDCDw/t5YyoCoc5.', '123456235412343456', '住户', NULL, NULL, 1);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int NOT NULL COMMENT '用户编号',
  `role_id` int NOT NULL COMMENT '角色编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, '2026-05-12 14:13:13', NULL);
INSERT INTO `user_role` VALUES (2, 2, '2026-05-12 14:13:13', NULL);
INSERT INTO `user_role` VALUES (4, 3, '2026-05-12 14:13:13', NULL);
INSERT INTO `user_role` VALUES (5, 3, '2026-05-12 14:13:13', NULL);

SET FOREIGN_KEY_CHECKS = 1;
