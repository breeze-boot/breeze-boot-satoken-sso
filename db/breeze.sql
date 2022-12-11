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

 Date: 11/12/2022 18:03:29
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_banner
-- ----------------------------
DROP TABLE IF EXISTS `sys_banner`;
CREATE TABLE `sys_banner`
(
    `id`          bigint(22) NOT NULL,
    `title`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `url`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_data_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_permission`;
CREATE TABLE `sys_data_permission`
(
    `id`                   bigint(22) NOT NULL,
    `data_permission_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
    `data_permission_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限编码',
    `data_permission_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识 ALL 全部 DEPT_LEVEL 部门 DEPT_AND_LOWER_LEVEL 部门和子部门 OWN 自己 DIY_DEPT 自定义部门 DIY 自定义',
    `operator`             varchar(22) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运算符号',
    `str_sql`              varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义SQL',
    `data_permissions`     varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限',
    `create_by`            varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time`          datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '创建时间',
    `update_by`            varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time`          datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`            tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`            varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`            bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据权限规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_data_permission
-- ----------------------------
INSERT INTO `sys_data_permission`
VALUES (1, '全部', NULL, 'ALL', 'OR', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_data_permission`
VALUES (3, '本级部门', NULL, 'DEPT_LEVEL', 'OR', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_data_permission`
VALUES (4, '自己', NULL, 'OWN', 'OR', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_data_permission`
VALUES (5, '自定义部门', NULL, 'DIY_DEPT', 'OR', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_data_permission`
VALUES (1591663064062078978, '本级部门和下属部门', '123', 'DEPT_AND_LOWER_LEVEL', 'OR', NULL,
        '1565314987957145600,1565314987957145609,1581851971500371970', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_data_permission`
VALUES (1601813397580947458, '用户消息查看数据权限', 'USER_MSG', 'DEPT_LEVEL', 'OR',
        '  (  ( a.msg_code IS NOT NULL  ) AND ( a.id = 1 )  ) ', '1565314987957145609', 'admin', 'admin',
        '2022-12-11 04:22:01', 'admin', 'admin', '2022-12-11 16:19:51', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_data_permission_custom
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_permission_custom`;
CREATE TABLE `sys_data_permission_custom`
(
    `id`                 bigint(22) NOT NULL,
    `data_permission_id` bigint(22) NULL DEFAULT NULL COMMENT '数据权限ID',
    `compare`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '比较',
    `table_column`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名',
    `operator`           varchar(22) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运算符号',
    `conditions`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '条件',
    `create_by`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time`        datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '创建时间',
    `update_by`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time`        datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`          tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`          bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据权限自定义规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_data_permission_custom
-- ----------------------------
INSERT INTO `sys_data_permission_custom`
VALUES (1601854258998124545, 1601813397580947458, 'IS NOT NULL', 'msg_code', NULL, NULL, 'admin', 'admin',
        '2022-12-11 16:19:51', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_data_permission_custom`
VALUES (1601854259065233410, 1601813397580947458, '=', 'id', 'AND', '1', 'admin', 'admin', '2022-12-11 16:19:51', NULL,
        NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `id`          bigint(22) NOT NULL DEFAULT 1111111111111111111,
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
VALUES (1601579918477983745, 1581851971500371970, 'Java1', '研发组1', 'admin', 'admin', '2022-12-10 22:09:43', NULL, NULL,
        NULL, 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (1601579970948726786, 1581851971500371970, 'Java2', 'Java2', 'admin', 'admin', '2022-12-10 22:09:56', NULL, NULL,
        NULL, 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (1601814619176181761, 1565314987957145600, '123', '2', 'admin', 'admin', '2022-12-11 13:42:20', NULL, NULL, NULL,
        0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (1601814664223006722, 1565314987957145600, '123', '123', 'admin', 'admin', '2022-12-11 13:42:31', NULL, NULL,
        NULL, 0, NULL, 1);
INSERT INTO `sys_dept`
VALUES (1601814700566650882, 1601814619176181761, '123', '123', 'admin', 'admin', '2022-12-11 13:42:40', NULL, NULL,
        NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`          bigint(22) NOT NULL,
    `dict_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
    `dict_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '字典编码',
    `is_open`     tinyint(1) NULL DEFAULT 0 COMMENT '是否启用 0 关闭 1 启用',
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
VALUES (1599032827285213185, '性别', 'SEX', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599033063277727745, '菜单类型', 'MENU_TYPE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599034133752188930, '日志类型', 'LOG_TYPE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599034233434017794, '操作类型', 'DO_TYPE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599035056616509442, '日志结果', 'LOG_RESULT', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599035447399813121, '消息级别', 'MSG_LEVEL', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599035906529300481, '消息类型', 'MSG_TYPE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599036245466812417, '开关', 'OPEN', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599036494331645953, '缓存', 'KEEPALIVE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599036771814215681, '显示隐藏', 'HIDDEN', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599037134667649025, '路由外链', 'HREF', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599037528810590209, '存储方式', 'OSS_STYLE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599218032822394881, '结果', 'RESULT', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599288041217064962, '锁定', 'IS_LOCK', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599292998100058114, '读取状态', 'MARK_READ', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1599293985942536193, '消息发送方式', 'SEND_MSG_TYPE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1601793691449020417, '数据权限类型', 'DATA_PERMISSION_TYPE', 1, 'admin', 'admin', '2022-12-11 12:19:11', NULL, NULL,
        '2022-12-11 00:21:23', 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1601817524482482177, '数据权限运算符', 'COMPARE', 1, 'admin', 'admin', '2022-12-11 13:53:53', 'admin', 'admin',
        '2022-12-11 13:54:04', 0, NULL, 1);
INSERT INTO `sys_dict`
VALUES (1601875011403386881, '123', '123', 0, 'admin', 'admin', '2022-12-11 17:42:19', 'admin', 'admin',
        '2022-12-11 17:42:23', 1, NULL, 1);

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`
(
    `id`          bigint(22) NOT NULL,
    `dict_id`     bigint(22) NULL DEFAULT NULL COMMENT '字典ID',
    `key`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '字典项的值',
    `value`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典项的名称',
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
VALUES (1599033180131037186, 1599033063277727745, '0', '文件夹', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599033573409951745, 1599033063277727745, '1', '菜单', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599033675105046530, 1599033063277727745, '2', '按钮', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599033861437001729, 1599032827285213185, '1', '男', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599033925890871297, 1599032827285213185, '0', '女', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599034388589711362, 1599034133752188930, '0', ' 普通日志', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599034442968862721, 1599034133752188930, '1', ' 登录日志', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599034596094513154, 1599034233434017794, '0', '添加', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599034610313203714, 1599034233434017794, '1', '删除', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599034627035893761, 1599034233434017794, '2', '修改', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599034642189914113, 1599034233434017794, '3', '查询', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599035133351301122, 1599035056616509442, '0', '失败', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599035172446408705, 1599035056616509442, '1', ' 成功', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599035472632745985, 1599035447399813121, 'success', '正常消息', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL,
        1);
INSERT INTO `sys_dict_item`
VALUES (1599035484666204161, 1599035447399813121, 'warning', '警示消息', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL,
        1);
INSERT INTO `sys_dict_item`
VALUES (1599035496510918657, 1599035447399813121, 'info', '一般消息', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599035508733120513, 1599035447399813121, 'danger', '紧急消息', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL,
        1);
INSERT INTO `sys_dict_item`
VALUES (1599036005007364098, 1599035906529300481, '0', ' 通知', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599036025513316353, 1599035906529300481, '1', '公告', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599036294716329985, 1599036245466812417, '1', '开启', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599036318925852674, 1599036245466812417, '0 ', '关闭', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599036529471524866, 1599036494331645953, '0', '不缓存 ', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599036549079896065, 1599036494331645953, '1', '缓存', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599036842584707074, 1599036771814215681, '0', '显示', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599036909861343234, 1599036771814215681, '1', '隐藏', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599037293560467457, 1599037134667649025, '1', '外链', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599037318663376897, 1599037134667649025, '0', '路由', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599037823330422786, 1599037528810590209, '0', '本地', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599037849897144321, 1599037528810590209, '1', 'MINIO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599218200087044097, 1599218032822394881, '1', '成功', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599218217967362049, 1599218032822394881, '0', '失败', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599288066458386434, 1599288041217064962, '0', '正常', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599288094061101058, 1599288041217064962, '1', '锁定', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599293037463601154, 1599292998100058114, '1', '已读', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599293192749318145, 1599292998100058114, '0', '未读', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599294372594450433, 1599293985942536193, '1', '部门的用户', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1599294501808373761, 1599293985942536193, '2', '自定义部门的用户', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL,
        1);
INSERT INTO `sys_dict_item`
VALUES (1601793891890614273, 1601793691449020417, 'ALL', '全部', NULL, 'admin', 'admin', '2022-12-11 12:19:58', 'admin',
        'admin', '2022-12-11 12:20:11', 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1601793991845072897, 1601793691449020417, 'OWN', '自己', NULL, 'admin', 'admin', '2022-12-11 12:20:22', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1601794088121126914, 1601793691449020417, 'DEPT_AND_LOWER_LEVEL', '本级以及下属部门', NULL, 'admin', 'admin',
        '2022-12-11 12:20:45', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1601794253766774785, 1601793691449020417, 'DEPT_LEVEL', '本级部门', NULL, 'admin', 'admin', '2022-12-11 12:21:25',
        NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1601794312206012418, 1601793691449020417, 'DIY_DEPT', '自定义部门', NULL, 'admin', 'admin', '2022-12-11 12:21:39',
        NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1601825250998652930, 1601817524482482177, '>', '大于', NULL, 'admin', 'admin', '2022-12-11 14:24:35', 'admin',
        'admin', '2022-12-11 14:26:44', 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1601825929611874305, 1601817524482482177, ' >=', '大于等于', NULL, 'admin', 'admin', '2022-12-11 14:27:17', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1601826138823757825, 1601817524482482177, '<', '小于', NULL, 'admin', 'admin', '2022-12-11 14:28:07', NULL, NULL,
        NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1601826169953882113, 1601817524482482177, '<=', '小于等于', NULL, 'admin', 'admin', '2022-12-11 14:28:14', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1601826214673551361, 1601817524482482177, '!=', '不等于', NULL, 'admin', 'admin', '2022-12-11 14:28:25', NULL,
        NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1601826299918585857, 1601817524482482177, '=', '等于', NULL, 'admin', 'admin', '2022-12-11 14:28:45', NULL, NULL,
        NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1601826517330333697, 1601817524482482177, 'IS NOT NULL', '不等于空', NULL, 'admin', 'admin', '2022-12-11 14:29:37',
        'admin', 'admin', '2022-12-11 14:29:47', 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1601826978187874306, 1601817524482482177, 'IS NULL', '等于空', NULL, 'admin', 'admin', '2022-12-11 14:31:27',
        'admin', 'admin', '2022-12-11 14:31:34', 0, NULL, 1);
INSERT INTO `sys_dict_item`
VALUES (1601844349157920769, 1601793691449020417, 'DIY', 'SQL自定义', NULL, 'admin', 'admin', '2022-12-11 15:40:28',
        'admin', 'admin', '2022-12-11 15:42:38', 1, NULL, 1);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`
(
    `id`                 bigint(22) NOT NULL,
    `title`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件标题',
    `original_file_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原文件名称',
    `content_type`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '格式',
    `new_file_name`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新文件名字',
    `path`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件存放路径',
    `oss_style`          tinyint(1) NOT NULL COMMENT '存储方式 0 本地 1 minio',
    `create_by`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time`        datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
    `update_time`        datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`          tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`          bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file`
VALUES (1601793171963498497, '123', 'logo.jpg', NULL, '3ecf4a1de28c4b19916c1e23646f4a64.jpg', '11', 0, 'admin', 'admin',
        '2022-12-11 12:17:07', 'admin', 'admin', '2022-12-11 12:17:34', 1, NULL, 1);
INSERT INTO `sys_file`
VALUES (1601801424969531393, '123', 'logo.jpg', NULL, '0f6e553849804d06b3f3557dd9f0e581.jpg', '11', 0, 'admin', 'admin',
        '2022-12-11 12:49:54', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_file`
VALUES (1601874412230283266, '用户头像', 'logo.jpg', NULL, 'dbefe07d4cbc4b208f27b1df8f35e04f.jpg', '11', 0, 'admin',
        'admin', '2022-12-11 17:39:56', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_file`
VALUES (1601875657418477570, '用户头像', 'logo.jpg', NULL, '063084b88aa741f187e5d67815b9596c.jpg', '11', 0, 'admin',
        'admin', '2022-12-11 17:44:53', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_file`
VALUES (1601876040106774530, '用户头像', 'logo.jpg', NULL, '68db874d095d4a138092b8045f72bb92.jpg', '11', 0, 'admin',
        'admin', '2022-12-11 17:46:24', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_file`
VALUES (1601876107265970178, '用户头像', 'logo.jpg', NULL, '1cb827d7d7fb4c8d91ea0af93eef6e1d.jpg', '11', 0, 'admin',
        'admin', '2022-12-11 17:46:40', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_file`
VALUES (1601876208134787073, '用户头像', 'logo.jpg', NULL, 'b4db34b80de144258f0de9e06bdea0f3.jpg', '11', 0, 'admin',
        'admin', '2022-12-11 17:47:04', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_file`
VALUES (1601877228986449921, '用户头像', 'logo.jpg', NULL, 'a993bc8d99c847b0add2417998ca057e.jpg', '11', 0, 'admin',
        'admin', '2022-12-11 17:51:07', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_file`
VALUES (1601877284841996290, '用户头像', 'logo.jpg', NULL, '46accfce4e2a4b0398b801348c562eaa.jpg', '11', 0, 'admin',
        'admin', '2022-12-11 17:51:21', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_file`
VALUES (1601877685288976385, '用户头像', 'logo.jpg', NULL, 'b34117dfa8814dcb969086894851b072.jpg', '11', 0, 'admin',
        'admin', '2022-12-11 17:52:56', NULL, NULL, NULL, 0, NULL, 1);

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
    `result_msg`    varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结果信息',
    `time`          varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用时',
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
VALUES (1601383768370180097, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"ALL\",\"operator\":\"OR\",\"dataPermissions\":[],\"dataPermissionDiy\":[{\"column\":\"id\",\"conditions\":\"12321\",\"compare\":\">\",\"operator\":null},{\"column\":\"title\",\"conditions\":\"123123\",\"compare\":\">=\",\"operator\":\"AND\"}]}]',
        0,
        'com.breeze.boot.system.mapper.SysDataPermissionCustomMapper.insert (batch index #1) failed. Cause: java.sql.BatchUpdateException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'column,
        conditions, create_by, create_name, create_time, update_time,
        tenant_id) \' at line 1\n; bad SQL grammar []; nested exception is java.sql.BatchUpdateException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'column, conditions, create_by, create_name, create_time, update_time, tenant_id)\' at line 1', '0.0139829', 'admin', 'admin', '2022-12-10 09:10:17', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601383949299871746, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"ALL\",\"operator\":\"OR\",\"dataPermissions\":[],\"dataPermissionDiy\":[{\"column\":\"id\",\"conditions\":\"12321\",\"compare\":\">\",\"operator\":null},{\"column\":\"title\",\"conditions\":\"123123\",\"compare\":\">=\",\"operator\":\"AND\"}]}]',
        0,
        'com.breeze.boot.system.mapper.SysDataPermissionCustomMapper.insert (batch index #1) failed. Cause: java.sql.BatchUpdateException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'column,
        conditions, create_by, create_name, create_time, update_time,
        tenant_id) \' at line 1\n; bad SQL grammar []; nested exception is java.sql.BatchUpdateException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'column, conditions, create_by, create_name, create_time, update_time, tenant_id)\' at line 1', '2.109E-4', 'admin', 'admin', '2022-12-10 09:11:00', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601385744847859714, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"124312\",\"dataPermissionCode\":\"123123\",\"dataPermissionType\":\"ALL\",\"operator\":\"OR\",\"dataPermissions\":[],\"dataPermissionDiy\":[{\"tableColumn\":\"id\",\"conditions\":\"12\",\"compare\":\">\",\"operator\":null},{\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">=\",\"operator\":\"AND\"}]}]',
        1, NULL, '0.0073161', 'admin', 'admin', '2022-12-10 09:18:09', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601385773767585793, '权限系统', '数据权限信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[[1601385700157550594]]', 1, NULL, '2.407E-4', 'admin', 'admin', '2022-12-10 09:18:15', NULL, NULL, NULL, 0,
        NULL, 1);
INSERT INTO `sys_log`
VALUES (1601385785176092673, '权限系统', '数据权限信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[[1601383882774016001]]', 1, NULL, '1.41E-4', 'admin', 'admin', '2022-12-10 09:18:18', NULL, NULL, NULL, 0,
        NULL, 1);
INSERT INTO `sys_log`
VALUES (1601385793145270273, '权限系统', '数据权限信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[[1601383767724257282]]', 1, NULL, '1.897E-4', 'admin', 'admin', '2022-12-10 09:18:20', NULL, NULL, NULL, 0,
        NULL, 1);
INSERT INTO `sys_log`
VALUES (1601388169302372353, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"234\",\"dataPermissionCode\":\"234\",\"dataPermissionType\":\"OWN\",\"operator\":\"AND\",\"dataPermissions\":[],\"dataPermissionDiy\":[{\"tableColumn\":\"id\",\"conditions\":\"234\",\"compare\":\">\",\"operator\":null},{\"tableColumn\":\"data_permission_name\",\"conditions\":\"23423\",\"compare\":\">=\",\"operator\":\"AND\"}]}]',
        1, NULL, '2.318E-4', 'admin', 'admin', '2022-12-10 09:27:47', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601388249589739522, '权限系统', '数据权限信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[[1601388155066904578]]', 1, NULL, '1.196E-4', 'admin', 'admin', '2022-12-10 09:28:06', NULL, NULL, NULL, 0,
        NULL, 1);
INSERT INTO `sys_log`
VALUES (1601494756054528002, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"ALL\",\"operator\":\"OR\",\"dataPermissions\":[],\"dataPermissionDiy\":[{\"tableColumn\":\"id\",\"conditions\":\"1\",\"compare\":\">\",\"operator\":null},{\"tableColumn\":\"title\",\"conditions\":\"123\",\"compare\":\">=\",\"operator\":\"AND\"}]}]',
        1, NULL, '0.0073805', 'admin', 'admin', '2022-12-10 16:31:19', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601509024460173314, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionDiy\":[{\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":null},{\"tableColumn\":\"title\",\"conditions\":\"123\",\"compare\":\">=\",\"operator\":\"AND\"}]}]',
        1, NULL, '4.727E-4', 'admin', 'admin', '2022-12-10 17:28:01', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601511868772777986, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_AND_LOWER_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionDiy\":[]}]',
        1, NULL, '0.0122155', 'admin', 'admin', '2022-12-10 17:39:19', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601511968307806209, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"12312\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionDiy\":[]}]',
        1, NULL, '5.529E-4', 'admin', 'admin', '2022-12-10 17:39:43', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601512018987581441, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DIY_DEPT\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\",\"1565314987957145609\"],\"dataPermissionDiy\":[]}]',
        1, NULL, '3.394E-4', 'admin', 'admin', '2022-12-10 17:39:55', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601512384831553537, '权限系统', '数据权限信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[[1601512018882723841,1601511968228114434,1601511868323987458,1601509024263041025,1601494737385680897]]', 1,
        NULL, '2.711E-4', 'admin', 'admin', '2022-12-10 17:41:22', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601512657008328706, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_AND_LOWER_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1565314987957145600\"],\"dataPermissionDiy\":[]}]',
        1, NULL, '1.22E-4', 'admin', 'admin', '2022-12-10 17:42:27', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601512954036436994, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_AND_LOWER_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1565314987957145600\"],\"dataPermissionDiy\":[]}]',
        1, NULL, '0.0077026', 'admin', 'admin', '2022-12-10 17:43:38', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601513013448753154, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"3123\",\"dataPermissionCode\":\"12\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionDiy\":[]}]',
        1, NULL, '1.986E-4', 'admin', 'admin', '2022-12-10 17:43:52', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601513052686467074, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DIY_DEPT\",\"operator\":\"OR\",\"dataPermissions\":[\"1565314987957145600\",\"1565314987957145609\"],\"dataPermissionDiy\":[]}]',
        1, NULL, '1.577E-4', 'admin', 'admin', '2022-12-10 17:44:01', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601513090988851202, '权限系统', '数据权限信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[[1601513052598386690,1601513013373255681,1601512941713571841,1601512656911859713]]', 1, NULL, '0.001449',
        'admin', 'admin', '2022-12-10 17:44:10', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601513620800749570, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_AND_LOWER_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1565314987957145600\"],\"dataPermissionDiy\":[]}]',
        1, NULL, '1.392E-4', 'admin', 'admin', '2022-12-10 17:46:17', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601513697049001986, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionDiy\":[]}]',
        1, NULL, '1.252E-4', 'admin', 'admin', '2022-12-10 17:46:35', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601513728321732610, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DIY_DEPT\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\",\"1565314987957145609\"],\"dataPermissionDiy\":[]}]',
        1, NULL, '2.403E-4', 'admin', 'admin', '2022-12-10 17:46:42', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601579918792556546, '权限系统', '部门信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":1601579918477983745,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-10 22:09:43\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"deptCode\":\"Java1\",\"deptName\":\"研发组1\",\"parentId\":1581851971500371970,\"sysDeptList\":null}]',
        1, NULL, '0.0138397', 'admin', 'admin', '2022-12-10 22:09:43', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601579971045195778, '权限系统', '部门信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":1601579970948726786,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-10 22:09:55\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"deptCode\":\"Java2\",\"deptName\":\"Java2\",\"parentId\":1581851971500371970,\"sysDeptList\":null}]',
        1, NULL, '2.97099E-4', 'admin', 'admin', '2022-12-10 22:09:56', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601580095355977729, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_AND_LOWER_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1601579970948726786\",\"1601579918477983745\",\"1565314987957145609\"],\"dataPermissionDiy\":[]}]',
        0, 'For input string: \"1601579970948726786,1601579918477983745,1565314987957145609\"', '0.0010434', 'admin',
        'admin', '2022-12-10 22:10:25', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601580403075284994, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_AND_LOWER_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1565314987957145600\",\"1581851971500371970\",\"1601579970948726786\",\"1565314987957145600\",\"1581851971500371970\",\"1601579918477983745\",\"1565314987957145600\",\"1565314987957145609\"],\"dataPermissionDiy\":[]}]',
        0,
        'For input string: \"1565314987957145600,1581851971500371970,1601579970948726786,1565314987957145600,1581851971500371970,1601579918477983745,1565314987957145600,1565314987957145609\"',
        '1.493E-4', 'admin', 'admin', '2022-12-10 22:11:39', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601580931440148482, '权限系统', '数据权限信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[[1601513728221069314,1601513696977698818,1601513620725252098]]', 1, NULL, '2.21199E-4', 'admin', 'admin',
        '2022-12-10 22:13:45', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601580966999457793, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123213\",\"dataPermissionType\":\"DEPT_AND_LOWER_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionDiy\":[]}]',
        1, NULL, '2.184E-4', 'admin', 'admin', '2022-12-10 22:13:53', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601586126949621761, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_AND_LOWER_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionDiy\":[{\"tableColumn\":\"id\",\"conditions\":\"1\",\"compare\":\">\",\"operator\":null},{\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":\"AND\"}]}]',
        1, NULL, '0.0091022', 'admin', 'admin', '2022-12-10 22:34:23', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601586280456953858, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionDiy\":[{\"tableColumn\":\"id\",\"conditions\":\"1\",\"compare\":\">\",\"operator\":null},{\"tableColumn\":\"title\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":\"AND\"}]}]',
        1, NULL, '2.72301E-4', 'admin', 'admin', '2022-12-10 22:35:00', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601592873114304514, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"AND\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionDiy\":null}]',
        0, NULL, '0.0179751', 'admin', 'admin', '2022-12-10 23:01:12', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601593162575806466, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"AND\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionDiy\":null}]',
        0, NULL, '3.53001E-4', 'admin', 'admin', '2022-12-10 23:02:21', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601595516263997442, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"tableColumn\":\"id\",\"conditions\":\"1\",\"compare\":\">\",\"operator\":null},{\"tableColumn\":\"id\",\"conditions\":\"1\",\"compare\":\">\",\"operator\":\"AND\"}]}]',
        1, NULL, '0.0068679', 'admin', 'admin', '2022-12-10 23:11:42', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601595674284400642, '权限系统', '数据权限信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[[1601595482772480002,1601593827565940738,1601593037157728258,1601592872770371585,1601586280213684226,1601586126412750850]]',
        1, NULL, '2.368E-4', 'admin', 'admin', '2022-12-10 23:12:20', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601595808149807106, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"tableColumn\":\"id\",\"conditions\":\"213\",\"compare\":\">\",\"operator\":null},{\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":\"AND\"}]}]',
        1, NULL, '1.992E-4', 'admin', 'admin', '2022-12-10 23:12:52', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601597256975671297, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"tableColumn\":\"id\",\"conditions\":\"213\",\"compare\":\">\",\"operator\":null},{\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":\"OR\"}]}]',
        1, NULL, '0.0080113', 'admin', 'admin', '2022-12-10 23:18:37', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601597277557121025, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"tableColumn\":\"id\",\"conditions\":\"213\",\"compare\":\">\",\"operator\":null},{\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":\"OR\"}]}]',
        1, NULL, '2.659E-4', 'admin', 'admin', '2022-12-10 23:18:42', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601597645796040705, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"AND\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"tableColumn\":\"id\",\"conditions\":\"213\",\"compare\":\">\",\"operator\":null},{\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":\"OR\"}]}]',
        0,
        'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: org.springframework.jdbc.CannotGetJdbcConnectionException: Failed to obtain JDBC Connection; nested exception is java.sql.SQLException\r\n### The error may exist in com/breeze/boot/system/mapper/SysDataPermissionMapper.java (best guess)\r\n### The error may involve com.breeze.boot.system.mapper.SysDataPermissionMapper.updateById\r\n### The error occurred while executing an update\r\n### Cause: org.springframework.jdbc.CannotGetJdbcConnectionException: Failed to obtain JDBC Connection; nested exception is java.sql.SQLException',
        '4.58401E-4', 'admin', 'admin', '2022-12-10 23:20:10', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601598127444754434, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601595807793291265\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"AND\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":\"1601595807994617857\",\"tableColumn\":\"id\",\"conditions\":\"213\",\"compare\":\">\",\"operator\":null},{\"id\":\"1601595808011395073\",\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":\"OR\"}]}]',
        1, NULL, '0.0098345', 'admin', 'admin', '2022-12-10 23:22:04', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601599611192377346, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601595807793291265\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"AND\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":\"1601595808011395073\",\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":\"OR\"}]}]',
        1, NULL, '3.37501E-4', 'admin', 'admin', '2022-12-10 23:27:58', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601599695959261185, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601595807793291265\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"AND\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":\"1601595807994617857\",\"tableColumn\":\"id\",\"conditions\":\"213\",\"compare\":\">\",\"operator\":null},{\"id\":\"1601595808011395073\",\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":\"OR\"}]}]',
        1, NULL, '2.24201E-4', 'admin', 'admin', '2022-12-10 23:28:18', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601600205659471874, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601595807793291265\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"AND\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":\"1601595808011395073\",\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":null}]}]',
        1, NULL, '2.42199E-4', 'admin', 'admin', '2022-12-10 23:30:20', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601600971996549121, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601595807793291265\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"AND\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":\"1601595808011395073\",\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":null}]}]',
        0, 'java.lang.IllegalArgumentException: java.lang.ClassCastException@69982ce2', '0.0340085', 'admin', 'admin',
        '2022-12-10 23:33:23', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601601264343732225, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601595807793291265\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"AND\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":\"1601595808011395073\",\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":null}]}]',
        0, 'java.lang.IllegalArgumentException: java.lang.ClassCastException@73cd52a0', '3.287E-4', 'admin', 'admin',
        '2022-12-10 23:34:32', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601602559519641601, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601595807793291265\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"AND\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":\"1601595808011395073\",\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":null}]}]',
        0,
        'com.breeze.boot.system.mapper.SysDataPermissionCustomMapper.insert (batch index #1) failed. Cause: java.sql.BatchUpdateException: Duplicate entry \'1601595808011395073\' for key \' PRIMARY\'\n; Duplicate entry \'1601595808011395073\' for key \'PRIMARY\'; nested exception is java.sql.BatchUpdateException: Duplicate entry \'1601595808011395073\' for key \'PRIMARY\'',
        '0.0075457', 'admin', 'admin', '2022-12-10 23:39:41', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601603147611373570, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":null,\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"12312\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":null,\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":null},{\"id\":null,\"tableColumn\":\"id\",\"conditions\":\"123213\",\"compare\":\">\",\"operator\":\"AND\"},{\"id\":null,\"tableColumn\":\"id\",\"conditions\":\"12321\",\"compare\":\">\",\"operator\":\"OR\"}]}]',
        1, NULL, '0.00539', 'admin', 'admin', '2022-12-10 23:42:01', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601603191710285825, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601603147057725442\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"12312\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":\"1601603147296800769\",\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":null},{\"id\":\"1601603147347132417\",\"tableColumn\":\"id\",\"conditions\":\"12321\",\"compare\":\">\",\"operator\":\"OR\"}]}]',
        1, NULL, '2.297E-4', 'admin', 'admin', '2022-12-10 23:42:12', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601603221393375233, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601603147057725442\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"12312\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"AND\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":\"1601603191588651009\",\"tableColumn\":\"id\",\"conditions\":\"12321\",\"compare\":\">\",\"operator\":null}]}]',
        1, NULL, '1.80601E-4', 'admin', 'admin', '2022-12-10 23:42:19', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601603238971703297, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601603147057725442\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"12312\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"AND\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[]}]',
        1, NULL, '1.88501E-4', 'admin', 'admin', '2022-12-10 23:42:23', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601603853957365761, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":null,\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":null,\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":null},{\"id\":null,\"tableColumn\":\"id\",\"conditions\":\"123213\",\"compare\":\">\",\"operator\":\"AND\"},{\"id\":null,\"tableColumn\":\"id\",\"conditions\":\"12321\",\"compare\":\">\",\"operator\":\"OR\"}]}]',
        1, NULL, '0.0050942', 'admin', 'admin', '2022-12-10 23:44:50', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601603879899136002, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601603852690685954\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":\"1601603853739261954\",\"tableColumn\":\"id\",\"conditions\":\"123213\",\"compare\":\">\",\"operator\":null},{\"id\":\"1601603853756039169\",\"tableColumn\":\"id\",\"conditions\":\"12321\",\"compare\":\">\",\"operator\":\"OR\"}]}]',
        1, NULL, '2.639E-4', 'admin', 'admin', '2022-12-10 23:44:56', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601603938766192642, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601603852690685954\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":\"1601603879777501186\",\"tableColumn\":\"id\",\"conditions\":\"12321\",\"compare\":\">\",\"operator\":null}]}]',
        1, NULL, '3.137E-4', 'admin', 'admin', '2022-12-10 23:45:10', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601603958135488513, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601603852690685954\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[]}]',
        1, NULL, '2.62301E-4', 'admin', 'admin', '2022-12-10 23:45:15', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601604417567047682, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601603852690685954\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":\"1601603853739261954\",\"tableColumn\":\"id\",\"conditions\":\"123213\",\"compare\":\">\",\"operator\":null},{\"id\":\"1601603853756039169\",\"tableColumn\":\"id\",\"conditions\":\"12321\",\"compare\":\">\",\"operator\":\"OR\"},{\"id\":\"1601603879764918273\",\"tableColumn\":\"id\",\"conditions\":\"123213\",\"compare\":\">\",\"operator\":null},{\"id\":\"1601603879777501186\",\"tableColumn\":\"id\",\"conditions\":\"12321\",\"compare\":\">\",\"operator\":\"OR\"},{\"id\":\"1601603938652946434\",\"tableColumn\":\"id\",\"conditions\":\"12321\",\"compare\":\">\",\"operator\":null}]}]',
        1, NULL, '0.0081895', 'admin', 'admin', '2022-12-10 23:47:04', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601604465772183554, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601603852690685954\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[]}]',
        1, NULL, '0.0010709', 'admin', 'admin', '2022-12-10 23:47:16', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601787364643315713, '权限系统', '数据权限信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[[1601603852690685954,1601603147057725442,1601595807793291265]]', 1, NULL, '0.0049724', 'admin', 'admin',
        '2022-12-11 11:54:02', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601787448537784322, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":null,\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":null,\"tableColumn\":\"id\",\"conditions\":\"1\",\"compare\":\">\",\"operator\":null},{\"id\":null,\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":\"AND\"}]}]',
        1, NULL, '9.697E-4', 'admin', 'admin', '2022-12-11 11:54:22', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601787627382906882, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601787448332263425\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":\"1601787448458092545\",\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\">\",\"operator\":null}]}]',
        1, NULL, '2.979E-4', 'admin', 'admin', '2022-12-11 11:55:05', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601788918012289026, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601787448332263425\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[]}]',
        1, NULL, '0.0066752', 'admin', 'admin', '2022-12-11 12:00:13', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601789042318876673, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601787448332263425\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":null,\"tableColumn\":\"id\",\"conditions\":\"1\",\"compare\":\">\",\"operator\":null}]}]',
        1, NULL, '9.971E-4', 'admin', 'admin', '2022-12-11 12:00:42', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601789061033861121, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42',
        '[{\"id\":\"1601787448332263425\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[]}]',
        1, NULL, '1.673E-4', 'admin', 'admin', '2022-12-11 12:00:47', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601792162470023170, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":\"1601787448332263425\",\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[{\"id\":null,\"tableColumn\":\"data_permission_name\",\"conditions\":\"1\",\"compare\":\">\",\"operator\":null}]}]',
        1, NULL, '0.0076115', 'admin', 'admin', '2022-12-11 12:13:06', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601793285905960961, '权限系统', '文件信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[1601793171963498497]]', 1, NULL, '1.884E-4', 'admin', 'admin', '2022-12-11 12:17:34', NULL, NULL, NULL, 0,
        NULL, 1);
