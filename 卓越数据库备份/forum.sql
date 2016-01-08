/*
MySQL Data Transfer
Source Host: 192.168.1.200
Source Database: forum
Target Host: 192.168.1.200
Target Database: forum
Date: 2007-3-25 13:44:37
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for forum_ballot_detail
-- ----------------------------
CREATE TABLE `forum_ballot_detail` (
  `id` varchar(50) character set latin1 NOT NULL default '',
  `postsId` varchar(50) default NULL COMMENT '帖子id',
  `item` varchar(100) default NULL,
  `count` varchar(50) default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_collection
-- ----------------------------
CREATE TABLE `forum_collection` (
  `id` varchar(50) character set latin1 NOT NULL default '',
  `u_id` varchar(50) default NULL,
  `posts_id` varchar(50) default NULL,
  `coll_name` varchar(100) default NULL,
  `coll_time` datetime default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_exprience_level
-- ----------------------------
CREATE TABLE `forum_exprience_level` (
  `id` varchar(50) NOT NULL default '',
  `user_level` varchar(50) default NULL,
  `point` int(20) default NULL,
  `level_img` varchar(50) default NULL,
  `remark` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_forbidden
-- ----------------------------
CREATE TABLE `forum_forbidden` (
  `id` varchar(50) character set latin1 NOT NULL default '',
  `posts_id` varchar(50) default NULL COMMENT '论坛帖子id',
  `userkey` varchar(100) default NULL COMMENT '要封的用户',
  `begin_time` datetime default NULL COMMENT '开始时间',
  `time_length` bigint(20) default NULL COMMENT '所封天数',
  `is_validate` bigint(20) default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_friend
-- ----------------------------
CREATE TABLE `forum_friend` (
  `id` varchar(50) NOT NULL default '',
  `user_id` varchar(50) default NULL,
  `friend_id` varchar(50) default NULL,
  `friend_name` varchar(50) default NULL,
  `add_date` datetime default NULL,
  `remark` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_log
-- ----------------------------
CREATE TABLE `forum_log` (
  `id` varchar(50) character set latin1 NOT NULL,
  `user_id` varchar(50) default NULL COMMENT '用户id',
  `moduleName` varchar(50) default NULL,
  `action` varchar(50) default NULL COMMENT '所要执行的操作',
  `oper_time` datetime default NULL COMMENT '操作时间',
  `post_id` varchar(50) default NULL COMMENT '帖子id',
  `remark` varchar(200) default NULL,
  `ip` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_post_content
-- ----------------------------
CREATE TABLE `forum_post_content` (
  `id` varchar(50) NOT NULL,
  `content` longtext,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_posts
-- ----------------------------
CREATE TABLE `forum_posts` (
  `id` varchar(50) NOT NULL,
  `item_id` varchar(50) default NULL,
  `userkey` varchar(100) default NULL COMMENT '发帖人或者回帖人',
  `parent_id` varchar(50) default NULL COMMENT '帖子父id',
  `click_times` varchar(20) default NULL,
  `forum_collection` varchar(20) default NULL COMMENT '是否为收藏',
  `hot_posts` varchar(20) default NULL COMMENT '是否是热帖',
  `dict_top_type` varchar(50) default NULL,
  `is_top` varchar(20) default NULL COMMENT '是否置顶',
  `title_lock` varchar(50) default NULL,
  `title_image` varchar(100) default NULL COMMENT '标题图片',
  `title_type` varchar(50) default NULL,
  `title` varchar(100) default NULL COMMENT '题标内容',
  `title_describe` varchar(200) default NULL,
  `item_image` varchar(10) default NULL COMMENT '顶置等显示图标',
  `post_at` datetime default NULL COMMENT '发帖回帖时间',
  `ip_from` varchar(20) default NULL COMMENT '从哪里发的，记录ip',
  `vote` varchar(50) default NULL,
  `elite` varchar(50) default NULL,
  `is_validate` varchar(20) default NULL,
  `is_del` varchar(20) default NULL,
  `is_recommend` varchar(20) default NULL,
  `post_type` varchar(20) default NULL,
  `state` varchar(50) default NULL,
  `checkcent` varchar(50) default NULL COMMENT '检查分数',
  `mod_time` datetime default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_posts_weapon
-- ----------------------------
CREATE TABLE `forum_posts_weapon` (
  `id` varchar(50) character set latin1 NOT NULL default '',
  `posts_id` varchar(50) default NULL COMMENT '帖子id',
  `weapon_id` varchar(50) default NULL COMMENT '武器id',
  `weapon_number` varchar(50) default NULL COMMENT '武器号',
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_report
-- ----------------------------
CREATE TABLE `forum_report` (
  `id` varchar(50) NOT NULL default '',
  `posts_id` varchar(50) default NULL,
  `user_id` varchar(50) default NULL,
  `what_posts` varchar(100) default NULL,
  `who_id` varchar(100) default NULL,
  `why` varchar(100) default NULL,
  `report_time` datetime default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_topic
-- ----------------------------
CREATE TABLE `forum_topic` (
  `id` varchar(50) character set latin1 NOT NULL default '',
  `paixu` int(50) default NULL,
  `topic_title` varchar(100) default NULL,
  `parent_id` varchar(50) default NULL,
  `topic_describe` varchar(200) default NULL COMMENT '区域描述',
  `topic_photo` varchar(100) default NULL COMMENT '图片',
  `integral` varchar(50) default NULL,
  `action` varchar(50) default NULL,
  `topic_intro` varchar(200) default NULL,
  `is_sys` varchar(20) default NULL,
  `answer_times` varchar(50) default NULL COMMENT '回帖数',
  `post_num` varchar(50) default NULL COMMENT '点击数',
  `is_allow_visitor_sendpost` varchar(20) default NULL COMMENT '是否允许用户发帖',
  `style` varchar(255) default NULL COMMENT '风格',
  `host` varchar(50) default NULL,
  `update_time` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_topten
-- ----------------------------
CREATE TABLE `forum_topten` (
  `id` varchar(255) character set latin1 NOT NULL default '',
  `posts_id` varchar(50) default NULL COMMENT '帖子id',
  `post_type` varchar(50) default NULL,
  `click_count` varchar(50) default NULL,
  `repost_count` varchar(50) default NULL,
  `post_title` varchar(200) default NULL,
  `img_url` varchar(100) default NULL,
  `area_id` varchar(50) default NULL,
  `topic_id` varchar(50) default NULL,
  `type` varchar(50) default NULL COMMENT '帖子类型',
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_user_info
-- ----------------------------
CREATE TABLE `forum_user_info` (
  `id` varchar(50) character set latin1 NOT NULL default '',
  `name` varchar(100) default NULL,
  `password` varchar(50) default NULL,
  `question` varchar(200) default NULL,
  `answer` varchar(200) default NULL,
  `groom_user` varchar(100) default NULL,
  `email` varchar(50) default NULL,
  `QQ` varchar(20) default NULL,
  `MSN` varchar(50) default NULL,
  `ICQ` varchar(50) default NULL,
  `homepage` varchar(100) default NULL,
  `introself` varchar(200) default NULL,
  `country` varchar(20) default NULL,
  `birthday` datetime default NULL,
  `style` varchar(20) default NULL,
  `language` varchar(20) default NULL,
  `area_id` varchar(50) default NULL COMMENT '论坛区域id(赋予权限时用)',
  `forum_publish_id` varchar(50) default NULL COMMENT '是否是被封',
  `point` int(20) default NULL COMMENT '积分',
  `forum_role` varchar(50) default NULL COMMENT '用户进入论坛角色',
  `experience` varchar(20) default NULL COMMENT '经验值',
  `register_date` datetime default NULL,
  `sex` varchar(10) default NULL,
  `last_login` datetime default NULL,
  `head_image` varchar(100) default NULL,
  `under_write` varchar(200) default NULL COMMENT '名签档',
  `remark` varchar(200) default NULL,
  `register_ip` varchar(50) default NULL,
  `newly_ip` varchar(200) default NULL,
  `send_post_num` int(50) default NULL,
  `answer_post_num` int(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_weapon
-- ----------------------------
CREATE TABLE `forum_weapon` (
  `id` varchar(50) character set latin1 NOT NULL default '',
  `weapon_name` varchar(200) default NULL,
  `dict_weapon` varchar(100) default NULL,
  `weapon_score` varchar(20) default NULL,
  `weapon_icon` varchar(100) default NULL COMMENT '武器图标',
  `weapon_url` varchar(100) default NULL,
  `del_mark` varchar(20) default NULL COMMENT '删除标志',
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
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
CREATE TABLE `sys_group` (
  `Id` varchar(50) NOT NULL,
  `name` varchar(50) default NULL,
  `del_mark` varchar(2) default NULL,
  `remark` varchar(100) default NULL,
  `is_sys` varchar(2) default NULL COMMENT '是否为系统用户  1为系统用户  0为非系统用户',
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
  `is_forum` varchar(20) default NULL,
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
  `is_sys` varchar(2) default NULL COMMENT '是否为系统用户  1为系统用户  0为非系统用户',
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
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
CREATE TABLE `sys_user` (
  `user_id` varchar(50) NOT NULL,
  `password` varchar(80) default NULL,
  `user_name` varchar(50) default NULL,
  `group_id` varchar(50) default NULL,
  `role_id` varchar(50) default NULL,
  `department_id` varchar(50) default NULL,
  `delete_mark` varchar(2) default NULL COMMENT '正常为1  删除为-1',
  `remark` varchar(200) default NULL,
  `is_sys` varchar(2) default NULL COMMENT '是否为系统用户  1为系统用户  0为非系统用户',
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
INSERT INTO `forum_collection` VALUES ('FORUM_COLLECTION_0000000021', 'admin', 'FORUM_POSTS_0000000269', 'ssssssssssss', '2006-12-07 16:54:01', null);
INSERT INTO `forum_collection` VALUES ('FORUM_COLLECTION_0000000041', 'admin', 'FORUM_POSTS_0000000269', 'ssssssssssss', '2006-12-14 13:22:55', null);
INSERT INTO `forum_collection` VALUES ('FORUM_COLLECTION_0000000081', 'admin', 'FORUM_POSTS_0000000465', '11111111111', '2006-12-22 16:08:26', null);
INSERT INTO `forum_exprience_level` VALUES ('0', '农民', '0', null, null);
INSERT INTO `forum_exprience_level` VALUES ('1', '魔兽小王子', '50', null, null);
INSERT INTO `forum_exprience_level` VALUES ('2', '我是天才', '100', null, null);
INSERT INTO `forum_exprience_level` VALUES ('3', '山岭巨人', '200', null, null);
INSERT INTO `forum_exprience_level` VALUES ('4', '独孤求败', '5000', null, null);
INSERT INTO `forum_friend` VALUES ('1', 'admin', '111', '吼吼~~~~', '2006-12-19 13:43:25', 'uuu');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000042', 'admin', 'SYS_TREE_0000000322', '查看', '2007-01-09 10:26:25', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000043', 'admin', 'SYS_TREE_0000000322', '查看', '2007-01-09 10:30:56', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000044', 'admin', 'SYS_TREE_0000000322', '查看', '2007-01-09 10:33:37', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000045', 'admin', 'SYS_TREE_0000000322', '查看', '2007-01-09 10:35:59', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000046', 'admin', 'SYS_TREE_0000000322', '查看', '2007-01-09 10:36:23', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000047', 'admin', 'SYS_TREE_0000000322', '查看', '2007-01-09 10:42:17', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000048', 'admin', 'SYS_TREE_0000000322', '查看', '2007-01-09 10:48:53', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000061', 'admin', 'SYS_TREE_0000000322', '查看', '2007-01-10 14:48:35', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000062', 'admin', 'SYS_TREE_0000000322', '查看', '2007-01-10 14:49:17', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000081', 'admin', '论坛日志管理', '查看', '2007-01-10 15:19:32', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000082', 'admin', '论坛日志管理', '查看', '2007-01-10 15:21:51', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000083', 'admin', '论坛日志管理', '查看', '2007-01-10 15:23:24', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000084', 'admin', '论坛日志管理', '查看', '2007-01-10 15:35:55', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000085', 'admin', '论坛日志管理', '查看', '2007-01-10 15:36:24', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000086', 'admin', '论坛日志管理', '查看', '2007-01-10 15:36:36', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000087', 'admin', '论坛日志管理', '查看', '2007-01-10 15:36:58', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000088', 'admin', '论坛日志管理', '查看', '2007-01-10 15:37:10', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000089', 'admin', '论坛日志管理', '查看', '2007-01-10 15:37:47', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000101', 'admin', '论坛日志管理', '查看', '2007-01-10 15:40:39', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000102', 'admin', '论坛日志管理', '查看', '2007-01-10 15:42:47', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000103', 'admin', '论坛日志管理', '查看', '2007-01-10 15:42:47', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000104', 'admin', '论坛日志管理', '查看', '2007-01-10 15:46:13', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000105', 'admin', '论坛日志管理', '查看', '2007-01-10 15:46:29', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000121', 'admin', '论坛日志管理', '查看', '2007-01-10 16:24:17', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000141', 'admin', '论坛日志管理', '查看', '2007-01-11 10:41:25', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000142', 'admin', '论坛日志管理', '查看', '2007-01-11 11:18:33', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000161', 'admin', '论坛日志管理', '查看', '2007-01-11 13:58:00', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000181', 'admin', '论坛日志管理', '查看', '2007-01-12 10:39:46', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000182', 'admin', '论坛日志管理', '查看', '2007-01-12 10:43:30', null, null, '192.168.1.111');
INSERT INTO `forum_log` VALUES ('FORUM_LOG_0000000183', 'admin', '论坛日志管理', '查看', '2007-01-12 10:47:26', null, null, '192.168.1.111');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000465', '111111111111111');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000466', '444444444444444');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000467', '555555555555555');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000468', '555555555555555');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000469', '555555555555555');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000470', '555555555555555');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000481', 'ggggggggggggg');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000482', 'kkkkkkkkkkkkkk');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000501', '212');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000502', '212');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000503', '333333333333333333333333333');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000521', 'lllllllllllllllll');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000541', '11111111111111111[fly]文字[/fly] ');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000542', 'ggggggg[fly]hhhhhhhhhhhh[/fly] ');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000561', '222');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000562', '333');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000563', '333');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000564', 'gggggggggggggggggg');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000565', 'ggggggggggggggggggggggggg');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000581', 'kkkkkkkkkkkkkkkk');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000601', '申请一下');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000621', '111');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000622', '222');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000623', '333');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000624', '444');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000641', '666');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000661', '1111');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000662', '1111');
INSERT INTO `forum_posts` VALUES ('FORUM_POSTS_0000000621', '12', 'admin', 'FORUM_POSTS_0000000621', '0', null, null, null, null, null, null, null, '111', '111', null, '2006-12-25 10:49:17', '192.168.1.9', null, null, null, null, null, null, null, null, '2006-12-25 10:54:08', null);
INSERT INTO `forum_posts` VALUES ('FORUM_POSTS_0000000622', '12', 'admin', 'FORUM_POSTS_0000000621', '0', null, null, null, null, null, null, null, '222', null, null, '2006-12-25 10:49:24', '192.168.1.9', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `forum_posts` VALUES ('FORUM_POSTS_0000000623', '12', 'admin', 'FORUM_POSTS_0000000621', '0', null, null, null, null, null, null, null, '333', null, null, '2006-12-25 10:49:28', '192.168.1.9', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `forum_posts` VALUES ('FORUM_POSTS_0000000624', '12', 'admin', 'FORUM_POSTS_0000000621', '0', null, null, null, null, null, null, null, '444', null, null, '2006-12-25 10:49:33', '192.168.1.9', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `forum_posts` VALUES ('FORUM_POSTS_0000000641', '12', 'admin', 'FORUM_POSTS_0000000621', '0', null, null, null, null, null, null, null, '666', null, null, '2006-12-25 10:54:08', '192.168.1.9', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `forum_posts` VALUES ('FORUM_POSTS_0000000661', '12', '111', 'FORUM_POSTS_0000000661', '0', null, null, null, null, null, null, null, '1111', '111', null, '2007-01-12 14:18:27', '192.168.1.111', null, null, null, null, null, null, null, null, '2007-01-12 14:18:55', null);
INSERT INTO `forum_posts` VALUES ('FORUM_POSTS_0000000662', '12', '111', 'FORUM_POSTS_0000000661', '0', null, null, null, null, null, null, null, '11', null, null, '2007-01-12 14:18:55', '192.168.1.111', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `forum_topic` VALUES ('1', '100', '卓越论坛系统', '1', '222', null, null, '1', '1', '0', '0', '0', '', null, null, null);
INSERT INTO `forum_topic` VALUES ('10', '110', '【网站建设】  ', '1', null, null, null, null, null, '1', '0', '0', 'N', null, '无', '2007-01-01 14:03:12');
INSERT INTO `forum_topic` VALUES ('11', '111', '论坛建议', '10', '大家对新的论坛有什么要求和想法，请大家在这里提出。我们共同建站！！！', null, null, null, null, '1', '0', '0', 'N', null, '无', '2007-01-01 14:03:16');
INSERT INTO `forum_topic` VALUES ('12', '112', '友情链接申请', '10', '希望你能成为我们的盟友', null, null, null, null, '1', '1', '1', 'N', null, '无', '2007-01-12 14:18:55');
INSERT INTO `forum_topic` VALUES ('13', '113', ' 【站务管理】  ', '1', null, null, null, null, null, '1', '0', '0', 'N', null, '无', '2007-01-16 14:03:25');
INSERT INTO `forum_topic` VALUES ('14', '114', '  投诉举报\r\n', '13', '欢迎大家监督举报违反我们律令的帖子', null, null, null, null, '1', '0', '0', 'N', null, '无', '2007-01-16 14:03:29');
INSERT INTO `forum_topic` VALUES ('15', '115', ' 版主申请', '13', '想成为我们的版主吗.只要你具备我们的要求你就可以了.以后的成长离不开你的支持', null, null, null, null, '1', '0', '0', 'N', null, '无', '2007-01-30 14:03:33');
INSERT INTO `forum_topic` VALUES ('2', '101', ' 【电脑公司特别版技术区】   \r\n', '1', null, null, null, null, null, '1', '0', '0', 'N', null, '无', '2007-01-23 14:03:36');
INSERT INTO `forum_topic` VALUES ('3', '102', ' 『最新版本讨论区』', '2', '电脑公司特别版7.0首度官方网站正式发布。', null, null, null, null, '1', '0', '0', 'N', null, '无', '2007-01-23 14:03:40');
INSERT INTO `forum_topic` VALUES ('4', '103', '『windows讨论区』', '2', '微软操作系统Windows使用交流区', null, null, null, null, '1', '0', '0', 'N', null, '无', '2007-01-23 14:03:42');
INSERT INTO `forum_topic` VALUES ('5', '105', '『常用软件下载区』', '2', '装机和日常工作中必不可少软件，我们这里为你提供.', null, null, null, null, '1', '0', '0', 'N', null, '无', '2007-01-30 14:03:45');
INSERT INTO `forum_topic` VALUES ('6', '106', ' 【休闲娱乐】  \r\n', '1', null, null, null, null, null, '1', '0', '0', 'N', null, '无', '2007-01-23 14:03:48');
INSERT INTO `forum_topic` VALUES ('7', '107', '『极品影音』', '6', '精彩影视、天籁之音。需要有18币币才能进入的哦', '', null, null, null, '1', '0', '0', 'N', null, '无', '2007-01-30 14:03:52');
INSERT INTO `forum_topic` VALUES ('8', '108', ' 『精彩靓图』', '6', '美丽风景，精美墙纸，生活美图，搞笑娱乐，经典艺术……', null, null, null, null, '1', '0', '0', 'N', null, '无', '2007-01-22 14:03:55');
INSERT INTO `forum_topic` VALUES ('9', '109', ' 『精彩文摘』', '6', '用文字一点点的表达记录着生活中的一切......', null, null, null, null, '1', '0', '0', 'N', null, '无', '2007-01-09 14:03:58');
INSERT INTO `forum_topten` VALUES ('1', '1', '1', '111', '1', '一定要男人养家?', '1', '1', '1', '1', '');
INSERT INTO `forum_topten` VALUES ('11', null, null, '1222', '22', '郎财女貌结婚时', null, null, null, '1', null);
INSERT INTO `forum_topten` VALUES ('2', null, null, '14444', '33', '中国最帅的四个男人,连男人们都喜欢他们!!!!  ', null, null, null, '2', null);
INSERT INTO `forum_topten` VALUES ('22', null, null, '444', '44', '不知火舞和古娜哪个更厉害一点~(火焰VS冰冻~)  ', null, null, null, '2', null);
INSERT INTO `forum_topten` VALUES ('3', null, null, '244', '55', '说说我眼中的Shinn!  ', null, null, null, '3', null);
INSERT INTO `forum_topten` VALUES ('33', null, null, '34', '55', '强袭自由~无限正义~命运高达~以及武器 介绍  ', null, null, null, '3', null);
INSERT INTO `forum_topten` VALUES ('4', null, null, '4444', '55', '!!!免费拿Q币、网游点卡、动漫周边！ ', null, null, null, '4', null);
INSERT INTO `forum_topten` VALUES ('44', null, null, '5444', '66', '朽木白哉详细资料~~[精品]  ', null, null, null, '4', null);
INSERT INTO `forum_topten` VALUES ('5', null, null, '44', '22', '日本动漫届权威人士的死神人气投票结果  ', null, null, null, '5', null);
INSERT INTO `forum_topten` VALUES ('55', null, null, '614', '555', 'ggggggggggggggggg', null, null, null, '5', null);
INSERT INTO `forum_topten` VALUES ('6', null, null, '144', '52', 'hhhhhhhhhhhhh', null, null, null, '6', null);
INSERT INTO `forum_topten` VALUES ('66', null, null, '144', '1', 'hhhhhhhhhhhhhhhhhh', null, null, null, '6', null);
INSERT INTO `forum_user_info` VALUES ('111', '111', '111', '111', '111', '111', '111', null, null, null, null, null, null, null, null, null, null, null, '0', 'normal', null, '2007-01-12 10:32:00', null, null, null, null, null, null, null, '0', '0');
INSERT INTO `forum_user_info` VALUES ('1111', '张锋', 'lgstar', 'whatisyourname', 'zhang', 'zhang', 'lgstar@163.com', null, null, null, null, null, null, null, null, null, null, null, '20', 'normal', null, '2006-12-06 09:25:47', null, '2007-01-02 13:46:33', null, null, null, null, null, '0', '0');
INSERT INTO `forum_user_info` VALUES ('3', '3', '3', '2', '2', '2', '2', null, null, null, null, null, null, null, null, null, null, null, '88', 'normal', null, '2006-12-27 14:24:33', null, '2006-12-31 13:46:50', null, null, null, null, null, '0', '0');
INSERT INTO `forum_user_info` VALUES ('admin', 'admin', 'admin', 'jsdkfjf', 'fjfsdlkfj', 'fjsdlfkj', 'admin@hotmail.com', '', '', '', '', '', null, '2007-01-12 11:03:03', null, null, '1', null, '10', 'manager', null, '2006-12-04 09:25:42', '', '2007-01-01 13:46:28', null, '', null, null, null, '0', '0');
INSERT INTO `forum_user_info` VALUES ('lgstar', 'lgstar', 'lgstar', 'whatisyourname', 'zhang', 'zhang', 'lgstar@163.com', null, null, null, null, null, null, null, null, null, null, null, '36', 'normal', null, '2006-12-05 09:25:52', null, '2007-01-04 13:46:37', null, null, null, null, null, '0', '0');
INSERT INTO `forum_user_info` VALUES ('zhangfeng', 'zhangfeng', 'zhangfeng', 'whatisyourname', 'zhang', 'zhang', 'lgstar888@163.com', null, null, null, null, null, null, null, null, null, null, null, '923', 'normal', null, '2006-12-19 09:25:56', null, '2007-01-03 13:46:47', null, null, null, null, null, '0', '0');
INSERT INTO `sys_department` VALUES ('1', '1', 'treeRoot', '1', '沈阳市公安局');
INSERT INTO `sys_department` VALUES ('SYS_DEPARTMENT_0000000001', '科技处', '1', '1', '科技处');
INSERT INTO `sys_department` VALUES ('SYS_DEPARTMENT_0000000002', '通讯处', '1', '1', '通讯处');
INSERT INTO `sys_group` VALUES ('administrator', 'administrator', '1', '管理员组', '1');
INSERT INTO `sys_group` VALUES ('operator', '座席员', '1', '座席员组', '1');
INSERT INTO `sys_group` VALUES ('police', '警务人员', '1', '警务人员组', '1');
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000101', '领导组', '1', '领导管理权限设置', '1');
INSERT INTO `sys_key` VALUES ('1', '20');
INSERT INTO `sys_key` VALUES ('ADDRESSLISTSORT_INFO', '200');
INSERT INTO `sys_key` VALUES ('ADDRESSLIST_INFO', '160');
INSERT INTO `sys_key` VALUES ('AFICHE_INFO', '120');
INSERT INTO `sys_key` VALUES ('AGRO_DRIVER_INFO', '180');
INSERT INTO `sys_key` VALUES ('AGRO_HIRER_INFO', '220');
INSERT INTO `sys_key` VALUES ('ASSETS_OPER', '300');
INSERT INTO `sys_key` VALUES ('BOOK_BORROW_INFO', '80');
INSERT INTO `sys_key` VALUES ('CCLOG', '580');
INSERT INTO `sys_key` VALUES ('CC_LOG', '580');
INSERT INTO `sys_key` VALUES ('CHECKWORK_ABSENCE', '680');
INSERT INTO `sys_key` VALUES ('CHECKWORK_INFO', '40');
INSERT INTO `sys_key` VALUES ('DEPARTMENT_STATION_INFO', '40');
INSERT INTO `sys_key` VALUES ('DRIVER_CLASS_INFO', '20');
INSERT INTO `sys_key` VALUES ('EMAIL_BOX', '120');
INSERT INTO `sys_key` VALUES ('EMPLOYEE_INFO', '200');
INSERT INTO `sys_key` VALUES ('FILE_INFO', '580');
INSERT INTO `sys_key` VALUES ('FLOW_INSTANCE', '300');
INSERT INTO `sys_key` VALUES ('FORUMTOPIC', '120');
INSERT INTO `sys_key` VALUES ('FORUM_COLLECTION', '100');
INSERT INTO `sys_key` VALUES ('FORUM_LOG', '200');
INSERT INTO `sys_key` VALUES ('FORUM_POSTS', '680');
INSERT INTO `sys_key` VALUES ('FORUM_USER_INFO', '100');
INSERT INTO `sys_key` VALUES ('GOV_ANTITHESES_INFO', '40');
INSERT INTO `sys_key` VALUES ('GOV_DRIVER_INFO', '160');
INSERT INTO `sys_key` VALUES ('GOV_DRIVER_OPER', '40');
INSERT INTO `sys_key` VALUES ('GOV_MOTOR_INFO', '160');
INSERT INTO `sys_key` VALUES ('INADJUNCT_INFO', '40');
INSERT INTO `sys_key` VALUES ('INEMAIL_INFO', '600');
INSERT INTO `sys_key` VALUES ('LEAVEWORD_INFO', '140');
INSERT INTO `sys_key` VALUES ('NEWS_ARTICLE', '300');
INSERT INTO `sys_key` VALUES ('OPERATOR_WORK_INFO', '480');
INSERT INTO `sys_key` VALUES ('PLAN_DETAIL', '40');
INSERT INTO `sys_key` VALUES ('PLAN_INFO', '220');
INSERT INTO `sys_key` VALUES ('POLICECALLINFIREWALL', '140');
INSERT INTO `sys_key` VALUES ('POLICE_CALLIN', '1120');
INSERT INTO `sys_key` VALUES ('POLICE_CALLIN_INFO', '1060');
INSERT INTO `sys_key` VALUES ('POLICE_FUZZ_INFO', '100');
INSERT INTO `sys_key` VALUES ('PORT_COMPARE', '60');
INSERT INTO `sys_key` VALUES ('RESOURCE_INFO', '160');
INSERT INTO `sys_key` VALUES ('RESOURCE_USE', '240');
INSERT INTO `sys_key` VALUES ('SYNOD_NOTE', '420');
INSERT INTO `sys_key` VALUES ('SYSRIGHTUSER', '3680');
INSERT INTO `sys_key` VALUES ('SYS_DEPARTMENT', '20');
INSERT INTO `sys_key` VALUES ('SYS_GROUP', '180');
INSERT INTO `sys_key` VALUES ('SYS_LOG', '60');
INSERT INTO `sys_key` VALUES ('SYS_MODULE', '860');
INSERT INTO `sys_key` VALUES ('SYS_ROLE', '200');
INSERT INTO `sys_key` VALUES ('SYS_TREE', '380');
INSERT INTO `sys_key` VALUES ('TEST_KEY', '60');
INSERT INTO `sys_key` VALUES ('WORKFLOW_INSTANCE', '220');
INSERT INTO `sys_module` VALUES ('1', '/ETforum/forum/forumList.do?method=toForumList&moduleId=1', 'NoRight.gif', '1', 'treeRoot', '1', '卓越科技论坛系统', '1', '0');
INSERT INTO `sys_module` VALUES ('19', '/ETforum/sys/tree.do?method=loadTree', 'NoRight.gif', '19', '3', '1', '类型管理', '7', '0');
INSERT INTO `sys_module` VALUES ('3', null, 'NoRight.gif', '3', '1', '1', '系统管理', '1', '0');
INSERT INTO `sys_module` VALUES ('4', '/ETforum/sys/user/UserOper.do?method=toMain', 'NoRight.gif', '4', '3', '1', '用户管理', '1', '0');
INSERT INTO `sys_module` VALUES ('5', '/ETforum/sys/module.do?method=loadTree', 'NoRight.gif', '5', '3', '1', '模块管理', '2', '0');
INSERT INTO `sys_module` VALUES ('6', '/ETforum/sys/group/GroupOper.do?method=toMain', 'NoRight.gif', '6', '3', '1', '组管理', '3', '0');
INSERT INTO `sys_module` VALUES ('7', '/ETforum/sys/role/Role.do?method=toRoleMain', 'NoRight.gif', '7', '3', '1', '角色管理', '4', '0');
INSERT INTO `sys_module` VALUES ('8', '/ETforum/sys/log/LogOper.do?method=toMain', 'NoRight.gif', '8', '3', '1', '日志管理', '5', '0');
INSERT INTO `sys_module` VALUES ('9', '/ETforum/sys/dep.do?method=loadTree', 'NoRight.gif', '911', '3', '1', '部门管理', '6', '0');
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000621', '/ETforum/forum/forumList.do?method=toForumList&moduleId=2', 'NoRight.gif', '自由讨论区', '1', '1', '技术区', 'SYS_MODULE_0000000621', '1');
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000641', '', 'NoRight.gif', '灌水区', 'SYS_MODULE_0000000621', '1', '灌水区', 'SYS_MODULE_0000000641', '1');
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000663', '', 'NoRight.gif', '灌水22222', 'SYS_MODULE_0000000621', '1', '灌水2', 'SYS_MODULE_0000000663', '1');
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000681', '', 'NoRight.gif', '', '1', '1', '常用查询', 'SYS_MODULE_0000000681', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000682', '/ETforum/forum/postQuery.do?method=toMySendPostList', 'NoRight.gif', '', 'SYS_MODULE_0000000681', '1', '我的发帖', 'SYS_MODULE_0000000682', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000683', '/ETforum/forum/postQuery.do?method=toMyAnswerPostList', 'NoRight.gif', '', 'SYS_MODULE_0000000681', '1', '我的回帖', 'SYS_MODULE_0000000683', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000684', '/ETforum/forum/postQuery.do?method=toMySavePostList', 'NoRight.gif', '', 'SYS_MODULE_0000000681', '1', '我的收藏', 'SYS_MODULE_0000000684', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000701', '/ETforum/forum/postQuery.do?method=toBestNewPostList', 'NoRight.gif', '', 'SYS_MODULE_0000000681', '1', '最新贴子查询', 'SYS_MODULE_0000000701', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000702', '/ETforum/forum/postQuery.do?method=toForumHostGroomList', 'NoRight.gif', '', 'SYS_MODULE_0000000681', '1', '版主推荐', 'SYS_MODULE_0000000702', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000703', '/ETforum/forum/postQuery.do?method=toNetfriendGroomList', 'NoRight.gif', '', 'SYS_MODULE_0000000681', '1', '网友推荐', 'SYS_MODULE_0000000703', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000704', '/ETforum/forum/postQuery.do?method=toAnswerTopTenList', 'NoRight.gif', '', 'SYS_MODULE_0000000681', '1', '回复十大', 'SYS_MODULE_0000000704', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000705', '/ETforum/forum/postQuery.do?method=toSendPostRankList', 'NoRight.gif', '', 'SYS_MODULE_0000000681', '1', '发贴排行榜', 'SYS_MODULE_0000000705', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000706', '/ETforum/forum/postQuery.do?method=toPointRankList', 'NoRight.gif', '', 'SYS_MODULE_0000000681', '1', '积分排行榜', 'SYS_MODULE_0000000706', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000721', '/ETforum/forum/userOper/register.do?method=toDeclare', 'NoRight.gif', '', '1', '1', '注册用户', 'SYS_MODULE_0000000721', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000741', '/ETforum/forum/userOper/register.do?method=toUpdate', 'NoRight.gif', '', '1', '1', '修改用户信息', 'SYS_MODULE_0000000741', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000761', '/ETforum/forum/userInfo.do?method=toUserInfoList', 'NoRight.gif', '', 'SYS_MODULE_0000000681', '1', '好友列表', 'SYS_MODULE_0000000761', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000781', '/ETforum/forum/moduleManager.do?method=toModuleList', 'NoRight.gif', '', '3', '1', '论坛后台模块管理', 'SYS_MODULE_0000000781', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000801', '/ETforum/forum/replace.do?method=toReplace', 'NoRight.gif', '', '3', '1', '替换/限制管理', 'SYS_MODULE_0000000801', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000821', '/ETforum/forum/forumLog.do?method=toLogMain', 'NoRight.gif', '', '3', '1', '论坛日志管理', 'SYS_MODULE_0000000821', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000841', '/ETforum/forum/userManager.do?method=toUserMain', 'NoRight.gif', '', '3', '1', '论坛用户管理', 'SYS_MODULE_0000000841', null);
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003161', 'operator', '1');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003164', 'police', '1');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003641', 'administrator', 'SYS_MODULE_0000000683');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003642', 'administrator', 'SYS_MODULE_0000000702');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003643', 'administrator', 'SYS_MODULE_0000000781');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003644', 'administrator', '3');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003646', 'administrator', 'SYS_MODULE_0000000841');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003649', 'administrator', '6');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003650', 'administrator', '1');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003652', 'administrator', 'SYS_MODULE_0000000704');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003653', 'administrator', '5');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003654', 'administrator', 'SYS_MODULE_0000000821');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003656', 'administrator', '4');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003657', 'administrator', 'SYS_MODULE_0000000761');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003658', 'administrator', 'SYS_MODULE_0000000705');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003659', 'administrator', 'SYS_MODULE_0000000706');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003661', 'administrator', 'SYS_MODULE_0000000621');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003662', 'administrator', 'SYS_MODULE_0000000681');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003663', 'administrator', '7');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003664', 'administrator', 'SYS_MODULE_0000000741');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003665', 'administrator', 'SYS_MODULE_0000000703');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003666', 'administrator', 'SYS_MODULE_0000000682');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003670', 'administrator', 'SYS_MODULE_0000000701');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003671', 'administrator', 'SYS_MODULE_0000000801');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003672', 'administrator', 'SYS_MODULE_0000000641');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003673', 'administrator', 'SYS_MODULE_0000000663');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003675', 'administrator', 'SYS_MODULE_0000000721');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003676', 'administrator', 'SYS_MODULE_0000000684');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003677', 'administrator', '9');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003678', 'administrator', '19');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000003679', 'administrator', '8');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000002954', '001', '1');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003401', '000', 'SYS_MODULE_0000000683');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003402', '000', 'SYS_MODULE_0000000702');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003403', '000', '3');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003407', '000', '6');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003408', '000', '1');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003410', '000', 'SYS_MODULE_0000000704');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003411', '000', '5');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003413', '000', '4');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003414', '000', 'SYS_MODULE_0000000705');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003415', '000', 'SYS_MODULE_0000000706');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003417', '000', 'SYS_MODULE_0000000621');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003418', '000', 'SYS_MODULE_0000000681');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003419', '000', '7');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003420', '000', 'SYS_MODULE_0000000703');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003421', '000', 'SYS_MODULE_0000000682');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003425', '000', 'SYS_MODULE_0000000701');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003426', '000', 'SYS_MODULE_0000000641');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003427', '000', 'SYS_MODULE_0000000663');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003429', '000', 'SYS_MODULE_0000000721');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003430', '000', 'SYS_MODULE_0000000684');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003431', '000', '9');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003432', '000', '19');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003433', '000', '8');
INSERT INTO `sys_role` VALUES ('administrator', 'administrator', '1', 'ouussa', '1');
INSERT INTO `sys_role` VALUES ('operator', '坐席员', '1', '管理员\r\n', '1');
INSERT INTO `sys_role` VALUES ('operatorManager', '坐席长', '1', '坐席长', '1');
INSERT INTO `sys_role` VALUES ('police', '警务人员', '1', '警务人员', '1');
INSERT INTO `sys_station_info` VALUES ('DEPARTMENT_STATION_INFO_0000000001', '3', '321', '321', '321', '');
INSERT INTO `sys_station_info` VALUES ('DEPARTMENT_STATION_INFO_0000000003', '5', '54', '432', '43', '');
INSERT INTO `sys_station_info` VALUES ('DEPARTMENT_STATION_INFO_0000000004', '3', '432', '432', '432', '');
INSERT INTO `sys_station_info` VALUES ('DEPARTMENT_STATION_INFO_0000000021', '2', '经理', '小王', '经理', '');
INSERT INTO `sys_tree` VALUES ('1', 'treeRoot', null, null, '参数', null, null, '1', '1', '1', '0');
INSERT INTO `sys_tree` VALUES ('14', '13', 'bloodParam', null, 'A', '1', null, '1', null, null, '0');
INSERT INTO `sys_tree` VALUES ('15', '13', 'bloodParam', null, 'B', '2', null, '1', null, null, '0');
INSERT INTO `sys_tree` VALUES ('16', '13', 'bloodParam', null, 'O', '3', null, '1', null, null, '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000101', '1', '', 'callcenter', '呼叫中心', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000102', 'SYS_TREE_0000000123', '', 'server_socket_port', '12000', null, 'idu', '1', '', '服务器socket通讯端口', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000103', 'SYS_TREE_0000000125', '', 'client_socket_port', '12001', null, 'idu', '1', '', '客户端socket通讯端口', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000105', 'SYS_TREE_0000000109', '', 'server_ip', '127.0.0.1', null, 'idu', '1', '', '服务器ip地址', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000106', 'SYS_TREE_0000000122', '', 'client_ip_list', '工控机ip地址', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000109', 'SYS_TREE_0000000121', '', '', '服务器ip地址', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000110', 'SYS_TREE_0000000106', '', 'client_ip', '192.168.1.201', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000121', 'SYS_TREE_0000000101', '', 'server', '服务器', null, 'idu', '1', '', '服务器相关参数', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000122', 'SYS_TREE_0000000101', '', 'client', '工控机', null, 'idu', '1', '', '客户机相关参数', '0');
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
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000181', 'SYS_TREE_0000000101', '', 'errorinhale', '呼入错误类型', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000182', 'SYS_TREE_0000000181', '', 'inhalesuccess', '呼入成功', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000183', 'SYS_TREE_0000000181', '', 'blacklist', '黑名单', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000184', 'SYS_TREE_0000000181', '', 'inhalefail', '呼入失败', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000201', 'SYS_TREE_0000000101', '', 'policeType', '警察种类', null, 'idu', '1', '', '警察种类', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000202', 'SYS_TREE_0000000201', '', 'policeType_bus', '交警', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000203', 'SYS_TREE_0000000201', '', 'policeType_people', '民警', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000221', 'SYS_TREE_0000000101', '', 'portType', '端口类型', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000222', 'SYS_TREE_0000000221', '', 'portType_in', '内线端口', null, 'idu', '1', '', '0-内线端口', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000223', 'SYS_TREE_0000000221', '', 'portType_out', '外线端口', null, 'idu', '1', '', '1-外线端口', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000224', 'SYS_TREE_0000000101', '', 'portState', '端口状态', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000225', 'SYS_TREE_0000000224', '', 'portState_unInstall', '未安装', null, 'idu', '1', '', '0 未安装', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000226', 'SYS_TREE_0000000224', '', 'portState_damage', '损坏', null, 'idu', '1', '', '1-损坏', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000227', 'SYS_TREE_0000000224', '', 'portState_close', '关闭', null, 'idu', '1', '', '2-关闭', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000228', 'SYS_TREE_0000000224', '', 'portState_normal', '正常', null, 'idu', '1', '', '3-正常', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000241', '1', '', 'forum_name', '卓越科技论坛系统', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000242', 'SYS_TREE_0000000241', '', '', '积分管理', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000243', 'SYS_TREE_0000000242', '', '', '发帖加分', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000244', 'SYS_TREE_0000000243', '', 'forum_point_addSendPoint', '5', null, 'idu', '1', '', '发帖加分', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000245', 'SYS_TREE_0000000242', '', '', '回帖加分', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000246', 'SYS_TREE_0000000245', '', 'forum_point_addAnswerPoint', '1', null, 'idu', '1', '', '回帖加分', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000247', 'SYS_TREE_0000000242', '', '', '加精加分', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000248', 'SYS_TREE_0000000247', '', 'forum_point_joinPrimePoint', '50', null, 'idu', '1', '', '加入精华加分', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000249', 'SYS_TREE_0000000242', '', '', '置顶加分', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000250', 'SYS_TREE_0000000249', '', 'forum_piont_putTopPoint', '100', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000251', 'SYS_TREE_0000000242', '', '', '删贴扣分', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000252', 'SYS_TREE_0000000251', '', 'forum_piont_cutDeletePostPoint', '5', null, 'idu', '1', '', '删贴扣分', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000261', 'SYS_TREE_0000000241', '', '', '常用查询类型定义', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000262', 'SYS_TREE_0000000261', '', '', '最新帖子', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000263', 'SYS_TREE_0000000261', '', '', '版主推荐', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000264', 'SYS_TREE_0000000261', '', '', '网友推荐', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000265', 'SYS_TREE_0000000261', '', '', '回复十大', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000266', 'SYS_TREE_0000000261', '', '', '发帖排行', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000267', 'SYS_TREE_0000000261', '', '', '积分排行', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000268', 'SYS_TREE_0000000262', '', 'forum_query_bestNewPost', '1', null, 'idu', '1', '', '最新帖子类型', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000269', 'SYS_TREE_0000000263', '', 'forum_query_forumHostGroom', '2', null, 'idu', '1', '', '版主推荐类型', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000270', 'SYS_TREE_0000000264', '', 'forum_query_netfriendGroom', '3', null, 'idu', '1', '', '网友推荐类型', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000271', 'SYS_TREE_0000000265', '', 'forum_query_answerTopTen', '4', null, 'idu', '1', '', '回复十大类型', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000272', 'SYS_TREE_0000000266', '', 'forum_query_sendPostRank', '5', null, 'idu', '1', '', '发帖排行类型', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000273', 'SYS_TREE_0000000267', '', 'forum_query_pointRand', '6', null, 'idu', '1', '', '积分排行类型', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000281', 'SYS_TREE_0000000241', '', 'forum_sort', '论坛类型', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000282', 'SYS_TREE_0000000281', '', '0', '父论坛', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000283', 'SYS_TREE_0000000281', '', '1', '子论坛', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000301', 'SYS_TREE_0000000241', '', 'date_type', '搜索时间类型', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000302', 'SYS_TREE_0000000301', '', 'day', '一天内的帖子', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000303', 'SYS_TREE_0000000301', '', 'week', '一个星期内的帖子', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000304', 'SYS_TREE_0000000301', '', 'month', '一个月内的帖子', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000305', 'SYS_TREE_0000000301', '', 'twoMonth', '两个月内的帖子', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000306', 'SYS_TREE_0000000301', '', 'threeMonth', '三个月内的帖子', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000321', 'SYS_TREE_0000000241', '', 'log_oper_area', '日志操作区域', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000322', 'SYS_TREE_0000000321', '', 'forum_log', '论坛日志管理', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000361', 'SYS_TREE_0000000322', '', 'forum_log_flag', '1', null, 'idu', '1', '', '', '0');
INSERT INTO `sys_user` VALUES ('000', 'C6F057B86584942E415435FFB1FA93D4', '000', 'operator', 'operatorManager', 'SYS_DEPARTMENT_0000000002', '1', '', null);
INSERT INTO `sys_user` VALUES ('001', 'DC5C7986DAEF50C1E02AB09B442EE34F', '001', 'operator', 'operator', 'SYS_DEPARTMENT_0000000002', '1', '', null);
INSERT INTO `sys_user` VALUES ('002', '93DD4DE5CDDBA2C733C65F233097F05A', '002', 'operator', 'operator', 'SYS_DEPARTMENT_0000000002', '1', '', null);
INSERT INTO `sys_user` VALUES ('003', 'E88A49BCCDE359F0CABB40DB83BA6080', '003', 'operator', 'operator', 'SYS_DEPARTMENT_0000000002', '1', '', null);
INSERT INTO `sys_user` VALUES ('004', '11364907CF269DD2183B64287156072A', '004', 'operator', 'operator', 'SYS_DEPARTMENT_0000000002', '1', '', null);
INSERT INTO `sys_user` VALUES ('005', 'CE08BECC73195DF12D99D761BFBBA68D', '005', 'operator', 'operator', 'SYS_DEPARTMENT_0000000002', '1', '', null);
INSERT INTO `sys_user` VALUES ('006', '568628E0D993B1973ADC718237DA6E93', '006', 'operator', 'operator', 'SYS_DEPARTMENT_0000000002', '1', '', null);
INSERT INTO `sys_user` VALUES ('007', '9E94B15ED312FA42232FD87A55DB0D39', '007', 'operator', 'operator', 'SYS_DEPARTMENT_0000000002', '1', '', null);
INSERT INTO `sys_user` VALUES ('008', 'A13EE062EFF9D7295BFC800A11F33704', '008', 'operator', 'operator', 'SYS_DEPARTMENT_0000000002', '1', '', null);
INSERT INTO `sys_user` VALUES ('009', 'DC5E819E186F11EF3F59E6C7D6830C35', '009', 'operator', 'operator', 'SYS_DEPARTMENT_0000000002', '1', '', null);
INSERT INTO `sys_user` VALUES ('010', 'EA20A043C08F5168D4409FF4144F32E2', '010', 'operator', 'operator', 'SYS_DEPARTMENT_0000000002', '1', '', null);
INSERT INTO `sys_user` VALUES ('011', '84EB13CFED01764D9C401219FAA56D53', '011', 'operator', 'operator', 'SYS_DEPARTMENT_0000000002', '1', '', null);
INSERT INTO `sys_user` VALUES ('012', 'D2490F048DC3B77A457E3E450AB4EB38', '012', 'operator', 'operator', 'SYS_DEPARTMENT_0000000002', '1', '', null);
INSERT INTO `sys_user` VALUES ('1', 'C4CA4238A0B923820DCC509A6F75849B', '1', 'administrator', 'administrator', 'SYS_DEPARTMENT_0000000001', '1', '', '0');
INSERT INTO `sys_user` VALUES ('2', 'C81E728D9D4C2F636F067F89CC14862C', '2', 'administrator', 'administrator', 'SYS_DEPARTMENT_0000000001', '1', '', '0');
INSERT INTO `sys_user` VALUES ('3', 'ECCBC87E4B5CE2FE28308FD9F2A7BAF3', '3', 'administrator', 'administrator', 'SYS_DEPARTMENT_0000000001', '1', '', '0');
INSERT INTO `sys_user` VALUES ('admin', '21232F297A57A5A743894A0E4A801FC3', 'admin', 'administrator', 'administrator', '1', '1', '', null);
INSERT INTO `sys_user_info` VALUES ('000', '', '1', '', '', null, '2006-10-16', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('001', '', '1', '', '', null, '2006-10-16', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('002', '', '1', '', '', null, '2006-10-16', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('003', '', '1', '', '', null, '2006-10-16', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('004', '', '1', '', '', null, '2006-10-16', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('005', '', '1', '', '', null, '2006-10-16', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('006', '', '1', '', '', null, '2006-10-16', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('007', '', '1', '', '', null, '2006-10-16', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('008', '', '1', '', '', null, '2006-10-16', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('009', '', '1', '', '', null, '2006-10-16', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('010', '', '1', '', '', null, '2006-10-16', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('011', '', '1', '', '', null, '2006-10-16', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('012', '', '1', '', '', null, '2006-10-16', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('1', '', '1', '', '', null, '2006-12-04', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_user_info` VALUES ('2', '', '1', '', '', null, '2006-12-04', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_user_info` VALUES ('3', '', '1', '', '', null, '2006-12-04', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_user_info` VALUES ('admin', '', '1', '1', '', null, '2006-10-16', '', '', null, '', '', '', '', '', '', '', '', '', null);
