/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80402 (8.4.2)
 Source Host           : localhost:3306
 Source Schema         : serve

 Target Server Type    : MySQL
 Target Server Version : 80402 (8.4.2)
 File Encoding         : 65001

 Date: 20/11/2024 23:01:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Records of log_login
-- ----------------------------
INSERT INTO `log_login` (`id`, `user_id`, `user_name`, `ip`, `ip_real`, `login_time`, `address`, `system`, `status`) VALUES (1, '234', '324', '234', '234', '2023-10-08 15:19:03', '234', '234', '1');

-- ----------------------------
-- Records of log_operation
-- ----------------------------

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` (`id`, `parent_id`, `ancestors`, `name`, `sort`, `leader`, `phone`, `email`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, 0, '[\"1\"]', 'CC总部', 1, 'admin', '13520121955', 'admin@mail.com', '1', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------

-- ----------------------------
-- Records of sys_menu
-- ----------------------------

-- ----------------------------
-- Records of sys_post
-- ----------------------------

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` (`id`, `user_name`, `password`, `nick_name`, `real_name`, `avatar`, `email`, `phone`, `sex`, `age`, `address`, `status`, `login_ip`, `login_address`, `login_info`, `login_time`, `pwd_update_time`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, 'admin', '123456', '超管', NULL, NULL, 'admin@mail.com', '19920008007', '1', NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Records of sys_user_department
-- ----------------------------

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