INSERT INTO `sys_log`
VALUES (1601793691935559682, '权限系统', '字典信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601793691449020417,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 12:19:10\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictName\":\"数据权限类型\",\"dictCode\":\"DATA_PERMISSION_TYPE\",\"isOpen\":0,\"sysDictDetailList\":null}]',
        1, NULL, '0.0013078', 'admin', 'admin', '2022-12-11 12:19:11', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601793699531444226, '权限系统', '字典信息开关', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601793691449020417,\"isOpen\":1}]', 1, NULL, '8.601E-4', 'admin', 'admin', '2022-12-11 12:19:13',
        NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601793891991277570, '权限系统', '字典项信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601793891890614273,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 12:19:58\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictId\":1601793691449020417,\"value\":\"ALL\",\"label\":\"ALL\",\"sort\":null}]',
        1, NULL, '0.0018745', 'admin', 'admin', '2022-12-11 12:19:58', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601793946357846017, '权限系统', '字典项信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601793891890614273,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":\"admin\",\"updateName\":\"admin\",\"updateTime\":\"2022-12-11 12:20:11\",\"dictId\":1601793691449020417,\"value\":\"ALL\",\"label\":\"全部\",\"sort\":null}]',
        1, NULL, '2.005E-4', 'admin', 'admin', '2022-12-11 12:20:11', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601793991949930498, '权限系统', '字典项信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601793991845072897,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 12:20:22\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictId\":1601793691449020417,\"value\":\"OWN\",\"label\":\"自己\",\"sort\":null}]',
        1, NULL, '0.0011391', 'admin', 'admin', '2022-12-11 12:20:22', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601794088209207298, '权限系统', '字典项信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601794088121126914,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 12:20:45\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictId\":1601793691449020417,\"value\":\"DEPT_AND_LOWER_LEVEL\",\"label\":\"本级以及下属部门\",\"sort\":null}]',
        1, NULL, '2.154E-4', 'admin', 'admin', '2022-12-11 12:20:45', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601794253884215297, '权限系统', '字典项信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601794253766774785,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 12:21:24\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictId\":1601793691449020417,\"value\":\"DEPT_LEVEL\",\"label\":\"本级部门\",\"sort\":null}]',
        1, NULL, '3.752E-4', 'admin', 'admin', '2022-12-11 12:21:25', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601794312315064322, '权限系统', '字典项信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601794312206012418,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 12:21:38\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictId\":1601793691449020417,\"value\":\"DIY_DEPT\",\"label\":\"自定义部门\",\"sort\":null}]',
        1, NULL, '2.924E-4', 'admin', 'admin', '2022-12-11 12:21:39', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601795810889551873, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":null,\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"自己\",\"operator\":\"OR\",\"dataPermissions\":[],\"dataPermissionTableSqlDiyData\":[]}]',
        1, NULL, '2.571E-4', 'admin', 'admin', '2022-12-11 12:27:36', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601796078377095169, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":null,\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"自定义部门\",\"operator\":\"OR\",\"dataPermissions\":[],\"dataPermissionTableSqlDiyData\":[]}]',
        1, NULL, '1.043E-4', 'admin', 'admin', '2022-12-11 12:28:40', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601796270786596865, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":null,\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DIY_DEPT\",\"operator\":\"OR\",\"dataPermissions\":[],\"dataPermissionTableSqlDiyData\":[]}]',
        1, NULL, '1.661E-4', 'admin', 'admin', '2022-12-11 12:29:26', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601796455231115265, '权限系统', '数据权限信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[1601796270660767745,1601796078272237570,1601795810818248705,1601787448332263425]]', 1, NULL, '2.266E-4',
        'admin', 'admin', '2022-12-11 12:30:10', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601803560717516801, '权限系统', '菜单信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601803560176451586,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 12:58:23\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"platformId\":1564528653105573889,\"platformName\":\"\",\"parentId\":1111111111111111111,\"title\":\"123\",\"name\":\"123\",\"type\":1,\"icon\":\"\",\"path\":\"123\",\"component\":\"123\",\"permission\":\"123\",\"keepAlive\":0,\"hidden\":0,\"href\":0,\"sort\":1,\"permissions\":null}]',
        1, NULL, '0.0109942', 'admin', 'admin', '2022-12-11 12:58:24', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601803587263266817, '权限系统', '菜单信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[1601803560176451586]', 1, NULL, '1.208E-4', 'admin', 'admin', '2022-12-11 12:58:30', NULL, NULL, NULL, 0,
        NULL, 1);
