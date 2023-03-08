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

 Date: 08/03/2023 23:17:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_banner
-- ----------------------------
DROP TABLE IF EXISTS `sys_banner`;
CREATE TABLE `sys_banner`  (
  `id` bigint(22) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_data_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_permission`;
CREATE TABLE `sys_data_permission`  (
  `id` bigint(22) NOT NULL,
  `data_permission_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `data_permission_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限编码',
  `data_permission_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识 ALL 全部 DEPT_LEVEL 部门 DEPT_AND_LOWER_LEVEL 部门和子部门 OWN 自己 DIY_DEPT 自定义部门 DIY 自定义',
  `operator` varchar(22) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运算符号',
  `str_sql` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义SQL',
  `data_permissions` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据权限规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_data_permission
-- ----------------------------
INSERT INTO `sys_data_permission` VALUES (1, '全部', NULL, 'ALL', 'OR', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_data_permission` VALUES (3, '本级部门', NULL, 'DEPT_LEVEL', 'OR', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_data_permission` VALUES (4, '自己', NULL, 'OWN', 'OR', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_data_permission` VALUES (5, '自定义部门', NULL, 'DIY_DEPT', 'OR', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_data_permission` VALUES (1591663064062078978, '本级部门和下属部门', '123', 'DEPT_AND_LOWER_LEVEL', 'OR', '', '1601814619176181761,1601814700566650882', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_data_permission` VALUES (1601813397580947458, '用户消息查看数据权限', 'USER_MSG', 'DEPT_LEVEL', 'OR', '  (  ( a.msg_code IS NOT NULL  ) AND ( a.id = 1 )  ) ', '1565314987957145609', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_data_permission_custom
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_permission_custom`;
CREATE TABLE `sys_data_permission_custom`  (
  `id` bigint(22) NOT NULL,
  `data_permission_id` bigint(22) NULL DEFAULT NULL COMMENT '数据权限ID',
  `compare` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '比较',
  `table_column` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名',
  `operator` varchar(22) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运算符号',
  `conditions` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '条件',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据权限自定义规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_data_permission_custom
-- ----------------------------
INSERT INTO `sys_data_permission_custom` VALUES (1601854258998124545, 1601813397580947458, 'IS NOT NULL', 'msg_code', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_data_permission_custom` VALUES (1601854259065233410, 1601813397580947458, '=', 'id', 'AND', '1', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(22) NOT NULL DEFAULT 1111111111111111111,
  `parent_id` bigint(22) NULL DEFAULT 0 COMMENT '上级部门ID',
  `dept_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门编码',
  `dept_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1565314987957145600, 1111111111111111111, 'GS', '总公司', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dept` VALUES (1565314987957145609, 1565314987957145600, 'DSB', '董事办', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dept` VALUES (1581851971500371970, 1565314987957145600, 'IT', 'IT研发部门', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dept` VALUES (1601579918477983745, 1581851971500371970, 'Java1', '研发组1', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dept` VALUES (1601579970948726786, 1581851971500371970, 'Java2', 'Java2', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(22) NOT NULL,
  `dict_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `dict_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '字典编码',
  `is_open` tinyint(1) NULL DEFAULT 0 COMMENT '是否启用 0 关闭 1 启用',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1599032827285213185, '性别', 'SEX', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599033063277727745, '菜单类型', 'MENU_TYPE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599034133752188930, '日志类型', 'LOG_TYPE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599034233434017794, '操作类型', 'DO_TYPE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599035056616509442, '日志结果', 'LOG_RESULT', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599035447399813121, '消息级别', 'MSG_LEVEL', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599035906529300481, '消息类型', 'MSG_TYPE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599036245466812417, '开关', 'OPEN', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599036494331645953, '缓存', 'KEEPALIVE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599036771814215681, '显示隐藏', 'HIDDEN', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599037134667649025, '路由外链', 'HREF', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599037528810590209, '存储方式', 'OSS_STYLE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599218032822394881, '结果', 'RESULT', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599288041217064962, '锁定', 'IS_LOCK', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599292998100058114, '读取状态', 'MARK_READ', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599293985942536193, '消息发送方式', 'SEND_MSG_TYPE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1601793691449020417, '数据权限类型', 'DATA_PERMISSION_TYPE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1601817524482482177, '数据权限运算符', 'COMPARE', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` bigint(22) NOT NULL,
  `dict_id` bigint(22) NULL DEFAULT NULL COMMENT '字典ID',
  `key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '字典项的值',
  `value` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典项的名称',
  `sort` tinyint(1) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES (1599033180131037186, 1599033063277727745, '0', '文件夹', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599033573409951745, 1599033063277727745, '1', '菜单', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599033675105046530, 1599033063277727745, '2', '按钮', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599033861437001729, 1599032827285213185, '1', '男', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599033925890871297, 1599032827285213185, '0', '女', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599034388589711362, 1599034133752188930, '0', ' 普通日志', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599034442968862721, 1599034133752188930, '1', ' 登录日志', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599034596094513154, 1599034233434017794, '0', '添加', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599034610313203714, 1599034233434017794, '1', '删除', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599034627035893761, 1599034233434017794, '2', '修改', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599034642189914113, 1599034233434017794, '3', '查询', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599035133351301122, 1599035056616509442, '0', '失败', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599035172446408705, 1599035056616509442, '1', ' 成功', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599035472632745985, 1599035447399813121, 'success', '正常消息', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599035484666204161, 1599035447399813121, 'warning', '警示消息', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599035496510918657, 1599035447399813121, 'info', '一般消息', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599035508733120513, 1599035447399813121, 'danger', '紧急消息', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599036005007364098, 1599035906529300481, '0', ' 通知', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599036025513316353, 1599035906529300481, '1', '公告', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599036294716329985, 1599036245466812417, '1', '开启', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599036318925852674, 1599036245466812417, '0 ', '关闭', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599036529471524866, 1599036494331645953, '0', '不缓存 ', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599036549079896065, 1599036494331645953, '1', '缓存', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599036842584707074, 1599036771814215681, '0', '显示', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599036909861343234, 1599036771814215681, '1', '隐藏', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599037293560467457, 1599037134667649025, '1', '外链', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599037318663376897, 1599037134667649025, '0', '路由', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599037823330422786, 1599037528810590209, '0', '本地', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599037849897144321, 1599037528810590209, '1', 'MINIO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599218200087044097, 1599218032822394881, '1', '成功', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599218217967362049, 1599218032822394881, '0', '失败', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599288066458386434, 1599288041217064962, '0', '正常', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599288094061101058, 1599288041217064962, '1', '锁定', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599293037463601154, 1599292998100058114, '1', '已读', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599293192749318145, 1599292998100058114, '0', '未读', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599294372594450433, 1599293985942536193, '1', '部门的用户', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1599294501808373761, 1599293985942536193, '2', '自定义部门的用户', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1601793891890614273, 1601793691449020417, 'ALL', '全部', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1601793991845072897, 1601793691449020417, 'OWN', '自己', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1601794088121126914, 1601793691449020417, 'DEPT_AND_LOWER_LEVEL', '本级以及下属部门', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1601794253766774785, 1601793691449020417, 'DEPT_LEVEL', '本级部门', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1601794312206012418, 1601793691449020417, 'DIY_DEPT', '自定义部门', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1601825250998652930, 1601817524482482177, '>', '大于', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1601825929611874305, 1601817524482482177, ' >=', '大于等于', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1601826138823757825, 1601817524482482177, '<', '小于', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1601826169953882113, 1601817524482482177, '<=', '小于等于', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1601826214673551361, 1601817524482482177, '!=', '不等于', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1601826299918585857, 1601817524482482177, '=', '等于', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1601826517330333697, 1601817524482482177, 'IS NOT NULL', '不等于空', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1601826978187874306, 1601817524482482177, 'IS NULL', '等于空', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` bigint(22) NOT NULL,
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件标题',
  `original_file_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原文件名称',
  `content_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '格式',
  `new_file_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新文件名字',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件存放路径',
  `oss_style` tinyint(1) NOT NULL COMMENT '存储方式 0 本地 1 minio',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(22) NOT NULL,
  `system_module` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统模块',
  `log_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志标题',
  `log_type` tinyint(1) NULL DEFAULT 0 COMMENT '日志类型 0 普通日志 1 登录日志',
  `request_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求类型  GET  POST  PUT DELETE ',
  `ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `do_type` tinyint(1) NULL DEFAULT 3 COMMENT '操作类型 0 添加 1 删除 2 修改 3 查询',
  `browser` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器名称',
  `system` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统类型',
  `param_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '入参',
  `result` tinyint(1) NULL DEFAULT 0 COMMENT '结果 0 失败 1 成功',
  `result_msg` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结果信息',
  `time` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用时',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(22) NOT NULL DEFAULT 1111111111111111111,
  `platform_id` bigint(22) NULL DEFAULT NULL COMMENT '平台ID',
  `parent_id` bigint(22) NULL DEFAULT 0 COMMENT '上一级的菜单ID',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名称',
  `type` tinyint(1) NULL DEFAULT 0 COMMENT '类型 0 文件夹 1 菜单 2 按钮',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `path` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `component` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `permission` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `href` tinyint(1) NULL DEFAULT 0 COMMENT '是否外链 0 路由 1 外链',
  `keep_alive` tinyint(1) NULL DEFAULT 0 COMMENT '是否缓存 0 不缓存 1 缓存',
  `hidden` tinyint(1) NULL DEFAULT 1 COMMENT '是否隐藏 1 隐藏 0 显示',
  `sort` int(11) NULL DEFAULT NULL COMMENT '顺序',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1578702340612325378, 1111111111111111111, 1578702340666851329, '日志管理', 'log', 1, 'el-icon-s-comment', '/log', '/sys/log/LogView', 'sys:log:list', 0, 0, 0, 7, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340620713986, 1111111111111111111, 1580357263003439106, '设计器', 'designer', 1, 'el-icon-user-solid', '/designer', '/process/designer/DesignerView', 'process:designer', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340620713987, 1111111111111111111, 1578702340683628545, '修改', NULL, 2, NULL, NULL, NULL, 'sys:user:modify', 0, 0, 0, 5, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340620713988, 1111111111111111111, 1578702340683628545, '删除', NULL, 2, NULL, NULL, NULL, 'sys:user:delete', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340624908290, 1111111111111111111, 1578702340683628545, '添加', NULL, 2, NULL, NULL, NULL, 'sys:user:create', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340624908291, 1111111111111111111, 1578702340683628546, '修改', NULL, 2, NULL, NULL, NULL, 'sys:menu:modify', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340624908292, 1111111111111111111, 1578702340683628546, '删除', NULL, 2, NULL, NULL, NULL, 'sys:menu:delete', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340624908293, 1111111111111111111, 1578702340683628546, '添加', NULL, 2, NULL, NULL, NULL, 'sys:menu:create', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340633296898, 1111111111111111111, 1578702340662657026, '删除', NULL, 2, NULL, NULL, NULL, 'sys:dept:delete', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340633296899, 1111111111111111111, 1578702340662657026, '添加', NULL, 2, NULL, NULL, NULL, 'sys:dept:create', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340641685505, 1111111111111111111, 1578702340654268418, '修改', NULL, 2, NULL, NULL, NULL, 'sys:role:modify', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340641685506, 1111111111111111111, 1578702340654268418, '删除', NULL, 2, NULL, NULL, NULL, 'sys:role:delete', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340650074114, 1111111111111111111, 1578702340654268418, '添加', NULL, 2, NULL, NULL, NULL, 'sys:role:create', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340650074115, 1111111111111111111, 1578702340662657027, '修改', NULL, 2, NULL, NULL, NULL, 'sys:dict:modify', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340650074116, 1111111111111111111, 1578702340662657027, '删除', NULL, 2, NULL, NULL, NULL, 'sys:dict:delete', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340650074117, 1111111111111111111, 1578702340662657027, '添加', NULL, 2, NULL, NULL, NULL, 'sys:dict:create', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340654268411, 1111111111111111111, 1578702340612325378, '清空表', NULL, 2, NULL, NULL, NULL, 'sys:log:clear', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340654268412, 1111111111111111111, 1578702340666851329, '租户管理', 'tenant', 1, 'el-icon-user-solid', '/tenant', '/sys/tenant/TenantView', 'sys:tenant:list', 0, 0, 0, 10, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340654268416, 1111111111111111111, 1578702340666851329, '岗位管理', 'post', 1, 'el-icon-user-solid', '/post', '/sys/post/PostView', 'sys:post:list', 0, 0, 0, 4, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340654268417, 1111111111111111111, 1578702340612325378, '删除', NULL, 2, NULL, NULL, NULL, 'sys:log:delete', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340654268418, 1111111111111111111, 1578702340666851329, '角色管理', 'role', 1, 'el-icon-s-ticket', '/role', '/sys/role/RoleView', 'sys:role:list', 0, 0, 0, 5, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340662657026, 1111111111111111111, 1578702340666851329, '部门管理', 'dept', 1, 'el-icon-s-check', '/dept', '/sys/dept/DeptView', 'sys:dept:list', 0, 0, 0, 9, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340662657027, 1111111111111111111, 1578702340666851329, '字典管理', 'dict', 1, 'el-icon-user-solid', '/dict', '/sys/dict/DictView', 'sys:dict:list', 0, 0, 0, 6, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340666851329, 1111111111111111111, 1111111111111111111, '系统设置', '', 0, 'el-icon-setting', '/sys', '', '', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340671045634, 1111111111111111111, 1578702340666851329, '平台管理', 'platform', 1, 'el-icon-s-platform', '/platform', '/sys/platform/PlatformView', 'sys:platform:list', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340671045635, 1111111111111111111, 1578702340671045634, '添加', NULL, 2, NULL, NULL, NULL, 'sys:platform:create', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340679434241, 1111111111111111111, 1578702340671045634, '修改', NULL, 2, NULL, NULL, NULL, 'sys:platform:modify', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340679434243, 1111111111111111111, 1578702340671045634, '删除', NULL, 2, NULL, NULL, NULL, 'sys:platform:delete', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340683628545, 1111111111111111111, 1578702340666851329, '用户管理', 'user', 1, 'el-icon-user-solid', '/user', '/sys/user/UserView', 'sys:user:list', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340683628546, 1111111111111111111, 1578702340666851329, '菜单管理', 'menu', 1, 'el-icon-folder-opened', '/menu', '/sys/menu/MenuView', 'sys:menu:list', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1580357263003439106, 1111111111111111111, 1111111111111111111, '流程管理', NULL, 0, 'el-icon-s-operation', '/process', NULL, NULL, 0, 0, 0, 4, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1580357773622202370, 1111111111111111111, 1580357263003439106, '流程分类', 'category', 1, 'el-icon-s-tools', '/category', '/process/category/CategoryView', 'process:category:list', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1581843318345035778, 1111111111111111111, 1578702340662657026, '修改', NULL, 2, NULL, NULL, NULL, 'sys:dept:modify', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1581965904601088001, 1111111111111111111, 1581966349440581634, '测试KeepAive', 'testKeep', 1, 'abc12312312', '/testKeep', '/test/testKeep/TestKeepView', 'keep:create', 0, 1, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1581965904601088002, 1111111111111111111, 1581966349440581634, '测试外部链接', NULL, 1, 'abc12312312', 'http://ww.baidu.com', NULL, NULL, 1, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1581966349440581634, 1111111111111111111, 1111111111111111111, '相关测试', NULL, 0, 'abc12312312', '/test', NULL, NULL, 0, 0, 0, 5, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1582554585967800321, 1111111111111111111, 1111111111111111111, '监控平台', NULL, 0, 'el-icon-camera', '/monitor', NULL, NULL, 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1582555155344568321, 1111111111111111111, 1582554585967800321, 'swagger', NULL, 1, 'abc12312312', 'http://localhost:9000/swagger-ui/index.html', NULL, NULL, 1, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1582558188828790785, 1111111111111111111, 1582554585967800321, '德鲁伊', NULL, 1, 'bcd12312', 'http://localhost:9000/druid/login.html', NULL, NULL, 1, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1582607135668621314, 1111111111111111111, 1581966349440581634, '掘金', NULL, 1, 'abc12312312', 'https://juejin.cn/', NULL, NULL, 1, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1582688861908611074, 1111111111111111111, 1581965904601088002, 'elementUI', NULL, 1, 'abc12312312', 'https://element.eleme.cn', NULL, NULL, 1, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1586717542633123841, 1111111111111111111, 1578702340683628545, '角色分配', 'userRole', 1, NULL, '/userRole', '/sys/user/role/UserRoleView', 'sys:role:list', 0, 1, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1586717542633123843, 1111111111111111111, 1578702340662657027, '字典项', 'dictItem', 1, NULL, '/dictItem', '/sys/dict/item/DictItemView', 'sys:item:list', 0, 0, 1, 4, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1587692336744742913, 1111111111111111111, 1578702340683628545, '详情', NULL, 2, NULL, NULL, NULL, 'sys:user:info', 0, 0, 0, 4, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1589181822230018781, 1111111111111111111, 9223372036854775119, '修改', NULL, 2, NULL, NULL, NULL, 'sys:msg:modify', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1589181822230018782, 1111111111111111111, 9223372036854775120, '修改', NULL, 2, NULL, NULL, NULL, 'sys:userMsg:modify', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048172, 1111111111111111111, 9223372036854775119, '删除', NULL, 2, NULL, NULL, NULL, 'sys:msg:delete', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048178, 1111111111111111111, 9223372036854775120, '删除', NULL, 2, NULL, NULL, NULL, 'sys:userMsg:delete', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048770, 1111111111111111111, 1578702340654268416, '添加', NULL, 2, NULL, NULL, NULL, 'sys:post:create', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048771, 1111111111111111111, 1578702340654268416, '修改', NULL, 2, NULL, NULL, NULL, 'sys:post:modify', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048772, 1111111111111111111, 1578702340654268416, '删除', NULL, 2, NULL, NULL, NULL, 'sys:post:delete', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048778, 1111111111111111111, 1594135789623984129, '删除', NULL, 2, NULL, NULL, NULL, 'sys:file:delete', 0, 0, 0, 4, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048781, 1111111111111111111, 1578702340654268412, '添加', NULL, 2, NULL, NULL, NULL, 'sys:tenant:create', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048782, 1111111111111111111, 1578702340654268412, '修改', NULL, 2, NULL, NULL, NULL, 'sys:tenant:modify', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048783, 1111111111111111111, 1578702340654268412, '删除', NULL, 2, NULL, NULL, NULL, 'sys:tenant:delete', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1589181822230049781, 1111111111111111111, 9223372036854775119, '添加', NULL, 2, NULL, NULL, NULL, 'sys:msg:create', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1589789746153263106, 1111111111111111111, 9223372036854775807, '添加', NULL, 2, NULL, NULL, NULL, 'sys:dataPermission:create', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1594135789623984129, 1111111111111111111, 1578702340666851329, '系统文件', 'file', 1, 'abc12312312', '/file', '/sys/file/FileView', 'sys:file:list', 0, 0, 0, 11, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1594531945449451666, 1111111111111111111, 1594135789623984129, '预览', NULL, 2, NULL, NULL, NULL, 'sys:file:preview', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1594532038764326666, 1111111111111111111, 1594135789623984129, '文件上传', NULL, 2, NULL, NULL, NULL, 'sys:file:upload', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1594532038764326913, 1111111111111111111, 1594135789623984129, '下载', NULL, 2, NULL, NULL, NULL, 'sys:file:download', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1597480827938615297, 1111111111111111111, 1111111111111111111, '消息管理', NULL, 0, 'bcd12312', '/msg', NULL, NULL, 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1598222373868695553, 1111111111111111111, 9223372036854775807, '删除', NULL, 2, NULL, 'sys:permission:delete', NULL, 'sys:dataPermission:delete', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1598222575933485057, 1111111111111111111, 9223372036854775807, '修改', NULL, 2, NULL, NULL, NULL, 'sys:dataPermission:modify', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1599935876379897858, 1111111111111111111, 1586717542633123841, '用户增加角色', NULL, 2, NULL, NULL, NULL, 'sys:user:userSetRole', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1599935889646481409, 1111111111111111111, 1, '用户增加角色', NULL, 2, NULL, NULL, NULL, 'sys:user:userSetRole', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1599935894306353153, 1111111111111111111, 1, '用户增加角色', NULL, 2, NULL, NULL, NULL, 'sys:user:userSetRole', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1599936079744921601, 1111111111111111111, 1, '用户增加角色', NULL, 2, NULL, NULL, NULL, 'sys:user:userSetRole', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1599936405688479746, 1111111111111111111, 1586717542633123841, '重置密码', NULL, 2, NULL, NULL, NULL, 'sys:user:resetPass', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1601081152259891202, 1111111111111111111, 1578702340683628545, '导出', NULL, 2, NULL, NULL, NULL, 'sys:user:export', 0, 0, 0, 6, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1601858203636092929, 1111111111111111111, 1578702340654268418, '数据权限', NULL, 2, NULL, NULL, NULL, 'sys:dataPermission:editRoleDataPermission', 0, 0, 0, 4, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935233, 1111111111111111111, 1578702340620713986, '流程部署', NULL, 2, NULL, NULL, NULL, 'process:definition:deploy', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935234, 1111111111111111111, 1580357773622202370, '添加', NULL, 2, NULL, NULL, NULL, 'process:category:create', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935235, 1111111111111111111, 1580357773622202370, '删除', NULL, 2, NULL, NULL, NULL, 'process:category:delete', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935256, 1111111111111111111, 1580357773622202370, '修改', NULL, 2, NULL, NULL, NULL, 'process:category:modify', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632925687029903361, 1111111111111111111, 9223372036854775121, '查看', NULL, 2, NULL, NULL, NULL, 'process:definition:info', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632925792583757826, 1111111111111111111, 9223372036854775121, '删除', NULL, 2, NULL, NULL, NULL, 'process:definition:delete', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632950027968548866, 1111111111111111111, 9223372036854775121, '编辑', NULL, 2, NULL, NULL, NULL, 'process:definition:modify', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632950163226464257, 1111111111111111111, 9223372036854775121, '启动', NULL, 2, NULL, NULL, NULL, 'process:instance:start', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632950317358747649, 1111111111111111111, 9223372036854775121, '设计', NULL, 2, NULL, NULL, NULL, 'process:designer', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632979037821743106, 1111111111111111111, 9223372036854775121, '新建', NULL, 2, NULL, NULL, NULL, 'process:definition:create', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1633285580421271553, 1111111111111111111, 1580357263003439106, '流程实例', 'instance', 1, 'bcd12312', '/instance', '/process/instance/InstanceView', 'process:instance:list', 0, 0, 0, 5, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1633338860669214722, 1111111111111111111, 9223372036854775121, '流程挂起', NULL, 2, NULL, NULL, NULL, 'process:definition:suspended', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1633349367631314945, 1111111111111111111, 9223372036854775121, '发布', NULL, 2, NULL, NULL, NULL, 'process:instance:publish', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (9223372036854775119, 1111111111111111111, 1597480827938615297, '消息公告', 'msg', 1, 'el-icon-s-operation', '/msg', '/msg/msg/MsgView', 'sys:msg:list', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (9223372036854775120, 1111111111111111111, 1597480827938615297, '用户消息', 'userMsg', 1, 'el-icon-s-operation', '/userMsg', '/msg/userMsg/UserMsgView', 'sys:userMsg:list', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (9223372036854775121, 1111111111111111111, 1580357263003439106, '流程定义', 'definition', 1, 'el-icon-s-operation', '/definition', '/process/definition/DefinitionView', 'process:definition:list', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (9223372036854775807, 1111111111111111111, 1578702340666851329, '数据权限', 'dataPermission', 1, 'el-icon-s-operation', '/dataPermission', '/sys/dataPermission/DataPermissionView', 'sys:dataPermission:list', 0, 0, 0, 8, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_msg
-- ----------------------------
DROP TABLE IF EXISTS `sys_msg`;
CREATE TABLE `sys_msg`  (
  `id` bigint(22) NOT NULL,
  `msg_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息标题',
  `msg_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息编码',
  `msg_type` tinyint(1) NULL DEFAULT NULL COMMENT '消息类型 0 通知 1 公告',
  `msg_level` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息级别 error 紧急消息（多次提醒） info 一般消息 warning 警示消息 success 正常消息',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_msg
-- ----------------------------
INSERT INTO `sys_msg` VALUES (1594154596111454210, '你好世界', 'Halo3', 1, 'warning', '你好', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_msg` VALUES (1594154596111454211, '你好世界', 'Halo2', 1, 'warning', '你好', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_msg` VALUES (1594154596111454212, '你好世界', 'Halo1', 1, 'danger', '你好', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_msg` VALUES (1595966082236538882, '你好世界', 'Halo', 1, 'info', '你好', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_platform
-- ----------------------------
DROP TABLE IF EXISTS `sys_platform`;
CREATE TABLE `sys_platform`  (
  `id` bigint(22) NOT NULL,
  `platform_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台名称',
  `platform_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台编码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` int(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '平台' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_platform
-- ----------------------------
INSERT INTO `sys_platform` VALUES (1111111111111111111, '后台管理中心', 'managementCenter', '后台管理中心', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_platform` VALUES (1580099387022348289, '微信小程序', 'mini', '微信小程序', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `id` bigint(22) NOT NULL,
  `post_code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `post_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位编码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` int(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1591377257933819906, 'CEO', '首席执行官', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_post` VALUES (1630094545759137794, 'BZ', '搬砖', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(22) NOT NULL,
  `role_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `role_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1565322827518140417, 'ROLE_ADMIN', '超级管理员', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role` VALUES (1589074115103707138, 'ROLE_SIMPLE', '普通用户', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role` VALUES (1591282373843464193, 'ROLE_MINI', '小程序游客登录用户', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role` VALUES (1630099759669080066, 'ROLE_123123123', '123123', NULL, NULL, NULL, 'admin', 'admin', '2023-03-01 12:23:34', 1, NULL, 1);
INSERT INTO `sys_role` VALUES (1630785777820987393, 'ROLE_12312312312', '123123123', 'admin', 'admin', '2023-03-01 12:23:23', 'admin', 'admin', '2023-03-01 12:23:36', 1, NULL, 1);

-- ----------------------------
-- Table structure for sys_role_data_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_data_permission`;
CREATE TABLE `sys_role_data_permission`  (
  `id` bigint(22) NOT NULL,
  `data_permission_id` bigint(22) NULL DEFAULT NULL COMMENT '规则权限ID',
  `role_id` bigint(22) NULL DEFAULT NULL COMMENT '角色ID',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_data_permission
-- ----------------------------
INSERT INTO `sys_role_data_permission` VALUES (1630037573185720322, 1, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_data_permission` VALUES (1630038128528400385, 4, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_data_permission` VALUES (1630038150712074241, 4, 1591282373843464193, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(22) NOT NULL,
  `menu_id` bigint(22) NULL DEFAULT NULL COMMENT '菜单ID',
  `role_id` bigint(22) NULL DEFAULT NULL COMMENT '角色ID',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1630014473949921282, 1578702340654268411, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473949921283, 1578702340654268417, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473958309889, 9223372036854775807, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473958309890, 1582554585967800321, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473966698498, 1582555155344568321, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473975087106, 1582558188828790785, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473979281409, 1580357263003439106, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473979281410, 1578702340620713986, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473987670018, 1581966349440581634, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473996058625, 1582607135668621314, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473996058626, 1581965904601088001, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014474004447234, 1581965904601088002, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014474004447235, 1582688861908611074, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405283581954, 1578702340666851329, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405300359169, 1578702340671045634, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405317136385, 1578702340671045635, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405325524993, 1578702340679434241, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405333913601, 1578702340679434243, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405342302210, 1578702340683628545, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405350690817, 1586717542633123841, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405350690818, 1599935876379897858, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405363273730, 1599936405688479746, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405363273731, 1578702340624908290, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405371662337, 1578702340620713988, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405375856641, 1587692336744742913, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405384245250, 1578702340620713987, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405384245251, 1601081152259891202, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405392633857, 1578702340683628546, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405401022465, 1578702340624908293, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405409411074, 1578702340624908291, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405409411075, 1578702340624908292, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405417799681, 1578702340654268416, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405426188290, 1589181822230048770, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405426188291, 1589181822230048771, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405434576898, 1589181822230048772, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405442965506, 1578702340654268418, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405442965507, 1578702340650074114, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405451354113, 1578702340641685505, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405459742721, 1578702340641685506, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405468131329, 1601858203636092929, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405468131330, 1578702340662657027, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405476519938, 1578702340650074115, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405476519939, 1578702340650074116, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405484908546, 1578702340650074117, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405493297154, 1586717542633123843, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405493297155, 1578702340612325378, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405501685762, 1578702340654268411, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405501685763, 1578702340654268417, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405510074370, 9223372036854775807, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405514268674, 1589789746153263106, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405522657282, 1598222575933485057, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405526851585, 1598222373868695553, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405526851586, 1578702340662657026, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405535240193, 1581843318345035778, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405543628801, 1578702340633296899, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405543628802, 1578702340633296898, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405552017410, 1578702340654268412, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405556211714, 1589181822230048781, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405564600321, 1589181822230048782, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405564600322, 1589181822230048783, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405572988929, 1594135789623984129, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405577183233, 1594532038764326666, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405577183234, 1594532038764326913, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405577183235, 1594531945449451666, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405585571842, 1589181822230048778, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405593960450, 1597480827938615297, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405593960451, 9223372036854775120, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405602349058, 1589181822230018782, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405602349059, 1589181822230048178, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405606543362, 9223372036854775119, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405606543363, 1589181822230049781, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405614931969, 1589181822230018781, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405623320577, 1589181822230048172, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405631709185, 1582554585967800321, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405631709186, 1582555155344568321, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405640097793, 1582558188828790785, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405640097794, 1580357263003439106, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405640097795, 1580357773622202370, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405648486401, 1632642093459935234, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405648486402, 1632642093459935235, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405656875010, 1632642093459935256, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405656875011, 9223372036854775121, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405665263618, 1632950317358747649, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405665263619, 1633338860669214722, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405673652226, 1632950163226464257, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405673652227, 1632979037821743106, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405682040833, 1632925687029903361, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405682040834, 1633349367631314945, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405686235138, 1632925792583757826, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405694623745, 1578702340620713986, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405694623746, 1632642093459935233, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405703012354, 1633285580421271553, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405703012355, 1581966349440581634, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405711400961, 1582607135668621314, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405711400962, 1581965904601088001, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405719789570, 1581965904601088002, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1633349405719789571, 1582688861908611074, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
  `id` bigint(22) NOT NULL,
  `tenant_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户编码',
  `tenant_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户名称',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES (1, 'T_GS', '公司', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(22) NOT NULL DEFAULT 0,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户姓名',
  `user_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户编码',
  `amount_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录账户名称',
  `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `avatar_file_id` bigint(22) NULL DEFAULT NULL COMMENT '头像文件ID',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
  `post_id` bigint(22) NULL DEFAULT NULL COMMENT '岗位ID',
  `dept_id` bigint(22) NULL DEFAULT NULL COMMENT '部门ID',
  `sex` int(1) NULL DEFAULT NULL COMMENT '性别 0 女性 1 男性',
  `id_card` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `open_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信OpenID',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮件',
  `is_lock` int(1) NULL DEFAULT NULL COMMENT '锁定',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_user_name`(`username`, `tenant_id`) USING BTREE COMMENT '同一个租户下，用户名唯一索引',
  UNIQUE INDEX `uni_open_id`(`open_id`, `tenant_id`) USING BTREE COMMENT '同一个租户下，openId唯一索引',
  UNIQUE INDEX `uni_phone`(`phone`, `tenant_id`) USING BTREE COMMENT '同一个租户下，电话唯一索引',
  UNIQUE INDEX `uni_id_card`(`id_card`, `tenant_id`) USING BTREE COMMENT '同一个租户下，身份证唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (2, 'user', 'user', '普通用户', '1/a5b0b0cefb984240b9c8e295be5aa5a0_1200x0-3f3375c5b68d4dea930aac8eca673278.jpg', 1630785620291317761, '$2a$10$z3lNPvzOC.71ch8WgCPNVu9JOo3z.yoMI6i.GDIm7qvvQWK9EWJ5u', 1630094545759137794, 1601579918477983745, 1, '371521123456789', '17812345671', '1231231', 'breeze-cloud@foxmail.com', 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user` VALUES (1111111111111111111, 'admin', 'admin', '超级管理员', 'http://localhost:9000/img/27/6dbf70aba7ed4a05ada26e5cf8846f8c_1200x0-b673a3d36f2c4734ac45b98974268ed2.jpg', 1630087487802441729, '$2a$10$0oaHJPN7Pqq49dUqQgUVSug5l1ELUqjvuDZ5BIJwr2PJyGqzMjIca', 1591377257933819906, 1581851971500371970, 0, '371521123456781', '17812345678', '123123', 'breeze-cloud1@foxmail.com', 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_user_msg
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_msg`;
CREATE TABLE `sys_user_msg`  (
  `id` bigint(22) NOT NULL,
  `user_id` bigint(22) NULL DEFAULT NULL COMMENT '用户ID',
  `msg_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息编码',
  `msg_snapshot_id` bigint(22) NULL DEFAULT NULL COMMENT '消息快照ID',
  `mark_read` tinyint(1) NULL DEFAULT 0 COMMENT '标记已读 0 未读 1 已读',
  `mark_close` tinyint(1) NULL DEFAULT 0 COMMENT '标记关闭 0 未关闭 1 已关闭',
  `dept_id` bigint(22) NULL DEFAULT NULL,
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_msg
-- ----------------------------
INSERT INTO `sys_user_msg` VALUES (1607285460718465026, 1111111111111111111, 'Halo3', 1607285460563275777, 0, 1, NULL, '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg` VALUES (1607285460743630850, 2, 'Halo3', 1607285460563275777, 0, 0, NULL, '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg` VALUES (1607285700490047490, 1111111111111111111, 'Halo3', 1607285700448104450, 0, 1, NULL, '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg` VALUES (1607285700502630402, 2, 'Halo3', 1607285700448104450, 0, 0, NULL, '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg` VALUES (1607287807159013377, 1111111111111111111, 'Halo3', 1607287807054155778, 0, 1, NULL, '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg` VALUES (1607287807167401985, 2, 'Halo3', 1607287807054155778, 0, 0, NULL, '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg` VALUES (1622468865802260482, 1111111111111111111, 'Halo3', 1622468865697402882, 0, 1, NULL, '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg` VALUES (1622468865819037697, 2, 'Halo3', 1622468865697402882, 0, 0, NULL, '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg` VALUES (1622468888862543874, 1111111111111111111, 'Halo3', 1622468888824795138, 0, 1, NULL, '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg` VALUES (1622468888879321089, 2, 'Halo3', 1622468888824795138, 0, 0, NULL, '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg` VALUES (1629389395788791809, 2, 'Halo3', 1629389395667156994, 0, 0, NULL, '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg` VALUES (1629389395792986114, 1111111111111111111, 'Halo3', 1629389395667156994, 0, 1, NULL, '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg` VALUES (1629390477151666178, 2, 'Halo3', 1629390477101334530, 0, 0, NULL, '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg` VALUES (1629390477160054785, 1111111111111111111, 'Halo3', 1629390477101334530, 0, 1, NULL, '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_user_msg_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_msg_snapshot`;
CREATE TABLE `sys_user_msg_snapshot`  (
  `id` bigint(22) NOT NULL,
  `msg_id` bigint(22) NULL DEFAULT NULL COMMENT '消息ID',
  `msg_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息标题',
  `msg_type` tinyint(1) NULL DEFAULT NULL COMMENT '消息类型 0 通知 1 公告',
  `msg_level` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息级别 error 紧急消息（多次提醒） info 一般消息 warning 警示消消息 success 正常消息',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息快照' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_msg_snapshot
-- ----------------------------
INSERT INTO `sys_user_msg_snapshot` VALUES (1607285700448104450, 1594154596111454210, '你好世界', 1, 'warning', '你好', '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg_snapshot` VALUES (1607287807054155778, 1594154596111454210, '你好世界', 1, 'warning', '你好', '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg_snapshot` VALUES (1622468865697402882, 1594154596111454210, '你好世界', 1, 'warning', '你好', '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg_snapshot` VALUES (1622468888824795138, 1594154596111454210, '你好世界', 1, 'warning', '你好', '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg_snapshot` VALUES (1629389395667156994, 1594154596111454210, '你好世界', 1, 'warning', '你好', '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_msg_snapshot` VALUES (1629390477101334530, 1594154596111454210, '你好世界', 1, 'warning', '你好', '', '', NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(22) NOT NULL,
  `user_id` bigint(22) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(22) NULL DEFAULT NULL COMMENT '角色ID',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1630093904366170114, 1111111111111111111, 1565322827518140417, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user_role` VALUES (1630785628193386497, 2, 1589074115103707138, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
