/***********************************************************************
--此SQL针对之前下载过FastOS部署过的用户,核心数据库fastdb 只需执行此SQL就可以了
************************************************************************/
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

/*Data for the table `sys_comp` */

insert  into `sys_comp`(`comp_id`,`parent_id`,`name`,`remark`,`order_num`,`del_flag`,`status`,`create_time`,`create_by`,`update_by`,`update_time`) values 

(1,0,'总公司','根部公司',0,'0','0','2019-12-21 23:54:41','','admin','2020-02-24 03:50:37'),

(1236303334564106350,1,'北京有限公司','',2,'0','0','2020-03-15 16:31:16','','admin','2020-04-09 06:23:51'),

(1239875326212898817,1,'南京有限公司','',3,'0','0','2020-03-17 19:24:48','admin','admin','2020-03-20 23:33:31'),

(1241024147242586113,1,'上海法斯特有限公司','',0,'0','0','2020-03-20 10:29:48','admin','admin','2020-03-20 23:33:20');

/*Table structure for table `sys_comp_dept` */

DROP TABLE IF EXISTS `sys_comp_dept`;

CREATE TABLE `sys_comp_dept` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `comp_id` bigint(20) DEFAULT NULL COMMENT '公司ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '地区ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='公司对应地区关系';

/*Data for the table `sys_comp_dept` */

insert  into `sys_comp_dept`(`id`,`comp_id`,`dept_id`) values 

(1241025032186204161,1241024147242586113,2),

(1241025032186204162,1241024147242586113,1241024879228325889),

(1241025032194592769,1241024147242586113,1241024945540272130),

(1241025079393095682,1239875326212898817,1239913616460955650),

(1248255304413716481,1236303334564106350,1238849577246834689),

(1248255304447270914,1236303334564106350,1241024822970126337);

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

/*Data for the table `sys_config` */

insert  into `sys_config`(`id`,`param_name`,`param_key`,`param_value`,`config_type`,`del_flag`,`remark`,`create_by`,`create_time`,`update_time`,`update_by`) values 

(1,'云存储','CLOUD_STORAGE_CONFIG_KEY','{\"type\":1,\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"qiniuBucketName\":\"ios-app\",\"aliyunDomain\":\"\",\"aliyunPrefix\":\"\",\"aliyunEndPoint\":\"\",\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qcloudBucketName\":\"\"}','Y','0','云存储配置信息','','2019-12-24 10:12:02',NULL,''),

(2,'项目标题','SYS_CONFIG_TITLE','FAST云系统OS','Y','0','系统名称','','2019-12-24 10:12:03','2020-02-10 19:14:21','admin'),

(4,'项目版本','SYS_CONFIG_VERSION','3.0.12312','Y','0','系统版本','','2019-12-24 10:12:07','2020-04-28 22:21:18','admin'),

(5,'系统参数','SYS_CONFIG_KEY','{\"login_maxCount\":100,\"login_NumCount\":3,\"lock_time\":5,\"company\":\"Powered By j2eefast\",\"copyrightYear\":\"2020\",\"ipc\":\"湘ICP备20005342号\"}','Y','0','系统参数配置、登陆错误次数锁定账户时间、所属公司','','2019-12-24 10:12:08','2020-04-27 06:38:01','admin'),

(7,'JS版本','SYS_CONFIG_DV_VERSION','202003311233','Y','0','JS\\CSS版本-日期表示','','2019-12-24 10:12:11','2020-02-29 20:15:19','admin'),

(8,'项目小标题','SYS_CONFIG_TITLE_MINI','FOS','Y','0','系统简称','','2019-12-24 10:12:13','2020-02-10 19:14:31','admin'),

(11,'上传路径','SYS_CONFIG_PROFILE','D:\\lixinfile\\uploadPath','Y','0','项目静态文件','','2019-12-24 10:12:18','2020-02-23 13:26:47','admin'),

(12,' 用户管理-账号初始密码','SYS_USER_INITPASSWORD','123456','Y','0','用户初始密码','','2019-12-24 10:12:20','2020-01-02 09:26:18','admin'),

(13,'项目页面压缩','SYS_COMPRESS','false','Y','0','项目输出前端页面是否压缩 true 压缩 false 不压缩 注意:开启页面压缩页面JS不能使用// 做注解 可以使用 /**/','admin','2019-12-27 10:51:49','2020-02-13 08:45:30','admin'),

(14,'登陆排挤','SYS_IS_LOGIN','1','Y','0','同一账户只能一个人登陆 0 支持 1不支持','admin','2020-01-10 18:50:31','2019-12-23 01:40:16','admin'),

(15,'屏保时间','SYS_LOCK_SCEREEN','{\"time\":30,\"flag\":true}','Y','0','是否开启true  false 自动屏保及时间(分钟)','admin','2020-01-18 19:29:17','2019-12-31 00:28:26','admin'),

(16,'主框架页-默认皮肤样式名称','SYS_INDEX_SKINNAME','skin-blue-light','Y','0','skin-black-light、skin-black、skin-blue-light、skin-blue、skin-green-light、skin-green、skin-purple-light、skin-purple、skin-red-light、skin-red、skin-yellow-light、skin-yellow','admin','2020-02-03 11:31:11','2020-01-10 12:06:06','admin'),

(17,'项目文件库','SYS_CONFIG_FILE','D:\\lixinfile\\file\\temp','Y','0','系统默认文件库','admin','2020-02-13 14:56:22','2020-02-23 13:27:08','admin'),

(1243831619548323842,'TOP提示消失时间','SYS_TIP_TIME','3','Y','0','分钟表示','admin','2020-03-28 17:25:42',NULL,''),

(1245316284947877890,'系统登陆页面默认主题','SYS_LOGIN_DEFAULT_VIEW','Gitee','Y','0','系统登陆页面默认主题','admin','2020-04-05 16:01:07','2020-04-05 07:03:11','admin');

/*Table structure for table `sys_database` */

DROP TABLE IF EXISTS `sys_database`;

