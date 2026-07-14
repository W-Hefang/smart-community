USE smart_community;

-- 清空现有菜单数据
DELETE FROM role_menu;
DELETE FROM tb_menu;

-- 重置自增ID
ALTER TABLE tb_menu AUTO_INCREMENT = 1;
ALTER TABLE role_menu AUTO_INCREMENT = 1;

-- 插入菜单数据（按照图片中的侧边栏结构）
INSERT INTO tb_menu (menu_id, menu_name, menu_remark, menu_type, menu_url, menu_icon, pid) VALUES
(1, 'userManager', '用户管理', 'menu', '', 'User', 0),
(2, 'userList', '住户列表', 'menu', '/user/list', 'List', 1),
(3, 'userList2', '用户列表', 'menu', '/user/list', 'UserFilled', 1),
(4, 'deviceManager', '设备管理', 'menu', '/device/list', 'Monitor', 0),
(5, 'communityManager', '社区管理', 'menu', '', 'Home', 0),
(6, 'communityList', '小区列表', 'menu', '/community/list', 'Building', 5),
(7, 'communityStatistics', '统计分析', 'menu', '/community/statistics', 'BarChart', 5),
(8, 'houseManager', '房屋管理', 'menu', '/house/list', 'HomeFilled', 0),
(9, 'repairManager', '维修管理', 'menu', '/repair/list', 'Wrench', 0),
(10, 'announcementManager', '公告管理', 'menu', '/announcement/list', 'Bell', 0);

-- 更新角色菜单权限

-- 系统管理员（角色ID 1）：所有权限
INSERT INTO role_menu (role_id, menu_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10);

-- 物业工作人员（角色ID 2）：所有权限
INSERT INTO role_menu (role_id, menu_id) VALUES
(2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6), (2, 7), (2, 8), (2, 9), (2, 10);

-- 住户（角色ID 3）：仅公告管理和社区管理
INSERT INTO role_menu (role_id, menu_id) VALUES
(3, 5), (3, 6), (3, 10);

SELECT * FROM tb_menu;
SELECT * FROM role_menu;