INSERT INTO `sys_menu` (`name`, `parent_id`, `path`, `component`, `key`, `path_type`, `type`, `visible`, `status`, `cache`, `chain`, `level`, `category_type`)
VALUES ('消息中心', 0, '//sysMessageInfo', '/sysMessageInfo/list', ':sysMessageInfo:page', 'views', 'C', 1, 1, 0, 0, 1, '0');

SET @parentId = LAST_INSERT_ID();

INSERT INTO `sys_menu` (`name`, `parent_id`, `key`, `path_type`, `type`, `visible`, `status`, `cache`, `chain`, `level`, `category_type`)
VALUES ('消息中心-保存', @parentId, ':sysMessageInfo.save', 'views', 'F', 0, 1, 1, 0, 2, '0');

INSERT INTO `sys_menu` (`name`, `parent_id`, `key`, `path_type`, `type`, `visible`, `status`, `cache`, `chain`, `level`, `category_type`)
VALUES ('消息中心-详情', @parentId, ':sysMessageInfo.detail', 'views', 'F', 0, 1, 1, 0, 2, '0');

INSERT INTO `sys_menu` (`name`, `parent_id`, `key`, `path_type`, `type`, `visible`, `status`, `cache`, `chain`, `level`, `category_type`)
VALUES ('消息中心-删除', @parentId, ':sysMessageInfo.delete', 'views', 'F', 0, 1, 1, 0, 2, '0');

INSERT INTO `sys_menu` (`name`, `parent_id`, `key`, `path_type`, `type`, `visible`, `status`, `cache`, `chain`, `level`, `category_type`)
VALUES ('消息中心-导入', @parentId, ':sysMessageInfo.import', 'views', 'F', 0, 1, 1, 0, 2, '0');

INSERT INTO `sys_menu` (`name`, `parent_id`, `key`, `path_type`, `type`, `visible`, `status`, `cache`, `chain`, `level`, `category_type`)
VALUES ('消息中心-导出', @parentId, ':sysMessageInfo.export', 'views', 'F', 0, 1, 1, 0, 2, '0');