/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 127.0.0.1:3306
 Source Schema         : breeze_admin

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 12/10/2022 16:18:10
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `id`          bigint(22) NOT NULL AUTO_INCREMENT,
    `dept_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
    `parent_id`   bigint(22) NULL DEFAULT 0,
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0),
    `is_delete`   tinyint(1) NULL DEFAULT 0,
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tenant_id`   bigint(22) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1565314987957145601 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept`
VALUES (2, '二级', 1565314987957145600, NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:38:19', 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (3, '三级', 2, NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:38:19', 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (4, '四级', 3, NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:38:19', 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (5, '六级', 4, NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:38:19', 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (6, '六级', 4, NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:38:19', 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (7, '六级', 4, NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:38:19', 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (8, '六级', 4, NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:38:19', 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (9, '六级', 4, NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:38:19', 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (10, '六级', 4, NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:38:19', 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (1565314987957145600, 'CTO/CEO', 0, NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:38:19', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`          bigint(22) NOT NULL AUTO_INCREMENT,
    `dict_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
    `dict_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
    `is_open`     tinyint(1) NULL DEFAULT 0 COMMENT '0-关闭 1-启用',
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0),
    `is_delete`   tinyint(1) NULL DEFAULT 0,
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tenant_id`   bigint(22) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1565595193934598146 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict`
VALUES (1, '1123', '01123', 1, NULL, NULL, '2022-08-19 20:28:49', NULL, NULL, '2022-09-25 07:38:30', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`
(
    `id`          bigint(22) NOT NULL AUTO_INCREMENT,
    `dict_id`     bigint(22) NULL DEFAULT NULL COMMENT '字典ID',
    `value`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
    `label`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `sort`        tinyint(1) NULL DEFAULT NULL,
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0),
    `is_delete`   tinyint(1) NULL DEFAULT 0,
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tenant_id`   bigint(22) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1566968028544348162 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item`
VALUES (1565616035716284417, 2, '13', '11', NULL, NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:38:35', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`
(
    `id`            bigint(22) NOT NULL AUTO_INCREMENT,
    `old_file_name` bigint(128) NULL DEFAULT NULL COMMENT '部门ID',
    `new_file_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录账户',
    `use_id`        bigint(22) NULL DEFAULT NULL,
    `user_code`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `username`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `url`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录密码',
    `create_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_name`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time`   datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_name`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_time`   datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0),
    `is_delete`     tinyint(1) NULL DEFAULT 0,
    `delete_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tenant_id`     bigint(22) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `id`            bigint(22) NOT NULL AUTO_INCREMENT,
    `system_module` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统模块',
    `log_title`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志标题',
    `log_type`      tinyint(1) NULL DEFAULT 0 COMMENT '日志类型 0 普通日志 1 登录日志',
    `request_type`  tinyint(1) NULL DEFAULT NULL COMMENT '请求类型 0 GET 1 POST 2 PUT 3 DELETE ',
    `ip`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP',
    `do_type`       tinyint(1) NULL DEFAULT 3 COMMENT '操作类型 0 添加 1 删除 2 修改 3 查询',
    `browser`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器名称',
    `system`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统类型',
    `result`        tinyint(1) NULL DEFAULT 0 COMMENT '结果 0 失败 1 成功',
    `create_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_name`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time`   datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_name`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_time`   datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0),
    `is_delete`     tinyint(1) NULL DEFAULT 0,
    `delete_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tenant_id`     bigint(22) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`          bigint(22) NOT NULL AUTO_INCREMENT,
    `platform_id` bigint(22) NULL DEFAULT NULL,
    `title`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `sort`        int(11) NULL DEFAULT NULL COMMENT '顺序',
    `icon`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `parent_id`   bigint(22) NULL DEFAULT 0 COMMENT '上一级的菜单ID',
    `permission`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限',
    `path`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `component`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径',
    `type`        varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0 文件夹 1 菜单 2 按钮',
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0),
    `is_delete`   tinyint(1) NULL DEFAULT 0,
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tenant_id`   bigint(22) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1580098825912553475 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu`
VALUES (1578702340612325378, 1564528653105573889, '日志管理', 'log', 8, 'el-icon-user-solid', 1578702340666851329,
        'sys:log:list', '/log', '/system/log/LogView', 'menu', NULL, NULL, '2022-08-18 17:00:15', NULL, '0',
        '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713986, 1564528653105573889, '设计器', 'designer', 4, 'el-icon-user-solid', 1578702340666851329, NULL,
        '/designer', '/system/designer/DesignerView', 'menu', NULL, NULL, '2022-08-17 22:38:55', NULL, '0',
        '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713987, 1564528653105573889, '修改', NULL, 11, NULL, 1578702340683628545, 'sys:user:update', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713988, 1564528653105573889, '删除', NULL, 12, NULL, 1578702340683628545, 'sys:user:delete', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908290, 1564528653105573889, '添加', NULL, 12, NULL, 1578702340683628545, 'sys:user:save', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908291, 1564528653105573889, '修改', NULL, 11, NULL, 1578702340683628546, 'sys:menu:update', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908292, 1564528653105573889, '删除', NULL, 12, NULL, 1578702340683628546, 'sys:menu:delete', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908293, 1564528653105573889, '添加', NULL, 12, NULL, 1578702340683628546, 'sys:menu:save', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340633296897, 1564528653105573889, '工作流', '', 1, 'el-icon-s-tools', 1578702340666851329, '', '', '',
        'menu', NULL, NULL, '2022-01-28 02:49:34', NULL, '0', '2022-09-25 07:38:49', 1, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340633296898, 1564528653105573889, '删除', NULL, 12, NULL, 1578702340662657026, 'sys:dept:delete', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340633296899, 1564528653105573889, '添加', NULL, 12, NULL, 1578702340662657026, 'sys:dept:save', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340641685505, 1564528653105573889, '修改', NULL, 11, NULL, 1578702340654268418, 'sys:role:update', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340641685506, 1564528653105573889, '删除', NULL, 12, NULL, 1578702340654268418, 'sys:role:delete', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074114, 1564528653105573889, '添加', NULL, 12, NULL, 1578702340654268418, 'sys:role:save', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074115, 1564528653105573889, '修改', NULL, 11, NULL, 1578702340662657027, 'sys:dict:update', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074116, 1564528653105573889, '删除', NULL, 12, NULL, 1578702340662657027, 'sys:dict:delete', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074117, 1564528653105573889, '添加', NULL, 12, NULL, 1578702340662657027, 'sys:dict:save', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268417, 1564528653105573889, '删除', NULL, 12, NULL, 1578702340612325378, 'sys:log:delete', NULL,
        NULL, 'btn', NULL, NULL, '2022-08-18 23:37:01', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268418, 1564528653105573889, '角色管理', 'role', 5, 'el-icon-user-solid', 1578702340666851329,
        'sys:role:list', '/role', '/system/role/RoleView', 'menu', NULL, NULL, '2022-08-17 22:38:55', NULL, '0',
        '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340662657026, 1564528653105573889, '部门管理', 'dept', 6, 'el-icon-user-solid', 1578702340666851329,
        'sys:dept:list', '/dept', '/system/dept/DeptView', 'menu', NULL, NULL, '2022-08-17 22:38:55', NULL, '0',
        '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340662657027, 1564528653105573889, '字典管理', 'dict', 7, 'el-icon-user-solid', 1578702340666851329,
        'sys:dict:list', '/dict', '/system/dict/DictView', 'menu', NULL, NULL, '2022-08-18 16:58:15', NULL, '0',
        '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340666851329, 1564528653105573889, '系统设置', '', 1, 'el-icon-s-tools', 0, '', '/system', '', 'folder',
        NULL, NULL, '2022-01-28 02:49:34', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340671045634, 1564528653105573889, '平台管理', 'platform', 1, 'el-icon-s-home', 1578702340666851329,
        'sys:platform:list', '/platform', '/system/platform/PlatformView', 'menu', NULL, NULL, '2022-02-26 17:31:51',
        NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340671045635, 1564528653105573889, '添加按钮', NULL, 11, NULL, 1578702340671045634, 'sys:platform:save',
        NULL, NULL, 'btn', NULL, NULL, '2022-08-18 19:47:23', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340679434241, 1564528653105573889, '修改按钮', NULL, 10, NULL, 4, 'sys:platform:update', NULL, NULL, 'btn',
        NULL, NULL, '2022-08-18 19:47:23', 'admin', 'admin', '2022-10-12 15:33:07', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340679434242, 1564528653105573889, '测试文件夹', 'folder', 1, 'icon', 0, NULL, '/folder', '/folder/index',
        'folder', NULL, NULL, NULL, NULL, '0', '2022-09-25 07:38:49', 1, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340679434243, 1564528653105573889, '删除按钮', NULL, 9, NULL, 1578702340671045634, 'sys:platform:delete',
        NULL, NULL, 'btn', NULL, NULL, '2022-08-18 18:57:09', NULL, '0', '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340683628545, 1564528653105573889, '用户管理', 'user', 2, 'el-icon-user-solid', 1578702340666851329,
        'sys:user:list', '/user', '/system/user/UserView', 'menu', NULL, NULL, '2022-02-27 00:06:55', NULL, '0',
        '2022-09-25 07:38:49', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340683628546, 1564528653105573889, '菜单管理', 'menu', 3, 'el-icon-s-order', 1578702340666851329,
        'sys:menu:list', '/menu', '/system/menu/MenuView', 'menu', NULL, NULL, '2022-02-27 00:08:30', NULL, '0',
        '2022-09-25 07:38:49', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_role`;
CREATE TABLE `sys_menu_role`
(
    `id`          bigint(22) NOT NULL AUTO_INCREMENT,
    `menu_id`     bigint(22) NULL DEFAULT NULL,
    `role_id`     bigint(22) NULL DEFAULT NULL,
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP (0),
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0),
    `is_delete`   tinyint(1) NULL DEFAULT 0,
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tenant_id`   bigint(22) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1578714139957030916 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu_role
-- ----------------------------
INSERT INTO `sys_menu_role`
VALUES (1578714139738927106, 1578702340666851329, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139843784705, 1578702340671045634, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139847979009, 1578702340679434243, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139856367617, 1578702340679434241, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139860561922, 1578702340671045635, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139868950529, 1578702340683628545, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139877339137, 1578702340624908291, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139885727745, 1578702340620713987, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139885727746, 1578702340624908292, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139894116354, 1578702340620713988, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139894116355, 1578702340624908293, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139894116356, 1578702340624908290, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139902504961, 1578702340633296898, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139902504962, 1578702340633296899, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139910893570, 1578702340683628546, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139910893571, 1578702340654268418, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139919282178, 1578702340641685505, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139927670785, 1578702340641685506, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139931865089, 1578702340650074114, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139931865090, 1578702340662657026, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139940253697, 1578702340662657027, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139940253698, 1578702340650074115, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139948642306, 1578702340650074116, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139948642307, 1578702340650074117, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139957030914, 1578702340612325378, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);
INSERT INTO `sys_menu_role`
VALUES (1578714139957030915, 1578702340654268417, 1565322827518140417, NULL, NULL, '2022-09-25 07:38:56', NULL, NULL,
        '2022-09-25 07:38:56', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_platform
-- ----------------------------
DROP TABLE IF EXISTS `sys_platform`;
CREATE TABLE `sys_platform`
(
    `id`            bigint(22) NOT NULL,
    `platform_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台名称',
    `platform_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台CODE',
    `description`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_name`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time`   datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_name`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_time`   datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0),
    `is_delete`     int(1) NULL DEFAULT 0,
    `delete_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tenant_id`     bigint(22) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '平台' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_platform
-- ----------------------------
INSERT INTO `sys_platform`
VALUES (1564528653105573889, '后台管理中心', 'managementCenter', '后台管理中心', NULL, 'admin', '2022-02-27 00:04:17', NULL, '0',
        '2022-09-25 07:39:01', 0, NULL, 1);
INSERT INTO `sys_platform`
VALUES (1580051730073628674, '1', '1', NULL, 'admin', 'admin', '2022-10-12 11:59:11', NULL, NULL, '2022-09-25 07:39:01',
        0, NULL, 1);
INSERT INTO `sys_platform`
VALUES (1580052539972653058, '22', '22', '22', 'admin', 'admin', '2022-10-12 12:27:42', NULL, NULL,
        '2022-09-25 07:39:01', 0, NULL, 1);
INSERT INTO `sys_platform`
VALUES (1580099387022348289, '微信小程序', 'mini', NULL, 'admin', 'admin', '2022-10-12 15:33:45', NULL, NULL, NULL, 0, NULL,
        1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint(22) NOT NULL AUTO_INCREMENT,
    `role_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `role_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0),
    `is_delete`   tinyint(1) NULL DEFAULT 0,
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tenant_id`   bigint(22) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1578702274250047491 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1565322827518140417, 'ROLE_ADMIN', '超级管理员', NULL, NULL, '2022-01-28 02:54:10', NULL, NULL,
        '2022-09-25 07:39:15', 0, NULL, 1);
INSERT INTO `sys_role`
VALUES (1578702274250047490, '123', '123123', NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:39:15', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`
(
    `id`          bigint(22) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(22) NULL DEFAULT NULL,
    `role_id`     bigint(22) NULL DEFAULT NULL,
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0),
    `is_delete`   tinyint(1) NULL DEFAULT 0,
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tenant_id`   bigint(22) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user`
VALUES (1, 1, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:39:24', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint(22) NOT NULL AUTO_INCREMENT,
    `dept_id`     bigint(22) NULL DEFAULT NULL COMMENT '部门ID',
    `amount_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录账户',
    `user_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `username`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `password`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录密码',
    `avatar`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
    `phone`       varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
    `sex`         int(1) NULL DEFAULT NULL COMMENT '性别',
    `id_card`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号 备用',
    `amount_type` int(1) NULL DEFAULT NULL COMMENT '类型',
    `is_lock`     int(1) NULL DEFAULT NULL COMMENT '锁定',
    `open_id`     int(11) NULL DEFAULT NULL COMMENT '微信ID',
    `email`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮件',
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0),
    `is_delete`   tinyint(1) NULL DEFAULT 0,
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tenant_id`   bigint(22) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1580050739311898627 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, 1565314987957145600, '超级管理员', 'admin', 'admin',
        '$2a$10$0oaHJPN7Pqq49dUqQgUVSug5l1ELUqjvuDZ5BIJwr2PJyGqzMjIca', NULL, '17812345678', 0, '371521123456789', NULL,
        0, 123123, 'breeze-cloud@foxmail.com', NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:34:54', 0, NULL, 1);
INSERT INTO `sys_user`
VALUES (2, 1565314987957145600, '普通用户', 'user', 'user', '$2a$10$HmLMBj1Bj29yPuf2gHzcsODyGVsg2M667g5jPrboHBeitt1kWBLjm',
        NULL, '17812345678', 1, '371521123456789', NULL, 0, NULL, 'cloud@foxmail.com', NULL, NULL,
        '2022-08-18 23:27:32', NULL, NULL, '2022-09-25 09:48:37', 0, NULL, 1);

SET
FOREIGN_KEY_CHECKS = 1;
