/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.6.198_root
 Source Server Type    : MySQL
 Source Server Version : 50736 (5.7.36-log)
 Source Host           : 192.168.6.198:32306
 Source Schema         : hc_competence_v2

 Target Server Type    : MySQL
 Target Server Version : 50736 (5.7.36-log)
 File Encoding         : 65001

 Date: 04/11/2024 09:51:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID | [dto,vo]',
                              `name` varchar(100) NOT NULL DEFAULT '' COMMENT '配置名称 | [dto,vo, filter]',
                              `config_key` varchar(100) NOT NULL DEFAULT '' COMMENT '配置键名 | [dto,vo,filter]',
                              `config_value` varchar(500) NOT NULL DEFAULT '' COMMENT '配置键值 | [dto,vo]',
                              `create_time` datetime NOT NULL COMMENT '创建时间',
                              `update_time` datetime NOT NULL COMMENT '更新时间',
                              `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统配置';

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
                            `leader` varchar(20) NOT NULL DEFAULT '' COMMENT '负责人 | [dto,vo]',
                            `phone` varchar(11) NOT NULL DEFAULT '' COMMENT '联系电话 | [dto,vo]',
                            `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱 | [dto,vo]',
                            `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 | [dto,vo,filter] | enum{ENABLE(1,"启用"),DISABLE(0,"禁用")}',
                            `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
                            `level` int(11) NOT NULL DEFAULT '0' COMMENT '树层级',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `remark` varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1840313784251580419 DEFAULT CHARSET=utf8 COMMENT='部门表[tree]';

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
                                 `id` bigint(20) NOT NULL COMMENT '字典ID',
                                 `sort_no` int(4) NOT NULL DEFAULT '0' COMMENT '字典排序',
                                 `label` varchar(100) NOT NULL DEFAULT '' COMMENT '字典标签',
                                 `value` varchar(100) NOT NULL DEFAULT '' COMMENT '字典键值',
                                 `code` varchar(100) NOT NULL DEFAULT '' COMMENT '字典类型编码',
                                 `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认（Y是 N否）',
                                 `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态enum{ENABLE(1,"正常"),DISABLE(0,"禁用")}',
                                 `create_time` datetime NOT NULL COMMENT '创建时间',
                                 `update_time` datetime NOT NULL COMMENT '更新时间',
                                 `remark` varchar(500) DEFAULT '' COMMENT '备注',
                                 `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典数据表';

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
                                 `id` bigint(20) NOT NULL COMMENT '字典主键',
                                 `name` varchar(100) NOT NULL DEFAULT '' COMMENT '字典名称',
                                 `code` varchar(100) NOT NULL DEFAULT '' COMMENT '字典代码',
                                 `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态enum{ENABLE(1,"正常"),DISABLE(0,"禁用")}',
                                 `create_time` datetime NOT NULL COMMENT '创建时间',
                                 `update_time` datetime NOT NULL COMMENT '更新时间',
                                 `remark` varchar(500) DEFAULT '' COMMENT '备注',
                                 `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典类型表';

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
                           `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
                           `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
                           `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
                           `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
                           `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
                           `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
                           `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
                           `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
                           `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                           `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
                           `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                           `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
                           PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log` (
                               `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
                               `job_name` varchar(64) NOT NULL COMMENT '任务名称',
                               `job_group` varchar(64) NOT NULL COMMENT '任务组名',
                               `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
                               `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
                               `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
                               `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度日志表';

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
                                  PRIMARY KEY (`info_id`),
                                  KEY `idx_sys_logininfor_s` (`status`),
                                  KEY `idx_sys_logininfor_lt` (`access_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1125 DEFAULT CHARSET=utf8 COMMENT='系统访问记录';

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
                            `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '菜单状态（0停用 1启用）',
                            `cache` tinyint(1) DEFAULT NULL COMMENT '是否缓存（0不缓存 1缓存）',
                            `chain` tinyint(1) DEFAULT NULL COMMENT '是否外链（0否 1是）',
                            `key` varchar(100) DEFAULT NULL COMMENT '权限标识',
                            `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
                            `level` int(5) DEFAULT NULL COMMENT '树层级',
                            `category_type` varchar(100) NOT NULL DEFAULT '' COMMENT '分组标识 | [dto,vo,filter]',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `remark` varchar(500) DEFAULT '' COMMENT '备注',
                            `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标记',
                            `parent_path` varchar(500) DEFAULT NULL COMMENT '父路径',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1851522039536578562 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单表';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='操作日志记录';

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
                                  `category_type` varchar(100) NOT NULL DEFAULT '' COMMENT '分组标识 | [dto,vo,filter]',
                                  `level` int(5) DEFAULT '0' COMMENT '树层级',
                                  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标记',
                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1839496128472678402 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限表';

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
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
                            `remark` varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1839505564725542915 DEFAULT CHARSET=utf8 COMMENT='岗位信息表';

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
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色信息表';

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
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1840286565693259783 DEFAULT CHARSET=utf8 COMMENT='角色和部门关联表';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色和菜单关联表';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色和菜单关联表';

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '租户ID | [dto,vo]',
                              `name` varchar(255) NOT NULL COMMENT '租户名称 | [dto,vo,filter]',
                              `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 | [dto,vo,filter] | enum{ENABLE(1,"启用"),DISABLE(0,"禁用")}',
                              `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
                              `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                              `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1734816207767801859 DEFAULT CHARSET=utf8mb4 COMMENT='租户表';

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
) ENGINE=InnoDB AUTO_INCREMENT=1734816455017828355 DEFAULT CHARSET=utf8mb4 COMMENT='租户菜单表';

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
) ENGINE=InnoDB AUTO_INCREMENT=1734816524865572866 DEFAULT CHARSET=utf8mb4 COMMENT='租户权限表';

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
) ENGINE=InnoDB AUTO_INCREMENT=1735948619464335362 DEFAULT CHARSET=utf8mb4 COMMENT='租户用户表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                            `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户 ID',
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
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                            `identity` varchar(50) NOT NULL DEFAULT '' COMMENT '身份',
                            `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '帐号类型 | enum{UNKNOWN(0,"未知"),ADMIN(1,"系统管理员"),WECHAT(2,"微信")}',
                            `initialize_password_change` tinyint(4) NOT NULL DEFAULT '0' COMMENT '初始化密码是否修改',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1839237413859069954 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户信息表';

INSERT INTO `sys_user` (`id`, `tenant_id`, `dept_id`, `account`, `password`, `salt`, `name`, `email`, `phone`, `sex`, `avatar`, `status`, `deleted`, `create_time`, `update_time`, `remark`, `identity`, `type`, `initialize_password_change`) VALUES (1, 0, 1, 'admin', '$2a$10$d7EegjNv76WG5GGmYT56heLo9UfmWCP73Yh04b.yRycnW3imzL2be', 'x0jdch', '管理员', '', '13500000000', '0', '/20240926/2da4b1e378c74bbcbeb35c4b9ec733e1.png', 1, 0, '2023-01-29 11:31:48', '2024-10-12 17:52:04', NULL, '', 1, 1);

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
) ENGINE=InnoDB AUTO_INCREMENT=1838897206635732995 DEFAULT CHARSET=utf8 COMMENT='用户与岗位关联表';

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
) ENGINE=InnoDB AUTO_INCREMENT=1839240990572777475 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户和角色关联表';

SET FOREIGN_KEY_CHECKS = 1;
