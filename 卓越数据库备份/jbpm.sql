/*
MySQL Data Transfer
Source Host: 192.168.1.200
Source Database: jbpm
Target Host: 192.168.1.200
Target Database: jbpm
Date: 2007-3-25 13:46:27
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for jbpm_action
-- ----------------------------
CREATE TABLE `jbpm_action` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `class` char(1) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `ISPROPAGATIONALLOWED_` char(1) default NULL,
  `REFERENCEDACTION_` bigint(20) default NULL,
  `ACTIONDELEGATION_` bigint(20) default NULL,
  `EVENT_` bigint(20) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `EXPRESSION_` mediumtext,
  `TIMERNAME_` varchar(255) default NULL,
  `DUEDATE_` varchar(255) default NULL,
  `REPEAT_` varchar(255) default NULL,
  `TRANSITIONNAME_` varchar(255) default NULL,
  `TIMERACTION_` bigint(20) default NULL,
  `EVENTINDEX_` int(11) default NULL,
  `EXCEPTIONHANDLER_` bigint(20) default NULL,
  `EXCEPTIONHANDLERINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_ACTION_EVENT` (`EVENT_`),
  KEY `FK_ACTION_EXPTHDL` (`EXCEPTIONHANDLER_`),
  KEY `FK_ACTION_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_CRTETIMERACT_TA` (`TIMERACTION_`),
  KEY `FK_ACTION_ACTNDEL` (`ACTIONDELEGATION_`),
  KEY `FK_ACTION_REFACT` (`REFERENCEDACTION_`),
  CONSTRAINT `FK_ACTION_ACTNDEL` FOREIGN KEY (`ACTIONDELEGATION_`) REFERENCES `jbpm_delegation` (`ID_`),
  CONSTRAINT `FK_ACTION_EVENT` FOREIGN KEY (`EVENT_`) REFERENCES `jbpm_event` (`ID_`),
  CONSTRAINT `FK_ACTION_EXPTHDL` FOREIGN KEY (`EXCEPTIONHANDLER_`) REFERENCES `jbpm_exceptionhandler` (`ID_`),
  CONSTRAINT `FK_ACTION_PROCDEF` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`),
  CONSTRAINT `FK_ACTION_REFACT` FOREIGN KEY (`REFERENCEDACTION_`) REFERENCES `jbpm_action` (`ID_`),
  CONSTRAINT `FK_CRTETIMERACT_TA` FOREIGN KEY (`TIMERACTION_`) REFERENCES `jbpm_action` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_bytearray
-- ----------------------------
CREATE TABLE `jbpm_bytearray` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `FILEDEFINITION_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_BYTEARR_FILDEF` (`FILEDEFINITION_`),
  CONSTRAINT `FK_BYTEARR_FILDEF` FOREIGN KEY (`FILEDEFINITION_`) REFERENCES `jbpm_moduledefinition` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_byteblock
-- ----------------------------
CREATE TABLE `jbpm_byteblock` (
  `PROCESSFILE_` bigint(20) NOT NULL,
  `BYTES_` blob,
  `INDEX_` int(11) NOT NULL,
  PRIMARY KEY  (`PROCESSFILE_`,`INDEX_`),
  KEY `FK_BYTEBLOCK_FILE` (`PROCESSFILE_`),
  CONSTRAINT `FK_BYTEBLOCK_FILE` FOREIGN KEY (`PROCESSFILE_`) REFERENCES `jbpm_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_comment
-- ----------------------------
CREATE TABLE `jbpm_comment` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `ACTORID_` varchar(255) default NULL,
  `TIME_` datetime default NULL,
  `MESSAGE_` mediumtext,
  `TOKEN_` bigint(20) default NULL,
  `TASKINSTANCE_` bigint(20) default NULL,
  `TOKENINDEX_` int(11) default NULL,
  `TASKINSTANCEINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_COMMENT_TOKEN` (`TOKEN_`),
  KEY `FK_COMMENT_TSK` (`TASKINSTANCE_`),
  CONSTRAINT `FK_COMMENT_TOKEN` FOREIGN KEY (`TOKEN_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_COMMENT_TSK` FOREIGN KEY (`TASKINSTANCE_`) REFERENCES `jbpm_taskinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_decisionconditions
-- ----------------------------
CREATE TABLE `jbpm_decisionconditions` (
  `DECISION_` bigint(20) NOT NULL,
  `TRANSITIONNAME_` varchar(255) default NULL,
  `EXPRESSION_` mediumtext,
  `INDEX_` int(11) NOT NULL,
  PRIMARY KEY  (`DECISION_`,`INDEX_`),
  KEY `FK_DECCOND_DEC` (`DECISION_`),
  CONSTRAINT `FK_DECCOND_DEC` FOREIGN KEY (`DECISION_`) REFERENCES `jbpm_node` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_delegation
-- ----------------------------
CREATE TABLE `jbpm_delegation` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASSNAME_` mediumtext,
  `CONFIGURATION_` mediumtext,
  `CONFIGTYPE_` varchar(255) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_DELEGATION_PRCD` (`PROCESSDEFINITION_`),
  CONSTRAINT `FK_DELEGATION_PRCD` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_event
-- ----------------------------
CREATE TABLE `jbpm_event` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `EVENTTYPE_` varchar(255) default NULL,
  `TYPE_` char(1) default NULL,
  `GRAPHELEMENT_` bigint(20) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `NODE_` bigint(20) default NULL,
  `TRANSITION_` bigint(20) default NULL,
  `TASK_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_EVENT_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_EVENT_NODE` (`NODE_`),
  KEY `FK_EVENT_TRANS` (`TRANSITION_`),
  KEY `FK_EVENT_TASK` (`TASK_`),
  CONSTRAINT `FK_EVENT_NODE` FOREIGN KEY (`NODE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_EVENT_PROCDEF` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`),
  CONSTRAINT `FK_EVENT_TASK` FOREIGN KEY (`TASK_`) REFERENCES `jbpm_task` (`ID_`),
  CONSTRAINT `FK_EVENT_TRANS` FOREIGN KEY (`TRANSITION_`) REFERENCES `jbpm_transition` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_exceptionhandler
-- ----------------------------
CREATE TABLE `jbpm_exceptionhandler` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `EXCEPTIONCLASSNAME_` text,
  `TYPE_` char(1) default NULL,
  `GRAPHELEMENT_` bigint(20) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `GRAPHELEMENTINDEX_` int(11) default NULL,
  `NODE_` bigint(20) default NULL,
  `TRANSITION_` bigint(20) default NULL,
  `TASK_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_id_group
-- ----------------------------
CREATE TABLE `jbpm_id_group` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `TYPE_` varchar(255) default NULL,
  PRIMARY KEY  (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_id_membership
-- ----------------------------
CREATE TABLE `jbpm_id_membership` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `ROLE_` varchar(255) default NULL,
  `USER_` bigint(20) default NULL,
  `GROUP_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_ID_MEMSHIP_GRP` (`GROUP_`),
  KEY `FK_ID_MEMSHIP_USR` (`USER_`),
  CONSTRAINT `FK_ID_MEMSHIP_GRP` FOREIGN KEY (`GROUP_`) REFERENCES `jbpm_id_group` (`ID_`),
  CONSTRAINT `FK_ID_MEMSHIP_USR` FOREIGN KEY (`USER_`) REFERENCES `jbpm_id_user` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_id_permissions
-- ----------------------------
CREATE TABLE `jbpm_id_permissions` (
  `ENTITY_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) default NULL,
  `NAME_` varchar(255) default NULL,
  `ACTION_` varchar(255) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_id_user
-- ----------------------------
CREATE TABLE `jbpm_id_user` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `EMAIL_` varchar(255) default NULL,
  `PASSWORD_` varchar(255) default NULL,
  PRIMARY KEY  (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_log
-- ----------------------------
CREATE TABLE `jbpm_log` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `INDEX_` int(11) default NULL,
  `DATE_` datetime default NULL,
  `TOKEN_` bigint(20) default NULL,
  `PARENT_` bigint(20) default NULL,
  `MESSAGE_` mediumtext,
  `EXCEPTION_` mediumtext,
  `ACTION_` bigint(20) default NULL,
  `NODE_` bigint(20) default NULL,
  `ENTER_` datetime default NULL,
  `LEAVE_` datetime default NULL,
  `DURATION_` bigint(20) default NULL,
  `TRANSITION_` bigint(20) default NULL,
  `CHILD_` bigint(20) default NULL,
  `SOURCENODE_` bigint(20) default NULL,
  `DESTINATIONNODE_` bigint(20) default NULL,
  `VARIABLEINSTANCE_` bigint(20) default NULL,
  `OLDBYTEARRAY_` bigint(20) default NULL,
  `NEWBYTEARRAY_` bigint(20) default NULL,
  `OLDDATEVALUE_` datetime default NULL,
  `NEWDATEVALUE_` datetime default NULL,
  `OLDDOUBLEVALUE_` double default NULL,
  `NEWDOUBLEVALUE_` double default NULL,
  `OLDLONGIDCLASS_` varchar(255) default NULL,
  `OLDLONGIDVALUE_` bigint(20) default NULL,
  `NEWLONGIDCLASS_` varchar(255) default NULL,
  `NEWLONGIDVALUE_` bigint(20) default NULL,
  `OLDSTRINGIDCLASS_` varchar(255) default NULL,
  `OLDSTRINGIDVALUE_` varchar(255) default NULL,
  `NEWSTRINGIDCLASS_` varchar(255) default NULL,
  `NEWSTRINGIDVALUE_` varchar(255) default NULL,
  `OLDLONGVALUE_` bigint(20) default NULL,
  `NEWLONGVALUE_` bigint(20) default NULL,
  `OLDSTRINGVALUE_` mediumtext,
  `NEWSTRINGVALUE_` mediumtext,
  `TASKINSTANCE_` bigint(20) default NULL,
  `TASKACTORID_` varchar(255) default NULL,
  `TASKOLDACTORID_` varchar(255) default NULL,
  `SWIMLANEINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_LOG_SOURCENODE` (`SOURCENODE_`),
  KEY `FK_LOG_TOKEN` (`TOKEN_`),
  KEY `FK_LOG_OLDBYTES` (`OLDBYTEARRAY_`),
  KEY `FK_LOG_NEWBYTES` (`NEWBYTEARRAY_`),
  KEY `FK_LOG_CHILDTOKEN` (`CHILD_`),
  KEY `FK_LOG_DESTNODE` (`DESTINATIONNODE_`),
  KEY `FK_LOG_TASKINST` (`TASKINSTANCE_`),
  KEY `FK_LOG_SWIMINST` (`SWIMLANEINSTANCE_`),
  KEY `FK_LOG_PARENT` (`PARENT_`),
  KEY `FK_LOG_NODE` (`NODE_`),
  KEY `FK_LOG_ACTION` (`ACTION_`),
  KEY `FK_LOG_VARINST` (`VARIABLEINSTANCE_`),
  KEY `FK_LOG_TRANSITION` (`TRANSITION_`),
  CONSTRAINT `FK_LOG_ACTION` FOREIGN KEY (`ACTION_`) REFERENCES `jbpm_action` (`ID_`),
  CONSTRAINT `FK_LOG_CHILDTOKEN` FOREIGN KEY (`CHILD_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_LOG_DESTNODE` FOREIGN KEY (`DESTINATIONNODE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_LOG_NEWBYTES` FOREIGN KEY (`NEWBYTEARRAY_`) REFERENCES `jbpm_bytearray` (`ID_`),
  CONSTRAINT `FK_LOG_NODE` FOREIGN KEY (`NODE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_LOG_OLDBYTES` FOREIGN KEY (`OLDBYTEARRAY_`) REFERENCES `jbpm_bytearray` (`ID_`),
  CONSTRAINT `FK_LOG_PARENT` FOREIGN KEY (`PARENT_`) REFERENCES `jbpm_log` (`ID_`),
  CONSTRAINT `FK_LOG_SOURCENODE` FOREIGN KEY (`SOURCENODE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_LOG_SWIMINST` FOREIGN KEY (`SWIMLANEINSTANCE_`) REFERENCES `jbpm_swimlaneinstance` (`ID_`),
  CONSTRAINT `FK_LOG_TASKINST` FOREIGN KEY (`TASKINSTANCE_`) REFERENCES `jbpm_taskinstance` (`ID_`),
  CONSTRAINT `FK_LOG_TOKEN` FOREIGN KEY (`TOKEN_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_LOG_TRANSITION` FOREIGN KEY (`TRANSITION_`) REFERENCES `jbpm_transition` (`ID_`),
  CONSTRAINT `FK_LOG_VARINST` FOREIGN KEY (`VARIABLEINSTANCE_`) REFERENCES `jbpm_variableinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_moduledefinition
-- ----------------------------
CREATE TABLE `jbpm_moduledefinition` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `NAME_` mediumtext,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `STARTTASK_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_TSKDEF_START` (`STARTTASK_`),
  KEY `FK_MODDEF_PROCDEF` (`PROCESSDEFINITION_`),
  CONSTRAINT `FK_MODDEF_PROCDEF` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`),
  CONSTRAINT `FK_TSKDEF_START` FOREIGN KEY (`STARTTASK_`) REFERENCES `jbpm_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_moduleinstance
-- ----------------------------
CREATE TABLE `jbpm_moduleinstance` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `TASKMGMTDEFINITION_` bigint(20) default NULL,
  `NAME_` varchar(255) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_TASKMGTINST_TMD` (`TASKMGMTDEFINITION_`),
  KEY `FK_MODINST_PRCINST` (`PROCESSINSTANCE_`),
  CONSTRAINT `FK_MODINST_PRCINST` FOREIGN KEY (`PROCESSINSTANCE_`) REFERENCES `jbpm_processinstance` (`ID_`),
  CONSTRAINT `FK_TASKMGTINST_TMD` FOREIGN KEY (`TASKMGMTDEFINITION_`) REFERENCES `jbpm_moduledefinition` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_node
-- ----------------------------
CREATE TABLE `jbpm_node` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `ACTION_` bigint(20) default NULL,
  `SUPERSTATE_` bigint(20) default NULL,
  `SUBPROCESSDEFINITION_` bigint(20) default NULL,
  `DECISIONDELEGATION` bigint(20) default NULL,
  `SIGNAL_` int(11) default NULL,
  `CREATETASKS_` char(1) default NULL,
  `NODECOLLECTIONINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_PROCST_SBPRCDEF` (`SUBPROCESSDEFINITION_`),
  KEY `FK_NODE_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_NODE_ACTION` (`ACTION_`),
  KEY `FK_DECISION_DELEG` (`DECISIONDELEGATION`),
  KEY `FK_NODE_SUPERSTATE` (`SUPERSTATE_`),
  CONSTRAINT `FK_DECISION_DELEG` FOREIGN KEY (`DECISIONDELEGATION`) REFERENCES `jbpm_delegation` (`ID_`),
  CONSTRAINT `FK_NODE_ACTION` FOREIGN KEY (`ACTION_`) REFERENCES `jbpm_action` (`ID_`),
  CONSTRAINT `FK_NODE_PROCDEF` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`),
  CONSTRAINT `FK_NODE_SUPERSTATE` FOREIGN KEY (`SUPERSTATE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_PROCST_SBPRCDEF` FOREIGN KEY (`SUBPROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_pooledactor
-- ----------------------------
CREATE TABLE `jbpm_pooledactor` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `ACTORID_` varchar(255) default NULL,
  `SWIMLANEINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_PLDACTR_ACTID` (`ACTORID_`),
  KEY `FK_POOLEDACTOR_SLI` (`SWIMLANEINSTANCE_`),
  CONSTRAINT `FK_POOLEDACTOR_SLI` FOREIGN KEY (`SWIMLANEINSTANCE_`) REFERENCES `jbpm_swimlaneinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_processdefinition
-- ----------------------------
CREATE TABLE `jbpm_processdefinition` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `VERSION_` int(11) default NULL,
  `ISTERMINATIONIMPLICIT_` char(1) default NULL,
  `STARTSTATE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_PROCDEF_STRTSTA` (`STARTSTATE_`),
  CONSTRAINT `FK_PROCDEF_STRTSTA` FOREIGN KEY (`STARTSTATE_`) REFERENCES `jbpm_node` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_processinstance
-- ----------------------------
CREATE TABLE `jbpm_processinstance` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `START_` datetime default NULL,
  `END_` datetime default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `ROOTTOKEN_` bigint(20) default NULL,
  `SUPERPROCESSTOKEN_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_PROCIN_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_PROCIN_ROOTTKN` (`ROOTTOKEN_`),
  KEY `FK_PROCIN_SPROCTKN` (`SUPERPROCESSTOKEN_`),
  CONSTRAINT `FK_PROCIN_PROCDEF` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`),
  CONSTRAINT `FK_PROCIN_ROOTTKN` FOREIGN KEY (`ROOTTOKEN_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_PROCIN_SPROCTKN` FOREIGN KEY (`SUPERPROCESSTOKEN_`) REFERENCES `jbpm_token` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_runtimeaction
-- ----------------------------
CREATE TABLE `jbpm_runtimeaction` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `EVENTTYPE_` varchar(255) default NULL,
  `TYPE_` char(1) default NULL,
  `GRAPHELEMENT_` bigint(20) default NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `ACTION_` bigint(20) default NULL,
  `PROCESSINSTANCEINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_RTACTN_PROCINST` (`PROCESSINSTANCE_`),
  KEY `FK_RTACTN_ACTION` (`ACTION_`),
  CONSTRAINT `FK_RTACTN_ACTION` FOREIGN KEY (`ACTION_`) REFERENCES `jbpm_action` (`ID_`),
  CONSTRAINT `FK_RTACTN_PROCINST` FOREIGN KEY (`PROCESSINSTANCE_`) REFERENCES `jbpm_processinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_swimlane
-- ----------------------------
CREATE TABLE `jbpm_swimlane` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `ASSIGNMENTDELEGATION_` bigint(20) default NULL,
  `TASKMGMTDEFINITION_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_SWL_ASSDEL` (`ASSIGNMENTDELEGATION_`),
  KEY `FK_SWL_TSKMGMTDEF` (`TASKMGMTDEFINITION_`),
  CONSTRAINT `FK_SWL_ASSDEL` FOREIGN KEY (`ASSIGNMENTDELEGATION_`) REFERENCES `jbpm_delegation` (`ID_`),
  CONSTRAINT `FK_SWL_TSKMGMTDEF` FOREIGN KEY (`TASKMGMTDEFINITION_`) REFERENCES `jbpm_moduledefinition` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_swimlaneinstance
-- ----------------------------
CREATE TABLE `jbpm_swimlaneinstance` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `ACTORID_` varchar(255) default NULL,
  `SWIMLANE_` bigint(20) default NULL,
  `TASKMGMTINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_SWIMLANEINST_TM` (`TASKMGMTINSTANCE_`),
  KEY `FK_SWIMLANEINST_SL` (`SWIMLANE_`),
  CONSTRAINT `FK_SWIMLANEINST_SL` FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm_swimlane` (`ID_`),
  CONSTRAINT `FK_SWIMLANEINST_TM` FOREIGN KEY (`TASKMGMTINSTANCE_`) REFERENCES `jbpm_moduleinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_task
-- ----------------------------
CREATE TABLE `jbpm_task` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `DESCRIPTION_` mediumtext,
  `ISBLOCKING_` char(1) default NULL,
  `DUEDATE_` varchar(255) default NULL,
  `TASKMGMTDEFINITION_` bigint(20) default NULL,
  `TASKNODE_` bigint(20) default NULL,
  `STARTSTATE_` bigint(20) default NULL,
  `ASSIGNMENTDELEGATION_` bigint(20) default NULL,
  `SWIMLANE_` bigint(20) default NULL,
  `TASKCONTROLLER_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_TSK_TSKCTRL` (`TASKCONTROLLER_`),
  KEY `FK_TASK_ASSDEL` (`ASSIGNMENTDELEGATION_`),
  KEY `FK_TASK_TASKNODE` (`TASKNODE_`),
  KEY `FK_TASK_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_TASK_STARTST` (`STARTSTATE_`),
  KEY `FK_TASK_TASKMGTDEF` (`TASKMGMTDEFINITION_`),
  KEY `FK_TASK_SWIMLANE` (`SWIMLANE_`),
  CONSTRAINT `FK_TASK_ASSDEL` FOREIGN KEY (`ASSIGNMENTDELEGATION_`) REFERENCES `jbpm_delegation` (`ID_`),
  CONSTRAINT `FK_TASK_PROCDEF` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`),
  CONSTRAINT `FK_TASK_STARTST` FOREIGN KEY (`STARTSTATE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_TASK_SWIMLANE` FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm_swimlane` (`ID_`),
  CONSTRAINT `FK_TASK_TASKMGTDEF` FOREIGN KEY (`TASKMGMTDEFINITION_`) REFERENCES `jbpm_moduledefinition` (`ID_`),
  CONSTRAINT `FK_TASK_TASKNODE` FOREIGN KEY (`TASKNODE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_TSK_TSKCTRL` FOREIGN KEY (`TASKCONTROLLER_`) REFERENCES `jbpm_taskcontroller` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_taskactorpool
-- ----------------------------
CREATE TABLE `jbpm_taskactorpool` (
  `TASKINSTANCE_` bigint(20) NOT NULL,
  `POOLEDACTOR_` bigint(20) NOT NULL,
  PRIMARY KEY  (`TASKINSTANCE_`,`POOLEDACTOR_`),
  KEY `FK_TSKACTPOL_PLACT` (`POOLEDACTOR_`),
  KEY `FK_TASKACTPL_TSKI` (`TASKINSTANCE_`),
  CONSTRAINT `FK_TASKACTPL_TSKI` FOREIGN KEY (`TASKINSTANCE_`) REFERENCES `jbpm_taskinstance` (`ID_`),
  CONSTRAINT `FK_TSKACTPOL_PLACT` FOREIGN KEY (`POOLEDACTOR_`) REFERENCES `jbpm_pooledactor` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_taskcontroller
-- ----------------------------
CREATE TABLE `jbpm_taskcontroller` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `TASKCONTROLLERDELEGATION_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_TSKCTRL_DELEG` (`TASKCONTROLLERDELEGATION_`),
  CONSTRAINT `FK_TSKCTRL_DELEG` FOREIGN KEY (`TASKCONTROLLERDELEGATION_`) REFERENCES `jbpm_delegation` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_taskinstance
-- ----------------------------
CREATE TABLE `jbpm_taskinstance` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `DESCRIPTION_` mediumtext,
  `ACTORID_` varchar(255) default NULL,
  `CREATE_` datetime default NULL,
  `START_` datetime default NULL,
  `END_` datetime default NULL,
  `DUEDATE_` datetime default NULL,
  `PRIORITY_` int(11) default NULL,
  `ISCANCELLED_` char(1) default NULL,
  `ISSIGNALLING_` char(1) default NULL,
  `ISBLOCKING_` char(1) default NULL,
  `TASK_` bigint(20) default NULL,
  `TOKEN_` bigint(20) default NULL,
  `SWIMLANINSTANCE_` bigint(20) default NULL,
  `TASKMGMTINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_TASK_ACTORID` (`ACTORID_`),
  KEY `FK_TASKINST_TMINST` (`TASKMGMTINSTANCE_`),
  KEY `FK_TASKINST_TOKEN` (`TOKEN_`),
  KEY `FK_TASKINST_SLINST` (`SWIMLANINSTANCE_`),
  KEY `FK_TASKINST_TASK` (`TASK_`),
  CONSTRAINT `FK_TASKINST_SLINST` FOREIGN KEY (`SWIMLANINSTANCE_`) REFERENCES `jbpm_swimlaneinstance` (`ID_`),
  CONSTRAINT `FK_TASKINST_TASK` FOREIGN KEY (`TASK_`) REFERENCES `jbpm_task` (`ID_`),
  CONSTRAINT `FK_TASKINST_TMINST` FOREIGN KEY (`TASKMGMTINSTANCE_`) REFERENCES `jbpm_moduleinstance` (`ID_`),
  CONSTRAINT `FK_TASKINST_TOKEN` FOREIGN KEY (`TOKEN_`) REFERENCES `jbpm_token` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_timer
-- ----------------------------
CREATE TABLE `jbpm_timer` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `DUEDATE_` datetime default NULL,
  `REPEAT_` varchar(255) default NULL,
  `TRANSITIONNAME_` varchar(255) default NULL,
  `EXCEPTION_` mediumtext,
  `ACTION_` bigint(20) default NULL,
  `TOKEN_` bigint(20) default NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `TASKINSTANCE_` bigint(20) default NULL,
  `GRAPHELEMENTTYPE_` varchar(255) default NULL,
  `GRAPHELEMENT_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_TIMER_TOKEN` (`TOKEN_`),
  KEY `FK_TIMER_PRINST` (`PROCESSINSTANCE_`),
  KEY `FK_TIMER_ACTION` (`ACTION_`),
  KEY `FK_TIMER_TSKINST` (`TASKINSTANCE_`),
  CONSTRAINT `FK_TIMER_ACTION` FOREIGN KEY (`ACTION_`) REFERENCES `jbpm_action` (`ID_`),
  CONSTRAINT `FK_TIMER_PRINST` FOREIGN KEY (`PROCESSINSTANCE_`) REFERENCES `jbpm_processinstance` (`ID_`),
  CONSTRAINT `FK_TIMER_TOKEN` FOREIGN KEY (`TOKEN_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_TIMER_TSKINST` FOREIGN KEY (`TASKINSTANCE_`) REFERENCES `jbpm_taskinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_token
-- ----------------------------
CREATE TABLE `jbpm_token` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `START_` datetime default NULL,
  `END_` datetime default NULL,
  `NODEENTER_` datetime default NULL,
  `NEXTLOGINDEX_` int(11) default NULL,
  `ISABLETOREACTIVATEPARENT_` char(1) default NULL,
  `ISTERMINATIONIMPLICIT_` char(1) default NULL,
  `NODE_` bigint(20) default NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `PARENT_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_TOKEN_PARENT` (`PARENT_`),
  KEY `FK_TOKEN_NODE` (`NODE_`),
  KEY `FK_TOKEN_PROCINST` (`PROCESSINSTANCE_`),
  CONSTRAINT `FK_TOKEN_NODE` FOREIGN KEY (`NODE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_TOKEN_PARENT` FOREIGN KEY (`PARENT_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_TOKEN_PROCINST` FOREIGN KEY (`PROCESSINSTANCE_`) REFERENCES `jbpm_processinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_tokenvariablemap
-- ----------------------------
CREATE TABLE `jbpm_tokenvariablemap` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `TOKEN_` bigint(20) default NULL,
  `CONTEXTINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_TKVARMAP_CTXT` (`CONTEXTINSTANCE_`),
  KEY `FK_TKVARMAP_TOKEN` (`TOKEN_`),
  CONSTRAINT `FK_TKVARMAP_CTXT` FOREIGN KEY (`CONTEXTINSTANCE_`) REFERENCES `jbpm_moduleinstance` (`ID_`),
  CONSTRAINT `FK_TKVARMAP_TOKEN` FOREIGN KEY (`TOKEN_`) REFERENCES `jbpm_token` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_transition
-- ----------------------------
CREATE TABLE `jbpm_transition` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `FROM_` bigint(20) default NULL,
  `TO_` bigint(20) default NULL,
  `FROMINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_TRANSITION_TO` (`TO_`),
  KEY `FK_TRANS_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_TRANSITION_FROM` (`FROM_`),
  CONSTRAINT `FK_TRANSITION_FROM` FOREIGN KEY (`FROM_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_TRANSITION_TO` FOREIGN KEY (`TO_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_TRANS_PROCDEF` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_variableaccess
-- ----------------------------
CREATE TABLE `jbpm_variableaccess` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VARIABLENAME_` varchar(255) default NULL,
  `ACCESS_` varchar(255) default NULL,
  `MAPPEDNAME_` varchar(255) default NULL,
  `PROCESSSTATE_` bigint(20) default NULL,
  `SCRIPT_` bigint(20) default NULL,
  `TASKCONTROLLER_` bigint(20) default NULL,
  `INDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_VARACC_TSKCTRL` (`TASKCONTROLLER_`),
  KEY `FK_VARACC_SCRIPT` (`SCRIPT_`),
  KEY `FK_VARACC_PROCST` (`PROCESSSTATE_`),
  CONSTRAINT `FK_VARACC_PROCST` FOREIGN KEY (`PROCESSSTATE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_VARACC_SCRIPT` FOREIGN KEY (`SCRIPT_`) REFERENCES `jbpm_action` (`ID_`),
  CONSTRAINT `FK_VARACC_TSKCTRL` FOREIGN KEY (`TASKCONTROLLER_`) REFERENCES `jbpm_taskcontroller` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Table structure for jbpm_variableinstance
-- ----------------------------
CREATE TABLE `jbpm_variableinstance` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `CONVERTER_` char(1) default NULL,
  `TOKEN_` bigint(20) default NULL,
  `TOKENVARIABLEMAP_` bigint(20) default NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `BYTEARRAYVALUE_` bigint(20) default NULL,
  `DATEVALUE_` datetime default NULL,
  `DOUBLEVALUE_` double default NULL,
  `LONGIDCLASS_` varchar(255) default NULL,
  `LONGVALUE_` bigint(20) default NULL,
  `STRINGIDCLASS_` varchar(255) default NULL,
  `STRINGVALUE_` varchar(255) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_VARINST_TK` (`TOKEN_`),
  KEY `FK_VARINST_TKVARMP` (`TOKENVARIABLEMAP_`),
  KEY `FK_VARINST_PRCINST` (`PROCESSINSTANCE_`),
  KEY `FK_BYTEINST_ARRAY` (`BYTEARRAYVALUE_`),
  CONSTRAINT `FK_BYTEINST_ARRAY` FOREIGN KEY (`BYTEARRAYVALUE_`) REFERENCES `jbpm_bytearray` (`ID_`),
  CONSTRAINT `FK_VARINST_PRCINST` FOREIGN KEY (`PROCESSINSTANCE_`) REFERENCES `jbpm_processinstance` (`ID_`),
  CONSTRAINT `FK_VARINST_TK` FOREIGN KEY (`TOKEN_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_VARINST_TKVARMP` FOREIGN KEY (`TOKENVARIABLEMAP_`) REFERENCES `jbpm_tokenvariablemap` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `jbpm_log` VALUES ('1', 'I', '0', '2006-09-25 09:26:16', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('2', 'S', '1', '2006-09-25 09:26:17', '1', null, null, null, null, null, null, null, null, '11', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('3', 'T', '2', '2006-09-25 09:26:17', '1', '2', null, null, null, null, null, null, null, '11', null, '10', '11', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('4', '2', '3', '2006-09-25 09:26:17', '1', '2', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, null, null);
INSERT INTO `jbpm_log` VALUES ('5', '1', '4', '2006-09-25 09:26:17', '1', '2', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, null, null);
INSERT INTO `jbpm_log` VALUES ('6', '3', '5', '2006-09-25 09:26:17', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, null, null);
INSERT INTO `jbpm_log` VALUES ('7', 'S', '6', '2006-09-25 09:26:17', '1', null, null, null, null, null, null, null, null, '12', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('8', 'N', '7', '2006-09-25 09:26:17', '1', '7', null, null, null, '11', '2006-09-25 09:26:17', '2006-09-25 09:26:17', '109', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('9', 'T', '8', '2006-09-25 09:26:17', '1', '7', null, null, null, null, null, null, null, '12', null, '11', '12', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('10', '2', '9', '2006-09-25 09:26:17', '1', '7', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2', null, null, null);
INSERT INTO `jbpm_log` VALUES ('11', '1', '10', '2006-09-25 09:26:17', '1', '7', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2', null, null, null);
INSERT INTO `jbpm_log` VALUES ('12', 'I', '0', '2006-09-25 09:29:25', '2', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('13', 'S', '1', '2006-09-25 09:29:25', '2', null, null, null, null, null, null, null, null, '11', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('14', 'T', '2', '2006-09-25 09:29:25', '2', '13', null, null, null, null, null, null, null, '11', null, '10', '11', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('15', '2', '3', '2006-09-25 09:29:25', '2', '13', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '3', null, null, null);
INSERT INTO `jbpm_log` VALUES ('16', '1', '4', '2006-09-25 09:29:25', '2', '13', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '3', null, null, null);
INSERT INTO `jbpm_log` VALUES ('17', '3', '5', '2006-09-25 09:29:25', '2', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '3', null, null, null);
INSERT INTO `jbpm_log` VALUES ('18', 'S', '6', '2006-09-25 09:29:25', '2', null, null, null, null, null, null, null, null, '12', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('19', 'N', '7', '2006-09-25 09:29:25', '2', '18', null, null, null, '11', '2006-09-25 09:29:25', '2006-09-25 09:29:25', '31', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('20', 'T', '8', '2006-09-25 09:29:25', '2', '18', null, null, null, null, null, null, null, '12', null, '11', '12', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('21', '2', '9', '2006-09-25 09:29:25', '2', '18', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '4', null, null, null);
INSERT INTO `jbpm_log` VALUES ('22', '1', '10', '2006-09-25 09:29:25', '2', '18', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '4', null, null, null);
INSERT INTO `jbpm_log` VALUES ('23', 'I', '0', '2006-09-25 09:35:49', '3', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('24', 'S', '1', '2006-09-25 09:35:49', '3', null, null, null, null, null, null, null, null, '11', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('25', 'T', '2', '2006-09-25 09:35:49', '3', '24', null, null, null, null, null, null, null, '11', null, '10', '11', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('26', '2', '3', '2006-09-25 09:35:49', '3', '24', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '5', null, null, null);
INSERT INTO `jbpm_log` VALUES ('27', '1', '4', '2006-09-25 09:35:49', '3', '24', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '5', null, null, null);
INSERT INTO `jbpm_log` VALUES ('28', '3', '5', '2006-09-25 09:35:49', '3', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '5', null, null, null);
INSERT INTO `jbpm_log` VALUES ('29', 'S', '6', '2006-09-25 09:35:49', '3', null, null, null, null, null, null, null, null, '12', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('30', 'N', '7', '2006-09-25 09:35:49', '3', '29', null, null, null, '11', '2006-09-25 09:35:49', '2006-09-25 09:35:49', '31', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('31', 'T', '8', '2006-09-25 09:35:49', '3', '29', null, null, null, null, null, null, null, '12', null, '11', '12', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('32', '2', '9', '2006-09-25 09:35:49', '3', '29', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '6', null, null, null);
INSERT INTO `jbpm_log` VALUES ('33', '1', '10', '2006-09-25 09:35:49', '3', '29', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '6', null, null, null);
INSERT INTO `jbpm_log` VALUES ('34', 'I', '0', '2006-09-25 09:41:09', '4', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('35', 'S', '1', '2006-09-25 09:41:09', '4', null, null, null, null, null, null, null, null, '11', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('36', 'T', '2', '2006-09-25 09:41:09', '4', '35', null, null, null, null, null, null, null, '11', null, '10', '11', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('37', '2', '3', '2006-09-25 09:41:09', '4', '35', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '7', null, null, null);
INSERT INTO `jbpm_log` VALUES ('38', '1', '4', '2006-09-25 09:41:09', '4', '35', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '7', null, null, null);
INSERT INTO `jbpm_log` VALUES ('39', '3', '5', '2006-09-25 09:41:09', '4', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '7', null, null, null);
INSERT INTO `jbpm_log` VALUES ('40', 'S', '6', '2006-09-25 09:41:09', '4', null, null, null, null, null, null, null, null, '12', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('41', 'N', '7', '2006-09-25 09:41:09', '4', '40', null, null, null, '11', '2006-09-25 09:41:09', '2006-09-25 09:41:09', '46', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('42', 'T', '8', '2006-09-25 09:41:09', '4', '40', null, null, null, null, null, null, null, '12', null, '11', '12', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('43', '2', '9', '2006-09-25 09:41:09', '4', '40', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '8', null, null, null);
INSERT INTO `jbpm_log` VALUES ('44', '1', '10', '2006-09-25 09:41:09', '4', '40', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '8', null, null, null);
INSERT INTO `jbpm_log` VALUES ('45', 'I', '0', '2006-09-25 09:55:13', '5', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('46', 'S', '1', '2006-09-25 09:55:13', '5', null, null, null, null, null, null, null, null, '4', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('47', 'T', '2', '2006-09-25 09:55:13', '5', '46', null, null, null, null, null, null, null, '4', null, '3', '4', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('48', '2', '3', '2006-09-25 09:55:13', '5', '46', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '9', null, null, null);
INSERT INTO `jbpm_log` VALUES ('49', '1', '4', '2006-09-25 09:55:13', '5', '46', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '9', null, null, null);
INSERT INTO `jbpm_log` VALUES ('50', '3', '5', '2006-09-25 09:55:13', '5', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '9', null, null, null);
INSERT INTO `jbpm_log` VALUES ('51', 'S', '6', '2006-09-25 09:55:13', '5', null, null, null, null, null, null, null, null, '5', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('52', 'N', '7', '2006-09-25 09:55:13', '5', '51', null, null, null, '4', '2006-09-25 09:55:13', '2006-09-25 09:55:13', '16', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('53', 'T', '8', '2006-09-25 09:55:13', '5', '51', null, null, null, null, null, null, null, '5', null, '4', '5', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('54', '2', '9', '2006-09-25 09:55:13', '5', '51', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '10', null, null, null);
INSERT INTO `jbpm_log` VALUES ('55', '1', '10', '2006-09-25 09:55:13', '5', '51', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '10', null, null, null);
INSERT INTO `jbpm_log` VALUES ('56', 'I', '0', '2006-09-25 10:03:06', '6', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('57', 'S', '1', '2006-09-25 10:03:07', '6', null, null, null, null, null, null, null, null, '4', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('58', 'T', '2', '2006-09-25 10:03:07', '6', '57', null, null, null, null, null, null, null, '4', null, '3', '4', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('59', '2', '3', '2006-09-25 10:03:07', '6', '57', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '12', null, null, null);
INSERT INTO `jbpm_log` VALUES ('60', '1', '4', '2006-09-25 10:03:07', '6', '57', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '12', null, null, null);
INSERT INTO `jbpm_log` VALUES ('61', '3', '5', '2006-09-25 10:03:07', '6', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '12', null, null, null);
INSERT INTO `jbpm_log` VALUES ('62', 'S', '6', '2006-09-25 10:03:07', '6', null, null, null, null, null, null, null, null, '5', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('63', 'N', '7', '2006-09-25 10:03:07', '6', '62', null, null, null, '4', '2006-09-25 10:03:07', '2006-09-25 10:03:07', '47', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('64', 'T', '8', '2006-09-25 10:03:07', '6', '62', null, null, null, null, null, null, null, '5', null, '4', '5', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('65', '2', '9', '2006-09-25 10:03:07', '6', '62', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '13', null, null, null);
INSERT INTO `jbpm_log` VALUES ('66', '1', '10', '2006-09-25 10:03:07', '6', '62', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '13', null, null, null);
INSERT INTO `jbpm_log` VALUES ('67', 'I', '0', '2006-09-25 10:17:49', '7', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('68', 'S', '1', '2006-09-25 10:17:49', '7', null, null, null, null, null, null, null, null, '4', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('69', 'T', '2', '2006-09-25 10:17:49', '7', '68', null, null, null, null, null, null, null, '4', null, '3', '4', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('70', '2', '3', '2006-09-25 10:17:49', '7', '68', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '14', null, null, null);
INSERT INTO `jbpm_log` VALUES ('71', '1', '4', '2006-09-25 10:17:49', '7', '68', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '14', null, null, null);
INSERT INTO `jbpm_log` VALUES ('72', '3', '5', '2006-09-25 10:17:49', '7', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '14', null, null, null);
INSERT INTO `jbpm_log` VALUES ('73', 'S', '6', '2006-09-25 10:17:49', '7', null, null, null, null, null, null, null, null, '5', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('74', 'N', '7', '2006-09-25 10:17:49', '7', '73', null, null, null, '4', '2006-09-25 10:17:49', '2006-09-25 10:17:49', '32', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('75', 'T', '8', '2006-09-25 10:17:49', '7', '73', null, null, null, null, null, null, null, '5', null, '4', '5', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jbpm_log` VALUES ('76', '2', '9', '2006-09-25 10:17:49', '7', '73', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '15', null, null, null);
INSERT INTO `jbpm_log` VALUES ('77', '1', '10', '2006-09-25 10:17:49', '7', '73', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '15', null, null, null);
INSERT INTO `jbpm_moduledefinition` VALUES ('1', 'C', 'org.jbpm.context.def.ContextDefinition', '3', null);
INSERT INTO `jbpm_moduledefinition` VALUES ('2', 'T', 'org.jbpm.taskmgmt.def.TaskMgmtDefinition', '3', null);
INSERT INTO `jbpm_moduledefinition` VALUES ('3', 'C', 'org.jbpm.context.def.ContextDefinition', '4', null);
INSERT INTO `jbpm_moduledefinition` VALUES ('4', 'T', 'org.jbpm.taskmgmt.def.TaskMgmtDefinition', '4', null);
INSERT INTO `jbpm_moduleinstance` VALUES ('1', 'T', '1', '4', 'org.jbpm.taskmgmt.exe.TaskMgmtInstance');
INSERT INTO `jbpm_moduleinstance` VALUES ('2', 'C', '1', null, 'org.jbpm.context.exe.ContextInstance');
INSERT INTO `jbpm_moduleinstance` VALUES ('3', 'T', '2', '4', 'org.jbpm.taskmgmt.exe.TaskMgmtInstance');
INSERT INTO `jbpm_moduleinstance` VALUES ('4', 'C', '2', null, 'org.jbpm.context.exe.ContextInstance');
INSERT INTO `jbpm_moduleinstance` VALUES ('5', 'T', '3', '4', 'org.jbpm.taskmgmt.exe.TaskMgmtInstance');
INSERT INTO `jbpm_moduleinstance` VALUES ('6', 'C', '3', null, 'org.jbpm.context.exe.ContextInstance');
INSERT INTO `jbpm_moduleinstance` VALUES ('7', 'T', '4', '4', 'org.jbpm.taskmgmt.exe.TaskMgmtInstance');
INSERT INTO `jbpm_moduleinstance` VALUES ('8', 'C', '4', null, 'org.jbpm.context.exe.ContextInstance');
INSERT INTO `jbpm_moduleinstance` VALUES ('9', 'T', '5', '2', 'org.jbpm.taskmgmt.exe.TaskMgmtInstance');
INSERT INTO `jbpm_moduleinstance` VALUES ('10', 'C', '5', null, 'org.jbpm.context.exe.ContextInstance');
INSERT INTO `jbpm_moduleinstance` VALUES ('11', 'T', '6', '2', 'org.jbpm.taskmgmt.exe.TaskMgmtInstance');
INSERT INTO `jbpm_moduleinstance` VALUES ('12', 'C', '6', null, 'org.jbpm.context.exe.ContextInstance');
INSERT INTO `jbpm_moduleinstance` VALUES ('13', 'T', '7', '2', 'org.jbpm.taskmgmt.exe.TaskMgmtInstance');
INSERT INTO `jbpm_moduleinstance` VALUES ('14', 'C', '7', null, 'org.jbpm.context.exe.ContextInstance');
INSERT INTO `jbpm_node` VALUES ('3', 'R', 'start', '3', null, null, null, null, null, null, '0');
INSERT INTO `jbpm_node` VALUES ('4', 'K', 'applyConference', '3', null, null, null, null, '4', '1', '1');
INSERT INTO `jbpm_node` VALUES ('5', 'K', 'checkupConference', '3', null, null, null, null, '4', '1', '2');
INSERT INTO `jbpm_node` VALUES ('6', 'K', 'distributeboardroom', '3', null, null, null, null, '4', '1', '3');
INSERT INTO `jbpm_node` VALUES ('7', 'K', 'updateConference', '3', null, null, null, null, '4', '1', '4');
INSERT INTO `jbpm_node` VALUES ('8', 'K', 'conferenceFeedback', '3', null, null, null, null, '4', '1', '5');
INSERT INTO `jbpm_node` VALUES ('9', 'E', 'end1', '3', null, null, null, null, null, null, '6');
INSERT INTO `jbpm_node` VALUES ('10', 'R', 'start', '4', null, null, null, null, null, null, '0');
INSERT INTO `jbpm_node` VALUES ('11', 'K', 'addplan', '4', null, null, null, null, '4', '1', '1');
INSERT INTO `jbpm_node` VALUES ('12', 'K', 'checkplan', '4', null, null, null, null, '4', '1', '2');
INSERT INTO `jbpm_node` VALUES ('13', 'K', 'updateplan', '4', null, null, null, null, '4', '1', '3');
INSERT INTO `jbpm_node` VALUES ('14', 'E', 'end1', '4', null, null, null, null, null, null, '4');
INSERT INTO `jbpm_processdefinition` VALUES ('3', 'conference', '-1', '0', '3');
INSERT INTO `jbpm_processdefinition` VALUES ('4', 'plan', '-1', '0', '10');
INSERT INTO `jbpm_processinstance` VALUES ('1', '2006-09-25 09:26:16', '2006-09-25 09:44:19', '4', '1', null);
INSERT INTO `jbpm_processinstance` VALUES ('2', '2006-09-25 09:29:25', '2006-09-25 09:44:13', '4', '2', null);
INSERT INTO `jbpm_processinstance` VALUES ('3', '2006-09-25 09:35:49', '2006-09-25 09:44:01', '4', '3', null);
INSERT INTO `jbpm_processinstance` VALUES ('4', '2006-09-25 09:41:09', '2006-09-25 09:44:31', '4', '4', null);
INSERT INTO `jbpm_processinstance` VALUES ('5', '2006-09-25 09:55:13', null, '3', '5', null);
INSERT INTO `jbpm_processinstance` VALUES ('6', '2006-09-25 10:03:06', null, '3', '6', null);
INSERT INTO `jbpm_processinstance` VALUES ('7', '2006-09-25 10:17:48', null, '3', '7', null);
INSERT INTO `jbpm_swimlane` VALUES ('1', 'Conferenceratifier', null, '2');
INSERT INTO `jbpm_swimlane` VALUES ('2', 'Conferenceproposer', null, '2');
INSERT INTO `jbpm_swimlane` VALUES ('3', 'Conferenceallocatee', null, '2');
INSERT INTO `jbpm_swimlane` VALUES ('4', 'planchecker', null, '4');
INSERT INTO `jbpm_swimlane` VALUES ('5', 'planuser', null, '4');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('1', 'planchecker', 'zhaoyifei', '4', '1');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('2', 'planuser', 'zhaoyifei', '5', '1');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('3', 'planchecker', 'zhaoyifei', '4', '3');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('4', 'planuser', 'zhaoyifei', '5', '3');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('5', 'planchecker', 'zhaoyifei', '4', '5');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('6', 'planuser', 'zhaoyifei', '5', '5');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('7', 'planchecker', 'zhaoyifei', '4', '7');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('8', 'planuser', 'zhaoyifei', '5', '7');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('9', 'Conferenceproposer', 'zhaoyifei', '2', '9');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('10', 'Conferenceallocatee', null, '3', '9');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('11', 'Conferenceratifier', null, '1', '9');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('12', 'Conferenceproposer', 'zhaoyifei', '2', '11');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('13', 'Conferenceallocatee', null, '3', '11');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('14', 'Conferenceproposer', 'zhaoyifei', '2', '13');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('15', 'Conferenceallocatee', null, '3', '13');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('16', 'Conferenceratifier', null, '1', '13');
INSERT INTO `jbpm_swimlaneinstance` VALUES ('17', 'Conferenceratifier', null, '1', '11');
INSERT INTO `jbpm_task` VALUES ('1', 'applyConference', '3', null, '0', null, '2', '4', null, null, '2', null);
INSERT INTO `jbpm_task` VALUES ('2', 'checkupConference', '3', null, '0', null, '2', '5', null, null, '3', null);
INSERT INTO `jbpm_task` VALUES ('3', 'distributeboardroom', '3', null, '0', null, '2', '6', null, null, '1', null);
INSERT INTO `jbpm_task` VALUES ('4', 'updateConference', '3', null, '0', null, '2', '7', null, null, '2', null);
INSERT INTO `jbpm_task` VALUES ('5', 'conferenceFeedback', '3', null, '0', null, '2', '8', null, null, '2', null);
INSERT INTO `jbpm_task` VALUES ('6', 'addplan', '4', null, '0', null, '4', '11', null, null, '5', null);
INSERT INTO `jbpm_task` VALUES ('7', 'checkplan', '4', null, '0', null, '4', '12', null, null, '4', null);
INSERT INTO `jbpm_task` VALUES ('8', 'updateplan', '4', null, '0', null, '4', '13', null, null, '5', null);
INSERT INTO `jbpm_taskinstance` VALUES ('1', 'addplan', null, null, '2006-09-25 09:26:17', '2006-09-25 09:26:17', '2006-09-25 09:26:17', null, '3', '0', '0', '0', '6', '1', '2', '1');
INSERT INTO `jbpm_taskinstance` VALUES ('2', 'checkplan', null, null, '2006-09-25 09:26:17', '2006-09-25 09:26:18', '2006-09-25 09:44:19', null, '3', '0', '0', '0', '7', '1', '1', '1');
INSERT INTO `jbpm_taskinstance` VALUES ('3', 'addplan', null, null, '2006-09-25 09:29:25', '2006-09-25 09:29:25', '2006-09-25 09:29:25', null, '3', '0', '0', '0', '6', '2', '4', '3');
INSERT INTO `jbpm_taskinstance` VALUES ('4', 'checkplan', null, null, '2006-09-25 09:29:25', '2006-09-25 09:29:25', '2006-09-25 09:44:13', null, '3', '0', '0', '0', '7', '2', '3', '3');
INSERT INTO `jbpm_taskinstance` VALUES ('5', 'addplan', null, null, '2006-09-25 09:35:49', '2006-09-25 09:35:49', '2006-09-25 09:35:49', null, '3', '0', '0', '0', '6', '3', '6', '5');
INSERT INTO `jbpm_taskinstance` VALUES ('6', 'checkplan', null, null, '2006-09-25 09:35:49', '2006-09-25 09:35:49', '2006-09-25 09:44:01', null, '3', '0', '0', '0', '7', '3', '5', '5');
INSERT INTO `jbpm_taskinstance` VALUES ('7', 'addplan', null, null, '2006-09-25 09:41:09', '2006-09-25 09:41:09', '2006-09-25 09:41:09', null, '3', '0', '0', '0', '6', '4', '8', '7');
INSERT INTO `jbpm_taskinstance` VALUES ('8', 'checkplan', null, null, '2006-09-25 09:41:09', '2006-09-25 09:41:10', '2006-09-25 09:44:31', null, '3', '0', '0', '0', '7', '4', '7', '7');
INSERT INTO `jbpm_taskinstance` VALUES ('9', 'applyConference', null, null, '2006-09-25 09:55:13', '2006-09-25 09:55:13', '2006-09-25 09:55:13', null, '3', '0', '0', '0', '1', '5', '9', '9');
INSERT INTO `jbpm_taskinstance` VALUES ('10', 'checkupConference', null, null, '2006-09-25 09:55:13', '2006-09-25 09:55:13', '2006-09-25 09:55:55', null, '3', '0', '0', '0', '2', '5', '10', '9');
INSERT INTO `jbpm_taskinstance` VALUES ('11', 'distributeboardroom', null, null, '2006-09-25 09:55:55', '2006-09-25 09:55:57', null, null, '3', '0', '1', '0', '3', '5', '11', '9');
INSERT INTO `jbpm_taskinstance` VALUES ('12', 'applyConference', null, null, '2006-09-25 10:03:07', '2006-09-25 10:03:07', '2006-09-25 10:03:07', null, '3', '0', '0', '0', '1', '6', '12', '11');
INSERT INTO `jbpm_taskinstance` VALUES ('13', 'checkupConference', null, null, '2006-09-25 10:03:07', '2006-09-25 10:03:07', '2006-09-25 10:18:07', null, '3', '0', '0', '0', '2', '6', '13', '11');
INSERT INTO `jbpm_taskinstance` VALUES ('14', 'applyConference', null, null, '2006-09-25 10:17:49', '2006-09-25 10:17:49', '2006-09-25 10:17:49', null, '3', '0', '0', '0', '1', '7', '14', '13');
INSERT INTO `jbpm_taskinstance` VALUES ('15', 'checkupConference', null, null, '2006-09-25 10:17:49', '2006-09-25 10:17:49', '2006-09-25 10:18:01', null, '3', '0', '0', '0', '2', '7', '15', '13');
INSERT INTO `jbpm_taskinstance` VALUES ('16', 'distributeboardroom', null, null, '2006-09-25 10:18:01', '2006-09-25 10:18:01', null, null, '3', '0', '1', '0', '3', '7', '16', '13');
INSERT INTO `jbpm_taskinstance` VALUES ('17', 'distributeboardroom', null, null, '2006-09-25 10:18:07', '2006-09-25 10:18:07', null, null, '3', '0', '1', '0', '3', '6', '17', '11');
INSERT INTO `jbpm_token` VALUES ('1', null, '2006-09-25 09:26:16', '2006-09-25 09:44:19', '2006-09-25 09:44:19', '16', '0', '0', '14', '1', null);
INSERT INTO `jbpm_token` VALUES ('2', null, '2006-09-25 09:29:25', '2006-09-25 09:44:13', '2006-09-25 09:44:13', '16', '0', '0', '14', '2', null);
INSERT INTO `jbpm_token` VALUES ('3', null, '2006-09-25 09:35:49', '2006-09-25 09:44:01', '2006-09-25 09:44:01', '16', '0', '0', '14', '3', null);
INSERT INTO `jbpm_token` VALUES ('4', null, '2006-09-25 09:41:09', '2006-09-25 09:44:31', '2006-09-25 09:44:31', '16', '0', '0', '14', '4', null);
INSERT INTO `jbpm_token` VALUES ('5', null, '2006-09-25 09:55:13', null, '2006-09-25 09:55:55', '17', '1', '0', '6', '5', null);
INSERT INTO `jbpm_token` VALUES ('6', null, '2006-09-25 10:03:06', null, '2006-09-25 10:18:07', '17', '1', '0', '6', '6', null);
INSERT INTO `jbpm_token` VALUES ('7', null, '2006-09-25 10:17:48', null, '2006-09-25 10:18:01', '17', '1', '0', '6', '7', null);
INSERT INTO `jbpm_transition` VALUES ('4', 'tr1', '3', '3', '4', '0');
INSERT INTO `jbpm_transition` VALUES ('5', 'tr1', '3', '4', '5', '0');
INSERT INTO `jbpm_transition` VALUES ('6', 'to_db', '3', '5', '6', '0');
INSERT INTO `jbpm_transition` VALUES ('7', 'to_update', '3', '5', '7', '1');
INSERT INTO `jbpm_transition` VALUES ('8', 'tr1', '3', '6', '8', '0');
INSERT INTO `jbpm_transition` VALUES ('9', 'tr1', '3', '7', '5', '0');
INSERT INTO `jbpm_transition` VALUES ('10', 'to_end', '3', '8', '9', '0');
INSERT INTO `jbpm_transition` VALUES ('11', 'tr1', '4', '10', '11', '0');
INSERT INTO `jbpm_transition` VALUES ('12', 'tr1', '4', '11', '12', '0');
INSERT INTO `jbpm_transition` VALUES ('13', 'to_update', '4', '12', '13', '0');
INSERT INTO `jbpm_transition` VALUES ('14', 'to_end', '4', '12', '14', '1');
INSERT INTO `jbpm_transition` VALUES ('15', 'to_check', '4', '13', '12', '0');
