---------------------------------------------
-- Export file for user CALLCENTER         --
-- Created by ddddd on 2006-11-8, 14:18:49 --
---------------------------------------------

spool create_table_sql.log

prompt
prompt Creating table CALLIN_STATE
prompt ===========================
prompt
create table CALLIN_STATE
(
  ID   VARCHAR2(50) not null,
  PORT VARCHAR2(50),
  IP   VARCHAR2(50)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table CALLIN_STATE
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CC_CARD
prompt ======================
prompt
create table CC_CARD
(
  ID         VARCHAR2(50) not null,
  CLIENT_ID  VARCHAR2(50),
  CARD_NO    VARCHAR2(50),
  TAG_FREEZE VARCHAR2(50),
  REMARK     VARCHAR2(50)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table CC_CARD
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CC_CLIENT
prompt ========================
prompt
create table CC_CLIENT
(
  ID         VARCHAR2(50) not null,
  NAME       VARCHAR2(50),
  IP         VARCHAR2(50),
  MAC_ADDR   VARCHAR2(100),
  CPU_SN     VARCHAR2(100),
  TAG_FREEZE VARCHAR2(50)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table CC_CLIENT
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CC_EXTENSION
prompt ===========================
prompt
create table CC_EXTENSION
(
  ID         VARCHAR2(50) not null,
  SN         VARCHAR2(50),
  EXTENSION  VARCHAR2(50),
  TAG_FREEZE VARCHAR2(50),
  CARD_ID    VARCHAR2(50),
  CLIENT_ID  VARCHAR2(50),
  REMARK     VARCHAR2(200)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table CC_EXTENSION
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CC_INFO
prompt ======================
prompt
create table CC_INFO
(
  ID           VARCHAR2(50) not null,
  CLIENT_ID    VARCHAR2(50),
  TAG_IO       VARCHAR2(50),
  PHONENUM     VARCHAR2(50),
  CURTIME      DATE,
  FILE_LINK    VARCHAR2(200),
  CONTENT      VARCHAR2(200),
  IS_VALIDATE  VARCHAR2(50),
  IS_EXCUTE_OK VARCHAR2(50),
  OPERATOR     VARCHAR2(50),
  REMARK       VARCHAR2(200)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table CC_INFO
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CC_LOG
prompt =====================
prompt
create table CC_LOG
(
  ID            VARCHAR2(50) not null,
  CLIENT_IP     VARCHAR2(50),
  IN_PORT       VARCHAR2(50),
  OPERATOR_PORT VARCHAR2(50),
  PHONE_NUM     VARCHAR2(50),
  BEGIN_TIME    DATE,
  OPERATE_TIME  DATE,
  BETWEEN_TIME  NUMBER(10),
  END_TIME      DATE,
  REC_FILE      VARCHAR2(200),
  CMD           VARCHAR2(2000),
  REMARK        VARCHAR2(200)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table CC_LOG
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CC_RULE
prompt ======================
prompt
create table CC_RULE
(
  ID          VARCHAR2(50) not null,
  RULE_ACT    VARCHAR2(50),
  TAG_MARK    VARCHAR2(50),
  TAG_TYPE    VARCHAR2(50),
  MSG_FORMAT  VARCHAR2(50),
  ARG_NUM     VARCHAR2(50),
  CREATE_TIME DATE,
  VER         VARCHAR2(50),
  TAG_FREEZE  VARCHAR2(50),
  REMARK      VARCHAR2(200)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table CC_RULE
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table OPERATOR_WORK_INFO
prompt =================================
prompt
create table OPERATOR_WORK_INFO
(
  ID             VARCHAR2(50) not null,
  OPERATOR       VARCHAR2(50),
  OPERATE_DATE   DATE,
  OPERATE_TIME   DATE,
  OPERATOR_STATE VARCHAR2(50)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table OPERATOR_WORK_INFO
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table PHONE_SEARCH
prompt ===========================
prompt
create table PHONE_SEARCH
(
  ID        VARCHAR2(50) not null,
  NUM       VARCHAR2(20),
  DEPRTMENT VARCHAR2(200),
  UNIT      VARCHAR2(200),
  REMARK    VARCHAR2(200)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table PHONE_SEARCH
  add constraint ID primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table POLICEINFO_TEMP
prompt ==============================
prompt
create table POLICEINFO_TEMP
(
  ID       VARCHAR2(50) not null,
  P_ID     VARCHAR2(50),
  TAG_INFO VARCHAR2(50),
  QU_INFO  VARCHAR2(200),
  CONTENT  VARCHAR2(2000),
  REMARK   VARCHAR2(200),
  TAG      VARCHAR2(10)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table POLICEINFO_TEMP
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table POLICE_CALLIN
prompt ============================
prompt
create table POLICE_CALLIN
(
  ID             VARCHAR2(50) not null,
  FUZZ_NO        VARCHAR2(50),
  PASS_VALID_NUM VARCHAR2(50),
  IS_VALID_IN    VARCHAR2(50),
  OPERATOR       VARCHAR2(50)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table POLICE_CALLIN
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table POLICE_CALLIN_FIREWALL
prompt =====================================
prompt
create table POLICE_CALLIN_FIREWALL
(
  ID               VARCHAR2(50) not null,
  CALLIN_NUM_BEGIN VARCHAR2(50),
  CALLIN_NUM_END   VARCHAR2(50),
  BEGIN_TIME       DATE,
  END_TIME         DATE,
  IS_PASS          VARCHAR2(50),
  IS_AVAILABLE     VARCHAR2(50),
  REMARK           VARCHAR2(200)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table POLICE_CALLIN_FIREWALL
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table POLICE_CALLIN_INFO
prompt =================================
prompt
create table POLICE_CALLIN_INFO
(
  ID       VARCHAR2(50) not null,
  P_ID     VARCHAR2(50),
  TAG_INFO VARCHAR2(50),
  QU_INFO  VARCHAR2(200),
  CONTENT  VARCHAR2(2000),
  REMARK   VARCHAR2(200)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table POLICE_CALLIN_INFO
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table POLICE_CALLIN_INFO
  add constraint FK431785C916279A7B foreign key (P_ID)
  references POLICE_CALLIN (ID);

prompt
prompt Creating table POLICE_FUZZ_INFO
prompt ===============================
prompt
create table POLICE_FUZZ_INFO
(
  ID              VARCHAR2(50) not null,
  FUZZ_NO         VARCHAR2(50),
  NAME            VARCHAR2(50),
  SEX             VARCHAR2(50),
  BIRTHDAY        VARCHAR2(50),
  PASSWORD        VARCHAR2(50),
  MOBILE_TEL      VARCHAR2(50),
  TAG_POLICE_KIND VARCHAR2(50),
  WORK_TIME       DATE,
  TAG_UNIT        VARCHAR2(50),
  TAG_AREA        VARCHAR2(50),
  ID_CARD         VARCHAR2(50),
  PERSON_TYPE     VARCHAR2(50),
  DUTY            VARCHAR2(50),
  TAG_FREEZE      VARCHAR2(50),
  TAG_DEL         VARCHAR2(50)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table POLICE_FUZZ_INFO
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table POLICE_UPDATE_PASS
prompt =================================
prompt
create table POLICE_UPDATE_PASS
(
  ID          VARCHAR2(50) not null,
  FUZZ_NUM    VARCHAR2(50),
  NAME        VARCHAR2(50),
  ID_CARD     VARCHAR2(50),
  UPTIME      DATE,
  NEWPASSWORD VARCHAR2(50),
  DEPARTMENT  VARCHAR2(200),
  IP          VARCHAR2(50),
  REMARK      VARCHAR2(200)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table POLICE_UPDATE_PASS
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table PORT_COMPARE
prompt ===========================
prompt
create table PORT_COMPARE
(
  ID           VARCHAR2(50) not null,
  PHYSICS_PORT VARCHAR2(50),
  LOGIC_PORT   VARCHAR2(50),
  IP           VARCHAR2(50),
  ADD_DATE     DATE
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table PORT_COMPARE
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_DEPARTMENT
prompt =============================
prompt
create table SYS_DEPARTMENT
(
  ID        VARCHAR2(50) not null,
  REMARKS   VARCHAR2(200),
  PARENT_ID VARCHAR2(50),
  TAG_SHOW  VARCHAR2(1),
  NAME      VARCHAR2(50)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_DEPARTMENT
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_GROUP
prompt ========================
prompt
create table SYS_GROUP
(
  ID       VARCHAR2(50) not null,
  NAME     VARCHAR2(50),
  DEL_MARK VARCHAR2(2),
  REMARK   VARCHAR2(100),
  IS_SYS   VARCHAR2(2)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_GROUP
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_KEY
prompt ======================
prompt
create table SYS_KEY
(
  TB_NAME   VARCHAR2(50) not null,
  TB_ID_MAX VARCHAR2(20)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_KEY
  add primary key (TB_NAME)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_LOG
prompt ======================
prompt
create table SYS_LOG
(
  ID         VARCHAR2(50) not null,
  USER_ID    VARCHAR2(50),
  DT         DATE,
  MODU       VARCHAR2(50),
  ACTOR_TYPE VARCHAR2(50),
  IP         VARCHAR2(20),
  REMARK     VARCHAR2(200)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_LOG
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_MODULE
prompt =========================
prompt
create table SYS_MODULE
(
  ID          VARCHAR2(50) not null,
  ACTION      VARCHAR2(100),
  ICON        VARCHAR2(60),
  REMARKS     VARCHAR2(200),
  PARENT_ID   VARCHAR2(50),
  TAG_SHOW    VARCHAR2(1),
  NAME        VARCHAR2(50),
  LAYER_ORDER VARCHAR2(50)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_MODULE
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_RIGHT_GROUP
prompt ==============================
prompt
create table SYS_RIGHT_GROUP
(
  ID       VARCHAR2(50) not null,
  GROUP_ID VARCHAR2(50),
  MOD_ID   VARCHAR2(50)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_RIGHT_GROUP
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_RIGHT_GROUP
  add constraint FK1A77682ADB5AC2FD foreign key (GROUP_ID)
  references SYS_GROUP (ID);
alter table SYS_RIGHT_GROUP
  add constraint FK_SYS_RIGHT_GROUP_MOD foreign key (MOD_ID)
  references SYS_MODULE (ID) on delete cascade;

prompt
prompt Creating table SYS_RIGHT_USER
prompt =============================
prompt
create table SYS_RIGHT_USER
(
  ID      VARCHAR2(50) not null,
  USER_ID VARCHAR2(50),
  MOD_ID  VARCHAR2(50)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_RIGHT_USER
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_RIGHT_USER
  add constraint FK958618C0B2B658A1 foreign key (MOD_ID)
  references SYS_MODULE (ID);

prompt
prompt Creating table SYS_ROLE
prompt =======================
prompt
create table SYS_ROLE
(
  ID          VARCHAR2(50) not null,
  NAME        VARCHAR2(50),
  DELETE_MARK VARCHAR2(1),
  REMARK      VARCHAR2(100),
  IS_SYS      VARCHAR2(2)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_ROLE
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_STATION_INFO
prompt ===============================
prompt
create table SYS_STATION_INFO
(
  ID              VARCHAR2(50) not null,
  DEPARTMENT_ID   VARCHAR2(50),
  DEP_PERSON_NAME VARCHAR2(50),
  DEP_LEVEL       VARCHAR2(255),
  DEP_DESCRIBE    VARCHAR2(255),
  REMARK          VARCHAR2(500)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_STATION_INFO
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_TREE
prompt =======================
prompt
create table SYS_TREE
(
  ID          VARCHAR2(50) not null,
  PARENT_ID   VARCHAR2(255),
  TYPE        VARCHAR2(50),
  PROC_ALIAS  VARCHAR2(50),
  LABEL       VARCHAR2(100),
  LAYER_ORDER VARCHAR2(3),
  HANDLE_NODE VARCHAR2(3),
  TAG_SHOW    VARCHAR2(1),
  TAG_SYS     VARCHAR2(30),
  REMARK      VARCHAR2(200),
  TAG_DEL     VARCHAR2(1)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_TREE
  add primary key (ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_USER
prompt =======================
prompt
create table SYS_USER
(
  USER_ID       VARCHAR2(50) not null,
  PASSWORD      VARCHAR2(80),
  USER_NAME     VARCHAR2(50),
  GROUP_ID      VARCHAR2(50),
  ROLE_ID       VARCHAR2(50),
  DEPARTMENT_ID VARCHAR2(50),
  DELETE_MARK   VARCHAR2(2),
  REMARK        VARCHAR2(200),
  IS_SYS        VARCHAR2(2)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_USER
  add primary key (USER_ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_USER
  add constraint FK74A81DFD7938FFF7 foreign key (ROLE_ID)
  references SYS_ROLE (ID);
alter table SYS_USER
  add constraint FK74A81DFDAD8390B7 foreign key (DEPARTMENT_ID)
  references SYS_DEPARTMENT (ID);
alter table SYS_USER
  add constraint FK74A81DFDDB5AC2FD foreign key (GROUP_ID)
  references SYS_GROUP (ID);

prompt
prompt Creating table SYS_USER_INFO
prompt ============================
prompt
create table SYS_USER_INFO
(
  USER_ID       VARCHAR2(50) not null,
  REAL_NAME     VARCHAR2(10),
  SEX_ID        VARCHAR2(10),
  IDENTITY_KIND VARCHAR2(30),
  IDENTITY_CARD VARCHAR2(30),
  EMAIL         VARCHAR2(50),
  BIRTHDAY      DATE,
  COUNTRY_ID    VARCHAR2(20),
  PROVINCE_ID   VARCHAR2(20),
  CITY_ID       VARCHAR2(20),
  QQ            VARCHAR2(20),
  BLOOD_TYPE    VARCHAR2(2),
  ADDRESS       VARCHAR2(80),
  POSTALCODE    VARCHAR2(30),
  MOBILE        VARCHAR2(20),
  FINISH_SCHOOL VARCHAR2(40),
  SPECIALITY    VARCHAR2(20),
  WORK_ID       VARCHAR2(20),
  HOMEPAGE      VARCHAR2(80),
  REMARK        VARCHAR2(255)
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_USER_INFO
  add primary key (USER_ID)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_USER_LOGIN
prompt =============================
prompt
create table SYS_USER_LOGIN
(
  KEY        VARCHAR2(20) not null,
  GROUP_ID   VARCHAR2(20),
  STATE_ID   NUMBER(10),
  LOGIN_IP   VARCHAR2(20),
  LOGIN_TIME DATE
)
tablespace CC
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_USER_LOGIN
  add primary key (KEY)
  using index 
  tablespace CC
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );


spool off