INSERT INTO `sys_log`
VALUES (1601808828482330626, '权限系统', '菜单信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601808827316314114,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 13:19:19\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"platformId\":1111111111111111111,\"platformName\":\"\",\"parentId\":1111111111111111111,\"title\":\"1\",\"name\":\"\",\"type\":0,\"icon\":\"\",\"path\":\"1\",\"component\":\"\",\"permission\":\"\",\"keepAlive\":0,\"hidden\":0,\"href\":0,\"sort\":1,\"permissions\":null}]',
        1, NULL, '0.010583', 'admin', 'admin', '2022-12-11 13:19:20', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601811184708100097, '权限系统', '菜单信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601811184070565890,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 13:28:41\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"platformId\":1111111111111111111,\"platformName\":\"\",\"parentId\":1601808827316314114,\"title\":\"123\",\"name\":\"123\",\"type\":1,\"icon\":\"\",\"path\":\"123\",\"component\":\"123\",\"permission\":\"123\",\"keepAlive\":0,\"hidden\":0,\"href\":0,\"sort\":1,\"permissions\":null}]',
        1, NULL, '4.364E-4', 'admin', 'admin', '2022-12-11 13:28:41', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601811238122561537, '权限系统', '菜单信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601811237686353922,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 13:28:53\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"platformId\":1111111111111111111,\"platformName\":\"\",\"parentId\":1601811184070565890,\"title\":\"123\",\"name\":\"\",\"type\":2,\"icon\":\"\",\"path\":\"\",\"component\":\"\",\"permission\":\"123\",\"keepAlive\":0,\"hidden\":0,\"href\":0,\"sort\":1,\"permissions\":null}]',
        1, NULL, '2.655E-4', 'admin', 'admin', '2022-12-11 13:28:54', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601811256220983297, '权限系统', '菜单信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[1601808827316314114]', 1, NULL, '1.004E-4', 'admin', 'admin', '2022-12-11 13:28:58', NULL, NULL, NULL, 0,
        NULL, 1);
