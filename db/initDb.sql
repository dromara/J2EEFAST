/********************************************************************
 * 初始化数据库
 * @author zhouzhou
 * @date 2018-11-14 23:28
 ********************************************************************/
###############################创建Mysql数据库表#########################
/** 注意: Mysql版本必须在5.7 以上**/
/* 已ROOT 账户登陆你的Mysql 执行以下 创建fast库*/
CREATE USER 'fast'@'localhost' IDENTIFIED BY 'fast@123$';
CREATE USER 'fast'@'%' IDENTIFIED BY 'fast@123$';

CREATE DATABASE fastdb DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_bin;

GRANT ALL PRIVILEGES ON `fastdb`.* TO 'fast'@'%' IDENTIFIED BY 'fast@123$';
GRANT ALL PRIVILEGES ON `fastdb`.* TO 'fast'@'localhost' IDENTIFIED BY 'fast@123$';

/***************************导入SQL即可*********************************
 *进入 fastdb 数据库导入以下数据结构
***************************相关表*****************************/
/*
SQLyog Ultimate v12.3.1 (64 bit)
MySQL - 5.7.24 : Database - fastdb
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fastdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;

USE `fastdb`;

/*Table structure for table `gen_table` */

DROP TABLE IF EXISTS `gen_table`;

CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '表描述',
  `class_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '生成功能作者',
  `actions_type` char(50) COLLATE utf8mb4_bin DEFAULT 'default' COMMENT '操作按钮风格(default,icon)',
  `is_cover` char(1) COLLATE utf8mb4_bin DEFAULT 'N' COMMENT '生成代码是否覆盖替换',
  `is_del` char(1) COLLATE utf8mb4_bin DEFAULT 'Y' COMMENT '只有一个页面 是否需要删除功能',
  `run_path` varchar(200) COLLATE utf8mb4_bin DEFAULT '/' COMMENT '生成路径(不填默认项目路径)',
  `options` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '其它生成选项',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '上级菜单ID',
  `parent_name` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '父菜单名称',
  `menu_name` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '菜单名称',
  `module_codes` varchar(100) COLLATE utf8mb4_bin DEFAULT 'core' COMMENT '归属模块（多个用逗号隔开）',
  `menu_order` int(11) DEFAULT '0' COMMENT '菜单排序',
  `menu_icon` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '菜单图标',
  `menu_target` varchar(20) COLLATE utf8mb4_bin DEFAULT '' COMMENT '目标(打开方式)',
  `db_type` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '数据库类型(0 主数据库 1从数据库)',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='代码生成业务表';

/*Table structure for table `gen_table_column` */

DROP TABLE IF EXISTS `gen_table_column`;

CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL COMMENT '编号',
  `table_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'JAVA字段名',
  `is_plus` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '是否未MapperPlus（1是）',
  `is_pk` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `edit_info` varchar(2000) COLLATE utf8mb4_bin DEFAULT '' COMMENT '字段说明',
  `dict_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '字典类型',
  `circle_type` char(1) COLLATE utf8mb4_bin DEFAULT 'T' COMMENT '字段说明样式T 普通形 Q 黄色问号弹出型 R红色明显提示',
  `is_table_sort` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '字段是否为表sortable 1是',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='代码生成业务表字段';

/*Table structure for table `qrtz_blob_triggers` */

DROP TABLE IF EXISTS `qrtz_blob_triggers`;

CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_calendars` */

DROP TABLE IF EXISTS `qrtz_calendars`;

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_cron_triggers` */

DROP TABLE IF EXISTS `qrtz_cron_triggers`;

CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_fired_triggers` */

DROP TABLE IF EXISTS `qrtz_fired_triggers`;

CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_job_details` */

DROP TABLE IF EXISTS `qrtz_job_details`;

CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_locks` */

DROP TABLE IF EXISTS `qrtz_locks`;

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_paused_trigger_grps` */

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_scheduler_state` */

DROP TABLE IF EXISTS `qrtz_scheduler_state`;

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_simple_triggers` */

DROP TABLE IF EXISTS `qrtz_simple_triggers`;

CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_simprop_triggers` */

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;

CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_triggers` */

DROP TABLE IF EXISTS `qrtz_triggers`;

CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_comp` */

DROP TABLE IF EXISTS `sys_comp`;

