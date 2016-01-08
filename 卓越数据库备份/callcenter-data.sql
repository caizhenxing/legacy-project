/*
MySQL Data Transfer
Source Host: 192.168.1.200
Source Database: callcenter
Target Host: 192.168.1.200
Target Database: callcenter
Date: 2007-3-25 13:42:24
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for cc_card
-- ----------------------------
DROP TABLE IF EXISTS `cc_card`;
CREATE TABLE `cc_card` (
  `id` varchar(50) NOT NULL,
  `client_id` varchar(50) default NULL,
  `card_no` varchar(50) default NULL,
  `tag_freeze` varchar(50) default NULL,
  `remark` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for cc_client
-- ----------------------------
DROP TABLE IF EXISTS `cc_client`;
CREATE TABLE `cc_client` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) default NULL,
  `ip` varchar(50) default NULL,
  `mac_addr` varchar(100) default NULL,
  `cpu_sn` varchar(100) default NULL,
  `tag_freeze` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for cc_extension
-- ----------------------------
DROP TABLE IF EXISTS `cc_extension`;
CREATE TABLE `cc_extension` (
  `id` varchar(50) NOT NULL,
  `sn` varchar(50) default NULL,
  `extension` varchar(50) default NULL,
  `Tag_freeze` varchar(50) default NULL,
  `card_Id` varchar(50) default NULL,
  `client_id` varchar(50) default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for cc_info
-- ----------------------------
DROP TABLE IF EXISTS `cc_info`;
CREATE TABLE `cc_info` (
  `id` varchar(50) NOT NULL,
  `client_id` varchar(50) default NULL,
  `tag_io` varchar(50) default NULL,
  `phonenum` varchar(50) default NULL,
  `curtime` datetime default NULL,
  `file_link` varchar(200) default NULL,
  `content` varchar(200) default NULL,
  `is_validate` varchar(50) default NULL,
  `is_excute_ok` varchar(50) default NULL,
  `operator` varchar(50) default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for cc_rule
-- ----------------------------
DROP TABLE IF EXISTS `cc_rule`;
CREATE TABLE `cc_rule` (
  `id` varchar(50) NOT NULL,
  `rule_act` varchar(50) default NULL,
  `tag_mark` varchar(50) default NULL,
  `tag_type` varchar(50) default NULL,
  `msg_format` varchar(50) default NULL,
  `arg_num` varchar(50) default NULL,
  `create_time` datetime default NULL,
  `ver` varchar(50) default NULL,
  `tag_freeze` varchar(50) default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `ID` varchar(50) NOT NULL,
  `REMARKS` varchar(200) default NULL,
  `PARENT_ID` varchar(50) default NULL,
  `tag_show` varchar(1) default NULL,
  `name` varchar(50) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `sys_dep_pk` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `Id` varchar(50) NOT NULL,
  `name` varchar(50) default NULL,
  `del_mark` varchar(2) default NULL,
  `remark` varchar(100) default NULL,
  PRIMARY KEY  (`Id`),
  UNIQUE KEY `sys_group_index` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_key
-- ----------------------------
DROP TABLE IF EXISTS `sys_key`;
CREATE TABLE `sys_key` (
  `TB_NAME` varchar(50) NOT NULL default '',
  `TB_ID_MAX` varchar(20) default NULL,
  PRIMARY KEY  (`TB_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
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
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module` (
  `ID` varchar(50) NOT NULL,
  `ACTION` varchar(100) default NULL,
  `ICON` varchar(60) default NULL,
  `REMARKS` varchar(200) default NULL,
  `PARENT_ID` varchar(50) default NULL,
  `tag_show` varchar(1) default NULL,
  `name` varchar(50) default NULL,
  `layer_order` varchar(50) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_right_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_right_group`;
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
DROP TABLE IF EXISTS `sys_right_user`;
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
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `Id` varchar(50) NOT NULL,
  `name` varchar(50) default NULL,
  `delete_mark` varchar(1) default NULL,
  `remark` varchar(100) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_station_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_station_info`;
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
DROP TABLE IF EXISTS `sys_tree`;
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
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(50) NOT NULL,
  `password` varchar(80) default NULL,
  `user_name` varchar(50) default NULL,
  `group_id` varchar(50) default NULL,
  `role_id` varchar(50) default NULL,
  `department_id` varchar(50) default NULL,
  `delete_mark` varchar(2) default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`user_id`),
  KEY `group_id` (`group_id`),
  KEY `sys_user_foreign1` (`role_id`),
  KEY `dep_id` (`department_id`),
  CONSTRAINT `sys_user_fk_3` FOREIGN KEY (`department_id`) REFERENCES `sys_department` (`ID`),
  CONSTRAINT `sys_user_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `sys_group` (`Id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `sys_user_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`Id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_user_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_info`;
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
DROP TABLE IF EXISTS `sys_user_login`;
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
INSERT INTO `cc_rule` VALUES ('1', 'EXRION', 'COMMAND', null, 'EXRION:{0},{1},{2};', '3', '2006-09-15 08:45:15', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('10', 'ANSTON', 'EVENT', null, 'ANSTON:{0};', '1', '2006-09-15 09:48:36', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('11', 'TLKTON', 'EVENT', null, 'TLKTON:{0};', '1', '2006-09-15 09:48:33', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('12', 'PTLINK', 'COMMAND', null, 'PTLINK:{0},{1};', '2', '2006-09-15 09:48:30', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('13', 'UNLINK', 'COMMAND', null, 'UNLINK:{0},{1};', '2', '2006-09-15 09:48:27', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('14', 'STTREC', 'COMMAND', null, 'STTREC:{0},{1};', '2', '2006-09-15 09:48:25', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('15', 'STPREC', 'COMMAND', null, 'STPREC:{0};', '1', '2006-09-15 09:48:22', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('16', 'STTPLY', 'COMMAND', null, 'STTPLY:{0},{1};', '2', '2006-09-15 09:48:19', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('17', 'STPPLY', 'COMMAND', null, 'STPPLY:{0};', '1', '2006-09-15 09:48:17', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('18', 'STTSIG', 'COMMAND', null, 'STTSIG:{0},{1},{2};', '3', '2006-09-15 09:48:14', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('19', 'STPSIG', 'COMMAND', null, 'STPSIG:{0};', '1', '2006-09-15 09:48:11', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('2', 'EXRIOF', 'COMMAND', null, 'EXRIOF:{0};', '1', '2006-09-15 09:31:14', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('20', 'SDDTMF', 'COMMAND', null, 'SDDTMF:{0},{1};', '2', '2006-09-15 09:48:08', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('21', 'PLYEND', 'EVENT', null, 'PLYEND:{0};', '1', '2006-09-15 09:48:05', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('22', 'PRTSUM', 'COMMAND', null, 'PRTSUM:;', '0', '2006-09-15 09:48:02', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('23', 'PRTTYP', 'COMMAND', null, 'PRTTYP:{0};', '1', '2006-09-15 09:48:00', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('24', 'PRTSTA', 'COMMAND', null, 'PRTSTA:{0};', '1', '2006-09-15 09:47:57', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('25', 'COMMOK', 'EVENT', null, 'COMMOK:;', '0', '2006-09-15 09:47:54', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('26', 'COMERR', 'EVENT', null, 'COMERR:{0};', '1', '2006-09-15 09:47:50', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('27', 'PRTSUM', 'EVENT', null, 'PRTSUM:{0};', '1', '2006-09-15 09:47:47', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('28', 'PRTTYP', 'EVENT', null, 'PRTTYP:{0},{1};', '2', '2006-09-15 09:47:44', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('29', 'PRTSTA', 'EVENT', null, 'PRTSTA:{0},{1};', '2', '2006-09-15 09:47:40', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('3', 'EXHKOF', 'EVENT', null, 'EXHKOF:{0};', '1', '2006-09-15 09:34:23', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('4', 'EXHKON', 'EVENT', null, 'EXHKON:{0};', '1', '2006-09-15 09:34:27', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('5', 'TKHKOF', 'COMMAND', null, 'TKHKOF:{0};', '1', '2006-09-15 09:34:30', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('6', 'TKHKON', 'COMMAND', null, 'TKHKON:{0};', '1', '2006-09-15 09:36:06', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('7', 'TKRING', 'EVENT', null, 'TKRING:{0},{1};', '2', '2006-09-15 09:48:43', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('8', 'NDTONE', 'EVENT', null, 'NDTONE:{0};', '1', '2006-09-15 09:48:41', 'V1.0', null, null);
INSERT INTO `cc_rule` VALUES ('9', 'BSYTON', 'EVENT', null, 'BSYTON:{0};', '1', '2006-09-15 09:48:39', 'V1.0', null, null);
INSERT INTO `sys_department` VALUES ('1', '1', 'treeRoot', '1', '沈阳市卓越科技公司');
INSERT INTO `sys_department` VALUES ('2', '2', '1', '1', '开发部');
INSERT INTO `sys_department` VALUES ('3', '34', '1', '1', '美工部');
INSERT INTO `sys_department` VALUES ('4', '4', '1', '1', '管理部');
INSERT INTO `sys_department` VALUES ('5', '5', '1', '1', '经理室');
INSERT INTO `sys_department` VALUES ('6', '6', '1', '1', '财会部');
INSERT INTO `sys_department` VALUES ('7', '7', '1', '1', '销售部');
INSERT INTO `sys_group` VALUES ('1', '1', '-1', '1');
INSERT INTO `sys_group` VALUES ('2', '2', '-1', '');
INSERT INTO `sys_group` VALUES ('3', '3', '-1', null);
INSERT INTO `sys_group` VALUES ('4', '4', '-1', null);
INSERT INTO `sys_group` VALUES ('administrator', 'administrator', '1', '1');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000001', 'rrr', '-1', 'rrr');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000021', 'test', '1', 'test123');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000041', 'test_0516', '-1', '');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000061', '组一', '1', '');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000062', '组二', '1', '');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000063', '组三', '1', '');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000064', '组四', '1', '');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000065', '组五', '1', '');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000066', '组六', '1', '');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000067', '组七', '1', '');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000068', '组1', '1', '');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000069', '组八', '1', '');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000070', '组2', '1', '');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000071', '祖三', '1', '');
INSERT INTO `sys_key` VALUES ('1', '20');
INSERT INTO `sys_key` VALUES ('ADDRESSLISTSORT_INFO', '200');
INSERT INTO `sys_key` VALUES ('ADDRESSLIST_INFO', '160');
INSERT INTO `sys_key` VALUES ('AFICHE_INFO', '120');
INSERT INTO `sys_key` VALUES ('AGRO_DRIVER_INFO', '180');
INSERT INTO `sys_key` VALUES ('AGRO_HIRER_INFO', '220');
INSERT INTO `sys_key` VALUES ('ASSETS_OPER', '300');
INSERT INTO `sys_key` VALUES ('BOOK_BORROW_INFO', '80');
INSERT INTO `sys_key` VALUES ('CHECKWORK_ABSENCE', '680');
INSERT INTO `sys_key` VALUES ('CHECKWORK_INFO', '40');
INSERT INTO `sys_key` VALUES ('DEPARTMENT_STATION_INFO', '40');
INSERT INTO `sys_key` VALUES ('DRIVER_CLASS_INFO', '20');
INSERT INTO `sys_key` VALUES ('EMAIL_BOX', '120');
INSERT INTO `sys_key` VALUES ('EMPLOYEE_INFO', '200');
INSERT INTO `sys_key` VALUES ('FILE_INFO', '580');
INSERT INTO `sys_key` VALUES ('FLOW_INSTANCE', '300');
INSERT INTO `sys_key` VALUES ('GOV_ANTITHESES_INFO', '40');
INSERT INTO `sys_key` VALUES ('GOV_DRIVER_INFO', '160');
INSERT INTO `sys_key` VALUES ('GOV_DRIVER_OPER', '40');
INSERT INTO `sys_key` VALUES ('GOV_MOTOR_INFO', '160');
INSERT INTO `sys_key` VALUES ('INADJUNCT_INFO', '40');
INSERT INTO `sys_key` VALUES ('INEMAIL_INFO', '600');
INSERT INTO `sys_key` VALUES ('LEAVEWORD_INFO', '140');
INSERT INTO `sys_key` VALUES ('NEWS_ARTICLE', '300');
INSERT INTO `sys_key` VALUES ('PLAN_DETAIL', '40');
INSERT INTO `sys_key` VALUES ('PLAN_INFO', '220');
INSERT INTO `sys_key` VALUES ('RESOURCE_INFO', '160');
INSERT INTO `sys_key` VALUES ('RESOURCE_USE', '240');
INSERT INTO `sys_key` VALUES ('SYNOD_NOTE', '420');
INSERT INTO `sys_key` VALUES ('SYSRIGHTUSER', '2700');
INSERT INTO `sys_key` VALUES ('SYS_GROUP', '80');
INSERT INTO `sys_key` VALUES ('SYS_LOG', '60');
INSERT INTO `sys_key` VALUES ('SYS_MODULE', '400');
INSERT INTO `sys_key` VALUES ('SYS_ROLE', '140');
INSERT INTO `sys_key` VALUES ('SYS_TREE', '180');
INSERT INTO `sys_key` VALUES ('TEST_KEY', '60');
INSERT INTO `sys_key` VALUES ('WORKFLOW_INSTANCE', '220');
INSERT INTO `sys_log` VALUES ('1', 'zhaoyifei', '2006-08-21 00:00:00', null, '1', '218.25.101.108', null);
INSERT INTO `sys_log` VALUES ('10', 'zhaoyifei', '2006-08-22 10:44:13', null, '1', '218.25.101.108', null);
INSERT INTO `sys_log` VALUES ('11', 'zhaoyifei', '2006-08-15 10:44:26', null, '1', '218.25.101.108', null);
INSERT INTO `sys_log` VALUES ('12', 'zhaoyifei', '2006-08-15 10:44:44', null, '1', '218.25.101.108', null);
INSERT INTO `sys_log` VALUES ('16', 'zhaoyifei', null, null, 'a', 'a', 'a');
INSERT INTO `sys_log` VALUES ('17', 'zhaoyifei', '2006-08-30 18:59:54', null, 'a', 'a', 'a');
INSERT INTO `sys_log` VALUES ('18', 'zhaoyifei', '2006-08-30 18:59:54', '1', 'a', 'a', 'a');
INSERT INTO `sys_log` VALUES ('2', 'zhaoyifei', '2006-08-22 00:00:00', null, '1', '218.25.101.108', null);
INSERT INTO `sys_log` VALUES ('3', 'zhaoyifei', '2006-08-29 10:39:58', null, '1', '218.25.101.108', null);
INSERT INTO `sys_log` VALUES ('4', 'zhaoyifei', '2006-08-22 10:40:13', null, '1', '218.25.101.108', null);
INSERT INTO `sys_log` VALUES ('5', 'zhaoyifei', '2006-08-29 10:40:31', null, '1', '218.25.101.108', null);
INSERT INTO `sys_log` VALUES ('6', 'zhaoyifei', '2006-08-22 10:40:45', null, '1', '218.25.101.108', null);
INSERT INTO `sys_log` VALUES ('7', 'zhaoyifei', '2006-08-22 10:41:01', null, '1', '218.25.101.108', null);
INSERT INTO `sys_log` VALUES ('8', 'zhaoyifei', '2006-08-21 10:43:41', null, '1', '218.25.101.108', null);
INSERT INTO `sys_log` VALUES ('9', 'zhaoyifei', '2006-08-22 10:43:54', null, '1', '218.25.101.108', null);
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000001', 'zhaoyifei', '2006-08-30 19:49:23', '人事管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000002', 'zhaoyifei', '2006-08-30 19:49:23', '人事管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000003', 'zhaoyifei', '2006-08-30 19:49:23', '工作流管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000004', 'zhaoyifei', '2006-08-30 19:49:23', '工作流管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000005', 'zhaoyifei', '2006-08-30 19:49:23', '考勤管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000006', 'zhaoyifei', '2006-08-30 19:49:23', '考勤管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000007', 'zhaoyifei', '2006-08-30 19:49:23', '网络通信', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000008', 'zhaoyifei', '2006-08-30 19:49:23', '网络通信', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000009', 'zhaoyifei', '2006-08-30 19:49:23', '资源管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000010', 'zhaoyifei', '2006-08-30 19:49:23', '资源管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000011', 'zhaoyifei', '2006-08-30 20:01:07', '系统管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000012', 'zhaoyifei', '2006-08-30 20:01:07', '系统管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000013', 'zhaoyifei', '2006-08-30 20:01:07', '资源管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000014', 'zhaoyifei', '2006-08-30 20:01:07', '资源管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000015', 'zhaoyifei', '2006-08-30 20:01:07', '人事管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000016', 'zhaoyifei', '2006-08-30 20:01:07', '人事管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000017', 'zhaoyifei', '2006-08-30 20:01:07', '工作流管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000018', 'zhaoyifei', '2006-08-30 20:01:07', '工作流管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000019', 'zhaoyifei', '2006-08-30 20:01:07', '考勤管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000020', 'zhaoyifei', '2006-08-30 20:01:07', '考勤管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000021', 'zhaoyifei', '2006-08-30 20:01:07', '资源管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000022', 'zhaoyifei', '2006-08-30 20:01:07', '资源管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000023', 'zhaoyifei', '2006-08-30 20:01:07', '网络通信', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000024', 'zhaoyifei', '2006-08-30 20:01:07', '网络通信', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000025', 'zhaoyifei', '2006-08-30 20:01:07', '考勤管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000026', 'zhaoyifei', '2006-08-30 20:01:07', '考勤管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000027', 'zhaoyifei', '2006-08-30 20:01:07', '工作流管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000028', 'zhaoyifei', '2006-08-30 20:01:07', '工作流管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000029', 'zhaoyifei', '2006-08-30 20:01:07', '人事管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000030', 'zhaoyifei', '2006-08-30 20:01:07', '人事管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000031', 'zhaoyifei', '2006-08-30 20:01:55', '人事管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000032', 'zhaoyifei', '2006-08-30 20:01:55', '人事管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000033', 'zhaoyifei', '2006-08-30 20:01:55', '工作流管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000034', 'zhaoyifei', '2006-08-30 20:01:55', '工作流管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000035', 'zhaoyifei', '2006-08-30 20:01:55', '考勤管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000036', 'zhaoyifei', '2006-08-30 20:01:55', '考勤管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000037', 'zhaoyifei', '2006-08-30 20:01:55', '网络通信', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000038', 'zhaoyifei', '2006-08-30 20:01:55', '网络通信', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000039', 'zhaoyifei', '2006-08-30 20:01:55', '资源管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000040', 'zhaoyifei', '2006-08-30 20:01:55', '资源管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000041', 'zhaoyifei', '2006-08-30 20:09:14', '考勤管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000042', 'zhaoyifei', '2006-08-30 20:09:14', '考勤管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000043', 'zhaoyifei', '2006-08-30 20:09:45', '考勤管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000044', 'zhaoyifei', '2006-08-30 20:09:46', '网络通信', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000045', 'zhaoyifei', '2006-08-30 20:09:48', '资源管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000046', 'zhaoyifei', '2006-08-30 20:09:49', '系统管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000047', 'zhaoyifei', '2006-08-30 20:09:50', '工作流管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000048', 'zhaoyifei', '2006-08-30 20:09:51', '人事管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000049', 'zhaoyifei', '2006-08-30 20:09:54', '个人办公', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000050', 'zhaoyifei', '2006-08-30 20:09:56', '人事管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000051', 'zhaoyifei', '2006-08-30 20:09:57', '考勤管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000052', 'zhaoyifei', '2006-08-30 20:09:58', '网络通信', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000053', 'zhaoyifei', '2006-08-30 20:09:59', '资源管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000054', 'zhaoyifei', '2006-08-30 20:10:00', '系统管理', 'comein', '192.168.1.3', '');
INSERT INTO `sys_module` VALUES ('1', '/callcenter/oa/mainOper.do?method=toMain', 'NoRight.gif', '1', 'treeRoot', '1', '卓越办公自动化系统', '1');
INSERT INTO `sys_module` VALUES ('19', '/callcenter/sys/tree.do?method=loadTree', 'NoRight.gif', '19', '3', '1', '类型管理', '7');
INSERT INTO `sys_module` VALUES ('3', null, 'NoRight.gif', '3', '1', '1', '系统管理', '1');
INSERT INTO `sys_module` VALUES ('4', '/callcenter/sys/user/UserOper.do?method=toMain', 'NoRight.gif', '4', '3', '1', '用户管理', '1');
INSERT INTO `sys_module` VALUES ('5', '/callcenter/sys/module.do?method=loadTree', 'NoRight.gif', '5', '3', '1', '模块管理', '2');
INSERT INTO `sys_module` VALUES ('6', '/callcenter/sys/group/GroupOper.do?method=toMain', 'NoRight.gif', '6', '3', '1', '组管理', '3');
INSERT INTO `sys_module` VALUES ('7', '/callcenter/sys/role/Role.do?method=toRoleMain', 'NoRight.gif', '7', '3', '1', '角色管理', '4');
INSERT INTO `sys_module` VALUES ('8', '/callcenter/sys/log/LogOper.do?method=toMain', 'NoRight.gif', '8', '3', '1', '日志管理', '5');
INSERT INTO `sys_module` VALUES ('9', '/callcenter/sys/dep.do?method=loadTree', 'NoRight.gif', '911', '3', '1', '部门管理', '6');
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000261', '/callcenter/sys/station/station.do?method=toStationMain', 'NoRight.gif', '岗位管理', '3', '1', '岗位管理', '8');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000001449', 'SYS_GROUP_0000000021', '1');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000002648', 'administrator', '9');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000002649', 'administrator', '19');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000002654', 'administrator', '3');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000002660', 'administrator', '1');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000002663', 'administrator', '6');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000002665', 'administrator', '5');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000002673', 'administrator', '4');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000002676', 'administrator', 'SYS_MODULE_0000000261');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000002682', 'administrator', '7');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000002693', 'administrator', '8');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000000017', 'zhaoyifei', '1');
INSERT INTO `sys_role` VALUES ('administrator', 'administrator', 'Y', 'ouuss');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000021', 'administrator', '1', '我是管理员\r\n<_>');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000061', 'winb', '1', 'fffff');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000072', 'win', 'Y', 'eeeeeeee');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000073', 'poi', 'Y', '\'\'\'[[');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000081', 'ds', 'Y', 'ss');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000101', 'rtyui', '1', '111');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000121', 'dd', '1', '');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000122', 'cc', '1', '');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000123', 'bb', '1', '');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000124', 'aa', '1', '');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000125', 'qq', '1', '');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000126', 'ff', '1', '');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000127', 'zz', '1', '');
INSERT INTO `sys_role` VALUES ('SYS_ROLE_0000000128', 'xx', '1', '');
INSERT INTO `sys_station_info` VALUES ('DEPARTMENT_STATION_INFO_0000000001', '3', '321', '321', '321', '');
INSERT INTO `sys_station_info` VALUES ('DEPARTMENT_STATION_INFO_0000000003', '5', '54', '432', '43', '');
INSERT INTO `sys_station_info` VALUES ('DEPARTMENT_STATION_INFO_0000000004', '3', '432', '432', '432', '');
INSERT INTO `sys_station_info` VALUES ('DEPARTMENT_STATION_INFO_0000000021', '2', '经理', '小王', '经理', '');
INSERT INTO `sys_tree` VALUES ('1', 'treeRoot', null, null, '参数', null, null, '1', '1', '1', '0');
INSERT INTO `sys_tree` VALUES ('14', '13', 'bloodParam', null, 'A', '1', null, '1', null, null, '0');
INSERT INTO `sys_tree` VALUES ('15', '13', 'bloodParam', null, 'B', '2', null, '1', null, null, '0');
INSERT INTO `sys_tree` VALUES ('16', '13', 'bloodParam', null, 'O', '3', null, '1', null, null, '0');
INSERT INTO `sys_tree` VALUES ('32', '1', 'departParam', 'departType', '沈阳卓越科技', '1', null, '1', null, '沈阳中科华利', '0');
INSERT INTO `sys_tree` VALUES ('33', '32', 'departParam', null, '研发部', '2', null, '1', null, '研发部', '0');
INSERT INTO `sys_tree` VALUES ('34', '32', 'departParam', null, '市场部', '3', null, '1', null, '市场部', '0');
INSERT INTO `sys_tree` VALUES ('35', '32', 'departParam', null, '设计部', '4', null, '1', null, '设计部', '0');
INSERT INTO `sys_tree` VALUES ('37', '32', 'departParam', null, '后勤部', '6', null, '1', null, '后勤部', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000101', '1', '', 'callcenter', '呼叫中心', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000102', 'SYS_TREE_0000000123', '', 'server_socket_port', '12000', null, 'idu', '1', '', '服务器socket通讯端口', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000103', 'SYS_TREE_0000000125', '', 'client_socket_port', '12001', null, 'idu', '1', '', '客户端socket通讯端口', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000105', 'SYS_TREE_0000000109', '', 'server_ip', '127.0.0.1', null, 'idu', '1', '', '服务器ip地址', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000106', 'SYS_TREE_0000000122', '', 'client_ip_list', '客户机ip地址', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000109', 'SYS_TREE_0000000121', '', '', '服务器ip地址', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000110', 'SYS_TREE_0000000106', '', 'client_ip_01', '192.168.1.2', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000111', 'SYS_TREE_0000000106', '', 'client_ip_02', '192.168.1.3', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000121', 'SYS_TREE_0000000101', '', 'server', '服务器', null, 'idu', '1', '', '服务器相关参数', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000122', 'SYS_TREE_0000000101', '', 'client', '客户机', null, 'idu', '1', '', '客户机相关参数', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000123', 'SYS_TREE_0000000121', '', '', '通讯端口', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000124', '', '', '', '', null, 'idu', '', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000125', 'SYS_TREE_0000000122', '', '', '通讯端口', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000126', 'SYS_TREE_0000000121', '', '', '通讯线程数量', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000127', '', '', '', '', null, 'idu', '', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000128', 'SYS_TREE_0000000122', '', '', '测试数量', null, 'idu', '1', '', '测试时客户机模拟数量', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000129', 'SYS_TREE_0000000126', '', 'max_socket_thread', '5', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000130', 'SYS_TREE_0000000128', '', 'client_test_num', '100000', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000141', 'SYS_TREE_0000000101', '', 'schedule', '操作进度设定', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000142', 'SYS_TREE_0000000141', '', '', '开关选项', null, 'idu', '1', '', '开关', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000145', 'SYS_TREE_0000000142', '', 'schedule_time', '时间开关', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000146', 'SYS_TREE_0000000142', '', 'schedule_amount', '数量开关', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000147', 'SYS_TREE_0000000142', '', 'schedule_all', '所有开关', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000148', 'SYS_TREE_0000000145', '', '', '定时操作', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000149', 'SYS_TREE_0000000146', '', '', '定量操作', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000150', 'SYS_TREE_0000000148', '', 'schedule_time_value', '10', null, 'idu', '1', '', '单位：秒。\r\n10秒钟一次存储。', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000151', 'SYS_TREE_0000000149', '', 'schedule_amount_value', '20', null, 'idu', '1', '', '20次命令和事件一次存取', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000152', 'SYS_TREE_0000000142', '', 'schedule_none', '开关关闭', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000153', 'SYS_TREE_0000000101', '', 'cmd_evt', '命令与事件', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000154', 'SYS_TREE_0000000153', '', '', '命令结果', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000155', 'SYS_TREE_0000000153', '', '', '事件结果', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000156', 'SYS_TREE_0000000154', '', 'cmd_send_ok', '命令传送成功', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000157', 'SYS_TREE_0000000155', '', 'evt_operate_ok', '事件操作成功', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000158', '', '', '', '', null, 'idu', '', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000159', 'SYS_TREE_0000000154', '', 'cmd_return_ok', '命令返回成功', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000160', 'SYS_TREE_0000000154', '', 'cmd_return_error', '命令返回失败', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000161', 'SYS_TREE_0000000154', '', 'cmd_send_error', '命令传送失败', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000162', 'SYS_TREE_0000000155', '', 'evt_operate_error', '事件操作失败', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000163', 'SYS_TREE_0000000155', '', 'evt_string_invalid', '事件字符串非法', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000164', 'SYS_TREE_0000000141', '', '', '当前开关', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000165', 'SYS_TREE_0000000164', '', 'current_schedule', 'schedule_time', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_user` VALUES ('guxf', '32C77DB2F8E75CE1B4E9F0672B442029', 'guxiaofeng', 'administrator', 'administrator', '1', '1', '1');
INSERT INTO `sys_user` VALUES ('zhaoyifei', 'BA4C2F175F0DBA2F2974E676C6DFBBAB', 'zhaoyifei', 'administrator', 'administrator', '1', '1', '1');
