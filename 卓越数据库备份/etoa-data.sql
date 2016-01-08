/*
MySQL Data Transfer
Source Host: 192.168.1.200
Source Database: etoa
Target Host: 192.168.1.200
Target Database: etoa
Date: 2007-3-25 13:44:03
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for addresslist_info
-- ----------------------------
DROP TABLE IF EXISTS `addresslist_info`;
CREATE TABLE `addresslist_info` (
  `id` varchar(50) default NULL,
  `user_id` varchar(50) default NULL,
  `sort` varchar(50) default NULL,
  `name` varchar(50) default NULL,
  `appellation` varchar(50) default NULL,
  `company` varchar(50) default NULL,
  `station` varchar(50) default NULL,
  `personal_email` varchar(50) default NULL,
  `business_email` varchar(50) default NULL,
  `other_email` varchar(50) default NULL,
  `personal_phone` varchar(50) default NULL,
  `business_phone` varchar(50) default NULL,
  `fax` varchar(50) default NULL,
  `mobile` varchar(50) default NULL,
  `beep_pager` varchar(50) default NULL,
  `other_phone` varchar(50) default NULL,
  `business_address` varchar(50) default NULL,
  `business_post` varchar(50) default NULL,
  `personal_address` varchar(50) default NULL,
  `personal_post` varchar(50) default NULL,
  `birthday` datetime default NULL,
  `remark` varchar(200) default NULL,
  `sign` varchar(20) default NULL,
  `company_page` varchar(200) default NULL,
  `personal_page` varchar(200) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for addresslistsort_info
-- ----------------------------
DROP TABLE IF EXISTS `addresslistsort_info`;
CREATE TABLE `addresslistsort_info` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `sort_explain` mediumtext,
  `sort_name` varchar(50) NOT NULL,
  `sort_mark` varchar(10) default NULL,
  PRIMARY KEY  (`id`),
  KEY `sysuser_id` (`user_id`),
  CONSTRAINT `sysuser_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for afiche_info
-- ----------------------------
DROP TABLE IF EXISTS `afiche_info`;
CREATE TABLE `afiche_info` (
  `id` varchar(50) NOT NULL COMMENT '标识Id',
  `afiche_title` varchar(255) default NULL COMMENT '标题',
  `afiche_user` varchar(50) default NULL COMMENT '撰写人／部门Id',
  `afiche_type` varchar(20) default NULL COMMENT '公告类型',
  `afiche_info` mediumtext COMMENT '公告内容',
  `afiche_sendto` varchar(50) default NULL COMMENT '公告发送部门',
  `create_time` datetime default NULL COMMENT '创建时间',
  `begin_time` datetime default NULL COMMENT '始时间开',
  `end_time` datetime default NULL COMMENT '结束时间',
  `del_sign` varchar(10) default NULL COMMENT '删除标记',
  `remark` mediumtext COMMENT '备注',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `afiche_info_index` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='公告信息表';

-- ----------------------------
-- Table structure for assets_info
-- ----------------------------
DROP TABLE IF EXISTS `assets_info`;
CREATE TABLE `assets_info` (
  `assets_Id` varchar(50) NOT NULL COMMENT '标识Id',
  `assets_name` varchar(50) default NULL COMMENT '资产名称',
  `assets_num` int(11) default NULL COMMENT '资产数量',
  `assets_withfor` varchar(255) default NULL COMMENT '所属部门',
  `assets_type` varchar(10) default NULL COMMENT '资产类型-固定资产（0）、消耗品（-1）',
  `assets_batch` varchar(255) default NULL,
  `remark` mediumtext COMMENT '备注',
  PRIMARY KEY  (`assets_Id`),
  UNIQUE KEY `assets_info_index` (`assets_Id`),
  KEY `assets_info_foreign` (`assets_batch`),
  CONSTRAINT `assets_info_ibfk_1` FOREIGN KEY (`assets_batch`) REFERENCES `assets_oper` (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='资产信息表';

-- ----------------------------
-- Table structure for assets_oper
-- ----------------------------
DROP TABLE IF EXISTS `assets_oper`;
CREATE TABLE `assets_oper` (
  `oper_id` varchar(50) NOT NULL COMMENT '标识Id',
  `oper_code` varchar(50) default NULL COMMENT '资产编号',
  `oper_name` varchar(50) default NULL COMMENT '资产名称',
  `oper_type` varchar(10) default NULL,
  `oper_time` datetime default NULL COMMENT '时间',
  `assets_price` double(10,0) default NULL COMMENT '单价',
  `assets_num` int(10) default NULL COMMENT '??',
  `in_company` varchar(255) default NULL COMMENT 'in单位',
  `in_people` varchar(30) default NULL COMMENT 'in经手人',
  `out_company` varchar(255) default NULL COMMENT 'out单位',
  `out_people` varchar(30) default NULL COMMENT 'out经手人',
  `sign` varchar(10) default NULL COMMENT '标记',
  `remark` mediumtext COMMENT '备注',
  PRIMARY KEY  (`oper_id`),
  UNIQUE KEY `assets_oper_index` (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='资产业务表';

-- ----------------------------
-- Table structure for book_borrow_info
-- ----------------------------
DROP TABLE IF EXISTS `book_borrow_info`;
CREATE TABLE `book_borrow_info` (
  `id` varchar(50) NOT NULL,
  `book_id` varchar(50) default NULL COMMENT '图书编号',
  `book_user` varchar(50) default NULL COMMENT '书图借阅人',
  `borrow_time` datetime default NULL,
  `return_time` datetime default NULL COMMENT '动作(1,表示借书 2,表示还书)',
  PRIMARY KEY  (`id`),
  KEY `book_info_id` (`book_id`),
  CONSTRAINT `book_info_id` FOREIGN KEY (`book_id`) REFERENCES `book_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for book_info
-- ----------------------------
DROP TABLE IF EXISTS `book_info`;
CREATE TABLE `book_info` (
  `id` varchar(50) NOT NULL,
  `book_name` varchar(100) default NULL COMMENT '图书名称',
  `book_author` varchar(50) default NULL COMMENT '图书作者',
  `book_concern` varchar(50) default NULL COMMENT '出版社',
  `book_num` varchar(50) default NULL COMMENT '图书编号',
  `book_price` varchar(50) default NULL COMMENT '图书价格',
  `book_type` varchar(50) default NULL COMMENT '图书类别',
  `introduce` mediumtext COMMENT '书图介绍',
  `buy_time` datetime default NULL,
  `note_time` datetime default NULL,
  `borrow_state` varchar(50) default NULL COMMENT '阅借状态(1,已借出 2,未借出 3,已丢失)',
  `remark` mediumtext,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for checkwork_absence
-- ----------------------------
DROP TABLE IF EXISTS `checkwork_absence`;
CREATE TABLE `checkwork_absence` (
  `id` varchar(50) NOT NULL COMMENT '标识Id',
  `user_id` varchar(50) NOT NULL,
  `employee_id` varchar(50) default NULL COMMENT '人员Id',
  `employee_depart` varchar(50) default NULL COMMENT '申请人员部门',
  `absence_type` varchar(30) default NULL COMMENT '缺勤类型-(病假、外出、出差等)',
  `absence_date` datetime default NULL,
  `absence_time` varchar(20) default NULL COMMENT '添加时间',
  `absence_reason` mediumtext COMMENT '缺勤缘由',
  `begin_time` datetime default NULL COMMENT '开始时间',
  `s_time` varchar(20) default NULL,
  `end_time` datetime default NULL COMMENT '结束时间',
  `e_time` varchar(20) default NULL,
  `absenceState` varchar(10) default '0' COMMENT '生效标记(0未生效，1生效)',
  `remark` mediumtext COMMENT '备注',
  `sign_date` datetime default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `checkWork_absence_index` (`id`),
  KEY `checkwork_absence_foreign` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='缺勤信息表';

-- ----------------------------
-- Table structure for checkwork_info
-- ----------------------------
DROP TABLE IF EXISTS `checkwork_info`;
CREATE TABLE `checkwork_info` (
  `id` varchar(50) NOT NULL COMMENT '标识',
  `user_id` varchar(50) default NULL,
  `employee_id` varchar(50) default NULL COMMENT '人员Id',
  `depart_id` varchar(50) default NULL COMMENT '部门Id',
  `check_date` datetime default NULL COMMENT '考勤日期',
  `begin_time` varchar(255) default NULL COMMENT '上班时间',
  `end_time` varchar(255) default NULL COMMENT '下班时间',
  `remark` mediumtext COMMENT '备注',
  `sign_mark` varchar(20) default NULL COMMENT '补签标记',
  `repair_date` datetime default NULL COMMENT '补签日期',
  `repair_time` varchar(20) default NULL,
  `sign_date` datetime default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `checkWork_info_index` (`id`),
  KEY `employee_id` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='考勤记录表';

-- ----------------------------
-- Table structure for checkwork_time
-- ----------------------------
DROP TABLE IF EXISTS `checkwork_time`;
CREATE TABLE `checkwork_time` (
  `id` varchar(50) default NULL,
  `onduty_time` varchar(20) default NULL COMMENT '班上时间',
  `offduty_time` varchar(20) default NULL COMMENT '下班时间'
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for driver_class_info
-- ----------------------------
DROP TABLE IF EXISTS `driver_class_info`;
CREATE TABLE `driver_class_info` (
  `class_id` varchar(20) NOT NULL COMMENT '班级Id',
  `class_name` varchar(50) default NULL COMMENT '名 称',
  `class_time` datetime default NULL COMMENT '招生时间',
  `class_opentime` datetime default NULL COMMENT '开班时间',
  `class_address` varchar(100) default NULL COMMENT '上课地点',
  `exam_address` varchar(100) default NULL COMMENT '考试地点',
  `class_examtime` datetime default NULL COMMENT '考试时间',
  `class_sign` varchar(20) default NULL COMMENT '标记',
  `class_remark` varchar(255) default NULL COMMENT '备注',
  PRIMARY KEY  (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='InnoDB free: 11264 kB; InnoDB free: 11264 kB; InnoDB free: 1';

-- ----------------------------
-- Table structure for driver_grade_info
-- ----------------------------
DROP TABLE IF EXISTS `driver_grade_info`;
CREATE TABLE `driver_grade_info` (
  `id` varchar(20) NOT NULL,
  `class_id` varchar(20) default '',
  `class_stuId` varchar(20) default NULL,
  `grade_name` varchar(50) default NULL,
  `grade_value` varchar(10) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='InnoDB free: 9216 kB';

-- ----------------------------
-- Table structure for driver_student_info
-- ----------------------------
DROP TABLE IF EXISTS `driver_student_info`;
CREATE TABLE `driver_student_info` (
  `id` varchar(20) NOT NULL,
  `class_id` varchar(20) default NULL,
  `class_stuId` varchar(20) default NULL,
  `class_grade` varchar(10) default NULL,
  `class_opentime` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='InnoDB free: 9216 kB';

-- ----------------------------
-- Table structure for email_box
-- ----------------------------
DROP TABLE IF EXISTS `email_box`;
CREATE TABLE `email_box` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) default NULL,
  `name` varchar(100) default NULL COMMENT '邮箱名',
  `smtp` varchar(100) default NULL COMMENT 'smtp地址',
  `pop3` varchar(100) default NULL COMMENT 'pop3地址',
  `pop3_user` varchar(100) default NULL,
  `pop_password` varchar(100) default NULL,
  `remark` varchar(200) default NULL,
  `smtp_user` varchar(100) default NULL,
  `smtp_password` varchar(100) default NULL,
  `smtp_port` int(11) default NULL,
  `smtp_ssl` varchar(20) default NULL,
  `pop3_port` int(11) default NULL,
  `pop3_ssl` varchar(20) default NULL,
  `email_address` varchar(100) default NULL COMMENT '邮件地址',
  `return_adress` varchar(100) default NULL COMMENT '回复地址',
  `tag_smtp_ssl` varchar(20) default NULL,
  `tag_pop3_ssl` varchar(20) default NULL,
  `tag_validate` varchar(50) default NULL,
  PRIMARY KEY  (`id`),
  KEY `email_box_fk_user` (`user_id`),
  CONSTRAINT `email_box_fk_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for employee_info
-- ----------------------------
DROP TABLE IF EXISTS `employee_info`;
CREATE TABLE `employee_info` (
  `id` varchar(50) NOT NULL COMMENT '标识Id',
  `name` varchar(30) default NULL COMMENT '姓名',
  `age` int(11) default NULL COMMENT '年龄',
  `sex` varchar(2) default NULL COMMENT '性别',
  `birth` date default NULL COMMENT '出生年月',
  `nation` varchar(50) default NULL COMMENT '民族',
  `blighty` varchar(255) default NULL COMMENT '籍贯',
  `polity` varchar(50) default NULL COMMENT '政治面貌',
  `marriage` varchar(6) default NULL COMMENT '婚否',
  `lover` varchar(30) default NULL COMMENT '配偶姓名',
  `code` varchar(30) default NULL COMMENT '身份证号码',
  `mobile` varchar(20) default NULL COMMENT '手机',
  `home_phone` varchar(20) default NULL COMMENT '家庭电话',
  `company_phone` varchar(20) default NULL COMMENT '单位电话',
  `other_phone` varchar(20) default NULL,
  `own_email` varchar(100) default NULL COMMENT '个人电子邮件',
  `other_email` varchar(100) default NULL COMMENT '其他电子邮件',
  `home_addr` varchar(500) default NULL COMMENT '家庭住址',
  `company_addr` varchar(500) default NULL COMMENT '单位地址',
  `post_code` varchar(20) default NULL COMMENT '司公邮编号码',
  `home_post` varchar(20) default NULL COMMENT '家庭邮政号码',
  `study_level` varchar(50) default NULL COMMENT '最高学历',
  `alma_mater` varchar(255) default NULL COMMENT '毕业院校',
  `department` varchar(50) default NULL COMMENT '所在部门',
  `station` varchar(50) default NULL COMMENT '现任职务',
  `recode` mediumtext COMMENT '履历',
  `del_sign` varchar(2) default NULL COMMENT '删除标记',
  `remark` mediumtext COMMENT '备注',
  `employee_id` varchar(200) default NULL,
  `is_leave` varchar(50) default NULL,
  `begin_work_time` datetime default NULL,
  `end_work_time` datetime default NULL,
  `end_work_remark` varchar(500) default NULL,
  `employee_photo` varchar(100) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `employee_info_index` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='员工基本信息表';

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` varchar(50) NOT NULL default '',
  `folder_id` mediumtext COMMENT '文档路径',
  `folder_type` varchar(50) default NULL COMMENT '文档类型',
  `folder_name` varchar(255) default NULL COMMENT '文档名',
  `folder_code` varchar(255) default NULL COMMENT '文档号',
  `folder_word` varchar(50) default NULL COMMENT '文档关键字',
  `create_time` datetime default NULL COMMENT '创建时间',
  `update_time` datetime default NULL COMMENT '更新日期',
  `folder_version` varchar(255) default NULL COMMENT '版本号',
  `folder_sign` varchar(20) default NULL COMMENT '生效位',
  `remark` mediumtext COMMENT '备注',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `file_info_index` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='文件信息表';

-- ----------------------------
-- Table structure for file_manager
-- ----------------------------
DROP TABLE IF EXISTS `file_manager`;
CREATE TABLE `file_manager` (
  `id` varchar(50) NOT NULL,
  `parent_id` varchar(50) default NULL,
  `name` varchar(200) default NULL,
  `is_file` varchar(50) default NULL,
  `file_code` varchar(200) default NULL,
  `file_key_word` varchar(500) default NULL,
  `create_time` datetime default NULL,
  `update_time` datetime default NULL,
  `file_edition` varchar(50) default NULL,
  `is_available` varchar(50) default NULL,
  `remark` varchar(500) default NULL,
  `file_size` varchar(50) default NULL,
  `file_type` varchar(50) default NULL,
  `file_upload_man` varchar(50) default NULL,
  `file_detail` varchar(500) default NULL,
  `file_check_man` varchar(50) default NULL,
  `file_name` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for file_right
-- ----------------------------
DROP TABLE IF EXISTS `file_right`;
CREATE TABLE `file_right` (
  `id` varchar(50) NOT NULL,
  `userId` varchar(50) default NULL,
  `fileId` varchar(50) default NULL,
  `right_new` varchar(50) default NULL,
  `right_manager` varchar(50) default NULL,
  `right_view` varchar(50) default NULL,
  PRIMARY KEY  (`id`),
  KEY `file_right_fk1` (`userId`),
  KEY `file_right_fk2` (`fileId`),
  CONSTRAINT `file_right_fk1` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `file_right_fk2` FOREIGN KEY (`fileId`) REFERENCES `file_manager` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for flow_define
-- ----------------------------
DROP TABLE IF EXISTS `flow_define`;
CREATE TABLE `flow_define` (
  `ID` varchar(50) NOT NULL,
  `work_flow_def` varchar(50) default NULL,
  `FLOW_DEF_ID` varchar(50) default NULL COMMENT '定义id',
  `FLOW_DEF_TYPE` varbinary(1) default NULL COMMENT '类型',
  `FLOW_DEF_NAME` varchar(50) default NULL COMMENT '汉语名',
  `FLOW_DEF_ACTION` varchar(100) default NULL COMMENT '动作',
  `FLOW_DEF_ACTOR` varchar(50) default NULL COMMENT '默认处理人',
  `FLOW_DEF_NEXT_ACTOR` varchar(50) default NULL COMMENT '下一步执行者',
  PRIMARY KEY  (`ID`),
  KEY `ForeignKey1` (`FLOW_DEF_ACTOR`),
  KEY `新建索引` (`FLOW_DEF_ID`),
  KEY `work_flow_def` (`work_flow_def`),
  CONSTRAINT `flow_define_ibfk_1` FOREIGN KEY (`work_flow_def`) REFERENCES `workflow_define` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for flow_instance
-- ----------------------------
DROP TABLE IF EXISTS `flow_instance`;
CREATE TABLE `flow_instance` (
  `message` varchar(500) default NULL,
  `ID` varchar(50) NOT NULL,
  `FLOW_INSTANCE_ID` varchar(50) default NULL,
  `FLOW_TASK_ID` varchar(50) default NULL,
  `FLOW_DEF_ID` varchar(50) default NULL,
  `FLOW_TYPE` varchar(50) default NULL,
  `FLOW_ACTOR` varchar(50) default NULL,
  `FLOW_OPERATOR` varchar(50) default NULL,
  `IF_SUCCESS` varchar(1) default NULL,
  `BEGIN_TIME` datetime default NULL,
  `END_TIME` datetime default NULL,
  PRIMARY KEY  (`ID`),
  KEY `NewForeignKey` (`FLOW_DEF_ID`),
  CONSTRAINT `NewForeignKey` FOREIGN KEY (`FLOW_DEF_ID`) REFERENCES `flow_define` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for flow_next_step
-- ----------------------------
DROP TABLE IF EXISTS `flow_next_step`;
CREATE TABLE `flow_next_step` (
  `Id` varchar(50) NOT NULL,
  `FLOW_DEF_ID` varchar(50) default NULL,
  `flow_next_step` varchar(50) default NULL,
  `flow_next_step_name` varchar(50) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `flow_next_step_fk` (`FLOW_DEF_ID`),
  CONSTRAINT `flow_next_step_fk` FOREIGN KEY (`FLOW_DEF_ID`) REFERENCES `flow_define` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for flow_right
-- ----------------------------
DROP TABLE IF EXISTS `flow_right`;
CREATE TABLE `flow_right` (
  `Id` varchar(50) NOT NULL,
  `FLOW_ACTOR` varchar(50) default NULL,
  `OA_USER` varchar(50) default NULL,
  `OA_ROLE` varchar(50) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_ballot_detail
-- ----------------------------
DROP TABLE IF EXISTS `forum_ballot_detail`;
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
DROP TABLE IF EXISTS `forum_collection`;
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
DROP TABLE IF EXISTS `forum_exprience_level`;
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
DROP TABLE IF EXISTS `forum_forbidden`;
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
DROP TABLE IF EXISTS `forum_friend`;
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
DROP TABLE IF EXISTS `forum_log`;
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
DROP TABLE IF EXISTS `forum_post_content`;
CREATE TABLE `forum_post_content` (
  `id` varchar(50) NOT NULL,
  `content` longtext,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_posts
-- ----------------------------
DROP TABLE IF EXISTS `forum_posts`;
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
DROP TABLE IF EXISTS `forum_posts_weapon`;
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
DROP TABLE IF EXISTS `forum_report`;
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
DROP TABLE IF EXISTS `forum_topic`;
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
DROP TABLE IF EXISTS `forum_topten`;
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
DROP TABLE IF EXISTS `forum_user_info`;
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
  `oa_id` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for forum_weapon
-- ----------------------------
DROP TABLE IF EXISTS `forum_weapon`;
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
-- Table structure for gov_antitheses_info
-- ----------------------------
DROP TABLE IF EXISTS `gov_antitheses_info`;
CREATE TABLE `gov_antitheses_info` (
  `Id` varchar(30) NOT NULL,
  `driver_id` varchar(30) NOT NULL,
  `motor_id` varchar(30) NOT NULL,
  `remark` varchar(50) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `gov_antitheses_index` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='InnoDB free: 11264 kB; InnoDB free: 11264 kB; InnoDB free: 1';

-- ----------------------------
-- Table structure for gov_driver_info
-- ----------------------------
DROP TABLE IF EXISTS `gov_driver_info`;
CREATE TABLE `gov_driver_info` (
  `driver_id` varchar(30) NOT NULL COMMENT '??id?',
  `driver_name` varchar(20) default NULL COMMENT '????',
  `driver_sex` int(4) default NULL COMMENT '? ?',
  `driver_date` date default NULL COMMENT '????',
  `driver_qq` varchar(20) default NULL COMMENT 'qq??',
  `driver_email` varchar(50) default NULL COMMENT 'email',
  `driver_address` varchar(100) default NULL COMMENT '????',
  `driver_phone` varchar(20) default NULL COMMENT '????',
  `driver_company` varchar(50) default NULL COMMENT '????',
  `driver_image` varchar(100) default NULL COMMENT '????',
  `driver_licence` varchar(50) default NULL COMMENT '?????',
  `driver_code` varchar(50) default NULL COMMENT '?????',
  `driver_type` varchar(10) default NULL COMMENT '????(0,-1,1)',
  `driver_time` datetime default NULL COMMENT '????',
  `del_mark` varchar(10) default NULL COMMENT '????',
  `remark` varchar(100) default NULL COMMENT '????',
  PRIMARY KEY  (`driver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='??????????';

-- ----------------------------
-- Table structure for gov_driver_oper
-- ----------------------------
DROP TABLE IF EXISTS `gov_driver_oper`;
CREATE TABLE `gov_driver_oper` (
  `id` varchar(50) NOT NULL default '0',
  `driver_id` varchar(50) default NULL,
  `motor_id` varchar(50) default NULL,
  `oper_type` varchar(11) default NULL,
  `oper_old` varchar(50) default NULL,
  `oper_new` varchar(50) default NULL,
  `oper_time` datetime default NULL,
  `oper_manager` varchar(20) default NULL,
  `oper_user` varchar(20) default NULL,
  `oper_remark` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for gov_motor_info
-- ----------------------------
DROP TABLE IF EXISTS `gov_motor_info`;
CREATE TABLE `gov_motor_info` (
  `motor_id` varchar(30) NOT NULL,
  `driver_id` varchar(50) default NULL,
  `motor_type` varchar(50) default NULL,
  `motor_code` varchar(50) default NULL,
  `motor_plate` varchar(20) default NULL,
  `motor_date` datetime default NULL,
  `motor_year` int(10) default NULL,
  `motor_color` varchar(20) default NULL,
  `motor_home` varchar(50) default NULL,
  `motor_sign` varchar(20) default NULL,
  `motor_reject` varchar(11) default NULL,
  `motor_image` varchar(100) default NULL,
  `motor_remark` varchar(100) default NULL,
  `motor_check` varchar(2) default NULL,
  PRIMARY KEY  (`motor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for gov_motor_oper
-- ----------------------------
DROP TABLE IF EXISTS `gov_motor_oper`;
CREATE TABLE `gov_motor_oper` (
  `oper_id` varchar(20) NOT NULL default '',
  `oper_name` varchar(30) default NULL,
  `oper_time` datetime default NULL,
  `oper_mark` varchar(50) default NULL,
  PRIMARY KEY  (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for gov_user_info
-- ----------------------------
DROP TABLE IF EXISTS `gov_user_info`;
CREATE TABLE `gov_user_info` (
  `user_id` varchar(30) NOT NULL default '',
  `user_pwd` varchar(30) default NULL,
  `user_name` varchar(20) default NULL,
  `user_sex` int(4) default NULL,
  `user_date` datetime default NULL,
  `user_qq` varchar(20) default NULL,
  `user_email` varchar(50) default NULL,
  `user_address` varchar(50) default NULL,
  `user_phone` varchar(20) default NULL,
  `user_mobile` varchar(20) default NULL,
  `user_company` varchar(50) default NULL,
  `user_duty` varchar(50) default NULL,
  `user_image` varchar(100) default NULL,
  `del_mark` varchar(10) default NULL,
  `user_remark` varchar(100) default NULL,
  PRIMARY KEY  (`user_id`(1))
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for handset_note_info
-- ----------------------------
DROP TABLE IF EXISTS `handset_note_info`;
CREATE TABLE `handset_note_info` (
  `id` varchar(50) NOT NULL default '',
  `handset_num` mediumtext COMMENT '手机号码',
  `handset_info` mediumtext,
  `send_state` varchar(10) default NULL,
  `send_time` datetime default NULL,
  `appoint_send_time` datetime default NULL,
  `del_sign` varchar(10) default NULL COMMENT '删除标记',
  `remark` mediumtext,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for inadjunct_info
-- ----------------------------
DROP TABLE IF EXISTS `inadjunct_info`;
CREATE TABLE `inadjunct_info` (
  `id` varchar(50) NOT NULL default '' COMMENT '标识Id',
  `email_id` varchar(50) default NULL COMMENT '主表Id',
  `adjunct_addr` mediumtext COMMENT '文档路径',
  `adjunct_type` varchar(30) default NULL COMMENT '文档类别',
  `adjunct_name` varchar(50) default NULL COMMENT '文档名',
  `adjunct_key` varchar(50) default NULL COMMENT '关键字',
  `del_time` datetime default NULL COMMENT '删除时间',
  `create_time` datetime default NULL COMMENT '创建日期',
  `oper_user` varchar(30) default NULL COMMENT '操作人员',
  `remark` mediumtext COMMENT '备注',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `inadjunct_info_index` (`id`),
  KEY `inadjenct_info_foreign` (`email_id`),
  CONSTRAINT `indadunct_info_foreign` FOREIGN KEY (`email_id`) REFERENCES `inemail_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='附件表';

-- ----------------------------
-- Table structure for inemail_capacity
-- ----------------------------
DROP TABLE IF EXISTS `inemail_capacity`;
CREATE TABLE `inemail_capacity` (
  `id` varchar(50) NOT NULL COMMENT '标识Id',
  `user_id` varchar(50) default NULL COMMENT '人员Id',
  `capacity_num` double(20,0) default NULL COMMENT '邮箱容量',
  `use_num` double(20,0) default NULL COMMENT '使用容量',
  `leave_num` double(20,0) default NULL COMMENT '可用容量',
  `remark` mediumtext COMMENT '备注',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `inemail_capacity_index` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='邮件容量表';

-- ----------------------------
-- Table structure for inemail_info
-- ----------------------------
DROP TABLE IF EXISTS `inemail_info`;
CREATE TABLE `inemail_info` (
  `id` varchar(50) NOT NULL COMMENT '标识Id',
  `send_user` varchar(50) default NULL COMMENT '信息创建人Id',
  `take_user` varchar(50) default NULL COMMENT '接受人Id',
  `take_list` mediumtext COMMENT '接收人列表',
  `copy_list` mediumtext COMMENT '抄送人列表',
  `secret_list` mediumtext COMMENT '暗送人列表',
  `email_title` varchar(255) default NULL COMMENT '信息标题',
  `email_info` mediumtext COMMENT '信息内容',
  `create_time` datetime default NULL COMMENT '创建时间',
  `send_time` datetime default NULL COMMENT '发送时间',
  `send_type` varchar(10) default NULL COMMENT '邮件发送类型-(抄送、暗送等)',
  `email_type` varchar(10) default NULL COMMENT '1，发送成功 2,发送失败',
  `email_sign` varchar(10) default NULL COMMENT '1，成功 2，不成功',
  `issend` varchar(10) default NULL COMMENT '是否发送(1，已发送,2未发送)',
  `oper_sign` varchar(10) default NULL COMMENT '是否读过标记-(读过0，未读-1)',
  `inorout` varchar(10) default NULL COMMENT '识标是内网还是外网邮件(1内网2外网)',
  `emailbox_id` varchar(50) default NULL,
  `del_sign` varchar(10) default NULL COMMENT '删除标记',
  `remark` mediumtext COMMENT '备注',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `inemail_info_index` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='发、收邮件表';

-- ----------------------------
-- Table structure for leaveword_info
-- ----------------------------
DROP TABLE IF EXISTS `leaveword_info`;
CREATE TABLE `leaveword_info` (
  `id` varchar(50) NOT NULL default '',
  `user_id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `leave_date` datetime NOT NULL,
  `title` varchar(50) default NULL,
  `content` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for message_capacity
-- ----------------------------
DROP TABLE IF EXISTS `message_capacity`;
CREATE TABLE `message_capacity` (
  `id` varchar(50) NOT NULL default '',
  `f_id` varchar(50) default NULL,
  `f_name` varchar(100) default NULL,
  `f_address` varchar(200) default NULL,
  `f_size` varchar(100) default NULL,
  `f_space` varchar(100) default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`),
  KEY `message_f_id` (`f_id`),
  CONSTRAINT `message_f_id` FOREIGN KEY (`f_id`) REFERENCES `message_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for message_help_info
-- ----------------------------
DROP TABLE IF EXISTS `message_help_info`;
CREATE TABLE `message_help_info` (
  `id` varchar(50) NOT NULL,
  `parent_id` varchar(50) default NULL,
  `isget` varchar(10) default NULL,
  `ismain` varchar(10) default NULL,
  `isread` varchar(10) default NULL,
  `senduser` varchar(100) default NULL,
  `takeuser` varchar(50) default NULL,
  `taketime` date default NULL,
  `isdel` varchar(10) default NULL,
  `del_time` date default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`),
  KEY `message_id` (`parent_id`),
  CONSTRAINT `message_id` FOREIGN KEY (`parent_id`) REFERENCES `message_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for message_info
-- ----------------------------
DROP TABLE IF EXISTS `message_info`;
CREATE TABLE `message_info` (
  `id` varchar(50) NOT NULL,
  `sendtime` date default NULL,
  `senduser` varchar(100) default NULL,
  `title` varchar(200) default NULL,
  `content` mediumtext,
  `isdel` varchar(10) default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for news_article
-- ----------------------------
DROP TABLE IF EXISTS `news_article`;
CREATE TABLE `news_article` (
  `articleid` varchar(50) NOT NULL,
  `classid` varchar(50) default NULL,
  `title` varchar(50) default NULL,
  `author` varchar(50) default NULL,
  `copyfrom` varchar(100) default NULL,
  `editor` varchar(20) default NULL,
  `keyvalue` varchar(20) default NULL,
  `hits` varchar(20) default NULL,
  `updatetime` datetime default NULL,
  `hot` varchar(1) default NULL,
  `ontop` varchar(1) default NULL,
  `elite` varchar(1) default NULL,
  `passed` varchar(1) default NULL,
  `content` mediumtext,
  `includepic` varchar(1) default NULL,
  `defaultpicurl` varchar(100) default NULL,
  `uploadfiles` varchar(100) default NULL,
  `deleted` varchar(1) default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`articleid`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for news_format_detail
-- ----------------------------
DROP TABLE IF EXISTS `news_format_detail`;
CREATE TABLE `news_format_detail` (
  `id` varchar(50) NOT NULL,
  `format_id` varchar(50) default NULL,
  `format_name` varchar(50) default NULL,
  `format_val` varchar(255) default NULL,
  `del_mark` int(10) default NULL,
  `remark` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `format_foreign` (`format_id`),
  CONSTRAINT `format_foreign` FOREIGN KEY (`format_id`) REFERENCES `news_format_info` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='InnoDB free: 11264 kB; InnoDB free: 9216 kB; (`format_id`) R';

-- ----------------------------
-- Table structure for news_format_info
-- ----------------------------
DROP TABLE IF EXISTS `news_format_info`;
CREATE TABLE `news_format_info` (
  `class_id` varchar(50) NOT NULL default '0',
  `format_type` int(50) default NULL,
  `del_mark` int(11) default NULL,
  `remark` varchar(255) default NULL,
  PRIMARY KEY  (`class_id`),
  UNIQUE KEY `format_info_key` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for plan_detail
-- ----------------------------
DROP TABLE IF EXISTS `plan_detail`;
CREATE TABLE `plan_detail` (
  `id` varchar(50) NOT NULL COMMENT '标识Id',
  `plan_id` varchar(50) NOT NULL COMMENT '主表Id',
  `begin_time` datetime default NULL COMMENT '开始时间',
  `end_time` datetime default NULL COMMENT '结束时间',
  `plan_info` mediumtext COMMENT '计划内容',
  `carry_state` varchar(50) default NULL COMMENT '实施情况',
  `carry_user` varchar(50) default NULL COMMENT '实施情况填写人',
  `carry_info` mediumtext COMMENT '实施情况概述',
  `plan_sign` varchar(50) default NULL COMMENT '类型标记',
  `remark` mediumtext COMMENT '备注',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `plan_detail_index` (`id`),
  KEY `plan_detail_foreign` (`plan_id`),
  CONSTRAINT `plan_detail_foreign` FOREIGN KEY (`plan_id`) REFERENCES `plan_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='初略工作计划明晰表';

-- ----------------------------
-- Table structure for plan_info
-- ----------------------------
DROP TABLE IF EXISTS `plan_info`;
CREATE TABLE `plan_info` (
  `id` varchar(50) NOT NULL COMMENT '标识Id',
  `employee_id` varchar(50) default NULL COMMENT '计划人',
  `plan_type` varchar(50) default NULL COMMENT '计划类型',
  `plan_title` varchar(255) default NULL COMMENT '计划标题',
  `flow_id` varchar(50) default NULL,
  `plan_date` datetime default NULL COMMENT '计划制定时间',
  `remark` mediumtext COMMENT '备注',
  `begin_date` datetime default NULL,
  `end_date` datetime default NULL,
  `approve_man` varchar(50) default NULL,
  `approve_time` datetime default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `plan_info_index` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='工作计划基本主表';

-- ----------------------------
-- Table structure for resource_info
-- ----------------------------
DROP TABLE IF EXISTS `resource_info`;
CREATE TABLE `resource_info` (
  `id` varchar(50) NOT NULL,
  `resource_name` varchar(255) default NULL COMMENT '源资名称',
  `resource_code` varchar(255) default NULL COMMENT '资源编号',
  `resource_type` varchar(50) default NULL COMMENT '资源类型-(例如会议室、车辆等)',
  `resource_state` mediumtext COMMENT '自然情况-(大小、位置等／型号、司机等)',
  `principal_id` varchar(50) default NULL COMMENT '资源负责人',
  `create_date` datetime default NULL COMMENT '添加时间',
  `del_sign` varchar(2) default '0' COMMENT '除删标记',
  `remark` mediumtext COMMENT '备注',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `resource_info_index` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='资源基本信息表';

-- ----------------------------
-- Table structure for resource_use
-- ----------------------------
DROP TABLE IF EXISTS `resource_use`;
CREATE TABLE `resource_use` (
  `id` varchar(50) NOT NULL COMMENT '标识Id',
  `resource_id` varchar(50) default NULL COMMENT '资源Id',
  `date_area` datetime default NULL COMMENT '使用日期',
  `date_end` datetime default NULL COMMENT '结束日期',
  `time_area` varchar(50) default NULL COMMENT '使用时间范围',
  `principal_name` varchar(50) default NULL COMMENT '资源分配人(即 审批人)',
  `appoint_name` varchar(50) default NULL COMMENT '派车人',
  `user_name` varchar(50) default NULL COMMENT '使用者',
  `resource_state` varchar(10) default '1' COMMENT '使用状态(0-未审批；1-等待审批；2-已批准)',
  `remark` mediumtext COMMENT '备注',
  `apply_state` varchar(10) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `resource_use_index` (`id`),
  KEY `resource_id` (`resource_id`),
  CONSTRAINT `resource_use_ibfk_1` FOREIGN KEY (`resource_id`) REFERENCES `resource_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='资源使用情况表';

-- ----------------------------
-- Table structure for synod_note
-- ----------------------------
DROP TABLE IF EXISTS `synod_note`;
CREATE TABLE `synod_note` (
  `id` varchar(50) NOT NULL COMMENT '标识Id',
  `synod_date` datetime default NULL COMMENT '会议时间',
  `synod_addr` varchar(255) default NULL COMMENT '会议地点',
  `synod_owner` varchar(50) default NULL COMMENT '会议发起人',
  `synod_people` mediumtext COMMENT '会议参加人员',
  `synod_topic` varchar(255) default NULL COMMENT '会议议题',
  `synod_outline` mediumtext COMMENT '会议概要',
  `synod_file` mediumtext COMMENT '会议文档',
  `apply_time` datetime default NULL COMMENT '????',
  `flow_id` varchar(50) default NULL COMMENT '工作流实例Id',
  `remark` mediumtext COMMENT '备注',
  `exam_id` varchar(50) default NULL COMMENT '???id',
  `exam_result` varchar(50) default NULL COMMENT '????',
  `end_flag` varchar(50) default NULL COMMENT '????',
  `end_doc` text COMMENT '????',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `synod_note_index` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='会议记录表';

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
  `admin` varchar(50) default NULL,
  `is_sys` varchar(2) default NULL,
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
  `is_sys` varchar(2) default NULL,
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
  `is_sys` varchar(2) default NULL,
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
  `is_sys` varchar(2) default NULL,
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
  `is_sys` varchar(2) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
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
-- Table structure for work_new_plan
-- ----------------------------
DROP TABLE IF EXISTS `work_new_plan`;
CREATE TABLE `work_new_plan` (
  `id` varchar(50) NOT NULL,
  `parent_id` varchar(50) default NULL,
  `plan_type` varchar(50) default NULL,
  `plan_title` varchar(50) default NULL,
  `plan_create_user` varchar(50) default NULL,
  `plan_oper_user` varchar(50) default NULL,
  `create_date` datetime default NULL,
  `begin_date` datetime default NULL,
  `end_date` datetime default NULL,
  `plan_info` varchar(3000) default NULL,
  `plan_complete` varchar(3000) default NULL,
  `plan_sign` varchar(50) default NULL,
  `complete` datetime default NULL,
  `remark` varchar(900) default NULL,
  PRIMARY KEY  (`id`),
  KEY `work_new_plan_fk` (`parent_id`),
  CONSTRAINT `work_new_plan_fk` FOREIGN KEY (`parent_id`) REFERENCES `work_parent_plan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for work_parent_plan
-- ----------------------------
DROP TABLE IF EXISTS `work_parent_plan`;
CREATE TABLE `work_parent_plan` (
  `id` varchar(50) character set gb2312 NOT NULL,
  `title` varchar(300) character set gb2312 default NULL,
  `create_user` varchar(50) character set gb2312 default NULL,
  `begin_date` datetime default NULL,
  `end_date` datetime default NULL,
  `sign` varchar(50) character set gb2312 default NULL,
  `create_date` datetime default NULL,
  `remark` varchar(500) character set gb2312 default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for work_plan_info
-- ----------------------------
DROP TABLE IF EXISTS `work_plan_info`;
CREATE TABLE `work_plan_info` (
  `id` varchar(50) NOT NULL,
  `parent_id` varchar(50) default NULL,
  `create_user` varchar(50) default NULL,
  `plan_classes` varchar(50) default NULL,
  `create_time` datetime default NULL,
  `plan_beign_time` datetime default NULL,
  `plan_end_time` datetime default NULL,
  `plan_title` varchar(100) default NULL,
  `plan_domain_type` varchar(50) default NULL,
  `plan_time_type` varchar(50) default NULL,
  `plan_view_type` varchar(50) default NULL,
  `plan_subhead` varchar(100) default NULL,
  `plan_info` varchar(800) default NULL,
  `remark` varchar(500) default NULL,
  `check_id` varchar(50) default NULL,
  `del_sign` varchar(50) default NULL COMMENT '用来标实列是否被删除',
  `plan_type` varchar(50) default NULL COMMENT '判断阶段计划是否是被公开的',
  PRIMARY KEY  (`id`),
  KEY `work_plan_info_fk_c` (`plan_classes`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for work_plan_mission
-- ----------------------------
DROP TABLE IF EXISTS `work_plan_mission`;
CREATE TABLE `work_plan_mission` (
  `id` varchar(50) NOT NULL,
  `name` varchar(300) default NULL,
  `create_plan_id` varchar(50) default NULL,
  `create_time` datetime default NULL,
  `keyword` varchar(200) default NULL,
  `mission_type` varchar(50) default NULL,
  `plan_id` varchar(50) default NULL,
  `mission_classes` varchar(50) default NULL,
  `begin_time` datetime default NULL,
  `end_time` datetime default NULL,
  `mission_pri` varchar(50) default NULL,
  `mission_pri_type` varchar(50) default NULL,
  `mission_info` varchar(3000) default NULL,
  `mission_sign` varchar(50) default NULL,
  `mission_complete` varchar(3000) default NULL,
  `complete_time` datetime default NULL,
  `remark` varchar(500) default NULL,
  `check_id` varchar(50) default NULL,
  `create_user` varchar(50) default NULL,
  `planmission_sign_del` varchar(50) default NULL COMMENT '详细计划删除标记',
  PRIMARY KEY  (`id`),
  KEY `work_plan_mission_fk_c` (`create_plan_id`),
  KEY `work_plan_mission_fk_p` (`plan_id`),
  CONSTRAINT `work_plan_mission_fk_c` FOREIGN KEY (`create_plan_id`) REFERENCES `work_plan_info` (`id`),
  CONSTRAINT `work_plan_mission_fk_p` FOREIGN KEY (`plan_id`) REFERENCES `work_plan_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for workflow_define
-- ----------------------------
DROP TABLE IF EXISTS `workflow_define`;
CREATE TABLE `workflow_define` (
  `id` varchar(50) NOT NULL default '',
  `name` varchar(50) default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for workflow_instance
-- ----------------------------
DROP TABLE IF EXISTS `workflow_instance`;
CREATE TABLE `workflow_instance` (
  `id` varchar(50) NOT NULL default '',
  `define_id` varchar(50) default NULL,
  `instance_id` varchar(50) default NULL,
  PRIMARY KEY  (`id`),
  KEY `wf_ins_fk` (`define_id`),
  CONSTRAINT `wf_ins_fk` FOREIGN KEY (`define_id`) REFERENCES `workflow_define` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `addresslistsort_info` VALUES ('ADDRESSLISTSORT_INFO_0000000161', 'zhaoyifei', '11', '11', '1');
INSERT INTO `addresslistsort_info` VALUES ('ADDRESSLISTSORT_INFO_0000000282', 'zhaoyifei', '', 'dsdsd', '1');
INSERT INTO `addresslistsort_info` VALUES ('ADDRESSLISTSORT_INFO_0000000283', 'zhaoyifei', '', 'asdasdsa', '2');
INSERT INTO `addresslistsort_info` VALUES ('ADDRESSLISTSORT_INFO_0000000284', 'zhaoyifei', '', 'aaaaaa', '2');
INSERT INTO `addresslistsort_info` VALUES ('ADDRESSLISTSORT_INFO_0000000421', 'jingyuzhuo', '我的程序', '编写程序', '1');
INSERT INTO `addresslistsort_info` VALUES ('ADDRESSLISTSORT_INFO_0000000422', 'jingyuzhuo', '321321', '5a5e', '2');
INSERT INTO `addresslistsort_info` VALUES ('ADDRESSLISTSORT_INFO_0000000441', 'guxiaofeng', '', '同学', '2');
INSERT INTO `addresslistsort_info` VALUES ('ADDRESSLISTSORT_INFO_0000000461', 'yepuliang', 'r', 'r', '1');
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000142', '嘉奖公告', 'zf', 'zf', '<DIV>\r\n<P><FONT size=4>由于OA开发部全体员工的不泄努力，公司终于在昨天推出了公司OA的新版本。</FONT></P>\r\n<P><FONT size=4>在这里我代表公司向OA开发部给予嘉奖，希望其他部门都能向他们学习，共同努力使公司不断向前</FONT></P>\r\n<P><FONT size=4>进步。</FONT></P></DIV>', '', '2006-09-18 14:18:41', '2006-09-01 00:00:00', '2006-09-30 00:00:00', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000143', '共同维护公司良好的运作环境', 'zf', 'zf', '<DIV>\r\n<P><FONT size=4>由于最近公司有新员工的加入，公司不的不重申一下公司规定，希望大家共同维护公司良好的运作</FONT></P>\r\n<P><FONT size=4>环境。</FONT></P></DIV>', '', '2006-09-18 14:19:14', '2006-09-01 00:00:00', '2006-09-30 00:00:00', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000144', '开会通知', 'zf', 'zf', '<DIV>今天下午开会，不能到的请速通知。</DIV>', '', '2006-09-18 14:20:49', '2006-09-01 00:00:00', '2006-09-30 00:00:00', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000161', 'g', 'fdgd', 'fgdf', '<DIV>hfghfghfghgfhfghgfhgfghfghghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh</DIV>', '', '2006-11-17 16:01:05', '2006-11-01 00:00:00', '2006-11-15 00:00:00', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000162', 'sfs', 'dfsd', 'fdsfsdfsd', '<DIV>ffffffffffffffffffffffffffffff</DIV>', '', '2006-11-17 16:01:40', '2006-11-09 00:00:00', '2006-11-08 00:00:00', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000163', 'jj', 'jjjjjjjjjjjj', 'jjjjjjjjjjjj', '<DIV>jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj</DIV>', '', '2006-11-17 16:02:19', '2006-11-15 00:00:00', '2006-11-17 16:02:19', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000181', 'a', 'guxiaofeng', 'hh', '<DIV></DIV>', '', '2006-12-20 15:02:24', '2006-12-20 00:00:00', '2006-12-21 00:00:00', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000182', 'ttt', 'ttt', 'ttt', '<DIV></DIV>', '', '2006-12-20 15:04:52', '2006-12-19 00:00:00', '2006-12-19 00:00:00', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000201', '关于春节放假通知', '陈轶瑛', '通知', '<DIV>关于春节放假时间安排通知</DIV>\r\n<DIV>公司规定市外员工放假时间为2007年2月15日至2007年2月27日；</DIV>\r\n<DIV>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 市内员工放假时间为2007年2月16日至2007年2月27日</DIV>\r\n<DIV>请各位同事安排好时间完成春节前的各项工作，并祝同事春节愉快，万事如意！</DIV>', '', '2007-02-12 13:16:32', '2007-02-12 00:00:00', '2007-02-12 13:16:32', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000202', '春节放假通知', '陈轶瑛', '通知类', '<DIV><FONT color=#800080>关于2007年春节时间安排的通知</FONT></DIV>\r\n<DIV><FONT color=#800080></FONT>&nbsp;</DIV>\r\n<DIV>&nbsp;&nbsp;&nbsp; <FONT color=#808080>2007年春节将至，公司规定从2月16日-2月27日为春节放假时间共放假十二天，家在外地的员工可从2月15日开始放假，市内员工从2月16日开始放假，春节放假期间员工要注意安全。春节前这项时间请各位同事安排好时间完成各自目前的工作。</FONT></DIV>\r\n<DIV>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </DIV>\r\n<DIV>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <FONT style=\"BACKGROUND-COLOR: #ffffff\" color=#339966>祝大家春节愉快，万事如意！<IMG src=\"/ETOA/images/affiche/img/msn/1.gif\"></FONT></DIV>\r\n<DIV><FONT color=#339966></FONT>&nbsp;</DIV>', '', '2007-02-12 13:28:34', '2007-02-12 00:00:00', '2007-02-27 00:00:00', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000203', '3月10日周六上班', '辜晓峰', '', '<DIV></DIV>', '', '2007-03-09 15:57:17', '2007-03-09 00:00:00', '2007-03-09 00:00:00', 'N', null);
INSERT INTO `assets_info` VALUES ('zkhuali-danrenchuang-001', '单人床', '1', '2', '0', 'ASSETS_OPER_0000000161', '');
INSERT INTO `assets_info` VALUES ('zkhuali-danrenchuang-002', '单人床', '1', '1', '0', 'ASSETS_OPER_0000000161', '');
INSERT INTO `assets_info` VALUES ('zkhuali-guding-002', '桌子', '1', '1', '0', 'ASSETS_OPER_0000000181', '');
INSERT INTO `assets_info` VALUES ('zkhuali-jisuanji-003', '???', '1', '1', '0', 'ASSETS_OPER_0000000121', '');
INSERT INTO `assets_info` VALUES ('zkhuali-jisuanji-004', '???', '1', '1', '0', 'ASSETS_OPER_0000000121', '');
INSERT INTO `assets_info` VALUES ('zkhuali-jisuanji-005', '???', '1', '1', '0', 'ASSETS_OPER_0000000121', '');
INSERT INTO `assets_info` VALUES ('zkhuali-jisuanji-006', '???', '1', '1', '0', 'ASSETS_OPER_0000000121', '');
INSERT INTO `assets_info` VALUES ('zkhuali-jisuanji-007', '???', '1', '1', '0', 'ASSETS_OPER_0000000121', '');
INSERT INTO `assets_info` VALUES ('zkhuali-jisuanji-01', '???', '1', '3', '0', 'ASSETS_OPER_0000000121', '');
INSERT INTO `assets_info` VALUES ('zkhuali-shouzhi-001', 'shouzhi', '990', '4', '-1', 'ASSETS_OPER_0000000123', '');
INSERT INTO `assets_info` VALUES ('zkhuali002', '???', '1', '2', '0', 'ASSETS_OPER_0000000121', '');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000121', 'zkhuali001', '???', 'mairu', '2006-05-16 00:00:00', '2000', '10', '????', '??', '???', '???', '1', '????????');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000122', 'zkhuali001', '???', 'fenpei', '2006-05-16 00:00:00', '2000', '1', '', '??', '', '???', '', '');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000123', 'zkhuali002', 'shouzi', 'mairu', '2006-05-16 00:00:00', '1', '1000', 'zkhuali', 'wangxiaoyan', 'unknow', 'unknow', '1', '>_<');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000124', 'zkhuali002', 'shouzi', 'fenpei', '2006-05-16 00:00:00', '1', '10', 'zkhuali', 'wangxiaoyan', 'zkhuali', 'zhangfeng', '', 'hualala...hualala...');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000141', 'zkhuali001', '???', 'fenpei', '2006-05-16 00:00:00', '2000', '1', '', '', '', '', '', '');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000161', 'zkhuali-guding-001', '单人床', 'mairu', '2006-05-17 00:00:00', '500', '2', '中科华利', '王建', '不清楚', '不知道', '1', '睡觉用的呗');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000162', 'zkhuali-guding-001', '单人床', 'fenpei', '2006-05-17 00:00:00', '500', '1', '中科华利', '张丰', '中科华利', '王建', '', '');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000181', 'zkhuali-zhuozi-001', '桌子', 'mairu', '2006-05-17 00:00:00', '200', '2', '中科华利', '王建', '中科华利', '旱季均', '1', '');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000201', 'zkhuali-zhuozi-001', '桌子', 'baofei', '2006-05-17 00:00:00', '200', '1', '', '', '', '', '', '');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000221', 'zkhuali001', '???', 'fenpei', '2006-05-26 00:00:00', '2000', '1', '研发', '小白', '不清楚', '不知道', '', '777');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000241', '76', '5765', 'zhuanru', '2006-08-15 00:00:00', '7657', '67', '65', '765', '77', '657', '1', '65');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000261', '321', '321', 'zhuanru', '2006-08-22 00:00:00', '32', '32', '321', '312', '312', '321', '1', '312');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000262', '999', '999', 'zhuanru', '2006-08-22 00:00:00', '999', '999', '999', '999', '999', '999', '1', '999');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000263', '111', '111', 'zhuanru', '2006-08-22 00:00:00', '111', '111', '111', '111', '111', '111', '1', '111');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000281', '654', '654', 'zhuanru', '2006-08-22 00:00:00', '6546', '56', '546', '456', '546', '546', '1', '546');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000282', '7657', '765', 'zhuanru', '2006-08-22 00:00:00', '765', '765', '76', '576', '7', '657', '1', '56');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000301', 'zkhuali-guding-001', '单人床', 'fenpei', '2006-09-16 00:00:00', '500', '1', '', '', '', '', '', '');
INSERT INTO `assets_oper` VALUES ('ASSETS_OPER_0000000321', 'A0002', 'A0002', 'zhuanru', '2006-09-19 00:00:00', '15', '1', '1', '1', '1', '1', '1', '1');
INSERT INTO `book_borrow_info` VALUES ('BOOK_BORROW_INFO_0000000262', 'EMAIL_BOX_0000000262', '1', '2006-12-27 00:00:00', null);
INSERT INTO `book_borrow_info` VALUES ('BOOK_BORROW_INFO_0000000263', 'EMAIL_BOX_0000000263', '1', '2006-12-27 00:00:00', null);
INSERT INTO `book_borrow_info` VALUES ('BOOK_BORROW_INFO_0000000264', 'EMAIL_BOX_0000000264', 'EMPLOYEE_INFO_0000000202', '2006-12-27 00:00:00', null);
INSERT INTO `book_info` VALUES ('EMAIL_BOX_0000000262', 'spring框架高级编程', 'Rod Johnson', '机械工业出版社', '001', '59.00', 'SYS_TREE_0000000082', '框架高级编程', '2006-12-27 00:00:00', '2006-12-27 00:00:00', '1', '');
INSERT INTO `book_info` VALUES ('EMAIL_BOX_0000000263', 'JAVA2核心技术卷2', 'Cay S.Horstmann', '机械工业出版社', '002', '108', 'SYS_TREE_0000000082', '', '2006-12-27 00:00:00', '2006-12-27 00:00:00', '1', '');
INSERT INTO `book_info` VALUES ('EMAIL_BOX_0000000264', '精通Java Web 动态图表编程', '唐桓', '电子工业出版社', '003', '55', 'SYS_TREE_0000000082', 'Java 概述，绘图基础，图表编程', '2006-12-27 00:00:00', '2006-12-27 00:00:00', '1', '');
INSERT INTO `checkwork_absence` VALUES ('CHECKWORK_ABSENCE_0000000895', 'yy', 'EMPLOYEE_INFO_0000000202', '2', '2', '2006-09-18 14:33:31', null, '外出', '2006-09-17 14:33:00', null, '2006-09-22 14:33:00', null, null, null, '2006-09-18 14:33:31');
INSERT INTO `checkwork_absence` VALUES ('CHECKWORK_ABSENCE_0000000896', 'yy', 'EMPLOYEE_INFO_0000000201', '2', '1', '2006-09-18 14:36:44', null, '', '2006-09-17 08:36:00', null, '2006-09-21 12:36:00', null, null, null, '2006-09-18 14:36:44');
INSERT INTO `checkwork_absence` VALUES ('CHECKWORK_ABSENCE_0000000904', 'yy', 'EMPLOYEE_INFO_0000000201', '2', '1', '2006-09-18 15:59:28', null, '', '2006-09-17 15:59:00', null, '2006-09-12 15:59:00', null, null, null, '2006-09-18 15:59:28');
INSERT INTO `checkwork_absence` VALUES ('CHECKWORK_ABSENCE_0000000908', 'yy', 'EMPLOYEE_INFO_0000000202', '2', '2', '2006-09-18 16:08:20', null, '', '2006-09-17 00:00:00', null, '2006-09-20 00:00:00', null, null, null, '2006-09-18 16:08:20');
INSERT INTO `checkwork_absence` VALUES ('CHECKWORK_ABSENCE_0000000909', 'yy', '1', '2', '2', '2006-09-18 16:10:35', null, '外出购物', '2006-09-17 16:10:00', null, '2006-09-20 16:10:00', null, null, null, '2006-09-18 16:10:35');
INSERT INTO `checkwork_absence` VALUES ('CHECKWORK_ABSENCE_0000000910', 'yy', 'EMPLOYEE_INFO_0000000281', '2', '3', '2006-09-18 16:11:22', null, '旅游,去旅游,去旅游,去旅游,去旅游,去旅游,去旅游,去旅游,去旅游,去', '2006-09-05 00:00:00', null, '2006-09-20 00:00:00', null, null, null, '2006-09-18 16:11:22');
INSERT INTO `checkwork_absence` VALUES ('CHECKWORK_ABSENCE_0000000921', 'zhaoyifei', 'EMPLOYEE_INFO_0000000303', '7', '2', '2006-09-18 16:00:34', null, 'asdasdasd', '2006-09-18 15:59:00', null, '2006-09-18 16:00:00', null, null, null, '2006-09-18 16:00:34');
INSERT INTO `checkwork_absence` VALUES ('CHECKWORK_ABSENCE_0000000941', 'yy', 'EMPLOYEE_INFO_0000000202', '2', '1', '2006-09-23 16:42:09', null, '发发发', '2006-09-13 16:42:00', null, '2006-09-24 16:42:00', null, null, null, '2006-09-23 16:42:09');
INSERT INTO `checkwork_absence` VALUES ('CHECKWORK_ABSENCE_0000000961', 'yy', 'EMPLOYEE_INFO_0000000202', '2', '1', '2006-09-26 16:23:29', null, '', '2006-09-05 16:23:00', null, '2006-09-26 17:23:00', null, null, null, '2006-09-26 16:23:29');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000021', '', 'EMPLOYEE_INFO_0000000201', '2', '2006-09-04 00:00:00', '10:00', '4:00', '111', '', '2006-09-12 00:00:00', '16:20', '2006-09-10 16:20:26');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000022', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-04 00:00:00', '10:00', '4:00', '11', '', '2006-09-06 00:00:00', '16:20', '2006-09-10 16:20:39');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000023', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-05 00:00:00', '10:00', '4:00', '111', '', '2006-09-06 00:00:00', '16:20', '2006-09-10 16:20:53');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000024', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-06 00:00:00', '10:00', '4:00', '11', 'Y', '2006-09-05 00:00:00', '16:21', '2006-09-10 16:21:08');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000041', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-07 20:28:09', '7:00', '4:00', 'yif  ', 'Y', '2006-09-11 00:00:00', '20:27', '2006-09-11 20:27:09');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000101', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-26 17:06:18', '7:00', '6:00', 'yy', 'Y', '2006-09-05 00:00:00', '17:05', '2006-09-15 17:05:32');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000121', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-21 17:06:10', '7:00', '6:00', '感冒', 'Y', '2006-09-23 00:00:00', '16:49', '2006-09-23 16:49:25');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000122', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-20 17:06:06', '7:00', '6:00', '', '', '2006-09-13 00:00:00', '16:50', '2006-09-23 16:50:43');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000141', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-10 00:00:00', '7:00', '6:00', '起来晚了', '', '2006-09-10 00:00:00', '09:00', '2006-09-25 09:00:39');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000142', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-11 00:00:00', '10:00', '4:00', '起来碗了', '', '2006-09-12 00:00:00', '09:03', '2006-09-25 09:03:34');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000143', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-12 00:00:00', '10:00', '4:00', '起来晚了', '', '2006-09-01 00:00:00', '09:04', '2006-09-25 09:05:07');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000144', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-13 00:00:00', '10:00', '4:00', '11', '', '2006-09-12 00:00:00', '09:06', '2006-09-25 09:06:59');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000145', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-14 00:00:00', '10:00', '4:00', '撒旦  \r\n', '', '2006-09-14 00:00:00', '09:09', '2006-09-25 09:09:51');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000146', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-15 00:00:00', '10:00', '4:00', '发发发', '', '2006-09-13 00:00:00', '09:10', '2006-09-25 09:10:03');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000147', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-16 00:00:00', '10:00', '4:00', '辅导费', '', '2006-09-06 00:00:00', '09:10', '2006-09-25 09:10:13');
INSERT INTO `checkwork_info` VALUES ('CHECKWORK_INFO_0000000148', '', 'EMPLOYEE_INFO_0000000202', '2', '2006-09-17 00:00:00', '10:00', '4:00', '反对', '', '2006-09-13 00:00:00', '09:10', '2006-09-25 09:10:22');
INSERT INTO `driver_class_info` VALUES ('DRIVER_CLASS_INFO_00', '20060516机械班', '2006-05-17 00:00:00', '2006-05-16 00:00:00', '沈阳市东陵区农机局三楼', '暂定于洪区大哇乡', '2006-05-31 00:00:00', null, 'driver_class_info');
INSERT INTO `email_box` VALUES ('EMAIL_BOX_0000000081', 'liuyang', '我的163邮箱', 'smtp.163.com', 'pop.163.com', 'lgstar888', 'bystar888', '', 'lgstar888', 'bystar888', null, '', null, '', 'lgstar888@163.com', 'lgstar888@163.com', '', '', null);
INSERT INTO `email_box` VALUES ('EMAIL_BOX_0000000121', 'zhaoyifei', 'tom', 'smtp.tom.com', 'pop.tom.com', 'zhaoyifei1', '222141', '', 'zhaoyifei1', '222141', null, '', null, '', 'zhaoyifei1@tom.com', 'zhaoyifei1@tom.com', '', '', null);
INSERT INTO `email_box` VALUES ('EMAIL_BOX_0000000241', 'jingyuzhuo', 'gdf', 'gfd', 'gfd', 'g', 'gfgf', '', 'g', 'gfgf', null, '', null, '', 'g', 'fdgfd', '', '', null);
INSERT INTO `email_box` VALUES ('EMAIL_BOX_0000000242', 'jingyuzhuo', 'gfgfg', 'gf', 'gf', 'fg', 'fgf', '', 'fg', 'fgf', null, '', null, '', 'gfg', 'fgf', '', '', null);
INSERT INTO `employee_info` VALUES ('1', 'zhaoyifei', '25', '1', '1981-02-22', '', 'SYS_TREE_0000000187', '', '1', '1', '1', '1', '1', '1', null, null, null, '1', '1', '1', null, 'SYS_TREE_0000000194', '1', '2', '1', '', '1', '', '', '1', null, null, '', '/loadfile/avatar_o.jpg');
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000201', '小狠', '25', '1', '2006-09-13', '小狠', '', '党员', '1', '', '210103820715424', '', '', '', null, null, null, '', '', '', null, '', '', '2', '', '', '1', '', null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000202', '小白', '25', '1', '1981-01-01', '', '', '', '1', '', '1240145455887', '', '', '', null, null, null, '', '', '', null, '', '', '2', '', '', '1', '', '', '1', null, null, '', '');
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000221', 'yyp', '33', '1', '2006-09-12', '', '', '党员', '1', '', '21010136589632', '', '', '', null, null, null, '', '', '', null, '', '', '3', '', '', '1', '', null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000241', 'tt', '21', '1', '2006-08-31', 'tt', '', '党员', '1', '', '121212', '21212', '', '', null, null, null, '', '', '', null, '', '', '3', '', '', '1', '', null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000242', '212', '22', '1', '2006-09-12', '', '', '', '1', '', '212', '', '', '', null, null, null, '', '', '', null, '', '', '2', '', '', '1', '', '', '1', null, null, '', '');
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000243', '212', '21', '1', '2006-08-30', '', '', '党员', '1', '', '212', '', '', '', null, null, null, '', '', '', null, '', '212', '4', '21', '', '1', '', null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000244', '3333', '33', '1', '2006-09-06', '', '', '党员', '1', '', '1212', '21212', '', '', null, null, null, '', '', '', null, '', '', '2', '', '', '1', '', null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000245', '313', '44', '1', '2006-09-14', '', '', '党员', '1', '', '313', '', '', '', null, null, null, '', '', '', null, '', '', '2', '', '', '1', '', null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000246', '333', '33', '1', '2006-09-20', '', '', '党员', '1', '', '3333', '', '', '', null, null, null, '', '', '', null, '', '', '2', '', '', 'Y', '', null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000247', '31313', '33', '1', '2006-09-13', '', '', '党员', '1', '', '333', '', '', '', null, null, null, '', '', '', null, '', '', '2', '', '', '1', '', null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000261', '王键22', '55', '1', '2006-09-05', '王键22', '', '党员', '1', '', '121212', '', '', '', null, null, null, '', '', '', null, '', '', '2', '', '', '1', '', null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000281', '赵一非', '25', '1', '1981-02-22', 'SYS_TREE_0000000182', 'SYS_TREE_0000000187', 'SYS_TREE_0000000204', '1', '', '210104810222141', '', '', '', null, null, null, '', '', '', null, 'SYS_TREE_0000000194', '', '2', '', 'aaaaa', '1', '', null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000282', 'gfdfgdf', '13', '1', '2006-09-14', 'SYS_TREE_0000000183', '', 'SYS_TREE_0000000204', '1', '', '222222', '', '', '', null, null, null, '', '', '', null, 'SYS_TREE_0000000192', '', '2', '', '', '1', '', null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000301', 'fdsf', '45', '1', '2006-09-06', '', 'SYS_TREE_0000000188', '', '0', 'df', '21054166116455665', '', 'fds', '', null, null, null, 'sdf', '', '', null, 'SYS_TREE_0000000193', 'fsdf', '2', 'dsf', '', '1', '', null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000302', 'aaaaaaaaaaaaa', '50', '1', '2006-08-30', 'SYS_TREE_0000000184', 'SYS_TREE_0000000188', 'SYS_TREE_0000000204', '1', '', '21321312321312', '', '', '', null, null, null, '', '', '', null, 'SYS_TREE_0000000192', '', '2', '', '', '1', '', null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000303', 'bbbbbbbbbbbbbbbbbb', '12', '1', '2006-08-30', '', '', '', '1', '', '455354345354', '', '', '', null, null, null, '', '', '', null, '', '', '4', '', '', '1', '', null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000321', 'fdfd', '45', '1', '2006-09-13', 'SYS_TREE_0000000184', 'SYS_TREE_0000000188', 'SYS_TREE_0000000203', '1', '', '45545445', '', '', '', null, null, null, '', '', '', null, 'SYS_TREE_0000000192', '', '3', '', '', '1', '', '', '1', '2006-09-14 00:00:00', '2006-09-05 00:00:00', '', '/loadfile/avatar_o.jpg');
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000341', '白2', '22', '0', '1982-09-07', 'SYS_TREE_0000000182', 'SYS_TREE_0000000187', 'SYS_TREE_0000000205', '0', '团体', '112333', '13445678901', '2121211', '222222222', null, null, null, '一二', '沈阳', '1100111', null, 'SYS_TREE_0000000194', '沈阳', '2', '开发', '我是谁啊 ', '1', '我是谁啊', '9527', '1', '1982-09-13 00:00:00', '1982-09-22 00:00:00', '吞吞吐吐', '/loadfile/Aqua16.gif');
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000346', 'rfg', '14', '1', '2006-09-12', '', '', '', '1', '', '45254', '', '', '', null, null, null, '', '', '', null, '', '', '2', '', '', '1', '', '', '1', '2006-09-16 00:00:00', '2006-09-16 00:00:00', '', '');
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000347', '45', '45', '1', '2006-09-05', '', '', '', '1', '', '455445', '', '', '', null, null, null, '', '', '', null, '', '', '', '', '', 'Y', '', '', '1', '2006-09-16 18:04:59', '2006-09-16 18:04:59', '', '/loadfile/81_8601_f2720adad45aaf2.jpg');
INSERT INTO `employee_info` VALUES ('EMPLOYEE_INFO_0000000361', 'qqq', '11', '1', '2006-09-19', '', '', '', '1', '', '12312312312', '', '', '', null, null, null, '', '', '', null, '', '', '', '', '', 'Y', '', '', '1', '2006-09-18 15:57:17', '2006-09-18 15:57:17', '', '');
INSERT INTO `file_info` VALUES ('FILE_INFO_0000000443', '../../upload/20060531032007312.doc', '办公', 'kkkkkkkk', '7777', 'FILE_INFO_0000000443', '2006-05-31 00:00:00', '2006-05-31 00:00:00', '1.0', 'N', '````````````````');
INSERT INTO `file_info` VALUES ('FILE_INFO_0000000445', '../../upload/20060531034000578.doc', '办公', 'kkkkkkkk', '7777', 'FILE_INFO_0000000443', '2006-05-31 00:00:00', '2006-05-31 00:00:00', '2.0', 'Y', '````````````````');
INSERT INTO `file_info` VALUES ('FILE_INFO_0000000484', '../../upload/20060531054220812.doc', '会议', '21212', '2121345', 'FILE_INFO_0000000484', '2006-05-31 00:00:00', '2006-05-31 00:00:00', '1.0', 'Y', '2121212');
INSERT INTO `file_info` VALUES ('FILE_INFO_0000000502', '../../upload/20060601085625968.txt', '会议', '556', '556', 'FILE_INFO_0000000502', '2006-06-01 00:00:00', '2006-06-01 00:00:00', '1.0', 'Y', '');
INSERT INTO `file_info` VALUES ('FILE_INFO_0000000507', '../../upload/20060601091009109.txt', '会议', '113', '113', 'FILE_INFO_0000000507', '2006-06-01 00:00:00', '2006-06-01 00:00:00', '1.0', 'Y', '');
INSERT INTO `file_info` VALUES ('FILE_INFO_0000000521', '../../upload/20060601091156171.doc', '会议', '111', '2344', 'FILE_INFO_0000000521', '2006-06-01 00:00:00', '2006-06-01 00:00:00', '1.0', 'Y', '111');
INSERT INTO `file_info` VALUES ('FILE_INFO_0000000541', '../../upload/20060601014356171.doc', '办公', '44', '444', 'FILE_INFO_0000000541', '2006-06-01 00:00:00', '2006-06-01 00:00:00', '1.0', 'W', '44');
INSERT INTO `file_info` VALUES ('FILE_INFO_0000000561', '../../upload/20060817013530625.doc', '办公', 'OA错误', '2039', 'FILE_INFO_0000000561', '2006-08-17 00:00:00', '2006-08-17 00:00:00', '1.0', 'W', '这个是对于OA错误的一个提示，以后可能还有很多错误，要陆续的添加。');
INSERT INTO `file_manager` VALUES ('1', 'treeRoot', '卓越科技在线文档', '0', '', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `file_manager` VALUES ('2', '1', '项目文档', '0', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `file_manager` VALUES ('3', '1', '公司规章', '0', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `file_manager` VALUES ('4', '1', '技术书籍', '0', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `flow_define` VALUES ('1', '1', 'exam', 'c', '考试管理流程', null, 'examOper', 'examOper');
INSERT INTO `flow_define` VALUES ('10', '6', 'updateplan', 't', '修改计划', '/oa/privy/plan.do?method=info&type=update', 'planuser', 'planchecker');
INSERT INTO `flow_define` VALUES ('11', '7', 'applyConference', 't', '申请会议', null, 'Conferenceproposer', 'Conferenceratifier');
INSERT INTO `flow_define` VALUES ('12', '7', 'checkupConference', 't', '审查会议', '/oa/assissant/conference/conferOper.do?method=toLoad&type=exam', 'Conferenceratifier', 'Conferenceallocatee');
INSERT INTO `flow_define` VALUES ('13', '7', 'distributeboardroom', 't', '分配会议室', '/oa/meetingManager.do?method=toAdpplyPage', 'Conferenceallocatee', null);
INSERT INTO `flow_define` VALUES ('14', '7', 'updateConference', 't', '修改会议申请', '/oa/assissant/conference/conferOper.do?method=toLoad&type=u', 'Conferenceproposer', 'Conferenceratifier');
INSERT INTO `flow_define` VALUES ('15', '7', 'conferenceFeedback', 't', '会议回执', '/oa/assissant/conference/conferOper.do?method=toLoad&type=end', 'Conferenceproposer', null);
INSERT INTO `flow_define` VALUES ('2', '1', 'applyExam', 't', '考试申请', '/AgroFront/oa/examOper.do?method=toSelectApply', 'examOper', 'manager');
INSERT INTO `flow_define` VALUES ('3', '1', 'approve', 't', '考试审批', '/AgroFront/oa/examOper.do?method=applyDriverList', 'manager', 'secPerson');
INSERT INTO `flow_define` VALUES ('4', '1', 'printCard', 't', '考试安排', '/AgroFront/oa/classOper.do?method=toSetClass', 'secPerson', 'newsOper');
INSERT INTO `flow_define` VALUES ('5', '1', 'affiche', 't', '考试公告', '/AgroFront/oa/operaffiche.do?method=toaficheMain', 'newsOper', null);
INSERT INTO `flow_define` VALUES ('6', '6', 'plan', 'e', '计划创建', null, 'planuser', 'planchecker');
INSERT INTO `flow_define` VALUES ('7', '7', 'conference', 'e', '会议申请', null, 'Conferenceproposer', 'Conferenceratifier');
INSERT INTO `flow_define` VALUES ('8', '6', 'addplan', 't', '填写计划', null, 'planuser', 'planchecker');
INSERT INTO `flow_define` VALUES ('9', '6', 'checkplan', 't', '审查计划', '/oa/privy/plan.do?method=info&type=check', 'planchecker', null);
INSERT INTO `flow_instance` VALUES ('PLAN_INFO_0000000361', 'FLOW_INSTANCE_0000000301', '1', '2', '9', null, 'zhaoyifei', 'zhaoyifei', '1', '2006-09-25 09:40:37', '2006-09-25 09:44:17');
INSERT INTO `flow_instance` VALUES ('PLAN_INFO_0000000362', 'FLOW_INSTANCE_0000000302', '2', '4', '9', null, 'zhaoyifei', 'zhaoyifei', '1', '2006-09-25 09:40:37', '2006-09-25 09:44:09');
INSERT INTO `flow_instance` VALUES ('PLAN_INFO_0000000363', 'FLOW_INSTANCE_0000000303', '3', '6', '9', null, 'zhaoyifei', 'zhaoyifei', '1', '2006-09-25 09:40:37', '2006-09-25 09:43:58');
INSERT INTO `flow_instance` VALUES ('PLAN_INFO_0000000381', 'FLOW_INSTANCE_0000000304', '4', '8', '9', null, 'zhaoyifei', 'zhaoyifei', '1', '2006-09-25 09:43:37', '2006-09-25 09:44:25');
INSERT INTO `flow_instance` VALUES ('SYNOD_NOTE_0000000481', 'FLOW_INSTANCE_0000000305', '5', '10', '12', null, '', 'zhaoyifei', '1', '2006-09-25 09:55:35', '2006-09-25 09:55:51');
INSERT INTO `flow_instance` VALUES ('SYNOD_NOTE_0000000481', 'FLOW_INSTANCE_0000000306', '5', '11', '13', null, '', null, null, '2006-09-25 09:58:13', null);
INSERT INTO `flow_instance` VALUES ('SYNOD_NOTE_0000000482', 'FLOW_INSTANCE_0000000307', '6', '13', '12', null, '', 'zhaoyifei', '1', '2006-09-25 10:17:50', '2006-09-25 10:18:05');
INSERT INTO `flow_instance` VALUES ('SYNOD_NOTE_0000000483', 'FLOW_INSTANCE_0000000308', '7', '15', '12', null, '', 'zhaoyifei', '1', '2006-09-25 10:17:50', '2006-09-25 10:17:59');
INSERT INTO `flow_instance` VALUES ('SYNOD_NOTE_0000000483', 'FLOW_INSTANCE_0000000309', '7', '16', '13', null, '', null, null, '2006-09-25 10:18:08', null);
INSERT INTO `flow_instance` VALUES ('SYNOD_NOTE_0000000482', 'FLOW_INSTANCE_0000000310', '6', '17', '13', null, '', null, null, '2006-09-25 10:18:08', null);
INSERT INTO `flow_next_step` VALUES ('1', '3', 'to_printCard', '通过');
INSERT INTO `flow_next_step` VALUES ('2', '3', 'to_end', '不通过');
INSERT INTO `flow_next_step` VALUES ('3', '9', 'to_update', '不通过');
INSERT INTO `flow_next_step` VALUES ('4', '9', 'to_end', '通过');
INSERT INTO `flow_next_step` VALUES ('5', '12', 'to_db', '通过');
INSERT INTO `flow_next_step` VALUES ('6', '12', 'to_update', '不通过');
INSERT INTO `flow_right` VALUES ('1', 'manager', null, null);
INSERT INTO `flow_right` VALUES ('10', 'planCreate', '', null);
INSERT INTO `flow_right` VALUES ('11', 'conferenceCreate', 'systemUser', null);
INSERT INTO `flow_right` VALUES ('2', 'examOper', 'examManager', null);
INSERT INTO `flow_right` VALUES ('3', 'secPerson', null, null);
INSERT INTO `flow_right` VALUES ('4', 'newsOper', null, null);
INSERT INTO `flow_right` VALUES ('5', 'Conferenceproposer', null, null);
INSERT INTO `flow_right` VALUES ('6', 'Conferenceratifier', 'zhaoyifei', null);
INSERT INTO `flow_right` VALUES ('7', 'Conferenceallocatee', 'wjlovegirl', null);
INSERT INTO `flow_right` VALUES ('8', 'planuser', null, null);
INSERT INTO `flow_right` VALUES ('9', 'planchecker', 'zhaoyifei', '');
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
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000121', 'test');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000122', '计费管理bug提交,有什么问题都可以在这里发帖或者回帖，我们会在第一时间解决您所提出的问题，谢谢你的合作！');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000123', '模块：个人办公\r\n信息：个人办公加上一个收藏夹。收藏常用的链接。');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000124', 'bug名称：邮件草稿箱错误\r\nbug列表：1)信息发布->邮件管理->内部邮件，邮件存入草稿箱后找不到继续发送的按钮或者保存按钮。');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000141', '看看好是不');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000161', 'bug：用户管理模块\r\n错误描述：添加没有添加进去，查询界面有问题\r\nbug：用户分机管理\r\n错误描述：添加多用户出错\r\nbug：费率管理\r\n错误描述：查询条件有问题\r\nbug：sp运营商维护\r\n错误描述：和号码管理一样\r\nbug：假日管理\r\n错误描述：没有模块\r\nbug：sp厂商（场商）管理\r\n错误描述：查询界面\r\nbug：添加成功弹出页\r\n错误描述：点击之后导致tt重启\r\nbug：节假日管理\r\n错误描述：查询列表显示有问题\r\nbug：节假日管理\r\n错误描述：添加没有检查时间\r\nbug：交换机管理\r\n错误描述：添加没有检查参数\r\nbug：基本话费查询\r\n错误描述：查询页应该加大一些\r\nbug：基本话费查询\r\n错误描述：开始时间结束时间没有选择\r\nbug：分类查询\r\n错误描述：查询页格式\r\nbug：话务量统计\r\n错误描述：和分类查询一样\r\n');
INSERT INTO `forum_post_content` VALUES ('FORUM_POSTS_0000000181', 'bug：用户分机管理\r\n错误描述：不能查找\r\nbug：用户分机管理\r\n错误描述：点击添加后,分析信息的请选择按钮点开后没有人员名称\r\nbug：用户分机管理\r\n错误描述：点击添加后,用户信息的选择按钮点选择人员后文本框里显示的是ID,用户选择人员的时候两个LIST里面的值的位子上下窜\r\n\r\n\r\n');
INSERT INTO `forum_posts` VALUES ('FORUM_POSTS_0000000121', 'FORUMTOPIC_0000000004', 'guxiaofeng', 'FORUM_POSTS_0000000121', '0', null, null, null, null, null, null, null, 'test', '', null, '2007-03-15 08:36:10', '218.25.101.108', null, null, null, null, null, null, null, null, '2007-03-15 08:36:10', null);
INSERT INTO `forum_posts` VALUES ('FORUM_POSTS_0000000122', 'FORUMTOPIC_0000000001', 'zhangfeng', 'FORUM_POSTS_0000000122', '0', null, null, null, null, null, null, null, '计费管理bug提交', '计费管理bug提交', null, '2007-03-15 09:30:10', '192.168.1.9', null, null, null, null, null, null, null, null, '2007-03-15 09:30:10', null);
INSERT INTO `forum_posts` VALUES ('FORUM_POSTS_0000000123', 'FORUMTOPIC_0000000004', 'guxiaofeng', 'FORUM_POSTS_0000000123', '0', null, null, null, null, null, null, null, '收藏夹', '', null, '2007-03-15 16:56:29', '218.25.101.108', null, null, null, null, null, null, null, null, '2007-03-15 16:56:29', null);
INSERT INTO `forum_posts` VALUES ('FORUM_POSTS_0000000124', 'FORUMTOPIC_0000000004', 'zhangfeng', 'FORUM_POSTS_0000000124', '0', null, null, null, null, null, null, null, '邮件草稿箱错误', '邮件草稿箱错误', null, '2007-03-15 17:13:12', '192.168.1.9', null, null, null, null, null, null, null, null, '2007-03-15 17:13:12', null);
INSERT INTO `forum_posts` VALUES ('FORUM_POSTS_0000000141', 'FORUMTOPIC_0000000005', 'zhaoyifei', 'FORUM_POSTS_0000000141', '0', null, null, null, null, null, null, null, '看看好是不', '', null, '2007-03-16 11:18:13', '192.168.1.111', null, null, null, null, null, null, null, null, '2007-03-16 11:18:13', null);
INSERT INTO `forum_posts` VALUES ('FORUM_POSTS_0000000161', 'FORUMTOPIC_0000000001', 'zhaoyifei', 'FORUM_POSTS_0000000161', '0', null, null, null, null, null, null, null, '计费管理bug', '', null, '2007-03-16 14:18:59', '192.168.1.3', null, null, null, null, null, null, null, null, '2007-03-16 14:18:59', null);
INSERT INTO `forum_posts` VALUES ('FORUM_POSTS_0000000181', 'FORUMTOPIC_0000000001', 'jingyuzhuo', 'FORUM_POSTS_0000000181', '0', null, null, null, null, null, null, null, '计费管理bug提交', '', null, '2007-03-22 15:44:18', '192.168.1.159', null, null, null, null, null, null, null, null, '2007-03-22 15:44:18', null);
INSERT INTO `forum_topic` VALUES ('1', '100', '卓越论坛系统', '1', '222', null, null, '1', '1', '0', '0', '0', '', null, null, null);
INSERT INTO `forum_topic` VALUES ('FORUMTOPIC_0000000001', null, '计费管理BUG提交区', 'FORUMTOPIC_0000000003', '', '', null, null, null, '1', '0', '3', 'N', null, null, '2007-03-22 15:44:18');
INSERT INTO `forum_topic` VALUES ('FORUMTOPIC_0000000003', null, 'BUG提交区', '1', '', '', null, null, null, '1', '0', '0', 'N', null, null, null);
INSERT INTO `forum_topic` VALUES ('FORUMTOPIC_0000000004', null, 'OA意见提交区', 'FORUMTOPIC_0000000003', '', '', null, null, null, '1', '0', '3', 'N', null, null, '2007-03-15 17:13:12');
INSERT INTO `forum_topic` VALUES ('FORUMTOPIC_0000000005', null, '论坛意见提交区', 'FORUMTOPIC_0000000003', '', '', null, null, null, '1', '0', '1', 'N', null, null, '2007-03-16 11:18:13');
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
INSERT INTO `forum_user_info` VALUES ('111', '111', '111', '111', '111', '111', '111', null, null, null, null, null, null, null, null, null, null, null, '0', 'normal', null, '2007-01-12 10:32:00', null, null, null, null, null, null, null, '0', '0', null);
INSERT INTO `forum_user_info` VALUES ('1111', '张锋', 'lgstar', 'whatisyourname', 'zhang', 'zhang', 'lgstar@163.com', null, null, null, null, null, null, null, null, null, null, null, '20', 'normal', null, '2006-12-06 09:25:47', null, '2007-01-02 13:46:33', null, null, null, null, null, '0', '0', null);
INSERT INTO `forum_user_info` VALUES ('3', '3', '3', '2', '2', '2', '2', null, null, null, null, null, null, null, null, null, null, null, '88', 'normal', null, '2006-12-27 14:24:33', null, '2006-12-31 13:46:50', null, null, null, null, null, '0', '0', null);
INSERT INTO `forum_user_info` VALUES ('admin', 'admin', 'admin', 'jsdkfjf', 'fjfsdlkfj', 'fjsdlfkj', 'admin@hotmail.com', '', '', '', '', '', null, '2007-01-12 11:03:03', null, null, '1', null, '10', 'manager', null, '2006-12-04 09:25:42', '', '2007-01-01 13:46:28', null, '', null, null, null, '0', '0', null);
INSERT INTO `forum_user_info` VALUES ('forum', 'forum', 'BBDBE444288550204C968FE7002A97A9', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', 'normal', null, '2007-03-13 13:17:34', null, null, null, null, null, null, null, '0', '0', 'forum');
INSERT INTO `forum_user_info` VALUES ('guxiaofeng', 'guxiaofeng', '49F68A5C8493EC2C0BF489821C21FC3B', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', 'normal', null, '2007-03-15 08:35:27', null, null, null, null, null, null, null, '0', '0', 'guxiaofeng');
INSERT INTO `forum_user_info` VALUES ('jingyuzhuo', 'jingyuzhuo', '25866405', 'w', 'w', 'w', 'w', null, null, null, null, null, null, null, null, null, null, null, '0', 'normal', null, '2007-03-10 14:54:46', null, null, null, null, null, null, null, '0', '0', null);
INSERT INTO `forum_user_info` VALUES ('lgstar', 'lgstar', 'lgstar', 'whatisyourname', 'zhang', 'zhang', 'lgstar@163.com', null, null, null, null, null, null, null, null, null, null, null, '36', 'normal', null, '2006-12-05 09:25:52', null, '2007-01-04 13:46:37', null, null, null, null, null, '0', '0', null);
INSERT INTO `forum_user_info` VALUES ('yepuliang', 'yepuliang', 'C4CA4238A0B923820DCC509A6F75849B', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', 'normal', null, '2007-03-12 14:34:20', null, null, null, null, null, null, null, '0', '0', 'yepuliang');
INSERT INTO `forum_user_info` VALUES ('zhangfeng', 'zhangfeng', 'zhangfeng', 'whatisyourname', 'zhang', 'zhang', 'lgstar888@163.com', null, null, null, null, null, null, null, null, null, null, null, '923', 'normal', null, '2006-12-19 09:25:56', null, '2007-01-03 13:46:47', null, null, null, null, null, '0', '0', null);
INSERT INTO `forum_user_info` VALUES ('zhaoyifei', 'zhaoyifei', 'BA4C2F175F0DBA2F2974E676C6DFBBAB', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', 'normal', null, '2007-03-12 14:13:01', null, null, null, null, null, null, null, '0', '0', 'zhaoyifei');
INSERT INTO `gov_antitheses_info` VALUES ('3', '3', 'GOV_MOTOR_INFO_00000000422', null);
INSERT INTO `gov_antitheses_info` VALUES ('GOV_ANTITHESES_INFO_0000000002', '', 'GOV_MOTOR_INFO_0000000122', null);
INSERT INTO `gov_antitheses_info` VALUES ('GOV_ANTITHESES_INFO_0000000003', '', 'GOV_MOTOR_INFO_0000000123', null);
INSERT INTO `gov_antitheses_info` VALUES ('GOV_ANTITHESES_INFO_0000000004', '', 'GOV_MOTOR_INFO_0000000124', null);
INSERT INTO `gov_antitheses_info` VALUES ('GOV_ANTITHESES_INFO_0000000006', '', '111', null);
INSERT INTO `gov_antitheses_info` VALUES ('GOV_ANTITHESES_INFO_0000000021', 'GOV_DRIVER_INFO_0000000101', 'GOV_MOTOR_INFO_0000000141', null);
INSERT INTO `gov_antitheses_info` VALUES ('GOV_ANTITHESES_INFO_0000000025', 'GOV_DRIVER_INFO_0000000101', 'GOV_MOTOR_INFO_0000000145', null);
INSERT INTO `gov_driver_info` VALUES ('1', '1', '1', '1971-05-04', '', '', '沈阳', '78945678', 'zkhuali', '', '1', '1', '公民', '2006-05-01 00:00:00', '1', '');
INSERT INTO `gov_driver_info` VALUES ('2', '2', null, null, null, null, null, null, null, null, null, null, null, null, '1', null);
INSERT INTO `gov_driver_info` VALUES ('3', '3', null, null, null, null, null, null, null, null, null, null, null, null, '1', null);
INSERT INTO `gov_driver_info` VALUES ('GOV_DRIVER_INFO_0000000101', 'fff', '1', '2006-04-03', 'fff', 'fff', 'fff', 'fff', 'fff', 'fff', 'fff', 'fff', 'fff', '2006-04-03 00:00:00', '1', 'fff');
INSERT INTO `gov_driver_info` VALUES ('GOV_DRIVER_INFO_0000000121', 'eee', '1', '1996-01-01', '333', 'e@e.com', 'eee', '333', 'eee', 'eee', 'eee', 'eee', 'eee', '2006-04-04 00:00:00', '1', 'eee');
INSERT INTO `gov_driver_info` VALUES ('GOV_DRIVER_INFO_0000000141', '王建', '1', '1960-08-01', '', '', '沈阳某处', '119', 'zkhuali', '', '王建', '88591', '公民', '2006-01-01 00:00:00', '', '');
INSERT INTO `gov_driver_oper` VALUES ('1', '1', 'null', '1', null, null, null, null, null, null);
INSERT INTO `gov_driver_oper` VALUES ('GOV_DRIVER_OPER_0000000001', 'null', 'GOV_MOTOR_INFO_0000000123', '9', '1', '1', '2006-04-05 00:00:00', '1', '1', '1');
INSERT INTO `gov_driver_oper` VALUES ('GOV_DRIVER_OPER_0000000002', 'null', '111', '1', '1', '2', '2006-04-03 00:00:00', '1', '1', '1');
INSERT INTO `gov_driver_oper` VALUES ('GOV_DRIVER_OPER_0000000021', 'GOV_DRIVER_INFO_0000000141', 'null', '1', '6', '3', '2006-05-16 00:00:00', '王建', '王建', 'null');
INSERT INTO `gov_motor_info` VALUES ('111', '1', '', '555', '555', '2000-01-03 00:00:00', '0', '', '', '0', '0', '', '', '0');
INSERT INTO `gov_motor_info` VALUES ('GOV_MOTOR_INFO_0000000122', '1', '11', '333', '333', '2000-01-01 00:00:00', '11', '11', '11', '0', '0', '11', '11\r\n\r\n\r\n', '0');
INSERT INTO `gov_motor_info` VALUES ('GOV_MOTOR_INFO_0000000123', '', '', '333333', '333333', '2000-01-01 00:00:00', '0', '', '', '0', '0', '', '', '-1');
INSERT INTO `gov_motor_info` VALUES ('GOV_MOTOR_INFO_0000000124', '', '', '444', '444', '2000-01-02 00:00:00', '0', '', '', '1', '1', '', '', '-1');
INSERT INTO `gov_motor_info` VALUES ('GOV_MOTOR_INFO_0000000141', 'GOV_DRIVER_INFO_0000000101', '123', '123456', '123456', '2006-05-08 00:00:00', '123', '123', '123', '0', '0', '', '', '0');
INSERT INTO `gov_motor_info` VALUES ('GOV_MOTOR_INFO_0000000145', 'GOV_DRIVER_INFO_0000000101', '123', '123', '123', '2006-04-12 00:00:00', '123', '123', '123', '0', '0', '123', '123', '0');
INSERT INTO `gov_motor_oper` VALUES ('1', '??', '2006-04-06 00:00:00', null);
INSERT INTO `gov_motor_oper` VALUES ('2', '??', '2006-04-06 00:00:00', null);
INSERT INTO `gov_motor_oper` VALUES ('3', '??', '2006-04-06 00:00:00', null);
INSERT INTO `gov_motor_oper` VALUES ('4', '??', '2006-04-06 00:00:00', null);
INSERT INTO `gov_motor_oper` VALUES ('5', '??', '2006-04-06 00:00:00', null);
INSERT INTO `gov_motor_oper` VALUES ('6', '??', '2006-04-06 00:00:00', null);
INSERT INTO `gov_motor_oper` VALUES ('7', '???', '2006-04-06 00:00:00', null);
INSERT INTO `gov_motor_oper` VALUES ('8', '??', '2006-04-06 00:00:00', null);
INSERT INTO `gov_motor_oper` VALUES ('9', '??', '2006-04-06 00:00:00', null);
INSERT INTO `handset_note_info` VALUES ('HANDSET_NOTE_INFO_0000000081', '4324', '324', '1', '2006-09-27 09:45:50', null, 'Y', '');
INSERT INTO `handset_note_info` VALUES ('HANDSET_NOTE_INFO_0000000102', '666666', '66666666', '2', '2006-09-27 10:35:07', null, 'N', '');
INSERT INTO `handset_note_info` VALUES ('HANDSET_NOTE_INFO_0000000121', '6546', '456546', '1', '2006-09-27 13:12:31', null, 'N', '');
INSERT INTO `handset_note_info` VALUES ('HANDSET_NOTE_INFO_0000000124', '8888', '8888', '1', '2006-09-27 14:22:53', null, 'N', '');
INSERT INTO `handset_note_info` VALUES ('HANDSET_NOTE_INFO_0000000125', '9879', '879789', '1', '2006-09-27 14:22:59', null, 'N', '');
INSERT INTO `handset_note_info` VALUES ('HANDSET_NOTE_INFO_0000000126', '87854757457', '4565636', '1', '2006-09-27 14:23:05', null, 'N', '');
INSERT INTO `handset_note_info` VALUES ('HANDSET_NOTE_INFO_0000000132', '456456', '6546', '1', '2006-09-27 14:23:46', null, 'Y', '');
INSERT INTO `inadjunct_info` VALUES ('INADJUNCT_INFO_0000000205', 'INEMAIL_INFO_0000001007', 'http://192.168.1.9:8089/ETOA/时钟.rar', null, '时钟.rar', null, null, '2006-09-19 15:38:09', 'zhaoyifei', null);
INSERT INTO `inadjunct_info` VALUES ('INADJUNCT_INFO_0000000221', 'INEMAIL_INFO_0000001041', 'http://192.168.1.9:8089/ETOA/时钟.rar', null, '时钟.rar', null, null, '2006-09-21 09:17:17', 'zhaoyifei', null);
INSERT INTO `inadjunct_info` VALUES ('INADJUNCT_INFO_0000000222', 'INEMAIL_INFO_0000001042', 'http://192.168.1.9:8089/ETOA/时钟.rar', null, '时钟.rar', null, null, '2006-09-21 09:17:29', '', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000701', 'zhaoyifei', 'lgstar888@163.com', 'lgstar888@163.com', null, null, '测试方法', '<DIV>测试方法</DIV>', '2006-09-13 09:24:27', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000703', 'zhaoyifei', 'lgstar888@163.com', 'lgstar888@163.com', null, null, '来个测试', '<DIV>来个测试</DIV>', '2006-09-13 09:27:37', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000722', null, 'developerWorks China <dw@cn.ibm.com>', null, null, null, 'IBM developerWorks 中国网站时事通讯：第 274 期 - [2006-09-12]', 'javax.mail.internet.MimeMultipart@d68841', '2006-09-13 05:14:47', null, null, '1', '1', null, null, '2', 'EMAIL_BOX_0000000121', 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000741', null, 'developerWorks China <dw@cn.ibm.com>', null, null, null, 'IBM developerWorks 中国网站时事通讯：第 274 期 - [2006-09-12]', '<HTML><img src=\"http://ibm.com/partnerworld/isv/ecma/campaign/process_html.jsp?id=190363\" height=\"1\" width=\"1\"></HTML><html xmlns:fo=\"http://www.w3.org/1999/XSL/Format\"><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=GB2312\"><meta content=\"text/html; charset=GB2312\" http-equiv=\"Content-Type\"></head><base target=\"_blank\"><body style=\"font-family: Default Sans Serif; font-size: 10pt\">======================================================================================<br>IBM developerWorks 中国网站时事通讯：第 274 期<br>2006-09-12<br><br><a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190298\">IBM\'s resource for developers</a>.<br><br>更多精彩内容，敬请访问：<a target=\"_blank\" href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190299\">在线版 &ldquo;developerWorks 中国网站时事通讯&rdquo; 栏目</a><br><br>======================================================================================<br>developerWorks 中国网站的朋友，你们好！<br><br><p>本周为大家推荐:</p><ul><li> WebSphere 新手入门</li><li> WebSphere 家族主要产品概览</li><li> “审视 Ajax” 系列文章</li></ul><p>在 IBM 的五大品牌中，WebSphere 是个重量级的大家族。她集合了 IBM 在 “流程整合” 方面众多优秀的产品。如 IBM 应用服务器 WebSphere Application Server 是应用程序部署运行的基础架构；WebSphere Process Server 为面向服务的体系结构（SOA）提供了集成平台；WebSphere Integration Developer 使集成开发人员能快速地构建和调试复合的业务集成应用；WebSphere Portal 则提供了强大的门户...如此众多的产品和技术如何更快的掌握？来看看我们新推出的 “WebSphere 新手入门” 和“WebSphere 家族主要产品概览” 专栏！专栏中系统组织的内容将向您详细介绍什么是 WebSphere，WebSphere 产品家族里各个重要产品的产品目标、学习路线图、经典文章、教程和多媒体技术资源。揭开 WebSphere 的神秘面纱，了解她的强大功能，从这里开始...</p><p>如今，人们对 Ajax（Asynchronous JavaScript and XML）的兴趣日益高涨，各式各样的 Ajax 应用程序提供了比传统 Web 页面更高的交互性和更丰富的用户体验。使用 Ajax 也可以前所未有地部署新的、革命性的聚合和 Web 界面展示技术。然而，当开发人员在考虑使用 Ajax 技术之前，都应该认真地思考一些问题，如 Ajax 技术哪些地方合适哪些地方不合适？本周，来自 Web architecture 专区的系列文章“ 审视 Ajax ”，将向您讲述使用 Ajax 技术时所面临的一些潜在问题，同时也展示了 Ajax 的巨大潜力，这将帮助您做出正确的决策。</p><p>如果您想了解、学习更多 Ajax 技术，请访问我们的“ Ajax 技术资源中心”。在这里您可以找到包括 Ajax 基础入门、使用 Java, PHP 等语言开发 Ajax 应用的各种技巧以及多种 Ajax 框架的技术信息。 </p><br><br><ul><li><p><b>WebSphere 新手入门</b><br><a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190300\">http://www.ibm.com/developerworks/cn/websphere/newto/index.html</a></p><br><br></li><li><p><b>WebSphere 家族主要产品概览</b><br><a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190301\">http://www.ibm.com/developerworks/cn/websphere/products/index.html</a></p><br><br></li><li><p><b>审视 Ajax，第 1 部分：透过华而不实的广告看本质</b><br><a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190302\">http://www.ibm.com/developerworks/cn/web/wa-ajaxtop1/index.html</a></p><br><br></li><li><p><b>审视 Ajax，第 2 部分：使用 mashup 改变您的生活</b><br><a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190303\">http://www.ibm.com/developerworks/cn/web/wa-ajaxtop2/index.html</a></p><br><br></li><li><p><b>Ajax 技术资源中心</b><br><a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190304\">http://www.ibm.com/developerworks/cn/xml/ajax/</a></p><br><br></li></ul><br>　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　Sunny<br>　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　IBM developerWorks 中国网站<br><br><br>=== 近期活动及资料下载 =============================================<br><br>◆ <a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190305\">WebSphere 用户组9月份最新免费讲座：探究WebSphere Process Server 中的业务状态机和业务规则组件</a><br>本讲座将介绍业务状态机的基本概念，通过用业务状态机构建业务过程的实例来探究业务状态机的使用等。 <br><br>◆ <a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190306\">dWLive! 技术讲座：信息随需应变：利用DB2的强大动力</a><br>本次研讨会将演示IBM提供用于支持快速开发集成解决方案以管理您不断扩展的数据量的信息管理工具和服务。<br><br><br>=== 本周教程 =============================================			<br><br>【<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190307\">在多个 Web 服务之间共享资源，第 2 部分：集成可重用的 FactoryService</a>】<br>Web 服务资源框架（Web Services Resource Framework，WSRF）为在 Web 服务中创建和访问有状态的资源定义了一个系统。在这个分两部分的 “在多个 Web 服务之间共享资源” 系列文章中，我们采用样例实现来展示如何在 Web 服务之间共享资源。WS Core 是 WSRF 的 Globus Toolkit 实现，它被用来开发本教程中介绍的例子。这个例子使用一个用户帐号作为资源。我们将开发两个 Web 服务来修改这个帐号的两个属性：用户名和地址。本文是本系列文章的第 2 部分，我们将开发一个可重用的 FactoryService，可以使用它共享相同的资源实例，而不是像第 1 部分一样生成多个拷贝。<br><br>【<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190308\">随需应变的业务流程生命周期，第 15 部分: 为面向服务的体系结构部署可伸缩、安全而稳定的基础</a>】<br>本教程将详细讨论 WebSphere Process Server Version 6.0.1 集群的安装和配置。对集群进行了完全配置后，集群就将能够进行水平集群化（横向扩展）和垂直集群化（纵向扩展）。集群配置将进行逻辑划分，以便允许管理员 独立扩展应用程序处理或消息传递工作负载专用的资源，从而分别缓解 CPU 或 I/O 瓶颈。<br><br>【<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190309\">了解 Web 服务规范，第 1 部分：SOAP</a>】<br>面向服务的体系结构（Service-Oriented Architectures，SOA）当前强调的重点在 Web 服务上，但很容易被所传播的各种信息搞得昏头转向。本系列教程将对主要 Web 服务规范进行全面说明，从简单对象访问协议（Simple Object Access Protocol，SOAP）一直介绍到 WS Business Process Execution Language (WS-BPEL)。本教程将介绍 Web 服务和 SOAP 基本概念，并说明如何构建 SOAP 服务器和客户机。<br><br>-&gt;更多内容请访问：<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190310\">developerWorks 精品教程</a><br><br><br>=== 产品 =============================================<br>=============================================================================================== <br>AIX and UNIX || Operating system,Application development<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190311\">对话 UNIX，第 2 部分：做得多不如做得巧</a>:::<br>了解如何利用 UNIX(R) Shell 提供的许多快捷方法。通过一些练习，您将可以更灵活地、而不是更辛苦地进行工作。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190312\">编写 AIX 内核扩展：扩展您的技能</a>:::<br>了解如何使用 AIX 内核来构建例程以扩展相应的功能，以及如何创建自己的系统调用、内核进程或文件系统。Power 体系结构的飞快发展，加之 AIX(R) Version 5.3 的各种增强功能，使得人们对 AIX 出现了前所未有的兴趣。本文通过大量的示例向您介绍如何利用 AIX 中内核扩展的强大功能。<br><br>-&gt;更多内容请访问：<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190313\">AIX and UNIX 专区</a><br><br><br>=============================================================================================== <br>Information Management || Administration,Migration,Open source,About the product,Application development,XML,Security,Configuration,PHP (Hypertext Preprocessor)<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190314\">从 MySQL 或 PostgreSQL 迁移到 DB2 Express-C</a>:::<br>容易使用、强大而且免费！这就是人们对 MySQL 和 PostgreSQL 的评价。但是，您知道 DB2 Express-C 是一种免费的具有专业水准且容易使用的数据库吗？学习如何轻松地从 MySQL/PostgreSQL 迁移到 DB2。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190315\">DB2 9 入门：应用程序开发方面的增强</a>:::<br>DB2 9 提供了大量的增强来简化数据库应用程序的开发，特别是在快速构建基于 Web 服务、XML 提要、数据联合等技术的新型“Web 2.0”应用程序方面进行了优化。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190316\">DB2 安全性，第 8 部分：数据库安全的 12 条军规</a>:::<br>随着越来越多的安全问题被公之于众，管理员必须不断查找系统的漏洞，以免自己的公司成为公众的下一个笑柄。本文着重阐述 12 种最佳安全实践来帮助数据库管理员和开发人员实现 DB2 良好的安全性。 <br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190317\">安装、配置和测试 Windows, Apache, Informix 与 PHP 服务器的分步指南</a>:::<br>学习如何安装、配置和测试 Windows, Apache, Informix 与 PHP (WAIP) 服务器。Windows、Apache、Informix 以及 PHP 共同形成一个非常强大且动态的 Web 服务器。<br><br>-&gt;更多内容请访问：<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190318\">Information Management 专区</a><br><br><br>=============================================================================================== <br>Lotus || Application development,Instant messaging,Web conferencing<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190319\">使用 SMS 消息传递插件扩展 IBM Lotus Sametime Connect V7.5</a>:::<br>学习如何通过构建 SMS 消息传递插件扩展新的基于 Eclipse 的 IBM Lotus Sametime Connect V7.5 客户机，该插件允许您直接从 Lotus Sametime 客户机向 Sametime 合作伙伴的移动电话或移动设备发送 SMS（Short Message Service，短消息服务）文本消息。<br><br>-&gt;更多内容请访问：<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190320\">Lotus 专区</a><br><br><br>=============================================================================================== <br>Rational || Process authoring,Process management,Process transformation,Rational Method Composer,Rational Portfolio Manager,RUP (Rational Unified Process),Rational Application Developer<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190321\">探究 Rational Method Composer 和 Rational Portfolio Manager 的集成</a>:::<br>您需要对过程工程提供全面的支持吗？IBM Rational Method Composer V7.0 提供了过程的开发、管理和发布方面的能力。IBM Rational Portfolio Manager V7.0 是项目组合管理的企业级解决方案，可以对组织中已提议的、在进行中和历史的项目进行实时的质量检测，管理和监控。这两个产品包含许多集成特性，可以使利用 Rational Method Composer 所创建的过程作为 Rational Portfolio Manager 中项目的基础。在本文中，将讨论如何使用这些集成特性的方法，并探究在项目中加强过程的好处。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190322\">使用 Rational Application Developer 在 Web 应用程序中集成 Crystal 报表，第一部分: 在 Web 应用程序中使用 JDBC 连接嵌入 Crystal 报表</a>:::<br>这篇文章主要针对那些想在 Web 应用程序中嵌入 Crystal 报表从而满足他们报告要求的人群。这是五部分系列文章中的第一部分，它将教你使用 IBM? Rational? Application Developer (RAD) 在 Web 应用程序中嵌入 Crystal 报表的操作经验。尤其是，这篇文章将解释在设计时和运行时，如何使用 Java? Database Connectivity (JDBC?)技术在 Web 应用程序中嵌入 Crystal 报表。<br><br>-&gt;更多内容请访问：<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190323\">Rational 专区</a><br><br><br>=============================================================================================== <br>WebSphere || SOA (Service-Oriented Architecture),WebSphere Integration Developer,WebSphere Process Server,About the product,Modeling,WebSphere Business Integration<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190324\">从Spring实例入手谈SCA Component的创建和调用</a>:::<br>面向服务组件的架构（Service Component Architecture，SCA）的概念，是目前业界最火的概念。但是对于很多开发人员来说，如何在SCA的架构上进行设计和开发还是显得相对抽象的。WID（WebSphere Integration Developer）＆WPS（WebSphere Process Server）的推出，使得客户能够更加简单的实现向这种面向组件架构的转变。本文将介绍SCA编程模型中创建和调用SCA Component的基本概念和方法，并以一系列简单的实例来说明不同场景中如何使用WID进行Component的创建和调用。SCA支持多种技术实现其component，Spring framework凭借依赖注入思想是这些技术中的天然一员，文中的实例以Spring作为开发框架，期待能够抛砖引玉，并为读者以后深入了解SCA打下基础。读者定位为具有WID开发经验的开发人员，对SOA和SCA，Spring有所了解。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190325\">WebSphere 新手入门</a>:::<br>了解 IBM 的集成和应用软件 <br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190326\">WebSphere Commerce中的需求链模型及其部署</a>:::<br>需求链业务模型（Demand Chain）是WebSphere Commerce（Business Edition）提供的间接业务模型（Indirect Business Model）之一，建立在Commerce中的两个直接模型Consumer Direct模型和B2B Direct模型，以及间接模型之一的主管模型（Hosting）的基础上。业务模型的实施包括：创建商店，确定业务流程，确定组织结构，而WebSphere Commerce提供多种业务模型的基础上，给用户提供相应的网上商店样本（原型商店），这些商店捆绑了根据实际总结的最佳业务流程和组织结构，从而使产品用户可以快速建立多种完善的电子商店，并在经营过程中开展随需应变的业务。本文将介绍WebSphere Commerce提供的Demand Chain业务模型的架构及其部署<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190327\">使用WBM和WID加速企业流程建模和实现</a>:::<br>本文将介绍如何利用IBM WebSphere Business Modeler（以下称WBM）和WebSphere Integration Developer（WID）建立一个业务分析建模和IT应用开发之间的桥梁。业务分析人员通过使用WBM的建模视图可以快速的对商业流程进行建模和性能模拟测试，并通过Export功能将模型转化为WID中的实现框架，从而达到快速开发，提高组件可重用性的目的。通过阅读本文，你将学到如何使用WBM v6和WID v6实现一个从流程到实现的快速开发过程。<br><br>-&gt;更多内容请访问：<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190328\">WebSphere 专区</a><br><br><br><br>=== 技术 =============================================<br>=============================================================================================== <br>Grid computing || Architecture,Globus,OGSA (Open Grid Services Architecture),Web services,WS-Resource,SOA (Service-Oriented Architecture)<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190329\">网格体系结构概述</a>:::<br>本文将介绍网格技术目前流行的三个网格体系结构，五层沙漏结构（Five-Level Sandglass Architecture）、开放网格服务体系结构（Open Grid Services Architecture，OGSA）、Web 服务资源框架（Web Services Resource Framework，WSRF），以及它们的实现和支撑技术。<br><br>-&gt;更多内容请访问：<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190330\">Grid computing 专区</a><br><br><br>=============================================================================================== <br>Java technology || Application development,Web services,Testing,Apache,Open source<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190331\">跨越边界: REST on Rails</a>:::<br>跨越边界 系列中以前的文章说 Ruby on Rails 是一个突然流行起来的框架，充当着 Ruby 编程语言的催化剂。随着 Ruby 的经验不断成功，开发人员开始寻求把他们的 Ruby 应用程序与用其他语言编写的应用程序集成。Rails 对 Web 服务提供了优秀的支持。本文介绍 Rails 中的 Web 服务，重点放在一个名为 Representational State Transfer (REST) 的策略上。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190332\">Java 理论与实践: 平衡测试，第 2 部分</a>:::<br>上一期的 Java 理论与实践专栏 说明了如何充分利用 FindBugs 之类的静态分析工具来管理软件质量，并重点测试了 bug 的整个目录（而不是特定 bug 实例）。在本期专栏中，专职清除专家 Brian Goetz 将详细说明构造和优化不平凡的 bug 模式检测器的过程。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190333\">用 Apache 和 Subversion 搭建安全的版本控制环境</a>:::<br>本文将在服务器端配置工作的角度，结合作者在实际开发工作当中的配置实例，介绍 Subversion 服务器端的基本配置和管理，以及如何将 Subversion 与 Apache 结合，实现一些高级管理功能。<br><br>-&gt;更多内容请访问：<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190334\">Java technology 专区</a><br><br><br>=============================================================================================== <br>Linux || Mobile and embedded systems,Tools,Kernel<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190335\">BusyBox 简化嵌入式 Linux 系统</a>:::<br>BusyBox 是很多标准 Linux 工具的一个单个可执行实现。BusyBox 包含了一些简单的工具，例如 cat 和 echo，还包含了一些更大、更复杂的工具，例如 grep、find、mount 以及 telnet（不过它的选项比传统的版本要少）；有些人将 BusyBox 称为 Linux 工具里的瑞士军刀。本文将探索 BusyBox 的目标，它是如何工作的，以及为什么它对于内存有限的环境来说是如此重要。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190336\">Linux 调度器内幕</a>:::<br>Linux 内核继续不断发展并采用新技术，在可靠性、可伸缩性和性能方面获得了长足的发展。2.6 版本的内核最重要的特性之一是由 Ingo Molnar 实现的调度器。这个调度器是动态的，可以支持负载均衡，并以恒定的速度进行操作 ―― O(1)。本文将介绍 Linux 2.6 调度器的这些属性以及更多内容。<br><br>-&gt;更多内容请访问：<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190337\">Linux 专区</a><br><br><br>=============================================================================================== <br>Open source || Apache,Geronimo,Web services,PHP (Hypertext Preprocessor),DHTML (Dynamic HTML)<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190338\">使用 Apache Geronimo 和 POJO 构建 SOA 框架</a>:::<br>在不考虑库和框架强制执行的应用程序编程接口 (API) 约束的情况下进行软件开发，是一个非常诱人主张。它使许多人接受了普通旧式 Java 对象（Plain Old Java Object，POJO）编程的范例 ―― 能够在 Java 平台上开发软件，而无需使用多余的接口或第三方 API。Apache Geronimo 框架为构建复杂应用程序和服务的 POJO 开发提供了一个可靠的基础设施。本文介绍 Geronimo 框架的一些组件和技巧，用于通过 POJO 策略来实现成功的、面向服务的开发。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190339\">使用 PHP 和 DHTML 设计 Web 2.0 应用程序，第 1 部分：使用这些技术打造有特色的应用</a>:::<br>2006 年最时髦的词莫过于 Web 2.0。Web 2.0 究竟意味着什么，这是一个热门的争论话题，但它似乎与一种很酷的动态 Web 应用程序有关。那些 Web 应用程序 ―― 通常以 PHP 开发 ―― 使用动态 HTML（DHTML）创建页面，移动及更改此页面时无需返回服务器进行更新。Jack Herrington 在 “使用 PHP 和 DHTML 设计 Web 2.0 应用程序” 系列文章中教您开始使用这项技术。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190340\">使用 XMLBean 在 Apache Geronimo 中部署 SOA 应用</a>:::<br>对 XMLBeans 好奇吗？这种高级易用的 XML-Java 绑定技术允许您像访问任何 Java 对象或 JavaBean 一样访问 XML 文件。本文将阐明有关 XMLBeans 技术的更多内容，包括它如何与 Apache Geronimo 一起使用以及如何简化和流化面向服务架构 (SOA) 开发。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190341\">使用 PHP 和 DHTML 设计 Web 2.0 应用，第 2 部分：使用 JavaScript 创建 HTML 动态元素</a>:::<br>“使用 PHP 和 DHTML 设计 Web 2.0 应用程序” 系列文章的第 1 部分探讨了如何使用 JavaScript、层叠样式表（CSS） 和 HTML 构建带有选项卡、微调控制项、弹出框等用户界面元素的 PHP 应用程序。第 2 部分将扩展上一篇文章的内容，将图形技术包含在内，使用 JavaScript 动态创建新的 HTML 元素。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190342\">Geronimo 叛逆者：OpenEJB 和 Apache Geronimo 的 EJB 实现</a>:::<br>Enterprise JavaBeans (EJBs) 到底有什么了不起的，为什么对 Java 2 Platform, Enterprise Edition (J2EE) 开发来说如此重要？在这一期的 Geronimo renegade 专栏中，OpenEJB 的共同创始人 David Blevins 将介绍 EJB 可以为您做什么，并解释 OpenEJB 如何被选择作为 Apache Geronimo 的 EJB 实现。<br><br>-&gt;更多内容请访问：<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190343\">Open source 专区</a><br><br><br>=============================================================================================== <br>SOA and Web services || Enterprise Service Bus,WebSphere Process Server,BPEL4WS (Business Process Execution Language for Web Services),SOAP (Simple Object Access Protocol),Standards and specifications,WS-Policy (Web Services Policy Framework),WS-Security,WSDL (Web Services Description Language),Optimization,Integration<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190345\">通过 Web 服务向后兼容性向前发展</a>:::<br>对于任何控制模式，管理基于 SOA 的系统的变更都是非常重要的部分。您可以从本文中了解一些用于 SOA 变更管理的技术。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190347\">在 SOA 中实现语义互操作性</a>:::<br>在 SOA 开发过程中，语义互操作性通常被忽略，或者事后才被想起。对于语义互操作性方面的体系结构决策，应用程序和数据架构师可能很难作出明智的决定。本文将揭开 SOA 上下文中的语义互操作性的神秘面纱。我们首先讨论语义谱 (semantic spectrum)，然后讨论语义互操作性的反模式、模式和最佳实践。 <br><br>-&gt;更多内容请访问：<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190349\">SOA and Web services 专区</a><br><br><br>=============================================================================================== <br>Web architecture || Information architecture,Java technology,Web authoring,Ajax,Tips<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190351\">用 Lucene 加速 Web 搜索应用程序的开发</a>:::<br>Lucene 是基于 Java 的全文信息检索包，它目前是 Apache Jakarta 家族下面的一个开源项目。在这篇文章中，我们首先来看如何利用 Lucene 实现高级搜索功能，然后学习如何利用 Lucene 来创建一个健壮的 Web 搜索应用程序。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190353\">审视 Ajax，第 1 部分: 透过华而不实的广告看本质</a>:::<br>最近对 Ajax（Asynchronous JavaScript and XML）的兴趣高涨了起来。种种 Ajax 应用程序提供了比传统 Web 页面更高的交互性和更丰富的用户体验。使用 Ajax 可以前所未有地部署新的、革命性的聚合和表示技术。受到 Alex Bosworth 列举的 Ajax 错误的触动，Chris Laffra 汇集了每位开发人员在考虑使用 Ajax 技术之前都应该认真思考的一些问题，分为两部分阐述。一些是潜在的问题，多数强调了 Ajax 的巨大潜力。<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190355\">审视 Ajax，第 2 部分: 使用 mashup 改变您的生活</a>:::<br>Chris Laffra 的这篇文章继续讨论 Ajax 开发人员在开发应用程序时需要记住什么。除了具体的建议和忠告外，还展望了 Ajax 的未来，它推动了个人网页上内容的用户导向型混合。<br><br>-&gt;更多内容请访问：<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190357\">Web architecture 专区</a><br><br><br>=============================================================================================== <br>XML || XML<br><br>:::<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190359\">Java 语言的 XML 验证 API</a>:::<br>检查文档是否遵循了模式中规定的规则。不同的解析器和工具支持不同的模式语言如 DTD、W3C XML Schema 语言、RELAX NG 和 Schematron。Java 5(TM) 增加了统一的验证应用程序编程接口（API），可以把文档和用这种或那种语言编写的模式作比较。了解这种 XML 验证 API。<br><br>-&gt;更多内容请访问：<a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190361\">XML 专区</a><br><br><br><br>				===关于 Newsletter================================================<br><br>如果您想退订 Newsletter, 请在 Newsletter 订阅处选择\"退订\"，然后提交，请不要直接回复至此邮件地址。<br><br><a href=\"http://ibm.com/developerworks/ecma/campaign/er.jsp?id=190362\">IBM developerWorks 中国网站</a><br></body></html>', '2006-09-13 05:14:47', null, null, '1', '1', null, null, '2', 'EMAIL_BOX_0000000121', 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000761', 'zhaoyifei', 'lgstar888@163.com', 'lgstar888@163.com', null, null, '邮件测试', '<DIV>这是一个新的测试，需要哪些东西来做，</DIV>', '2006-09-13 10:44:26', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000763', 'zhaoyifei', 'zhaoyifei1@tom.com ', 'zhaoyifei1@tom.com ', null, null, '我个邮件做测试', '<DIV>我个邮件做测试</DIV>', '2006-09-13 10:46:36', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000764', 'zhaoyifei', 'zhaoyifei@tom.com', 'zhaoyifei@tom.com', null, null, '外部邮件', '<DIV>外部邮件,我要做测试</DIV>', '2006-09-13 10:48:13', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000765', 'zhaoyifei', 'zhaoyifei@tom.com', 'zhaoyifei@tom.com', null, null, '测试', '<FONT style=\"BACKGROUND-COLOR: #ffffff\">测试，看看有问题没</FONT>\r\n<DIV></DIV>', '2006-09-13 10:52:49', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000766', 'zhaoyifei', 'zhaoyifei@tom.com', 'zhaoyifei@tom.com', null, null, '止jjj', '<DIV>止jjj止jjj止jjj</DIV>', '2006-09-13 11:10:21', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000783', 'zhaoyifei', 'zhaoyifei1@tom.com', 'zhaoyifei1@tom.com', null, null, '帮个没度没总理', '<DIV>帮个没度没总理，这个是个很现实的问题</DIV>', '2006-09-13 13:48:49', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000801', '', 'lgstar888@163.com', 'lgstar888@163.com', null, null, '帮个没度没总理', '<DIV>ggfggfgfggfhg</DIV>', '2006-09-13 14:30:39', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000821', null, 'report@51job.com', null, null, null, '亚洲最大的全球软件与咨询服务公司--急聘IT精英', '<HTML>\r\n<HEAD>\r\n<!--Template No. 1385 Original Designer: camelsky -->\r\n<title>人才招聘-51job</title>\r\n<META content=\"前程无忧为企业提供人才招聘、猎头、培训、测评和人事外包在内的全方位的人力资源服务，帮助个人求职者与企业搭建最佳的人才招募和人才培养渠道。\" name=\"description\">\r\n<META content=\"人才，招聘，简历，猎头，培训，测评，人事，工作，面试，薪酬，跳槽\" name=\"keywords\">\r\n<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\r\n<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache\">\r\n<META HTTP-EQUIV=\"Expires\" CONTENT=\"0\">\r\n<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=gb2312\">\r\n<style>\r\n	a.{\r\n	 color: #0066FF;\r\n	 text-decoration: underline;\r\n	}\r\n	a.:hover {\r\n		color: #FF6600;\r\n		text-decoration: underline;\r\n	}\r\n	.css {\r\n		font-size: 12px;\r\n		line-height: 20px;\r\n		color: #333333;\r\n	}\r\n	body {\r\n		margin-left: 0px;\r\n		margin-top: 0px;\r\n		margin-right: 0px;\r\n		margin-bottom: 0px;\r\n	}\r\n</style>\r\n</HEAD>\r\n<BODY bgcolor=\"#CCCCCC\">\r\n \r\n<table width=\"760\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">\r\n  <tr>\r\n    <td><img src=\"http://companyadc.51job.com/companyads/shanghai/bj/tata_060901/images/index_01.jpg\" width=\"760\" height=\"56\"></td>\r\n  </tr>\r\n  <tr>\r\n    <td><img src=\"http://companyadc.51job.com/companyads/shanghai/bj/tata_060901/images/index_02.jpg\" width=\"760\" height=\"83\"></td>\r\n  </tr>\r\n  <tr>\r\n    <td><img src=\"http://companyadc.51job.com/companyads/shanghai/bj/tata_060901/images/index_03.jpg\" width=\"760\" height=\"98\" border=\"0\" usemap=\"#Map\"></td>\r\n  </tr>\r\n  <tr>\r\n    <td><table width=\"90%\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n      <tr>\r\n        <td height=\"40\"><img src=\"http://companyadc.51job.com/companyads/shanghai/bj/tata_060901/images/job.jpg\" width=\"498\" height=\"30\"></td>\r\n      </tr>\r\n      <tr>\r\n        <td>\r\n            <table width=\"92%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" class=\"css\">\r\n              <tr>\r\n                \r\n                  <td width=\"50%\" height=\"25\"><img src=\"http://companyadc.51job.com/companyads/shanghai/bj/tata_060901/images/dot.jpg\" width=\"4\" height=\"6\" align=\"absmiddle\">\r\n                      <A href=\"http://www.51job.com/sc/show_job_detail.php?itisfrom=wudan&amp;jobiduni=27229563\" target=\"_blank\">测试工程师</A>\r\n                  </td>\r\n                \r\n                \r\n                  <td width=\"50%\"><img src=\"http://companyadc.51job.com/companyads/shanghai/bj/tata_060901/images/dot.jpg\" width=\"4\" height=\"6\" align=\"absmiddle\">\r\n                      <A href=\"http://www.51job.com/sc/show_job_detail.php?itisfrom=wudan&amp;jobiduni=27229591\" target=\"_blank\">系统工程师</A>\r\n                  </td>\r\n                \r\n              </tr>\r\n            </table>\r\n        \r\n            <table width=\"92%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" class=\"css\">\r\n              <tr>\r\n                \r\n                  <td width=\"50%\" height=\"25\"><img src=\"http://companyadc.51job.com/companyads/shanghai/bj/tata_060901/images/dot.jpg\" width=\"4\" height=\"6\" align=\"absmiddle\">\r\n                      <A href=\"http://www.51job.com/sc/show_job_detail.php?itisfrom=wudan&amp;jobiduni=27229576\" target=\"_blank\">系统分析员</A>\r\n                  </td>\r\n                \r\n                \r\n              </tr>\r\n            </table>\r\n        \r\n        </td>\r\n      </tr>\r\n    </table>\r\n    </td>\r\n  </tr>\r\n  <tr>\r\n    <td><table width=\"90%\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n      <tr>\r\n        <td height=\"40\" colspan=\"2\"><img src=\"http://companyadc.51job.com/companyads/shanghai/bj/tata_060901/images/index_06.jpg\" width=\"498\" height=\"30\"></td>\r\n      </tr>\r\n      <tr>\r\n        <td width=\"4%\"><span class=\"css\"> </span></td>\r\n        <td width=\"96%\"><span class=\"css\">\r\n          塔塔信息技术（上海）有限公司是亚州著名的全球软件与咨询服务公司-塔塔咨询服务公司（TCS）的在华全资子公司。TCS成立于1968年，在50个国家拥有150多家分支机构，服务于全球55个国家的1000多家客户，其中有8家位居美国财富杂志前列。塔塔信息技术（上海）有限公司位于杭州的全球研发中心已通过了CMMI五级和PCMM 五级认证。\r<br>\r<br>关于我们：\r<br>&#8226;建立于1968年，是亚洲最大的软件服务公司之一\r<br>&#8226;隶属塔塔集团\r<br>&#8226;全球有71,190多名咨询顾问\r<br>&#8226;2005至2006年度营业收达29.7亿美元\r<br>&#8226;通过SEI-CMMI五级，PCMM五级及ISO9001，BS7799，BS15000\r<br>&#8226;150多家分支机构遍布全球50个国家\r<br>&#8226;印度最大的软件研发中心之一\r<br>&#8226;印度最大的软件出口商之一\r<br>\r<br>在中国，我们的总部设在上海，在北京设有分支机构，在杭州设有全球开发中心。我们诚邀信息技术领域的人才加盟。我们将提供一流的工作环境，优厚的薪资福利，海外培训的机会以及优秀的职业发展道路。\r<br>\r<br>现在加盟，创造美好事业！\r<br>\r<br>（应聘者须提供中英文简历并请注明应聘职位）\r\n        </span></td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>\r\n  <tr>\r\n    <td><table width=\"90%\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n      <tr>\r\n        <td colspan=\"2\" class=\"css\">&nbsp;</td>\r\n      </tr>\r\n      <tr>\r\n        <td height=\"40\" colspan=\"2\" class=\"css\"><img src=\"http://companyadc.51job.com/companyads/shanghai/bj/tata_060901/images/contact.jpg\" width=\"498\" height=\"30\"></td>\r\n      </tr>\r\n      <tr>\r\n        <td width=\"4%\" class=\"css\">&nbsp;</td>\r\n        <td class=\"css\">公司地址：\r\n          北京市海淀区东北旺西路8号中关村软件园9号楼3区D座一层\r\n          <br>\r\nE-mail　：\r\n<A href=\"mailto:tata@hr.51job.com\">tata@hr.51job.com</A></td>\r\n      </tr>\r\n      <tr>\r\n        <td colspan=\"2\" class=\"css\">&nbsp;</td>\r\n        </tr>\r\n    </table>      </td>\r\n  </tr>\r\n  <tr>\r\n    <td height=\"27\" valign=\"bottom\" background=\"http://companyadc.51job.com/companyads/shanghai/bj/tata_060901/images/index_09.jpg\"><div align=\"center\"><span class=\"css\">未经51job.com 同意，不得转载本网站之所有招聘信息及作品;无忧工作网版权所&copy;1999-2006</span></div></td>\r\n  </tr>\r\n</table>\r\n \r\n<map name=\"Map\">\r\n  <area shape=\"rect\" coords=\"643,23,725,48\" href=\"http://www.tcs.com/\" target=\"_blank\">\r\n</map>\r\n</BODY>\r\n</HTML>', '2006-09-13 22:33:20', null, null, '1', '1', null, null, '2', 'EMAIL_BOX_0000000081', 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000841', null, '\"??????????\" <jobagent@mx91.mail.chinahr.com>', null, null, null, '您在中华英才网订阅的职位', '<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\r\n\"http://www.w3.org/TR/html4/loose.dtd\">\r\n<html>\r\n	<head>\r\n		<title>您在中华英才网订阅的职位―ChinaHR</title>\r\n		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GB2312\">\r\n<style type=\"text/css\">\r\n<!--\r\nbody {\r\n	margin-left: 0px;\r\n	margin-top: 0px;\r\n	margin-right: 0px;\r\n	margin-bottom: 0px;\r\n	color: #676767;\r\n	line-height: 18px;\r\n	font-size: 12px;\r\n}\r\nA:link {\r\n	FONT-SIZE: 12px; COLOR: #313398; TEXT-DECORATION: none;\r\n}\r\nA:visited {\r\n	FONT-SIZE: 12px; COLOR: #313398; TEXT-DECORATION: none;\r\n}\r\nA:active {\r\n	FONT-SIZE: 12px; COLOR: #313398; TEXT-DECORATION: underline;\r\n}\r\nA:hover {\r\n	FONT-SIZE: 12px; COLOR: #313398; TEXT-DECORATION: underline;\r\n}\r\n.redLink{\r\n	FONT-SIZE: 14px;\r\n}\r\n.redLink A:link {\r\n	FONT-SIZE: 14px; COLOR: #FF0000; TEXT-DECORATION: underline;\r\n}\r\n.redLink A:visited {\r\n	FONT-SIZE: 14px; COLOR: #FF0000; TEXT-DECORATION: underline;\r\n}\r\n.redLink A:active {\r\n	FONT-SIZE: 14px; COLOR: #FF0000; TEXT-DECORATION: underline;\r\n}\r\n.redLink A:hover {\r\n	FONT-SIZE: 14px; COLOR: #FF0000; TEXT-DECORATION: underline;\r\n}\r\n.main_tabel {\r\n	border: 1px solid #FF6600;\r\n}\r\n.normal_orange14 {\r\n	font-size: 14px;\r\n	color: #FF6600;\r\n}\r\n.normal_orange16 {\r\n	font-size: 14px;\r\n	color: #FF6600;\r\n	font-weight: bold;\r\n}\r\n.normal_black12 {\r\n	font-size: 12px;\r\n	color: #000000;\r\n}\r\n.normal_black121 {\r\n	font-size: 12px;\r\n	color: #000000;\r\n	font-family: Verdana, Arial, Helvetica, sans-serif;\r\n}\r\n.normal_red12 {\r\n	font-size: 12px;\r\n	color: #ff6600;\r\n}\r\n.normal_white12 {\r\n	font-size: 12px;\r\n	color: #FFFFFF;\r\n}\r\n.style1 {color: #4D4D4D}\r\n\r\n-->\r\n</style></head>\r\n\r\n<body>\r\n		<table width=\"750\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n			<tr>\r\n				<td height=\"10\"><table width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table2\">\r\n						<tr>\r\n							<td height=\"10\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n						</tr>\r\n					</table>\r\n					<TABLE width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"main_tabel\"\r\n						ID=\"Table3\">\r\n						<TR>\r\n							<TD><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" background=\"http://image.chinahr.com/a/sjob/images/email_03.gif\"\r\n									ID=\"Table4\">\r\n									<tr>\r\n										<td width=\"8\">&nbsp;</td>\r\n										<td><img src=\"http://image.chinahr.com/a/sjob/images/email_04.gif\" width=\"188\" height=\"77\"></td>\r\n									</tr>\r\n								</table>\r\n								<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table5\">\r\n									<tr>\r\n										<td height=\"20\" class=\"normal_orange14\">Hi lgstarzkhl\r\n										</td>\r\n									</tr>\r\n									<tr>\r\n										<td height=\"24\" class=\"normal_black12\">感谢您在ChinaHR订阅职位，您订阅的“<span class=\'normal_orange14\'>高级软件工程师,软件工程师,互联网软件开发工程师</span>”具体条件是：</td>\r\n									</tr>\r\n                <tr>\r\n                  <td height=\"12\"></td>\r\n                </tr>\r\n								</table>\r\n								<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table15\">\r\n									<tr>\r\n										<td width=\"12\" height=\"13\"><img src=\"http://image.chinahr.com/a/sjob/images/email_08.gif\" width=\"12\" height=\"13\"></td>\r\n										<td height=\"13\" background=\"http://image.chinahr.com/a/sjob/images/email_13.gif\"><img src=\"http://image.chinahr.com/a/sjob/images/email_13.gif\" width=\"1\" height=\"13\"></td>\r\n										<td width=\"12\" height=\"13\"><img src=\"http://image.chinahr.com/a/sjob/images/email_10.gif\" width=\"12\" height=\"13\"></td>\r\n									</tr>\r\n									<tr>\r\n										<td background=\"http://image.chinahr.com/a/sjob/images/email_16.gif\">&nbsp;</td>\r\n										<td bgcolor=\"#f0f0f0\">计算机/互联网・电子商务+高级软件工程师/软件工程师/互联网软件开发工程师+沈阳+面议<br>\r\n                    ・<a href=\"http://SearchJob.chinahr.com/JobAgentSubscibe.aspx?JobAgentID=10317209&SRC=Email&SD=2006-09-14\">重新设置/修改这些条件</a>\r\n										</td>\r\n										<td background=\"http://image.chinahr.com/a/sjob/images/email_14.gif\">&nbsp;</td>\r\n									</tr>\r\n									<tr>\r\n										<td height=\"13\"><img src=\"http://image.chinahr.com/a/sjob/images/email_11.gif\" width=\"12\" height=\"13\"></td>\r\n										<td height=\"13\" background=\"http://image.chinahr.com/a/sjob/images/email_15.gif\"><img src=\"http://image.chinahr.com/a/sjob/images/email_15.gif\" width=\"1\" height=\"13\"></td>\r\n										<td height=\"13\"><img src=\"http://image.chinahr.com/a/sjob/images/email_12.gif\" width=\"12\" height=\"13\"></td>\r\n									</tr>\r\n								</table>\r\n								<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table6\">\r\n									<tr>\r\n										<td height=\"12\"></td>\r\n									</tr>\r\n									<tr>\r\n										<td height=\"20\">符合条件的职位共<span  class=\"normal_red12\">16</span>个，目前仅能显示<span  class=\"normal_red12\">16</span>个 <a href=\"http://SearchJob.chinahr.com/SearchResult.aspx?indIDList=100,3600&occIDList=1001018,1001035,1001019&myLocIDList=120&myLocParentIDList=4000&isInterView=1&SRC=Email&SD=2006-09-14\">查看满足条件的全部职位</a></td>\r\n									</tr>\r\n								</table>\r\n								<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FF6600\" class=\"normal_white12\" ID=\"Table7\">\r\n									<tr>\r\n										<td width=\"154\" height=\"28\">&nbsp;&nbsp;职位名称</td>\r\n										<td width=\"136\">&nbsp;&nbsp;发布日期</td>\r\n										<td width=\"104\">&nbsp;&nbsp;公司名称</td>\r\n										<td width=\"65\">&nbsp;&nbsp;工作地点</td>\r\n										<td width=\"77\">&nbsp;&nbsp;月薪</td>\r\n										<td width=\"83\">&nbsp;</td>\r\n									</tr>\r\n								</table>\r\n								<!--P0-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20040717005963000166&createdate=2006-05-12&SRC=Email&SD=2006-09-14\" target=\"_blank\">游戏策划人员及高级讲师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-13</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=200407170059630004&SRC=Email&SD=2006-09-14\" target=\"blank\">北京汇众益智科技有限公司沈阳培训中心</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责：\r\n1.严格按照教学规范和教学大纲规定的内容授课\r\n2.监督学员学习情况，包括课堂纪律、考勤、作业收集等\r\n3.批改学员作业，对学员提出的问题给予解答\r\n4.课下对学员进行辅导\r\n5.指导学员完成项目设计、开发、测试等工作\r\n6</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-13</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-12-07</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;10</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;100 - 499人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;大专</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20040717005963000166&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20040717005963000166&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P1-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20040717005963000211&createdate=2006-08-10&SRC=Email&SD=2006-09-14\" target=\"_blank\">3D游戏设计高级讲师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-13</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20040717005963&SRC=Email&SD=2006-09-14\" target=\"blank\">北京汇众益智科技有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责：\r\n1. 严格按照教学规范和教学大纲规定的内容授课\r\n2. 监督学员学习情况，包括课堂纪律、考勤、作业收集等\r\n3. 批改学员作业，对学员提出的问题给予解答\r\n4. 课下对学员进行辅导\r\n5. 指导学员完成项目设计、开发、测试等</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-13</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2007-06-06</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;10</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;100 - 499人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;大专</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20040717005963000211&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20040717005963000211&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P2-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20040717005963000210&createdate=2006-08-10&SRC=Email&SD=2006-09-14\" target=\"_blank\">游戏策划人员及高级讲师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-13</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20040717005963&SRC=Email&SD=2006-09-14\" target=\"blank\">北京汇众益智科技有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责：\r\n1.严格按照教学规范和教学大纲规定的内容授课\r\n2.监督学员学习情况，包括课堂纪律、考勤、作业收集等\r\n3.批改学员作业，对学员提出的问题给予解答\r\n4.课下对学员进行辅导\r\n5.指导学员完成项目设计、开发、测试等工作\r\n6</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-13</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2007-06-06</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;11</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;100 - 499人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;大专</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20040717005963000210&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20040717005963000210&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P3-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20060904001645000011&createdate=2006-09-13&SRC=Email&SD=2006-09-14\" target=\"_blank\">通用软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-13</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20060904001645&SRC=Email&SD=2006-09-14\" target=\"blank\">北京森融天华投资顾问有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责： \r\n1、从事公司基础软件产品的研发工作；  \r\n2、负责公司软件产品的模块开发和问题解决。 \r\n\r\n职位要求： \r\n1、计算机及相关专业毕业，大学本科以上学历，一年以上相关工作经验； \r\n2、具有软件独立开发、策化与分析能力；</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-13</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-23</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;5</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1 - 49人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20060904001645000011&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20060904001645000011&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P4-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20060904001645000007&createdate=2006-09-08&SRC=Email&SD=2006-09-14\" target=\"_blank\">通用软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-13</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20060904001645&SRC=Email&SD=2006-09-14\" target=\"blank\">北京森融天华投资顾问有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责：\r\n1、从事公司基础软件产品的研发工作； \r\n2、负责公司软件产品的模块开发和问题解决。\r\n\r\n职位要求：\r\n1、计算机及相关专业毕业，大学本科以上学历，一年以上相关工作经验；\r\n2、具有软件独立开发、策化与分析能力； \r\n3、</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-13</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-18</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;5</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1 - 49人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20060904001645000007&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20060904001645000007&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P5-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20060904001645000008&createdate=2006-09-10&SRC=Email&SD=2006-09-14\" target=\"_blank\">软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-13</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20060904001645&SRC=Email&SD=2006-09-14\" target=\"blank\">北京森融天华投资顾问有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责： \r\n1、从事公司基础软件产品的研发工作；  \r\n2、负责公司软件产品的模块开发和问题解决。 \r\n\r\n职位要求： \r\n1、计算机及相关专业毕业，大学本科以上学历，一年以上相关工作经验； \r\n2、具有软件独立开发、策化与分析能力；</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-13</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-10</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;2</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1 - 49人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20060904001645000008&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20060904001645000008&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P6-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20060904001645000009&createdate=2006-09-10&SRC=Email&SD=2006-09-14\" target=\"_blank\">软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-13</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20060904001645&SRC=Email&SD=2006-09-14\" target=\"blank\">北京森融天华投资顾问有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责：  \r\n1、从事公司基础软件产品的研发工作；   \r\n2、负责公司软件产品的模块开发和问题解决。  \r\n\r\n\r\n\r\n\r\n职位要求：  \r\n1、计算机及相关专业毕业，大学本科以上学历，一年以上相关工作经验；  \r\n2、具有软件独立</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-13</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-10</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;2</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1 - 49人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20060904001645000009&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20060904001645000009&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P7-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=11200300370529000385&createdate=2006-09-01&SRC=Email&SD=2006-09-14\" target=\"_blank\">网管软件开发资深工程师C28-DL</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-11</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=11200300370529&SRC=Email&SD=2006-09-14\" target=\"blank\">Achievo Information Technology Co.,Ltd.（USA）</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1,4+ years programming experience and extensive knowledge of Java, web applications (including servlet containers), mul</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-11</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-01</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;5</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;外商独资．外企办事处</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;500 - 999人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;4年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=11200300370529000385&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=11200300370529000385&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P8-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=11200300370529000384&createdate=2006-09-01&SRC=Email&SD=2006-09-14\" target=\"_blank\"> 网络管理软件开发经理C28-DL</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-11</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=11200300370529&SRC=Email&SD=2006-09-14\" target=\"blank\">Achievo Information Technology Co.,Ltd.（USA）</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1,8+ years of software development experience with 4+ years experience in telecommunication industry\r\nSystem design and</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-11</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-01</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;1</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;外商独资．外企办事处</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;500 - 999人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;5年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;不限</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=11200300370529000384&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=11200300370529000384&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P9-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=11200300370529000386&createdate=2006-09-01&SRC=Email&SD=2006-09-14\" target=\"_blank\">网管软件开发工程师C28-DL</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-11</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=11200300370529&SRC=Email&SD=2006-09-14\" target=\"blank\">Achievo Information Technology Co.,Ltd.（USA）</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1,2-3 years program experience and knowledge of Java, web applications (including servlet containers), thread programmi</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-11</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-01</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;6</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;外商独资．外企办事处</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;500 - 999人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;3年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;不限</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=11200300370529000386&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=11200300370529000386&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P10-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=22200200851010000012&createdate=2006-06-02&SRC=Email&SD=2006-09-14\" target=\"_blank\">软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-11</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=22200200851010&SRC=Email&SD=2006-09-14\" target=\"blank\">东软集团</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1.本科以上学历，计算机或相关专业；\r\n2.精通Java、Unix C、VB、C\\C++、VC、.NET中的一种语言编程	\r\n3.两年以上开发经验\r\n4.有日语基础或愿意学习日语 \r\n有意者请将个人简历以邮件方式发送至yang.zhao@</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-11</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-12-31</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;80</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;中外合营(合资．合作)</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1000人以上</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;2年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=22200200851010000012&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=22200200851010000012&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P11-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=22200200851010000011&createdate=2006-06-02&SRC=Email&SD=2006-09-14\" target=\"_blank\">嵌入式软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-11</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=22200200851010&SRC=Email&SD=2006-09-14\" target=\"blank\">东软集团</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1.本科以上学历，三年以上软件开发经验(含1年以上C语言开发经验)\r\n2.有在下述任何一种OS（Linux，VxWORKS，μITRON，ｐSOS，QNX，OS9，Neucleu，Windows等）上的软件实际开发经验\r\n3.日语能力：国</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-11</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-12-31</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;10</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;中外合营(合资．合作)</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1000人以上</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;3年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=22200200851010000011&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=22200200851010000011&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P12-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=22200200851010000013&createdate=2006-06-02&SRC=Email&SD=2006-09-14\" target=\"_blank\">资深软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-11</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=22200200851010&SRC=Email&SD=2006-09-14\" target=\"blank\">东软集团</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1.本科以上学历，计算机或相关专业\r\n2.精通Java、Unix C、VB、C\\C++、VC、.NET中的一种语言编程	\r\n3.五年以上开发经验\r\n4.有日语基础或愿意学习日语\r\n有意者请将个人简历以邮件方式发送至yang.zhao@ne</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-11</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-12-31</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;30</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;中外合营(合资．合作)</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1000人以上</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;5年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=22200200851010000013&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=22200200851010000013&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P13-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=22200200851010000023&createdate=2006-08-14&SRC=Email&SD=2006-09-14\" target=\"_blank\">嵌入式软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-11</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=22200200851010&SRC=Email&SD=2006-09-14\" target=\"blank\">东软集团</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>大学本科以上学历，计算机相关专业\r\n从事嵌入式软件开发工作两年年以上，熟练使用C/C++\r\n有日语基础或愿意学习日语</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-11</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-13</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;3</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;中外合营(合资．合作)</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1000人以上</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;2年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=22200200851010000023&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=22200200851010000023&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P14-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=11200039611101000020&createdate=2005-10-19&SRC=Email&SD=2006-09-14\" target=\"_blank\">急聘对日软件高级PM</a></td>\r\n					<td width=\"136\">&nbsp;2006-08-23</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=11200039611101&SRC=Email&SD=2006-09-14\" target=\"blank\">北京新思软件技术有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>职责：负责与日本客户进行技术谈判与交流，进行系统设计与详细设计，组织协调并亲自参与项目作业小组实施编程与测试作业，保证作业能如期保质完成。在整个作业期间，负责全组人员的调配；工程进度和质量的控制；技术难题的解决；与上级，与客户及与其他小组</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-08-23</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2007-01-26</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;3</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;中外合营(合资．合作)</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;500 - 999人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;5年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=11200039611101000020&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=11200039611101000020&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P15-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=11200039611101000022&createdate=2005-10-25&SRC=Email&SD=2006-09-14\" target=\"_blank\">对日软件工程师（SE）</a></td>\r\n					<td width=\"136\">&nbsp;2006-08-23</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=11200039611101&SRC=Email&SD=2006-09-14\" target=\"blank\">北京新思软件技术有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>职责：\r\n1、具有系统设计经验；\r\n2、精通JAVA或C/C++或.NET或COBOL；或精通大型信息系统的安全性、数据容错、备份和恢复的设计，以及具体的安全措施实施过程； \r\n3、计算机本科以上学历；\r\n4、日语三级以上，或者英语优秀者</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-08-23</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2007-02-24</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;10</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;中外合营(合资．合作)</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;500 - 999人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;3年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=11200039611101000022&SRC=Email&SD=2006-09-14\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=11200039611101000022&SRC=Email&SD=2006-09-14\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n\r\n								<!--职位结束-->\r\n								<br>\r\n								<table width=\"620\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table8\">\r\n									<tr>        \r\n										<td><hr size=\"1\" color=\"#dcdcdc\">&nbsp;<a href=\"http://SearchJob.chinahr.com/SearchResult.aspx?indIDList=100,3600&occIDList=1001018,1001035,1001019&myLocIDList=120&myLocParentIDList=4000&isInterView=1&SRC=Email&SD=2006-09-14\">查看满足条件的全部职位</a></td>\r\n									</tr>\r\n								</table>\r\n								<table width=\"620\" height=\"4\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table10\">\r\n									<tr>\r\n										<td height=\"15\"><img src=\"images/space.gif\" width=\"1\" height=\"1\"></td>\r\n									</tr>\r\n								</table>\r\n							</TD>\r\n						</TR>\r\n					</TABLE>\r\n					<table width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table19\">\r\n						<tr>\r\n							<td height=\"10\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n						</tr>\r\n					</table>\r\n					<table width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table9\">\r\n                <tr>\r\n                  <td>・如果您对我们的服务有任何不满或建议，请<a href=\"mailto:servicebj@corp.chinahr.com\">联系我们</a>。\r\n                  <br>\r\n                    ・如果您已经找到满意工作，请点击此处<a href=\"http://My.chinahr.com/JobAgentNew/CancalJobAgent.aspx?AgentID=10317209&SRC=Email&SD=2006-09-14\">取消订阅</a>。<br><br></td>\r\n          </table>\r\n				</td>\r\n			</tr>\r\n		</table>\r\n	</body>\r\n</html>\r\n', '2006-09-14 18:41:17', null, null, '1', '1', null, null, '2', 'EMAIL_BOX_0000000081', 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000861', null, '\"ChinaHR.com\" <maillist@mailsrv.Chinahr.com>', null, null, null, '工作礼品任你拿，填简历赢时尚大奖', '\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n<title>求职 招聘 中华英才网！</title>\r\n<style>\r\nbody,tr,td,p,table\r\n{\r\n    \r\n    font-size:9pt;\r\n    line-height:140%;\r\n	margin-left: 0px;\r\n	margin-top: 0px;\r\n	margin-right: 0px;\r\n	margin-bottom: 0px;\r\n}\r\n\r\n\r\nA:link {COLOR:#000000;text-decoration:none}\r\nA:visited {COLOR:#000000;text-decoration:none}\r\nA:acitve {COLOR:#000000;text-decoration:underline}\r\nA:hover {COLOR:#000000;text-decoration:underline}\r\n\r\n-->\r\n</style>\r\n</head>\r\n\r\n<body>\r\n<center>\r\n<!--AdForward Show:直接刺激简历赢大奖1x1up Begin -->\r\n<IFRAME MARGINHEIGHT=\"0\" MARGINWIDTH=\"0\" FRAMEBORDER=\"0\" WIDTH=\"1\" HEIGHT=\"1\" SCROLLING=\"NO\" SRC=\"http://chinahrafaad.allyes.com/main/adfshow?user=Allyes.Network|直邮|直接刺激简历赢大奖1x1up&db=chinahrafaad&border=0&local=yes\">\r\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"http://chinahrafaad.allyes.com/main/adfshow?user=Allyes.Network|直邮|直接刺激简历赢大奖1x1up&db=chinahrafaad&local=yes&js=on\"></SCRIPT></IFRAME>\r\n<!--AdForward Show:直接刺激简历赢大奖1x1up End -->\r\n  <table width=\"500\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n    <tr>\r\n      <td><table width=\"498\" height=\"73\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n          <tr> \r\n            <td><div align=\"center\"><a href=\"http://chinahrafaad.allyes.com/main/adfclick?db=chinahrafaad&bid=30692,15578,101&cid=11358,28,1&sid=31210&show=ignore&url=http://www.chinahr.com\" target=\"_blank\"><img src=\"http://tjbn.allyes.com/khb/images/edm_11.gif\" alt=\"\" width=\"140\" height=\"42\" border=\"0\"></a></div></td>\r\n            <td><a href=\"http://chinahrafaad.allyes.com/main/adfclick?db=chinahrafaad&bid=30708,15579,101&cid=11359,28,1&sid=31212&show=ignore&url=http://my.chinahr.com/IndexLogin.aspx\" target=\"_blank\"><img src=\"http://tjbn.allyes.com/khb/images/edm_08.gif\" alt=\"\" width=\"81\" height=\"51\" border=\"0\"></a></td>\r\n            <td><a href=\"http://chinahrafaad.allyes.com/main/adfclick?db=chinahrafaad&bid=30710,15580,101&cid=11360,28,1&sid=31214&show=ignore&url=http://searchjob.chinahr.com/\" target=\"_blank\"><img src=\"http://tjbn.allyes.com/khb/images/edm_03.gif\" alt=\"\" width=\"111\" height=\"51\" border=\"0\"></a></td>\r\n            <td><a href=\"http://chinahrafaad.allyes.com/main/adfclick?db=chinahrafaad&bid=30712,15581,101&cid=11361,28,1&sid=31216&show=ignore&url=http://my.chinahr.com/Resume/IndexLogin.aspx\" target=\"_blank\"><img src=\"http://tjbn.allyes.com/khb/images/edm_05.gif\" alt=\"\" width=\"131\" height=\"51\" border=\"0\"></a></td>\r\n          </tr>\r\n        </table>\r\n        <table width=\"498\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n          <tr> \r\n            <td><a href=\"http://chinahrafaad.allyes.com/main/adfclick?db=chinahrafaad&bid=30714,15582,101&cid=11362,28,1&sid=31218&show=ignore&url=http://my.chinahr.com/IndexLogin.aspx\" target=\"_blank\"><img src=\"http://tjbn.allyes.com/khb/images/edm_18.gif\" alt=\"\" width=\"498\" height=\"65\" border=\"0\"></a></td>\r\n          </tr>\r\n        </table>\r\n        <table width=\"498\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n          <tr> \r\n            <td><a href=\"http://chinahrafaad.allyes.com/main/adfclick?db=chinahrafaad&bid=30714,15582,101&cid=11362,28,1&sid=31218&show=ignore&url=http://my.chinahr.com/IndexLogin.aspx\" target=\"_blank\"><img src=\"http://tjbn.allyes.com/khb/images/edm_20.gif\" alt=\"\" width=\"307\" height=\"132\" border=\"0\"></a></td>\r\n            <td rowspan=\"2\"><a href=\"http://chinahrafaad.allyes.com/main/adfclick?db=chinahrafaad&bid=30714,15582,101&cid=11362,28,1&sid=31218&show=ignore&url=http://my.chinahr.com/IndexLogin.aspx\" target=\"_blank\"><img src=\"http://tjbn.allyes.com/khb/images/edm_01.gif\" alt=\"\" width=\"191\" height=\"200\" border=\"0\"></a></td>\r\n          </tr>\r\n          <tr> \r\n            <td><a href=\"http://chinahrafaad.allyes.com/main/adfclick?db=chinahrafaad&bid=30716,15583,101&cid=11363,28,1&sid=31220&show=ignore&url=http://my.chinahr.com/IndexLogin.aspx\" target=\"_blank\"><img src=\"http://tjbn.allyes.com/khb/images/edm_22.gif\" alt=\"\" width=\"307\" height=\"68\" border=\"0\"></a></td>\r\n          </tr>\r\n        </table>\r\n        <table width=\"498\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n          <tr> \r\n            <td width=\"4\"><img src=\"http://tjbn.allyes.com/khb/images/s.gif\" width=\"4\" height=\"4\"></td>\r\n            <td bgcolor=\"E9FBFF\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n                <tr> \r\n                  <td width=\"4%\">&nbsp;</td>\r\n                  <td colspan=\"2\"><a href=\"http://chinahrafaad.allyes.com/main/adfclick?db=chinahrafaad&bid=30720,15585,101&cid=11365,28,1&sid=31224&show=ignore&url=http://my.chinahr.com/IndexLogin.aspx\" target=\"_blank\">你是英才的注册会员吗？你还没有提交简历吗？<br>\r\n                    英才有奖提交简历活动现已开始！<br>\r\n                    活动时间从即日起到2006年9月15日<br>\r\n                    凡新增简历者均有机会获奖！</a></td>\r\n                </tr>\r\n                <tr> \r\n                  <td>&nbsp;</td>\r\n                  <td width=\"57%\" valign=\"top\"><a href=\"http://chinahrafaad.allyes.com/main/adfclick?db=chinahrafaad&bid=30720,15585,101&cid=11365,28,1&sid=31224&show=ignore&url=http://my.chinahr.com/IndexLogin.aspx\" target=\"_blank\">奖品设置如下：<br>\r\n                    一等奖：IPOD 3个<br>\r\n                    二等奖：瑞士军刀 100个<br>\r\n                    三等奖：新影联电影票 1000张</a><br>\r\n                    <br>\r\n                  </td>\r\n                  <td width=\"39%\"><a href=\"http://chinahrafaad.allyes.com/main/adfclick?db=chinahrafaad&bid=30718,15584,101&cid=11364,28,1&sid=31222&show=ignore&url=http://my.chinahr.com/Resume/IndexLogin.aspx\" target=\"_blank\"><img src=\"http://tjbn.allyes.com/khb/images/edm_26.gif\" alt=\"\" width=\"120\" height=\"60\" border=\"0\"></a></td>\r\n                </tr>\r\n              </table></td>\r\n            <td width=\"165\"><a href=\"http://chinahrafaad.allyes.com/main/adfclick?db=chinahrafaad&bid=30714,15582,101&cid=11362,28,1&sid=31218&show=ignore&url=http://my.chinahr.com/IndexLogin.aspx\" target=\"_blank\"><img src=\"http://tjbn.allyes.com/khb/images/edm_24.gif\" alt=\"\" width=\"165\" height=\"171\" border=\"0\"></a></td>\r\n          </tr>\r\n        </table>\r\n        <table width=\"498\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n          <tr>\r\n            <td height=\"70\">\r\n<div align=\"center\">Copyright &copy; ChinaHR.com Corporation. \r\n                All rights reserved. 中华英才网&reg; 版权所有<br>\r\n                本网所有咨讯内容、广告信息、未经书面同意，不得转载。<br>\r\n                中国权威<strong><a href=\"http://chinahrafaad.allyes.com/main/adfclick?db=chinahrafaad&bid=30720,15585,101&cid=11365,28,1&sid=31224&show=ignore&url=http://www.chinahr.com\" target=\"_blank\">招聘</a>、<a href=\"http://chinahrafaad.allyes.com/main/adfclick?db=chinahrafaad&bid=30720,15585,101&cid=11365,28,1&sid=31224&show=ignore&url=http://www.chinahr.com\" target=\"_blank\">求职</a></strong>网站--中华英才网&reg;<br>\r\n              </div></td>\r\n          </tr>\r\n        </table></td>\r\n    </tr>\r\n  </table>\r\n  <!--AdForward Show:直接刺激简历赢大奖1x1down Begin -->\r\n<IFRAME MARGINHEIGHT=\"0\" MARGINWIDTH=\"0\" FRAMEBORDER=\"0\" WIDTH=\"1\" HEIGHT=\"1\" SCROLLING=\"NO\" SRC=\"http://chinahrafaad.allyes.com/main/adfshow?user=Allyes.Network|直邮|直接刺激简历赢大奖1x1down&db=chinahrafaad&border=0&local=yes\">\r\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"http://chinahrafaad.allyes.com/main/adfshow?user=Allyes.Network|直邮|直接刺激简历赢大奖1x1down&db=chinahrafaad&local=yes&js=on\"></SCRIPT></IFRAME>\r\n<!--AdForward Show:直接刺激简历赢大奖1x1down End -->\r\n</center>\r\n</body>\r\n</html>\r\n\r\n\r\n\r\n\r\n', '2006-09-15 20:02:11', null, null, '1', '1', null, null, '2', 'EMAIL_BOX_0000000081', 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000863', null, 'Brainbench <communication@brainbench.com>', null, null, null, 'The 2006 Global Skills Report', '<div class=\"fontColor\" align=\"center\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:11px;\">If you cannot read this message, please click <a class=\"linkColor\" href=\"http://gw.vtrenz.net/?THNG4VE1ZJ:THNG4VE1ZJ=ssID:39008350,email:zhaoyifei1@tom.com,mode:live\">here</a></div>\r\n				<table align=\"center\" height=\"25\" width=\"615\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" borderColor=\"ffffff\" bgColor=\"ffffff\" >\r\n				<tr>\r\n					<td valign=\"top\" align=\"left\" style=\"padding-top:5; padding-left:5; padding-bottom:5; padding-right:5;\">\r\n						\r\n<TABLE style=\"BORDER-RIGHT: #000 2px solid; BORDER-TOP: #000 2px solid; BORDER-LEFT: #000 2px solid; BORDER-BOTTOM: #000 2px solid\" cellSpacing=0 cellPadding=0 width=565 border=0><!--DWLayoutTable-->\r\n<TBODY>\r\n<TR vAlign=center>\r\n<TD vAlign=center align=left bgColor=#ff6600 colSpan=4 height=60><A href=\"http://gw.vtrenz.net/?THNG4VE1ZJ:HJB4FUE9S1=ssID:39008350,email:zhaoyifei1@tom.com\"><IMG height=44 alt=\"Brainbench logo\" hspace=10 src=\"http://www.brainbench.com/avmedia/images/default/bblogosmall.gif\" width=174 border=0></A> </TD></TR>\r\n<TR vAlign=center bgColor=#336699>\r\n<TD vAlign=top colSpan=4 height=19><!--DWLayoutEmptyCell-->&nbsp;</TD></TR>\r\n<TR vAlign=center>\r\n<TD vAlign=bottom width=209 height=245><BR>\r\n<DIV align=center><IMG style=\"MARGIN-BOTTOM: -5px\" height=15 alt=\"Global Skills Report\" hspace=0 src=\"http://www.brainbench.com/avmedia/images/bb/globalskills.gif\" width=129 border=0> <BR><SPAN style=\"FONT-WEIGHT: bold; FONT-SIZE: 60px; COLOR: #f60; FONT-FAMILY: Arial\">2006</SPAN> <IMG height=133 alt=\"Bald guy looking through magnifying glass\" hspace=0 src=\"http://www.brainbench.com/avmedia/images/bb/baldymagnify.jpg\" width=112 align=bottom border=0> &nbsp;</DIV></TD>\r\n<TD width=11>&nbsp;</TD>\r\n<TD vAlign=top align=left width=320 rowSpan=2 padding=\"10\" cell>\r\n<DIV align=left>\r\n<P><FONT face=\"Verdana, Arial, Helvetica, sans-serif\" color=#ff6600 size=3><BR><STRONG><FONT size=4>Hot Off the Press... The 2006 Global Skills Report!</FONT></STRONG></FONT></P>\r\n<P><FONT face=\"Verdana, Arial, Helvetica, sans-serif\" size=2>As a valued customer of Brainbench, we wish to thank you by giving you special notice of the release of our 2006 Global Skills Report. This report ranks the most popular skills in the United States and around the globe. </FONT></P>\r\n<P><FONT face=\"Verdana, Arial, Helvetica, sans-serif\" size=2>We reveal the most commonly certified skills, trends, and changes from our 2005 study, and offer insights into these results as well as peer into the future of the global skills marketplace. We hope you will find this report informative and thought provoking as it was compiled with your interests in mind.</FONT></P>\r\n<P><FONT face=\"Verdana, Arial, Helvetica, sans-serif\" size=2><A href=\"http://gw.vtrenz.net/?THNG4VE1ZJ:MR14IJYH6K=ssID:39008350,email:zhaoyifei1@tom.com\"><STRONG>Download your complimentary copy!</STRONG></A></FONT></P>\r\n<HR>\r\n\r\n<P><FONT face=\"Verdana, Arial, Helvetica, sans-serif\" size=2>Also, through September 30, over 30 Brainbench certification tests (typically $49.95) are available to you at <STRONG>no cost</STRONG> through sponsorships made by our partners. Upgrade your resume - impress your boss - show off to your friends - and it will cost you nothing but your own time investment. <A href=\"http://gw.vtrenz.net/?THNG4VE1ZJ:QTN4X6RRIV=ssID:39008350,email:zhaoyifei1@tom.com\">Get started today!</A></FONT></P>\r\n<P>&nbsp;</P></DIV></TD>\r\n<TD width=21>&nbsp;</TD></TR>\r\n<TR vAlign=center>\r\n<TD vAlign=center bgColor=#ff6600 height=103>\r\n<DIV align=center>\r\n<P>&nbsp;</P>\r\n<P><FONT face=\"Verdana, Arial, Helvetica, sans-serif\" size=2>Brainbench\'s current certification tests available <BR>at no charge include:</FONT></P>\r\n<P><FONT face=\"Verdana, Arial, Helvetica, sans-serif\" size=2><FONT color=#ffffff>C#, <BR>ASP.NET,<BR>Computer Forensics,<BR>RDBMS, <BR>J2EE, <BR>Computer Technical Support,<BR>MS Office 2003,<BR>Project Management, <BR><A href=\"http://gw.vtrenz.net/?THNG4VE1ZJ:QTN4X6RRIV=ssID:39008350,email:zhaoyifei1@tom.com\">and many more!</A></FONT></FONT> <BR></P>\r\n<P> </P></DIV></TD>\r\n<TD>&nbsp;</TD>\r\n<TD></TD></TR></TBODY></TABLE>\r\n\r\n					</td>\r\n				</tr>\r\n				</table>\r\n				\r\n<style>\r\n	.fontColor { color:000000; }\r\n	.linkColor { color:0000FF; }\r\n</style>\r\n\r\n\r\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\r\n	<tr>\r\n		<td class=\"fontColor\" align=\"center\" nowrap valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:11px;\">\r\n			This message was sent to <b>zhaoyifei1@tom.com</b> by:\r\n		</td>\r\n	</tr>\r\n	<tr>\r\n		<td class=\"fontColor\" align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:11px;\">\r\n			<div style=\"font-weight:bold;\">Brainbench</div>\r\n			14100 Parke Long Court<br> Suite K<br>\r\n			Chantilly, VA 20151<br>\r\n			703-437-4800<br>\r\n			\r\n				<a class=\"linkColor\" href=\" \" target=\"_blank\"> </a>\r\n			\r\n				 | \r\n			\r\n				<a class=\"linkColor\" href=\"mailto:questions@brainbench.com\">questions@brainbench.com</a>\r\n			\r\n			<br><br>\r\n			\r\n				<a class=\"linkColor\" href=\"http://gw.vtrenz.net/?PE9MCR3RX0=ssID:39008350,email:zhaoyifei1@tom.com\" target=\"_blank\">Modify Your Profile</a> | \r\n			\r\n			<a class=\"linkColor\" href=\"http://gw.vtrenz.net/?HYT5UMPU1H=ssID:39008350,email:zhaoyifei1@tom.com\" target=\"_blank\">Unsubscribe</a>\r\n			\r\n				 | <a class=\"linkColor\" href=\"http://gw.vtrenz.net/?S2MAHPIPOZ=ssID:39008350,email:zhaoyifei1@tom.com\" target=\"_blank\">Forward To A Friend</a>\r\n			\r\n		</td>\r\n	</tr>\r\n	<tr>\r\n		<td class=\"fontColor\" align=\"center\" valign=\"middle\" style=\"padding:20px;\"><img src=\"http://gw.vtrenz.net/?YB1SUXK8TF=ssID:39008350\" border=\"0\" width=\"50\" height=\"15\"></td>\r\n	</tr>\r\n	\r\n</table> ', '2006-09-16 06:58:59', null, null, '1', '1', null, null, '2', 'EMAIL_BOX_0000000121', 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000881', null, '=?GBK?B?zNSxpg==?= <service@smtp.mail.taobao.com>', null, null, null, '今天是个特别的日子，你还记得吗？', '<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n<title>今天是个特别的日子，你还记得吗？</title>\r\n<style type=\"text/css\">\r\n<!--\r\nbody,td,th {\r\n	font-size: 12px;\r\n	line-height: 130%;\r\n}\r\n.LM {line-height:120%;}\r\n-->\r\n</style>\r\n</head>\r\n\r\n<body>\r\n<table width=\"570\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  <tr>\r\n    <td><table width=\"570\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n      <base target=\"_blank\" />\r\n      <tr>\r\n        <td colspan=\"2\" valign=\"top\"><img src=\"http://pics.taobao.com/bao/album/promotion/dm_top_570.gif\" width=\"570\" height=\"100\" border=\"0\" usemap=\"#MapmailnavigatorMap\" />\r\n            <map name=\"MapmailnavigatorMap\" id=\"Mapmailnavigator22\">\r\n              <area shape=\"rect\" coords=\"3,68,45,98\" href=\"http://www.taobao.com/\" />\r\n              <area shape=\"rect\" coords=\"49,68,107,98\" href=\"http://www.taobao.com/vertical/digital.php\" />\r\n              <area shape=\"rect\" coords=\"110,69,154,98\" href=\"http://www.taobao.com/vertical/lady.php\" />\r\n              <area shape=\"rect\" coords=\"156,69,198,97\" href=\"http://www.taobao.com/vertical/man.php\" />\r\n              <area shape=\"rect\" coords=\"200,69,243,99\" href=\"http://www.taobao.com/vertical/life.php\" />\r\n              <area shape=\"rect\" coords=\"244,68,307,102\" href=\"http://www.taobao.com/vertical/culture.php\" />\r\n              <area shape=\"rect\" coords=\"309,68,352,99\" href=\"http://www.taobao.com/vertical/sports.php\" />\r\n              <area shape=\"rect\" coords=\"353,68,398,102\" href=\"http://www.taobao.com/vertical/game.php\" />\r\n              <area shape=\"rect\" coords=\"399,67,442,100\" href=\"http://www.taobao.com/vertical/collection.php\" />\r\n              <area shape=\"rect\" coords=\"443,68,488,98\" href=\"http://www.taobao.com/vertical/hkstreet.php\" />\r\n              <area shape=\"rect\" coords=\"4,6,325,62\" href=\"http://www.taobao.com\" />\r\n              <area shape=\"rect\" coords=\"489,67,550,98\" href=\"http://www.taobao.com/vertical/mall/index.php?from=top\" />\r\n          </map></td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>\r\n  <tr>\r\n    <td><table width=\"570\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n      <tr>\r\n        <td rowspan=\"2\"><img src=\"http://pics.taobao.com/bao/album/marketchannel/060831_custom_01.gif\" width=\"192\" height=\"192\" alt=\"\" /></td>\r\n        <td colspan=\"2\"><img src=\"http://pics.taobao.com/bao/album/marketchannel/060831_custom_02.gif\" width=\"174\" height=\"152\" alt=\"\" /></td>\r\n        <td colspan=\"2\"><img src=\"http://pics.taobao.com/mail/060406_custom1_03.gif\" width=\"204\" height=\"152\" alt=\"\" /></td>\r\n        <td><img src=\"http://pics.taobao.com/mail/spacer.gif\" width=\"1\" height=\"152\" alt=\"\" /></td>\r\n      </tr>\r\n      <tr>\r\n        <td><img src=\"http://pics.taobao.com/mail/060406_custom1_04.gif\" width=\"131\" height=\"40\" alt=\"\" /></td>\r\n        <td rowspan=\"3\"><img src=\"http://pics.taobao.com/mail/060406_custom1_05.gif\" width=\"43\" height=\"65\" alt=\"\" /></td>\r\n        <td rowspan=\"2\"><a href=\"http://www.taobao.com/\" target=\"_blank\"><img src=\"http://pics.taobao.com/bao/album/chl/fp/060406_custom4_06.gif\" alt=\"\" width=\"125\" height=\"52\" border=\"0\" /></a></td>\r\n        <td rowspan=\"4\"><img src=\"http://pics.taobao.com/bao/album/chl/fp/060406_custom4_07.gif\" width=\"79\" height=\"89\" alt=\"\" /></td>\r\n        <td><img src=\"http://pics.taobao.com/mail/spacer.gif\" width=\"1\" height=\"40\" alt=\"\" /></td>\r\n      </tr>\r\n      <tr>\r\n        <td rowspan=\"2\"><img src=\"http://pics.taobao.com/mail/060406_custom1_08.gif\" width=\"192\" height=\"25\" alt=\"\" /></td>\r\n        <td rowspan=\"2\" background=\"http://pics.taobao.com/mail/060406_custom1_09.gif\">bystar888</td>\r\n        <td><img src=\"http://pics.taobao.com/mail/spacer.gif\" width=\"1\" height=\"12\" alt=\"\" /></td>\r\n      </tr>\r\n      <tr>\r\n        <td rowspan=\"2\"><a href=\"http://member1.taobao.com/member/forgot_passwd.jhtml\" target=\"_blank\"><img src=\"http://pics.taobao.com/bao/album/chl/fp/060406_custom4_10.gif\" alt=\"\" width=\"125\" height=\"37\" border=\"0\" /></a></td>\r\n        <td><img src=\"http://pics.taobao.com/mail/spacer.gif\" width=\"1\" height=\"13\" alt=\"\" /></td>\r\n      </tr>\r\n      <tr>\r\n        <td colspan=\"3\"><img src=\"http://pics.taobao.com/mail/060406_custom1_11.gif\" width=\"366\" height=\"24\" alt=\"\" /></td>\r\n        <td><img src=\"http://pics.taobao.com/mail/spacer.gif\" width=\"1\" height=\"24\" alt=\"\" /></td>\r\n      </tr>\r\n      <tr>\r\n        <td><img src=\"http://pics.taobao.com/mail/060406_custom1_12.gif\" width=\"192\" height=\"164\" alt=\"\" /></td>\r\n        <td colspan=\"2\"><img src=\"http://pics.taobao.com/mail/060406_custom1_13.gif\" width=\"174\" height=\"164\" alt=\"\" /></td>\r\n        <td colspan=\"2\"><img src=\"http://pics.taobao.com/bao/album/chl/fp/060406_custom4_14.gif\" width=\"204\" height=\"164\" alt=\"\" /></td>\r\n        <td><img src=\"http://pics.taobao.com/mail/spacer.gif\" width=\"1\" height=\"164\" alt=\"\" /></td>\r\n      </tr>\r\n      <tr>\r\n        <td><img src=\"http://pics.taobao.com/mail/060406_custom1_15.gif\" width=\"192\" height=\"203\" alt=\"\" /></td>\r\n        <td colspan=\"2\"><img src=\"http://pics.taobao.com/mail/060406_custom1_16.gif\" width=\"174\" height=\"203\" alt=\"\" /></td>\r\n        <td colspan=\"2\"><img src=\"http://pics.taobao.com/mail/060406_custom1_17.gif\" width=\"204\" height=\"203\" alt=\"\" /></td>\r\n        <td><img src=\"http://pics.taobao.com/mail/spacer.gif\" width=\"1\" height=\"203\" alt=\"\" /></td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>\r\n  <tr>\r\n    <td align=\"center\"><span style=\"font-size:12px;\"><br />\r\n      如有任何疑问，请联系淘宝客服，或者访问淘宝网<a href=\"http://www.taobao.com/\" target=\"_blank\">http://www.taobao.com</a>与我们取得联系。<br>\r\n      客服热线：0571-88157858 传真：0571-88157828 Email： service@taobao.com<br>\r\n        Copyright 2003-2006, 版权所有 TAOBAO.COM    </span></td>\r\n  </tr>\r\n</table>\r\n</body>\r\n</html>', '2006-09-16 18:00:31', null, null, '1', '1', null, null, '2', 'EMAIL_BOX_0000000081', 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000961', 'zhaoyifei', 'zhaoyifei1@tom.com', 'zhaoyifei1@tom.com', null, null, '来个带附件的', '<DIV>来个带附件的</DIV>', '2006-09-19 14:25:33', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000962', 'zhaoyifei', 'zhaoyifei1@tom.com', 'zhaoyifei1@tom.com', null, null, '发个测试', '<DIV>发个测试</DIV>', '2006-09-19 14:35:48', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000964', 'zhaoyifei', 'zhaoyifei1@tom.com', 'zhaoyifei1@tom.com', null, null, '再来一个', '<DIV>再来一个</DIV>', '2006-09-19 14:36:41', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000965', 'zhaoyifei', 'zhaoyifei1@tom.com', 'zhaoyifei1@tom.com', null, null, '再来一个', '<DIV>再来一个</DIV>', '2006-09-19 14:36:51', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000966', 'zhaoyifei', 'zhaoyifei1@163.com', 'zhaoyifei1@163.com', null, null, '再来一个', '<DIV>再来一个</DIV>', '2006-09-19 14:37:22', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000967', 'zhaoyifei', 'lgstar888@163.com', 'lgstar888@163.com', null, null, '测试邮件', '<DIV>测试邮件</DIV>', '2006-09-19 14:38:25', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000969', 'zhaoyifei', 'zhaoyifei1@tom.com', 'zhaoyifei1@tom.com', null, null, '测试邮件', '<DIV>测试邮件</DIV>', '2006-09-19 14:39:02', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000971', 'zhaoyifei', 'zhaoyifei1@163.com', 'zhaoyifei1@163.com', null, null, '发个测试邮件', '<DIV>发个测试邮件</DIV>', '2006-09-19 14:40:43', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000972', 'zhaoyifei', 'zhaoyifei1@163.com', 'zhaoyifei1@163.com', null, null, '发送邮件主题', '<DIV>发送邮件主题</DIV>', '2006-09-19 14:42:03', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000973', 'zhaoyifei', 'lgstar888@163.com', 'lgstar888@163.com', null, null, '测试方法', '<DIV><FONT color=#ff0000>测试方法</FONT></DIV>', '2006-09-19 15:03:29', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000974', 'zhaoyifei', 'lgstar888@163.com', 'lgstar888@163.com', null, null, '测试成功', '<DIV>测试成功</DIV>', '2006-09-19 15:05:42', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000981', 'zhaoyifei', 'lgstar888@163.com', 'lgstar888@163.com', null, null, '发送文件', '<DIV>发送文件</DIV>', '2006-09-19 15:07:46', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000983', 'zhaoyifei', 'lgstar888@163.com', 'lgstar888@163.com', null, null, '邮件本地化成功', '<DIV>邮件本地化成功</DIV>', '2006-09-19 15:11:33', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000985', 'zhaoyifei', 'lgstar888@163.com', 'lgstar888@163.com', null, null, '发送', '<DIV>发送</DIV>', '2006-09-19 15:13:32', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001001', 'zhaoyifei', 'lgstar888@163.com', 'lgstar888@163.com', null, null, '发送邮件', '<DIV>发送邮件</DIV>', '2006-09-19 15:15:44', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001003', 'zhaoyifei', 'lgstar888@163.com', 'lgstar888@163.com', null, null, 'imimchengong', '<DIV>imimchengong</DIV>', '2006-09-19 15:19:28', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001004', 'zhaoyifei', 'zhaoyifei1@tom.com', 'zhaoyifei1@tom.com', null, null, 'aaaaaa', '<DIV>aaaaaaaaaaaa</DIV>', '2006-09-19 15:19:49', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001007', 'zhaoyifei', 'lgstar888@163.com', 'lgstar888@163.com', null, null, '发送邮件成功', '<DIV>发送邮件成功</DIV>', '2006-09-19 15:38:09', null, '', '2', '1', '1', '2', '2', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001021', 'yy', '', 'yy', 'yy', 'yy', 'yy', '<DIV>yyy</DIV>', '2006-09-20 15:59:42', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001022', 'yy', 'yy', 'yy', 'yy', 'yy', 'yy', '<DIV>yyy</DIV>', '2006-09-20 15:59:43', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001041', 'zhaoyifei', 'zhaoyifei1@tom.com', 'zhaoyifei1@tom.com', null, null, '带附件的邮件', '<DIV>带附件的邮件</DIV>', '2006-09-21 09:17:17', null, '', '2', '1', '1', '2', '2', 'EMAIL_BOX_0000000121', 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001042', '', 'zhaoyifei1@tom.com', null, null, null, '带附件的邮件', '<DIV>带附件的邮件</DIV>', '2006-09-21 09:17:17', null, null, '1', '1', null, null, '2', 'EMAIL_BOX_0000000121', 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001043', '', '\"??????????\" <jobagent@mx89.mail.chinahr.com>', null, null, null, '您在中华英才网订阅的职位', '<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\r\n\"http://www.w3.org/TR/html4/loose.dtd\">\r\n<html>\r\n	<head>\r\n		<title>您在中华英才网订阅的职位―ChinaHR</title>\r\n		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GB2312\">\r\n<style type=\"text/css\">\r\n<!--\r\nbody {\r\n	margin-left: 0px;\r\n	margin-top: 0px;\r\n	margin-right: 0px;\r\n	margin-bottom: 0px;\r\n	color: #676767;\r\n	line-height: 18px;\r\n	font-size: 12px;\r\n}\r\nA:link {\r\n	FONT-SIZE: 12px; COLOR: #313398; TEXT-DECORATION: none;\r\n}\r\nA:visited {\r\n	FONT-SIZE: 12px; COLOR: #313398; TEXT-DECORATION: none;\r\n}\r\nA:active {\r\n	FONT-SIZE: 12px; COLOR: #313398; TEXT-DECORATION: underline;\r\n}\r\nA:hover {\r\n	FONT-SIZE: 12px; COLOR: #313398; TEXT-DECORATION: underline;\r\n}\r\n.redLink{\r\n	FONT-SIZE: 14px;\r\n}\r\n.redLink A:link {\r\n	FONT-SIZE: 14px; COLOR: #FF0000; TEXT-DECORATION: underline;\r\n}\r\n.redLink A:visited {\r\n	FONT-SIZE: 14px; COLOR: #FF0000; TEXT-DECORATION: underline;\r\n}\r\n.redLink A:active {\r\n	FONT-SIZE: 14px; COLOR: #FF0000; TEXT-DECORATION: underline;\r\n}\r\n.redLink A:hover {\r\n	FONT-SIZE: 14px; COLOR: #FF0000; TEXT-DECORATION: underline;\r\n}\r\n.main_tabel {\r\n	border: 1px solid #FF6600;\r\n}\r\n.normal_orange14 {\r\n	font-size: 14px;\r\n	color: #FF6600;\r\n}\r\n.normal_orange16 {\r\n	font-size: 14px;\r\n	color: #FF6600;\r\n	font-weight: bold;\r\n}\r\n.normal_black12 {\r\n	font-size: 12px;\r\n	color: #000000;\r\n}\r\n.normal_black121 {\r\n	font-size: 12px;\r\n	color: #000000;\r\n	font-family: Verdana, Arial, Helvetica, sans-serif;\r\n}\r\n.normal_red12 {\r\n	font-size: 12px;\r\n	color: #ff6600;\r\n}\r\n.normal_white12 {\r\n	font-size: 12px;\r\n	color: #FFFFFF;\r\n}\r\n.style1 {color: #4D4D4D}\r\n\r\n-->\r\n</style></head>\r\n\r\n<body>\r\n		<table width=\"750\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n			<tr>\r\n				<td height=\"10\"><table width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table2\">\r\n						<tr>\r\n							<td height=\"10\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n						</tr>\r\n					</table>\r\n					<TABLE width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"main_tabel\"\r\n						ID=\"Table3\">\r\n						<TR>\r\n							<TD><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" background=\"http://image.chinahr.com/a/sjob/images/email_03.gif\"\r\n									ID=\"Table4\">\r\n									<tr>\r\n										<td width=\"8\">&nbsp;</td>\r\n										<td><img src=\"http://image.chinahr.com/a/sjob/images/email_04.gif\" width=\"188\" height=\"77\"></td>\r\n									</tr>\r\n								</table>\r\n								<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table5\">\r\n									<tr>\r\n										<td height=\"20\" class=\"normal_orange14\">Hi lgstarzkhl\r\n										</td>\r\n									</tr>\r\n									<tr>\r\n										<td height=\"24\" class=\"normal_black12\">感谢您在ChinaHR订阅职位，您订阅的“<span class=\'normal_orange14\'>高级软件工程师,软件工程师,互联网软件开发工程师</span>”具体条件是：</td>\r\n									</tr>\r\n                <tr>\r\n                  <td height=\"12\"></td>\r\n                </tr>\r\n								</table>\r\n								<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table15\">\r\n									<tr>\r\n										<td width=\"12\" height=\"13\"><img src=\"http://image.chinahr.com/a/sjob/images/email_08.gif\" width=\"12\" height=\"13\"></td>\r\n										<td height=\"13\" background=\"http://image.chinahr.com/a/sjob/images/email_13.gif\"><img src=\"http://image.chinahr.com/a/sjob/images/email_13.gif\" width=\"1\" height=\"13\"></td>\r\n										<td width=\"12\" height=\"13\"><img src=\"http://image.chinahr.com/a/sjob/images/email_10.gif\" width=\"12\" height=\"13\"></td>\r\n									</tr>\r\n									<tr>\r\n										<td background=\"http://image.chinahr.com/a/sjob/images/email_16.gif\">&nbsp;</td>\r\n										<td bgcolor=\"#f0f0f0\">计算机/互联网・电子商务+高级软件工程师/软件工程师/互联网软件开发工程师+沈阳+面议<br>\r\n                    ・<a href=\"http://SearchJob.chinahr.com/JobAgentSubscibe.aspx?JobAgentID=10317209&SRC=Email&SD=2006-09-20\">重新设置/修改这些条件</a>\r\n										</td>\r\n										<td background=\"http://image.chinahr.com/a/sjob/images/email_14.gif\">&nbsp;</td>\r\n									</tr>\r\n									<tr>\r\n										<td height=\"13\"><img src=\"http://image.chinahr.com/a/sjob/images/email_11.gif\" width=\"12\" height=\"13\"></td>\r\n										<td height=\"13\" background=\"http://image.chinahr.com/a/sjob/images/email_15.gif\"><img src=\"http://image.chinahr.com/a/sjob/images/email_15.gif\" width=\"1\" height=\"13\"></td>\r\n										<td height=\"13\"><img src=\"http://image.chinahr.com/a/sjob/images/email_12.gif\" width=\"12\" height=\"13\"></td>\r\n									</tr>\r\n								</table>\r\n								<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table6\">\r\n									<tr>\r\n										<td height=\"12\"></td>\r\n									</tr>\r\n									<tr>\r\n										<td height=\"20\">符合条件的职位共<span  class=\"normal_red12\">22</span>个，目前仅能显示<span  class=\"normal_red12\">20</span>个 <a href=\"http://SearchJob.chinahr.com/SearchResult.aspx?indIDList=100,3600&occIDList=1001018,1001035,1001019&myLocIDList=120&myLocParentIDList=4000&isInterView=1&SRC=Email&SD=2006-09-20\">查看满足条件的全部职位</a></td>\r\n									</tr>\r\n								</table>\r\n								<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FF6600\" class=\"normal_white12\" ID=\"Table7\">\r\n									<tr>\r\n										<td width=\"154\" height=\"28\">&nbsp;&nbsp;职位名称</td>\r\n										<td width=\"136\">&nbsp;&nbsp;发布日期</td>\r\n										<td width=\"104\">&nbsp;&nbsp;公司名称</td>\r\n										<td width=\"65\">&nbsp;&nbsp;工作地点</td>\r\n										<td width=\"77\">&nbsp;&nbsp;月薪</td>\r\n										<td width=\"83\">&nbsp;</td>\r\n									</tr>\r\n								</table>\r\n								<!--P0-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20060904001645000007&createdate=2006-09-08&SRC=Email&SD=2006-09-20\" target=\"_blank\">通用软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-19</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20060904001645&SRC=Email&SD=2006-09-20\" target=\"blank\">北京森融天华投资顾问有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责：\r\n1、从事公司基础软件产品的研发工作； \r\n2、负责公司软件产品的模块开发和问题解决。\r\n\r\n职位要求：\r\n1、计算机及相关专业毕业，大学本科以上学历，一年以上相关工作经验；\r\n2、具有软件独立开发、策化与分析能力； \r\n3、</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-19</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-18</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;5</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1 - 49人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20060904001645000007&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20060904001645000007&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P1-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20060904001645000009&createdate=2006-09-10&SRC=Email&SD=2006-09-20\" target=\"_blank\">软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-19</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20060904001645&SRC=Email&SD=2006-09-20\" target=\"blank\">北京森融天华投资顾问有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责：  \r\n1、从事公司基础软件产品的研发工作；   \r\n2、负责公司软件产品的模块开发和问题解决。  \r\n\r\n\r\n\r\n\r\n职位要求：  \r\n1、计算机及相关专业毕业，大学本科以上学历，一年以上相关工作经验；  \r\n2、具有软件独立</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-19</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-10</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;2</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1 - 49人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20060904001645000009&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20060904001645000009&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P2-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20060904001645000015&createdate=2006-09-16&SRC=Email&SD=2006-09-20\" target=\"_blank\">C++工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-19</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20060904001645&SRC=Email&SD=2006-09-20\" target=\"blank\">北京森融天华投资顾问有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责：  \r\n1、从事公司基础软件产品的研发工作；   \r\n2、负责公司软件产品的模块开发和问题解决。 \r\n\r\n\r\n \r\n\r\n职位要求：  \r\n1、计算机及相关专业毕业，大学本科以上学历，一年以上相关工作经验；  \r\n2、具有软件独立</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-19</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-16</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;2</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1 - 49人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20060904001645000015&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20060904001645000015&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P3-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20040717005963000210&createdate=2006-08-10&SRC=Email&SD=2006-09-20\" target=\"_blank\">游戏策划人员及高级讲师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-19</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20040717005963&SRC=Email&SD=2006-09-20\" target=\"blank\">北京汇众益智科技有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责：\r\n1.严格按照教学规范和教学大纲规定的内容授课\r\n2.监督学员学习情况，包括课堂纪律、考勤、作业收集等\r\n3.批改学员作业，对学员提出的问题给予解答\r\n4.课下对学员进行辅导\r\n5.指导学员完成项目设计、开发、测试等工作\r\n6</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-19</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2007-06-06</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;11</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;100 - 499人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;大专</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20040717005963000210&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20040717005963000210&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P4-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20040717005963000211&createdate=2006-08-10&SRC=Email&SD=2006-09-20\" target=\"_blank\">3D游戏设计高级讲师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-19</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20040717005963&SRC=Email&SD=2006-09-20\" target=\"blank\">北京汇众益智科技有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责：\r\n1. 严格按照教学规范和教学大纲规定的内容授课\r\n2. 监督学员学习情况，包括课堂纪律、考勤、作业收集等\r\n3. 批改学员作业，对学员提出的问题给予解答\r\n4. 课下对学员进行辅导\r\n5. 指导学员完成项目设计、开发、测试等</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-19</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2007-06-06</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;10</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;100 - 499人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;大专</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20040717005963000211&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20040717005963000211&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P5-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20040717005963000166&createdate=2006-05-12&SRC=Email&SD=2006-09-20\" target=\"_blank\">游戏策划人员及高级讲师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-19</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=200407170059630004&SRC=Email&SD=2006-09-20\" target=\"blank\">北京汇众益智科技有限公司沈阳培训中心</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责：\r\n1.严格按照教学规范和教学大纲规定的内容授课\r\n2.监督学员学习情况，包括课堂纪律、考勤、作业收集等\r\n3.批改学员作业，对学员提出的问题给予解答\r\n4.课下对学员进行辅导\r\n5.指导学员完成项目设计、开发、测试等工作\r\n6</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-19</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-12-07</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;10</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;100 - 499人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;大专</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20040717005963000166&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20040717005963000166&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P6-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=11200300370529000384&createdate=2006-09-01&SRC=Email&SD=2006-09-20\" target=\"_blank\"> 网络管理软件开发经理C28-DL</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-19</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=11200300370529&SRC=Email&SD=2006-09-20\" target=\"blank\">Achievo Information Technology Co.,Ltd.（USA）</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1,8+ years of software development experience with 4+ years experience in telecommunication industry\r\nSystem design and</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-19</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-01</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;1</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;外商独资．外企办事处</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;500 - 999人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;5年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;不限</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=11200300370529000384&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=11200300370529000384&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P7-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=11200300370529000385&createdate=2006-09-01&SRC=Email&SD=2006-09-20\" target=\"_blank\">网管软件开发资深工程师C28-DL</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-19</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=11200300370529&SRC=Email&SD=2006-09-20\" target=\"blank\">Achievo Information Technology Co.,Ltd.（USA）</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1,4+ years programming experience and extensive knowledge of Java, web applications (including servlet containers), mul</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-19</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-01</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;5</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;外商独资．外企办事处</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;500 - 999人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;4年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=11200300370529000385&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=11200300370529000385&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P8-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=11200300370529000386&createdate=2006-09-01&SRC=Email&SD=2006-09-20\" target=\"_blank\">网管软件开发工程师C28-DL</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-19</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=11200300370529&SRC=Email&SD=2006-09-20\" target=\"blank\">Achievo Information Technology Co.,Ltd.（USA）</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1,2-3 years program experience and knowledge of Java, web applications (including servlet containers), thread programmi</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-19</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-01</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;6</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;外商独资．外企办事处</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;500 - 999人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;3年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;不限</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=11200300370529000386&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=11200300370529000386&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P9-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=11200300370529000392&createdate=2006-09-14&SRC=Email&SD=2006-09-20\" target=\"_blank\">软件开发经理（1名）美国项目 C28-DL</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-19</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=11200300370529&SRC=Email&SD=2006-09-20\" target=\"blank\">Achievo Information Technology Co.,Ltd.（USA）</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1）8年以上软件开发经验，包括四年以上在电讯行业\r\n2）系统设计和架构经验\r\n3）包括基于Java开发的服务器，Linux/Unix，网络管理系统的开发经验\r\n4）拥有在网络，J2EE,JSP,EJB,Java,xml,https等开发背</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-19</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2007-09-14</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;1</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;外商独资．外企办事处</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;500 - 999人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;3年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;不限</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=11200300370529000392&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=11200300370529000392&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P10-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=11200300370529000393&createdate=2006-09-14&SRC=Email&SD=2006-09-20\" target=\"_blank\">软件工程师，高级（3-4名）美国项目 C28-DL</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-19</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=11200300370529&SRC=Email&SD=2006-09-20\" target=\"blank\">Achievo Information Technology Co.,Ltd.（USA）</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1）6年以上编程经验，广泛的Java, web applications (包括servlet containers), multi-threaded application设计 (locking, mutexs等使用)，丰富的网络和模式</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-19</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2007-09-14</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;4</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;外商独资．外企办事处</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;500 - 999人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;5年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;不限</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=11200300370529000393&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=11200300370529000393&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P11-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=11200300370529000394&createdate=2006-09-14&SRC=Email&SD=2006-09-20\" target=\"_blank\">软件工程师，初级（5-6名）美国项目 C28-DL</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-19</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=11200300370529&SRC=Email&SD=2006-09-20\" target=\"blank\">Achievo Information Technology Co.,Ltd.（USA）</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1）0-4年编程经验和Java, web applications (包括servlet containers),线编程，基本网络技能（TCP/IP等），部分网络和模式知识\r\n2）希望有DSL和SNMP的网络技术知识\r\n3）英语书写流利\r</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-19</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2007-09-14</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;6</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;外商独资．外企办事处</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;500 - 999人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;不限</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=11200300370529000394&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=11200300370529000394&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P12-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20060904001645000016&createdate=2006-09-18&SRC=Email&SD=2006-09-20\" target=\"_blank\">C++软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-18</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20060904001645&SRC=Email&SD=2006-09-20\" target=\"blank\">北京森融天华投资顾问有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责： \r\n1、从事公司基础软件产品的研发工作；  \r\n2、负责公司软件产品的模块开发和问题解决。 \r\n\r\n职位要求： \r\n1、计算机及相关专业毕业，大学本科以上学历，一年以上相关工作经验； \r\n2、具有软件独立开发、策化与分析能力；</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-18</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-18</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;2</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1 - 49人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;不限</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20060904001645000016&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20060904001645000016&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P13-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20060904001645000013&createdate=2006-09-14&SRC=Email&SD=2006-09-20\" target=\"_blank\">通用软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-18</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20060904001645&SRC=Email&SD=2006-09-20\" target=\"blank\">北京森融天华投资顾问有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责：\r\n1、从事公司基础软件产品的研发工作； \r\n2、负责公司软件产品的模块开发和问题解决。\r\n\r\n职位要求：\r\n1、计算机及相关专业毕业，大学本科以上学历，一年以上相关工作经验；\r\n2、具有软件独立开发、策化与分析能力； \r\n3、</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-18</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-24</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;5</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1 - 49人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20060904001645000013&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20060904001645000013&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P14-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20060904001645000011&createdate=2006-09-13&SRC=Email&SD=2006-09-20\" target=\"_blank\">通用软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-18</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20060904001645&SRC=Email&SD=2006-09-20\" target=\"blank\">北京森融天华投资顾问有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责： \r\n1、从事公司基础软件产品的研发工作；  \r\n2、负责公司软件产品的模块开发和问题解决。 \r\n\r\n职位要求： \r\n1、计算机及相关专业毕业，大学本科以上学历，一年以上相关工作经验； \r\n2、具有软件独立开发、策化与分析能力；</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-18</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-23</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;5</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1 - 49人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20060904001645000011&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20060904001645000011&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P15-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=20060904001645000008&createdate=2006-09-10&SRC=Email&SD=2006-09-20\" target=\"_blank\">软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-18</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=20060904001645&SRC=Email&SD=2006-09-20\" target=\"blank\">北京森融天华投资顾问有限公司</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>工作职责： \r\n1、从事公司基础软件产品的研发工作；  \r\n2、负责公司软件产品的模块开发和问题解决。 \r\n\r\n职位要求： \r\n1、计算机及相关专业毕业，大学本科以上学历，一年以上相关工作经验； \r\n2、具有软件独立开发、策化与分析能力；</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-18</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-10</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;2</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;私营．民营企业</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1 - 49人</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;1年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=20060904001645000008&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=20060904001645000008&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P16-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=22200200851010000011&createdate=2006-06-02&SRC=Email&SD=2006-09-20\" target=\"_blank\">嵌入式软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-14</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=22200200851010&SRC=Email&SD=2006-09-20\" target=\"blank\">东软集团</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1.本科以上学历，三年以上软件开发经验(含1年以上C语言开发经验)\r\n2.有在下述任何一种OS（Linux，VxWORKS，μITRON，ｐSOS，QNX，OS9，Neucleu，Windows等）上的软件实际开发经验\r\n3.日语能力：国</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-14</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-12-31</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;10</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;中外合营(合资．合作)</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1000人以上</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;3年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=22200200851010000011&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=22200200851010000011&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P17-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=22200200851010000012&createdate=2006-06-02&SRC=Email&SD=2006-09-20\" target=\"_blank\">软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-14</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=22200200851010&SRC=Email&SD=2006-09-20\" target=\"blank\">东软集团</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1.本科以上学历，计算机或相关专业；\r\n2.精通Java、Unix C、VB、C\\C++、VC、.NET中的一种语言编程	\r\n3.两年以上开发经验\r\n4.有日语基础或愿意学习日语 \r\n有意者请将个人简历以邮件方式发送至yang.zhao@</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-14</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-12-31</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;80</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;中外合营(合资．合作)</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1000人以上</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;2年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=22200200851010000012&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=22200200851010000012&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P18-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=22200200851010000013&createdate=2006-06-02&SRC=Email&SD=2006-09-20\" target=\"_blank\">资深软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-14</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=22200200851010&SRC=Email&SD=2006-09-20\" target=\"blank\">东软集团</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>1.本科以上学历，计算机或相关专业\r\n2.精通Java、Unix C、VB、C\\C++、VC、.NET中的一种语言编程	\r\n3.五年以上开发经验\r\n4.有日语基础或愿意学习日语\r\n有意者请将个人简历以邮件方式发送至yang.zhao@ne</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-14</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-12-31</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;30</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;中外合营(合资．合作)</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1000人以上</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;5年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=22200200851010000013&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=22200200851010000013&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--P19-->\n<table width=\"620\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F0F0F0\" ID=\"Table1\">\r\n	<tr>\r\n		<td width=\"12\"><p>&nbsp;</p>\r\n		</td>\r\n		<td><table width=\"525\" height=\"34\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"normal_black121\"\r\n				ID=\"Table2\">\r\n				<tr>\r\n					<td width=\"142\"><a href=\"http://SearchJob.chinahr.com/BrowseMulitJobInfo.aspx?jid=22200200851010000023&createdate=2006-08-14&SRC=Email&SD=2006-09-20\" target=\"_blank\">嵌入式软件工程师</a></td>\r\n					<td width=\"136\">&nbsp;2006-09-14</td>\r\n					<td width=\"104\">&nbsp;<a href=\"http://SearchJob.chinahr.com/BrowseCompanyInfo.aspx?companyID=22200200851010&SRC=Email&SD=2006-09-20\" target=\"blank\">东软集团</a>\r\n					</td>\r\n					<td width=\"65\">&nbsp;沈阳</td>\r\n					<td width=\"77\">&nbsp;面议</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table3\">\r\n				<tr>\r\n					<td>大学本科以上学历，计算机相关专业\r\n从事嵌入式软件开发工作两年年以上，熟练使用C/C++\r\n有日语基础或愿意学习日语</td>\r\n				</tr>\r\n			</table>\r\n			<table width=\"525\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ID=\"Table4\">\r\n				<tr>\r\n					<td width=\"60\">发布时间：</td>\r\n					<td width=\"190\">&nbsp;2006-09-14</td>\r\n					<td width=\"60\">截止日期：</td>\r\n					<td>&nbsp;2006-10-13</td>\r\n				</tr>\r\n				<tr>\r\n					<td>工作地点：</td>\r\n					<td>&nbsp;沈阳</td>\r\n					<td>招聘人数：</td>\r\n					<td>&nbsp;3</td>\r\n				</tr>\r\n				<tr>\r\n					<td>公司性质：</td>\r\n					<td>&nbsp;中外合营(合资．合作)</td>\r\n					<td>公司规模：</td>\r\n					<td>&nbsp;1000人以上</td>\r\n				</tr>\r\n				<tr>\r\n					<td>职位月薪：</td>\r\n					<td>&nbsp;面议</td>\r\n					<td>工作经验：</td>\r\n					<td>&nbsp;2年</td>\r\n				</tr>\r\n				<tr>\r\n					<td>最低学历：</td>\r\n					<td>&nbsp;本科</td>\r\n					<td>&nbsp;</td>\r\n					<td>&nbsp;</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n		<td width=\"83\" align=\"center\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"redLink\" ID=\"Table5\">\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobApply/Apply.aspx?jid=22200200851010000023&SRC=Email&SD=2006-09-20\" target=\"blank\"><img src=\"http://image.chinahr.com/a/sjob/images/email_09.gif\" width=\"51\" height=\"27\" border=\"0\"></a></div>\r\n					</td>\r\n				</tr>\r\n				<tr>\r\n					<td height=\"14\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n				</tr>\r\n				<tr>\r\n					<td><div align=\"center\"><a href=\"http://My.chinahr.com/JobFolder/SaveJobToFavorite.aspx?jid=22200200851010000023&SRC=Email&SD=2006-09-20\" target=\"blank\">收藏职位</a></div>\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<!--职位结束-->\r\n								<br>\r\n								<table width=\"620\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table8\">\r\n									<tr>        \r\n										<td><hr size=\"1\" color=\"#dcdcdc\">&nbsp;<a href=\"http://SearchJob.chinahr.com/SearchResult.aspx?indIDList=100,3600&occIDList=1001018,1001035,1001019&myLocIDList=120&myLocParentIDList=4000&isInterView=1&SRC=Email&SD=2006-09-20\">查看满足条件的全部职位</a></td>\r\n									</tr>\r\n								</table>\r\n								<table width=\"620\" height=\"4\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table10\">\r\n									<tr>\r\n										<td height=\"15\"><img src=\"images/space.gif\" width=\"1\" height=\"1\"></td>\r\n									</tr>\r\n								</table>\r\n							</TD>\r\n						</TR>\r\n					</TABLE>\r\n					<table width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table19\">\r\n						<tr>\r\n							<td height=\"10\"><img src=\"http://image.chinahr.com/a/sjob/images/space.gif\" width=\"1\" height=\"1\"></td>\r\n						</tr>\r\n					</table>\r\n					<table width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" ID=\"Table9\">\r\n                <tr>\r\n                  <td>・如果您对我们的服务有任何不满或建议，请<a href=\"mailto:servicebj@corp.chinahr.com\">联系我们</a>。\r\n                  <br>\r\n                    ・如果您已经找到满意工作，请点击此处<a href=\"http://My.chinahr.com/JobAgentNew/CancalJobAgent.aspx?AgentID=10317209&SRC=Email&SD=2006-09-20\">取消订阅</a>。<br><br></td>\r\n          </table>\r\n				</td>\r\n			</tr>\r\n		</table>\r\n	</body>\r\n</html>\r\n', '2006-09-20 16:54:17', null, null, '1', '1', null, null, '2', 'EMAIL_BOX_0000000081', 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001061', 'yy', '', 'yy', 'yy', 'yy', 'yy', '<DIV></DIV>', '2006-09-26 09:38:34', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001062', 'yy', 'yy', 'yy', 'yy', 'yy', 'yy', '<DIV></DIV>', '2006-09-26 09:38:34', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001081', 'yepuliang', '', 'guxiaofeng', 'guxiaofeng', 'guxiaofeng', '论坛后台计划估算', '<DIV>后台时间估算：</DIV>\r\n<DIV>1，用户管理：包括（核心功能用户列表，用户搜索）估算大约一天。</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>2.替换限制处理：（核心功能：帖子词语过滤，保留敏感用户名，屏蔽用户言论）大约1天半</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>3.论坛备份：（</DIV>\r\n<DIV>全部备份：包括全部论坛数据表数据。</DIV>\r\n<DIV>标准备份：包括常用的数据表数据。</DIV>\r\n<DIV>最小备份：仅包括用户、版块设置及系统设置数据。<BR>自定义备份：根据需要自行选择需要备份的数据表）</DIV>\r\n<DIV>半天到1天</DIV>\r\n<DIV>4.日志操作</DIV>\r\n<DIV>密码错误日志，用户评分日志，积分交易日志，版主管理日志，禁止用户日志，后台访问记录，系统错误记录。系统日志，用户日志，版主日志<BR>模块日志，论坛安全日志，积分操作日志</DIV>\r\n<DIV>（估计1天到3天具体情况定）</DIV>\r\n<DIV>5.扩展功能：email群发，短消息群发，计划任务。（1天到2天）</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>6，还有公告和广告的设置（估计一天吧）</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>很多东西时间我也把握的不太好（大约8天左右）</DIV>', '2006-12-20 11:08:39', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001083', 'zhangfeng', '', 'guxiaofeng', '', '', '工作计划写完了', '<FONT style=\"BACKGROUND-COLOR: #ffffff\">工作计划写完了，有时间的时候看一下!</FONT>\r\n<DIV></DIV>', '2006-12-20 16:10:31', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001101', 'guxiaofeng', '', 'liuyang,yepuliang,zhangfeng,yangshuo,jingyuzhuo,zhaoyifei,guxiaofeng,chenyiying', '', '', '周计划命名规则', '<DIV>周计划命名规则：用周一的时间加上周计划。例如</DIV>\r\n<DIV>20070129周计划</DIV>', '2007-01-30 09:34:35', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001102', 'yepuliang', '', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', '论坛新地址', '<DIV>论坛新地址</DIV>\r\n<DIV><A href=\"http://192.168.1.202:8089/ETforum/\">http://192.168.1.202:8089/ETforum/</A>　　有时间上去发帖子</DIV>', '2007-02-02 10:01:31', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001103', 'yepuliang', 'yepuliang', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', '论坛新地址', '<DIV>论坛新地址</DIV>\r\n<DIV><A href=\"http://192.168.1.202:8089/ETforum/\">http://192.168.1.202:8089/ETforum/</A>　　有时间上去发帖子</DIV>', '2007-02-02 10:01:31', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001104', 'yepuliang', 'zhangfeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', '论坛新地址', '<DIV>论坛新地址</DIV>\r\n<DIV><A href=\"http://192.168.1.202:8089/ETforum/\">http://192.168.1.202:8089/ETforum/</A>　　有时间上去发帖子</DIV>', '2007-02-02 10:01:31', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001105', 'yepuliang', 'jingyuzhuo', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', '论坛新地址', '<DIV>论坛新地址</DIV>\r\n<DIV><A href=\"http://192.168.1.202:8089/ETforum/\">http://192.168.1.202:8089/ETforum/</A>　　有时间上去发帖子</DIV>', '2007-02-02 10:01:31', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001106', 'yepuliang', 'zhaoyifei', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', '论坛新地址', '<DIV>论坛新地址</DIV>\r\n<DIV><A href=\"http://192.168.1.202:8089/ETforum/\">http://192.168.1.202:8089/ETforum/</A>　　有时间上去发帖子</DIV>', '2007-02-02 10:01:31', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001107', 'yepuliang', 'guxiaofeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng', '论坛新地址', '<DIV>论坛新地址</DIV>\r\n<DIV><A href=\"http://192.168.1.202:8089/ETforum/\">http://192.168.1.202:8089/ETforum/</A>　　有时间上去发帖子</DIV>', '2007-02-02 10:01:31', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001108', 'guxiaofeng', '', 'yepuliang,zhangfeng,zhaoyifei', '', '', '补保险', '<DIV>先补养老保险，到今年一月份。医疗保险由公司统一办理。</DIV>', '2007-02-06 16:59:48', null, '', '3', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001121', 'zhangfeng', '', 'yepuliang', '', '', '发个邮件做测试', '<DIV>发个邮件做测试</DIV>', '2007-01-30 11:10:37', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001122', 'zhangfeng', '', 'yepuliang,zhaoyifei', '', '', '我做的测试', '<DIV>我做的测试，发给赵一非，小白</DIV>', '2007-01-30 13:39:31', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001141', 'zhaoyifei', '', 'yepuliang,zhangfeng,guxiaofeng', '', '', '再发一封测试一下', '<DIV>再发一封测试一下</DIV>', '2007-01-30 14:24:43', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001142', 'zhangfeng', '', 'yepuliang,zhaoyifei,guxiaofeng', 'yepuliang,zhaoyifei,guxiaofeng', 'yepuliang,zhaoyifei,guxiaofeng', 'yepuliang,zhaoyifei,guxiaofeng', '<DIV>yepuliang,zhaoyifei,guxiaofeng</DIV>', '2007-01-30 14:28:26', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001143', 'zhangfeng', 'yepuliang', 'yepuliang,zhaoyifei,guxiaofeng', 'yepuliang,zhaoyifei,guxiaofeng', 'yepuliang,zhaoyifei,guxiaofeng', 'yepuliang,zhaoyifei,guxiaofeng', '<DIV>yepuliang,zhaoyifei,guxiaofeng</DIV>', '2007-01-30 14:29:55', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001144', 'zhangfeng', 'zhaoyifei', 'yepuliang,zhaoyifei,guxiaofeng', 'yepuliang,zhaoyifei,guxiaofeng', 'yepuliang,zhaoyifei,guxiaofeng', 'yepuliang,zhaoyifei,guxiaofeng', '<DIV>yepuliang,zhaoyifei,guxiaofeng</DIV>', '2007-01-30 14:30:00', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001146', 'zhaoyifei', '', 'yepuliang', '', '', '内部邮件', '<DIV>内部邮件</DIV>', '2007-01-30 14:33:32', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001147', 'yepuliang', '', 'guxiaofeng', 'guxiaofeng', 'guxiaofeng', '论坛后台计划估算', '<DIV>\r\n<DIV>后台时间估算：</DIV>\r\n<DIV>1，用户管理：包括（核心功能用户列表，用户搜索）估算大约一天。</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>2.替换限制处理：（核心功能：帖子词语过滤，保留敏感用户名，屏蔽用户言论）大约1天半</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>3.论坛备份：（</DIV>\r\n<DIV>全部备份：包括全部论坛数据表数据。</DIV>\r\n<DIV>标准备份：包括常用的数据表数据。</DIV>\r\n<DIV>最小备份：仅包括用户、版块设置及系统设置数据。<BR>自定义备份：根据需要自行选择需要备份的数据表）</DIV>\r\n<DIV>半天到1天</DIV>\r\n<DIV>4.日志操作</DIV>\r\n<DIV>密码错误日志，用户评分日志，积分交易日志，版主管理日志，禁止用户日志，后台访问记录，系统错误记录。系统日志，用户日志，版主日志<BR>模块日志，论坛安全日志，积分操作日志</DIV>\r\n<DIV>（估计1天到3天具体情况定）</DIV>\r\n<DIV>5.扩展功能：email群发，短消息群发，计划任务。（1天到2天）</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>6，还有公告和广告的设置（估计一天吧）</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>很多东西时间我也把握的不太好（大约8天左右）</DIV></DIV>', '2007-01-30 14:35:50', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001148', 'yepuliang', 'guxiaofeng', 'guxiaofeng', 'guxiaofeng', 'guxiaofeng', '论坛后台计划估算', '<DIV>\r\n<DIV>后台时间估算：</DIV>\r\n<DIV>1，用户管理：包括（核心功能用户列表，用户搜索）估算大约一天。</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>2.替换限制处理：（核心功能：帖子词语过滤，保留敏感用户名，屏蔽用户言论）大约1天半</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>3.论坛备份：（</DIV>\r\n<DIV>全部备份：包括全部论坛数据表数据。</DIV>\r\n<DIV>标准备份：包括常用的数据表数据。</DIV>\r\n<DIV>最小备份：仅包括用户、版块设置及系统设置数据。<BR>自定义备份：根据需要自行选择需要备份的数据表）</DIV>\r\n<DIV>半天到1天</DIV>\r\n<DIV>4.日志操作</DIV>\r\n<DIV>密码错误日志，用户评分日志，积分交易日志，版主管理日志，禁止用户日志，后台访问记录，系统错误记录。系统日志，用户日志，版主日志<BR>模块日志，论坛安全日志，积分操作日志</DIV>\r\n<DIV>（估计1天到3天具体情况定）</DIV>\r\n<DIV>5.扩展功能：email群发，短消息群发，计划任务。（1天到2天）</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>6，还有公告和广告的设置（估计一天吧）</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>很多东西时间我也把握的不太好（大约8天左右）</DIV></DIV>', '2007-01-30 14:35:50', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001161', 'zhaoyifei', '', 'yepuliang,zhangfeng,yangshuo,jingyuzhuo', '', '', '发送主题', '<DIV>发送主题发送主题发送主题发送主题</DIV>', '2007-01-30 15:02:39', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001181', 'guxiaofeng', '', 'yepuliang', '', '', '网站需求', '<P><FONT style=\"BACKGROUND-COLOR: #ffffff\">网站需求：</FONT></P>\r\n<P>已经完成（需要测试）：<BR>&nbsp;内容发布系统、论坛系统、网站版和样式、会员注册系统</P>\r\n<P>需要完成：<BR>计数器\\产品系统\\购物车系统电子商务系统\\在线反馈\\系统在线调查\\留言板系统\\在线招聘系统\\流量分析系统\\广告管理\\</P>\r\n<P>\\相册系统\\聊天\\博客系统\\交友系统\\多媒体视频\\</P>\r\n<DIV></DIV>', '2007-03-06 10:52:50', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001182', 'chenyiying', '', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei', '周六正常上班，请各位同事注意！', '周六正常上班，请各位同事注意！', '周六正常上班', '<DIV>&nbsp;</DIV>', '2007-03-09 15:55:29', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001183', 'chenyiying', '周六正常上班，请各位同事注意！', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei', '周六正常上班，请各位同事注意！', '周六正常上班，请各位同事注意！', '周六正常上班', '<DIV>&nbsp;</DIV>', '2007-03-09 15:55:32', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001184', 'zhaoyifei', 'yepuliang', 'yepuliang,zhangfeng,guxiaofeng', '', '', '页面信息', '<DIV>页面信息页面信息页面信息页面信息</DIV>', '2007-01-30 15:36:49', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001185', 'zhaoyifei', 'zhangfeng', 'yepuliang,zhangfeng,guxiaofeng', '', '', '页面信息', '<DIV>页面信息页面信息页面信息页面信息</DIV>', '2007-01-30 15:36:49', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001201', 'zhangfeng', '', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng,chenyiying', '', '', '计费管理系统测试', '<DIV>计费管理系统的链接如下：</DIV>\r\n<DIV><A href=\"http://192.168.1.9:8089/PbxCount/\">http://192.168.1.9:8089/PbxCount/</A></DIV>\r\n<DIV>测试用户均为管理员权限</DIV>\r\n<DIV>用户名：zhangfeng</DIV>\r\n<DIV>密码：zhangfeng</DIV>\r\n<DIV>&nbsp;测试出错误可按如下格式写，如：</DIV>\r\n<DIV>话费查询-&gt;基本话费查询，主叫号输入后不能查询到相对应的信息，列出的不是所要查询的信息，缺少时长等信息。</DIV>\r\n<DIV>&nbsp;</DIV>', '2007-03-15 09:13:55', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001202', 'zhangfeng', 'yepuliang', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng,chenyiying', '', '', '计费管理系统测试', '<DIV>计费管理系统的链接如下：</DIV>\r\n<DIV><A href=\"http://192.168.1.9:8089/PbxCount/\">http://192.168.1.9:8089/PbxCount/</A></DIV>\r\n<DIV>测试用户均为管理员权限</DIV>\r\n<DIV>用户名：zhangfeng</DIV>\r\n<DIV>密码：zhangfeng</DIV>\r\n<DIV>&nbsp;测试出错误可按如下格式写，如：</DIV>\r\n<DIV>话费查询-&gt;基本话费查询，主叫号输入后不能查询到相对应的信息，列出的不是所要查询的信息，缺少时长等信息。</DIV>\r\n<DIV>&nbsp;</DIV>', '2007-03-15 09:13:57', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001203', 'zhangfeng', 'zhangfeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng,chenyiying', '', '', '计费管理系统测试', '<DIV>计费管理系统的链接如下：</DIV>\r\n<DIV><A href=\"http://192.168.1.9:8089/PbxCount/\">http://192.168.1.9:8089/PbxCount/</A></DIV>\r\n<DIV>测试用户均为管理员权限</DIV>\r\n<DIV>用户名：zhangfeng</DIV>\r\n<DIV>密码：zhangfeng</DIV>\r\n<DIV>&nbsp;测试出错误可按如下格式写，如：</DIV>\r\n<DIV>话费查询-&gt;基本话费查询，主叫号输入后不能查询到相对应的信息，列出的不是所要查询的信息，缺少时长等信息。</DIV>\r\n<DIV>&nbsp;</DIV>', '2007-03-15 09:13:57', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001204', 'zhangfeng', 'jingyuzhuo', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng,chenyiying', '', '', '计费管理系统测试', '<DIV>计费管理系统的链接如下：</DIV>\r\n<DIV><A href=\"http://192.168.1.9:8089/PbxCount/\">http://192.168.1.9:8089/PbxCount/</A></DIV>\r\n<DIV>测试用户均为管理员权限</DIV>\r\n<DIV>用户名：zhangfeng</DIV>\r\n<DIV>密码：zhangfeng</DIV>\r\n<DIV>&nbsp;测试出错误可按如下格式写，如：</DIV>\r\n<DIV>话费查询-&gt;基本话费查询，主叫号输入后不能查询到相对应的信息，列出的不是所要查询的信息，缺少时长等信息。</DIV>\r\n<DIV>&nbsp;</DIV>', '2007-03-15 09:13:57', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001205', 'zhangfeng', 'zhaoyifei', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng,chenyiying', '', '', '计费管理系统测试', '<DIV>计费管理系统的链接如下：</DIV>\r\n<DIV><A href=\"http://192.168.1.9:8089/PbxCount/\">http://192.168.1.9:8089/PbxCount/</A></DIV>\r\n<DIV>测试用户均为管理员权限</DIV>\r\n<DIV>用户名：zhangfeng</DIV>\r\n<DIV>密码：zhangfeng</DIV>\r\n<DIV>&nbsp;测试出错误可按如下格式写，如：</DIV>\r\n<DIV>话费查询-&gt;基本话费查询，主叫号输入后不能查询到相对应的信息，列出的不是所要查询的信息，缺少时长等信息。</DIV>\r\n<DIV>&nbsp;</DIV>', '2007-03-15 09:13:57', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001206', 'zhangfeng', 'guxiaofeng', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng,chenyiying', '', '', '计费管理系统测试', '<DIV>计费管理系统的链接如下：</DIV>\r\n<DIV><A href=\"http://192.168.1.9:8089/PbxCount/\">http://192.168.1.9:8089/PbxCount/</A></DIV>\r\n<DIV>测试用户均为管理员权限</DIV>\r\n<DIV>用户名：zhangfeng</DIV>\r\n<DIV>密码：zhangfeng</DIV>\r\n<DIV>&nbsp;测试出错误可按如下格式写，如：</DIV>\r\n<DIV>话费查询-&gt;基本话费查询，主叫号输入后不能查询到相对应的信息，列出的不是所要查询的信息，缺少时长等信息。</DIV>\r\n<DIV>&nbsp;</DIV>', '2007-03-15 09:13:57', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001207', 'zhangfeng', 'chenyiying', 'yepuliang,zhangfeng,jingyuzhuo,zhaoyifei,guxiaofeng,chenyiying', '', '', '计费管理系统测试', '<DIV>计费管理系统的链接如下：</DIV>\r\n<DIV><A href=\"http://192.168.1.9:8089/PbxCount/\">http://192.168.1.9:8089/PbxCount/</A></DIV>\r\n<DIV>测试用户均为管理员权限</DIV>\r\n<DIV>用户名：zhangfeng</DIV>\r\n<DIV>密码：zhangfeng</DIV>\r\n<DIV>&nbsp;测试出错误可按如下格式写，如：</DIV>\r\n<DIV>话费查询-&gt;基本话费查询，主叫号输入后不能查询到相对应的信息，列出的不是所要查询的信息，缺少时长等信息。</DIV>\r\n<DIV>&nbsp;</DIV>', '2007-03-15 09:13:57', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001208', 'guxiaofeng', '', 'yepuliang,jingyuzhuo,zhaoyifei', '', '', '大家对于计费管理软件进行测试。', '<DIV>bug名称：话费查询查询信息有错误<BR>bug列表：1)话费查询-&gt;基本话费查询，主叫号输入后不能查询到相对应的信息，列出的不是所要查询的信息，缺少时长等信息。<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2)其它问题等等<BR>时间和发送人在论坛中应该能找到相应信息</DIV>', '2007-03-15 17:00:27', null, '', '3', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001209', 'guxiaofeng', '', 'yepuliang,jingyuzhuo,zhaoyifei', '', '', '测试张峰和莽珂做的计费软件', '<DIV>\r\n<DIV>\r\n<DIV>\r\n<DIV>计费管理系统的链接如下：</DIV>\r\n<DIV><A href=\"http://192.168.1.9:8089/PbxCount/\">http://192.168.1.9:8089/PbxCount/</A></DIV>\r\n<DIV>测试用户均为管理员权限</DIV>\r\n<DIV>用户名：zhangfeng</DIV>\r\n<DIV>密码：zhangfeng</DIV>\r\n<DIV>********************************</DIV>\r\n<DIV>在帖子中的格式如下：</DIV></DIV>\r\n<DIV>bug名称：话费查询查询信息有错误<BR>bug列表：1)话费查询-&gt;基本话费查询，主叫号输入后不能查询到相对应的信息，列出的不是所要查询的信息，缺少时长等信息。<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2)其它问题等等<BR>时间和发送人在论坛中应该能找到相应信息</DIV>\r\n<DIV>********************************</DIV>\r\n<DIV>请大家以后严格安装此格式进行测试。</DIV></DIV></DIV>', '2007-03-15 17:06:27', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001210', 'guxiaofeng', 'yepuliang', 'yepuliang,jingyuzhuo,zhaoyifei', '', '', '测试张峰和莽珂做的计费软件', '<DIV>\r\n<DIV>\r\n<DIV>\r\n<DIV>计费管理系统的链接如下：</DIV>\r\n<DIV><A href=\"http://192.168.1.9:8089/PbxCount/\">http://192.168.1.9:8089/PbxCount/</A></DIV>\r\n<DIV>测试用户均为管理员权限</DIV>\r\n<DIV>用户名：zhangfeng</DIV>\r\n<DIV>密码：zhangfeng</DIV>\r\n<DIV>********************************</DIV>\r\n<DIV>在帖子中的格式如下：</DIV></DIV>\r\n<DIV>bug名称：话费查询查询信息有错误<BR>bug列表：1)话费查询-&gt;基本话费查询，主叫号输入后不能查询到相对应的信息，列出的不是所要查询的信息，缺少时长等信息。<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2)其它问题等等<BR>时间和发送人在论坛中应该能找到相应信息</DIV>\r\n<DIV>********************************</DIV>\r\n<DIV>请大家以后严格安装此格式进行测试。</DIV></DIV></DIV>', '2007-03-15 17:06:27', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001211', 'guxiaofeng', 'jingyuzhuo', 'yepuliang,jingyuzhuo,zhaoyifei', '', '', '测试张峰和莽珂做的计费软件', '<DIV>\r\n<DIV>\r\n<DIV>\r\n<DIV>计费管理系统的链接如下：</DIV>\r\n<DIV><A href=\"http://192.168.1.9:8089/PbxCount/\">http://192.168.1.9:8089/PbxCount/</A></DIV>\r\n<DIV>测试用户均为管理员权限</DIV>\r\n<DIV>用户名：zhangfeng</DIV>\r\n<DIV>密码：zhangfeng</DIV>\r\n<DIV>********************************</DIV>\r\n<DIV>在帖子中的格式如下：</DIV></DIV>\r\n<DIV>bug名称：话费查询查询信息有错误<BR>bug列表：1)话费查询-&gt;基本话费查询，主叫号输入后不能查询到相对应的信息，列出的不是所要查询的信息，缺少时长等信息。<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2)其它问题等等<BR>时间和发送人在论坛中应该能找到相应信息</DIV>\r\n<DIV>********************************</DIV>\r\n<DIV>请大家以后严格安装此格式进行测试。</DIV></DIV></DIV>', '2007-03-15 17:06:27', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001212', 'guxiaofeng', 'zhaoyifei', 'yepuliang,jingyuzhuo,zhaoyifei', '', '', '测试张峰和莽珂做的计费软件', '<DIV>\r\n<DIV>\r\n<DIV>\r\n<DIV>计费管理系统的链接如下：</DIV>\r\n<DIV><A href=\"http://192.168.1.9:8089/PbxCount/\">http://192.168.1.9:8089/PbxCount/</A></DIV>\r\n<DIV>测试用户均为管理员权限</DIV>\r\n<DIV>用户名：zhangfeng</DIV>\r\n<DIV>密码：zhangfeng</DIV>\r\n<DIV>********************************</DIV>\r\n<DIV>在帖子中的格式如下：</DIV></DIV>\r\n<DIV>bug名称：话费查询查询信息有错误<BR>bug列表：1)话费查询-&gt;基本话费查询，主叫号输入后不能查询到相对应的信息，列出的不是所要查询的信息，缺少时长等信息。<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2)其它问题等等<BR>时间和发送人在论坛中应该能找到相应信息</DIV>\r\n<DIV>********************************</DIV>\r\n<DIV>请大家以后严格安装此格式进行测试。</DIV></DIV></DIV>', '2007-03-15 17:06:27', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001221', 'guxiaofeng', '', 'zhaoyifei', '', '', '把计划名称重写。', '<DIV></DIV>', '2007-03-22 14:14:42', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001222', 'guxiaofeng', 'zhaoyifei', 'zhaoyifei', '', '', '把计划名称重写。', '<DIV></DIV>', '2007-03-22 14:14:42', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `leaveword_info` VALUES ('LEAVEWORD_INFO_0000000162', 'yy', 'yy', '2006-09-12 13:59:28', '11', '11');
INSERT INTO `leaveword_info` VALUES ('LEAVEWORD_INFO_0000000163', 'yy', 'yy', '2006-09-12 14:00:28', '11', '11');
INSERT INTO `leaveword_info` VALUES ('LEAVEWORD_INFO_0000000181', 'zhaoyifei', 'zhaoyifei', '2006-09-13 13:22:22', 'gffg', 'fggfgffg');
INSERT INTO `leaveword_info` VALUES ('LEAVEWORD_INFO_0000000201', 'zhaoyifei', 'zhaoyifei', '2006-09-13 14:39:33', 'ff', 'fgggfr');
INSERT INTO `leaveword_info` VALUES ('LEAVEWORD_INFO_0000000202', 'zhaoyifei', 'zhaoyifei', '2006-09-13 14:40:14', 'gfdgfd', 'gfdg');
INSERT INTO `leaveword_info` VALUES ('LEAVEWORD_INFO_0000000221', 'yy', 'yy', '2006-09-13 15:40:26', 'yy', 'yyy');
INSERT INTO `message_help_info` VALUES ('MESSAGE_HELP_INFO_0000000021', 'MESSAGE_INFO_0000000021', null, null, null, 'zhaoyifei', 'yepuliang', null, 'N', null, null);
INSERT INTO `message_help_info` VALUES ('MESSAGE_HELP_INFO_0000000022', 'MESSAGE_INFO_0000000021', null, null, null, 'zhaoyifei', 'jingyuzhuo', null, 'N', null, null);
INSERT INTO `message_help_info` VALUES ('MESSAGE_HELP_INFO_0000000041', 'MESSAGE_INFO_0000000041', null, '2', null, 'zhaoyifei', 'zhaoyifei', '2007-03-23', 'N', null, null);
INSERT INTO `message_info` VALUES ('MESSAGE_INFO_0000000001', '2007-03-22', '', '短消息信息', '<DIV>短消息信息</DIV>', 'Y', null);
INSERT INTO `message_info` VALUES ('MESSAGE_INFO_0000000002', '2007-03-22', '', 'aaa', '<DIV>aaa</DIV>', 'Y', null);
INSERT INTO `message_info` VALUES ('MESSAGE_INFO_0000000003', '2007-03-22', 'zhaoyifei', 'cccc', '<DIV>ccccccc</DIV>', 'Y', null);
INSERT INTO `message_info` VALUES ('MESSAGE_INFO_0000000021', '2007-03-22', 'zhaoyifei', 'eeeee', '<DIV>eeeeeeeeee</DIV>', 'N', null);
INSERT INTO `message_info` VALUES ('MESSAGE_INFO_0000000041', '2007-03-23', 'zhaoyifei', '[回复：] eeeee', '<DIV>zhaoyifeizhaoyifeizhaoyifeizhaoyifeizhaoyifei</DIV>', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000324', '21', '今天看到北大青鸟的发的宣传单 ，把 潭浩强 说成C语言之父 ，郁闷 ', 'zf', 'zf', 'zf', 'zf', '0', '2006-09-18 14:13:41', '0', '0', '0', '1', '<P>我承认潭浩强在C语言教育方面的重大贡献，但也不能把说成C语言之父把？不知在C语言方面做过那些重大的贡献，写过重要的c语法规则还是写过高效C编译器？他都没有把？只不过写过一本C的教学书而已啊？而这本书的规则都是别人建立好的，他只不过转述一下而已？</P>\r\n<P>&nbsp;</P>', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000325', '21', '服务器端Socket编程问题', 'zf', 'zf', 'zf', 'zf', '0', '2006-09-18 14:14:21', '0', '0', '0', '1', '<P>用.net的sockets类写了客户与服务器端程序，在本地和局域网内测试都通过了。<BR>但将服务器端程序放在有固定IP的服务器上，在另一台机器上运行客户端，出现错误提示：<BR>“由于目标机器积极拒绝,无法连接。”<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <BR>服务器端代码基本如下：<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Dim adIP As IPAddress<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; adIP = IPAddress.Parse(\"61.152.xxx.xx\")<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; endPoint = New IPEndPoint(adIP, 2188）<BR>&nbsp;&nbsp;&nbsp;&nbsp; <BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Dim listener As Socket<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; listener = New Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp)<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \'实例化Socket类<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; listener.Blocking = True \'指示Socket处于阻塞模式<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; listener.Bind(endPoint)&nbsp; \'绑定本地的IP地址<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; SetStatus(Status.侦听)&nbsp;&nbsp; \'显示状态<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; listener.Listen(10)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \'侦听</P>\r\n<P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Private Socket&nbsp; As Socket&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \'定义Socket<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Socket = listener.Accept()</P>\r\n<P>服务器未开防火墙，也没有任何杀毒软件。<BR>在客户端Telnet 服务器80端口没问题，但在上面端口5158进行Telnet,提示连接失败.<BR>是否是服务器上设置权限有问题，服务器是Windows 2003 Server 版本。</P>\r\n<P>请高手帮忙，解决立马给分，本人大概有300分，都给。</P>', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000326', '20', '你愿意做哪一种狐狸', 'zf', 'zf', 'zf', 'zf', '0', '2006-11-22 13:58:37', '0', '0', '0', '1', '&nbsp;盛夏酷暑，一群口干舌燥的狐狸来到一个葡萄架下。一串串晶莹剔透的葡萄挂满枝头，狐狸们馋得直流口水，可葡萄架很高。<BR><BR>第一只狐狸跳了几下摘不到，从附近找来一个梯子，爬上去满载而归。<BR><BR>第二只狐狸跳了多次仍吃不到，找遍四周，没有任何工具可以利用，笑了笑说：“这里的葡萄一定特别酸！”于是，心安理得地走了。<BR><BR>第三只狐狸高喊着“下定决心，不怕万难，吃不到葡萄死不瞑目&nbsp;”的口号，一次又一次跳个没完，最后累死在葡萄架下。<BR><BR>第四只葡萄因为吃不到葡萄整天闷闷不乐，抑郁成疾，不治而亡。<BR><BR>第五只狐狸想：“连个葡萄都吃不到，活着还有什么意义呀！”于是找个树藤上吊了。<BR><BR>第六只狐狸吃不到葡萄便破口大骂，被路人一棒子了却性命。<BR><BR>第七只狐狸抱着“我得不到的东西也决不让别人得到”的阴暗心理，一把火把葡萄园烧了，遭到其他狐狸的共同围剿。<BR><BR>第八只狐狸想从第一只狐狸那里偷、骗、抢些葡萄，也受到了严厉惩罚。<BR><BR>第九只&nbsp;狐狸因为吃不到葡萄气极发疯，蓬头垢面，口中念念有词：“吃葡萄不吐葡萄皮……”<BR><BR>另有几只狐狸来到一个更高的葡萄架下，经过友好协商，利用叠罗汉的方法，成果共享，皆大欢喜。<BR>\r\n<P>当时我看到这个文章。不是针对程序员的。不过我发现 这9个狐狸更能代表每一种程序员。甚至是一个团队。<BR>请大家对号入座。</P>\r\n<P>&nbsp;&nbsp;&nbsp; 同时更加欢迎大家 回复的时候扩充&nbsp; 更多的狐狸.</P>', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000381', '0', '1', '1', '1', '1', '1', '0', '2006-12-25 15:28:04', '0', '0', '0', '1', '<P>111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</P>\r\n<P>1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</P>\r\n<P>&nbsp;</P>\r\n<P>1</P>\r\n<P>1</P>\r\n<P>&nbsp;</P>\r\n<P>1</P>\r\n<P>1</P>\r\n<P>11</P>\r\n<P>111111111111111111111111111111111111111111111111111111111111111111111111111111111</P>\r\n<P>1</P>\r\n<P>&nbsp;</P>\r\n<P>&nbsp;</P>\r\n<P>&nbsp;</P>\r\n<P>&nbsp;</P>\r\n<P>&nbsp;</P>\r\n<P>&nbsp;</P>\r\n<P>&nbsp;</P>\r\n<P>&nbsp;</P>\r\n<P>&nbsp;</P>\r\n<P>&nbsp;</P>\r\n<P>&nbsp;</P>\r\n<P>1111111111111111111111111111111111111111111111111111111111111111111111111111111111111</P>\r\n<P>11111111111111</P>', '0', '', '', 'Y', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000401', '0', '22', '21', '22', '22', '22', '0', '2006-12-27 13:09:40', '0', '0', '0', '1', '212', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000421', '21', '关于春节放假的通知', '陈轶瑛', '123', '123', '放假通知', '0', '2007-02-12 13:33:35', '0', '0', '0', '1', '<TABLE cellSpacing=1 cellPadding=0 width=\"100%\" bgColor=#999999 border=0>\r\n<TBODY>\r\n<TR>\r\n<TD vAlign=top bgColor=#ffffff>\r\n<TABLE cellSpacing=5 cellPadding=5 width=\"100%\" border=0>\r\n<TBODY>\r\n<TR>\r\n<TD vAlign=top>\r\n<DIV><FONT color=#800080>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <FONT face=黑体>关于2007年春节时间安排的通知</FONT></FONT></DIV>\r\n<DIV><FONT color=#800080></FONT>&nbsp;</DIV>\r\n<DIV><FONT face=宋体>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<FONT size=4> <FONT color=#808080>2007年春节将至，公司规定从2月16日-2月27日为春节放假时间共放假十二天，家在外地的员工可从2月15日开始放假，市内员工从2月16日开始放假，春节放假期间员工要注意安全。春节前这项时间请各位同事安排好时间完成各自目前的工作。</FONT></FONT></FONT></DIV>\r\n<DIV>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </DIV>\r\n<DIV><FONT face=楷体_GB2312>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <FONT style=\"BACKGROUND-COLOR: #ffffff\" color=#339966>祝大家春节愉快，万事如意</FONT></FONT></DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>', '0', '', '', 'N', null);
INSERT INTO `plan_detail` VALUES ('PLAN_DETAIL_0000000221', 'PLAN_INFO_0000000601', '2006-11-01 00:00:00', '2006-11-03 00:00:00', '3231', null, null, '', '28', '3123');
INSERT INTO `plan_info` VALUES ('PLAN_INFO_0000000601', 'zhangfeng', '28', 'aaa', null, '2006-11-10 13:41:17', 'aaa', '2006-11-01 00:00:00', '2006-11-09 00:00:00', null, null);
INSERT INTO `resource_info` VALUES ('2222', '捷达2000', '辽A2222', '39', '白色，新车，12万购得', '王健', '2006-09-16 15:40:51', null, '省里同意购买，下乡专用，挂机括弧');
INSERT INTO `resource_info` VALUES ('2233', '金杯小货', '辽B2233', '39', '白色，中型货车，11万', '122d', '2006-05-19 00:00:00', '0', '局里批准购的');
INSERT INTO `resource_info` VALUES ('RESOURCE_INFO_0000000001', '大会议室23', null, '40', '刚刚建成，暂时使用,好的', '王健', '2006-09-25 10:30:05', null, '不要过渡使用，有生命危险');
INSERT INTO `resource_info` VALUES ('RESOURCE_INFO_0000000002', '第3会议室', '2', '40', '我的地盘我作主', 'wj', '2006-09-16 09:53:07', null, '不要问为什么');
INSERT INTO `resource_info` VALUES ('RESOURCE_INFO_0000000003', '五号楼第2会议室', '3', '40', '谁与争风，我欲乘风', 'rcvbn,wj', '2006-09-19 14:36:12', null, '不要唠叨');
INSERT INTO `resource_info` VALUES ('RESOURCE_INFO_0000000122', '桑塔纳2000', '辽A-A1848', '39', '红色，新款，型号：2.0', '王健', '2006-05-30 00:00:00', null, '部里统一下发');
INSERT INTO `resource_info` VALUES ('RESOURCE_INFO_0000000341', '11', '辽A-A1841 ', '39', '213131344', '313', '2006-09-25 11:08:22', null, '212uu2121');
INSERT INTO `resource_info` VALUES ('RESOURCE_INFO_0000000342', 'xiaobit', '辽A-A8848', '39', 'xiao', '小白', '2006-09-23 11:03:37', null, 'xiao');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000042', '2222', '2006-05-08 00:00:00', '2006-05-23 00:00:00', null, 'yy', 'wj', '黄临界', '2', '出去完，用车', '39');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000043', '2233', '2006-05-16 00:00:00', '2006-05-16 00:00:00', null, null, 'wj', '王晨', '0', '出去谈党建项目，用车', '39');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000046', '2222', '2006-05-15 00:00:00', '2006-05-16 00:00:00', null, null, 'wj', '叶浦亮', '2', '去抚顺开会', '39');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000104', '2233', '2006-05-08 00:00:00', '2006-05-22 00:00:00', null, 'yy', 'wj', '往往', '0', '速度发送到', '39');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000141', 'RESOURCE_INFO_0000000002', '1999-01-22 00:00:00', null, '10:00 ---- 11:00', null, null, '王健', '1', '无聊的东西', '40');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000162', '2222', '2006-08-15 00:00:00', '2006-08-30 00:00:00', null, '原因', '54', '54', '0', '54', '39');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000181', '2222', '2006-08-22 00:00:00', '2006-08-30 00:00:00', null, null, '1111111111111wwwwwwwww', 'jjjk', '2', 'hgghj', '39');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000201', 'RESOURCE_INFO_0000000122', '2006-08-21 00:00:00', '2006-08-29 00:00:00', null, null, 'fff', 'aa', '0', 'aa', '39');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000261', 'RESOURCE_INFO_0000000001', '2006-07-07 00:00:00', null, '3:00 ---- 4:00', null, null, '212', '1', 'rrr', '40');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000262', 'RESOURCE_INFO_0000000002', '2006-12-12 00:00:00', null, '1:00 ---- 2:00', null, null, '小白', '1', '12121212', '40');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000281', 'RESOURCE_INFO_0000000002', '2006-09-21 00:00:00', null, '10:51 ---- 14:51', null, null, 'zhaoyifei,小狠,小白,赵一非', '1', '讨论OA', '40');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000301', 'RESOURCE_INFO_0000000001', '2006-09-20 00:00:00', null, '12:48 ---- 12:48', null, null, '', '1', '开会', '40');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000321', 'RESOURCE_INFO_0000000001', '2006-09-13 00:00:00', null, '14:40 ---- 14:40', null, null, '31313', '1', '2222', '40');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000322', 'RESOURCE_INFO_0000000002', '2006-09-29 00:00:00', null, '14:40 ---- 15:40', null, null, 'yyp', '1', '开个会', '40');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000461', 'RESOURCE_INFO_0000000342', '2006-09-21 00:00:00', '2006-09-28 00:00:00', null, null, '小白', 'xiaobai', '0', 'xiaobai', '39');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000462', 'RESOURCE_INFO_0000000342', '2006-09-23 00:00:00', '2006-09-24 00:00:00', null, null, '小狠', 'baibai', '2', 'bai', '39');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000481', 'RESOURCE_INFO_0000000122', '2006-09-12 00:00:00', '2006-09-20 00:00:00', null, null, '31313', '212', '1', '212', '39');
INSERT INTO `resource_use` VALUES ('RESOURCE_USE_0000000501', 'RESOURCE_INFO_0000000001', '2006-09-05 00:00:00', null, '09:47 ---- 09:47', null, null, '3333', '1', '开会', '40');
INSERT INTO `synod_note` VALUES ('SYNOD_NOTE_0000000322', '2006-05-01 15:43:00', 'RESOURCE_INFO_0000000002', '11', '11,11,11,11,122d,rcvbn,rr,rtx', '999', '999', null, '2006-05-24 15:44:00', '47', '999', 'zhaoyifei', 'to_db', '-1', null);
INSERT INTO `synod_note` VALUES ('SYNOD_NOTE_0000000341', '2006-05-01 08:52:00', 'RESOURCE_INFO_0000000002', 'wj', '11,11,11,11,1111111111111111,122d,rcvbn,rr,rtx,wj', '999', '你说呢？', null, '2006-05-25 08:53:00', '49', '你说呢？', 'zhaoyifei', 'to_db', '-1', null);
INSERT INTO `synod_note` VALUES ('SYNOD_NOTE_0000000361', '2006-05-01 15:48:00', 'RESOURCE_INFO_0000000001', '王健', '11,11,11,11,1111111111111111,122d,rcvbn,rr', '11111111', '1111111111', '-1', '2006-05-25 15:48:00', '50', '11111111111', '', '-1', '1', '-1');
INSERT INTO `synod_note` VALUES ('SYNOD_NOTE_0000000381', '2006-05-01 09:36:00', 'RESOURCE_INFO_0000000001', 'rr3', '111111111111111111111,1111111111111wwwwwwwww,rr3,wj,王健', '556', '556', '', '2006-05-30 09:36:00', '51', '556', 'zhaoyifei', 'to_db', '1', '');
INSERT INTO `synod_note` VALUES ('SYNOD_NOTE_0000000401', '2006-08-22 08:58:00', 'RESOURCE_INFO_0000000002', '111111111111111111111', '111111111111111111111,1111111111111wwwwwwwww,123,43,777,fff,rr3,wj,王健', 'ret', 'dsf', '', '2006-08-19 08:58:20', '', 'fdssd', '', '-1', '-1', '');
INSERT INTO `synod_note` VALUES ('SYNOD_NOTE_0000000421', '2006-09-12 16:13:00', 'RESOURCE_INFO_0000000002', '小白', '313,3333,yyp', 'tt', 'ttt', '', '2006-09-16 16:13:48', '', 'ttt', '', '-1', '-1', '');
INSERT INTO `synod_note` VALUES ('SYNOD_NOTE_0000000441', '2006-09-19 08:30:00', 'RESOURCE_INFO_0000000001', '小白', '212,212,313,31313,333,3333,tt,yyp,zhaoyifei,小狠,小白', '今天吃饭什么', '今天吃饭什么', '', '2006-09-19 09:40:58', '', '今天吃饭什么', '', '-1', '-1', '');
INSERT INTO `synod_note` VALUES ('SYNOD_NOTE_0000000461', '2006-09-20 09:46:00', 'RESOURCE_INFO_0000000002', '小白', '212,212,313,31313,333,3333,45,aaaaaaaaaaaaa,bbbbbbbbbbbbbbbbbb,fdfd,fdsf,gfdfgdf,qqq,rfg,tt,yyp,zhaoyifei,小狠,小白,王键22,白2,赵一非', 'dfgfdg', 'f', '', '2006-09-19 09:46:25', '', '', '', '-1', '-1', '');
INSERT INTO `synod_note` VALUES ('SYNOD_NOTE_0000000462', '2006-09-19 09:46:00', 'RESOURCE_INFO_0000000001', 'yyp', '212', 'dsf', '', '', '2006-09-19 09:47:08', '', '', '', '-1', '-1', '');
INSERT INTO `synod_note` VALUES ('SYNOD_NOTE_0000000481', '2006-09-25 09:54:00', 'RESOURCE_INFO_0000000002', '212', '212,212,313,31313,333,3333,45,aaaaaaaaaaaaa,bbbbbbbbbbbbbbbbbb,fdfd,fdsf,gfdfgdf,qqq,rfg,tt,yyp,zhaoyifei,小狠,小白,王键22,白2,赵一非', 'cvvcvc', '', '', '2006-09-25 09:55:10', '5', '', 'zhaoyifei', 'to_db', '-1', '');
INSERT INTO `synod_note` VALUES ('SYNOD_NOTE_0000000482', '2006-09-26 10:02:00', 'RESOURCE_INFO_0000000003', '王键22', '212,212,313,31313,333,3333,45,aaaaaaaaaaaaa,bbbbbbbbbbbbbbbbbb,fdfd,fdsf,gfdfgdf,qqq,rfg,tt,yyp,zhaoyifei,小狠,小白,王键22,白2,赵一非', 'sfsdffds', 'dsddsddfs', '', '2006-09-25 10:03:06', '6', '', 'zhaoyifei', 'to_db', '-1', '');
INSERT INTO `synod_note` VALUES ('SYNOD_NOTE_0000000483', '2006-09-26 10:16:00', 'RESOURCE_INFO_0000000002', 'yyp', '212,212,313,31313,333,3333,45,aaaaaaaaaaaaa,bbbbbbbbbbbbbbbbbb,fdfd,fdsf,gfdfgdf,qqq,rfg,tt,yyp,zhaoyifei,小狠,小白,王键22,白2,赵一非', 'n', 'bbv', '', '2006-09-25 10:17:47', '7', '', 'zhaoyifei', 'to_db', '-1', '');
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
INSERT INTO `sys_group` VALUES ('SYS_GROUP_0000000141', '论坛测试组', '1', '', '0');
INSERT INTO `sys_key` VALUES ('1', '20');
INSERT INTO `sys_key` VALUES ('ADDRESSLISTSORT_INFO', '480');
INSERT INTO `sys_key` VALUES ('ADDRESSLIST_INFO', '260');
INSERT INTO `sys_key` VALUES ('AFICHE_INFO', '220');
INSERT INTO `sys_key` VALUES ('AGRO_DRIVER_INFO', '180');
INSERT INTO `sys_key` VALUES ('AGRO_HIRER_INFO', '220');
INSERT INTO `sys_key` VALUES ('ASSETS_OPER', '340');
INSERT INTO `sys_key` VALUES ('BOOK_BORROW_INFO', '280');
INSERT INTO `sys_key` VALUES ('CHECKWORK_ABSENCE', '980');
INSERT INTO `sys_key` VALUES ('CHECKWORK_INFO', '160');
INSERT INTO `sys_key` VALUES ('DEPARTMENT_STATION_INFO', '40');
INSERT INTO `sys_key` VALUES ('DRIVER_CLASS_INFO', '20');
INSERT INTO `sys_key` VALUES ('EMAIL_BOX', '280');
INSERT INTO `sys_key` VALUES ('EMPLOYEE_INFO', '380');
INSERT INTO `sys_key` VALUES ('FILE_INFO', '580');
INSERT INTO `sys_key` VALUES ('FLOW_INSTANCE', '320');
INSERT INTO `sys_key` VALUES ('FORUMTOPIC', '20');
INSERT INTO `sys_key` VALUES ('FORUM_POSTS', '200');
INSERT INTO `sys_key` VALUES ('GOV_ANTITHESES_INFO', '40');
INSERT INTO `sys_key` VALUES ('GOV_DRIVER_INFO', '160');
INSERT INTO `sys_key` VALUES ('GOV_DRIVER_OPER', '40');
INSERT INTO `sys_key` VALUES ('GOV_MOTOR_INFO', '160');
INSERT INTO `sys_key` VALUES ('HANDSET_NOTE_INFO', '140');
INSERT INTO `sys_key` VALUES ('INADJUNCT_INFO', '240');
INSERT INTO `sys_key` VALUES ('INEMAIL_INFO', '1240');
INSERT INTO `sys_key` VALUES ('LEAVEWORD_INFO', '260');
INSERT INTO `sys_key` VALUES ('MESSAGE_HELP_INFO', '60');
INSERT INTO `sys_key` VALUES ('MESSAGE_INFO', '60');
INSERT INTO `sys_key` VALUES ('NEWS_ARTICLE', '440');
INSERT INTO `sys_key` VALUES ('PLAN_DETAIL', '240');
INSERT INTO `sys_key` VALUES ('PLAN_INFO', '620');
INSERT INTO `sys_key` VALUES ('RESOURCE_INFO', '380');
INSERT INTO `sys_key` VALUES ('RESOURCE_USE', '520');
INSERT INTO `sys_key` VALUES ('SYNOD_NOTE', '500');
INSERT INTO `sys_key` VALUES ('SYSRIGHTUSER', '5340');
INSERT INTO `sys_key` VALUES ('SYS_DEPARTMENT', '160');
INSERT INTO `sys_key` VALUES ('SYS_GROUP', '160');
INSERT INTO `sys_key` VALUES ('SYS_LOG', '2700');
INSERT INTO `sys_key` VALUES ('SYS_MODULE', '780');
INSERT INTO `sys_key` VALUES ('SYS_ROLE', '200');
INSERT INTO `sys_key` VALUES ('SYS_STATION_INFO', '20');
INSERT INTO `sys_key` VALUES ('SYS_TREE', '520');
INSERT INTO `sys_key` VALUES ('TEST_KEY', '60');
INSERT INTO `sys_key` VALUES ('TEST_KS_NO_TABLE', '60');
INSERT INTO `sys_key` VALUES ('WORKFLOW_INSTANCE', '240');
INSERT INTO `sys_key` VALUES ('WORK_PLAN_INFO', '1580');
INSERT INTO `sys_key` VALUES ('WORK_PLAN_MISSION', '1380');
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
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000501', 'yepuliang', '2006-12-22 13:06:06', '公共通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000502', 'yepuliang', '2006-12-22 13:06:08', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000503', 'yepuliang', '2006-12-22 13:06:20', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000504', 'yepuliang', '2006-12-22 13:07:38', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000505', 'yepuliang', '2006-12-25 08:49:06', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000506', 'yepuliang', '2006-12-25 09:04:50', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000507', 'yepuliang', '2006-12-25 15:25:11', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000508', 'yepuliang', '2006-12-25 15:26:40', '回收站', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000509', 'yepuliang', '2006-12-25 15:26:44', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000510', 'yepuliang', '2006-12-25 15:28:58', '回收站', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000511', 'yepuliang', '2006-12-25 15:41:45', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000512', 'yepuliang', '2006-12-25 15:44:31', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000513', 'guxiaofeng', '2006-12-25 16:50:29', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000514', 'guxiaofeng', '2006-12-25 16:50:33', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000515', 'guxiaofeng', '2006-12-25 16:50:37', '我的阶段计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000516', 'guxiaofeng', '2006-12-25 16:50:42', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000517', 'zhaoyifei', '2006-12-25 16:52:32', '用户管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000518', 'guxiaofeng', '2006-12-25 16:52:47', '图书入库', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000519', 'guxiaofeng', '2006-12-25 16:55:25', '类型管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000520', 'guxiaofeng', '2006-12-25 16:55:28', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000521', 'yepuliang', '2006-12-26 08:41:13', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000522', 'jingyuzhuo', '2006-12-26 09:32:30', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000523', 'jingyuzhuo', '2006-12-26 09:32:34', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000524', 'jingyuzhuo', '2006-12-26 09:43:32', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000525', 'jingyuzhuo', '2006-12-26 09:43:34', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000526', 'jingyuzhuo', '2006-12-26 09:43:35', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000527', 'jingyuzhuo', '2006-12-26 09:46:07', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000528', 'jingyuzhuo', '2006-12-26 09:47:08', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000529', 'jingyuzhuo', '2006-12-26 09:47:11', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000530', 'jingyuzhuo', '2006-12-26 09:47:18', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000531', 'jingyuzhuo', '2006-12-26 09:54:56', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000532', 'jingyuzhuo', '2006-12-26 09:58:08', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000533', 'jingyuzhuo', '2006-12-26 09:58:11', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000534', 'jingyuzhuo', '2006-12-26 09:58:13', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000535', 'jingyuzhuo', '2006-12-26 09:58:45', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000536', 'jingyuzhuo', '2006-12-26 09:59:06', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000537', 'jingyuzhuo', '2006-12-26 09:59:14', '我的阶段计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000538', 'jingyuzhuo', '2006-12-26 09:59:30', '我的阶段计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000539', 'jingyuzhuo', '2006-12-26 09:59:43', '我的详细计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000540', 'jingyuzhuo', '2006-12-26 10:00:24', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000541', 'jingyuzhuo', '2006-12-26 13:44:57', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000542', 'jingyuzhuo', '2006-12-26 13:51:03', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000543', 'jingyuzhuo', '2006-12-26 13:51:20', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000544', 'jingyuzhuo', '2006-12-26 13:53:06', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000545', 'jingyuzhuo', '2006-12-26 14:05:49', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000546', 'jingyuzhuo', '2006-12-26 14:10:15', '我的阶段计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000547', 'jingyuzhuo', '2006-12-26 14:10:21', '我的阶段计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000548', 'jingyuzhuo', '2006-12-26 14:10:21', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000549', 'jingyuzhuo', '2006-12-26 14:10:30', '我的阶段计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000550', 'jingyuzhuo', '2006-12-26 14:10:43', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000551', 'jingyuzhuo', '2006-12-26 14:11:09', '我的阶段计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000552', 'jingyuzhuo', '2006-12-26 14:12:39', '我的详细计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000553', 'jingyuzhuo', '2006-12-26 14:12:43', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000554', 'jingyuzhuo', '2006-12-26 14:12:48', '我的详细计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000555', 'jingyuzhuo', '2006-12-26 14:12:53', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000556', 'jingyuzhuo', '2006-12-26 14:13:36', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000557', 'guxiaofeng', '2006-12-26 14:15:45', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000558', 'guxiaofeng', '2006-12-26 14:16:01', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000559', 'guxiaofeng', '2006-12-26 14:23:03', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000560', 'guxiaofeng', '2006-12-26 14:23:17', '类型管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000561', 'jingyuzhuo', '2006-12-26 15:08:54', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000562', 'guxiaofeng', '2006-12-26 15:19:18', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000563', 'guxiaofeng', '2006-12-26 15:19:20', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000564', 'guxiaofeng', '2006-12-26 15:19:27', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000565', 'guxiaofeng', '2006-12-26 15:19:38', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000566', 'guxiaofeng', '2006-12-26 15:19:45', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000567', 'guxiaofeng', '2006-12-26 15:19:45', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000568', 'guxiaofeng', '2006-12-26 15:19:46', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000569', 'guxiaofeng', '2006-12-26 15:21:03', '类型管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000570', 'guxiaofeng', '2006-12-26 15:21:50', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000571', 'guxiaofeng', '2006-12-26 15:28:55', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000572', 'guxiaofeng', '2006-12-26 15:29:21', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000573', 'guxiaofeng', '2006-12-26 15:29:38', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000574', 'guxiaofeng', '2006-12-26 15:29:40', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000575', 'guxiaofeng', '2006-12-26 15:30:09', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000576', 'guxiaofeng', '2006-12-26 15:30:47', '我的详细计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000577', 'guxiaofeng', '2006-12-26 15:30:55', '我的阶段计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000578', 'guxiaofeng', '2006-12-26 15:30:56', '我的详细计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000579', 'yepuliang', '2006-12-26 15:31:51', '卓越办公自动化系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000580', 'guxiaofeng', '2006-12-26 15:32:03', '我的详细计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000581', 'guxiaofeng', '2006-12-26 15:32:04', '我的阶段计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000582', 'guxiaofeng', '2006-12-26 15:32:06', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000583', 'yepuliang', '2006-12-26 15:34:32', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000584', 'guxiaofeng', '2006-12-26 15:34:36', '我的详细计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000585', 'guxiaofeng', '2006-12-26 15:34:39', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000586', 'guxiaofeng', '2006-12-26 15:35:49', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000587', 'guxiaofeng', '2006-12-26 15:38:18', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000588', 'guxiaofeng', '2006-12-26 15:38:25', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000589', 'guxiaofeng', '2006-12-26 15:38:27', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000590', 'guxiaofeng', '2006-12-26 15:38:55', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000591', 'guxiaofeng', '2006-12-26 15:39:00', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000592', 'guxiaofeng', '2006-12-26 15:39:02', '我的详细计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000593', 'guxiaofeng', '2006-12-26 15:39:03', '我的阶段计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000594', 'guxiaofeng', '2006-12-26 15:39:03', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000595', 'guxiaofeng', '2006-12-26 15:39:04', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000596', 'guxiaofeng', '2006-12-26 15:39:05', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000597', 'guxiaofeng', '2006-12-26 15:39:06', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000598', 'guxiaofeng', '2006-12-26 15:39:07', '我的阶段计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000599', 'guxiaofeng', '2006-12-26 15:39:07', '我的详细计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000600', 'guxiaofeng', '2006-12-26 15:39:08', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000601', 'guxiaofeng', '2006-12-26 15:40:50', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000602', 'guxiaofeng', '2006-12-26 15:40:53', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000603', 'guxiaofeng', '2006-12-26 15:40:55', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000604', 'guxiaofeng', '2006-12-26 15:41:22', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000605', 'guxiaofeng', '2006-12-26 15:41:50', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000606', 'guxiaofeng', '2006-12-26 15:42:25', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000607', 'guxiaofeng', '2006-12-26 15:42:36', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000608', 'guxiaofeng', '2006-12-26 15:43:23', '我的详细计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000609', 'guxiaofeng', '2006-12-26 15:43:46', '我的详细计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000610', 'guxiaofeng', '2006-12-26 15:43:47', '我的阶段计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000611', 'guxiaofeng', '2006-12-26 15:43:48', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000612', 'guxiaofeng', '2006-12-26 15:43:49', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000613', 'guxiaofeng', '2006-12-26 15:43:50', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000614', 'jingyuzhuo', '2006-12-26 15:47:31', '办公平台', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000615', 'jingyuzhuo', '2006-12-26 15:47:36', '类型管理', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000616', 'jingyuzhuo', '2006-12-26 15:48:57', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000617', 'jingyuzhuo', '2006-12-26 15:49:19', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000618', 'jingyuzhuo', '2006-12-26 15:49:28', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000619', 'jingyuzhuo', '2006-12-26 15:49:31', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000620', 'jingyuzhuo', '2006-12-26 15:49:59', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000621', 'jingyuzhuo', '2006-12-26 15:50:01', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000622', 'jingyuzhuo', '2006-12-26 15:50:02', '我的阶段计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000623', 'jingyuzhuo', '2006-12-26 15:50:07', '我的详细计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000624', 'jingyuzhuo', '2006-12-26 15:50:12', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000625', 'jingyuzhuo', '2006-12-26 15:50:23', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000626', 'jingyuzhuo', '2006-12-26 15:53:12', '类型管理', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000627', 'jingyuzhuo', '2006-12-26 15:57:25', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000628', 'jingyuzhuo', '2006-12-26 15:59:41', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000629', 'jingyuzhuo', '2006-12-26 16:03:10', '类型管理', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000630', 'jingyuzhuo', '2006-12-26 16:04:13', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000631', 'jingyuzhuo', '2006-12-26 16:04:29', '类型管理', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000632', 'jingyuzhuo', '2006-12-26 16:08:37', '类型管理', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000633', 'jingyuzhuo', '2006-12-26 16:21:53', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000634', 'jingyuzhuo', '2006-12-26 16:28:19', '类型管理', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000635', 'jingyuzhuo', '2006-12-26 16:28:40', '类型管理', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000636', 'jingyuzhuo', '2006-12-26 16:58:31', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000637', 'jingyuzhuo', '2006-12-26 17:05:51', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000638', 'jingyuzhuo', '2006-12-26 17:05:58', '类型管理', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000639', 'jingyuzhuo', '2006-12-26 17:07:16', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000640', 'jingyuzhuo', '2006-12-26 17:07:25', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000641', 'jingyuzhuo', '2006-12-26 17:08:37', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000642', 'jingyuzhuo', '2006-12-26 21:34:08', '阶段工作计划', 'lookup', '218.24.65.47', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000643', 'jingyuzhuo', '2006-12-26 21:34:10', '计划审批', 'lookup', '218.24.65.47', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000644', 'jingyuzhuo', '2006-12-26 21:59:09', '计划审批', 'lookup', '218.24.65.47', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000645', 'jingyuzhuo', '2006-12-26 22:35:57', '计划审批', 'lookup', '218.24.65.47', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000646', 'guxiaofeng', '2006-12-27 08:41:12', '图书入库', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000647', 'guxiaofeng', '2006-12-27 08:42:39', '图书借阅查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000648', 'guxiaofeng', '2006-12-27 08:42:57', '图书入库', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000649', 'guxiaofeng', '2006-12-27 08:45:06', '图书入库', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000650', 'guxiaofeng', '2006-12-27 08:45:07', '图书借阅查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000651', 'guxiaofeng', '2006-12-27 08:45:10', '图书入库', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000652', 'guxiaofeng', '2006-12-27 08:46:44', '图书入库', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000653', 'guxiaofeng', '2006-12-27 08:47:24', '图书借阅查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000654', 'guxiaofeng', '2006-12-27 08:47:34', '公司通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000655', 'guxiaofeng', '2006-12-27 08:47:36', '图书入库', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000656', 'guxiaofeng', '2006-12-27 08:47:37', '图书借阅查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000657', 'guxiaofeng', '2006-12-27 08:47:39', '图书入库', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000658', 'guxiaofeng', '2006-12-27 08:51:24', '图书借阅查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000659', 'guxiaofeng', '2006-12-27 08:52:20', '图书入库', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000660', 'guxiaofeng', '2006-12-27 08:54:43', '图书入库', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000661', 'guxiaofeng', '2006-12-27 08:54:44', '图书借阅查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000662', 'guxiaofeng', '2006-12-27 08:54:45', '图书入库', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000663', 'guxiaofeng', '2006-12-27 08:54:49', '图书借阅查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000664', 'guxiaofeng', '2006-12-27 08:54:50', '图书入库', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000665', 'guxiaofeng', '2006-12-27 08:57:08', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000666', 'guxiaofeng', '2006-12-27 08:57:10', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000667', 'guxiaofeng', '2006-12-27 08:57:12', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000668', 'guxiaofeng', '2006-12-27 08:57:15', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000669', 'guxiaofeng', '2006-12-27 08:57:51', '资产信息管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000670', 'guxiaofeng', '2006-12-27 09:00:39', '资产操作查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000671', 'guxiaofeng', '2006-12-27 09:00:43', '资产信息管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000672', 'jingyuzhuo', '2006-12-27 09:08:08', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000673', 'jingyuzhuo', '2006-12-27 09:08:58', '类型管理', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000674', 'jingyuzhuo', '2006-12-27 09:14:53', '类型管理', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000675', 'jingyuzhuo', '2006-12-27 09:24:43', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000676', 'jingyuzhuo', '2006-12-27 09:25:07', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000677', 'jingyuzhuo', '2006-12-27 09:27:12', '类型管理', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000678', 'jingyuzhuo', '2006-12-27 09:28:46', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000679', 'jingyuzhuo', '2006-12-27 09:28:49', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000680', 'jingyuzhuo', '2006-12-27 09:30:54', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000681', 'jingyuzhuo', '2006-12-27 09:42:14', '类型管理', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000682', 'guxiaofeng', '2006-12-27 09:58:34', '日志管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000683', 'jingyuzhuo', '2006-12-27 10:20:25', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000684', 'jingyuzhuo', '2006-12-27 10:20:31', '类型管理', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000685', 'jingyuzhuo', '2006-12-27 10:24:05', '类型管理', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000686', 'jingyuzhuo', '2006-12-27 10:24:16', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000687', 'chenyiying', '2006-12-27 10:24:47', '图书入库', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000688', 'chenyiying', '2006-12-27 10:24:50', '图书借阅查询', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000689', 'chenyiying', '2006-12-27 10:24:54', '图书入库', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000690', 'chenyiying', '2006-12-27 10:36:14', '图书借阅查询', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000691', 'chenyiying', '2006-12-27 10:36:20', '图书入库', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000692', 'chenyiying', '2006-12-27 10:36:23', '图书入库', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000693', 'chenyiying', '2006-12-27 10:36:26', '图书借阅查询', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000694', 'guxiaofeng', '2006-12-27 11:11:51', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000695', 'guxiaofeng', '2006-12-27 11:13:02', '办公平台', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000696', 'yepuliang', '2006-12-27 13:08:01', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000697', 'yepuliang', '2006-12-27 13:08:48', '回收站', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000698', 'yepuliang', '2006-12-27 13:08:56', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000699', 'yepuliang', '2006-12-27 13:09:15', '回收站', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000700', 'yepuliang', '2006-12-27 13:09:31', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000701', 'jingyuzhuo', '2007-01-04 15:28:36', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000702', 'jingyuzhuo', '2007-01-04 15:30:34', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000703', 'jingyuzhuo', '2007-01-04 15:42:23', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000704', 'jingyuzhuo', '2007-01-04 15:45:36', '类型管理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000705', 'jingyuzhuo', '2007-01-04 15:55:20', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000706', 'jingyuzhuo', '2007-01-04 15:58:53', '类型管理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000707', 'jingyuzhuo', '2007-01-04 16:06:19', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000708', 'jingyuzhuo', '2007-01-04 16:21:13', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000709', 'jingyuzhuo', '2007-01-04 16:23:25', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000710', 'jingyuzhuo', '2007-01-04 16:24:19', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000711', 'jingyuzhuo', '2007-01-04 16:40:17', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000712', 'jingyuzhuo', '2007-01-04 17:00:57', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000713', 'jingyuzhuo', '2007-01-04 17:00:58', '公共通讯录', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000714', 'jingyuzhuo', '2007-01-04 17:00:59', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000715', 'jingyuzhuo', '2007-01-04 17:01:24', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000716', 'jingyuzhuo', '2007-01-04 17:04:49', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000717', 'jingyuzhuo', '2007-01-04 17:05:11', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000718', 'jingyuzhuo', '2007-01-04 17:13:10', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000719', 'jingyuzhuo', '2007-01-04 17:13:57', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000720', 'jingyuzhuo', '2007-01-04 17:13:58', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000721', 'guxiaofeng', '2007-01-09 12:13:15', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000722', 'guxiaofeng', '2007-01-09 12:14:02', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000723', 'guxiaofeng', '2007-01-09 12:14:09', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000724', 'guxiaofeng', '2007-01-09 12:15:09', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000725', 'guxiaofeng', '2007-01-09 12:15:10', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000726', 'guxiaofeng', '2007-01-09 12:15:11', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000727', 'guxiaofeng', '2007-01-09 12:15:12', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000728', 'guxiaofeng', '2007-01-09 12:15:13', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000729', 'guxiaofeng', '2007-01-09 12:15:16', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000730', 'guxiaofeng', '2007-01-09 12:15:18', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000731', 'guxiaofeng', '2007-01-09 12:15:19', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000732', 'guxiaofeng', '2007-01-09 12:15:20', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000733', 'guxiaofeng', '2007-01-09 12:15:21', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000734', 'guxiaofeng', '2007-01-09 12:15:22', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000735', 'guxiaofeng', '2007-01-09 12:15:23', '修改密码', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000736', 'guxiaofeng', '2007-01-09 12:15:25', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000737', 'guxiaofeng', '2007-01-09 12:15:29', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000738', 'guxiaofeng', '2007-01-09 12:15:44', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000739', 'guxiaofeng', '2007-01-09 12:15:46', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000740', 'guxiaofeng', '2007-01-09 12:15:47', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000741', 'jingyuzhuo', '2007-01-10 09:10:52', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000742', 'jingyuzhuo', '2007-01-10 09:12:47', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000743', 'jingyuzhuo', '2007-01-10 09:12:51', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000744', 'jingyuzhuo', '2007-01-10 09:12:53', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000745', 'jingyuzhuo', '2007-01-10 09:15:40', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000746', 'jingyuzhuo', '2007-01-10 10:15:15', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000747', 'jingyuzhuo', '2007-01-10 10:20:52', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000748', 'jingyuzhuo', '2007-01-10 10:20:54', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000749', 'jingyuzhuo', '2007-01-10 10:20:55', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000750', 'jingyuzhuo', '2007-01-10 13:26:30', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000751', 'jingyuzhuo', '2007-01-10 13:26:35', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000752', 'jingyuzhuo', '2007-01-10 13:26:37', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000753', 'jingyuzhuo', '2007-01-10 13:26:39', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000754', 'jingyuzhuo', '2007-01-10 13:26:41', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000755', 'jingyuzhuo', '2007-01-10 13:26:44', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000756', 'jingyuzhuo', '2007-01-10 13:26:45', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000757', 'jingyuzhuo', '2007-01-10 13:26:56', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000758', 'jingyuzhuo', '2007-01-10 13:27:03', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000759', 'jingyuzhuo', '2007-01-10 13:27:06', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000760', 'jingyuzhuo', '2007-01-10 13:27:27', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000761', 'jingyuzhuo', '2007-01-15 09:19:23', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000762', 'jingyuzhuo', '2007-01-15 09:22:03', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000763', 'jingyuzhuo', '2007-01-15 10:01:52', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000764', 'guxiaofeng', '2007-01-15 10:05:35', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000765', 'guxiaofeng', '2007-01-15 10:05:37', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000766', 'guxiaofeng', '2007-01-15 10:06:28', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000767', 'guxiaofeng', '2007-01-15 10:07:16', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000768', 'guxiaofeng', '2007-01-15 10:07:21', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000769', 'guxiaofeng', '2007-01-15 10:07:55', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000770', 'guxiaofeng', '2007-01-15 10:11:44', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000771', 'guxiaofeng', '2007-01-15 10:11:46', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000772', 'guxiaofeng', '2007-01-15 10:11:48', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000773', 'guxiaofeng', '2007-01-15 10:12:25', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000774', 'guxiaofeng', '2007-01-15 10:13:59', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000775', 'guxiaofeng', '2007-01-15 10:14:04', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000776', 'guxiaofeng', '2007-01-15 10:14:07', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000777', 'guxiaofeng', '2007-01-15 10:14:08', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000778', 'guxiaofeng', '2007-01-15 10:14:09', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000779', 'guxiaofeng', '2007-01-15 10:14:10', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000780', 'guxiaofeng', '2007-01-15 10:14:11', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000781', 'guxiaofeng', '2007-01-15 10:14:14', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000782', 'guxiaofeng', '2007-01-15 10:14:15', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000783', 'guxiaofeng', '2007-01-15 10:14:18', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000784', 'guxiaofeng', '2007-01-15 10:14:21', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000785', 'guxiaofeng', '2007-01-15 10:15:50', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000786', 'guxiaofeng', '2007-01-15 10:16:12', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000787', 'guxiaofeng', '2007-01-15 10:16:14', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000788', 'guxiaofeng', '2007-01-15 10:18:23', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000789', 'guxiaofeng', '2007-01-15 10:18:24', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000790', 'guxiaofeng', '2007-01-15 10:18:29', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000791', 'guxiaofeng', '2007-01-15 10:18:35', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000792', 'guxiaofeng', '2007-01-15 10:18:36', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000793', 'guxiaofeng', '2007-01-15 10:18:36', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000794', 'guxiaofeng', '2007-01-15 10:18:37', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000795', 'guxiaofeng', '2007-01-15 10:18:37', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000796', 'guxiaofeng', '2007-01-15 10:18:38', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000797', 'guxiaofeng', '2007-01-15 10:18:38', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000798', 'guxiaofeng', '2007-01-15 10:18:39', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000799', 'guxiaofeng', '2007-01-15 10:18:40', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000800', 'guxiaofeng', '2007-01-15 10:18:46', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000801', 'guxiaofeng', '2007-01-15 10:18:50', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000802', 'guxiaofeng', '2007-01-15 10:18:52', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000803', 'guxiaofeng', '2007-01-15 10:18:53', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000804', 'guxiaofeng', '2007-01-15 10:18:58', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000805', 'guxiaofeng', '2007-01-15 10:18:59', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000806', 'guxiaofeng', '2007-01-15 10:19:27', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000807', 'guxiaofeng', '2007-01-15 10:19:36', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000808', 'guxiaofeng', '2007-01-15 10:21:17', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000809', 'guxiaofeng', '2007-01-15 10:21:20', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000810', 'guxiaofeng', '2007-01-15 10:22:53', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000811', 'guxiaofeng', '2007-01-15 10:22:55', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000812', 'guxiaofeng', '2007-01-15 10:24:22', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000813', 'guxiaofeng', '2007-01-15 10:26:45', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000814', 'guxiaofeng', '2007-01-15 10:27:14', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000815', 'guxiaofeng', '2007-01-15 10:27:23', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000816', 'guxiaofeng', '2007-01-15 10:28:31', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000817', 'guxiaofeng', '2007-01-15 10:29:41', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000818', 'guxiaofeng', '2007-01-15 10:29:45', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000819', 'guxiaofeng', '2007-01-15 10:29:49', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000820', 'guxiaofeng', '2007-01-15 10:29:58', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000821', 'guxiaofeng', '2007-01-15 10:30:19', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000822', 'guxiaofeng', '2007-01-15 10:30:32', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000823', 'guxiaofeng', '2007-01-15 10:30:33', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000824', 'guxiaofeng', '2007-01-15 10:30:38', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000825', 'guxiaofeng', '2007-01-15 10:30:52', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000826', 'guxiaofeng', '2007-01-15 10:31:03', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000827', 'guxiaofeng', '2007-01-15 10:31:37', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000828', 'guxiaofeng', '2007-01-15 10:31:50', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000829', 'guxiaofeng', '2007-01-15 10:31:55', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000830', 'guxiaofeng', '2007-01-15 10:32:11', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000831', 'guxiaofeng', '2007-01-15 10:32:21', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000832', 'guxiaofeng', '2007-01-15 10:32:25', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000833', 'guxiaofeng', '2007-01-15 10:32:35', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000834', 'guxiaofeng', '2007-01-15 10:32:45', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000835', 'guxiaofeng', '2007-01-15 10:33:02', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000836', 'guxiaofeng', '2007-01-15 10:33:11', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000837', 'guxiaofeng', '2007-01-15 10:33:29', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000838', 'guxiaofeng', '2007-01-15 10:33:48', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000839', 'guxiaofeng', '2007-01-15 10:33:49', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000840', 'guxiaofeng', '2007-01-15 10:33:50', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000841', 'guxiaofeng', '2007-01-15 10:33:58', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000842', 'guxiaofeng', '2007-01-15 10:34:00', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000843', 'guxiaofeng', '2007-01-15 10:34:00', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000844', 'guxiaofeng', '2007-01-15 10:34:01', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000845', 'guxiaofeng', '2007-01-15 10:34:10', '卓越办公自动化系统', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000846', 'guxiaofeng', '2007-01-15 10:35:22', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000847', 'guxiaofeng', '2007-01-15 10:35:31', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000848', 'guxiaofeng', '2007-01-15 10:35:46', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000849', 'guxiaofeng', '2007-01-15 10:36:04', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000850', 'guxiaofeng', '2007-01-15 10:36:55', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000851', 'guxiaofeng', '2007-01-15 10:37:13', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000852', 'guxiaofeng', '2007-01-15 10:37:14', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000853', 'guxiaofeng', '2007-01-15 10:37:16', '我的阶段计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000854', 'guxiaofeng', '2007-01-15 10:37:16', '我的详细计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000855', 'guxiaofeng', '2007-01-15 10:37:17', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000856', 'guxiaofeng', '2007-01-15 10:37:29', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000857', 'guxiaofeng', '2007-01-15 10:37:53', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000858', 'guxiaofeng', '2007-01-15 10:38:24', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000859', 'guxiaofeng', '2007-01-15 10:40:15', '类型管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000860', 'jingyuzhuo', '2007-01-15 13:42:26', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000861', 'jingyuzhuo', '2007-01-15 13:43:15', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000862', 'jingyuzhuo', '2007-01-15 13:43:18', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000863', 'jingyuzhuo', '2007-01-15 13:44:34', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000864', 'jingyuzhuo', '2007-01-15 13:44:42', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000865', 'jingyuzhuo', '2007-01-15 13:44:47', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000866', 'jingyuzhuo', '2007-01-15 13:45:47', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000867', 'jingyuzhuo', '2007-01-15 13:45:56', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000868', 'jingyuzhuo', '2007-01-15 13:48:30', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000869', 'jingyuzhuo', '2007-01-15 13:55:43', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000870', 'jingyuzhuo', '2007-01-15 13:55:55', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000871', 'jingyuzhuo', '2007-01-15 13:56:06', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000872', 'jingyuzhuo', '2007-01-15 13:57:40', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000873', 'jingyuzhuo', '2007-01-15 13:59:10', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000874', 'jingyuzhuo', '2007-01-15 14:00:27', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000875', 'jingyuzhuo', '2007-01-15 14:00:36', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000876', 'jingyuzhuo', '2007-01-15 14:00:44', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000877', 'jingyuzhuo', '2007-01-15 14:01:00', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000878', 'jingyuzhuo', '2007-01-15 14:01:09', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000879', 'jingyuzhuo', '2007-01-15 14:01:29', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000880', 'jingyuzhuo', '2007-01-15 14:07:06', '类型管理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000881', 'jingyuzhuo', '2007-01-15 14:08:24', '类型管理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000882', 'jingyuzhuo', '2007-01-15 14:08:39', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000883', 'jingyuzhuo', '2007-01-15 14:11:08', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000884', 'jingyuzhuo', '2007-01-15 14:34:57', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000885', 'jingyuzhuo', '2007-01-15 14:34:58', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000886', 'jingyuzhuo', '2007-01-15 14:35:15', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000887', 'jingyuzhuo', '2007-01-15 14:35:52', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000888', 'jingyuzhuo', '2007-01-15 14:35:53', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000889', 'jingyuzhuo', '2007-01-15 14:36:09', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000890', 'jingyuzhuo', '2007-01-15 14:36:18', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000891', 'jingyuzhuo', '2007-01-15 14:36:20', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000892', 'jingyuzhuo', '2007-01-15 14:36:44', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000893', 'jingyuzhuo', '2007-01-15 14:37:22', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000894', 'jingyuzhuo', '2007-01-15 14:37:24', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000895', 'jingyuzhuo', '2007-01-15 14:37:25', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000896', 'jingyuzhuo', '2007-01-15 14:40:03', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000897', 'jingyuzhuo', '2007-01-15 14:40:48', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000898', 'jingyuzhuo', '2007-01-15 14:47:13', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000899', 'jingyuzhuo', '2007-01-15 14:48:37', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000900', 'jingyuzhuo', '2007-01-15 14:48:39', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000901', 'jingyuzhuo', '2007-01-15 14:48:42', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000902', 'jingyuzhuo', '2007-01-15 14:48:43', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000903', 'jingyuzhuo', '2007-01-15 14:48:45', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000904', 'jingyuzhuo', '2007-01-15 14:48:47', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000905', 'jingyuzhuo', '2007-01-15 14:49:06', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000906', 'jingyuzhuo', '2007-01-15 14:49:23', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000907', 'jingyuzhuo', '2007-01-15 14:51:50', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000908', 'jingyuzhuo', '2007-01-15 14:51:57', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000909', 'jingyuzhuo', '2007-01-15 14:52:49', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000910', 'jingyuzhuo', '2007-01-15 14:52:55', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000911', 'jingyuzhuo', '2007-01-15 14:53:04', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000912', 'jingyuzhuo', '2007-01-15 14:54:04', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000913', 'jingyuzhuo', '2007-01-15 14:56:04', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000914', 'jingyuzhuo', '2007-01-15 14:56:17', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000915', 'jingyuzhuo', '2007-01-15 14:56:43', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000916', 'jingyuzhuo', '2007-01-15 14:56:45', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000917', 'jingyuzhuo', '2007-01-15 14:56:46', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000918', 'jingyuzhuo', '2007-01-15 14:59:09', '类型管理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000919', 'jingyuzhuo', '2007-01-15 14:59:57', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000920', 'jingyuzhuo', '2007-01-15 15:09:44', '类型管理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000921', 'jingyuzhuo', '2007-01-15 15:10:18', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000922', 'jingyuzhuo', '2007-01-15 15:10:20', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000923', 'jingyuzhuo', '2007-01-15 15:12:06', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000924', 'jingyuzhuo', '2007-01-15 15:12:16', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000925', 'jingyuzhuo', '2007-01-15 15:16:21', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000926', 'jingyuzhuo', '2007-01-15 15:16:28', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000927', 'jingyuzhuo', '2007-01-15 15:17:53', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000928', 'jingyuzhuo', '2007-01-15 15:19:05', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000929', 'jingyuzhuo', '2007-01-15 15:19:52', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000930', 'jingyuzhuo', '2007-01-15 15:20:47', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000931', 'jingyuzhuo', '2007-01-15 15:21:01', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000932', 'jingyuzhuo', '2007-01-15 15:23:41', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000933', 'jingyuzhuo', '2007-01-15 15:23:58', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000934', 'jingyuzhuo', '2007-01-15 15:24:00', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000935', 'jingyuzhuo', '2007-01-15 15:24:01', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000936', 'jingyuzhuo', '2007-01-15 15:25:36', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000937', 'jingyuzhuo', '2007-01-15 15:29:43', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000938', 'jingyuzhuo', '2007-01-15 15:29:45', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000939', 'jingyuzhuo', '2007-01-15 15:29:46', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000940', 'jingyuzhuo', '2007-01-15 15:29:47', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000941', 'jingyuzhuo', '2007-01-15 15:34:50', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000942', 'jingyuzhuo', '2007-01-15 15:57:47', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000943', 'jingyuzhuo', '2007-01-15 15:57:57', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000944', 'jingyuzhuo', '2007-01-15 15:58:01', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000945', 'jingyuzhuo', '2007-01-15 15:58:25', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000946', 'jingyuzhuo', '2007-01-15 15:59:08', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000947', 'jingyuzhuo', '2007-01-15 15:59:15', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000948', 'jingyuzhuo', '2007-01-15 15:59:31', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000949', 'jingyuzhuo', '2007-01-15 15:59:40', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000950', 'jingyuzhuo', '2007-01-15 16:00:21', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000951', 'jingyuzhuo', '2007-01-15 16:00:23', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000952', 'jingyuzhuo', '2007-01-15 16:00:34', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000953', 'jingyuzhuo', '2007-01-15 16:00:37', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000954', 'jingyuzhuo', '2007-01-15 16:01:08', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000955', 'jingyuzhuo', '2007-01-15 16:02:27', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000956', 'jingyuzhuo', '2007-01-15 16:04:38', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000957', 'jingyuzhuo', '2007-01-15 16:06:17', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000958', 'jingyuzhuo', '2007-01-15 16:06:35', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000959', 'jingyuzhuo', '2007-01-15 16:06:59', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000960', 'jingyuzhuo', '2007-01-15 16:07:55', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000961', 'jingyuzhuo', '2007-01-16 09:02:09', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000962', 'jingyuzhuo', '2007-01-16 09:02:16', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000963', 'jingyuzhuo', '2007-01-16 09:02:22', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000964', 'jingyuzhuo', '2007-01-16 09:02:39', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000965', 'jingyuzhuo', '2007-01-16 09:03:13', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000966', 'jingyuzhuo', '2007-01-16 09:03:15', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000967', 'jingyuzhuo', '2007-01-16 09:03:16', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000968', 'jingyuzhuo', '2007-01-16 09:03:17', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000969', 'jingyuzhuo', '2007-01-16 09:04:05', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000970', 'jingyuzhuo', '2007-01-16 09:04:08', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000971', 'jingyuzhuo', '2007-01-16 09:05:16', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000972', 'jingyuzhuo', '2007-01-16 09:05:19', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000973', 'jingyuzhuo', '2007-01-16 09:05:21', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000974', 'jingyuzhuo', '2007-01-16 09:05:21', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000975', 'jingyuzhuo', '2007-01-16 09:05:41', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000976', 'jingyuzhuo', '2007-01-16 09:05:43', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000977', 'jingyuzhuo', '2007-01-16 09:05:54', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000978', 'jingyuzhuo', '2007-01-16 09:05:56', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000979', 'jingyuzhuo', '2007-01-16 09:05:58', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000980', 'jingyuzhuo', '2007-01-16 09:05:59', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000981', 'jingyuzhuo', '2007-01-16 10:48:12', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000982', 'jingyuzhuo', '2007-01-16 10:52:13', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000983', 'jingyuzhuo', '2007-01-16 10:52:43', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000984', 'jingyuzhuo', '2007-01-16 10:57:37', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000985', 'jingyuzhuo', '2007-01-16 10:57:40', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000986', 'jingyuzhuo', '2007-01-16 10:57:43', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000987', 'jingyuzhuo', '2007-01-16 10:57:44', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000988', 'jingyuzhuo', '2007-01-16 10:57:45', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000989', 'jingyuzhuo', '2007-01-16 10:57:50', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000990', 'jingyuzhuo', '2007-01-16 10:57:51', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000991', 'jingyuzhuo', '2007-01-16 10:58:38', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000992', 'jingyuzhuo', '2007-01-16 11:00:31', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000993', 'jingyuzhuo', '2007-01-16 11:00:37', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000994', 'jingyuzhuo', '2007-01-16 11:01:44', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000995', 'jingyuzhuo', '2007-01-16 11:01:45', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000996', 'jingyuzhuo', '2007-01-16 11:02:25', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000997', 'jingyuzhuo', '2007-01-16 11:02:51', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000998', 'jingyuzhuo', '2007-01-16 11:03:28', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000999', 'jingyuzhuo', '2007-01-16 11:03:47', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001000', 'jingyuzhuo', '2007-01-16 11:06:35', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001001', 'jingyuzhuo', '2007-01-16 11:06:39', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001002', 'jingyuzhuo', '2007-01-16 11:07:05', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001003', 'jingyuzhuo', '2007-01-16 11:07:30', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001004', 'jingyuzhuo', '2007-01-16 11:07:55', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001005', 'jingyuzhuo', '2007-01-16 11:08:31', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001006', 'jingyuzhuo', '2007-01-16 11:09:33', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001007', 'jingyuzhuo', '2007-01-16 11:10:34', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001008', 'jingyuzhuo', '2007-01-16 11:11:18', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001009', 'jingyuzhuo', '2007-01-16 11:11:47', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001010', 'jingyuzhuo', '2007-01-16 11:11:48', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001011', 'jingyuzhuo', '2007-01-16 11:11:49', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001012', 'jingyuzhuo', '2007-01-16 11:11:50', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001013', 'jingyuzhuo', '2007-01-16 11:11:51', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001014', 'jingyuzhuo', '2007-01-16 11:11:54', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001015', 'jingyuzhuo', '2007-01-16 11:13:14', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001016', 'jingyuzhuo', '2007-01-16 11:13:19', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001017', 'jingyuzhuo', '2007-01-16 11:13:41', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001018', 'jingyuzhuo', '2007-01-16 11:13:45', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001019', 'jingyuzhuo', '2007-01-16 11:14:11', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001020', 'jingyuzhuo', '2007-01-16 11:15:56', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001021', 'jingyuzhuo', '2007-01-16 11:19:38', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001022', 'jingyuzhuo', '2007-01-16 11:19:42', '卓越办公自动化系统', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001023', 'jingyuzhuo', '2007-01-16 13:16:37', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001024', 'jingyuzhuo', '2007-01-16 13:16:41', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001025', 'jingyuzhuo', '2007-01-16 13:16:49', '我的详细计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001026', 'jingyuzhuo', '2007-01-16 13:16:51', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001027', 'jingyuzhuo', '2007-01-16 13:17:08', '我的阶段计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001028', 'zhaoyifei', '2007-01-16 13:17:52', '模块管理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001029', 'jingyuzhuo', '2007-01-16 13:20:17', '工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001030', 'jingyuzhuo', '2007-01-16 13:20:20', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001031', 'jingyuzhuo', '2007-01-16 13:20:21', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001032', 'jingyuzhuo', '2007-01-16 13:20:22', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001033', 'guxiaofeng', '2007-01-16 13:21:42', '工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001034', 'guxiaofeng', '2007-01-16 13:21:52', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001035', 'guxiaofeng', '2007-01-16 13:21:55', '工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001036', 'guxiaofeng', '2007-01-16 13:21:57', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001037', 'guxiaofeng', '2007-01-16 13:22:06', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001038', 'guxiaofeng', '2007-01-16 13:22:11', '工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001039', 'guxiaofeng', '2007-01-16 13:22:24', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001040', 'guxiaofeng', '2007-01-16 13:22:29', '工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001041', 'guxiaofeng', '2007-01-29 09:10:46', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001042', 'guxiaofeng', '2007-01-29 09:10:48', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001043', 'zhaoyifei', '2007-01-29 09:27:59', '详细工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001044', 'zhaoyifei', '2007-01-29 09:28:01', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001045', 'zhaoyifei', '2007-01-29 09:28:03', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001046', 'zhaoyifei', '2007-01-29 09:28:05', '详细工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001047', 'zhaoyifei', '2007-01-29 09:28:09', '详细工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001048', 'zhaoyifei', '2007-01-29 09:28:12', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001049', 'zhaoyifei', '2007-01-29 09:28:16', '详细工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001050', 'zhaoyifei', '2007-01-29 09:28:25', '模块管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001051', 'zhaoyifei', '2007-01-29 09:29:17', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001052', 'zhaoyifei', '2007-01-29 09:29:18', '详细工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001053', 'zhaoyifei', '2007-01-29 09:29:20', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001054', 'zhaoyifei', '2007-01-29 09:29:34', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001055', 'zhaoyifei', '2007-01-29 09:29:54', '详细工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001056', 'zhaoyifei', '2007-01-29 09:29:58', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001057', 'zhaoyifei', '2007-01-29 09:30:32', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001058', 'zhaoyifei', '2007-01-29 09:30:33', '详细工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001059', 'zhaoyifei', '2007-01-29 09:30:35', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001060', 'zhaoyifei', '2007-01-29 09:30:36', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001061', 'zhaoyifei', '2007-01-29 09:30:48', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001062', 'guxiaofeng', '2007-01-30 08:43:46', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001063', 'guxiaofeng', '2007-01-30 08:43:49', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001064', 'guxiaofeng', '2007-01-30 08:43:53', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001065', 'guxiaofeng', '2007-01-30 08:43:59', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001066', 'guxiaofeng', '2007-01-30 08:44:13', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001067', 'guxiaofeng', '2007-01-30 08:44:14', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001068', 'guxiaofeng', '2007-01-30 08:45:22', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001069', 'guxiaofeng', '2007-01-30 08:46:17', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001070', 'guxiaofeng', '2007-01-30 08:47:46', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001071', 'guxiaofeng', '2007-01-30 08:47:56', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001072', 'guxiaofeng', '2007-01-30 08:48:13', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001073', 'guxiaofeng', '2007-01-30 08:49:22', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001074', 'guxiaofeng', '2007-01-30 08:50:06', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001075', 'guxiaofeng', '2007-01-30 08:51:23', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001076', 'guxiaofeng', '2007-01-30 08:52:02', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001077', 'guxiaofeng', '2007-01-30 08:53:04', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001078', 'guxiaofeng', '2007-01-30 08:53:40', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001079', 'zhaoyifei', '2007-01-30 08:58:36', '用户管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001080', 'zhaoyifei', '2007-01-30 08:59:04', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001081', 'guxiaofeng', '2007-01-30 09:00:35', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001082', 'guxiaofeng', '2007-01-30 09:00:39', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001083', 'guxiaofeng', '2007-01-30 09:02:16', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001084', 'yepuliang', '2007-01-30 09:04:04', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001085', 'zhangfeng', '2007-01-30 09:04:14', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001086', 'zhangfeng', '2007-01-30 09:04:23', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001087', 'zhangfeng', '2007-01-30 09:04:25', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001088', 'zhangfeng', '2007-01-30 09:04:35', '详细工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001089', 'zhangfeng', '2007-01-30 09:04:42', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001090', 'zhangfeng', '2007-01-30 09:04:43', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001091', 'zhangfeng', '2007-01-30 09:04:44', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001092', 'jingyuzhuo', '2007-01-30 09:08:34', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001093', 'yepuliang', '2007-01-30 09:09:17', '详细工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001094', 'guxiaofeng', '2007-01-30 09:09:24', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001095', 'guxiaofeng', '2007-01-30 09:10:00', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001096', 'guxiaofeng', '2007-01-30 09:10:00', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001097', 'guxiaofeng', '2007-01-30 09:10:04', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001098', 'guxiaofeng', '2007-01-30 09:10:05', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001099', 'guxiaofeng', '2007-01-30 09:10:17', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001100', 'guxiaofeng', '2007-01-30 09:10:20', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001101', 'guxiaofeng', '2007-01-30 09:13:35', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001102', 'guxiaofeng', '2007-01-30 09:13:37', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001103', 'guxiaofeng', '2007-01-30 09:18:17', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001104', 'guxiaofeng', '2007-01-30 09:18:44', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001105', 'guxiaofeng', '2007-01-30 09:18:45', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001106', 'guxiaofeng', '2007-01-30 09:18:45', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001107', 'guxiaofeng', '2007-01-30 09:18:46', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001108', 'guxiaofeng', '2007-01-30 09:18:48', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001109', 'guxiaofeng', '2007-01-30 09:19:53', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001110', 'guxiaofeng', '2007-01-30 09:20:17', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001111', 'guxiaofeng', '2007-01-30 09:20:21', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001112', 'guxiaofeng', '2007-01-30 09:20:30', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001113', 'guxiaofeng', '2007-01-30 09:20:57', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001114', 'guxiaofeng', '2007-01-30 09:21:13', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001115', 'guxiaofeng', '2007-01-30 09:21:19', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001116', 'guxiaofeng', '2007-01-30 09:21:21', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001117', 'guxiaofeng', '2007-01-30 09:21:25', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001118', 'guxiaofeng', '2007-01-30 09:21:35', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001119', 'guxiaofeng', '2007-01-30 09:21:53', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001120', 'guxiaofeng', '2007-01-30 09:22:05', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001121', 'guxiaofeng', '2007-01-30 09:22:33', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001122', 'guxiaofeng', '2007-01-30 09:22:38', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001123', 'guxiaofeng', '2007-01-30 09:22:46', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001124', 'guxiaofeng', '2007-01-30 09:23:24', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001125', 'guxiaofeng', '2007-01-30 09:24:48', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001126', 'guxiaofeng', '2007-01-30 09:24:57', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001127', 'guxiaofeng', '2007-01-30 09:24:58', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001128', 'guxiaofeng', '2007-01-30 09:25:02', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001129', 'guxiaofeng', '2007-01-30 09:25:03', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001130', 'guxiaofeng', '2007-01-30 09:25:08', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001131', 'guxiaofeng', '2007-01-30 09:25:56', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001132', 'guxiaofeng', '2007-01-30 09:26:00', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001133', 'guxiaofeng', '2007-01-30 09:26:36', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001134', 'guxiaofeng', '2007-01-30 09:26:37', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001135', 'guxiaofeng', '2007-01-30 09:26:39', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001136', 'guxiaofeng', '2007-01-30 09:26:48', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001137', 'guxiaofeng', '2007-01-30 09:31:31', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001138', 'guxiaofeng', '2007-01-30 09:31:32', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001139', 'guxiaofeng', '2007-01-30 09:31:33', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001140', 'guxiaofeng', '2007-01-30 09:31:33', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001141', 'guxiaofeng', '2007-01-30 09:31:34', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001142', 'guxiaofeng', '2007-01-30 09:31:35', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001143', 'guxiaofeng', '2007-01-30 09:32:10', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001144', 'jingyuzhuo', '2007-01-30 09:33:47', '计划审批', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001145', 'guxiaofeng', '2007-01-30 09:34:52', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001146', 'guxiaofeng', '2007-01-30 09:34:57', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001147', 'guxiaofeng', '2007-01-30 09:35:13', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001148', 'jingyuzhuo', '2007-01-30 09:35:14', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001149', 'zhaoyifei', '2007-01-30 09:35:26', '公共通讯录', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001150', 'zhaoyifei', '2007-01-30 09:35:33', '个人通讯录', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001151', 'zhaoyifei', '2007-01-30 09:35:37', '类型管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001152', 'zhaoyifei', '2007-01-30 09:35:40', '用户管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001153', 'zhaoyifei', '2007-01-30 09:35:42', '模块管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001154', 'zhaoyifei', '2007-01-30 09:35:43', '组管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001155', 'zhaoyifei', '2007-01-30 09:35:44', '角色管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001156', 'zhaoyifei', '2007-01-30 09:35:46', '部门管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001157', 'zhaoyifei', '2007-01-30 09:35:48', '岗位管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001158', 'zhaoyifei', '2007-01-30 09:35:51', '办公平台', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001159', 'guxiaofeng', '2007-01-30 09:35:55', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001160', 'guxiaofeng', '2007-01-30 09:36:28', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001161', 'guxiaofeng', '2007-01-30 09:37:16', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001162', 'guxiaofeng', '2007-01-30 09:37:18', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001163', 'guxiaofeng', '2007-01-30 09:37:21', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001164', 'guxiaofeng', '2007-01-30 09:37:22', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001165', 'guxiaofeng', '2007-01-30 09:37:22', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001166', 'guxiaofeng', '2007-01-30 09:37:23', '邮箱管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001167', 'guxiaofeng', '2007-01-30 09:37:24', '邮箱管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001168', 'yepuliang', '2007-01-30 09:37:26', '内部邮件', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001169', 'yepuliang', '2007-01-30 09:37:44', '内部邮件', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001170', 'guxiaofeng', '2007-01-30 09:37:44', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001171', 'yepuliang', '2007-01-30 09:37:48', '外部邮件', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001172', 'yepuliang', '2007-01-30 09:37:50', '邮箱管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001173', 'yepuliang', '2007-01-30 09:37:53', '内部邮件', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001174', 'zhaoyifei', '2007-01-30 09:38:04', '内部邮件', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001175', 'guxiaofeng', '2007-01-30 09:38:24', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001176', 'guxiaofeng', '2007-01-30 09:40:01', '邮箱管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001177', 'guxiaofeng', '2007-01-30 09:41:50', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001178', 'zhangfeng', '2007-01-30 09:43:54', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001179', 'guxiaofeng', '2007-01-30 09:44:53', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001180', 'guxiaofeng', '2007-01-30 09:46:47', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001181', 'guxiaofeng', '2007-01-30 09:46:49', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001182', 'guxiaofeng', '2007-01-30 09:46:50', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001183', 'guxiaofeng', '2007-01-30 09:46:50', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001184', 'guxiaofeng', '2007-01-30 09:46:54', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001185', 'guxiaofeng', '2007-01-30 09:46:54', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001186', 'guxiaofeng', '2007-01-30 09:46:55', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001187', 'guxiaofeng', '2007-01-30 09:46:57', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001188', 'guxiaofeng', '2007-01-30 09:47:10', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001189', 'guxiaofeng', '2007-01-30 09:47:32', '办公平台', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001190', 'guxiaofeng', '2007-01-30 09:47:52', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001191', 'guxiaofeng', '2007-01-30 09:48:35', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001192', 'guxiaofeng', '2007-01-30 09:48:37', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001193', 'guxiaofeng', '2007-01-30 09:48:38', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001194', 'guxiaofeng', '2007-01-30 09:49:43', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001195', 'guxiaofeng', '2007-01-30 09:49:46', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001196', 'zhangfeng', '2007-01-30 09:54:56', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001197', 'liuyang', '2007-01-30 09:55:04', '阶段工作计划', 'lookup', '192.168.1.144', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001198', 'guxiaofeng', '2007-01-30 09:55:26', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001199', 'guxiaofeng', '2007-01-30 09:55:26', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001200', 'zhaoyifei', '2007-01-30 09:55:34', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001201', 'jingyuzhuo', '2007-01-30 09:55:44', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001202', 'jingyuzhuo', '2007-01-30 09:55:55', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001203', 'zhaoyifei', '2007-01-30 09:56:37', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001204', 'zhaoyifei', '2007-01-30 09:56:39', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001205', 'jingyuzhuo', '2007-01-30 09:59:56', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001206', 'jingyuzhuo', '2007-01-30 10:00:22', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001207', 'jingyuzhuo', '2007-01-30 10:00:32', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001208', 'jingyuzhuo', '2007-01-30 10:00:34', '工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001209', 'jingyuzhuo', '2007-01-30 10:00:35', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001210', 'guxiaofeng', '2007-01-30 10:02:34', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001211', 'guxiaofeng', '2007-01-30 10:02:42', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001212', 'guxiaofeng', '2007-01-30 10:03:43', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001213', 'jingyuzhuo', '2007-01-30 10:03:50', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001214', 'jingyuzhuo', '2007-01-30 10:03:51', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001215', 'jingyuzhuo', '2007-01-30 10:03:56', '计划审批', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001216', 'jingyuzhuo', '2007-01-30 10:04:40', '计划审批', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001217', 'guxiaofeng', '2007-01-30 10:06:11', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001218', 'guxiaofeng', '2007-01-30 10:06:36', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001219', 'guxiaofeng', '2007-01-30 10:06:56', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001220', 'guxiaofeng', '2007-01-30 10:06:57', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001221', 'guxiaofeng', '2007-01-30 10:07:39', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001222', 'guxiaofeng', '2007-01-30 10:07:40', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001223', 'jingyuzhuo', '2007-01-30 10:13:09', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001224', 'jingyuzhuo', '2007-01-30 10:14:58', '计划审批', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001225', 'jingyuzhuo', '2007-01-30 10:20:01', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001226', 'jingyuzhuo', '2007-01-30 10:20:05', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001227', 'jingyuzhuo', '2007-01-30 10:23:39', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001228', 'jingyuzhuo', '2007-01-30 10:23:40', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001229', 'jingyuzhuo', '2007-01-30 10:23:42', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001230', 'jingyuzhuo', '2007-01-30 10:23:49', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001231', 'jingyuzhuo', '2007-01-30 10:24:09', '计划审批', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001232', 'jingyuzhuo', '2007-01-30 10:24:22', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001233', 'jingyuzhuo', '2007-01-30 10:24:28', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001234', 'guxiaofeng', '2007-01-30 10:25:33', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001235', 'guxiaofeng', '2007-01-30 10:26:16', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001236', 'guxiaofeng', '2007-01-30 10:26:20', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001237', 'guxiaofeng', '2007-01-30 10:26:21', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001238', 'jingyuzhuo', '2007-01-30 10:28:15', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001239', 'guxiaofeng', '2007-01-30 10:31:15', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001240', 'guxiaofeng', '2007-01-30 10:31:57', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001241', 'guxiaofeng', '2007-01-30 10:32:06', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001242', 'guxiaofeng', '2007-01-30 10:32:07', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001243', 'guxiaofeng', '2007-01-30 10:32:15', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001244', 'guxiaofeng', '2007-01-30 10:32:16', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001245', 'guxiaofeng', '2007-01-30 10:32:17', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001246', 'guxiaofeng', '2007-01-30 10:32:18', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001247', 'guxiaofeng', '2007-01-30 10:32:19', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001248', 'guxiaofeng', '2007-01-30 10:34:00', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001249', 'guxiaofeng', '2007-01-30 10:34:01', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001250', 'guxiaofeng', '2007-01-30 10:34:28', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001251', 'guxiaofeng', '2007-01-30 10:34:52', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001252', 'guxiaofeng', '2007-01-30 10:35:03', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001253', 'guxiaofeng', '2007-01-30 10:35:04', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001254', 'guxiaofeng', '2007-01-30 10:35:05', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001255', 'guxiaofeng', '2007-01-30 10:35:34', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001256', 'guxiaofeng', '2007-01-30 10:36:33', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001257', 'guxiaofeng', '2007-01-30 10:36:41', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001258', 'guxiaofeng', '2007-01-30 10:36:51', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001259', 'guxiaofeng', '2007-01-30 10:37:19', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001260', 'guxiaofeng', '2007-01-30 10:37:20', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001261', 'guxiaofeng', '2007-01-30 10:37:21', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001262', 'guxiaofeng', '2007-01-30 10:38:09', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001263', 'guxiaofeng', '2007-01-30 10:38:11', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001264', 'guxiaofeng', '2007-01-30 10:38:38', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001265', 'guxiaofeng', '2007-01-30 10:40:26', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001266', 'guxiaofeng', '2007-01-30 10:40:48', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001267', 'guxiaofeng', '2007-01-30 10:40:49', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001268', 'guxiaofeng', '2007-01-30 10:40:49', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001269', 'guxiaofeng', '2007-01-30 10:40:50', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001270', 'guxiaofeng', '2007-01-30 10:41:01', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001271', 'guxiaofeng', '2007-01-30 10:41:08', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001272', 'guxiaofeng', '2007-01-30 10:43:45', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001273', 'guxiaofeng', '2007-01-30 10:44:30', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001274', 'guxiaofeng', '2007-01-30 10:44:31', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001275', 'guxiaofeng', '2007-01-30 10:44:32', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001276', 'guxiaofeng', '2007-01-30 10:44:33', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001277', 'guxiaofeng', '2007-01-30 10:44:42', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001278', 'guxiaofeng', '2007-01-30 10:44:44', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001279', 'guxiaofeng', '2007-01-30 10:44:45', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001280', 'guxiaofeng', '2007-01-30 10:44:45', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001281', 'guxiaofeng', '2007-01-30 10:44:46', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001282', 'guxiaofeng', '2007-01-30 10:44:47', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001283', 'guxiaofeng', '2007-01-30 10:44:48', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001284', 'guxiaofeng', '2007-01-30 10:44:48', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001285', 'guxiaofeng', '2007-01-30 10:44:49', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001286', 'guxiaofeng', '2007-01-30 10:44:50', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001287', 'guxiaofeng', '2007-01-30 10:44:51', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001288', 'guxiaofeng', '2007-01-30 10:45:06', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001289', 'guxiaofeng', '2007-01-30 10:45:35', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001290', 'guxiaofeng', '2007-01-30 10:45:37', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001291', 'guxiaofeng', '2007-01-30 10:45:49', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001292', 'guxiaofeng', '2007-01-30 10:45:59', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001293', 'guxiaofeng', '2007-01-30 10:46:00', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001294', 'guxiaofeng', '2007-01-30 10:46:01', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001295', 'guxiaofeng', '2007-01-30 10:46:02', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001296', 'guxiaofeng', '2007-01-30 10:46:03', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001297', 'guxiaofeng', '2007-01-30 10:46:27', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001298', 'guxiaofeng', '2007-01-30 10:46:30', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001299', 'guxiaofeng', '2007-01-30 10:46:31', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001300', 'guxiaofeng', '2007-01-30 10:53:50', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001301', 'guxiaofeng', '2007-01-30 10:53:54', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001302', 'guxiaofeng', '2007-01-30 10:53:56', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001303', 'guxiaofeng', '2007-01-30 10:53:57', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001304', 'guxiaofeng', '2007-01-30 10:53:57', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001305', 'guxiaofeng', '2007-01-30 10:54:02', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001306', 'guxiaofeng', '2007-01-30 10:54:04', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001307', 'guxiaofeng', '2007-01-30 11:01:09', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001308', 'guxiaofeng', '2007-01-30 11:01:12', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001309', 'guxiaofeng', '2007-01-30 11:01:34', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001310', 'guxiaofeng', '2007-01-30 11:02:21', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001311', 'guxiaofeng', '2007-01-30 11:02:55', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001312', 'guxiaofeng', '2007-01-30 11:03:11', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001313', 'guxiaofeng', '2007-01-30 11:03:12', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001314', 'guxiaofeng', '2007-01-30 11:03:13', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001315', 'guxiaofeng', '2007-01-30 11:03:13', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001316', 'guxiaofeng', '2007-01-30 11:03:14', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001317', 'guxiaofeng', '2007-01-30 11:03:15', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001318', 'guxiaofeng', '2007-01-30 11:03:22', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001319', 'guxiaofeng', '2007-01-30 11:03:23', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001320', 'guxiaofeng', '2007-01-30 11:04:22', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001321', 'guxiaofeng', '2007-01-30 11:08:21', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001322', 'guxiaofeng', '2007-01-30 11:08:22', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001323', 'guxiaofeng', '2007-01-30 11:08:31', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001324', 'guxiaofeng', '2007-01-30 11:09:03', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001325', 'guxiaofeng', '2007-01-30 11:09:24', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001326', 'guxiaofeng', '2007-01-30 11:09:45', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001327', 'guxiaofeng', '2007-01-30 11:10:00', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001328', 'guxiaofeng', '2007-01-30 11:10:22', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001329', 'guxiaofeng', '2007-01-30 11:10:24', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001330', 'guxiaofeng', '2007-01-30 11:10:27', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001331', 'guxiaofeng', '2007-01-30 11:11:01', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001332', 'guxiaofeng', '2007-01-30 11:11:13', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001333', 'guxiaofeng', '2007-01-30 11:11:15', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001334', 'guxiaofeng', '2007-01-30 11:11:17', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001335', 'guxiaofeng', '2007-01-30 11:11:18', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001336', 'guxiaofeng', '2007-01-30 11:11:19', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001337', 'guxiaofeng', '2007-01-30 11:19:27', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001338', 'guxiaofeng', '2007-01-30 11:19:27', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001339', 'guxiaofeng', '2007-01-30 11:19:28', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001340', 'guxiaofeng', '2007-01-30 11:19:30', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001341', 'guxiaofeng', '2007-01-30 11:19:30', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001342', 'guxiaofeng', '2007-01-30 11:19:31', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001343', 'guxiaofeng', '2007-01-30 11:19:32', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001344', 'guxiaofeng', '2007-01-30 11:19:32', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001345', 'guxiaofeng', '2007-01-30 11:19:34', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001346', 'guxiaofeng', '2007-01-30 11:24:39', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001347', 'guxiaofeng', '2007-01-30 11:24:54', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001348', 'guxiaofeng', '2007-01-30 11:26:02', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001349', 'guxiaofeng', '2007-01-30 11:26:27', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001350', 'guxiaofeng', '2007-01-30 11:26:31', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001351', 'guxiaofeng', '2007-01-30 13:22:39', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001352', 'guxiaofeng', '2007-01-30 13:22:50', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001353', 'guxiaofeng', '2007-01-30 13:22:51', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001354', 'guxiaofeng', '2007-01-30 13:22:51', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001355', 'guxiaofeng', '2007-01-30 13:25:56', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001356', 'guxiaofeng', '2007-01-30 13:26:11', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001357', 'guxiaofeng', '2007-01-30 13:26:12', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001358', 'guxiaofeng', '2007-01-30 13:26:48', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001359', 'guxiaofeng', '2007-01-30 13:28:13', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001360', 'guxiaofeng', '2007-01-30 13:28:48', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001361', 'guxiaofeng', '2007-01-30 13:29:21', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001362', 'guxiaofeng', '2007-01-30 13:29:31', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001363', 'guxiaofeng', '2007-01-30 13:29:32', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001364', 'guxiaofeng', '2007-01-30 13:29:32', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001365', 'guxiaofeng', '2007-01-30 13:30:00', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001366', 'guxiaofeng', '2007-01-30 13:30:02', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001367', 'guxiaofeng', '2007-01-30 13:30:03', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001368', 'guxiaofeng', '2007-01-30 13:35:59', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001369', 'guxiaofeng', '2007-01-30 13:36:03', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001370', 'jingyuzhuo', '2007-01-30 13:36:32', '工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001371', 'jingyuzhuo', '2007-01-30 13:36:34', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001372', 'guxiaofeng', '2007-01-30 13:36:54', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001373', 'guxiaofeng', '2007-01-30 13:36:55', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001374', 'guxiaofeng', '2007-01-30 13:36:57', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001375', 'guxiaofeng', '2007-01-30 13:36:58', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001376', 'guxiaofeng', '2007-01-30 13:36:59', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001377', 'guxiaofeng', '2007-01-30 13:36:59', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001378', 'guxiaofeng', '2007-01-30 13:37:02', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001379', 'guxiaofeng', '2007-01-30 13:37:36', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001380', 'guxiaofeng', '2007-01-30 13:39:14', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001381', 'guxiaofeng', '2007-01-30 13:40:47', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001382', 'guxiaofeng', '2007-01-30 13:41:07', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001383', 'guxiaofeng', '2007-01-30 13:41:29', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001384', 'guxiaofeng', '2007-01-30 13:41:53', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001385', 'guxiaofeng', '2007-01-30 13:42:59', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001386', 'guxiaofeng', '2007-01-30 13:43:15', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001387', 'guxiaofeng', '2007-01-30 13:43:18', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001388', 'guxiaofeng', '2007-01-30 13:43:38', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001389', 'guxiaofeng', '2007-01-30 13:43:39', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001390', 'guxiaofeng', '2007-01-30 13:43:39', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001391', 'guxiaofeng', '2007-01-30 13:45:59', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001392', 'guxiaofeng', '2007-01-30 13:46:08', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001393', 'guxiaofeng', '2007-01-30 13:46:09', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001394', 'guxiaofeng', '2007-01-30 13:46:10', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001395', 'guxiaofeng', '2007-01-30 13:46:11', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001396', 'guxiaofeng', '2007-01-30 13:46:12', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001397', 'guxiaofeng', '2007-01-30 13:46:13', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001398', 'guxiaofeng', '2007-01-30 13:46:14', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001399', 'guxiaofeng', '2007-01-30 13:46:14', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001400', 'guxiaofeng', '2007-01-30 13:46:15', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001401', 'guxiaofeng', '2007-01-30 13:46:21', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001402', 'guxiaofeng', '2007-01-30 13:47:30', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001403', 'guxiaofeng', '2007-01-30 13:47:31', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001404', 'guxiaofeng', '2007-01-30 13:47:32', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001405', 'guxiaofeng', '2007-01-30 13:47:33', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001406', 'guxiaofeng', '2007-01-30 13:47:34', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001407', 'guxiaofeng', '2007-01-30 13:47:35', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001408', 'guxiaofeng', '2007-01-30 13:47:37', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001409', 'guxiaofeng', '2007-01-30 13:47:46', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001410', 'guxiaofeng', '2007-01-30 13:47:51', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001411', 'jingyuzhuo', '2007-01-30 13:51:47', '卓越办公自动化系统', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001412', 'jingyuzhuo', '2007-01-30 13:51:52', '工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001413', 'jingyuzhuo', '2007-01-30 13:51:57', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001414', 'jingyuzhuo', '2007-01-30 13:51:58', '工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001415', 'jingyuzhuo', '2007-01-30 13:52:00', '工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001416', 'jingyuzhuo', '2007-01-30 13:52:00', '工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001417', 'jingyuzhuo', '2007-01-30 13:52:06', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001418', 'jingyuzhuo', '2007-01-30 13:52:06', '工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001419', 'jingyuzhuo', '2007-01-30 13:52:08', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001420', 'jingyuzhuo', '2007-01-30 13:52:09', '计划审批', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001421', 'jingyuzhuo', '2007-01-30 13:52:11', '工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001422', 'jingyuzhuo', '2007-01-30 13:52:12', '工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001423', 'jingyuzhuo', '2007-01-30 13:52:12', '工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001424', 'jingyuzhuo', '2007-01-30 13:52:13', '工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001425', 'guxiaofeng', '2007-01-30 13:54:54', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001426', 'guxiaofeng', '2007-01-30 13:54:56', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001427', 'guxiaofeng', '2007-01-30 13:54:58', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001428', 'guxiaofeng', '2007-01-30 13:55:00', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001429', 'guxiaofeng', '2007-01-30 13:55:06', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001430', 'guxiaofeng', '2007-01-30 13:55:10', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001431', 'guxiaofeng', '2007-01-30 13:55:22', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001432', 'guxiaofeng', '2007-01-30 13:56:15', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001433', 'guxiaofeng', '2007-01-30 13:56:17', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001434', 'guxiaofeng', '2007-01-30 13:56:18', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001435', 'guxiaofeng', '2007-01-30 13:56:19', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001436', 'guxiaofeng', '2007-01-30 13:56:22', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001437', 'jingyuzhuo', '2007-01-30 13:57:45', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001438', 'guxiaofeng', '2007-01-30 13:58:50', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001439', 'guxiaofeng', '2007-01-30 14:00:58', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001440', 'guxiaofeng', '2007-01-30 14:02:40', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001441', 'guxiaofeng', '2007-01-30 14:02:40', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001442', 'guxiaofeng', '2007-01-30 14:03:19', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001443', 'guxiaofeng', '2007-01-30 14:04:43', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001444', 'guxiaofeng', '2007-01-30 14:04:53', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001445', 'guxiaofeng', '2007-01-30 14:04:55', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001446', 'guxiaofeng', '2007-01-30 14:04:56', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001447', 'guxiaofeng', '2007-01-30 14:04:56', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001448', 'guxiaofeng', '2007-01-30 14:04:57', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001449', 'guxiaofeng', '2007-01-30 14:04:59', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001450', 'guxiaofeng', '2007-01-30 14:05:45', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001451', 'guxiaofeng', '2007-01-30 14:08:21', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001452', 'guxiaofeng', '2007-01-30 14:08:22', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001453', 'guxiaofeng', '2007-01-30 14:08:26', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001454', 'guxiaofeng', '2007-01-30 14:08:36', '类型管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001455', 'guxiaofeng', '2007-01-30 14:08:45', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001456', 'guxiaofeng', '2007-01-30 14:09:58', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001457', 'guxiaofeng', '2007-01-30 14:09:59', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001458', 'chenyiying', '2007-01-30 14:13:26', '详细工作计划', 'lookup', '192.168.1.7', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001459', 'chenyiying', '2007-01-30 14:16:19', '阶段工作计划', 'lookup', '192.168.1.7', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001460', 'chenyiying', '2007-01-30 14:16:21', '详细工作计划', 'lookup', '192.168.1.7', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001461', 'chenyiying', '2007-01-30 14:16:23', '详细工作计划', 'lookup', '192.168.1.7', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001462', 'chenyiying', '2007-01-30 14:16:28', '办公平台', 'lookup', '192.168.1.7', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001463', 'guxiaofeng', '2007-01-30 14:18:31', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001464', 'jingyuzhuo', '2007-01-30 14:27:06', '计划审批', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001465', 'jingyuzhuo', '2007-01-30 14:27:11', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001466', 'jingyuzhuo', '2007-01-30 14:27:25', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001467', 'guxiaofeng', '2007-01-30 14:28:13', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001468', 'guxiaofeng', '2007-01-30 14:29:24', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001469', 'guxiaofeng', '2007-01-30 14:29:27', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001470', 'guxiaofeng', '2007-01-30 14:31:10', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001471', 'guxiaofeng', '2007-01-30 14:31:33', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001472', 'guxiaofeng', '2007-01-30 14:33:07', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001473', 'jingyuzhuo', '2007-01-30 14:53:28', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001474', 'jingyuzhuo', '2007-01-30 14:53:49', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001475', 'zhangfeng', '2007-01-30 14:54:54', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001476', 'zhangfeng', '2007-01-30 14:54:56', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001477', 'zhaoyifei', '2007-01-30 14:55:46', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001478', 'zhaoyifei', '2007-01-30 14:56:12', '详细工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001479', 'zhaoyifei', '2007-01-30 14:56:16', '计划审批', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001480', 'zhaoyifei', '2007-01-30 14:56:20', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001481', 'zhangfeng', '2007-01-30 14:57:26', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001482', 'jingyuzhuo', '2007-01-30 15:41:18', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001483', 'jingyuzhuo', '2007-01-30 15:41:20', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001484', 'jingyuzhuo', '2007-01-30 15:41:21', '计划审批', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001485', 'jingyuzhuo', '2007-01-30 15:41:22', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001486', 'jingyuzhuo', '2007-01-30 15:41:23', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001487', 'jingyuzhuo', '2007-01-30 15:41:53', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001488', 'jingyuzhuo', '2007-01-30 15:41:55', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001489', 'jingyuzhuo', '2007-01-30 15:41:56', '公共通讯录', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001490', 'jingyuzhuo', '2007-01-30 15:41:56', '个人通讯录', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001491', 'jingyuzhuo', '2007-01-30 15:41:57', '修改密码', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001492', 'jingyuzhuo', '2007-01-30 15:42:00', '内部邮件', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001493', 'jingyuzhuo', '2007-01-30 15:42:00', '外部邮件', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001494', 'jingyuzhuo', '2007-01-30 15:42:01', '邮箱管理', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001495', 'jingyuzhuo', '2007-01-30 15:42:01', '外部邮件', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001496', 'jingyuzhuo', '2007-01-30 15:42:04', '航班查询', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001497', 'jingyuzhuo', '2007-01-30 15:42:08', '修改密码', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001498', 'jingyuzhuo', '2007-01-30 15:42:08', '个人通讯录', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001499', 'guxiaofeng', '2007-01-30 15:57:13', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001500', 'guxiaofeng', '2007-01-30 16:00:54', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001501', 'jingyuzhuo', '2007-01-30 16:07:32', '工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001502', 'jingyuzhuo', '2007-01-30 16:07:34', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001503', 'jingyuzhuo', '2007-01-30 16:07:36', '详细工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001504', 'jingyuzhuo', '2007-01-30 16:07:37', '计划审批', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001505', 'jingyuzhuo', '2007-01-30 16:08:49', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001506', 'guxiaofeng', '2007-01-30 16:20:20', '阶段工作计划', 'lookup', '192.168.1.8', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001507', 'guxiaofeng', '2007-01-31 13:17:04', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001508', 'guxiaofeng', '2007-01-31 14:27:07', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001509', 'guxiaofeng', '2007-01-31 14:27:09', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001510', 'guxiaofeng', '2007-01-31 14:31:06', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001511', 'guxiaofeng', '2007-01-31 14:31:08', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001512', 'guxiaofeng', '2007-02-01 09:38:25', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001513', 'guxiaofeng', '2007-02-01 09:38:28', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001514', 'guxiaofeng', '2007-02-01 09:39:11', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001515', 'guxiaofeng', '2007-02-01 09:39:51', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001516', 'guxiaofeng', '2007-02-01 09:40:01', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001517', 'guxiaofeng', '2007-02-01 09:51:42', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001518', 'guxiaofeng', '2007-02-01 09:51:48', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001519', 'guxiaofeng', '2007-02-01 09:51:50', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001520', 'guxiaofeng', '2007-02-01 09:52:20', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001521', 'guxiaofeng', '2007-02-01 09:52:21', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001522', 'guxiaofeng', '2007-02-01 09:52:22', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001523', 'guxiaofeng', '2007-02-01 09:52:23', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001524', 'guxiaofeng', '2007-02-01 09:52:24', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001525', 'guxiaofeng', '2007-02-01 09:52:25', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001526', 'guxiaofeng', '2007-02-01 09:52:25', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001527', 'guxiaofeng', '2007-02-01 09:52:50', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001528', 'guxiaofeng', '2007-02-01 09:53:29', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001529', 'guxiaofeng', '2007-02-01 09:53:30', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001530', 'guxiaofeng', '2007-02-01 09:53:31', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001531', 'guxiaofeng', '2007-02-01 09:53:31', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001532', 'guxiaofeng', '2007-02-01 09:53:48', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001533', 'guxiaofeng', '2007-02-01 09:58:05', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001534', 'guxiaofeng', '2007-02-01 10:00:14', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001535', 'guxiaofeng', '2007-02-01 10:18:43', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001536', 'guxiaofeng', '2007-02-01 10:21:29', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001537', 'guxiaofeng', '2007-02-01 10:22:46', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001538', 'guxiaofeng', '2007-02-01 10:22:48', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001539', 'jingyuzhuo', '2007-02-01 10:23:10', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001540', 'jingyuzhuo', '2007-02-01 10:23:33', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001541', 'jingyuzhuo', '2007-02-01 10:23:43', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001542', 'jingyuzhuo', '2007-02-01 10:24:10', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001543', 'guxiaofeng', '2007-02-01 10:24:18', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001544', 'jingyuzhuo', '2007-02-01 10:24:29', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001545', 'guxiaofeng', '2007-02-01 10:24:59', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001546', 'guxiaofeng', '2007-02-01 10:25:06', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001547', 'guxiaofeng', '2007-02-01 10:32:44', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001548', 'guxiaofeng', '2007-02-01 10:33:45', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001549', 'jingyuzhuo', '2007-02-01 10:38:20', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001550', 'jingyuzhuo', '2007-02-01 10:38:21', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001551', 'jingyuzhuo', '2007-02-01 10:38:22', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001552', 'jingyuzhuo', '2007-02-01 10:38:30', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001553', 'zhaoyifei', '2007-02-01 10:40:27', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001554', 'jingyuzhuo', '2007-02-01 10:49:18', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001555', 'jingyuzhuo', '2007-02-01 10:56:45', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001556', 'jingyuzhuo', '2007-02-01 10:56:47', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001557', 'jingyuzhuo', '2007-02-01 10:56:50', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001558', 'guxiaofeng', '2007-02-01 10:58:55', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001559', 'guxiaofeng', '2007-02-01 10:59:03', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001560', 'guxiaofeng', '2007-02-01 10:59:19', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001561', 'guxiaofeng', '2007-02-01 10:59:31', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001562', 'guxiaofeng', '2007-02-01 11:00:29', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001563', 'guxiaofeng', '2007-02-01 11:00:37', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001564', 'guxiaofeng', '2007-02-01 11:00:47', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001565', 'guxiaofeng', '2007-02-01 11:00:56', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001566', 'guxiaofeng', '2007-02-01 11:01:40', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001567', 'jingyuzhuo', '2007-02-01 11:03:06', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001568', 'jingyuzhuo', '2007-02-01 12:32:34', '工作计划', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001569', 'jingyuzhuo', '2007-02-01 12:34:33', '阶段工作计划', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001570', 'jingyuzhuo', '2007-02-01 12:39:15', '详细工作计划', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001571', 'jingyuzhuo', '2007-02-01 12:39:42', '详细工作计划', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001572', 'jingyuzhuo', '2007-02-01 12:39:44', '计划审批', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001573', 'jingyuzhuo', '2007-02-01 12:41:16', '内部邮件', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001574', 'jingyuzhuo', '2007-02-01 12:41:19', '外部邮件', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001575', 'jingyuzhuo', '2007-02-01 12:41:22', '邮箱管理', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001576', 'jingyuzhuo', '2007-02-01 12:41:26', '外部邮件', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001577', 'jingyuzhuo', '2007-02-01 12:41:27', '内部邮件', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001578', 'jingyuzhuo', '2007-02-01 12:41:47', '航班查询', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001579', 'jingyuzhuo', '2007-02-01 12:41:51', '航班查询', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001580', 'jingyuzhuo', '2007-02-01 12:41:57', '国际时间查询', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001581', 'jingyuzhuo', '2007-02-01 12:42:08', '火车查询', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001582', 'jingyuzhuo', '2007-02-01 12:42:12', '邮政编码', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001583', 'jingyuzhuo', '2007-02-01 12:42:28', '天气预报', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001584', 'jingyuzhuo', '2007-02-01 12:42:49', '新闻添加', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001585', 'jingyuzhuo', '2007-02-01 12:42:53', '新闻添加', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001586', 'jingyuzhuo', '2007-02-01 12:42:55', '新闻添加', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001587', 'jingyuzhuo', '2007-02-01 12:42:58', '新闻添加', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001588', 'jingyuzhuo', '2007-02-01 12:43:42', '回收站', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001589', 'jingyuzhuo', '2007-02-01 12:43:48', '公告管理', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001590', 'jingyuzhuo', '2007-02-01 12:44:08', '图书入库', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001591', 'jingyuzhuo', '2007-02-01 12:44:22', '图书借阅查询', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001592', 'jingyuzhuo', '2007-02-01 12:45:29', '公司通讯录', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001593', 'jingyuzhuo', '2007-02-01 12:46:01', '留言板', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001594', 'jingyuzhuo', '2007-02-01 12:46:13', '留言板管理', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001595', 'jingyuzhuo', '2007-02-01 12:46:24', '部门岗位信息', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001596', 'jingyuzhuo', '2007-02-01 12:46:27', '部门岗位信息', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001597', 'jingyuzhuo', '2007-02-01 12:46:29', '办公平台', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001598', 'jingyuzhuo', '2007-02-01 12:50:53', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001599', 'jingyuzhuo', '2007-02-01 12:51:00', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001600', 'jingyuzhuo', '2007-02-01 12:51:01', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001601', 'jingyuzhuo', '2007-02-01 12:51:02', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001602', 'jingyuzhuo', '2007-02-01 14:02:11', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001603', 'jingyuzhuo', '2007-02-01 14:02:22', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001604', 'jingyuzhuo', '2007-02-01 14:02:34', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001605', 'jingyuzhuo', '2007-02-01 15:24:31', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001606', 'jingyuzhuo', '2007-02-01 15:24:37', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001607', 'jingyuzhuo', '2007-02-01 15:24:40', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001608', 'jingyuzhuo', '2007-02-01 15:24:41', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001609', 'jingyuzhuo', '2007-02-01 15:24:44', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001610', 'jingyuzhuo', '2007-02-01 15:24:48', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001611', 'jingyuzhuo', '2007-02-01 15:24:54', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001612', 'jingyuzhuo', '2007-02-01 15:24:56', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001613', 'jingyuzhuo', '2007-02-01 15:24:58', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001614', 'jingyuzhuo', '2007-02-01 15:26:13', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001615', 'guxiaofeng', '2007-02-01 16:34:55', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001616', 'guxiaofeng', '2007-02-01 16:35:10', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001617', 'jingyuzhuo', '2007-02-01 17:26:45', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001618', 'guxiaofeng', '2007-02-01 17:29:54', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001619', 'guxiaofeng', '2007-02-01 17:30:06', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001620', 'guxiaofeng', '2007-02-01 17:30:35', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001621', 'guxiaofeng', '2007-02-01 17:30:59', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001622', 'guxiaofeng', '2007-02-01 17:32:57', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001623', 'guxiaofeng', '2007-02-01 17:33:04', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001624', 'guxiaofeng', '2007-02-01 17:33:05', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001625', 'guxiaofeng', '2007-02-01 17:33:07', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001626', 'guxiaofeng', '2007-02-01 17:33:23', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001627', 'jingyuzhuo', '2007-02-01 18:04:16', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001628', 'jingyuzhuo', '2007-02-01 18:04:18', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001629', 'guxiaofeng', '2007-02-02 08:58:40', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001630', 'guxiaofeng', '2007-02-02 08:58:53', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001631', 'guxiaofeng', '2007-02-02 08:58:56', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001632', 'guxiaofeng', '2007-02-02 08:58:57', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001633', 'guxiaofeng', '2007-02-02 08:58:58', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001634', 'guxiaofeng', '2007-02-02 08:59:00', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001635', 'guxiaofeng', '2007-02-02 08:59:04', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001636', 'guxiaofeng', '2007-02-02 08:59:10', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001637', 'guxiaofeng', '2007-02-02 09:05:00', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001638', 'guxiaofeng', '2007-02-02 09:05:05', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001639', 'guxiaofeng', '2007-02-02 09:05:06', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001640', 'guxiaofeng', '2007-02-02 09:05:15', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001641', 'guxiaofeng', '2007-02-02 09:05:16', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001642', 'guxiaofeng', '2007-02-02 09:05:16', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001643', 'guxiaofeng', '2007-02-02 09:05:17', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001644', 'guxiaofeng', '2007-02-02 09:05:18', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001645', 'guxiaofeng', '2007-02-02 09:05:18', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001646', 'guxiaofeng', '2007-02-02 09:05:19', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001647', 'guxiaofeng', '2007-02-02 09:05:20', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001648', 'guxiaofeng', '2007-02-02 09:05:20', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001649', 'guxiaofeng', '2007-02-02 09:05:21', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001650', 'guxiaofeng', '2007-02-02 09:05:22', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001651', 'guxiaofeng', '2007-02-02 09:05:57', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001652', 'guxiaofeng', '2007-02-02 09:05:59', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001653', 'guxiaofeng', '2007-02-02 09:06:35', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001654', 'guxiaofeng', '2007-02-02 09:08:56', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001655', 'guxiaofeng', '2007-02-02 09:09:00', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001656', 'guxiaofeng', '2007-02-02 09:09:07', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001657', 'guxiaofeng', '2007-02-02 09:09:11', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001658', 'guxiaofeng', '2007-02-02 09:09:12', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001659', 'guxiaofeng', '2007-02-02 09:12:02', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001660', 'guxiaofeng', '2007-02-02 09:14:02', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001661', 'guxiaofeng', '2007-02-02 09:17:56', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001662', 'guxiaofeng', '2007-02-02 09:18:32', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001663', 'guxiaofeng', '2007-02-02 09:18:52', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001664', 'guxiaofeng', '2007-02-02 09:19:04', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001665', 'guxiaofeng', '2007-02-02 09:19:40', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001666', 'guxiaofeng', '2007-02-02 09:19:41', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001667', 'guxiaofeng', '2007-02-02 09:19:43', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001668', 'guxiaofeng', '2007-02-02 09:19:45', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001669', 'guxiaofeng', '2007-02-02 09:20:29', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001670', 'guxiaofeng', '2007-02-02 09:20:44', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001671', 'guxiaofeng', '2007-02-02 09:20:50', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001672', 'guxiaofeng', '2007-02-02 09:21:34', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001673', 'guxiaofeng', '2007-02-02 09:22:10', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001674', 'guxiaofeng', '2007-02-02 09:22:47', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001675', 'guxiaofeng', '2007-02-02 09:23:20', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001676', 'guxiaofeng', '2007-02-02 09:25:54', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001677', 'guxiaofeng', '2007-02-02 09:28:10', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001678', 'guxiaofeng', '2007-02-02 09:28:17', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001679', 'guxiaofeng', '2007-02-02 09:28:41', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001680', 'guxiaofeng', '2007-02-02 09:28:46', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001681', 'guxiaofeng', '2007-02-02 09:29:01', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001682', 'guxiaofeng', '2007-02-02 09:29:12', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001683', 'guxiaofeng', '2007-02-02 09:29:37', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001684', 'guxiaofeng', '2007-02-02 09:30:05', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001685', 'yepuliang', '2007-02-02 09:59:49', '内部邮件', 'lookup', '192.168.1.202', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001686', 'guxiaofeng', '2007-02-02 10:25:53', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001687', 'guxiaofeng', '2007-02-02 10:25:54', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001688', 'guxiaofeng', '2007-02-02 10:25:55', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001689', 'zhangfeng', '2007-02-02 14:17:39', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001690', 'yepuliang', '2007-02-02 14:21:11', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001691', 'jingyuzhuo', '2007-02-02 14:22:11', '阶段工作计划', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001692', 'yepuliang', '2007-02-02 14:22:40', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001693', 'zhangfeng', '2007-02-02 14:24:26', '详细工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001694', 'yepuliang', '2007-02-02 14:29:16', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001695', 'guxiaofeng', '2007-02-02 14:35:54', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001696', 'guxiaofeng', '2007-02-02 14:35:56', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001697', 'zhaoyifei', '2007-02-02 16:11:34', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001698', 'zhaoyifei', '2007-02-02 16:47:00', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001699', 'zhaoyifei', '2007-02-02 16:50:07', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001700', 'zhaoyifei', '2007-02-02 16:50:09', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001701', 'zhaoyifei', '2007-02-02 16:50:46', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001702', 'zhaoyifei', '2007-02-02 16:50:47', '详细工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001703', 'zhaoyifei', '2007-02-02 16:50:58', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001704', 'jingyuzhuo', '2007-02-03 13:45:30', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001705', 'jingyuzhuo', '2007-02-03 13:45:53', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001706', 'jingyuzhuo', '2007-02-03 13:47:19', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001707', 'guxiaofeng', '2007-02-05 08:51:01', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001708', 'guxiaofeng', '2007-02-05 08:51:04', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001709', 'guxiaofeng', '2007-02-05 09:12:32', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001710', 'guxiaofeng', '2007-02-05 09:12:42', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001711', 'zhangfeng', '2007-02-05 09:15:57', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001712', 'zhangfeng', '2007-02-05 09:15:58', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001713', 'zhangfeng', '2007-02-05 09:16:26', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001714', 'zhangfeng', '2007-02-05 09:16:29', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001715', 'zhangfeng', '2007-02-05 09:17:44', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001716', 'guxiaofeng', '2007-02-05 14:32:18', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001717', 'guxiaofeng', '2007-02-05 15:45:42', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001718', 'guxiaofeng', '2007-02-05 15:45:51', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001719', 'guxiaofeng', '2007-02-05 15:45:56', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001720', 'guxiaofeng', '2007-02-05 15:46:05', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001721', 'guxiaofeng', '2007-02-05 15:46:08', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001722', 'guxiaofeng', '2007-02-05 15:46:15', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001723', 'guxiaofeng', '2007-02-05 15:54:04', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001724', 'guxiaofeng', '2007-02-06 08:36:51', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001725', 'guxiaofeng', '2007-02-06 08:36:54', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001726', 'zhangfeng', '2007-02-06 08:56:08', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001727', 'zhangfeng', '2007-02-06 08:56:18', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001728', 'zhangfeng', '2007-02-06 08:56:19', '详细工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001729', 'zhangfeng', '2007-02-06 08:57:14', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001730', 'zhaoyifei', '2007-02-06 09:29:34', '工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001731', 'zhaoyifei', '2007-02-06 09:29:55', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001732', 'zhaoyifei', '2007-02-06 09:29:57', '详细工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001733', 'zhaoyifei', '2007-02-06 09:29:58', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001734', 'guxiaofeng', '2007-02-06 10:50:53', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001735', 'guxiaofeng', '2007-02-06 10:51:30', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001736', 'zhangfeng', '2007-02-06 15:10:27', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001737', 'zhangfeng', '2007-02-06 15:10:32', '详细工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001738', 'zhangfeng', '2007-02-06 15:10:33', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001739', 'zhangfeng', '2007-02-06 15:18:43', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001740', 'zhangfeng', '2007-02-06 15:18:52', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001741', 'zhangfeng', '2007-02-06 15:18:53', '详细工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001742', 'zhangfeng', '2007-02-06 15:18:58', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001743', 'zhangfeng', '2007-02-06 15:19:00', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001744', 'guxiaofeng', '2007-02-06 15:49:42', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001745', 'guxiaofeng', '2007-02-06 15:49:45', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001746', 'guxiaofeng', '2007-02-06 15:49:47', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001747', 'guxiaofeng', '2007-02-06 15:49:48', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001748', 'guxiaofeng', '2007-02-06 15:49:49', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001749', 'guxiaofeng', '2007-02-06 15:49:49', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001750', 'guxiaofeng', '2007-02-06 15:51:05', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001751', 'guxiaofeng', '2007-02-06 16:03:22', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001752', 'guxiaofeng', '2007-02-06 16:26:36', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001753', 'guxiaofeng', '2007-02-06 16:27:22', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001754', 'guxiaofeng', '2007-02-06 16:27:26', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001755', 'guxiaofeng', '2007-02-06 16:28:46', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001756', 'guxiaofeng', '2007-02-06 16:28:47', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001757', 'guxiaofeng', '2007-02-06 16:28:47', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001758', 'guxiaofeng', '2007-02-06 16:28:49', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001759', 'guxiaofeng', '2007-02-06 16:28:50', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001760', 'guxiaofeng', '2007-02-06 16:28:51', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001761', 'guxiaofeng', '2007-02-06 16:29:13', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001762', 'guxiaofeng', '2007-02-06 16:33:51', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001763', 'guxiaofeng', '2007-02-06 16:34:13', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001764', 'guxiaofeng', '2007-02-06 16:57:46', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001765', 'yepuliang', '2007-02-06 17:04:30', '内部邮件', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001766', 'zhaoyifei', '2007-02-06 17:05:54', '卓越办公自动化系统', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001767', 'zhaoyifei', '2007-02-06 17:06:07', '卓越办公自动化系统', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001768', 'zhaoyifei', '2007-02-06 17:06:46', '内部邮件', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001769', 'zhaoyifei', '2007-02-06 17:06:55', '外部邮件', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001770', 'zhaoyifei', '2007-02-06 17:06:57', '邮箱管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001771', 'zhaoyifei', '2007-02-06 17:06:58', '外部邮件', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001772', 'zhaoyifei', '2007-02-06 17:07:00', '内部邮件', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001773', 'guxiaofeng', '2007-02-06 17:07:50', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001774', 'guxiaofeng', '2007-02-06 17:07:53', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001775', 'guxiaofeng', '2007-02-06 17:20:20', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001776', 'guxiaofeng', '2007-02-06 17:22:51', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001777', 'guxiaofeng', '2007-02-06 17:27:12', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001778', 'guxiaofeng', '2007-02-06 17:27:16', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001779', 'guxiaofeng', '2007-02-06 17:27:18', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001780', 'guxiaofeng', '2007-02-06 17:27:19', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001781', 'guxiaofeng', '2007-02-06 17:27:20', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001782', 'guxiaofeng', '2007-02-06 17:28:15', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001783', 'guxiaofeng', '2007-02-06 17:28:19', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001784', 'guxiaofeng', '2007-02-06 17:29:50', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001785', 'guxiaofeng', '2007-02-06 17:30:24', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001786', 'guxiaofeng', '2007-02-06 17:33:11', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001787', 'guxiaofeng', '2007-02-06 17:33:13', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001788', 'guxiaofeng', '2007-02-06 17:33:36', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001789', 'guxiaofeng', '2007-02-06 17:34:01', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001790', 'guxiaofeng', '2007-02-06 17:34:28', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001791', 'guxiaofeng', '2007-02-06 17:37:07', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001792', 'guxiaofeng', '2007-02-06 17:38:30', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001793', 'zhaoyifei', '2007-02-06 17:44:31', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001794', 'guxiaofeng', '2007-02-07 08:37:35', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001795', 'guxiaofeng', '2007-02-07 08:37:40', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001796', 'guxiaofeng', '2007-02-07 08:44:19', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001797', 'guxiaofeng', '2007-02-07 08:44:21', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001798', 'guxiaofeng', '2007-02-08 08:53:17', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001799', 'guxiaofeng', '2007-02-08 08:53:22', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001800', 'guxiaofeng', '2007-02-08 09:11:57', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001801', 'guxiaofeng', '2007-02-08 09:13:04', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001802', 'guxiaofeng', '2007-02-08 09:13:06', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001803', 'guxiaofeng', '2007-02-08 09:13:06', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001804', 'guxiaofeng', '2007-02-08 09:13:07', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001805', 'guxiaofeng', '2007-02-08 09:13:08', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001806', 'guxiaofeng', '2007-02-08 09:13:09', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001807', 'guxiaofeng', '2007-02-08 09:13:11', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001808', 'guxiaofeng', '2007-02-08 09:13:12', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001809', 'guxiaofeng', '2007-02-08 09:13:13', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001810', 'guxiaofeng', '2007-02-08 09:21:20', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001811', 'guxiaofeng', '2007-02-08 09:22:14', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001812', 'guxiaofeng', '2007-02-08 09:40:17', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001813', 'zhaoyifei', '2007-02-08 09:59:26', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001814', 'jingyuzhuo', '2007-02-08 12:48:27', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001815', 'jingyuzhuo', '2007-02-08 13:32:09', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001816', 'jingyuzhuo', '2007-02-08 13:32:10', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001817', 'jingyuzhuo', '2007-02-08 13:33:22', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001818', 'jingyuzhuo', '2007-02-08 13:33:45', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001819', 'zhaoyifei', '2007-02-09 09:24:09', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001820', 'zhaoyifei', '2007-02-09 09:28:21', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001821', 'zhaoyifei', '2007-02-09 09:31:14', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001822', 'zhaoyifei', '2007-02-09 09:34:16', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001823', 'yepuliang', '2007-02-09 09:37:21', '修改密码', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001824', 'guxiaofeng', '2007-02-12 10:02:17', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001825', 'guxiaofeng', '2007-02-12 10:02:19', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001826', 'guxiaofeng', '2007-02-12 10:02:30', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001827', 'zhangfeng', '2007-02-12 10:35:57', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001828', 'zhangfeng', '2007-02-12 10:36:14', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001829', 'zhangfeng', '2007-02-12 10:36:15', '详细工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001830', 'zhangfeng', '2007-02-12 10:36:24', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001831', 'zhangfeng', '2007-02-12 10:36:42', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001832', 'guxiaofeng', '2007-02-12 10:38:32', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001833', 'guxiaofeng', '2007-02-12 10:39:14', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001834', 'guxiaofeng', '2007-02-12 10:56:46', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001835', 'guxiaofeng', '2007-02-12 10:56:48', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001836', 'guxiaofeng', '2007-02-12 11:07:38', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001837', 'guxiaofeng', '2007-02-12 11:07:49', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001838', 'guxiaofeng', '2007-02-12 11:07:54', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001839', 'guxiaofeng', '2007-02-12 11:07:55', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001840', 'guxiaofeng', '2007-02-12 11:07:55', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001841', 'jingyuzhuo', '2007-02-12 12:51:43', '阶段工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001842', 'jingyuzhuo', '2007-02-12 12:51:45', '详细工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001843', 'jingyuzhuo', '2007-02-12 12:51:48', '计划审批', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001844', 'jingyuzhuo', '2007-02-12 12:51:53', '详细工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001845', 'jingyuzhuo', '2007-02-12 12:51:54', '阶段工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001846', 'jingyuzhuo', '2007-02-12 12:51:55', '详细工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001847', 'jingyuzhuo', '2007-02-12 12:51:59', '阶段工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001848', 'jingyuzhuo', '2007-02-12 12:52:03', '详细工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001849', 'jingyuzhuo', '2007-02-12 12:52:06', '阶段工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001850', 'jingyuzhuo', '2007-02-12 12:52:09', '详细工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001851', 'jingyuzhuo', '2007-02-12 12:52:14', '计划审批', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001852', 'jingyuzhuo', '2007-02-12 12:52:18', '工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001853', 'jingyuzhuo', '2007-02-12 12:52:21', '阶段工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001854', 'jingyuzhuo', '2007-02-12 12:52:22', '详细工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001855', 'jingyuzhuo', '2007-02-12 12:52:27', '计划审批', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001856', 'jingyuzhuo', '2007-02-12 12:52:29', '阶段工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001857', 'jingyuzhuo', '2007-02-12 12:52:52', '详细工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001858', 'jingyuzhuo', '2007-02-12 12:52:55', '计划审批', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001859', 'jingyuzhuo', '2007-02-12 12:52:57', '阶段工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001860', 'jingyuzhuo', '2007-02-12 12:52:57', '详细工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001861', 'jingyuzhuo', '2007-02-12 12:53:06', '阶段工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001862', 'jingyuzhuo', '2007-02-12 12:53:52', '阶段工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001863', 'jingyuzhuo', '2007-02-12 12:53:54', '详细工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001864', 'jingyuzhuo', '2007-02-12 12:54:00', '详细工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001865', 'jingyuzhuo', '2007-02-12 12:54:01', '阶段工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001866', 'jingyuzhuo', '2007-02-12 12:54:08', '详细工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001867', 'jingyuzhuo', '2007-02-12 12:54:21', '详细工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001868', 'jingyuzhuo', '2007-02-12 12:54:21', '阶段工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001869', 'jingyuzhuo', '2007-02-12 12:54:29', '计划审批', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001870', 'jingyuzhuo', '2007-02-12 12:59:03', '计划审批', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001871', 'jingyuzhuo', '2007-02-12 12:59:05', '详细工作计划', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001872', 'jingyuzhuo', '2007-02-12 12:59:05', '阶段工作计划', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001873', 'jingyuzhuo', '2007-02-12 12:59:39', '公告管理', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001874', 'jingyuzhuo', '2007-02-12 13:00:06', '公告管理', 'lookup', '192.168.1.122', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001875', 'chenyiying', '2007-02-12 13:03:43', '新闻添加', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001876', 'chenyiying', '2007-02-12 13:03:47', '公告管理', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001877', 'chenyiying', '2007-02-12 13:03:48', '公告管理', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001878', 'chenyiying', '2007-02-12 13:03:49', '公告管理', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001879', 'chenyiying', '2007-02-12 13:03:57', '详细工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001880', 'chenyiying', '2007-02-12 13:04:13', '工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001881', 'chenyiying', '2007-02-12 13:04:17', '工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001882', 'chenyiying', '2007-02-12 13:04:18', '阶段工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001883', 'chenyiying', '2007-02-12 13:11:32', '详细工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001884', 'chenyiying', '2007-02-12 13:12:20', '卓越办公自动化系统', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001885', 'chenyiying', '2007-02-12 13:12:27', '详细工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001886', 'chenyiying', '2007-02-12 13:12:32', '卓越办公自动化系统', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001887', 'chenyiying', '2007-02-12 13:12:36', '详细工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001888', 'chenyiying', '2007-02-12 13:12:42', '卓越办公自动化系统', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001889', 'chenyiying', '2007-02-12 13:12:45', '详细工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001890', 'chenyiying', '2007-02-12 13:12:48', '工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001891', 'chenyiying', '2007-02-12 13:12:49', '阶段工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001892', 'chenyiying', '2007-02-12 13:12:50', '详细工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001893', 'chenyiying', '2007-02-12 13:12:56', '卓越办公自动化系统', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001894', 'chenyiying', '2007-02-12 13:13:09', '公告管理', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001895', 'guxiaofeng', '2007-02-12 13:16:55', '工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001896', 'guxiaofeng', '2007-02-12 13:16:57', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001897', 'chenyiying', '2007-02-12 13:17:07', '卓越办公自动化系统', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001898', 'guxiaofeng', '2007-02-12 13:17:24', '计划审批', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001899', 'guxiaofeng', '2007-02-12 13:17:25', '详细工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001900', 'guxiaofeng', '2007-02-12 13:17:26', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001901', 'guxiaofeng', '2007-02-12 13:17:43', '卓越办公自动化系统', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001902', 'chenyiying', '2007-02-12 13:17:47', '公告管理', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001903', 'chenyiying', '2007-02-12 13:18:11', '详细工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001904', 'chenyiying', '2007-02-12 13:18:15', '卓越办公自动化系统', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001905', 'chenyiying', '2007-02-12 13:18:27', '公告管理', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001906', 'chenyiying', '2007-02-12 13:19:41', '办公平台', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001907', 'chenyiying', '2007-02-12 13:19:45', '公告管理', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001908', 'chenyiying', '2007-02-12 13:19:45', '公告管理', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001909', 'yepuliang', '2007-02-12 13:24:32', '公共通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001910', 'yepuliang', '2007-02-12 13:24:36', '详细工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001911', 'yepuliang', '2007-02-12 13:24:37', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001912', 'yepuliang', '2007-02-12 13:24:38', '详细工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001913', 'yepuliang', '2007-02-12 13:24:47', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001914', 'yepuliang', '2007-02-12 13:25:42', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001915', 'chenyiying', '2007-02-12 13:28:52', '卓越办公自动化系统', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001916', 'chenyiying', '2007-02-12 13:29:47', '新闻添加', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001917', 'chenyiying', '2007-02-12 13:29:56', '公告管理', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001918', 'chenyiying', '2007-02-12 13:30:02', '新闻添加', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001919', 'chenyiying', '2007-02-12 13:30:53', '卓越办公自动化系统', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001920', 'chenyiying', '2007-02-12 13:33:40', '卓越办公自动化系统', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001921', 'chenyiying', '2007-02-12 13:33:57', '新闻添加', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001922', 'chenyiying', '2007-02-12 13:34:02', '卓越办公自动化系统', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001923', 'yepuliang', '2007-02-27 09:06:53', '工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001924', 'yepuliang', '2007-02-27 09:06:59', '详细工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001925', 'yepuliang', '2007-02-27 09:07:01', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001926', 'zhangfeng', '2007-02-27 09:11:14', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001927', 'zhangfeng', '2007-02-27 09:14:24', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001928', 'guxiaofeng', '2007-02-28 08:32:07', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001929', 'guxiaofeng', '2007-02-28 08:32:12', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001930', 'guxiaofeng', '2007-02-28 08:32:13', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001931', 'guxiaofeng', '2007-02-28 08:32:15', '邮箱管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001932', 'guxiaofeng', '2007-02-28 08:32:19', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001933', 'guxiaofeng', '2007-02-28 08:32:21', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001934', 'guxiaofeng', '2007-02-28 08:32:23', '邮箱管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001935', 'guxiaofeng', '2007-02-28 08:32:24', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001936', 'guxiaofeng', '2007-02-28 08:32:28', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001937', 'guxiaofeng', '2007-02-28 08:32:29', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001938', 'guxiaofeng', '2007-02-28 08:32:39', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001939', 'guxiaofeng', '2007-02-28 08:32:49', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001940', 'guxiaofeng', '2007-02-28 08:32:50', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001941', 'guxiaofeng', '2007-02-28 08:33:00', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001942', 'guxiaofeng', '2007-02-28 08:33:02', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001943', 'guxiaofeng', '2007-02-28 08:33:03', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001944', 'guxiaofeng', '2007-02-28 08:39:34', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001945', 'guxiaofeng', '2007-02-28 08:41:04', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001946', 'guxiaofeng', '2007-02-28 08:44:50', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001947', 'guxiaofeng', '2007-02-28 08:44:51', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001948', 'guxiaofeng', '2007-02-28 08:44:54', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001949', 'guxiaofeng', '2007-02-28 08:44:55', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001950', 'guxiaofeng', '2007-02-28 08:44:57', '邮箱管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001951', 'guxiaofeng', '2007-02-28 08:45:22', '邮箱管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001952', 'guxiaofeng', '2007-02-28 08:45:45', '邮箱管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001953', 'guxiaofeng', '2007-02-28 08:45:46', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001954', 'guxiaofeng', '2007-02-28 08:45:47', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001955', 'guxiaofeng', '2007-02-28 08:45:48', '邮箱管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001956', 'guxiaofeng', '2007-02-28 08:45:49', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001957', 'guxiaofeng', '2007-02-28 08:45:49', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001958', 'zhangfeng', '2007-02-28 08:58:45', '个人通讯录', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001959', 'zhangfeng', '2007-02-28 08:58:50', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001960', 'zhangfeng', '2007-02-28 08:58:59', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001961', 'guxiaofeng', '2007-02-28 09:13:05', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001962', 'guxiaofeng', '2007-02-28 09:13:22', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001963', 'guxiaofeng', '2007-02-28 09:13:31', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001964', 'guxiaofeng', '2007-02-28 09:58:05', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001965', 'guxiaofeng', '2007-02-28 09:58:07', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001966', 'guxiaofeng', '2007-02-28 09:58:07', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001967', 'guxiaofeng', '2007-02-28 09:58:08', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001968', 'guxiaofeng', '2007-02-28 09:58:11', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001969', 'guxiaofeng', '2007-02-28 09:58:50', '航班查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001970', 'guxiaofeng', '2007-02-28 10:03:12', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001971', 'guxiaofeng', '2007-02-28 10:09:39', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001972', 'guxiaofeng', '2007-02-28 10:09:40', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001973', 'guxiaofeng', '2007-02-28 10:09:45', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001974', 'guxiaofeng', '2007-02-28 10:09:47', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001975', 'guxiaofeng', '2007-02-28 10:10:51', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001976', 'guxiaofeng', '2007-02-28 10:12:14', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001977', 'guxiaofeng', '2007-03-02 09:38:55', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001978', 'guxiaofeng', '2007-03-02 09:39:54', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001979', 'guxiaofeng', '2007-03-02 09:40:21', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001980', 'guxiaofeng', '2007-03-02 09:41:14', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001981', 'guxiaofeng', '2007-03-02 09:41:16', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001982', 'guxiaofeng', '2007-03-02 09:41:26', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001983', 'guxiaofeng', '2007-03-02 09:41:45', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001984', 'guxiaofeng', '2007-03-02 09:41:46', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001985', 'guxiaofeng', '2007-03-02 09:41:47', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001986', 'guxiaofeng', '2007-03-02 09:41:49', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001987', 'guxiaofeng', '2007-03-02 09:41:49', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001988', 'guxiaofeng', '2007-03-02 09:41:50', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001989', 'guxiaofeng', '2007-03-02 09:42:17', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001990', 'guxiaofeng', '2007-03-02 09:42:20', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001991', 'guxiaofeng', '2007-03-02 09:43:28', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001992', 'guxiaofeng', '2007-03-02 09:43:36', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001993', 'guxiaofeng', '2007-03-02 10:20:27', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001994', 'guxiaofeng', '2007-03-02 10:21:02', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001995', 'guxiaofeng', '2007-03-02 10:46:53', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001996', 'guxiaofeng', '2007-03-02 10:50:40', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001997', 'guxiaofeng', '2007-03-02 10:51:42', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001998', 'guxiaofeng', '2007-03-02 10:52:01', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001999', 'guxiaofeng', '2007-03-02 10:52:02', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002000', 'guxiaofeng', '2007-03-02 10:52:05', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002001', 'guxiaofeng', '2007-03-02 10:52:13', '航班查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002002', 'guxiaofeng', '2007-03-02 10:57:09', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002003', 'guxiaofeng', '2007-03-02 10:58:18', '邮箱管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002004', 'guxiaofeng', '2007-03-02 11:02:32', '办公平台', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002005', 'guxiaofeng', '2007-03-02 11:18:53', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002006', 'guxiaofeng', '2007-03-02 11:19:00', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002007', 'guxiaofeng', '2007-03-02 11:19:02', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002008', 'guxiaofeng', '2007-03-02 11:19:05', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002009', 'guxiaofeng', '2007-03-02 11:20:03', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002010', 'guxiaofeng', '2007-03-02 11:20:04', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002011', 'guxiaofeng', '2007-03-02 11:20:05', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002012', 'guxiaofeng', '2007-03-02 11:20:08', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002013', 'guxiaofeng', '2007-03-02 11:20:09', '外部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002014', 'guxiaofeng', '2007-03-02 11:20:10', '邮箱管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002015', 'guxiaofeng', '2007-03-02 11:20:13', '航班查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002016', 'guxiaofeng', '2007-03-02 11:20:14', '国际时间查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002017', 'guxiaofeng', '2007-03-02 11:20:14', '火车查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002018', 'guxiaofeng', '2007-03-02 11:20:15', '航班查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002019', 'guxiaofeng', '2007-03-02 11:20:18', '火车查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002020', 'guxiaofeng', '2007-03-02 11:20:20', '邮政编码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002021', 'guxiaofeng', '2007-03-02 11:20:23', '天气预报', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002022', 'guxiaofeng', '2007-03-02 11:20:31', '新闻添加', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002023', 'guxiaofeng', '2007-03-02 11:20:38', '新闻添加', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002024', 'guxiaofeng', '2007-03-02 11:20:51', '员工信息', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002025', 'guxiaofeng', '2007-03-02 11:20:57', '设置', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002026', 'guxiaofeng', '2007-03-02 11:20:58', '使用查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002027', 'guxiaofeng', '2007-03-02 11:20:59', '申请', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002028', 'guxiaofeng', '2007-03-02 11:21:00', '会议室查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002029', 'guxiaofeng', '2007-03-02 11:21:17', '缺勤查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002030', 'guxiaofeng', '2007-03-02 11:21:31', '会议申请管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002031', 'guxiaofeng', '2007-03-02 11:21:36', '资产操作查询', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002032', 'guxiaofeng', '2007-03-02 11:21:38', '资产信息管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002033', 'guxiaofeng', '2007-03-02 11:21:40', '办公平台', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002034', 'guxiaofeng', '2007-03-02 11:21:46', '类型管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002035', 'guxiaofeng', '2007-03-02 11:21:55', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002036', 'guxiaofeng', '2007-03-02 15:34:12', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002037', 'guxiaofeng', '2007-03-02 15:34:16', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002038', 'guxiaofeng', '2007-03-02 15:34:17', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002039', 'guxiaofeng', '2007-03-02 16:13:05', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002040', 'guxiaofeng', '2007-03-02 16:13:06', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002041', 'zhangfeng', '2007-03-02 16:24:48', '修改密码', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002042', 'zhangfeng', '2007-03-02 16:24:49', '个人通讯录', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002043', 'zhangfeng', '2007-03-02 16:24:53', '公共通讯录', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002044', 'zhangfeng', '2007-03-02 16:24:58', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002045', 'zhangfeng', '2007-03-02 16:25:07', '详细工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002046', 'zhangfeng', '2007-03-02 16:25:22', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002047', 'jingyuzhuo', '2007-03-02 16:36:06', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002048', 'jingyuzhuo', '2007-03-02 16:36:18', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002049', 'jingyuzhuo', '2007-03-02 16:37:12', '卓越办公自动化系统', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002050', 'jingyuzhuo', '2007-03-02 16:37:16', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002051', 'jingyuzhuo', '2007-03-02 16:45:33', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002052', 'jingyuzhuo', '2007-03-02 16:45:48', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002053', 'zhangfeng', '2007-03-02 16:50:13', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002054', 'zhangfeng', '2007-03-02 16:50:24', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002055', 'jingyuzhuo', '2007-03-02 16:53:27', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002056', 'guxiaofeng', '2007-03-06 10:05:36', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002057', 'guxiaofeng', '2007-03-06 10:06:27', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002058', 'guxiaofeng', '2007-03-06 10:06:32', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002059', 'guxiaofeng', '2007-03-06 10:06:58', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002060', 'guxiaofeng', '2007-03-06 10:48:18', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002061', 'zhaoyifei', '2007-03-09 09:34:23', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002062', 'zhaoyifei', '2007-03-09 09:55:14', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002063', 'zhaoyifei', '2007-03-09 10:02:37', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002064', 'zhaoyifei', '2007-03-09 10:02:40', '卓越办公自动化系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002065', 'zhaoyifei', '2007-03-09 10:02:44', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002066', 'zhaoyifei', '2007-03-09 10:05:51', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002067', 'zhaoyifei', '2007-03-09 10:35:36', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002068', 'zhaoyifei', '2007-03-09 10:35:41', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002069', 'zhaoyifei', '2007-03-09 10:37:02', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002070', 'zhaoyifei', '2007-03-09 10:39:25', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002071', 'zhaoyifei', '2007-03-09 10:40:06', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002072', 'zhaoyifei', '2007-03-09 10:46:29', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002073', 'zhaoyifei', '2007-03-09 10:47:33', '办公平台', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002074', 'zhaoyifei', '2007-03-09 10:47:35', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002075', 'zhaoyifei', '2007-03-09 10:48:39', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002076', 'zhaoyifei', '2007-03-09 11:03:08', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002077', 'zhaoyifei', '2007-03-09 15:12:53', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002078', 'zhaoyifei', '2007-03-09 15:14:24', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002079', 'zhaoyifei', '2007-03-09 15:30:26', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002080', 'zhaoyifei', '2007-03-09 15:33:09', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002081', 'zhaoyifei', '2007-03-10 13:39:01', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002082', 'zhaoyifei', '2007-03-10 13:39:35', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002083', 'zhaoyifei', '2007-03-10 13:40:29', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002084', 'forum', '2007-03-10 13:41:08', '模块添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002085', 'forum', '2007-03-10 13:41:09', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002086', 'forum', '2007-03-10 13:41:11', '替换/限制处理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002087', 'forum', '2007-03-10 13:41:14', '论坛日志管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002088', 'forum', '2007-03-10 13:41:17', '模块添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002089', 'forum', '2007-03-10 13:41:18', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002090', 'forum', '2007-03-10 13:41:20', '卓越办公自动化系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002091', 'forum', '2007-03-10 13:41:22', '卓越办公自动化系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002092', 'forum', '2007-03-10 13:41:23', '卓越办公自动化系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002093', 'forum', '2007-03-10 13:41:23', '卓越办公自动化系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002094', 'forum', '2007-03-10 13:41:24', '卓越办公自动化系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002095', 'forum', '2007-03-10 13:41:26', '卓越办公自动化系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002096', 'forum', '2007-03-10 13:41:27', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002097', 'forum', '2007-03-10 13:41:49', '论坛日志管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002098', 'forum', '2007-03-10 13:45:02', '论坛日志管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002099', 'forum', '2007-03-10 13:45:32', '论坛日志管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002100', 'forum', '2007-03-10 13:45:53', '论坛日志管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002101', 'yepuliang', '2007-03-10 14:39:44', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002102', 'zhaoyifei', '2007-03-10 14:43:25', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002103', 'zhaoyifei', '2007-03-10 14:43:34', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002104', 'zhaoyifei', '2007-03-10 14:46:22', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002105', 'zhaoyifei', '2007-03-10 14:46:22', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002106', 'forum', '2007-03-10 14:46:39', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002107', 'forum', '2007-03-10 14:46:48', '模块添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002108', 'forum', '2007-03-10 14:46:50', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002109', 'forum', '2007-03-10 14:46:51', '替换/限制处理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002110', 'forum', '2007-03-10 14:46:52', '论坛日志管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002111', 'forum', '2007-03-10 14:46:54', '替换/限制处理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002112', 'forum', '2007-03-10 14:46:55', '论坛日志管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002113', 'forum', '2007-03-10 14:46:56', '替换/限制处理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002114', 'forum', '2007-03-10 14:46:57', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002115', 'forum', '2007-03-10 14:47:00', '模块添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002116', 'forum', '2007-03-10 14:47:01', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002117', 'forum', '2007-03-10 14:47:02', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002118', 'forum', '2007-03-10 14:48:47', '论坛', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002119', 'forum', '2007-03-10 14:48:48', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002120', 'forum', '2007-03-10 14:48:59', '论坛', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002121', 'forum', '2007-03-10 14:52:45', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002122', 'forum', '2007-03-10 14:53:44', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002123', 'forum', '2007-03-10 14:53:51', '模块添加', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002124', 'forum', '2007-03-10 14:53:54', '用户管理', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002125', 'forum', '2007-03-10 14:54:13', '论坛日志管理', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002126', 'forum', '2007-03-10 14:54:15', '替换/限制处理', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002127', 'forum', '2007-03-10 14:54:55', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002128', 'jingyuzhuo', '2007-03-10 14:55:14', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002129', 'jingyuzhuo', '2007-03-10 14:55:16', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002130', 'jingyuzhuo', '2007-03-10 14:55:19', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002131', 'jingyuzhuo', '2007-03-10 14:55:27', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002132', 'jingyuzhuo', '2007-03-10 14:55:28', '卓越办公自动化系统', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002133', 'jingyuzhuo', '2007-03-10 14:55:30', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002134', 'jingyuzhuo', '2007-03-10 14:55:35', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002135', 'jingyuzhuo', '2007-03-10 14:57:06', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002136', 'jingyuzhuo', '2007-03-10 14:57:07', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002137', 'jingyuzhuo', '2007-03-10 14:57:50', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002138', 'jingyuzhuo', '2007-03-10 14:57:52', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002139', 'jingyuzhuo', '2007-03-10 14:57:53', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002140', 'jingyuzhuo', '2007-03-10 14:58:08', '论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002141', 'zhaoyifei', '2007-03-10 16:29:16', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002142', 'zhaoyifei', '2007-03-10 16:31:36', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002143', 'zhaoyifei', '2007-03-10 16:34:13', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002144', 'zhaoyifei', '2007-03-10 16:34:15', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002145', 'zhaoyifei', '2007-03-10 16:34:32', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002146', 'zhaoyifei', '2007-03-10 16:42:50', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002147', 'zhaoyifei', '2007-03-10 16:43:19', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002148', 'yepuliang', '2007-03-10 16:50:01', '工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002149', 'yepuliang', '2007-03-10 16:50:04', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002150', 'zhaoyifei', '2007-03-10 16:50:14', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002151', 'zhangfeng', '2007-03-10 16:50:22', '修改密码', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002152', 'zhangfeng', '2007-03-10 16:50:26', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002153', 'zhangfeng', '2007-03-10 16:50:30', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002154', 'jingyuzhuo', '2007-03-10 16:50:48', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002155', 'jingyuzhuo', '2007-03-10 16:51:08', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002156', 'jingyuzhuo', '2007-03-10 16:51:08', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002157', 'jingyuzhuo', '2007-03-10 16:51:08', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002158', 'jingyuzhuo', '2007-03-10 16:51:14', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002159', 'jingyuzhuo', '2007-03-10 16:51:15', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002160', 'jingyuzhuo', '2007-03-10 16:55:41', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002161', 'zhangfeng', '2007-03-10 16:57:08', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002162', 'zhaoyifei', '2007-03-10 16:59:17', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002163', 'zhaoyifei', '2007-03-10 16:59:56', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002164', 'zhaoyifei', '2007-03-10 17:00:04', '用户管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002165', 'jingyuzhuo', '2007-03-10 17:00:06', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002166', 'zhaoyifei', '2007-03-10 17:00:25', '工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002167', 'zhaoyifei', '2007-03-10 17:00:31', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002168', 'jingyuzhuo', '2007-03-10 17:00:36', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002169', 'jingyuzhuo', '2007-03-10 17:01:02', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002170', 'jingyuzhuo', '2007-03-10 17:01:09', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002171', 'jingyuzhuo', '2007-03-10 17:01:13', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002172', 'jingyuzhuo', '2007-03-10 17:02:20', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002173', 'jingyuzhuo', '2007-03-10 17:02:34', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002174', 'jingyuzhuo', '2007-03-10 17:03:31', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002175', 'chenyiying', '2007-03-10 17:04:23', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002176', 'chenyiying', '2007-03-10 17:04:27', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002177', 'zhaoyifei', '2007-03-10 17:04:35', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002178', 'chenyiying', '2007-03-10 17:04:49', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002179', 'jingyuzhuo', '2007-03-10 17:05:09', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002180', 'jingyuzhuo', '2007-03-10 17:06:44', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002181', 'chenyiying', '2007-03-10 17:07:45', '工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002182', 'chenyiying', '2007-03-10 17:07:55', '工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002183', 'chenyiying', '2007-03-10 17:07:57', '详细工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002184', 'chenyiying', '2007-03-10 17:08:00', '阶段工作计划', 'lookup', '192.168.1.110', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002185', 'jingyuzhuo', '2007-03-10 17:12:21', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002186', 'chenyiying', '2007-03-10 17:12:59', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002187', 'zhaoyifei', '2007-03-10 17:27:38', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002188', 'zhaoyifei', '2007-03-10 17:27:39', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002189', 'zhaoyifei', '2007-03-10 17:27:45', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002190', 'zhaoyifei', '2007-03-10 17:28:00', '工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002191', 'guxiaofeng', '2007-03-12 07:54:15', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002192', 'guxiaofeng', '2007-03-12 07:54:28', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002193', 'guxiaofeng', '2007-03-12 07:54:32', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002194', 'jingyuzhuo', '2007-03-12 08:33:00', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002195', 'jingyuzhuo', '2007-03-12 08:33:04', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002196', 'chenyiying', '2007-03-12 08:33:30', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002197', 'chenyiying', '2007-03-12 08:33:37', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002198', 'chenyiying', '2007-03-12 08:33:39', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002199', 'chenyiying', '2007-03-12 08:33:45', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002200', 'guxiaofeng', '2007-03-12 08:44:03', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002201', 'guxiaofeng', '2007-03-12 08:45:28', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002202', 'zhangfeng', '2007-03-12 08:46:01', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002203', 'guxiaofeng', '2007-03-12 08:47:02', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002204', 'yepuliang', '2007-03-12 16:53:02', '修改密码', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002205', 'yepuliang', '2007-03-12 16:53:16', '内部邮件', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002206', 'yepuliang', '2007-03-12 16:53:58', '个人通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002207', 'guxiaofeng', '2007-03-13 08:16:59', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002208', 'guxiaofeng', '2007-03-13 08:17:32', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002209', 'guxiaofeng', '2007-03-13 08:18:39', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002210', 'guxiaofeng', '2007-03-13 08:18:47', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002211', 'guxiaofeng', '2007-03-13 08:22:03', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002212', 'guxiaofeng', '2007-03-13 08:22:04', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002213', 'guxiaofeng', '2007-03-13 08:22:14', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002214', 'guxiaofeng', '2007-03-13 08:22:32', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002215', 'guxiaofeng', '2007-03-13 08:22:54', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002216', 'guxiaofeng', '2007-03-13 08:23:16', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002217', 'guxiaofeng', '2007-03-13 08:23:27', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002218', 'guxiaofeng', '2007-03-13 08:23:29', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002219', 'guxiaofeng', '2007-03-13 08:23:45', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002220', 'guxiaofeng', '2007-03-13 08:24:24', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002221', 'guxiaofeng', '2007-03-13 08:24:29', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002222', 'zhaoyifei', '2007-03-13 08:37:38', '工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002223', 'zhaoyifei', '2007-03-13 08:37:47', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002224', 'zhaoyifei', '2007-03-13 08:37:49', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002225', 'zhaoyifei', '2007-03-13 08:37:54', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002226', 'guxiaofeng', '2007-03-13 09:38:54', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002227', 'guxiaofeng', '2007-03-13 09:38:57', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002228', 'guxiaofeng', '2007-03-13 09:41:20', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002229', 'guxiaofeng', '2007-03-13 09:41:48', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002230', 'guxiaofeng', '2007-03-13 09:47:06', '组管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002231', 'guxiaofeng', '2007-03-13 09:49:00', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002232', 'guxiaofeng', '2007-03-13 09:49:10', '角色管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002233', 'guxiaofeng', '2007-03-13 09:49:24', '组管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002234', 'guxiaofeng', '2007-03-13 09:50:58', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002235', 'guxiaofeng', '2007-03-13 09:51:42', '组管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002236', 'guxiaofeng', '2007-03-13 09:51:52', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002237', 'guxiaofeng', '2007-03-13 09:52:48', '组管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002238', 'guxiaofeng', '2007-03-13 09:53:29', '类型管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002239', 'guxiaofeng', '2007-03-13 09:53:31', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002240', 'guxiaofeng', '2007-03-13 09:54:23', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002241', 'forum', '2007-03-13 09:55:35', '卓越办公自动化系统', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002242', 'guxiaofeng', '2007-03-13 09:56:11', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002243', 'guxiaofeng', '2007-03-13 09:57:11', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002244', 'guxiaofeng', '2007-03-13 09:57:13', '组管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002245', 'zhaoyifei', '2007-03-13 09:57:17', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002246', 'zhaoyifei', '2007-03-13 09:57:23', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002247', 'guxiaofeng', '2007-03-13 09:57:29', '岗位管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002248', 'guxiaofeng', '2007-03-13 09:57:31', '类型管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002249', 'zhangfeng', '2007-03-13 09:57:33', '图书入库', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002250', 'guxiaofeng', '2007-03-13 09:57:35', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002251', 'guxiaofeng', '2007-03-13 09:57:58', '组管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002252', 'zhaoyifei', '2007-03-13 09:59:10', '办公平台', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002253', 'guxiaofeng', '2007-03-13 09:59:11', '类型管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002254', 'zhaoyifei', '2007-03-13 09:59:14', '类型管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002255', 'guxiaofeng', '2007-03-13 09:59:15', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002256', 'zhaoyifei', '2007-03-13 09:59:16', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002257', 'zhaoyifei', '2007-03-13 09:59:18', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002258', 'zhaoyifei', '2007-03-13 09:59:38', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002259', 'zhaoyifei', '2007-03-13 09:59:39', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002260', 'zhaoyifei', '2007-03-13 09:59:41', '类型管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002261', 'zhaoyifei', '2007-03-13 09:59:52', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002262', 'guxiaofeng', '2007-03-13 10:01:05', '模块管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002263', 'guxiaofeng', '2007-03-13 10:01:06', '组管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002264', 'guxiaofeng', '2007-03-13 10:01:21', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002265', 'guxiaofeng', '2007-03-13 10:02:59', '办公平台', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002266', 'guxiaofeng', '2007-03-13 10:03:03', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002267', 'zhaoyifei', '2007-03-13 10:03:22', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002268', 'zhaoyifei', '2007-03-13 10:03:32', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002269', 'guxiaofeng', '2007-03-13 10:04:32', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002270', 'guxiaofeng', '2007-03-13 10:04:43', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002271', 'guxiaofeng', '2007-03-13 10:04:54', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002272', 'zhaoyifei', '2007-03-13 10:05:35', '模块管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002273', 'zhaoyifei', '2007-03-13 10:05:45', '用户管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002274', 'zhaoyifei', '2007-03-13 10:05:48', '组管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002275', 'zhaoyifei', '2007-03-13 10:05:50', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002276', 'zhaoyifei', '2007-03-13 10:06:23', '用户管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002277', 'guxiaofeng', '2007-03-13 10:06:39', '用户管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002278', 'guxiaofeng', '2007-03-13 10:08:10', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002279', 'zhaoyifei', '2007-03-13 10:09:30', '模块管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002280', 'zhaoyifei', '2007-03-13 10:18:12', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002281', 'zhaoyifei', '2007-03-13 10:18:24', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002282', 'zhaoyifei', '2007-03-13 10:18:27', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002283', 'zhaoyifei', '2007-03-13 10:18:29', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002284', 'zhaoyifei', '2007-03-13 10:18:31', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002285', 'zhaoyifei', '2007-03-13 10:18:32', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002286', 'zhaoyifei', '2007-03-13 10:18:41', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002287', 'zhaoyifei', '2007-03-13 10:18:42', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002288', 'zhaoyifei', '2007-03-13 10:18:44', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002289', 'zhaoyifei', '2007-03-13 10:18:59', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002290', 'zhaoyifei', '2007-03-13 10:19:01', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002291', 'zhaoyifei', '2007-03-13 10:19:05', '模块添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002292', 'zhaoyifei', '2007-03-13 10:20:31', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002293', 'zhaoyifei', '2007-03-13 10:20:33', '模块添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002294', 'zhaoyifei', '2007-03-13 10:20:36', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002295', 'zhaoyifei', '2007-03-13 10:20:37', '替换/限制处理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002296', 'zhaoyifei', '2007-03-13 10:20:37', '论坛日志管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002297', 'zhaoyifei', '2007-03-13 10:20:40', '替换/限制处理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002298', 'zhaoyifei', '2007-03-13 10:20:40', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002299', 'zhaoyifei', '2007-03-13 10:20:41', '模块添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002300', 'zhaoyifei', '2007-03-13 10:20:42', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002301', 'yepuliang', '2007-03-13 10:23:46', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002302', 'yepuliang', '2007-03-13 10:23:50', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002303', 'zhaoyifei', '2007-03-13 10:23:50', '论坛', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002304', 'zhaoyifei', '2007-03-13 10:23:52', '模块添加', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002305', 'zhaoyifei', '2007-03-13 10:23:58', '论坛', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002306', 'zhaoyifei', '2007-03-13 10:23:59', '模块添加', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002307', 'zhaoyifei', '2007-03-13 10:24:00', '用户管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002308', 'zhaoyifei', '2007-03-13 10:24:01', '替换/限制处理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002309', 'zhaoyifei', '2007-03-13 10:24:01', '论坛日志管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002310', 'zhaoyifei', '2007-03-13 10:24:20', '模块添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002311', 'zhaoyifei', '2007-03-13 10:24:21', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002312', 'zhaoyifei', '2007-03-13 10:24:23', '替换/限制处理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002313', 'zhaoyifei', '2007-03-13 10:24:23', '论坛日志管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002314', 'zhaoyifei', '2007-03-13 10:24:26', '替换/限制处理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002315', 'zhaoyifei', '2007-03-13 10:24:26', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002316', 'zhaoyifei', '2007-03-13 10:24:27', '模块添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002317', 'zhaoyifei', '2007-03-13 10:24:28', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002318', 'zhaoyifei', '2007-03-13 10:24:28', '替换/限制处理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002319', 'zhaoyifei', '2007-03-13 10:24:29', '论坛日志管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002320', 'zhaoyifei', '2007-03-13 10:24:34', '替换/限制处理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002321', 'yepuliang', '2007-03-13 13:01:27', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002322', 'zhaoyifei', '2007-03-13 13:16:47', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002323', 'forum', '2007-03-13 13:17:34', '论坛', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002324', 'zhaoyifei', '2007-03-13 13:17:38', '员工信息', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002325', 'forum', '2007-03-13 13:17:51', '模块添加', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002326', 'forum', '2007-03-13 13:17:52', '用户管理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002327', 'forum', '2007-03-13 13:17:54', '替换/限制处理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002328', 'forum', '2007-03-13 13:17:55', '论坛日志管理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002329', 'forum', '2007-03-13 13:18:04', '替换/限制处理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002330', 'forum', '2007-03-13 13:18:32', '论坛', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002331', 'forum', '2007-03-13 13:19:57', '论坛', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002332', 'forum', '2007-03-13 13:21:44', '论坛', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002333', 'forum', '2007-03-13 13:21:56', '论坛', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002334', 'forum', '2007-03-13 13:21:57', '模块添加', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002335', 'forum', '2007-03-13 13:21:58', '用户管理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002336', 'forum', '2007-03-13 13:21:58', '替换/限制处理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002337', 'forum', '2007-03-13 13:21:59', '论坛日志管理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002338', 'zhaoyifei', '2007-03-13 13:22:10', '论坛', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002339', 'yepuliang', '2007-03-13 13:43:18', '公司通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002340', 'zhaoyifei', '2007-03-13 14:34:04', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002341', 'zhaoyifei', '2007-03-13 14:34:11', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002342', 'zhaoyifei', '2007-03-13 14:34:12', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002343', 'zhaoyifei', '2007-03-13 14:41:20', '员工信息', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002344', 'yepuliang', '2007-03-13 15:11:43', '个人通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002345', 'yepuliang', '2007-03-13 15:12:41', '公共通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002346', 'yepuliang', '2007-03-13 15:12:47', '公司通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002347', 'yepuliang', '2007-03-13 15:27:00', '留言板', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002348', 'yepuliang', '2007-03-13 15:28:03', '留言板', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002349', 'zhangfeng', '2007-03-13 15:44:43', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002350', 'zhangfeng', '2007-03-13 15:44:43', '个人通讯录', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002351', 'zhaoyifei', '2007-03-13 15:45:18', '员工信息', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002352', 'zhaoyifei', '2007-03-13 15:45:58', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002353', 'zhaoyifei', '2007-03-13 15:47:35', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002354', 'yepuliang', '2007-03-13 15:48:07', '修改密码', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002355', 'yepuliang', '2007-03-13 15:48:08', '个人通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002356', 'yepuliang', '2007-03-13 15:48:10', '公共通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002357', 'yepuliang', '2007-03-13 15:48:11', '工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002358', 'zhaoyifei', '2007-03-13 16:01:31', '员工信息', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002359', 'zhaoyifei', '2007-03-13 16:43:23', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002360', 'zhangfeng', '2007-03-14 08:41:14', '留言板', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002361', 'zhangfeng', '2007-03-14 08:41:19', '部门岗位信息', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002362', 'zhangfeng', '2007-03-14 08:41:22', '部门岗位信息', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002363', 'zhangfeng', '2007-03-14 08:41:22', '留言板管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002364', 'zhangfeng', '2007-03-14 08:41:46', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002365', 'zhangfeng', '2007-03-14 08:41:47', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002366', 'zhangfeng', '2007-03-14 08:45:59', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002367', 'zhaoyifei', '2007-03-14 09:32:41', '手机短信', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002368', 'zhaoyifei', '2007-03-14 09:33:24', '部门岗位信息', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002369', 'zhaoyifei', '2007-03-14 09:33:26', '公司通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002370', 'zhaoyifei', '2007-03-14 09:33:28', '公告管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002371', 'zhaoyifei', '2007-03-14 09:55:19', '缺勤查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002372', 'zhangfeng', '2007-03-14 10:56:32', '详细工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002373', 'zhangfeng', '2007-03-14 10:56:39', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002374', 'zhangfeng', '2007-03-14 10:56:46', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002375', 'zhangfeng', '2007-03-14 10:56:54', '公共通讯录', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002376', 'zhangfeng', '2007-03-14 10:57:00', '个人通讯录', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002377', 'zhangfeng', '2007-03-14 10:57:04', '修改密码', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002378', 'jingyuzhuo', '2007-03-14 13:15:58', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002379', 'jingyuzhuo', '2007-03-14 13:16:16', '工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002380', 'zhaoyifei', '2007-03-14 13:33:51', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002381', 'zhaoyifei', '2007-03-14 13:33:53', '论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002382', 'zhaoyifei', '2007-03-14 14:01:06', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002383', 'zhaoyifei', '2007-03-14 14:26:42', '修改密码', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002384', 'zhaoyifei', '2007-03-14 14:26:44', '岗位管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002385', 'yepuliang', '2007-03-14 14:28:04', '工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002386', 'yepuliang', '2007-03-14 14:28:06', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002387', 'zhaoyifei', '2007-03-14 14:52:58', '会议申请管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002388', 'zhaoyifei', '2007-03-14 14:53:26', '会议申请管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002389', 'zhaoyifei', '2007-03-14 15:26:53', '会议室查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002390', 'zhaoyifei', '2007-03-14 15:26:56', '使用查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002391', 'zhaoyifei', '2007-03-14 15:27:03', '会议室查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002392', 'zhaoyifei', '2007-03-14 15:30:29', '申请', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002393', 'zhaoyifei', '2007-03-14 15:30:31', '设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002394', 'zhaoyifei', '2007-03-14 15:30:35', '使用查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002395', 'zhaoyifei', '2007-03-14 15:30:36', '申请', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002396', 'zhaoyifei', '2007-03-14 15:30:39', '会议室查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002397', 'zhaoyifei', '2007-03-14 15:51:47', '设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002398', 'guxiaofeng', '2007-03-15 08:35:27', '论坛', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002399', 'guxiaofeng', '2007-03-15 08:37:35', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002400', 'guxiaofeng', '2007-03-15 08:37:44', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002401', 'guxiaofeng', '2007-03-15 08:37:47', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002402', 'guxiaofeng', '2007-03-15 08:37:50', '论坛', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002403', 'guxiaofeng', '2007-03-15 08:41:07', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002404', 'guxiaofeng', '2007-03-15 08:44:05', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002405', 'guxiaofeng', '2007-03-15 08:44:46', '论坛', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002406', 'guxiaofeng', '2007-03-15 08:46:36', '论坛', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002407', 'zhangfeng', '2007-03-15 08:47:45', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002408', 'zhangfeng', '2007-03-15 08:47:48', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002409', 'zhangfeng', '2007-03-15 08:47:55', '详细工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002410', 'zhangfeng', '2007-03-15 08:48:12', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002411', 'zhangfeng', '2007-03-15 08:48:22', '修改密码', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002412', 'zhangfeng', '2007-03-15 08:48:23', '个人通讯录', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002413', 'zhangfeng', '2007-03-15 08:48:25', '公共通讯录', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002414', 'zhangfeng', '2007-03-15 08:48:28', '内部邮件', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002415', 'zhaoyifei', '2007-03-15 09:17:27', '模块添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002416', 'zhaoyifei', '2007-03-15 09:20:15', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002417', 'zhaoyifei', '2007-03-15 09:21:01', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002418', 'zhaoyifei', '2007-03-15 09:27:30', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002419', 'zhaoyifei', '2007-03-15 09:27:32', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002420', 'zhangfeng', '2007-03-15 09:28:08', 'OA论坛', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002421', 'zhangfeng', '2007-03-15 09:30:27', '办公平台', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002422', 'zhangfeng', '2007-03-15 09:43:27', 'OA论坛', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002423', 'zhaoyifei', '2007-03-15 09:46:09', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002424', 'zhangfeng', '2007-03-15 09:46:17', '办公平台', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002425', 'zhangfeng', '2007-03-15 10:17:44', '办公平台', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002426', 'zhangfeng', '2007-03-15 10:17:57', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002427', 'zhangfeng', '2007-03-15 10:19:54', 'OA论坛', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002428', 'zhangfeng', '2007-03-15 10:54:03', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002429', 'zhangfeng', '2007-03-15 10:55:35', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002430', 'zhangfeng', '2007-03-15 10:55:37', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002431', 'zhangfeng', '2007-03-15 10:55:43', '详细工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002432', 'zhangfeng', '2007-03-15 10:55:50', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002433', 'zhangfeng', '2007-03-15 10:55:51', '详细工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002434', 'zhangfeng', '2007-03-15 10:55:53', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002435', 'zhangfeng', '2007-03-15 10:55:56', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002436', 'zhaoyifei', '2007-03-15 14:35:12', '会议室查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002437', 'zhaoyifei', '2007-03-15 14:37:37', '设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002438', 'zhaoyifei', '2007-03-15 14:37:42', '设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002439', 'guxiaofeng', '2007-03-15 14:41:04', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002440', 'zhaoyifei', '2007-03-15 14:52:50', '使用查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002441', 'zhaoyifei', '2007-03-15 14:57:43', '申请', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002442', 'zhaoyifei', '2007-03-15 14:59:40', '会议室查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002443', 'zhaoyifei', '2007-03-15 15:01:16', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002444', 'zhaoyifei', '2007-03-15 16:00:21', '车辆登记', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002445', 'guxiaofeng', '2007-03-15 16:08:18', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002446', 'guxiaofeng', '2007-03-15 16:08:20', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002447', 'guxiaofeng', '2007-03-15 16:12:56', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002448', 'guxiaofeng', '2007-03-15 16:13:03', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002449', 'guxiaofeng', '2007-03-15 16:15:56', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002450', 'guxiaofeng', '2007-03-15 16:15:58', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002451', 'guxiaofeng', '2007-03-15 16:16:08', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002452', 'guxiaofeng', '2007-03-15 16:18:22', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002453', 'zhaoyifei', '2007-03-15 16:18:41', '模块管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002454', 'zhaoyifei', '2007-03-15 16:20:38', '模块管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002455', 'zhaoyifei', '2007-03-15 16:21:25', '模块添加', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002456', 'zhaoyifei', '2007-03-15 16:21:32', '模块管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002457', 'zhaoyifei', '2007-03-15 16:22:22', '类型管理', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002458', 'zhaoyifei', '2007-03-15 16:23:05', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002459', 'guxiaofeng', '2007-03-15 16:23:52', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002460', 'zhaoyifei', '2007-03-15 16:24:07', '类型管理', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002461', 'zhaoyifei', '2007-03-15 16:33:04', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002462', 'zhaoyifei', '2007-03-15 16:35:06', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002463', 'zhaoyifei', '2007-03-15 16:36:23', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002464', 'zhaoyifei', '2007-03-15 16:36:48', '计划审批', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002465', 'zhaoyifei', '2007-03-15 16:36:53', '详细工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002466', 'zhaoyifei', '2007-03-15 16:47:30', '模块添加', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002467', 'zhaoyifei', '2007-03-15 16:47:39', '模块管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002468', 'zhaoyifei', '2007-03-15 16:48:10', '用户管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002469', 'zhaoyifei', '2007-03-15 16:48:13', '模块管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002470', 'guxiaofeng', '2007-03-15 16:48:14', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002471', 'guxiaofeng', '2007-03-15 16:49:36', '类型管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002472', 'guxiaofeng', '2007-03-15 16:49:53', '类型管理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002473', 'guxiaofeng', '2007-03-15 16:49:55', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002474', 'zhaoyifei', '2007-03-15 16:50:02', '用户管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002475', 'guxiaofeng', '2007-03-15 16:50:03', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002476', 'zhaoyifei', '2007-03-15 16:50:04', '组管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002477', 'zhangfeng', '2007-03-15 16:51:12', '公共通讯录', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002478', 'zhangfeng', '2007-03-15 16:51:13', '个人通讯录', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002479', 'zhangfeng', '2007-03-15 16:51:13', '修改密码', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002480', 'zhangfeng', '2007-03-15 16:51:19', 'OA论坛', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002481', 'zhangfeng', '2007-03-15 16:51:21', '模块添加', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002482', 'zhaoyifei', '2007-03-15 16:51:42', '模块管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002483', 'zhaoyifei', '2007-03-15 16:51:48', '模块管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002484', 'zhaoyifei', '2007-03-15 16:51:49', '组管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002485', 'guxiaofeng', '2007-03-15 16:52:47', '替换/限制处理', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002486', 'guxiaofeng', '2007-03-15 16:53:41', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002487', 'guxiaofeng', '2007-03-15 16:55:08', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002488', 'zhangfeng', '2007-03-15 16:55:10', '内部邮件', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002489', 'guxiaofeng', '2007-03-15 16:55:11', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002490', 'guxiaofeng', '2007-03-15 16:55:14', 'OA论坛', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002491', 'guxiaofeng', '2007-03-15 16:56:45', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002492', 'guxiaofeng', '2007-03-15 16:57:03', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002493', 'guxiaofeng', '2007-03-15 16:57:09', 'OA论坛', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002494', 'guxiaofeng', '2007-03-15 16:57:12', '模块添加', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002495', 'guxiaofeng', '2007-03-15 16:57:43', '办公平台', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002496', 'guxiaofeng', '2007-03-15 16:57:54', 'OA论坛', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002497', 'guxiaofeng', '2007-03-15 16:59:19', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002498', 'zhangfeng', '2007-03-15 17:00:37', '内部邮件', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002499', 'guxiaofeng', '2007-03-15 17:00:37', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002500', 'zhangfeng', '2007-03-15 17:00:38', '外部邮件', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002501', 'zhangfeng', '2007-03-15 17:00:40', '邮箱管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002502', 'zhaoyifei', '2007-03-15 17:05:38', '车辆登记', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002503', 'zhangfeng', '2007-03-15 17:05:49', 'OA论坛', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002504', 'zhaoyifei', '2007-03-15 17:12:24', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002505', 'zhaoyifei', '2007-03-15 17:12:47', '卓越办公自动化系统', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002506', 'zhaoyifei', '2007-03-15 17:13:35', '模块管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002507', 'zhaoyifei', '2007-03-15 17:13:48', '模块管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002508', 'zhaoyifei', '2007-03-15 17:13:53', '车辆登记', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002509', 'zhaoyifei', '2007-03-15 17:13:54', '用车申请', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002510', 'zhaoyifei', '2007-03-15 17:14:01', '模块管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002511', 'zhaoyifei', '2007-03-15 17:14:22', '模块管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002512', 'zhaoyifei', '2007-03-15 17:14:26', '用户管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002513', 'zhaoyifei', '2007-03-15 17:14:29', '模块管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002514', 'zhaoyifei', '2007-03-15 17:14:30', '用户管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002515', 'zhaoyifei', '2007-03-15 17:14:40', '模块管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002516', 'zhaoyifei', '2007-03-15 17:14:45', '角色管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002517', 'zhaoyifei', '2007-03-15 17:14:49', '类型管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002518', 'zhaoyifei', '2007-03-15 17:14:56', '组管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002519', 'zhangfeng', '2007-03-15 17:15:03', 'OA论坛', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002520', 'zhaoyifei', '2007-03-15 17:15:21', 'OA论坛', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002521', 'jingyuzhuo', '2007-03-16 11:10:10', '阶段工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002522', 'jingyuzhuo', '2007-03-16 11:10:40', '详细工作计划', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002523', 'jingyuzhuo', '2007-03-16 11:17:01', 'OA论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002524', 'jingyuzhuo', '2007-03-16 11:17:07', '模块添加', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002525', 'jingyuzhuo', '2007-03-16 11:17:08', '用户管理', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002526', 'jingyuzhuo', '2007-03-16 11:17:09', '模块添加', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002527', 'jingyuzhuo', '2007-03-16 11:17:09', '替换/限制处理', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002528', 'jingyuzhuo', '2007-03-16 11:17:10', '论坛日志管理', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002529', 'yepuliang', '2007-03-16 11:18:27', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002530', 'yepuliang', '2007-03-16 11:18:29', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002531', 'yepuliang', '2007-03-16 11:18:29', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002532', 'yepuliang', '2007-03-16 11:18:30', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002533', 'yepuliang', '2007-03-16 11:18:31', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002534', 'yepuliang', '2007-03-16 11:18:32', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002535', 'yepuliang', '2007-03-16 11:18:33', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002536', 'yepuliang', '2007-03-16 11:18:33', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002537', 'zhangfeng', '2007-03-16 11:18:41', 'OA论坛', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002538', 'yepuliang', '2007-03-16 11:18:42', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002539', 'yepuliang', '2007-03-16 11:18:43', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002540', 'yepuliang', '2007-03-16 11:18:44', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002541', 'yepuliang', '2007-03-16 11:18:45', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002542', 'yepuliang', '2007-03-16 11:18:46', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002543', 'yepuliang', '2007-03-16 11:18:46', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002544', 'zhangfeng', '2007-03-16 11:18:47', '修改密码', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002545', 'yepuliang', '2007-03-16 11:18:47', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002546', 'yepuliang', '2007-03-16 11:18:48', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002547', 'zhangfeng', '2007-03-16 11:18:48', '个人通讯录', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002548', 'yepuliang', '2007-03-16 11:18:48', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002549', 'yepuliang', '2007-03-16 11:18:49', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002550', 'yepuliang', '2007-03-16 11:18:49', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002551', 'yepuliang', '2007-03-16 11:18:49', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002552', 'yepuliang', '2007-03-16 11:18:50', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002553', 'yepuliang', '2007-03-16 11:18:50', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002554', 'yepuliang', '2007-03-16 11:18:51', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002555', 'yepuliang', '2007-03-16 11:18:51', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002556', 'yepuliang', '2007-03-16 11:18:51', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002557', 'yepuliang', '2007-03-16 11:18:52', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002558', 'yepuliang', '2007-03-16 11:18:52', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002559', 'yepuliang', '2007-03-16 11:18:53', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002560', 'yepuliang', '2007-03-16 11:18:53', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002561', 'yepuliang', '2007-03-16 11:18:55', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002562', 'yepuliang', '2007-03-16 11:18:55', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002563', 'yepuliang', '2007-03-16 11:18:55', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002564', 'yepuliang', '2007-03-16 11:18:57', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002565', 'yepuliang', '2007-03-16 11:18:58', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002566', 'yepuliang', '2007-03-16 11:18:58', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002567', 'yepuliang', '2007-03-16 11:18:59', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002568', 'yepuliang', '2007-03-16 11:18:59', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002569', 'yepuliang', '2007-03-16 11:19:00', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002570', 'yepuliang', '2007-03-16 11:19:00', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002571', 'yepuliang', '2007-03-16 11:19:01', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002572', 'yepuliang', '2007-03-16 11:19:01', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002573', 'yepuliang', '2007-03-16 11:19:01', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002574', 'yepuliang', '2007-03-16 11:19:02', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002575', 'yepuliang', '2007-03-16 11:22:57', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002576', 'yepuliang', '2007-03-16 11:22:58', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002577', 'yepuliang', '2007-03-16 11:22:59', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002578', 'yepuliang', '2007-03-16 11:23:00', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002579', 'yepuliang', '2007-03-16 11:23:01', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002580', 'yepuliang', '2007-03-16 11:23:01', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002581', 'jingyuzhuo', '2007-03-16 11:28:34', 'OA论坛', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002582', 'jingyuzhuo', '2007-03-16 11:28:37', '模块添加', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002583', 'jingyuzhuo', '2007-03-16 11:28:38', '用户管理', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002584', 'jingyuzhuo', '2007-03-16 11:28:43', '详细工作计划', 'lookup', '192.168.1.200', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002585', 'zhaoyifei', '2007-03-16 13:23:09', '会议申请管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002586', 'zhaoyifei', '2007-03-16 14:01:30', 'OA论坛', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002587', 'zhaoyifei', '2007-03-16 14:02:00', '卓越办公自动化系统', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002588', 'zhaoyifei', '2007-03-16 14:06:53', 'OA论坛', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002589', 'zhaoyifei', '2007-03-16 14:48:48', '资产信息管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002590', 'zhaoyifei', '2007-03-16 14:51:47', '资产操作查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002591', 'zhaoyifei', '2007-03-16 14:59:28', '资产信息管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002592', 'zhaoyifei', '2007-03-16 15:06:36', 'OA论坛', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002593', 'zhaoyifei', '2007-03-16 15:29:50', '资产操作查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002594', 'zhaoyifei', '2007-03-16 15:29:55', '资产操作查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002595', 'zhaoyifei', '2007-03-16 15:30:01', '资产操作查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002596', 'zhaoyifei', '2007-03-16 15:30:02', '资产信息管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002597', 'zhaoyifei', '2007-03-16 15:30:03', '资产操作查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002598', 'zhaoyifei', '2007-03-16 15:31:41', '资产信息管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002599', 'zhaoyifei', '2007-03-16 15:47:15', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002600', 'zhangfeng', '2007-03-16 15:47:27', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002601', 'zhangfeng', '2007-03-16 15:47:29', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002602', 'zhaoyifei', '2007-03-16 15:53:07', '资产操作查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002603', 'zhaoyifei', '2007-03-16 15:53:12', '资产信息管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002604', 'zhaoyifei', '2007-03-16 15:54:35', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002605', 'zhaoyifei', '2007-03-16 15:55:19', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002606', 'zhaoyifei', '2007-03-16 15:57:59', '资产信息管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002607', 'zhaoyifei', '2007-03-16 15:58:26', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002608', 'zhaoyifei', '2007-03-16 16:11:32', '用户管理', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002609', 'zhaoyifei', '2007-03-16 16:11:36', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002610', 'zhaoyifei', '2007-03-16 16:11:39', '详细工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002611', 'zhaoyifei', '2007-03-16 16:11:39', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002612', 'zhaoyifei', '2007-03-16 16:11:41', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002613', 'zhaoyifei', '2007-03-16 16:11:42', '详细工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002614', 'zhaoyifei', '2007-03-16 16:11:43', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002615', 'zhaoyifei', '2007-03-16 17:04:36', 'OA论坛', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002616', 'zhaoyifei', '2007-03-16 17:04:45', 'OA论坛', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002617', 'guxiaofeng', '2007-03-16 17:07:38', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002618', 'guxiaofeng', '2007-03-16 17:07:40', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002619', 'yepuliang', '2007-03-16 17:14:12', '工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002620', 'yepuliang', '2007-03-16 17:14:14', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002621', 'yepuliang', '2007-03-16 17:14:23', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002622', 'guxiaofeng', '2007-03-16 17:15:47', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002623', 'guxiaofeng', '2007-03-16 17:17:09', 'OA论坛', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002624', 'guxiaofeng', '2007-03-16 17:19:37', 'OA论坛', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002625', 'guxiaofeng', '2007-03-16 17:20:09', 'OA论坛', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002626', 'guxiaofeng', '2007-03-16 17:20:57', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002627', 'guxiaofeng', '2007-03-16 17:32:45', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002628', 'guxiaofeng', '2007-03-16 17:32:52', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002629', 'guxiaofeng', '2007-03-16 17:39:00', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002630', 'guxiaofeng', '2007-03-16 17:41:51', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002631', 'guxiaofeng', '2007-03-16 17:41:52', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002632', 'guxiaofeng', '2007-03-16 17:41:53', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002633', 'guxiaofeng', '2007-03-16 17:41:56', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002634', 'guxiaofeng', '2007-03-16 17:41:57', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002635', 'guxiaofeng', '2007-03-16 17:41:58', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002636', 'guxiaofeng', '2007-03-16 17:42:00', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002637', 'guxiaofeng', '2007-03-16 17:42:01', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002638', 'guxiaofeng', '2007-03-16 17:42:02', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002639', 'guxiaofeng', '2007-03-16 17:42:03', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002640', 'guxiaofeng', '2007-03-16 17:42:05', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002641', 'guxiaofeng', '2007-03-16 17:42:08', 'OA论坛', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002642', 'guxiaofeng', '2007-03-16 17:42:10', '模块添加', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002643', 'guxiaofeng', '2007-03-16 17:42:17', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002644', 'guxiaofeng', '2007-03-16 17:42:43', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002645', 'guxiaofeng', '2007-03-17 13:10:15', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002646', 'guxiaofeng', '2007-03-17 13:10:18', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002647', 'guxiaofeng', '2007-03-17 15:01:03', '修改密码', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002648', 'guxiaofeng', '2007-03-17 15:01:04', '个人通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002649', 'guxiaofeng', '2007-03-17 15:01:07', '公共通讯录', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002650', 'guxiaofeng', '2007-03-17 15:01:11', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002651', 'guxiaofeng', '2007-03-17 15:01:16', '详细工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002652', 'guxiaofeng', '2007-03-17 15:02:44', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002653', 'guxiaofeng', '2007-03-17 15:03:05', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002654', 'guxiaofeng', '2007-03-17 15:03:09', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002655', 'guxiaofeng', '2007-03-17 15:03:19', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002656', 'guxiaofeng', '2007-03-17 15:03:24', 'OA论坛', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002657', 'guxiaofeng', '2007-03-17 15:05:42', '办公平台', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002658', 'guxiaofeng', '2007-03-17 15:05:57', '模块添加', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002659', 'guxiaofeng', '2007-03-17 15:07:52', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002660', 'guxiaofeng', '2007-03-17 15:07:54', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002661', 'yepuliang', '2007-03-19 13:17:29', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002662', 'yepuliang', '2007-03-19 16:10:04', '公司通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002663', 'yepuliang', '2007-03-19 16:10:07', '公司通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002664', 'yepuliang', '2007-03-19 16:10:10', '公司通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002665', 'yepuliang', '2007-03-19 16:10:14', '公司通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002666', 'yepuliang', '2007-03-19 16:10:22', '个人通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002667', 'guxiaofeng', '2007-03-19 16:49:40', '计划审批', 'lookup', '221.212.27.15', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002668', 'yepuliang', '2007-03-20 16:59:30', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002669', 'yepuliang', '2007-03-20 16:59:38', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002670', 'yepuliang', '2007-03-21 10:33:46', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002671', 'zhangfeng', '2007-03-21 14:45:39', '工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002672', 'zhangfeng', '2007-03-21 14:45:41', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002673', 'zhaoyifei', '2007-03-21 15:09:15', '论坛日志管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002674', 'yepuliang', '2007-03-21 16:18:48', '模块添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002675', 'zhaoyifei', '2007-03-22 12:54:16', 'OA论坛', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002676', 'zhaoyifei', '2007-03-22 12:54:27', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002677', 'zhaoyifei', '2007-03-22 14:00:39', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002678', 'zhaoyifei', '2007-03-22 14:00:42', 'OA论坛', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002679', 'guxiaofeng', '2007-03-22 14:13:39', '工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002680', 'guxiaofeng', '2007-03-22 14:13:42', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002681', 'guxiaofeng', '2007-03-22 14:14:08', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002682', 'guxiaofeng', '2007-03-22 14:14:50', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002683', 'guxiaofeng', '2007-03-22 14:15:37', '计划审批', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002684', 'guxiaofeng', '2007-03-22 14:24:40', '阶段工作计划', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002685', 'guxiaofeng', '2007-03-22 14:34:05', '内部邮件', 'lookup', '218.25.101.108', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002686', 'jingyuzhuo', '2007-03-22 14:40:34', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002687', 'jingyuzhuo', '2007-03-22 14:43:08', '阶段工作计划', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002688', 'zhangfeng', '2007-03-22 14:51:17', 'OA论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002689', 'zhangfeng', '2007-03-22 14:51:33', 'OA论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002690', 'zhangfeng', '2007-03-22 14:51:38', 'OA论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002691', 'jingyuzhuo', '2007-03-22 15:30:03', 'OA论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002692', 'jingyuzhuo', '2007-03-22 15:30:14', '论坛日志管理', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002693', 'jingyuzhuo', '2007-03-22 15:30:16', '用户管理', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002694', 'jingyuzhuo', '2007-03-22 15:30:17', '模块添加', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002695', 'jingyuzhuo', '2007-03-22 15:30:17', 'OA论坛', 'lookup', '192.168.1.159', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002696', 'yepuliang', '2007-03-22 16:19:53', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002697', 'zhaoyifei', '2007-03-22 17:13:10', '阶段工作计划', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002698', 'zhaoyifei', '2007-03-22 17:13:11', '计划审批', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002699', 'zhaoyifei', '2007-03-22 17:13:18', 'OA论坛', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000002700', 'zhaoyifei', '2007-03-22 17:15:45', 'OA论坛', 'lookup', '192.168.1.3', '');
INSERT INTO `sys_module` VALUES ('1', '/ETOA/oa/mainOper.do?method=toMain', 'NoRight.gif', '1', 'treeRoot', '1', '卓越办公自动化系统', '0', null);
INSERT INTO `sys_module` VALUES ('10', '', 'NoRight.gif', '变量管理777', 'SYS_MODULE_0000000542', '1', '人事管理', '31', null);
INSERT INTO `sys_module` VALUES ('16', '', 'NoRight.gif', '16', '1', '0', '工作流管理', '7', null);
INSERT INTO `sys_module` VALUES ('17', null, 'NoRight.gif', '17', '16', '1', '工作流定义', '41', null);
INSERT INTO `sys_module` VALUES ('18', null, 'NoRight.gif', '18', '16', '1', '工作流查询', '42', null);
INSERT INTO `sys_module` VALUES ('19', '/ETOA/sys/tree.do?method=loadTree', 'NoRight.gif', '19', '3', '1', '类型管理', '43', null);
INSERT INTO `sys_module` VALUES ('20', '', 'NoRight.gif', '20', 'SYS_MODULE_0000000542', '1', '考勤管理', '44', null);
INSERT INTO `sys_module` VALUES ('24', '/ETOA/oa/absenceWork.do?method=toResign', 'NoRight.gif', '24', '20', '1', '补签登记', '32', null);
INSERT INTO `sys_module` VALUES ('25', '', 'NoRight.gif', '25', '1', '1', '信息发布', '2', null);
INSERT INTO `sys_module` VALUES ('27', '', 'NoRight.gif', '', 'SYS_MODULE_0000000542', '1', '资源管理', '33', null);
INSERT INTO `sys_module` VALUES ('28', '', 'NoRight.gif', null, '27', '1', '会议室管理', '34', null);
INSERT INTO `sys_module` VALUES ('29', '/ETOA/oa/meetingManager.do?method=toAddPage', 'NoRight.gif', null, '28', '1', '设置', '35', null);
INSERT INTO `sys_module` VALUES ('3', null, 'NoRight.gif', '3', '1', '1', '系统管理', '8', null);
INSERT INTO `sys_module` VALUES ('30', '/ETOA/oa/meetingManager.do?method=toMain', 'NoRight.gif', null, '28', '1', '使用查询', '36', null);
INSERT INTO `sys_module` VALUES ('31', '/ETOA/oa/assissant/hr.do?method=toHrMain', 'NoRight.gif', '', '10', '1', '员工信息', '37', null);
INSERT INTO `sys_module` VALUES ('33', null, 'NoRight.gif', null, '27', '1', '车辆管理', '38', null);
INSERT INTO `sys_module` VALUES ('34', '/ETOA/oa/carManager.do?method=toAdd&operSign=first', 'NoRight.gif', null, '33', '1', '车辆登记', '39', null);
INSERT INTO `sys_module` VALUES ('35', '/ETOA/oa/carManager.do?method=toApplyPage&pageSign=first', 'NoRight.gif', null, '33', '1', '用车申请', '40', null);
INSERT INTO `sys_module` VALUES ('36', '/ETOA/oa/carManager.do?method=toCarList', 'NoRight.gif', null, '33', '1', '车辆查询', '45', null);
INSERT INTO `sys_module` VALUES ('38', '/ETOA/oa/meetingManager.do?method=toAdpplyPage', 'NoRight.gif', null, '28', '1', '申请', '46', null);
INSERT INTO `sys_module` VALUES ('39', '/ETOA/oa/carApprove.do?method=toApprovePage&sign=first', 'NoRight.gif', null, '33', '1', '车辆审批', '47', null);
INSERT INTO `sys_module` VALUES ('4', '/ETOA/sys/user/UserOper.do?method=toMain', 'NoRight.gif', '4', '3', '1', '用户管理', '48', null);
INSERT INTO `sys_module` VALUES ('40', '/ETOA/oa/meetingManager.do?method=roomList', 'NoRight.gif', null, '28', '1', '会议室查询', '49', null);
INSERT INTO `sys_module` VALUES ('41', '/ETOA/oa/file/fileManager.do?method=main', 'NoRight.gif', 'dsf', '1', '0', '文件管理中心', '6', null);
INSERT INTO `sys_module` VALUES ('5', '/ETOA/sys/module.do?method=loadTree', 'NoRight.gif', '5', '3', '1', '模块管理', '50', null);
INSERT INTO `sys_module` VALUES ('6', '/ETOA/sys/group/GroupOper.do?method=toMain', 'NoRight.gif', '6', '3', '1', '组管理', '51', null);
INSERT INTO `sys_module` VALUES ('7', '/ETOA/sys/role/Role.do?method=toRoleMain', 'NoRight.gif', '7', '3', '1', '角色管理', '52', null);
INSERT INTO `sys_module` VALUES ('8', '/ETOA/sys/log/LogOper.do?method=toMain', 'NoRight.gif', '8', '3', '1', '日志管理', '53', null);
INSERT INTO `sys_module` VALUES ('9', '/ETOA/sys/dep.do?method=loadTree', 'NoRight.gif', '911', '3', '1', '部门管理', '54', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000001', '', 'NoRight.gif', '', '1', '1', '个人办公', '1', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000002', '/ETOA/oa/privy/plan/plan.jsp', 'NoRight.gif', '', 'SYS_MODULE_0000000001', '0', '工作计划', '55', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000041', '', 'NoRight.gif', '会议管理', 'SYS_MODULE_0000000542', '1', '会议管理', '56', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000042', '', 'NoRight.gif', '资产管理', 'SYS_MODULE_0000000542', '1', '资产管理', '57', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000061', '', 'NoRight.gif', '邮件管理', '25', '1', '邮件管理', '58', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000081', '/ETOA/oa/assissant/asset/assetsOperAction.do?method=toMain', 'NoRight.gif', '资产信息管理', 'SYS_MODULE_0000000042', '1', '资产信息管理', '59', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000082', '/ETOA/oa/assissant/asset/assetsOperAction.do?method=toOperMain', 'NoRight.gif', '资产操作查询', 'SYS_MODULE_0000000042', '1', '资产操作查询', '60', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000101', '', 'NoRight.gif', '', 'SYS_MODULE_0000000201', '1', '新闻管理', '61', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000102', '/ETOA/news/opernews.do?method=toArticleMain', 'NoRight.gif', '', 'SYS_MODULE_0000000101', '1', '新闻添加', '62', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000103', '/ETOA/news/opernews.do?method=toRecycleList', 'NoRight.gif', '', 'SYS_MODULE_0000000101', '1', '回收站', '63', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000104', '/ETOA/oa/operaffiche.do?method=toaficheMain', 'NoRight.gif', '', 'SYS_MODULE_0000000201', '1', '公告管理', '64', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000121', '/ETOA/oa/assissant/conference/conferOper.do?method=toMain', 'NoRight.gif', '会议申请管理', 'SYS_MODULE_0000000041', '1', '会议申请管理', '65', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000181', '/ETOA/oa/communicate/email.do?method=toEmailMain', 'NoRight.gif', '内部邮件管理', 'SYS_MODULE_0000000061', '1', '内部邮件', '66', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000182', '/ETOA/oa/communicate/outemail.do?method=toEmailMain', 'NoRight.gif', '', 'SYS_MODULE_0000000061', '1', '外部邮件', '67', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000183', '/ETOA/oa/communicate/emailbox.do?method=toEmailBoxMain', 'NoRight.gif', '对于外部邮箱管理', 'SYS_MODULE_0000000061', '1', '邮箱管理', '68', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000201', '', 'NoRight.gif', '公共信息', '1', '1', '公共信息', '3', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000202', '', 'NoRight.gif', '图书管理', 'SYS_MODULE_0000000201', '1', '图书管理', '69', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000203', '/ETOA/oa/commoninfo/book.do?method=toBookMain', 'NoRight.gif', '图书入库', 'SYS_MODULE_0000000202', '1', '图书入库', '70', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000204', '/ETOA/oa/commoninfo/bookborrow.do?method=toBookBorrowMain', 'NoRight.gif', '图书借阅查询', 'SYS_MODULE_0000000202', '1', '图书借阅查询', '71', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000222', '/ETOA/oa/privy/addressList.do?method=toPersonalAddressListMain', 'NoRight.gif', '个人通讯录', 'SYS_MODULE_0000000001', '1', '个人通讯录', '72', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000223', '/ETOA/oa/privy/addressList.do?method=toCommonAddressListMain', 'NoRight.gif', '公共通讯录', 'SYS_MODULE_0000000001', '1', '公共通讯录', '73', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000241', '/ETOA/oa/privy/addressList.do?method=toAddressListMain', 'NoRight.gif', '公司通讯录', 'SYS_MODULE_0000000201', '1', '公司通讯录', '74', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000261', '/ETOA/sys/station/station.do?method=toStationMain', 'NoRight.gif', '岗位管理', '3', '1', '岗位管理', '75', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000281', '/ETOA/oa/commoninfo/leaveWord.do?method=toSeeLeaveWordMain', 'NoRight.gif', '留言板', 'SYS_MODULE_0000000201', '1', '留言板', '76', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000282', '/ETOA/oa/commoninfo/leaveWord.do?method=toLeaveWordMain', 'NoRight.gif', '留言板管理', 'SYS_MODULE_0000000201', '1', '留言板管理', '77', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000301', '/ETOA/sys/station/station.do?method=station', 'NoRight.gif', '部门岗位信息', 'SYS_MODULE_0000000201', '1', '部门岗位信息', '78', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000321', 'http://www.t7online.com/', 'NoRight.gif', '天气预报', 'SYS_MODULE_0000000201', '1', '天气预报', '25', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000322', 'http://www.post.com.cn/spec-serv/yzbm/yzbm.htm', 'NoRight.gif', '邮政编码查询', 'SYS_MODULE_0000000201', '1', '邮政编码', '24', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000341', 'http://train.chinamor.cn.net/cctt.asp', 'NoRight.gif', '火车查询', 'SYS_MODULE_0000000201', '1', '火车查询', '23', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000342', 'http://www.consumer.att.com/global/chinese/consumer_information/time.html', 'NoRight.gif', '国际时间查询', 'SYS_MODULE_0000000201', '1', '国际时间查询', '22', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000343', 'http://www.airchina.com.cn/', 'NoRight.gif', '航班查询', 'SYS_MODULE_0000000201', '1', '航班查询', '21', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000361', '/ETOA/oa/absenceWork.do?method=toAbsence&operType=qingjia', 'NoRight.gif', '请假登记', '20', '1', '请假登记', '20', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000362', '/ETOA/oa/absenceWork.do?method=toAbsence&operType=waichu', 'NoRight.gif', '外出登记', '20', '1', '外出登记', '19', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000363', '/ETOA/oa/absenceWork.do?method=toAbsence&operType=chuchai', 'NoRight.gif', '出差登记', '20', '1', '出差登记', '18', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000381', '/ETOA/oa/checkWork.do?method=toMain', 'NoRight.gif', '考勤查询', '20', '1', '考勤查询', '17', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000382', '/ETOA/oa/absenceWork.do?method=toMain', 'NoRight.gif', '缺勤查询', '20', '1', '缺勤查询', '16', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000542', '', 'NoRight.gif', '', '1', '1', '辅助办公', '4', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000543', '', 'NoRight.gif', '', '1', '0', '公文流转', '5', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000561', '/ETOA/oa/mainOper.do?method=toMain', 'NoRight.gif', '', '1', '1', '办公平台', '9', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000562', '/ETOA/oa/communicate/handsetnote.do?method=toHandsetNoteMain', 'NoRight.gif', '', '25', '1', '手机短信', '11', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000563', '/ETOA/oa/shortmessage/shortmessage.do?method=toMessageMain', 'NoRight.gif', '', '25', '1', '短消息', '12', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000564', '', 'NoRight.gif', '', '25', '1', '聊天室', '13', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000565', '', 'NoRight.gif', '', '25', '1', '论坛', '14', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000601', '/ETOA/sys/user/UserOper.do?method=toModifyPwd', 'NoRight.gif', '修改密码', 'SYS_MODULE_0000000001', '1', '修改密码', '15', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000661', '/ETOA/workplan.do?method=myplan', 'NoRight.gif', '', 'SYS_MODULE_0000000001', '1', '工作计划', 'SYS_MODULE_0000000661', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000681', '/ETOA/plan/plan.jsp', 'NoRight.gif', '', 'SYS_MODULE_0000000661', '1', '阶段工作计划', 'SYS_MODULE_0000000681', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000682', '/ETOA/plan/planday.jsp', 'NoRight.gif', '', 'SYS_MODULE_0000000661', '1', '详细工作计划', 'SYS_MODULE_0000000682', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000685', '/ETOA/plan/plancheck.jsp', 'NoRight.gif', '', 'SYS_MODULE_0000000661', '1', '计划审批', 'SYS_MODULE_0000000685', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000762', '', 'NoRight.gif', '', 'SYS_MODULE_0000000565', '1', '论坛后台管理', 'SYS_MODULE_0000000762', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000763', '/ETOA/forum/moduleManager.do?method=toModuleList', 'NoRight.gif', '', 'SYS_MODULE_0000000762', '1', '模块添加', 'SYS_MODULE_0000000763', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000764', '/ETOA/forum/userManager.do?method=toUserMain', 'NoRight.gif', '', 'SYS_MODULE_0000000762', '1', '用户管理', 'SYS_MODULE_0000000764', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000765', '/ETOA/forum/replace.do?method=toReplace', 'NoRight.gif', '', 'SYS_MODULE_0000000762', '1', '替换/限制处理', 'SYS_MODULE_0000000765', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000766', '/ETOA/forum/forumLog.do?method=toLogMain', 'NoRight.gif', '', 'SYS_MODULE_0000000762', '1', '论坛日志管理', 'SYS_MODULE_0000000766', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000767', '/ETOA/forum/forumList.do?method=toForumList&moduleId=1', 'NoRight.gif', '', 'SYS_MODULE_0000000565', '1', 'OA论坛', 'SYS_MODULE_0000000767', null);
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005096', 'SYS_GROUP_0000000141', 'SYS_MODULE_0000000767');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005097', 'SYS_GROUP_0000000141', 'SYS_MODULE_0000000762');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005098', 'SYS_GROUP_0000000141', 'SYS_MODULE_0000000565');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005099', 'SYS_GROUP_0000000141', 'SYS_MODULE_0000000766');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005100', 'SYS_GROUP_0000000141', 'SYS_MODULE_0000000763');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005101', 'SYS_GROUP_0000000141', '25');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005102', 'SYS_GROUP_0000000141', 'SYS_MODULE_0000000764');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005103', 'SYS_GROUP_0000000141', 'SYS_MODULE_0000000765');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005104', 'SYS_GROUP_0000000141', '1');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005185', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000767');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005186', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000762');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005187', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000661');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005188', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000202');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005189', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000342');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005190', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000341');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005191', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000565');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005192', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000001');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005193', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000601');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005194', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000766');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005195', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000241');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005196', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000682');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005197', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000763');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005198', 'SYS_GROUP_0000000101', '25');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005199', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000301');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005200', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000282');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005201', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000764');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005202', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000561');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005203', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000765');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005204', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000281');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005205', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000183');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005206', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000322');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005207', 'SYS_GROUP_0000000101', '1');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005208', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000101');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005209', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000104');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005210', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000204');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005211', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000103');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005212', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000061');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005213', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000203');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005214', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000343');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005215', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000321');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005216', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000681');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005217', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000102');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005218', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000182');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005220', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000201');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005221', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000223');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005222', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000181');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005223', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000222');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005241', 'administrator', 'SYS_MODULE_0000000082');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005242', 'administrator', 'SYS_MODULE_0000000767');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005243', 'administrator', 'SYS_MODULE_0000000762');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005244', 'administrator', 'SYS_MODULE_0000000661');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005245', 'administrator', '29');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005246', 'administrator', 'SYS_MODULE_0000000202');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005247', 'administrator', 'SYS_MODULE_0000000543');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005248', 'administrator', '39');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005249', 'administrator', 'SYS_MODULE_0000000342');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005250', 'administrator', '24');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005251', 'administrator', 'SYS_MODULE_0000000362');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005252', 'administrator', 'SYS_MODULE_0000000341');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005253', 'administrator', 'SYS_MODULE_0000000565');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005254', 'administrator', 'SYS_MODULE_0000000001');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005255', 'administrator', 'SYS_MODULE_0000000601');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005256', 'administrator', 'SYS_MODULE_0000000363');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005257', 'administrator', '34');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005258', 'administrator', 'SYS_MODULE_0000000766');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005259', 'administrator', 'SYS_MODULE_0000000542');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005260', 'administrator', 'SYS_MODULE_0000000081');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005261', 'administrator', '30');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005262', 'administrator', '18');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005263', 'administrator', 'SYS_MODULE_0000000241');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005264', 'administrator', '20');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005265', 'administrator', 'SYS_MODULE_0000000682');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005266', 'administrator', 'SYS_MODULE_0000000763');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005267', 'administrator', '25');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005268', 'administrator', 'SYS_MODULE_0000000301');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005269', 'administrator', 'SYS_MODULE_0000000282');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005270', 'administrator', 'SYS_MODULE_0000000764');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005271', 'administrator', 'SYS_MODULE_0000000685');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005272', 'administrator', '35');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005273', 'administrator', 'SYS_MODULE_0000000561');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005274', 'administrator', '9');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005275', 'administrator', 'SYS_MODULE_0000000765');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005276', 'administrator', '19');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005277', 'administrator', 'SYS_MODULE_0000000281');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005278', 'administrator', 'SYS_MODULE_0000000183');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005279', 'administrator', '28');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005280', 'administrator', 'SYS_MODULE_0000000041');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005281', 'administrator', '3');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005282', 'administrator', '41');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005283', 'administrator', 'SYS_MODULE_0000000322');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005284', 'administrator', '27');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005285', 'administrator', 'SYS_MODULE_0000000563');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005286', 'administrator', '17');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005287', 'administrator', '6');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005288', 'administrator', 'SYS_MODULE_0000000382');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005289', 'administrator', '36');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005290', 'administrator', '1');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005291', 'administrator', 'SYS_MODULE_0000000101');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005292', 'administrator', 'SYS_MODULE_0000000104');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005293', 'administrator', '5');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005294', 'administrator', '16');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005295', 'administrator', 'SYS_MODULE_0000000361');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005296', 'administrator', '33');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005297', 'administrator', 'SYS_MODULE_0000000002');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005298', 'administrator', 'SYS_MODULE_0000000204');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005299', 'administrator', '4');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005300', 'administrator', '40');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005301', 'administrator', 'SYS_MODULE_0000000103');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005302', 'administrator', 'SYS_MODULE_0000000061');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005303', 'administrator', 'SYS_MODULE_0000000261');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005304', 'administrator', 'SYS_MODULE_0000000203');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005305', 'administrator', 'SYS_MODULE_0000000343');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005306', 'administrator', 'SYS_MODULE_0000000321');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005307', 'administrator', 'SYS_MODULE_0000000381');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005308', 'administrator', 'SYS_MODULE_0000000681');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005309', 'administrator', 'SYS_MODULE_0000000102');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005310', 'administrator', '7');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005311', 'administrator', '38');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005312', 'administrator', '31');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005313', 'administrator', 'SYS_MODULE_0000000182');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005314', 'administrator', '10');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005315', 'administrator', 'SYS_MODULE_0000000201');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005316', 'administrator', 'SYS_MODULE_0000000121');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005317', 'administrator', 'SYS_MODULE_0000000223');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005318', 'administrator', 'SYS_MODULE_0000000181');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005319', 'administrator', '8');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005320', 'administrator', 'SYS_MODULE_0000000042');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000005321', 'administrator', 'SYS_MODULE_0000000222');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003021', 'zhaoyifei', '25');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003022', 'zhaoyifei', 'SYS_MODULE_0000000562');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003023', 'zhaoyifei', '1');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003661', 'jingyuzhuo', 'SYS_MODULE_0000000661');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003662', 'jingyuzhuo', 'SYS_MODULE_0000000001');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003663', 'jingyuzhuo', 'SYS_MODULE_0000000682');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003664', 'jingyuzhuo', 'SYS_MODULE_0000000685');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003665', 'jingyuzhuo', '1');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003666', 'jingyuzhuo', 'SYS_MODULE_0000000681');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000004074', 'guxiaofeng', 'SYS_MODULE_0000000661');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000004076', 'guxiaofeng', 'SYS_MODULE_0000000001');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000004078', 'guxiaofeng', 'SYS_MODULE_0000000685');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000004080', 'guxiaofeng', '1');
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
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000243', 'SYS_TREE_0000000242', '', 'planTimeMother', '月计划', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000244', 'SYS_TREE_0000000242', '', 'planTimeWeek', '周计划', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000245', 'SYS_TREE_0000000242', '', 'planTimePhase', '阶段计划', null, 'idu', '1', '', '', '0', null);
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
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000341', '1', '', '', 'common', null, 'idu', '1', '', '公共信息', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000342', '1', '', 'plan', '计划审批', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000361', 'SYS_TREE_0000000342', '', 'plan_auditing', '查询类型', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000401', 'SYS_TREE_0000000361', '', 'plan_auditing_no', '未审核', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000402', 'SYS_TREE_0000000361', '', 'plan_auditing_yes', '审核并发布', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000403', 'SYS_TREE_0000000361', '', 'plan_auditing_not', '已审核', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000421', 'SYS_TREE_0000000361', '', 'plan_auditing_del', '删除标记', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000441', '1', '', 'forum_name', '卓越科技论坛系统', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000442', 'SYS_TREE_0000000441', '', '', '积分管理', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000443', 'SYS_TREE_0000000442', '', '', '发帖加分', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000444', 'SYS_TREE_0000000443', '', 'forum_point_addSendPoint', '5', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000445', 'SYS_TREE_0000000442', '', '', '回帖加分', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000446', 'SYS_TREE_0000000445', '', 'forum_point_addAnswerPoint', '1', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000447', 'SYS_TREE_0000000442', '', '', '加精加分', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000448', 'SYS_TREE_0000000447', '', 'forum_point_joinPrimePoint', '50', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000449', 'SYS_TREE_0000000442', '', '', '置顶加分', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000450', 'SYS_TREE_0000000449', '', 'forum_piont_putTopPoint', '100', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000451', 'SYS_TREE_0000000442', '', '', '删贴扣分', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000452', 'SYS_TREE_0000000451', '', 'forum_piont_cutDeletePostPoint', '5', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000453', 'SYS_TREE_0000000441', '', '', '论坛类型', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000454', 'SYS_TREE_0000000453', '', '0', '父论坛', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000455', 'SYS_TREE_0000000453', '', '1', '子论坛', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000461', 'SYS_TREE_0000000441', '', '', '常用查询类型定义', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000462', 'SYS_TREE_0000000461', '', '', '最新帖子', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000463', 'SYS_TREE_0000000462', '', 'forum_query_bestNewPost', '1', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000464', 'SYS_TREE_0000000461', '', '', '版主推荐', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000465', 'SYS_TREE_0000000464', '', 'forum_query_forumHostGroom', '2', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000466', 'SYS_TREE_0000000461', '', '', '网友推荐', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000467', 'SYS_TREE_0000000466', '', 'forum_query_netfriendGroom', '3', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000468', 'SYS_TREE_0000000461', '', '', '回复十大', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000469', 'SYS_TREE_0000000468', '', 'forum_query_answerTopTen', '4', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000470', 'SYS_TREE_0000000461', '', '', '发帖排行', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000471', 'SYS_TREE_0000000470', '', 'forum_query_sendPostRank', '5', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000472', 'SYS_TREE_0000000461', '', '', '积分排行', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000473', 'SYS_TREE_0000000472', '', 'forum_query_pointRand', '6', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000474', 'SYS_TREE_0000000441', '', 'date_type', '搜索时间类型', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000475', 'SYS_TREE_0000000474', '', 'day', '一天内的帖子', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000476', 'SYS_TREE_0000000474', '', 'week', '一个星期内的帖子', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000477', 'SYS_TREE_0000000474', '', 'month', '一个月内的帖子', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000478', 'SYS_TREE_0000000474', '', 'twoMonth', '两个月内的帖子', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000479', 'SYS_TREE_0000000474', '', 'threeMonth', '三个月内的帖子', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000480', 'SYS_TREE_0000000441', '', 'log_oper_area', '日志操作区域', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000481', 'SYS_TREE_0000000480', '', 'forum_log', '论坛日志管理', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000501', '1', '', 'finsh_state', '完成状态', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000502', 'SYS_TREE_0000000501', '', 'finsh', '完成', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000503', 'SYS_TREE_0000000501', '', 'no_begin', '未开始', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000504', 'SYS_TREE_0000000501', '', 'going', '进行中', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000505', 'SYS_TREE_0000000501', '', 'not_finsh', '不能完成', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_user` VALUES ('chenyiying', '58311CD2040A696B4F9C68C7748D9430', '陈轶英', 'SYS_GROUP_0000000101', 'SYS_ROLE_0000000181', '3', '1', '', '0');
INSERT INTO `sys_user` VALUES ('forum', 'BBDBE444288550204C968FE7002A97A9', 'forum', 'SYS_GROUP_0000000141', 'SYS_ROLE_0000000181', '1', '1', '', '0');
INSERT INTO `sys_user` VALUES ('guxiaofeng', '49F68A5C8493EC2C0BF489821C21FC3B', '辜晓峰', 'administrator', 'administrator', '1', '1', '', '0');
INSERT INTO `sys_user` VALUES ('jingyuzhuo', '226EE5D680B8EDC3A19F21876142EA4B', '荆玉琢', 'SYS_GROUP_0000000101', 'SYS_ROLE_0000000181', '2', '1', '', '0');
INSERT INTO `sys_user` VALUES ('liuyang', '6B649039A388694C7306CB4D14041A14', '刘洋', 'SYS_GROUP_0000000101', 'SYS_ROLE_0000000181', '3', '0', '', '0');
INSERT INTO `sys_user` VALUES ('yangshuo', '6A3128EA1839E0402F4349A252845499', '杨朔', 'SYS_GROUP_0000000101', 'SYS_ROLE_0000000181', '1', '1', '', '0');
INSERT INTO `sys_user` VALUES ('yepuliang', 'C4CA4238A0B923820DCC509A6F75849B', '叶浦亮', 'SYS_GROUP_0000000101', 'SYS_ROLE_0000000181', '2', '1', '', '0');
INSERT INTO `sys_user` VALUES ('zhangfeng', '71CA0B537DD369350A996EBAFB959027', '张峰', 'SYS_GROUP_0000000101', 'SYS_ROLE_0000000181', '2', '1', '', '0');
INSERT INTO `sys_user` VALUES ('zhaoyifei', 'BA4C2F175F0DBA2F2974E676C6DFBBAB', '赵一非', 'administrator', 'administrator', '2', '1', '1', '1');
INSERT INTO `sys_user_info` VALUES ('chenyiying', '', '1', '', '', null, '2006-11-17', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('forum', '', '1', '', '', null, '2007-03-10', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('guxiaofeng', '', '1', '', '', null, '2006-11-17', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('jingyuzhuo', '', '1', '', '', null, '2006-11-17', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('liuyang', '', '1', '', '', null, '2006-11-17', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('yangshuo', '', '1', '', '', null, '2006-12-20', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('yepuliang', '', '1', '', '', null, '2006-11-17', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('zhangfeng', '', '1', '', '', null, '2006-11-17', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `sys_user_info` VALUES ('zhaoyifei', '', '', '', '', null, '2006-11-17', '', '', null, '', '', '', '', '', '', '', '', '', null);
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000000941', '', 'zhangfeng', '', '2006-11-30 09:37:59', '2006-11-28 00:00:00', '2006-12-04 00:00:00', '论坛制作', 'zhangfeng', 'SYS_TREE_0000000244', '', '论坛', '论坛制作', '', '1', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000000981', '', 'yepuliang', '', '2006-12-04 08:39:23', '2006-12-04 00:00:00', '2006-12-05 00:00:00', '卓越论坛计划', 'yepuliang', 'SYS_TREE_0000000244', '', '论坛前台', '完成论坛前台常用操作，包括模块列表，帖子管理，权限操作，常用查询等核心模块。', '', '0', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000000982', '', 'zhaoyifei', '', '2006-12-08 17:05:57', '2006-12-08 00:00:00', '2006-12-31 00:00:00', '12月份工作计划', 'zhaoyifei', 'SYS_TREE_0000000243', '', '12月', '12月份工作计划', '12月份工作计划', '1', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001002', '', 'zhangfeng', '', '2006-12-11 08:38:42', '2006-12-11 00:00:00', '2006-12-16 00:00:00', '12.11日周工作计划', 'zhangfeng', 'SYS_TREE_0000000244', '', '', '论坛工作继续，市局项目添加及修改，ETNews新闻后台移植', '', '2', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001003', '', 'yepuliang', '', '2006-12-12 09:45:23', '2006-12-11 00:00:00', '2006-12-15 00:00:00', '论坛后台计划', 'yepuliang', 'SYS_TREE_0000000244', '', '论坛后台', '论坛后台的制作', '', '2', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001004', '', 'zhangfeng', '', '2006-12-18 09:57:43', '2006-12-18 00:00:00', '2006-12-23 00:00:00', '论坛制作', 'zhangfeng', 'SYS_TREE_0000000244', '', '', '继续完成论坛未完成的工作', '', '2', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001005', '', 'yepuliang', '', '2006-12-18 10:16:50', '2006-12-18 00:00:00', '2006-12-22 00:00:00', '论坛后台制作', 'yepuliang', 'SYS_TREE_0000000244', '', '模块管理', '', '', '2', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001006', '', 'jingyuzhuo', '', '2006-12-18 10:23:29', '2006-12-11 00:00:00', '2006-12-25 00:00:00', 'crm客户管理系统', 'jingyuzhuo', 'SYS_TREE_0000000243', '', 'crm', '完成客户管理系统的其中\r\n客户的模块', '', '0', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001021', '', 'yepuliang', '', '2006-12-20 11:01:04', '2006-12-25 00:00:00', '2006-12-29 00:00:00', '后台模块时间估算', 'yepuliang', 'SYS_TREE_0000000243', '', '后台', '后台时间估算：\r\n\r\n1，用户管理：包括（核心功能用户列表，用户搜索）估算大约一天。\r\n\r\n2.替换限制处理：（核心功能：帖子词语过滤，保留敏感用户名，屏蔽用户言论）大约1天半\r\n\r\n3.论坛备份：（全部备份：包括全部论坛数据表数据。标准备份：包括常用的数据表数据。\r\n最小备份：仅包括用户、版块设置及系统设置数据。\r\n自定义备份：根据需要自行选择需要备份的数据表\r\n）半天到1天\r\n\r\n4.日志操作\r\n\r\n密码错误日志，用户评分日志，积分交易日志，版主管理日志，禁止用户日志，后台访问记录，系统错误记录。系统日志\r\n用户日志，版主日志\r\n模块日志，论坛安全日志\r\n积分操作日志（估计1天到3天具体情况定）\r\n\r\n5.扩展功能：email群发，短消息群发，计划任务。（1天到2天）', '', '0', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001022', '', 'yepuliang', '', '2006-12-20 16:19:15', '2006-12-20 00:00:00', '2006-12-22 00:00:00', '论坛前台收尾', '', 'SYS_TREE_0000000244', '', '论坛前台收尾', '1.查看新帖，发帖排行，搜索\r\n（周四，周五）\r\n\r\n2.完成联系人管理', '发帖排行和搜索已完成\r\n\r\n联系人管理已完成', '1', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001041', '', 'yepuliang', '', '2006-12-25 08:53:31', '2006-12-25 00:00:00', '2006-12-29 00:00:00', '开始着手后台', 'yepuliang', 'SYS_TREE_0000000243', '', '', '1，用户管理：包括（核心功能用户列表，用户搜索）1天\r\n\r\n2.替换限制处理：（核心功能：帖子词语过滤，保留敏感用户名，屏蔽用户言论）1天半\r\n\r\n3.论坛备份：（全部备份：包括全部论坛数据表数据。标准备份：包括常用的数据表数据。\r\n最小备份：仅包括用户、版块设置及系统设置数据。\r\n自定义备份：根据需要自行选择需要备份的数据表\r\n）1天半', '', '2', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001061', '', 'jingyuzhuo', '', '2006-12-26 09:35:24', '2006-12-26 00:00:00', '2006-12-31 00:00:00', 'CRM软件开发', '', 'SYS_TREE_0000000244', '', 'CRM', '1.OK\r\n2.NO\r\n3.OK\r\n4.OK\r\n', '1.OK\r\n2.NO\r\n3.OK\r\n4.OK\r\n', '1', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001381', '', 'guxiaofeng', '', '2007-01-30 08:48:28', '2007-01-30 00:00:00', '2007-01-30 00:00:00', 'guxf', 'guxiaofeng', 'SYS_TREE_0000000243', '', 'guxf', '', '', 'SYS_TREE_0000000403', '1', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001383', '', 'zhaoyifei', '', '2007-01-30 09:00:53', '2007-01-30 00:00:00', '2007-02-02 00:00:00', '20070129周工作计划', '', 'SYS_TREE_0000000244', '', '', '主要工作完成交换机程序。', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001384', '', 'yepuliang', '', '2007-01-30 09:04:55', '2007-01-29 00:00:00', '2007-02-02 00:00:00', '新闻样式管理', 'yepuliang', 'SYS_TREE_0000000245', '', '样式', '', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001385', '', 'zhangfeng', '', '2007-01-30 09:05:36', '2007-01-29 00:00:00', '2007-02-02 00:00:00', '计费管理软件制作', 'zhangfeng', 'SYS_TREE_0000000244', '', '', '计费管理软件制作,完成费率计算', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001386', '', 'jingyuzhuo', '', '2007-01-30 09:37:52', '2007-01-30 00:00:00', '2007-02-02 00:00:00', '2007年第五周工作计划', '', 'SYS_TREE_0000000244', '', '第五周工作计划', '完成用户注册的模块', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001387', '', 'guxiaofeng', '', '2007-01-30 09:51:59', '2007-01-29 00:00:00', '2007-02-02 00:00:00', '20070129周计划', '', 'SYS_TREE_0000000244', '', '', '规章制度、价格体系、\r\n公司电邮、公司程序规划', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001388', '', 'guxiaofeng', '', '2007-01-30 10:47:38', '2007-01-01 00:00:00', '2007-06-30 00:00:00', '2007上半年计划1(项目、产品)', '', 'SYS_TREE_0000000245', '', '项目、产品计划', '项目、产品计划', '', 'SYS_TREE_0000000401', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001389', '', 'guxiaofeng', '', '2007-01-30 13:23:33', '2007-01-01 00:00:00', '2007-06-30 00:00:00', '2007上半年计划2(项目、产品)', 'guxiaofeng', 'SYS_TREE_0000000243', '', '', '', '', 'SYS_TREE_0000000401', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001390', '', 'guxiaofeng', '', '2007-01-30 13:48:44', '2007-01-29 00:00:00', '2007-02-16 00:00:00', '近期计划（春节前）', '', 'SYS_TREE_0000000245', '', '', '春节前一些计划。', '', 'SYS_TREE_0000000401', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001391', '', 'guxiaofeng', '', '2007-02-01 10:00:42', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '测试', '', 'SYS_TREE_0000000243', '', '啊', '暗暗暗暗暗暗暗暗暗暗啊', '天天通天屯他', 'SYS_TREE_0000000401', '1', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001392', '', 'zhangfeng', '', '2007-02-02 14:22:02', '2007-02-05 00:00:00', '2007-02-09 00:00:00', '20070205定制费率计划，开始做费率计算', '', 'SYS_TREE_0000000244', '', '', '定制费率计划，开始做费率计算，完善类结构', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001393', '', 'jingyuzhuo', '', '2007-02-02 14:24:50', '2007-02-05 00:00:00', '2007-02-09 00:00:00', '20070205周计划', 'jingyuzhuo', 'SYS_TREE_0000000244', '', '注册模块', '完成注册模块', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001394', '', 'yepuliang', '', '2007-02-02 14:26:58', '2007-02-05 00:00:00', '2007-02-09 00:00:00', '20070205周计划', 'yepuliang', 'SYS_TREE_0000000244', '', '20070205周计划', '1.新闻样式的收尾\r\n2.准备做手机短信', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001395', '', 'zhaoyifei', '', '2007-02-02 16:14:48', '2007-02-05 00:00:00', '2007-02-09 00:00:00', '20070205周工作计划', 'zhaoyifei', 'SYS_TREE_0000000244', '', '', '呼叫中心继续', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001396', '', 'zhaoyifei', '', '2007-02-09 09:28:13', '2007-02-09 00:00:00', '2007-02-16 00:00:00', 'zhaoyifei', 'zhaoyifei', 'SYS_TREE_0000000243', '', '', 'aa', '', 'SYS_TREE_0000000401', '1', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001397', '', 'zhangfeng', '', '2007-02-12 10:37:34', '2007-02-12 00:00:00', '2007-02-15 00:00:00', '20070212', 'zhangfeng', 'SYS_TREE_0000000244', '', '', '继续做费率管理，处理类等', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001481', '', 'jingyuzhuo', '', '2007-02-12 12:53:33', '2007-02-12 00:00:00', '2007-02-13 00:00:00', 'jingyuzhuoshiyan', 'jingyuzhuo', 'SYS_TREE_0000000243', '', 'jingyuzhuoshiyan', 'jingyuzhuoshiyan', 'jingyuzhuoshiyan', 'SYS_TREE_0000000401', '1', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001482', '', 'chenyiying', '', '2007-02-12 13:05:41', '2007-02-12 00:00:00', '2007-03-02 00:00:00', '索弗网站', 'chenyiying', 'SYS_TREE_0000000243', '', '', '准备资料，待转为动态 网页，并设为模板', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001483', '', 'yepuliang', '', '2007-02-12 13:27:29', '2007-02-12 00:00:00', '2007-02-15 00:00:00', '20070212周计划', 'yepuliang', 'SYS_TREE_0000000244', '', '', '', '', 'SYS_TREE_0000000401', '0', '');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001484', '', 'yepuliang', '', '2007-02-27 09:07:52', '2007-02-27 00:00:00', '2007-03-02 00:00:00', '20070227周计划', 'yepuliang', 'SYS_TREE_0000000244', '', '', '', '', 'SYS_TREE_0000000403', '0', '');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001485', '', 'zhangfeng', '', '2007-02-28 08:59:58', '2007-02-26 00:00:00', '2007-03-02 00:00:00', '费率制作管理', 'zhangfeng', 'SYS_TREE_0000000244', '', '', '费率管理完善', '', 'SYS_TREE_0000000403', '0', '');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001486', '', 'zhangfeng', '', '2007-03-02 16:27:31', '2007-03-05 00:00:00', '2007-03-09 00:00:00', '20070305', 'zhangfeng', 'SYS_TREE_0000000244', '', '', '费率测试及完成', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001488', '', 'jingyuzhuo', '', '2007-03-02 16:39:32', '2007-03-05 00:00:00', '2007-03-09 00:00:00', '20070302', 'jingyuzhuo', 'SYS_TREE_0000000244', '', '用户注册', '完善并且测试用户注册模块', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001489', '', 'yepuliang', '', '2007-03-06 15:32:52', '2007-03-05 00:00:00', '2007-03-09 00:00:00', '20070305周计划', 'yepuliang', 'SYS_TREE_0000000244', '', '', '', '', 'SYS_TREE_0000000401', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001490', '', 'zhaoyifei', '', '2007-03-08 16:07:11', '2007-03-05 00:00:00', '2007-03-10 00:00:00', '20070305周工作计划', 'zhaoyifei', 'SYS_TREE_0000000244', '', '语音信箱、呼叫中心、oa', '语音信箱收尾，语音信箱文档，呼叫中心开始，oa文档整理', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001491', '', 'chenyiying', '', '2007-03-08 16:15:02', '2007-03-08 00:00:00', '2007-03-17 00:00:00', 'OA网页设计', 'chenyiying', 'SYS_TREE_0000000243', '', 'OA', '设计PHOTOSHO图', '', 'SYS_TREE_0000000403', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001501', '', 'zhaoyifei', '', '2007-03-10 16:35:48', '2007-03-12 00:00:00', '2007-03-16 00:00:00', '20070312周工作计划', 'zhaoyifei', 'SYS_TREE_0000000244', '', 'oa文档、语音信箱、文档', 'oa文档完成，语音信箱完成，文档完成', '', 'SYS_TREE_0000000402', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001502', '', 'yepuliang', '', '2007-03-10 16:51:08', '2007-03-12 00:00:00', '2007-03-16 00:00:00', '20070312周计划', 'yepuliang', 'SYS_TREE_0000000244', '', '', '', '', 'SYS_TREE_0000000402', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001503', '', 'zhangfeng', '', '2007-03-10 16:51:58', '2007-03-12 00:00:00', '2007-03-16 00:00:00', '20070312', 'zhangfeng', 'SYS_TREE_0000000243', '', '', '计费系统测试,OA短消息，电子邮件重构', '', 'SYS_TREE_0000000402', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001504', '', 'jingyuzhuo', '', '2007-03-10 16:52:28', '2007-03-12 00:00:00', '2007-03-16 00:00:00', '20070312', 'jingyuzhuo', 'SYS_TREE_0000000244', '', '测试', '测试用户注册模块', '', 'SYS_TREE_0000000402', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001505', '', 'chenyiying', '', '2007-03-10 17:09:17', '2007-03-10 00:00:00', '2007-03-17 00:00:00', 'OA设计', 'chenyiying', 'SYS_TREE_0000000243', '', 'OA设计', '1.OA设计\r\n2.准备网站模板', '1.OA设计\r\n2.准备网站模板', 'SYS_TREE_0000000402', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001506', '', 'guxiaofeng', '', '2007-03-12 07:55:02', '2007-03-12 00:00:00', '2007-03-18 00:00:00', '20070312周计划', 'guxiaofeng', 'SYS_TREE_0000000243', '', '', '', '', 'SYS_TREE_0000000402', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001521', '', 'jingyuzhuo', '', '2007-03-15 16:52:15', '2007-03-15 00:00:00', '2007-03-15 00:00:00', 'shiyan', 'jingyuzhuo', 'SYS_TREE_0000000244', '', 'shiyan', 'shiyan', 'shiyan', 'SYS_TREE_0000000401', '1', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001541', '', 'zhaoyifei', '', '2007-03-16 15:49:29', '2007-03-19 00:00:00', '2007-03-23 00:00:00', '语音信箱、文档，呼叫中心', 'zhaoyifei', 'SYS_TREE_0000000244', '', '呼叫中心、语音信箱', '继续完成语音信箱，呼叫中心方案', '', 'SYS_TREE_0000000401', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001542', '', 'zhangfeng', '', '2007-03-16 15:50:19', '2007-03-19 00:00:00', '2007-03-23 00:00:00', '20070319', 'zhangfeng', 'SYS_TREE_0000000243', '', '', 'OA短消息制作，邮件草稿箱修改，计费软件问题修改', '', 'SYS_TREE_0000000401', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001543', '', 'yepuliang', '', '2007-03-16 17:15:22', '2007-03-19 00:00:00', '2007-03-23 00:00:00', '20070319周计划', 'yepuliang', 'SYS_TREE_0000000244', '', '', '', '', 'SYS_TREE_0000000401', '0', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001561', '', 'jingyuzhuo', '', '2007-03-22 14:42:00', '2007-03-19 00:00:00', '2007-03-23 00:00:00', '20070319', 'jingyuzhuo', 'SYS_TREE_0000000243', '', '用户注册修改', '用户注册修改', '', 'SYS_TREE_0000000401', '0', '1');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000741', '帖子操作', 'WORK_PLAN_INFO_0000000941', '2006-11-30 09:39:06', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000941', 'zhangfeng', '2006-11-28 00:00:00', '2006-11-30 00:00:00', '', '', '帖子操作接口的实现与完成<2006-12-1 15:11>\r\n发帖<2006-12-1 15:11>\r\n回帖<2006-12-1 15:11>\r\n结帖<2006-12-1 15:11>\r\n推荐订阅<2006-12-1 15:11>\r\n收藏<2006-12-1 15:11>\r\n编缉<2006-12-1 15:11>\r\n引用<2006-12-1 15:11>\r\n报告<2006-12-1 15:11>\r\n页面制作<2006-12-1 15:12>', '3', '发帖完成<2006-12-5 10:32>\r\n回帖完成<2006-12-5 10:33>\r\n收藏完成<2006-12-5 10:33>', '2006-12-05 10:33:28', '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000742', '权限操作接口', 'WORK_PLAN_INFO_0000000941', '2006-11-30 09:39:59', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000941', 'zhangfeng', '2006-11-30 00:00:00', '2006-12-03 00:00:00', '', '', '加入精华权限操作接口的实现与完成<2006-12-1 15:12>\r\n删除帖子<2006-12-1 15:12>\r\n移动帖子<2006-12-1 15:12>\r\n高亮显示<2006-12-1 15:12>\r\n置顶<2006-12-1 15:12>\r\n<2006-12-6 14:8>', '3', '', '2006-12-06 14:08:29', '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000743', '用户接口', 'WORK_PLAN_INFO_0000000941', '2006-11-30 09:40:41', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000941', 'zhangfeng', '2006-11-03 00:00:00', '2006-11-06 00:00:00', '', '', '用户接口的操作与完成<2006-12-1 15:13>\r\n包括用户登录<2006-12-1 15:13>\r\n用户操作等信息<2006-12-1 15:13>\r\n包括登陆页<2006-12-1 15:13>\r\n用户显示信息<2006-12-1 15:13>', '3', '用户登陆完成<2006-12-5 10:34>\r\n登陆页完成<2006-12-5 10:34>', '2006-12-05 10:34:19', '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000744', '周二', null, '2006-11-30 09:44:01', '', 'SYS_TREE_0000000281', null, 'yepuliang', '2006-11-30 00:00:00', '2006-11-30 00:00:00', '', '', '完成论坛常用查询', '3', '', '2006-11-30 09:44:16', '一天够呛', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000745', '周二', null, '2006-11-30 09:44:21', '', 'SYS_TREE_0000000281', null, 'yepuliang', '2006-11-30 00:00:00', '2006-11-30 00:00:00', '', '', '完成论坛常用查询', '2', '', null, '一天够呛', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000746', '整理投标资料', null, '2006-11-30 09:44:53', '投标资料', 'SYS_TREE_0000000281', null, '', '2006-11-30 00:00:00', '2006-12-05 00:00:00', '', '', '1/整理投标资料（5项）\r\n2/做\r\n做投标资料空白书', '2', '', '2007-01-30 10:37:41', '', '', 'chenyiying', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000748', '整理标投资料', null, '2006-11-30 09:51:45', '整理资料', 'SYS_TREE_0000000281', null, '', '2006-11-30 00:00:00', '2006-12-05 00:00:00', '', '', '1/整理投标资料5大类\r\n2/复印相关证件\r\n3/整理空白投标文件\r\n\r\n7777<2007-1-15 10:12>', '2', '', '2007-01-30 10:40:18', '', '', 'chenyiying', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000750', '整理投标资料', null, '2006-11-30 09:55:26', '投标资料', 'SYS_TREE_0000000281', null, 'chenyiying', '2006-11-30 00:00:00', '2006-12-05 00:00:00', '', '', '1/整理填写投标资料\r\n2/复印相关证件\r\n3/做空白投标文件', '2', '', null, '', '', 'chenyiying', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000752', '论坛', null, '2006-11-30 10:08:43', '论坛', 'SYS_TREE_0000000281', null, 'chenyiying', '2006-12-06 00:00:00', '2006-12-31 00:00:00', '', '', '1/论坛平面', '2', '', null, '', '', 'chenyiying', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000801', '程序修改，套页面', 'WORK_PLAN_INFO_0000000941', '2006-12-01 15:09:57', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000941', 'yepuliang,zhangfeng', '2006-12-03 00:00:00', '2006-12-07 00:00:00', '', '', '包括楼号<2006-12-1 15:10>\r\n首页显示登陆列表信息<2006-12-1 15:10>\r\n权限显示<2006-12-1 15:10>\r\n列表页面信息细节的补充<2006-12-1 15:10>', '3', '楼号添加已完成<2006-12-5 10:32>', '2006-12-05 10:32:14', '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000821', '周一', 'WORK_PLAN_INFO_0000000981', '2006-12-04 08:41:49', '论坛前台', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000981', 'yepuliang', '2006-12-04 00:00:00', '2006-12-08 00:00:00', '', '', '1.完成常用查询<2006-12-4 8:41>\r\n2.配合张锋完成帖子列表<2006-12-4 8:41>\r\n3.测试上周完成的程序<2006-12-4 8:42>\r\n<2006-12-4 12:5>\r\n<2006-12-4 12:5>\r\naaaaaaa<2006-12-6 9:0>', '1', '', '2006-12-12 09:43:36', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000822', '周二', 'WORK_PLAN_INFO_0000000981', '2006-12-04 08:44:01', '论坛前台', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000981', 'yepuliang', '2006-12-05 00:00:00', '2006-12-05 00:00:00', '', '', '1.完成部分辅助功能<2006-12-4 8:43>\r\n2.实现各个模块整合<2006-12-4 8:43>\r\n3.查找遗漏点<2006-12-4 8:44>\r\n4.测试<2006-12-4 8:44>', '1', '', '2006-12-12 09:43:44', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000823', '周三', 'WORK_PLAN_INFO_0000000981', '2006-12-04 08:46:24', '论坛后台', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000981', 'yepuliang', '2006-12-06 00:00:00', '2006-12-08 00:00:00', '', '', '1.开始论坛后台程序的编写<2006-12-4 8:45>\r\n2.首先完成模块管理<2006-12-4 8:45>\r\n3.完成人员管理<2006-12-4 8:46>\r\n4.实现与前台的更好结合<2006-12-4 8:46>', '1', '', '2006-12-12 09:43:50', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000824', '周四', 'WORK_PLAN_INFO_0000000981', '2006-12-04 08:50:41', '论坛后台', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000981', 'yepuliang', '2006-12-07 00:00:00', '2006-12-07 00:00:00', '', '', '论坛后台模块管理<2006-12-4 8:51>', '1', '', '2006-12-12 09:43:55', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000825', '周五', 'WORK_PLAN_INFO_0000000981', '2006-12-04 08:51:58', '论坛后台', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000981', 'yepuliang', '2006-12-08 00:00:00', '2006-12-08 00:00:00', '', '', '实现后台用户管理<2006-12-4 8:52>', '1', '', '2006-12-12 09:44:00', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000826', '第二周工作计划', 'WORK_PLAN_INFO_0000000982', '2006-12-08 17:06:40', '第二周', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000982', 'zhaoyifei', '2006-12-11 00:00:00', '2006-12-15 00:00:00', '', '', '第二周<2006-12-8 17:6>', '2', '', '2006-12-08 17:07:00', '', '', 'zhaoyifei', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000827', '第三周工作计划', 'WORK_PLAN_INFO_0000000982', '2006-12-08 17:08:03', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000982', 'zhaoyifei', '2006-12-18 00:00:00', '2006-12-22 00:00:00', '', '', '第三周<2006-12-8 17:8>', '2', '', null, '', '', 'zhaoyifei', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000828', '第四周工作计划', 'WORK_PLAN_INFO_0000000982', '2006-12-08 17:08:41', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000982', 'zhaoyifei', '2006-12-25 00:00:00', '2006-12-29 00:00:00', '', '', '第四周<2006-12-8 17:8>\r\n<2006-12-8 17:10>', '2', '', '2006-12-08 17:10:38', '', '', 'zhaoyifei', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000862', '公安项目添加程序', 'WORK_PLAN_INFO_0000001002', '2006-12-11 09:04:54', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001002', 'zhangfeng', '2006-12-11 00:00:00', '2006-12-11 00:00:00', '', '', '公安项目未完成添加程序，包括查询列表，警员注册信息查询，上下列表添加<2006-12-11 8:42>', '2', '', null, '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000863', '后台模块管理', 'WORK_PLAN_INFO_0000001003', '2006-12-12 09:46:45', '模块管理', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001003', 'yepuliang', '2006-12-12 00:00:00', '2006-12-12 00:00:00', '', '', '1。对论坛模块的增加，修改操作<2006-12-12 9:47>', '1', '', '2006-12-26 15:31:48', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000864', '论坛页面完善', 'WORK_PLAN_INFO_0000001004', '2006-12-18 10:00:07', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001004', 'yepuliang,zhangfeng', '2006-12-18 00:00:00', '2006-12-23 00:00:00', '', '', '是否缺页，注册等页面制作<2006-12-18 9:59>\r\n页面查找，大概需要一天时间<2006-12-20 15:50>', '2', '', '2006-12-20 15:50:49', '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000865', '功能完善', 'WORK_PLAN_INFO_0000001004', '2006-12-18 10:00:53', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001004', 'zhangfeng,yepuliang', '2006-12-18 00:00:00', '2006-12-23 00:00:00', '', '', '编缉，转发等功能细致完成<2006-12-18 10:1>\r\n发帖回帖功能实现，加上UBB标签转换等工作，需时半天<2006-12-20 15:44>', '3', '', '2006-12-20 15:44:27', '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000866', '注册，登录', 'WORK_PLAN_INFO_0000001004', '2006-12-18 10:01:50', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001004', 'zhangfeng', '2006-12-18 00:00:00', '2006-12-18 00:00:00', '', '', '页面及程序完成<2006-12-18 10:2>\r\n登录用户名是否重复，注册过后匿名不能重复<2006-12-18 10:17>\r\n论坛列表显示，用户信息<2006-12-18 15:24>', '3', '注册完成，登陆完成<2006-12-18 12:56>\r\n论坛列表显示，用户信息<2006-12-18 15:24>\r\nHelix安装，通过<2006-12-18 16:38>', '2006-12-18 16:37:52', '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000867', '后台管理', 'WORK_PLAN_INFO_0000001005', '2006-12-18 10:18:21', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001005', 'yepuliang', '2006-12-18 00:00:00', '2006-12-19 00:00:00', '', '', '1.模块添加，修改<2006-12-18 10:18>\r\n2.详细细节设置<2006-12-18 10:18>', '1', '', '2006-12-26 15:31:40', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000868', '套页面', 'WORK_PLAN_INFO_0000001005', '2006-12-18 10:20:15', '页面', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001005', 'yepuliang', '2006-12-20 00:00:00', '2006-12-21 00:00:00', '', '', '1.套页面<2006-12-18 10:20>\r\n2.论坛整合<2006-12-18 10:20>', '1', '', '2006-12-26 15:31:32', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000869', '前后台整合测试', 'WORK_PLAN_INFO_0000001005', '2006-12-18 10:22:10', '整合测试', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001005', 'yepuliang', '2006-12-22 00:00:00', '2006-12-22 00:00:00', '', '', '1.进一步完善。<2006-12-18 10:21>\r\n2.测试，出精简版先能用<2006-12-18 10:22>', '2', '', null, '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000870', '补充', 'WORK_PLAN_INFO_0000001005', '2006-12-18 14:35:24', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001005', 'yepuliang', '2006-12-19 00:00:00', '2006-12-19 00:00:00', '', '', '1.制作控制面板<2006-12-18 14:35>\r\n2.收藏夹<2006-12-18 14:35>\r\n3.联系人管理<2006-12-18 14:35>\r\n4.论坛搜索<2006-12-18 14:35>', '1', '1,控制面板完成\r\n\r\n2收藏夹完成<2006-12-20 17:15>', '2006-12-26 15:31:13', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000881', '查看新帖，搜索', 'WORK_PLAN_INFO_0000001004', '2006-12-20 15:45:47', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001004', 'yepuliang', '2006-12-20 00:00:00', '2006-12-20 00:00:00', '', '', '查看新帖，2个小时<2006-12-20 15:45>\r\n搜索，4个小时<2006-12-20 15:46>\r\n导航条，1个小时<2006-12-20 15:49>\r\n选择导航，一个小时<2006-12-20 15:49>', '1', '', '2006-12-26 15:31:00', '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000901', 'CRM软件开发', 'WORK_PLAN_INFO_0000001061', '2006-12-26 09:40:38', 'CRM', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001061', 'jingyuzhuo', '2006-12-26 00:00:00', '2006-12-28 00:00:00', '', '', 'JNNNJNJNJ<2006-12-26 9:40>\r\n31231232<2007-1-15 15:21>\r\n3232sdfsdfds<2007-1-15 15:21>', '2', '', '2007-01-30 10:00:54', 'L,LMLK', '', 'jingyuzhuo', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001083', '添加新闻模版', 'WORK_PLAN_INFO_0000001384', '2007-01-30 09:05:41', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001384', 'yepuliang', '2007-01-29 00:00:00', '2007-01-30 00:00:00', '', '', '完成<2007-2-2 14:21>', '2', '', '2007-02-02 14:21:44', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001084', '交换机程序', 'WORK_PLAN_INFO_0000001383', '2007-01-30 09:06:25', '交换机', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001383', 'zhaoyifei', '2007-01-30 00:00:00', '2007-02-02 00:00:00', '', '', '完成交换机程序<2007-1-30 9:3>\r\n测试<2007-1-30 9:4>\r\n完善语音卡事件类型。<2007-1-30 9:11>\r\n实现一个通用的逻辑业务抽象类，其他业务逻辑可以通过继承方式扩展<2007-1-30 9:14>\r\nTelephoneSwitchImpl类主要实现业务逻辑，30号到31号主要完成<2007-1-30 9:21>\r\n2号之前完成编码工作，2号开始测试<2007-1-30 9:24>', '1', '交换机程序已经完成部分，还有一些业务逻辑未完成。<2007-1-30 9:19>\r\nAbsTelephoneSwitch主要是抽象的逻辑，比较通用，29号已经完成<2007-1-30 9:23>\r\n基本程序写完，需要写测试文档，完成详细设计<2007-2-1 10:41>\r\n下一步需要完善语音卡类型<2007-2-1 10:42>\r\n测试基本通过<2007-2-2 16:12>\r\n语音卡类型没有完善<2007-2-2 16:12>\r\n通用逻辑抽象类没有提出来<2007-2-2 16:13>', '2007-02-02 16:13:36', '', '', 'zhaoyifei', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001085', '图片新闻版式制作', 'WORK_PLAN_INFO_0000001384', '2007-01-30 09:07:10', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001384', 'yepuliang', '2007-01-30 00:00:00', '2007-01-31 00:00:00', '', '', '增加图片新闻版式<2007-1-30 9:7>', '2', '完成<2007-2-2 14:22>', '2007-02-02 14:22:02', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001086', '添加各处验证脚本', 'WORK_PLAN_INFO_0000001384', '2007-01-30 09:08:07', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001384', 'yepuliang', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '', '2', '未完成<2007-2-2 14:22>', '2007-02-02 14:22:21', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001087', '测试', 'WORK_PLAN_INFO_0000001384', '2007-01-30 09:09:09', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001384', 'yepuliang', '2007-02-02 00:00:00', '2007-02-02 00:00:00', '', '', '', '2', '未完成<2007-2-2 14:22>', '2007-02-02 14:22:33', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001088', '完善查询', 'WORK_PLAN_INFO_0000001385', '2007-01-30 09:09:50', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001385', 'zhangfeng', '2007-01-29 00:00:00', '2007-01-30 00:00:00', '', '', '完善查询条件<2007-1-30 9:9>\r\n修改内部邮件<2007-1-30 9:55>', '3', '内部邮件完成<2007-2-2 14:19>\r\n查询基本完成，但信息不全<2007-2-2 14:20>', '2007-02-02 14:20:05', '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001089', '费率计算', 'WORK_PLAN_INFO_0000001385', '2007-01-30 09:17:43', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001385', 'zhangfeng', '2007-01-30 00:00:00', '2007-01-31 00:00:00', '', '', '费率类完善<2007-1-30 9:16>', '-1', '未完成<2007-2-2 14:18>', '2007-02-02 14:18:28', '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001090', '费率配置计划与完成', 'WORK_PLAN_INFO_0000001385', '2007-01-30 09:28:13', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001385', 'zhangfeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '费率配置计划制作<2007-1-30 9:28>', '3', '定制表结构，基本完成<2007-2-2 14:18>', '2007-02-06 08:56:33', '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001091', '规章制度、价格体系', 'WORK_PLAN_INFO_0000001387', '2007-01-30 09:53:28', '管理制度', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001387', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-02 00:00:00', '', '', '和杨朔、陈健完善公司管理制度<2007-1-30 9:53>\r\n完善价格体系<2007-1-30 9:54>\r\n提出保密制度<2007-1-30 9:54>', '2', '', '2007-01-30 10:02:18', '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001092', '用户注册', 'WORK_PLAN_INFO_0000001386', '2007-01-30 09:59:37', '注册', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001386', 'jingyuzhuo', '2007-02-03 00:00:00', '2007-02-07 00:00:00', '', '', '本周完成注册的模块<2007-1-30 9:59>\r\n1. 2月3日我将完成所有页面的建立,及填写内容\r\n2. 2月4日-2月6日我将完成注册模块的增加删改功能\r\n3 2月7日-2月8日我将完成验证及其模块中密码找回问题<2007-1-30 14:48>', '3', '未完成<2007-1-30 14:25>\r\n用户注册模块的表已经建立.<2007-1-30 14:49>', '2007-01-30 16:09:12', '', '', 'jingyuzhuo', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001093', '公安呼叫中心扩展', 'WORK_PLAN_INFO_0000001387', '2007-01-30 10:00:30', '呼叫中心、扩展', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001387', 'guxiaofeng', '2007-01-30 00:00:00', '2007-02-02 00:00:00', '', '', '查询网络资源、<2007-1-30 10:2>\r\n跟华老师沟通<2007-1-30 10:10>\r\n三警合一的详细资料弄到手。<2007-1-30 10:11>', '2', '', '2007-01-30 10:11:37', '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001094', '混淆器', 'WORK_PLAN_INFO_0000001387', '2007-01-30 10:19:15', '混淆器、公安呼叫中心', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001387', 'guxiaofeng', '2007-01-30 00:00:00', '2007-02-02 00:00:00', '', '', '公安呼叫中心混淆器<2007-1-30 10:19>', '2', '', '2007-01-30 10:29:52', '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001095', '公司电邮', 'WORK_PLAN_INFO_0000001387', '2007-01-30 10:19:40', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001387', 'guxiaofeng', '2007-01-30 00:00:00', '2007-02-02 00:00:00', '', '', '公司电邮<2007-1-30 10:19>', '2', '', '2007-01-30 10:29:18', '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001096', '公司软件项目近期规划', 'WORK_PLAN_INFO_0000001387', '2007-01-30 10:20:43', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001387', 'guxiaofeng', '2007-01-30 00:00:00', '2007-02-02 00:00:00', '', '', '制定公司软件项目近期规划<2007-1-30 10:20>', '2', '', '2007-01-30 10:29:35', '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001097', '给各个分公司发资料', 'WORK_PLAN_INFO_0000001387', '2007-01-30 10:21:23', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001387', 'guxiaofeng', '2007-01-30 00:00:00', '2007-02-02 00:00:00', '', '', '给各个分公司发资料。<2007-1-30 10:21>', '2', '', '2007-01-30 10:30:32', '马明做。', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001098', '交社保费用', 'WORK_PLAN_INFO_0000001387', '2007-01-30 10:21:42', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001387', 'guxiaofeng', '2007-01-30 00:00:00', '2007-02-02 00:00:00', '', '', '交社保费用<2007-1-30 10:21>', '2', '', '2007-01-30 10:27:58', '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001099', '索弗网站', 'WORK_PLAN_INFO_0000001387', '2007-01-30 10:22:56', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001387', 'chenyiying', '2007-01-30 00:00:00', '2007-02-02 00:00:00', '', '', '索弗网站更新<2007-1-30 10:22>', '2', '', '2007-01-30 10:26:44', '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001100', '田宇', 'WORK_PLAN_INFO_0000001387', '2007-01-30 10:23:20', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001387', 'guxiaofeng', '2007-01-30 00:00:00', '2007-02-02 00:00:00', '', '', '尽快联系到田宇<2007-1-30 10:23>', '2', '', '2007-01-30 10:25:05', '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001101', '内容发布系统', 'WORK_PLAN_INFO_0000001388', '2007-01-30 10:50:31', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '网站，\r\n张峰、叶浦亮、陈轶瑛<2007-1-30 10:50>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001102', 'ｃｒｍ用户管理', 'WORK_PLAN_INFO_0000001388', '2007-01-30 10:50:55', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '荆玉琢<2007-1-30 10:54>\r\n加入到oa中。<2007-1-30 10:55>', '2', '', '2007-01-30 10:55:21', '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001103', '论坛系统', 'WORK_PLAN_INFO_0000001388', '2007-01-30 10:52:00', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '张峰、叶浦亮<2007-1-30 10:51>\r\n张峰收尾<2007-1-30 10:51>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001105', '计费系统', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:20:48', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001106', '板卡呼叫中心', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:21:00', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001107', '计划任务系统', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:21:10', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '<录入时间:2007-3-6 10:16>暂时完成简单的情形，以后加上工作流。\r\n', '2007-03-06 10:16:59', '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001108', '短信（ｓｍ卡）', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:21:21', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001109', '网站版、样式', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:21:32', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001110', '数据库备份', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:21:42', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001111', '相册系统', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:21:53', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001112', '工作流系统', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:22:02', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001113', '会员注册系统', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:22:12', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001114', '计数器', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:22:19', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001115', 'Bug管理系统', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:22:29', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001116', '项目管理系统', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:22:38', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001117', '留言板系统', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:22:46', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001118', '产品系统', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:23:02', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001119', '电子商务系统', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:23:13', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001120', '聊天室', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:23:21', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001121', '流量分析系统', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:23:30', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001122', '多媒体视频', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:23:39', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001123', '广告管理', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:23:53', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001124', '邮局系统代理', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:24:04', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001125', '主要单表综合查询', 'WORK_PLAN_INFO_0000001388', '2007-01-30 11:24:17', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001126', '权限管理系统', 'WORK_PLAN_INFO_0000001389', '2007-01-30 13:23:53', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001389', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001127', '完善ｏａ', 'WORK_PLAN_INFO_0000001389', '2007-01-30 13:24:03', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001389', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001128', '信息检索系统', 'WORK_PLAN_INFO_0000001389', '2007-01-30 13:24:14', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001389', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001129', 'ｃｒｍ', 'WORK_PLAN_INFO_0000001389', '2007-01-30 13:24:32', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001389', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001130', '博客系统', 'WORK_PLAN_INFO_0000001389', '2007-01-30 13:24:44', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001389', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001131', '在线反馈系统', 'WORK_PLAN_INFO_0000001389', '2007-01-30 13:24:55', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001389', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001132', '在线调查', 'WORK_PLAN_INFO_0000001389', '2007-01-30 13:25:03', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001389', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001133', '在线招聘系统', 'WORK_PLAN_INFO_0000001389', '2007-01-30 13:25:12', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001389', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001134', '交友系统', 'WORK_PLAN_INFO_0000001389', '2007-01-30 13:25:19', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001389', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001135', '软呼叫中心', 'WORK_PLAN_INFO_0000001389', '2007-01-30 13:25:27', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001389', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001136', '３Ｇ手机网门户', 'WORK_PLAN_INFO_0000001389', '2007-01-30 13:25:35', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001389', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001137', '传真服务器系统', 'WORK_PLAN_INFO_0000001389', '2007-01-30 13:25:43', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001389', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001138', '体坛演义', 'WORK_PLAN_INFO_0000001390', '2007-01-30 13:49:54', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001390', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '体坛演义金鑫主题only显示<2007-1-30 13:49>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001139', '海归政策', 'WORK_PLAN_INFO_0000001390', '2007-01-30 13:50:35', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001390', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', 'a<2007-1-30 14:29>\r\nv<2007-1-30 14:30>\r\nfghgfd<2007-1-30 14:32>', '2', '', '2007-01-30 16:20:50', '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001140', '海归政策', 'WORK_PLAN_INFO_0000001390', '2007-01-30 13:51:39', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001390', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '海归优惠政策的解读。<2007-1-30 13:53>', '2', '', '2007-01-30 16:20:43', '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001141', '海归政策', 'WORK_PLAN_INFO_0000001390', '2007-01-30 13:52:04', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001390', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001142', '海归政策', 'WORK_PLAN_INFO_0000001390', '2007-01-30 13:53:09', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001390', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001143', '网站信息', 'WORK_PLAN_INFO_0000001390', '2007-01-30 13:58:42', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001390', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '存在网、网络名片、衣食住行、幼儿园网站等。数字程控交换机网络集成网站<2007-1-30 13:58>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001144', 'oa短消息', 'WORK_PLAN_INFO_0000001388', '2007-01-30 14:03:48', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-01-30 00:00:00', '2007-01-30 00:00:00', '', '', '荆玉琢做。<2007-1-30 14:3>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001145', 'ETOA系统修改', 'WORK_PLAN_INFO_0000001386', '2007-01-30 14:11:19', 'ETOA', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001386', 'jingyuzhuo', '2007-01-30 00:00:00', '2007-02-03 00:00:00', '', '', '计划审批中　标题头的　计划人名显示的问题<2004-1-22 14:8>\r\n在审批计划中增加一个部门查询条件，再增加一个周查询，和月查询<2004-1-22 14:10>\r\n还要修改详细工作计划中的详细时间的开始和结束弹出页的问题<2004-1-22 14:13>\r\n详细计划添加中也就是append.jsp这个页中的验证是否为空的问题<2004-1-22 14:14>\r\n在updateMission.jsp页中的指派单位中默认为当前登陆的人,还有是指派单位中弹出页中的箭头的问题<2004-1-22 14:16>\r\n修改详细计划的时候点击修改的时候不修改,反而增加一条新的记录的问题,在显示详细计划的LIST页面上加上删除标记<2004-1-22 14:19>\r\n点击工作计划的时候,显示的应该是本周已经审核的计划<2004-1-22 14:19>\r\n详细计划也是就当前的这个页的时间放在计划的前面,前面加上\"录入\"两个字<2004-1-22 14:21>', '2', '未开始<2004-1-22 14:20>', '2007-01-30 14:53:44', '由于本人没有与领导及时勾通,导致用户注册模块本周不能准时完成,望领导谅解.', '', 'jingyuzhuo', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001146', '索弗网站系统', 'WORK_PLAN_INFO_0000001388', '2007-02-01 09:55:28', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001388', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '以索弗网站为基点进行网站设计。<2007-2-1 9:55>\r\n设计成一个通用的客户服务系统。<2007-2-2 9:3>', '2', '', '2007-02-02 09:03:46', '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001147', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:00:57', '他', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '他<2007-2-1 10:1>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001148', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:01:03', '他', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '他<2007-2-1 10:1>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001149', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:01:18', '他', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '他<2007-2-1 10:1>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001150', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:01:20', '他', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '他<2007-2-1 10:1>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001151', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:01:21', '他', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '他<2007-2-1 10:1>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001152', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:01:24', '他', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '他<2007-2-1 10:1>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001153', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:01:25', '他', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '他<2007-2-1 10:1>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001154', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:01:27', '他', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '他<2007-2-1 10:1>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001155', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:01:28', '他', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '他<2007-2-1 10:1>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001156', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:01:52', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001157', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:02:19', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001158', '天天通天屯他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:03:15', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001159', '天天通天屯他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:03:26', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001160', '天天通天屯他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:03:27', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001161', '天天通天屯他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:03:30', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001162', '天天通天屯他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:03:31', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001163', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:04:36', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001164', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:04:43', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001165', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:04:45', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001166', '他', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:07:10', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001167', '实施实施实施实施事实上', 'WORK_PLAN_INFO_0000001391', '2007-02-01 10:17:19', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001391', 'guxiaofeng', '2007-02-01 00:00:00', '2007-02-01 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001168', '费率计算', 'WORK_PLAN_INFO_0000001392', '2007-02-02 14:22:51', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001392', 'zhangfeng', '2007-02-05 00:00:00', '2007-02-09 00:00:00', '', '', '定制费率计划，开始做费率计算，完善类结构<2007-2-2 14:23>', '2', '', null, '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001169', '注册模块', 'WORK_PLAN_INFO_0000001393', '2007-02-02 14:27:19', '注册', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001393', 'jingyuzhuo', '2007-02-05 00:00:00', '2007-02-05 00:00:00', '', '', '2月2日 完成基本的增删改查功能<2007-2-2 14:27>\r\n上面的写错了，2月5日 完成基本的增删改查功能<2007-2-2 14:32>', '2', '', '2007-02-02 14:32:17', '', '', 'jingyuzhuo', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001170', '样式管理收尾', 'WORK_PLAN_INFO_0000001394', '2007-02-02 14:28:23', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001394', 'yepuliang', '2007-02-05 00:00:00', '2007-02-05 00:00:00', '', '', '样式管理收尾<2007-2-2 14:28>', '2', '<录入时间:2007-2-12 13:25>完成\r\n', '2007-02-12 13:25:23', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001171', '准备做手机短信', 'WORK_PLAN_INFO_0000001394', '2007-02-02 14:28:52', '手机短信', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001394', 'yepuliang', '2007-02-06 00:00:00', '2007-02-09 00:00:00', '', '', '', '2', '<录入时间:2007-2-12 13:25>完成\r\n', '2007-02-12 13:25:35', '', '', 'yepuliang', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001172', '注册模块', 'WORK_PLAN_INFO_0000001393', '2007-02-02 14:30:31', '注册', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001393', 'jingyuzhuo', '2007-02-06 00:00:00', '2007-02-06 00:00:00', '', '', '2月3日完成注册模块的用户找回密码，等验证功能。<2007-2-2 14:30>\r\n上面的时间写错了，是2月6日<2007-2-2 14:33>', '2', '', '2007-02-02 14:33:02', '', '', 'jingyuzhuo', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001173', '注册模块', 'WORK_PLAN_INFO_0000001393', '2007-02-02 14:35:00', '注册', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001393', 'jingyuzhuo', '2007-02-07 00:00:00', '2007-02-07 00:00:00', '', '', '2月7日做用户注册模块的测试工作，完善。<2007-2-2 14:35>', '2', '', '2007-02-03 13:46:44', '', '', 'jingyuzhuo', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001174', '交换机程序完善', 'WORK_PLAN_INFO_0000001395', '2007-02-02 16:17:15', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001395', 'zhaoyifei', '2007-02-02 00:00:00', '2007-02-09 00:00:00', '', '', '等待领导审批下一步完成交换机功能内容。<2007-2-2 16:16>\r\n交换机继续完成。<2007-2-2 16:16>\r\n完善上周做的工作，把抽象类提取出来，整理事件及状态<2007-2-2 16:18>', '2', '', '2007-02-02 16:18:18', '', '', 'zhaoyifei', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001175', '特殊号码添加', 'WORK_PLAN_INFO_0000001392', '2007-02-06 09:01:45', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001392', 'zhangfeng', '2007-02-06 00:00:00', '2007-02-06 00:00:00', '', '', '特殊号码添加<2007-2-6 9:1>\r\n资费信息添加<2007-2-6 9:1>\r\n号码信息添加<2007-2-6 9:2>', '2', '', '2007-02-06 09:02:33', '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001176', 'pbx业务和测试讨论', 'WORK_PLAN_INFO_0000001395', '2007-02-06 09:32:52', 'pbx 业务 测试', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001395', 'zhaoyifei', '2007-02-06 00:00:00', '2007-02-06 00:00:00', '', '', 'pbx业务需求进一步确定，测试方式确定，需要开会讨论<2007-2-6 9:32>\r\n其他详细计划等确定业务和测试之后定<2007-2-6 9:32>\r\n文档整理，根据业务和测试内容<2007-2-6 9:33>', '2', '', '2007-02-06 09:34:00', '', '', 'zhaoyifei', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001177', '费率管理', 'WORK_PLAN_INFO_0000001392', '2007-02-06 15:15:52', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001392', 'zhangfeng', '2007-02-07 00:00:00', '2007-02-09 00:00:00', '', '', 'SP信息添加<2007-2-6 15:13>\r\n区位信息添加<2007-2-6 15:14>\r\nSP-区位对应<2007-2-6 15:15>\r\n假日信息添加<2007-2-6 15:15>\r\nSP-假日对应信息<2007-2-6 15:15>', '2', '', null, '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001178', '呼叫中心', 'WORK_PLAN_INFO_0000001392', '2007-02-06 15:18:11', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001392', 'zhangfeng', '2007-02-08 00:00:00', '2007-02-09 00:00:00', '', '', '警务人员询问问题申批，有标志信息<2007-2-6 15:18>', '2', '', null, '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001179', '无线中继', null, '2007-02-06 16:04:31', '无线中继', 'SYS_TREE_0000000281', null, 'guxiaofeng', '2007-02-06 00:00:00', '2007-02-06 00:00:00', '', '', '任序介绍的：\r\n姜：13304216968<2007-2-6 16:4>', '2', '', null, '', '', 'guxiaofeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001180', 'pbx语音信箱语音通知', 'WORK_PLAN_INFO_0000001395', '2007-02-06 17:46:55', '语音信箱', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001395', 'zhaoyifei', '2007-02-06 00:00:00', '2007-02-09 00:00:00', '', '', '完成语音信箱功能<2007-2-6 17:45>\r\n完成语音通知功能<2007-2-6 17:45>\r\n这周有可能完成不了，下周继续<2007-2-6 17:46>\r\n完善文档语音信箱和通知部分<2007-2-6 17:46>', '2', '', null, '', '', 'zhaoyifei', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001241', '语音信箱', 'WORK_PLAN_INFO_0000001395', '2007-02-06 17:51:39', 'pbx 语音信箱', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001395', 'zhaoyifei', '2007-02-06 00:00:00', '2007-02-09 00:00:00', '', '', '语音信箱部分完成并测试<2007-2-6 17:51>\r\n完善文档<2007-2-6 17:51>', '2', '', null, '', '', 'zhaoyifei', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001242', '实验', null, '2007-02-08 12:48:47', 'shiyan', 'SYS_TREE_0000000281', null, 'jingyuzhuo', '2007-02-08 00:00:00', '2007-02-08 00:00:00', '', '', '', '2', '', null, '', '', 'jingyuzhuo', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001243', 'jingjing', null, '2007-02-08 13:32:23', 'jingjing', 'SYS_TREE_0000000281', null, 'jingyuzhuo', '2007-02-08 00:00:00', '2007-02-08 00:00:00', '', '', '', '2', '', null, '', '', 'jingyuzhuo', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001244', 'bbbbb', null, '2007-02-08 13:33:53', 'bbbb', 'SYS_TREE_0000000281', null, 'jingyuzhuo', '2007-02-08 00:00:00', '2007-02-08 00:00:00', '', '', '', '2', '', null, '', '', 'jingyuzhuo', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001245', 'bbbbb', null, '2007-02-08 13:33:57', 'bbbb', 'SYS_TREE_0000000281', null, 'jingyuzhuo', '2007-02-08 00:00:00', '2007-02-08 00:00:00', '', '', '', '2', '', null, '', '', 'jingyuzhuo', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001246', 'sp-国际区位对应表', 'WORK_PLAN_INFO_0000001397', '2007-02-12 10:38:41', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001397', 'zhangfeng', '2007-02-12 00:00:00', '2007-02-12 00:00:00', '', '', 'sp-国际区位对应<2007-2-12 10:38>\r\nSP-假日对应表<2007-2-12 10:38>', '2', '', null, '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001247', '费率处理类制作', 'WORK_PLAN_INFO_0000001397', '2007-02-12 10:51:48', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001397', 'zhangfeng', '2007-02-13 00:00:00', '2007-02-14 00:00:00', '', '', '费率类制作，处理<2007-2-12 10:51>', '2', '', null, '', '', 'zhangfeng', null);
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001261', 'jingyuzhuoshiyan', 'WORK_PLAN_INFO_0000001481', '2007-02-12 12:53:47', 'jingyuzhuoshiyan', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001481', 'jingyuzhuo', '2007-02-12 00:00:00', '2007-02-12 00:00:00', '', '', '<录入时间:2007-2-12 12:53>jingyuzhuoshiyan\r\n', '2', '', null, 'jingyuzhuoshiyan', '', 'jingyuzhuo', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001262', '索弗网站', 'WORK_PLAN_INFO_0000001482', '2007-02-12 13:07:11', '转为动态网页', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001482', 'chenyiying', '2007-02-12 00:00:00', '2007-03-02 00:00:00', '', '', '<录入时间:2007-2-12 13:7>技术资料整理，\r\n待转为动态网页\r\n', '2', '', null, '', '', 'chenyiying', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001263', '关于春节放假', 'WORK_PLAN_INFO_0000001482', '2007-02-12 13:10:53', '春节放假通知', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001482', 'chenyiying', '2007-02-15 00:00:00', '2007-02-27 00:00:00', '', '', '<录入时间:2007-2-12 13:10>公司春节放假时间安排\r\n一、市外职员放假时间为2月15日-2月27\r\n二、市内职员放假时间为2月16日-2月27日\r\n初十正常上班，请各位安排好放假前的工作事项。不要影响工作。\r\n', '2', '', null, '', '', 'chenyiying', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001264', '熟悉SMS发短信流程', 'WORK_PLAN_INFO_0000001483', '2007-02-12 13:28:24', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001483', 'yepuliang', '2007-02-12 00:00:00', '2007-02-15 00:00:00', '', '', '<录入时间:2007-2-12 13:28>熟悉SMS发短信流程\r\n', '2', '', '2007-02-12 13:28:40', '', '', 'yepuliang', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001265', '熟悉3种短信收发的特点', 'WORK_PLAN_INFO_0000001484', '2007-02-27 09:08:32', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001484', 'yepuliang', '2007-02-27 00:00:00', '2007-02-28 00:00:00', '', '', '', '2', '', null, '', '', 'yepuliang', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001266', '公司制度', null, '2007-02-28 08:34:00', '', 'SYS_TREE_0000000281', null, 'guxiaofeng', '2007-02-28 00:00:00', '2007-02-28 00:00:00', '', '', '<录入时间:2007-2-28 8:34>完善公司管理制度\r\n<录入时间:2007-2-28 8:36>明日打卡\r\n', '3', '<录入时间:2007-3-15 16:11>赵一非无卡。\r\n', '2007-03-15 16:11:17', '', '', 'guxiaofeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001267', '招聘', null, '2007-02-28 08:35:42', '', 'SYS_TREE_0000000281', null, 'guxiaofeng', '2007-02-28 00:00:00', '2007-02-28 00:00:00', '', '', '<录入时间:2007-2-28 8:35>软件市场人员、软件开发、项目经理\r\n', '3', '', '2007-03-02 09:45:04', '', '', 'guxiaofeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001268', '去马科长那里', null, '2007-02-28 08:38:37', '', 'SYS_TREE_0000000281', null, 'guxiaofeng', '2007-02-28 00:00:00', '2007-02-28 00:00:00', '', '', '<录入时间:2007-2-28 8:38>拜年，新年的项目了解一下。\r\n', '1', '<录入时间:2007-3-15 16:12>准备二期。\r\n', '2007-03-15 16:11:47', '', '', 'guxiaofeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001269', 'SP-区位对应', 'WORK_PLAN_INFO_0000001485', '2007-02-28 09:00:50', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001485', 'zhangfeng', '2007-02-28 00:00:00', '2007-02-28 00:00:00', '', '', '<录入时间:2007-2-28 9:1>SP-区位对应制作\r\n', '2', '', null, '', '', 'zhangfeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001270', '费率类制作', 'WORK_PLAN_INFO_0000001485', '2007-02-28 09:01:18', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001485', 'zhangfeng', '2007-03-01 00:00:00', '2007-03-01 00:00:00', '', '', '<录入时间:2007-2-28 9:1>费率类制作\r\n', '2', '', null, '', '', 'zhangfeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001271', '测试流程', 'WORK_PLAN_INFO_0000001486', '2007-03-02 16:33:34', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001486', 'zhangfeng', '2007-03-05 00:00:00', '2007-03-05 00:00:00', '', '', '<录入时间:2007-3-2 16:33>测试打电话实际数据\r\n', '2', '', null, '', '', 'zhangfeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001272', '用户密码找回完善', 'WORK_PLAN_INFO_0000001488', '2007-03-02 16:43:09', '密码找回完善', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001488', 'jingyuzhuo', '2007-03-05 00:00:00', '2007-03-06 00:00:00', '', '', '<录入时间:2007-3-2 16:42>完善密码找回功能\r\n', '2', '', '2007-03-02 16:44:02', '', '', 'jingyuzhuo', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001273', '测试并完善', 'WORK_PLAN_INFO_0000001488', '2007-03-02 16:45:23', '测试并完善', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001488', 'jingyuzhuo', '2007-03-06 00:00:00', '2007-03-09 00:00:00', '', '', '<录入时间:2007-3-2 16:45>测试并完善用户注册模块\r\n', '2', '', null, '', '', 'jingyuzhuo', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001274', '流程测试，完善', 'WORK_PLAN_INFO_0000001486', '2007-03-02 16:45:23', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001486', 'jingyuzhuo', '2007-03-05 00:00:00', '2007-03-07 00:00:00', '', '', '<录入时间:2007-3-2 16:45>流程测试，完善\r\n', '2', '', '2007-03-02 16:45:33', '', '', 'zhangfeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001275', '计划OA', 'WORK_PLAN_INFO_0000001486', '2007-03-02 16:48:26', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001486', 'jingyuzhuo', '2007-03-08 00:00:00', '2007-03-09 00:00:00', '', '', '<录入时间:2007-3-2 16:48>制定OA计划，配置人员\r\n', '2', '', null, '', '', 'zhangfeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001276', '把论坛加到OA中', 'WORK_PLAN_INFO_0000001489', '2007-03-06 16:03:15', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001489', 'yepuliang', '2007-03-06 00:00:00', '2007-03-06 00:00:00', '', '', '<录入时间:2007-3-6 16:3>把论坛加到OA中\r\n', '2', '', null, '', '', 'yepuliang', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001277', '语音信箱收尾', 'WORK_PLAN_INFO_0000001490', '2007-03-08 16:08:46', '语音信箱', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001490', 'zhaoyifei', '2007-03-05 00:00:00', '2007-03-09 00:00:00', '', '', '<录入时间:2007-3-8 16:8>语音信箱编码及测试\r\n<录入时间:2007-3-8 16:8>语音信箱录音部分修改\r\n', '2', '', null, '', '', 'zhaoyifei', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001278', '语音信箱文档', 'WORK_PLAN_INFO_0000001490', '2007-03-08 16:09:42', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001490', 'zhaoyifei', '2007-03-09 00:00:00', '2007-03-10 00:00:00', '', '', '<录入时间:2007-3-8 16:9>语音信箱文档完成\r\n', '2', '', null, '', '', 'zhaoyifei', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001279', 'OA网页设计', 'WORK_PLAN_INFO_0000001491', '2007-03-08 16:16:02', 'OA', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001491', 'chenyiying', '2007-03-08 00:00:00', '2007-03-17 00:00:00', '', '', '<录入时间:2007-3-8 16:16>OA的网页设计\r\n', '2', '', null, '', '', 'chenyiying', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001280', '修改论坛', 'WORK_PLAN_INFO_0000001489', '2007-03-09 08:49:20', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001489', 'yepuliang', '2007-03-07 00:00:00', '2007-03-08 00:00:00', '', '', '<录入时间:2007-3-9 8:49>修改论坛\r\n', '2', '<录入时间:2007-3-9 8:55>如何删除已添加的论坛区域有待解决\r\n', '2007-03-09 08:55:15', '', '', 'yepuliang', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001281', 'oa文档制作', 'WORK_PLAN_INFO_0000001501', '2007-03-10 16:37:35', 'oa', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001501', 'zhaoyifei', '2007-03-12 00:00:00', '2007-03-13 00:00:00', '', '', '<录入时间:2007-3-10 16:36>完成oa文档内容。\r\n<录入时间:2007-3-10 16:37>内容包括sys模块所有，im模块，人事管理模块，公共接口部分\r\n', '2', '', null, '', '', 'zhaoyifei', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001282', '语音信息', 'WORK_PLAN_INFO_0000001501', '2007-03-10 16:39:49', 'cc', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001501', 'zhaoyifei', '2007-03-14 00:00:00', '2007-03-16 00:00:00', '', '', '<录入时间:2007-3-10 16:39>语音信箱编码、测试。主动放音部分，索引放音，录音部分重新写一下。\r\n<录入时间:2007-3-10 16:39>自己测试自己的代码，大家测试下周进行。\r\n', '2', '', null, '', '', 'zhaoyifei', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001283', '语音信息文档', 'WORK_PLAN_INFO_0000001501', '2007-03-10 16:40:33', 'cc', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001501', 'zhaoyifei', '2007-03-16 00:00:00', '2007-03-16 00:00:00', '', '', '<录入时间:2007-3-10 16:40>编码测试通过后写文档。\r\n', '2', '', null, '', '', 'zhaoyifei', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001284', '计费系统测试', 'WORK_PLAN_INFO_0000001503', '2007-03-10 16:53:02', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001503', 'jingyuzhuo', '2007-03-12 00:00:00', '2007-03-12 00:00:00', '', '', '<录入时间:2007-3-10 16:52>计费软件测试\r\n<录入时间:2007-3-10 16:53>测试文档完善\r\n', '2', '', null, '', '', 'zhangfeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001285', '论坛完善', 'WORK_PLAN_INFO_0000001502', '2007-03-10 16:53:06', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001502', 'yepuliang', '2007-03-12 00:00:00', '2007-03-13 00:00:00', '', '', '<录入时间:2007-3-10 16:53>把论坛加到OA中，进行BUG管理测试\r\n', '2', '<录入时间:2007-3-14 14:31>论坛已成功加到OA中，现在存在几点问题\r\n1.页面长度定死，与OA的框架页面补协调\r\n2.个别页面样式不妥有待调整\r\n3.有待大家测试提出问题以便改正。\r\n', '2007-03-14 14:30:47', '', '', 'yepuliang', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001286', 'OA短消息计划', 'WORK_PLAN_INFO_0000001503', '2007-03-10 16:54:00', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001503', 'zhangfeng', '2007-03-13 00:00:00', '2007-03-13 00:00:00', '', '', '<录入时间:2007-3-10 16:54>OA短消息计划，表定义\r\n<录入时间:2007-3-10 16:55>邮件修改\r\n', '2', '', '2007-03-10 16:56:18', '', '', 'zhangfeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001287', '完善OA文档', 'WORK_PLAN_INFO_0000001502', '2007-03-10 16:54:01', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001502', 'jingyuzhuo', '2007-03-13 00:00:00', '2007-03-14 00:00:00', '', '', '<录入时间:2007-3-10 16:54>写oa文档\r\n', '2', '', null, '', '', 'yepuliang', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001288', '测试用户注册', 'WORK_PLAN_INFO_0000001504', '2007-03-10 16:54:50', '测试', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001504', 'jingyuzhuo', '2007-03-12 00:00:00', '2007-03-16 00:00:00', '', '', '<录入时间:2007-3-10 16:53>测试用户注册模块\r\n<录入时间:2007-3-10 16:54>并且更正所有检测出来的问题，以及对注册模块界面的美化。\r\n', '2', '', null, '', '', 'jingyuzhuo', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001289', 'OA文档编写', 'WORK_PLAN_INFO_0000001503', '2007-03-10 16:55:15', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001503', 'zhangfeng', '2007-03-14 00:00:00', '2007-03-14 00:00:00', '', '', '<录入时间:2007-3-10 16:55>OA文档整理与编写\r\n', '2', '', null, '', '', 'zhangfeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001290', 'OA系统计划', 'WORK_PLAN_INFO_0000001503', '2007-03-10 16:56:53', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001503', 'zhangfeng', '2007-03-15 00:00:00', '2007-03-16 00:00:00', '', '', '<录入时间:2007-3-10 16:57>OA系统计划详细编写\r\n', '2', '', null, '', '', 'zhangfeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001291', '1.OA设计', 'WORK_PLAN_INFO_0000001505', '2007-03-10 17:09:48', '1.OA设计', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001505', 'jingyuzhuo', '2007-03-10 00:00:00', '2007-03-10 00:00:00', '', '', '<录入时间:2007-3-10 17:9>1.OA设计\r\n2.准备网站模板\r\n', '2', '', null, '1.OA设计\r\n2.准备网站模板', '', 'chenyiying', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001292', '招聘人员、购置计算机。', 'WORK_PLAN_INFO_0000001506', '2007-03-12 07:57:02', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001506', 'guxiaofeng', '2007-03-12 00:00:00', '2007-03-16 00:00:00', '', '', '<录入时间:2007-3-12 7:56>招聘市场人员１名、项目经理一名、开发人员２名。\r\n<录入时间:2007-3-12 7:59>开发机器２台，项目经理一台。问一下莽珂的机器，应该暂时购置３台。\r\n', '2', '', '2007-03-12 08:00:39', '', '', 'guxiaofeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001293', '去哈尔滨', 'WORK_PLAN_INFO_0000001506', '2007-03-12 08:01:30', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001506', 'guxiaofeng', '2007-03-15 00:00:00', '2007-03-15 00:00:00', '', '', '<录入时间:2007-3-12 8:1>呼叫中心事宜。\r\n<录入时间:2007-3-12 8:2>报价。\r\n', '2', '', '2007-03-12 08:02:27', '', '', 'guxiaofeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001294', '准备沈阳市公安方案', 'WORK_PLAN_INFO_0000001506', '2007-03-12 08:02:01', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001506', 'guxiaofeng', '2007-03-12 00:00:00', '2007-03-12 00:00:00', '', '', '', '2', '', null, '', '', 'guxiaofeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001295', '今天', null, '2007-03-13 08:18:08', '', 'SYS_TREE_0000000281', null, 'yepuliang', '2007-03-13 00:00:00', '2007-03-13 00:00:00', '', '', '<录入时间:2007-3-13 8:18>医药分单。\r\n', '2', '', null, '', '', 'guxiaofeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001296', '0313', null, '2007-03-13 08:24:48', '', 'SYS_TREE_0000000281', null, 'guxiaofeng', '2007-03-13 00:00:00', '2007-03-13 00:00:00', '', '', '<录入时间:2007-3-13 8:24><录入时间:2007-3-13 8:21>医药分单、确认考试驾照、ｆｔｐ账号、写方案、（３点）、sp业务、青鸟调查报告、\r\n<录入时间:2007-3-13 8:25>公司业务。\r\n<录入时间:2007-3-15 16:10>速达软件\r\n', '3', '<录入时间:2007-3-15 16:10>剩下公安扩展方案和ｓｐ业务和速达软件。\r\n', '2007-03-15 16:10:43', '', '', 'guxiaofeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001301', '完成oa文档', 'WORK_PLAN_INFO_0000001502', '2007-03-14 14:32:18', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001502', 'yepuliang', '2007-03-14 00:00:00', '2007-03-14 00:00:00', '', '', '<录入时间:2007-3-14 14:32>1.已完成通讯录部分\r\n2.已完成留言板部分\r\n3.已完成考勤管理部分\r\n', '2', '', null, '', '', 'yepuliang', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001321', 'shiyan', 'WORK_PLAN_INFO_0000001521', '2007-03-15 16:52:37', 'shiyan', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001521', 'jingyuzhuo', '2007-03-15 00:00:00', '2007-03-15 00:00:00', '', '', '<录入时间:2007-3-15 16:52>shiyan\r\n', '2', '', null, 'shiyan', '', 'jingyuzhuo', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001341', 'OA短消息制作', 'WORK_PLAN_INFO_0000001542', '2007-03-16 15:50:50', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001542', 'zhangfeng', '2007-03-19 00:00:00', '2007-03-22 00:00:00', '', '', '<录入时间:2007-3-16 15:51>OA短消息制作\r\n', 'SYS_TREE_0000000503', '', '2007-03-21 14:46:32', '', '', 'zhangfeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001342', '语音信箱', 'WORK_PLAN_INFO_0000001541', '2007-03-16 15:51:30', '语音信箱', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001541', 'zhaoyifei', '2007-03-19 00:00:00', '2007-03-20 00:00:00', '', '', '<录入时间:2007-3-16 15:50>语音信箱放音部分还有问题，显示有dtmf码，不正常状态\r\n<录入时间:2007-3-16 15:51>改进不正常的地方\r\n<录入时间:2007-3-16 15:51>完成文档\r\n', 'SYS_TREE_0000000503', '', null, '', '', 'zhaoyifei', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001343', 'OA邮件制作', 'WORK_PLAN_INFO_0000001542', '2007-03-16 15:51:38', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001542', 'zhangfeng', '2007-03-22 00:00:00', '2007-03-22 00:00:00', '', '', '<录入时间:2007-3-16 15:51>草稿箱信息修改\r\n', 'SYS_TREE_0000000503', '', '2007-03-21 14:46:20', '', '', 'zhangfeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001344', '计费软件完善，修改问题', 'WORK_PLAN_INFO_0000001542', '2007-03-16 15:52:16', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001542', 'zhangfeng', '2007-03-19 00:00:00', '2007-03-23 00:00:00', '', '', '<录入时间:2007-3-16 15:52>计费软件制作，完善，修改问题\r\n', 'SYS_TREE_0000000503', '', '2007-03-16 15:52:36', '', '', 'zhangfeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001345', '呼叫中心方案', 'WORK_PLAN_INFO_0000001541', '2007-03-16 15:52:25', '呼叫中心', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001541', 'zhaoyifei', '2007-03-21 00:00:00', '2007-03-23 00:00:00', '', '', '<录入时间:2007-3-16 15:52>制定呼叫中心方案计划\r\n', 'SYS_TREE_0000000503', '', '2007-03-16 15:52:42', '', '', 'zhaoyifei', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001346', '写论坛文档', 'WORK_PLAN_INFO_0000001543', '2007-03-16 17:15:52', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001543', 'yepuliang', '2007-03-19 00:00:00', '2007-03-20 00:00:00', '', '', '', 'SYS_TREE_0000000503', '<录入时间:2007-3-20 17:0>未完成。\r\n\r\n添加了一些OA文档的剩余部分。\r\n<录入时间:2007-3-20 17:1>做了一个论坛技术文档的计划\r\n', '2007-03-20 17:01:24', '', '', 'yepuliang', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001347', '完善oa文档', 'WORK_PLAN_INFO_0000001502', '2007-03-16 17:17:54', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001502', 'yepuliang', '2007-03-16 00:00:00', '2007-03-16 00:00:00', '', '', '<录入时间:2007-3-16 17:17>1.完成会议管理文档\r\n<录入时间:2007-3-16 17:18>完成资源管理文档\r\n', 'SYS_TREE_0000000503', '<录入时间:2007-3-16 17:18>完成\r\n', '2007-03-16 17:18:08', '', '', 'yepuliang', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001348', 'oa宣传手册', null, '2007-03-17 15:08:36', '', 'SYS_TREE_0000000281', null, 'guxiaofeng', '2007-03-17 00:00:00', '2007-03-17 00:00:00', '', '', '<录入时间:2007-3-17 15:8>oa宣传要提到优点。\r\n<录入时间:2007-3-17 15:12>传真服务器。\r\n', 'SYS_TREE_0000000503', '', '2007-03-17 15:12:11', '', '', 'guxiaofeng', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001349', '准备完成产品系统', 'WORK_PLAN_INFO_0000001543', '2007-03-19 09:09:13', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001543', 'yepuliang', '2007-03-21 00:00:00', '2007-03-22 00:00:00', '', '', '', 'SYS_TREE_0000000503', '<录入时间:2007-3-22 16:20>论坛文档已完成！\r\n<录入时间:2007-3-22 16:21>准备完成产品系统。\r\n', '2007-03-22 16:21:38', '', '', 'yepuliang', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001350', '准备完成计数器', 'WORK_PLAN_INFO_0000001543', '2007-03-19 09:09:29', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001543', 'yepuliang', '2007-03-23 00:00:00', '2007-03-23 00:00:00', '', '', '<录入时间:2007-3-22 16:22>计数器延后，准备完成产品系统\r\n', 'SYS_TREE_0000000503', '', '2007-03-22 16:23:03', '', '', 'yepuliang', '0');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000001361', '用户注册修改', 'WORK_PLAN_INFO_0000001561', '2007-03-22 14:46:51', '用户注册修改', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001561', 'jingyuzhuo', '2007-03-19 00:00:00', '2007-03-23 00:00:00', '', '', '<录入时间:2007-3-22 14:46>用户注册修改\r\n原因:因为表的改动,所以再次对注册模块进行修改\r\n', 'SYS_TREE_0000000503', '', null, '', '', 'jingyuzhuo', '0');
INSERT INTO `workflow_define` VALUES ('1', 'exam', '考试管理');
INSERT INTO `workflow_define` VALUES ('6', 'plan', '计划审批');
INSERT INTO `workflow_define` VALUES ('7', 'conference', '会议管理');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000001', '1', '32');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000021', '1', '33');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000022', '1', '34');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000041', '6', '35');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000061', '6', '36');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000062', '6', '37');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000081', '6', '38');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000101', '6', '39');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000102', '6', '40');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000121', '7', '45');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000122', '7', '47');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000141', '6', '46');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000142', '6', '48');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000161', '7', '49');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000181', '7', '50');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000201', '7', '51');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000221', '6', '1');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000222', '6', '2');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000223', '6', '3');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000224', '6', '4');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000225', '7', '5');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000226', '7', '6');
INSERT INTO `workflow_instance` VALUES ('WORKFLOW_INSTANCE_0000000227', '7', '7');
