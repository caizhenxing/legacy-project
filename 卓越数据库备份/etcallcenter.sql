/*
MySQL Data Transfer
Source Host: 192.168.1.200
Source Database: etcallcenter
Target Host: 192.168.1.200
Target Database: etcallcenter
Date: 2007-3-25 13:42:57
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
CREATE TABLE `sys_department` (
  `ID` varchar(50) NOT NULL,
  `REMARKS` varchar(200) default NULL,
  `PARENT_ID` varchar(50) default NULL,
  `tag_show` varchar(1) default NULL,
  `name` varchar(50) default NULL,
  `admin` varchar(50) default NULL,
  `is_sys` varchar(2) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `sys_dep_pk` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
CREATE TABLE `sys_group` (
  `Id` varchar(50) NOT NULL,
  `name` varchar(50) default NULL,
  `del_mark` varchar(2) default NULL,
  `remark` varchar(100) default NULL,
  `is_sys` varchar(2) default NULL,
  PRIMARY KEY  (`Id`),
  UNIQUE KEY `sys_group_index` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_key
-- ----------------------------
CREATE TABLE `sys_key` (
  `TB_NAME` varchar(50) NOT NULL default '',
  `TB_ID_MAX` varchar(20) default NULL,
  PRIMARY KEY  (`TB_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
CREATE TABLE `sys_log` (
  `ID` varchar(50) NOT NULL,
  `USER_ID` varchar(50) default NULL,
  `DT` datetime default NULL,
  `MODU` varchar(50) default NULL,
  `ACTOR_TYPE` varchar(50) default NULL,
  `IP` varchar(20) default NULL,
  `REMARK` varchar(200) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `log_fk1` (`USER_ID`),
  CONSTRAINT `sys_log_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_module
-- ----------------------------
CREATE TABLE `sys_module` (
  `ID` varchar(50) NOT NULL,
  `ACTION` varchar(100) default NULL,
  `ICON` varchar(60) default NULL,
  `REMARKS` varchar(200) default NULL,
  `PARENT_ID` varchar(50) default NULL,
  `tag_show` varchar(1) default NULL,
  `name` varchar(50) default NULL,
  `layer_order` varchar(50) default NULL,
  `is_sys` varchar(2) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_right_group
-- ----------------------------
CREATE TABLE `sys_right_group` (
  `ID` varchar(50) NOT NULL,
  `GROUP_ID` varchar(50) default NULL,
  `MOD_ID` varchar(50) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `right_group_fk` (`GROUP_ID`),
  KEY `right_group_fk1` (`MOD_ID`),
  CONSTRAINT `sys_right_group_ibfk_1` FOREIGN KEY (`GROUP_ID`) REFERENCES `sys_group` (`Id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `sys_right_group_ibfk_2` FOREIGN KEY (`MOD_ID`) REFERENCES `sys_module` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_right_user
-- ----------------------------
CREATE TABLE `sys_right_user` (
  `ID` varchar(50) NOT NULL,
  `USER_ID` varchar(50) default NULL,
  `MOD_ID` varchar(50) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `right_user_fk1` (`USER_ID`),
  KEY `right_user_fk2` (`MOD_ID`),
  CONSTRAINT `sys_right_user_ibfk_1` FOREIGN KEY (`MOD_ID`) REFERENCES `sys_module` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `sys_right_user_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
CREATE TABLE `sys_role` (
  `Id` varchar(50) NOT NULL,
  `name` varchar(50) default NULL,
  `delete_mark` varchar(1) default NULL,
  `remark` varchar(100) default NULL,
  `is_sys` varchar(2) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_station_info
-- ----------------------------
CREATE TABLE `sys_station_info` (
  `id` varchar(50) NOT NULL,
  `department_id` varchar(50) default NULL,
  `dep_person_name` varchar(50) default NULL,
  `dep_level` tinytext COMMENT '职位级别',
  `dep_describe` tinytext,
  `remark` mediumtext,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_tree
-- ----------------------------
CREATE TABLE `sys_tree` (
  `ID` varchar(50) NOT NULL,
  `parent_id` varbinary(50) default NULL,
  `type` varchar(50) default NULL,
  `proc_alias` varchar(50) default NULL,
  `Label` varchar(100) default NULL,
  `Layer_order` varchar(3) default NULL,
  `Handle_node` varchar(3) default NULL,
  `Tag_show` varchar(1) default NULL,
  `Tag_sys` varchar(30) default NULL,
  `Remark` varchar(200) default NULL,
  `Tag_del` varchar(1) default NULL,
  `is_sys` varchar(2) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
CREATE TABLE `sys_user` (
  `user_id` varchar(50) NOT NULL,
  `password` varchar(70) default NULL,
  `user_name` varchar(50) default NULL,
  `group_id` varchar(50) default NULL,
  `role_id` varchar(50) default NULL,
  `department_id` varchar(50) default NULL,
  `delete_mark` varchar(2) default NULL,
  `remark` varchar(200) default NULL,
  `is_sys` varchar(2) default NULL,
  PRIMARY KEY  (`user_id`),
  KEY `group_id` (`group_id`),
  KEY `sys_user_foreign1` (`role_id`),
  KEY `dep_id` (`department_id`),
  CONSTRAINT `sys_user_ibfk_3` FOREIGN KEY (`department_id`) REFERENCES `sys_department` (`ID`),
  CONSTRAINT `sys_user_ibfk_4` FOREIGN KEY (`group_id`) REFERENCES `sys_group` (`Id`),
  CONSTRAINT `sys_user_ibfk_5` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_user_info
-- ----------------------------
CREATE TABLE `sys_user_info` (
  `USER_ID` varchar(50) NOT NULL,
  `REAL_NAME` varchar(10) default NULL,
  `SEX_ID` varchar(10) default NULL,
  `IDENTITY_KIND` varchar(30) default NULL,
  `IDENTITY_CARD` varchar(30) default NULL,
  `EMAIL` varchar(50) default NULL,
  `BIRTHDAY` date default NULL,
  `COUNTRY_ID` varchar(20) default NULL,
  `PROVINCE_ID` varchar(20) default NULL,
  `CITY_ID` varchar(20) default NULL,
  `QQ` varchar(20) default NULL,
  `BLOOD_TYPE` varchar(2) default NULL,
  `ADDRESS` varchar(80) default NULL,
  `POSTALCODE` varchar(30) default NULL,
  `MOBILE` varchar(20) default NULL,
  `FINISH_SCHOOL` varchar(40) default NULL,
  `SPECIALITY` varchar(20) default NULL,
  `WORK_ID` varchar(20) default NULL,
  `HOMEPAGE` varchar(80) default NULL,
  `REMARK` varchar(255) default NULL,
  PRIMARY KEY  (`USER_ID`),
  CONSTRAINT `sys_user_info_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_user_login
-- ----------------------------
CREATE TABLE `sys_user_login` (
  `key` varchar(20) NOT NULL default '',
  `group_id` varchar(20) default NULL,
  `state_id` int(4) default NULL,
  `login_ip` varchar(20) default NULL,
  `login_time` date default NULL,
  PRIMARY KEY  (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `sys_department` VALUES ('1', '1', 'treeRoot', '1', '沈阳市卓越科技公司', 'guxiaofeng', null);
INSERT INTO `sys_department` VALUES ('2', '2', '1', '1', '开发部', 'zhaoyifei', null);
INSERT INTO `sys_department` VALUES ('3', '34', '1', '1', '美工部', 'chenyiying', null);
INSERT INTO `sys_department` VALUES ('4', '4', '1', '1', '管理部', null, null);
INSERT INTO `sys_department` VALUES ('5', '5', '1', '1', '经理室', null, null);
INSERT INTO `sys_department` VALUES ('6', '6', '1', '1', '财会部', null, null);
INSERT INTO `sys_department` VALUES ('7', '7', '1', '1', '销售部', null, null);
INSERT INTO `sys_department` VALUES ('SYS_DEPARTMENT_0000000002', '', '1', '1', '信息部', null, null);
INSERT INTO `sys_group` VALUES ('administrator', 'administrator', '1', '管理员', '1');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000101', 'user', '1', '', '0');
INSERT INTO `sys_key` VALUES ('1', '20');
INSERT INTO `sys_key` VALUES ('ADDRESSLISTSORT_INFO', '440');
INSERT INTO `sys_key` VALUES ('ADDRESSLIST_INFO', '240');
INSERT INTO `sys_key` VALUES ('AFICHE_INFO', '200');
INSERT INTO `sys_key` VALUES ('AGRO_DRIVER_INFO', '180');
INSERT INTO `sys_key` VALUES ('AGRO_HIRER_INFO', '220');
INSERT INTO `sys_key` VALUES ('ASSETS_OPER', '340');
INSERT INTO `sys_key` VALUES ('BOOK_BORROW_INFO', '260');
INSERT INTO `sys_key` VALUES ('CHECKWORK_ABSENCE', '980');
INSERT INTO `sys_key` VALUES ('CHECKWORK_INFO', '160');
INSERT INTO `sys_key` VALUES ('DEPARTMENT_STATION_INFO', '40');
INSERT INTO `sys_key` VALUES ('DRIVER_CLASS_INFO', '20');
INSERT INTO `sys_key` VALUES ('EMAIL_BOX', '260');
INSERT INTO `sys_key` VALUES ('EMPLOYEE_INFO', '380');
INSERT INTO `sys_key` VALUES ('FILE_INFO', '580');
INSERT INTO `sys_key` VALUES ('FLOW_INSTANCE', '320');
INSERT INTO `sys_key` VALUES ('GOV_ANTITHESES_INFO', '40');
INSERT INTO `sys_key` VALUES ('GOV_DRIVER_INFO', '160');
INSERT INTO `sys_key` VALUES ('GOV_DRIVER_OPER', '40');
INSERT INTO `sys_key` VALUES ('GOV_MOTOR_INFO', '160');
INSERT INTO `sys_key` VALUES ('HANDSET_NOTE_INFO', '140');
INSERT INTO `sys_key` VALUES ('INADJUNCT_INFO', '240');
INSERT INTO `sys_key` VALUES ('INEMAIL_INFO', '1100');
INSERT INTO `sys_key` VALUES ('LEAVEWORD_INFO', '260');
INSERT INTO `sys_key` VALUES ('NEWS_ARTICLE', '380');
INSERT INTO `sys_key` VALUES ('PLAN_DETAIL', '240');
INSERT INTO `sys_key` VALUES ('PLAN_INFO', '620');
INSERT INTO `sys_key` VALUES ('RESOURCE_INFO', '380');
INSERT INTO `sys_key` VALUES ('RESOURCE_USE', '520');
INSERT INTO `sys_key` VALUES ('SYNOD_NOTE', '500');
INSERT INTO `sys_key` VALUES ('SYSRIGHTUSER', '3640');
INSERT INTO `sys_key` VALUES ('SYS_DEPARTMENT', '160');
INSERT INTO `sys_key` VALUES ('SYS_GROUP', '140');
INSERT INTO `sys_key` VALUES ('SYS_LOG', '500');
INSERT INTO `sys_key` VALUES ('SYS_MODULE', '700');
INSERT INTO `sys_key` VALUES ('SYS_ROLE', '200');
INSERT INTO `sys_key` VALUES ('SYS_STATION_INFO', '20');
INSERT INTO `sys_key` VALUES ('SYS_TREE', '360');
INSERT INTO `sys_key` VALUES ('TEST_KEY', '60');
INSERT INTO `sys_key` VALUES ('TEST_KS_NO_TABLE', '60');
INSERT INTO `sys_key` VALUES ('WORKFLOW_INSTANCE', '240');
INSERT INTO `sys_key` VALUES ('WORK_PLAN_INFO', '1040');
INSERT INTO `sys_key` VALUES ('WORK_PLAN_MISSION', '900');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000461', 'zhaoyifei', '2006-12-21 10:52:17', '类型管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000462', 'zhaoyifei', '2006-12-21 10:52:17', '用户管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000463', 'zhaoyifei', '2006-12-21 10:52:18', '模块管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000464', 'zhaoyifei', '2006-12-21 10:52:18', '组管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000465', 'zhaoyifei', '2006-12-21 10:52:19', '角色管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000466', 'zhaoyifei', '2006-12-21 10:52:19', '日志管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000467', 'zhaoyifei', '2006-12-21 10:52:20', '部门管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000468', 'zhaoyifei', '2006-12-21 10:52:20', '岗位管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000469', 'zhaoyifei', '2006-12-21 10:52:21', '办公平台', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000470', 'zhaoyifei', '2006-12-21 10:52:25', '员工信息', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000471', 'zhaoyifei', '2006-12-21 10:52:30', '设置', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000472', 'zhaoyifei', '2006-12-21 10:52:30', '使用查询', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000473', 'zhaoyifei', '2006-12-21 10:52:31', '申请', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000474', 'zhaoyifei', '2006-12-21 10:52:32', '会议室查询', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000475', 'zhaoyifei', '2006-12-21 10:52:35', '车辆登记', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000476', 'zhaoyifei', '2006-12-21 10:52:35', '用车申请', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000477', 'zhaoyifei', '2006-12-21 10:52:36', '车辆查询', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000478', 'zhaoyifei', '2006-12-21 10:52:36', '车辆审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000479', 'zhaoyifei', '2006-12-21 10:52:40', '缺勤查询', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000480', 'zhaoyifei', '2006-12-21 10:52:40', '考勤查询', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000481', 'zhaoyifei', '2006-12-21 13:29:52', '日志管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000482', 'zhaoyifei', '2006-12-21 13:30:37', '岗位管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000483', 'zhaoyifei', '2006-12-21 13:35:32', '部门管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000484', 'zhaoyifei', '2006-12-21 13:36:04', '类型管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000485', 'zhaoyifei', '2006-12-21 13:36:09', '类型管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000486', 'zhaoyifei', '2006-12-21 13:36:10', '类型管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000487', 'zhaoyifei', '2006-12-21 13:36:12', '类型管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000488', 'zhaoyifei', '2006-12-21 13:36:16', '岗位管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000489', 'zhaoyifei', '2006-12-21 13:36:19', '用户管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000490', 'zhaoyifei', '2006-12-21 13:36:21', '模块管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000491', 'zhaoyifei', '2006-12-21 13:36:23', '模块管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000492', 'zhaoyifei', '2006-12-21 13:36:24', '模块管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000493', 'zhaoyifei', '2006-12-21 13:36:25', '模块管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000494', 'zhaoyifei', '2006-12-21 13:36:26', '模块管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000495', 'zhaoyifei', '2006-12-21 13:36:27', '模块管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000496', 'zhaoyifei', '2006-12-21 13:36:29', '部门管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000497', 'zhaoyifei', '2006-12-21 13:36:31', '部门管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000498', 'zhaoyifei', '2006-12-21 13:36:45', '部门管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000499', 'zhaoyifei', '2006-12-21 13:36:46', '部门管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000500', 'zhaoyifei', '2006-12-21 13:36:47', '部门管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_module` VALUES ('1', '/ETOA/oa/mainOper.do?method=toMain', 'NoRight.gif', '1', 'treeRoot', '1', '卓越办公自动化系统', '0', null);
INSERT INTO `sys_module` VALUES ('19', '../sys/tree.do?method=loadTree', 'NoRight.gif', '19', '3', '1', '类型管理', '43', null);
INSERT INTO `sys_module` VALUES ('3', null, 'NoRight.gif', '3', '1', '1', '系统管理', '8', null);
INSERT INTO `sys_module` VALUES ('4', '../sys/user/UserOper.do?method=toMain', 'NoRight.gif', '4', '3', '1', '用户管理', '48', null);
INSERT INTO `sys_module` VALUES ('5', '../sys/module.do?method=loadTree', 'NoRight.gif', '5', '3', '1', '模块管理', '50', null);
INSERT INTO `sys_module` VALUES ('6', '../sys/group/GroupOper.do?method=toMain', 'NoRight.gif', '6', '3', '1', '组管理', '51', null);
INSERT INTO `sys_module` VALUES ('7', '../sys/role/Role.do?method=toRoleMain', 'NoRight.gif', '7', '3', '1', '角色管理', '52', null);
INSERT INTO `sys_module` VALUES ('8', '../sys/log/LogOper.do?method=toMain', 'NoRight.gif', '8', '3', '1', '日志管理', '53', null);
INSERT INTO `sys_module` VALUES ('9', '../sys/dep.do?method=loadTree', 'NoRight.gif', '911', '3', '1', '部门管理', '54', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000261', '../sys/station/station.do?method=toStationMain', 'NoRight.gif', '岗位管理', '3', '1', '岗位管理', '75', null);
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003538', 'SYS_GROUP_0000000101', '1');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003586', 'administrator', '9');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003588', 'administrator', '19');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003593', 'administrator', '3');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003598', 'administrator', '6');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003601', 'administrator', '1');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003604', 'administrator', '5');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003610', 'administrator', '4');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003614', 'administrator', 'SYS_MODULE_0000000261');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003621', 'administrator', '7');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003630', 'administrator', '8');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003023', 'zhaoyifei', '1');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003557', 'guxiaofeng', '1');
INSERT INTO `sys_role` VALUES ('administrator', 'administrator', '1', '', '1');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000181', 'user', '1', '', '0');
INSERT INTO `sys_station_info` VALUES ('DEPARTMENT_STATION_INFO_0000000001', '3', '321', '321', '321', '');
INSERT INTO `sys_station_info` VALUES ('DEPARTMENT_STATION_INFO_0000000003', '5', '54', '432', '43', '');
INSERT INTO `sys_station_info` VALUES ('DEPARTMENT_STATION_INFO_0000000021', '2', '经理', '小王', '经理', '');
INSERT INTO `sys_tree` VALUES ('1', 'treeRoot', '', '', '参数', null, null, '1', '', '1', '0', null);
INSERT INTO `sys_tree` VALUES ('10', '1', '', 'provinceType', '省份城市', '3', null, '1', '', '省份', '0', null);
INSERT INTO `sys_tree` VALUES ('11', '10', 'provinceParam', null, '辽宁', '1', null, '1', null, null, '0', null);
INSERT INTO `sys_tree` VALUES ('12', '10', '', '', '吉林', '2', null, '1', '', '辽宁', '0', null);
INSERT INTO `sys_tree` VALUES ('14', '13', 'bloodParam', null, 'A', '1', null, '1', null, null, '0', null);
INSERT INTO `sys_tree` VALUES ('15', '13', 'bloodParam', null, 'B', '2', null, '1', null, null, '0', null);
INSERT INTO `sys_tree` VALUES ('16', '13', 'bloodParam', null, 'O', '3', null, '1', null, null, '0', null);
INSERT INTO `sys_tree` VALUES ('17', '1', 'workParam', 'workType', '职业', '5', null, '1', '1', '职业', '0', null);
INSERT INTO `sys_tree` VALUES ('18', '17', 'workParam', null, '军人', '1', null, '1', null, null, '0', null);
INSERT INTO `sys_tree` VALUES ('19', '17', 'workParam', null, '农民', '2', null, '1', null, null, '0', null);
INSERT INTO `sys_tree` VALUES ('2', '1', 'newsParam', 'newsType', '新闻类型', '1', null, '1', '1', '1', '0', null);
INSERT INTO `sys_tree` VALUES ('20', '2', 'newsParam', null, '娱乐新闻', '4', null, '1', null, null, '0', null);
INSERT INTO `sys_tree` VALUES ('21', '2', 'newsParam', null, '其他新闻', '5', null, '1', null, null, '0', null);
INSERT INTO `sys_tree` VALUES ('22', '1', 'absenceParam', 'absenceType', '缺勤类型', '1', null, '1', '1', '缺勤类型', '0', null);
INSERT INTO `sys_tree` VALUES ('23', '22', 'absenceParam', null, '病假', '1', null, '1', null, '病假', '0', null);
INSERT INTO `sys_tree` VALUES ('24', '22', 'absenceParam', null, '外出', '2', null, '1', null, null, '0', null);
INSERT INTO `sys_tree` VALUES ('25', '22', 'absenceParam', null, '出差', '3', null, '1', null, null, '0', null);
INSERT INTO `sys_tree` VALUES ('26', '22', 'absenceParam', null, '事假', '4', null, '1', null, null, '0', null);
INSERT INTO `sys_tree` VALUES ('27', '1', null, 'planType', '计划类型', '6', null, '1', null, '1', '0', null);
INSERT INTO `sys_tree` VALUES ('3', '2', '', '', '体育新闻', '1', null, '1', '', '体育新闻', '0', null);
INSERT INTO `sys_tree` VALUES ('32', '1', 'departParam', 'departType', '沈阳卓越科技', '1', null, '1', null, '沈阳中科华利', '0', null);
INSERT INTO `sys_tree` VALUES ('33', '32', 'departParam', null, '研发部', '2', null, '1', null, '研发部', '0', null);
INSERT INTO `sys_tree` VALUES ('34', '32', 'departParam', null, '市场部', '3', null, '1', null, '市场部', '0', null);
INSERT INTO `sys_tree` VALUES ('35', '32', 'departParam', null, '设计部', '4', null, '1', null, '设计部', '0', null);
INSERT INTO `sys_tree` VALUES ('36', '32', 'departParam', null, '人事部', '5', null, '1', null, '人事部', '0', null);
INSERT INTO `sys_tree` VALUES ('37', '32', 'departParam', null, '后勤部', '6', null, '1', null, '后勤部', '0', null);
INSERT INTO `sys_tree` VALUES ('38', '1', 'resourceParam', 'resourceType', '资源类型', '1', null, '1', null, '资源类型', '0', null);
INSERT INTO `sys_tree` VALUES ('39', '38', 'resourceParam', null, '车辆', '2', null, '1', null, '车辆', '0', null);
INSERT INTO `sys_tree` VALUES ('4', '2', 'newsParam', null, '游戏新闻', '2', null, '1', null, '游戏新闻', '0', null);
INSERT INTO `sys_tree` VALUES ('40', '38', 'resourceParam', null, '会议室', '3', null, '1', null, '会议室', '0', null);
INSERT INTO `sys_tree` VALUES ('41', '22', 'absenceParam', null, '补签', '5', null, '1', null, null, '0', null);
INSERT INTO `sys_tree` VALUES ('42', '1', 'approveParam', 'approveType', '审批种类', '1', null, '1', null, null, '0', null);
INSERT INTO `sys_tree` VALUES ('43', '1', 'bloodParam', 'bloodType', '血型', '4', null, '1', '1', '血型', '0', null);
INSERT INTO `sys_tree` VALUES ('5', '2', 'newsParam', null, '花边新闻', '3', null, '1', null, '花边新闻', '0', null);
INSERT INTO `sys_tree` VALUES ('6', '1', 'areaParam', 'areaType', '地区类型', '2', null, '1', '1', '地区类型', '0', null);
INSERT INTO `sys_tree` VALUES ('7', 'SYS_TREE_0000000120', '', '', '中国', '1', null, '1', '', '中国', '0', null);
INSERT INTO `sys_tree` VALUES ('8', 'SYS_TREE_0000000123', '', '', '美国', '2', null, '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('9', 'SYS_TREE_0000000122', '', '', '法国', '3', null, '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000021', '1', '', 'documentType', '文档类型', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000022', 'SYS_TREE_0000000021', '', '', '会议', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000041', 'SYS_TREE_0000000021', '', '', '办公', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000061', '10', '', '', '黑龙江', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000081', '1', '', 'book_type', '图书类型', null, 'idu', '1', '', '图书类型', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000082', 'SYS_TREE_0000000081', '', '', '软件与程序设计', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000083', 'SYS_TREE_0000000081', '', '', '操作系统', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000084', 'SYS_TREE_0000000081', '', '', '数据库', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000085', 'SYS_TREE_0000000081', '', '', '图形图像、网页制作', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000086', 'SYS_TREE_0000000081', '', '', '网络与通信', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000087', 'SYS_TREE_0000000081', '', '', '工具书、软件', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000088', 'SYS_TREE_0000000081', '', '', '考试认证', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000089', 'SYS_TREE_0000000081', '', '', '教材', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000101', '', '', '', 'A', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000102', '', '', '', 'B', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000103', '', '', 'A', 'A', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000104', '10', '', '', '河北', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000106', '43', '', '', 'A', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000107', '43', '', '', 'B', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000108', '43', '', '', 'O', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000109', '10', '', '', '山东', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000110', '10', '', '', '北京', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000111', '10', '', '', '内蒙古', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000112', '10', '', '', '山西', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000113', '17', '', '', '教师', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000114', '43', '', '', '其他', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000115', 'SYS_TREE_0000000120', '', '', '日本', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000116', 'SYS_TREE_0000000122', '', '', '德国', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000117', 'SYS_TREE_0000000122', '', '', '英国', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000118', 'SYS_TREE_0000000120', '', '', '韩国', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000119', 'SYS_TREE_0000000123', '', '', '加拿大', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000120', '6', '', '', '亚洲', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000121', '6', '', '', '非洲', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000122', '6', '', '', '欧洲', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000123', '6', '', '', '美洲', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000124', '6', '', '', '大洋洲', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000125', 'SYS_TREE_0000000121', '', '', '南非', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000126', 'SYS_TREE_0000000124', '', '', '澳大利亚', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000141', '10', '', '', '河南', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000181', '1', '', 'nation', '民族', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000182', 'SYS_TREE_0000000181', '', '', '汉族', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000183', 'SYS_TREE_0000000181', '', '', '满族', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000184', 'SYS_TREE_0000000181', '', '', '蒙古族', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000185', 'SYS_TREE_0000000181', '', '', '回族', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000186', 'SYS_TREE_0000000181', '', '', '藏族', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000187', '11', '', '', '沈阳', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000188', '11', '', '', '大连', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000189', '11', '', '', '抚顺', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000190', '1', '', 'degree', '学历', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000191', 'SYS_TREE_0000000190', '', '', '小学', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000192', 'SYS_TREE_0000000190', '', '', '初中', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000193', 'SYS_TREE_0000000190', '', '', '高中', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000194', 'SYS_TREE_0000000190', '', '', '大学', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000195', 'SYS_TREE_0000000190', '', '', '硕士', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000196', 'SYS_TREE_0000000190', '', '', '博士', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000197', 'SYS_TREE_0000000190', '', '', '其他', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000201', '1', '', 'polity', '政治面貌', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000202', 'SYS_TREE_0000000201', '', '', '无', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000203', 'SYS_TREE_0000000201', '', '', '党员', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000204', 'SYS_TREE_0000000201', '', '', '团员', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000205', 'SYS_TREE_0000000201', '', '', '民主党派', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000221', 'SYS_TREE_0000000181', '', '', '维吾尔', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000241', '1', '', '', '计划类型', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000242', 'SYS_TREE_0000000241', '', 'planTimeList', '时间类型', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000243', 'SYS_TREE_0000000242', '', '', '月计划', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000244', 'SYS_TREE_0000000242', '', '', '周计划', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000245', 'SYS_TREE_0000000242', '', '', '阶段计划', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000246', 'SYS_TREE_0000000241', '', 'planViewList', '可视范围', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000247', 'SYS_TREE_0000000246', '', '', '全部可视', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000248', 'SYS_TREE_0000000246', '', '', '自己可视', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000261', 'SYS_TREE_0000000241', '', 'priType', '优先级类型', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000262', 'SYS_TREE_0000000241', '', 'pri', '优先级', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000263', 'SYS_TREE_0000000261', '', '', '一级', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000264', 'SYS_TREE_0000000261', '', '', '二级', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000265', 'SYS_TREE_0000000261', '', '', '三级', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000266', 'SYS_TREE_0000000262', '', '', '1', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000267', 'SYS_TREE_0000000262', '', '', '2', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000268', 'SYS_TREE_0000000262', '', '', '3', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000269', 'SYS_TREE_0000000262', '', '', '4', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000270', 'SYS_TREE_0000000262', '', '', '5', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000281', '27', '', '', '工作计划', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000282', '27', '', '', '学习计划', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000301', '11', '', '', '葫芦岛', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000302', '11', '', '', '锦州', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000321', '1', '', 'logType', '日志类型', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000322', 'SYS_TREE_0000000321', '', 'lookup', '查看', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000323', 'SYS_TREE_0000000321', '', 'modify', '修改', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000341', '11', '', '', '朝阳', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_user` VALUES ('chenyiying', '58311CD2040A696B4F9C68C7748D9430', '陈轶英', 'SYS_GROUP_0000000101', 'SYS_ROLE_0000000181', '3', '1', '', '0');
INSERT INTO `sys_user` VALUES ('guxiaofeng', '543424CCE1BF8BED2C1E4D4B872D2B87', '辜晓峰', 'SYS_GROUP_0000000101', 'SYS_ROLE_0000000181', '1', '1', '', '0');
INSERT INTO `sys_user` VALUES ('jingyuzhuo', '226EE5D680B8EDC3A19F21876142EA4B', '荆玉琢', 'SYS_GROUP_0000000101', 'SYS_ROLE_0000000181', '2', '1', '', '0');
INSERT INTO `sys_user` VALUES ('liuyang', '6B649039A388694C7306CB4D14041A14', '刘洋', 'SYS_GROUP_0000000101', 'SYS_ROLE_0000000181', '3', '1', '', '0');
INSERT INTO `sys_user` VALUES ('yangshuo', '6A3128EA1839E0402F4349A252845499', '杨朔', 'SYS_GROUP_0000000101', 'SYS_ROLE_0000000181', '1', '1', '', '0');
INSERT INTO `sys_user` VALUES ('yepuliang', 'C4CA4238A0B923820DCC509A6F75849B', '叶浦亮', 'SYS_GROUP_0000000101', 'SYS_ROLE_0000000181', '2', '1', '', '0');
INSERT INTO `sys_user` VALUES ('zhangfeng', '71CA0B537DD369350A996EBAFB959027', '张峰', 'SYS_GROUP_0000000101', 'SYS_ROLE_0000000181', '2', '1', '', '0');
INSERT INTO `sys_user` VALUES ('zhaoyifei', 'BA4C2F175F0DBA2F2974E676C6DFBBAB', '赵一非', 'administrator', 'administrator', '2', '1', '1', '1');
INSERT INTO `sys_user_info` VALUES ('chenyiying', '', '1', '', '', null, '2006-11-17', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('guxiaofeng', '', '1', '', '', null, '2006-11-17', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('jingyuzhuo', '', '1', '', '', null, '2006-11-17', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('liuyang', '', '1', '', '', null, '2006-11-17', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('yangshuo', '', '1', '', '', null, '2006-12-20', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('yepuliang', '', '1', '', '', null, '2006-11-17', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('zhangfeng', '', '1', '', '', null, '2006-11-17', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('zhaoyifei', '', '', '', '', null, '2006-11-17', '', '', null, '', '', '', '', '', '', '', '', '', null);
