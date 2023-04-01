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

 Date: 25/03/2023 23:35:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `INT_PROP_1` int(11) NULL DEFAULT NULL,
  `INT_PROP_2` int(11) NULL DEFAULT NULL,
  `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
  `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_task_history
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_task_history`;
CREATE TABLE `qrtz_task_history`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_ID` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `FIRE_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TASK_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `TASK_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `FIRED_TIME` bigint(13) NULL DEFAULT NULL,
  `FIRED_WAY` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `COMPLETE_TIME` bigint(13) NULL DEFAULT NULL,
  `EXPEND_TIME` bigint(13) NULL DEFAULT NULL,
  `REFIRED` int(11) NULL DEFAULT NULL,
  `EXEC_STATE` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `LOG` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`FIRE_ID`) USING BTREE,
  INDEX `IDX_QRTZ_TK_S`(`SCHED_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PRIORITY` int(11) NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `id` bigint(22) NOT NULL DEFAULT 1111111111111111111 COMMENT '主键ID',
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
  `id` bigint(22) NOT NULL COMMENT '主键ID',
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
INSERT INTO `sys_dict` VALUES (1637619982970351618, '任务策略', 'MISFIRE_POLICY', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1637621879726895105, '并发', 'CONCURRENT', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1637622109440536577, '任务状态', 'JOB_STATUS', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1639508202175832066, '任务组', 'JOB_GROUP', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` bigint(22) NOT NULL COMMENT '主键ID',
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
INSERT INTO `sys_dict_item` VALUES (1637620045218017282, 1637619982970351618, '1', ' 执行一次（默认）', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1637620093146329089, 1637619982970351618, '-1', '立刻执行', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1637620131188666370, 1637619982970351618, '2', '放弃执行', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1637621918473875458, 1637621879726895105, '1', '并发', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1637621961926864897, 1637621879726895105, '0', '串行', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1637622141858312194, 1637622109440536577, '1', '开启', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1637622196887580673, 1637622109440536577, '0', '暂停', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1639508998309257217, 1639508202175832066, 'DEFAULT', '默认', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_dict_item` VALUES (1639510731953512449, 1639508202175832066, 'SYSTEM', '系统', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` bigint(22) NOT NULL COMMENT '主键ID',
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
  `id` bigint(22) NOT NULL COMMENT '主键ID',
  `system_module` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统模块',
  `log_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志标题',
  `log_type` tinyint(1) NULL DEFAULT 0 COMMENT '日志类型 0 普通日志 1 登录日志',
  `request_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求类型  GET  POST  PUT DELETE ',
  `ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `do_type` tinyint(1) NULL DEFAULT 3 COMMENT '操作类型 0 添加 1 删除 2 修改 3 查询',
  `browser` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器名称',
  `system` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统类型',
  `param_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '入参',
  `result` tinyint(1) NULL DEFAULT 0 COMMENT '结果 0 失败 1 成功',
  `result_msg` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结果信息',
  `time` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用时',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(22) NOT NULL DEFAULT 1111111111111111111 COMMENT '主键ID',
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
INSERT INTO `sys_menu` VALUES (1578702340612321378, 1111111111111111111, 1594135789623184129, '日志管理', 'jobLog', 1, 'el-icon-s-comment', '/jobLog', '/sys/job/jobLog/JobLogView', 'sys:jobLog:list', 0, 0, 1, 7, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
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
INSERT INTO `sys_menu` VALUES (1578702340654268411, 1111111111111111111, 1578702340612325378, '清空表', NULL, 2, NULL, NULL, NULL, 'sys:log:truncate', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340654268412, 1111111111111111111, 1578702340666851329, '租户管理', 'tenant', 1, 'el-icon-user-solid', '/tenant', '/sys/tenant/TenantView', 'sys:tenant:list', 0, 0, 0, 10, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340654268416, 1111111111111111111, 1578702340666851329, '岗位管理', 'post', 1, 'el-icon-user-solid', '/post', '/sys/post/PostView', 'sys:post:list', 0, 0, 0, 4, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340654268417, 1111111111111111111, 1578702340612325378, '删除', NULL, 2, NULL, NULL, NULL, 'sys:log:delete', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340654268418, 1111111111111111111, 1578702340666851329, '角色管理', 'role', 1, 'el-icon-s-ticket', '/role', '/sys/role/RoleView', 'sys:role:list', 0, 0, 0, 5, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340662657026, 1111111111111111111, 1578702340666851329, '部门管理', 'dept', 1, 'el-icon-s-check', '/dept', '/sys/dept/DeptView', 'sys:dept:list', 0, 0, 0, 9, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340662657027, 1111111111111111111, 1578702340666851329, '字典管理', 'dict', 1, 'el-icon-user-solid', '/dict', '/sys/dict/DictView', 'sys:dict:list', 0, 0, 0, 6, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1578702340666851329, 1111111111111111111, 1111111111111111111, '权限管理', '', 0, 'el-icon-setting', '/upms', '', '', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
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
INSERT INTO `sys_menu` VALUES (1594135789623184129, 1111111111111111111, 1637297406628823041, '任务管理', 'job', 1, 'abc12312312', '/job', '/sys/job/JobView', 'sys:job:list', 0, 0, 0, 11, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1594135789623984129, 1111111111111111111, 1637297406628823041, '系统文件', 'file', 1, 'abc12312312', '/file', '/sys/file/FileView', 'sys:file:list', 0, 0, 0, 11, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1594531945449451666, 1111111111111111111, 1594135789623984129, '预览', NULL, 2, NULL, NULL, NULL, 'sys:file:preview', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1594532038764326666, 1111111111111111111, 1594135789623984129, '文件上传', NULL, 2, NULL, NULL, NULL, 'sys:file:upload', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1594532038764326913, 1111111111111111111, 1594135789623984129, '下载', NULL, 2, NULL, NULL, NULL, 'sys:file:download', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1598222373868695553, 1111111111111111111, 9223372036854775807, '删除', NULL, 2, NULL, 'sys:permission:delete', NULL, 'sys:dataPermission:delete', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1598222575933485057, 1111111111111111111, 9223372036854775807, '修改', NULL, 2, NULL, NULL, NULL, 'sys:dataPermission:modify', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1599935876379897858, 1111111111111111111, 1586717542633123841, '用户增加角色', NULL, 2, NULL, NULL, NULL, 'sys:user:userSetRole', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1599935889646481409, 1111111111111111111, 1, '用户增加角色', NULL, 2, NULL, NULL, NULL, 'sys:user:userSetRole', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1599935894306353153, 1111111111111111111, 1, '用户增加角色', NULL, 2, NULL, NULL, NULL, 'sys:user:userSetRole', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1599936079744921601, 1111111111111111111, 1, '用户增加角色', NULL, 2, NULL, NULL, NULL, 'sys:user:userSetRole', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1599936405688479746, 1111111111111111111, 1586717542633123841, '重置密码', NULL, 2, NULL, NULL, NULL, 'sys:user:resetPass', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1601081152259891202, 1111111111111111111, 1578702340683628545, '导出', NULL, 2, NULL, NULL, NULL, 'sys:user:export', 0, 0, 0, 6, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1601858203636092929, 1111111111111111111, 1578702340654268418, '数据权限', NULL, 2, NULL, NULL, NULL, 'sys:dataPermission:editRoleDataPermission', 0, 0, 0, 4, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632642093459915239, 1111111111111111111, 1578702340612321378, '删除', NULL, 2, NULL, NULL, NULL, 'sys:jobLog:delete', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935231, 1111111111111111111, 1594135789623184129, '添加', NULL, 2, NULL, NULL, NULL, 'sys:job:create', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935232, 1111111111111111111, 1594135789623184129, '删除', NULL, 2, NULL, NULL, NULL, 'sys:job:delete', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935233, 1111111111111111111, 1578702340620713986, '流程部署', NULL, 2, NULL, NULL, NULL, 'process:definition:deploy', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935234, 1111111111111111111, 1580357773622202370, '添加', NULL, 2, NULL, NULL, NULL, 'process:category:create', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935235, 1111111111111111111, 1580357773622202370, '删除', NULL, 2, NULL, NULL, NULL, 'process:category:delete', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935236, 1111111111111111111, 1578702340612321378, '清空', NULL, 2, NULL, NULL, NULL, 'sys:jobLog:truncate', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935239, 1111111111111111111, 1594135789623184129, '修改', NULL, 2, NULL, NULL, NULL, 'sys:job:modify', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935256, 1111111111111111111, 1580357773622202370, '修改', NULL, 2, NULL, NULL, NULL, 'process:category:modify', 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632925687029903361, 1111111111111111111, 9223372036854775121, '查看', NULL, 2, NULL, NULL, NULL, 'process:definition:info', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632925792583757826, 1111111111111111111, 9223372036854775121, '删除', NULL, 2, NULL, NULL, NULL, 'process:definition:delete', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632950163226464257, 1111111111111111111, 9223372036854775121, '启动', NULL, 2, NULL, NULL, NULL, 'process:instance:start', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632950317358747649, 1111111111111111111, 9223372036854775121, '设计', NULL, 2, NULL, NULL, NULL, 'process:designer', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1632979037821743106, 1111111111111111111, 9223372036854775121, '新建', NULL, 2, NULL, NULL, NULL, 'process:definition:create', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1633285580421271553, 1111111111111111111, 1580357263003439106, '流程实例', 'instance', 1, 'bcd12312', '/instance', '/process/instance/InstanceView', 'process:instance:list', 0, 0, 0, 5, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1633338860669214722, 1111111111111111111, 9223372036854775121, '流程挂起', NULL, 2, NULL, NULL, NULL, 'process:definition:suspended', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1633349367631314945, 1111111111111111111, 9223372036854775121, '发布', NULL, 2, NULL, NULL, NULL, 'process:instance:publish', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1635920329879056385, 1111111111111111111, 1580357263003439106, 'FlowableUI', NULL, 1, NULL, 'http://localhost:8989/flowable-ui/idm/#/user-mgmt', NULL, NULL, 1, 0, 0, 15, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1637297406628823041, 1111111111111111111, 1111111111111111111, '系统管理', NULL, 0, 'abc12312312', '/sys', NULL, NULL, 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (1637647486464438273, 1111111111111111111, 1594135789623184129, '运行一次', NULL, 2, NULL, NULL, NULL, 'sys:job:run', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (9223372036854775119, 1111111111111111111, 1637297406628823041, '消息公告', 'msg', 1, 'el-icon-s-operation', '/msg', '/sys/msg/MsgView', 'sys:msg:list', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (9223372036854775120, 1111111111111111111, 1637297406628823041, '用户消息', 'userMsg', 1, 'el-icon-s-operation', '/userMsg', '/sys/userMsg/UserMsgView', 'sys:userMsg:list', 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (9223372036854775121, 1111111111111111111, 1580357263003439106, '流程定义', 'definition', 1, 'el-icon-s-operation', '/definition', '/process/definition/DefinitionView', 'process:definition:list', 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (9223372036854775807, 1111111111111111111, 1578702340666851329, '数据权限', 'dataPermission', 1, 'el-icon-s-operation', '/dataPermission', '/sys/dataPermission/DataPermissionView', 'sys:dataPermission:list', 0, 0, 0, 8, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_msg
-- ----------------------------
DROP TABLE IF EXISTS `sys_msg`;
CREATE TABLE `sys_msg`  (
  `id` bigint(22) NOT NULL COMMENT '主键ID',
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
  `id` bigint(22) NOT NULL COMMENT '主键ID',
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
  `id` bigint(22) NOT NULL COMMENT '主键ID',
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
INSERT INTO `sys_post` VALUES (1637420262796746753, 'HR', '人力总监', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_quartz_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_job`;
CREATE TABLE `sys_quartz_job`  (
  `id` bigint(22) NOT NULL DEFAULT 2 COMMENT '主键ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '任务组名',
  `cron_expression` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
  `clazz_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '任务类名',
  `misfire_policy` tinyint(1) NULL DEFAULT 1 COMMENT 'misfire策略 1 执行一次（默认）-1 立刻执行 2 放弃执行',
  `concurrent` tinyint(1) NULL DEFAULT 0 COMMENT '是否并发 0 不并发 1 并发',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态 0 关闭 1 开启',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint(22) NOT NULL DEFAULT 1 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'quartz任务调度' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_quartz_job
-- ----------------------------
INSERT INTO `sys_quartz_job` VALUES (1, '样例-五种不同参数', '默认', '*/10 * * * * ?', 'breezeJobs.testM(\"testP\", 1, 3D, 4L, true, false)', 1, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_quartz_job` VALUES (2, '样例-单个参数', '默认', '*/20 * * * * ?', 'breezeJobs.testM(\"testP\")', 2, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_quartz_job` VALUES (3, '样例-全类名-五种不同参数', '默认', '*/15 * * * * ?', 'com.breeze.boot.quartz.job.BreezeJobs.testM(\"testP\", 1, 3D, 4L, true, false)', -1, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_quartz_job` VALUES (4, '样例-全类名-单个参数', '默认', '*/3 * * * * ?', 'com.breeze.boot.quartz.job.BreezeJobs.testM(\"testP\")', 1, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_quartz_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_job_log`;
CREATE TABLE `sys_quartz_job_log`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `cron_expression` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '当前的cron',
  `clazz_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标类名',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `job_message` varchar(510) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `job_status` tinyint(1) NULL DEFAULT 0 COMMENT '执行结果 0 失败 1 成功',
  `exception_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常信息',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1639507499378253826 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'quartz任务调度日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(22) NOT NULL COMMENT '主键ID',
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

-- ----------------------------
-- Table structure for sys_role_data_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_data_permission`;
CREATE TABLE `sys_role_data_permission`  (
  `id` bigint(22) NOT NULL COMMENT '主键ID',
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
  `id` bigint(22) NOT NULL COMMENT '主键ID',
  `menu_id` bigint(22) NULL DEFAULT NULL COMMENT '菜单ID',
  `role_id` bigint(22) NULL DEFAULT NULL COMMENT '角色ID',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1630014473949921282, 1578702340654268411, 1589074115103707138, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473949921283, 1578702340654268417, 1589074115103707138, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473958309889, 9223372036854775807, 1589074115103707138, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473958309890, 1582554585967800321, 1589074115103707138, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473966698498, 1582555155344568321, 1589074115103707138, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473975087106, 1582558188828790785, 1589074115103707138, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473979281409, 1580357263003439106, 1589074115103707138, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473979281410, 1578702340620713986, 1589074115103707138, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473987670018, 1581966349440581634, 1589074115103707138, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473996058625, 1582607135668621314, 1589074115103707138, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014473996058626, 1581965904601088001, 1589074115103707138, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014474004447234, 1581965904601088002, 1589074115103707138, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1630014474004447235, 1582688861908611074, 1589074115103707138, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953092661250, 1578702340666851329, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953109438466, 1578702340671045634, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953117827074, 1578702340671045635, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953126215682, 1578702340679434241, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953134604289, 1578702340679434243, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953142992897, 1578702340683628545, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953159770114, 1586717542633123841, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953172353025, 1599935876379897858, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953180741634, 1599936405688479746, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953184935937, 1578702340624908290, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953193324546, 1578702340620713988, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953197518849, 1587692336744742913, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953210101762, 1578702340620713987, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953226878977, 1601081152259891202, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953239461889, 1578702340683628546, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953247850497, 1578702340624908293, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953256239106, 1578702340624908291, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953273016322, 1578702340624908292, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953277210625, 1578702340654268416, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953289793538, 1589181822230048770, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953306570754, 1589181822230048771, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953323347969, 1589181822230048772, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953331736577, 1578702340654268418, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953335930881, 1578702340650074114, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953348513793, 1578702340641685505, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953365291010, 1578702340641685506, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953377873922, 1601858203636092929, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953390456834, 1578702340662657027, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953390456835, 1578702340650074115, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953398845441, 1578702340650074116, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953407234050, 1578702340650074117, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953407234051, 1586717542633123843, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953419816962, 1578702340612325378, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953428205569, 1578702340654268411, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953449177089, 1578702340654268417, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953457565698, 9223372036854775807, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953465954305, 1589789746153263106, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953474342914, 1598222575933485057, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953482731521, 1598222373868695553, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953491120130, 1578702340662657026, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953507897345, 1581843318345035778, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953516285954, 1578702340633296899, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953524674561, 1578702340633296898, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953533063169, 1578702340654268412, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953541451778, 1589181822230048781, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953549840385, 1589181822230048782, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953558228994, 1589181822230048783, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953575006209, 1637297406628823041, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953591783426, 9223372036854775120, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953595977729, 1589181822230018782, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953604366338, 1589181822230048178, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953612754946, 9223372036854775119, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953621143553, 1589181822230049781, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953625337858, 1589181822230018781, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953642115073, 1589181822230048172, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953650503681, 1594135789623184129, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953667280897, 1632642093459935231, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953684058114, 1637647486464438273, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953692446722, 1632642093459935232, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953709223938, 1632642093459935239, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953717612545, 1578702340612321378, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953726001154, 1632642093459915239, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953734389761, 1632642093459935236, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953742778370, 1594135789623984129, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953751166978, 1594532038764326666, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953759555586, 1594532038764326913, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953767944194, 1594531945449451666, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953784721409, 1589181822230048778, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953793110018, 1582554585967800321, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953797304321, 1582555155344568321, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953805692929, 1582558188828790785, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953814081538, 1580357263003439106, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953822470145, 1580357773622202370, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953830858753, 1632642093459935234, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953847635969, 1632642093459935235, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953864413185, 1632642093459935256, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953872801793, 9223372036854775121, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953881190402, 1632950317358747649, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953881190403, 1632950163226464257, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953889579010, 1632979037821743106, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953889579011, 1632925792583757826, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953897967617, 1633338860669214722, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953902161922, 1632925687029903361, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953914744833, 1633349367631314945, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953923133442, 1578702340620713986, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953931522050, 1632642093459935233, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953939910657, 1633285580421271553, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953939910658, 1635920329879056385, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953948299265, 1581966349440581634, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953952493569, 1582607135668621314, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953960882178, 1581965904601088001, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953969270785, 1581965904601088002, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_role_menu` VALUES (1639514953969270786, 1582688861908611074, 1565322827518140417, NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
  `id` bigint(22) NOT NULL COMMENT '主键ID',
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
INSERT INTO `sys_tenant` VALUES (1, 'GS', '公司', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(22) NOT NULL DEFAULT 0 COMMENT '主键ID',
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
INSERT INTO `sys_user` VALUES (2, 'user', 'user', '普通用户', NULL, NULL, '$2a$10$BpfYqjzOQimudlsLJoBopez5w/rvR5IouTNneOci6Rtgjdnd2dWe2', 1630094545759137794, 1601579918477983745, 1, '371521123456789', '17812345671', '1231231', 'breeze-cloud@foxmail.com', 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_user` VALUES (1111111111111111111, 'admin', 'admin', '超级管理员', NULL, NULL, '$2a$10$BpfYqjzOQimudlsLJoBopez5w/rvR5IouTNneOci6Rtgjdnd2dWe2', 1591377257933819906, 1581851971500371970, 0, '371521123456781', '17812345678', '123123', 'breeze-cloud1@foxmail.com', 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_user_msg
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_msg`;
CREATE TABLE `sys_user_msg`  (
  `id` bigint(22) NOT NULL COMMENT '主键ID',
  `user_id` bigint(22) NULL DEFAULT NULL COMMENT '用户ID',
  `msg_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息编码',
  `msg_snapshot_id` bigint(22) NULL DEFAULT NULL COMMENT '消息快照ID',
  `mark_read` tinyint(1) NULL DEFAULT 0 COMMENT '标记已读 0 未读 1 已读',
  `mark_close` tinyint(1) NULL DEFAULT 0 COMMENT '标记关闭 0 未关闭 1 已关闭',
  `dept_id` bigint(22) NULL DEFAULT NULL COMMENT '部门ID',
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
  `id` bigint(22) NOT NULL COMMENT '主键ID',
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
  `id` bigint(22) NOT NULL COMMENT '主键ID',
  `user_id` bigint(22) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(22) NULL DEFAULT NULL COMMENT '角色ID',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tenant_id` bigint(22) NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1636245019017228289, 2, 1589074115103707138, NULL, NULL, NULL, 1);
INSERT INTO `sys_user_role` VALUES (1636245019034005506, 2, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_user_role` VALUES (1636245019042394114, 2, 1591282373843464193, NULL, NULL, NULL, 1);
INSERT INTO `sys_user_role` VALUES (1637419916733112321, 1111111111111111111, 1565322827518140417, NULL, NULL, NULL, 1);
INSERT INTO `sys_user_role` VALUES (1637419916745695234, 1111111111111111111, 1589074115103707138, NULL, NULL, NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
