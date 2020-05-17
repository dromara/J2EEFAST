--- QRTZ 定时任务表
DROP TABLE  QRTZ_FIRED_TRIGGERS;
DROP TABLE QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE QRTZ_SCHEDULER_STATE;
DROP TABLE QRTZ_LOCKS;
DROP TABLE QRTZ_SIMPLE_TRIGGERS;
DROP TABLE QRTZ_SIMPROP_TRIGGERS;
DROP TABLE QRTZ_CRON_TRIGGERS;
DROP TABLE QRTZ_BLOB_TRIGGERS;
DROP TABLE QRTZ_TRIGGERS;
DROP TABLE QRTZ_JOB_DETAILS;
DROP TABLE QRTZ_CALENDARS;


-- 存储每一个已配置的 Job 的详细信息
CREATE TABLE qrtz_job_details
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  JOB_NAME  VARCHAR2(200) NOT NULL,
  JOB_GROUP VARCHAR2(200) NOT NULL,
  DESCRIPTION VARCHAR2(250) NULL,
  JOB_CLASS_NAME   VARCHAR2(250) NOT NULL,
  IS_DURABLE VARCHAR2(1) NOT NULL,
  IS_NONCONCURRENT VARCHAR2(1) NOT NULL,
  IS_UPDATE_DATA VARCHAR2(1) NOT NULL,
  REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,
  JOB_DATA BLOB NULL,
  CONSTRAINT QRTZ_JOB_DETAILS_PK PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
);
--  存储已配置的 Trigger 的信息
CREATE TABLE qrtz_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  JOB_NAME  VARCHAR2(200) NOT NULL,
  JOB_GROUP VARCHAR2(200) NOT NULL,
  DESCRIPTION VARCHAR2(250) NULL,
  NEXT_FIRE_TIME NUMBER(13) NULL,
  PREV_FIRE_TIME NUMBER(13) NULL,
  PRIORITY NUMBER(13) NULL,
  TRIGGER_STATE VARCHAR2(16) NOT NULL,
  TRIGGER_TYPE VARCHAR2(8) NOT NULL,
  START_TIME NUMBER(13) NOT NULL,
  END_TIME NUMBER(13) NULL,
  CALENDAR_NAME VARCHAR2(200) NULL,
  MISFIRE_INSTR NUMBER(2) NULL,
  JOB_DATA BLOB NULL,
  CONSTRAINT QRTZ_TRIGGERS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_TRIGGER_TO_JOBS_FK FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
  REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
);
-- 存储简单的 Trigger，包括重复次数，间隔，以及已触的次数
CREATE TABLE qrtz_simple_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  REPEAT_COUNT NUMBER(7) NOT NULL,
  REPEAT_INTERVAL NUMBER(12) NOT NULL,
  TIMES_TRIGGERED NUMBER(10) NOT NULL,
  CONSTRAINT QRTZ_SIMPLE_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_SIMPLE_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
-- 存储 Cron Trigger，包括 Cron 表达式和时区信息
CREATE TABLE qrtz_cron_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  CRON_EXPRESSION VARCHAR2(120) NOT NULL,
  TIME_ZONE_ID VARCHAR2(80),
  CONSTRAINT QRTZ_CRON_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_CRON_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_simprop_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  STR_PROP_1 VARCHAR2(512) NULL,
  STR_PROP_2 VARCHAR2(512) NULL,
  STR_PROP_3 VARCHAR2(512) NULL,
  INT_PROP_1 NUMBER(10) NULL,
  INT_PROP_2 NUMBER(10) NULL,
  LONG_PROP_1 NUMBER(13) NULL,
  LONG_PROP_2 NUMBER(13) NULL,
  DEC_PROP_1 NUMERIC(13,4) NULL,
  DEC_PROP_2 NUMERIC(13,4) NULL,
  BOOL_PROP_1 VARCHAR2(1) NULL,
  BOOL_PROP_2 VARCHAR2(1) NULL,
  CONSTRAINT QRTZ_SIMPROP_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_SIMPROP_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
-- Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型，<span style="color:#800080;">JobStore</span> 并不知道如何存储实例的时候)
CREATE TABLE qrtz_blob_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  BLOB_DATA BLOB NULL,
  CONSTRAINT QRTZ_BLOB_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_BLOB_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
-- 以 Blob 类型存储 Quartz 的 Calendar 信息
CREATE TABLE qrtz_calendars
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  CALENDAR_NAME  VARCHAR2(200) NOT NULL,
  CALENDAR BLOB NOT NULL,
  CONSTRAINT QRTZ_CALENDARS_PK PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
);
-- 存储已暂停的 Trigger 组的信息
CREATE TABLE qrtz_paused_trigger_grps
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_GROUP  VARCHAR2(200) NOT NULL,
  CONSTRAINT QRTZ_PAUSED_TRIG_GRPS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
);
-- 存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息
CREATE TABLE qrtz_fired_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  ENTRY_ID VARCHAR2(95) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  INSTANCE_NAME VARCHAR2(200) NOT NULL,
  FIRED_TIME NUMBER(13) NOT NULL,
  SCHED_TIME NUMBER(13) NOT NULL,
  PRIORITY NUMBER(13) NOT NULL,
  STATE VARCHAR2(16) NOT NULL,
  JOB_NAME VARCHAR2(200) NULL,
  JOB_GROUP VARCHAR2(200) NULL,
  IS_NONCONCURRENT VARCHAR2(1) NULL,
  REQUESTS_RECOVERY VARCHAR2(1) NULL,
  CONSTRAINT QRTZ_FIRED_TRIGGER_PK PRIMARY KEY (SCHED_NAME,ENTRY_ID)
);
-- 存储少量的有关 Scheduler 的状态信息，和别的 Scheduler 实例(假如是用于一个集群中)
CREATE TABLE qrtz_scheduler_state
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  INSTANCE_NAME VARCHAR2(200) NOT NULL,
  LAST_CHECKIN_TIME NUMBER(13) NOT NULL,
  CHECKIN_INTERVAL NUMBER(13) NOT NULL,
  CONSTRAINT QRTZ_SCHEDULER_STATE_PK PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
);
-- 存储程序的悲观锁的信息(假如使用了悲观锁)
CREATE TABLE qrtz_locks
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  LOCK_NAME  VARCHAR2(40) NOT NULL,
  CONSTRAINT QRTZ_LOCKS_PK PRIMARY KEY (SCHED_NAME,LOCK_NAME)
);

