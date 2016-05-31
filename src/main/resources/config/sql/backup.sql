SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `module`
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `moduleid` varchar(255) NOT NULL,
  `modulename` varchar(255) NOT NULL,
  `moduleurl` varchar(255) DEFAULT NULL,
  `parentid` varchar(255) DEFAULT NULL,
  `fcode` varchar(255) DEFAULT NULL,
  `levels` int(11) DEFAULT NULL,
  `orders` int(11) DEFAULT NULL,
  `moduletype` int(11) NOT NULL,
  `mark` varchar(255) DEFAULT NULL,
  `alias` varchar(255) DEFAULT NULL,
  `parentalias` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `delflag` tinyint(4) NOT NULL,
  PRIMARY KEY (`moduleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module
-- ----------------------------
INSERT INTO `module` VALUES ('07e7d8be-2b0d-48f8-8376-c1f22c31982c', '用户管理', '/web/sys/user', 'a39eea77-e28a-44a6-a822-039e2a056b07', null, '2', null, '1', null, 'userManage', 'systemManage', now(), null, '0');
INSERT INTO `module` VALUES ('2e9ba74d-4ea0-49ef-8995-972ce8b26c11', '删除模块', '/web/sys/module/deleteModule', '46683b3f-ef74-4952-8ce1-fe4e7ed7ec5a', null, '3', null, '2', null, 'deleteModule', 'moduleManage', now(), null, '0');
INSERT INTO `module` VALUES ('329c3b64-4aed-4b8c-aa0d-dc7ecf01afec', '角色管理', '/web/sys/role', 'a39eea77-e28a-44a6-a822-039e2a056b07', null, '2', null, '1', null, 'roleManage', 'systemManage', now(), null, '0');
INSERT INTO `module` VALUES ('33c4ae2e-8b3f-4b81-b2bb-31e2b923de2a', '微信管理', '/web/weichat', null, null, '1', null, '1', null, 'weichatManage', null, now(), null, '0');
INSERT INTO `module` VALUES ('3dd79417-1df9-4781-844d-90b7c10fb1ff', '查询用户', '/web/sys/user/getUsers', '07e7d8be-2b0d-48f8-8376-c1f22c31982c', null, '3', null, '2', null, 'getUsers', 'userManage', now(), null, '0');
INSERT INTO `module` VALUES ('46683b3f-ef74-4952-8ce1-fe4e7ed7ec5a', '模块管理', '/web/sys/module', 'a39eea77-e28a-44a6-a822-039e2a056b07', null, '2', null, '1', null, 'moduleManage', 'systemManage', now(), null, '0');
INSERT INTO `module` VALUES ('4c38f415-9484-4a4d-bc1a-b296682d9dcb', '修改模块', '/web/sys/module/modifyModule', '46683b3f-ef74-4952-8ce1-fe4e7ed7ec5a', null, '3', null, '2', null, 'modifyModule', 'moduleManage', now(), null, '0');
INSERT INTO `module` VALUES ('58020977-881d-4a39-95a9-f87c908d9a7e', '删除用户', '/web/sys/user/deleteUser', '07e7d8be-2b0d-48f8-8376-c1f22c31982c', null, '3', null, '2', null, 'deleteUser', 'userManage', now(), null, '0');
INSERT INTO `module` VALUES ('59b7bfc0-15cf-4ac1-9100-f24aeb1db239', '新增模块', '/web/sys/module/createModule', '46683b3f-ef74-4952-8ce1-fe4e7ed7ec5a', null, '3', null, '2', null, 'createModule', 'moduleManage', now(), null, '0');
INSERT INTO `module` VALUES ('5d0398db-db79-4d47-9959-55eb5837434c', '删除角色', '/web/sys/role/deleteRole', '329c3b64-4aed-4b8c-aa0d-dc7ecf01afec', null, '3', null, '2', null, 'deleteRole', 'roleManage', now(), null, '0');
INSERT INTO `module` VALUES ('64d47f4b-996e-40dd-a7e4-e004f05eda81', '新增角色', '/web/sys/role/createRole', '329c3b64-4aed-4b8c-aa0d-dc7ecf01afec', null, '3', null, '2', null, 'createRole', 'roleManage', now(), null, '0');
INSERT INTO `module` VALUES ('6d1e5b95-e485-4239-9287-b9181cd7561d', '用户角色分配', '/web/sys/user/allotUserRole', '07e7d8be-2b0d-48f8-8376-c1f22c31982c', null, '3', null, '2', null, 'allotUserRole', 'userManage', now(), null, '0');
INSERT INTO `module` VALUES ('6fb8a24a-4204-475d-ac7d-cb6a661d4057', '模块详情', '/web/sys/module/moduleDetail', '46683b3f-ef74-4952-8ce1-fe4e7ed7ec5a', null, '3', null, '2', null, 'moduleDetail', 'moduleManage', now(), null, '0');
INSERT INTO `module` VALUES ('a39eea77-e28a-44a6-a822-039e2a056b07', '系统管理', '/web/sys', null, null, '1', null, '1', null, 'systemManage', null, now(), null, '0');
INSERT INTO `module` VALUES ('b7ec1360-ca8c-45d5-b8ca-3c9b07854ba5', '修改角色', '/web/sys/role/modifyRole', '329c3b64-4aed-4b8c-aa0d-dc7ecf01afec', null, '3', null, '2', null, 'modifyRole', 'roleManage', now(), null, '0');
INSERT INTO `module` VALUES ('c89607d5-8ce1-42ad-9cfd-528f8114838e', '修改用户', '/web/sys/user/modifyUser', '07e7d8be-2b0d-48f8-8376-c1f22c31982c', null, '3', null, '2', null, 'modifyUser', 'userManage', now(), null, '0');
INSERT INTO `module` VALUES ('cd689073-1649-478c-a4ba-3bf3cfb20009', '查询角色', '/web/sys/role/getRoles', '329c3b64-4aed-4b8c-aa0d-dc7ecf01afec', null, '3', null, '2', null, 'getRoles', 'roleManage', now(), null, '0');
INSERT INTO `module` VALUES ('d269a817-4e99-4011-ac62-fd76d7a06a49', '新增用户', '/web/sys/user/createUser', '07e7d8be-2b0d-48f8-8376-c1f22c31982c', null, '3', null, '2', null, 'createUser', 'userManage', now(), null, '0');
INSERT INTO `module` VALUES ('d7da37c4-9c6f-4123-8e6e-e4396608c896', '角色权限一览', '/web/sys/role/getModuleByRoleId', '329c3b64-4aed-4b8c-aa0d-dc7ecf01afec', null, '3', null, '2', null, 'getModuleByRoleId', 'roleManage', now(), null, '0');
INSERT INTO `module` VALUES ('e232adda-d7d4-444d-8fa5-af8619b23014', '查询模块', '/web/sys/module/getModules', '46683b3f-ef74-4952-8ce1-fe4e7ed7ec5a', null, '3', null, '2', null, 'getModules', 'moduleManage', now(), null, '0');
INSERT INTO `module` VALUES ('fac8fa7f-daa3-11e5-b161-408d5c777ae8', '用户详情', '/web/sys/user/userDetail', '07e7d8be-2b0d-48f8-8376-c1f22c31982c', null, '3', null, '2', null, 'userDetail', 'userManage', now(), null, '0');
INSERT INTO `module` VALUES ('faca8d9c-7be6-4b20-bc62-f4ae60899988', '角色详情', '/web/sys/role/roleDetail', '329c3b64-4aed-4b8c-aa0d-dc7ecf01afec', null, '3', null, '2', null, 'roleDetail', 'roleManage', now(), null, '0');
INSERT INTO `module` VALUES ('fe5af669-4095-4a4d-ae8f-5f3183923d0f', '角色权限分配', '/web/sys/role/allotRoleAuthority', '329c3b64-4aed-4b8c-aa0d-dc7ecf01afec', null, '3', null, '2', null, 'allotRoleAuthority', 'roleManage', now(), null, '0');
INSERT INTO `module` VALUES ('c23c84ec-ba13-4aee-a466-487110b925b5', '系统工具', '/web/tools', null, null, '1', null, '1', null, 'systemTools', null, now(), null, '0');
INSERT INTO `module` VALUES ('2334987d-e4dd-11e5-9808-408d5c777ae8', '接口测试', '/web/tools/appTest', 'c23c84ec-ba13-4aee-a466-487110b925b5', null, '2', null, '1', null, 'appTest', 'systemTools', now(), null, '0');
INSERT INTO `module` VALUES ('acba637c-e4dd-11e5-9808-408d5c777ae8', '测试接口', '/web/tools/appTest/testInterface', '2334987d-e4dd-11e5-9808-408d5c777ae8', null, '3', null, '2', null, 'testInterface', 'appTest', now(), null, '0');
INSERT INTO `module` VALUES ('746b3b04-e4ef-11e5-9808-408d5c777ae8', '性能监控', '/web/tools/performanceMonitor', 'c23c84ec-ba13-4aee-a466-487110b925b5', null, '2', null, '1', null, 'performanceMonitor', 'systemTools', now(), null, '0');
INSERT INTO `module` VALUES ('6227311a-e501-11e5-9808-408d5c777ae8', '监控显示', '/web/tools/performanceMonitor/druid', '746b3b04-e4ef-11e5-9808-408d5c777ae8', null, '3', null, '2', null, 'druid', 'performanceMonitor', now(), null, '0');
INSERT INTO `module` VALUES ('f56f5c14-f7b8-11e5-ba97-408d5c777ae8', '数据库表', '/web/tools/dbTable', 'c23c84ec-ba13-4aee-a466-487110b925b5', null, '2', null, '1', null, 'dbTable', 'systemTools', now(), null, '0');
INSERT INTO `module` VALUES ('649f4f18-f7b9-11e5-ba97-408d5c777ae8', '表结构一览', '/web/tools/dbTable/descTable', 'f56f5c14-f7b8-11e5-ba97-408d5c777ae8', null, '3', null, '2', null, 'descTable', 'dbTable', now(), null, '0');
INSERT INTO `module` VALUES ('bbed1ad3-0c1e-11e6-82c7-408d5c777ae8', '日程管理', '/web/sys/schedule', 'a39eea77-e28a-44a6-a822-039e2a056b07', null, '2', null, '1', null, 'scheduleManage', 'systemManage', now(), null, '0');
INSERT INTO `module` VALUES ('85313f0d-0c1f-11e6-82c7-408d5c777ae8', '日程一览', '/web/sys/schedule/getSchedule', 'bbed1ad3-0c1e-11e6-82c7-408d5c777ae8', null, '3', null, '2', null, 'getSchedules', 'scheduleManage', now(), null, '0');
INSERT INTO `module` VALUES ('fd019c4e-0cf1-11e6-82c7-408d5c777ae8', '保存日程', '/web/sys/schedule/saveSchedule', 'bbed1ad3-0c1e-11e6-82c7-408d5c777ae8', null, '3', null, '2', null, 'saveSchedules', 'scheduleManage', now(), null, '0');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleid` varchar(255) NOT NULL,
  `rolename` varchar(255) NOT NULL,
  `parentid` varchar(255) DEFAULT NULL,
  `fcode` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT '1',
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `delflag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('3458716a-586a-4c17-908b-91e861816cb9', '超级管理员', null, null, '1', now(), null, '0');

-- ----------------------------
-- Table structure for `role_module`
-- ----------------------------
DROP TABLE IF EXISTS `role_module`;
CREATE TABLE `role_module` (
  `id` varchar(255) NOT NULL,
  `roleid` varchar(255) NOT NULL,
  `moduleid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_module
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userid` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `personname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `portrait` varchar(255) DEFAULT NULL,
  `parentid` varchar(255) DEFAULT NULL,
  `fcode` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `delflag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('4ecf7680-edb7-44ce-b9cd-e0a3b6a45971', 'admin', 'dd94709528bb1c83d08f3088d4043f4742891f4f', '我叫管理员', 'admin@matser.com', '11111111111', null, null, null, null, now(), null, '0');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` varchar(255) NOT NULL,
  `userid` varchar(255) NOT NULL,
  `roleid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('b2771c7b-21de-4100-af15-25d8161acb28', '4ecf7680-edb7-44ce-b9cd-e0a3b6a45971', '3458716a-586a-4c17-908b-91e861816cb9');

-- ----------------------------
-- Table structure for `company_schedule`
-- ----------------------------
DROP TABLE IF EXISTS `company_schedule`;
CREATE TABLE `company_schedule` (
  `companyScheduleId` varchar(255) NOT NULL,
  `companyName` varchar(255) NOT NULL,
  `companyDate` varchar(255) NOT NULL,
  `companyScheduleType` int(11) NOT NULL COMMENT '1:周末;2:正常;3:节假日;4:休假',
  PRIMARY KEY (`companyScheduleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
