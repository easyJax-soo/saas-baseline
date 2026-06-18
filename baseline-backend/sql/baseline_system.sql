/*
 Benchmark System SQL
 Multi-tenant SaaS Baseline Schema

 Generated: 2026-06-11
 Source: 合并自 hc_competence_v3_1007.sql (主) + system_mysql.sql (补充 sys_user.tenant_id)
 已脱敏: hc_competence -> baseline_system, com.gzhaochuan -> com.baseline
 所有关联表数据使用 SELECT 子查询动态解析 ID, 不硬编码主键
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================================
-- Section 1: DDL
-- ============================================================================

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '应用名称',
  `client_id` varchar(100) DEFAULT NULL COMMENT '客户端ID',
  `client_secret` varchar(255) DEFAULT '' COMMENT '客户端密钥',
  `resource_ids` varchar(255) DEFAULT '' COMMENT '资源id集合,多个用逗号隔开',
  `scope` varchar(255) DEFAULT 'all' COMMENT '作用域',
  `authorized_grant_types` varchar(255) DEFAULT '' COMMENT '授权类型',
  `web_server_redirect_uri` varchar(255) DEFAULT '' COMMENT '重定向URI',
  `authorities` varchar(255) DEFAULT '' COMMENT '权限列表',
  `access_token_validity` int(11) DEFAULT '86400' COMMENT '访问令牌有效期(秒)',
  `refresh_token_validity` int(11) DEFAULT '172800' COMMENT '刷新令牌有效期(秒)',
  `additional_information` varchar(255) DEFAULT NULL COMMENT '预留字段(JSON)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `autoapprove` varchar(255) DEFAULT 'true' COMMENT '自动批准',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_client_id` (`client_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='OAuth2客户端信息';

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '配置名称',
  `group_code` varchar(255) NOT NULL DEFAULT '' COMMENT '分组编码',
  `config_key` varchar(100) NOT NULL DEFAULT '' COMMENT '配置键名',
  `config_value` varchar(500) NOT NULL DEFAULT '' COMMENT '配置键值',
  `input_type` varchar(255) NOT NULL DEFAULT '' COMMENT '输入框类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_config_key` (`config_key`),
  KEY `idx_group_code` (`group_code`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统配置';

-- ----------------------------
-- Table structure for sys_config_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_config_group`;
CREATE TABLE `sys_config_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '分组名称',
  `group_code` varchar(100) NOT NULL DEFAULT '' COMMENT '配置分组编码',
  `sys_default` tinyint(4) NOT NULL DEFAULT '0' COMMENT '系统默认,不允许删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_group_code` (`group_code`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统配置分组';

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父部门id',
  `parent_path` varchar(500) NOT NULL DEFAULT '' COMMENT '父路径',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '部门名称',
  `code` varchar(255) NOT NULL DEFAULT '' COMMENT '部门编码',
  `sort_no` int(11) NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `leader_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '负责人用户ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态(0停用 1启用)',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标志',
  `level` int(11) NOT NULL DEFAULT '0' COMMENT '树层级',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_tenant_code` (`tenant_id`, `code`) USING BTREE,
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='部门表';

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
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态(0禁用 1启用)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典数据表';

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '字典名称',
  `code` varchar(100) NOT NULL DEFAULT '' COMMENT '字典代码',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态(0禁用 1启用)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典类型表';

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `status` char(1) DEFAULT '0' COMMENT '登录状态',
  `msg` varchar(255) DEFAULT '' COMMENT '提示信息',
  `access_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`),
  KEY `idx_sys_logininfor_s` (`status`),
  KEY `idx_sys_logininfor_lt` (`access_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统访问记录';

-- ----------------------------
-- Table structure for sys_member
-- ----------------------------
DROP TABLE IF EXISTS `sys_member`;
CREATE TABLE `sys_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `account` varchar(30) NOT NULL COMMENT '登录账号',
  `password` varchar(255) DEFAULT '' COMMENT '密码',
  `salt` varchar(20) DEFAULT '' COMMENT '盐加密',
  `name` varchar(30) DEFAULT '' COMMENT '会员昵称',
  `email` varchar(50) DEFAULT '' COMMENT '会员邮箱',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '会员性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像路径',
  `status` tinyint(4) DEFAULT '1' COMMENT '帐号状态(0禁用 1正常)',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0存在 1删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_account` (`account`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`),
  KEY `idx_status` (`status`),
  KEY `idx_phone` (`phone`),
  KEY `idx_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='会员信息表';

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
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_member_id` (`member_id`) USING BTREE,
  UNIQUE KEY `uk_cert_no_hash` (`cert_no_hash`) USING BTREE,
  KEY `idx_auth_status` (`auth_status`),
  KEY `idx_submit_time` (`submit_time`),
  KEY `idx_auditor_id` (`auditor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='会员实名认证信息表';

-- ----------------------------
-- Table structure for sys_member_third_party_bind
-- ----------------------------
DROP TABLE IF EXISTS `sys_member_third_party_bind`;
CREATE TABLE `sys_member_third_party_bind` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
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
  UNIQUE KEY `uk_provider_third_party_user_id` (`provider`,`third_party_user_id`) USING BTREE,
  KEY `idx_member_id` (`member_id`),
  KEY `idx_provider` (`provider`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='会员第三方绑定信息表';

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
  `target` varchar(20) DEFAULT '' COMMENT '打开方式(menuItem页签 menuBlank新窗口)',
  `type` char(1) DEFAULT '' COMMENT '菜单类型(M目录 C菜单 F按钮)',
  `visible` tinyint(1) DEFAULT '0' COMMENT '显示状态(0隐藏 1显示)',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '菜单状态(0停用 1启用)',
  `cache` tinyint(1) DEFAULT NULL COMMENT '是否缓存',
  `chain` tinyint(1) DEFAULT NULL COMMENT '是否外链',
  `key` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `level` int(5) DEFAULT NULL COMMENT '树层级',
  `project_code` varchar(100) NOT NULL DEFAULT '' COMMENT '项目编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标记',
  `parent_path` varchar(500) DEFAULT NULL COMMENT '父路径',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='菜单表';

-- ----------------------------
-- Table structure for sys_oplog
-- ----------------------------
DROP TABLE IF EXISTS `sys_oplog`;
CREATE TABLE `sys_oplog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `description` varchar(512) DEFAULT '' COMMENT '操作描述',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称class#method',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作人员ID',
  `operator` varchar(50) DEFAULT '' COMMENT '操作人姓名',
  `url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `ip` varchar(128) DEFAULT '' COMMENT '主机地址',
  `params` varchar(2000) DEFAULT NULL COMMENT '请求参数',
  `result` varchar(2000) DEFAULT NULL COMMENT '响应参数',
  `status` tinyint(4) DEFAULT '0' COMMENT '操作状态(0失败 1成功)',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint(20) DEFAULT '0' COMMENT '消耗时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='操作日志记录';

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父ID',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '权限名称',
  `permission` varchar(100) NOT NULL DEFAULT '' COMMENT '权限标识',
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
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_permission` (`permission`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `code` varchar(64) NOT NULL DEFAULT '' COMMENT '岗位编码',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '岗位名称',
  `sort_no` int(4) NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态(0禁用 1启用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志',
  `remark` varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`),
  KEY `idx_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='岗位信息表';

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
  KEY `idx_project_type` (`project_type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统项目表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '角色名称',
  `key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `data_scope` tinyint(4) NOT NULL DEFAULT '0' COMMENT '数据范围(1全部 2自定义 3本部门 4本部门及以下)',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '角色状态(0禁用 1启用)',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_key` (`key`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色信息表';

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色和部门关联表';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_role_id` (`role_id`),
  KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色和菜单关联表';

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_role_id` (`role_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色和权限关联表';

-- ----------------------------
-- Table structure for sys_role_project
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_project`;
CREATE TABLE `sys_role_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `project_code` varchar(255) NOT NULL COMMENT '项目编码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_role_id` (`role_id`),
  KEY `idx_tenant_id` (`tenant_id`),
  KEY `idx_project_code` (`project_code`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色和项目关联表';

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '租户ID',
  `name` varchar(255) NOT NULL COMMENT '租户名称',
  `code` varchar(255) NOT NULL DEFAULT '' COMMENT '租户编码',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态(0禁用 1启用)',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户表';

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
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`),
  KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户菜单关联表';

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
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户权限关联表';

-- ----------------------------
-- Table structure for sys_tenant_project
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_project`;
CREATE TABLE `sys_tenant_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_code` varchar(255) NOT NULL DEFAULT '' COMMENT '项目编码',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`),
  KEY `idx_project_code` (`project_code`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户项目表';

-- ----------------------------
-- Table structure for sys_tenant_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_user`;
CREATE TABLE `sys_tenant_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `is_tenant_admin` tinyint(1) DEFAULT '0' COMMENT '租户管理员',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态(0禁用 1启用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户用户关联表';

-- ----------------------------
-- Table structure for sys_user
-- Note: tenant_id 字段来自 system_mysql.sql,用于多租户用户隔离
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `dept_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '部门ID',
  `account` varchar(30) NOT NULL COMMENT '登录账号',
  `password` varchar(255) DEFAULT '' COMMENT '密码',
  `salt` varchar(20) DEFAULT '' COMMENT '盐加密',
  `name` varchar(30) DEFAULT '' COMMENT '用户昵称',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别(0男 1女 2未知)',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像路径',
  `status` tinyint(4) DEFAULT '1' COMMENT '账号状态(0禁用 1启用)',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `identity` varchar(50) NOT NULL DEFAULT '' COMMENT '身份',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '账号类型(0未知 1管理员 2微信)',
  `initialize_password_change` tinyint(4) NOT NULL DEFAULT '0' COMMENT '初始化密码是否修改',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_account` (`account`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户与岗位关联表';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户和角色关联表';

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
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户ID',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新用户ID',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '更新用户',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_id` (`user_id`) USING BTREE,
  UNIQUE KEY `uk_cert_no_hash` (`cert_no_hash`) USING BTREE,
  KEY `idx_auth_status` (`auth_status`),
  KEY `idx_submit_time` (`submit_time`),
  KEY `idx_auditor_id` (`auditor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户实名认证信息表';

-- ----------------------------
-- Table structure for sys_user_third_party_bind
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_third_party_bind`;
CREATE TABLE `sys_user_third_party_bind` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_provider_third_party_user_id` (`provider`,`third_party_user_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`),
  KEY `idx_provider` (`provider`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户第三方绑定信息表';

-- ============================================================================
-- Section 2: Seed Data (无硬编码主键, 使用 SELECT 子查询解析关联ID)
-- ============================================================================

-- ----------------------------
-- Seed: sys_tenant (默认平台租户)
-- ----------------------------
INSERT INTO `sys_tenant` (`name`, `code`, `status`, `deleted`, `create_time`, `update_time`, `remark`)
VALUES ('默认租户', 'default', 1, 0, NOW(), NOW(), '系统默认平台租户');

-- ----------------------------
-- Seed: sys_config_group
-- ----------------------------
INSERT INTO `sys_config_group` (`name`, `group_code`, `sys_default`, `create_time`, `update_time`, `remark`)
VALUES ('系统配置', 'sys', 1, NOW(), NOW(), '系统默认配置组');

-- ----------------------------
-- Seed: sys_config (系统级配置项)
-- ----------------------------
INSERT INTO `sys_config` (`name`, `group_code`, `config_key`, `config_value`, `input_type`, `create_time`, `update_time`, `remark`)
VALUES
('系统LOGO', 'sys', 'sysLogo', '/20241014/3e26fa7d829841f09867ce3dd7602c1e.png', 'image', NOW(), NOW(), '系统首页和菜单左上角的LOGO图片'),
('系统版本', 'sys', 'sysVersion', 'Copyright © 2014-2026 v5.4.1', 'input', NOW(), NOW(), '系统版本'),
('是否多模块', 'sys', 'sysMultiModule', 'false', 'input', NOW(), NOW(), 'true=登录后跳转项目选择页面, false=登录后跳转首页');

-- ----------------------------
-- Seed: sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` (`name`, `code`, `status`, `create_time`, `update_time`, `remark`, `deleted`)
VALUES
('启用禁用状态', 'sysStatus', 1, NOW(), NOW(), '通用启用禁用状态', 0),
('性别', 'sysSex', 1, NOW(), NOW(), '用户性别', 0),
('输入框类型', 'sysConfigInput', 1, NOW(), NOW(), '配置项输入框类型', 0),
('是否', 'sysYesNo', 1, NOW(), NOW(), '通用是否', 0),
('窗口打开方式', 'sysWindowOpen', 1, NOW(), NOW(), '链接窗口打开方式', 0),
('显示状态', 'sysShowStatus', 1, NOW(), NOW(), '显示/隐藏状态', 0),
('认证类型', 'sysAuthType', 1, NOW(), NOW(), '实名认证类型', 0),
('认证审核状态', 'sysAuthStatus', 1, NOW(), NOW(), '实名认证审核状态', 0),
('系统菜单类型', 'sysMenuType', 1, NOW(), NOW(), '菜单类型', 0),
('租户用户类型', 'sysTenantUserType', 1, NOW(), NOW(), '租户用户角色类型', 0),
('HTTP请求方法', 'sysHttpMethod', 1, NOW(), NOW(), 'HTTP请求方法', 0),
('成功失败状态', 'sysSuccessFail', 1, NOW(), NOW(), '操作成功失败状态', 0);

-- ----------------------------
-- Seed: sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` (`sort_no`, `label`, `value`, `code`, `is_default`, `status`, `create_time`, `update_time`, `remark`, `deleted`)
VALUES
(1, '禁用', '0', 'sysStatus', 0, 1, NOW(), NOW(), '禁用状态', 0),
(2, '启用', '1', 'sysStatus', 1, 1, NOW(), NOW(), '启用状态', 0),
(1, '男', '0', 'sysSex', 1, 1, NOW(), NOW(), '', 0),
(2, '女', '1', 'sysSex', 0, 1, NOW(), NOW(), '', 0),
(3, '保密', '2', 'sysSex', 0, 1, NOW(), NOW(), '', 0),
(1, '文本', 'input', 'sysConfigInput', 1, 1, NOW(), NOW(), '', 0),
(2, '开关', 'switch', 'sysConfigInput', 0, 1, NOW(), NOW(), '', 0),
(3, '图片', 'image', 'sysConfigInput', 0, 1, NOW(), NOW(), '', 0),
(1, '否', '0', 'sysYesNo', 1, 1, NOW(), NOW(), '', 0),
(2, '是', '1', 'sysYesNo', 0, 1, NOW(), NOW(), '', 0),
(1, '当前窗口', '_self', 'sysWindowOpen', 1, 1, NOW(), NOW(), '', 0),
(2, '新窗口', '_blank', 'sysWindowOpen', 0, 1, NOW(), NOW(), '', 0),
(1, '隐藏', '0', 'sysShowStatus', 0, 1, NOW(), NOW(), '', 0),
(2, '显示', '1', 'sysShowStatus', 1, 1, NOW(), NOW(), '', 0),
(1, '身份证', '1', 'sysAuthType', 1, 1, NOW(), NOW(), '', 0),
(2, '护照', '2', 'sysAuthType', 0, 1, NOW(), NOW(), '', 0),
(1, '待审核', '0', 'sysAuthStatus', 1, 1, NOW(), NOW(), '', 0),
(2, '审核通过', '1', 'sysAuthStatus', 0, 1, NOW(), NOW(), '', 0),
(3, '审核拒绝', '2', 'sysAuthStatus', 0, 1, NOW(), NOW(), '', 0),
(4, '已过期', '3', 'sysAuthStatus', 0, 1, NOW(), NOW(), '', 0),
(1, '目录', 'M', 'sysMenuType', 0, 1, NOW(), NOW(), '', 0),
(2, '菜单', 'C', 'sysMenuType', 1, 1, NOW(), NOW(), '', 0),
(3, '按钮', 'F', 'sysMenuType', 0, 1, NOW(), NOW(), '', 0),
(1, '普通用户', '0', 'sysTenantUserType', 1, 1, NOW(), NOW(), '', 0),
(2, '租户管理员', '1', 'sysTenantUserType', 0, 1, NOW(), NOW(), '', 0),
(1, 'GET请求', 'GET', 'sysHttpMethod', 1, 1, NOW(), NOW(), '', 0),
(2, 'POST请求', 'POST', 'sysHttpMethod', 0, 1, NOW(), NOW(), '', 0),
(3, 'PUT请求', 'PUT', 'sysHttpMethod', 0, 1, NOW(), NOW(), '', 0),
(4, 'DELETE请求', 'DELETE', 'sysHttpMethod', 0, 1, NOW(), NOW(), '', 0),
(1, '失败', '0', 'sysSuccessFail', 0, 1, NOW(), NOW(), '', 0),
(2, '成功', '1', 'sysSuccessFail', 1, 1, NOW(), NOW(), '', 0);

-- ----------------------------
-- Seed: sys_dept (根部门)
-- ----------------------------
INSERT INTO `sys_dept` (`tenant_id`, `parent_id`, `parent_path`, `name`, `code`, `sort_no`, `leader_user_id`, `status`, `deleted`, `level`, `create_time`, `update_time`, `remark`)
VALUES (0, 0, '0', '总公司', 'ROOT', 0, 0, 1, 0, 1, NOW(), NOW(), '根部门');

-- ----------------------------
-- Seed: sys_role (超级管理员角色)
-- ----------------------------
INSERT INTO `sys_role` (`tenant_id`, `name`, `key`, `data_scope`, `status`, `deleted`, `create_time`, `update_time`, `remark`)
VALUES (0, '超级管理员', 'admin', 1, 1, 0, NOW(), NOW(), '系统超级管理员角色,拥有全部数据权限');

-- ----------------------------
-- Seed: sys_project (默认系统项目)
-- ----------------------------
INSERT INTO `sys_project` (`code`, `name`, `description`, `project_type`, `url`, `logo`, `icon`, `sort_no`, `status`, `is_default`, `target`, `create_time`, `update_time`, `remark`, `deleted`)
VALUES ('system', '用户中心', '系统默认项目', 'sysBash', '', '', '', 1, 1, 1, '_self', NOW(), NOW(), '系统默认项目', 0);

-- ----------------------------
-- Seed: sys_post
-- ----------------------------
INSERT INTO `sys_post` (`tenant_id`, `code`, `name`, `sort_no`, `status`, `create_time`, `update_time`, `deleted`, `remark`)
VALUES
(0, 'ceo', '首席执行官', 1, 1, NOW(), NOW(), 0, '公司最高管理者'),
(0, 'cto', '首席技术官', 2, 1, NOW(), NOW(), 0, '技术负责人'),
(0, 'dev', '开发工程师', 3, 1, NOW(), NOW(), 0, '研发岗位');

-- ----------------------------
-- Seed: sys_user (admin 用户, 部门取根部门 id)
-- 密码 bcrypt: admin123
-- ----------------------------
INSERT INTO `sys_user` (`id`, `tenant_id`, `dept_id`, `account`, `password`, `salt`, `name`, `email`, `phone`, `sex`, `avatar`, `status`, `deleted`, `create_time`, `create_user_id`, `create_user`, `update_time`, `update_user_id`, `update_user`, `remark`, `identity`, `type`, `initialize_password_change`)
SELECT 1, 0, d.id, 'admin',
       '$2a$10$0a22a59ac468fe43853abOVa.6V08YEF00tG4oTUpCzT5uzhBnTbC',
       'g8arzw', '管理员', 'admin@example.com', '13500000000', '0',
       '/20240926/2da4b1e378c74bbcbeb35c4b9ec733e1.png',
       1, 0, NOW(), 0, 'system', NOW(), 0, 'system', '系统管理员', '', 1, 1
FROM `sys_dept` d
WHERE d.`code` = 'ROOT' AND d.`tenant_id` = 0
LIMIT 1;

-- ----------------------------
-- Seed: sys_menu (一级菜单)
-- ----------------------------
INSERT INTO `sys_menu` (`name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`)
VALUES
('首页', 0, 1, 'component', '/home', 'home/home', 'menuItem', 'C', 1, 1, 1, 0, 'system:home', 'HomeOutlined', 1, 'system', NOW(), NOW(), '系统首页', 0, '/'),
('系统管理', 0, 2, 'layout', '/setting', NULL, 'menuItem', 'M', 1, 1, 0, 0, 'system:setting', 'SettingOutlined', 1, 'system', NOW(), NOW(), '系统管理模块', 0, '/'),
('用户中心', 0, 3, 'component', '/userCenter', 'userCenter/userCenter', 'menuItem', 'C', 0, 1, 1, 0, 'system:userCenter', 'UserOutlined', 1, 'system', NOW(), NOW(), '个人中心', 0, '/'),
('修改密码', 0, 4, 'component', '/changePassword', 'changePassword/changePassword', 'menuItem', 'C', 0, 1, 1, 0, 'system:changePassword', 'LockOutlined', 1, 'system', NOW(), NOW(), '修改登录密码', 0, '/');

-- ----------------------------
-- Seed: sys_menu (系统管理 二级菜单, parent_id 通过 key 子查询)
-- ----------------------------
INSERT INTO `sys_menu` (`name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`)
SELECT t.name, (SELECT id FROM `sys_menu` WHERE `key`='system:setting'), t.sort_no, 'component', t.path, t.component, 'menuItem', 'C', 1, 1, 1, 0, t.k, t.icon, 2, 'system', NOW(), NOW(), t.remark, 0, '/setting'
FROM (
  SELECT '用户管理' AS name, 1 AS sort_no, '/setting/user' AS path, 'setting/user/user' AS component, 'system:user' AS k, 'UserOutlined' AS icon, '系统用户管理' AS remark UNION ALL
  SELECT '角色管理', 2, '/role', 'setting/role/role', 'system:role', 'SafetyOutlined', '角色权限管理' UNION ALL
  SELECT '菜单管理', 3, '/menu', 'setting/menu/menu', 'system:menu', 'MenuOutlined', '系统菜单管理' UNION ALL
  SELECT '权限管理', 4, '/permission', 'setting/permission/permission', 'system:permission', 'KeyOutlined', '权限配置管理' UNION ALL
  SELECT '部门管理', 5, '/dept', 'setting/dept/dept', 'system:dept', 'ApartmentOutlined', '组织部门管理' UNION ALL
  SELECT '岗位管理', 6, '/Post', 'setting/post/post', 'system:post', 'IdcardOutlined', '岗位信息管理' UNION ALL
  SELECT '字典管理', 7, '/dic', 'setting/dic/dic', 'system:dic', 'BookOutlined', '数据字典管理' UNION ALL
  SELECT '系统配置', 8, '/setting/system', 'setting/system/system', 'system:config', 'ToolOutlined', '系统参数配置' UNION ALL
  SELECT '日志管理', 9, '/log', 'setting/log/log', 'system:log', 'FileTextOutlined', '系统日志管理' UNION ALL
  SELECT '租户管理', 10, '/tenant', 'setting/tenant/tenant', 'system:tenant', 'BankOutlined', '多租户管理' UNION ALL
  SELECT '租户用户', 11, '/tenantUser', 'setting/tenant/tenantUser', 'system:tenantUser', 'UsergroupAddOutlined', '租户用户管理'
) t;

-- ----------------------------
-- Seed: sys_menu (按钮类菜单, parent_id 通过父菜单 key 解析)
-- ----------------------------
INSERT INTO `sys_menu` (`name`, `parent_id`, `sort_no`, `path_type`, `path`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `project_code`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`)
SELECT t.name,
       (SELECT id FROM `sys_menu` WHERE `key` = t.parent_key),
       t.sort_no, '', '', '', 'F', 1, 1, 0, 0, t.k, '', 3, 'system', NOW(), NOW(), t.remark, 0, ''
FROM (
  -- 用户管理按钮
  SELECT 'system:user' AS parent_key, '用户新增' AS name, 1 AS sort_no, 'system:user.add' AS k, '新增用户' AS remark UNION ALL
  SELECT 'system:user', '用户编辑', 2, 'system:user.edit', '编辑用户' UNION ALL
  SELECT 'system:user', '用户删除', 3, 'system:user.delete', '删除用户' UNION ALL
  SELECT 'system:user', '用户详情', 4, 'system:user.detail', '查看用户详情' UNION ALL
  SELECT 'system:user', '修改密码', 5, 'system:user.changePassword', '修改用户密码' UNION ALL
  -- 角色管理按钮
  SELECT 'system:role', '角色新增', 1, 'system:role.add', '新增角色' UNION ALL
  SELECT 'system:role', '角色编辑', 2, 'system:role.edit', '编辑角色' UNION ALL
  SELECT 'system:role', '角色删除', 3, 'system:role.delete', '删除角色' UNION ALL
  SELECT 'system:role', '角色详情', 4, 'system:role.detail', '查看角色详情' UNION ALL
  -- 菜单管理按钮
  SELECT 'system:menu', '菜单新增', 1, 'system:menu.add', '新增菜单' UNION ALL
  SELECT 'system:menu', '菜单编辑', 2, 'system:menu.edit', '编辑菜单' UNION ALL
  SELECT 'system:menu', '菜单删除', 3, 'system:menu.delete', '删除菜单' UNION ALL
  SELECT 'system:menu', '菜单详情', 4, 'system:menu.detail', '查看菜单详情' UNION ALL
  -- 权限管理按钮
  SELECT 'system:permission', '权限新增', 1, 'system:permission.add', '新增权限' UNION ALL
  SELECT 'system:permission', '权限编辑', 2, 'system:permission.edit', '编辑权限' UNION ALL
  SELECT 'system:permission', '权限删除', 3, 'system:permission.delete', '删除权限' UNION ALL
  SELECT 'system:permission', '权限详情', 4, 'system:permission.detail', '查看权限详情' UNION ALL
  -- 部门管理按钮
  SELECT 'system:dept', '部门新增', 1, 'system:dept.add', '新增部门' UNION ALL
  SELECT 'system:dept', '部门编辑', 2, 'system:dept.edit', '编辑部门' UNION ALL
  SELECT 'system:dept', '部门删除', 3, 'system:dept.delete', '删除部门' UNION ALL
  SELECT 'system:dept', '部门详情', 4, 'system:dept.detail', '查看部门详情' UNION ALL
  -- 岗位管理按钮
  SELECT 'system:post', '岗位新增', 1, 'system:post.add', '新增岗位' UNION ALL
  SELECT 'system:post', '岗位编辑', 2, 'system:post.edit', '编辑岗位' UNION ALL
  SELECT 'system:post', '岗位删除', 3, 'system:post.delete', '删除岗位' UNION ALL
  SELECT 'system:post', '岗位详情', 4, 'system:post.detail', '查看岗位详情' UNION ALL
  -- 字典管理按钮
  SELECT 'system:dic', '字典分类新增', 1, 'system:dic.addType', '新增字典分类' UNION ALL
  SELECT 'system:dic', '字典项新增', 2, 'system:dic.add', '新增字典项' UNION ALL
  SELECT 'system:dic', '字典编辑', 3, 'system:dic.edit', '编辑字典' UNION ALL
  SELECT 'system:dic', '字典删除', 4, 'system:dic.delete', '删除字典' UNION ALL
  -- 系统配置按钮
  SELECT 'system:config', '新增配置', 1, 'system:config.add', '新增系统配置' UNION ALL
  SELECT 'system:config', '编辑配置', 2, 'system:config.edit', '编辑系统配置' UNION ALL
  SELECT 'system:config', '删除配置', 3, 'system:config.delete', '删除系统配置' UNION ALL
  SELECT 'system:config', '查看配置详情', 4, 'system:config.detail', '查看系统配置详情' UNION ALL
  SELECT 'system:config', '新增配置分组', 5, 'system:config.addGroup', '新增系统配置分组' UNION ALL
  SELECT 'system:config', '编辑配置分组', 6, 'system:config.editGroup', '编辑系统配置分组' UNION ALL
  SELECT 'system:config', '删除配置分组', 7, 'system:config.deleteGroup', '删除系统配置分组' UNION ALL
  -- 日志管理按钮
  SELECT 'system:log', '日志删除', 1, 'system:log.delete', '删除日志' UNION ALL
  SELECT 'system:log', '日志详情', 2, 'system:log.detail', '查看日志详情' UNION ALL
  -- 租户管理按钮
  SELECT 'system:tenant', '租户新增', 1, 'system:tenant.add', '新增租户' UNION ALL
  SELECT 'system:tenant', '租户编辑', 2, 'system:tenant.edit', '编辑租户' UNION ALL
  SELECT 'system:tenant', '租户删除', 3, 'system:tenant.delete', '删除租户' UNION ALL
  SELECT 'system:tenant', '租户详情', 4, 'system:tenant.detail', '查看租户详情' UNION ALL
  -- 租户用户按钮
  SELECT 'system:tenantUser', '租户用户绑定', 1, 'system:tenantUser.bind', '绑定租户用户' UNION ALL
  SELECT 'system:tenantUser', '租户用户解绑', 2, 'system:tenantUser.unbind', '解绑租户用户' UNION ALL
  SELECT 'system:tenantUser', '租户用户详情', 3, 'system:tenantUser.detail', '查看租户用户详情'
) t;

-- ----------------------------
-- Seed: sys_permission (一级权限分组)
-- ----------------------------
INSERT INTO `sys_permission` (`parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`)
VALUES
(0, '字典管理', 'system:dict', 1, 'system', 1, 0, NOW(), NOW(), '字典类型和字典数据管理'),
(0, '系统配置', 'system:config', 2, 'system', 1, 0, NOW(), NOW(), '系统配置管理'),
(0, '用户管理', 'system:user', 3, 'system', 1, 0, NOW(), NOW(), '系统用户管理'),
(0, '角色管理', 'system:role', 4, 'system', 1, 0, NOW(), NOW(), '系统角色管理'),
(0, '权限管理', 'system:permission', 5, 'system', 1, 0, NOW(), NOW(), '权限管理'),
(0, '菜单管理', 'system:menu', 6, 'system', 1, 0, NOW(), NOW(), '系统菜单管理'),
(0, '部门管理', 'system:dept', 7, 'system', 1, 0, NOW(), NOW(), '部门信息管理'),
(0, '岗位管理', 'system:post', 8, 'system', 1, 0, NOW(), NOW(), '岗位管理'),
(0, '租户管理', 'system:tenant', 9, 'system', 1, 0, NOW(), NOW(), '系统租户管理'),
(0, '文件管理', 'system:file', 10, 'system', 1, 0, NOW(), NOW(), '文件上传管理'),
(0, '操作日志', 'system:oplog', 11, 'system', 1, 0, NOW(), NOW(), '操作日志管理'),
(0, '下拉选项', 'system:options', 12, 'system', 1, 0, NOW(), NOW(), '下拉选项管理');

-- ----------------------------
-- Seed: sys_permission (二级权限项, parent_id 通过 permission 子查询)
-- ----------------------------
INSERT INTO `sys_permission` (`parent_id`, `name`, `permission`, `sort_no`, `project_code`, `level`, `deleted`, `create_time`, `update_time`, `remark`)
SELECT (SELECT id FROM `sys_permission` WHERE `permission` = t.parent_perm),
       t.name, t.perm, t.sort_no, 'system', 2, 0, NOW(), NOW(), t.remark
FROM (
  -- 字典管理 system:dict
  SELECT 'system:dict' AS parent_perm, '字典类型列表' AS name, 'system:dictType:list' AS perm, 1 AS sort_no, '查看字典类型列表' AS remark UNION ALL
  SELECT 'system:dict', '保存字典类型', 'system:dictType:save', 2, '新增编辑字典类型' UNION ALL
  SELECT 'system:dict', '删除字典类型', 'system:dictType:delete', 3, '删除字典类型' UNION ALL
  SELECT 'system:dict', '保存字典数据', 'system:dict:save', 4, '新增编辑字典数据' UNION ALL
  SELECT 'system:dict', '字典数据分页', 'system:dict:page', 5, '字典数据分页查询' UNION ALL
  SELECT 'system:dict', '删除字典数据', 'system:dict:delete', 6, '删除字典数据' UNION ALL
  -- 系统配置 system:config
  SELECT 'system:config', '配置列表', 'system:config:list', 1, '获取配置项列表' UNION ALL
  SELECT 'system:config', '保存配置', 'system:config:save', 2, '保存系统配置' UNION ALL
  SELECT 'system:config', '配置详情', 'system:config:detail', 3, '获取配置详情' UNION ALL
  SELECT 'system:config', '删除配置', 'system:config:delete', 4, '删除系统配置' UNION ALL
  SELECT 'system:config', '配置分组列表', 'system:config:group:list', 5, '获取配置分组列表' UNION ALL
  SELECT 'system:config', '保存配置分组', 'system:config:group:save', 6, '保存配置分组' UNION ALL
  SELECT 'system:config', '删除配置分组', 'system:config:group:delete', 7, '删除配置分组' UNION ALL
  -- 用户管理 system:user
  SELECT 'system:user', '用户分页', 'system:user:page', 1, '系统用户分页查询' UNION ALL
  SELECT 'system:user', '保存用户', 'system:user:save', 2, '新增编辑系统用户' UNION ALL
  SELECT 'system:user', '用户详情', 'system:user:detail', 3, '系统用户详情' UNION ALL
  SELECT 'system:user', '删除用户', 'system:user:delete', 4, '批量删除系统用户' UNION ALL
  SELECT 'system:user', '修改密码', 'system:user:changePassword', 5, '修改用户密码' UNION ALL
  SELECT 'system:user', '重置密码', 'system:user:resetPassword', 6, '重置用户密码' UNION ALL
  SELECT 'system:user', '更新个人信息', 'system:user:setInfo', 7, '更新个人信息' UNION ALL
  -- 角色管理 system:role
  SELECT 'system:role', '角色分页', 'system:role:page', 1, '系统角色分页列表' UNION ALL
  SELECT 'system:role', '角色列表', 'system:role:list', 2, '系统角色列表' UNION ALL
  SELECT 'system:role', '保存角色', 'system:role:save', 3, '新增编辑系统角色' UNION ALL
  SELECT 'system:role', '角色详情', 'system:role:detail', 4, '系统角色详情' UNION ALL
  SELECT 'system:role', '删除角色', 'system:role:delete', 5, '批量删除系统角色' UNION ALL
  -- 权限管理 system:permission
  SELECT 'system:permission', '权限树', 'system:permission:tree', 1, '权限树结构数据' UNION ALL
  SELECT 'system:permission', '保存权限', 'system:permission:save', 2, '新增编辑权限' UNION ALL
  SELECT 'system:permission', '权限详情', 'system:permission:detail', 3, '权限详情' UNION ALL
  SELECT 'system:permission', '删除权限', 'system:permission:delete', 4, '删除权限' UNION ALL
  -- 菜单管理 system:menu
  SELECT 'system:menu', '保存菜单', 'system:menu:save', 1, '保存菜单' UNION ALL
  SELECT 'system:menu', '菜单详情', 'system:menu:detail', 2, '菜单详情' UNION ALL
  SELECT 'system:menu', '删除菜单', 'system:menu:delete', 3, '删除菜单' UNION ALL
  SELECT 'system:menu', '菜单树', 'system:menu:tree', 4, '菜单树形列表' UNION ALL
  -- 部门管理 system:dept
  SELECT 'system:dept', '保存部门', 'system:dept:save', 1, '保存部门' UNION ALL
  SELECT 'system:dept', '部门详情', 'system:dept:detail', 2, '部门详情' UNION ALL
  SELECT 'system:dept', '删除部门', 'system:dept:delete', 3, '删除部门' UNION ALL
  SELECT 'system:dept', '部门树', 'system:dept:tree', 4, '部门树形列表' UNION ALL
  -- 岗位管理 system:post
  SELECT 'system:post', '岗位分页', 'system:post:page', 1, '岗位分页' UNION ALL
  SELECT 'system:post', '保存岗位', 'system:post:save', 2, '新增编辑岗位' UNION ALL
  SELECT 'system:post', '岗位详情', 'system:post:detail', 3, '岗位详情' UNION ALL
  SELECT 'system:post', '删除岗位', 'system:post:delete', 4, '批量删除岗位' UNION ALL
  -- 租户管理 system:tenant
  SELECT 'system:tenant', '租户分页', 'system:tenant:page', 1, '系统租户分页' UNION ALL
  SELECT 'system:tenant', '保存租户', 'system:tenant:save', 2, '新增编辑系统租户' UNION ALL
  SELECT 'system:tenant', '租户详情', 'system:tenant:detail', 3, '系统租户详情' UNION ALL
  SELECT 'system:tenant', '删除租户', 'system:tenant:delete', 4, '批量删除系统租户' UNION ALL
  SELECT 'system:tenant', '租户资源详情', 'system:tenant:resource:detail', 5, '获取租户资源详情' UNION ALL
  SELECT 'system:tenant', '保存租户资源', 'system:tenant:resource:save', 6, '保存租户资源' UNION ALL
  SELECT 'system:tenant', '租户用户分页', 'system:tenantUser:page', 7, '系统租户用户分页' UNION ALL
  SELECT 'system:tenant', '租户用户详情', 'system:tenantUser:detail', 8, '系统租户用户详情' UNION ALL
  SELECT 'system:tenant', '绑定租户用户', 'system:tenantUser:bind', 9, '绑定现有用户到租户' UNION ALL
  SELECT 'system:tenant', '解绑租户用户', 'system:tenantUser:unbind', 10, '解绑租户用户' UNION ALL
  SELECT 'system:tenant', '未绑定用户列表', 'system:tenantUser:unboundUsers', 11, '获取租户未绑定的用户列表' UNION ALL
  -- 文件管理 system:file
  SELECT 'system:file', '文件上传', 'system:file:upload', 1, '文件上传' UNION ALL
  SELECT 'system:file', '分片上传', 'system:file:chunk', 2, '分片上传' UNION ALL
  -- 操作日志 system:oplog
  SELECT 'system:oplog', '日志分页', 'system:oplog:page', 1, '操作日志分页列表' UNION ALL
  SELECT 'system:oplog', '日志详情', 'system:oplog:detail', 2, '操作日志详情' UNION ALL
  SELECT 'system:oplog', '删除日志', 'system:oplog:delete', 3, '批量删除操作日志' UNION ALL
  -- 下拉选项 system:options
  SELECT 'system:options', '用户选项', 'system:options:user', 1, '获取用户选项列表' UNION ALL
  SELECT 'system:options', '角色选项', 'system:options:role', 2, '获取角色选项列表' UNION ALL
  SELECT 'system:options', '部门选项', 'system:options:dept', 3, '获取部门选项树' UNION ALL
  SELECT 'system:options', '菜单选项', 'system:options:menu', 4, '获取菜单选项树' UNION ALL
  SELECT 'system:options', '权限选项', 'system:options:permission', 5, '获取权限选项树' UNION ALL
  SELECT 'system:options', '岗位选项', 'system:options:post', 6, '获取岗位选项列表'
) t;

-- ----------------------------
-- Seed: oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` (`tenant_id`, `name`, `client_id`, `client_secret`, `resource_ids`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `create_time`, `create_user_id`, `create_user`, `update_time`, `update_user_id`, `update_user`, `autoapprove`)
VALUES
(0, '系统默认客户端', 'super', 'a9b1463d8ea65f0620e26e60b4ad6c9a', '', 'all', 'authorization_code,password,client_credentials,implicit,refresh_token,wechat,admin_password,phone', 'http://www.baidu.com', '', 86400, 172800, NULL, NOW(), 0, 'system', NOW(), 0, 'system', 'true'),
(0, '微信小程序登录', 'wechat_mini', '14253f4fa3297140dfa241f5159066de', '', 'all', 'wechat', '', '', 86400, 172800, NULL, NOW(), 0, 'system', NOW(), 0, 'system', 'true');

-- ----------------------------
-- Seed: sys_user_role (admin 用户 -> admin 角色, 全部用子查询解析)
-- ----------------------------
INSERT INTO `sys_user_role` (`tenant_id`, `user_id`, `role_id`, `create_time`)
SELECT 0, u.id, r.id, NOW()
FROM `sys_user` u, `sys_role` r
WHERE u.`account` = 'admin' AND r.`key` = 'admin';

-- ----------------------------
-- Seed: sys_role_menu (admin 角色拥有全部已启用菜单, CROSS JOIN 子查询)
-- ----------------------------
INSERT INTO `sys_role_menu` (`tenant_id`, `role_id`, `menu_id`, `create_time`)
SELECT 0, r.id, m.id, NOW()
FROM `sys_role` r, `sys_menu` m
WHERE r.`key` = 'admin' AND m.`deleted` = 0;

-- ----------------------------
-- Seed: sys_role_permission (admin 角色拥有全部权限, CROSS JOIN 子查询)
-- ----------------------------
INSERT INTO `sys_role_permission` (`tenant_id`, `role_id`, `permission_id`, `create_time`)
SELECT 0, r.id, p.id, NOW()
FROM `sys_role` r, `sys_permission` p
WHERE r.`key` = 'admin' AND p.`deleted` = 0;

-- ----------------------------
-- Seed: sys_role_dept (admin 角色绑定根部门)
-- ----------------------------
INSERT INTO `sys_role_dept` (`tenant_id`, `role_id`, `dept_id`, `create_time`)
SELECT 0, r.id, d.id, NOW()
FROM `sys_role` r, `sys_dept` d
WHERE r.`key` = 'admin' AND d.`code` = 'ROOT' AND d.`tenant_id` = 0;

-- ----------------------------
-- Seed: sys_tenant_user (默认租户 -> admin 用户, 全子查询解析)
-- ----------------------------
INSERT INTO `sys_tenant_user` (`tenant_id`, `user_id`, `is_tenant_admin`, `status`, `create_time`, `update_time`)
SELECT t.id, u.id, 1, 1, NOW(), NOW()
FROM `sys_tenant` t, `sys_user` u
WHERE t.`code` = 'default' AND u.`account` = 'admin';

-- ----------------------------
-- Seed: sys_tenant_menu (默认租户开放全部菜单)
-- ----------------------------
INSERT INTO `sys_tenant_menu` (`tenant_id`, `menu_id`, `create_time`, `update_time`)
SELECT t.id, m.id, NOW(), NOW()
FROM `sys_tenant` t, `sys_menu` m
WHERE t.`code` = 'default' AND m.`deleted` = 0;

-- ----------------------------
-- Seed: sys_tenant_permission (默认租户开放全部权限)
-- ----------------------------
INSERT INTO `sys_tenant_permission` (`tenant_id`, `permission_id`, `create_time`, `update_time`)
SELECT t.id, p.id, NOW(), NOW()
FROM `sys_tenant` t, `sys_permission` p
WHERE t.`code` = 'default' AND p.`deleted` = 0;

-- ----------------------------
-- Seed: sys_role_project (admin 角色绑定默认 system 项目)
-- ----------------------------
INSERT INTO `sys_role_project` (`role_id`, `tenant_id`, `project_code`, `create_time`)
SELECT r.id, 0, p.`code`, NOW()
FROM `sys_role` r, `sys_project` p
WHERE r.`key` = 'admin' AND p.`code` = 'system' AND p.`deleted` = 0;

-- ----------------------------
-- Seed: sys_tenant_project (默认租户绑定默认 system 项目)
-- ----------------------------
INSERT INTO `sys_tenant_project` (`project_code`, `tenant_id`, `create_time`, `update_time`)
SELECT p.`code`, t.id, NOW(), NOW()
FROM `sys_tenant` t, `sys_project` p
WHERE t.`code` = 'default' AND p.`code` = 'system' AND p.`deleted` = 0;

SET FOREIGN_KEY_CHECKS = 1;
