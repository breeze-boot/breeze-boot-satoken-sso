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

 Date: 13/11/2022 18:21:43
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
INSERT INTO `sys_dept`
VALUES (1591625973663952898, 1111111111111111111, '123', '123', 'admin', 'admin', '2022-11-13 10:56:18', NULL, NULL,
        NULL, 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (1591625991590408193, 1591625973663952898, '123123', '123', 'admin', 'admin', '2022-11-13 10:56:22', NULL, NULL,
        NULL, 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (1591626006719262722, 1591625973663952898, '123', '12321321', 'admin', 'admin', '2022-11-13 10:56:26', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (1591626029745991681, 1591625991590408193, '21312', '123', 'admin', 'admin', '2022-11-13 10:56:31', 'admin',
        'admin', '2022-11-13 12:00:56', 1, NULL, 1);
INSERT INTO `sys_dept`
VALUES (1591626045579489282, 1591626006719262722, '123', '123123', 'admin', 'admin', '2022-11-13 10:56:35', NULL, NULL,
        NULL, 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (1591642223282659329, 1591625991590408193, '23123', '2131', 'admin', 'admin', '2022-11-13 12:00:52', NULL, NULL,
        NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`          bigint(22) NOT NULL,
    `dept_id`     bigint(22) NULL DEFAULT NULL,
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
VALUES (12321312, 1565314987957145600, '1', '0', 0, NULL, NULL, '2022-11-13 17:38:23', NULL, NULL,
        '2022-11-13 17:40:30', 0, NULL, 1);

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
VALUES (1578702340612325378, 1564528653105573889, 1578702340666851329, '日志管理', 'log', 1, 'el-icon-s-comment', '/log',
        '/sys/log/LogView', 'sys:log:list', 0, 0, 0, 7, 'admin', 'admin', '2022-11-06 17:30:26', 'admin', 'admin',
        '2022-11-08 19:18:46', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713986, 1564528653105573889, 1580357263003439106, '设计器', 'designer', 1, 'el-icon-user-solid',
        '/designer', '/designer/designer/DesignerView', 'designer:show', 0, 0, 0, 2, 'admin', 'admin',
        '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 18:53:32', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713987, 1564528653105573889, 1578702340683628545, '修改', NULL, 2, NULL, NULL, NULL, 'sys:user:edit',
        0, 0, 0, 4, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:14:24', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713988, 1564528653105573889, 1578702340683628545, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:user:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:14:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908290, 1564528653105573889, 1578702340683628545, '添加', NULL, 2, NULL, NULL, NULL, 'sys:user:save',
        0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:14:24', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908291, 1564528653105573889, 1578702340683628546, '修改', NULL, 2, NULL, NULL, NULL, 'sys:menu:edit',
        0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908292, 1564528653105573889, 1578702340683628546, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:menu:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908293, 1564528653105573889, 1578702340683628546, '添加', NULL, 2, NULL, NULL, NULL, 'sys:menu:save',
        0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340633296898, 1564528653105573889, 1578702340662657026, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:dept:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340633296899, 1564528653105573889, 1578702340662657026, '添加', NULL, 2, NULL, NULL, NULL, 'sys:dept:save',
        0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340641685505, 1564528653105573889, 1578702340654268418, '修改', NULL, 2, NULL, NULL, NULL, 'sys:role:edit',
        0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340641685506, 1564528653105573889, 1578702340654268418, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:role:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074114, 1564528653105573889, 1578702340654268418, '添加', NULL, 2, NULL, NULL, NULL, 'sys:role:save',
        0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074115, 1564528653105573889, 1578702340662657027, '修改', NULL, 2, NULL, NULL, NULL, 'sys:dict:edit',
        0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 18:59:31', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074116, 1564528653105573889, 1578702340662657027, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:dict:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 18:59:32', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074117, 1564528653105573889, 1578702340662657027, '添加', NULL, 2, NULL, NULL, NULL, 'sys:dict:save',
        0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 18:59:32', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268411, 1564528653105573889, 1578702340612325378, '清空表', NULL, 2, NULL, NULL, NULL,
        'sys:log:clear', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268412, 1564528653105573889, 1578702340666851329, '租户管理', 'tenant', 1, 'el-icon-user-solid',
        '/tenant', '/sys/tenant/TenantView', 'sys:tenant:list', 0, 0, 0, 10, 'admin', 'admin', '2022-11-06 17:30:26',
        NULL, NULL, '2022-11-08 19:09:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268416, 1564528653105573889, 1578702340666851329, '岗位管理', 'post', 1, 'el-icon-user-solid', '/post',
        '/sys/post/PostView', 'sys:post:list', 0, 0, 0, 4, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-08 19:09:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268417, 1564528653105573889, 1578702340612325378, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:log:delete', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268418, 1564528653105573889, 1578702340666851329, '角色管理', 'role', 1, 'el-icon-s-ticket', '/role',
        '/sys/role/RoleView', 'sys:role:list', 0, 0, 0, 5, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-08 19:09:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340662657026, 1564528653105573889, 1578702340666851329, '部门管理', 'dept', 1, 'el-icon-s-check', '/dept',
        '/sys/dept/DeptView', 'sys:dept:list', 0, 0, 0, 9, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-08 19:09:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340662657027, 1564528653105573889, 1578702340666851329, '字典管理', 'dict', 1, 'el-icon-user-solid', '/dict',
        '/sys/dict/DictView', 'sys:dict:list', 0, 0, 0, 6, 'admin', 'admin', '2022-11-06 17:30:26', 'admin', 'admin',
        '2022-11-08 19:18:48', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340666851329, 1564528653105573889, 1111111111111111111, '系统设置', '', 0, 'el-icon-setting', '/sys', '', '',
        0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', 'admin', 'admin', '2022-11-08 19:19:09', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340671045634, 1564528653105573889, 1578702340666851329, '平台管理', 'platform', 1, 'el-icon-s-platform',
        '/platform', '/sys/platform/PlatformView', 'sys:platform:list', 0, 0, 0, 1, 'admin', 'admin',
        '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340671045635, 1564528653105573889, 1578702340671045634, '添加', NULL, 2, NULL, NULL, NULL,
        'sys:platform:save', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340679434241, 1564528653105573889, 1578702340671045634, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:platform:edit', 0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340679434243, 1564528653105573889, 1578702340671045634, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:platform:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33',
        0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340683628545, 1564528653105573889, 1578702340666851329, '用户管理', 'user', 1, 'el-icon-user-solid', '/user',
        '/sys/user/UserView', 'sys:user:list', 0, 0, 0, 2, 'admin', 'admin', '2022-11-06 17:30:26', 'admin', 'admin',
        '2022-11-08 20:31:14', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340683628546, 1564528653105573889, 1578702340666851329, '菜单管理', 'menu', 1, 'el-icon-folder-opened',
        '/menu', '/sys/menu/MenuView', 'sys:menu:list', 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-08 19:09:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1580357263003439106, 1564528653105573889, 1111111111111111111, '工作流', NULL, 0, 'el-icon-s-operation', '/flow',
        NULL, NULL, 0, 0, 0, 4, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-07 16:24:08', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1580357773622202370, 1564528653105573889, 1580357263003439106, '流程分类', 'type', 1, 'el-icon-s-tools', '/type',
        '/flow/type/TypeView', 'flow:type:list', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-06 17:30:34', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1581843318345035778, 1564528653105573889, 1578702340662657026, '修改', NULL, 2, NULL, NULL, NULL, 'sys:dept:edit',
        0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0, NULL, 1);
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
        NULL, 0, 0, 0, 5, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-07 16:24:11', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582554585967800321, 1564528653105573889, 1111111111111111111, '监控平台', NULL, 0, 'el-icon-camera', '/monitor',
        NULL, NULL, 0, 0, 0, 3, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-07 16:24:06', 0, NULL, 1);
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
VALUES (1586717542633123841, 1564528653105573889, 1578702340683628545, '用户角色配置', 'userRole', 1, NULL, '/sysRole',
        '/sys/user/role/UserRoleView', 'sys:role:list', 0, 1, 1, 1, 'admin', 'admin', '2022-11-06 17:30:26', 'admin',
        'admin', '2022-11-12 17:07:12', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1586717542633123843, 1564528653105573889, 1578702340662657027, '字典项', 'dictItem', 1, NULL, '/dictItem',
        '/sys/dict/item/DictItemView', 'sys:item:list', 0, 0, 1, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL,
        '2022-11-08 19:09:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1587692336744742913, 1580099387022348289, 1578702340683628545, '详情', NULL, 2, NULL, NULL, NULL, 'sys:user:info',
        0, 0, 0, 4, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:14:24', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048770, 1564528653105573889, 1578702340654268416, '添加', NULL, 2, NULL, NULL, NULL, 'sys:post:save',
        0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048771, 1564528653105573889, 1578702340654268416, '修改', NULL, 2, NULL, NULL, NULL, 'sys:post:edit',
        0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048772, 1564528653105573889, 1578702340654268416, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:post:delete', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048781, 1564528653105573889, 1578702340654268412, '添加', NULL, 2, NULL, NULL, NULL,
        'sys:tenant:save', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048782, 1564528653105573889, 1578702340654268412, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:tenant:edit', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048783, 1564528653105573889, 1578702340654268412, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:tenant:delete', 0, 0, 0, 1, 'admin', 'admin', '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589789746153263106, 1564528653105573889, 9223372036854775807, '保存', NULL, 2, NULL, NULL, NULL,
        'sys:permission:save', 0, 0, 0, 1, 'admin', 'admin', '2022-11-08 09:19:47', NULL, NULL, '2022-11-08 19:15:29',
        0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1591623647872049154, 1564528653105573889, 1578702340666851329, '123', '123', 1, NULL, '123', '123', '123', 0, 0,
        0, 1, 'admin', 'admin', '2022-11-13 10:47:03', 'admin', 'admin', '2022-11-13 10:47:16', 1, NULL, 1);
INSERT INTO `sys_menu`
VALUES (9223372036854775807, 1564528653105573889, 1578702340666851329, '数据权限', 'permission', 1, 'el-icon-s-operation',
        '/permission', '/sys/permission/PermissionView', 'sys:permission:list', 0, 0, 0, 8, 'admin', 'admin',
        '2022-11-06 17:30:26', NULL, NULL, '2022-11-08 19:09:33', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    `id`              bigint(22) NOT NULL,
    `permission_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
    `permission_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限编码',
    `permission_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识 ALL 全部 DEPT_LEVEL 部门 DEPT_AND_LOWER_LEVEL 部门和子部门 OWN 自己 \r\nDIY_DEPT 自定义部门 DIY 自定义',
    `operator`        varchar(22) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运算符号',
    `str_sql`         varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义SQL',
    `permissions`     varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限',
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission`
VALUES (1, '全部', NULL, 'ALL', 'OR', NULL, NULL, NULL, NULL, '2022-11-13 16:42:41', NULL, NULL, '2022-11-13 16:42:41', 0,
        NULL, 1);
INSERT INTO `sys_permission`
VALUES (2, '本级部门和下属部门', NULL, 'DEPT_AND_LOWER_LEVEL', 'OR', NULL, NULL, NULL, NULL, '2022-11-13 16:48:28', NULL, NULL,
        '2022-11-13 16:48:28', 0, NULL, 1);
INSERT INTO `sys_permission`
VALUES (3, '本级部门', NULL, 'DEPT_LEVEL', 'OR', NULL, NULL, NULL, NULL, '2022-11-13 16:42:41', NULL, NULL,
        '2022-11-13 16:42:41', 0, NULL, 1);
INSERT INTO `sys_permission`
VALUES (4, '自己', NULL, 'OWN', 'OR', NULL, NULL, NULL, NULL, '2022-11-13 16:42:41', NULL, NULL, '2022-11-13 16:42:41', 0,
        NULL, 1);
INSERT INTO `sys_permission`
VALUES (5, '自定义部门', NULL, 'DIY_DEPT', 'OR', NULL, NULL, NULL, NULL, '2022-11-13 16:42:41', NULL, NULL,
        '2022-11-13 16:42:41', 0, NULL, 1);
INSERT INTO `sys_permission`
VALUES (1591663064062078978, '本级部门和下属部门', '123', 'DEPT_AND_LOWER_LEVEL', 'OR', NULL,
        '1565314987957145600,1565314987957145609,1581851971500371970', 'admin', 'admin', '2022-11-13 16:50:23', NULL,
        NULL, '2022-11-13 16:50:23', 0, NULL, 1);
INSERT INTO `sys_permission`
VALUES (1591726829130383362, '12', '12', 'DIY', 'OR', '  ( a.id >= 1 ) ', NULL, 'admin', 'admin', '2022-11-13 17:37:04',
        NULL, NULL, NULL, 0, NULL, 1);

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
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post`
VALUES (1589476458991341569, '123', '123', NULL, 'admin', 'admin', '2022-11-07 12:34:54', 'admin', 'admin',
        '2022-11-07 12:49:35', 1, NULL, 1);
INSERT INTO `sys_post`
VALUES (1589480071138533378, '123123', '123', NULL, 'admin', 'admin', '2022-11-07 12:49:15', 'admin', 'admin',
        '2022-11-07 12:49:41', 1, NULL, 1);
INSERT INTO `sys_post`
VALUES (1589480140373909506, '123', '123', '123', 'admin', 'admin', '2022-11-07 12:49:31', 'admin', 'admin',
        '2022-11-07 12:49:41', 1, NULL, 1);
INSERT INTO `sys_post`
VALUES (1589491083396931585, '123123', '2312312', '123123', 'admin', 'admin', '2022-11-07 13:33:00', 'admin', 'admin',
        '2022-11-07 13:33:18', 1, NULL, 1);
INSERT INTO `sys_post`
VALUES (1589958947107217410, '1231', '123', '23', 'admin', 'admin', '2022-11-08 20:32:08', 'admin', 'admin',
        '2022-11-08 20:32:10', 1, NULL, 1);
INSERT INTO `sys_post`
VALUES (1591377257933819906, '123', '123', NULL, 'user', 'user', '2022-11-12 18:27:59', NULL, NULL, NULL, 0, NULL, 1);

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
VALUES (1589074115103707138, 'ROLE_SIMPLE', '普通用户', NULL, NULL, NULL, 'admin', 'admin', '2022-11-07 12:31:43', 0, NULL,
        1);
INSERT INTO `sys_role`
VALUES (1591282373843464193, 'ROLE_MINI', '小程序游客登录用户', 'admin', 'admin', '2022-11-12 12:10:57', NULL, NULL, NULL, 0,
        NULL, 1);

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
VALUES (1589789792248664066, 1578702340666851329, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792273829890, 1578702340671045634, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792273829891, 1578702340671045635, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792282218498, 1578702340679434241, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792290607106, 1578702340679434243, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792298995713, 1578702340683628545, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792307384322, 1586717542633123841, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792315772930, 1578702340624908290, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792324161537, 1578702340620713988, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792328355841, 1587692336744742913, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792336744449, 1578702340620713987, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792336744450, 1578702340683628546, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792345133057, 1578702340624908293, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792353521665, 1578702340624908291, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792361910274, 1578702340624908292, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792370298882, 1578702340654268416, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792378687489, 1589181822230048770, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792382881793, 1589181822230048771, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792382881794, 1589181822230048772, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792391270402, 1578702340654268418, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792399659009, 1578702340650074114, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792408047617, 1578702340641685505, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792416436226, 1578702340641685506, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792429019138, 9223372036854775807, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792433213441, 1589789746153263106, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792441602049, 1578702340662657026, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792449990658, 1581843318345035778, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792449990659, 1578702340633296899, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792449990660, 1578702340633296898, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792458379265, 1578702340654268412, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792466767873, 1589181822230048781, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792466767874, 1589181822230048782, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792475156481, 1589181822230048783, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792475156482, 1578702340666851321, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792475156483, 1578702340662657027, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792483545089, 1578702340650074117, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792483545090, 1586717542633123843, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792491933698, 1578702340650074115, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792508710914, 1578702340650074116, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792529682434, 1578702340612325378, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792546459650, 1578702340654268411, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792554848257, 1578702340654268417, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792563236866, 1582554585967800321, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792571625473, 1582555155344568321, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792575819777, 1582558188828790785, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792575819778, 1580357263003439106, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792584208386, 1580357773622202370, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792584208387, 1578702340620713986, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792592596993, 1581966349440581634, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792592596994, 1582607135668621314, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792596791298, 1581965904601088001, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792596791299, 1581965904601088002, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1589789792605179906, 1582688861908611074, 1565322827518140417, 'admin', 'admin', '2022-11-08 09:19:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275747020802, 1578702340666851329, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275772186626, 1578702340671045634, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275776380929, 1578702340671045635, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275784769538, 1578702340679434241, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275793158145, 1578702340679434243, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275793158146, 1578702340683628545, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275801546754, 1586717542633123841, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275809935362, 1578702340624908290, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275814129665, 1578702340620713988, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275814129666, 1587692336744742913, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275835101186, 1578702340620713987, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275847684098, 1578702340612325378, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275860267010, 1578702340654268411, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275868655618, 1578702340654268417, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591301275877044225, 9223372036854775807, 1589074115103707138, 'admin', 'admin', '2022-11-09 23:20:34', NULL,
        NULL, '2022-11-09 23:20:34', 1, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591371110837379073, 1578702340666851329, 1589074115103707138, 'admin', 'admin', '2022-11-12 18:03:34', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591371110870933506, 1578702340671045634, 1589074115103707138, 'admin', 'admin', '2022-11-12 18:03:34', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591371110879322114, 1578702340683628545, 1589074115103707138, 'admin', 'admin', '2022-11-12 18:03:34', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591371110891905025, 1578702340612325378, 1589074115103707138, 'admin', 'admin', '2022-11-12 18:03:34', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591371110900293634, 1578702340654268411, 1589074115103707138, 'admin', 'admin', '2022-11-12 18:03:34', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591371110908682242, 1578702340654268417, 1589074115103707138, 'admin', 'admin', '2022-11-12 18:03:34', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1591371110912876545, 9223372036854775807, 1589074115103707138, 'admin', 'admin', '2022-11-12 18:03:34', NULL,
        NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `id`            bigint(22) NOT NULL,
    `permission_id` bigint(22) NOT NULL COMMENT '规则权限ID',
    `role_id`       bigint(22) NULL DEFAULT NULL COMMENT '角色ID',
    `create_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time`   datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '创建时间',
    `update_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time`   datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`     tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`     bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission`
VALUES (0, 1591663064062078978, 1565322827518140417, NULL, NULL, '2022-11-13 17:19:09', NULL, NULL,
        '2022-11-13 17:19:09', 0, NULL, 1);
INSERT INTO `sys_role_permission`
VALUES (1, 1591726829130383362, 1565322827518140417, NULL, NULL, '2022-11-13 17:37:20', NULL, NULL,
        '2022-11-13 17:37:20', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`
(
    `id`          bigint(22) NOT NULL,
    `tenant_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户编码',
    `tenant_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户名称',
    `create_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`   tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant`
VALUES (1, '公司', 'GS', 'admin', 'admin', '2022-11-07 12:35:05', 'admin', 'admin', '2022-11-07 12:35:13', 0, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint(22) NOT NULL,
    `post_id`     bigint(22) NULL DEFAULT NULL COMMENT '岗位ID',
    `dept_id`     bigint(22) NULL DEFAULT NULL COMMENT '部门ID',
    `avatar`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
    `amount_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录账户名称',
    `user_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户编码',
    `username`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户姓名',
    `password`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
    `sex`         int(1) NULL DEFAULT NULL COMMENT '性别 0 女性 1 男性',
    `id_card`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
    `phone`       varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
    `open_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信OpenID',
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
    UNIQUE INDEX `uni_user_name`(`username`, `tenant_id`) USING BTREE COMMENT '同一个租户下，用户名唯一索引',
    UNIQUE INDEX `uni_open_id`(`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, NULL, 1565314987957145600, NULL, '超级管理员', 'admin', 'admin',
        '$2a$10$0oaHJPN7Pqq49dUqQgUVSug5l1ELUqjvuDZ5BIJwr2PJyGqzMjIca', 0, '371521123456789', '17812345678', '123123',
        'breeze-cloud1@foxmail.com', 0, NULL, NULL, NULL, NULL, NULL, '2022-11-09 01:24:01', 0, NULL, 1);
INSERT INTO `sys_user`
VALUES (2, NULL, 1565314987957145609, NULL, '普通用户', 'user', 'user',
        '$2a$10$rpIRU3nP7S3Dxb2hgCVSHuFni6nG9TEh/.UTjzhLEC3BKjEB/e7C6', 1, '371521123456789', '17812345671', '1231231',
        'breeze-cloud@foxmail.com', 0, NULL, NULL, NULL, 'admin', 'admin', '2022-11-12 15:02:26', 0, NULL, 1);
INSERT INTO `sys_user`
VALUES (1591379516805488641, 1591377257933819906, 1565314987957145600, NULL, '123', '123', '123',
        '$2a$10$KvHavudKCTZYGU/uS4eqeee0qPWHcdeyJuf69A2Lgaq/KT6G1YRjS', 1, '123', '123', NULL, '123', 1, 'user', 'user',
        '2022-11-12 18:36:58', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user`
VALUES (1591623309945364482, 1591377257933819906, 1565314987957145600, NULL, '123', '123', 'admin123',
        '$2a$10$XupEKkKgLrEiGfOw1qNBLOHUYb1eJxtHN21N.6zyZ6Gk9.IndIbsO', 1, '123', '123', NULL, '123', 1, 'admin',
        'admin', '2022-11-13 10:45:43', NULL, NULL, '2022-11-13 01:28:57', 1, NULL, 1);

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
VALUES (1591325527187525633, 2, 1589074115103707138, 'admin', 'admin', '2022-11-12 15:02:26', NULL, NULL, NULL, 0, NULL,
        1);
INSERT INTO `sys_user_role`
VALUES (1591325527250440193, 2, 1565322827518140417, 'admin', 'admin', '2022-11-12 15:02:26', NULL, NULL, NULL, 0, NULL,
        1);
INSERT INTO `sys_user_role`
VALUES (1591325527258828802, 2, 1591282373843464193, 'admin', 'admin', '2022-11-12 15:02:26', NULL, NULL, NULL, 0, NULL,
        1);
INSERT INTO `sys_user_role`
VALUES (1591379516927123458, 1591379516805488641, 1565322827518140417, 'user', 'user', '2022-11-12 18:36:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_role`
VALUES (1591379516952289281, 1591379516805488641, 1589074115103707138, 'user', 'user', '2022-11-12 18:36:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_role`
VALUES (1591379516969066498, 1591379516805488641, 1591282373843464193, 'user', 'user', '2022-11-12 18:36:58', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_role`
VALUES (1591623310083776514, 1591623309945364482, 1565322827518140417, 'admin', 'admin', '2022-11-13 10:45:43', NULL,
        NULL, '2022-11-13 01:28:50', 1, NULL, 1);
INSERT INTO `sys_user_role`
VALUES (1591623406103977985, 1591623309945364482, 1565322827518140417, 'admin', 'admin', '2022-11-13 10:46:06', NULL,
        NULL, '2022-11-13 01:28:57', 1, NULL, 1);
INSERT INTO `sys_user_role`
VALUES (1591623406112366594, 1591623309945364482, 1589074115103707138, 'admin', 'admin', '2022-11-13 10:46:06', NULL,
        NULL, '2022-11-13 01:28:57', 1, NULL, 1);
INSERT INTO `sys_user_role`
VALUES (1591623406120755201, 1591623309945364482, 1591282373843464193, 'admin', 'admin', '2022-11-13 10:46:06', NULL,
        NULL, '2022-11-13 01:28:57', 1, NULL, 1);

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