create index idx_qrtz_j_req_recovery on qrtz_job_details(SCHED_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_j_grp on qrtz_job_details(SCHED_NAME,JOB_GROUP);

create index idx_qrtz_t_j on qrtz_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_t_jg on qrtz_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_t_c on qrtz_triggers(SCHED_NAME,CALENDAR_NAME);
create index idx_qrtz_t_g on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP);
create index idx_qrtz_t_state on qrtz_triggers(SCHED_NAME,TRIGGER_STATE);
create index idx_qrtz_t_n_state on qrtz_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_n_g_state on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_next_fire_time on qrtz_triggers(SCHED_NAME,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st on qrtz_triggers(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME);
create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_ft_j_g on qrtz_fired_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_ft_jg on qrtz_fired_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_ft_t_g on qrtz_fired_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
create index idx_qrtz_ft_tg on qrtz_fired_triggers(SCHED_NAME,TRIGGER_GROUP);

-- 系统表

DROP TABLE gen_table CASCADE CONSTRAINTS;
DROP TABLE gen_table_column CASCADE CONSTRAINTS;
DROP TABLE sys_comp CASCADE CONSTRAINTS;
DROP TABLE sys_comp_dept CASCADE CONSTRAINTS;
DROP TABLE sys_config CASCADE CONSTRAINTS;
DROP TABLE sys_dept CASCADE CONSTRAINTS;
DROP TABLE sys_dict_data CASCADE CONSTRAINTS;

--- 代码生成器
CREATE TABLE gen_table (
  table_id number(20) NOT NULL,
  table_name varchar2(200) DEFAULT '',
  table_comment varchar2(500)  DEFAULT '',
  class_name varchar2(100)  DEFAULT '',
  tpl_category varchar2(200)  DEFAULT 'crud',
  package_name varchar2(100)  DEFAULT NULL,
  module_name varchar2(30)  DEFAULT NULL ,
  business_name varchar2(30)  DEFAULT NULL ,
  function_name varchar2(50)  DEFAULT NULL,
  function_author varchar2(50)  DEFAULT NULL,
  actions_type char(50)   DEFAULT 'default',
  is_cover char(1)   DEFAULT 'N',
  is_del char(1)   DEFAULT 'Y' ,
  run_path varchar2(200)   DEFAULT '/' ,
  options varchar2(1000)  DEFAULT NULL ,
  parent_id number(20) DEFAULT '0',
  parent_name varchar2(50)   DEFAULT '' ,
  menu_name varchar2(50)   DEFAULT '',
  module_codes varchar2(100)   DEFAULT 'core',
  menu_order number(11) DEFAULT '0',
  menu_icon varchar2(50)   DEFAULT '',
  menu_target varchar2(20)   DEFAULT '',
  db_type char(1)  DEFAULT '0',
  create_by varchar2(64)  DEFAULT '',
  create_time timestamp DEFAULT NULL,
  update_by varchar2(64) DEFAULT '',
  update_time timestamp DEFAULT NULL,
  remark varchar2(500) DEFAULT NULL,
  PRIMARY KEY (table_id)
);

CREATE TABLE gen_table_column (
  column_id number(20) NOT NULL,
  table_id varchar2(64)  DEFAULT NULL,
  column_name varchar2(200)  DEFAULT NULL,
  column_comment varchar2(500)  DEFAULT NULL,
  column_type varchar2(100)  DEFAULT NULL,
  java_type varchar2(500)  DEFAULT NULL,
  java_field varchar2(200)  DEFAULT NULL,
  is_plus char(1)  DEFAULT '0',
  is_pk char(1)  DEFAULT NULL,
  is_increment char(1)  DEFAULT NULL,
  is_required char(1)  DEFAULT NULL,
  is_insert char(1)  DEFAULT NULL,
  is_edit char(1)  DEFAULT NULL,
  is_list char(1)  DEFAULT NULL,
  is_query char(1)  DEFAULT NULL,
  query_type varchar2(200)  DEFAULT 'EQ',
  html_type varchar2(200)  DEFAULT NULL,
  edit_info varchar2(2000)   DEFAULT '',
  dict_type varchar2(200)  DEFAULT '',
  circle_type char(1)   DEFAULT 'T',
  is_table_sort char(1)  DEFAULT '0',
  sort number(11) DEFAULT NULL,
  create_by varchar2(64)  DEFAULT '',
  create_time timestamp DEFAULT NULL,
  update_by varchar2(64)  DEFAULT '',
  update_time timestamp DEFAULT NULL,
  PRIMARY KEY (column_id)
);

CREATE TABLE sys_comp (
  comp_id number(20) NOT NULL,
  parent_id number(20) DEFAULT NULL,
  name varchar2(200)  DEFAULT NULL,
  remark varchar2(200)  DEFAULT '',
  order_num number(11) DEFAULT NULL,
  del_flag char(1)  DEFAULT '0',
  status char(1)  DEFAULT '0',
  create_time timestamp DEFAULT NULL,
  create_by varchar2(64)  DEFAULT '',
  update_by varchar2(64)  DEFAULT '',
  update_time timestamp DEFAULT NULL,
  PRIMARY KEY (comp_id)
);

CREATE TABLE sys_comp_dept (
  id number(20) NOT NULL,
  comp_id number(20) DEFAULT NULL,
  dept_id number(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE sys_config (
  id number(20) NOT NULL,
  param_name varchar2(100)  DEFAULT '',
  param_key varchar2(50)  DEFAULT '',
  param_value nvarchar2(2000) DEFAULT '',
  config_type char(1)  DEFAULT 'N',
  del_flag char(1) DEFAULT '0',
  remark varchar2(500)  DEFAULT '',
  create_by varchar2(64)  DEFAULT '',
  create_time timestamp DEFAULT NULL,
  update_time timestamp DEFAULT NULL,
  update_by varchar2(64)  DEFAULT '',
  PRIMARY KEY (id, param_key)
);

CREATE TABLE sys_dept (
  dept_id number(20) NOT NULL,
  parent_id number(20) DEFAULT NULL,
  name varchar2(50)  DEFAULT '',
  type number(11) DEFAULT NULL,
  order_num number(11) DEFAULT NULL,
  del_flag char(1)  DEFAULT '0',
  status char(1)  DEFAULT '0',
  create_time timestamp DEFAULT NULL,
  create_by varchar2(64)  DEFAULT '',
  update_by varchar2(64)  DEFAULT '',
  update_time timestamp DEFAULT NULL,
  remark varchar2(200)  DEFAULT '',
  PRIMARY KEY (dept_id)
);

CREATE TABLE sys_dict_data (
  dict_code number(20) NOT NULL,
  dict_sort number(4) DEFAULT '0',
  dict_label varchar2(100)  DEFAULT '',
  dict_value varchar2(100)  DEFAULT '',
  dict_type varchar2(100)  DEFAULT '',
  css_class varchar2(500)  DEFAULT '',
  del_flag char(1)  DEFAULT '0',
  is_sys char(1)  DEFAULT 'Y',
  status char(1)  DEFAULT '0',
  css_style varchar2(500)  DEFAULT '',
  list_class varchar2(500)  DEFAULT '',
  is_default char(1)  DEFAULT 'N',
  create_by varchar2(64)  DEFAULT '',
  create_time timestamp DEFAULT NULL,
  update_by varchar2(64)  DEFAULT '',
  update_time timestamp DEFAULT NULL,
  remark varchar2(500)  DEFAULT NULL,
  PRIMARY KEY (dict_code)
);

CREATE TABLE sys_dict_type (
  dict_id number(20) NOT NULL,
  dict_name varchar2(100)  DEFAULT '',
  dict_type varchar2(100)  DEFAULT '',
  is_sys char(1)  DEFAULT 'Y',
  del_flag char(1)  DEFAULT '0',
  status char(1)  DEFAULT '0',
  create_by varchar2(64)  DEFAULT '',
  create_time timestamp DEFAULT NULL,
  update_by varchar2(64)  DEFAULT '',
  update_time timestamp DEFAULT NULL,
  remark varchar2(500)  DEFAULT NULL,
  PRIMARY KEY (dict_id,dict_type)
);

CREATE TABLE sys_job (
  job_id number(20) NOT NULL,
  bean_name varchar2(200)  DEFAULT NULL,
  method_name varchar2(100)  DEFAULT NULL,
  params nvarchar2(1000)  DEFAULT NULL,
  cron_expression varchar2(100)  DEFAULT NULL,
  del_flag char(1)  DEFAULT '0',
  status char(1)  DEFAULT '0',
  remark varchar2(255)  DEFAULT NULL,
  create_time timestamp DEFAULT NULL,
  job_group varchar2(64)  DEFAULT 'DEFAULT',
  create_by varchar2(64)  DEFAULT '',
  update_by varchar2(64)  DEFAULT '',
  update_time timestamp DEFAULT NULL,
  job_name varchar2(64)  DEFAULT '',
  PRIMARY KEY (job_id)
);

CREATE TABLE sys_job_log (
  log_id number(20) NOT NULL,
  job_id number(20) NOT NULL,
  bean_name varchar2(200)  DEFAULT NULL,
  method_name varchar2(100)  DEFAULT NULL,
  params nvarchar2(2000)  DEFAULT NULL,
  status char(1)  DEFAULT '1' NOT NULL,
  error nvarchar2(2000)  DEFAULT NULL,
  times number(11) NOT NULL,
  create_time timestamp DEFAULT NULL,
  job_name varchar2(64)  DEFAULT '',
  job_group varchar2(64)  DEFAULT 'DEFAULT',
  PRIMARY KEY (log_id)
);

CREATE TABLE sys_login_infor (
  id number(20) NOT NULL,
  username varchar2(50)  DEFAULT '',
  ipaddr varchar2(50)  DEFAULT '',
  login_location varchar2(255)  DEFAULT '',
  browser varchar2(50)  DEFAULT '',
  os varchar2(50)  DEFAULT '',
  mobile char(1)  DEFAULT NULL,
  status varchar2(10)  DEFAULT '50005',
  msg varchar2(255)  DEFAULT '',
  login_time timestamp DEFAULT NULL,
  comp_id number(20) DEFAULT '-1',
  PRIMARY KEY (id)
);

CREATE TABLE sys_menu (
  menu_id number(20) NOT NULL,
  parent_id number(20) DEFAULT NULL,
  name varchar2(50)  DEFAULT '',
  url varchar2(200)  DEFAULT '',
  module_codes varchar2(500)  DEFAULT 'core',
  target varchar2(20)  DEFAULT '',
  perms varchar2(500)  DEFAULT '',
  type number(11) DEFAULT NULL,
  icon varchar2(50)  DEFAULT '',
  order_num number(11) DEFAULT NULL,
  hide number(4) DEFAULT '0',
  create_by varchar2(64)  DEFAULT '',
  create_time timestamp DEFAULT NULL,
  update_by varchar2(64)  DEFAULT '',
  update_time timestamp DEFAULT NULL,
  remark varchar2(500)  DEFAULT '',
  PRIMARY KEY (menu_id)
);

CREATE TABLE sys_module (
  id number(20) NOT NULL,
  module_code varchar2(64)  NOT NULL,
  module_name varchar2(100)  NOT NULL,
  description varchar2(500)  DEFAULT NULL,
  main_class_name varchar2(500)  DEFAULT NULL,
  icon varchar2(50)  DEFAULT '',
  current_version varchar2(50)  DEFAULT NULL,
  upgrade_info varchar2(300)  DEFAULT NULL,
  del_flag char(1)  DEFAULT '0',
  status char(1)  DEFAULT '0' NOT NULL,
  create_by varchar2(64)  DEFAULT '',
  create_time timestamp DEFAULT NULL,
  update_by varchar2(64)  DEFAULT '',
  update_time timestamp DEFAULT NULL,
  remark varchar2(500)  DEFAULT NULL,
  PRIMARY KEY (id,module_code)
);

CREATE TABLE sys_notice (
  id number(20) NOT NULL,
  notice_title varchar2(50)  DEFAULT NULL,
  notice_tip_title varchar2(100)  DEFAULT NULL,
  start_time timestamp DEFAULT NULL,
  end_time timestamp DEFAULT NULL,
  status char(1)  DEFAULT NULL,
  create_by varchar2(64)  DEFAULT '',
  create_time timestamp DEFAULT NULL,
  update_by varchar2(64)  DEFAULT '',
  update_time timestamp DEFAULT NULL,
  remark varchar2(200)  DEFAULT '',
  html_no varchar2(32)  DEFAULT NULL,
  notice_type char(1)  DEFAULT NULL,
  notice_level char(1)  DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE sys_oper_log (
  oper_id number(20) NOT NULL,
  title varchar2(50)  DEFAULT '',
  business_type number(2) DEFAULT '0',
  method varchar2(100)  DEFAULT '',
  request_method varchar2(10)  DEFAULT '',
  operator_type number(1) DEFAULT '0',
  oper_name varchar2(50)  DEFAULT '',
  comp_name varchar2(50)  DEFAULT '',
  oper_url varchar2(255)  DEFAULT '',
  oper_ip varchar2(50)  DEFAULT '' ,
  oper_location varchar2(255)  DEFAULT '',
  oper_param varchar2(2000)  DEFAULT '',
  json_result varchar2(2000)  DEFAULT '' ,
  status number(1) DEFAULT '0',
  error_msg varchar2(2000)  DEFAULT '',
  oper_time timestamp DEFAULT NULL,
  time number(20) DEFAULT '0',
  PRIMARY KEY (oper_id)
);

CREATE TABLE sys_post (
  post_id number(20) NOT NULL,
  post_code varchar2(64)  NOT NULL,
  post_name varchar2(50)  NOT NULL,
  post_sort number(4) NOT NULL,
  post_type char(1)  DEFAULT '0',
  status char(1)  NOT NULL,
  create_by varchar2(64)  DEFAULT '',
  create_time timestamp DEFAULT NULL,
  update_by varchar2(64)  DEFAULT '',
  update_time timestamp DEFAULT NULL,
  remark varchar2(500)  DEFAULT NULL,
  PRIMARY KEY (post_id,post_code)
);

CREATE TABLE sys_role (
  role_id number(20) NOT NULL,
  role_name varchar2(100)  DEFAULT '',
  dept_id number(20) DEFAULT NULL,
  create_time timestamp DEFAULT NULL,
  role_key varchar2(100)  DEFAULT '',
  data_scope char(1)  DEFAULT '1',
  role_sort number(4) DEFAULT NULL,
  del_flag char(1)  DEFAULT '0',
  status char(1)  DEFAULT '0',
  create_by varchar2(64)  DEFAULT '',
  update_by varchar2(64)  DEFAULT '',
  update_time timestamp DEFAULT NULL,
  remark varchar2(100)  DEFAULT NULL,
  PRIMARY KEY (role_id)
);

CREATE TABLE sys_role_dept (
  id number(20) NOT NULL,
  role_id number(20) DEFAULT NULL,
  dept_id number(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE sys_role_menu (
  id number(20) NOT NULL,
  role_id number(20) DEFAULT NULL,
  menu_id number(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE sys_role_module (
  id number(20) NOT NULL,
  role_id number(20) DEFAULT NULL,
  module_code varchar2(64)  DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE sys_user (
  user_id number(20) NOT NULL,
  username varchar2(50)  NOT NULL,
  name varchar2(500)  DEFAULT NULL,
  password varchar2(100)  DEFAULT NULL,
  salt varchar2(20)  DEFAULT NULL,
  email varchar2(100)  DEFAULT NULL,
  mobile varchar2(100)  DEFAULT NULL,
  status char(1)  DEFAULT NULL,
  del_flag char(1)  DEFAULT '0',
  pwd_security_level char(1)  DEFAULT '0',
  pwd_update_date timestamp DEFAULT NULL,
  comp_id number(20) DEFAULT NULL,
  dept_id number(20) DEFAULT NULL,
  create_time timestamp DEFAULT NULL,
  card_id varchar2(50)  DEFAULT NULL,
  avatar varchar2(100)  DEFAULT '',
  update_time timestamp DEFAULT NULL,
  update_by varchar2(64)  DEFAULT '',
  create_by varchar2(64)  DEFAULT '',
  remark varchar2(1000)  DEFAULT '',
  PRIMARY KEY (user_id,username)
);

CREATE TABLE sys_user_comp (
  id number(20) NOT NULL,
  user_id number(20) DEFAULT NULL,
  comp_id number(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE sys_user_dept (
  id number(20) NOT NULL,
  user_id number(20) DEFAULT NULL ,
  dept_id number(20) DEFAULT NULL ,
  PRIMARY KEY (id)
);


CREATE TABLE sys_user_post (
  id number(20) NOT NULL,
  user_id number(20) NOT NULL,
  post_code varchar2(64)  NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE sys_user_role (
  id number(20) NOT NULL,
  user_id number(20) DEFAULT NULL,
  role_id number(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE sys_database (
  id number(20) NOT NULL,
  db_name varchar2(255)  NOT NULL,
  jdbc_driver varchar2(500)  NOT NULL,
  user_name varchar2(255)  NOT NULL,
  password varchar2(255)  NOT NULL,
  jdbc_url varchar2(2000)  NOT NULL,
  status char(1)  DEFAULT '0',
  del_flag char(1)  DEFAULT '0',
  enc_type varchar2(10)  DEFAULT 'DEFAULT',
  create_time timestamp DEFAULT NULL,
  create_by varchar2(64)  DEFAULT NULL,
  update_by varchar2(64)  DEFAULT NULL,
  update_time timestamp DEFAULT NULL,
  remark varchar2(500)  DEFAULT NULL,
  PRIMARY KEY (id,db_name)
) 