CREATE TABLE `sys_comp` (
  `comp_id` bigint(20) NOT NULL COMMENT '公司主键id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级公司ID，一级公司为0',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '公司名称',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '描述',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标志 0：正常 1：删除',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`comp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='公司表';

/*Table structure for table `sys_comp_dept` */

DROP TABLE IF EXISTS `sys_comp_dept`;

CREATE TABLE `sys_comp_dept` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `comp_id` bigint(20) DEFAULT NULL COMMENT '公司ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '地区ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='公司对应地区关系';

/*Table structure for table `sys_config` */

DROP TABLE IF EXISTS `sys_config`;

CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `param_name` varchar(100) CHARACTER SET utf8 DEFAULT '' COMMENT '参数名称',
  `param_key` varchar(50) CHARACTER SET utf8 DEFAULT '' COMMENT '参数Key',
  `param_value` varchar(10000) CHARACTER SET utf8 DEFAULT '' COMMENT '参数值 Value',
  `config_type` char(1) CHARACTER SET utf8 DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标志 0：正常 1：删除',
  `remark` varchar(500) CHARACTER SET utf8 DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8 DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新者',
  `update_by` varchar(64) CHARACTER SET utf8 DEFAULT '' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统配置信息表';

/*Table structure for table `sys_dept` */

DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL COMMENT '主键id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) CHARACTER SET utf8 DEFAULT '' COMMENT '部门名称',
  `type` int(11) DEFAULT NULL COMMENT '类型  0: 地区 1:线路',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标志 0：正常 1：删除',
  `status` char(1) CHARACTER SET utf8 DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(64) CHARACTER SET utf8 DEFAULT '',
  `update_by` varchar(64) CHARACTER SET utf8 DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8 DEFAULT '',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='部门管理/地区管理';

/*Table structure for table `sys_dict_data` */

DROP TABLE IF EXISTS `sys_dict_data`;

CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL COMMENT '字典主键',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '样式属性（其他样式扩展）css类名（如：red）',
  `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标志 0：正常 1：删除',
  `is_sys` char(1) COLLATE utf8mb4_bin DEFAULT 'Y' COMMENT '是否系统内置(Y 是 N否)',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `css_style` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'css样式（如：color:red)',
  `list_class` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='字典数据表';

/*Table structure for table `sys_dict_type` */

DROP TABLE IF EXISTS `sys_dict_type`;

CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '字典类型',
  `is_sys` char(1) COLLATE utf8mb4_bin DEFAULT 'Y' COMMENT '是否系统内置(Y 是 N否)',
  `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标志 0：正常 1：删除',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='字典类型表';

/*Table structure for table `sys_index` */

DROP TABLE IF EXISTS `sys_index`;

CREATE TABLE `sys_index` (
  `help_topic_id` bigint(20) NOT NULL COMMENT '连续ID',
  `temp` varchar(5) COLLATE utf8mb4_bin DEFAULT '',
  PRIMARY KEY (`help_topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统数据库获取连续id';

/*Table structure for table `sys_job` */

DROP TABLE IF EXISTS `sys_job`;

CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Spring Bean名称',
  `method_name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) CHARACTER SET utf8 DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Cron表达式',
  `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标志 0：正常 1：删除',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `job_group` varchar(64) CHARACTER SET utf8 DEFAULT 'DEFAULT' COMMENT '任务组名',
  `create_by` varchar(64) CHARACTER SET utf8 DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8 DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `job_name` varchar(64) CHARACTER SET utf8 DEFAULT '' COMMENT '任务名称',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='定时任务';

/*Table structure for table `sys_job_log` */

DROP TABLE IF EXISTS `sys_job_log`;

CREATE TABLE `sys_job_log` (
  `log_id` bigint(20) NOT NULL COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Spring Bean名称',
  `method_name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) CHARACTER SET utf8 DEFAULT NULL COMMENT '参数',
  `status` char(1) COLLATE utf8mb4_bin NOT NULL DEFAULT '1' COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) CHARACTER SET utf8 DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `job_name` varchar(64) CHARACTER SET utf8 DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8 DEFAULT 'DEFAULT' COMMENT '任务组名',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='定时任务日志';

/*Table structure for table `sys_login_infor` */

DROP TABLE IF EXISTS `sys_login_infor`;

CREATE TABLE `sys_login_infor` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '登录账号',
  `ipaddr` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '系统类型',
  `mobile` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '移动平台(0) 电脑设备(1)',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '50005' COMMENT '登录状态（00000成功 其他失败）',
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  `comp_id` bigint(20) DEFAULT '-1' COMMENT '公司ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统访问记录';

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL COMMENT '主键',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '菜单名称',
  `url` varchar(200) COLLATE utf8mb4_bin DEFAULT '' COMMENT '菜单URL',
  `module_codes` varchar(500) COLLATE utf8mb4_bin DEFAULT 'core' COMMENT '归属模块（多个用逗号隔开）',
  `target` varchar(20) COLLATE utf8mb4_bin DEFAULT '' COMMENT '目标(打开方式)',
  `perms` varchar(500) COLLATE utf8mb4_bin DEFAULT '' COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `hide` tinyint(4) DEFAULT '0' COMMENT '是否删除  1：隐藏  0：正常',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='菜单管理';

