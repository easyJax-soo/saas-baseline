/*
 Navicat Premium Data Transfer

 Source Server         : docker
 Source Server Type    : MySQL
 Source Server Version : 50736 (5.7.36)
 Source Host           : localhost:3306
 Source Schema         : hc_competence_v3

 Target Server Type    : MySQL
 Target Server Version : 50736 (5.7.36)
 File Encoding         : 65001

 Date: 07/10/2025 19:15:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID | [dto,vo]',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '应用名称 | [dto,vo]',
  `client_id` varchar(100) DEFAULT NULL COMMENT '客户端ID | [dto,vo]',
  `client_secret` varchar(255) DEFAULT '' COMMENT '客户端密钥 | [dto,vo]',
  `resource_ids` varchar(255) DEFAULT '' COMMENT '资源id集合，多个资源用英文逗号隔开',
  `scope` varchar(255) DEFAULT 'all' COMMENT '作用域 | [dto,vo]',
  `authorized_grant_types` varchar(255) DEFAULT '' COMMENT '授权类型 | [dto,vo]',
  `web_server_redirect_uri` varchar(255) DEFAULT '' COMMENT '重定向URI | [dto,vo]',
  `authorities` varchar(255) DEFAULT '' COMMENT '权限列表 | [dto,vo]',
  `access_token_validity` int(11) DEFAULT '86400' COMMENT '访问令牌有效期 | [dto,vo]',
  `refresh_token_validity` int(11) DEFAULT '172800' COMMENT '刷新令牌有效期 | [dto,vo]',
  `additional_information` varchar(255) DEFAULT NULL COMMENT '预留字段，格式必须是json',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `autoapprove` varchar(255) DEFAULT 'true' COMMENT '自动批准 | [vo]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client_details` (`id`, `tenant_id`, `name`, `client_id`, `client_secret`, `resource_ids`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `create_time`, `update_time`, `autoapprove`) VALUES (1, 0, '', 'super', 'a9b1463d8ea65f0620e26e60b4ad6c9a', NULL, 'all', 'authorization_code,password,client_credentials,implicit,refresh_token,wechat,admin_password,phone', 'http://www.baidu.com', '', 86400, 172800, NULL, NULL, NULL, 'true');
INSERT INTO `oauth_client_details` (`id`, `tenant_id`, `name`, `client_id`, `client_secret`, `resource_ids`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `create_time`, `update_time`, `autoapprove`) VALUES (2, 0, '微信小程序登录', 'wechat_mini', '14253f4fa3297140dfa241f5159066de', '', 'all', 'wechat', '', '', 86400, 172800, NULL, NULL, NULL, 'true');
INSERT INTO `oauth_client_details` (`id`, `tenant_id`, `name`, `client_id`, `client_secret`, `resource_ids`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `create_time`, `update_time`, `autoapprove`) VALUES (3, 0, 'dolphinscheduler', 'dolphinscheduler', 'dolphinscheduler', '', 'all', 'authorization_code', 'http://192.168.2.42:12345/dolphinscheduler/redirect/login/oauth2?provider=hc', '', 86400, 172800, NULL, NULL, NULL, 'true');
COMMIT;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID | [dto,vo]',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '配置名称 | [dto,vo, filter]',
  `group_code` varchar(255) NOT NULL DEFAULT '' COMMENT '分组编码',
  `config_key` varchar(100) NOT NULL DEFAULT '' COMMENT '配置键名 | [dto,vo,filter]',
  `config_value` varchar(500) NOT NULL DEFAULT '' COMMENT '配置键值 | [dto,vo]',
  `input_type` varchar(255) NOT NULL DEFAULT '' COMMENT '输入框类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统配置';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_config` (`id`, `name`, `group_code`, `config_key`, `config_value`, `input_type`, `create_time`, `update_time`, `remark`) VALUES (3, '系统 LOGO', 'sys', 'sysLogo', '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', 'image', '2024-10-14 11:31:42', '2025-09-20 19:33:39', '系统首页和菜单左上角的 LOGO 图片');
INSERT INTO `sys_config` (`id`, `name`, `group_code`, `config_key`, `config_value`, `input_type`, `create_time`, `update_time`, `remark`) VALUES (4, '系统版本', 'sys', 'sysVersion', 'Copyright © 2014-2024 v5.4.1', 'input', '2024-10-14 15:32:39', '2025-09-20 22:10:21', '系统版本');
INSERT INTO `sys_config` (`id`, `name`, `group_code`, `config_key`, `config_value`, `input_type`, `create_time`, `update_time`, `remark`) VALUES (5, '是否多模块', 'sys', 'sysMultiModule', 'true', 'input', '2024-10-14 15:32:39', '2025-10-07 16:49:20', '如果是true，登录后跳转到项目选择页面/projectSelection，且不允许访问其他页面地址；\n如果是false，登录后默认跳转会首页/home。');
INSERT INTO `sys_config` (`id`, `name`, `group_code`, `config_key`, `config_value`, `input_type`, `create_time`, `update_time`, `remark`) VALUES (47, '1111', '111', '111', '11', 'input', '2025-09-27 18:33:54', '2025-09-27 18:33:54', NULL);
INSERT INTO `sys_config` (`id`, `name`, `group_code`, `config_key`, `config_value`, `input_type`, `create_time`, `update_time`, `remark`) VALUES (48, '111', '111', '11', '11', 'input', '2025-09-27 18:34:02', '2025-09-27 18:34:02', NULL);
INSERT INTO `sys_config` (`id`, `name`, `group_code`, `config_key`, `config_value`, `input_type`, `create_time`, `update_time`, `remark`) VALUES (49, '111', '111', '1111', '11', 'input', '2025-09-27 18:34:19', '2025-09-27 18:34:19', '11');
INSERT INTO `sys_config` (`id`, `name`, `group_code`, `config_key`, `config_value`, `input_type`, `create_time`, `update_time`, `remark`) VALUES (50, '111', '111', '111111', '111', 'input', '2025-10-05 19:54:23', '2025-10-05 19:54:23', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_config_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_config_group`;
CREATE TABLE `sys_config_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID | [dto,vo]',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '分组名称',
  `group_code` varchar(100) NOT NULL DEFAULT '' COMMENT '配置分组编码',
  `sys_default` tinyint(4) NOT NULL DEFAULT '0' COMMENT '系统默认，不允许删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统配置';

-- ----------------------------
-- Records of sys_config_group
-- ----------------------------
BEGIN;
INSERT INTO `sys_config_group` (`id`, `name`, `group_code`, `sys_default`, `create_time`, `update_time`, `remark`) VALUES (1, '系统配置', 'sys', 1, '2025-09-20 19:53:36', '2025-09-20 21:54:33', NULL);
INSERT INTO `sys_config_group` (`id`, `name`, `group_code`, `sys_default`, `create_time`, `update_time`, `remark`) VALUES (15, 'tet1', '111', 0, '2025-09-27 18:31:21', '2025-09-27 18:31:21', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id | [dto,vo]',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父部门id | [dto,vo]',
  `parent_path` varchar(500) NOT NULL DEFAULT '' COMMENT '父路径',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '部门名称 | [dto,vo,filter]',
  `code` varchar(255) NOT NULL DEFAULT '' COMMENT '部门编码',
  `sort_no` int(11) NOT NULL DEFAULT '0' COMMENT '显示顺序 | [dto,vo]',
  `leader_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '负责人用户 ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 | [dto,vo,filter] | enum{ENABLE(1,"启用"),DISABLE(0,"禁用")}',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `level` int(11) NOT NULL DEFAULT '0' COMMENT '树层级',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1975152931050598402 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='部门表[tree]';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` (`id`, `tenant_id`, `parent_id`, `parent_path`, `name`, `code`, `sort_no`, `leader_user_id`, `status`, `deleted`, `level`, `create_time`, `update_time`, `remark`) VALUES (1973622676695433217, 0, 0, '0', 'one', 'one', 1, 1, 1, 0, 1, '2025-10-02 13:34:49', '2025-10-04 16:08:04', '');
INSERT INTO `sys_dept` (`id`, `tenant_id`, `parent_id`, `parent_path`, `name`, `code`, `sort_no`, `leader_user_id`, `status`, `deleted`, `level`, `create_time`, `update_time`, `remark`) VALUES (1974386947301781505, 0, 1973622676695433217, '0,1973622676695433217', '11', '11', 1, 0, 1, 0, 2, '2025-10-04 16:11:45', '2025-10-04 16:11:45', '');
INSERT INTO `sys_dept` (`id`, `tenant_id`, `parent_id`, `parent_path`, `name`, `code`, `sort_no`, `leader_user_id`, `status`, `deleted`, `level`, `create_time`, `update_time`, `remark`) VALUES (1975152931050598401, 1974442253536960514, 0, '0', '租户部门', '01111', 1, 1, 1, 0, 1, '2025-10-06 18:55:30', '2025-10-06 18:55:30', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `sort_no` int(4) NOT NULL DEFAULT '0' COMMENT '字典排序',
  `label` varchar(100) NOT NULL DEFAULT '' COMMENT '字典标签',
  `value` varchar(100) NOT NULL DEFAULT '' COMMENT '字典键值',
  `code` varchar(100) NOT NULL DEFAULT '' COMMENT '字典类型编码',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认（Y是 N否）',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态enum{ENABLE(1,"正常"),DISABLE(0,"禁用")}',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1975140154361745410 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典数据表';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1, 0, '禁用1', '0', 'sysStatus', 0, 1, '2025-09-13 17:57:10', '2025-10-04 15:28:31', '禁用', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (2, 0, '启用', '1', 'sysStatus', 0, 1, '2025-09-13 17:57:10', '2025-09-13 18:23:17', '启用', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1619596681978855426, 0, '男', '0', 'sysSex', 0, 1, '2023-01-29 15:21:54', '2023-01-29 15:23:32', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1619596888103731201, 0, '女', '1', 'sysSex', 0, 1, '2023-01-29 15:22:43', '2023-01-29 15:23:15', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1619597233836015618, 0, '保密', '2', 'sysSex', 0, 1, '2023-01-29 15:24:06', '2023-01-29 15:24:12', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1967852212727554050, 0, '文本', 'input', 'sysConfigInput', 0, 1, '2025-09-13 17:57:10', '2025-09-13 18:23:17', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1967852212727554051, 0, '开关', 'switch', 'sysConfigInput', 0, 1, '2025-09-13 17:57:10', '2025-09-13 18:23:17', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1967852212727554052, 0, '图片', 'image', 'sysConfigInput', 0, 1, '2025-09-13 17:57:10', '2025-09-13 18:23:17', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1970776952704937986, 0, '系统基座1', 'sysBash', 'sysProjectType', 0, 1, '2025-09-24 17:06:55', '2025-09-24 21:34:44', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1970781975287508993, 0, '否1', '0', 'sysYesNo', 0, 1, '2025-09-24 17:26:53', '2025-09-24 20:36:14', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1970781997760589826, 0, '是1', '1', 'sysYesNo', 0, 1, '2025-09-24 17:26:58', '2025-09-24 20:44:59', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1970783223243943937, 0, '当前窗口', '_self', 'sysWindowOpen', 0, 1, '2025-09-24 17:31:50', '2025-09-24 17:31:50', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1970783265438642178, 0, '新窗口', '_blank', 'sysWindowOpen', 0, 1, '2025-09-24 17:32:01', '2025-09-24 17:32:01', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1970787429633179650, 0, ' 隐藏1', '0', 'sysShowStatus', 0, 1, '2025-09-24 17:48:33', '2025-09-24 20:44:32', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1970787452504719362, 0, '显示', '1', 'sysShowStatus', 0, 1, '2025-09-24 17:48:39', '2025-09-24 20:45:14', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1970837621124268033, 0, '基座', 'bash', 'sysProjectType', 0, 1, '2025-09-24 21:08:00', '2025-09-24 21:08:00', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974068845618421761, 0, '身份证', '1', 'sysAuthType', 0, 1, '2025-10-03 19:07:44', '2025-10-03 19:07:44', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974068885598527490, 0, '护照', '2', 'sysAuthType', 0, 1, '2025-10-03 19:07:53', '2025-10-03 19:07:53', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974069102326603778, 0, '待审核', '0', 'sysAuthStatus', 0, 1, '2025-10-03 19:08:45', '2025-10-03 19:08:45', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974069168684687362, 0, '审核通过', '1', 'sysAuthStatus', 0, 1, '2025-10-03 19:09:01', '2025-10-03 19:09:37', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974069234153578498, 0, '审核拒绝', '2', 'sysAuthStatus', 0, 1, '2025-10-03 19:09:17', '2025-10-03 19:09:17', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974069277996638209, 0, '已过期', '3', 'sysAuthStatus', 0, 1, '2025-10-03 19:09:27', '2025-10-03 19:09:30', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974369216351432705, 0, '目录111', 'M', 'sysMenuType', 0, 1, '2025-10-04 15:01:18', '2025-10-06 20:50:53', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974369251675860993, 0, '菜单', 'C', 'sysMenuType', 0, 1, '2025-10-04 15:01:26', '2025-10-04 15:01:26', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974369251675860995, 0, '按钮', 'F', 'sysMenuType', 0, 1, '2025-10-04 15:01:50', '2025-10-04 15:01:50', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974758009109700609, 0, '普通用户', '0', 'sysTenantUserType', 0, 1, '2025-10-05 16:46:13', '2025-10-05 16:46:13', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974758162050801666, 0, '租户管理员', '1', 'sysTenantUserType', 0, 1, '2025-10-05 16:46:50', '2025-10-05 16:46:50', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1975139204402208769, 0, 'GET请求', 'GET', 'sysHttpMethod', 0, 1, '2025-10-06 18:00:57', '2025-10-06 18:00:57', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1975139239047159810, 0, 'POST请求', 'POST', 'sysHttpMethod', 0, 1, '2025-10-06 18:01:06', '2025-10-06 18:01:06', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1975140126641590274, 0, '失败', '0', 'sysSuccessFail', 0, 1, '2025-10-06 18:04:37', '2025-10-06 18:04:37', '', 0);
INSERT INTO `sys_dict_data` (`id`, `sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1975140154361745409, 0, '成功11', '1', 'sysSuccessFail', 0, 1, '2025-10-06 18:04:44', '2025-10-06 18:08:26', '', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '字典名称',
  `code` varchar(100) NOT NULL DEFAULT '' COMMENT '字典代码',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态enum{ENABLE(1,"正常"),DISABLE(0,"禁用")}',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1975140072094666754 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典类型表';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1, '禁用状态', 'sysStatus', 1, '2025-09-13 10:56:41', '2025-09-13 10:56:55', '禁用状态', 0);
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1619596633572392961, '性别', 'sysSex', 1, '2023-01-29 15:21:43', '2023-01-29 15:21:43', '', 0);
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1872476669064470531, '输入框类型', 'sysConfigInput', 1, '2023-01-29 15:16:04', '2025-09-24 17:25:30', '123', 0);
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1970776832458436609, '系统模块类型', 'sysProjectType', 1, '2025-09-24 17:06:27', '2025-09-24 17:29:01', '', 0);
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1970781897877434370, '是否', 'sysYesNo', 1, '2025-09-24 17:26:34', '2025-09-24 17:29:08', '', 0);
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1970783086656434177, '窗口打开方式', 'sysWindowOpen', 1, '2025-09-24 17:31:18', '2025-09-24 17:31:18', '', 0);
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1970787274649452545, '显示状态', 'sysShowStatus', 1, '2025-09-24 17:47:56', '2025-09-24 17:47:56', '', 0);
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974068773941960705, '认证类型', 'sysAuthType', 1, '2025-10-03 19:07:27', '2025-10-03 19:07:27', '', 0);
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974069005303963650, '认证审核状态', 'sysAuthStatus', 1, '2025-10-03 19:08:22', '2025-10-03 19:08:22', '', 0);
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974369128606593025, '系统菜单类型', 'sysMenuType', 1, '2025-10-04 15:00:57', '2025-10-06 15:32:41', '', 0);
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1974757929799606273, '租户用户类型', 'sysTenantUserType', 1, '2025-10-05 16:45:54', '2025-10-05 16:45:54', '', 0);
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1975139036906872834, 'HTTP请求方法', 'sysHttpMethod', 1, '2025-10-06 18:00:17', '2025-10-06 18:00:17', '', 0);
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `status`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1975140072094666753, '系统成功失败状态', 'sysSuccessFail', 1, '2025-10-06 18:04:24', '2025-10-06 18:04:24', '', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（1成功 0失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示信息',
  `access_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE,
  KEY `idx_sys_logininfor_s` (`status`) USING BTREE,
  KEY `idx_sys_logininfor_lt` (`access_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统访问记录';

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_member
-- ----------------------------
DROP TABLE IF EXISTS `sys_member`;
CREATE TABLE `sys_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户 ID',
  `account` varchar(30) NOT NULL COMMENT '登录账号',
  `password` varchar(255) DEFAULT '' COMMENT '密码',
  `salt` varchar(20) DEFAULT '' COMMENT '盐加密',
  `name` varchar(30) DEFAULT '' COMMENT '用户昵称',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像路径',
  `status` tinyint(4) DEFAULT '1' COMMENT '帐号状态 enum{ENABLE(1,"正常"),DISABLE(0,"禁用")}',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_account` (`account`) USING BTREE COMMENT '账号唯一性约束',
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE,
  KEY `idx_phone` (`phone`) USING BTREE,
  KEY `idx_email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1975153078404886531 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='会员信息表';

-- ----------------------------
-- Records of sys_member
-- ----------------------------
BEGIN;
INSERT INTO `sys_member` (`id`, `tenant_id`, `account`, `password`, `salt`, `name`, `email`, `phone`, `sex`, `avatar`, `status`, `deleted`, `create_time`, `create_user_id`, `create_user`, `update_time`, `update_user_id`, `update_user`, `remark`) VALUES (1974402879558438913, 0, '13112173618', '11', 'mql7hp', '13112173618', 'just_wyx@126.com', '13112173618', '0', '', 1, 0, '2025-10-04 17:15:04', 1, 'admin', '2025-10-04 18:20:28', 1, 'admin', NULL);
INSERT INTO `sys_member` (`id`, `tenant_id`, `account`, `password`, `salt`, `name`, `email`, `phone`, `sex`, `avatar`, `status`, `deleted`, `create_time`, `create_user_id`, `create_user`, `update_time`, `update_user_id`, `update_user`, `remark`) VALUES (1975153078404886530, 1974442253536960514, 'zuhu01Test', '11', 'bmz6k0', '租户会员', '', '', '0', '', 1, 0, '2025-10-06 18:56:05', 1, 'admin', '2025-10-06 18:56:05', 1, 'admin', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_member_real_name_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_member_real_name_auth`;
CREATE TABLE `sys_member_real_name_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `member_id` bigint(20) NOT NULL COMMENT '会员ID',
  `auth_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '认证类型：1-身份证，2-护照，3-港澳通行证，4-台胞证',
  `real_name` varchar(50) NOT NULL COMMENT '真实姓名',
  `cert_no` varchar(100) NOT NULL COMMENT '证件号码（加密存储）',
  `cert_no_hash` varchar(64) NOT NULL COMMENT '证件号码哈希值（用于查重）',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别：0-女，1-男',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `front_image_url` varchar(500) DEFAULT NULL COMMENT '证件正面照片URL',
  `back_image_url` varchar(500) DEFAULT NULL COMMENT '证件反面照片URL',
  `face_image_url` varchar(500) DEFAULT NULL COMMENT '人脸照片URL',
  `auth_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '认证状态：0-待审核，1-审核通过，2-审核拒绝，3-已过期',
  `submit_time` datetime NOT NULL COMMENT '提交时间',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `auditor_id` bigint(20) DEFAULT NULL COMMENT '审核人ID',
  `auditor_name` varchar(50) DEFAULT NULL COMMENT '审核人姓名',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `expire_time` datetime DEFAULT NULL COMMENT '认证过期时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_member_id` (`member_id`) USING BTREE COMMENT '一个会员只能有一条有效认证记录',
  UNIQUE KEY `uk_cert_no_hash` (`cert_no_hash`) USING BTREE COMMENT '证件号码唯一性约束',
  KEY `idx_auth_status` (`auth_status`) USING BTREE,
  KEY `idx_submit_time` (`submit_time`) USING BTREE,
  KEY `idx_auditor_id` (`auditor_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='会员实名认证信息表';

-- ----------------------------
-- Records of sys_member_real_name_auth
-- ----------------------------
BEGIN;
INSERT INTO `sys_member_real_name_auth` (`id`, `member_id`, `auth_type`, `real_name`, `cert_no`, `cert_no_hash`, `phone`, `gender`, `birthday`, `address`, `front_image_url`, `back_image_url`, `face_image_url`, `auth_status`, `submit_time`, `audit_time`, `auditor_id`, `auditor_name`, `audit_remark`, `expire_time`, `create_time`, `update_time`, `create_by`, `update_by`, `deleted`) VALUES (1, 1974402879558438913, 1, 'asda', '2121', '11', NULL, NULL, NULL, NULL, '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', 2, '2025-10-04 17:37:54', '2025-10-04 17:43:48', NULL, NULL, '1231', '2028-10-04 17:42:54', '2025-10-04 17:37:56', '2025-10-04 17:53:19', NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_member_third_party_bind
-- ----------------------------
DROP TABLE IF EXISTS `sys_member_third_party_bind`;
CREATE TABLE `sys_member_third_party_bind` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `provider` varchar(64) NOT NULL COMMENT '第三方标识',
  `provider_name` varchar(64) NOT NULL COMMENT '第三方名称',
  `third_party_user_id` varchar(64) NOT NULL COMMENT '第三方用户ID',
  `member_id` bigint(20) NOT NULL COMMENT '会员ID',
  `bind_time` datetime DEFAULT NULL COMMENT '绑定时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_provider_third_party_user_id` (`provider`,`third_party_user_id`) USING BTREE COMMENT '第三方用户唯一性约束',
  KEY `idx_member_id` (`member_id`) USING BTREE,
  KEY `idx_provider` (`provider`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='会员第三方绑定信息表';

-- ----------------------------
-- Records of sys_member_third_party_bind
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `sort_no` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path_type` varchar(32) NOT NULL DEFAULT '' COMMENT '路由类型',
  `path` varchar(200) DEFAULT '#' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `parameter` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `target` varchar(20) DEFAULT '' COMMENT '打开方式（menuItem页签 menuBlank新窗口）',
  `type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` tinyint(1) DEFAULT '0' COMMENT '显示状态（0隐藏 1显示）',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '菜单状态（0停用 1启用）',
  `cache` tinyint(1) DEFAULT NULL COMMENT '是否缓存（0不缓存 1缓存）',
  `chain` tinyint(1) DEFAULT NULL COMMENT '是否外链（0否 1是）',
  `key` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `level` int(5) DEFAULT NULL COMMENT '树层级',
  `project_code` varchar(100) NOT NULL DEFAULT '' COMMENT '项目 编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标记',
  `parent_path` varchar(500) DEFAULT NULL COMMENT '父路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1975187364231163906 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (1, '首页', 0, 1, 'component', '/home', 'home/home', NULL, 'menuItem', 'C', 1, 1, 1, 0, 'system:home', 'HomeOutlined', 1, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '系统首页', 0, '/');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2, '系统管理', 0, 2, 'layout', '/setting', NULL, NULL, 'menuItem', 'M', 1, 1, 0, 0, 'system:setting', 'SettingOutlined', 1, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '系统管理模块', 0, '/');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (3, '用户中心', 0, 3, 'component', '/userCenter', 'userCenter/userCenter', NULL, 'menuItem', 'F', 1, 1, 1, 0, 'system:userCenter', 'UserOutlined', 1, 'system', '2025-10-06 16:33:00', '2025-10-06 17:49:47', '个人中心', 0, '0');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (5, '修改密码', 0, 4, 'component', '/changePassword', 'changePassword/changePassword', NULL, 'menuItem', 'F', 1, 1, 1, 0, 'system:changePassword', 'LockOutlined', 1, 'system', '2025-10-06 16:33:00', '2025-10-06 17:49:53', '修改登录密码', 0, '0');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (21, '用户管理', 2, 1, 'component', '/setting/user', 'setting/user/user', NULL, 'menuItem', 'C', 1, 1, 1, 0, 'system:user', 'UserOutlined', 2, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '系统用户管理', 0, '/setting');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (22, '会员管理', 2, 2, 'component', '/setting/member', 'setting/member/member', NULL, 'menuItem', 'C', 1, 1, 1, 0, 'system:member', 'TeamOutlined', 2, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '会员信息管理', 0, '/setting');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (23, '角色管理', 2, 3, 'component', '/role', 'setting/role/role', NULL, 'menuItem', 'C', 1, 1, 1, 0, 'system:role', 'SafetyOutlined', 2, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '角色权限管理', 0, '/setting');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (24, '菜单管理', 2, 4, 'component', '/menu', 'setting/menu/menu', NULL, 'menuItem', 'C', 1, 1, 1, 0, 'system:menu', 'MenuOutlined', 2, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '系统菜单管理', 0, '/setting');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (25, '权限管理', 2, 5, 'component', '/permission', 'setting/permission/permission', NULL, 'menuItem', 'C', 1, 1, 1, 0, 'system:permission', 'KeyOutlined', 2, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '权限配置管理', 0, '/setting');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (26, '部门管理', 2, 6, 'component', '/dept', 'setting/dept/dept', NULL, 'menuItem', 'C', 1, 1, 1, 0, 'system:dept', 'ApartmentOutlined', 2, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '组织部门管理', 0, '/setting');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (27, '岗位管理', 2, 7, 'component', '/Post', 'setting/post/post', NULL, 'menuItem', 'C', 1, 1, 1, 0, 'system:post', 'IdcardOutlined', 2, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '岗位信息管理', 0, '/setting');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (28, '字典管理', 2, 8, 'component', '/dic', 'setting/dic/dic', NULL, 'menuItem', 'C', 1, 1, 1, 0, 'system:dic', 'BookOutlined', 2, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '数据字典管理', 0, '/setting');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (29, '系统配置', 2, 9, 'component', '/setting/system', 'setting/system/system', NULL, 'menuItem', 'C', 1, 1, 1, 0, 'system:config', 'ToolOutlined', 2, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '系统参数配置', 0, '/setting');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (210, '日志管理', 2, 10, 'component', '/log', 'setting/log/log', NULL, 'menuItem', 'C', 1, 1, 1, 0, 'system:log', 'FileTextOutlined', 2, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '系统日志管理', 0, '/setting');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (211, '租户管理', 2, 11, 'component', '/tenant', 'setting/tenant/tenant', NULL, 'menuItem', 'C', 1, 1, 1, 0, 'system:tenant', 'BankOutlined', 2, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '多租户管理', 0, '/setting');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (212, '租户用户', 2, 12, 'component', '/tenantUser', 'setting/tenant/tenantUser', NULL, 'menuItem', 'C', 1, 1, 1, 0, 'system:tenantUser', 'UsergroupAddOutlined', 2, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '租户用户管理', 0, '/setting');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (214, '项目管理', 2, 14, 'component', '/setting/project', 'setting/project/project', NULL, 'menuItem', 'C', 1, 1, 1, 0, 'system:project', 'ProjectOutlined', 2, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '项目信息管理', 0, '/setting');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2101, '用户新增', 21, 1, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:user.add', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '新增用户', 0, '/setting/user');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2102, '用户编辑', 21, 2, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:user.edit', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '编辑用户', 0, '/setting/user');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2103, '用户删除', 21, 3, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:user.delete', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '删除用户', 0, '/setting/user');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2104, '用户详情', 21, 4, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:user.detail', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '查看用户详情', 0, '/setting/user');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2105, '修改密码', 21, 5, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:user.changePassword', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '修改用户密码', 0, '/setting/user');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2106, '第三方平台绑定管理', 21, 6, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:user.platformBind', '', 3, 'system', '2025-10-06 17:21:32', '2025-10-06 17:21:32', '管理用户第三方平台绑定', 0, '/setting/user');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2107, '实名认证管理', 21, 7, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:user.realNameAuth', '', 3, 'system', '2025-10-06 17:21:32', '2025-10-06 17:21:32', '管理用户实名认证', 0, '/setting/user');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2201, '会员新增', 22, 1, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:member.add', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '新增会员', 0, '/setting/member');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2202, '会员编辑', 22, 2, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:member.edit', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '编辑会员', 0, '/setting/member');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2203, '会员删除', 22, 3, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:member.delete', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '删除会员', 0, '/setting/member');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2204, '会员详情', 22, 4, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:member.detail', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '查看会员详情', 0, '/setting/member');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2205, '重置密码', 22, 5, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:member.resetPassword', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '重置会员密码', 0, '/setting/member');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2206, '第三方平台绑定管理', 22, 6, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:member.platformBind', '', 3, 'system', '2025-10-06 17:21:32', '2025-10-06 17:21:32', '管理会员第三方平台绑定', 0, '/setting/member');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2207, '实名认证管理', 22, 7, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:member.realNameAuth', '', 3, 'system', '2025-10-06 17:21:32', '2025-10-06 17:21:32', '管理会员实名认证', 0, '/setting/member');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2301, '角色新增', 23, 1, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:role.add', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '新增角色', 0, '/setting/role');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2302, '角色编辑', 23, 2, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:role.edit', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '编辑角色', 0, '/setting/role');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2303, '角色删除', 23, 3, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:role.delete', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '删除角色', 0, '/setting/role');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2304, '角色详情', 23, 4, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:role.detail', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '查看角色详情', 0, '/setting/role');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2401, '菜单新增', 24, 1, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:menu.add', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '新增菜单', 0, '/setting/menu');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2402, '菜单编辑', 24, 2, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:menu.edit', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '编辑菜单', 0, '/setting/menu');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2403, '菜单删除', 24, 3, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:menu.delete', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '删除菜单', 0, '/setting/menu');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2404, '菜单详情', 24, 4, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:menu.detail', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '查看菜单详情', 0, '/setting/menu');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2405, '按钮管理', 24, 5, '', '', NULL, NULL, '', 'F', 1, 0, 0, 0, 'system:menu.button', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '菜单按钮管理', 0, '/setting/menu');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2501, '权限新增', 25, 1, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:permission.add', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '新增权限', 0, '/setting/permission');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2502, '权限编辑', 25, 2, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:permission.edit', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '编辑权限', 0, '/setting/permission');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2503, '权限删除', 25, 3, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:permission.delete', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '删除权限', 0, '/setting/permission');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2504, '权限详情', 25, 4, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:permission.detail', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '查看权限详情', 0, '/setting/permission');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2601, '部门新增', 26, 1, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:dept.add', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '新增部门', 0, '/setting/dept');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2602, '部门编辑', 26, 2, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:dept.edit', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '编辑部门', 0, '/setting/dept');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2603, '部门删除', 26, 3, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:dept.delete', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '删除部门', 0, '/setting/dept');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2604, '部门详情', 26, 4, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:dept.detail', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '查看部门详情', 0, '/setting/dept');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2701, '岗位新增', 27, 1, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:post.add', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '新增岗位', 0, '/setting/post');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2702, '岗位编辑', 27, 2, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:post.edit', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '编辑岗位', 0, '/setting/post');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2703, '岗位删除', 27, 3, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:post.delete', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '删除岗位', 0, '/setting/post');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2704, '岗位详情', 27, 4, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:post.detail', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '查看岗位详情', 0, '/setting/post');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2801, '字典分类新增', 28, 1, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:dic.addType', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '新增字典分类', 0, '/setting/dic');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2802, '字典项新增', 28, 2, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:dic.add', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '新增字典项', 0, '/setting/dic');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2803, '字典编辑', 28, 3, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:dic.edit', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '编辑字典', 0, '/setting/dic');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2804, '字典删除', 28, 4, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:dic.delete', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '删除字典', 0, '/setting/dic');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2901, '新增配置', 29, 1, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:config.add', '', 3, 'system', '2025-10-06 17:54:05', '2025-10-06 17:54:05', '新增系统配置', 0, '/setting/system');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2902, '编辑配置', 29, 2, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:config.edit', '', 3, 'system', '2025-10-06 17:54:05', '2025-10-06 17:54:05', '编辑系统配置', 0, '/setting/system');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2903, '删除配置', 29, 3, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:config.delete', '', 3, 'system', '2025-10-06 17:54:05', '2025-10-06 17:54:05', '删除系统配置', 0, '/setting/system');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2904, '查看配置详情', 29, 4, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:config.detail', '', 3, 'system', '2025-10-06 17:54:05', '2025-10-06 17:54:05', '查看系统配置详情', 0, '/setting/system');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2905, '新增配置分组', 29, 5, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:config.addGroup', '', 3, 'system', '2025-10-06 17:54:05', '2025-10-06 17:54:05', '新增系统配置分组', 0, '/setting/system');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2906, '编辑配置分组', 29, 6, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:config.editGroup', '', 3, 'system', '2025-10-06 17:54:05', '2025-10-06 17:54:05', '编辑系统配置分组', 0, '/setting/system');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (2907, '删除配置分组', 29, 7, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:config.deleteGroup', '', 3, 'system', '2025-10-06 17:54:05', '2025-10-06 17:54:05', '删除系统配置分组', 0, '/setting/system');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (21001, '日志删除', 210, 1, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:log.delete', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '删除日志', 0, '/log');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (21002, '日志详情', 210, 2, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:log.detail', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '查看日志详情', 0, '/log');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (21101, '租户新增', 211, 1, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:tenant.add', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '新增租户', 0, '/tenant');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (21102, '租户编辑', 211, 2, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:tenant.edit', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '编辑租户', 0, '/tenant');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (21103, '租户删除', 211, 3, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:tenant.delete', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '删除租户', 0, '/tenant');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (21104, '租户详情', 211, 4, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:tenant.detail', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '查看租户详情', 0, '/tenant');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (21201, '租户用户绑定', 212, 1, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:tenantUser.bind', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '绑定租户用户', 0, '/tenantUser');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (21202, '租户用户解绑', 212, 2, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:tenantUser.unbind', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '解绑租户用户', 0, '/tenantUser');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (21203, '租户用户详情', 212, 3, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:tenantUser.detail', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '查看租户用户详情', 0, '/tenantUser');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (21401, '项目新增', 214, 1, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:project.add', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '新增项目', 0, '/setting/project');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (21402, '项目编辑', 214, 2, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:project.edit', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '编辑项目', 0, '/setting/project');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (21403, '项目删除', 214, 3, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:project.delete', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '删除项目', 0, '/setting/project');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (21404, '项目详情', 214, 4, '', '', NULL, NULL, '', 'F', 1, 1, 0, 0, 'system:project.detail', '', 3, 'system', '2025-10-06 16:33:00', '2025-10-06 16:33:00', '查看项目详情', 0, '/setting/project');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (1975172316163149825, '123', 0, 1, '', '123', NULL, NULL, '', 'C', 1, 1, NULL, NULL, '123', '1', 1, 'sdsad', '2025-10-06 20:12:32', '2025-10-06 21:11:33', '', 0, '0');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (1975187225789771778, '123', 0, 1, '', '#', NULL, NULL, '', 'M', 1, 1, NULL, NULL, NULL, 'UpCircleFilled', 1, 'sdsad', '2025-10-06 21:11:47', '2025-10-06 21:11:47', '', 0, '0');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (1975187299349475330, '12', 1975172316163149825, 123, '', '123', NULL, NULL, '', 'C', 1, 1, NULL, NULL, '123', '123', 2, 'sdsad', '2025-10-06 21:12:04', '2025-10-06 21:12:04', '', 0, '0,1975172316163149825');
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (1975187364231163905, '123', 1975187299349475330, 1, '', '#', NULL, NULL, '', 'F', 1, 1, NULL, NULL, '123', 'DownSquareFilled', 3, 'sdsad', '2025-10-06 21:12:20', '2025-10-06 21:12:20', '', 0, '0,1975172316163149825,1975187299349475330');
COMMIT;

-- ----------------------------
-- Table structure for sys_oplog
-- ----------------------------
DROP TABLE IF EXISTS `sys_oplog`;
CREATE TABLE `sys_oplog` (
  `id` bigint(20) NOT NULL COMMENT '日志主键',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `description` varchar(512) DEFAULT '' COMMENT '操作描述',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称class#method',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作人员ID',
  `operator` varchar(50) DEFAULT '' COMMENT '操作人姓名',
  `url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `ip` varchar(128) DEFAULT '' COMMENT '主机地址',
  `params` varchar(2000) DEFAULT NULL COMMENT '请求参数',
  `result` varchar(2000) DEFAULT NULL COMMENT '响应参数',
  `status` tinyint(4) DEFAULT '0' COMMENT '操作状态 enum{SUCCESS(1,"成功"),FAIL(0,"失败")}',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint(20) DEFAULT '0' COMMENT '消耗时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='操作日志记录';

-- ----------------------------
-- Records of sys_oplog
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID | [dto,vo]',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父ID',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '权限名称 | [dto,vo,filter]',
  `permission` varchar(100) NOT NULL DEFAULT '' COMMENT '权限标识 | [dto,vo,filter]',
  `sort_no` int(4) NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `project_code` varchar(100) NOT NULL DEFAULT '' COMMENT '项目编码',
  `level` int(5) DEFAULT '0' COMMENT '树层级',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1408 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1, 0, '字典管理', 'system:dict', 1, 'system', 1, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '字典类型和字典数据管理');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (2, 0, '系统配置', 'system:config', 2, 'system', 1, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统配置管理');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (3, 0, '用户管理', 'system:user', 3, 'system', 1, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统用户管理');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (4, 0, '角色管理', 'system:role', 4, 'system', 1, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统角色管理');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (5, 0, '权限管理', 'system:permission', 5, 'system', 1, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '权限管理');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (6, 0, '菜单管理', 'system:menu', 6, 'system', 1, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统菜单管理');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (7, 0, '部门管理', 'system:dept', 7, 'system', 1, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '部门信息管理');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (8, 0, '岗位管理', 'system:post', 8, 'system', 1, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '岗位管理');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (9, 0, '租户管理', 'system:tenant', 9, 'system', 1, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统租户管理');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (10, 0, '项目管理', 'system:project', 10, 'system', 1, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统项目管理');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (11, 0, '会员管理', 'system:member', 11, 'system', 1, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '会员管理');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (12, 0, '文件管理', 'system:file', 12, 'system', 1, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '文件上传管理');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (13, 0, '操作日志', 'system:oplog', 13, 'system', 1, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '操作日志管理');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (14, 0, '下拉选项', 'system:options', 14, 'system', 1, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '下拉选项管理');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (101, 1, '字典类型列表', 'system:dictType:list', 1, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '查看字典类型列表');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (102, 1, '保存字典类型', 'system:dictType:save', 2, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '新增编辑字典类型');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (103, 1, '删除字典类型', 'system:dictType:delete', 3, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '删除字典类型');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (104, 1, '保存字典数据', 'system:dict:save', 4, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '新增编辑字典数据');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (105, 1, '字典数据分页', 'system:dict:page', 5, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '字典数据分页查询');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (106, 1, '删除字典数据', 'system:dict:delete', 6, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '删除字典数据');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (201, 2, '配置列表', 'system:config:list', 1, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '获取配置项列表');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (202, 2, '保存配置', 'system:config:save', 2, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '保存系统配置');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (203, 2, '配置详情', 'system:config:detail', 3, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '获取配置详情');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (204, 2, '删除配置', 'system:config:delete', 4, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '删除系统配置');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (205, 2, '配置分组列表', 'system:config:group:list', 5, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '获取配置分组列表');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (206, 2, '保存配置分组', 'system:config:group:save', 6, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '保存配置分组');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (207, 2, '删除配置分组', 'system:config:group:delete', 7, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '删除配置分组');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (301, 3, '用户分页', 'system:user:page', 1, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统用户分页查询');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (302, 3, '保存用户', 'system:user:save', 2, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '新增编辑系统用户');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (303, 3, '用户详情', 'system:user:detail', 3, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统用户详情');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (304, 3, '删除用户', 'system:user:delete', 4, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '批量删除系统用户');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (305, 3, '修改密码', 'system:user:changePassword', 5, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '修改用户密码');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (306, 3, '重置密码', 'system:user:resetPassword', 6, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '重置用户密码');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (307, 3, '更新个人信息', 'system:user:setInfo', 7, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '更新个人信息');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (401, 4, '角色分页', 'system:role:page', 1, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统角色分页列表');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (402, 4, '角色列表', 'system:role:list', 2, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统角色列表');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (403, 4, '保存角色', 'system:role:save', 3, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '新增编辑系统角色');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (404, 4, '角色详情', 'system:role:detail', 4, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统角色详情');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (405, 4, '删除角色', 'system:role:delete', 5, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '批量删除系统角色');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (501, 5, '权限树', 'system:permission:tree', 1, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '权限树结构数据');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (502, 5, '保存权限', 'system:permission:save', 2, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '新增编辑权限');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (503, 5, '权限详情', 'system:permission:detail', 3, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '权限详情');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (504, 5, '删除权限', 'system:permission:delete', 4, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '删除权限');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (601, 6, '保存菜单', 'system:menu:save', 1, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '保存菜单');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (602, 6, '菜单详情', 'system:menu:detail', 2, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '菜单详情');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (603, 6, '删除菜单', 'system:menu:delete', 3, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '删除菜单');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (604, 6, '菜单树', 'system:menu:tree', 4, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '菜单树形列表');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (605, 6, '菜单按钮列表', 'system:menuBtn:list', 5, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '菜单按钮列表');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (701, 7, '保存部门', 'system:dept:save', 1, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '保存部门');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (702, 7, '部门详情', 'system:dept:detail', 2, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '部门详情');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (703, 7, '删除部门', 'system:dept:delete', 3, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '删除部门');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (704, 7, '部门树', 'system:dept:tree', 4, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '部门树形列表');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (801, 8, '岗位分页', 'system:post:page', 1, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '岗位分页');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (802, 8, '保存岗位', 'system:post:save', 2, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '新增编辑岗位');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (803, 8, '岗位详情', 'system:post:detail', 3, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '岗位详情');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (804, 8, '删除岗位', 'system:post:delete', 4, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '批量删除岗位');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (901, 9, '租户分页', 'system:tenant:page', 1, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统租户分页');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (902, 9, '保存租户', 'system:tenant:save', 2, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '新增编辑系统租户');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (903, 9, '租户详情', 'system:tenant:detail', 3, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统租户详情');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (904, 9, '删除租户', 'system:tenant:delete', 4, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '批量删除系统租户');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (905, 9, '租户资源详情', 'system:tenant:resource:detail', 5, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '获取租户资源详情');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (906, 9, '保存租户资源', 'system:tenant:resource:save', 6, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '保存租户资源');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (907, 9, '租户用户分页', 'system:tenantUser:page', 7, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统租户用户分页');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (908, 9, '租户用户详情', 'system:tenantUser:detail', 8, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '系统租户用户详情');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (909, 9, '绑定租户用户', 'system:tenantUser:bind', 9, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '绑定现有用户到租户');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (910, 9, '解绑租户用户', 'system:tenantUser:unbind', 10, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '解绑租户用户');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (911, 9, '未绑定用户列表', 'system:tenantUser:unboundUsers', 11, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '获取租户未绑定的用户列表');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1001, 10, '保存项目', 'system:project:save', 1, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '保存项目');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1002, 10, '项目详情', 'system:project:detail', 2, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '项目详情');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1003, 10, '删除项目', 'system:project:delete', 3, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '删除项目');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1004, 10, '项目分页', 'system:project:page', 4, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '项目分页列表');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1101, 11, '会员查询', 'system:member:query', 1, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '会员分页查询');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1102, 11, '新增会员', 'system:member:add', 2, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '新增或编辑会员');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1103, 11, '删除会员', 'system:member:remove', 3, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '删除会员');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1104, 11, '编辑会员', 'system:member:edit', 4, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '编辑会员信息');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1105, 11, '会员实名认证审核', 'system:memberRealNameAuth:audit', 5, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '审核会员实名认证');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1106, 11, '会员实名认证查询', 'system:memberRealNameAuth:query', 6, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '获取会员认证详情');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1107, 11, '会员第三方绑定分页', 'system:memberThirdPartyBind:page', 7, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '会员第三方绑定分页');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1108, 11, '解绑会员第三方', 'system:memberThirdPartyBind:unbind', 8, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '解绑会员第三方账号');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1109, 3, '用户实名认证详情', 'system:userRealNameAuth:detail', 9, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '获取用户实名认证详情');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1110, 3, '用户实名认证审核', 'system:userRealNameAuth:audit', 10, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '审核用户实名认证');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1111, 3, '用户第三方绑定分页', 'system:userThirdPartyBind:page', 11, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '用户第三方绑定分页');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1112, 3, '解绑用户第三方', 'system:userThirdPartyBind:unbind', 12, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '解绑用户第三方账号');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1201, 12, '文件上传', 'system:file:upload', 1, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '文件上传');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1202, 12, '分片上传', 'system:file:chunk', 2, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '分片上传');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1301, 13, '日志分页', 'system:oplog:page', 1, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '操作日志分页列表');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1302, 13, '日志详情', 'system:oplog:detail', 2, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '操作日志详情');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1303, 13, '删除日志', 'system:oplog:delete', 3, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '批量删除操作日志');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1401, 14, '用户选项', 'system:options:user', 1, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '获取用户选项列表');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1402, 14, '角色选项', 'system:options:role', 2, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '获取角色选项列表');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1403, 14, '部门选项', 'system:options:dept', 3, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '获取部门选项树');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1404, 14, '菜单选项', 'system:options:menu', 4, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '获取菜单选项树');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1405, 14, '权限选项', 'system:options:permission', 5, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '获取权限选项树');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1406, 14, '岗位选项', 'system:options:post', 6, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '获取岗位选项列表');
INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1407, 14, '项目选项', 'system:options:project', 7, 'system', 2, 0, '2025-10-06 15:39:49', '2025-10-06 15:39:49', '获取项目选项列表');
COMMIT;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID | [dto,vo]',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '岗位 ID',
  `code` varchar(64) NOT NULL DEFAULT '' COMMENT '岗位编码 | [dto,vo,filter]',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '岗位名称 | [dto,vo,filter]',
  `sort_no` int(4) NOT NULL DEFAULT '0' COMMENT '显示顺序 | [dto,vo]',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 | [dto,vo,filter] | enum{ENABLE(1,"启用"),DISABLE(0,"禁用")}',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `remark` varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1975152851002306562 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='岗位信息表';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
BEGIN;
INSERT INTO `sys_post` (`id`, `tenant_id`, `code`, `name`, `sort_no`, `status`, `create_time`, `update_time`, `deleted`, `remark`) VALUES (1969677835505250305, 0, 'ceo', 'CEO', 1, 1, '2025-09-21 16:19:26', '2025-09-21 19:40:02', 0, '');
INSERT INTO `sys_post` (`id`, `tenant_id`, `code`, `name`, `sort_no`, `status`, `create_time`, `update_time`, `deleted`, `remark`) VALUES (1969691767629836290, 0, 'csa', 'ce', 1, 0, '2025-09-21 17:14:47', '2025-09-21 19:37:30', 0, '');
INSERT INTO `sys_post` (`id`, `tenant_id`, `code`, `name`, `sort_no`, `status`, `create_time`, `update_time`, `deleted`, `remark`) VALUES (1975152851002306561, 1974442253536960514, 'zuhu', '租户岗位', 11, 1, '2025-10-06 18:55:11', '2025-10-06 19:47:50', 0, '');
COMMIT;

-- ----------------------------
-- Table structure for sys_project
-- ----------------------------
DROP TABLE IF EXISTS `sys_project`;
CREATE TABLE `sys_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '项目编码',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '项目名称',
  `description` varchar(500) DEFAULT '' COMMENT '项目描述',
  `project_type` varchar(50) NOT NULL DEFAULT '' COMMENT '项目类型',
  `url` varchar(500) DEFAULT '' COMMENT '项目地址',
  `logo` varchar(500) DEFAULT '' COMMENT '项目Logo',
  `icon` varchar(100) DEFAULT '' COMMENT '项目图标',
  `sort_no` int(4) DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '项目状态（0停用 1启用）',
  `is_default` tinyint(1) DEFAULT '0' COMMENT '是否默认项目（0否 1是）',
  `target` varchar(20) DEFAULT '_self' COMMENT '打开方式（_self当前窗口 _blank新窗口）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_code` (`code`) USING BTREE,
  KEY `idx_project_type` (`project_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1972109080194953221 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统项目表';

-- ----------------------------
-- Records of sys_project
-- ----------------------------
BEGIN;
INSERT INTO `sys_project` (`id`, `code`, `name`, `description`, `project_type`, `url`, `logo`, `icon`, `sort_no`, `status`, `is_default`, `target`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1970790026950217730, 'system', '用户中心', '1111', 'sysBash', 'http://localhost:8001', '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', NULL, 1, 1, 1, '_self', '2025-09-24 17:58:53', '2025-10-04 14:49:15', '11', 0);
INSERT INTO `sys_project` (`id`, `code`, `name`, `description`, `project_type`, `url`, `logo`, `icon`, `sort_no`, `status`, `is_default`, `target`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1970832392383266817, 'code', 'asa', '', 'sys_bash', '', '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', '', 1, 1, 0, '_blank', '2025-09-24 20:47:13', '2025-09-24 20:51:30', '', 1);
INSERT INTO `sys_project` (`id`, `code`, `name`, `description`, `project_type`, `url`, `logo`, `icon`, `sort_no`, `status`, `is_default`, `target`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1972109080194953217, 'code2', '项目 2', '', 'bash', '', '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', '', 2, 1, 0, '_blank', '2025-09-28 09:20:19', '2025-09-28 09:20:43', '', 0);
INSERT INTO `sys_project` (`id`, `code`, `name`, `description`, `project_type`, `url`, `logo`, `icon`, `sort_no`, `status`, `is_default`, `target`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1972109080194953218, 'ttt', '11', '', 'bash1', '', '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', '', 2, 1, 0, '_blank', '2025-10-03 15:23:57', NULL, '', 0);
INSERT INTO `sys_project` (`id`, `code`, `name`, `description`, `project_type`, `url`, `logo`, `icon`, `sort_no`, `status`, `is_default`, `target`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1972109080194953219, 'sdsad', 'asdasd', '', 'sysBash', '', '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', '', 1, 1, 0, '_blank', '2025-10-03 15:24:06', NULL, '', 0);
INSERT INTO `sys_project` (`id`, `code`, `name`, `description`, `project_type`, `url`, `logo`, `icon`, `sort_no`, `status`, `is_default`, `target`, `create_time`, `update_time`, `remark`, `deleted`) VALUES (1972109080194953220, 'aaa', 'aaa', '', 'bash', 'https://www.baidu.com/', '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', '', 1, 1, 0, '_blank', '2025-10-03 15:24:13', NULL, '', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL COMMENT '角色ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '角色名称',
  `key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `data_scope` tinyint(4) NOT NULL DEFAULT '0' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '角色状态 enum{ENABLE(1,"正常"),DISABLE(0,"禁用")}',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间 [filter,vo,dto]',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `tenant_id`, `name`, `key`, `data_scope`, `status`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1, 0, 'admin', 'admin', 2, 1, 0, '2025-07-31 21:27:51', '2025-07-31 21:27:51', NULL);
INSERT INTO `sys_role` (`id`, `tenant_id`, `name`, `key`, `data_scope`, `status`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1974376957262319618, 0, 'test', 'test', 2, 1, 0, '2025-10-04 15:32:03', '2025-10-04 15:32:03', NULL);
INSERT INTO `sys_role` (`id`, `tenant_id`, `name`, `key`, `data_scope`, `status`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1975160434278768641, 1974442253536960514, '租户角色', 'zuhurole', 2, 1, 0, '2025-10-06 19:25:19', '2025-10-06 19:25:19', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色和部门关联表';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID ',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1974039004068225026, 0, 1, 1970859363574153218, '2025-10-03 17:09:09');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1974039004110168065, 0, 1, 1615289089999876097, '2025-10-03 17:09:09');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1974039004160499713, 0, 1, 1615287751081570305, '2025-10-03 17:09:09');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1974039004307300354, 0, 1, 1974010812901122050, '2025-10-03 17:09:09');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077287866369, 0, 1974376957262319618, 1615289089999876097, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077359169537, 0, 1974376957262319618, 1615287751081570305, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077396918273, 0, 1974376957262319618, 1615539985480527873, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077438861314, 0, 1974376957262319618, 1732948557743718402, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077480804353, 0, 1974376957262319618, 1615293280910684161, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077552107522, 0, 1974376957262319618, 1731962820017717250, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077598244866, 0, 1974376957262319618, 1733020876147228673, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077648576514, 0, 1974376957262319618, 1948282621156347937, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077686325249, 0, 1974376957262319618, 1974011020020047874, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077736656897, 0, 1974376957262319618, 1615294535687057410, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077778599938, 0, 1974376957262319618, 1719274274148507650, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077816348674, 0, 1974376957262319618, 1732667161796071426, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077866680322, 0, 1974376957262319618, 1615295079587622913, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077900234754, 0, 1974376957262319618, 1719279342595829762, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138077946372097, 0, 1974376957262319618, 1615538703218880513, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078026063874, 0, 1974376957262319618, 1615537961389109250, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078101561346, 0, 1974376957262319618, 1732661389724930049, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078147698689, 0, 1974376957262319618, 1615287751081570206, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078202224641, 0, 1974376957262319618, 1615287751081570205, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078252556289, 0, 1974376957262319618, 1615536665256898561, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078290305025, 0, 1974376957262319618, 1615525605078577153, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078336442370, 0, 1974376957262319618, 1615526329959165953, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078386774017, 0, 1974376957262319618, 31, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078428717057, 0, 1974376957262319618, 32, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078466465793, 0, 1974376957262319618, 5, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078550351873, 0, 1974376957262319618, 2201, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078600683522, 0, 1974376957262319618, 2202, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078651015170, 0, 1974376957262319618, 2204, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078692958210, 0, 1974376957262319618, 2205, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078734901249, 0, 1974376957262319618, 2207, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078785232897, 0, 1974376957262319618, 2106, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078860730370, 0, 1974376957262319618, 29, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078902673410, 0, 1974376957262319618, 2101, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138078940422146, 0, 1974376957262319618, 2102, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079015919618, 0, 1974376957262319618, 2104, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079066251265, 0, 1974376957262319618, 2105, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079108194305, 0, 1974376957262319618, 2107, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079150137345, 0, 1974376957262319618, 2206, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079192080385, 0, 1974376957262319618, 2301, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079242412034, 0, 1974376957262319618, 2302, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079284355073, 0, 1974376957262319618, 2304, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079326298113, 0, 1974376957262319618, 2402, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079397601281, 0, 1974376957262319618, 2404, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079439544322, 0, 1974376957262319618, 2405, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079494070273, 0, 1974376957262319618, 2501, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079523430402, 0, 1974376957262319618, 2502, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079569567746, 0, 1974376957262319618, 2504, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079607316482, 0, 1974376957262319618, 2601, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079674425345, 0, 1974376957262319618, 2602, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079728951298, 0, 1974376957262319618, 2604, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079766700033, 0, 1974376957262319618, 2701, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079800254465, 0, 1974376957262319618, 2702, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079829614593, 0, 1974376957262319618, 2704, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079858974722, 0, 1974376957262319618, 2802, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079892529154, 0, 1974376957262319618, 2803, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079926083585, 0, 1974376957262319618, 21002, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079955443714, 0, 1974376957262319618, 21101, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138079988998146, 0, 1974376957262319618, 21102, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080018358273, 0, 1974376957262319618, 21104, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080114827266, 0, 1974376957262319618, 21202, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080148381698, 0, 1974376957262319618, 21203, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080173547521, 0, 1974376957262319618, 21401, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080194519041, 0, 1974376957262319618, 21402, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080215490562, 0, 1974376957262319618, 21404, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080324542465, 0, 1974376957262319618, 21, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080374874114, 0, 1974376957262319618, 22, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080416817153, 0, 1974376957262319618, 23, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080467148801, 0, 1974376957262319618, 24, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080504897537, 0, 1974376957262319618, 25, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080567812097, 0, 1974376957262319618, 26, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080609755137, 0, 1974376957262319618, 27, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080651698177, 0, 1974376957262319618, 28, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080681058305, 0, 1974376957262319618, 210, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080706224129, 0, 1974376957262319618, 211, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080731389953, 0, 1974376957262319618, 212, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080756555778, 0, 1974376957262319618, 214, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080777527297, 0, 1974376957262319618, 1, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080798498818, 0, 1974376957262319618, 2904, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080823664642, 0, 1974376957262319618, 2905, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080861413377, 0, 1974376957262319618, 2906, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080903356418, 0, 1974376957262319618, 2907, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975138080932716545, 0, 1974376957262319618, 2902, '2025-10-06 17:56:29');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975160434568175617, 1974442253536960514, 1975160434278768641, 1, '2025-10-06 19:25:19');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975160434656256002, 1974442253536960514, 1975160434278768641, 2, '2025-10-06 19:25:19');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975160434710781953, 1974442253536960514, 1975160434278768641, 21, '2025-10-06 19:25:19');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975160434769502209, 1974442253536960514, 1975160434278768641, 2101, '2025-10-06 19:25:19');
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) VALUES (1975160434819833857, 1974442253536960514, 1975160434278768641, 2106, '2025-10-06 19:25:19');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID ',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1974039004407963649, 1, 0, 1, '2025-10-03 17:09:09');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1974039004479266818, 1, 0, 7, '2025-10-03 17:09:09');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081347952642, 1974376957262319618, 0, 17, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081427644417, 1974376957262319618, 0, 24, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081465393154, 1974376957262319618, 0, 34, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081507336193, 1974376957262319618, 0, 38, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081532502018, 1974376957262319618, 0, 46, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081557667842, 1974376957262319618, 0, 50, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081582833666, 1974376957262319618, 0, 55, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081607999489, 1974376957262319618, 0, 61, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081633165314, 1974376957262319618, 0, 71, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081654136833, 1974376957262319618, 0, 77, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081679302658, 1974376957262319618, 0, 80, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081742217217, 1974376957262319618, 0, 83, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081763188738, 1974376957262319618, 0, 88, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081792548865, 1974376957262319618, 0, 15, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081817714690, 1974376957262319618, 0, 16, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081842880514, 1974376957262319618, 0, 18, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081872240642, 1974376957262319618, 0, 19, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081922572290, 1974376957262319618, 0, 20, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081947738114, 1974376957262319618, 0, 21, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081968709633, 1974376957262319618, 0, 22, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138081998069761, 1974376957262319618, 0, 23, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082027429889, 1974376957262319618, 0, 25, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082081955841, 1974376957262319618, 0, 26, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082123898881, 1974376957262319618, 0, 27, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082161647618, 1974376957262319618, 0, 28, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082199396354, 1974376957262319618, 0, 29, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082237145089, 1974376957262319618, 0, 30, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082270699521, 1974376957262319618, 0, 31, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082316836865, 1974376957262319618, 0, 32, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082350391298, 1974376957262319618, 0, 33, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082379751425, 1974376957262319618, 0, 35, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082404917249, 1974376957262319618, 0, 36, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082430083074, 1974376957262319618, 0, 37, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082480414721, 1974376957262319618, 0, 39, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082505580545, 1974376957262319618, 0, 40, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082526552066, 1974376957262319618, 0, 41, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082551717889, 1974376957262319618, 0, 42, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082585272322, 1974376957262319618, 0, 43, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082639798274, 1974376957262319618, 0, 44, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082664964098, 1974376957262319618, 0, 45, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082685935617, 1974376957262319618, 0, 47, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082711101442, 1974376957262319618, 0, 48, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082736267266, 1974376957262319618, 0, 49, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082799181826, 1974376957262319618, 0, 51, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082904039426, 1974376957262319618, 0, 52, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082933399554, 1974376957262319618, 0, 53, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138082996314113, 1974376957262319618, 0, 54, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083034062850, 1974376957262319618, 0, 56, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083071811585, 1974376957262319618, 0, 57, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083109560321, 1974376957262319618, 0, 58, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083151503361, 1974376957262319618, 0, 59, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083193446401, 1974376957262319618, 0, 60, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083252166657, 1974376957262319618, 0, 62, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083268943873, 1974376957262319618, 0, 63, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083294109698, 1974376957262319618, 0, 64, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083319275522, 1974376957262319618, 0, 65, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083340247041, 1974376957262319618, 0, 66, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083373801473, 1974376957262319618, 0, 67, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083398967298, 1974376957262319618, 0, 68, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083424133122, 1974376957262319618, 0, 69, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083445104642, 1974376957262319618, 0, 70, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083470270465, 1974376957262319618, 0, 72, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083499630594, 1974376957262319618, 0, 73, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083520602114, 1974376957262319618, 0, 74, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083554156545, 1974376957262319618, 0, 75, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083583516673, 1974376957262319618, 0, 76, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083604488194, 1974376957262319618, 0, 78, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083633848321, 1974376957262319618, 0, 79, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083654819842, 1974376957262319618, 0, 81, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083679985665, 1974376957262319618, 0, 82, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083705151489, 1974376957262319618, 0, 84, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083772260353, 1974376957262319618, 0, 85, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083843563522, 1974376957262319618, 0, 86, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083860340738, 1974376957262319618, 0, 87, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083881312258, 1974376957262319618, 0, 89, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083935838210, 1974376957262319618, 0, 90, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138083981975553, 1974376957262319618, 0, 91, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084019724289, 1974376957262319618, 0, 92, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084053278721, 1974376957262319618, 0, 93, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084099416065, 1974376957262319618, 0, 94, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084137164802, 1974376957262319618, 0, 95, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084158136322, 1974376957262319618, 0, 96, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084179107841, 1974376957262319618, 0, 97, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084246216705, 1974376957262319618, 0, 98, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084271382529, 1974376957262319618, 0, 99, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084292354050, 1974376957262319618, 0, 100, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084317519873, 1974376957262319618, 0, 1, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084342685697, 1974376957262319618, 0, 101, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084367851522, 1974376957262319618, 0, 102, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084413988866, 1974376957262319618, 0, 103, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084439154690, 1974376957262319618, 0, 104, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084464320514, 1974376957262319618, 0, 105, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084489486337, 1974376957262319618, 0, 106, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084510457857, 1974376957262319618, 0, 2, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084535623681, 1974376957262319618, 0, 201, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084602732545, 1974376957262319618, 0, 202, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084661452801, 1974376957262319618, 0, 203, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084715978754, 1974376957262319618, 0, 204, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084778893314, 1974376957262319618, 0, 205, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084812447746, 1974376957262319618, 0, 206, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084850196481, 1974376957262319618, 0, 207, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084883750913, 1974376957262319618, 0, 3, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084925693953, 1974376957262319618, 0, 301, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138084971831297, 1974376957262319618, 0, 302, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085001191425, 1974376957262319618, 0, 303, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085034745858, 1974376957262319618, 0, 304, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085085077506, 1974376957262319618, 0, 305, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085110243330, 1974376957262319618, 0, 306, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085143797761, 1974376957262319618, 0, 307, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085177352194, 1974376957262319618, 0, 1109, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085219295233, 1974376957262319618, 0, 1110, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085248655362, 1974376957262319618, 0, 1111, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085278015490, 1974376957262319618, 0, 1112, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085311569921, 1974376957262319618, 0, 4, '2025-10-06 17:56:30');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085340930050, 1974376957262319618, 0, 401, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085374484481, 1974376957262319618, 0, 402, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085420621825, 1974376957262319618, 0, 403, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085449981953, 1974376957262319618, 0, 404, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085475147778, 1974376957262319618, 0, 405, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085504507906, 1974376957262319618, 0, 5, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085533868033, 1974376957262319618, 0, 501, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085567422466, 1974376957262319618, 0, 502, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085605171202, 1974376957262319618, 0, 503, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085621948418, 1974376957262319618, 0, 504, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085642919937, 1974376957262319618, 0, 6, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085672280065, 1974376957262319618, 0, 601, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085705834498, 1974376957262319618, 0, 602, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085731000322, 1974376957262319618, 0, 603, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085760360450, 1974376957262319618, 0, 604, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085793914882, 1974376957262319618, 0, 605, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085840052226, 1974376957262319618, 0, 7, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085873606658, 1974376957262319618, 0, 701, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085898772481, 1974376957262319618, 0, 702, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085919744001, 1974376957262319618, 0, 703, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085944909826, 1974376957262319618, 0, 704, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138085974269954, 1974376957262319618, 0, 8, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086020407297, 1974376957262319618, 0, 801, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086041378818, 1974376957262319618, 0, 802, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086070738946, 1974376957262319618, 0, 803, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086095904769, 1974376957262319618, 0, 804, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086121070593, 1974376957262319618, 0, 9, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086146236418, 1974376957262319618, 0, 901, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086183985153, 1974376957262319618, 0, 902, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086209150978, 1974376957262319618, 0, 903, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086230122498, 1974376957262319618, 0, 904, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086255288322, 1974376957262319618, 0, 905, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086284648449, 1974376957262319618, 0, 906, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086305619969, 1974376957262319618, 0, 907, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086364340226, 1974376957262319618, 0, 908, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086393700354, 1974376957262319618, 0, 909, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086427254786, 1974376957262319618, 0, 910, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086465003522, 1974376957262319618, 0, 911, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086498557953, 1974376957262319618, 0, 10, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086532112386, 1974376957262319618, 0, 1001, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086565666817, 1974376957262319618, 0, 1002, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086595026945, 1974376957262319618, 0, 1003, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086632775681, 1974376957262319618, 0, 1004, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086662135809, 1974376957262319618, 0, 11, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086691495938, 1974376957262319618, 0, 1101, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086729244673, 1974376957262319618, 0, 1102, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086754410498, 1974376957262319618, 0, 1103, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086787964930, 1974376957262319618, 0, 1104, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086808936449, 1974376957262319618, 0, 1105, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086829907969, 1974376957262319618, 0, 1106, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086850879490, 1974376957262319618, 0, 1107, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086876045314, 1974376957262319618, 0, 1108, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086897016834, 1974376957262319618, 0, 12, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086922182658, 1974376957262319618, 0, 1201, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086955737089, 1974376957262319618, 0, 1202, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138086989291521, 1974376957262319618, 0, 13, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138087010263041, 1974376957262319618, 0, 1301, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138087035428865, 1974376957262319618, 0, 1302, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138087064788993, 1974376957262319618, 0, 1303, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138087094149122, 1974376957262319618, 0, 14, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138087123509249, 1974376957262319618, 0, 1401, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138087152869377, 1974376957262319618, 0, 1402, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138087182229506, 1974376957262319618, 0, 1403, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138087228366849, 1974376957262319618, 0, 1404, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138087266115586, 1974376957262319618, 0, 1405, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138087299670017, 1974376957262319618, 0, 1406, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975138087337418754, 1974376957262319618, 0, 1407, '2025-10-06 17:56:31');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975160435042131970, 1975160434278768641, 1974442253536960514, 3, '2025-10-06 19:25:19');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975160435117629442, 1975160434278768641, 1974442253536960514, 301, '2025-10-06 19:25:19');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975160435167961090, 1975160434278768641, 1974442253536960514, 302, '2025-10-06 19:25:19');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975160435214098434, 1975160434278768641, 1974442253536960514, 303, '2025-10-06 19:25:19');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975160435260235777, 1975160434278768641, 1974442253536960514, 1111, '2025-10-06 19:25:19');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `tenant_id`, `permission_id`, `create_time`) VALUES (1975160435306373122, 1975160434278768641, 1974442253536960514, 1112, '2025-10-06 19:25:19');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_project
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_project`;
CREATE TABLE `sys_role_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID ',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `project_code` varchar(255) NOT NULL COMMENT '项目编码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色和项目关联表';

-- ----------------------------
-- Records of sys_role_project
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_project` (`id`, `role_id`, `tenant_id`, `project_code`, `create_time`) VALUES (5, 1, 0, 'sdsad', '2025-10-03 17:09:09');
INSERT INTO `sys_role_project` (`id`, `role_id`, `tenant_id`, `project_code`, `create_time`) VALUES (6, 1, 0, 'userCenter', '2025-10-03 17:09:09');
INSERT INTO `sys_role_project` (`id`, `role_id`, `tenant_id`, `project_code`, `create_time`) VALUES (7, 1, 0, 'aaa', '2025-10-03 17:09:09');
INSERT INTO `sys_role_project` (`id`, `role_id`, `tenant_id`, `project_code`, `create_time`) VALUES (8, 1, 0, 'code2', '2025-10-03 17:09:09');
INSERT INTO `sys_role_project` (`id`, `role_id`, `tenant_id`, `project_code`, `create_time`) VALUES (25, 1974376957262319618, 0, 'system', '2025-10-06 17:56:31');
INSERT INTO `sys_role_project` (`id`, `role_id`, `tenant_id`, `project_code`, `create_time`) VALUES (26, 1975160434278768641, 1974442253536960514, 'system', '2025-10-06 19:25:19');
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '租户ID | [dto,vo]',
  `name` varchar(255) NOT NULL COMMENT '租户名称 | [dto,vo,filter]',
  `code` varchar(255) NOT NULL DEFAULT '' COMMENT '租户编码 | [dto,vo,filter]',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 | [dto,vo,filter] | enum{ENABLE(1,"启用"),DISABLE(0,"禁用")}',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1975515355480629250 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户表';

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
BEGIN;
INSERT INTO `sys_tenant` (`id`, `name`, `code`, `status`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1974059584496029697, '1111', '111', 1, 0, '2025-10-03 18:30:56', '2025-10-03 18:36:20', NULL);
INSERT INTO `sys_tenant` (`id`, `name`, `code`, `status`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1974442253536960514, '1221212', '111', 1, 0, '2025-10-04 19:51:31', '2025-10-04 19:51:31', NULL);
INSERT INTO `sys_tenant` (`id`, `name`, `code`, `status`, `deleted`, `create_time`, `update_time`, `remark`) VALUES (1975515355480629249, 'asaa', '1212', 1, 0, '2025-10-07 18:55:39', '2025-10-07 18:55:39', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_menu`;
CREATE TABLE `sys_tenant_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '菜单ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1975180611586277379 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户菜单表';

-- ----------------------------
-- Records of sys_tenant_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389545852929, 1, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389612961794, 21, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389688459266, 2101, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389726208002, 2102, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389780733953, 2103, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389822676994, 2104, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389860425730, 2105, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389910757377, 2106, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389969477634, 2107, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390057558018, 22, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390107889666, 2201, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390158221314, 2202, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390204358657, 2203, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390263078913, 2204, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390351159298, 2205, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390409879553, 2206, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390456016898, 2207, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390548291586, 23, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390594428930, 2301, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390661537794, 2302, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390703480833, 2303, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390745423874, 2304, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390787366913, 3, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390829309954, 5, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180609698840577, 2701, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180609753366530, 2101, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180609824669697, 2601, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180609908555777, 2301, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180609950498817, 1, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610025996289, 2201, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610097299458, 21, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610193768449, 21001, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610252488706, 21002, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610298626049, 2702, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610348957697, 2102, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610395095041, 2602, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610441232386, 2302, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610491564034, 2, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610541895682, 2202, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610600615938, 22, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610655141890, 2203, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610701279234, 23, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610747416578, 2703, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610793553921, 2603, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610831302657, 2303, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610869051394, 2204, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180610969714690, 2704, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180611011657729, 2104, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180611074572290, 2604, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180611124903937, 2304, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180611171041281, 2205, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180611217178626, 2106, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180611263315970, 26, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180611313647617, 2206, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180611359784961, 2207, 1974442253536960514, '2025-10-06 20:45:30', '2025-10-06 20:45:30');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180611435282433, 2107, 1974442253536960514, '2025-10-06 20:45:30', '2025-10-06 20:45:30');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180611481419777, 27, 1974442253536960514, '2025-10-06 20:45:30', '2025-10-06 20:45:30');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180611523362817, 210, 1974442253536960514, '2025-10-06 20:45:30', '2025-10-06 20:45:30');
INSERT INTO `sys_tenant_menu` (`id`, `menu_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180611586277378, 2103, 1974442253536960514, '2025-10-06 20:45:30', '2025-10-06 20:45:30');
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_permission`;
CREATE TABLE `sys_tenant_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `permission_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '权限ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1975180609505902595 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户权限表';

-- ----------------------------
-- Records of sys_tenant_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162387293511681, 3, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162387381592066, 301, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162387457089538, 302, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162387549364225, 303, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162387645833218, 304, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162387754885121, 305, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162387834576897, 306, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162387910074369, 307, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162387977183233, 1109, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388077846530, 1110, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388132372482, 1111, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388178509826, 1112, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388224647169, 4, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388266590209, 401, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388308533249, 402, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388354670594, 403, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388417585153, 404, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388476305409, 405, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388530831361, 11, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388606328834, 1101, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388648271874, 1102, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388690214913, 1103, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388727963649, 1104, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388778295298, 1105, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388832821250, 1106, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388870569986, 1107, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388925095938, 1108, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162388967038977, 14, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389008982018, 1401, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389046730754, 1402, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389092868098, 1403, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389130616833, 1404, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389168365569, 1405, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389206114305, 1406, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389243863041, 1407, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389310971906, 12, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389348720642, 1201, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162389382275073, 1202, 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606058184705, 301, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606146265089, 1101, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606196596737, 401, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606288871425, 801, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606339203073, 701, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606431477762, 501, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606486003714, 1401, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606586667010, 1301, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606641192962, 1201, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606737661953, 1202, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606796382209, 302, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606842519553, 1102, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606888656898, 802, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606934794242, 402, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180606989320194, 702, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607043846146, 502, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607115149314, 1402, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607169675266, 1302, 1974442253536960514, '2025-10-06 20:45:28', '2025-10-06 20:45:28');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607215812609, 303, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607278727169, 1103, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607324864514, 803, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607371001858, 403, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607412944898, 703, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607454887937, 503, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607496830977, 1403, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607538774017, 1303, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607584911362, 3, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607631048706, 4, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607677186049, 304, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607727517697, 1104, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607807209474, 804, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607886901249, 404, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607941427202, 704, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180607995953153, 504, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608067256321, 1404, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608117587969, 405, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608193085441, 1405, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608264388609, 5, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608352468994, 305, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608461520897, 1105, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608507658241, 1106, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608578961409, 1406, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608629293057, 306, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608679624706, 307, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608729956354, 7, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608797065218, 1107, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608843202562, 1407, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608893534209, 8, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180608943865858, 1108, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180609040334850, 1109, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180609120026626, 1110, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180609208107010, 11, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180609275215874, 1111, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180609346519041, 12, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180609401044993, 1112, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180609451376642, 13, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
INSERT INTO `sys_tenant_permission` (`id`, `permission_id`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180609505902594, 14, 1974442253536960514, '2025-10-06 20:45:29', '2025-10-06 20:45:29');
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant_project
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_project`;
CREATE TABLE `sys_tenant_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_code` varchar(255) NOT NULL DEFAULT '' COMMENT '权限ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1975515412355391491 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户项目表';

-- ----------------------------
-- Records of sys_tenant_project
-- ----------------------------
BEGIN;
INSERT INTO `sys_tenant_project` (`id`, `project_code`, `tenant_id`, `create_time`, `update_time`) VALUES (1975162390959333377, 'system', 1974059584496029697, '2025-10-06 19:33:05', '2025-10-06 19:33:05');
INSERT INTO `sys_tenant_project` (`id`, `project_code`, `tenant_id`, `create_time`, `update_time`) VALUES (1975180611913433089, 'system', 1974442253536960514, '2025-10-06 20:45:30', '2025-10-06 20:45:30');
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_user`;
CREATE TABLE `sys_tenant_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 | [dto,vo]',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID | [dto,vo,filter]',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID | [dto,vo,filter]',
  `is_tenant_admin` tinyint(1) DEFAULT '0' COMMENT '租户管理员| [dto,vo,filter] | enum{ADMIN(1,"租户管理员"),GENERAL(0,"普通用户")}',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态（0停用, 1正常）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1975515547609112578 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户用户表';

-- ----------------------------
-- Records of sys_tenant_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_tenant_user` (`id`, `tenant_id`, `user_id`, `is_tenant_admin`, `status`, `create_time`, `update_time`) VALUES (1, 1974059584496029697, 1, 1, 1, NULL, NULL);
INSERT INTO `sys_tenant_user` (`id`, `tenant_id`, `user_id`, `is_tenant_admin`, `status`, `create_time`, `update_time`) VALUES (1974765276336959489, 1974442253536960514, 1, 1, 1, '2025-10-05 17:15:06', '2025-10-05 17:15:06');
INSERT INTO `sys_tenant_user` (`id`, `tenant_id`, `user_id`, `is_tenant_admin`, `status`, `create_time`, `update_time`) VALUES (1975160644526645249, 1974442253536960514, 1974377423761199105, 0, 1, '2025-10-06 19:26:09', '2025-10-06 19:26:09');
INSERT INTO `sys_tenant_user` (`id`, `tenant_id`, `user_id`, `is_tenant_admin`, `status`, `create_time`, `update_time`) VALUES (1975515547609112577, 1975515355480629249, 1, 1, 1, '2025-10-07 18:56:25', '2025-10-07 18:56:25');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '部门 ID',
  `account` varchar(30) NOT NULL COMMENT '登录账号',
  `password` varchar(255) DEFAULT '' COMMENT '密码',
  `salt` varchar(20) DEFAULT '' COMMENT '盐加密',
  `name` varchar(30) DEFAULT '' COMMENT '用户昵称',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像路径',
  `status` tinyint(4) DEFAULT '1' COMMENT '帐号状态 enum{ENABLE(1,"正常"),DISABLE(0,"禁用")}',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `initialize_password_change` tinyint(4) NOT NULL DEFAULT '0' COMMENT '初始化密码是否修改',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1975171649725992963 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `dept_id`, `account`, `password`, `salt`, `name`, `email`, `phone`, `sex`, `avatar`, `status`, `deleted`, `create_time`, `create_user_id`, `create_user`, `update_time`, `update_user_id`, `update_user`, `remark`, `initialize_password_change`) VALUES (1, 1973622676695433217, 'admin', '$2a$10$LnnSCm7GJbkzd1MdcGPVtudVX7TS/2MnOhvL3g715wzJqcH4Oetji', 'x0jdch', '管理员', '123@qq.com', '13500000000', '0', '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', 1, 0, '2023-01-29 11:31:48', 0, '', '2025-10-05 20:18:15', 1, 'admin', '123', 1);
INSERT INTO `sys_user` (`id`, `dept_id`, `account`, `password`, `salt`, `name`, `email`, `phone`, `sex`, `avatar`, `status`, `deleted`, `create_time`, `create_user_id`, `create_user`, `update_time`, `update_user_id`, `update_user`, `remark`, `initialize_password_change`) VALUES (1974377423761199105, 1975152931050598401, 'test', '11', 'lycm1e', 'test', 'just_wyx@126.com', '13112173618', '0', '', 1, 0, '2025-10-04 15:33:55', 1, 'admin', '2025-10-06 19:26:34', 1, 'admin', NULL, 0);
INSERT INTO `sys_user` (`id`, `dept_id`, `account`, `password`, `salt`, `name`, `email`, `phone`, `sex`, `avatar`, `status`, `deleted`, `create_time`, `create_user_id`, `create_user`, `update_time`, `update_user_id`, `update_user`, `remark`, `initialize_password_change`) VALUES (1975171649725992962, 1975152931050598401, 'adsda', '11', '3kattz', '123', 'just_wyx@126.com', '13112173618', '0', '', 1, 0, '2025-10-06 20:09:53', 1, 'admin', '2025-10-06 20:09:53', 1, 'admin', NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1975171650103480323 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户与岗位关联表';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_post` (`id`, `tenant_id`, `user_id`, `post_id`, `create_time`) VALUES (1735949837460865025, 0, 1734161123736240129, 1734151254845112322, '2023-12-16 17:07:48');
INSERT INTO `sys_user_post` (`id`, `tenant_id`, `user_id`, `post_id`, `create_time`) VALUES (1838897206635732993, 0, 1734852449188364290, 1719274392302051329, '2024-09-25 19:03:54');
INSERT INTO `sys_user_post` (`id`, `tenant_id`, `user_id`, `post_id`, `create_time`) VALUES (1838897206635732994, 0, 1734852449188364290, 1734151254845112322, '2024-09-25 19:03:54');
INSERT INTO `sys_user_post` (`id`, `tenant_id`, `user_id`, `post_id`, `create_time`) VALUES (1869571140068724738, 0, 1869570228210257922, 1719274392302051329, '2024-12-19 10:31:09');
INSERT INTO `sys_user_post` (`id`, `tenant_id`, `user_id`, `post_id`, `create_time`) VALUES (1869571140072919042, 0, 1869570228210257922, 1734151254845112322, '2024-12-19 10:31:09');
INSERT INTO `sys_user_post` (`id`, `tenant_id`, `user_id`, `post_id`, `create_time`) VALUES (1872572287627780098, 0, 1872572284867928066, 1719274392302051329, '2024-12-27 17:16:39');
INSERT INTO `sys_user_post` (`id`, `tenant_id`, `user_id`, `post_id`, `create_time`) VALUES (1872572287631974402, 0, 1872572284867928066, 1839503967920455682, '2024-12-27 17:16:39');
INSERT INTO `sys_user_post` (`id`, `tenant_id`, `user_id`, `post_id`, `create_time`) VALUES (1872572287631974403, 0, 1872572284867928066, 1839505500951150593, '2024-12-27 17:16:39');
INSERT INTO `sys_user_post` (`id`, `tenant_id`, `user_id`, `post_id`, `create_time`) VALUES (1889134876350488577, 0, 1873614591394988034, 1874754557789179905, '2025-02-11 10:10:28');
INSERT INTO `sys_user_post` (`id`, `tenant_id`, `user_id`, `post_id`, `create_time`) VALUES (1896761982241501186, 0, 1734851623120171010, 1874754043613646850, '2025-03-04 11:17:51');
INSERT INTO `sys_user_post` (`id`, `tenant_id`, `user_id`, `post_id`, `create_time`) VALUES (1974074888563445761, 0, 1, 1969677835505250305, '2025-10-03 19:31:45');
INSERT INTO `sys_user_post` (`id`, `tenant_id`, `user_id`, `post_id`, `create_time`) VALUES (1975160748197257217, 1974442253536960514, 1974377423761199105, 1975152851002306561, '2025-10-06 19:26:34');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_real_name_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_real_name_auth`;
CREATE TABLE `sys_user_real_name_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `auth_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '认证类型：1-身份证，2-护照，3-港澳通行证，4-台胞证',
  `real_name` varchar(50) NOT NULL COMMENT '真实姓名',
  `cert_no` varchar(100) NOT NULL COMMENT '证件号码（加密存储）',
  `cert_no_hash` varchar(64) NOT NULL COMMENT '证件号码哈希值（用于查重）',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别：0-女，1-男',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `front_image_url` varchar(500) DEFAULT NULL COMMENT '证件正面照片URL',
  `back_image_url` varchar(500) DEFAULT NULL COMMENT '证件反面照片URL',
  `face_image_url` varchar(500) DEFAULT NULL COMMENT '人脸照片URL',
  `auth_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '认证状态：0-待审核，1-审核通过，2-审核拒绝，3-已过期',
  `submit_time` datetime NOT NULL COMMENT '提交时间',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `auditor_id` bigint(20) DEFAULT NULL COMMENT '审核人ID',
  `auditor_name` varchar(50) DEFAULT NULL COMMENT '审核人姓名',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `expire_time` datetime DEFAULT NULL COMMENT '认证过期时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_id` (`user_id`) USING BTREE COMMENT '一个用户只能有一条有效认证记录',
  UNIQUE KEY `uk_cert_no_hash` (`cert_no_hash`) USING BTREE COMMENT '证件号码唯一性约束',
  KEY `idx_auth_status` (`auth_status`) USING BTREE,
  KEY `idx_submit_time` (`submit_time`) USING BTREE,
  KEY `idx_auditor_id` (`auditor_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='实名认证信息表';

-- ----------------------------
-- Records of sys_user_real_name_auth
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_real_name_auth` (`id`, `user_id`, `auth_type`, `real_name`, `cert_no`, `cert_no_hash`, `phone`, `gender`, `birthday`, `address`, `front_image_url`, `back_image_url`, `face_image_url`, `auth_status`, `submit_time`, `audit_time`, `auditor_id`, `auditor_name`, `audit_remark`, `expire_time`, `create_time`, `update_time`, `create_by`, `update_by`, `deleted`) VALUES (1, 1, 1, '吴宇旭', '111', '11', '11', NULL, NULL, NULL, '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', 0, '2025-10-02 17:16:45', '2025-10-02 17:28:06', 3, 'admin', '111', '2028-10-02 17:27:20', '2025-10-02 17:16:47', '2025-10-02 18:23:13', NULL, 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1975171649918930947 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`id`, `tenant_id`, `user_id`, `role_id`, `create_time`) VALUES (1974074888416645121, 0, 1, 1, '2025-10-03 19:31:45');
INSERT INTO `sys_user_role` (`id`, `tenant_id`, `user_id`, `role_id`, `create_time`) VALUES (1974377424012857346, 0, 1974377423761199105, 1974376957262319618, '2025-10-04 15:33:55');
INSERT INTO `sys_user_role` (`id`, `tenant_id`, `user_id`, `role_id`, `create_time`) VALUES (1975160748029485058, 1974442253536960514, 1974377423761199105, 1975160434278768641, '2025-10-06 19:26:34');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_third_party_bind
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_third_party_bind`;
CREATE TABLE `sys_user_third_party_bind` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `provider` varchar(64) NOT NULL COMMENT '第三方标识',
  `provider_name` varchar(64) NOT NULL COMMENT '第三方名称',
  `third_party_user_id` varchar(64) NOT NULL COMMENT '第三方用户ID',
  `user_id` bigint(20) NOT NULL COMMENT '平台用户ID',
  `bind_time` datetime DEFAULT NULL COMMENT '绑定时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='第三方用户绑定信息表';

-- ----------------------------
-- Records of sys_user_third_party_bind
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- 添加索引
-- ----------------------------

-- oauth_client_details表索引
ALTER TABLE `oauth_client_details` ADD INDEX `idx_client_id` (`client_id`) USING BTREE;
ALTER TABLE `oauth_client_details` ADD INDEX `idx_tenant_id` (`tenant_id`) USING BTREE;
ALTER TABLE `oauth_client_details` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_config表索引
ALTER TABLE `sys_config` ADD INDEX `idx_config_key` (`config_key`) USING BTREE;
ALTER TABLE `sys_config` ADD INDEX `idx_group_code` (`group_code`) USING BTREE;
ALTER TABLE `sys_config` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_config_group表索引
ALTER TABLE `sys_config_group` ADD INDEX `idx_group_code` (`group_code`) USING BTREE;
ALTER TABLE `sys_config_group` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_dept表索引
ALTER TABLE `sys_dept` ADD INDEX `idx_parent_id` (`parent_id`) USING BTREE;
ALTER TABLE `sys_dept` ADD INDEX `idx_tenant_id` (`tenant_id`) USING BTREE;
ALTER TABLE `sys_dept` ADD INDEX `idx_status` (`status`) USING BTREE;
ALTER TABLE `sys_dept` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_dict_data表索引
ALTER TABLE `sys_dict_data` ADD INDEX `idx_code` (`code`) USING BTREE;
ALTER TABLE `sys_dict_data` ADD INDEX `idx_value` (`value`) USING BTREE;
ALTER TABLE `sys_dict_data` ADD INDEX `idx_status` (`status`) USING BTREE;
ALTER TABLE `sys_dict_data` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_dict_type表索引
ALTER TABLE `sys_dict_type` ADD INDEX `idx_status` (`status`) USING BTREE;
ALTER TABLE `sys_dict_type` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_logininfor表索引（部分索引已在表创建时定义）
ALTER TABLE `sys_logininfor` ADD INDEX `idx_user_name` (`user_name`) USING BTREE;
ALTER TABLE `sys_logininfor` ADD INDEX `idx_ipaddr` (`ipaddr`) USING BTREE;

-- sys_member表索引（部分索引已在表创建时定义）
ALTER TABLE `sys_member` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_member_real_name_auth表索引（部分索引已在表创建时定义）
ALTER TABLE `sys_member_real_name_auth` ADD INDEX `idx_real_name` (`real_name`) USING BTREE;
ALTER TABLE `sys_member_real_name_auth` ADD INDEX `idx_cert_no_hash` (`cert_no_hash`) USING BTREE;

-- sys_member_third_party_bind表索引（索引已在表创建时定义）

-- sys_menu表索引
ALTER TABLE `sys_menu` ADD INDEX `idx_parent_id` (`parent_id`) USING BTREE;
ALTER TABLE `sys_menu` ADD INDEX `idx_type` (`type`) USING BTREE;
ALTER TABLE `sys_menu` ADD INDEX `idx_status` (`status`) USING BTREE;
ALTER TABLE `sys_menu` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_oplog表索引
ALTER TABLE `sys_oplog` ADD INDEX `idx_title` (`title`) USING BTREE;
ALTER TABLE `sys_oplog` ADD INDEX `idx_operator` (`operator`) USING BTREE;
ALTER TABLE `sys_oplog` ADD INDEX `idx_status` (`status`) USING BTREE;
ALTER TABLE `sys_oplog` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_permission表索引
ALTER TABLE `sys_permission` ADD INDEX `idx_parent_id` (`parent_id`) USING BTREE;
ALTER TABLE `sys_permission` ADD INDEX `idx_permission` (`permission`) USING BTREE;
ALTER TABLE `sys_permission` ADD INDEX `idx_project_code` (`project_code`) USING BTREE;
ALTER TABLE `sys_permission` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_post表索引
ALTER TABLE `sys_post` ADD INDEX `idx_code` (`code`) USING BTREE;
ALTER TABLE `sys_post` ADD INDEX `idx_status` (`status`) USING BTREE;
ALTER TABLE `sys_post` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_project表索引（部分索引已在表创建时定义）
ALTER TABLE `sys_project` ADD INDEX `idx_status` (`status`) USING BTREE;
ALTER TABLE `sys_project` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_role表索引
ALTER TABLE `sys_role` ADD INDEX `idx_key` (`key`) USING BTREE;
ALTER TABLE `sys_role` ADD INDEX `idx_status` (`status`) USING BTREE;
ALTER TABLE `sys_role` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_role_dept表索引（多对多关联表）
ALTER TABLE `sys_role_dept` ADD INDEX `idx_role_id` (`role_id`) USING BTREE;
ALTER TABLE `sys_role_dept` ADD INDEX `idx_dept_id` (`dept_id`) USING BTREE;

-- sys_role_menu表索引（多对多关联表）
ALTER TABLE `sys_role_menu` ADD INDEX `idx_role_id` (`role_id`) USING BTREE;
ALTER TABLE `sys_role_menu` ADD INDEX `idx_menu_id` (`menu_id`) USING BTREE;

-- sys_role_permission表索引（多对多关联表）
ALTER TABLE `sys_role_permission` ADD INDEX `idx_role_id` (`role_id`) USING BTREE;
ALTER TABLE `sys_role_permission` ADD INDEX `idx_permission_id` (`permission_id`) USING BTREE;

-- sys_role_project表索引（多对多关联表）
ALTER TABLE `sys_role_project` ADD INDEX `idx_role_id` (`role_id`) USING BTREE;
ALTER TABLE `sys_role_project` ADD INDEX `idx_project_code` (`project_code`) USING BTREE;

-- sys_tenant表索引
ALTER TABLE `sys_tenant` ADD INDEX `idx_code` (`code`) USING BTREE;
ALTER TABLE `sys_tenant` ADD INDEX `idx_status` (`status`) USING BTREE;
ALTER TABLE `sys_tenant` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_tenant_menu表索引（多对多关联表）
ALTER TABLE `sys_tenant_menu` ADD INDEX `idx_tenant_id` (`tenant_id`) USING BTREE;
ALTER TABLE `sys_tenant_menu` ADD INDEX `idx_menu_id` (`menu_id`) USING BTREE;

-- sys_tenant_permission表索引（多对多关联表）
ALTER TABLE `sys_tenant_permission` ADD INDEX `idx_tenant_id` (`tenant_id`) USING BTREE;
ALTER TABLE `sys_tenant_permission` ADD INDEX `idx_permission_id` (`permission_id`) USING BTREE;

-- sys_tenant_project表索引（多对多关联表）
ALTER TABLE `sys_tenant_project` ADD INDEX `idx_tenant_id` (`tenant_id`) USING BTREE;
ALTER TABLE `sys_tenant_project` ADD INDEX `idx_project_code` (`project_code`) USING BTREE;

-- sys_tenant_user表索引（多对多关联表）
ALTER TABLE `sys_tenant_user` ADD INDEX `idx_tenant_id` (`tenant_id`) USING BTREE;
ALTER TABLE `sys_tenant_user` ADD INDEX `idx_user_id` (`user_id`) USING BTREE;

-- sys_user表索引
ALTER TABLE `sys_user` ADD INDEX `idx_account` (`account`) USING BTREE;
ALTER TABLE `sys_user` ADD INDEX `idx_phone` (`phone`) USING BTREE;
ALTER TABLE `sys_user` ADD INDEX `idx_email` (`email`) USING BTREE;
ALTER TABLE `sys_user` ADD INDEX `idx_status` (`status`) USING BTREE;
ALTER TABLE `sys_user` ADD INDEX `idx_dept_id` (`dept_id`) USING BTREE;
ALTER TABLE `sys_user` ADD INDEX `idx_create_time` (`create_time`) USING BTREE;

-- sys_user_post表索引（多对多关联表）
ALTER TABLE `sys_user_post` ADD INDEX `idx_user_id` (`user_id`) USING BTREE;
ALTER TABLE `sys_user_post` ADD INDEX `idx_post_id` (`post_id`) USING BTREE;

-- sys_user_real_name_auth表索引（部分索引已在表创建时定义）
ALTER TABLE `sys_user_real_name_auth` ADD INDEX `idx_real_name` (`real_name`) USING BTREE;

-- sys_user_role表索引（多对多关联表）
ALTER TABLE `sys_user_role` ADD INDEX `idx_user_id` (`user_id`) USING BTREE;
ALTER TABLE `sys_user_role` ADD INDEX `idx_role_id` (`role_id`) USING BTREE;
ALTER TABLE `sys_user_role` ADD INDEX `idx_tenant_id` (`tenant_id`) USING BTREE;

-- sys_user_third_party_bind表索引
ALTER TABLE `sys_user_third_party_bind` ADD INDEX `idx_provider` (`provider`) USING BTREE;
ALTER TABLE `sys_user_third_party_bind` ADD INDEX `idx_user_id` (`user_id`) USING BTREE;

-- 公共字段索引（为有公共字段的表添加）
ALTER TABLE `oauth_client_details` ADD INDEX `idx_create_user_id` (`create_user_id`) USING BTREE;
ALTER TABLE `oauth_client_details` ADD INDEX `idx_update_time` (`update_time`) USING BTREE;

ALTER TABLE `sys_config` ADD INDEX `idx_create_user_id` (`create_user_id`) USING BTREE;
ALTER TABLE `sys_config` ADD INDEX `idx_update_time` (`update_time`) USING BTREE;

ALTER TABLE `sys_config_group` ADD INDEX `idx_create_user_id` (`create_user_id`) USING BTREE;
ALTER TABLE `sys_config_group` ADD INDEX `idx_update_time` (`update_time`) USING BTREE;

SET FOREIGN_KEY_CHECKS = 1;
