prompt PL/SQL Developer import file
prompt Created on 2006Äê11ÔÂ2ÈÕ by ddddd
set feedback off
set define off
prompt Dropping POLICEINFO_TEMP...
drop table POLICEINFO_TEMP cascade constraints;
prompt Creating POLICEINFO_TEMP...
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

prompt Disabling triggers for POLICEINFO_TEMP...
alter table POLICEINFO_TEMP disable all triggers;
prompt Loading POLICEINFO_TEMP...
insert into POLICEINFO_TEMP (ID, P_ID, TAG_INFO, QU_INFO, CONTENT, REMARK, TAG)
values ('POLICEINFO_TEMP_0000000001', 'CC_LOG_0000001201', 'SYS_TREE_0000000262', '111', '11', '111', 'N');
insert into POLICEINFO_TEMP (ID, P_ID, TAG_INFO, QU_INFO, CONTENT, REMARK, TAG)
values ('POLICEINFO_TEMP_0000000002', 'CC_LOG_0000001201', 'SYS_TREE_0000000262', '222', '222', '222', 'N');
commit;
prompt 2 records loaded
prompt Enabling triggers for POLICEINFO_TEMP...
alter table POLICEINFO_TEMP enable all triggers;
set feedback on
set define on
prompt Done.