INSERT INTO `sys_log`
VALUES (1601811268346716162, '权限系统', '菜单信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[1601811237686353922]', 1, NULL, '1.42E-4', 'admin', 'admin', '2022-12-11 13:29:01', NULL, NULL, NULL, 0, NULL,
        1);
INSERT INTO `sys_log`
VALUES (1601811276848570369, '权限系统', '菜单信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[1601811184070565890]', 1, NULL, '1.933E-4', 'admin', 'admin', '2022-12-11 13:29:03', NULL, NULL, NULL, 0,
        NULL, 1);
INSERT INTO `sys_log`
VALUES (1601811287531462657, '权限系统', '菜单信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[1601808827316314114]', 1, NULL, '9.12E-5', 'admin', 'admin', '2022-12-11 13:29:06', NULL, NULL, NULL, 0, NULL,
        1);
INSERT INTO `sys_log`
VALUES (1601812162689769473, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":null,\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_AND_LOWER_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"IT研发部门\"],\"dataPermissionTableSqlDiyData\":[]}]',
        0, 'For input string: \"IT研发部门\"', '9.217E-4', 'admin', 'admin', '2022-12-11 13:32:34', NULL, NULL, NULL, 0,
        NULL, 1);
INSERT INTO `sys_log`
VALUES (1601813397677416450, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":null,\"dataPermissionName\":\"123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1581851971500371970\"],\"dataPermissionTableSqlDiyData\":[]}]',
        1, NULL, '1.437E-4', 'admin', 'admin', '2022-12-11 13:37:29', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601814619285233665, '权限系统', '部门信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601814619176181761,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 13:42:20\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"deptCode\":\"123\",\"deptName\":\"2\",\"parentId\":1565314987957145600,\"sysDeptList\":null}]',
        1, NULL, '0.0048132', 'admin', 'admin', '2022-12-11 13:42:20', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601814664319475714, '权限系统', '部门信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601814664223006722,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 13:42:30\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"deptCode\":\"123\",\"deptName\":\"123\",\"parentId\":1565314987957145600,\"sysDeptList\":null}]',
        1, NULL, '1.229E-4', 'admin', 'admin', '2022-12-11 13:42:31', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601814700650536962, '权限系统', '部门信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601814700566650882,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 13:42:39\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"deptCode\":\"123\",\"deptName\":\"123\",\"parentId\":1601814619176181761,\"sysDeptList\":null}]',
        1, NULL, '1.574E-4', 'admin', 'admin', '2022-12-11 13:42:40', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601816458189742081, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":null,\"dataPermissionName\":\"1\",\"dataPermissionCode\":\"1\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1565314987957145609\"],\"dataPermissionTableSqlDiyData\":[]}]',
        1, NULL, '1.01E-4', 'admin', 'admin', '2022-12-11 13:49:39', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601817524566368258, '权限系统', '字典信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601817524482482177,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 13:53:52\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictName\":\"数据权限运算符\",\"dictCode\":\"compare\",\"isOpen\":0,\"sysDictDetailList\":null}]',
        1, NULL, '0.0011253', 'admin', 'admin', '2022-12-11 13:53:53', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601817528513208322, '权限系统', '字典信息开关', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601817524482482177,\"isOpen\":1}]', 1, NULL, '8.002E-4', 'admin', 'admin', '2022-12-11 13:53:54',
        NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601817569411866626, '权限系统', '字典信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601817524482482177,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":\"admin\",\"updateName\":\"admin\",\"updateTime\":\"2022-12-11 13:54:03\",\"dictName\":\"数据权限运算符\",\"dictCode\":\"COMPARE\",\"isOpen\":1,\"sysDictDetailList\":[]}]',
        1, NULL, '3.749E-4', 'admin', 'admin', '2022-12-11 13:54:04', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601819358899085313, '权限系统', '字典项信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601794312206012418,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":\"admin\",\"updateName\":\"admin\",\"updateTime\":\"2022-12-11 14:01:10\",\"dictId\":1601793691449020417,\"value\":\"自定义部门\",\"key\":\"DIY_DEPT\",\"sort\":null}]',
        0,
        '\r\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'key = \'DIY_DEPT\',
        update_by = \'admin\', update_name = \'admin\',
        update_time = \'202\' at line 1\r\n### The error may exist in com/breeze/boot/system/mapper/SysDictItemMapper.java (best guess)\r\n### The error may involve com.breeze.boot.system.mapper.SysDictItemMapper.updateById-Inline\r\n### The error occurred while setting parameters\r\n### SQL: UPDATE sys_dict_item SET dict_id = ?,
        value = ?, key = ?, update_by = ?, update_name = ?, update_time =
                                                            ? WHERE sys_dict_item.tenant_id = 1 AND id = ? AND is_delete = 0\r\n### Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax;
check the manual that corresponds to your MySQL server version for the right syntax to use near
\'key = \'DIY_DEPT
\', update_by = \'admin
\', update_name = \'admin
\', update_time = \'
202\' at line 1\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'key =
\'DIY_DEPT\', update_by =
\'admin\', update_name =
\'admin\', update_time =
\'202\' at line 1
', '0.001015
', 'admin', 'admin', '
2022-12-11 14:01:10
', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log` VALUES (1601820144479723521, '权限系统', '字典项信息修改', 0, 'PUT', '
127.0.0.1
', 2, '127.0.0.1
', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46
', '[{
\"id\":1601794312206012418,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":\"admin\",\"updateName\":\"admin\",\"updateTime\":\"2022-12-11 14:04:17\",\"dictId\":1601793691449020417,\"value\":\"自定义部门\",\"key\":\"DIY_DEPT\",\"sort\":null}]', 0, '\r\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'key = \'DIY_DEPT\', update_by = \'admin\', update_name = \'admin\', update_time = \'202\' at line 1\r\n### The error may exist in com/breeze/boot/system/mapper/SysDictItemMapper.java (best guess)\r\n### The error may involve com.breeze.boot.system.mapper.SysDictItemMapper.updateById-Inline\r\n### The error occurred while setting parameters\r\n### SQL: UPDATE sys_dict_item SET dict_id = ?, value = ?, key = ?, update_by = ?, update_name = ?, update_time = ? WHERE sys_dict_item.tenant_id = 1 AND id = ? AND is_delete = 0\r\n### Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'key = \'DIY_DEPT\', update_by = \'admin\', update_name = \'admin\', update_time = \'202\' at line 1\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'key = \'DIY_DEPT\', update_by = \'admin\', update_name = \'admin\', update_time = \'202\' at line 1', '0.0077481', 'admin', 'admin', '2022-12-11 14:04:17', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log` VALUES (1601823838168313857, '权限系统', '用户信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46', '[{\"id\":1601823837514002434,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 14:18:57\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"username\":\"1\",\"userCode\":\"1\",\"amountName\":\"1\",\"avatar\":null,\"password\":\"$2a$10$3F7FjtGaN9CPsIYBZjxSceWwIkmF/3Cm6jPOG9xdCbkuE4u7zsPUi\",\"postId\":1591377257933819906,\"postName\":\"123\",\"deptId\":1601814619176181761,\"deptName\":\"IT研发部门\",\"sex\":1,\"idCard\":\"1\",\"phone\":\"1\",\"openId\":\"123123\",\"email\":\"1\",\"isLock\":1,\"tenantId\":null,\"sysRoles\":[{\"id\":1565322827518140417,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"roleCode\":\"ROLE_ADMIN\",\"roleName\":\"超级管理员\"}],\"roleIds\":[1565322827518140417]}]', 0, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'
123123\' for key \'uni_open_id
\'\r\n### The error may exist in com/breeze/boot/system/mapper/SysUserMapper.java (best guess)\r\n### The error may involve com.breeze.boot.system.mapper.SysUserMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user (id, username, user_code, amount_name, password, post_id, dept_id, sex, id_card, phone, open_id, email, is_lock, create_by, create_name, create_time, update_time, tenant_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'
123123\' for key \'uni_open_id
\'\n; Duplicate entry \'
123123\' for key \'uni_open_id
\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'
123123\' for key \'uni_open_id
\'', '0.0146463', 'admin', 'admin', '2022-12-11 14:18:58', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601823979092733953, '权限系统', '用户信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601823979025625090,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 14:19:31\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"username\":\"1\",\"userCode\":\"1123123\",\"amountName\":\"1123123\",\"avatar\":null,\"password\":\"$2a$10$8o/leEe8WmC1L23/ojaI2uqYLPDZzc1nIbX/IyHAi4HZL.xJS.PI6\",\"postId\":1591377257933819906,\"postName\":\"123\",\"deptId\":1601814619176181761,\"deptName\":\"IT研发部门\",\"sex\":1,\"idCard\":\"1\",\"phone\":\"1123\",\"openId\":\"123123\",\"email\":\"112312\",\"isLock\":1,\"tenantId\":null,\"sysRoles\":[{\"id\":1565322827518140417,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"roleCode\":\"ROLE_ADMIN\",\"roleName\":\"超级管理员\"}],\"roleIds\":[1565322827518140417]}]',
        0,
        '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'123123\' for key \' uni_open_id\'\r\n### The error may exist in com/breeze/boot/system/mapper/SysUserMapper.java (best guess)\r\n### The error may involve com.breeze.boot.system.mapper.SysUserMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user (id, username, user_code, amount_name, password, post_id, dept_id, sex, id_card, phone, open_id, email, is_lock, create_by, create_name, create_time, update_time, tenant_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'123123\' for key \'uni_open_id\'\n; Duplicate entry \'123123\' for key \'uni_open_id\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'123123\' for key \'uni_open_id\'',
        '2.436E-4', 'admin', 'admin', '2022-12-11 14:19:32', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601824853357654017, '权限系统', '用户信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":null,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"username\":\"1\",\"userCode\":\"1123123\",\"amountName\":\"1123123\",\"avatar\":null,\"password\":\"1\",\"postId\":1591377257933819906,\"postName\":\"123\",\"deptId\":1601814619176181761,\"deptName\":\"IT研发部门\",\"sex\":1,\"idCard\":\"1\",\"phone\":\"1123\",\"openId\":\"123123\",\"email\":\"112312\",\"isLock\":1,\"tenantId\":null,\"sysRoles\":[{\"id\":1565322827518140417,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"roleCode\":\"ROLE_ADMIN\",\"roleName\":\"超级管理员\"}],\"roleIds\":[1565322827518140417]}]',
        0,
        'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error querying database.  Cause: org.springframework.jdbc.CannotGetJdbcConnectionException: Failed to obtain JDBC Connection; nested exception is java.sql.SQLException\r\n### The error may exist in com/breeze/boot/system/mapper/SysDeptMapper.java (best guess)\r\n### The error may involve com.breeze.boot.system.mapper.SysDeptMapper.selectById\r\n### The error occurred while executing a query\r\n### Cause: org.springframework.jdbc.CannotGetJdbcConnectionException: Failed to obtain JDBC Connection; nested exception is java.sql.SQLException',
        '0.0011013', 'admin', 'admin', '2022-12-11 14:23:00', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601825091271168002, '权限系统', '用户信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601825085344616449,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 14:23:55\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"username\":\"1\",\"userCode\":\"1123123\",\"amountName\":\"1123123\",\"avatar\":null,\"password\":\"$2a$10$57h5KS1yZqCj1Jaz/NWIOuuS7ibLfC4M8x2yutHloRVpl79R/vvee\",\"postId\":1591377257933819906,\"postName\":\"123\",\"deptId\":1601814619176181761,\"deptName\":\"IT研发部门\",\"sex\":1,\"idCard\":\"1\",\"phone\":\"1123\",\"email\":\"112312\",\"isLock\":1,\"tenantId\":null,\"sysRoles\":[{\"id\":1565322827518140417,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"roleCode\":\"ROLE_ADMIN\",\"roleName\":\"超级管理员\"}],\"roleIds\":[1565322827518140417]}]',
        1, NULL, '0.0097274', 'admin', 'admin', '2022-12-11 14:23:57', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601825121671483394, '权限系统', '用户信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[1601825085344616449]]', 1, NULL, '8.135E-4', 'admin', 'admin', '2022-12-11 14:24:04', NULL, NULL, NULL, 0,
        NULL, 1);
INSERT INTO `sys_log`
VALUES (1601825251116093441, '权限系统', '字典项信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601825250998652930,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 14:24:34\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictId\":1601817524482482177,\"value\":\">\",\"key\":\">\",\"sort\":null}]',
        1, NULL, '0.0014615', 'admin', 'admin', '2022-12-11 14:24:35', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601825295361806338, '权限系统', '字典项信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601825250998652930,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":\"admin\",\"updateName\":\"admin\",\"updateTime\":\"2022-12-11 14:24:45\",\"dictId\":1601817524482482177,\"value\":\">\",\"key\":\"大于\",\"sort\":null}]',
        1, NULL, '2.33E-4', 'admin', 'admin', '2022-12-11 14:24:46', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601825791589912578, '权限系统', '字典项信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601825250998652930,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":\"admin\",\"updateName\":\"admin\",\"updateTime\":\"2022-12-11 14:26:43\",\"dictId\":1601817524482482177,\"value\":\"大于\",\"key\":\">\",\"sort\":null}]',
        1, NULL, '2.638E-4', 'admin', 'admin', '2022-12-11 14:26:44', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601825929687371777, '权限系统', '字典项信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601825929611874305,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 14:27:16\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictId\":1601817524482482177,\"value\":\"大于等于\",\"key\":\" >=\",\"sort\":null}]',
        1, NULL, '2.644E-4', 'admin', 'admin', '2022-12-11 14:27:17', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601826138928615425, '权限系统', '字典项信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601826138823757825,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 14:28:06\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictId\":1601817524482482177,\"value\":\"小于\",\"key\":\"<\",\"sort\":null}]',
        1, NULL, '7.955E-4', 'admin', 'admin', '2022-12-11 14:28:07', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601826170197151745, '权限系统', '字典项信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601826169953882113,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 14:28:14\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictId\":1601817524482482177,\"value\":\"小于等于\",\"key\":\"<=\",\"sort\":null}]',
        1, NULL, '7.329E-4', 'admin', 'admin', '2022-12-11 14:28:14', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601826214765826050, '权限系统', '字典项信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601826214673551361,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 14:28:24\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictId\":1601817524482482177,\"value\":\"不等于\",\"key\":\"!=\",\"sort\":null}]',
        1, NULL, '3.044E-4', 'admin', 'admin', '2022-12-11 14:28:25', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601826300006666242, '权限系统', '字典项信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601826299918585857,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 14:28:45\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictId\":1601817524482482177,\"value\":\"等于\",\"key\":\"=\",\"sort\":null}]',
        1, NULL, '2.247E-4', 'admin', 'admin', '2022-12-11 14:28:45', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601826517430996993, '权限系统', '字典项信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601826517330333697,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 14:29:36\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictId\":1601817524482482177,\"value\":\"不等于空\",\"key\":\"          value: \' IS NOT NULL\',\",\"sort\":null}]',
        1, NULL, '2.201E-4', 'admin', 'admin', '2022-12-11 14:29:37', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601826560393252865, '权限系统', '字典项信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601826517330333697,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":\"admin\",\"updateName\":\"admin\",\"updateTime\":\"2022-12-11 14:29:47\",\"dictId\":1601817524482482177,\"value\":\"不等于空\",\"key\":\"IS NOT NULL\",\"sort\":null}]',
        1, NULL, '8.977E-4', 'admin', 'admin', '2022-12-11 14:29:47', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601826978263371778, '权限系统', '字典项信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601826978187874306,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 14:31:26\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictId\":1601817524482482177,\"value\":\"等于空\",\"key\":\"等于空\",\"sort\":null}]',
        1, NULL, '2.531E-4', 'admin', 'admin', '2022-12-11 14:31:27', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601827007782883330, '权限系统', '字典项信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601826978187874306,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":\"admin\",\"updateName\":\"admin\",\"updateTime\":\"2022-12-11 14:31:33\",\"dictId\":1601817524482482177,\"value\":\"等于空\",\"key\":\"IS NULL\",\"sort\":null}]',
        1, NULL, '5.374E-4', 'admin', 'admin', '2022-12-11 14:31:34', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601828166560026626, '权限系统', '数据权限信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":null,\"dataPermissionName\":\"123123\",\"dataPermissionCode\":\"123\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1601814619176181761\"],\"dataPermissionTableSqlDiyData\":[{\"id\":null,\"tableColumn\":\"id\",\"conditions\":\"123\",\"compare\":\"IS NULL\",\"operator\":null}]}]',
        1, NULL, '0.0013246', 'admin', 'admin', '2022-12-11 14:36:10', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601828188911472642, '权限系统', '数据权限信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[1601828166383865857]]', 1, NULL, '2.645E-4', 'admin', 'admin', '2022-12-11 14:36:15', NULL, NULL, NULL, 0,
        NULL, 1);
INSERT INTO `sys_log`
VALUES (1601829775155302401, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":\"1601813397580947458\",\"dataPermissionName\":\"用户消息查看数据权限\",\"dataPermissionCode\":\"USER_MSG\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1565314987957145609\"],\"dataPermissionTableSqlDiyData\":[{\"id\":null,\"tableColumn\":\"msg_code\",\"conditions\":\"CODE\",\"compare\":\"=\",\"operator\":null}]}]',
        1, NULL, '1.993E-4', 'admin', 'admin', '2022-12-11 14:42:34', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601829793761234945, '权限系统', '数据权限信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[1601816458118438913]]', 1, NULL, '1.116E-4', 'admin', 'admin', '2022-12-11 14:42:38', NULL, NULL, NULL, 0,
        NULL, 1);
INSERT INTO `sys_log`
VALUES (1601829893363372034, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":\"1601813397580947458\",\"dataPermissionName\":\"用户消息查看数据权限\",\"dataPermissionCode\":\"USER_MSG\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1565314987957145609\"],\"dataPermissionTableSqlDiyData\":[{\"id\":null,\"tableColumn\":\"msg_code\",\"conditions\":\"‘CODE’\",\"compare\":\"=\",\"operator\":null}]}]',
        1, NULL, '1.312E-4', 'admin', 'admin', '2022-12-11 14:43:02', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601830159793950722, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":\"1601813397580947458\",\"dataPermissionName\":\"用户消息查看数据权限\",\"dataPermissionCode\":\"USER_MSG\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1565314987957145609\"],\"dataPermissionTableSqlDiyData\":[{\"id\":null,\"tableColumn\":\"msg_code\",\"conditions\":\"\",\"compare\":\"IS NOT NULL\",\"operator\":null}]}]',
        1, NULL, '9.307E-4', 'admin', 'admin', '2022-12-11 14:44:05', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601844349577351170, '权限系统', '字典项信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601844349157920769,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 15:40:28\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictId\":1601793691449020417,\"value\":\"SQL自定义\",\"key\":\"DIY\",\"sort\":null}]',
        1, NULL, '0.010031', 'admin', 'admin', '2022-12-11 15:40:28', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601844892567752706, '权限系统', '字典项信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[1601844349157920769]]', 1, NULL, '2.175E-4', 'admin', 'admin', '2022-12-11 15:42:38', NULL, NULL, NULL, 0,
        NULL, 1);
INSERT INTO `sys_log`
VALUES (1601847842270765058, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":\"1601813397580947458\",\"dataPermissionName\":\"用户消息查看数据权限\",\"dataPermissionCode\":\"USER_MSG\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1565314987957145609\"],\"dataPermissionTableSqlDiyData\":[{\"id\":null,\"tableColumn\":\"msg_code\",\"conditions\":\"\",\"compare\":\"IS NOT NULL\",\"operator\":null}]}]',
        1, NULL, '0.0062592', 'admin', 'admin', '2022-12-11 15:54:21', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601849474349953025, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":\"1601813397580947458\",\"dataPermissionName\":\"用户消息查看数据权限\",\"dataPermissionCode\":\"USER_MSG\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1565314987957145609\"],\"dataPermissionTableSqlDiyData\":[{\"id\":null,\"tableColumn\":\"msg_code\",\"conditions\":\"\",\"compare\":\"IS NOT NULL\",\"operator\":null}]}]',
        1, NULL, '0.0106449', 'admin', 'admin', '2022-12-11 16:00:50', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601854259400777730, '权限系统', '数据权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":\"1601813397580947458\",\"dataPermissionName\":\"用户消息查看数据权限\",\"dataPermissionCode\":\"USER_MSG\",\"dataPermissionType\":\"DEPT_LEVEL\",\"operator\":\"OR\",\"dataPermissions\":[\"1565314987957145609\"],\"dataPermissionTableSqlDiyData\":[{\"id\":\"1601849474098294786\",\"tableColumn\":\"msg_code\",\"conditions\":null,\"compare\":\"IS NOT NULL\",\"operator\":null},{\"id\":null,\"tableColumn\":\"id\",\"conditions\":\"1\",\"compare\":\"=\",\"operator\":\"AND\"}]}]',
        1, NULL, '0.0133813', 'admin', 'admin', '2022-12-11 16:19:51', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601858204684668929, '权限系统', '菜单信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601858203636092929,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 16:35:31\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"platformId\":1111111111111111111,\"platformName\":\"\",\"parentId\":1578702340654268418,\"title\":\"editRoleDataPermission\",\"name\":\"\",\"type\":2,\"icon\":\"\",\"path\":\"\",\"component\":\"\",\"permission\":\"editRoleDataPermission\",\"keepAlive\":0,\"hidden\":0,\"href\":0,\"sort\":1,\"permissions\":null}]',
        1, NULL, '0.0109203', 'admin', 'admin', '2022-12-11 16:35:32', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601858316005691393, '权限系统', '菜单信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601858203636092929,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":\"admin\",\"updateName\":\"admin\",\"updateTime\":\"2022-12-11 16:35:58\",\"platformId\":1111111111111111111,\"platformName\":\"后台管理中心\",\"parentId\":1578702340654268418,\"title\":\"数据权限\",\"name\":null,\"type\":2,\"icon\":null,\"path\":null,\"component\":null,\"permission\":\"editRoleDataPermission\",\"keepAlive\":0,\"hidden\":0,\"href\":0,\"sort\":1,\"permissions\":null}]',
        1, NULL, '5.709E-4', 'admin', 'admin', '2022-12-11 16:35:58', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601860383717871617, '权限系统', '菜单信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601858203636092929,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":\"admin\",\"updateName\":\"admin\",\"updateTime\":\"2022-12-11 16:44:11\",\"platformId\":1111111111111111111,\"platformName\":\"后台管理中心\",\"parentId\":1578702340654268418,\"title\":\"数据权限\",\"name\":null,\"type\":2,\"icon\":null,\"path\":null,\"component\":null,\"permission\":\"sys:dataPermission:editRoleDataPermission\",\"keepAlive\":0,\"hidden\":0,\"href\":0,\"sort\":1,\"permissions\":null}]',
        1, NULL, '3.854E-4', 'admin', 'admin', '2022-12-11 16:44:11', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601860659304615938, '权限系统', '角色权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"roleId\":1565322827518140417,\"permissionIds\":[1578702340666851329,1578702340671045634,1578702340671045635,1578702340679434241,1578702340679434243,1594135789623984129,1594532038764326913,1594532038764326666,1594531945449451666,1589181822230048778,1578702340683628545,1586717542633123841,1599935876379897858,1599936405688479746,1601081152259891202,1578702340624908290,1578702340620713988,1587692336744742913,1578702340620713987,1578702340683628546,1578702340624908293,1578702340624908291,1578702340624908292,1578702340654268416,1589181822230048770,1589181822230048771,1589181822230048772,1578702340654268418,1578702340650074114,1601858203636092929,1578702340641685505,1578702340641685506,1578702340662657027,1578702340650074117,1586717542633123843,1578702340650074115,1578702340650074116,1578702340612325378,1578702340654268411,1578702340654268417,9223372036854775807,1589789746153263106,1598222575933485057,1598222373868695553,1578702340662657026,1581843318345035778,1578702340633296899,1578702340633296898,1578702340654268412,1589181822230048781,1589181822230048782,1589181822230048783,1597480827938615297,9223372036854775120,1589181822230018782,1589181822230048178,9223372036854775119,1589181822230049781,1589181822230018781,1589181822230048172,1582554585967800321,1582555155344568321,1582558188828790785,1580357263003439106,1580357773622202370,1578702340620713986,1581966349440581634,1582607135668621314,1581965904601088001,1581965904601088002,1582688861908611074]}]',
        1, NULL, '0.0012114', 'admin', 'admin', '2022-12-11 16:45:17', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601861074167418882, '权限系统', '角色权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"roleId\":1565322827518140417,\"permissionIds\":[1578702340666851329,1578702340671045634,1578702340671045635,1578702340679434241,1578702340679434243,1594135789623984129,1594532038764326913,1594532038764326666,1594531945449451666,1589181822230048778,1578702340683628545,1586717542633123841,1599935876379897858,1599936405688479746,1601081152259891202,1578702340624908290,1578702340620713988,1587692336744742913,1578702340620713987,1578702340683628546,1578702340624908293,1578702340624908291,1578702340624908292,1578702340654268416,1589181822230048770,1589181822230048771,1589181822230048772,1578702340654268418,1578702340650074114,1601858203636092929,1578702340641685505,1578702340641685506,1578702340662657027,1578702340650074117,1586717542633123843,1578702340650074115,1578702340650074116,1578702340612325378,1578702340654268411,1578702340654268417,9223372036854775807,1589789746153263106,1598222575933485057,1598222373868695553,1578702340662657026,1581843318345035778,1578702340633296899,1578702340633296898,1578702340654268412,1589181822230048781,1589181822230048782,1589181822230048783,1597480827938615297,9223372036854775120,1589181822230018782,1589181822230048178,9223372036854775119,1589181822230049781,1589181822230018781,1589181822230048172,1582554585967800321,1582555155344568321,1582558188828790785,1580357263003439106,1580357773622202370,1578702340620713986,1581966349440581634,1582607135668621314,1581965904601088001,1581965904601088002,1582688861908611074]}]',
        1, NULL, '1.762E-4', 'admin', 'admin', '2022-12-11 16:46:56', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601864004597579778, '权限系统', '编辑角色数据权限', 0, 'POST', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[{\"id\":1601864003712581634,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 16:58:34\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":1,\"roleId\":1565322827518140417},{\"id\":1601864003796467713,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 16:58:34\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":3,\"roleId\":1565322827518140417},{\"id\":1601864003809050626,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 16:58:34\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":4,\"roleId\":1565322827518140417}]]',
        1, NULL, '0.0149879', 'admin', 'admin', '2022-12-11 16:58:35', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601864012533202946, '权限系统', '编辑角色数据权限', 0, 'POST', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[{\"id\":1601864012390596609,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 16:58:36\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":1,\"roleId\":1565322827518140417},{\"id\":1601864012407373825,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 16:58:36\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":3,\"roleId\":1565322827518140417},{\"id\":1601864012415762434,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 16:58:36\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":4,\"roleId\":1565322827518140417}]]',
        1, NULL, '1.908E-4', 'admin', 'admin', '2022-12-11 16:58:36', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601864181391687682, '权限系统', '编辑角色数据权限', 0, 'POST', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[{\"id\":1601864173909049345,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 16:59:14\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":1,\"roleId\":1565322827518140417},{\"id\":1601864174131347457,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 16:59:14\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":3,\"roleId\":1565322827518140417},{\"id\":1601864174236205058,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 16:59:15\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":4,\"roleId\":1565322827518140417}]]',
        1, NULL, '2.539E-4', 'admin', 'admin', '2022-12-11 16:59:17', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601864495800909826, '权限系统', '编辑角色数据权限', 0, 'POST', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[{\"id\":null,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":1,\"roleId\":1565322827518140417},{\"id\":null,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":3,\"roleId\":1565322827518140417}]]',
        0,
        '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Truncated incorrect DOUBLE value: \'\\xAC\\xED\\x00\\x05sr\\x00\\x13java.util.ArrayListx\\x81\\xD2\\x1D\\x99\\xC7a\\x9D\\x03\\x00\\x01I\\x00\\x04sizexp\\x00\\x00\\x00\\x02w\\x04\\x00\\x00\\\'\r\n### The error may exist in com/breeze/boot/system/mapper/SysRoleDataPermissionMapper.java (best guess)\r\n### The error may involve com.breeze.boot.system.mapper.SysRoleDataPermissionMapper.delete-Inline\r\n### The error occurred while setting parameters\r\n### SQL: UPDATE sys_role_data_permission SET is_delete = 1 WHERE sys_role_data_permission.tenant_id = 1 AND is_delete = 0 AND (role_id = ?)\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Truncated incorrect DOUBLE value: \'\\xAC\\xED\\x00\\x05sr\\x00\\x13java.util.ArrayListx\\x81\\xD2\\x1D\\x99\\xC7a\\x9D\\x03\\x00\\x01I\\x00\\x04sizexp\\x00\\x00\\x00\\x02w\\x04\\x00\\x00\\\'\n; Data truncation: Truncated incorrect DOUBLE value: \'\\xAC\\xED\\x00\\x05sr\\x00\\x13java.util.ArrayListx\\x81\\xD2\\x1D\\x99\\xC7a\\x9D\\x03\\x00\\x01I\\x00\\x04sizexp\\x00\\x00\\x00\\x02w\\x04\\x00\\x00\\\'; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Truncated incorrect DOUBLE value: \'\\xAC\\xED\\x00\\x05sr\\x00\\x13java.util.ArrayListx\\x81\\xD2\\x1D\\x99\\xC7a\\x9D\\x03\\x00\\x01I\\x00\\x04sizexp\\x00\\x00\\x00\\x02w\\x04\\x00\\x00\\\'',
        '0.008939', 'admin', 'admin', '2022-12-11 17:00:32', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601864951486873602, '权限系统', '编辑角色数据权限', 0, 'POST', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[{\"id\":null,\"createBy\":null,\"createName\":null,\"createTime\":null,\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":1,\"roleId\":1565322827518140417}]]',
        0,
        '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Truncated incorrect DOUBLE value: \'\\xAC\\xED\\x00\\x05sr\\x00\\x13java.util.ArrayListx\\x81\\xD2\\x1D\\x99\\xC7a\\x9D\\x03\\x00\\x01I\\x00\\x04sizexp\\x00\\x00\\x00\\x01w\\x04\\x00\\x00\\\'\r\n### The error may exist in com/breeze/boot/system/mapper/SysRoleDataPermissionMapper.java (best guess)\r\n### The error may involve com.breeze.boot.system.mapper.SysRoleDataPermissionMapper.delete-Inline\r\n### The error occurred while setting parameters\r\n### SQL: UPDATE sys_role_data_permission SET is_delete = 1 WHERE sys_role_data_permission.tenant_id = 1 AND is_delete = 0 AND (role_id = ?)\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Truncated incorrect DOUBLE value: \'\\xAC\\xED\\x00\\x05sr\\x00\\x13java.util.ArrayListx\\x81\\xD2\\x1D\\x99\\xC7a\\x9D\\x03\\x00\\x01I\\x00\\x04sizexp\\x00\\x00\\x00\\x01w\\x04\\x00\\x00\\\'\n; Data truncation: Truncated incorrect DOUBLE value: \'\\xAC\\xED\\x00\\x05sr\\x00\\x13java.util.ArrayListx\\x81\\xD2\\x1D\\x99\\xC7a\\x9D\\x03\\x00\\x01I\\x00\\x04sizexp\\x00\\x00\\x00\\x01w\\x04\\x00\\x00\\\'; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Truncated incorrect DOUBLE value: \'\\xAC\\xED\\x00\\x05sr\\x00\\x13java.util.ArrayListx\\x81\\xD2\\x1D\\x99\\xC7a\\x9D\\x03\\x00\\x01I\\x00\\x04sizexp\\x00\\x00\\x00\\x01w\\x04\\x00\\x00\\\'',
        '5.679E-4', 'admin', 'admin', '2022-12-11 17:02:20', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601865599720878081, '权限系统', '编辑角色数据权限', 0, 'POST', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[{\"id\":1601865598982680578,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:04:54\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":1,\"roleId\":1565322827518140417},{\"id\":1601865599028817922,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:04:54\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":3,\"roleId\":1565322827518140417},{\"id\":1601865599053983745,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:04:54\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":4,\"roleId\":1565322827518140417},{\"id\":1601865599062372354,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:04:54\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":5,\"roleId\":1565322827518140417},{\"id\":1601865599079149570,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:04:54\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":1591663064062078978,\"roleId\":1565322827518140417},{\"id\":1601865599095926786,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:04:54\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":1601813397580947458,\"roleId\":1565322827518140417}]]',
        1, NULL, '0.008184', 'admin', 'admin', '2022-12-11 17:04:55', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601870001890295810, '权限系统', '编辑角色数据权限', 0, 'POST', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[{\"id\":1601870001479254017,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:22:24\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":1,\"roleId\":1565322827518140417},{\"id\":1601870001525391362,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:22:24\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":3,\"roleId\":1565322827518140417}]]',
        1, NULL, '0.0082547', 'admin', 'admin', '2022-12-11 17:22:24', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601870145582956545, '权限系统', '编辑角色数据权限', 0, 'POST', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[{\"id\":1601870145415184386,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:22:58\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":4,\"roleId\":1565322827518140417},{\"id\":1601870145431961602,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:22:58\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":5,\"roleId\":1565322827518140417},{\"id\":1601870145452933121,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:22:58\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":1591663064062078978,\"roleId\":1565322827518140417}]]',
        1, NULL, '3.625E-4', 'admin', 'admin', '2022-12-11 17:22:59', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601871515371331586, '权限系统', '角色信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601871514721214465,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:28:25\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"roleCode\":\"12312\",\"roleName\":\"1234\",\"dataPermissionName\":null}]',
        1, NULL, '0.0136973', 'admin', 'admin', '2022-12-11 17:28:25', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601871597936205825, '权限系统', '角色信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601871597835542529,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:28:44\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"roleCode\":\"123\",\"roleName\":\"123\",\"dataPermissionName\":null}]',
        1, NULL, '2.544E-4', 'admin', 'admin', '2022-12-11 17:28:45', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601871632077840386, '权限系统', '角色信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601871631998148610,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:28:53\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"roleCode\":\"23432\",\"roleName\":\"234\",\"dataPermissionName\":null}]',
        1, NULL, '1.515E-4', 'admin', 'admin', '2022-12-11 17:28:53', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601871945136496641, '权限系统', '角色信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601871945031639042,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:30:07\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"roleCode\":\"123123\",\"roleName\":\"123\",\"dataPermissionName\":null}]',
        1, NULL, '2.713E-4', 'admin', 'admin', '2022-12-11 17:30:08', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601872221503381506, '权限系统', '角色信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601872221411106817,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:31:13\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"roleCode\":\"123123\",\"roleName\":\"123\",\"dataPermissionName\":null}]',
        1, NULL, '1.914E-4', 'admin', 'admin', '2022-12-11 17:31:14', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601872254562885633, '权限系统', '角色权限信息修改', 0, 'PUT', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"roleId\":1601872221411106817,\"permissionIds\":[1578702340666851329]}]', 1, NULL, '0.0010213', 'admin',
        'admin', '2022-12-11 17:31:21', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601873074448654337, '权限系统', '编辑角色数据权限', 0, 'POST', '127.0.0.1', 2, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[{\"id\":1601873074251522049,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:34:36\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":1,\"roleId\":1601872221411106817},{\"id\":1601873074272493569,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:34:36\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":3,\"roleId\":1601872221411106817},{\"id\":1601873074289270786,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:34:36\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dataPermissionId\":4,\"roleId\":1601872221411106817}]]',
        1, NULL, '0.0032092', 'admin', 'admin', '2022-12-11 17:34:37', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601873238865350657, '权限系统', '角色信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[1601872221411106817]]', 1, NULL, '0.0047643', 'admin', 'admin', '2022-12-11 17:35:16', NULL, NULL, NULL, 0,
        NULL, 1);
INSERT INTO `sys_log`
VALUES (1601875011541798914, '权限系统', '字典信息保存', 0, 'POST', '127.0.0.1', 0, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[{\"id\":1601875011403386881,\"createBy\":\"admin\",\"createName\":\"admin\",\"createTime\":\"2022-12-11 17:42:18\",\"updateBy\":null,\"updateName\":null,\"updateTime\":null,\"dictName\":\"123\",\"dictCode\":\"123\",\"isOpen\":0,\"sysDictDetailList\":null}]',
        1, NULL, '0.0025988', 'admin', 'admin', '2022-12-11 17:42:19', NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_log`
VALUES (1601875030843985921, '权限系统', '字典信息删除', 0, 'DELETE', '127.0.0.1', 1, '127.0.0.1',
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.46',
        '[[1601875011403386881]]', 1, NULL, '1.213E-4', 'admin', 'admin', '2022-12-11 17:42:23', NULL, NULL, NULL, 0,
        NULL, 1);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`          bigint(22) NOT NULL DEFAULT 1111111111111111111,
    `platform_id` bigint(22) NULL DEFAULT NULL COMMENT '平台ID',
    `parent_id`   bigint(22) NULL DEFAULT 0 COMMENT '上一级的菜单ID',
    `title`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名称',
    `type`        tinyint(1) NULL DEFAULT 0 COMMENT '类型 0 文件夹 1 菜单 2 按钮',
    `icon`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
    `path`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
    `component`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
    `permission`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
    `href`        tinyint(1) NULL DEFAULT 0 COMMENT '是否外链 0 路由 1 外链',
    `keep_alive`  tinyint(1) NULL DEFAULT 0 COMMENT '是否缓存 0 不缓存 1 缓存',
    `hidden`      tinyint(1) NULL DEFAULT 1 COMMENT '是否隐藏 1 隐藏 0 显示',
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
VALUES (1578702340612325378, 1111111111111111111, 1578702340666851329, '日志管理', 'log', 1, 'el-icon-s-comment', '/log',
        '/sys/log/LogView', 'sys:log:list', 0, 0, 0, 7, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:24', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713986, 1111111111111111111, 1580357263003439106, '设计器', 'designer', 1, 'el-icon-user-solid',
        '/designer', '/designer/designer/DesignerView', 'designer:show', 0, 0, 0, 2, 'admin', 'admin',
        '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713987, 1111111111111111111, 1578702340683628545, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:user:modify', 0, 0, 0, 4, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340620713988, 1111111111111111111, 1578702340683628545, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:user:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908290, 1111111111111111111, 1578702340683628545, '添加', NULL, 2, NULL, NULL, NULL,
        'sys:user:create', 0, 0, 0, 2, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908291, 1111111111111111111, 1578702340683628546, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:menu:modify', 0, 0, 0, 2, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908292, 1111111111111111111, 1578702340683628546, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:menu:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340624908293, 1111111111111111111, 1578702340683628546, '添加', NULL, 2, NULL, NULL, NULL,
        'sys:menu:create', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340633296898, 1111111111111111111, 1578702340662657026, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:dept:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340633296899, 1111111111111111111, 1578702340662657026, '添加', NULL, 2, NULL, NULL, NULL,
        'sys:dept:create', 0, 0, 0, 2, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340641685505, 1111111111111111111, 1578702340654268418, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:role:modify', 0, 0, 0, 2, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340641685506, 1111111111111111111, 1578702340654268418, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:role:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074114, 1111111111111111111, 1578702340654268418, '添加', NULL, 2, NULL, NULL, NULL,
        'sys:role:create', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074115, 1111111111111111111, 1578702340662657027, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:dict:modify', 0, 0, 0, 2, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074116, 1111111111111111111, 1578702340662657027, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:dict:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340650074117, 1111111111111111111, 1578702340662657027, '添加', NULL, 2, NULL, NULL, NULL,
        'sys:dict:create', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268411, 1111111111111111111, 1578702340612325378, '清空表', NULL, 2, NULL, NULL, NULL,
        'sys:log:clear', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268412, 1111111111111111111, 1578702340666851329, '租户管理', 'tenant', 1, 'el-icon-user-solid',
        '/tenant', '/sys/tenant/TenantView', 'sys:tenant:list', 0, 0, 0, 10, 'admin', 'admin', '2022-12-04 12:30:26',
        NULL, NULL, '2022-12-11 01:20:24', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268416, 1111111111111111111, 1578702340666851329, '岗位管理', 'post', 1, 'el-icon-user-solid', '/post',
        '/sys/post/PostView', 'sys:post:list', 0, 0, 0, 4, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:24', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268417, 1111111111111111111, 1578702340612325378, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:log:delete', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340654268418, 1111111111111111111, 1578702340666851329, '角色管理', 'role', 1, 'el-icon-s-ticket', '/role',
        '/sys/role/RoleView', 'sys:role:list', 0, 0, 0, 5, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:24', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340662657026, 1111111111111111111, 1578702340666851329, '部门管理', 'dept', 1, 'el-icon-s-check', '/dept',
        '/sys/dept/DeptView', 'sys:dept:list', 0, 0, 0, 9, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:24', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340662657027, 1111111111111111111, 1578702340666851329, '字典管理', 'dict', 1, 'el-icon-user-solid', '/dict',
        '/sys/dict/DictView', 'sys:dict:list', 0, 0, 0, 6, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:24', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340666851329, 1111111111111111111, 1111111111111111111, '系统设置', '', 0, 'el-icon-setting', '/sys', '', '',
        0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340671045634, 1111111111111111111, 1578702340666851329, '平台管理', 'platform', 1, 'el-icon-s-platform',
        '/platform', '/sys/platform/PlatformView', 'sys:platform:list', 0, 0, 0, 1, 'admin', 'admin',
        '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340671045635, 1111111111111111111, 1578702340671045634, '添加', NULL, 2, NULL, NULL, NULL,
        'sys:platform:create', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:24',
        0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340679434241, 1111111111111111111, 1578702340671045634, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:platform:modify', 0, 0, 0, 2, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25',
        0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340679434243, 1111111111111111111, 1578702340671045634, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:platform:delete', 0, 0, 0, 3, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25',
        0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340683628545, 1111111111111111111, 1578702340666851329, '用户管理', 'user', 1, 'el-icon-user-solid', '/user',
        '/sys/user/UserView', 'sys:user:list', 0, 0, 0, 2, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1578702340683628546, 1111111111111111111, 1578702340666851329, '菜单管理', 'menu', 1, 'el-icon-folder-opened',
        '/menu', '/sys/menu/MenuView', 'sys:menu:list', 0, 0, 0, 3, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1580357263003439106, 1111111111111111111, 1111111111111111111, '工作流', NULL, 0, 'el-icon-s-operation', '/flow',
        NULL, NULL, 0, 0, 0, 4, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1580357773622202370, 1111111111111111111, 1580357263003439106, '流程分类', 'type', 1, 'el-icon-s-tools', '/type',
        '/flow/type/TypeView', 'flow:type:list', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1581843318345035778, 1111111111111111111, 1578702340662657026, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:dept:modify', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1581965904601088001, 1111111111111111111, 1581966349440581634, '测试KeepAive', 'testKeep', 1, 'abc12312312',
        '/testKeep', '/test/testKeep/TestKeepView', 'keep:create', 0, 1, 0, 3, 'admin', 'admin', '2022-12-04 12:30:26',
        NULL, NULL, '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1581965904601088002, 1111111111111111111, 1581966349440581634, '测试外部链接', NULL, 1, 'abc12312312',
        'http://ww.baidu.com', NULL, NULL, 1, 0, 0, 3, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1581966349440581634, 1111111111111111111, 1111111111111111111, '相关测试', NULL, 0, 'abc12312312', '/test', NULL,
        NULL, 0, 0, 0, 5, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582554585967800321, 1111111111111111111, 1111111111111111111, '监控平台', NULL, 0, 'el-icon-camera', '/monitor',
        NULL, NULL, 0, 0, 0, 3, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582555155344568321, 1111111111111111111, 1582554585967800321, 'swagger', NULL, 1, 'abc12312312',
        'http://localhost:9000/swagger-ui/index.html', NULL, NULL, 1, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26',
        NULL, NULL, '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582558188828790785, 1111111111111111111, 1582554585967800321, '德鲁伊', NULL, 1, 'bcd12312',
        'http://localhost:9000/druid/login.html', NULL, NULL, 1, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL,
        NULL, '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582607135668621314, 1111111111111111111, 1581966349440581634, '掘金', NULL, 1, 'abc12312312',
        'https://juejin.cn/', NULL, NULL, 1, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1582688861908611074, 1111111111111111111, 1581965904601088002, 'elementUI', NULL, 1, 'abc12312312',
        'https://element.eleme.cn', NULL, NULL, 1, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1586717542633123841, 1111111111111111111, 1578702340683628545, '用户角色配置', 'userRole', 1, NULL, '/sysRole',
        '/sys/user/role/UserRoleView', 'sys:role:list', 0, 1, 1, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1586717542633123843, 1111111111111111111, 1578702340662657027, '字典项', 'dictItem', 1, NULL, '/dictItem',
        '/sys/dict/item/DictItemView', 'sys:item:list', 0, 0, 1, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1587692336744742913, 1111111111111111111, 1578702340683628545, '详情', NULL, 2, NULL, NULL, NULL, 'sys:user:info',
        0, 0, 0, 4, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230018781, 1111111111111111111, 9223372036854775119, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:msg:modify', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230018782, 1111111111111111111, 9223372036854775120, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:userMsg:modify', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048172, 1111111111111111111, 9223372036854775119, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:msg:delete', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048178, 1111111111111111111, 9223372036854775120, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:userMsg:delete', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048770, 1111111111111111111, 1578702340654268416, '添加', NULL, 2, NULL, NULL, NULL,
        'sys:post:create', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048771, 1111111111111111111, 1578702340654268416, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:post:modify', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048772, 1111111111111111111, 1578702340654268416, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:post:delete', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048778, 1111111111111111111, 1594135789623984129, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:file:delete', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048781, 1111111111111111111, 1578702340654268412, '添加', NULL, 2, NULL, NULL, NULL,
        'sys:tenant:create', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048782, 1111111111111111111, 1578702340654268412, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:tenant:modify', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230048783, 1111111111111111111, 1578702340654268412, '删除', NULL, 2, NULL, NULL, NULL,
        'sys:tenant:delete', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589181822230049781, 1111111111111111111, 9223372036854775119, '添加', NULL, 2, NULL, NULL, NULL,
        'sys:msg:create', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1589789746153263106, 1111111111111111111, 9223372036854775807, '保存', NULL, 2, NULL, NULL, NULL,
        'sys:dataPermission:create', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1594135789623984129, 1111111111111111111, 1578702340666851329, '系统文件', 'file', 1, 'abc12312312', '/file',
        '/sys/file/FileView', 'sys:file:list', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1594531945449451666, 1111111111111111111, 1594135789623984129, '预览', NULL, 2, NULL, NULL, NULL,
        'sys:file:preview', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1594532038764326666, 1111111111111111111, 1594135789623984129, '文件上传', NULL, 2, NULL, NULL, NULL,
        'sys:file:upload', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1594532038764326913, 1111111111111111111, 1594135789623984129, '下载', NULL, 2, NULL, NULL, NULL,
        'sys:file:download', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1597480827938615297, 1111111111111111111, 1111111111111111111, '消息管理', NULL, 0, 'bcd12312', '/msg', NULL, NULL,
        0, 0, 0, 2, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1598222373868695553, 1111111111111111111, 9223372036854775807, '删除', NULL, 2, NULL, 'sys:permission:delete',
        NULL, 'sys:dataPermission:delete', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1598222575933485057, 1111111111111111111, 9223372036854775807, '修改', NULL, 2, NULL, NULL, NULL,
        'sys:dataPermission:modify', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1599935876379897858, 1111111111111111111, 1586717542633123841, '用户增加角色', NULL, 2, NULL, NULL, NULL,
        'sys:user:userSetRole', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25',
        0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1599935889646481409, 1111111111111111111, 1, '用户增加角色', NULL, 2, NULL, NULL, NULL, 'sys:user:userSetRole', 0, 0,
        0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1599935894306353153, 1111111111111111111, 1, '用户增加角色', NULL, 2, NULL, NULL, NULL, 'sys:user:userSetRole', 0, 0,
        0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1599936079744921601, 1111111111111111111, 1, '用户增加角色', NULL, 2, NULL, NULL, NULL, 'sys:user:userSetRole', 0, 0,
        0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1599936405688479746, 1111111111111111111, 1586717542633123841, '重置密码', NULL, 2, NULL, NULL, NULL,
        'sys:user:resetPass', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1601081152259891202, 1111111111111111111, 1578702340683628545, '导出', NULL, 2, NULL, NULL, NULL,
        'sys:user:export', 0, 0, 0, 1, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0,
        NULL, 1);
INSERT INTO `sys_menu`
VALUES (1601803560176451586, 1111111111111111111, 1111111111111111111, '123', '123', 1, NULL, '123', '123', '123', 0, 0,
        0, 1, 'admin', 'admin', '2022-12-11 12:58:23', 'admin', 'admin', '2022-12-11 01:20:25', 1, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1601808827316314114, 1111111111111111111, 1111111111111111111, '1', NULL, 0, NULL, '1', NULL, NULL, 0, 0, 0, 1,
        'admin', 'admin', '2022-12-11 13:19:19', 'admin', 'admin', '2022-12-11 13:29:06', 1, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1601811184070565890, 1111111111111111111, 1601808827316314114, '123', '123', 1, NULL, '123', '123', '123', 0, 0,
        0, 1, 'admin', 'admin', '2022-12-11 13:28:41', 'admin', 'admin', '2022-12-11 13:29:03', 1, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1601811237686353922, 1111111111111111111, 1601811184070565890, '123', NULL, 2, NULL, NULL, NULL, '123', 0, 0, 0,
        1, 'admin', 'admin', '2022-12-11 13:28:54', 'admin', 'admin', '2022-12-11 13:29:01', 1, NULL, 1);
INSERT INTO `sys_menu`
VALUES (1601858203636092929, 1111111111111111111, 1578702340654268418, '数据权限', NULL, 2, NULL, NULL, NULL,
        'sys:dataPermission:editRoleDataPermission', 0, 0, 0, 1, 'admin', 'admin', '2022-12-11 16:35:31', 'admin',
        'admin', '2022-12-11 16:44:11', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (9223372036854775119, 1111111111111111111, 1597480827938615297, '消息公告', 'msg', 1, 'el-icon-s-operation', '/msg',
        '/msg/msg/MsgView', 'sys:msg:list', 0, 0, 0, 8, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL,
        '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (9223372036854775120, 1111111111111111111, 1597480827938615297, '用户消息', 'userMsg', 1, 'el-icon-s-operation',
        '/userMsg', '/msg/userMsg/UserMsgView', 'sys:userMsg:list', 0, 0, 0, 8, 'admin', 'admin', '2022-12-04 12:30:26',
        NULL, NULL, '2022-12-11 01:20:25', 0, NULL, 1);
INSERT INTO `sys_menu`
VALUES (9223372036854775807, 1111111111111111111, 1578702340666851329, '数据权限', 'dataPermission', 1,
        'el-icon-s-operation', '/dataPermission', '/sys/dataPermission/DataPermissionView', 'sys:dataPermission:list',
        0, 0, 0, 8, 'admin', 'admin', '2022-12-04 12:30:26', NULL, NULL, '2022-12-11 01:20:25', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_msg
-- ----------------------------
DROP TABLE IF EXISTS `sys_msg`;
CREATE TABLE `sys_msg`
(
    `id`          bigint(22) NOT NULL,
    `msg_title`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息标题',
    `msg_code`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息编码',
    `msg_type`    tinyint(1) NULL DEFAULT NULL COMMENT '消息类型 0 通知 1 公告',
    `msg_level`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息级别 error 紧急消息（多次提醒） info 一般消息 warning 警示消息 success 正常消息',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_msg
-- ----------------------------
INSERT INTO `sys_msg`
VALUES (1594154596111454210, '你好世界', 'Halo3', 1, 'warning', '你好', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_msg`
VALUES (1594154596111454211, '你好世界', 'Halo2', 1, 'warning', '你好', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_msg`
VALUES (1594154596111454212, '你好世界', 'Halo1', 1, 'danger', '你好', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_msg`
VALUES (1595966082236538882, '你好世界', 'Halo', 1, 'info', '你好', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

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
VALUES (1111111111111111111, '后台管理中心', 'managementCenter', '后台管理中心', NULL, NULL, NULL, NULL, NULL,
        '2022-12-11 01:20:20', 0, NULL, 1);
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post`
VALUES (1591377257933819906, '123', '123', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

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
VALUES (1589074115103707138, 'ROLE_SIMPLE', '普通用户', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role`
VALUES (1591282373843464193, 'ROLE_MINI', '小程序游客登录用户', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_role_data_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_data_permission`;
CREATE TABLE `sys_role_data_permission`
(
    `id`                 bigint(22) NOT NULL,
    `data_permission_id` bigint(22) NULL DEFAULT NULL COMMENT '规则权限ID',
    `role_id`            bigint(22) NULL DEFAULT NULL COMMENT '角色ID',
    `create_by`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time`        datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
    `update_time`        datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`          tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`          bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_data_permission
-- ----------------------------
INSERT INTO `sys_role_data_permission`
VALUES (1601870145415184386, 4, 1565322827518140417, 'admin', 'admin', '2022-12-11 17:22:59', NULL, NULL,
        '2022-12-11 06:00:54', 0, NULL, 1);
INSERT INTO `sys_role_data_permission`
VALUES (1601870145431961602, 5, 1565322827518140417, 'admin', 'admin', '2022-12-11 17:22:59', NULL, NULL,
        '2022-12-11 06:00:54', 0, NULL, 1);
INSERT INTO `sys_role_data_permission`
VALUES (1601870145452933121, 1591663064062078978, 1565322827518140417, 'admin', 'admin', '2022-12-11 17:22:59', NULL,
        NULL, '2022-12-11 06:00:54', 0, NULL, 1);

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
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
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
VALUES (1599298446392025089, 1578702340671045634, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446408802306, 1578702340683628545, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446412996609, 1578702340612325378, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446421385218, 1578702340654268411, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446421385219, 1578702340654268417, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446421385220, 9223372036854775807, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446429773825, 1582554585967800321, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446438162434, 1582555155344568321, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446438162435, 1582558188828790785, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446446551041, 1580357263003439106, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446454939650, 1578702340620713986, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446454939651, 1581966349440581634, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446463328258, 1582607135668621314, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446471716866, 1581965904601088001, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446471716867, 1581965904601088002, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1599298446475911169, 1582688861908611074, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072061878274, 1578702340666851329, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072087044098, 1578702340671045634, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072095432705, 1578702340671045635, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072095432706, 1578702340679434241, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072103821313, 1578702340679434243, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072103821314, 1594135789623984129, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072112209921, 1594532038764326913, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072120598529, 1594532038764326666, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072120598530, 1594531945449451666, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072128987138, 1589181822230048778, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072128987139, 1578702340683628545, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072137375745, 1586717542633123841, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072141570050, 1599935876379897858, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072149958658, 1599936405688479746, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072149958659, 1601081152259891202, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072149958660, 1578702340624908290, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072158347265, 1578702340620713988, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072158347266, 1587692336744742913, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072166735873, 1578702340620713987, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072175124482, 1578702340683628546, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072175124483, 1578702340624908293, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072183513090, 1578702340624908291, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072183513091, 1578702340624908292, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072191901698, 1578702340654268416, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072191901699, 1589181822230048770, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072200290306, 1589181822230048771, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072200290307, 1589181822230048772, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072208678913, 1578702340654268418, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072208678914, 1578702340650074114, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072212873217, 1601858203636092929, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072221261825, 1578702340641685505, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072221261826, 1578702340641685506, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072225456129, 1578702340662657027, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072225456130, 1578702340650074117, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072233844738, 1586717542633123843, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072233844739, 1578702340650074115, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072242233345, 1578702340650074116, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072242233346, 1578702340612325378, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072246427650, 1578702340654268411, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072246427651, 1578702340654268417, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072254816257, 9223372036854775807, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072254816258, 1589789746153263106, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072263204865, 1598222575933485057, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072271593473, 1598222373868695553, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072271593474, 1578702340662657026, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072279982081, 1581843318345035778, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072279982082, 1578702340633296899, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072288370690, 1578702340633296898, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072288370691, 1578702340654268412, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072296759298, 1589181822230048781, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072296759299, 1589181822230048782, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072300953601, 1589181822230048783, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072300953602, 1597480827938615297, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072309342210, 9223372036854775120, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072313536514, 1589181822230018782, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072317730818, 1589181822230048178, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072317730819, 9223372036854775119, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072317730820, 1589181822230049781, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072326119425, 1589181822230018781, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072334508034, 1589181822230048172, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072334508035, 1582554585967800321, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072342896641, 1582555155344568321, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072351285249, 1582558188828790785, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072351285250, 1580357263003439106, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072351285251, 1580357773622202370, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072359673857, 1578702340620713986, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072359673858, 1581966349440581634, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072368062465, 1582607135668621314, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072368062466, 1581965904601088001, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072376451073, 1581965904601088002, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu`
VALUES (1601861072376451074, 1582688861908611074, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

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
VALUES (1, '公司', 'GS', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint(22) NOT NULL,
    `username`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户姓名',
    `user_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户编码',
    `amount_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录账户名称',
    `avatar`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
    `password`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
    `post_id`     bigint(22) NULL DEFAULT NULL COMMENT '岗位ID',
    `dept_id`     bigint(22) NULL DEFAULT NULL COMMENT '部门ID',
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
VALUES (1, 'admin', 'admin', '超级管理员', NULL, '$2a$10$0oaHJPN7Pqq49dUqQgUVSug5l1ELUqjvuDZ5BIJwr2PJyGqzMjIca',
        1591377257933819906, 1581851971500371970, 0, '371521123456789', '17812345678', '123123',
        'breeze-cloud1@foxmail.com', 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user`
VALUES (2, 'user', 'user', '普通用户', NULL, '$2a$10$UrXDeYnDfvGR4BUm.e80LeSyPJTj1zyqECqO6Whd.Wx4v0W64gsDa',
        1591377257933819906, 1565314987957145609, 1, '371521123456789', '17812345671', '1231231',
        'breeze-cloud@foxmail.com', 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_user_msg
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_msg`;
CREATE TABLE `sys_user_msg`
(
    `id`              bigint(22) NOT NULL,
    `user_id`         bigint(22) NULL DEFAULT NULL COMMENT '用户ID',
    `msg_code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息编码',
    `msg_snapshot_id` bigint(22) NULL DEFAULT NULL COMMENT '消息快照ID',
    `mark_read`       tinyint(1) NULL DEFAULT 0 COMMENT '标记已读 0 未读 1 已读',
    `mark_close`      tinyint(1) NULL DEFAULT 0 COMMENT '标记关闭 0 未关闭 1 已关闭',
    `dept_id`         bigint(22) NULL DEFAULT NULL,
    `create_by`       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
    `create_name`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
    `create_time`     datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
    `update_name`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
    `update_time`     datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `is_delete`       tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    `delete_by`       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
    `tenant_id`       bigint(22) NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_msg_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_msg_snapshot`;
CREATE TABLE `sys_user_msg_snapshot`
(
    `id`          bigint(22) NOT NULL,
    `msg_id`      bigint(22) NULL DEFAULT NULL COMMENT '消息ID',
    `msg_title`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息标题',
    `msg_type`    tinyint(1) NULL DEFAULT NULL COMMENT '消息类型 0 通知 1 公告',
    `msg_level`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息级别 error 紧急消息（多次提醒） info 一般消息 warning 警示消消息 success 正常消息',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息快照' ROW_FORMAT = Dynamic;

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
VALUES (1595606952636207106, 2, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_role`
VALUES (1596336767207194626, 1, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

SET
FOREIGN_KEY_CHECKS = 1;
