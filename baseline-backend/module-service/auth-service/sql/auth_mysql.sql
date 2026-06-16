CREATE TABLE `oauth_client_details` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID | [dto,vo]',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
    `name` varchar(100) NOT NULL DEFAULT '' COMMENT '应用名称 | [dto,vo]',
    `client_id` varchar(100) DEFAULT NULL COMMENT '客户端ID | [dto,vo]',
    `client_secret` varchar(255) DEFAULT '' COMMENT '客户端密钥 | [dto,vo]',
    `resource_ids` varchar(255) DEFAULT '' COMMENT '资源id集合，多个资源用英文逗号隔开',
    `scope` varchar(255) DEFAULT '' COMMENT '作用域 | [dto,vo]',
    `authorized_grant_types` varchar(255) DEFAULT '' COMMENT '授权类型 | [dto,vo]',
    `web_server_redirect_uri` varchar(255) DEFAULT '' COMMENT '重定向URI | [dto,vo]',
    `authorities` varchar(255) DEFAULT '' COMMENT '权限列表 | [dto,vo]',
    `access_token_validity` int(11) DEFAULT '86400' COMMENT '访问令牌有效期 | [dto,vo]',
    `refresh_token_validity` int(11) DEFAULT '172800' COMMENT '刷新令牌有效期 | [dto,vo]',
    `additional_information` varchar(255) DEFAULT '' COMMENT '预留字段，格式必须是json',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `autoapprove` varchar(255) DEFAULT 'true' COMMENT '自动批准 | [vo]',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `hc_competence_v2`.`oauth_client_details` (`id`, `tenant_id`, `name`, `client_id`, `client_secret`, `resource_ids`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `create_time`, `update_time`, `autoapprove`) VALUES (1, 0, '', 'super', 'a9b1463d8ea65f0620e26e60b4ad6c9a', NULL, 'all', 'authorization_code,password,client_credentials,implicit,refresh_token,wechat', 'https://www.baidu.com', '', 86400, 172800, NULL, NULL, NULL, 'true');