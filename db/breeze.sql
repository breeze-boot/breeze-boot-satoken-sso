/*
 Navicat Premium Data Transfer

 Source Server         : 本地127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 127.0.0.1:3366
 Source Schema         : breeze

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 21/10/2022 16:15:45
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
    `id`          bigint(22) NOT NULL,
    `parent_id`   bigint(22) NULL DEFAULT 0 COMMENT '上级部门ID',
    `dept_code`   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门编码',
    `dept_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`   tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`   bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept`
VALUES (1565314987957145600, 0, 'GS', '总公司', NULL, NULL, NULL, 'admin', 'admin', '2022-09-25 14:50:56', 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (1565314987957145609, 1565314987957145600, 'DSB', '董事办', NULL, NULL, NULL, 'admin', 'admin',
        '2022-09-25 14:50:54', 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (1581851971500371970, 1565314987957145600, 'IT', 'IT研发部门', 'admin', 'admin', '2022-10-17 11:37:54', NULL, NULL,
        '2022-09-25 14:50:54', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`          bigint(22) NOT NULL,
    `dict_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
    `dict_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '字典编码',
    `is_open`     tinyint(1) NULL DEFAULT 0 COMMENT '是否启用 0-关闭 1-启用',
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`   tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`   bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典' ROW_FORMAT = Dynamic;

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
    `id`          bigint(22) NOT NULL,
    `dict_id`     bigint(22) NULL DEFAULT NULL COMMENT '字典ID',
    `value`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '字典项的值',
    `label`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典项的名称',
    `sort`        tinyint(1) NULL DEFAULT NULL COMMENT '排序',
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`   tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`   bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典项' ROW_FORMAT = Dynamic;

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
    `id`            bigint(22) NOT NULL,
    `old_file_name` bigint(128) NULL DEFAULT NULL COMMENT '原文件名称',
    `new_file_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新文件名字',
    `use_id`        bigint(22) NULL DEFAULT NULL COMMENT '用户ID',
    `user_code`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户编码',
    `username`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `url`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'url',
    `create_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time`   datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
    `update_time`   datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`     tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`     bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `id`            bigint(22) NOT NULL,
    `system_module` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统模块',
    `log_title`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志标题',
    `log_type`      tinyint(1) NULL DEFAULT 0 COMMENT '日志类型 0 普通日志 1 登录日志',
    `request_type`  varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求类型  GET  POST  PUT DELETE ',
    `ip`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP',
    `do_type`       tinyint(1) NULL DEFAULT 3 COMMENT '操作类型 0 添加 1 删除 2 修改 3 查询',
    `browser`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器名称',
    `system`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统类型',
    `param_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '入参',
    `result`        tinyint(1) NULL DEFAULT 0 COMMENT '结果 0 失败 1 成功',
    `ex_msg`        varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `time`          varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time`   datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time`   datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`     tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`     bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log`
VALUES (1583108485980610561, '通用权限系统', '查询', 0, 'POST', '127.0.0.1', 3, '127.0.0.1', '管理中心',
        '[{\"current\":1,\"size\":10,\"platformName\":\"\",\"platformCode\":\"\"}]', 1, NULL, '0.0699615', 'admin',
        'admin', '2022-10-20 22:50:50', NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`          bigint(22) NOT NULL,
    `platform_id` bigint(22) NULL DEFAULT NULL COMMENT '平台ID',
    `parent_id`   bigint(22) NULL DEFAULT 0 COMMENT '上一级的菜单ID',
    `title`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名称',
    `type`        tinyint(1) NULL DEFAULT 0 COMMENT '类型 0 文件夹 1 菜单 2 按钮',
    `icon`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
    `path`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
    `component`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
    `permission`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
    `href`        tinyint(1) NULL DEFAULT 0 COMMENT '0 路由 1 外链',
    `keep_alive`  tinyint(1) NULL DEFAULT 0 COMMENT '0 不缓存 1 缓存',
    `sort`        int(11) NULL DEFAULT NULL COMMENT '顺序',
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`   tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`   bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu`
VALUES (1578702340612325378, 1564528653105573889, 1578702340666851329, '日志管理', 'log', 1, 'el-icon-user-solid', '/log',
        '/system/log/LogView', 'sys:log:list', 0, 0, 7, 'admin', 'admin', '2022-08-18 17:00:15', 'admin', 'admin',
        '2022-09-25 21:22:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713986, 1564528653105573889, 1580357263003439106, '设计器', 'designer', 1, 'el-icon-user-solid',
        '/designer', '/system/designer/DesignerView', 'flow:dsigner:list', 0, 0, 2, 'admin', 'admin',
        '2022-08-17 22:38:55', 'admin', 'admin', '2022-09-25 21:22:39', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713987, 1564528653105573889, 1578702340683628545, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:user:update', 0, 0, 2, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713988, 1564528653105573889, 1578702340683628545, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:user:delete', 0, 0, 3, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908290, 1564528653105573889, 1578702340683628545, '添加', NULL, 2, NULL, NULL, NULL, 'sys:user:save',
        0, 0, 1, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908291, 1564528653105573889, 1578702340683628546, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:menu:update', 0, 0, 2, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908292, 1564528653105573889, 1578702340683628546, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:menu:delete', 0, 0, 3, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908293, 1564528653105573889, 1578702340683628546, '添加', NULL, 2, NULL, NULL, NULL, 'sys:menu:save',
        0, 0, 1, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340633296898, 1564528653105573889, 1578702340662657026, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:dept:delete', 0, 0, 3, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340633296899, 1564528653105573889, 1578702340662657026, '添加', NULL, 2, NULL, NULL, NULL, 'sys:dept:save',
        0, 0, 2, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340641685505, 1564528653105573889, 1578702340654268418, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:role:update', 0, 0, 2, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340641685506, 1564528653105573889, 1578702340654268418, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:role:delete', 0, 0, 3, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074114, 1564528653105573889, 1578702340654268418, '添加', NULL, 2, NULL, NULL, NULL, 'sys:role:save',
        0, 0, 1, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074115, 1564528653105573889, 1578702340662657027, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:dict:update', 0, 0, 2, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074116, 1564528653105573889, 1578702340662657027, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:dict:delete', 0, 0, 3, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074117, 1564528653105573889, 1578702340662657027, '添加', NULL, 2, NULL, NULL, NULL, 'sys:dict:save',
        0, 0, 1, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268417, 1564528653105573889, 1578702340612325378, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:log:delete', 0, 0, 1, 'admin', 'admin', '2022-08-18 23:37:01', 'admin', 'admin', '2022-09-25 20:59:40', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268418, 1564528653105573889, 1578702340666851329, '角色管理', 'role', 1, 'el-icon-user-solid', '/role',
        '/system/role/RoleView', 'sys:role:list', 0, 0, 4, 'admin', 'admin', '2022-08-17 22:38:55', 'admin', 'admin',
        '2022-09-25 21:22:05', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340662657026, 1564528653105573889, 1578702340666851329, '部门管理', 'dept', 1, 'el-icon-user-solid', '/dept',
        '/system/dept/DeptView', 'sys:dept:list', 0, 0, 5, 'admin', 'admin', '2022-08-17 22:38:55', 'admin', 'admin',
        '2022-09-25 21:22:08', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340662657027, 1564528653105573889, 1578702340666851329, '字典管理', 'dict', 1, 'el-icon-user-solid', '/dict',
        '/system/dict/DictView', 'sys:dict:list', 0, 0, 6, 'admin', 'admin', '2022-08-18 16:58:15', 'admin', 'admin',
        '2022-09-25 21:22:11', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340666851329, 1564528653105573889, 0, '系统设置', '', 0, 'el-icon-s-tools', '/system', '', '', 0, 0, 1,
        'admin', 'admin', '2022-01-28 02:49:34', NULL, '0', '2022-09-25 20:59:40', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340671045634, 1564528653105573889, 1578702340666851329, '平台管理', 'platform', 1, 'el-icon-s-home',
        '/platform', '/system/platform/PlatformView', 'sys:platform:list', 0, 0, 1, 'admin', 'admin',
        '2022-02-26 17:31:51', NULL, '0', '2022-09-25 21:22:15', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340671045635, 1564528653105573889, 1578702340671045634, '添加', NULL, 2, NULL, NULL, NULL,
        'sys:platform:save', 0, 0, 1, 'admin', 'admin', '2022-08-18 19:47:23', 'admin', 'admin', '2022-09-25 20:59:40',
        0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340679434241, 1564528653105573889, 1578702340671045634, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:platform:update', 0, 0, 2, 'admin', 'admin', '2022-08-18 19:47:23', 'admin', 'admin',
        '2022-09-25 20:59:40', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340679434243, 1564528653105573889, 1578702340671045634, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:platform:delete', 0, 0, 3, 'admin', 'admin', '2022-08-18 18:57:09', 'admin', 'admin',
        '2022-09-25 20:59:40', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340683628545, 1564528653105573889, 1578702340666851329, '用户管理', 'user', 1, 'el-icon-user-solid', '/user',
        '/system/user/UserView', 'sys:user:list', 0, 0, 2, 'admin', 'admin', '2022-02-27 00:06:55', NULL, '0',
        '2022-09-25 21:22:17', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340683628546, 1564528653105573889, 1578702340666851329, '菜单管理', 'menu', 1, 'el-icon-s-order', '/menu',
        '/system/menu/MenuView', 'sys:menu:list', 0, 0, 3, 'admin', 'admin', '2022-02-27 00:08:30', NULL, '0',
        '2022-09-25 21:22:21', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1580357263003439106, 1564528653105573889, 0, '工作流', NULL, 0, 'el-icon-s-tools', '/flow', NULL, NULL, 0, 0, 2,
        'admin', 'admin', '2022-10-13 08:38:28', 'admin', 'admin', '2022-09-25 20:59:40', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1580357773622202370, 1564528653105573889, 1580357263003439106, '流程分类', 'type', 1, 'el-icon-s-tools', '/type',
        '/flow/type/TypeView', 'flow:type:list', 0, 0, 1, 'admin', 'admin', '2022-10-13 08:40:30', 'admin', 'admin',
        '2022-09-25 21:22:23', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1581843318345035778, 1564528653105573889, 1578702340662657026, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:dept:update', 0, 0, 1, 'admin', 'admin', '2022-10-17 11:03:31', NULL, NULL, '2022-09-25 20:59:40', 0, NULL,
        1);
INSERT INTO `sys_menu`
VALUES (1581965904601088001, 1564528653105573889, 1581966349440581634, '测试KeepAive', 'testKeep', 1, 'abc12312312',
        '/testKeep', '/test/testKeep/TestKeepView', 'keep:save', 0, 1, 3, 'admin', 'admin', '2022-10-17 19:10:38',
        'admin', 'admin', '2022-09-25 21:22:29', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1581965904601088002, 1564528653105573889, 1581966349440581634, '测试外部链接', NULL, 1, 'abc12312312',
        'http://ww.baidu.com', NULL, NULL, 1, 0, 3, 'admin', 'admin', '2022-10-17 19:10:38', 'admin', 'admin',
        '2022-09-25 22:03:36', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1581966349440581634, 1564528653105573889, 0, '相关测试', NULL, 0, 'abc12312312', '/test', NULL, NULL, 0, 0, 3,
        'admin', 'admin', '2022-10-17 19:12:24', 'admin', 'admin', '2022-09-25 21:01:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582554585967800321, 1564528653105573889, 0, '监控平台', NULL, 0, 'abc12312312', '/monitor', NULL, NULL, 0, 0, 1,
        'admin', 'admin', '2022-10-19 10:09:50', 'admin', 'admin', '2022-10-19 10:10:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582555155344568321, 1564528653105573889, 1582554585967800321, 'swagger', NULL, 1, 'abc12312312',
        'http://localhost:9000/swagger-ui/index.html', NULL, NULL, 1, 0, 1, 'admin', 'admin', '2022-10-19 10:12:06',
        'admin', 'admin', '2022-10-19 10:12:47', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582558188828790785, 1564528653105573889, 1582554585967800321, '德鲁伊', NULL, 1, 'bcd12312',
        'http://localhost:9000/druid/login.html', NULL, NULL, 1, 0, 1, 'admin', 'admin', '2022-10-19 10:24:09', 'admin',
        'admin', '2022-10-19 10:24:28', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582607135668621314, 1564528653105573889, 1581966349440581634, '掘金', NULL, 1, 'abc12312312',
        'https://juejin.cn/', NULL, NULL, 1, 0, 1, 'admin', 'admin', '2022-10-19 13:38:39', NULL, NULL, NULL, 0, NULL,
        1);
INSERT INTO `sys_menu`
VALUES (1582688861908611074, 1564528653105573889, 1581965904601088002, 'elementUI', NULL, 1, 'abc12312312',
        'https://element.eleme.cn', NULL, NULL, 1, 0, 1, 'admin', 'admin', '2022-10-19 19:03:24', 'admin', 'admin',
        '2022-10-19 19:04:50', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_platform
-- ----------------------------
DROP TABLE IF EXISTS `sys_platform`;
CREATE TABLE `sys_platform`
(
    `id`            bigint(22) NOT NULL,
    `platform_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台名称',
    `platform_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台编码',
    `description`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `create_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time`   datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time`   datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`     int(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`     bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '平台' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_platform
-- ----------------------------
INSERT INTO `sys_platform`
VALUES (1564528653105573889, '后台管理中心', 'managementCenter', '后台管理中心', 'admin', 'admin', '2022-02-27 00:04:17', NULL, '0',
        '2022-09-25 14:41:30', 0, NULL, 1);
INSERT INTO `sys_platform`
VALUES (1580099387022348289, '微信小程序', 'mini', '微信小程序', 'admin', 'admin', '2022-10-12 15:33:45', NULL, NULL,
        '2022-09-25 14:42:09', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint(22) NOT NULL,
    `role_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
    `role_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`   tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`   bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1565322827518140417, 'ROLE_ADMIN', '超级管理员', NULL, NULL, '2022-01-28 02:54:10', NULL, NULL,
        '2022-09-25 07:39:15', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`          bigint(22) NOT NULL,
    `menu_id`     bigint(22) NULL DEFAULT NULL COMMENT '菜单ID',
    `role_id`     bigint(22) NULL DEFAULT NULL COMMENT '角色ID',
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '创建时间',
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`   tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`   bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu`
VALUES (1581848214339317761, 1578702340666851329, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214385455105, 1578702340671045634, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214385455106, 1578702340679434243, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214385455107, 1578702340679434241, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214393843714, 1578702340671045635, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214393843715, 1578702340683628545, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214393843716, 1578702340620713987, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214393843717, 1578702340620713988, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214398038018, 1578702340624908290, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214398038019, 1578702340683628546, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214398038020, 1578702340624908291, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214398038021, 1578702340624908292, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214402232322, 1578702340624908293, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214402232323, 1578702340654268418, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214402232324, 1578702340641685505, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214402232325, 1578702340641685506, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214402232326, 1578702340650074114, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214402232327, 1578702340662657026, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214414815233, 1581843318345035778, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214414815234, 1578702340633296898, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214414815235, 1578702340633296899, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214414815236, 1578702340662657027, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214414815237, 1578702340650074115, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214414815238, 1578702340650074116, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214419009537, 1578702340650074117, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214419009538, 1578702340612325378, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214419009539, 1578702340654268417, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214419009540, 1580357263003439106, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214419009541, 1580357773622202370, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581848214419009542, 1578702340620713986, 1565322827518140417, 'admin', 'admin', '2022-09-25 14:54:34', NULL,
        NULL, '2022-09-25 14:54:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043371495425, 1578702340666851329, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043438604289, 1578702340671045634, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043446992897, 1578702340671045635, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043446992898, 1578702340679434241, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043446992899, 1578702340679434243, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043446992900, 1578702340683628545, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043446992901, 1578702340624908290, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043455381505, 1578702340620713987, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043455381506, 1578702340620713988, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043459575809, 1578702340683628546, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043459575810, 1578702340624908293, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043459575811, 1578702340624908291, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043459575812, 1578702340624908292, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043459575813, 1578702340654268418, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043459575814, 1578702340650074114, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043459575815, 1578702340641685505, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043467964417, 1578702340641685506, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043472158722, 1578702340662657026, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043472158723, 1581843318345035778, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043472158724, 1578702340633296899, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043472158725, 1578702340633296898, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043472158726, 1578702340662657027, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043472158727, 1578702340650074117, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043472158728, 1578702340650074115, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043480547330, 1578702340650074116, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043480547331, 1578702340612325378, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043480547332, 1578702340654268417, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043480547333, 1578702340620713986, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581853043480547334, 1580357263003439106, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:16', NULL,
        NULL, '2022-09-25 17:42:16', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244390580225, 1578702340671045634, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244436717570, 1578702340671045635, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244436717571, 1578702340679434241, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244445106178, 1578702340679434243, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244445106179, 1578702340683628545, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244445106180, 1578702340624908290, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244445106181, 1578702340620713987, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244449300482, 1578702340620713988, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244449300483, 1578702340683628546, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244449300484, 1578702340624908293, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244449300485, 1578702340624908291, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244449300486, 1578702340624908292, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244449300487, 1578702340654268418, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244449300488, 1578702340650074114, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244457689089, 1578702340641685505, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244457689090, 1578702340641685506, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244470272001, 1578702340662657026, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244478660609, 1581843318345035778, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244478660610, 1578702340633296899, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244478660611, 1578702340633296898, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244478660612, 1578702340662657027, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244478660613, 1578702340650074117, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244478660614, 1578702340650074115, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244478660615, 1578702340650074116, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244478660616, 1580357263003439106, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244478660617, 1578702340620713986, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895244487049217, 1578702340666851329, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:42:37', NULL,
        NULL, '2022-09-25 17:42:37', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895331992813570, 1578702340683628545, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332001202177, 1578702340624908290, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332001202178, 1578702340620713987, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332005396482, 1578702340620713988, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332005396483, 1578702340683628546, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332005396484, 1578702340624908293, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332005396485, 1578702340624908291, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332005396486, 1578702340624908292, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332005396487, 1578702340654268418, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332013785090, 1578702340650074114, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332013785091, 1578702340641685505, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332013785092, 1578702340641685506, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332013785093, 1578702340662657026, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332017979393, 1581843318345035778, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332017979394, 1578702340633296899, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332017979395, 1578702340633296898, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332017979396, 1578702340662657027, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332017979397, 1578702340650074117, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332017979398, 1578702340650074115, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332017979399, 1578702340650074116, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332017979400, 1578702340620713986, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332017979401, 1578702340666851329, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895332026368001, 1580357263003439106, 1565322827518140417, 'admin', 'admin', '2022-09-25 17:43:02', NULL,
        NULL, '2022-09-25 17:43:02', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438582661121, 1578702340671045634, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438591049730, 1578702340671045635, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438591049731, 1578702340679434241, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438591049732, 1578702340679434243, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438591049733, 1578702340683628545, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438591049734, 1578702340624908290, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438599438338, 1578702340620713987, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438599438339, 1578702340620713988, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438599438340, 1578702340683628546, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438599438341, 1578702340624908293, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438599438342, 1578702340624908291, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438599438343, 1578702340624908292, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438599438344, 1578702340654268418, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438599438345, 1578702340650074114, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438603632642, 1578702340641685505, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438603632643, 1578702340641685506, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438603632644, 1578702340662657026, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438603632645, 1581843318345035778, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438603632646, 1578702340633296899, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438603632647, 1578702340633296898, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438603632648, 1578702340662657027, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438603632649, 1578702340650074117, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438603632650, 1578702340650074115, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438603632651, 1578702340650074116, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438603632652, 1578702340620713986, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438603632653, 1578702340666851329, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581895438603632654, 1580357263003439106, 1565322827518140417, 'admin', 'admin', '2022-09-25 20:16:30', NULL,
        NULL, '2022-09-25 20:16:30', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705054646274, 1578702340666851329, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705067229185, 1578702340671045634, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705067229186, 1578702340671045635, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705067229187, 1578702340679434241, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705067229188, 1578702340679434243, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705079812098, 1578702340683628545, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705079812099, 1578702340624908290, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705079812100, 1578702340620713987, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705079812101, 1578702340620713988, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705079812102, 1578702340683628546, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705084006402, 1578702340624908293, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705084006403, 1578702340624908291, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705084006404, 1578702340624908292, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705084006405, 1578702340654268418, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705092395009, 1578702340650074114, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705092395010, 1578702340641685505, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705096589314, 1578702340641685506, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705100783617, 1578702340662657026, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705100783618, 1581843318345035778, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705109172226, 1578702340633296899, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705109172227, 1578702340633296898, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705109172228, 1578702340662657027, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705109172229, 1578702340650074117, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705109172230, 1578702340650074115, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705117560834, 1578702340650074116, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705117560835, 1580357263003439106, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705117560836, 1578702340620713986, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705121755138, 1581966349440581634, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581966705121755139, 1581965904601088001, 1565322827518140417, 'admin', 'admin', '2022-09-25 21:34:23', NULL,
        NULL, '2022-09-25 21:34:23', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760473456641, 1578702340671045634, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760595091457, 1578702340671045635, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760599285761, 1578702340679434241, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760599285762, 1578702340679434243, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760599285763, 1578702340683628545, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760607674370, 1578702340624908290, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760607674371, 1578702340620713987, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760607674372, 1578702340620713988, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760607674373, 1578702340683628546, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760607674374, 1578702340624908293, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760616062978, 1578702340624908291, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760616062979, 1578702340624908292, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760616062980, 1578702340654268418, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760616062981, 1578702340650074114, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760616062982, 1578702340641685505, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760624451586, 1578702340641685506, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760637034497, 1578702340662657026, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760645423105, 1581843318345035778, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760649617409, 1578702340633296899, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760649617410, 1578702340633296898, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760649617411, 1578702340662657027, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760649617412, 1578702340650074117, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760649617413, 1578702340650074115, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760649617414, 1578702340650074116, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760658006017, 1578702340620713986, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760658006018, 1581966349440581634, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760658006019, 1581965904601088001, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760658006020, 1581965904601088002, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760658006021, 1578702340666851329, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1581999760658006022, 1580357263003439106, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:15:27', NULL,
        NULL, '2022-09-26 04:15:27', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232901443585, 1578702340666851329, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232918220801, 1578702340671045634, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232918220802, 1578702340671045635, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232918220803, 1578702340679434241, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232918220804, 1578702340679434243, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232926609410, 1578702340683628545, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232926609411, 1578702340624908290, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232926609412, 1578702340620713987, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232926609413, 1578702340620713988, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232926609414, 1578702340683628546, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232926609415, 1578702340624908293, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232926609416, 1578702340624908291, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232934998017, 1578702340624908292, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232934998018, 1578702340654268418, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232934998019, 1578702340650074114, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232934998020, 1578702340641685505, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232934998021, 1578702340641685506, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232934998022, 1578702340662657026, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232934998023, 1581843318345035778, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232934998024, 1578702340633296899, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232943386626, 1578702340633296898, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232943386627, 1578702340662657027, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232943386628, 1578702340650074117, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232943386629, 1578702340650074115, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232943386630, 1578702340650074116, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232943386631, 1582554585967800321, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232943386632, 1582555155344568321, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232943386633, 1580357263003439106, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232943386634, 1578702340620713986, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232943386635, 1581966349440581634, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232951775234, 1581965904601088001, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582555232951775235, 1581965904601088002, 1565322827518140417, 'admin', 'admin', '2022-09-26 04:27:19', NULL,
        NULL, '2022-09-26 04:27:19', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217073233921, 1578702340671045634, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217085816833, 1578702340671045635, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217090011137, 1578702340679434241, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217090011138, 1578702340679434243, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217090011139, 1578702340683628545, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217098399745, 1578702340624908290, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217098399746, 1578702340620713987, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217098399747, 1578702340620713988, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217106788354, 1578702340683628546, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217106788355, 1578702340624908293, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217106788356, 1578702340624908291, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217106788357, 1578702340624908292, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217106788358, 1578702340654268418, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217115176961, 1578702340650074114, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217115176962, 1578702340641685505, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217115176963, 1578702340641685506, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217123565570, 1578702340662657026, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217123565571, 1581843318345035778, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217123565572, 1578702340633296899, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217131954177, 1578702340633296898, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217131954178, 1578702340662657027, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217131954179, 1578702340650074117, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217131954180, 1578702340650074115, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217140342785, 1578702340650074116, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217140342786, 1582554585967800321, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217140342787, 1582555155344568321, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217140342788, 1582558188828790785, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217140342789, 1578702340620713986, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217148731394, 1581966349440581634, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217148731395, 1581965904601088001, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217152925697, 1581965904601088002, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217152925698, 1578702340666851329, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582558217152925699, 1580357263003439106, 1565322827518140417, 'admin', 'admin', '2022-09-26 07:41:54', NULL,
        NULL, '2022-09-26 07:41:54', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185664724993, 1578702340666851329, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185681502210, 1578702340671045634, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185685696513, 1578702340671045635, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185685696514, 1578702340679434241, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185685696515, 1578702340679434243, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185689890817, 1578702340683628545, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185689890818, 1578702340624908290, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185689890819, 1578702340620713987, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185689890820, 1578702340620713988, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185698279426, 1578702340683628546, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185698279427, 1578702340624908293, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185698279428, 1578702340624908291, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185698279429, 1578702340624908292, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185698279430, 1578702340654268418, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185698279431, 1578702340650074114, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185702473729, 1578702340641685505, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185702473730, 1578702340641685506, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185702473731, 1578702340662657026, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185702473732, 1581843318345035778, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185710862338, 1578702340633296899, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185710862339, 1578702340633296898, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185710862340, 1578702340662657027, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185710862341, 1578702340650074117, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185710862342, 1578702340650074115, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185710862343, 1578702340650074116, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185719250946, 1582554585967800321, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185719250947, 1582555155344568321, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185719250948, 1582558188828790785, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185719250949, 1580357263003439106, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185723445249, 1578702340620713986, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185723445250, 1581966349440581634, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185723445251, 1582607135668621314, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185723445252, 1581965904601088001, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582607185727639554, 1581965904601088002, 1565322827518140417, 'admin', 'admin', '2022-09-26 10:51:11', NULL,
        NULL, '2022-09-26 10:51:11', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077072211969, 1578702340671045634, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077088989186, 1578702340671045635, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077088989187, 1578702340679434241, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077097377793, 1578702340679434243, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077097377794, 1578702340683628545, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077097377795, 1578702340624908290, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077105766401, 1578702340620713987, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077105766402, 1578702340620713988, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077105766403, 1578702340683628546, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077109960706, 1578702340624908293, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077109960707, 1578702340624908291, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077109960708, 1578702340624908292, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077109960709, 1578702340654268418, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077118349313, 1578702340650074114, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077126737921, 1578702340641685505, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077135126529, 1578702340641685506, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077143515137, 1578702340662657026, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077143515138, 1581843318345035778, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077143515139, 1578702340633296899, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077151903746, 1578702340633296898, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077151903747, 1578702340662657027, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077151903748, 1578702340650074117, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077151903749, 1578702340650074115, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077151903750, 1578702340650074116, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077151903751, 1582554585967800321, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077151903752, 1582555155344568321, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077151903753, 1582558188828790785, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077156098049, 1580357263003439106, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077156098050, 1580357773622202370, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077156098051, 1578702340620713986, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077156098052, 1581966349440581634, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077156098053, 1582607135668621314, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077156098054, 1581965904601088001, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077164486658, 1581965904601088002, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077164486659, 1582688861908611074, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1582689077164486660, 1578702340666851329, 1565322827518140417, 'admin', 'admin', '2022-10-19 19:04:16', NULL,
        NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`
(
    `id`          bigint(22) NOT NULL,
    `name`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户名称',
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`   tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`   bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant`
VALUES (1, '1', NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:39:24', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint(22) NOT NULL,
    `dept_id`     bigint(22) NOT NULL COMMENT '部门ID',
    `avatar`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
    `amount_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录账户名称',
    `user_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户编码',
    `username`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户姓名',
    `password`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
    `sex`         int(1) NULL DEFAULT NULL COMMENT '性别 0 女性 1 男性',
    `id_card`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
    `phone`       varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
    `open_id`     int(11) NULL DEFAULT NULL COMMENT '微信OpenID',
    `email`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮件',
    `is_lock`     int(1) NULL DEFAULT NULL COMMENT '锁定',
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`   tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`   bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uni_user_name`(`username`, `tenant_id`) USING BTREE COMMENT '同一个租户下，用户名唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, 1565314987957145600, NULL, '超级管理员', 'admin', 'admin',
        '$2a$10$0oaHJPN7Pqq49dUqQgUVSug5l1ELUqjvuDZ5BIJwr2PJyGqzMjIca', 0, '371521123456789', '17812345678', 123123,
        'breeze-cloud@foxmail.com', 0, NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:34:54', 0, NULL, 1);
INSERT INTO `sys_user`
VALUES (2, 1565314987957145600, NULL, '普通用户', 'user', 'user',
        '$2a$10$HmLMBj1Bj29yPuf2gHzcsODyGVsg2M667g5jPrboHBeitt1kWBLjm', 1, '371521123456789', '17812345678', NULL,
        'cloud@foxmail.com', 0, NULL, NULL, '2022-08-18 23:27:32', NULL, NULL, '2022-09-25 09:48:37', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`          bigint(22) NOT NULL,
    `user_id`     bigint(22) NULL DEFAULT NULL COMMENT '用户ID',
    `role_id`     bigint(22) NULL DEFAULT NULL COMMENT '角色ID',
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`   tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`   bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role`
VALUES (1, 1, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, '2022-09-25 07:39:24', 0, NULL, 1);

SET
FOREIGN_KEY_CHECKS = 1;
