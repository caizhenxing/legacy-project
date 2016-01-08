/*
MySQL Data Transfer
Source Host: 192.168.1.200
Source Database: website
Target Host: 192.168.1.200
Target Database: website
Date: 2007-3-25 13:47:10
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for addresslistsort_info
-- ----------------------------
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
CREATE TABLE `checkwork_time` (
  `id` varchar(50) default NULL,
  `onduty_time` varchar(20) default NULL COMMENT '班上时间',
  `offduty_time` varchar(20) default NULL COMMENT '下班时间'
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for driver_class_info
-- ----------------------------
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
CREATE TABLE `flow_right` (
  `Id` varchar(50) NOT NULL,
  `FLOW_ACTOR` varchar(50) default NULL,
  `OA_USER` varchar(50) default NULL,
  `OA_ROLE` varchar(50) default NULL,
  PRIMARY KEY  (`Id`)
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
-- Table structure for gov_antitheses_info
-- ----------------------------
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
-- Table structure for news_area
-- ----------------------------
CREATE TABLE `news_area` (
  `id` varchar(50) NOT NULL,
  `style_id` varchar(50) default NULL,
  `news_area_name` varchar(50) default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for news_area_style
-- ----------------------------
CREATE TABLE `news_area_style` (
  `id` varchar(50) NOT NULL default '',
  `style_id` varchar(50) default NULL,
  `news_area_name` varchar(50) default NULL,
  `style_num` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for news_article
-- ----------------------------
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
CREATE TABLE `news_format_info` (
  `class_id` varchar(50) NOT NULL default '0',
  `format_type` int(50) default NULL,
  `del_mark` int(11) default NULL,
  `remark` varchar(255) default NULL,
  PRIMARY KEY  (`class_id`),
  UNIQUE KEY `format_info_key` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for news_style
-- ----------------------------
CREATE TABLE `news_style` (
  `id` varchar(50) NOT NULL,
  `style_describe` varchar(50) default NULL,
  `news_num` varchar(50) default NULL,
  `show_style` varchar(50) default NULL,
  `title_char_num` varchar(50) default NULL,
  `title_char_color` varchar(50) default NULL,
  `title_char_font` varchar(50) default NULL,
  `title_char_size` varchar(50) default NULL,
  `content_char_num` varchar(200) default NULL,
  `article_property` varchar(50) default NULL,
  `author` varchar(50) default NULL,
  `click_times` varchar(50) default NULL,
  `updatetime` varchar(50) default NULL,
  `show_more` varchar(50) default NULL,
  `is_hot` varchar(2) default NULL,
  `hot_article` varchar(2) default NULL,
  `tuijian_article` varchar(2) default NULL,
  `date_range` varchar(50) default NULL,
  `paixu_field` varchar(50) default NULL,
  `paixu_method` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for plan_detail
-- ----------------------------
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
-- Table structure for web_user_register
-- ----------------------------
CREATE TABLE `web_user_register` (
  `id` varchar(50) NOT NULL,
  `login_name` varchar(50) default NULL COMMENT '用户登陆名',
  `password` varchar(50) default NULL COMMENT '用户登陆密码',
  `nickname` varchar(50) default NULL COMMENT '用户昵称',
  `dict_sex` varchar(50) default NULL COMMENT '性别',
  `stature` varchar(50) default NULL COMMENT '用户身高',
  `avoirdupois` varchar(50) default NULL COMMENT '用户体重',
  `birthday` varchar(50) default NULL COMMENT '出生日期',
  `work_address` varchar(50) default NULL COMMENT '用户工作所在地',
  `home_address` varchar(50) default NULL COMMENT '家庭地址',
  `dict_country` varchar(50) default NULL COMMENT '国家',
  `dict_province` varchar(50) default NULL COMMENT '省份',
  `dict_city` varchar(50) default NULL COMMENT '城市',
  `dict_school_age` varchar(50) default NULL COMMENT '学历',
  `dict_couple_yes_no` varchar(50) default NULL COMMENT '是否结婚',
  `native_place` varchar(50) default NULL COMMENT '籍贯',
  `make_friend` varchar(50) default NULL COMMENT '交朋友目的',
  `intro_self` varchar(200) default NULL COMMENT '自我介绍',
  `finish_school` varchar(50) default NULL COMMENT '毕业学校',
  `dict_folk` varchar(50) default NULL COMMENT '民族',
  `dict_calling` varchar(50) default NULL COMMENT '所属行业',
  `dict_work_title` varchar(50) default NULL COMMENT '职业头衔',
  `dict_earning` varchar(50) default NULL COMMENT '个人收入',
  `house_term` varchar(50) default NULL COMMENT '住房条件',
  `dict_smoke` varchar(50) default NULL COMMENT '是否吸烟',
  `dict_drink` varchar(50) default NULL COMMENT '是否饮酒',
  `like_person` varchar(50) default NULL COMMENT '喜欢的人',
  `like_adage` varchar(50) default NULL COMMENT '欢喜的人生格言',
  `like_self` varchar(200) default NULL COMMENT '人个爱好',
  `name` varchar(50) default NULL COMMENT '真实姓名',
  `tel_self` varchar(50) default NULL COMMENT '私人电话',
  `answer_one` varchar(100) default NULL COMMENT '用于密码找回的答案1',
  `answer_two` varchar(100) default NULL COMMENT '用于密码找回的答案2',
  `answer_third` varchar(100) default NULL COMMENT '用于密码找回的答案3',
  `question_one` varchar(100) default NULL COMMENT '用于密码找回的问题1',
  `question_two` varchar(100) default NULL COMMENT '用于密码找回的问题2',
  `question_third` varchar(100) default NULL COMMENT '用于密码找回的问题3',
  `dict_paper_type` varchar(50) default NULL COMMENT '证件类型',
  `photo_url` varchar(100) default NULL COMMENT '照片路径',
  `paper_message` varchar(50) default NULL COMMENT '证件信息',
  `head_photo` varchar(50) default NULL COMMENT '头像路径',
  `qq_num` varchar(50) default NULL COMMENT 'QQ号码',
  `msn_num` varchar(50) default NULL COMMENT 'MSN号码',
  `homepage` varchar(50) default NULL COMMENT '个人主页',
  `email` varchar(50) default NULL COMMENT 'email',
  `company_name` varchar(50) default NULL COMMENT '公司名称',
  `company_type` varchar(50) default NULL COMMENT '公司类型',
  `company_address` varchar(50) default NULL COMMENT '公司地址',
  `company_inter` varchar(50) default NULL COMMENT '公司网址',
  `remark` varchar(200) default NULL COMMENT '备注',
  `message_source` varchar(50) default NULL COMMENT '信息来源',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for work_new_plan
-- ----------------------------
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
  PRIMARY KEY  (`id`),
  KEY `work_plan_info_fk_c` (`plan_classes`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for work_plan_mission
-- ----------------------------
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
  PRIMARY KEY  (`id`),
  KEY `work_plan_mission_fk_c` (`create_plan_id`),
  KEY `work_plan_mission_fk_p` (`plan_id`),
  CONSTRAINT `work_plan_mission_fk_c` FOREIGN KEY (`create_plan_id`) REFERENCES `work_plan_info` (`id`),
  CONSTRAINT `work_plan_mission_fk_p` FOREIGN KEY (`plan_id`) REFERENCES `work_plan_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for workflow_define
-- ----------------------------
CREATE TABLE `workflow_define` (
  `id` varchar(50) NOT NULL default '',
  `name` varchar(50) default NULL,
  `remark` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for workflow_instance
-- ----------------------------
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
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000142', '嘉奖公告', 'zf', 'zf', '<DIV>\r\n<P><FONT size=4>由于OA开发部全体员工的不泄努力，公司终于在昨天推出了公司OA的新版本。</FONT></P>\r\n<P><FONT size=4>在这里我代表公司向OA开发部给予嘉奖，希望其他部门都能向他们学习，共同努力使公司不断向前</FONT></P>\r\n<P><FONT size=4>进步。</FONT></P></DIV>', '', '2006-09-18 14:18:41', '2006-09-01 00:00:00', '2006-09-30 00:00:00', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000143', '共同维护公司良好的运作环境', 'zf', 'zf', '<DIV>\r\n<P><FONT size=4>由于最近公司有新员工的加入，公司不的不重申一下公司规定，希望大家共同维护公司良好的运作</FONT></P>\r\n<P><FONT size=4>环境。</FONT></P></DIV>', '', '2006-09-18 14:19:14', '2006-09-01 00:00:00', '2006-09-30 00:00:00', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000144', '开会通知', 'zf', 'zf', '<DIV>今天下午开会，不能到的请速通知。</DIV>', '', '2006-09-18 14:20:49', '2006-09-01 00:00:00', '2006-09-30 00:00:00', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000161', 'g', 'fdgd', 'fgdf', '<DIV>hfghfghfghgfhfghgfhgfghfghghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh</DIV>', '', '2006-11-17 16:01:05', '2006-11-01 00:00:00', '2006-11-15 00:00:00', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000162', 'sfs', 'dfsd', 'fdsfsdfsd', '<DIV>ffffffffffffffffffffffffffffff</DIV>', '', '2006-11-17 16:01:40', '2006-11-09 00:00:00', '2006-11-08 00:00:00', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000163', 'jj', 'jjjjjjjjjjjj', 'jjjjjjjjjjjj', '<DIV>jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj</DIV>', '', '2006-11-17 16:02:19', '2006-11-15 00:00:00', '2006-11-17 16:02:19', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000181', 'a', 'guxiaofeng', 'hh', '<DIV></DIV>', '', '2006-12-20 15:02:24', '2006-12-20 00:00:00', '2006-12-21 00:00:00', 'N', null);
INSERT INTO `afiche_info` VALUES ('AFICHE_INFO_0000000182', 'ttt', 'ttt', 'ttt', '<DIV></DIV>', '', '2006-12-20 15:04:52', '2006-12-19 00:00:00', '2006-12-19 00:00:00', 'N', null);
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
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000901', 'zhaoyifei', '', 'zhaoyifei,zhanglinlin', 'zhaoyifei', 'zhaoyifei', '你的新邮件', '<DIV>你的新邮件</DIV>', '2006-09-18 14:22:03', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000902', 'zhaoyifei', 'zhaoyifei', 'zhaoyifei,zhanglinlin', 'zhaoyifei', 'zhaoyifei', '你的新邮件', '<DIV>你的新邮件</DIV>', '2006-09-18 14:22:03', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000000921', 'zhaoyifei', '', 'zhaoyifei', '', '', '你将接收到的一封新邮件', '<DIV>你将接收到的一封新邮件</DIV>', '2006-09-19 13:06:37', null, '', '2', '1', '', '2', '1', null, 'N', null);
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
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001082', 'yepuliang', 'guxiaofeng', 'guxiaofeng', 'guxiaofeng', 'guxiaofeng', '论坛后台计划估算', '<DIV>后台时间估算：</DIV>\r\n<DIV>1，用户管理：包括（核心功能用户列表，用户搜索）估算大约一天。</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>2.替换限制处理：（核心功能：帖子词语过滤，保留敏感用户名，屏蔽用户言论）大约1天半</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>3.论坛备份：（</DIV>\r\n<DIV>全部备份：包括全部论坛数据表数据。</DIV>\r\n<DIV>标准备份：包括常用的数据表数据。</DIV>\r\n<DIV>最小备份：仅包括用户、版块设置及系统设置数据。<BR>自定义备份：根据需要自行选择需要备份的数据表）</DIV>\r\n<DIV>半天到1天</DIV>\r\n<DIV>4.日志操作</DIV>\r\n<DIV>密码错误日志，用户评分日志，积分交易日志，版主管理日志，禁止用户日志，后台访问记录，系统错误记录。系统日志，用户日志，版主日志<BR>模块日志，论坛安全日志，积分操作日志</DIV>\r\n<DIV>（估计1天到3天具体情况定）</DIV>\r\n<DIV>5.扩展功能：email群发，短消息群发，计划任务。（1天到2天）</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>6，还有公告和广告的设置（估计一天吧）</DIV>\r\n<DIV>&nbsp;</DIV>\r\n<DIV>很多东西时间我也把握的不太好（大约8天左右）</DIV>', '2006-12-20 11:08:39', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001083', 'zhangfeng', '', 'guxiaofeng', '', '', '工作计划写完了', '<FONT style=\"BACKGROUND-COLOR: #ffffff\">工作计划写完了，有时间的时候看一下!</FONT>\r\n<DIV></DIV>', '2006-12-20 16:10:31', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001101', 'yepuliang', '', 'guxiaofeng', 'guxiaofeng', 'guxiaofeng', '后台管理', '<DIV>1.限制替换处理&nbsp;&nbsp;&nbsp; 1天</DIV>\r\n<DIV>2.论坛备份&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1天</DIV>\r\n<DIV>3.日志&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1天</DIV>', '2007-01-04 13:30:52', null, '', '2', '1', '', '2', '1', null, 'N', null);
INSERT INTO `inemail_info` VALUES ('INEMAIL_INFO_0000001102', 'yepuliang', 'guxiaofeng', 'guxiaofeng', 'guxiaofeng', 'guxiaofeng', '后台管理', '<DIV>1.限制替换处理&nbsp;&nbsp;&nbsp; 1天</DIV>\r\n<DIV>2.论坛备份&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1天</DIV>\r\n<DIV>3.日志&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1天</DIV>', '2007-01-04 13:30:52', null, '', '1', '1', null, '2', '1', null, 'N', null);
INSERT INTO `leaveword_info` VALUES ('LEAVEWORD_INFO_0000000162', 'yy', 'yy', '2006-09-12 13:59:28', '11', '11');
INSERT INTO `leaveword_info` VALUES ('LEAVEWORD_INFO_0000000163', 'yy', 'yy', '2006-09-12 14:00:28', '11', '11');
INSERT INTO `leaveword_info` VALUES ('LEAVEWORD_INFO_0000000181', 'zhaoyifei', 'zhaoyifei', '2006-09-13 13:22:22', 'gffg', 'fggfgffg');
INSERT INTO `leaveword_info` VALUES ('LEAVEWORD_INFO_0000000201', 'zhaoyifei', 'zhaoyifei', '2006-09-13 14:39:33', 'ff', 'fgggfr');
INSERT INTO `leaveword_info` VALUES ('LEAVEWORD_INFO_0000000202', 'zhaoyifei', 'zhaoyifei', '2006-09-13 14:40:14', 'gfdgfd', 'gfdg');
INSERT INTO `leaveword_info` VALUES ('LEAVEWORD_INFO_0000000221', 'yy', 'yy', '2006-09-13 15:40:26', 'yy', 'yyy');
INSERT INTO `news_area` VALUES ('NEWS_AREA_0000000043', 'NEW_STYLE_0000000038', '娱乐新闻', '');
INSERT INTO `news_area` VALUES ('NEWS_AREA_0000000061', 'NEW_STYLE_0000000061', '公司动态', '');
INSERT INTO `news_area` VALUES ('NEWS_AREA_0000000062', 'NEW_STYLE_0000000061', '产品介绍', '');
INSERT INTO `news_area` VALUES ('NEWS_AREA_0000000081', 'NEW_STYLE_0000000101', '电脑新闻', '');
INSERT INTO `news_area` VALUES ('NEWS_AREA_0000000101', 'NEW_STYLE_0000000021', '图片新闻', '');
INSERT INTO `news_area` VALUES ('NEWS_AREA_0000000121', 'NEW_STYLE_0000000021', '标题+内容新闻', '');
INSERT INTO `news_area_style` VALUES ('NEWS_AREA_STYLE_0000000041', 'NEW_STYLE_0000000061', 'NEWS_AREA_0000000061', '001');
INSERT INTO `news_area_style` VALUES ('NEWS_AREA_STYLE_0000000042', 'NEW_STYLE_0000000038', 'NEWS_AREA_0000000043', '004');
INSERT INTO `news_area_style` VALUES ('NEWS_AREA_STYLE_0000000043', 'NEW_STYLE_0000000021', 'NEWS_AREA_0000000062', '002');
INSERT INTO `news_area_style` VALUES ('NEWS_AREA_STYLE_0000000044', 'NEW_STYLE_0000000101', 'NEWS_AREA_0000000081', '003');
INSERT INTO `news_area_style` VALUES ('NEWS_AREA_STYLE_0000000081', 'NEW_STYLE_0000000101', 'NEWS_AREA_0000000081', '005');
INSERT INTO `news_area_style` VALUES ('NEWS_AREA_STYLE_0000000082', 'NEW_STYLE_0000000161', 'NEWS_AREA_0000000121', '006');
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000324', 'NEWS_AREA_0000000043', '今天看到北大青鸟的发的宣传单 ，把 潭浩强 说成C语言之父 ，郁闷 ', 'zf', 'zf', 'zf', 'zf', '0', '2006-09-18 14:13:41', '0', '0', '0', '1', '<P>我承认潭浩强在C语言教育方面的重大贡献，但也不能把说成C语言之父把？不知在C语言方面做过那些重大的贡献，写过重要的c语法规则还是写过高效C编译器？他都没有把？只不过写过一本C的教学书而已啊？而这本书的规则都是别人建立好的，他只不过转述一下而已？</P>\r\n<P>&nbsp;</P>', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000325', 'NEWS_AREA_0000000043', '服务器端Socket编程问题', 'zf', 'zf', 'zf', 'zf', '0', '2006-09-18 14:14:21', '0', '0', '0', '1', '<P>用.net的sockets类写了客户与服务器端程序，在本地和局域网内测试都通过了。<BR>但将服务器端程序放在有固定IP的服务器上，在另一台机器上运行客户端，出现错误提示：<BR>“由于目标机器积极拒绝,无法连接。”<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <BR>服务器端代码基本如下：<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Dim adIP As IPAddress<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; adIP = IPAddress.Parse(\"61.152.xxx.xx\")<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; endPoint = New IPEndPoint(adIP, 2188）<BR>&nbsp;&nbsp;&nbsp;&nbsp; <BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Dim listener As Socket<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; listener = New Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp)<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \'实例化Socket类<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; listener.Blocking = True \'指示Socket处于阻塞模式<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; listener.Bind(endPoint)&nbsp; \'绑定本地的IP地址<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; SetStatus(Status.侦听)&nbsp;&nbsp; \'显示状态<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; listener.Listen(10)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \'侦听</P>\r\n<P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Private Socket&nbsp; As Socket&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \'定义Socket<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Socket = listener.Accept()</P>\r\n<P>服务器未开防火墙，也没有任何杀毒软件。<BR>在客户端Telnet 服务器80端口没问题，但在上面端口5158进行Telnet,提示连接失败.<BR>是否是服务器上设置权限有问题，服务器是Windows 2003 Server 版本。</P>\r\n<P>请高手帮忙，解决立马给分，本人大概有300分，都给。</P>', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000326', 'NEWS_AREA_0000000043', '你愿意做哪一种狐狸', 'zf', 'zf', 'zf', 'zf', '0', '2006-11-22 13:58:37', '0', '0', '0', '1', '&nbsp;盛夏酷暑，一群口干舌燥的狐狸来到一个葡萄架下。一串串晶莹剔透的葡萄挂满枝头，狐狸们馋得直流口水，可葡萄架很高。<BR><BR>第一只狐狸跳了几下摘不到，从附近找来一个梯子，爬上去满载而归。<BR><BR>第二只狐狸跳了多次仍吃不到，找遍四周，没有任何工具可以利用，笑了笑说：“这里的葡萄一定特别酸！”于是，心安理得地走了。<BR><BR>第三只狐狸高喊着“下定决心，不怕万难，吃不到葡萄死不瞑目&nbsp;”的口号，一次又一次跳个没完，最后累死在葡萄架下。<BR><BR>第四只葡萄因为吃不到葡萄整天闷闷不乐，抑郁成疾，不治而亡。<BR><BR>第五只狐狸想：“连个葡萄都吃不到，活着还有什么意义呀！”于是找个树藤上吊了。<BR><BR>第六只狐狸吃不到葡萄便破口大骂，被路人一棒子了却性命。<BR><BR>第七只狐狸抱着“我得不到的东西也决不让别人得到”的阴暗心理，一把火把葡萄园烧了，遭到其他狐狸的共同围剿。<BR><BR>第八只狐狸想从第一只狐狸那里偷、骗、抢些葡萄，也受到了严厉惩罚。<BR><BR>第九只&nbsp;狐狸因为吃不到葡萄气极发疯，蓬头垢面，口中念念有词：“吃葡萄不吐葡萄皮……”<BR><BR>另有几只狐狸来到一个更高的葡萄架下，经过友好协商，利用叠罗汉的方法，成果共享，皆大欢喜。<BR>\r\n<P>当时我看到这个文章。不是针对程序员的。不过我发现 这9个狐狸更能代表每一种程序员。甚至是一个团队。<BR>请大家对号入座。</P>\r\n<P>&nbsp;&nbsp;&nbsp; 同时更加欢迎大家 回复的时候扩充&nbsp; 更多的狐狸.</P>', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000401', 'NEWS_AREA_0000000043', '22', '21', '22', '22', '22', '0', '2006-12-27 13:09:40', '0', '0', '0', '1', '212', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000421', 'NEWS_AREA_0000000061', '1111111111111', 'yy', '222', '111', '111', '0', '2007-01-29 13:02:08', '0', '0', '0', '1', '<P>111111111111111111111111111111111111111111111111111111</P>\r\n<P>22222222222222222222222222222222222</P>', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000422', 'NEWS_AREA_0000000061', '2222222', 'yy', '22', '22', '22', '0', '2007-01-29 15:02:15', '0', '0', '0', '1', '222222222222222222222222222222222222222222222', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000426', 'NEWS_AREA_0000000062', '111111', '2222222222', '2222222', '22222222', '22222222', '0', '2007-01-29 13:03:34', '0', '0', '0', '1', '22222222222222222', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000461', 'NEWS_AREA_0000000081', '盗销一条龙 熊猫烧香被疑幕后黑手推动', 'yy', 'yy', 'yy', 'yy', '0', '2007-01-29 16:41:04', '0', '0', '0', '1', '盗号产销一条龙现身网上，一年收入可赚一座别墅 \r\n<P>　　“就在很多人怀疑熊猫烧香病毒是一个15岁武汉男孩制作的时候，它背后的黑势力近日已经现身，网上已经发现了产销一条龙盗窃销售网游设备的产业链。”昨日，江民公司反病毒工程师向记者“报料”，称已经发现了近期疯狂肆虐的“熊猫烧香”幕后势力的痕迹。</P>\r\n<P>　　反病毒工程师向记者展示的一份证据显示，在他们截获的病毒中，已经发现“熊猫烧香”的作者通过http：//www.feifeic<A href=\"http://download.zol.com.cn/detail/12/111665.shtml\">QQ</A>.com等几家地下网站公开销售<A href=\"http://youxi.zol.com.cn/ol/ol_1.html\">网络游戏</A>的装备，“他们这些网站可以称为是盗号、买卖一条龙，现在看来熊猫烧香的背景远没有那么简单。”反病毒工程师告诉记者。</P>\r\n<P>　　据了解，熊猫烧香本身就是一种下载程序，会在指定的网站下载后门、<A href=\"http://download.zol.com.cn/detail/11/109844.shtml\">木马</A>、各种盗号程序。</P>\r\n<P>　　记者随后访问被杀毒公司侦查到的几个网络连接，但却均显示“无法访问”，对此反病毒工程师称“现在打不开只能说是这个病毒作者开始警惕了，只要作者愿意，一年可以靠卖盗窃产品赚一座别墅。”</P>\r\n<P>　　据了解，目前已经转入盗销一条龙的几家网站已经有所警觉，几家网站都已经采取了应对措施。而之前，几家杀毒公司都已经向公安机关报案。</P>\r\n<P>　　据反病毒专家介绍，近期熊猫烧香的新变种中，病毒作者还在病毒体中留下了“超级巡警终于火了”的言论，因此有不少网友怀疑此次“熊猫烧香”病毒是由“超级巡警”软件提供方幕后推动。但该说法并未得到国内几家杀毒公司的肯定，称目前除了病毒体中的那句留言尚未有太多证据显示是该团体在幕后推动。</P>\r\n<P>　　自2005年以来，计算机病毒作者常常以获取经济利益为目标，之前出现的网银大盗、证券大盗、游戏大盗、QQ大盗等都是如此，而这些都已经触犯了我国的刑法。2006年5月“证券大盗”木马病毒作者一审被判无期徒刑，而“熊猫烧香”病毒的真相何时会水落石出，本报将继续关注。</P>', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000462', 'NEWS_AREA_0000000081', '电脑最忌讳的18个小动作', 'yy', 'yy', 'yy', 'yy', '0', '2007-01-29 16:41:21', '0', '0', '0', '1', '<SPAN style=\"FONT-SIZE: 12px\">1、大力敲击回车键这个恐怕是人所共有的通病了，因为回车键通常是我们完成一件事情时，最后要敲击的一个键，大概是出于一种胜利的兴奋感，每个人在输入这个回车键时总是那么大力而爽快地敲击。本人的多个键盘就是这样报废的，最先不看见字的是AWSD（呵呵，心知肚明），最先不能使用的按键却是Enter。<BR>　　<BR>解决办法：解决方法有两个，第一是控制好你的情绪，第二是准备好你的钱包。我选的第二个，有时候好心情是钱买不来的，你呢？<BR>2、在键盘上面吃零食，喝饮料<BR>这个习惯恐怕是很普遍了，我看到很多人都是这样的，特别是入迷者更是把电脑台当成饭桌来使用。我想你要是拆一回你的键盘，也许同样的行为就会减少的，你可以看到你的键盘就像水积岩一样，为你平时的习惯，保留了很多的“化石”，饭粒、饼干渣、头发等等比比皆是，难怪有人说：公用机房里的键盘比公厕还脏。同时这样的碎片还可能进入你的键盘里面，堵塞你键盘上的电路，从而造成输入困难。饮料的危害就更加厉害了，一次就足以毁灭你的键盘。就是你的键盘侥幸没有被毁灭，恐怕打起字来，也是粘粘糊糊很不好过。<BR>解决方法：避免在键盘上吃东西，要不然像我一样买一个防水的PHILIPS键盘，然后每过一段时间就给他打扫卫生，擦澡（虽然这样还是很脏的）；你要是腰包更加饱的话，可以考虑半年换一个键盘（我从来不建议用差的键盘，那可是关乎健康的问题）试试，应该情况会好一些。还有记得给你房间买一个饭桌了。<BR><BR>3、光碟总是放在光驱里(还有看VCD时,暂停后出玩或吃饭!!!)<BR>很多人总是喜欢把光碟放在光驱里，特别是CD碟，其实这种习惯是很不好的。光碟放在光驱里，光驱会每过一段时间，就会进行检测，特别是刻录机，总是在不断的检测光驱，而高倍速光驱在工作时，电机及控制部件都会产生很高的热量，为此光驱厂商们一直在极力想办法解决。<BR>　　虽然现在已有几种方法能将光驱温度控制在合理的范围内，但如果光驱长时间处于工作状态，那么，即使再先进的技术也仍无法有效控制高温的产生。热量不仅会影响部件的稳定性，同时也会加速机械部件的磨损和激光头的老化。所以令光驱长时间工作，实在是不智之举，除非你想把你的光碟和光驱煮熟。<BR><BR>解决方法：尽量把光碟上的内容转到硬盘上来使用，比如把CD转化为MP3的，如果你是一个完美主义者，那就用<A style=\"TEXT-DECORATION: underline\" href=\"http://download.zol.com.cn/detail/16/152313.shtml\" target=_blank>虚拟光驱</A>的形式管理你的常用CD碟吧；游戏则尽量使用硬盘版的；大多数光碟版的游戏，都可以在网上找到把光碟版转化为硬盘版的<A style=\"TEXT-DECORATION: underline\" href=\"http://soft.zol.com.cn/\" target=_blank>软件</A>；不然就同样采用虚拟光驱的形式。网上有很多虚拟光驱可以<A style=\"TEXT-DECORATION: underline\" href=\"http://download.zol.com.cn/\" target=_blank>下载</A>，怕麻烦的话可以用国产的《东方光驱魔术师3》或《VirtualDrive7.0》， 界面很简单，而且没有了E文的问题，很好上手。<BR><BR>4、关了机又马上重新启动<BR>经常有人一关机就想起来光碟没有拿出来，或者还有某个事情没有完成等等，笔者就是其中一个，可以说有同样毛病的人还是很多的。很多人反应迅速，在关闭电源的刚刚完成就能想起来，然后就伸出手来开机；更有DIY好手，总是动作灵敏，关机，十秒钟处理完故障，重新开机；殊不知这样对计算机危害有多大。<BR>首先，短时间频繁脉冲的电压冲击，可能会损害计算机上的集成电路；其次，受到伤害最大的是硬盘，现在的硬盘都是高速硬盘，从切断电源到盘片完全停止转动，需要比较长的时间。如果盘片没有停转，就重新开机，就相当于让处在减速状态的硬盘重新加速。长此下去，这样的冲击一定会使得你的硬盘一命归西的。<BR><BR>解决办法：关机后有事情忘了做，也就放下他；一定要完成的，请等待一分钟以上再重新开机，要不就在机子没有断开电源的时候按下机箱上的热启动键。要是你以上的方法都做不到，为了你爱机的健康，我建议你在电脑桌上系一个绳子，以便用来绑住你的手一分钟以上。<BR><BR>5、开机箱盖运行<BR>开机箱盖运行一看就知道是DIY们常干的事情。的确开了机箱盖，是能够使得CPU凉快一些，但是这样的代价是以牺牲其它配件的利益来实现的。因为开了机箱盖，机箱里将失去前后对流，空气流将不再经过内存等配件，最受苦的是机箱前面的光驱和硬盘们，失去了对流，将会使得他们位于下部的电路板产生的热量变成向上升，不单单散不掉，还用来加热自己，特别是刻录机，温度会比平时高很多。<BR>　　不信你比较一下开不开机箱盖的光驱温度。开机箱盖还会带来电磁辐射，噪音等危害，而且会使得机箱中的配件更加容易脏，带来静电的危害，并阻碍风扇的转动。同时，让其他隐患有机可乘，比如你在电脑前边喝茶边观看一部片子，一个爆笑的镜头使你将口中的清茶悉数喷进了敞开的机箱内……<BR><BR>解决办法：很简单，给你机箱盖锁上锁头，然后把钥匙寄给我。要是怕超频不稳定，就不要超频了，现在的CPU够快了，在市场上的主流CPU就够用了。要是你用的是老掉牙的CPU，我建议你还是换一个的好，换一个也就是300 元左右（赛扬2或3、毒龙1.2G）何必受提心吊胆和电磁辐射、噪音的苦？还是那句话：快乐是用钱买不到的。<BR><BR>6、用手摸屏幕<BR>其实无论是CRT或者是LCD都是不能用手摸的。计算机在使用过程中会在元器件表面积聚大量的静电电荷。最典型的就是显示器在使用后用手去触摸显示屏幕，会发生剧烈的静电放电现象，静电放电可能会损害显示器，特别是脆弱的LCD。<BR>　　另外，CRT的表面有防强光、防静电的AGAS(Anti-GlareAnti-Static)涂层，防反射、防静电的ARAS(Anti- ReflectionAnti-Static)涂层，用手触摸，还会在上面留下手印，不信你从侧面看显示器，就能看到一个个手印在你的屏幕上，难道你想帮公安局叔叔们的忙，提前提取出伤害显示器“凶手”的指纹吗？同时，用手摸显示器，还会因为手上的油脂破坏显示器表面的涂层。<BR>LCD显示器比CRT 显示器脆弱很多，用手对着LCD显示屏指指点点或用力地戳显示屏都是不可取的，虽然对于CRT显示器这不算什么大问题，但LCD显示器则不同，这可能对保护层造成划伤、损害显示器的液晶分子，使得显示效果大打折扣，因此这个坏习惯必须改正，毕竟你的LCD显示器并不是触摸屏。<BR><BR>解决方法：在你的显示器上贴一个禁止手模的标志，更不能用指甲在显示器上划道道；想在你的屏幕上“指点江山”，就去买一个激光指定笔吧。强烈的冲击和振动更应该避免， LCD显示器中的屏幕和敏感的电器元件如果受到强烈冲击会导致损坏；显示器清洗应当在专门的音像店里买到相应的清洗剂，然后用眼镜布等柔软的布轻轻擦洗。<BR><BR>7、一直使用同一张墙纸或具有静止画面的屏保<BR>无论是CRT或者是LCD的显示器，长时间显示同样的画面，都会使得相应区域的老化速度加快，长此下去，肯定会出现显示失真的现象。要是你有机会看看机房里的计算机，你就会发现，很多上面已经有了一个明显的画面轮廓。何况人生是多姿多彩的，何必老是用同一副嘴脸呢？<BR><BR>解决措施：每过一定的时间就更换一个主题，最好不要超过半年。平时比较长时间不用时，可以把显示器关掉。要是你没有这样的习惯，可以在显示属性的屏幕保护那里设定好合适的时间，让WINDOWS帮你完成。<BR><BR>8、把光碟或者其他东西放在显示器上。<BR>显示器在正常运转的时候会变热。为了防止过热，显示器会吸入冷空气，使它通过内部电路，然后将它从顶端排出。不信你现在摸摸你放在上面的光碟，是不是热热的象烙饼？若你总是把光碟或纸张放在显示器上头；更加夸张的是让你家猫咪冬天时在上头蜷着睡觉，当显示器是温床，这会让热气在显示器内部累积的。那么色彩失真、影像问题、甚至坏掉都会找上你的显示器。<BR><BR>解决办法：如果你想让显示器保有最好的画质，以及延长它的寿命，赶快叫醒你的猫咪，让它到别处去睡吧。并把你的“烙饼”收到光碟袋里去。<BR><BR>9、拿电脑主机来垫脚<BR>如果想要杀死你的台式计算机，那么开车带它去越野兜风，或是背着它去爬山、蹦迪，那样会更快一些；你的这种方法震动太小了，要比较长的时间才能出成绩。如果你愿意坚持下去，估计取得的第一个成绩就是产生一出个圆满归西的是硬盘吧，死因是硬盘坏道。<BR><BR>解决方法：把你把脚架在电脑上的照片作为你的桌面，让你看看那一个姿势有多难看，这样你就不会把脚再次伸向主机；要不然就把你的电脑发票贴在显示器上，看着发票上的金额，你应该不会无动于衷吧。如果上面的方法都不能制止你的行为的话，我想你就该考虑去买一个带有脚扣的椅子了。<BR><BR>10、计算机与空调、电视机等家用电器使用相同的电源插座<BR>这是因为带有电机的家电运行时会产生尖峰、浪涌等常见的电力污染现象，会有可能弄坏计算机的电力系统，使你的系统无法运作甚至损坏。同时他们在启动时，也会和计算机争夺电源，电量的小幅减少的后果是可能会突然令你的系统重启或关机。<BR>　　<BR>解决方法：为了你的计算机不挨饿或者是吃的“食物（电力）”不干净，首先应使用品质好的计算机开关稳压电源，如长城等品牌。其次，对于一些电力环境很不稳定的用户，建议购买UPS或是稳压电源之类的设备，以保证为计算机提供洁净的电力供应。还有就是优化布线，尽量减少各种电器间的影响。<BR>11、给你的计算机抽二手烟<BR>　　就像香烟、雪茄或微小烟粒会伤害你的肺一样，烟也可能会跑进你的软驱并危及资料。烟雾也可能会覆盖CD-ROM、DVD驱动器的读取头，造成读取错误。烟头烟灰更有可能使得你的<A style=\"TEXT-DECORATION: underline\" href=\"http://detail.zol.com.cn/print_index/subcate11_list_1.html\" target=_blank>打印机</A>和扫描仪质量大大的下降。<BR>　　<BR>解决方法：要保护你的系统和你自己的最佳方式，就是不要抽烟。如果你就是戒不掉抽烟这个习惯的话，到外面去抽，或在计算机四周打开空气清新器吧！当然更不要把你的键盘当烟灰缸用。<BR>看完了硬件方面的问题，我们来看看在软件方面的问题吧。<BR><BR>12、不停的更换驱动程序<BR>　　很多的DIY很喜欢不断的更新驱动程序，虽然更新驱动程序有可能提升性能和兼容性，但是不适当的新版本可能会引起硬件功能的异常，在旧版本运转正常的时候建议不要随意升级驱动。先仔细阅读驱动的README文件，对你有好处。就是像显卡这样更新换代迅速的硬件最好不要总是追新，不要随便使用最新版的驱动程序，应该使用适合自己硬件情况的驱动程序，因为每一代的驱动程序都是针对当时市面上最流行的显卡芯片设计，老芯片就不要随便使用新的驱动，更不要随便使用测试版的驱动，测试版的驱动就先留给网站的编辑们去测试他们的系统。<BR>　　<BR>解决方法：到专业的网站上去看看新驱动的介绍，我觉得最保险的办法是：显卡最多用芯片推出半年后的驱动；主板最多用芯片组推出6-9个月的驱动；声卡等最多用推出一年后的驱动；再往后的驱动就不要用了（除非你试过前面的驱动统统有问题）。<BR><BR>13、装很多测试版的或者共享版的软件<BR>　　追新一族总是喜欢在自己的机子用上最新的软件，和驱动程序一样，更新程序有可能提升性能、增加功能和兼容性，但是不适当的新版本可能会引起系统的异常。特别是测试版的程序，更是害处更多，既然没有推出正式版，就说明该软件还存在着很多不确定的BUG，这些小虫就像定时炸弹一样，随时可能在你的系统中爆炸，损坏你的系统。<BR>　　共享版的软件有一些过一段时间（或次数）就会失效，要是你的系统通过共享版软件更改了某方面的功能，而共享版软件又因为失效而无法运行，那么你的系统就不能回到你想要的状态了；还有就是使用了共享版的软件来建立的资料或者文档，因为共享版软件失效，而无法打开。所以安装共享版时应当注意共享版提供使用的次数或者时间，以免无法还原系统和丢失资料。<BR>　　<BR>解决办法：如果不是一定要使用新版本才能解决问题的话，尽量使用最新的正式版的软件，测试的工作就留给专家们去完成吧。尽可能注册你的共享版软件，不然就要注意共享版软件的限制，以免丢失重要的文件或者损害系统。<BR><BR>14、在系统运行中进行非正常重启<BR>　　在系统运行时，进行非正常重启（包括按机箱上的重启键、电源键和Ctrl Alt Del），可能使得系统丢失系统文件、存盘错误以及丢失设置等。本来windows是提供了磁盘扫描工具，可以纠正部分出错的文件，但是因为扫描需要一段比较长的时间，很多人都会中断他的工作，经常出现这样的情况，还有可能使得硬盘上的数据的出错几率和次数大大增加，从而使得整个系统崩溃。<BR>　　<BR>解决方法：尽量使用比较稳定的系统，建议CPU频率在750MHz和内存在256M以上的用户使用WINXP的系统，其他的可以考虑WIN2000，还有就是最好把硬盘转化为HTFS的格式，它比FAT32的格式要更加安全，不容易出错。还有就是FAT32和FAT16的用户，最好让磁盘扫描工具执行完它的工作。当然最好的办法是找出死机的原因，杜绝此类现象的出现。<BR><BR>15、不扫描和整理硬盘<BR>　　经常看到很多人的硬盘里充满了错误和碎片，总是觉得很不好受，其实那些东西不但会使得你的系统出错的几率加大，还有可能让你的系统变的很慢，甚至无法运行。其实很好理解这样的坏处，就像你的房间东西到处扔，还有的缠在一起、甚至损坏了，当然找起东西来效率很低，碰到缠住的，还要先解开；甚至找到了也用不了，因为他们是坏的。<BR>　　<BR>解决方法：平时记得给你硬盘打扫卫生，每过一段时间就应该清理一下硬盘，并且进行整理。如果是添加删除操作比较多的用户，应当一个月整理一次，普通的用户可以三个月整理一次。WINDOWS自带的磁盘整理工具效率很低，可以使用VoptXP、诺顿等工具来提高速度<BR><BR>16、虚拟内存不指定范围<BR>　　虚拟内存顾名思义就是在硬盘上用硬盘的空间模拟内存，以保证大过系统内存的内存请求，保证程序的运行。一般WINDOWS默认是由WINDOWS自己管理虚拟内存的大小，这样的话有两个坏处：首先每次请求的数值并不一致，所以系统会随意在硬盘分区上划出一个地方，存放临时文件，过后又没有及时删除，使得硬盘上的碎片增多，从而影响系统的效率。其次，一般WINDOWS都是指定自己所在的硬盘做为虚拟内存的存放的默认盘，但是由于很多的用户总是把程序装在同一个分区下，使得该分区的空间越来越小，也就是说虚拟内存能使用的空间在减少，少于一定的程度时，将不能执行大型的软件，甚至无法进入系统。　　<BR>解决方法：应当手动指定虚拟内存的位置和大小，原则上指定系统虚拟内存的大小为512M以上为好（最小和最大空间一样，以保证WINDOWS不会改变位置），位置看看那个分区有多余的空间就行了。<BR><BR>17、不用卸载，而是直接删除文件夹<BR>　　很多的软件安装时会在注册表和SYSTEM文件夹下面添加注册信息和文件，如果不通过软件本身的卸载程序来卸载的话，注册表和SYSTEM文件夹里面的信息和文件将永远残留在里面。他们的存在将会使得你的系统变得很庞大，效率越来越低下，超过你的忍耐限度，你就不得不重装你的系统了。<BR>　　<BR>解决办法：删除程序时，应当到控制面板中的删除添加程序去执行（你可以做一个快捷方式在桌面上就方便多了），或者在开始菜单栏中找到程序的目录里的删除快捷方式，通过它来删除程序。还有就是尽量使用绿色免安装的软件。<BR><BR>18、加载或者安装太多同样功能的软件<BR>　　同样功能的软件势必会行使相同功能的职责，从而引起争端。我认为相同功能的软件应当有所取舍，选择最适合自己使用习惯的软件。特别防病毒软件应当选择一个就可以了，而不是同时加载很多个在系统后台，加载太多会产生消耗太多的系统资源、软件冲突的弊端，在发现病毒时，还有可能出现因为“争杀”病毒而引起系统崩溃的麻烦；就单单对着多个弹出的窗口，分别进行处理，也是一个麻烦。<BR>　　<BR>解决方法：尽可能“从一而终”，不要太花心，选择一个适合自己使用习惯的软件，其他的可以卸载掉；正确对待防病毒软件，应当选择一个病毒库更新速度快的防病毒软件，并及时将自己的病毒库更新到最新的病毒库，而不是安装多个防病毒软件。<BR>人无完人，知错就改就是好同志。我们要改正平时的一些不良的用机习惯，毕竟电脑是我们的好伙伴，不要因为某些错误习惯而一再伤害我们的朋友</SPAN> <BR>', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000463', 'NEWS_AREA_0000000081', '卡巴斯基悬赏20万元 全国范围紧急打假', 'y', 'y', 'y', 'y', '0', '2007-01-29 16:47:07', '0', '0', '0', '1', '反病毒软件被大肆盗版，正版软件用户权益受到严重伤害，在得知盗版消息后，为维护良好的信息网络环境，一向以维护用户权益为己任的<A href=\"http://download.zol.com.cn/detail/16/156877.shtml\">卡巴斯基</A>即刻采取行动。1月10日，卡巴斯基携手数字星空紧急约见在京媒体记者，召开重金悬赏打假新闻通告会，力争通过全民参与的方式查出造假“真凶”，还反病毒市场一片晴天。\r\n<P>&nbsp;&nbsp;&nbsp; 在记者见面会上，卡巴斯基董事总经理张立申发布了打假悬赏令，悬赏令告示：在2007年1月10日至3月10日期间，凡是提供盗版卡巴斯基产品源头信息的单位和个人，在经司法机关核实后，都可获得由卡巴斯基提供的专项打假奖金，奖金总额为20万元人民币。张立申认为消费者与卡巴斯基一样都是受害者，卡巴斯基也希望通过自己的行动掀起全民打假的热潮，只有用消费者的力量，才可以真正打赢反盗版的人民战争。<BR><BR>&nbsp;&nbsp;&nbsp; 面对市场上众多的反病毒品牌，为何造假者偏偏对卡巴斯基“情有独钟”呢？有业内权威人士分析认为：一方面说明了卡巴斯基品牌的高影响力及产品的高消费认知度，造假者都是惟利分子，一个无市场、低影响力的品牌无法令其“动心”；另一方面，造假者也“准确”把握住消费者对高品质产品的需求心理，借卡巴斯基尚未推出新版本的“契机”，趁机作案。</P>\r\n<P>&nbsp;&nbsp;&nbsp; 据卡巴斯基总裁助理许辉介绍，近日，公司客服人员经常接到诸如“2007版不能激活”等相关咨询问题，对于从未按年份命名产品版本的卡巴斯基来说，此类问题的出现无疑证明了一件事情――产品遭受盗版。为此，卡巴斯基公司在对全国市场进行调查时发现，在浙江、广州、武汉等地存在大批量、多版本的盗版盒装卡巴斯基上市销售，由于盗版产品的品质及服务都无法得到保障，使众多消费者的正当权益遭到侵害，并且严重干扰了正版产品的销售渠道。</P>\r\n<P>&nbsp;&nbsp;&nbsp; 为了彻底根除造假行为，作为卡巴斯基反病毒软件个人版在中国大陆独家总发行商的数字星空，也表示将全力配合此次活动。据数字星空CEO刘建华介绍，在此次记者见面会后，双方将在国内八个核心城市展开悬赏打假路演活动；同时，数字星空在经卡巴斯基授权后，将就盗版事件向国家有关部门报案，进行立案侦察，从而掀起一波自上而下的打假热潮，令造假者无处藏身，最终将其绳之以法。</P>', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000464', '0', '劲减6000元 华硕双核PRO80超低价促销', 'yy', 'yy', 'yy', 'yy', '0', '2007-01-29 16:51:25', '0', '0', '0', '1', '华硕Pro80系列为去年11月华硕发布的8款商用机型中的一款。在这8款产品中，Pro80为一款性价比较高的机型。近日，从市场方面得到的消息，PRO80系列的Pro80（23E580DRHJn-V）超低价促销，目前的市场售价为7500元，较之前的报价下降了6000元。具有了很高的性价比，这很有可能是因为这款产品已经过了新品期，价格开始走上正轨的关系。', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000465', 'NEWS_AREA_0000000081', '闪龙3500+跌破400元 内存价暴跌不止', 'yy', 'yy', 'yy', 'yy', '0', '2007-01-29 16:56:31', '0', '0', '0', '1', '今日北京中关村现货CPU市场上，AMD处理器价格再次进入下降态势，但个别热点产品仍有所上涨，本次涉及到的产品线全部是AM2接口。其中AM2接口闪龙集体降价，规格从2800+到3500+，最高跌幅达到40元。AM2接口速龙3200+/3500+分别上涨10元。AM2接口双核速龙X2 2600+/3800+/4200+分别下跌30/40/10元。今天北京CPU现货市场上，英特尔处理器依然维持着降价态势，但个别主流产品缺货上涨，总体波动幅度并不大。其中478接口赛扬系列多款产品价格下降，幅度在5-20元之间；775接口双核酷睿2 E6300缺货小涨15元，而E6400却大降30元；775接口奔腾4以及双核奔腾D个别型号小跌5-10元。\r\n<P>　　今日北京台式机<A href=\"http://detail.zol.com.cn/memory_index/subcate3_list_1.html\">内存</A>现货市场上，价格跳水不止，暴跌仍在继续，涉及到的品牌有现代、kingmax、宇瞻、金士顿和金邦。其中现代兼容条三款产品小跌5元；宇瞻DDR2主力产品跌幅在10-30元之间；kingmax和金士顿绝大部分产品参与降价，幅度在5-30元之间，通过连日来的跳水，目前金士顿全线产品性价比优势明显；金邦白金系列DDR2内存跌幅也达到了10-30元。今天北京<A href=\"http://detail.zol.com.cn/notebook_index/subcate16_list_1.html\">笔记本</A>内存现货市场上，价格依旧维持着下探态势，但涉及到的产品数量却不多，降价规模也不大。其中KINGMAX 1GB DDR333突然大降40元；金士顿512MB DDR333小降10元；英飞凌全线产品齐跌10元。</P>\r\n<P>　　近一段时间内地<A href=\"http://detail.zol.com.cn/hard_drives_index/subcate2_list_1.html\">硬盘</A>渠道供应不畅通，今天北京台式机硬盘现货市场上，价格总体保持平稳态势，但市场中出货量最大的希捷160GB硬盘全线缺货，价格也因此小幅度上涨了5元，其他品牌产品则没有丝毫变化。今天北京笔记本硬盘现货市场上，希捷和日立全体产品价格平稳，没有一款型号出现涨跌。此外，日立40GB依然处于断货状态中。</P>', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000466', 'NEWS_AREA_0000000081', '超频幅度近100% E4300处理器延伸测试', 'yy', 'yy', 'yy', 'yy', '0', '2007-01-29 17:08:34', '0', '0', '0', '1', '<P><B><FONT face=Arial>序：98%的超频幅度意味着什么？意味着将近一倍的频率提升，意味着60%以上的性能增长，更意味着新一代超频利器的诞生……</FONT></B></P>\r\n<P><FONT face=Arial>　　基于Core微体系结构、Conroe核心设计的<A href=\"http://detail.zol.com.cn/cpu_index/subcate28_125_list_1.html\">Intel</A> Core 2 Duo/Extreme系列双核心处理器无疑引发了新一轮的超频热潮，而这也使得我们对新款C2D E4300处理器的超频能力有了更多的期待。高倍频（x 9）低外频（200MHz）的设计为它成为一名超频好手提供了充分的先决条件，而这也正是E4300最大的附加价值所在。</FONT></P>', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000481', 'NEWS_AREA_0000000061', '公司动态3', 'yy', 'yy', 'yy', 'yy', '0', '2007-01-30 15:57:50', '0', '0', '0', '1', 'yyy', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000482', 'NEWS_AREA_0000000061', '公司动态2', 'yy', 'yy', 'yy', 'yy', '0', '2007-01-30 15:57:37', '0', '0', '0', '1', 'yyy', '0', '', '', 'N', null);
INSERT INTO `news_article` VALUES ('NEWS_ARTICLE_0000000541', 'NEWS_AREA_0000000121', 'Java Communications', '111', '11', '11', '11', '0', '2007-02-07 10:42:21', '0', '0', '0', '1', 'The Java Communications 3.0 API is a Java extension that facilitates developing platform-independent communications applications for technologies such as Smart Cards, embedded systems, and point-of-sale devices, financial services devices, fax, modems, display terminals, and robotic equipment', '0', '', '', 'N', null);
INSERT INTO `news_style` VALUES ('NEW_STYLE_0000000021', '样式2', '4', '1', '22', '#000000', '', '12px', '', '', '', '', '', '', '', '1', '1', '1', '1', 'desc');
INSERT INTO `news_style` VALUES ('NEW_STYLE_0000000038', '样式4', '4', '1', '12', '#FF0000', '', '12px', '25', '1', '1', '1', '1', '1', '', '1', '1', '1', '2', 'desc');
INSERT INTO `news_style` VALUES ('NEW_STYLE_0000000061', '样式1', '4', '1', '15', '#008000', '', '12px', '', '', '', '', '', '', '', '', '', '', '1', 'desc');
INSERT INTO `news_style` VALUES ('NEW_STYLE_0000000101', '样式3', '8', '1', '9', '#FF0000', '', '12px', '', '1', '1', '', '', '1', '', '', '', '', '1', 'desc');
INSERT INTO `news_style` VALUES ('NEW_STYLE_0000000121', '图片新闻', '6', '1', '2', '#000000', '', '12px', '', '', '', '', '', '', '', '', '', '', '1', 'desc');
INSERT INTO `news_style` VALUES ('NEW_STYLE_0000000161', '标题+内容', '1', '2', '10', '#FF0000', '', '18px', '25', '', '', '', '', '', '', '', '', '', '1', 'desc');
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
INSERT INTO `sys_key` VALUES ('1', '20');
INSERT INTO `sys_key` VALUES ('ADDRESSLISTSORT_INFO', '440');
INSERT INTO `sys_key` VALUES ('ADDRESSLIST_INFO', '240');
INSERT INTO `sys_key` VALUES ('AFICHE_INFO', '200');
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
INSERT INTO `sys_key` VALUES ('GOV_ANTITHESES_INFO', '40');
INSERT INTO `sys_key` VALUES ('GOV_DRIVER_INFO', '160');
INSERT INTO `sys_key` VALUES ('GOV_DRIVER_OPER', '40');
INSERT INTO `sys_key` VALUES ('GOV_MOTOR_INFO', '160');
INSERT INTO `sys_key` VALUES ('HANDSET_NOTE_INFO', '140');
INSERT INTO `sys_key` VALUES ('INADJUNCT_INFO', '240');
INSERT INTO `sys_key` VALUES ('INEMAIL_INFO', '1120');
INSERT INTO `sys_key` VALUES ('LEAVEWORD_INFO', '260');
INSERT INTO `sys_key` VALUES ('NEWS_AREA', '140');
INSERT INTO `sys_key` VALUES ('NEWS_AREA_STYLE', '100');
INSERT INTO `sys_key` VALUES ('NEWS_ARTICLE', '560');
INSERT INTO `sys_key` VALUES ('NEW_STYLE', '180');
INSERT INTO `sys_key` VALUES ('PLAN_DETAIL', '240');
INSERT INTO `sys_key` VALUES ('PLAN_INFO', '620');
INSERT INTO `sys_key` VALUES ('RESOURCE_INFO', '380');
INSERT INTO `sys_key` VALUES ('RESOURCE_USE', '520');
INSERT INTO `sys_key` VALUES ('SYNOD_NOTE', '500');
INSERT INTO `sys_key` VALUES ('SYSRIGHTUSER', '4160');
INSERT INTO `sys_key` VALUES ('SYS_DEPARTMENT', '160');
INSERT INTO `sys_key` VALUES ('SYS_GROUP', '140');
INSERT INTO `sys_key` VALUES ('SYS_LOG', '1440');
INSERT INTO `sys_key` VALUES ('SYS_MODULE', '880');
INSERT INTO `sys_key` VALUES ('SYS_ROLE', '200');
INSERT INTO `sys_key` VALUES ('SYS_STATION_INFO', '20');
INSERT INTO `sys_key` VALUES ('SYS_TREE', '440');
INSERT INTO `sys_key` VALUES ('TEST_KEY', '60');
INSERT INTO `sys_key` VALUES ('TEST_KS_NO_TABLE', '60');
INSERT INTO `sys_key` VALUES ('WORKFLOW_INSTANCE', '240');
INSERT INTO `sys_key` VALUES ('WORK_PLAN_INFO', '1120');
INSERT INTO `sys_key` VALUES ('WORK_PLAN_MISSION', '960');
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
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000701', 'jingyuzhuo', '2006-12-29 08:51:47', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000702', 'jingyuzhuo', '2006-12-29 08:52:07', '我的详细计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000703', 'jingyuzhuo', '2006-12-29 08:52:10', '我的阶段计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000704', 'jingyuzhuo', '2006-12-29 08:52:13', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000705', 'jingyuzhuo', '2006-12-29 08:52:16', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000706', 'jingyuzhuo', '2006-12-29 08:52:18', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000707', 'jingyuzhuo', '2006-12-29 08:52:37', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000708', 'jingyuzhuo', '2006-12-29 08:52:47', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000709', 'jingyuzhuo', '2006-12-29 08:52:48', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000710', 'jingyuzhuo', '2006-12-29 08:52:49', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000711', 'jingyuzhuo', '2006-12-29 08:52:50', '我的阶段计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000712', 'jingyuzhuo', '2006-12-29 08:52:52', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000713', 'jingyuzhuo', '2006-12-29 08:53:21', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000714', 'jingyuzhuo', '2006-12-29 08:53:28', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000715', 'jingyuzhuo', '2006-12-29 08:53:41', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000716', 'jingyuzhuo', '2006-12-29 08:53:53', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000717', 'jingyuzhuo', '2006-12-29 08:53:54', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000718', 'jingyuzhuo', '2006-12-29 08:53:56', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000719', 'jingyuzhuo', '2006-12-29 08:54:04', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000720', 'jingyuzhuo', '2006-12-29 08:54:14', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000721', 'jingyuzhuo', '2006-12-29 08:54:24', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000722', 'jingyuzhuo', '2006-12-29 09:04:36', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000723', 'jingyuzhuo', '2006-12-29 09:04:38', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000724', 'jingyuzhuo', '2006-12-29 09:04:44', '详细工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000725', 'jingyuzhuo', '2006-12-29 09:10:47', '计划审批', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000726', 'jingyuzhuo', '2006-12-29 09:10:52', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000727', 'jingyuzhuo', '2006-12-29 09:14:47', '阶段工作计划', 'lookup', '192.168.1.123', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000728', 'yepuliang', '2007-01-04 13:23:48', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000729', 'yepuliang', '2007-01-04 13:23:51', '详细工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000730', 'yepuliang', '2007-01-04 13:26:53', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000731', 'yepuliang', '2007-01-04 13:27:01', '详细工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000732', 'yepuliang', '2007-01-04 13:27:54', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000733', 'yepuliang', '2007-01-04 13:28:13', '内部邮件', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000734', 'yepuliang', '2007-01-04 13:31:07', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000735', 'yepuliang', '2007-01-04 13:31:09', '详细工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000736', 'yepuliang', '2007-01-04 13:31:10', '我的阶段计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000737', 'yepuliang', '2007-01-04 13:31:12', '我的详细计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000738', 'yepuliang', '2007-01-04 13:31:14', '我的阶段计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000739', 'yepuliang', '2007-01-04 13:31:28', '我的详细计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000740', 'yepuliang', '2007-01-04 13:31:40', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000741', 'zhangfeng', '2007-01-05 14:12:12', '公共通讯录', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000742', 'zhangfeng', '2007-01-05 14:12:16', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000743', 'zhangfeng', '2007-01-05 14:17:48', '我的详细计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000744', 'zhangfeng', '2007-01-05 14:17:55', '我的阶段计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000745', 'zhangfeng', '2007-01-05 14:17:56', '我的详细计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000746', 'zhangfeng', '2007-01-05 14:17:57', '详细工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000747', 'zhangfeng', '2007-01-05 14:18:10', '阶段工作计划', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000748', 'yepuliang', '2007-01-05 17:16:59', '详细工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000749', 'yepuliang', '2007-01-05 17:17:04', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000750', 'guxiaofeng', '2007-01-08 08:39:07', '日志管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000751', 'yepuliang', '2007-01-09 08:43:55', '详细工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000752', 'yepuliang', '2007-01-09 08:43:56', '我的阶段计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000753', 'yepuliang', '2007-01-09 08:44:04', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000754', 'yepuliang', '2007-01-09 08:44:05', '详细工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000755', 'yepuliang', '2007-01-09 08:44:07', '我的阶段计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000756', 'yepuliang', '2007-01-09 08:44:10', '我的详细计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000757', 'yepuliang', '2007-01-09 08:44:13', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000758', 'zhaoyifei', '2007-01-12 10:21:04', '用户管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000759', 'zhaoyifei', '2007-01-12 10:33:22', '类型管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000760', 'zhaoyifei', '2007-01-12 10:33:24', '模块管理', 'lookup', '192.168.1.9', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000761', 'yepuliang', '2007-01-16 08:50:42', '修改密码', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000762', 'yepuliang', '2007-01-16 08:50:49', '个人通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000763', 'yepuliang', '2007-01-16 08:50:54', '公共通讯录', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000764', 'yepuliang', '2007-01-16 08:50:56', '阶段工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000765', 'yepuliang', '2007-01-16 08:50:57', '详细工作计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000766', 'yepuliang', '2007-01-16 08:50:58', '我的阶段计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000767', 'yepuliang', '2007-01-16 08:50:59', '我的详细计划', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000768', 'yepuliang', '2007-01-16 08:51:02', '内部邮件', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000769', 'yepuliang', '2007-01-16 08:51:03', '外部邮件', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000770', 'yepuliang', '2007-01-16 08:51:04', '邮箱管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000771', 'yepuliang', '2007-01-16 08:51:11', '航班查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000772', 'yepuliang', '2007-01-16 08:51:13', '国际时间查询', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000773', 'yepuliang', '2007-01-16 08:51:20', '邮政编码', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000774', 'yepuliang', '2007-01-16 08:51:26', '留言板', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000775', 'yepuliang', '2007-01-16 08:51:28', '留言板管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000776', 'yepuliang', '2007-01-16 08:51:29', '部门岗位信息', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000777', 'yepuliang', '2007-01-16 08:51:36', '部门岗位信息', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000778', 'yepuliang', '2007-01-16 08:51:37', '部门岗位信息', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000779', 'yepuliang', '2007-01-16 08:51:38', '部门岗位信息', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000780', 'yepuliang', '2007-01-16 08:51:39', '留言板管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000781', 'yepuliang', '2007-01-17 09:54:32', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000782', 'yepuliang', '2007-01-17 09:55:35', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000783', 'yepuliang', '2007-01-17 09:55:45', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000784', 'yepuliang', '2007-01-17 09:58:03', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000785', 'yepuliang', '2007-01-17 10:00:24', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000786', 'yepuliang', '2007-01-17 10:08:02', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000787', 'yepuliang', '2007-01-17 10:12:29', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000788', 'yepuliang', '2007-01-17 10:22:23', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000789', 'yepuliang', '2007-01-17 10:22:46', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000790', 'yepuliang', '2007-01-17 10:23:01', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000791', 'yepuliang', '2007-01-17 10:24:11', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000792', 'yepuliang', '2007-01-17 10:30:06', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000793', 'yepuliang', '2007-01-17 10:31:18', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000794', 'yepuliang', '2007-01-17 10:33:45', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000795', 'yepuliang', '2007-01-17 10:55:57', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000796', 'yepuliang', '2007-01-17 10:56:20', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000797', 'yepuliang', '2007-01-17 10:56:22', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000798', 'yepuliang', '2007-01-17 10:56:23', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000799', 'yepuliang', '2007-01-17 10:56:36', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000800', 'yepuliang', '2007-01-17 10:59:05', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000801', 'yepuliang', '2007-01-17 13:18:37', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000802', 'yepuliang', '2007-01-17 13:22:34', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000803', 'yepuliang', '2007-01-17 13:23:41', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000804', 'yepuliang', '2007-01-17 13:31:38', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000805', 'yepuliang', '2007-01-17 13:36:38', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000806', 'yepuliang', '2007-01-17 13:37:56', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000807', 'yepuliang', '2007-01-17 13:38:07', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000808', 'yepuliang', '2007-01-17 13:38:24', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000809', 'yepuliang', '2007-01-17 13:44:57', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000810', 'yepuliang', '2007-01-17 13:45:55', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000811', 'yepuliang', '2007-01-17 13:47:15', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000812', 'yepuliang', '2007-01-17 14:24:30', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000813', 'yepuliang', '2007-01-17 14:24:32', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000814', 'yepuliang', '2007-01-17 14:24:47', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000815', 'yepuliang', '2007-01-17 14:26:42', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000816', 'yepuliang', '2007-01-17 14:26:50', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000817', 'yepuliang', '2007-01-17 14:27:03', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000818', 'yepuliang', '2007-01-17 14:30:21', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000819', 'yepuliang', '2007-01-17 14:34:02', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000820', 'yepuliang', '2007-01-17 14:50:09', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000821', 'jingyuzhuo', '2007-01-19 10:35:33', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000822', 'jingyuzhuo', '2007-01-19 10:36:35', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000823', 'jingyuzhuo', '2007-01-19 10:36:38', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000824', 'zhaoyifei', '2007-01-19 10:44:09', '类型管理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000825', 'zhaoyifei', '2007-01-19 10:45:25', '用户管理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000826', 'jingyuzhuo', '2007-01-19 10:46:58', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000827', 'jingyuzhuo', '2007-01-19 10:47:01', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000828', 'jingyuzhuo', '2007-01-19 10:47:12', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000829', 'jingyuzhuo', '2007-01-19 10:49:23', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000830', 'zhaoyifei', '2007-01-19 11:04:48', '模块管理', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000831', 'jingyuzhuo', '2007-01-19 11:05:52', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000832', 'jingyuzhuo', '2007-01-19 11:05:55', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000833', 'jingyuzhuo', '2007-01-19 11:05:56', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000834', 'jingyuzhuo', '2007-01-19 11:05:56', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000835', 'jingyuzhuo', '2007-01-19 11:06:30', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000836', 'jingyuzhuo', '2007-01-19 11:06:32', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000837', 'jingyuzhuo', '2007-01-19 11:06:34', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000838', 'jingyuzhuo', '2007-01-19 11:08:27', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000839', 'jingyuzhuo', '2007-01-19 11:08:28', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000840', 'jingyuzhuo', '2007-01-19 11:08:29', '用户注册', 'lookup', '127.0.0.1', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000841', 'yepuliang', '2007-01-23 15:56:12', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000842', 'yepuliang', '2007-01-23 15:56:18', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000843', 'yepuliang', '2007-01-23 16:28:25', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000844', 'yepuliang', '2007-01-23 16:29:32', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000845', 'yepuliang', '2007-01-23 16:29:52', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000846', 'yepuliang', '2007-01-23 16:29:55', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000847', 'yepuliang', '2007-01-23 16:30:00', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000848', 'yepuliang', '2007-01-23 16:30:01', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000849', 'yepuliang', '2007-01-23 16:30:01', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000850', 'yepuliang', '2007-01-23 16:30:02', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000851', 'yepuliang', '2007-01-23 16:30:02', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000852', 'yepuliang', '2007-01-23 16:30:03', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000853', 'yepuliang', '2007-01-23 16:30:06', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000854', 'yepuliang', '2007-01-23 16:30:08', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000855', 'yepuliang', '2007-01-23 16:30:10', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000856', 'yepuliang', '2007-01-23 16:30:10', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000857', 'yepuliang', '2007-01-23 16:30:11', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000858', 'yepuliang', '2007-01-23 16:30:11', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000859', 'yepuliang', '2007-01-23 16:31:08', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000860', 'yepuliang', '2007-01-23 16:31:09', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000861', 'yepuliang', '2007-01-23 16:31:32', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000862', 'yepuliang', '2007-01-23 16:31:41', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000863', 'yepuliang', '2007-01-23 16:31:53', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000864', 'yepuliang', '2007-01-23 16:31:54', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000865', 'yepuliang', '2007-01-23 16:31:55', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000866', 'yepuliang', '2007-01-23 16:31:56', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000867', 'yepuliang', '2007-01-23 16:31:57', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000868', 'yepuliang', '2007-01-23 16:31:58', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000869', 'yepuliang', '2007-01-23 16:31:58', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000870', 'yepuliang', '2007-01-23 16:31:59', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000871', 'yepuliang', '2007-01-23 16:32:00', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000872', 'yepuliang', '2007-01-23 16:32:00', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000873', 'yepuliang', '2007-01-23 16:32:01', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000874', 'yepuliang', '2007-01-23 16:32:01', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000875', 'yepuliang', '2007-01-23 16:32:02', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000876', 'yepuliang', '2007-01-23 16:32:02', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000877', 'yepuliang', '2007-01-23 16:32:03', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000878', 'yepuliang', '2007-01-23 16:32:03', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000879', 'yepuliang', '2007-01-23 16:32:04', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000880', 'yepuliang', '2007-01-23 16:32:11', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000881', 'yepuliang', '2007-01-24 13:55:46', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000882', 'yepuliang', '2007-01-24 13:56:05', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000883', 'yepuliang', '2007-01-24 15:04:06', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000884', 'yepuliang', '2007-01-24 15:13:10', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000885', 'yepuliang', '2007-01-24 15:13:11', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000886', 'yepuliang', '2007-01-24 15:13:15', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000887', 'yepuliang', '2007-01-24 15:17:30', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000888', 'yepuliang', '2007-01-24 15:17:35', '新闻测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000889', 'yepuliang', '2007-01-24 15:17:38', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000890', 'yepuliang', '2007-01-24 15:17:39', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000891', 'yepuliang', '2007-01-24 15:17:40', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000892', 'yepuliang', '2007-01-24 15:17:50', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000893', 'yepuliang', '2007-01-24 15:19:52', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000894', 'yepuliang', '2007-01-24 15:30:19', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000895', 'yepuliang', '2007-01-24 15:35:15', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000896', 'yepuliang', '2007-01-24 15:39:17', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000897', 'yepuliang', '2007-01-24 15:48:33', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000898', 'yepuliang', '2007-01-24 15:48:37', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000899', 'yepuliang', '2007-01-24 16:40:31', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000900', 'yepuliang', '2007-01-24 16:41:52', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000901', 'yepuliang', '2007-01-24 18:20:15', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000902', 'yepuliang', '2007-01-24 18:21:14', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000903', 'yepuliang', '2007-01-24 18:23:14', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000904', 'yepuliang', '2007-01-24 18:23:48', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000905', 'yepuliang', '2007-01-24 18:25:45', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000906', 'yepuliang', '2007-01-24 18:26:26', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000907', 'yepuliang', '2007-01-24 18:26:31', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000908', 'yepuliang', '2007-01-24 18:26:34', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000909', 'yepuliang', '2007-01-24 18:26:35', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000910', 'yepuliang', '2007-01-24 18:26:37', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000911', 'yepuliang', '2007-01-24 18:26:41', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000912', 'yepuliang', '2007-01-24 18:26:42', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000913', 'yepuliang', '2007-01-24 18:26:46', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000914', 'yepuliang', '2007-01-24 18:26:53', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000915', 'yepuliang', '2007-01-24 18:26:54', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000916', 'yepuliang', '2007-01-24 18:30:04', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000917', 'yepuliang', '2007-01-24 18:33:13', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000918', 'yepuliang', '2007-01-24 18:33:15', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000919', 'yepuliang', '2007-01-24 18:33:16', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000920', 'yepuliang', '2007-01-24 18:34:10', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000921', 'yepuliang', '2007-01-24 18:43:41', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000922', 'yepuliang', '2007-01-24 18:43:49', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000923', 'yepuliang', '2007-01-24 18:43:57', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000924', 'yepuliang', '2007-01-24 18:43:59', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000925', 'yepuliang', '2007-01-24 18:44:19', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000926', 'yepuliang', '2007-01-24 18:44:22', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000927', 'yepuliang', '2007-01-24 18:44:23', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000928', 'yepuliang', '2007-01-24 18:44:24', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000929', 'yepuliang', '2007-01-24 18:44:27', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000930', 'yepuliang', '2007-01-24 18:44:27', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000931', 'yepuliang', '2007-01-24 18:44:29', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000932', 'yepuliang', '2007-01-24 18:47:00', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000933', 'yepuliang', '2007-01-24 18:47:04', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000934', 'yepuliang', '2007-01-24 18:47:28', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000935', 'yepuliang', '2007-01-24 18:47:44', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000936', 'yepuliang', '2007-01-24 18:47:51', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000937', 'yepuliang', '2007-01-24 18:47:51', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000938', 'yepuliang', '2007-01-24 19:09:05', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000939', 'yepuliang', '2007-01-24 19:09:07', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000940', 'yepuliang', '2007-01-24 19:10:02', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000941', 'yepuliang', '2007-01-24 19:10:57', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000942', 'yepuliang', '2007-01-24 19:11:20', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000943', 'yepuliang', '2007-01-24 19:11:44', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000944', 'yepuliang', '2007-01-24 19:11:51', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000945', 'yepuliang', '2007-01-24 19:12:02', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000946', 'yepuliang', '2007-01-24 19:12:04', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000947', 'yepuliang', '2007-01-24 19:12:09', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000948', 'yepuliang', '2007-01-24 19:12:22', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000949', 'yepuliang', '2007-01-24 19:12:33', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000950', 'yepuliang', '2007-01-24 19:13:09', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000951', 'yepuliang', '2007-01-24 19:13:10', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000952', 'yepuliang', '2007-01-24 19:13:11', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000953', 'yepuliang', '2007-01-24 19:13:12', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000954', 'yepuliang', '2007-01-24 19:13:14', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000955', 'yepuliang', '2007-01-24 19:13:26', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000956', 'yepuliang', '2007-01-24 19:13:34', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000957', 'yepuliang', '2007-01-24 19:13:37', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000958', 'yepuliang', '2007-01-24 19:13:38', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000959', 'yepuliang', '2007-01-24 19:15:04', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000960', 'yepuliang', '2007-01-24 19:15:06', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000961', 'yepuliang', '2007-01-24 19:15:08', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000962', 'yepuliang', '2007-01-24 19:15:19', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000963', 'yepuliang', '2007-01-24 19:15:20', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000964', 'yepuliang', '2007-01-24 19:15:41', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000965', 'yepuliang', '2007-01-24 19:15:43', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000966', 'yepuliang', '2007-01-24 19:15:44', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000967', 'yepuliang', '2007-01-24 19:15:45', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000968', 'yepuliang', '2007-01-24 19:16:00', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000969', 'yepuliang', '2007-01-24 19:16:27', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000970', 'yepuliang', '2007-01-24 19:16:30', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000971', 'yepuliang', '2007-01-24 19:16:43', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000972', 'yepuliang', '2007-01-24 19:16:45', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000973', 'yepuliang', '2007-01-24 19:16:56', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000974', 'yepuliang', '2007-01-24 19:16:57', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000975', 'yepuliang', '2007-01-24 19:16:58', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000976', 'yepuliang', '2007-01-24 19:18:10', '新闻测试', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000977', 'yepuliang', '2007-01-24 19:18:13', '样式测试', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000978', 'yepuliang', '2007-01-24 19:18:15', '样式测试2', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000979', 'yepuliang', '2007-01-24 19:18:25', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000980', 'yepuliang', '2007-01-24 19:18:38', '样式测试2', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000981', 'yepuliang', '2007-01-26 15:54:17', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000982', 'yepuliang', '2007-01-26 16:08:35', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000983', 'yepuliang', '2007-01-26 16:08:39', '新闻测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000984', 'yepuliang', '2007-01-26 16:08:41', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000985', 'yepuliang', '2007-01-26 16:08:43', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000986', 'yepuliang', '2007-01-26 16:08:45', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000987', 'yepuliang', '2007-01-26 16:08:46', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000988', 'yepuliang', '2007-01-26 16:08:58', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000989', 'yepuliang', '2007-01-26 16:08:59', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000990', 'yepuliang', '2007-01-26 16:11:44', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000991', 'yepuliang', '2007-01-26 16:11:45', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000992', 'yepuliang', '2007-01-26 16:11:47', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000993', 'yepuliang', '2007-01-26 16:12:00', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000994', 'yepuliang', '2007-01-26 16:12:03', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000995', 'yepuliang', '2007-01-26 16:27:16', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000996', 'yepuliang', '2007-01-26 16:27:17', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000997', 'yepuliang', '2007-01-26 16:27:19', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000998', 'yepuliang', '2007-01-26 16:27:23', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000000999', 'yepuliang', '2007-01-26 16:27:23', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001000', 'yepuliang', '2007-01-26 16:27:24', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001001', 'yepuliang', '2007-01-26 16:27:37', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001002', 'yepuliang', '2007-01-26 16:28:44', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001003', 'yepuliang', '2007-01-26 16:28:44', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001004', 'yepuliang', '2007-01-26 16:28:46', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001005', 'yepuliang', '2007-01-26 16:28:58', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001006', 'yepuliang', '2007-01-26 16:29:08', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001007', 'yepuliang', '2007-01-26 16:29:24', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001008', 'yepuliang', '2007-01-26 16:29:27', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001009', 'yepuliang', '2007-01-26 16:29:28', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001010', 'yepuliang', '2007-01-26 16:29:29', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001011', 'yepuliang', '2007-01-26 16:29:42', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001012', 'yepuliang', '2007-01-26 16:29:46', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001013', 'yepuliang', '2007-01-26 16:29:47', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001014', 'yepuliang', '2007-01-26 16:30:00', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001015', 'yepuliang', '2007-01-26 16:30:05', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001016', 'yepuliang', '2007-01-26 16:30:15', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001017', 'yepuliang', '2007-01-26 16:30:18', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001018', 'yepuliang', '2007-01-26 16:30:19', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001019', 'yepuliang', '2007-01-26 16:30:20', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001020', 'yepuliang', '2007-01-26 16:30:21', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001021', 'yepuliang', '2007-01-26 16:30:21', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001022', 'yepuliang', '2007-01-26 16:30:22', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001023', 'yepuliang', '2007-01-26 16:30:23', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001024', 'yepuliang', '2007-01-26 16:30:24', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001025', 'yepuliang', '2007-01-26 16:30:35', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001026', 'yepuliang', '2007-01-26 16:30:40', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001027', 'yepuliang', '2007-01-26 16:30:49', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001028', 'yepuliang', '2007-01-26 16:32:01', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001029', 'yepuliang', '2007-01-26 16:32:02', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001030', 'yepuliang', '2007-01-26 16:34:45', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001031', 'yepuliang', '2007-01-26 16:34:46', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001032', 'yepuliang', '2007-01-26 16:34:47', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001033', 'yepuliang', '2007-01-26 16:34:48', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001034', 'yepuliang', '2007-01-26 16:34:48', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001035', 'yepuliang', '2007-01-26 16:34:49', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001036', 'yepuliang', '2007-01-26 16:36:07', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001037', 'yepuliang', '2007-01-26 16:36:20', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001038', 'yepuliang', '2007-01-26 16:36:57', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001039', 'yepuliang', '2007-01-26 16:37:01', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001040', 'yepuliang', '2007-01-26 16:37:14', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001041', 'yepuliang', '2007-01-26 16:37:45', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001042', 'yepuliang', '2007-01-26 16:37:59', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001043', 'yepuliang', '2007-01-26 16:38:22', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001044', 'yepuliang', '2007-01-26 16:39:37', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001045', 'yepuliang', '2007-01-26 16:40:17', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001046', 'yepuliang', '2007-01-26 16:40:53', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001047', 'yepuliang', '2007-01-26 16:42:29', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001048', 'yepuliang', '2007-01-26 16:44:01', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001049', 'yepuliang', '2007-01-26 16:44:53', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001050', 'yepuliang', '2007-01-26 16:45:11', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001051', 'yepuliang', '2007-01-26 16:45:52', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001052', 'yepuliang', '2007-01-26 16:45:53', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001053', 'yepuliang', '2007-01-26 16:49:35', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001054', 'yepuliang', '2007-01-26 16:50:02', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001055', 'yepuliang', '2007-01-26 16:50:32', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001056', 'yepuliang', '2007-01-26 16:50:41', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001057', 'yepuliang', '2007-01-26 16:52:16', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001058', 'yepuliang', '2007-01-26 16:56:15', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001059', 'yepuliang', '2007-01-26 16:56:23', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001060', 'yepuliang', '2007-01-26 16:56:26', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001061', 'yepuliang', '2007-01-26 16:56:37', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001062', 'yepuliang', '2007-01-26 16:57:10', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001063', 'yepuliang', '2007-01-26 16:57:52', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001064', 'yepuliang', '2007-01-26 16:57:54', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001065', 'yepuliang', '2007-01-26 16:57:55', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001066', 'yepuliang', '2007-01-26 16:57:56', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001067', 'yepuliang', '2007-01-26 16:57:57', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001068', 'yepuliang', '2007-01-26 16:59:04', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001069', 'yepuliang', '2007-01-26 16:59:47', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001070', 'yepuliang', '2007-01-26 16:59:48', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001071', 'yepuliang', '2007-01-26 16:59:50', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001072', 'yepuliang', '2007-01-26 16:59:51', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001073', 'yepuliang', '2007-01-26 16:59:52', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001074', 'yepuliang', '2007-01-26 16:59:54', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001075', 'yepuliang', '2007-01-26 17:00:50', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001076', 'yepuliang', '2007-01-26 17:01:58', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001077', 'yepuliang', '2007-01-26 17:02:09', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001078', 'yepuliang', '2007-01-26 17:02:29', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001079', 'yepuliang', '2007-01-26 17:02:33', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001080', 'yepuliang', '2007-01-26 17:02:35', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001081', 'yepuliang', '2007-01-26 17:02:37', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001082', 'yepuliang', '2007-01-26 17:02:48', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001083', 'yepuliang', '2007-01-26 17:02:51', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001084', 'yepuliang', '2007-01-26 17:02:52', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001085', 'yepuliang', '2007-01-26 17:04:10', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001086', 'yepuliang', '2007-01-26 17:04:22', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001087', 'yepuliang', '2007-01-26 17:07:21', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001088', 'yepuliang', '2007-01-26 17:07:26', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001089', 'yepuliang', '2007-01-26 17:16:05', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001090', 'yepuliang', '2007-01-26 17:16:31', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001091', 'yepuliang', '2007-01-26 17:16:42', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001092', 'yepuliang', '2007-01-26 17:16:47', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001093', 'yepuliang', '2007-01-26 17:16:59', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001094', 'yepuliang', '2007-01-26 17:17:18', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001095', 'yepuliang', '2007-01-26 17:17:20', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001096', 'yepuliang', '2007-01-26 17:17:29', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001097', 'yepuliang', '2007-01-26 17:17:55', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001098', 'yepuliang', '2007-01-26 17:17:56', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001099', 'yepuliang', '2007-01-26 17:18:09', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001100', 'yepuliang', '2007-01-26 17:19:46', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001101', 'yepuliang', '2007-01-26 17:19:48', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001102', 'yepuliang', '2007-01-26 17:21:30', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001103', 'yepuliang', '2007-01-26 17:23:47', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001104', 'yepuliang', '2007-01-26 17:24:00', '卓越办公自动化系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001105', 'yepuliang', '2007-01-26 17:24:03', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001106', 'yepuliang', '2007-01-26 17:24:39', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001107', 'yepuliang', '2007-01-26 17:25:02', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001108', 'yepuliang', '2007-01-26 17:25:05', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001109', 'yepuliang', '2007-01-26 17:26:48', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001110', 'yepuliang', '2007-01-26 17:27:18', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001111', 'yepuliang', '2007-01-26 17:28:38', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001112', 'yepuliang', '2007-01-26 17:29:21', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001113', 'yepuliang', '2007-01-26 17:29:40', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001114', 'yepuliang', '2007-01-26 17:29:54', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001115', 'yepuliang', '2007-01-26 17:31:12', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001116', 'yepuliang', '2007-01-26 17:31:17', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001117', 'yepuliang', '2007-01-26 17:32:54', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001118', 'yepuliang', '2007-01-26 17:32:54', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001119', 'yepuliang', '2007-01-26 17:34:57', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001120', 'yepuliang', '2007-01-26 17:35:12', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001121', 'yepuliang', '2007-01-29 10:15:54', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001122', 'yepuliang', '2007-01-29 10:16:51', '类型管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001123', 'yepuliang', '2007-01-29 10:16:54', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001124', 'yepuliang', '2007-01-29 10:17:49', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001125', 'yepuliang', '2007-01-29 10:18:12', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001126', 'yepuliang', '2007-01-29 10:18:26', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001127', 'yepuliang', '2007-01-29 10:18:47', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001128', 'yepuliang', '2007-01-29 10:24:36', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001129', 'yepuliang', '2007-01-29 10:25:44', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001130', 'yepuliang', '2007-01-29 10:25:47', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001131', 'yepuliang', '2007-01-29 10:25:51', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001132', 'yepuliang', '2007-01-29 10:29:27', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001133', 'yepuliang', '2007-01-29 10:29:31', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001134', 'yepuliang', '2007-01-29 10:32:02', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001135', 'yepuliang', '2007-01-29 10:35:32', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001136', 'yepuliang', '2007-01-29 10:36:05', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001137', 'yepuliang', '2007-01-29 10:36:19', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001138', 'yepuliang', '2007-01-29 10:36:20', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001139', 'yepuliang', '2007-01-29 10:36:21', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001140', 'yepuliang', '2007-01-29 10:36:21', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001141', 'yepuliang', '2007-01-29 10:37:01', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001142', 'yepuliang', '2007-01-29 10:37:04', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001143', 'yepuliang', '2007-01-29 10:37:05', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001144', 'yepuliang', '2007-01-29 10:37:05', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001145', 'yepuliang', '2007-01-29 10:37:07', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001146', 'yepuliang', '2007-01-29 10:37:08', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001147', 'yepuliang', '2007-01-29 10:37:09', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001148', 'yepuliang', '2007-01-29 10:37:10', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001149', 'yepuliang', '2007-01-29 10:37:12', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001150', 'yepuliang', '2007-01-29 10:37:13', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001151', 'yepuliang', '2007-01-29 10:37:14', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001152', 'yepuliang', '2007-01-29 10:37:15', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001153', 'yepuliang', '2007-01-29 10:37:16', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001154', 'yepuliang', '2007-01-29 10:37:17', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001155', 'yepuliang', '2007-01-29 10:37:18', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001156', 'yepuliang', '2007-01-29 10:37:19', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001157', 'yepuliang', '2007-01-29 10:37:20', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001158', 'yepuliang', '2007-01-29 10:37:23', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001159', 'yepuliang', '2007-01-29 10:37:26', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001160', 'yepuliang', '2007-01-29 10:37:27', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001161', 'yepuliang', '2007-01-30 09:10:42', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001162', 'yepuliang', '2007-01-30 09:10:43', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001163', 'yepuliang', '2007-01-30 09:11:25', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001164', 'yepuliang', '2007-01-30 09:11:29', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001165', 'yepuliang', '2007-01-30 09:11:34', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001166', 'yepuliang', '2007-01-30 09:28:24', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001167', 'yepuliang', '2007-01-30 09:28:26', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001168', 'yepuliang', '2007-01-30 09:28:27', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001169', 'yepuliang', '2007-01-30 09:28:31', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001170', 'yepuliang', '2007-01-30 09:28:39', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001171', 'yepuliang', '2007-01-30 09:28:49', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001172', 'yepuliang', '2007-01-30 09:28:52', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001173', 'yepuliang', '2007-01-30 09:29:06', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001174', 'yepuliang', '2007-01-30 09:29:07', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001175', 'yepuliang', '2007-01-30 09:29:08', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001176', 'yepuliang', '2007-01-30 09:29:20', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001177', 'yepuliang', '2007-01-30 09:29:21', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001178', 'yepuliang', '2007-01-30 09:29:22', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001179', 'yepuliang', '2007-01-30 09:29:32', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001180', 'yepuliang', '2007-01-30 09:29:39', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001181', 'yepuliang', '2007-01-30 09:29:40', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001182', 'yepuliang', '2007-01-30 09:29:42', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001183', 'yepuliang', '2007-01-30 09:29:47', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001184', 'yepuliang', '2007-01-30 09:29:48', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001185', 'yepuliang', '2007-01-30 09:29:53', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001186', 'yepuliang', '2007-01-30 09:29:55', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001187', 'yepuliang', '2007-01-30 09:29:58', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001188', 'yepuliang', '2007-01-30 09:30:00', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001189', 'yepuliang', '2007-01-30 09:30:01', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001190', 'yepuliang', '2007-01-30 09:30:14', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001191', 'yepuliang', '2007-01-30 09:30:21', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001192', 'yepuliang', '2007-01-30 09:30:25', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001193', 'yepuliang', '2007-01-30 09:30:26', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001194', 'yepuliang', '2007-01-30 09:30:28', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001195', 'yepuliang', '2007-01-30 09:30:29', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001196', 'yepuliang', '2007-01-30 09:30:33', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001197', 'yepuliang', '2007-01-30 09:30:34', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001198', 'yepuliang', '2007-01-30 09:55:28', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001199', 'yepuliang', '2007-01-30 09:56:50', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001200', 'yepuliang', '2007-01-30 09:57:46', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001201', 'yepuliang', '2007-01-30 13:14:03', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001202', 'yepuliang', '2007-01-30 13:25:26', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001203', 'yepuliang', '2007-01-30 13:25:47', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001204', 'yepuliang', '2007-01-30 13:26:20', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001205', 'yepuliang', '2007-01-30 13:26:21', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001206', 'yepuliang', '2007-01-30 13:29:55', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001207', 'yepuliang', '2007-01-30 14:25:28', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001208', 'guxiaofeng', '2007-01-30 14:53:05', '新闻测试', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001209', 'guxiaofeng', '2007-01-30 14:53:11', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001210', 'guxiaofeng', '2007-01-30 15:15:20', '样式测试', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001211', 'guxiaofeng', '2007-01-30 15:15:22', '样式测试2', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001212', 'guxiaofeng', '2007-01-30 15:15:23', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001213', 'guxiaofeng', '2007-01-30 15:15:25', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001214', 'guxiaofeng', '2007-01-30 15:17:38', '新闻添加', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001215', 'guxiaofeng', '2007-01-30 15:20:16', '样式测试', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001216', 'guxiaofeng', '2007-01-30 15:20:20', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001217', 'guxiaofeng', '2007-01-30 15:20:23', '样式测试2', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001218', 'guxiaofeng', '2007-01-30 15:20:30', '样式测试2', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001219', 'guxiaofeng', '2007-01-30 15:20:34', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001220', 'guxiaofeng', '2007-01-30 15:20:38', '样式测试2', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001221', 'guxiaofeng', '2007-01-30 15:20:40', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001222', 'guxiaofeng', '2007-01-30 15:26:58', '新闻测试', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001223', 'guxiaofeng', '2007-01-30 15:27:01', '样式测试', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001224', 'guxiaofeng', '2007-01-30 15:27:02', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001225', 'guxiaofeng', '2007-01-30 15:27:45', '新闻添加', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001226', 'guxiaofeng', '2007-01-30 15:34:04', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001227', 'guxiaofeng', '2007-01-30 15:35:12', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001228', 'guxiaofeng', '2007-01-30 15:35:30', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001229', 'yepuliang', '2007-01-30 15:38:27', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001230', 'guxiaofeng', '2007-01-30 15:39:01', '内部邮件', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001231', 'guxiaofeng', '2007-01-30 15:39:02', '内部邮件', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001232', 'guxiaofeng', '2007-01-30 15:39:03', '内部邮件', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001233', 'guxiaofeng', '2007-01-30 15:39:03', '外部邮件', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001234', 'guxiaofeng', '2007-01-30 15:39:05', '邮箱管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001235', 'guxiaofeng', '2007-01-30 15:39:06', '外部邮件', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001236', 'yepuliang', '2007-01-30 15:39:06', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001237', 'guxiaofeng', '2007-01-30 15:39:07', '内部邮件', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001238', 'guxiaofeng', '2007-01-30 15:39:12', '阶段工作计划', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001239', 'guxiaofeng', '2007-01-30 15:39:23', '公共通讯录', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001240', 'guxiaofeng', '2007-01-30 15:39:24', '个人通讯录', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001241', 'yepuliang', '2007-01-30 15:57:00', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001242', 'yepuliang', '2007-01-30 15:58:27', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001243', 'yepuliang', '2007-01-30 15:58:28', '回收站', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001244', 'yepuliang', '2007-01-30 15:58:29', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001245', 'yepuliang', '2007-01-30 15:58:30', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001246', 'yepuliang', '2007-01-30 15:58:31', '回收站', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001247', 'yepuliang', '2007-01-30 15:59:07', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001248', 'yepuliang', '2007-01-30 15:59:08', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001249', 'yepuliang', '2007-01-30 15:59:18', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001250', 'yepuliang', '2007-01-30 16:03:14', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001251', 'yepuliang', '2007-01-30 16:06:59', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001252', 'yepuliang', '2007-01-30 16:07:00', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001253', 'guxiaofeng', '2007-01-30 16:22:20', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001254', 'guxiaofeng', '2007-01-30 16:22:55', '新闻添加', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001255', 'guxiaofeng', '2007-01-30 16:23:28', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001256', 'guxiaofeng', '2007-01-30 16:23:55', '新闻添加', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001257', 'guxiaofeng', '2007-01-30 16:24:25', '新闻添加', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001258', 'guxiaofeng', '2007-01-30 16:24:43', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001259', 'guxiaofeng', '2007-01-30 16:25:40', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001260', 'guxiaofeng', '2007-01-30 16:25:52', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001261', 'guxiaofeng', '2007-01-31 14:08:56', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001262', 'guxiaofeng', '2007-01-31 14:09:09', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001263', 'guxiaofeng', '2007-01-31 14:09:10', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001264', 'guxiaofeng', '2007-01-31 14:09:11', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001265', 'guxiaofeng', '2007-01-31 14:12:14', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001266', 'guxiaofeng', '2007-01-31 14:12:16', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001267', 'guxiaofeng', '2007-01-31 14:12:16', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001268', 'guxiaofeng', '2007-01-31 14:12:24', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001269', 'guxiaofeng', '2007-01-31 14:13:50', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001270', 'guxiaofeng', '2007-01-31 14:13:51', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001271', 'guxiaofeng', '2007-01-31 14:13:52', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001272', 'guxiaofeng', '2007-01-31 14:13:57', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001273', 'guxiaofeng', '2007-01-31 14:13:58', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001274', 'guxiaofeng', '2007-01-31 14:14:08', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001275', 'guxiaofeng', '2007-01-31 14:17:00', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001276', 'guxiaofeng', '2007-01-31 14:17:02', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001277', 'guxiaofeng', '2007-01-31 14:19:32', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001278', 'guxiaofeng', '2007-01-31 14:19:33', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001279', 'guxiaofeng', '2007-01-31 14:20:55', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001280', 'guxiaofeng', '2007-01-31 14:31:43', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001281', 'guxiaofeng', '2007-01-31 14:31:45', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001282', 'guxiaofeng', '2007-01-31 14:31:45', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001283', 'guxiaofeng', '2007-01-31 14:31:49', '新闻测试', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001284', 'guxiaofeng', '2007-01-31 14:34:28', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001285', 'guxiaofeng', '2007-01-31 14:34:29', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001286', 'guxiaofeng', '2007-01-31 14:45:38', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001287', 'guxiaofeng', '2007-01-31 14:45:39', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001288', 'guxiaofeng', '2007-01-31 14:45:40', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001289', 'guxiaofeng', '2007-01-31 14:45:41', '新闻模块样式设置', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001290', 'guxiaofeng', '2007-01-31 14:45:42', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001291', 'yepuliang', '2007-01-31 14:50:09', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001292', 'yepuliang', '2007-01-31 14:50:11', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001293', 'yepuliang', '2007-01-31 14:50:14', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001294', 'yepuliang', '2007-01-31 14:50:42', '类型管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001295', 'yepuliang', '2007-01-31 14:50:52', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001296', 'yepuliang', '2007-01-31 14:51:02', '用户注册', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001297', 'yepuliang', '2007-01-31 14:51:05', '用户注册', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001298', 'yepuliang', '2007-01-31 14:51:06', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001299', 'yepuliang', '2007-01-31 14:51:07', '办公平台', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001300', 'yepuliang', '2007-01-31 14:51:08', '新闻系统', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001301', 'yepuliang', '2007-02-05 08:52:42', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001302', 'yepuliang', '2007-02-05 08:53:51', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001303', 'yepuliang', '2007-02-05 08:53:54', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001304', 'yepuliang', '2007-02-05 08:55:54', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001305', 'yepuliang', '2007-02-05 08:56:03', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001306', 'yepuliang', '2007-02-05 08:56:04', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001307', 'yepuliang', '2007-02-05 08:57:36', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001308', 'yepuliang', '2007-02-05 08:57:40', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001309', 'guxiaofeng', '2007-02-05 08:58:41', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001310', 'guxiaofeng', '2007-02-05 08:58:43', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001311', 'guxiaofeng', '2007-02-05 09:06:50', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001312', 'guxiaofeng', '2007-02-05 09:07:50', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001313', 'guxiaofeng', '2007-02-05 09:09:33', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001314', 'guxiaofeng', '2007-02-05 09:10:01', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001315', 'guxiaofeng', '2007-02-05 09:10:08', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001316', 'guxiaofeng', '2007-02-05 09:10:14', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001317', 'guxiaofeng', '2007-02-05 09:10:21', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001318', 'guxiaofeng', '2007-02-05 09:10:23', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001319', 'guxiaofeng', '2007-02-05 09:10:25', '新闻模块样式设置', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001320', 'guxiaofeng', '2007-02-05 09:10:41', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001321', 'guxiaofeng', '2007-02-05 09:11:45', '新闻模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001322', 'guxiaofeng', '2007-02-05 09:11:57', '留言板管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001323', 'guxiaofeng', '2007-02-05 09:11:59', '留言板管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001324', 'guxiaofeng', '2007-02-05 09:12:00', '留言板', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001325', 'guxiaofeng', '2007-02-05 09:12:01', '留言板管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001326', 'guxiaofeng', '2007-02-05 09:12:01', '留言板', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001327', 'guxiaofeng', '2007-02-05 09:12:02', '留言板管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001328', 'guxiaofeng', '2007-02-05 09:12:11', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001329', 'guxiaofeng', '2007-02-05 09:12:11', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001330', 'guxiaofeng', '2007-02-05 09:12:13', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001331', 'guxiaofeng', '2007-02-05 09:12:38', '新闻模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001332', 'guxiaofeng', '2007-02-05 09:12:41', '新闻模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001333', 'guxiaofeng', '2007-02-05 09:12:47', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001334', 'guxiaofeng', '2007-02-05 09:12:48', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001335', 'guxiaofeng', '2007-02-05 09:12:50', '新闻模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001336', 'guxiaofeng', '2007-02-05 09:12:51', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001337', 'guxiaofeng', '2007-02-05 09:12:53', '回收站', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001338', 'guxiaofeng', '2007-02-05 09:12:55', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001339', 'guxiaofeng', '2007-02-05 09:12:56', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001340', 'guxiaofeng', '2007-02-05 09:12:57', '新闻模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001341', 'guxiaofeng', '2007-02-05 09:20:30', '日志管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001342', 'guxiaofeng', '2007-02-05 09:26:04', '类型管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001343', 'guxiaofeng', '2007-02-05 09:26:06', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001344', 'guxiaofeng', '2007-02-05 09:26:10', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001345', 'guxiaofeng', '2007-02-05 09:26:10', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001346', 'guxiaofeng', '2007-02-05 09:42:21', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001347', 'guxiaofeng', '2007-02-05 09:42:22', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001348', 'guxiaofeng', '2007-02-05 09:44:03', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001349', 'guxiaofeng', '2007-02-05 09:49:31', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001350', 'guxiaofeng', '2007-02-05 10:12:30', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001351', 'guxiaofeng', '2007-02-05 10:28:26', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001352', 'guxiaofeng', '2007-02-05 10:36:48', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001353', 'guxiaofeng', '2007-02-05 10:37:03', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001354', 'guxiaofeng', '2007-02-05 10:37:05', '新闻模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001355', 'guxiaofeng', '2007-02-05 10:37:29', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001356', 'guxiaofeng', '2007-02-05 10:37:31', '公告管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001357', 'guxiaofeng', '2007-02-05 10:37:33', '公告管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001358', 'guxiaofeng', '2007-02-05 10:37:34', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001359', 'guxiaofeng', '2007-02-05 10:37:34', '公告管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001360', 'guxiaofeng', '2007-02-05 10:37:36', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001361', 'yepuliang', '2007-02-05 14:43:11', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001362', 'yepuliang', '2007-02-05 14:43:13', '回收站', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001363', 'yepuliang', '2007-02-05 14:43:13', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001364', 'yepuliang', '2007-02-05 14:43:20', '新闻模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001365', 'yepuliang', '2007-02-05 14:43:38', '留言板管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001366', 'yepuliang', '2007-02-05 14:43:40', '留言板', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001367', 'yepuliang', '2007-02-05 14:43:41', '公告管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001368', 'yepuliang', '2007-02-05 14:43:42', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001369', 'yepuliang', '2007-02-05 14:43:43', '新闻模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001370', 'yepuliang', '2007-02-05 14:43:44', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001371', 'yepuliang', '2007-02-05 14:43:45', '回收站', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001372', 'yepuliang', '2007-02-05 14:43:45', '新闻添加', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001373', 'yepuliang', '2007-02-05 14:43:50', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001374', 'yepuliang', '2007-02-05 14:43:54', '样式测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001375', 'yepuliang', '2007-02-05 14:43:56', '样式测试2', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001376', 'yepuliang', '2007-02-05 14:43:57', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001377', 'guxiaofeng', '2007-02-05 14:44:29', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001378', 'guxiaofeng', '2007-02-05 14:44:29', '组管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001379', 'guxiaofeng', '2007-02-05 14:44:44', '模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001380', 'guxiaofeng', '2007-02-05 14:44:46', '用户管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001381', 'guxiaofeng', '2007-02-06 08:29:51', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001382', 'guxiaofeng', '2007-02-06 08:29:52', '新闻模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001383', 'guxiaofeng', '2007-02-06 08:29:52', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001384', 'guxiaofeng', '2007-02-06 08:30:07', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001385', 'guxiaofeng', '2007-02-06 08:38:54', '版式定制', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001386', 'guxiaofeng', '2007-02-06 08:39:02', '版式定制', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001387', 'guxiaofeng', '2007-02-06 08:39:38', '新闻模块管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001388', 'guxiaofeng', '2007-02-06 08:39:57', '新闻添加', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001389', 'guxiaofeng', '2007-02-06 08:40:01', '新闻模块管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001390', 'guxiaofeng', '2007-02-06 08:40:03', '新闻添加', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001391', 'guxiaofeng', '2007-02-06 08:40:05', '回收站', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001392', 'guxiaofeng', '2007-02-06 08:40:06', '新闻添加', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001393', 'guxiaofeng', '2007-02-06 08:40:22', '新闻模块管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001394', 'guxiaofeng', '2007-02-06 08:40:34', '版式定制', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001395', 'guxiaofeng', '2007-02-06 08:42:11', '版式定制', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001396', 'guxiaofeng', '2007-02-06 08:42:12', '新闻模块管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001397', 'guxiaofeng', '2007-02-06 08:42:13', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001398', 'guxiaofeng', '2007-02-06 08:42:14', '回收站', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001399', 'guxiaofeng', '2007-02-06 08:42:14', '新闻添加', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001400', 'guxiaofeng', '2007-02-06 08:42:15', '回收站', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001401', 'guxiaofeng', '2007-02-06 08:42:16', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001402', 'guxiaofeng', '2007-02-06 08:42:17', '新闻模块管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001403', 'guxiaofeng', '2007-02-06 08:42:17', '版式定制', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001404', 'guxiaofeng', '2007-02-06 08:42:23', '版式定制', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001405', 'guxiaofeng', '2007-02-06 08:42:40', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001406', 'guxiaofeng', '2007-02-06 08:43:50', '新闻测试', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001407', 'guxiaofeng', '2007-02-06 08:43:51', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001408', 'guxiaofeng', '2007-02-06 08:44:41', '新闻添加', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001409', 'guxiaofeng', '2007-02-06 08:44:45', '版式定制', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001410', 'guxiaofeng', '2007-02-06 08:45:22', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001411', 'guxiaofeng', '2007-02-06 08:49:38', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001412', 'guxiaofeng', '2007-02-06 08:57:46', '新闻添加', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001413', 'guxiaofeng', '2007-02-06 08:57:57', '新闻模块管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001414', 'guxiaofeng', '2007-02-06 08:57:59', '新闻模块管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001415', 'guxiaofeng', '2007-02-06 08:58:01', '版式定制', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001416', 'guxiaofeng', '2007-02-06 08:58:02', '版式定制', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001417', 'guxiaofeng', '2007-02-06 08:58:02', '版式定制', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001418', 'guxiaofeng', '2007-02-06 09:05:51', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001419', 'guxiaofeng', '2007-02-06 09:06:02', '样式模版1', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001420', 'guxiaofeng', '2007-02-06 09:06:08', '样式管理', 'lookup', '192.168.1.2', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001421', 'guxiaofeng', '2007-02-06 09:23:56', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001422', 'guxiaofeng', '2007-02-06 09:24:11', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001423', 'guxiaofeng', '2007-02-06 09:26:32', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001424', 'guxiaofeng', '2007-02-06 09:26:43', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001425', 'guxiaofeng', '2007-02-06 09:26:45', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001426', 'guxiaofeng', '2007-02-06 09:26:46', '新闻模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001427', 'guxiaofeng', '2007-02-06 09:26:47', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001428', 'guxiaofeng', '2007-02-06 09:26:53', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001429', 'guxiaofeng', '2007-02-06 09:27:36', '新闻模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001430', 'guxiaofeng', '2007-02-06 09:27:44', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001431', 'yepuliang', '2007-02-06 14:33:20', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001432', 'yepuliang', '2007-02-06 15:03:13', '新闻测试', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001433', 'yepuliang', '2007-02-06 15:03:16', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001434', 'yepuliang', '2007-02-06 15:04:10', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001435', 'yepuliang', '2007-02-06 15:07:43', '样式模版1', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001436', 'yepuliang', '2007-02-06 15:12:54', '新闻模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001437', 'yepuliang', '2007-02-06 15:13:44', '新闻模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001438', 'yepuliang', '2007-02-06 15:13:45', '版式定制', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001439', 'yepuliang', '2007-02-06 15:13:46', '新闻模块管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_log` VALUES ('SYS_LOG_0000001440', 'yepuliang', '2007-02-06 15:13:47', '样式管理', 'lookup', '192.168.1.111', '');
INSERT INTO `sys_module` VALUES ('1', '/website/oa/mainOper.do?method=toMain', 'NoRight.gif', '1', 'treeRoot', '1', '卓越科技新闻系统', '0', null);
INSERT INTO `sys_module` VALUES ('10', '', 'NoRight.gif', '变量管理777', 'SYS_MODULE_0000000542', '1', '人事管理', '31', null);
INSERT INTO `sys_module` VALUES ('16', '', 'NoRight.gif', '16', '1', '0', '工作流管理', '7', null);
INSERT INTO `sys_module` VALUES ('17', null, 'NoRight.gif', '17', '16', '1', '工作流定义', '41', null);
INSERT INTO `sys_module` VALUES ('18', null, 'NoRight.gif', '18', '16', '1', '工作流查询', '42', null);
INSERT INTO `sys_module` VALUES ('19', '/website/sys/tree.do?method=loadTree', 'NoRight.gif', '19', '3', '1', '类型管理', '43', null);
INSERT INTO `sys_module` VALUES ('20', '', 'NoRight.gif', '20', 'SYS_MODULE_0000000542', '1', '考勤管理', '44', null);
INSERT INTO `sys_module` VALUES ('24', '/website/oa/absenceWork.do?method=toResign', 'NoRight.gif', '24', '20', '1', '补签登记', '32', null);
INSERT INTO `sys_module` VALUES ('25', null, 'NoRight.gif', '25', '1', '1', '网络通信', '2', null);
INSERT INTO `sys_module` VALUES ('27', '', 'NoRight.gif', '', 'SYS_MODULE_0000000542', '1', '资源管理', '33', null);
INSERT INTO `sys_module` VALUES ('28', '', 'NoRight.gif', null, '27', '1', '会议室管理', '34', null);
INSERT INTO `sys_module` VALUES ('29', '/website/oa/meetingManager.do?method=toAddPage', 'NoRight.gif', null, '28', '1', '设置', '35', null);
INSERT INTO `sys_module` VALUES ('3', null, 'NoRight.gif', '3', '1', '1', '系统管理', '8', null);
INSERT INTO `sys_module` VALUES ('30', '/website/oa/meetingManager.do?method=toMain', 'NoRight.gif', null, '28', '1', '使用查询', '36', null);
INSERT INTO `sys_module` VALUES ('31', '/website/oa/assissant/hr.do?method=toHrMain', 'NoRight.gif', '', '10', '1', '员工信息', '37', null);
INSERT INTO `sys_module` VALUES ('33', null, 'NoRight.gif', null, '27', '1', '车辆管理', '38', null);
INSERT INTO `sys_module` VALUES ('34', '/website/oa/carManager.do?method=toAdd&operSign=first', 'NoRight.gif', null, '33', '1', '车辆登记', '39', null);
INSERT INTO `sys_module` VALUES ('35', '/website/oa/carManager.do?method=toApplyPage&pageSign=first', 'NoRight.gif', null, '33', '1', '用车申请', '40', null);
INSERT INTO `sys_module` VALUES ('36', '/website/oa/carManager.do?method=toCarList', 'NoRight.gif', null, '33', '1', '车辆查询', '45', null);
INSERT INTO `sys_module` VALUES ('38', '/website/oa/meetingManager.do?method=toAdpplyPage', 'NoRight.gif', null, '28', '1', '申请', '46', null);
INSERT INTO `sys_module` VALUES ('39', '/website/oa/carApprove.do?method=toApprovePage&sign=first', 'NoRight.gif', null, '33', '1', '车辆审批', '47', null);
INSERT INTO `sys_module` VALUES ('4', '/website/sys/user/UserOper.do?method=toMain', 'NoRight.gif', '4', '3', '1', '用户管理', '48', null);
INSERT INTO `sys_module` VALUES ('40', '/website/oa/meetingManager.do?method=roomList', 'NoRight.gif', null, '28', '1', '会议室查询', '49', null);
INSERT INTO `sys_module` VALUES ('41', '/website/oa/file/fileManager.do?method=main', 'NoRight.gif', 'dsf', '1', '0', '文件管理中心', '6', null);
INSERT INTO `sys_module` VALUES ('5', '/website/sys/module.do?method=loadTree', 'NoRight.gif', '5', '3', '1', '模块管理', '50', null);
INSERT INTO `sys_module` VALUES ('6', '/website/sys/group/GroupOper.do?method=toMain', 'NoRight.gif', '6', '3', '1', '组管理', '51', null);
INSERT INTO `sys_module` VALUES ('7', '/website/sys/role/Role.do?method=toRoleMain', 'NoRight.gif', '7', '3', '1', '角色管理', '52', null);
INSERT INTO `sys_module` VALUES ('8', '/website/sys/log/LogOper.do?method=toMain', 'NoRight.gif', '8', '3', '1', '日志管理', '53', null);
INSERT INTO `sys_module` VALUES ('9', '/website/sys/dep.do?method=loadTree', 'NoRight.gif', '911', '3', '1', '部门管理', '54', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000001', '', 'NoRight.gif', '', '1', '1', '个人办公', '1', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000002', '/website/oa/privy/plan/plan.jsp', 'NoRight.gif', '', 'SYS_MODULE_0000000001', '0', '工作计划', '55', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000041', '', 'NoRight.gif', '会议管理', 'SYS_MODULE_0000000542', '1', '会议管理', '56', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000042', '', 'NoRight.gif', '资产管理', 'SYS_MODULE_0000000542', '1', '资产管理', '57', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000061', '', 'NoRight.gif', '邮件管理', '25', '1', '邮件管理', '58', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000081', '/website/oa/assissant/asset/assetsOperAction.do?method=toMain', 'NoRight.gif', '资产信息管理', 'SYS_MODULE_0000000042', '1', '资产信息管理', '59', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000082', '/website/oa/assissant/asset/assetsOperAction.do?method=toOperMain', 'NoRight.gif', '资产操作查询', 'SYS_MODULE_0000000042', '1', '资产操作查询', '60', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000101', '', 'NoRight.gif', '', 'SYS_MODULE_0000000201', '1', '新闻管理', '61', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000102', '/website/news/opernews.do?method=toArticleMain', 'NoRight.gif', '', 'SYS_MODULE_0000000101', '1', '新闻添加', '62', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000103', '/website/news/opernews.do?method=toRecycleList', 'NoRight.gif', '', 'SYS_MODULE_0000000101', '1', '回收站', '63', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000104', '/website/oa/operaffiche.do?method=toaficheMain', 'NoRight.gif', '', 'SYS_MODULE_0000000201', '1', '公告管理', '64', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000121', '/website/oa/assissant/conference/conferOper.do?method=toMain', 'NoRight.gif', '会议申请管理', 'SYS_MODULE_0000000041', '1', '会议申请管理', '65', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000181', '/website/oa/communicate/email.do?method=toEmailMain', 'NoRight.gif', '内部邮件管理', 'SYS_MODULE_0000000061', '1', '内部邮件', '66', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000182', '/website/oa/communicate/outemail.do?method=toEmailMain', 'NoRight.gif', '', 'SYS_MODULE_0000000061', '1', '外部邮件', '67', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000183', '/website/oa/communicate/emailbox.do?method=toEmailBoxMain', 'NoRight.gif', '对于外部邮箱管理', 'SYS_MODULE_0000000061', '1', '邮箱管理', '68', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000201', '', 'NoRight.gif', '公共信息', '1', '1', '公共信息', '3', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000202', '', 'NoRight.gif', '图书管理', 'SYS_MODULE_0000000201', '1', '图书管理', '69', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000203', '/website/oa/commoninfo/book.do?method=toBookMain', 'NoRight.gif', '图书入库', 'SYS_MODULE_0000000202', '1', '图书入库', '70', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000204', '/website/oa/commoninfo/bookborrow.do?method=toBookBorrowMain', 'NoRight.gif', '图书借阅查询', 'SYS_MODULE_0000000202', '1', '图书借阅查询', '71', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000222', '/website/oa/privy/addressList.do?method=toPersonalAddressListMain', 'NoRight.gif', '个人通讯录', 'SYS_MODULE_0000000001', '1', '个人通讯录', '72', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000223', '/website/oa/privy/addressList.do?method=toCommonAddressListMain', 'NoRight.gif', '公共通讯录', 'SYS_MODULE_0000000001', '1', '公共通讯录', '73', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000241', '/website/oa/privy/addressList.do?method=toAddressListMain', 'NoRight.gif', '公司通讯录', 'SYS_MODULE_0000000201', '1', '公司通讯录', '74', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000261', '/website/sys/station/station.do?method=toStationMain', 'NoRight.gif', '岗位管理', '3', '1', '岗位管理', '75', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000281', '/website/oa/commoninfo/leaveWord.do?method=toSeeLeaveWordMain', 'NoRight.gif', '留言板', 'SYS_MODULE_0000000201', '1', '留言板', '76', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000282', '/website/oa/commoninfo/leaveWord.do?method=toLeaveWordMain', 'NoRight.gif', '留言板管理', 'SYS_MODULE_0000000201', '1', '留言板管理', '77', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000301', '/website/sys/station/station.do?method=station', 'NoRight.gif', '部门岗位信息', 'SYS_MODULE_0000000201', '1', '部门岗位信息', '78', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000321', 'http://www.t7online.com/', 'NoRight.gif', '天气预报', 'SYS_MODULE_0000000201', '1', '天气预报', '25', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000322', 'http://www.post.com.cn/spec-serv/yzbm/yzbm.htm', 'NoRight.gif', '邮政编码查询', 'SYS_MODULE_0000000201', '1', '邮政编码', '24', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000341', 'http://train.chinamor.cn.net/cctt.asp', 'NoRight.gif', '火车查询', 'SYS_MODULE_0000000201', '1', '火车查询', '23', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000342', 'http://www.consumer.att.com/global/chinese/consumer_information/time.html', 'NoRight.gif', '国际时间查询', 'SYS_MODULE_0000000201', '1', '国际时间查询', '22', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000343', 'http://www.airchina.com.cn/', 'NoRight.gif', '航班查询', 'SYS_MODULE_0000000201', '1', '航班查询', '21', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000361', '/website/oa/absenceWork.do?method=toAbsence&operType=qingjia', 'NoRight.gif', '请假登记', '20', '1', '请假登记', '20', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000362', '/website/oa/absenceWork.do?method=toAbsence&operType=waichu', 'NoRight.gif', '外出登记', '20', '1', '外出登记', '19', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000363', '/website/oa/absenceWork.do?method=toAbsence&operType=chuchai', 'NoRight.gif', '出差登记', '20', '1', '出差登记', '18', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000381', '/website/oa/checkWork.do?method=toMain', 'NoRight.gif', '考勤查询', '20', '1', '考勤查询', '17', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000382', '/website/oa/absenceWork.do?method=toMain', 'NoRight.gif', '缺勤查询', '20', '1', '缺勤查询', '16', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000542', '', 'NoRight.gif', '', '1', '1', '辅助办公', '4', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000543', '', 'NoRight.gif', '', '1', '0', '公文流转', '5', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000561', '/website/oa/mainOper.do?method=toMain', 'NoRight.gif', '', '1', '1', '办公平台', '9', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000562', '/website/oa/communicate/handsetnote.do?method=toHandsetNoteMain', 'NoRight.gif', '', '25', '1', '手机短信', '11', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000563', '', 'NoRight.gif', '', '25', '1', '短消息', '12', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000564', '', 'NoRight.gif', '', '25', '1', '聊天室', '13', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000565', '', 'NoRight.gif', '', '25', '1', '论坛', '14', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000601', '/website/sys/user/UserOper.do?method=toModifyPwd', 'NoRight.gif', '修改密码', 'SYS_MODULE_0000000001', '1', '修改密码', '15', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000661', '', 'NoRight.gif', '', 'SYS_MODULE_0000000001', '1', '工作计划', 'SYS_MODULE_0000000661', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000681', '/website/plan/plan.jsp', 'NoRight.gif', '', 'SYS_MODULE_0000000661', '1', '阶段工作计划', 'SYS_MODULE_0000000681', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000682', '/website/plan/planday.jsp', 'NoRight.gif', '', 'SYS_MODULE_0000000661', '1', '详细工作计划', 'SYS_MODULE_0000000682', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000683', '/website/workplan.do?method=myplan', 'NoRight.gif', '', 'SYS_MODULE_0000000661', '1', '我的阶段计划', 'SYS_MODULE_0000000683', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000684', '/website/workmission.do?method=mymission', 'NoRight.gif', '', 'SYS_MODULE_0000000661', '1', '我的详细计划', 'SYS_MODULE_0000000684', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000685', '/website/plan/plancheck.jsp', 'NoRight.gif', '', 'SYS_MODULE_0000000661', '1', '计划审批', 'SYS_MODULE_0000000685', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000701', '/website/news/newsInfo.do?method=toNewsList', 'NoRight.gif', '', '1', '1', '新闻系统', 'SYS_MODULE_0000000701', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000721', '/website/user/userOper/register.do?method=loadregister', 'NoRight.gif', '', '1', '1', '用户注册', 'SYS_MODULE_0000000721', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000741', '/website/news/newsInfo.do?method=toNewsListTest', 'NoRight.gif', '', '1', '1', '新闻测试', 'SYS_MODULE_0000000741', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000761', '/website/news/newsInfo.do?method=toNewsTypeSelect', 'NoRight.gif', '', 'SYS_MODULE_0000000741', '1', '样式测试', 'SYS_MODULE_0000000761', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000781', '/website/news/format.do?method=formatMain', 'NoRight.gif', '', 'SYS_MODULE_0000000101', '1', '样式管理', 'SYS_MODULE_0000000781', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000801', '/website/news/newsArea.do?method=areaMain', 'NoRight.gif', '', 'SYS_MODULE_0000000101', '1', '新闻模块管理', 'SYS_MODULE_0000000801', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000821', '/website/news/newsInfo.do?method=toNewsStyleTest', 'NoRight.gif', '', 'SYS_MODULE_0000000741', '1', '样式测试2', 'SYS_MODULE_0000000821', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000841', '/website/news/newsInfo.do?method=toStyleModuleOne', 'NoRight.gif', '', 'SYS_MODULE_0000000741', '1', '样式模版1', 'SYS_MODULE_0000000841', null);
INSERT INTO `sys_module` VALUES ('SYS_MODULE_0000000861', '/website/news/newsAreaStyle.do?method=areaStyleMain', 'NoRight.gif', '', 'SYS_MODULE_0000000101', '1', '版式定制', 'SYS_MODULE_0000000861', null);
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004128', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000821');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004129', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000761');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004130', 'SYS_GROUP_0000000101', '1');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004131', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000101');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004132', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000103');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004133', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000102');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004134', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000741');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004135', 'SYS_GROUP_0000000101', 'SYS_MODULE_0000000201');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004136', 'administrator', 'SYS_MODULE_0000000841');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004137', 'administrator', 'SYS_MODULE_0000000861');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004138', 'administrator', 'SYS_MODULE_0000000801');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004139', 'administrator', '19');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004140', 'administrator', '3');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004141', 'administrator', 'SYS_MODULE_0000000781');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004142', 'administrator', '6');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004143', 'administrator', '1');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004144', 'administrator', 'SYS_MODULE_0000000101');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004145', 'administrator', '5');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004146', 'administrator', '4');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004147', 'administrator', 'SYS_MODULE_0000000103');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004148', 'administrator', 'SYS_MODULE_0000000102');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004149', 'administrator', '7');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004150', 'administrator', 'SYS_MODULE_0000000741');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004151', 'administrator', 'SYS_MODULE_0000000201');
INSERT INTO `sys_right_group` VALUES ('SYSRIGHTUSER_0000004152', 'administrator', '8');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003021', 'zhaoyifei', '25');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003022', 'zhaoyifei', 'SYS_MODULE_0000000562');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003023', 'zhaoyifei', '1');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003734', 'jingyuzhuo', 'SYS_MODULE_0000000683');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003735', 'jingyuzhuo', 'SYS_MODULE_0000000661');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003736', 'jingyuzhuo', 'SYS_MODULE_0000000001');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003737', 'jingyuzhuo', 'SYS_MODULE_0000000682');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003738', 'jingyuzhuo', 'SYS_MODULE_0000000685');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003739', 'jingyuzhuo', 'SYS_MODULE_0000000721');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003740', 'jingyuzhuo', 'SYS_MODULE_0000000684');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003741', 'jingyuzhuo', '19');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003742', 'jingyuzhuo', '3');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003743', 'jingyuzhuo', '1');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003744', 'jingyuzhuo', 'SYS_MODULE_0000000681');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003961', 'yepuliang', 'SYS_MODULE_0000000841');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003962', 'yepuliang', 'SYS_MODULE_0000000861');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003963', 'yepuliang', 'SYS_MODULE_0000000821');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003964', 'yepuliang', 'SYS_MODULE_0000000761');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003965', 'yepuliang', 'SYS_MODULE_0000000801');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003966', 'yepuliang', 'SYS_MODULE_0000000721');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003967', 'yepuliang', 'SYS_MODULE_0000000561');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003968', 'yepuliang', '9');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003969', 'yepuliang', '19');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003970', 'yepuliang', 'SYS_MODULE_0000000781');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003971', 'yepuliang', '3');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003972', 'yepuliang', '6');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003973', 'yepuliang', '1');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003974', 'yepuliang', '5');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003975', 'yepuliang', '4');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003976', 'yepuliang', 'SYS_MODULE_0000000261');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003977', 'yepuliang', '7');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003978', 'yepuliang', 'SYS_MODULE_0000000741');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003979', 'yepuliang', 'SYS_MODULE_0000000701');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000003980', 'yepuliang', '8');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000004065', 'guxiaofeng', 'SYS_MODULE_0000000841');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000004066', 'guxiaofeng', 'SYS_MODULE_0000000801');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000004067', 'guxiaofeng', 'SYS_MODULE_0000000781');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000004068', 'guxiaofeng', '1');
INSERT INTO `sys_right_user` VALUES ('SYSRIGHTUSER_0000004069', 'guxiaofeng', 'SYS_MODULE_0000000741');
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
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000341', '1', '', '', 'common', null, 'idu', '1', '', '公共信息', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000342', '1', '', '', '客户关系管理', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000361', 'SYS_TREE_0000000342', '', 'query_sort', '查询类型', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000362', 'SYS_TREE_0000000361', '', 'sort2', '类型2', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000363', 'SYS_TREE_0000000361', '', 'sort1', '类型1', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000364', '1', '', 'auditing', '审核类型', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000365', 'SYS_TREE_0000000364', '', 'auditing_ing', '审核中', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000366', 'SYS_TREE_0000000364', '', 'auditing_no', '未审核', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000383', 'SYS_TREE_0000000364', '', 'auditing_finsh', '已审核', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000401', '1', '', '', '新闻列表样式管理', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000402', 'SYS_TREE_0000000401', '', '', '样式1', null, 'idu', '1', '', '只显示标题', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000403', 'SYS_TREE_0000000402', '', 'type_one', 'one', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000404', 'SYS_TREE_0000000402', '', 'type_one_class', 'zhi', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000421', 'SYS_TREE_0000000401', '', '', '样式2', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000422', 'SYS_TREE_0000000421', '', 'type_two', 'two', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_tree` VALUES ('SYS_TREE_0000000423', 'SYS_TREE_0000000421', '', 'type_two_class', 'zhi', null, 'idu', '1', '', '', '0', null);
INSERT INTO `sys_user` VALUES ('chenyiying', '58311CD2040A696B4F9C68C7748D9430', '陈轶英', 'SYS_GROUP_0000000101', 'SYS_ROLE_0000000181', '3', '1', '', '0');
INSERT INTO `sys_user` VALUES ('guxiaofeng', '543424CCE1BF8BED2C1E4D4B872D2B87', '辜晓峰', 'administrator', 'administrator', '1', '1', '', '0');
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
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000000941', '', 'zhangfeng', '', '2006-11-30 09:37:59', '2006-11-28 00:00:00', '2006-12-04 00:00:00', '论坛制作', 'zhangfeng', 'SYS_TREE_0000000244', '', '论坛', '论坛制作', '', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000000961', '', 'guxiaofeng', '', '2006-12-01 13:44:15', null, null, 'aaaaaaa', '', 'SYS_TREE_0000000243', '', '', 'cccccccccccc', 'ddddddddd', '0');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000000962', '', 'guxiaofeng', '', '2006-12-01 13:45:59', null, null, 'rrrrrrrrrrr', '', 'SYS_TREE_0000000243', '', '', 'sssssssssssssssssssssssss', '', '0');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000000981', '', 'yepuliang', '', '2006-12-04 08:39:23', '2006-12-04 00:00:00', '2006-12-05 00:00:00', '卓越论坛计划', 'yepuliang', 'SYS_TREE_0000000244', '', '论坛前台', '完成论坛前台常用操作，包括模块列表，帖子管理，权限操作，常用查询等核心模块。', '', '0');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000000982', '', 'zhaoyifei', '', '2006-12-08 17:05:57', '2006-12-08 00:00:00', '2006-12-31 00:00:00', '12月份工作计划', 'zhaoyifei', 'SYS_TREE_0000000243', '', '12月', '12月份工作计划', '12月份工作计划', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001001', '', 'guxiaofeng', '', '2006-12-11 08:35:01', '2006-12-11 00:00:00', '2006-12-11 00:00:00', 'rrrr', 'guxiaofeng', 'SYS_TREE_0000000243', '', 'sss', 'tttt', 'uuuu', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001002', '', 'zhangfeng', '', '2006-12-11 08:38:42', '2006-12-11 00:00:00', '2006-12-16 00:00:00', '12.11日周工作计划', 'zhangfeng', 'SYS_TREE_0000000244', '', '', '论坛工作继续，市局项目添加及修改，ETNews新闻后台移植', '', '2');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001003', '', 'yepuliang', '', '2006-12-12 09:45:23', '2006-12-11 00:00:00', '2006-12-15 00:00:00', '论坛后台计划', 'yepuliang', 'SYS_TREE_0000000244', '', '论坛后台', '论坛后台的制作', '', '2');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001004', '', 'zhangfeng', '', '2006-12-18 09:57:43', '2006-12-18 00:00:00', '2006-12-23 00:00:00', '论坛制作', 'zhangfeng', 'SYS_TREE_0000000244', '', '', '继续完成论坛未完成的工作', '', '2');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001005', '', 'yepuliang', '', '2006-12-18 10:16:50', '2006-12-18 00:00:00', '2006-12-22 00:00:00', '论坛后台制作', 'yepuliang', 'SYS_TREE_0000000244', '', '模块管理', '', '', '2');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001006', '', 'jingyuzhuo', '', '2006-12-18 10:23:29', '2006-12-11 00:00:00', '2006-12-25 00:00:00', 'crm客户管理系统', 'jingyuzhuo', 'SYS_TREE_0000000243', '', 'crm', '完成客户管理系统的其中\r\n客户的模块', '', '0');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001021', '', 'yepuliang', '', '2006-12-20 11:01:04', '2006-12-25 00:00:00', '2006-12-29 00:00:00', '后台模块时间估算', 'yepuliang', 'SYS_TREE_0000000243', '', '后台', '后台时间估算：\r\n\r\n1，用户管理：包括（核心功能用户列表，用户搜索）估算大约一天。\r\n\r\n2.替换限制处理：（核心功能：帖子词语过滤，保留敏感用户名，屏蔽用户言论）大约1天半\r\n\r\n3.论坛备份：（全部备份：包括全部论坛数据表数据。标准备份：包括常用的数据表数据。\r\n最小备份：仅包括用户、版块设置及系统设置数据。\r\n自定义备份：根据需要自行选择需要备份的数据表\r\n）半天到1天\r\n\r\n4.日志操作\r\n\r\n密码错误日志，用户评分日志，积分交易日志，版主管理日志，禁止用户日志，后台访问记录，系统错误记录。系统日志\r\n用户日志，版主日志\r\n模块日志，论坛安全日志\r\n积分操作日志（估计1天到3天具体情况定）\r\n\r\n5.扩展功能：email群发，短消息群发，计划任务。（1天到2天）', '', '0');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001022', '', 'yepuliang', '', '2006-12-20 16:19:15', '2006-12-20 00:00:00', '2006-12-22 00:00:00', '论坛前台收尾', '', 'SYS_TREE_0000000244', '', '论坛前台收尾', '1.查看新帖，发帖排行，搜索\r\n（周四，周五）\r\n\r\n2.完成联系人管理', '发帖排行和搜索已完成\r\n\r\n联系人管理已完成', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001041', '', 'yepuliang', '', '2006-12-25 08:53:31', '2006-12-25 00:00:00', '2006-12-29 00:00:00', '开始着手后台', 'yepuliang', 'SYS_TREE_0000000243', '', '', '1，用户管理：包括（核心功能用户列表，用户搜索）1天\r\n\r\n2.替换限制处理：（核心功能：帖子词语过滤，保留敏感用户名，屏蔽用户言论）1天半\r\n\r\n3.论坛备份：（全部备份：包括全部论坛数据表数据。标准备份：包括常用的数据表数据。\r\n最小备份：仅包括用户、版块设置及系统设置数据。\r\n自定义备份：根据需要自行选择需要备份的数据表\r\n）1天半', '', '2');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001061', '', 'jingyuzhuo', '', '2006-12-26 09:35:24', '2006-12-26 00:00:00', '2006-12-31 00:00:00', 'CRM软件开发', '', 'SYS_TREE_0000000244', '', 'CRM', '1.OK\r\n2.NO\r\n3.OK\r\n4.OK\r\n', '1.OK\r\n2.NO\r\n3.OK\r\n4.OK\r\n', '1');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001062', '', 'jingyuzhuo', '', '2006-12-26 09:58:27', null, null, '1', '', 'SYS_TREE_0000000243', '', '', '', '', '');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001063', '', 'jingyuzhuo', '', '2006-12-26 10:22:58', null, null, '12', '', 'SYS_TREE_0000000243', '', '', '', '', '');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001064', '', 'jingyuzhuo', '', '2006-12-26 10:45:56', null, null, '3', 'jingyuzhuo', 'SYS_TREE_0000000243', '', '', '', '', '2');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001081', '', 'jingyuzhuo', '', '2006-12-26 13:46:04', null, null, '2', 'jingyuzhuo', 'SYS_TREE_0000000243', '', '', '', '', '2');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001082', '', 'jingyuzhuo', '', '2006-12-26 13:46:41', null, null, '2', 'jingyuzhuo', 'SYS_TREE_0000000243', '', '', '', '', '2');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001083', '', 'jingyuzhuo', '', '2006-12-26 13:46:46', null, null, '2', 'jingyuzhuo', 'SYS_TREE_0000000243', '', '', '', '', '2');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001084', '', 'jingyuzhuo', '', '2006-12-26 13:47:09', null, null, '2', 'jingyuzhuo', 'SYS_TREE_0000000243', '', '', '', '', '2');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001085', '', 'jingyuzhuo', '', '2006-12-26 14:10:50', null, null, '2', 'jingyuzhuo', 'SYS_TREE_0000000243', '', '', '', '', '2');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001101', '', 'jingyuzhuo', '', '2006-12-29 08:53:34', null, null, '33', 'jingyuzhuo', 'SYS_TREE_0000000243', '', '', '', '', '2');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001102', '', 'jingyuzhuo', '', '2006-12-29 08:54:11', null, null, 'jing', 'jingyuzhuo', 'SYS_TREE_0000000243', '', '', '', '', '2');
INSERT INTO `work_plan_info` VALUES ('WORK_PLAN_INFO_0000001103', '', 'yepuliang', '', '2007-01-04 13:32:57', '2007-01-04 00:00:00', '2007-01-10 00:00:00', '后台管理', '', 'SYS_TREE_0000000245', '', '后台', '1.限制替换处理  1天(完成)\r\n2.论坛数据备份  1天\r\n3.日志管理      1天(完成)', '', '');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000741', '帖子操作', 'WORK_PLAN_INFO_0000000941', '2006-11-30 09:39:06', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000941', 'zhangfeng', '2006-11-28 00:00:00', '2006-11-30 00:00:00', '', '', '帖子操作接口的实现与完成<2006-12-1 15:11>\r\n发帖<2006-12-1 15:11>\r\n回帖<2006-12-1 15:11>\r\n结帖<2006-12-1 15:11>\r\n推荐订阅<2006-12-1 15:11>\r\n收藏<2006-12-1 15:11>\r\n编缉<2006-12-1 15:11>\r\n引用<2006-12-1 15:11>\r\n报告<2006-12-1 15:11>\r\n页面制作<2006-12-1 15:12>', '3', '发帖完成<2006-12-5 10:32>\r\n回帖完成<2006-12-5 10:33>\r\n收藏完成<2006-12-5 10:33>', '2006-12-05 10:33:28', '', '', 'zhangfeng');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000742', '权限操作接口', 'WORK_PLAN_INFO_0000000941', '2006-11-30 09:39:59', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000941', 'zhangfeng', '2006-11-30 00:00:00', '2006-12-03 00:00:00', '', '', '加入精华权限操作接口的实现与完成<2006-12-1 15:12>\r\n删除帖子<2006-12-1 15:12>\r\n移动帖子<2006-12-1 15:12>\r\n高亮显示<2006-12-1 15:12>\r\n置顶<2006-12-1 15:12>\r\n<2006-12-6 14:8>', '3', '', '2006-12-06 14:08:29', '', '', 'zhangfeng');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000743', '用户接口', 'WORK_PLAN_INFO_0000000941', '2006-11-30 09:40:41', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000941', 'zhangfeng', '2006-11-03 00:00:00', '2006-11-06 00:00:00', '', '', '用户接口的操作与完成<2006-12-1 15:13>\r\n包括用户登录<2006-12-1 15:13>\r\n用户操作等信息<2006-12-1 15:13>\r\n包括登陆页<2006-12-1 15:13>\r\n用户显示信息<2006-12-1 15:13>', '3', '用户登陆完成<2006-12-5 10:34>\r\n登陆页完成<2006-12-5 10:34>', '2006-12-05 10:34:19', '', '', 'zhangfeng');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000744', '周二', null, '2006-11-30 09:44:01', '', 'SYS_TREE_0000000281', null, 'yepuliang', '2006-11-30 00:00:00', '2006-11-30 00:00:00', '', '', '完成论坛常用查询', '3', '', '2006-11-30 09:44:16', '一天够呛', '', 'yepuliang');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000745', '周二', null, '2006-11-30 09:44:21', '', 'SYS_TREE_0000000281', null, 'yepuliang', '2006-11-30 00:00:00', '2006-11-30 00:00:00', '', '', '完成论坛常用查询', '2', '', null, '一天够呛', '', 'yepuliang');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000746', '整理投标资料', null, '2006-11-30 09:44:53', '投标资料', 'SYS_TREE_0000000281', null, 'guxiaofeng', '2006-11-30 00:00:00', '2006-12-05 00:00:00', '', '', '1/整理投标资料（5项）\r\n2/做\r\n做投标资料空白书', '2', '', null, '', '', 'chenyiying');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000748', '整理标投资料', null, '2006-11-30 09:51:45', '整理资料', 'SYS_TREE_0000000281', null, 'guxiaofeng', '2006-11-30 00:00:00', '2006-12-05 00:00:00', '', '', '1/整理投标资料5大类\r\n2/复印相关证件\r\n3/整理空白投标文件\r\n', '2', '', null, '', '', 'chenyiying');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000750', '整理投标资料', null, '2006-11-30 09:55:26', '投标资料', 'SYS_TREE_0000000281', null, 'chenyiying', '2006-11-30 00:00:00', '2006-12-05 00:00:00', '', '', '1/整理填写投标资料\r\n2/复印相关证件\r\n3/做空白投标文件', '2', '', null, '', '', 'chenyiying');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000752', '论坛', null, '2006-11-30 10:08:43', '论坛', 'SYS_TREE_0000000281', null, 'chenyiying', '2006-12-06 00:00:00', '2006-12-31 00:00:00', '', '', '1/论坛平面', '2', '', null, '', '', 'chenyiying');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000781', 'rrrrrrrr', 'WORK_PLAN_INFO_0000000962', '2006-12-01 13:46:10', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000962', 'guxiaofeng', '2006-12-01 00:00:00', '2006-12-01 00:00:00', '', '', 'ssssssssssssssssssssssssssss', '2', 'rrr<2006-12-1 13:47>\r\nsss<2006-12-1 13:47>\r\nttt<2006-12-1 13:47>', '2006-12-01 13:47:38', 'tttttttttttttttttttttt', '', 'guxiaofeng');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000801', '程序修改，套页面', 'WORK_PLAN_INFO_0000000941', '2006-12-01 15:09:57', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000941', 'yepuliang,zhangfeng', '2006-12-03 00:00:00', '2006-12-07 00:00:00', '', '', '包括楼号<2006-12-1 15:10>\r\n首页显示登陆列表信息<2006-12-1 15:10>\r\n权限显示<2006-12-1 15:10>\r\n列表页面信息细节的补充<2006-12-1 15:10>', '3', '楼号添加已完成<2006-12-5 10:32>', '2006-12-05 10:32:14', '', '', 'zhangfeng');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000821', '周一', 'WORK_PLAN_INFO_0000000981', '2006-12-04 08:41:49', '论坛前台', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000981', 'yepuliang', '2006-12-04 00:00:00', '2006-12-08 00:00:00', '', '', '1.完成常用查询<2006-12-4 8:41>\r\n2.配合张锋完成帖子列表<2006-12-4 8:41>\r\n3.测试上周完成的程序<2006-12-4 8:42>\r\n<2006-12-4 12:5>\r\n<2006-12-4 12:5>\r\naaaaaaa<2006-12-6 9:0>', '1', '', '2006-12-12 09:43:36', '', '', 'yepuliang');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000822', '周二', 'WORK_PLAN_INFO_0000000981', '2006-12-04 08:44:01', '论坛前台', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000981', 'yepuliang', '2006-12-05 00:00:00', '2006-12-05 00:00:00', '', '', '1.完成部分辅助功能<2006-12-4 8:43>\r\n2.实现各个模块整合<2006-12-4 8:43>\r\n3.查找遗漏点<2006-12-4 8:44>\r\n4.测试<2006-12-4 8:44>', '1', '', '2006-12-12 09:43:44', '', '', 'yepuliang');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000823', '周三', 'WORK_PLAN_INFO_0000000981', '2006-12-04 08:46:24', '论坛后台', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000981', 'yepuliang', '2006-12-06 00:00:00', '2006-12-08 00:00:00', '', '', '1.开始论坛后台程序的编写<2006-12-4 8:45>\r\n2.首先完成模块管理<2006-12-4 8:45>\r\n3.完成人员管理<2006-12-4 8:46>\r\n4.实现与前台的更好结合<2006-12-4 8:46>', '1', '', '2006-12-12 09:43:50', '', '', 'yepuliang');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000824', '周四', 'WORK_PLAN_INFO_0000000981', '2006-12-04 08:50:41', '论坛后台', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000981', 'yepuliang', '2006-12-07 00:00:00', '2006-12-07 00:00:00', '', '', '论坛后台模块管理<2006-12-4 8:51>', '1', '', '2006-12-12 09:43:55', '', '', 'yepuliang');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000825', '周五', 'WORK_PLAN_INFO_0000000981', '2006-12-04 08:51:58', '论坛后台', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000981', 'yepuliang', '2006-12-08 00:00:00', '2006-12-08 00:00:00', '', '', '实现后台用户管理<2006-12-4 8:52>', '1', '', '2006-12-12 09:44:00', '', '', 'yepuliang');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000826', '第二周工作计划', 'WORK_PLAN_INFO_0000000982', '2006-12-08 17:06:40', '第二周', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000982', 'zhaoyifei', '2006-12-11 00:00:00', '2006-12-15 00:00:00', '', '', '第二周<2006-12-8 17:6>', '2', '', '2006-12-08 17:07:00', '', '', 'zhaoyifei');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000827', '第三周工作计划', 'WORK_PLAN_INFO_0000000982', '2006-12-08 17:08:03', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000982', 'zhaoyifei', '2006-12-18 00:00:00', '2006-12-22 00:00:00', '', '', '第三周<2006-12-8 17:8>', '2', '', null, '', '', 'zhaoyifei');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000828', '第四周工作计划', 'WORK_PLAN_INFO_0000000982', '2006-12-08 17:08:41', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000000982', 'zhaoyifei', '2006-12-25 00:00:00', '2006-12-29 00:00:00', '', '', '第四周<2006-12-8 17:8>\r\n<2006-12-8 17:10>', '2', '', '2006-12-08 17:10:38', '', '', 'zhaoyifei');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000861', 'aaaa', 'WORK_PLAN_INFO_0000001001', '2006-12-11 08:35:31', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001001', 'guxiaofeng', '2006-12-11 00:00:00', '2006-12-11 00:00:00', '', '', 'ttt<2006-12-11 8:35>', '2', '', null, 'rrrr', '', 'guxiaofeng');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000862', '公安项目添加程序', 'WORK_PLAN_INFO_0000001002', '2006-12-11 09:04:54', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001002', 'zhangfeng', '2006-12-11 00:00:00', '2006-12-11 00:00:00', '', '', '公安项目未完成添加程序，包括查询列表，警员注册信息查询，上下列表添加<2006-12-11 8:42>', '2', '', null, '', '', 'zhangfeng');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000863', '后台模块管理', 'WORK_PLAN_INFO_0000001003', '2006-12-12 09:46:45', '模块管理', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001003', 'yepuliang', '2006-12-12 00:00:00', '2006-12-12 00:00:00', '', '', '1。对论坛模块的增加，修改操作<2006-12-12 9:47>', '1', '', '2006-12-26 15:31:48', '', '', 'yepuliang');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000864', '论坛页面完善', 'WORK_PLAN_INFO_0000001004', '2006-12-18 10:00:07', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001004', 'yepuliang,zhangfeng', '2006-12-18 00:00:00', '2006-12-23 00:00:00', '', '', '是否缺页，注册等页面制作<2006-12-18 9:59>\r\n页面查找，大概需要一天时间<2006-12-20 15:50>', '2', '', '2006-12-20 15:50:49', '', '', 'zhangfeng');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000865', '功能完善', 'WORK_PLAN_INFO_0000001004', '2006-12-18 10:00:53', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001004', 'zhangfeng,yepuliang', '2006-12-18 00:00:00', '2006-12-23 00:00:00', '', '', '编缉，转发等功能细致完成<2006-12-18 10:1>\r\n发帖回帖功能实现，加上UBB标签转换等工作，需时半天<2006-12-20 15:44>', '3', '', '2006-12-20 15:44:27', '', '', 'zhangfeng');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000866', '注册，登录', 'WORK_PLAN_INFO_0000001004', '2006-12-18 10:01:50', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001004', 'zhangfeng', '2006-12-18 00:00:00', '2006-12-18 00:00:00', '', '', '页面及程序完成<2006-12-18 10:2>\r\n登录用户名是否重复，注册过后匿名不能重复<2006-12-18 10:17>\r\n论坛列表显示，用户信息<2006-12-18 15:24>', '3', '注册完成，登陆完成<2006-12-18 12:56>\r\n论坛列表显示，用户信息<2006-12-18 15:24>\r\nHelix安装，通过<2006-12-18 16:38>', '2006-12-18 16:37:52', '', '', 'zhangfeng');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000867', '后台管理', 'WORK_PLAN_INFO_0000001005', '2006-12-18 10:18:21', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001005', 'yepuliang', '2006-12-18 00:00:00', '2006-12-19 00:00:00', '', '', '1.模块添加，修改<2006-12-18 10:18>\r\n2.详细细节设置<2006-12-18 10:18>', '1', '', '2006-12-26 15:31:40', '', '', 'yepuliang');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000868', '套页面', 'WORK_PLAN_INFO_0000001005', '2006-12-18 10:20:15', '页面', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001005', 'yepuliang', '2006-12-20 00:00:00', '2006-12-21 00:00:00', '', '', '1.套页面<2006-12-18 10:20>\r\n2.论坛整合<2006-12-18 10:20>', '1', '', '2006-12-26 15:31:32', '', '', 'yepuliang');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000869', '前后台整合测试', 'WORK_PLAN_INFO_0000001005', '2006-12-18 10:22:10', '整合测试', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001005', 'yepuliang', '2006-12-22 00:00:00', '2006-12-22 00:00:00', '', '', '1.进一步完善。<2006-12-18 10:21>\r\n2.测试，出精简版先能用<2006-12-18 10:22>', '2', '', null, '', '', 'yepuliang');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000870', '补充', 'WORK_PLAN_INFO_0000001005', '2006-12-18 14:35:24', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001005', 'yepuliang', '2006-12-19 00:00:00', '2006-12-19 00:00:00', '', '', '1.制作控制面板<2006-12-18 14:35>\r\n2.收藏夹<2006-12-18 14:35>\r\n3.联系人管理<2006-12-18 14:35>\r\n4.论坛搜索<2006-12-18 14:35>', '1', '1,控制面板完成\r\n\r\n2收藏夹完成<2006-12-20 17:15>', '2006-12-26 15:31:13', '', '', 'yepuliang');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000881', '查看新帖，搜索', 'WORK_PLAN_INFO_0000001004', '2006-12-20 15:45:47', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001004', 'yepuliang', '2006-12-20 00:00:00', '2006-12-20 00:00:00', '', '', '查看新帖，2个小时<2006-12-20 15:45>\r\n搜索，4个小时<2006-12-20 15:46>\r\n导航条，1个小时<2006-12-20 15:49>\r\n选择导航，一个小时<2006-12-20 15:49>', '1', '', '2006-12-26 15:31:00', '', '', 'zhangfeng');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000901', 'CRM软件开发', 'WORK_PLAN_INFO_0000001061', '2006-12-26 09:40:38', 'CRM', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001061', 'jingyuzhuo', '2006-12-26 00:00:00', '2006-12-26 00:00:00', '', '', 'JNNNJNJNJ<2006-12-26 9:40>', '2', '', null, 'L,LMLK', '', 'jingyuzhuo');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000921', '1', 'WORK_PLAN_INFO_0000001084', '2006-12-26 13:48:53', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001084', 'jingyuzhuo', '2006-12-26 00:00:00', '2006-12-26 00:00:00', '', '', '', '2', '', null, '', '', 'jingyuzhuo');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000922', '1', null, '2006-12-26 14:06:43', '', 'SYS_TREE_0000000281', null, 'jingyuzhuo', '2006-12-26 00:00:00', '2006-12-26 00:00:00', '', '', '', '2', '', null, '', '', 'jingyuzhuo');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000923', '3232', null, '2006-12-26 14:07:16', '', 'SYS_TREE_0000000281', null, 'jingyuzhuo', '2006-12-26 00:00:00', '2006-12-26 00:00:00', '', '', '', '2', '', null, '', '', 'jingyuzhuo');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000924', '2', 'WORK_PLAN_INFO_0000001085', '2006-12-26 14:10:57', '', 'SYS_TREE_0000000281', 'WORK_PLAN_INFO_0000001085', 'jingyuzhuo', '2006-12-26 00:00:00', '2006-12-26 00:00:00', '', '', '', '2', '', null, '', '', 'jingyuzhuo');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000941', '24', null, '2006-12-29 08:52:28', '', 'SYS_TREE_0000000281', null, 'jingyuzhuo', '2006-12-29 00:00:00', '2006-12-29 00:00:00', '', '', '', '2', '', null, '', '', 'jingyuzhuo');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000942', '123', null, '2006-12-29 08:53:12', '', 'SYS_TREE_0000000281', null, 'jingyuzhuo', '2006-12-29 00:00:00', '2006-12-29 00:00:00', '', '', '', '2', '', null, '', '', 'jingyuzhuo');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000943', 'jingyuzhuo', null, '2006-12-29 09:07:52', 'jyz', 'SYS_TREE_0000000281', null, 'jingyuzhuo', '2006-12-29 00:00:00', '2006-12-29 00:00:00', '', '', '', '2', '', null, 'mj', '', 'jingyuzhuo');
INSERT INTO `work_plan_mission` VALUES ('WORK_PLAN_MISSION_0000000944', '后台剩余功能', null, '2007-01-04 13:26:38', '后台', 'SYS_TREE_0000000281', null, 'guxiaofeng', '2007-01-04 00:00:00', '2007-01-10 00:00:00', '', '', '1.完成替换，限制处理<2007-1-4 13:26>', '2', '', null, '', '', 'yepuliang');
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