CREATE TABLE `sys_database` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `db_name` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '数据库名称(英文)获取标识',
  `jdbc_driver` varchar(500) COLLATE utf8mb4_bin NOT NULL COMMENT 'jdbc的驱动类型',
  `user_name` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '数据库账号',
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '数据库密码',
  `jdbc_url` varchar(2000) COLLATE utf8mb4_bin NOT NULL COMMENT '数据库连接',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '0 正常 1停用',
  `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标志删除标志 0：正常 1：删除',
  `enc_type` varchar(10) COLLATE utf8mb4_bin DEFAULT 'DEFAULT' COMMENT '加密标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`,`db_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='多源数据库配置表';

/*Data for the table `sys_database` */

insert  into `sys_database`(`id`,`db_name`,`jdbc_driver`,`user_name`,`password`,`jdbc_url`,`status`,`del_flag`,`enc_type`,`create_time`,`create_by`,`update_by`,`update_time`,`remark`) values 

(1255502628215017474,'DEFAULT','com.mysql.cj.jdbc.Driver','K4ainPMht0X3syWyd3uchw==','3Q2t8koueiBCiIdmigSVab5x+8xMKDE5','Z6o9+H4eT/RtUojcePOBUNnlmBK9FrBP8sZUC8u2ToYI74BfouO8iZ5HyyJuK1JS/q6CerZuO4XgIZWHjU6u287LyJwrTfXoE7f4APXrhXhGHjr6Y0Xg6yArcgidUzv15vQDo3mqaKEMaSu130OcYOZ9nEufLpt/gov86f814EQ=','0','0','ENC','2020-04-29 22:22:07','system',NULL,NULL,'主数据源，项目启动数据源！'),

(1255502628454092802,'FLOWABLE','com.mysql.cj.jdbc.Driver','vJYZ0kVZgjgR15T9OVjZsog3x/WVMGkb','lHVZ+5paT61d0E5bWac4fiJY9+522//OI2dAyoBv4dw=','z+dnk/4Lrz0iYuTXbTe4mftmAIgkEacqo584vOUZ6L/yX/FyMxwlpsx1lH2WzPInMLBvvl0Q/6yysjP0ulsqJuUACePYTJpDAMUDNZDwM6NSmVew+Avb0oEZxV09s7xtXtBaebAZpT0YIg0VKg4iDCYTKZp1Joa0Hfy5sapTbhnSfLPuTk9YEg==','0','0','ENC','2020-04-29 22:22:07','system',NULL,NULL,'Flowable 工作流数据源');

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

/*Data for the table `sys_dept` */

insert  into `sys_dept`(`dept_id`,`parent_id`,`name`,`type`,`order_num`,`del_flag`,`status`,`create_time`,`create_by`,`update_by`,`update_time`,`remark`) values 

(1,0,'中国',0,0,'0','0','2019-12-22 13:14:30',NULL,NULL,NULL,''),

(2,1,'上海市',0,1,'0','0','2019-12-22 13:14:32',NULL,NULL,NULL,''),

(1238849577246834689,1,'北京市',0,1,'0','0','2020-03-14 23:28:51','admin','',NULL,''),

(1239913616460955650,1,'南京市',0,2,'0','0','2020-03-17 21:56:57','admin','',NULL,''),

(1241024269615599618,1,'湖南省',0,3,'0','0','2020-03-20 10:30:18','admin','',NULL,''),

(1241024322249920514,1241024269615599618,'长沙市',0,0,'0','0','2020-03-20 10:30:30','admin','',NULL,''),

(1241024822970126337,1238849577246834689,'朝阳区',0,0,'0','0','2020-03-20 10:32:30','admin','admin','2020-04-09 22:31:59',''),

(1241024879228325889,2,'浦东新区',0,0,'0','0','2020-03-20 10:32:43','admin','admin','2020-04-09 22:32:13',''),

(1241024945540272130,2,'闵行区',0,1,'0','0','2020-03-20 10:32:59','admin','',NULL,''),

(1248260285279739905,1241024269615599618,'永州市',0,1,'0','0','2020-04-09 06:43:38','admin','',NULL,'');

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

/*Data for the table `sys_dict_data` */

insert  into `sys_dict_data`(`dict_code`,`dict_sort`,`dict_label`,`dict_value`,`dict_type`,`css_class`,`del_flag`,`is_sys`,`status`,`css_style`,`list_class`,`is_default`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values 

(1,1,'男','0','sys_user_sex','','0','Y','0','','','Y','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','性别男'),

(2,2,'女','1','sys_user_sex','','0','Y','0','','','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','性别女'),

(3,3,'未知','2','sys_user_sex','','0','Y','0','','','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','性别未知'),

(4,1,'显示','0','sys_show_hide','','0','Y','0','','primary','Y','admin','2018-03-16 11:33:00','admin','2019-12-01 02:34:17','显示菜单'),

(5,2,'隐藏','1','sys_show_hide','','0','Y','0','','danger','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','隐藏菜单'),

(6,1,'正常','0','sys_normal_disable','','0','Y','0','','primary','Y','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),

(7,2,'停用','1','sys_normal_disable','','0','Y','0','','danger','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','停用状态'),

(8,1,'正常','0','sys_job_status','','0','Y','0','','primary','Y','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),

(9,2,'暂停','1','sys_job_status','','0','Y','0','','danger','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','停用状态'),

(10,1,'默认','DEFAULT','sys_job_group','','0','Y','0','','','Y','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','默认分组'),

(11,2,'系统','SYSTEM','sys_job_group','','0','Y','0','','','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统分组'),

(12,1,'是','Y','sys_yes_no','','0','Y','0','','primary','Y','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统默认是'),

(13,2,'否','N','sys_yes_no','','0','Y','0','','danger','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统默认否'),

(14,1,'通知','0','sys_notice_type','','0','Y','0','','success','N','admin','2018-03-16 11:33:00','admin','2020-02-25 06:26:20','通知'),

(15,2,'公告','1','sys_notice_type','','0','Y','0','','danger','Y','admin','2018-03-16 11:33:00','admin','2020-02-25 06:26:26','公告'),

(16,1,'发布','0','sys_notice_status','','0','Y','0','','primary','N','admin','2018-03-16 11:33:00','admin','2020-02-26 09:49:07','正常状态'),

(17,2,'未发布','1','sys_notice_status','','0','Y','0','','danger','Y','admin','2018-03-16 11:33:00','admin','2020-02-26 09:49:02','关闭状态'),

(18,1,'新增','1','sys_oper_type','','0','Y','0','','info','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','新增操作'),

(19,2,'修改','2','sys_oper_type','','0','Y','0','','info','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','修改操作'),

(20,3,'删除','3','sys_oper_type','','0','Y','0','','danger','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','删除操作'),

(21,4,'授权','4','sys_oper_type','','0','Y','0','','primary','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','授权操作'),

(22,5,'导出','5','sys_oper_type','','0','Y','0','','warning','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','导出操作'),

(23,6,'导入','6','sys_oper_type','','0','Y','0','','warning','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','导入操作'),

(24,7,'强退','7','sys_oper_type','','0','Y','0','','danger','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','强退操作'),

(25,8,'生成代码','8','sys_oper_type','','0','Y','0','','warning','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','生成操作'),

(26,9,'清空数据','9','sys_oper_type','','0','Y','0','','danger','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','清空操作'),

(27,1,'成功','0','sys_common_status','','0','Y','0','','primary','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),

(28,2,'失败','1','sys_common_status','','0','Y','0','','danger','N','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','停用状态'),

(29,1,'正常','0','sys_user_show_hide',NULL,'0','Y','0','','primary','Y','admin','2019-12-19 17:39:34','',NULL,'用户正常状态'),

(30,2,'禁用','1','sys_user_show_hide','','0','Y','0','','danger','N','admin','2019-12-19 17:40:10','admin','2019-12-01 02:54:23','用户禁用状态'),

(31,1,'地区','0','sys_dept_line',NULL,'0','Y','0','','primary','Y','admin','2019-12-22 13:07:31','',NULL,'地区字典'),

(32,1,'线路','1','sys_dept_line','','0','Y','0','','warning','N','admin','2019-12-22 13:08:02','admin','2019-12-03 22:13:19','线路显示'),

(33,0,'在线','0','sys_monitor_online','','0','Y','0','','success','Y','admin','2019-12-23 16:06:32','admin','2019-12-05 00:31:38','用户在线'),

(34,1,'离线','1','sys_monitor_online',NULL,'0','Y','0','','warning','N','admin','2019-12-23 16:07:07','',NULL,'用户离线'),

(35,0,'成功','00000','sys_login_status',NULL,'0','Y','0','','primary','Y','admin','2019-12-23 17:17:20','',NULL,'登陆成功'),

(36,1,'失败','-1','sys_login_status',NULL,'0','Y','0','','warning','N','admin','2019-12-23 17:17:59','',NULL,'其他失败情况'),

(37,0,'纸币','1','sys_pay_type','label label-primary','0','Y','0','','','Y','admin','2019-12-25 11:04:01','admin','2019-12-30 02:22:06','交易纸币'),

(38,1,'硬币','2','sys_pay_type','label label-success','0','Y','0','','','N','admin','2019-12-25 11:04:38','admin','2019-12-30 02:22:22','硬币类型'),

(39,2,'公交卡','3','sys_pay_type','label label-warning','0','Y','0','','','N','admin','2019-12-25 11:05:11','admin','2019-12-30 02:22:55','公交卡类型'),

(40,3,'微信','4','sys_pay_type','label label-info','0','Y','0','','','N','admin','2019-12-25 11:06:04','admin','2019-12-30 02:23:18','微信类型'),

(41,4,'支付宝','5','sys_pay_type','label label-default','0','Y','0','','','N','admin','2019-12-25 11:06:37','admin','2019-12-30 02:23:33','支付宝类型'),

(42,0,'空闲','0000','sys_bag_status','','0','Y','0','','primary','Y','admin','2019-12-25 16:40:03','admin','2019-12-08 22:52:33','钞袋空闲'),

(43,1,'使用中','0001','sys_bag_status','','0','Y','0','','success','N','admin','2019-12-25 16:41:08','admin','2019-12-08 22:52:44','钞袋使用中'),

(44,2,'清机完成未入胆','0002','sys_bag_status',NULL,'0','Y','0','','warning','N','admin','2019-12-25 16:42:20','',NULL,'钞袋清机完成未入胆'),

(45,3,'钞袋在胆箱中','0003','sys_bag_status',NULL,'0','Y','0','','info','N','admin','2019-12-25 16:42:52','',NULL,'钞袋胆箱中'),

(46,4,'清胆完成未入库','0004','sys_bag_status',NULL,'0','Y','0','','warning','N','admin','2019-12-25 16:43:27','',NULL,'清单完成未入库'),

(47,5,'入库完成,等待处理','0005','sys_bag_status','','0','Y','0','','success','N','admin','2019-12-25 16:43:59','admin','2019-12-08 22:52:22','完成入库，未处理'),

(48,0,'初始密码','0','sys_user_passwordModifySecurityLevel','','0','Y','0','','danger','Y','admin','2019-12-29 17:33:11','admin','2020-01-30 22:46:46','用户密码为初始密码'),

(49,1,'非常弱','1','sys_user_passwordModifySecurityLevel',NULL,'0','Y','0','','danger','N','admin','2019-12-29 17:35:16','',NULL,'密码等级'),

(50,2,'弱','2','sys_user_passwordModifySecurityLevel','','0','Y','0','','danger','N','admin','2019-12-29 17:36:18','admin','2019-12-11 01:50:28','密码等级弱'),

(51,3,'一般','3','sys_user_passwordModifySecurityLevel',NULL,'0','Y','0','','warning','N','admin','2019-12-29 17:37:01','',NULL,'密码等级一般'),

(52,4,'强','4','sys_user_passwordModifySecurityLevel',NULL,'0','Y','0','','success','N','admin','2019-12-29 17:37:54','',NULL,'密码安全等级强'),

(53,5,'非常强','5','sys_user_passwordModifySecurityLevel','','0','Y','0','','success','N','admin','2019-12-29 17:38:25','admin','2019-12-11 01:52:09','密码等级非常强'),

(54,6,'安全','6','sys_user_passwordModifySecurityLevel',NULL,'0','Y','0','','primary','N','admin','2019-12-29 17:39:10','',NULL,'密码等级安全'),

(55,7,'非常安全','7','sys_user_passwordModifySecurityLevel',NULL,'0','Y','0','','primary','N','admin','2019-12-29 17:39:37','',NULL,'密码等级最高级别安全'),

(56,0,'中文(简体)','zh_CN','sys_lang_type','','0','Y','0','','default','Y','admin','2020-01-05 14:39:47','admin','2019-12-17 22:49:13','中文'),

(57,1,'English','en_US','sys_lang_type','','0','Y','0','','default','N','admin','2020-01-05 14:40:32','admin','2020-01-07 20:04:28','英文字体'),

(59,1,'月报表','1','bcs_poi_type','','0','Y','0','','success','N','admin','2020-01-08 10:56:14','',NULL,'月报表'),

(64,0,'主数据库','0','sys_db','','0','Y','0','','primary','Y','admin','2020-01-09 14:23:51','',NULL,'主数据库'),

(66,2,'한국어','ko_KR','sys_lang_type','','0','Y','0','','default','N','admin','2020-01-29 22:47:51','admin','2020-01-08 07:15:59','韩语'),

(67,3,'日本語','ja_JP','sys_lang_type','','0','Y','0','','default','N','admin','2020-01-29 23:11:28','',NULL,'日本语言'),

(68,4,'中文(繁體)','hkzh_TW','sys_lang_type','','0','Y','0','','default','N','admin','2020-01-30 11:19:35','',NULL,'中文简体'),

(69,0,'目录','0','sys_menu_type','label label-success','0','Y','0','','','N','admin','2020-02-01 16:42:21','',NULL,'菜单目录'),

(70,1,'菜单','1','sys_menu_type','label label-primary','0','Y','0','','','Y','admin','2020-02-01 16:44:06','',NULL,'菜单'),

(71,2,'权限','2','sys_menu_type','label label-warning','0','Y','0','','','N','admin','2020-02-01 16:50:27','admin','2020-01-11 22:49:48','权限按钮'),

(72,0,'Tab打开','_tab','sys_menu_target','','0','Y','0','','primary','Y','admin','2020-02-07 10:50:17','admin','2020-01-12 22:10:20','菜单打开已TAB形式打开'),

(73,1,'新窗口','_blank','sys_menu_target','','0','Y','0','','info','N','admin','2020-02-07 10:53:07','admin','2020-01-12 22:10:31','在新窗口打开_blank'),

(74,2,'弹出窗体','_alert','sys_menu_target','','0','Y','0','','warning','N','admin','2020-02-07 10:54:51','admin','2020-01-12 22:10:40','弹出窗口形式'),

(75,3,'全屏','_fullscreen','sys_menu_target','','0','Y','0','','danger','N','admin','2020-02-07 13:30:58','admin','2020-01-12 22:24:11','打开窗体全屏显示'),

(82,0,'默认','default','sys_table_actions_type','','0','N','0','','','Y','admin','2020-02-12 16:44:19','admin','2020-01-16 13:21:34','默认风格'),

(83,1,'图标','icon','sys_table_actions_type','','0','N','0','','','N','admin','2020-02-12 16:51:59','',NULL,'操作按钮图标'),

(84,0,'正常','0','sys_module_status','','0','Y','0','','primary','Y','admin','2020-02-14 19:39:35','admin','2020-01-17 16:12:38','正常'),

(85,1,'停用','1','sys_module_status','','0','Y','0','','danger','N','admin','2020-02-14 19:40:24','admin','2020-01-20 23:52:10',''),

(86,2,'未安装','2','sys_module_status','','0','Y','0','','danger','N','admin','2020-02-14 19:41:01','',NULL,NULL),

(87,0,'高层','1','sys_post_type','','0','Y','0','','','N','admin','2020-02-27 17:41:47','',NULL,NULL),

(88,1,'中层','2','sys_post_type','','0','Y','0','','','N','admin','2020-02-27 17:42:20','admin','2020-01-29 23:48:22',''),

(89,2,'基层','3','sys_post_type','','0','Y','0','','','Y','admin','2020-02-27 17:42:52','',NULL,NULL),

(90,3,'其他','4','sys_post_type','','0','Y','0','','','N','admin','2020-02-27 17:44:11','',NULL,NULL),

(91,0,'正常','0','sys_status','','0','Y','0','','primary','Y','admin','2020-02-27 18:04:01','',NULL,'正常'),

(92,1,'停用','1','sys_status','','0','Y','0','','danger','N','admin','2020-02-27 18:04:21','admin','2020-01-30 00:09:54',''),

(1243048136005525506,3,'失效','2','sys_notice_status','','0','Y','0','','warning','N','admin','2020-03-25 21:32:25','admin','2020-02-25 06:42:17','失效状态'),

(1243462158207123457,0,'普通','0','sys_notice_level','','0','Y','0','','primary','Y','admin','2020-03-27 16:57:36','',NULL,'普通等级'),

(1243462260573306881,1,'紧急','1','sys_notice_level','','0','Y','0','','warning','N','admin','2020-03-27 16:58:00','admin','2020-02-26 10:07:36','等级紧急'),

(1243462373609799681,2,'严重','2','sys_notice_level','','0','Y','0','','danger','N','admin','2020-03-27 16:58:27','',NULL,'等级严重'),

(1246694957642915842,0,'Admin-LTE','Admin-LTE','sys_login_view','','0','Y','0','','','N','admin','2020-04-04 23:03:35','admin','2020-04-06 16:58:43',''),

(1246695157451169794,1,'高仿码云','Gitee','sys_login_view','','0','Y','0','','','Y','admin','2020-04-04 23:04:23','admin','2020-03-05 14:57:16',''),

(1246999804745269250,2,'高仿OsChina','OsChina','sys_login_view','','0','Y','0','','','N','admin','2020-04-05 19:14:56','',NULL,'高仿OsChina'),

(1247074141611454465,3,'蓝色科技','Blue-Science','sys_login_view','','0','Y','0','','','N','admin','2020-04-06 00:10:20','',NULL,'仿百度登陆'),

(1250243358410100738,0,'默认','DEFAULT','sys_db_encrypt_type','','0','Y','0','','info','Y','admin','2020-04-14 18:03:40','',NULL,'默认不加密明文存储'),

(1250243543534096386,1,'国密','SM4','sys_db_encrypt_type','','0','Y','0','','danger','N','admin','2020-04-14 18:04:24','admin','2020-04-14 18:06:47','国密算法加密'),

(1250243880370262017,2,'DES','DES','sys_db_encrypt_type','','0','Y','0','','warning','N','admin','2020-04-14 18:05:44','',NULL,'DES算法加密'),

(1250244110092292098,3,'ENC','ENC','sys_db_encrypt_type','','0','Y','0','','success','N','admin','2020-04-14 18:06:39','admin','2020-04-14 18:06:57','ENC算法加密'),

(1250425838756929537,0,'Mysql','com.mysql.cj.jdbc.Driver','sys_db_driver_type','','0','Y','0','','primary','Y','admin','2020-04-15 06:08:46','',NULL,''),

(1250426132769251329,1,'Oracle','oracle.jdbc.OracleDriver','sys_db_driver_type','','0','Y','0','','success','N','admin','2020-04-15 06:09:57','',NULL,''),

(1250426268857638913,2,'Sql Server','net.sourceforge.jtds.jdbc.Driver','sys_db_driver_type','','0','Y','0','','info','N','admin','2020-04-15 06:10:29','',NULL,''),

(1250426387174760450,3,'Postgre Sql','org.postgresql.Driver','sys_db_driver_type','','0','Y','0','','success','N','admin','2020-04-15 06:10:57','',NULL,''),

(1251483713247899650,0,'办公流程','office','sys_bpm_category','','0','Y','0','','success','Y','admin','2020-04-18 04:12:23','admin','2020-04-19 01:34:03',''),

(1251485000723066882,1,'项目管理','project','sys_bpm_category','','0','Y','0','','info','N','admin','2020-04-18 04:17:30','',NULL,''),

(1251775715659202562,0,'激活','1','sys_bpm_status','','0','Y','0','','primary','N','admin','2020-04-18 23:32:42','admin','2020-04-21 18:00:56',''),

(1251776027023360002,1,'挂起','2','sys_bpm_status','','0','Y','0','color:red','danger','N','admin','2020-04-18 23:33:56','admin','2020-04-19 01:28:32',''),

(1252216192816517122,0,'事假','0','sys_leave_type','','0','Y','0','','primary','Y','admin','2020-04-20 04:43:00','admin','2020-04-20 04:43:10',''),

(1252216336798584834,1,'病假','1','sys_leave_type','','0','Y','0','','success','N','admin','2020-04-20 04:43:34','',NULL,''),

(1252216431539523586,2,'婚嫁','2','sys_leave_type','','0','Y','0','','info','N','admin','2020-04-20 04:43:57','',NULL,''),

(1252216526460817409,3,'丧假','3','sys_leave_type','','0','Y','0','','warning','N','admin','2020-04-20 04:44:20','admin','2020-04-20 04:44:49',''),

(1252216821798539265,4,'产假','4','sys_leave_type','','0','Y','0','','danger','N','admin','2020-04-20 04:45:30','',NULL,''),

(1252216888336977921,5,'其他','5','sys_leave_type','','0','Y','0','','default','N','admin','2020-04-20 04:45:46','',NULL,''),

(1254031958289395713,0,'一般','50','sys_bpm_priority','','0','Y','0','','success','Y','admin','2020-04-25 04:58:12','',NULL,''),

(1254032043152748546,1,'重要','100','sys_bpm_priority','','0','Y','0','','warning','N','admin','2020-04-25 04:58:33','',NULL,''),

(1254032114871152641,2,'紧急','150','sys_bpm_priority','','0','Y','0','','danger','N','admin','2020-04-25 04:58:50','',NULL,''),

(1254646390153490434,0,'结束','0','sys_bpm_ins_status','','0','Y','0','','info','N','admin','2020-04-26 21:39:44','admin','2020-04-26 21:40:44',''),

(1254646515491876865,1,'进行中','1','sys_bpm_ins_status','','0','Y','0','','danger','Y','admin','2020-04-26 21:40:14','admin','2020-04-26 21:40:49',''),

(1254761798395215873,0,'弹出窗体','0','sys_bpm_from_type','','0','Y','0','','default','Y','admin','2020-04-27 05:18:20','',NULL,''),

(1254761912262180866,1,'TAB打开','1','sys_bpm_from_type','','0','Y','0','','','Y','admin','2020-04-27 05:18:47','',NULL,'');

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

/*Data for the table `sys_dict_type` */

insert  into `sys_dict_type`(`dict_id`,`dict_name`,`dict_type`,`is_sys`,`del_flag`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values 

(1106120265689055233,'状态','sys_status','Y','0','0','admin','2020-02-27 18:03:27','',NULL,NULL),

(1106120532442595330,'用户性别','sys_user_sex','Y','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','用户性别列表'),

(1106120574163337218,'菜单状态','sys_show_hide','Y','0','0','admin','2018-03-16 11:33:00','admin','2019-11-29 22:17:20','菜单状态列表'),

(1106120645697191938,'系统开关','sys_normal_disable','Y','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统开关列表'),

(1106120699468169217,'任务状态','sys_job_status','Y','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','任务状态列表'),

(1106120784318939137,'任务分组','sys_job_group','Y','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','任务分组列表'),

(1106120825993543682,'系统是否','sys_yes_no','Y','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统是否列表'),

(1106120875872206849,'公告类型','sys_notice_type','Y','0','0','admin','2018-03-16 11:33:00','admin','2020-02-25 06:24:58','通知类型列表'),

(1106120935070613505,'公告状态','sys_notice_status','Y','0','0','admin','2018-03-16 11:33:00','admin','2020-02-25 06:25:08','通知状态列表'),

(1106120968910258177,'操作类型','sys_oper_type','Y','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','操作类型列表'),

(1149218674746355713,'系统状态','sys_common_status','Y','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','登录状态列表'),

(1160532775455047681,'模块状态标志','sys_module_status','Y','0','0','admin','2020-02-14 18:49:20','',NULL,'模块状态标志'),

(1160532886713155585,'岗位分类','sys_post_type','Y','0','0','admin','2020-02-27 17:40:05','',NULL,'岗位分类'),

(1160533264645111810,'用户状态0','sys_user_show_hide','Y','0','0','admin','2019-12-19 17:38:33','admin','2019-12-01 23:49:00','用户状态列表'),

(1160533377727741954,'地区线路','sys_dept_line','Y','0','0','admin','2019-12-22 12:57:47','',NULL,'地区线路标志'),

(1160533455343337474,'用户监控','sys_monitor_online','Y','0','0','admin','2019-12-23 15:52:34','',NULL,'用户监控状态'),

(1160533574843252737,'登陆状态','sys_login_status','Y','0','0','admin','2019-12-23 17:15:43','',NULL,'系统登陆状态'),

(1160533625615302658,'密码安全策略','sys_user_passwordModifySecurityLevel','Y','0','0','admin','2019-12-29 17:30:21','',NULL,'密码安全等级'),

(1160533707215486977,'国际化语言类型','sys_lang_type','Y','0','0','admin','2020-01-05 14:15:33','admin','2020-04-09 18:11:12','国际化语言'),

(1160533765403066370,'数据库类型','sys_db','Y','0','0','admin','2020-01-09 14:21:58','admin','2020-01-02 08:27:14','数据库类型'),

(1160533863834992641,'菜单类型','sys_menu_type','Y','0','0','admin','2020-02-01 16:39:17','',NULL,'菜单类型'),

(1160533945309347841,'菜单目标','sys_menu_target','Y','0','0','admin','2020-02-07 10:46:07','',NULL,'左侧菜单打开呈现方式'),

(1160534007389241346,'表格操作风格','sys_table_actions_type','Y','0','0','admin','2020-02-12 16:43:07','admin','2020-01-16 23:41:47','菜单表格操作按钮风格'),

(1243461975188668417,'公告等级','sys_notice_level','Y','0','0','admin','2020-03-27 16:56:52','',NULL,'公告等级 等级 0 普通 1紧急 2严重'),

(1246694124452814850,'登录页面','sys_login_view','Y','0','0','admin','2020-04-04 23:00:16','',NULL,'登录页面视图风格'),

(1250242290494164994,'多源数据加密类型','sys_db_encrypt_type','Y','0','0','admin','2020-04-14 17:59:25','admin','2020-04-14 18:02:38','针对数据库连接中敏感字段加密(连接账号、密码、url)'),

(1250425576013144065,'驱动类型','sys_db_driver_type','Y','0','0','admin','2020-04-15 06:07:44','',NULL,'数据库驱动类型'),

(1251483362960601089,'流程分类','sys_bpm_category','Y','0','0','admin','2020-04-18 04:11:00','admin','2020-04-19 01:33:04','业务流程分类'),

(1251775275764793345,'业务流程状态','sys_bpm_status','Y','0','0','admin','2020-04-18 23:30:57','admin','2020-04-18 23:31:20','业务流程状态'),

(1252214983749668865,'请假类型','sys_leave_type','Y','0','0','admin','2020-04-20 04:38:12','',NULL,''),

(1254031180950646785,'任务优先级','sys_bpm_priority','Y','0','0','admin','2020-04-25 04:55:07','',NULL,'流程任务任务优先级'),

(1254646232007258113,'流程实例状态','sys_bpm_ins_status','Y','0','0','admin','2020-04-26 21:39:07','',NULL,'进行中，结束'),

(1254761249419542530,'流程表单形式','sys_bpm_from_type','Y','0','0','admin','2020-04-27 05:16:09','',NULL,'流程表单展现形式');

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

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`menu_id`,`parent_id`,`name`,`url`,`module_codes`,`target`,`perms`,`type`,`icon`,`order_num`,`hide`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values 

(1,0,'系统设置',NULL,'core,bcs','','',0,'fa fa-desktop',0,0,'',NULL,'admin','2020-01-30 21:53:30',''),

(2,1,'用户管理','sys/user','core','','sys:user:view',1,'fa fa-user',2,0,'',NULL,'admin','2019-12-11 22:07:21',''),

(3,1,'角色管理','sys/role','core','','sys:role:view',1,'fa fa-user-secret',3,0,'',NULL,'admin','2019-12-11 22:08:30',''),

(15,2,'查看',NULL,'core','','sys:user:list,sys:user:info',2,NULL,0,0,'',NULL,'',NULL,''),

(16,2,'新增',NULL,'core','','sys:user:add,sys:dept:select',2,NULL,0,0,'',NULL,'admin','2020-02-01 21:18:34',''),

(17,2,'修改',NULL,'core','','sys:user:edit',2,NULL,0,0,'',NULL,'',NULL,''),

(18,2,'删除',NULL,'core','','sys:user:del',2,NULL,0,0,'',NULL,'',NULL,''),

(19,3,'查看',NULL,'core','','sys:role:list,sys:role:info',2,NULL,0,0,'',NULL,'',NULL,''),

(20,3,'新增','#','core','','sys:role:add',2,NULL,0,0,'',NULL,'admin','2019-12-05 02:02:27',''),

(21,3,'修改','#','core','','sys:role:edit',2,NULL,0,0,'',NULL,'admin','2019-12-05 02:02:45',''),

(22,3,'删除','#','core','','sys:role:del',2,NULL,0,0,'',NULL,'admin','2019-12-05 02:03:07',''),

(41,1,'公司管理','sys/comp','core','','sys:comp:view',1,'fa fa-bars',0,0,'',NULL,'admin','2019-12-02 02:12:51',''),

(42,41,'新增','#','core','','sys:comp:add',2,NULL,1,0,'',NULL,'admin','2019-12-03 08:25:44',''),

(43,41,'查看',NULL,'core','','sys:comp:list',2,NULL,0,0,'',NULL,'',NULL,''),

(44,41,'修改','#','core','','sys:comp:edit',2,NULL,0,0,'',NULL,'admin','2019-12-03 08:24:53',''),

(45,41,'删除','#','core','','sys:comp:del',2,NULL,0,0,'',NULL,'admin','2019-12-03 08:25:05',''),

(46,0,'设备管理',NULL,'bcs','','',0,'fa fa-cogs',4,0,'',NULL,'admin','2020-01-18 06:33:56',''),

(47,46,'终端管理','bcs/mach','bcs','_tab','bcs:mach:view',1,'fa fa-bus',0,0,'',NULL,'admin','2020-02-04 21:12:35',''),

(48,47,'新增',NULL,'bcs','','bcs:mach:add',2,NULL,0,0,'',NULL,'admin','2020-02-04 21:37:54',''),

(49,47,'修改',NULL,'bcs','','bcs:mach:edit',2,NULL,0,0,'',NULL,'admin','2020-02-04 21:38:30',''),

(50,47,'查看',NULL,'bcs','','tbc:mach:list',2,NULL,0,0,'',NULL,'admin','2020-01-20 06:56:37',''),

(59,0,'交易管理',NULL,'bcs','','',0,'fa fa-bar-chart',5,0,'',NULL,'admin','2020-01-26 15:04:44',''),

(60,59,'所有交易','tbc/trad','bcs','_tab','tbc:trad:view',1,'fa fa-bar-chart',1,0,'',NULL,'admin','2020-01-26 15:05:02',''),

(61,59,'实时交易','tbc/redltime','bcs','_tab','tbc:reltime:view',1,'fa fa-building',0,0,'',NULL,'admin','2020-01-26 15:04:54',''),

(62,59,'历史交易','tbc/odltrad','bcs','_tab','tbc:oldtrad:view',1,'fa fa-area-chart',2,0,'',NULL,'admin','2020-01-26 15:05:09',''),

(63,62,'查询','#','bcs','','tbc:oldtrad:list',2,NULL,0,0,'',NULL,'admin','2020-01-26 15:06:00',''),

(64,62,'查询明细','#','bcs','','tbc:oldtrad:detail',2,NULL,0,0,'',NULL,'admin','2020-01-26 15:06:17',''),

(65,61,'查询','#','bcs','','tbc:redltime:list',2,NULL,0,0,'',NULL,'admin','2020-01-26 15:05:28',''),

(66,61,'明细查看','#','bcs','','tbc:reltime:detail,tbc:redltime:list',2,NULL,0,0,'',NULL,'admin','2020-01-26 15:05:37',''),

(67,60,'查询',NULL,'bcs','','tbc:trad:list',2,NULL,0,0,'',NULL,'admin','2020-01-26 15:05:49',''),

(68,0,'监控管理',NULL,'core,bcs','','',0,'fa fa-eye',3,0,'',NULL,'admin','2020-01-26 15:07:39',''),

(70,0,'系统工具',NULL,'core,bcs','','',0,'fa fa-windows',1,0,'',NULL,'admin','2020-01-20 06:58:58',''),

(72,70,'定时任务','sys/schedule','core','_tab','sys:job:view',1,'fa fa-tasks',8,0,'',NULL,'admin','2020-02-08 17:26:42',''),

(73,70,'参数管理','sys/config','core','','sys:config:view',1,'fa fa-sun-o',6,0,'',NULL,'admin','2019-12-05 18:44:58',''),

(76,70,'文件上传','modules/oss/tss.html','core','_tab','sys:tss:list',1,'fa fa-cloud-upload',5,1,'',NULL,'admin','2020-02-13 09:08:06',''),

(77,70,'字典管理','sys/dict','core','','sys:dict:view',1,'fa fa-bookmark-o',7,0,'',NULL,'',NULL,''),

(78,72,'查看','#','core','','sys:job:list',2,NULL,0,0,'',NULL,'admin','2020-02-08 17:26:54',''),

(79,72,'新增','#','core','','sys:job:add',2,NULL,0,0,'',NULL,'admin','2020-02-08 17:27:09',''),

(80,72,'修改','#','core','','sys:job:edit',2,NULL,0,0,'',NULL,'admin','2020-02-08 17:27:33',''),

(81,72,'删除','#','core','','sys:job:del',2,NULL,0,0,'',NULL,'admin','2020-02-08 17:27:43',''),

(82,72,'状态','#','core','','sys:job:changeStatus',2,NULL,0,0,'',NULL,'admin','2020-02-08 17:29:55',''),

(83,72,'恢复',NULL,'core','','sys:job:resume',2,NULL,0,0,'',NULL,'admin','2020-02-08 17:30:08',''),

(84,72,'执行',NULL,'core','','sys:job:run',2,NULL,0,0,'',NULL,'admin','2020-02-08 17:30:26',''),

(85,72,'日志','#','core','','sys:job:loglist',2,NULL,0,0,'',NULL,'admin','2020-02-08 20:34:02',''),

(86,77,'查看',NULL,'core','','sys:dict:list,sys:dict:info',2,NULL,0,0,'',NULL,'',NULL,''),

(87,77,'新增',NULL,'core','','sys:dict:add',2,NULL,0,0,'',NULL,'',NULL,''),

(88,77,'修改',NULL,'core','','sys:dict:edit',2,NULL,0,0,'',NULL,'',NULL,''),

(89,77,'删除',NULL,'core','','sys:dict:del',2,NULL,0,0,'',NULL,'',NULL,''),

(94,70,'菜单管理','sys/menu','core','','sys:menu:view',1,'fa fa-th-list',1,0,'',NULL,'admin','2019-12-04 05:24:44',''),

(96,94,'新增',NULL,'core','','sys:menu:add',2,NULL,0,0,'',NULL,'',NULL,''),

(97,94,'修改',NULL,'core','','sys:menu:edit',2,NULL,0,0,'',NULL,'',NULL,''),

(98,94,'删除','#','core','','sys:menu:del',2,NULL,0,0,'',NULL,'admin','2019-12-03 06:21:51',''),

(99,68,'用户监控','sys/online','core','','sys:online:view',1,'fa fa-user-circle',1,0,'',NULL,'admin','2019-12-05 02:41:18',''),

(101,99,'强退','#','core','','sys:online:forceLogout',2,NULL,0,0,'',NULL,'admin','2019-12-05 02:42:00',''),

(102,0,'库房管理','#','bcs','','',0,'fa fa-university',6,0,'',NULL,'admin','2020-01-26 15:01:02',''),

(103,68,'设备监控','modules/tbc/monitor.html','bcs','_tab','',1,'fa fa-heartbeat',0,0,'',NULL,'admin','2020-01-26 15:07:52',''),

(104,46,'故障处理','modules/tbc/breakdown.html','bcs','_tab','tbc:breakdown:list',1,'fa fa-wrench',2,0,'',NULL,'admin','2020-01-18 06:34:38',''),

(106,102,'入库情况','tbc/put','bcs','_tab','tbc:put:view',1,'fa fa-truck',1,0,'',NULL,'admin','2020-01-26 15:01:46',''),

(107,102,'处理情况','modules/warehouse/dispose.html','bcs','_tab','',1,'fa fa-pencil',2,0,'',NULL,'admin','2020-01-26 15:01:54',''),

(108,0,'报表管理',NULL,'core','','',0,'fa fa-file-excel-o',7,1,'',NULL,'admin','2020-02-13 09:12:41',''),

(109,108,'报表','sys/poi','core','','sys:poi:view',1,'fa fa-file',0,0,'',NULL,'admin','2019-12-20 17:56:48',''),

(110,108,'月报表','#','core','','',1,'fa fa-file',1,1,'',NULL,'',NULL,''),

(111,108,'年报表','#','core','','',1,'fa fa-file',2,1,'',NULL,'',NULL,''),

(114,0,'数据分析',NULL,'core','','',0,'fa fa-database',8,1,'',NULL,'',NULL,''),

(115,114,'司机上班情况','#','core','_tab','',1,'fa fa-calendar',0,0,'',NULL,'admin','2020-04-23 06:03:55',''),

(118,70,'设备升级','modules/update/update.html','core','_tab','',1,'fa fa-rocket',4,1,'',NULL,'admin','2020-02-13 09:08:23',''),

(119,70,'设备日志','tbc/log','bcs','_tab','tbc:log:view',1,'fa fa-file-text',3,0,'',NULL,'admin','2020-01-29 23:19:00',''),

(120,104,'故障修复',NULL,'bcs','','tbc:breakdown:save',2,NULL,0,0,'',NULL,'admin','2020-01-18 06:35:09',''),

(121,106,'查看','#','bcs','','tbc:put:list',2,NULL,0,0,'',NULL,'admin','2020-01-26 15:03:02',''),

(122,106,'入库处理权限','#','bcs','','tbc:put:dispose',2,NULL,0,0,'',NULL,'admin','2020-01-26 15:03:13',''),

(123,107,'查询权限',NULL,'bcs','','tbc:waredispose:list',2,NULL,0,0,'',NULL,'admin','2020-01-26 15:03:32',''),

(124,107,'查询用户',NULL,'bcs','','tbc:warehouse:user',2,NULL,0,0,'',NULL,'admin','2020-01-26 15:03:47',''),

(125,76,'云参数配置',NULL,'core','','sys:tss:all',2,NULL,0,0,'',NULL,'',NULL,''),

(126,76,'文件上传按钮',NULL,'core','','sys:tss:upload',2,NULL,0,0,'',NULL,'',NULL,''),

(127,76,'文件删除',NULL,'core','','sys:tss:delete',2,NULL,0,0,'',NULL,'',NULL,''),

(128,118,'软件升级',NULL,'core','','sys:tbc:mach:update',2,NULL,0,0,'',NULL,'',NULL,''),

(129,68,'服务监控','sys/server','core','_tab','sys:server:view',1,'fa fa-server',3,0,'',NULL,'admin','2020-03-15 16:39:00',''),

(130,0,'日志管理','#','core','','',0,'fa fa-pencil-square-o',2,0,'','2020-01-12 22:13:35','admin','2020-04-23 06:03:37',''),

(131,130,'操作日志','sys/operlog','core,bcs','_tab','sys:log:view',1,'fa fa-file-image-o',0,0,'',NULL,'admin','2020-01-20 06:59:08',''),

(132,130,'登陆日志','sys/logininfo','core,bcs','_tab','sys:logininfo:view',1,'fa fa-address-book',0,0,'',NULL,'admin','2020-01-20 06:59:14',''),

(133,47,'设备查询管理人员',NULL,'bcs','','tbc:mach:mgaelist',2,NULL,0,0,'',NULL,'admin','2020-01-20 06:57:52',''),

(134,47,'删除权限',NULL,'bcs','','bcs:mach:del',2,NULL,0,0,'',NULL,'admin','2020-02-04 21:39:23',''),

(135,70,'支付设置','modules/pay/pay.html','core','_tab','sys:pay:info',1,'fa fa-paypal',8,1,'',NULL,'admin','2020-02-13 09:09:21',''),

(136,135,'微信参数修改权限',NULL,'core','','sys:pay:wecahtUpdate',2,NULL,0,0,'',NULL,'',NULL,''),

(137,135,'支付宝参数修改权限',NULL,'core','','sys:pay:alipayUpdate',2,NULL,0,0,'',NULL,'',NULL,''),

(138,102,'钞袋管理','tbc/bag','bcs','_tab','tbc:bag:view',1,'fa fa-shopping-bag',0,0,'',NULL,'admin','2020-01-26 15:01:37',''),

(139,138,'数据显示',NULL,'bcs','','tbc:bag:list',2,NULL,0,0,'',NULL,'admin','2020-01-26 15:02:08',''),

(140,1,'地区管理','sys/dept','core,bcs','_tab','sys:dept:view',1,'fa fa-outdent',1,0,'',NULL,'admin','2020-01-30 22:53:21',''),

(141,140,'新增','#','core,bcs','','sys:dept:add',2,NULL,0,0,'',NULL,'admin','2020-01-30 22:53:35',''),

(142,140,'查看','#','core,bcs','','sys:dept:list',2,NULL,0,0,'',NULL,'admin','2020-01-30 22:53:47',''),

(143,140,'修改','#','core,bcs','','sys:dept:edit',2,NULL,0,0,'',NULL,'admin','2020-01-30 22:53:54',''),

(144,140,'删除','#','core,bcs','','sys:dept:del',2,NULL,0,0,'',NULL,'admin','2020-01-30 22:54:02',''),

(145,109,'下载权限',NULL,'core','','sys:poi:download',2,NULL,0,0,'',NULL,'',NULL,''),

(146,62,'导出报表',NULL,'bcs','','tbc:poi:excel',2,NULL,0,0,'',NULL,'admin','2020-01-26 15:06:30',''),

(147,138,'新增','#','bcs','','tbc:bag:add',2,NULL,0,0,'',NULL,'admin','2020-01-26 15:02:33',''),

(148,138,'修改','#','bcs','','tbc:bag:edit',2,NULL,0,0,'',NULL,'admin','2020-01-26 15:02:42',''),

(149,138,'删除','#','bcs','','tbc:bag:del',2,NULL,0,0,'',NULL,'admin','2020-01-26 15:02:51',''),

(150,59,'交易统计','modules/tbc/graph.html','bcs','_tab','tbc:graph:list',1,'fa fa-file-excel-o',4,1,'',NULL,'admin','2020-01-26 15:05:18',''),

(151,47,'开箱',NULL,'bcs','','tbc:mach:lockopen',2,NULL,0,0,'',NULL,'admin','2020-01-20 06:58:26',''),

(152,46,'胆箱管理','bcs/box','bcs','_tab','bcs:box:view',1,'fa fa-archive',1,0,'',NULL,'admin','2020-02-06 16:14:05',''),

(153,152,'新增',NULL,'bcs','','bcs:box:add',2,NULL,0,0,'',NULL,'admin','2020-02-06 16:11:30',''),

(154,152,'修改',NULL,'bcs','','bcs:box:edit',2,NULL,0,0,'',NULL,'admin','2020-02-06 16:11:48',''),

(155,152,'获取心跳',NULL,'bcs','','tbc:box:info',2,NULL,0,0,'',NULL,'admin','2020-01-20 06:56:26',''),

(156,47,'开锁日志',NULL,'bcs','','tbc:mach:lockinfo',2,NULL,0,0,'',NULL,'admin','2020-01-20 06:58:36',''),

(158,1,'项目测试','gtv/dev','core','_alert','gtv:dev:view',1,'glyphicon glyphicon-copyright-mark',0,1,'',NULL,'admin','2020-04-09 17:44:59',''),

(159,158,'查看','#','core','','gtv:dev:list,gtv:dev:add,gtv:dev:edit,gtv:dev:del',2,NULL,0,0,'',NULL,'admin','2020-04-09 17:44:34',''),

(160,77,'字典查询','','core','','system:dict:list',2,'',0,0,'',NULL,'',NULL,''),

(161,94,'查看','','core','','sys:menu:list',2,'',0,0,'admin','2019-12-22 19:55:13','admin','2019-12-04 05:24:34',''),

(162,131,'查看','','core,bcs','','sys:log:list',2,'',0,0,'admin','2019-12-22 20:44:22','admin','2020-02-03 23:31:03',''),

(163,131,'详细信息','','core,bcs','','sys:log:detail',2,'',0,0,'admin','2019-12-22 21:12:06','admin','2020-02-03 23:31:11',''),

(164,132,'查看','','core,bcs','','sys:logininfo:list',2,'',0,0,'admin','2019-12-23 17:05:09','admin','2020-02-03 23:31:19',''),

(165,132,'删除','','core,bcs','','sys:logininfo:del',2,'',0,0,'admin','2019-12-23 17:06:29','admin','2020-02-03 23:31:26',''),

(166,132,'清空','','core,bcs','','sys:logininfo:clean',2,'',0,0,'admin','2019-12-23 17:06:56','admin','2020-02-03 23:31:33',''),

(167,99,'查看','','core','','sys:online:list',2,'',0,0,'admin','2019-12-23 18:17:46','',NULL,''),

(168,73,'查看','','core','','sys:config:list',2,'',0,0,'admin','2019-12-24 10:20:33','',NULL,''),

(169,73,'新增','','core','','sys:config:add',2,'',0,0,'admin','2019-12-24 10:30:19','',NULL,''),

(170,73,'修改','','core','','sys:config:edit',2,'',0,0,'admin','2019-12-24 10:30:48','admin','2019-12-05 20:08:35',''),

(171,73,'删除','','core','','sys:config:del',2,'',0,0,'admin','2019-12-24 10:32:50','',NULL,''),

(172,106,'详情查看','','bcs','','tbc:put:detail',2,'',0,0,'admin','2019-12-27 15:47:37','admin','2020-01-26 15:03:22',''),

(173,2,'密码重置','','core','','sys:user:resetPwd',2,'',0,0,'admin','2019-12-29 15:03:07','',NULL,''),

(174,0,'研发工具','','core','','',0,'fa fa-code',9,0,'admin','2020-01-19 21:28:55','admin','2019-12-18 23:28:58',''),

(175,174,'代码生成','tool/gen','core','_tab','tool:gen:view',1,'fa fa-terminal',0,0,'admin','2020-01-06 16:47:02','admin','2020-01-16 03:47:14',''),

(176,175,'查看','','core','','tool:gen:list',2,'',0,0,'admin','2020-01-06 16:47:25','',NULL,''),

(177,175,'代码生成','','core','','tool:gen:code',2,'',1,0,'admin','2020-01-07 13:35:58','',NULL,''),

(178,175,'预览','','core','','tool:gen:preview',2,'',2,0,'admin','2020-01-07 14:47:53','',NULL,''),

(179,175,'修改','','core','','tool:gen:edit',2,'',3,0,'admin','2020-01-07 16:21:19','',NULL,''),

(180,109,'查看','','core','','sys:poi:list',2,'',0,0,'admin','2020-01-08 11:11:15','',NULL,''),

(181,175,'删除','','core','','tool:gen:del',2,'',4,0,'admin','2020-01-09 09:21:09','',NULL,''),

(182,72,'详情','','core','','sys:job:detail',2,'',0,0,'admin','2020-01-19 12:09:40','admin','2020-02-08 20:34:46',''),

(183,72,'日志删除','','core','','sys:joblog:del',2,'',0,0,'admin','2020-01-19 16:38:23','',NULL,''),

(184,72,'日志清空','','core','','sys:joblog:clean',2,'',0,0,'admin','2020-01-19 16:38:57','',NULL,''),

(186,174,'图标样式','tool/icon','core','_alert','tool:icon:view',1,'fa fa-hashtag',1,0,'admin','2020-02-13 19:48:15','admin','2020-04-23 06:05:15',''),

(187,119,'查看','','bcs','','tbc:log:list',2,'',0,0,'admin','2020-02-10 17:25:16','admin','2020-01-29 23:19:13',''),

(188,119,'删除','','bcs','','tbc:log:del',2,'',1,0,'admin','2020-02-12 17:39:36','admin','2020-01-29 23:19:23',''),

(189,70,'模块管理','sys/module','core','_tab','sys:module:view',1,'fa fa-cogs',2,0,'admin','2020-02-14 14:20:35','admin','2020-01-30 21:39:09',''),

(190,189,'查看','','core','','sys:module:list',2,'',0,0,'admin','2020-02-14 14:21:13','admin','2020-01-30 21:39:54',''),

(191,189,'新增','','core','','sys:module:add',2,'',1,0,'admin','2020-02-14 14:21:48','admin','2020-01-30 21:40:10',''),

(192,189,'修改','','core','','sys:module:edit',2,'',2,0,'admin','2020-02-14 14:22:16','admin','2020-01-30 21:40:24',''),

(193,189,'删除','','core','','sys:module:del',2,'',3,0,'admin','2020-02-14 14:23:03','admin','2020-01-30 21:40:37',''),

(194,94,'授权','','core','','sys:role:authorization',2,'',5,0,'admin','2020-02-24 17:46:07','',NULL,''),

(195,3,'分配用户','','core','','sys:role:authUserList',2,'',5,0,'admin','2020-02-25 11:01:37','',NULL,''),

(196,2,'授权角色','','core','','sys:user:authRoleList',2,'',6,0,'admin','2020-02-26 10:00:58','',NULL,''),

(197,1,'岗位管理','sys/post','core,bcs','_tab','sys:post:view',1,'fa fa-address-card-o',5,0,'admin','2020-02-28 00:00:00','admin','2020-02-28 00:00:00','岗位信息菜单'),

(198,197,'查询','#','core,bcs','','sys:post:list',2,'',0,0,'admin','2020-02-28 00:00:00','admin','2020-02-28 00:00:00',''),

(199,197,'新增','#','core,bcs','','sys:post:add',2,'',1,0,'admin','2020-02-28 00:00:00','admin','2018-03-01 00:00:00',''),

(200,197,'修改','#','core,bcs','','sys:post:edit',2,'',2,0,'admin','2020-02-28 00:00:00','admin','2018-03-01 00:00:00',''),

(201,197,'删除','#','core,bcs','','sys:post:del',2,'',3,0,'admin','2020-02-28 00:00:00','admin','2020-01-30 21:55:13',''),

(202,1,'员工管理','bcs/user','bcs','_tab','bcs:user:view',1,'fa fa-user-o',5,0,'admin','2020-02-28 17:53:16','admin','2020-01-31 00:08:13',''),

(203,202,'查看','','bcs','','bcs:user:list,sys:dept:select',2,'',0,0,'admin','2020-02-28 18:04:00','admin','2020-02-02 15:02:31',''),

(204,202,'新增','','bcs','','bcs:user:add',2,'',1,0,'admin','2020-03-01 15:23:22','',NULL,''),

(205,202,'修改','','bcs','','bcs:user:edit',2,'',2,0,'admin','2020-03-01 15:23:52','',NULL,''),

(206,202,'删除','','bcs','','bcs:user:del',2,'',3,0,'admin','2020-03-01 15:24:21','',NULL,''),

(207,152,'删除','','bcs','','bcs:box:del',2,'',3,0,'admin','2020-03-06 10:32:42','',NULL,''),

(208,152,'查看','','bcs','','bcs:box:list',2,'',4,0,'admin','2020-03-06 10:35:04','',NULL,''),

(1241023067553239042,70,'产品许可','sys/license','core','_tab','sys:license:view',1,'fa fa-send-o',13,0,'admin','2020-03-20 10:25:31','admin','2020-03-20 23:26:47',''),

(1241023310755762177,1241023067553239042,'上传许可','','core','','sys:license:upload',2,'',0,0,'admin','2020-03-20 10:26:29','',NULL,''),

(1241023877494312961,1241023067553239042,'生成许可','','core','','sys:license:creatLicense',2,'',1,0,'admin','2020-03-20 10:28:44','',NULL,''),

(1241544436850233346,0,'项目帮助','','core','',NULL,0,'fa icon-link',11,0,'admin','2020-03-21 17:57:15','admin','2020-02-21 04:01:22',''),

(1241551431233835010,1241544436850233346,'在线提问','https://gitee.com/zhouhuanOGP/J2EEFAST/issues','core','_tab','',1,'fa fa-question-circle',0,0,'admin','2020-03-21 18:25:03','',NULL,''),

(1241552388105572354,1241544436850233346,'在线文档','https://gitee.com/zhouhuanOGP/J2EEFAST/wikis/pages','core','_alert','',1,'fa fa-wordpress',1,0,'admin','2020-03-21 18:28:51','',NULL,''),

(1243014163867074562,70,'公告管理','sys/notice','core','_tab','sys:notice:view',1,'fa fa-bullhorn',15,0,'admin','2020-03-25 19:17:25','admin','2020-02-25 04:26:40',''),

(1243014380247023618,1243014163867074562,'新增','','core','','sys:notice:add',2,'',0,0,'admin','2020-03-25 19:18:17','',NULL,''),

(1243014490183925762,1243014163867074562,'修改','','core','','sys:notice:edit',2,'',1,0,'admin','2020-03-25 19:18:43','',NULL,''),

(1243014687924387842,1243014163867074562,'删除','','core','','sys:notice:del',2,'',3,0,'admin','2020-03-25 19:19:30','',NULL,''),

(1250359587451895810,68,'SQL监控','druid/index.html','core,core','_tab','',1,'fa fa-bug',2,0,'admin','2019-09-20 01:45:31','admin',NULL,''),

(1250364490287108097,70,'多源数据','sys/database','core','_tab','sys:database:view',1,'fa fa-database',16,0,'admin','2019-09-20 01:45:31','admin',NULL,''),

(1250951041614217217,0,'流程管理','','bpm','_tab','',0,'fa fa-puzzle-piece',50,0,'admin','2019-09-20 01:45:31','admin',NULL,''),

(1250956551021264898,1250951041614217217,'模型设计','bpm/model','bpm','_tab','bpm:model:view',1,'glyphicon glyphicon-leaf',0,0,'admin','2019-09-20 01:45:31','admin',NULL,''),

(1251546760767324162,1250951041614217217,'定义管理','bpm/procdef','bpm','_tab','bpm:procdef:view',1,'fa fa-cubes',1,0,'admin','2019-09-20 01:45:31','admin',NULL,''),

(1251700255260614658,174,'接口文档','doc.html','core','_alert','',1,'fa fa-gg',3,0,'admin','2019-09-20 01:45:31','admin',NULL,''),

(1253348267653218305,0,'我的办公','','bpm','',NULL,0,'fa fa-laptop',18,0,'admin','2020-04-08 07:41:28','',NULL,''),

(1253349238940778498,1253348267653218305,'发起流程','bpm/process','bpm','_tab','bpm:process:view',1,'fa fa-adn',0,0,'admin','2020-04-02 07:45:19','admin','2020-04-27 06:41:05',''),

(1253349975972265985,1253348267653218305,'已办任务','bpm/task/applyed','bpm','_tab','bpm:task:view',1,'fa fa-newspaper-o',10,0,'admin','2020-04-02 07:48:15','admin','2020-04-27 07:59:29',''),

(1253350357083504642,1253348267653218305,'待办任务','bpm/task/applaying','bpm','_tab','bpm:task:view',1,'fa fa-bookmark-o',30,0,'admin','2020-04-02 07:49:46','admin','2020-04-27 07:59:42',''),

(1253521391095074817,1250951041614217217,'实例管理','bpm/processInstance','bpm','_tab','bpm:processInstance:view',1,'fa fa-object-ungroup',4,0,'admin','2020-04-23 19:09:24','',NULL,''),

(1253521499077431298,1253521391095074817,'查看','','bpm','','bpm:processInstance:list',2,'',0,0,'admin','2020-04-23 19:09:49','',NULL,''),

(1253974386131783681,1253350357083504642,'查询','','bpm','','bpm:task:list',2,'',0,0,'admin','2020-04-25 01:09:26','',NULL,''),

(1254065382001725442,1253350357083504642,'办理','','bpm','','bpm:form:edit',2,'',1,0,'admin','2020-04-25 07:11:01','',NULL,''),

(1254249304429821953,1253350357083504642,'审批','','bpm','','bpm:task:approval',2,'',2,0,'admin','2020-04-25 19:21:52','',NULL,''),

(1254607853257482242,1253349975972265985,'查询','','bpm','','bpm:task:list',2,'',0,0,'admin','2020-04-26 19:06:36','',NULL,''),

(1254616704945958914,1253349238940778498,'查看','','bpm','','bpm:process:list',2,'',0,0,'admin','2020-04-26 19:41:47','admin','2020-04-27 06:41:23',''),

(1254802944899129345,1253348267653218305,'我的流程','bpm/process/myProcess','bpm','_tab','bpm:process:view',1,'fa icon-people',3,0,'admin','2020-04-27 08:01:50','',NULL,''),

(1254803046447423490,1254802944899129345,'查询','','bpm','','bpm:process:list',2,'',0,0,'admin','2020-04-27 08:02:14','',NULL,''),

(1255326656635211777,1253349238940778498,'流程查看','','bpm','','bpm:task:comment',2,'',4,0,'admin','2020-04-28 18:42:52','',NULL,'');

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

/*Data for the table `sys_module` */

insert  into `sys_module`(`id`,`module_code`,`module_name`,`description`,`main_class_name`,`icon`,`current_version`,`upgrade_info`,`del_flag`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values 

(1,'core','核心模块','用户、角色、组织、模块、菜单、字典、参数、系统相关功能','com.j2eefast.modules.sys.controller.SysLoginController','fa  icon-settings','2.0.2',NULL,'0','0','admin','2020-02-14 17:31:35','admin','2020-02-14 17:31:35','初始版本'),

(2,'bcs','公交系统','公交项目系统功能 终端、交易、库房等功能','com.lixinos.bcs.common.config.InitializeConfig','fa fa-lastfm','2.0.2',NULL,'0','2','admin','2020-02-14 20:36:37','admin','2020-04-10 02:31:52','初始版本'),

(1250950663929724930,'bpm','业务流程','流程设计器、流程监管控制、流程办理、流程追踪','com.j2eefast.flowable.bpm.controller.DeModelController','fa fa-codepen','1.0.0',NULL,'0','0','admin','2020-04-16 16:54:14','admin','2020-04-28 07:56:39','业务流程');

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

/*Data for the table `sys_post` */

insert  into `sys_post`(`post_id`,`post_code`,`post_name`,`post_sort`,`post_type`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values 

(1,'CEO','董事长',1,'1','0','admin','2018-03-16 11:33:00','admin','2020-02-13 09:10:01',''),

(2,'SE','项目经理',2,'1','0','admin','2018-03-16 11:33:00','admin','2020-02-13 09:10:07',''),

(3,'HR','人力资源',3,'1','0','admin','2018-03-16 11:33:00','admin','2020-02-13 09:10:13',''),

(4,'USER','普通员工',4,'3','0','admin','2018-03-16 11:33:00','admin','2020-04-09 17:44:11','');

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

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`role_name`,`dept_id`,`create_time`,`role_key`,`data_scope`,`role_sort`,`del_flag`,`status`,`create_by`,`update_by`,`update_time`,`remark`) values 

(1,'最大管理员',1,'2018-06-02 21:35:28','ADMIN',NULL,0,'0','0','','admin','2020-04-08 22:18:05','最高级别管理员'),

(2,'公司管理角色',3,'2018-11-17 21:02:35','COMP',NULL,0,'0','0','','admin','2020-04-28 18:43:31','公司权限管理'),

(3,'地区管理用户',NULL,'2018-12-05 17:27:37','DEPT',NULL,0,'0','0','','admin','2020-04-28 18:43:40','管理地区终端'),

(4,'终端管理角色',NULL,'2018-12-07 18:51:34','MACH',NULL,0,'0','0','','admin','2020-04-28 18:43:57','终端限角色'),

(6,'公交司机',NULL,'2018-12-26 15:29:13','SIJI',NULL,0,'0','0','','admin','2020-04-28 18:44:06','公交司机'),

(7,'库房管理',NULL,'2019-04-25 09:29:15','KUFANG',NULL,0,'0','0','','admin','2020-04-28 18:44:29','库房管理'),

(8,'测试角色',NULL,'2020-02-17 14:46:42','TEST',NULL,0,'0','0','admin','admin','2020-04-28 22:49:38','测试角色'),

(1255020745534443521,'部门经理',NULL,'2020-04-27 22:27:18','DEPT','1',0,'0','0','admin','admin','2020-04-28 18:44:54',''),

(1255022318142590978,'人事经理',NULL,'2020-04-27 22:33:32','PER','1',0,'0','0','admin','admin','2020-04-28 18:45:18','');

/*Table structure for table `sys_role_dept` */

DROP TABLE IF EXISTS `sys_role_dept`;

CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色与部门对应关系';

/*Data for the table `sys_role_dept` */

insert  into `sys_role_dept`(`id`,`role_id`,`dept_id`) values 

(230,2,3),

(231,2,4),

(232,2,5),

(233,1,2),

(234,1,3),

(235,1,4),

(236,1,5),

(237,1,7),

(238,1,8),

(239,1,9);

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色与菜单对应关系';

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`id`,`role_id`,`menu_id`) values 

(1243849569432977409,1,1),

(1243849569445560321,1,158),

(1243849569445560322,1,159),

(1243849569445560323,1,41),

(1243849569445560324,1,45),

(1243849569453948929,1,44),

(1243849569453948930,1,43),

(1243849569453948931,1,42),

(1243849569453948932,1,140),

(1243849569462337538,1,142),

(1243849569462337539,1,141),

(1243849569462337540,1,144),

(1243849569462337541,1,143),

(1243849569462337542,1,2),

(1243849569462337543,1,15),

(1243849569462337544,1,173),

(1243849569470726145,1,18),

(1243849569470726146,1,17),

(1243849569470726147,1,16),

(1243849569470726148,1,196),

(1243849569479114754,1,3),

(1243849569479114755,1,21),

(1243849569479114756,1,20),

(1243849569479114757,1,19),

(1243849569479114758,1,22),

(1243849569479114759,1,195),

(1243849569479114760,1,197),

(1243849569487503361,1,198),

(1243849569487503362,1,199),

(1243849569491697666,1,200),

(1243849569491697667,1,201),

(1243849569491697668,1,70),

(1243849569491697669,1,94),

(1243849569491697670,1,96),

(1243849569491697671,1,98),

(1243849569500086274,1,97),

(1243849569500086275,1,161),

(1243849569500086276,1,194),

(1243849569500086277,1,189),

(1243849569504280578,1,190),

(1243849569504280579,1,191),

(1243849569504280580,1,192),

(1243849569504280581,1,193),

(1243849569504280582,1,118),

(1243849569504280583,1,128),

(1243849569504280584,1,76),

(1243849569512669186,1,125),

(1243849569512669187,1,127),

(1243849569512669188,1,126),

(1243849569512669189,1,73),

(1243849569512669190,1,168),

(1243849569512669191,1,171),

(1243849569516863490,1,170),

(1243849569516863491,1,169),

(1243849569516863492,1,77),

(1243849569516863493,1,160),

(1243849569516863494,1,89),

(1243849569516863495,1,88),

(1243849569516863496,1,87),

(1243849569516863497,1,86),

(1243849569516863498,1,72),

(1243849569516863499,1,182),

(1243849569516863500,1,85),

(1243849569525252098,1,84),

(1243849569525252099,1,83),

(1243849569525252100,1,82),

(1243849569525252101,1,81),

(1243849569525252102,1,184),

(1243849569525252103,1,80),

(1243849569525252104,1,183),

(1243849569525252105,1,78),

(1243849569525252106,1,79),

(1243849569533640706,1,135),

(1243849569533640707,1,137),

(1243849569533640708,1,136),

(1243849569533640709,1,1241023067553239042),

(1243849569533640710,1,1241023310755762177),

(1243849569533640711,1,1241023877494312961),

(1243849569533640712,1,1243014163867074562),

(1243849569533640713,1,1243014380247023618),

(1243849569533640714,1,1243014490183925762),

(1243849569542029313,1,1243014687924387842),

(1243849569542029314,1,130),

(1243849569542029315,1,131),

(1243849569542029316,1,162),

(1243849569542029317,1,163),

(1243849569542029318,1,132),

(1243849569542029319,1,166),

(1243849569542029320,1,165),

(1243849569542029321,1,164),

(1243849569542029322,1,68),

(1243849569542029323,1,99),

(1243849569550417921,1,167),

(1243849569550417922,1,101),

(1243849569550417923,1,69),

(1243849569550417924,1,129),

(1243849569550417925,1,108),

(1243849569550417926,1,109),

(1243849569550417927,1,180),

(1243849569558806530,1,145),

(1243849569558806531,1,110),

(1243849569558806532,1,111),

(1243849569558806533,1,112),

(1243849569558806534,1,114),

(1243849569558806535,1,115),

(1243849569558806536,1,116),

(1243849569567195137,1,174),

(1243849569567195138,1,175),

(1243849569567195139,1,176),

(1243849569567195140,1,177),

(1243849569567195141,1,178),

(1243849569567195142,1,179),

(1243849569567195143,1,181),

(1243849569567195144,1,186),

(1243849569567195145,1,1241544436850233346),

(1243849569567195146,1,1241551431233835010),

(1243849569575583745,1,1241552388105572354),

(1255326816421416962,2,1),

(1255326816429805569,2,41),

(1255326816438194177,2,44),

(1255326816446582786,2,43),

(1255326816459165697,2,140),

(1255326816475942913,2,144),

(1255326816484331522,2,143),

(1255326816492720130,2,142),

(1255326816496914433,2,141),

(1255326816505303042,2,2),

(1255326816513691650,2,16),

(1255326816530468865,2,15),

(1255326816538857474,2,18),

(1255326816547246082,2,17),

(1255326816555634689,2,70),

(1255326816568217601,2,72),

(1255326816576606209,2,79),

(1255326816584994818,2,80),

(1255326816597577730,2,78),

(1255326816601772034,2,85),

(1255326816618549250,2,84),

(1255326816622743554,2,83),

(1255326816631132161,2,182),

(1255326816639520769,2,82),

(1255326816647909378,2,81),

(1255326816660492289,2,130),

(1255326816668880897,2,132),

(1255326816681463810,2,164),

(1255326816694046721,2,68),

(1255326816702435330,2,99),

(1255326816702435331,2,101),

(1255326816715018241,2,108),

(1255326816723406849,2,109),

(1255326816735989762,2,110),

(1255326816744378369,2,111),

(1255326816756961282,2,1253348267653218305),

(1255326816765349890,2,1253349238940778498),

(1255326816773738498,2,1254616704945958914),

(1255326816777932801,2,1255326656635211777),

(1255326816794710018,2,1254802944899129345),

(1255326816798904322,2,1254803046447423490),

(1255326816815681538,2,1253349975972265985),

(1255326816819875842,2,1254607853257482242),

(1255326816828264449,2,1253350357083504642),

(1255326816828264450,2,1253974386131783681),

(1255326816840847362,2,1254065382001725442),

(1255326816840847363,2,1254249304429821953),

(1255326856820953089,3,1),

(1255326856841924609,3,140),

(1255326856841924610,3,144),

(1255326856850313218,3,143),

(1255326856858701826,3,142),

(1255326856867090434,3,141),

(1255326856888061954,3,2),

(1255326856900644866,3,16),

(1255326856900644867,3,15),

(1255326856913227777,3,18),

(1255326856921616385,3,17),

(1255326856930004994,3,3),

(1255326856942587906,3,22),

(1255326856959365122,3,21),

(1255326856967753730,3,20),

(1255326856976142337,3,19),

(1255326856984530945,3,130),

(1255326856992919553,3,132),

(1255326857001308161,3,131),

(1255326857013891074,3,68),

(1255326857034862594,3,99),

(1255326857043251202,3,101),

(1255326857047445506,3,1253348267653218305),

(1255326857060028418,3,1253349238940778498),

(1255326857064222721,3,1254616704945958914),

(1255326857072611330,3,1255326656635211777),

(1255326857072611331,3,1254802944899129345),

(1255326857097777154,3,1254803046447423490),

(1255326857106165762,3,1253349975972265985),

(1255326857118748673,3,1254607853257482242),

(1255326857122942977,3,1253350357083504642),

(1255326857131331585,3,1253974386131783681),

(1255326857135525889,3,1254065382001725442),

(1255326857143914497,3,1254249304429821953),

(1255326926970687490,4,130),

(1255326926987464705,4,131),

(1255326926995853314,4,68),

(1255326927004241922,4,108),

(1255326927012630530,4,109),

(1255326927025213442,4,110),

(1255326927041990658,4,111),

(1255326927054573569,4,1253348267653218305),

(1255326927067156482,4,1253349238940778498),

(1255326927075545089,4,1254616704945958914),

(1255326927088128001,4,1255326656635211777),

(1255326927096516610,4,1254802944899129345),

(1255326927104905218,4,1254803046447423490),

(1255326927121682434,4,1253349975972265985),

(1255326927134265345,4,1254607853257482242),

(1255326927142653954,4,1253350357083504642),

(1255326927151042561,4,1253974386131783681),

(1255326927163625473,4,1254065382001725442),

(1255326927172014082,4,1254249304429821953),

(1255326965323403266,6,1),

(1255326965335986178,6,2),

(1255326965340180481,6,16),

(1255326965348569090,6,15),

(1255326965356957698,6,68),

(1255326965365346306,6,99),

(1255326965382123522,6,101),

(1255326965390512129,6,167),

(1255326965394706433,6,129),

(1255326965403095042,6,108),

(1255326965411483649,6,109),

(1255326965419872257,6,145),

(1255326965424066561,6,180),

(1255326965432455170,6,110),

(1255326965449232386,6,111),

(1255326965466009601,6,1253348267653218305),

(1255326965474398210,6,1253349238940778498),

(1255326965482786818,6,1254616704945958914),

(1255326965486981121,6,1255326656635211777),

(1255326965495369729,6,1254802944899129345),

(1255326965503758337,6,1254803046447423490),

(1255326965512146945,6,1253349975972265985),

(1255326965524729858,6,1254607853257482242),

(1255326965533118465,6,1253350357083504642),

(1255326965549895682,6,1253974386131783681),

(1255326965549895683,6,1254065382001725442),

(1255326965558284290,6,1254249304429821953),

(1255327060563464194,7,130),

(1255327060571852802,7,132),

(1255327060580241410,7,166),

(1255327060592824322,7,164),

(1255327060597018626,7,165),

(1255327060613795841,7,131),

(1255327060617990145,7,163),

(1255327060626378754,7,162),

(1255327060630573058,7,68),

(1255327060638961666,7,99),

(1255327060651544578,7,101),

(1255327060659933185,7,167),

(1255327060676710402,7,129),

(1255327060680904706,7,1253348267653218305),

(1255327060693487618,7,1253349238940778498),

(1255327060693487619,7,1254616704945958914),

(1255327060701876226,7,1255326656635211777),

(1255327060710264833,7,1254802944899129345),

(1255327060718653441,7,1254803046447423490),

(1255327060735430658,7,1253349975972265985),

(1255327060743819265,7,1254607853257482242),

(1255327060752207873,7,1253350357083504642),

(1255327060760596481,7,1253974386131783681),

(1255327060768985089,7,1254065382001725442),

(1255327060777373697,7,1254249304429821953),

(1255327165207154689,1255020745534443521,130),

(1255327165215543297,1255020745534443521,132),

(1255327165228126209,1255020745534443521,166),

(1255327165244903425,1255020745534443521,164),

(1255327165257486337,1255020745534443521,165),

(1255327165265874945,1255020745534443521,131),

(1255327165274263554,1255020745534443521,163),

(1255327165282652161,1255020745534443521,162),

(1255327165291040769,1255020745534443521,68),

(1255327165299429378,1255020745534443521,99),

(1255327165312012289,1255020745534443521,101),

(1255327165328789506,1255020745534443521,167),

(1255327165337178113,1255020745534443521,1250359587451895810),

(1255327165349761026,1255020745534443521,129),

(1255327165358149634,1255020745534443521,1253348267653218305),

(1255327165366538241,1255020745534443521,1253349238940778498),

(1255327165383315457,1255020745534443521,1254616704945958914),

(1255327165395898369,1255020745534443521,1255326656635211777),

(1255327165404286977,1255020745534443521,1254802944899129345),

(1255327165412675586,1255020745534443521,1254803046447423490),

(1255327165421064193,1255020745534443521,1253349975972265985),

(1255327165429452802,1255020745534443521,1254607853257482242),

(1255327165437841409,1255020745534443521,1253350357083504642),

(1255327165446230018,1255020745534443521,1253974386131783681),

(1255327165463007234,1255020745534443521,1254065382001725442),

(1255327165471395841,1255020745534443521,1254249304429821953),

(1255327268231843842,1255022318142590978,1),

(1255327268240232450,1255022318142590978,2),

(1255327268248621058,1255022318142590978,16),

(1255327268257009666,1255022318142590978,173),

(1255327268269592577,1255022318142590978,15),

(1255327268286369793,1255022318142590978,18),

(1255327268298952705,1255022318142590978,17),

(1255327268307341314,1255022318142590978,196),

(1255327268315729921,1255022318142590978,3),

(1255327268319924226,1255022318142590978,22),

(1255327268328312834,1255022318142590978,21),

(1255327268340895746,1255022318142590978,20),

(1255327268349284354,1255022318142590978,19),

(1255327268361867265,1255022318142590978,195),

(1255327268382838785,1255022318142590978,197),

(1255327268382838786,1255022318142590978,198),

(1255327268391227393,1255022318142590978,199),

(1255327268403810305,1255022318142590978,200),

(1255327268420587522,1255022318142590978,201),

(1255327268428976130,1255022318142590978,1253348267653218305),

(1255327268445753345,1255022318142590978,1253349238940778498),

(1255327268449947649,1255022318142590978,1254616704945958914),

(1255327268458336258,1255022318142590978,1255326656635211777),

(1255327268462530562,1255022318142590978,1254802944899129345),

(1255327268470919169,1255022318142590978,1254803046447423490),

(1255327268483502081,1255022318142590978,1253349975972265985),

(1255327268491890689,1255022318142590978,1254607853257482242),

(1255327268500279298,1255022318142590978,1253350357083504642),

(1255327268517056513,1255022318142590978,1253974386131783681),

(1255327268521250818,1255022318142590978,1254065382001725442),

(1255327268529639426,1255022318142590978,1254249304429821953),

(1255388757273829377,8,1253348267653218305),

(1255388757282217985,8,1253349238940778498),

(1255388757298995201,8,1254616704945958914),

(1255388757307383809,8,1255326656635211777),

(1255388757315772418,8,1254802944899129345),

(1255388757340938242,8,1254803046447423490),

(1255388757357715458,8,1253349975972265985),

(1255388757370298369,8,1254607853257482242),

(1255388757378686977,8,1253350357083504642),

(1255388757387075585,8,1253974386131783681),

(1255388757408047105,8,1254065382001725442),

(1255388757429018625,8,1254249304429821953);

/*Table structure for table `sys_role_module` */

DROP TABLE IF EXISTS `sys_role_module`;

CREATE TABLE `sys_role_module` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) DEFAULT NULL COMMENT '权限ID',
  `module_code` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模块编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色对应模块表';

/*Data for the table `sys_role_module` */

insert  into `sys_role_module`(`id`,`role_id`,`module_code`) values 

(1,0,'core'),

(2,0,'bcs'),

(1243849569814659074,1,'core'),

(1248613427259342849,1248613019166146562,'core'),

(1248614868602560514,1248614843285741570,'core'),

(1253241716917596162,1253241705483923457,'bpm'),

(1253272504656662529,1253272504346284033,'bpm'),

(1253273692424835074,1253273692361920514,'bpm'),

(1253275442384965633,1253275442024255489,'bpm'),

(1253278193789652993,1253278193458302977,'bpm'),

(1253278968548921346,1253278968200794113,'bpm'),

(1253279760643170306,1253279760320208897,'bpm'),

(1253282631019352066,1253282630738333698,'bpm'),

(1253284235252527105,1253284234870845441,'bpm'),

(1253285120099667969,1253285119793483777,'bpm'),

(1253286385043103745,1253286384715948033,'bpm'),

(1253287931927584769,1253287931847892993,'bpm'),

(1253307882520395777,1253307882121936898,'bpm'),

(1253308564052852738,1253308563956383746,'bpm'),

(1253311262487691265,1253311242518609921,'bpm'),

(1253312562994561026,1253312543344246786,'bpm'),

(1253314722092179457,1253314721664360450,'bpm'),

(1253320520159617025,1253320520096702466,'bpm'),

(1253320650694746114,1253320650627637249,'bpm'),

(1253497138178035713,1253333281983893506,'bpm'),

(1255326816870207490,2,'core'),

(1255326816886984705,2,'bpm'),

(1255326857173274626,3,'core'),

(1255326857190051841,3,'bpm'),

(1255326927209762817,4,'core'),

(1255326927218151425,4,'bpm'),

(1255326965591838721,6,'core'),

(1255326965600227329,6,'bpm'),

(1255327060798345218,7,'core'),

(1255327060810928129,7,'bpm'),

(1255327165492367361,1255020745534443521,'core'),

(1255327165509144577,1255020745534443521,'bpm'),

(1255327268550610945,1255022318142590978,'core'),

(1255327268563193858,1255022318142590978,'bpm'),

(1255388757466767361,8,'bpm'),

(1255406907579138050,1255406907281342466,'bpm');

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

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`username`,`name`,`password`,`salt`,`email`,`mobile`,`status`,`del_flag`,`pwd_security_level`,`pwd_update_date`,`comp_id`,`dept_id`,`create_time`,`card_id`,`avatar`,`update_time`,`update_by`,`create_by`,`remark`) values 

(1,'admin','超级管理员','77191ae6693e6db3e9ac44d57059bc046a5e93633b6700e95829b71c3b70dbfc','9rAjzhlpiE4cKEzJyy85','18774995071@163.com','18774995071','0','0','2','2019-12-11 19:45:08',1,1,'2018-05-11 11:11:11','','/profile/avatar/2020/03/28/9568481d5b04c22cb0805b89e5a9f869.png/','2020-04-29 05:20:01','admin','',''),

(1239914383204892673,'00213','张三','5cdfc4a3e661948a6f93f385fab4699e9ace521c4e0805420d172e5740d34341','HXdhsZrJjnok8iHrIB4r','18774994072@163.com','18774994072','1','0','2','2020-03-08 15:16:25',1241024147242586113,NULL,'2020-03-17 22:00:00','','','2020-04-28 07:04:05','admin','admin',''),

(1239915009632583681,'00214','李四','4a47fa6905203f74987cb944b70046fec94b31a6093795cf27495730d982f866','ag6XnFV1GZFh9mkkK07g','18772321312@163.com','18772321312','0','0','2',NULL,1241024147242586113,NULL,'2020-03-17 22:02:29','','/profile/avatar/2020/04/28/cca66a79348eedeadf4820de5bee5f34.png/','2020-03-28 23:22:11','admin','admin',''),

(1241025732404285442,'00215','张飞','2c0af1edf6a2a9178fb4569a8242170b5f00c8b6a080d78061eca80f4f10e554','Dbmqk7XCryFvujJxOgcy','751312449@qq.com','18774994013','0','0','2','2020-03-26 15:22:47',1241024147242586113,NULL,'2020-03-20 10:36:06','','','2020-04-27 22:52:42','admin','admin',''),

(1241025930912305153,'00216','刘备','07c0c2aa6cac98a16bda5b8b99aea4ddc8a1c303c834207ebe8b23a843739ae5','yCUogGZH3bqhszGbPNp0','123456@qq.com','13234234234','0','0','2','2020-03-25 16:47:56',1241024147242586113,NULL,'2020-03-20 10:36:54','','/profile/avatar/2020/04/25/af453257274b94faacf886a5915d9412.png/','2020-04-27 22:52:50','admin','admin',''),

(1241026155169157122,'00217','关羽','9222af358bf70643870652d1a2c9b2db95c198aae6287cf42970b375c1b2ddf0','5vSTXoPBqTQZA4hJhUEQ','123213@QQ.com','18742342223','1','0','2','2020-03-08 15:22:26',1241024147242586113,NULL,'2020-03-20 10:37:47','','','2020-04-28 07:04:45','admin','admin',''),

(1241026312006766594,'00219','曹操','94f04f2fb68f262e9879a0b4dbe04c29567b4f5932b48d6862e7cc413de68ec0','kXpq77QShWyQyEKpLqkY','zhouhuan@lixinfintech.com','15645645645','0','0','2','2020-03-10 21:21:27',1241024147242586113,NULL,'2020-03-20 10:38:25','','','2020-04-28 07:04:57','admin','admin',''),

(1253346480368009217,'00220','赵云','ba6dbd6acb28771d677066dea2aa7fd19e842819200c7dea4c135e7b1ec28bf0','5bxWbC3HvKzVKEIakjGG','123213@QQ.com','17771231243','0','0','2',NULL,1241024147242586113,NULL,'2020-04-23 07:34:22','','/profile/avatar/2020/04/24/7ef941c5c5e76300dfefbe01a2f4a27b.png/','2020-04-28 07:05:08','admin','admin',''),

(1255151411211141121,'00221','诸葛亮','2b7ed7674167ec0394ca898506ab23f25e5d5d2f4769b8a7ec51ef86e30fe4a3','T4zvwo0YsflbdacFtXDg','14562132342@163.com','14562132342','0','0','2',NULL,1241024147242586113,NULL,'2020-04-28 07:06:31','','',NULL,'','admin',''),

(1255152805615894529,'00222','小乔','01588d23049291f53360d2e4a0b221b8bb4883ad22859ea4ffc7699492e6eaca','tkMZuyjCooVJ8HQMvdUh','18721231232@163.com','18721231232','0','0','2',NULL,1236303334564106350,NULL,'2020-04-28 07:12:03','','',NULL,'','admin',''),

(1255152992669270017,'00223','大乔','7000256e83de2099062b1a9f1bc21295f9d1db9bcfa33d7d43f3f2b5cf4704b6','2pEjNQFKyhF4PiupXN6t','18771231232@163.com','18771231232','0','0','2',NULL,1236303334564106350,NULL,'2020-04-28 07:12:48','','',NULL,'','admin','');

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

/*Data for the table `sys_user_comp` */

insert  into `sys_user_comp`(`id`,`user_id`,`comp_id`) values 

(1,1,1);

/*Table structure for table `sys_user_dept` */

DROP TABLE IF EXISTS `sys_user_dept`;

CREATE TABLE `sys_user_dept` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '地区ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户与公司地区对应关系';

/*Data for the table `sys_user_dept` */

insert  into `sys_user_dept`(`id`,`user_id`,`dept_id`) values 

(1239913958166708226,1239817862977224700,1239913616460955650),

(1253489810745778178,1,1241024269615599618),

(1253489810754166785,1,1241024322249920514),

(1255027138366771201,1241025732404285442,1241024879228325889),

(1255027173653450753,1241025930912305153,1241024945540272130),

(1255150800969269249,1239914383204892673,1241024879228325889),

(1255150856732540929,1239915009632583681,1241024879228325889),

(1255150856749318146,1239915009632583681,1241024945540272130),

(1255150966392619009,1241026155169157122,1241024879228325889),

(1255151018934665217,1241026312006766594,1241024879228325889),

(1255151018959831041,1241026312006766594,1241024945540272130),

(1255151065877315585,1253346480368009217,1241024879228325889),

(1255151065898287106,1253346480368009217,1241024945540272130),

(1255151411286638593,1255151411211141121,1241024879228325889),

(1255151411303415809,1255151411211141121,1241024945540272130),

(1255152805678809090,1255152805615894529,1238849577246834689),

(1255152805695586305,1255152805615894529,1241024822970126337),

(1255152992723795970,1255152992669270017,1241024822970126337);

/*Table structure for table `sys_user_post` */

DROP TABLE IF EXISTS `sys_user_post`;

CREATE TABLE `sys_user_post` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_code` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户与岗位关联表';

/*Data for the table `sys_user_post` */

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户与角色对应关系';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`) values 

(1239913957210406914,1239817862977224700,8),

(1253489810720612353,1,1),

(1255027138345799681,1241025732404285442,1255022318142590978),

(1255027173615702017,1241025930912305153,1255022318142590978),

(1255150800918937601,1239914383204892673,1255020745534443521),

(1255150856703180801,1239915009632583681,1255020745534443521),

(1255150966359064578,1241026155169157122,2),

(1255151018909499394,1241026312006766594,7),

(1255151411253084162,1255151411211141121,6),

(1255152805641060354,1255152805615894529,1255022318142590978),

(1255152992694435842,1255152992669270017,1255020745534443521),

(1255321640788692994,1253346480368009217,8);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