/*Table structure for table `sys_module` */

DROP TABLE IF EXISTS `sys_module`;

CREATE TABLE `sys_module` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `module_code` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '模块编码',
  `module_name` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '模块名称',
  `description` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模块描述',
  `main_class_name` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '主类全名',
  `icon` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '图标',
  `current_version` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '当前版本',
  `upgrade_info` varchar(300) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '升级信息',
  `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标志 0：正常 1：删除',
  `status` char(1) COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`,`module_code`),
  UNIQUE KEY `module_code` (`module_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='模块表';

/*Table structure for table `sys_notice` */

DROP TABLE IF EXISTS `sys_notice`;

CREATE TABLE `sys_notice` (
  `id` bigint(20) unsigned zerofill NOT NULL COMMENT '主键ID',
  `notice_title` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '公告标题',
  `notice_tip_title` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '公告提示栏',
  `start_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态 0 正常 1关闭 2失效',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(200) COLLATE utf8mb4_bin DEFAULT '' COMMENT '备注',
  `html_no` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '静态页编号',
  `notice_type` char(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '公告类型 0通知 1公告',
  `notice_level` char(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通过公告等级级别 0 普通 1紧急 2严重'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Table structure for table `sys_oper_log` */

DROP TABLE IF EXISTS `sys_oper_log`;

CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL COMMENT '主键',
  `title` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) COLLATE utf8mb4_bin DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '操作人员',
  `comp_name` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '公司名称',
  `oper_url` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) COLLATE utf8mb4_bin DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) COLLATE utf8mb4_bin DEFAULT '' COMMENT '返回参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) COLLATE utf8mb4_bin DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `time` bigint(20) DEFAULT '0' COMMENT '操作时长',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='操作日志记录';

/*Table structure for table `sys_post` */

DROP TABLE IF EXISTS `sys_post`;

CREATE TABLE `sys_post` (
  `post_id` bigint(20) NOT NULL COMMENT '主键',
  `post_code` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `post_type` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '岗位分类',
  `status` char(1) COLLATE utf8mb4_bin NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`,`post_code`),
  UNIQUE KEY `post_code` (`post_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='岗位信息表';

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL COMMENT '主键',
  `role_name` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '角色名称',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID[暂停使用]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `role_key` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '角色权限字符串',
  `data_scope` char(1) COLLATE utf8mb4_bin DEFAULT '1' COMMENT '权限范围 1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限',
  `role_sort` int(4) DEFAULT NULL COMMENT '显示顺序',
  `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色';

/*Table structure for table `sys_role_dept` */

DROP TABLE IF EXISTS `sys_role_dept`;

CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色与部门对应关系';

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色与菜单对应关系';

/*Table structure for table `sys_role_module` */

DROP TABLE IF EXISTS `sys_role_module`;

CREATE TABLE `sys_role_module` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) DEFAULT NULL COMMENT '权限ID',
  `module_code` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模块编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色对应模块表';

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL COMMENT '主键',
  `username` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '登陆账号',
  `name` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '姓名',
  `password` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '盐',
  `email` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态  1：禁用   0：正常',
  `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否删除',
  `pwd_security_level` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '密码安全级别（0初始 1很弱 2弱 3安全 4很安全）',
  `pwd_update_date` datetime DEFAULT NULL COMMENT '密码最后更新时间',
  `comp_id` bigint(20) DEFAULT NULL COMMENT '公司ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `card_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户对应卡号',
  `avatar` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '用户头像',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `remark` varchar(1000) COLLATE utf8mb4_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`user_id`,`username`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统用户';

/*Table structure for table `sys_user_comp` */

DROP TABLE IF EXISTS `sys_user_comp`;

CREATE TABLE `sys_user_comp` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `comp_id` bigint(20) DEFAULT NULL COMMENT '公司ID',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `comp_id` (`comp_id`),
  CONSTRAINT `sys_user_comp_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`),
  CONSTRAINT `sys_user_comp_ibfk_2` FOREIGN KEY (`comp_id`) REFERENCES `sys_comp` (`comp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户关联公司表';

/*Table structure for table `sys_user_dept` */

DROP TABLE IF EXISTS `sys_user_dept`;

CREATE TABLE `sys_user_dept` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '地区ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户与公司地区对应关系';

/*Table structure for table `sys_user_post` */

DROP TABLE IF EXISTS `sys_user_post`;

CREATE TABLE `sys_user_post` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_code` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户与岗位关联表';

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户与角色对应关系';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

