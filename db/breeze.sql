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

 Date: 06/11/2022 20:50:31
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
VALUES (1565314987957145600, 1111111111111111111, 'GS', '总公司', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (1565314987957145609, 1565314987957145600, 'DSB', '董事办', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (1581851971500371970, 1565314987957145600, 'IT', 'IT研发部门', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

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
    `system`        varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统类型',
    `param_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '入参',
    `result`        tinyint(1) NULL DEFAULT 0 COMMENT '结果 0 失败 1 成功',
    `result_msg`    varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
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
    `hidden`      tinyint(1) NULL DEFAULT 1,
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
        '/system/log/LogView', 'sys:log:list', 0, 0, 0, 7, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713986, 1564528653105573889, 1580357263003439106, '设计器', 'designer', 1, 'el-icon-user-solid',
        '/designer', '/system/designer/DesignerView', 'flow:dsigner:list', 0, 0, 0, 2, 'admin', 'admin',
        '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713987, 1564528653105573889, 1578702340683628545, '修改', NULL, 2, NULL, NULL, NULL, 'sys:user:edit',
        0, 0, 0, 4, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713988, 1564528653105573889, 1578702340683628545, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:user:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908290, 1564528653105573889, 1578702340683628545, '添加', NULL, 2, NULL, NULL, NULL, 'sys:user:save',
        0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908291, 1564528653105573889, 1578702340683628546, '修改', NULL, 2, NULL, NULL, NULL, 'sys:menu:edit',
        0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908292, 1564528653105573889, 1578702340683628546, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:menu:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908293, 1564528653105573889, 1578702340683628546, '添加', NULL, 2, NULL, NULL, NULL, 'sys:menu:save',
        0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340633296898, 1564528653105573889, 1578702340662657026, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:dept:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340633296899, 1564528653105573889, 1578702340662657026, '添加', NULL, 2, NULL, NULL, NULL, 'sys:dept:save',
        0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340641685505, 1564528653105573889, 1578702340654268418, '修改', NULL, 2, NULL, NULL, NULL, 'sys:role:edit',
        0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340641685506, 1564528653105573889, 1578702340654268418, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:role:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074114, 1564528653105573889, 1578702340654268418, '添加', NULL, 2, NULL, NULL, NULL, 'sys:role:save',
        0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074115, 1564528653105573889, 1578702340662657027, '修改', NULL, 2, NULL, NULL, NULL, 'sys:dict:edit',
        0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074116, 1564528653105573889, 1578702340662657027, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:dict:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074117, 1564528653105573889, 1578702340662657027, '添加', NULL, 2, NULL, NULL, NULL, 'sys:dict:save',
        0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268411, 1564528653105573889, 1578702340612325378, '清空表', NULL, 2, NULL, NULL, NULL,
        'sys:log:clear', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268412, 1564528653105573889, 1578702340666851329, '租户管理', 'tenant', 1, 'el-icon-user-solid',
        '/tenant', '/system/tenant/TenantView', 'sys:tenant:list', 0, 0, 0, 10, 'admin', 'admin', '2022-11-06 17:30:26',
        NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268416, 1564528653105573889, 1578702340666851329, '岗位管理', 'post', 1, 'el-icon-user-solid', '/post',
        '/system/post/PostView', 'sys:post:list', 0, 0, 0, 4, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268417, 1564528653105573889, 1578702340612325378, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:log:delete', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268418, 1564528653105573889, 1578702340666851329, '角色管理', 'role', 1, 'el-icon-user-solid', '/role',
        '/system/role/RoleView', 'sys:role:list', 0, 0, 0, 5, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340662657026, 1564528653105573889, 1578702340666851329, '部门管理', 'dept', 1, 'el-icon-user-solid', '/dept',
        '/system/dept/DeptView', 'sys:dept:list', 0, 0, 0, 9, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340662657027, 1564528653105573889, 1578702340666851329, '字典管理', 'dict', 1, 'el-icon-user-solid', '/dict',
        '/system/dict/DictView', 'sys:dict:list', 0, 0, 0, 6, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340666851329, 1564528653105573889, 1111111111111111111, '系统设置', '', 0, 'el-icon-s-tools', '/system', '',
        '', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340671045634, 1564528653105573889, 1578702340666851329, '平台管理', 'platform', 1, 'el-icon-s-home',
        '/platform', '/system/platform/PlatformView', 'sys:platform:list', 0, 0, 0, 1, 'admin', 'admin',
        '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340671045635, 1564528653105573889, 1578702340671045634, '添加', NULL, 2, NULL, NULL, NULL,
        'sys:platform:save', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340679434241, 1564528653105573889, 1578702340671045634, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:platform:edit', 0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340679434243, 1564528653105573889, 1578702340671045634, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:platform:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34',
        0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340683628545, 1564528653105573889, 1578702340666851329, '用户管理', 'user', 1, 'el-icon-user-solid', '/user',
        '/system/user/UserView', 'sys:user:list', 0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340683628546, 1564528653105573889, 1578702340666851329, '菜单管理', 'menu', 1, 'el-icon-s-order', '/menu',
        '/system/menu/MenuView', 'sys:menu:list', 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1580357263003439106, 1564528653105573889, 1111111111111111111, '工作流', NULL, 0, 'el-icon-s-tools', '/flow', NULL,
        NULL, 0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1580357773622202370, 1564528653105573889, 1580357263003439106, '流程分类', 'type', 1, 'el-icon-s-tools', '/type',
        '/flow/type/TypeView', 'flow:type:list', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1581843318345035778, 1564528653105573889, 1578702340662657026, '修改', NULL, 2, NULL, NULL, NULL, 'sys:dept:edit',
        0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1581965904601088001, 1564528653105573889, 1581966349440581634, '测试KeepAive', 'testKeep', 1, 'abc12312312',
        '/testKeep', '/test/testKeep/TestKeepView', 'keep:save', 0, 1, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26',
        NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1581965904601088002, 1564528653105573889, 1581966349440581634, '测试外部链接', NULL, 1, 'abc12312312',
        'http://ww.baidu.com', NULL, NULL, 1, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1581966349440581634, 1564528653105573889, 1111111111111111111, '相关测试', NULL, 0, 'abc12312312', '/test', NULL,
        NULL, 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582554585967800321, 1564528653105573889, 1111111111111111111, '监控平台', NULL, 0, 'abc12312312', '/monitor', NULL,
        NULL, 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582555155344568321, 1564528653105573889, 1582554585967800321, 'swagger', NULL, 1, 'abc12312312',
        'http://localhost:9000/swagger-ui/index.html', NULL, NULL, 1, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26',
        NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582558188828790785, 1564528653105573889, 1582554585967800321, '德鲁伊', NULL, 1, 'bcd12312',
        'http://localhost:9000/druid/login.html', NULL, NULL, 1, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL,
        NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582607135668621314, 1564528653105573889, 1581966349440581634, '掘金', NULL, 1, 'abc12312312',
        'https://juejin.cn/', NULL, NULL, 1, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582688861908611074, 1564528653105573889, 1581965904601088002, 'elementUI', NULL, 1, 'abc12312312',
        'https://element.eleme.cn', NULL, NULL, 1, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1586717542633123841, 1564528653105573889, 1578702340683628545, '用户角色配置', 'userRole', 1, NULL, '/userRole',
        '/system/user/role/UserRoleView', 'sys:role:list', 0, 1, 1, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL,
        NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1586717542633123843, 1564528653105573889, 1578702340662657027, '字典项', 'dictItem', 1, NULL, '/dictItem',
        '/system/dict/item/DictItemView', 'sys:item:list', 0, 0, 1, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL,
        NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1587692336744742913, 1580099387022348289, 1578702340683628545, '详情', NULL, 2, NULL, NULL, NULL, 'sys:user:info',
        0, 0, 0, 4, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048770, 1564528653105573889, 1578702340654268416, '添加', NULL, 2, NULL, NULL, NULL, 'sys:post:save',
        0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048771, 1564528653105573889, 1578702340654268416, '修改', NULL, 2, NULL, NULL, NULL, 'sys:post:edit',
        0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048772, 1564528653105573889, 1578702340654268416, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:post:delete', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048781, 1564528653105573889, 1578702340654268412, '添加', NULL, 2, NULL, NULL, NULL,
        'sys:tenant:save', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048782, 1564528653105573889, 1578702340654268412, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:tenant:edit', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048783, 1564528653105573889, 1578702340654268412, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:tenant:delete', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (9223372036854775807, 1564528653105573889, 1578702340666851329, '数据权限', 'permission', 1, 'abc12312312',
        '/permission', '/system/permission/PermissionView', 'sys:permission:list', 0, 0, 0, 8, 'admin', 'admin',
        '2022-11-06 17:30:26', NULL, NULL, '2022-11-06 17:30:34', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    `id`          bigint(22) NOT NULL,
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
    `code`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
    `operator`    varchar(22) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运算符号',
    `sql`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义SQL',
    `permissions` varchar(22) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限',
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限规则' ROW_FORMAT = Dynamic;

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
VALUES (1564528653105573889, '后台管理中心', 'managementCenter', '后台管理中心', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_platform`
VALUES (1580099387022348289, '微信小程序', 'mini', '微信小程序', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`
(
    `id`          bigint(22) NOT NULL,
    `post_code`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
    `post_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位编码',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`   int(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`   bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '平台' ROW_FORMAT = Dynamic;

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
VALUES (1565322827518140417, 'ROLE_ADMIN', '超级管理员', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role`
VALUES (1589074115103707138, '1', '1', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role`
VALUES (1589074141280358402, '1', '1', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

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
VALUES (1589184989101535234, 1578702340666851329, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989109923842, 1578702340671045634, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989109923843, 1578702340671045635, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989109923844, 1578702340679434241, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989114118146, 1578702340679434243, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989118312449, 1578702340683628545, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989126701058, 1586717542633123841, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989126701059, 1578702340624908290, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989130895362, 1578702340620713988, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989130895363, 1587692336744742913, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989139283970, 1578702340620713987, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989143478274, 1578702340683628546, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989151866881, 1578702340624908293, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989151866882, 1578702340624908291, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989160255490, 1578702340624908292, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989160255491, 1578702340654268416, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989160255492, 1589181822230048770, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989168644098, 1589181822230048771, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989168644099, 1589181822230048772, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989172838402, 1578702340654268418, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989172838403, 1578702340650074114, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989172838404, 1578702340641685505, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989181227009, 1578702340641685506, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989181227010, 1578702340662657027, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989181227011, 1578702340650074117, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989189615617, 1586717542633123843, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989193809921, 1578702340650074115, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989193809922, 1578702340650074116, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989193809923, 1578702340612325378, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989193809924, 1578702340654268411, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989202198529, 1578702340654268417, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989202198530, 9223372036854775807, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989210587137, 1578702340662657026, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989210587138, 1581843318345035778, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989218975745, 1578702340633296899, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989218975746, 1578702340633296898, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989218975747, 1578702340654268412, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989227364354, 1589181822230048781, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989227364355, 1589181822230048782, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989227364356, 1589181822230048783, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989235752962, 1582554585967800321, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989235752963, 1582555155344568321, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989244141569, 1582558188828790785, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989244141570, 1580357263003439106, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989244141571, 1580357773622202370, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989244141572, 1578702340620713986, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989252530177, 1581966349440581634, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989252530178, 1582607135668621314, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989252530179, 1581965904601088001, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989260918786, 1581965904601088002, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589184989260918787, 1582688861908611074, 1565322827518140417, 'admin', 'admin', '2022-11-06 17:16:42', NULL,
        NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `id`              bigint(22) NOT NULL,
    `permission_type` tinyint(1) NULL DEFAULT 0 COMMENT '规则类型 0 全部 1 部门 2 部门和子部门 3 个人 9999 自定义',
    `permission_id`   bigint(22) NULL DEFAULT NULL COMMENT '规则权限ID',
    `role_id`         bigint(22) NULL DEFAULT NULL COMMENT '角色ID',
    `create_by`       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time`     datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '创建时间',
    `update_by`       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time`     datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`       tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`       bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`
(
    `id`          bigint(22) NOT NULL,
    `tenant_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户名称',
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
VALUES (1, '1', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint(22) NOT NULL,
    `post_id`     bigint(22) NULL DEFAULT NULL COMMENT '岗位ID',
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
VALUES (1, NULL, 1565314987957145600, NULL, '超级管理员', 'admin', 'admin',
        '$2a$10$0oaHJPN7Pqq49dUqQgUVSug5l1ELUqjvuDZ5BIJwr2PJyGqzMjIca', 0, '371521123456789', '17812345678', 123123,
        '123456@qq.com', 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user`
VALUES (2, NULL, 1565314987957145609, NULL, '普通用户', 'user', 'user',
        '$2a$10$EjLxtgp2e3qPuzkJ/DI4jO9/T6k5Mc8xhVCguKLl7Ikdce19n3oOa', 1, '371521123456789', '17812345678', 123123,
        '123456@qq.com', 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

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
VALUES (1587683742213103617, 1, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, '2022-11-06 17:31:04', 0, NULL, 1);
INSERT INTO `sys_user_role`
VALUES (1589145210775298049, 2, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, '2022-11-06 17:31:04', 0, NULL, 1);
INSERT INTO `sys_user_role`
VALUES (1589145210804658178, 2, 1589074141280358402, NULL, NULL, NULL, NULL, NULL, '2022-11-06 17:31:04', 0, NULL, 1);
INSERT INTO `sys_user_role`
VALUES (1589145210813046786, 2, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, '2022-11-06 17:31:04', 0, NULL, 1);

-- ----------------------------
-- Table structure for test_data_permisson_salary
-- ----------------------------
DROP TABLE IF EXISTS `test_data_permisson_salary`;
CREATE TABLE `test_data_permisson_salary`
(
    `id`          bigint(22) NOT NULL,
    `salary`      decimal(11, 2) NULL DEFAULT NULL,
    `dept_id`     bigint(22) NULL DEFAULT NULL,
    `user_id`     bigint(22) NULL DEFAULT NULL,
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

SET
FOREIGN_KEY_CHECKS = 1;
