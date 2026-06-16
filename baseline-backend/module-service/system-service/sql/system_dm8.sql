DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_config";
CREATE TABLE "HC_COMPETENCE_V2"."sys_config" (
                                                 "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT 'ID',
                                                 "name" VARCHAR(100) NOT NULL DEFAULT '' COMMENT '配置名称 | [dto,vo, filter]',
                                                 "config_key" VARCHAR(100) NOT NULL DEFAULT '' COMMENT '配置键名 | [dto,vo,filter]',
                                                 "config_value" VARCHAR(500) NOT NULL DEFAULT '' COMMENT '配置键值 | [dto,vo]',
                                                 "create_time" TIMESTAMP NOT NULL COMMENT '创建时间',
                                                 "update_time" TIMESTAMP NOT NULL COMMENT '更新时间',
                                                 "remark" VARCHAR(500) DEFAULT NULL COMMENT '备注',
                                                 PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_config" is '系统配置';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_dept";
CREATE TABLE "HC_COMPETENCE_V2"."sys_dept" (
                                               "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT 'ID',
                                               "tenant_id" BIGINT NOT NULL DEFAULT 0 COMMENT '租户 ID | [dto,vo]',
                                               "parent_id" BIGINT NOT NULL DEFAULT 0 COMMENT '父部门id | [dto,vo]',
                                               "parent_path" VARCHAR(500) NOT NULL DEFAULT '' COMMENT '父路径',
                                               "name" VARCHAR(30) NOT NULL DEFAULT '' COMMENT '部门名称 | [dto,vo,filter]',
                                               "code" VARCHAR(255) NOT NULL DEFAULT '' COMMENT '部门编码',
                                               "sort_no" NUMBER(11) NOT NULL DEFAULT 0 COMMENT '显示顺序 | [dto,vo]',
                                               "leader" VARCHAR(20) NOT NULL DEFAULT '' COMMENT '负责人 | [dto,vo]',
                                               "phone" VARCHAR(11) NOT NULL DEFAULT '' COMMENT '联系电话 | [dto,vo]',
                                               "email" VARCHAR(50) NOT NULL DEFAULT '' COMMENT '邮箱 | [dto,vo]',
                                               "status" NUMBER(4) NOT NULL DEFAULT 0 COMMENT '状态 | [dto,vo,filter] | enum{ENABLE(1,"启用"),DISABLE(0,"禁用")}',
                                               "deleted" NUMBER(4) NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
                                               "level" NUMBER(11) NOT NULL DEFAULT 0 COMMENT '树层级',
                                               "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                                               "update_time" TIMESTAMP DEFAULT NULL COMMENT '更新时间',
                                               "remark" VARCHAR(500) NOT NULL DEFAULT '' COMMENT '备注',
                                               PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_dept" is '部门表[tree]';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_dict_data";
CREATE TABLE "HC_COMPETENCE_V2"."sys_dict_data" (
                                                    "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '字典ID',
                                                    "sort_no" NUMBER(4) NOT NULL DEFAULT 0 COMMENT '字典排序',
                                                    "label" VARCHAR(100) NOT NULL DEFAULT '' COMMENT '字典标签',
                                                    "value" VARCHAR(100) NOT NULL DEFAULT '' COMMENT '字典键值',
                                                    "code" VARCHAR(100) NOT NULL DEFAULT '' COMMENT '字典类型编码',
                                                    "is_default" NUMBER(1) NOT NULL DEFAULT 0 COMMENT '是否默认（Y是 N否）',
                                                    "status" NUMBER(4) NOT NULL DEFAULT 1 COMMENT '状态enum{ENABLE(1,"正常"),DISABLE(0,"禁用")}',
                                                    "create_time" TIMESTAMP NOT NULL COMMENT '创建时间',
                                                    "update_time" TIMESTAMP NOT NULL COMMENT '更新时间',
                                                    "remark" VARCHAR(500) DEFAULT '' COMMENT '备注',
                                                    "deleted" NUMBER(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                                    PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_dict_data" is '字典数据表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_dict_type";
CREATE TABLE "HC_COMPETENCE_V2"."sys_dict_type" (
                                                    "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '字典主键',
                                                    "name" VARCHAR(100) NOT NULL DEFAULT '' COMMENT '字典名称',
                                                    "code" VARCHAR(100) NOT NULL DEFAULT '' COMMENT '字典代码',
                                                    "status" NUMBER(4) NOT NULL DEFAULT 1 COMMENT '状态enum{ENABLE(1,"正常"),DISABLE(0,"禁用")}',
                                                    "create_time" TIMESTAMP NOT NULL COMMENT '创建时间',
                                                    "update_time" TIMESTAMP NOT NULL COMMENT '更新时间',
                                                    "remark" VARCHAR(500) DEFAULT '' COMMENT '备注',
                                                    "deleted" NUMBER(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                                    PRIMARY KEY ("id"),
                                                    UNIQUE ("code")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_dict_type" is '字典类型表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_job";
CREATE TABLE "HC_COMPETENCE_V2"."sys_job" (
                                              "job_id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '任务ID',
                                              "job_name" VARCHAR(64) NOT NULL DEFAULT '' COMMENT '任务名称',
                                              "job_group" VARCHAR(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
                                              "invoke_target" VARCHAR(500) NOT NULL COMMENT '调用目标字符串',
                                              "cron_expression" VARCHAR(255) DEFAULT '' COMMENT 'cron执行表达式',
                                              "misfire_policy" VARCHAR(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
                                              "concurrent" CHAR(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
                                              "status" CHAR(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
                                              "create_by" VARCHAR(64) DEFAULT '' COMMENT '创建者',
                                              "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                                              "update_by" VARCHAR(64) DEFAULT '' COMMENT '更新者',
                                              "update_time" TIMESTAMP DEFAULT NULL COMMENT '更新时间',
                                              "remark" VARCHAR(500) DEFAULT '' COMMENT '备注信息',
                                              PRIMARY KEY ("job_id", "job_name", "job_group")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_job" is '定时任务调度表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_job_log";
CREATE TABLE "HC_COMPETENCE_V2"."sys_job_log" (
                                                  "job_log_id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '任务日志ID',
                                                  "job_name" VARCHAR(64) NOT NULL COMMENT '任务名称',
                                                  "job_group" VARCHAR(64) NOT NULL COMMENT '任务组名',
                                                  "invoke_target" VARCHAR(500) NOT NULL COMMENT '调用目标字符串',
                                                  "job_message" VARCHAR(500) DEFAULT NULL COMMENT '日志信息',
                                                  "status" CHAR(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
                                                  "exception_info" VARCHAR(2000) DEFAULT '' COMMENT '异常信息',
                                                  "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                                                  PRIMARY KEY ("job_log_id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_job_log" is '定时任务调度日志表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_logininfor";
CREATE TABLE "HC_COMPETENCE_V2"."sys_logininfor" (
                                                     "info_id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '访问ID',
                                                     "tenant_id" BIGINT NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
                                                     "user_name" VARCHAR(50) DEFAULT '' COMMENT '用户账号',
                                                     "ipaddr" VARCHAR(128) DEFAULT '' COMMENT '登录IP地址',
                                                     "status" CHAR(1) DEFAULT '0' COMMENT '登录状态（1成功 0失败）',
                                                     "msg" VARCHAR(255) DEFAULT '' COMMENT '提示信息',
                                                     "access_time" TIMESTAMP DEFAULT NULL COMMENT '访问时间',
                                                     PRIMARY KEY ("info_id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_logininfor" is '系统访问记录';

CREATE INDEX idx_sys_logininfor_s ON "HC_COMPETENCE_V2"."sys_logininfor" ("status");
CREATE INDEX idx_sys_logininfor_lt ON "HC_COMPETENCE_V2"."sys_logininfor" ("access_time");



DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_menu";
CREATE TABLE "HC_COMPETENCE_V2"."sys_menu" (
                                               "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '菜单ID',
                                               "name" VARCHAR(50) NOT NULL COMMENT '菜单名称',
                                               "parent_id" BIGINT DEFAULT '0' COMMENT '父菜单ID',
                                               "sort_no" NUMBER(4) DEFAULT '0' COMMENT '显示顺序',
                                               "path_type" VARCHAR(32) NOT NULL DEFAULT '' COMMENT '路由类型',
                                               "path" VARCHAR(200) DEFAULT '#' COMMENT '路由地址',
                                               "component" VARCHAR(255) DEFAULT NULL COMMENT '组件路径',
                                               "parameter" VARCHAR(255) DEFAULT NULL COMMENT '路由参数',
                                               "target" VARCHAR(20) DEFAULT '' COMMENT '打开方式（menuItem页签 menuBlank新窗口）',
                                               "type" CHAR(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
                                               "visible" NUMBER(1) DEFAULT '0' COMMENT '显示状态（0隐藏 1显示）',
                                               "status" NUMBER(4) NOT NULL DEFAULT '0' COMMENT '菜单状态（0停用 1启用）',
                                               "cache" NUMBER(1) DEFAULT NULL COMMENT '是否缓存（0不缓存 1缓存）',
                                               "chain" NUMBER(1) DEFAULT NULL COMMENT '是否外链（0否 1是）',
                                               "key" VARCHAR(100) DEFAULT NULL COMMENT '权限标识',
                                               "icon" VARCHAR(100) DEFAULT '#' COMMENT '菜单图标',
                                               "level" NUMBER(5) DEFAULT NULL COMMENT '树层级',
                                               "category_type" VARCHAR(100) NOT NULL DEFAULT '' COMMENT '分组标识 | [dto,vo,filter]',
                                               "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                                               "update_time" TIMESTAMP DEFAULT NULL COMMENT '更新时间',
                                               "remark" VARCHAR(500) DEFAULT '' COMMENT '备注',
                                               "deleted" NUMBER(1) DEFAULT '0' COMMENT '删除标记',
                                               "parent_path" VARCHAR(500) DEFAULT NULL COMMENT '父路径',
                                               PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_menu" is '菜单表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_oplog";
CREATE TABLE "HC_COMPETENCE_V2"."sys_oplog" (
                                                "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '日志主键',
                                                "tenant_id" BIGINT NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
                                                "title" VARCHAR(50) DEFAULT '' COMMENT '模块标题',
                                                "description" VARCHAR(512) DEFAULT '' COMMENT '操作描述',
                                                "method" VARCHAR(100) DEFAULT '' COMMENT '方法名称class#method',
                                                "operator_id" BIGINT DEFAULT NULL COMMENT '操作人员ID',
                                                "operator" VARCHAR(50) DEFAULT '' COMMENT '操作人姓名',
                                                "url" VARCHAR(255) DEFAULT '' COMMENT '请求URL',
                                                "ip" VARCHAR(128) DEFAULT '' COMMENT '主机地址',
                                                "params" VARCHAR(2000) DEFAULT NULL COMMENT '请求参数',
                                                "result" VARCHAR(2000) DEFAULT NULL COMMENT '响应参数',
                                                "status" NUMBER(4) DEFAULT '0' COMMENT '操作状态 enum{SUCCESS(1,"成功"),FAIL(0,"失败")}',
                                                "error_msg" VARCHAR(2000) DEFAULT '' COMMENT '错误消息',
                                                "create_time" TIMESTAMP DEFAULT NULL COMMENT '操作时间',
                                                "cost_time" BIGINT DEFAULT '0' COMMENT '消耗时间',
                                                PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_oplog" is '操作日志记录';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_permission";
CREATE TABLE "HC_COMPETENCE_V2"."sys_permission" (
                                                     "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '权限ID | [dto,vo]',
                                                     "parent_id" BIGINT DEFAULT '0' COMMENT '父ID',
                                                     "name" VARCHAR(50) NOT NULL DEFAULT '' COMMENT '权限名称 | [dto,vo,filter]',
                                                     "permission" VARCHAR(100) NOT NULL DEFAULT '' COMMENT '权限标识 | [dto,vo,filter]',
                                                     "sort_no" NUMBER(4) NOT NULL DEFAULT '0' COMMENT '显示顺序',
                                                     "category_type" VARCHAR(100) NOT NULL DEFAULT '' COMMENT '分组标识 | [dto,vo,filter]',
                                                     "level" NUMBER(5) DEFAULT '0' COMMENT '树层级',
                                                     "deleted" NUMBER(1) DEFAULT '0' COMMENT '删除标记',
                                                     "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                                                     "update_time" TIMESTAMP DEFAULT NULL COMMENT '更新时间',
                                                     "remark" VARCHAR(500) DEFAULT NULL COMMENT '备注',
                                                     PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_permission" is '权限表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_post";
CREATE TABLE "HC_COMPETENCE_V2"."sys_post" (
                                               "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '岗位ID | [dto,vo]',
                                               "tenant_id" BIGINT NOT NULL DEFAULT '0' COMMENT '岗位 ID',
                                               "code" VARCHAR(64) NOT NULL DEFAULT '' COMMENT '岗位编码 | [dto,vo,filter]',
                                               "name" VARCHAR(50) NOT NULL DEFAULT '' COMMENT '岗位名称 | [dto,vo,filter]',
                                               "sort_no" NUMBER(4) NOT NULL DEFAULT '0' COMMENT '显示顺序 | [dto,vo]',
                                               "status" NUMBER(4) NOT NULL DEFAULT '0' COMMENT '状态 | [dto,vo,filter] | enum{ENABLE(1,"启用"),DISABLE(0,"禁用")}',
                                               "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                                               "update_time" TIMESTAMP DEFAULT NULL COMMENT '更新时间',
                                               "deleted" NUMBER(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
                                               "remark" VARCHAR(500) NOT NULL DEFAULT '' COMMENT '备注',
                                               PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_post" is '岗位信息表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_role";
CREATE TABLE "HC_COMPETENCE_V2"."sys_role" (
                                               "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '角色ID',
                                               "tenant_id" BIGINT NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
                                               "name" VARCHAR(30) NOT NULL DEFAULT '' COMMENT '角色名称',
                                               "key" VARCHAR(100) NOT NULL COMMENT '角色权限字符串',
                                               "data_scope" NUMBER(4) NOT NULL DEFAULT '0' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
                                               "status" NUMBER(4) NOT NULL DEFAULT '1' COMMENT '角色状态 enum{ENABLE(1,"正常"),DISABLE(0,"禁用")}',
                                               "deleted" NUMBER(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
                                               "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间 [filter,vo,dto]',
                                               "update_time" TIMESTAMP DEFAULT NULL COMMENT '更新时间',
                                               "remark" VARCHAR(500) DEFAULT NULL COMMENT '备注',
                                               PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_role" is '角色信息表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_role_dept";
CREATE TABLE "HC_COMPETENCE_V2"."sys_role_dept" (
                                                    "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT 'ID',
                                                    "tenant_id" BIGINT NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
                                                    "role_id" BIGINT NOT NULL DEFAULT '0' COMMENT '角色ID',
                                                    "dept_id" BIGINT NOT NULL DEFAULT '0' COMMENT '部门ID',
                                                    "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                                                    PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_role_dept" is '角色和部门关联表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_role_menu";
CREATE TABLE "HC_COMPETENCE_V2"."sys_role_menu" (
                                                    "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT 'ID',
                                                    "tenant_id" BIGINT NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
                                                    "role_id" BIGINT NOT NULL COMMENT '角色ID',
                                                    "menu_id" BIGINT NOT NULL COMMENT '菜单ID',
                                                    "create_time" TIMESTAMP NOT NULL COMMENT '创建时间',
                                                    PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_role_menu" is '角色和菜单关联表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_role_permission";
CREATE TABLE "HC_COMPETENCE_V2"."sys_role_permission" (
                                                          "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT 'ID',
                                                          "role_id" BIGINT NOT NULL COMMENT '角色ID',
                                                          "tenant_id" BIGINT NOT NULL DEFAULT '0' COMMENT '租户ID',
                                                          "permission_id" BIGINT NOT NULL COMMENT '权限ID',
                                                          "create_time" TIMESTAMP NOT NULL COMMENT '创建时间',
                                                          PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_role_permission" is '角色和权限关联表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_tenant";
CREATE TABLE "HC_COMPETENCE_V2"."sys_tenant" (
                                                 "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '租户ID | [dto,vo]',
                                                 "name" VARCHAR(255) NOT NULL COMMENT '租户名称 | [dto,vo,filter]',
                                                 "status" NUMBER(4) NOT NULL DEFAULT '1' COMMENT '状态 | [dto,vo,filter] | enum{ENABLE(1,"启用"),DISABLE(0,"禁用")}',
                                                 "deleted" NUMBER(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
                                                 "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                                                 "update_time" TIMESTAMP DEFAULT NULL COMMENT '更新时间',
                                                 "remark" VARCHAR(500) DEFAULT NULL COMMENT '备注',
                                                 PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_tenant" is '租户表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_tenant_menu";
CREATE TABLE "HC_COMPETENCE_V2"."sys_tenant_menu" (
                                                      "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '主键',
                                                      "menu_id" BIGINT NOT NULL DEFAULT '0' COMMENT '菜单ID',
                                                      "tenant_id" BIGINT NOT NULL DEFAULT '0' COMMENT '租户ID',
                                                      "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                                                      "update_time" TIMESTAMP DEFAULT NULL COMMENT '更新时间',
                                                      PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_tenant_menu" is '租户菜单表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_tenant_permission";
CREATE TABLE "HC_COMPETENCE_V2"."sys_tenant_permission" (
                                                            "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '主键',
                                                            "permission_id" BIGINT NOT NULL DEFAULT '0' COMMENT '权限ID',
                                                            "tenant_id" BIGINT NOT NULL DEFAULT '0' COMMENT '租户ID',
                                                            "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                                                            "update_time" TIMESTAMP DEFAULT NULL COMMENT '更新时间',
                                                            PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_tenant_permission" is '租户权限表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_tenant_user";
CREATE TABLE "HC_COMPETENCE_V2"."sys_tenant_user" (
                                                      "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '主键 | [dto,vo]',
                                                      "tenant_id" BIGINT NOT NULL DEFAULT '0' COMMENT '租户ID | [dto,vo,filter]',
                                                      "user_id" BIGINT NOT NULL DEFAULT '0' COMMENT '用户ID | [dto,vo,filter]',
                                                      "is_tenant_admin" NUMBER(1) DEFAULT '0' COMMENT '租户管理员| [dto,vo,filter] | enum{ADMIN(1,"租户管理员"),GENERAL(0,"普通用户")}',
                                                      "status" NUMBER(4) NOT NULL DEFAULT '1' COMMENT '状态（0停用, 1正常）',
                                                      "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                                                      "update_time" TIMESTAMP DEFAULT NULL COMMENT '更新时间',
                                                      PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_tenant_user" is '租户用户表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_user";
CREATE TABLE "HC_COMPETENCE_V2"."sys_user" (
                                               "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT '用户ID',
                                               "tenant_id" BIGINT NOT NULL DEFAULT '0' COMMENT '租户 ID',
                                               "dept_id" BIGINT NOT NULL DEFAULT '0' COMMENT '部门 ID',
                                               "account" VARCHAR(30) NOT NULL COMMENT '登录账号',
                                               "password" VARCHAR(255) DEFAULT '' COMMENT '密码',
                                               "salt" VARCHAR(20) DEFAULT '' COMMENT '盐加密',
                                               "name" VARCHAR(30) DEFAULT '' COMMENT '用户昵称',
                                               "email" VARCHAR(50) DEFAULT '' COMMENT '用户邮箱',
                                               "phone" VARCHAR(11) DEFAULT '' COMMENT '手机号码',
                                               "sex" CHAR(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
                                               "avatar" VARCHAR(100) DEFAULT '' COMMENT '头像路径',
                                               "status" NUMBER(4) DEFAULT '1' COMMENT '帐号状态 enum{ENABLE(1,"正常"),DISABLE(0,"禁用")}',
                                               "deleted" NUMBER(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
                                               "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                                               "update_time" TIMESTAMP DEFAULT NULL COMMENT '更新时间',
                                               "remark" VARCHAR(500) DEFAULT NULL COMMENT '备注',
                                               "identity" VARCHAR(50) NOT NULL DEFAULT '' COMMENT '身份',
                                               "type" NUMBER(4) NOT NULL DEFAULT '1' COMMENT '帐号类型 | enum{UNKNOWN(0,"未知"),ADMIN(1,"系统管理员"),WECHAT(2,"微信")}',
                                               "initialize_password_change" NUMBER(4) NOT NULL DEFAULT '0' COMMENT '初始化密码是否修改',
                                               PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_user" is '用户信息表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_user_post";
CREATE TABLE "HC_COMPETENCE_V2"."sys_user_post" (
                                                    "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT 'ID',
                                                    "tenant_id" BIGINT NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
                                                    "user_id" BIGINT NOT NULL COMMENT '用户ID',
                                                    "post_id" BIGINT NOT NULL COMMENT '岗位ID',
                                                    "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                                                    PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_user_post" is '用户与岗位关联表';


DROP TABLE IF EXISTS "HC_COMPETENCE_V2"."sys_user_role";
CREATE TABLE "HC_COMPETENCE_V2"."sys_user_role" (
                                                    "id" BIGINT AUTO_INCREMENT NOT NULL COMMENT 'ID',
                                                    "tenant_id" BIGINT NOT NULL DEFAULT '0' COMMENT '租户 ID | [dto,vo]',
                                                    "user_id" BIGINT NOT NULL COMMENT '用户ID',
                                                    "role_id" BIGINT NOT NULL COMMENT '角色ID',
                                                    "create_time" TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                                                    PRIMARY KEY ("id")
);
COMMENT ON TABLE "HC_COMPETENCE_V2"."sys_user_role" is '用户和角色关联表';


INSERT INTO "HC_COMPETENCE_V2"."sys_user" ("id", "tenant_id", "dept_id", "account", "password", "salt", "name", "email", "phone", "sex", "avatar", "status", "deleted", "create_time", "update_time", "remark", "identity", "type", "initialize_password_change") VALUES (1, 0, 1, 'admin', '$2a$10$d7EegjNv76WG5GGmYT56heLo9UfmWCP73Yh04b.yRycnW3imzL2be', 'x0jdch', '管理员', '', '13500000000', '0', '/20240926/2da4b1e378c74bbcbeb35c4b9ec733e1.png', 1, 0, '2023-01-29 11:31:48', '2024-10-12 17:52:04', NULL, '', 1, 1);


INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615287751081570205, '修改密码', 1615287751081570305, 600, 'views', '/changePassword', 'system/page/userCenter/changePassword', 'changePassword', '', 'C', 0, 1, 1, 0, 'system', '', 2, '0,1', '2023-01-20 16:16:03', '2024-06-25 17:24:13', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615287751081570206, '个人中心', 1615287751081570305, 500, 'views', '/userCenter', 'system/page/userCenter/index', 'userCenter', '', 'C', 0, 1, 1, 0, 'system', 'el-icon-avatar', 2, '0,1', '2023-01-18 15:07:44', '2024-06-25 17:22:33', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615287751081570305, '配置', 0, 400, 'views', '/setting', '', '', '', 'M', 1, 1, 1, 0, 'system', 'el-icon-setting', 1, '0,1', '2023-01-17 17:59:45', '2024-01-18 10:40:29', '', 0, '0');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615289089999876097, '首页', 0, 100, 'views', '/home', 'common/home/index', 'home', '', 'C', 1, 1, 1, 0, 'system:home', 'el-icon-s-home', 1, '0,1', '2023-01-17 18:05:04', '2024-10-10 14:22:42', '', 0, '0');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615293280910684161, '用户管理', 1615287751081570305, 2, 'views', '/user', 'system/page/user/index', 'user', '', 'C', 1, 1, 1, 0, 'system:user', 'el-icon-user-solid', 2, '0,1', '2023-01-17 18:21:44', '2024-10-10 14:24:38', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615294535687057410, '菜单管理', 1615287751081570305, 3, 'views', '/menu', 'system/page/menu/index', '', '', 'C', 1, 1, 1, 0, 'system:menu', 'el-icon-s-grid', 2, '0', '2023-01-17 18:26:43', '2024-10-10 14:26:19', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615295079587622913, '角色管理', 1615287751081570305, 4, 'views', '/role', 'system/page/role/index', '', '', 'C', 1, 1, 1, 0, 'system:role', 'el-icon-s-custom', 2, '0,1', '2023-01-17 18:28:52', '2024-10-10 14:27:03', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615525605078577153, '菜单编辑', 1615294535687057410, 20, 'views', '/addMenu', 'system/page/menu/save', '/addMenu', '', 'C', 0, 1, 1, 0, 'system:menuSave', 'el-icon-fold', 3, '0', '2023-01-18 09:44:54', '2023-02-06 15:24:55', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615526329959165953, '角色编辑', 1615295079587622913, 1, 'views', '/addRoles', 'system/page/role/save', '/addRoles', '', 'C', 0, 1, 1, 0, 'system:role', '', 3, '0,1', '2023-01-18 09:47:47', '2024-06-25 17:23:11', '', 0, '0,1615287751081570305,1615295079587622913');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615536665256898561, '用户编辑', 1615293280910684161, 15, 'views', '/addUser', 'system/page/user/save', '/addUser', '', 'C', 0, 1, 1, 0, 'system:userSave', 'el-icon-custom', 3, '0,1', '2023-01-18 10:28:51', '2024-06-25 17:20:15', '', 0, '0,1615287751081570305,1615293280910684161');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615537961389109250, '系统日志', 1615287751081570305, 6, 'views', '/log', 'system/page/log/index', '/setting/log', '', 'C', 1, 1, 1, 0, 'system:log', 'el-icon-warning', 2, '0,1', '2023-01-18 10:34:00', '2024-06-25 17:21:40', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615538703218880513, '字典管理', 1615287751081570305, 5, 'views', '/dic', 'system/page/dic/index', '/setting/dic', '', 'C', 1, 1, 1, 0, 'system:dic', 'el-icon-document', 2, '0', '2023-01-18 10:36:57', '2024-06-25 17:21:32', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615539985480527873, '系统设置', 1615287751081570305, 1, 'views', '/setting/system', 'system/page/system/index', 'setting/system', '', 'C', 1, 1, 1, 0, 'system:system', 'el-icon-setting', 2, '0', '2023-01-18 10:42:02', '2024-10-11 14:58:07', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615880143845298177, '添加', 1615293280910684161, 1, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:user.add', '', 3, '0,1', '2023-01-19 09:13:43', '2023-01-20 09:51:31', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615880425778024449, '编辑', 1615293280910684161, 2, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:user.edit', '', 3, '0,1', '2023-01-19 09:14:50', '2023-01-20 09:51:45', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1615880663335014402, '删除', 1615293280910684161, 3, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:user.delete', '', 3, '0,1', '2023-01-19 09:15:46', '2023-01-20 09:52:05', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1619182354989887490, '添加', 1615294535687057410, 1, 'views', '', '', '', '', 'F', 0, 1, 1, 0, 'system:menu.add', '', 3, '0', '2023-01-28 11:55:31', '2023-02-06 15:12:21', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1619276450832179201, '修改密码', 1615293280910684161, 4, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:user.changePassword', '', 3, '0,1', '2023-01-28 18:09:25', '2023-01-28 18:09:47', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622493963518783489, '编辑', 1615294535687057410, 5, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:menu.edit', '', 3, '0', '2023-02-06 15:14:40', '2023-02-06 15:14:40', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622494560359854081, '用户详情', 1615293280910684161, 10, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:user.detail', '', 3, '0,1', '2023-02-06 15:17:02', '2023-02-06 15:17:02', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622495739865247745, '删除', 1615294535687057410, 10, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:menu.delete', '', 3, '0', '2023-02-06 15:21:44', '2023-02-06 15:21:44', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622495829472358402, '详情', 1615294535687057410, 15, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:menu.detail', '', 3, '0', '2023-02-06 15:22:05', '2023-02-06 15:22:05', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622499523941150722, '添加', 1615295079587622913, 5, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:role.add', '', 3, '0,1', '2023-02-06 15:36:46', '2023-02-06 15:38:57', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622499736399425538, '编辑', 1615295079587622913, 10, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:role.edit', '', 3, '0,1', '2023-02-06 15:37:36', '2023-02-06 15:39:50', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622499858394951681, '删除', 1615295079587622913, 15, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:role.delete', '', 3, '0,1', '2023-02-06 15:38:05', '2023-02-06 15:38:05', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622499961981677570, '详情', 1615295079587622913, 20, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:role.detail', '', 3, '0,1', '2023-02-06 15:38:30', '2023-02-06 15:38:30', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622546098054877185, 'add', 1615538703218880513, 1, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:dic.add', '', 3, '0', '2023-02-06 18:41:50', '2023-02-06 18:41:50', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622546257912385537, 'edit', 1615538703218880513, 5, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:dic.edit', '', 3, '0', '2023-02-06 18:42:28', '2023-02-06 18:42:28', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622546405941956609, 'delete', 1615538703218880513, 10, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:dic.delete', '', 3, '0', '2023-02-06 18:43:03', '2023-02-06 18:43:03', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622546774902296577, 'addType', 1615538703218880513, 15, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:dic.addType', '', 3, '0', '2023-02-06 18:44:31', '2023-02-06 18:44:31', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622546860046667777, 'editType', 1615538703218880513, 20, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:dic.editType', '', 3, '0', '2023-02-06 18:44:52', '2023-02-06 18:44:52', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622546990682460161, 'deleteType', 1615538703218880513, 20, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:dic.deleteType', '', 3, '0', '2023-02-06 18:45:23', '2023-02-06 18:45:23', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622555275506495489, 'delete', 1615537961389109250, 1, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:log.delete', '', 3, '0,1', '2023-02-06 19:18:18', '2023-02-06 19:18:18', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1622555336604921857, 'detail', 1615537961389109250, 5, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:log.detail', '', 3, '0,1', '2023-02-06 19:18:33', '2023-02-06 19:18:33', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1719274274148507650, '岗位管理', 1615287751081570305, 3, 'views', '/post', 'system/page/post/list', '', '', 'C', 1, 1, 1, 0, 'system:post', 'el-icon-user', 2, '0,1', '2023-10-31 16:45:04', '2024-06-25 17:20:48', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1719279342595829762, '组织架构', 1615287751081570305, 4, 'views', '/dept', 'system/page/dept/list', '', '', 'C', 1, 1, 1, 0, 'system:dept', 'el-icon-menu', 2, '0,1', '2023-10-31 17:05:13', '2024-10-10 14:29:26', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1731962820017717250, '权限列表', 1615287751081570305, 2, 'views', '/permission', 'system/page/sysPermission/list', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-notebook-2', 2, '0', '2023-12-05 17:04:49', '2024-10-10 14:25:38', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1732661389724930049, '租户管理', 1615287751081570305, 11, 'views', '/tenant', 'system/page/sysTenant/list', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-user-solid', 2, '0', '2023-12-07 15:20:41', '2024-10-10 14:31:44', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1732667161796071426, '租户用户', 1615287751081570305, 3, 'views', '/tenantUser', 'system/page/sysTenantUser/list', '', '', 'C', 0, 1, 1, 0, 'system', 'el-icon-user-filled', 2, '0', '2023-12-07 15:43:37', '2024-06-25 17:25:11', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1732948557743718402, '编辑租户菜单', 1615287751081570305, 1, 'views', '/addTenantMenu', 'system/page/sysTenantMenu/save', '', '', 'C', 0, 1, 1, 0, 'system', 'el-icon-expand', 2, '0', '2023-12-08 10:21:47', '2024-06-25 17:28:26', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228673, '编辑租户权限', 1615287751081570305, 2, 'views', '/addTenantPermission', 'system/page/sysTenantPermission/save', '', '', 'C', 0, 1, 1, 0, 'system', 'el-icon-user-filled', 2, '0', '2023-12-08 15:09:10', '2024-06-25 17:28:37', '', 0, '0,1615287751081570305');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228674, '添加', 1719274274148507650, 5, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:post.add', '', 3, '0,1', '2023-02-06 15:36:46', '2023-02-06 15:38:57', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228675, '编辑', 1719274274148507650, 10, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:post.edit', '', 3, '0,1', '2023-02-06 15:37:36', '2023-02-06 15:39:50', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228676, '删除', 1719274274148507650, 15, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:post.delete', '', 3, '0,1', '2023-02-06 15:38:05', '2023-02-06 15:38:05', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228677, '详情', 1719274274148507650, 20, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:post.detail', '', 3, '0,1', '2023-02-06 15:38:30', '2023-02-06 15:38:30', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228678, '添加', 1731962820017717250, 5, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:permission.add', '', 3, '0', '2023-02-06 15:36:46', '2023-02-06 15:38:57', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228679, '编辑', 1731962820017717250, 10, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:permission.edit', '', 3, '0', '2023-02-06 15:37:36', '2023-02-06 15:39:50', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228680, '删除', 1731962820017717250, 15, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:permission.delete', '', 3, '0', '2023-02-06 15:38:05', '2023-02-06 15:38:05', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228681, '详情', 1731962820017717250, 20, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:permission.detail', '', 3, '0', '2023-02-06 15:38:30', '2023-02-06 15:38:30', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228682, '添加', 1719279342595829762, 5, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:dept.add', '', 3, '0,1', '2023-02-06 15:36:46', '2023-02-06 15:38:57', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228683, '编辑', 1719279342595829762, 10, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:dept.edit', '', 3, '0,1', '2023-02-06 15:37:36', '2023-02-06 15:39:50', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228684, '删除', 1719279342595829762, 15, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:dept.delete', '', 3, '0,1', '2023-02-06 15:38:05', '2023-02-06 15:38:05', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228685, '详情', 1719279342595829762, 20, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:dept.detail', '', 3, '0,1', '2023-02-06 15:38:30', '2023-02-06 15:38:30', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228690, '添加', 1732661389724930049, 5, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:tenant.add', '', 3, '0', '2023-02-06 15:36:46', '2023-02-06 15:38:57', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228691, '编辑', 1732661389724930049, 10, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:tenant.edit', '', 3, '0', '2023-02-06 15:37:36', '2023-02-06 15:39:50', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228692, '删除', 1732661389724930049, 15, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:tenant.delete', '', 3, '0', '2023-02-06 15:38:05', '2023-02-06 15:38:05', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228693, '详情', 1732661389724930049, 20, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:tenant.detail', '', 3, '0', '2023-02-06 15:38:30', '2023-02-06 15:38:30', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228694, '添加', 1732667161796071426, 5, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:tenantUser.add', '', 3, '0', '2023-02-06 15:36:46', '2023-02-06 15:38:57', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228695, '编辑', 1732667161796071426, 10, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:tenantUser.edit', '', 3, '0', '2023-02-06 15:37:36', '2023-02-06 15:39:50', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228696, '删除', 1732667161796071426, 15, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:tenantUser.delete', '', 3, '0', '2023-02-06 15:38:05', '2023-02-06 15:38:05', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1733020876147228697, '详情', 1732667161796071426, 20, 'views', '', '', '', '', 'F', 1, 1, 1, 0, 'system:tenantUser.detail', '', 3, '0', '2023-02-06 15:38:30', '2023-02-06 15:38:30', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1744931006212202497, '数采管理', 0, 1000, '', '/iot', '', '', '', 'M', 1, 1, 1, 0, '', 'el-icon-data-analysis', 1, '0', '2024-01-10 11:55:46', '2024-01-18 10:58:48', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1744932589108977666, '设备管理', 1744931006212202497, 1, 'modules', '/device', 'iot/page/device/device', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-printer', 2, '0', '2024-01-10 12:02:03', '2024-01-18 10:56:58', '', 0, 'null1744931006212202497');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1744932589108977667, '驱动管理', 1744931006212202497, 1, 'modules', '/driver', 'iot/page/driver/driver', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-ticket', 2, '0', '2024-01-10 12:02:03', '2024-01-18 10:57:33', '', 0, 'null1744931006212202497');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1744932589108977668, '物模管理', 1744931006212202497, 1, 'modules', '/profile', 'iot/page/profile/profile', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-shop', 2, '0', '2024-01-10 12:02:03', '2024-01-18 10:57:59', '', 0, 'null1744931006212202497');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1744932589108977669, '数据管理', 1744931006212202497, 1, 'modules', '/pointValue', 'iot/page/point/value/pointValue', '', '', 'C', 0, 1, 1, 0, '', 'el-icon-histogram', 2, '0', '2024-01-10 12:02:03', '2024-02-02 17:02:51', '', 0, 'null1744931006212202497');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1744999474223636482, '模版编辑', 1744932589108977668, 1, 'modules', '/profile/edit', 'iot/page/profile/edit/profileEdit', '', '', 'C', 0, 1, 1, 0, '', 'el-icon-wallet-filled', 3, '0', '2024-01-10 16:27:50', '2024-01-25 07:10:56', '', 0, 'null1744931006212202497,1744932589108977668');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1744999474223636483, '模版详情', 1744932589108977668, 1, 'modules', '/dprofile', 'iot/page/profile/detail', '', '', 'C', 0, 1, 1, 0, 'dprofile', 'el-icon-wallet-filled', 3, '0', '2024-01-10 16:27:50', '2024-10-21 11:40:44', '', 0, 'null1744931006212202497,1744932589108977668');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1744999474223636484, '模板位号', 1744932589108977668, 1, '', '/profile/point', '', '', '', 'M', 0, 1, 1, 0, '', 'el-icon-wallet-filled', 3, '0', '2024-01-10 16:27:50', '2024-01-18 10:58:23', '', 0, 'null1744931006212202497,1744932589108977668');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1744999474223636485, '位号信息', 1744999474223636484, 3, 'modules', '/profile/point/detail', 'iot/page/point/detail/pointDetail', '', '', 'C', 0, 1, 1, 0, '', 'el-icon-operation', 4, '0', '2023-11-10 09:58:37', '2024-01-18 10:58:45', '', 0, 'null1744931006212202497,1744932589108977668,1744999474223636484');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1744999474223636486, '位号编辑', 1744999474223636484, 4, 'modules', '/profile/point/edit', 'iot/page/point/edit/pointEdit', '', '', 'C', 0, 1, 1, 0, '', 'el-icon-pointer', 4, '0', '2023-11-10 10:01:35', '2024-01-18 09:10:51', '', 0, '1744932589108977668,1744999474223636484,1744999474223636484');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1745006847948480514, '驱动详情', 1744932589108977667, 1, 'modules', '/ddriver', 'iot/page/driver/detail', '', '', 'C', 0, 1, 1, 0, 'ddriver', 'el-icon-management', 3, '0', '2024-01-10 16:57:08', '2024-10-21 10:18:02', '', 0, 'null1744931006212202497,1744932589108977667');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1745008685301096450, '设备信息', 1744932589108977666, 1, 'modules', '/ddevice', 'iot/page/device/detail', '', '', 'C', 0, 1, 1, 0, 'ddevice', 'el-icon-document', 3, '0', '2024-01-10 17:04:26', '2024-10-17 16:45:01', '', 0, 'null1744931006212202497,1744932589108977666');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1745009326970888193, '设备编辑', 1744932589108977666, 1, 'modules', '/device/edit', 'iot/page/device/edit/deviceEdit', '', '', 'C', 0, 1, 1, 0, '', 'el-icon-folder-remove', 3, '0', '2024-01-10 17:06:59', '2024-01-25 02:57:18', '', 0, 'null1744931006212202497,1744932589108977666');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1753302912527462401, '数据源管理', 0, 1000, '', '/dataSource', '', '', '', 'M', 1, 1, 1, 0, '', 'el-icon-coin', 1, '', '2024-02-02 14:22:44', '2024-02-02 14:23:03', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1753303584903753729, 'console', 1753302912527462401, 1, 'modules', '/console', 'dataSource/page/console', '', '', 'C', 1, 1, 1, 0, 'console', 'el-icon-crop', 2, '', '2024-02-02 14:25:24', '2024-10-30 15:02:12', '', 0, 'null1753302912527462401');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1753303944896671746, '数据源', 1753302912527462401, 2, 'modules', '/datasource/dataOrigin', 'datasource/page/data/origin/index', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-cellphone', 2, '', '2024-02-02 14:26:50', '2024-06-25 17:16:31', '', 0, 'null1753302912527462401');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1753343821776384002, '设备分组', 1744931006212202497, 2, 'modules', '/group', 'iot/page/group/list', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-grid', 2, '0', '2024-02-02 17:05:17', '2024-02-02 17:05:17', '', 0, 'null1744931006212202497');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1754429122229547010, '模板位号管理', 1744931006212202497, 12, 'modules', '/profile/point/editpoint', 'iot/page/point/card/pointCard', '', '', 'C', 0, 1, 1, 0, '', 'el-icon-brush', 2, '', '2024-02-05 16:57:53', '2024-02-05 18:22:16', '', 1, 'null1744931006212202497');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1754431120224006145, '模板号位详情', 1744931006212202497, 13, 'modules', '/profile/point/editpoint', 'iot/page/point/card/pointCard', '', '', 'C', 0, 1, 1, 0, '', '', 2, '', '2024-02-05 17:05:49', '2024-02-05 18:22:20', '', 1, 'null1744931006212202497');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1759060554061742081, '固件升级', 1744931006212202497, 600, 'modules', '/firmware', 'iot/page/deviceFirmware/list', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-sort', 2, '0', '2024-02-18 11:41:33', '2024-02-18 14:42:05', '', 0, 'null1744931006212202497');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1759105497867714561, '固件升级任务', 1759060554061742081, 1, 'modules', '/firmwareTask', 'iot/page/deviceFirmwareUpgradeTask/list', '', '', 'C', 0, 1, 1, 0, '', 'el-icon-flag', 3, '0', '2024-02-18 14:40:08', '2024-02-18 14:44:19', '', 0, 'null1744931006212202497,1759060554061742081');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1759106287470608385, '升级历史', 1759105497867714561, 1, 'modules', '/firmwareHistory', 'iot/page/deviceFirmwareUpgradeHistory/list', '', '', 'C', 0, 1, 1, 0, '', 'el-icon-data-line', 4, '0', '2024-02-18 14:43:16', '2024-02-18 14:44:36', '', 0, 'null1744931006212202497,1759060554061742081,1759105497867714561');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1760211717546528769, '档案模板', 1744931006212202497, 700, 'modules', '/archive', 'iot/page/archive/list', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-folder-add', 2, '0', '2024-02-21 15:55:51', '2024-02-21 16:13:08', '', 0, 'null1744931006212202497');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1760215942456365058, '档案模板属性', 1744931006212202497, 750, 'modules', '/archiveAttribute', 'iot/page/archiveAttribute/list', '', '', 'C', 0, 1, 1, 0, 'archiveAttribute', 'el-icon-folder', 2, '0', '2024-02-21 16:12:39', '2024-10-16 15:29:10', '', 0, 'null1744931006212202497');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1762722762139615234, '视频纳管', 1762722762139615234, 250, 'views', '/vmanager', '', '', '', 'M', 1, 1, 1, 0, 'vmanager', 'el-icon-camera-filled', 2, '0', '2024-02-28 14:13:51', '2024-10-30 15:08:02', '', 0, 'null1762722762139615234');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1762723275677614082, '控制台', 1851522039536578561, 1, 'modules', '/vmanager/console', 'vmanager/page/console/index', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-trend-charts', 2, '0', '2024-02-28 14:15:53', '2024-10-30 15:11:12', '', 0, 'null1851522039536578561');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1762723594557964290, '分屏监控', 1851522039536578561, 2, 'modules', '/vamanager/screen', 'vmanager/page/screen/index', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-full-screen', 2, '0', '2024-02-28 14:17:09', '2024-10-30 15:10:50', '', 0, 'null1851522039536578561');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1762724591476916225, '分组管理', 1851522039536578561, 3, 'modules', '/vmanager/group', 'vmanager/page/deviceChannelGroup/list', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-discount', 2, '0', '2024-02-28 14:21:07', '2024-10-30 15:11:23', '', 0, 'null1851522039536578561');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1762724869093703682, '国标设备', 1851522039536578561, 4, 'modules', '/vmanager/device', 'vmanager/page/device/list', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-film', 2, '0', '2024-02-28 14:22:13', '2024-10-30 15:11:41', '', 0, 'null1851522039536578561');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1765930209830731778, '规则引擎', 0, 1000, 'views', '/flow', '', '', '', 'M', 1, 1, 1, 0, '', 'el-icon-coin', 1, '0', '2024-03-08 10:39:06', '2024-10-10 14:24:01', '', 0, NULL);
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1765930975651921921, '规则管理', 1765930209830731778, 2, 'modules', '/ruleEngine/index', 'rule-engine/page/flows', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-connection', 2, '0', '2024-03-08 10:42:09', '2024-06-25 17:17:01', '', 0, 'null1765930209830731778');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1765950001551736833, '流程编辑器', 1765930209830731778, 2, 'modules', 'ruleEngine/editor', 'rule-engine/page/editor', '', '', 'C', 0, 1, 1, 0, '', '', 2, '0', '2024-03-08 11:57:45', '2024-06-25 17:17:26', '', 0, 'null1765930209830731778');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1766034419666587650, '日志详情', 1765930209830731778, 3, 'modules', '/logs/detail', 'rule-engine/page/logDetail', '', '', 'C', 0, 1, 1, 0, '', '', 2, '0', '2024-03-08 17:33:12', '2024-06-25 17:17:37', '', 0, 'null1765930209830731778');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1770755426633306113, '推流列表', 1851522039536578561, 5, 'modules', '/vmanager/streamPush', 'vmanager/page/streamPush/list', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-upload-filled', 2, '0', '2024-03-21 18:12:47', '2024-10-30 15:11:50', '', 0, 'null1851522039536578561');
INSERT INTO "HC_COMPETENCE_V2"."sys_menu" ("id", "name", "parent_id", "sort_no", "path_type", "path", "component", "parameter", "target", "type", "visible", "status", "cache", "chain", "key", "icon", "level", "category_type", "create_time", "update_time", "remark", "deleted", "parent_path") VALUES (1772468599627411457, '拉流代理', 1851522039536578561, 5, 'modules', '/vmanager/streamProxy', 'vmanager/page/streamProxy/list', '', '', 'C', 1, 1, 1, 0, '', 'el-icon-promotion', 2, '0', '2024-03-26 11:40:20', '2024-10-30 15:11:59', '', 0, 'null1851522039536578561');
INSERT INTO `HC_COMPETENCE_V2`.`sys_menu` (`id`, `name`, `parent_id`, `sort_no`, `path_type`, `path`, `component`, `parameter`, `target`, `type`, `visible`, `status`, `cache`, `chain`, `key`, `icon`, `level`, `category_type`, `create_time`, `update_time`, `remark`, `deleted`, `parent_path`) VALUES (1851522039536578561, '视频采集', 0, 1001, 'views', '/vmamger', '', '', '', 'M', 1, 1, 0, 0, '', '', 1, ',0', '2024-10-30 15:10:28', '2024-10-30 15:16:13', '', 0, NULL);