INSERT INTO `sys_permission` (`name`, `parent_id`, `permission`, `category_type`, `level`)
VALUES ('消息中心-分页', 0, ':sysMessageInfo:page', 0, 1);

SET @parentId = LAST_INSERT_ID();

INSERT INTO `sys_permission` (`name`, `parent_id`, `permission`, `category_type`, `level`)
VALUES ('消息中心-保存', @parentId, ':sysMessageInfo:save', 0, 2);

INSERT INTO `sys_permission` (`name`, `parent_id`, `permission`, `category_type`, `level`)
VALUES ('消息中心-详情', @parentId, ':sysMessageInfo:detail', 0, 2);

INSERT INTO `sys_permission` (`name`, `parent_id`, `permission`, `category_type`, `level`)
VALUES ('消息中心-删除', @parentId, ':sysMessageInfo:delete', 0, 2);

INSERT INTO `sys_permission` (`name`, `parent_id`, `permission`, `category_type`, `level`)
VALUES ('消息中心-导入', @parentId, ':sysMessageInfo:import', 0, 2);

INSERT INTO `sys_permission` (`name`, `parent_id`, `permission`, `category_type`, `level`)
VALUES ('消息中心-导出', @parentId, ':sysMessageInfo:export', 0, 2);