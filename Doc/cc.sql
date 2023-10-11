/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80100 (8.1.0)
 Source Host           : localhost:3306
 Source Schema         : cc

 Target Server Type    : MySQL
 Target Server Version : 80100 (8.1.0)
 File Encoding         : 65001

 Date: 11/10/2023 14:42:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for log_login
-- ----------------------------
DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户 id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
  `ip` varchar(255) DEFAULT NULL COMMENT '登录 IP',
  `ip_real` varchar(255) DEFAULT NULL COMMENT '真实IP',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `system` varchar(255) DEFAULT NULL COMMENT '设备信息',
  `status` char(1) DEFAULT '1' COMMENT '登录状态（0 失败，1 成功）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of log_login
-- ----------------------------
BEGIN;
INSERT INTO `log_login` (`id`, `user_id`, `user_name`, `ip`, `ip_real`, `login_time`, `address`, `system`, `status`) VALUES (1, '234', '324', '234', '234', '2023-10-08 15:19:03', '234', '234', '1');
COMMIT;

-- ----------------------------
-- Table structure for log_operation
-- ----------------------------
DROP TABLE IF EXISTS `log_operation`;
CREATE TABLE `log_operation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户 id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
  `ip` varchar(255) DEFAULT NULL COMMENT '登录 IP',
  `ip_real` varchar(255) DEFAULT NULL COMMENT '真实IP',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `system` varchar(255) DEFAULT NULL COMMENT '设备信息',
  `status` char(1) DEFAULT '1' COMMENT '登录状态（0 失败，1 成功）',
  `type` char(1) DEFAULT NULL COMMENT '操作（0 查询，1 新增，2 修改，3 删除）',
  `source` char(1) DEFAULT NULL COMMENT '来源（0 其他，1 PC，2 手机）',
  `url` varchar(100) DEFAULT NULL COMMENT '请求 URL',
  `method` varchar(100) DEFAULT NULL COMMENT '请求方法',
  `method_type` varchar(5) DEFAULT NULL COMMENT '请求方式：post，get......',
  `message` varchar(255) DEFAULT NULL COMMENT '错误消息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of log_operation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint DEFAULT NULL COMMENT '父级id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '祖级，树结构',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `sort` int DEFAULT NULL COMMENT '排序',
  `leader` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT '删除标识（0删除 1未删除）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门表';

-- ----------------------------
-- Records of sys_department
-- ----------------------------
BEGIN;
INSERT INTO `sys_department` (`id`, `parent_id`, `ancestors`, `name`, `sort`, `leader`, `phone`, `email`, `del_flag`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, 0, '[\"1\"]', 'CC总部', 1, 'admin', '13520121955', 'admin@mail.com', '1', '1', NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL COMMENT '父级id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称（国际化直接自动转化为对应的key）',
  `sort` int DEFAULT NULL COMMENT '排序',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求路径',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组件地址，默认在views/目录下，但不用填写views/',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '菜单类型（0 菜单 1 目录）',
  `auth` char(1) DEFAULT '1' COMMENT '是否登录访问（默认此项不填 0否 1是）',
  `isLink` char(1) DEFAULT '0' COMMENT '是否是连接（0否 1是）与iframe互斥',
  `isIframe` char(1) DEFAULT '0' COMMENT '是否是内嵌（0否 1是）与link互斥',
  `address` varchar(255) DEFAULT NULL COMMENT 'link或者iframe时，访问地址',
  `isHide` char(1) DEFAULT '0' COMMENT '是否隐藏路由（0否 1是）',
  `isHideSubMenu` char(1) DEFAULT NULL COMMENT '是否隐藏子路由（0否 1是）',
  `isMobile` char(1) DEFAULT '0' COMMENT '是否为手机端（0否 1是）',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT 'key',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `sort` int DEFAULT NULL COMMENT '排序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='岗位表';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色key',
  `sort` int DEFAULT NULL COMMENT '排序',
  `permissions` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '权限标识[C,R,U,D]',
  `data_scope` char(1) DEFAULT NULL COMMENT '数据权限（1全部 2当前及以下 3当前）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT '删除标识（0删除 1未删除）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(128) NOT NULL,
  `dept_id` bigint NOT NULL COMMENT '部门id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户登陆名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登陆密码',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '性别（0女 1男 2未知）',
  `avatar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '头像',
  `salt` varchar(20) DEFAULT NULL COMMENT '加密',
  `login_ip` varchar(128) DEFAULT NULL COMMENT '最后登陆ip',
  `login_address` varchar(255) DEFAULT NULL COMMENT '最后登陆地址',
  `login_info` varchar(255) DEFAULT NULL COMMENT '最后登陆设备',
  `login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `pwd_update_time` datetime DEFAULT NULL COMMENT '密码最后修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT '删除标识（0删除 1未删除）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_dept_id` (`dept_id`),
  CONSTRAINT `user_dept_id` FOREIGN KEY (`dept_id`) REFERENCES `sys_department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `dept_id`, `user_name`, `password`, `nick_name`, `email`, `phone`, `sex`, `avatar`, `salt`, `login_ip`, `login_address`, `login_info`, `login_time`, `pwd_update_time`, `del_flag`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('1', 1, 'admin', '123456', '超管', 'admin@mail.com', '19920008007', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '1', NULL, NULL, NULL, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
