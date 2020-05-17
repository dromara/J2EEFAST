???prompt Disabling triggers for SYS_COMP...
alter table SYS_COMP disable all triggers;
prompt Disabling triggers for SYS_COMP_DEPT...
alter table SYS_COMP_DEPT disable all triggers;
prompt Disabling triggers for SYS_CONFIG...
alter table SYS_CONFIG disable all triggers;
prompt Disabling triggers for SYS_DEPT...
alter table SYS_DEPT disable all triggers;
prompt Disabling triggers for SYS_DICT_DATA...
alter table SYS_DICT_DATA disable all triggers;
prompt Disabling triggers for SYS_DICT_TYPE...
alter table SYS_DICT_TYPE disable all triggers;
prompt Disabling triggers for SYS_JOB...
alter table SYS_JOB disable all triggers;
prompt Disabling triggers for SYS_MENU...
alter table SYS_MENU disable all triggers;
prompt Disabling triggers for SYS_MODULE...
alter table SYS_MODULE disable all triggers;
prompt Disabling triggers for SYS_NOTICE...
alter table SYS_NOTICE disable all triggers;
prompt Disabling triggers for SYS_POST...
alter table SYS_POST disable all triggers;
prompt Disabling triggers for SYS_ROLE...
alter table SYS_ROLE disable all triggers;
prompt Disabling triggers for SYS_ROLE_DEPT...
alter table SYS_ROLE_DEPT disable all triggers;
prompt Disabling triggers for SYS_ROLE_MENU...
alter table SYS_ROLE_MENU disable all triggers;
prompt Disabling triggers for SYS_ROLE_MODULE...
alter table SYS_ROLE_MODULE disable all triggers;
prompt Disabling triggers for SYS_USER...
alter table SYS_USER disable all triggers;
prompt Disabling triggers for SYS_USER_COMP...
alter table SYS_USER_COMP disable all triggers;
prompt Disabling triggers for SYS_USER_DEPT...
alter table SYS_USER_DEPT disable all triggers;
prompt Disabling triggers for SYS_USER_POST...
alter table SYS_USER_POST disable all triggers;
prompt Disabling triggers for SYS_USER_ROLE...
alter table SYS_USER_ROLE disable all triggers;
prompt Deleting SYS_USER_ROLE...
delete from SYS_USER_ROLE;
commit;
prompt Deleting SYS_USER_POST...
delete from SYS_USER_POST;
commit;
prompt Deleting SYS_USER_DEPT...
delete from SYS_USER_DEPT;
commit;
prompt Deleting SYS_USER_COMP...
delete from SYS_USER_COMP;
commit;
prompt Deleting SYS_USER...
delete from SYS_USER;
commit;
prompt Deleting SYS_ROLE_MODULE...
delete from SYS_ROLE_MODULE;
commit;
prompt Deleting SYS_ROLE_MENU...
delete from SYS_ROLE_MENU;
commit;
prompt Deleting SYS_ROLE_DEPT...
delete from SYS_ROLE_DEPT;
commit;
prompt Deleting SYS_ROLE...
delete from SYS_ROLE;
commit;
prompt Deleting SYS_POST...
delete from SYS_POST;
commit;
prompt Deleting SYS_NOTICE...
delete from SYS_NOTICE;
commit;
prompt Deleting SYS_MODULE...
delete from SYS_MODULE;
commit;
prompt Deleting SYS_MENU...
delete from SYS_MENU;
commit;
prompt Deleting SYS_JOB...
delete from SYS_JOB;
commit;
prompt Deleting SYS_DICT_TYPE...
delete from SYS_DICT_TYPE;
commit;
prompt Deleting SYS_DICT_DATA...
delete from SYS_DICT_DATA;
commit;
prompt Deleting SYS_DEPT...
delete from SYS_DEPT;
commit;
prompt Deleting SYS_CONFIG...
delete from SYS_CONFIG;
commit;
prompt Deleting SYS_COMP_DEPT...
delete from SYS_COMP_DEPT;
commit;
prompt Deleting SYS_COMP...
delete from SYS_COMP;
commit;
prompt Loading SYS_COMP...
insert into SYS_COMP (comp_id, parent_id, name, remark, order_num, del_flag, status, create_time, create_by, update_by, update_time)
values (1, 0, '总公司', '根部公司', 0, '0', '0', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, 'admin', to_timestamp('24-02-2020 03:50:37.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into SYS_COMP (comp_id, parent_id, name, remark, order_num, del_flag, status, create_time, create_by, update_by, update_time)
values (1236303334564106350, 1, '北京有限公司', null, 2, '0', '0', to_timestamp('15-03-2020 16:31:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, 'admin', to_timestamp('09-04-2020 06:23:51.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into SYS_COMP (comp_id, parent_id, name, remark, order_num, del_flag, status, create_time, create_by, update_by, update_time)
values (1239875326212898817, 1, '南京有限公司', null, 3, '0', '0', to_timestamp('17-03-2020 19:24:48.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', 'admin', to_timestamp('20-03-2020 23:33:31.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into SYS_COMP (comp_id, parent_id, name, remark, order_num, del_flag, status, create_time, create_by, update_by, update_time)
values (1241024147242586113, 1, '上海法斯特有限公司', null, 0, '0', '0', to_timestamp('20-03-2020 10:29:48.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', 'admin', to_timestamp('20-03-2020 23:33:20.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
commit;
prompt 4 records loaded
prompt Loading SYS_COMP_DEPT...
insert into SYS_COMP_DEPT (id, comp_id, dept_id)
values (1241025032186204161, 1241024147242586113, 2);
insert into SYS_COMP_DEPT (id, comp_id, dept_id)
values (1241025032186204162, 1241024147242586113, 1241024879228325889);
insert into SYS_COMP_DEPT (id, comp_id, dept_id)
values (1241025032194592769, 1241024147242586113, 1241024945540272130);
insert into SYS_COMP_DEPT (id, comp_id, dept_id)
values (1241025079393095682, 1239875326212898817, 1239913616460955650);
insert into SYS_COMP_DEPT (id, comp_id, dept_id)
values (1248255304413716481, 1236303334564106350, 1238849577246834689);
insert into SYS_COMP_DEPT (id, comp_id, dept_id)
values (1248255304447270914, 1236303334564106350, 1241024822970126337);
commit;
prompt 6 records loaded
prompt Loading SYS_CONFIG...
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (1, '云存储', 'CLOUD_STORAGE_CONFIG_KEY', '{"type":1,"qiniuDomain":"http://7xqbwh.dl1.z0.glb.clouddn.com","qiniuPrefix":"upload","qiniuAccessKey":"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ","qiniuSecretKey":"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV","qiniuBucketName":"ios-app","aliyunDomain":"","aliyunPrefix":"","aliyunEndPoint":"","aliyunAccessKeyId":"","aliyunAccessKeySecret":"","aliyunBucketName":"","qcloudDomain":"","qcloudPrefix":"","qcloudSecretId":"","qcloudSecretKey":"","qcloudBucketName":""}', 'Y', '0', '云存储配置信息', null, to_timestamp('24-12-2019 10:12:02.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null);
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (2, '项目标题', 'SYS_CONFIG_TITLE', 'J2eeFAST', 'Y', '0', '系统名称', null, to_timestamp('24-12-2019 10:12:03.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('04-05-2020 09:48:59.280000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (4, '项目版本', 'SYS_CONFIG_VERSION', '2.0.7', 'Y', '0', '系统版本', null, to_timestamp('24-12-2019 10:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('10-04-2020 06:32:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (5, '系统参数', 'SYS_CONFIG_KEY', '{"login_maxCount":100,"login_NumCount":3,"lock_time":5,"company":"Powered By j2eefast","copyrightYear":"2020","ipc":"湘ICP备20005342号"}', 'Y', '0', '系统参数配置、登陆错误次数锁定账户时间、所属公司', null, to_timestamp('24-12-2019 10:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('03-05-2020 23:34:20.928000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (7, 'JS版本', 'SYS_CONFIG_DV_VERSION', '202003311233', 'Y', '0', 'JS\\CSS版本-日期表示', null, to_timestamp('24-12-2019 10:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('10-04-2020 06:32:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (8, '项目小标题', 'SYS_CONFIG_TITLE_MINI', 'JFT', 'Y', '0', '系统简称', null, to_timestamp('24-12-2019 10:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('04-05-2020 09:32:40.567000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (11, '上传路径', 'SYS_CONFIG_PROFILE', 'D:\\lixinfile\\uploadPath', 'Y', '0', '项目静态文件', null, to_timestamp('24-12-2019 10:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('10-04-2020 06:32:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (12, '用户管理-账号初始密码', 'SYS_USER_INITPASSWORD', '123456', 'Y', '0', '用户初始密码', null, to_timestamp('24-12-2019 10:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('10-04-2020 06:32:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (13, '项目页面压缩', 'SYS_COMPRESS', 'false', 'Y', '0', '项目输出前端页面是否压缩 true 压缩 false 不压缩 注意:开启页面压缩页面JS不能使用// 做注解 可以使用 /**/', 'admin', to_timestamp('24-12-2019 10:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('10-04-2020 06:32:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (14, '登陆排挤', 'SYS_IS_LOGIN', '1', 'Y', '0', '同一账户只能一个人登陆 0 支持 1不支持', 'admin', to_timestamp('24-12-2019 10:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('10-04-2020 06:32:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (15, '屏保时间', 'SYS_LOCK_SCEREEN', '{"time":30,"flag":true}', 'Y', '0', '是否开启true  false 自动屏保及时间(分钟)', 'admin', to_timestamp('24-12-2019 10:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('10-04-2020 06:32:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (16, '主框架页-默认皮肤样式名称', 'SYS_INDEX_SKINNAME', 'skin-blue-light', 'Y', '0', 'skin-black-light、skin-black、skin-blue-light、skin-blue、skin-green-light、skin-green、skin-purple-light、skin-purple、skin-red-light、skin-red、skin-yellow-light、skin-yellow', 'admin', to_timestamp('24-12-2019 10:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('10-04-2020 06:32:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (17, '项目文件库', 'SYS_CONFIG_FILE', 'D:\\lixinfile\\file\\temp', 'Y', '0', '系统默认文件库', 'admin', to_timestamp('24-12-2019 10:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('10-04-2020 06:32:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (1243831619548323842, 'TOP提示消失时间', 'SYS_TIP_TIME', '3', 'Y', '0', '分钟表示', 'admin', to_timestamp('24-12-2019 10:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('10-04-2020 06:32:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into SYS_CONFIG (id, param_name, param_key, param_value, config_type, del_flag, remark, create_by, create_time, update_time, update_by)
values (1245316284947877890, '系统登陆页面默认主题', 'SYS_LOGIN_DEFAULT_VIEW', 'Gitee', 'Y', '0', '系统登陆页面默认主题', 'admin', to_timestamp('24-12-2019 10:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('10-04-2020 06:32:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
commit;
prompt 15 records loaded
prompt Loading SYS_DEPT...
insert into SYS_DEPT (dept_id, parent_id, name, type, order_num, del_flag, status, create_time, create_by, update_by, update_time, remark)
values (1, 0, '中国', 0, 0, '0', '0', to_timestamp('22-12-2019 13:14:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null, null);
insert into SYS_DEPT (dept_id, parent_id, name, type, order_num, del_flag, status, create_time, create_by, update_by, update_time, remark)
values (2, 1, '上海市', 0, 1, '0', '0', to_timestamp('22-12-2019 13:14:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null, null);
insert into SYS_DEPT (dept_id, parent_id, name, type, order_num, del_flag, status, create_time, create_by, update_by, update_time, remark)
values (1238849577246834689, 1, '北京市', 0, 1, '0', '0', to_timestamp('22-12-2019 13:14:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', null, null, null);
insert into SYS_DEPT (dept_id, parent_id, name, type, order_num, del_flag, status, create_time, create_by, update_by, update_time, remark)
values (1239913616460955650, 1, '南京市', 0, 2, '0', '0', to_timestamp('22-12-2019 13:14:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', null, null, null);
insert into SYS_DEPT (dept_id, parent_id, name, type, order_num, del_flag, status, create_time, create_by, update_by, update_time, remark)
values (1241024269615599618, 1, '湖南省', 0, 3, '0', '0', to_timestamp('22-12-2019 13:14:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', null, null, null);
insert into SYS_DEPT (dept_id, parent_id, name, type, order_num, del_flag, status, create_time, create_by, update_by, update_time, remark)
values (1241024322249920514, 1241024269615599618, '长沙市', 0, 0, '0', '0', to_timestamp('22-12-2019 13:14:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', null, null, null);
insert into SYS_DEPT (dept_id, parent_id, name, type, order_num, del_flag, status, create_time, create_by, update_by, update_time, remark)
values (1241024822970126337, 1238849577246834689, '朝阳区', 0, 0, '0', '0', to_timestamp('22-12-2019 13:14:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', 'admin', to_timestamp('22-12-2019 13:14:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_DEPT (dept_id, parent_id, name, type, order_num, del_flag, status, create_time, create_by, update_by, update_time, remark)
values (1241024879228325889, 2, '浦东新区', 0, 0, '0', '0', to_timestamp('22-12-2019 13:14:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', 'admin', to_timestamp('22-12-2019 13:14:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_DEPT (dept_id, parent_id, name, type, order_num, del_flag, status, create_time, create_by, update_by, update_time, remark)
values (1241024945540272130, 2, '闵行区', 0, 1, '0', '0', to_timestamp('22-12-2019 13:14:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', null, null, null);
insert into SYS_DEPT (dept_id, parent_id, name, type, order_num, del_flag, status, create_time, create_by, update_by, update_time, remark)
values (1248260285279739905, 1241024269615599618, '永州市', 0, 1, '0', '0', to_timestamp('22-12-2019 13:14:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', null, null, null);
commit;
prompt 10 records loaded
prompt Loading SYS_DICT_DATA...
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (1, 1, '男', '0', 'sys_user_sex', null, '0', 'Y', '0', null, null, 'Y', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '性别男');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (2, 2, '女', '1', 'sys_user_sex', null, '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '性别女');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (3, 3, '未知', '2', 'sys_user_sex', null, '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '性别未知');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (4, 1, '显示', '0', 'sys_show_hide', null, '0', 'Y', '0', null, 'primary', 'Y', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:34:17.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '显示菜单');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (5, 2, '隐藏', '1', 'sys_show_hide', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '隐藏菜单');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (6, 1, '正常', '0', 'sys_normal_disable', null, '0', 'Y', '0', null, 'primary', 'Y', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '正常状态');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (7, 2, '停用', '1', 'sys_normal_disable', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '停用状态');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (8, 1, '正常', '0', 'sys_job_status', null, '0', 'Y', '0', null, 'primary', 'Y', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '正常状态');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (9, 2, '暂停', '1', 'sys_job_status', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '停用状态');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (10, 1, '默认', 'DEFAULT', 'sys_job_group', null, '0', 'Y', '0', null, null, 'Y', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '默认分组');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (11, 2, '系统', 'SYSTEM', 'sys_job_group', null, '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '系统分组');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (12, 1, '是', 'Y', 'sys_yes_no', null, '0', 'Y', '0', null, 'primary', 'Y', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '系统默认是');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (13, 2, '否', 'N', 'sys_yes_no', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '系统默认否');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (14, 1, '通知', '0', 'sys_notice_type', null, '0', 'Y', '0', null, 'success', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('25-02-2020 06:26:20.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '通知');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (15, 2, '公告', '1', 'sys_notice_type', null, '0', 'Y', '0', null, 'danger', 'Y', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('25-02-2020 06:26:26.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '公告');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (16, 1, '发布', '0', 'sys_notice_status', null, '0', 'Y', '0', null, 'primary', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('26-02-2020 09:49:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '正常状态');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (17, 2, '未发布', '1', 'sys_notice_status', null, '0', 'Y', '0', null, 'danger', 'Y', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('26-02-2020 09:49:02.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '关闭状态');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (18, 1, '新增', '1', 'sys_oper_type', null, '0', 'Y', '0', null, 'info', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '新增操作');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (19, 2, '修改', '2', 'sys_oper_type', null, '0', 'Y', '0', null, 'info', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '修改操作');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (20, 3, '删除', '3', 'sys_oper_type', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '删除操作');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (21, 4, '授权', '4', 'sys_oper_type', null, '0', 'Y', '0', null, 'primary', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '授权操作');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (22, 5, '导出', '5', 'sys_oper_type', null, '0', 'Y', '0', null, 'warning', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '导出操作');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (23, 6, '导入', '6', 'sys_oper_type', null, '0', 'Y', '0', null, 'warning', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '导入操作');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (24, 7, '强退', '7', 'sys_oper_type', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '强退操作');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (25, 8, '生成代码', '8', 'sys_oper_type', null, '0', 'Y', '0', null, 'warning', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '生成操作');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (26, 9, '清空数据', '9', 'sys_oper_type', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '清空操作');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (27, 1, '成功', '0', 'sys_common_status', null, '0', 'Y', '0', null, 'primary', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '正常状态');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (28, 2, '失败', '1', 'sys_common_status', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('16-03-2018 11:33:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '停用状态');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (29, 1, '正常', '0', 'sys_user_show_hide', null, '0', 'Y', '0', null, 'primary', 'Y', 'admin', to_timestamp('19-12-2019 17:39:34.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '用户正常状态');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (30, 2, '禁用', '1', 'sys_user_show_hide', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('19-12-2019 17:40:10.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '用户禁用状态');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (31, 1, '地区', '0', 'sys_dept_line', null, '0', 'Y', '0', null, 'primary', 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '地区字典');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (32, 1, '线路', '1', 'sys_dept_line', null, '0', 'Y', '0', null, 'warning', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '线路显示');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (33, 0, '在线', '0', 'sys_monitor_online', null, '0', 'Y', '0', null, 'success', 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '用户在线');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (34, 1, '离线', '1', 'sys_monitor_online', null, '0', 'Y', '0', null, 'warning', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '用户离线');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (35, 0, '成功', '00000', 'sys_login_status', null, '0', 'Y', '0', null, 'primary', 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '登陆成功');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (36, 1, '失败', '-1', 'sys_login_status', null, '0', 'Y', '0', null, 'warning', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '其他失败情况');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (37, 0, '纸币', '1', 'sys_pay_type', 'label label-primary', '0', 'Y', '0', null, null, 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '交易纸币');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (38, 1, '硬币', '2', 'sys_pay_type', 'label label-success', '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '硬币类型');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (39, 2, '公交卡', '3', 'sys_pay_type', 'label label-warning', '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '公交卡类型');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (40, 3, '微信', '4', 'sys_pay_type', 'label label-info', '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '微信类型');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (41, 4, '支付宝', '5', 'sys_pay_type', 'label label-default', '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '支付宝类型');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (42, 0, '空闲', '0000', 'sys_bag_status', null, '0', 'Y', '0', null, 'primary', 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '钞袋空闲');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (43, 1, '使用中', '0001', 'sys_bag_status', null, '0', 'Y', '0', null, 'success', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '钞袋使用中');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (45, 3, '钞袋在胆箱中', '0003', 'sys_bag_status', null, '0', 'Y', '0', null, 'info', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '钞袋胆箱中');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (46, 4, '清胆完成未入库', '0004', 'sys_bag_status', null, '0', 'Y', '0', null, 'warning', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '清单完成未入库');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (47, 5, '入库完成,等待处理', '0005', 'sys_bag_status', null, '0', 'Y', '0', null, 'success', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '完成入库，未处理');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (48, 0, '初始密码', '0', 'sys_user_passwordModifySecurityLevel', null, '0', 'Y', '0', null, 'danger', 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '用户密码为初始密码');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (49, 1, '非常弱', '1', 'sys_user_passwordModifySecurityLevel', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '密码等级');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (50, 2, '弱', '2', 'sys_user_passwordModifySecurityLevel', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '密码等级弱');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (51, 3, '一般', '3', 'sys_user_passwordModifySecurityLevel', null, '0', 'Y', '0', null, 'warning', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '密码等级一般');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (52, 4, '强', '4', 'sys_user_passwordModifySecurityLevel', null, '0', 'Y', '0', null, 'success', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '密码安全等级强');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (53, 5, '非常强', '5', 'sys_user_passwordModifySecurityLevel', null, '0', 'Y', '0', null, 'success', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '密码等级非常强');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (54, 6, '安全', '6', 'sys_user_passwordModifySecurityLevel', null, '0', 'Y', '0', null, 'primary', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '密码等级安全');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (55, 7, '非常安全', '7', 'sys_user_passwordModifySecurityLevel', null, '0', 'Y', '0', null, 'primary', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '密码等级最高级别安全');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (56, 0, '中文(简体)', 'zh_CN', 'sys_lang_type', null, '0', 'Y', '0', null, 'default', 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '中文');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (57, 1, 'English', 'en_US', 'sys_lang_type', null, '0', 'Y', '0', null, 'default', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '英文字体');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (59, 1, '月报表', '1', 'bcs_poi_type', null, '0', 'Y', '0', null, 'success', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '月报表');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (64, 0, '主数据库', '0', 'sys_db', null, '0', 'Y', '0', null, 'primary', 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '主数据库');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (66, 2, '한국어', 'ko_KR', 'sys_lang_type', null, '0', 'Y', '0', null, 'default', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '韩语');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (67, 3, '日本語', 'ja_JP', 'sys_lang_type', null, '0', 'Y', '0', null, 'default', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '日本语言');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (68, 4, '中文(繁體)', 'hkzh_TW', 'sys_lang_type', null, '0', 'Y', '0', null, 'default', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '中文简体');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (69, 0, '目录', '0', 'sys_menu_type', 'label label-success', '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '菜单目录');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (70, 1, '菜单', '1', 'sys_menu_type', 'label label-primary', '0', 'Y', '0', null, null, 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '菜单');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (71, 2, '权限', '2', 'sys_menu_type', 'label label-warning', '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '权限按钮');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (72, 0, 'Tab打开', '_tab', 'sys_menu_target', null, '0', 'Y', '0', null, 'primary', 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '菜单打开已TAB形式打开');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (73, 1, '新窗口', '_blank', 'sys_menu_target', null, '0', 'Y', '0', null, 'info', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '在新窗口打开_blank');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (74, 2, '弹出窗体', '_alert', 'sys_menu_target', null, '0', 'Y', '0', null, 'warning', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '弹出窗口形式');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (75, 3, '全屏', '_fullscreen', 'sys_menu_target', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '打开窗体全屏显示');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (82, 0, '默认', 'default', 'sys_table_actions_type', null, '0', 'N', '0', null, null, 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '默认风格');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (83, 1, '图标', 'icon', 'sys_table_actions_type', null, '0', 'N', '0', null, null, 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '操作按钮图标');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (84, 0, '正常', '0', 'sys_module_status', null, '0', 'Y', '0', null, 'primary', 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '正常');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (85, 1, '停用', '1', 'sys_module_status', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (86, 2, '未安装', '2', 'sys_module_status', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (87, 0, '高层', '1', 'sys_post_type', null, '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (88, 1, '中层', '2', 'sys_post_type', null, '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (89, 2, '基层', '3', 'sys_post_type', null, '0', 'Y', '0', null, null, 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (90, 3, '其他', '4', 'sys_post_type', null, '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (91, 0, '正常', '0', 'sys_status', null, '0', 'Y', '0', null, 'primary', 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '正常');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (92, 1, '停用', '1', 'sys_status', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (1243048136005525506, 3, '失效', '2', 'sys_notice_status', null, '0', 'Y', '0', null, 'warning', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '失效状态');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (1243462158207123457, 0, '普通', '0', 'sys_notice_level', null, '0', 'Y', '0', null, 'primary', 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '普通等级');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (1243462260573306881, 1, '紧急', '1', 'sys_notice_level', null, '0', 'Y', '0', null, 'warning', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '等级紧急');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (1243462373609799681, 2, '严重', '2', 'sys_notice_level', null, '0', 'Y', '0', null, 'danger', 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '等级严重');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (1246694957642915842, 0, 'Admin-LTE', 'Admin-LTE', 'sys_login_view', null, '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (1246695157451169794, 1, '高仿码云', 'Gitee', 'sys_login_view', null, '0', 'Y', '0', null, null, 'Y', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (1246999804745269250, 2, '高仿OsChina', 'OsChina', 'sys_login_view', null, '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '高仿OsChina');
insert into SYS_DICT_DATA (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, del_flag, is_sys, status, css_style, list_class, is_default, create_by, create_time, update_by, update_time, remark)
values (1247074141611454465, 3, '蓝色科技', 'Blue-Science', 'sys_login_view', null, '0', 'Y', '0', null, null, 'N', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '仿百度登陆');
commit;
prompt 87 records loaded
prompt Loading SYS_DICT_TYPE...
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1106120265689055233, '状态', 'sys_status', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1106120532442595330, '用户性别', 'sys_user_sex', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '用户性别列表');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1106120574163337218, '菜单状态', 'sys_show_hide', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '菜单状态列表');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1106120645697191938, '系统开关', 'sys_normal_disable', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '系统开关列表');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1106120699468169217, '任务状态', 'sys_job_status', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '任务状态列表');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1106120784318939137, '任务分组', 'sys_job_group', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '任务分组列表');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1106120825993543682, '系统是否', 'sys_yes_no', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '系统是否列表');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1106120875872206849, '公告类型', 'sys_notice_type', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '通知类型列表');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1106120935070613505, '公告状态', 'sys_notice_status', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '通知状态列表');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1106120968910258177, '操作类型', 'sys_oper_type', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '操作类型列表');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1149218674746355713, '系统状态', 'sys_common_status', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '登录状态列表');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1160532775455047681, '模块状态标志', 'sys_module_status', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '模块状态标志');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1160532886713155585, '岗位分类', 'sys_post_type', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '岗位分类');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1160533264645111810, '用户状态0', 'sys_user_show_hide', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '用户状态列表');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1160533377727741954, '地区线路', 'sys_dept_line', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '地区线路标志');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1160533455343337474, '用户监控', 'sys_monitor_online', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '用户监控状态');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1160533574843252737, '登陆状态', 'sys_login_status', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '系统登陆状态');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1160533625615302658, '密码安全策略', 'sys_user_passwordModifySecurityLevel', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '密码安全等级');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1160533707215486977, '国际化语言类型', 'sys_lang_type', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '国际化语言');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1160533765403066370, '数据库类型', 'sys_db', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '数据库类型');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1160533863834992641, '菜单类型', 'sys_menu_type', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '菜单类型');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1160533945309347841, '菜单目标', 'sys_menu_target', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '左侧菜单打开呈现方式');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1160534007389241346, '表格操作风格', 'sys_table_actions_type', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '菜单表格操作按钮风格');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1243461975188668417, '公告等级', 'sys_notice_level', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '公告等级 等级 0 普通 1紧急 2严重');
insert into SYS_DICT_TYPE (dict_id, dict_name, dict_type, is_sys, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1246694124452814850, '登录页面', 'sys_login_view', 'Y', '0', '0', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '登录页面视图风格');
commit;
prompt 25 records loaded
prompt Loading SYS_JOB...
insert into SYS_JOB (job_id, bean_name, method_name, params, cron_expression, del_flag, status, remark, create_time, job_group, create_by, update_by, update_time, job_name)
values (1239107312446103553, 'jobTaskTest', 'fastParams', '123', '0 */5 6-23 * * ?', '0', '0', 'fastParams有参数', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'DEFAULT', 'admin', null, null, '测试任务');
insert into SYS_JOB (job_id, bean_name, method_name, params, cron_expression, del_flag, status, remark, create_time, job_group, create_by, update_by, update_time, job_name)
values (1239107405597401090, 'fastParams', 'fastNoParms', null, '0 */5 6-23 * * ?', '1', '0', '测试任务无参', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'DEFAULT', 'admin', null, null, '测试任务无参');
insert into SYS_JOB (job_id, bean_name, method_name, params, cron_expression, del_flag, status, remark, create_time, job_group, create_by, update_by, update_time, job_name)
values (1243581612249190401, 'jobNoticeTask', 'fastNotice', null, '0 0/5 * * * ? *', '0', '0', '公告通知时效性排查', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'DEFAULT', 'admin', 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '公告通知时效性');
commit;
prompt 3 records loaded
prompt Loading SYS_MENU...
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (1256979770241142786, 70, '多源数据', 'sys/database', 'core', '_tab', 'sys:database:view', 1, 'fa fa-database', 16, 0, 'admin', to_timestamp('04-05-2020 00:11:45.437000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (1256980257396969473, 174, '接口文档', 'doc.html', 'core', '_alert', null, 1, 'fa fa-gg', 3, 0, 'admin', to_timestamp('04-05-2020 00:13:41.584000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('04-05-2020 00:14:01.995000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (1, 0, '系统设置', null, 'core,bcs', null, null, 0, 'fa fa-desktop', 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (2, 1, '用户管理', 'sys/user', 'core', null, 'sys:user:view', 1, 'fa fa-user', 2, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (3, 1, '角色管理', 'sys/role', 'core', null, 'sys:role:view', 1, 'fa fa-user-secret', 3, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (15, 2, '查看', null, 'core', null, 'sys:user:list,sys:user:info', 2, null, 0, 0, null, null, null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (16, 2, '新增', null, 'core', null, 'sys:user:add,sys:dept:select', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (17, 2, '修改', null, 'core', null, 'sys:user:edit', 2, null, 0, 0, null, null, null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (18, 2, '删除', null, 'core', null, 'sys:user:del', 2, null, 0, 0, null, null, null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (19, 3, '查看', null, 'core', null, 'sys:role:list,sys:role:info', 2, null, 0, 0, null, null, null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (20, 3, '新增', '#', 'core', null, 'sys:role:add', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (21, 3, '修改', '#', 'core', null, 'sys:role:edit', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (22, 3, '删除', '#', 'core', null, 'sys:role:del', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (41, 1, '公司管理', 'sys/comp', 'core', null, 'sys:comp:view', 1, 'fa fa-bars', 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (42, 41, '新增', '#', 'core', null, 'sys:comp:add', 2, null, 1, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (43, 41, '查看', null, 'core', null, 'sys:comp:list', 2, null, 0, 0, null, null, null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (44, 41, '修改', '#', 'core', null, 'sys:comp:edit', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (45, 41, '删除', '#', 'core', null, 'sys:comp:del', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (46, 0, '设备管理', null, 'bcs', null, null, 0, 'fa fa-cogs', 4, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (47, 46, '终端管理', 'bcs/mach', 'bcs', '_tab', 'bcs:mach:view', 1, 'fa fa-bus', 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (48, 47, '新增', null, 'bcs', null, 'bcs:mach:add', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (49, 47, '修改', null, 'bcs', null, 'bcs:mach:edit', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (50, 47, '查看', null, 'bcs', null, 'tbc:mach:list', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (59, 0, '交易管理', null, 'bcs', null, null, 0, 'fa fa-bar-chart', 5, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (60, 59, '所有交易', 'tbc/trad', 'bcs', '_tab', 'tbc:trad:view', 1, 'fa fa-bar-chart', 1, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (61, 59, '实时交易', 'tbc/redltime', 'bcs', '_tab', 'tbc:reltime:view', 1, 'fa fa-building', 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (62, 59, '历史交易', 'tbc/odltrad', 'bcs', '_tab', 'tbc:oldtrad:view', 1, 'fa fa-area-chart', 2, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (63, 62, '查询', '#', 'bcs', null, 'tbc:oldtrad:list', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (64, 62, '查询明细', '#', 'bcs', null, 'tbc:oldtrad:detail', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (65, 61, '查询', '#', 'bcs', null, 'tbc:redltime:list', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (66, 61, '明细查看', '#', 'bcs', null, 'tbc:reltime:detail,tbc:redltime:list', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (67, 60, '查询', null, 'bcs', null, 'tbc:trad:list', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (68, 0, '监控管理', null, 'core,bcs', null, null, 0, 'fa fa-eye', 3, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (69, 68, 'SQL监控', 'druid/sql.html', 'core', null, null, 1, 'fa fa-bug', 2, 0, null, null, null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (70, 0, '系统工具', null, 'core,bcs', null, null, 0, 'fa fa-windows', 1, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (72, 70, '定时任务', 'sys/schedule', 'core', '_tab', 'sys:job:view', 1, 'fa fa-tasks', 8, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (73, 70, '参数管理', 'sys/config', 'core', null, 'sys:config:view', 1, 'fa fa-sun-o', 6, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (76, 70, '文件上传', 'modules/oss/tss.html', 'core', '_tab', 'sys:tss:list', 1, 'fa fa-cloud-upload', 5, 1, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (77, 70, '字典管理', 'sys/dict', 'core', null, 'sys:dict:view', 1, 'fa fa-bookmark-o', 7, 0, null, null, null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (78, 72, '查看', '#', 'core', null, 'sys:job:list', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (79, 72, '新增', '#', 'core', null, 'sys:job:add', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (80, 72, '修改', '#', 'core', null, 'sys:job:edit', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (81, 72, '删除', '#', 'core', null, 'sys:job:del', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (82, 72, '状态', '#', 'core', null, 'sys:job:changeStatus', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (83, 72, '恢复', null, 'core', null, 'sys:job:resume', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (84, 72, '执行', null, 'core', null, 'sys:job:run', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (85, 72, '日志', '#', 'core', null, 'sys:job:loglist', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (86, 77, '查看', null, 'core', null, 'sys:dict:list,sys:dict:info', 2, null, 0, 0, null, null, null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (87, 77, '新增', null, 'core', null, 'sys:dict:add', 2, null, 0, 0, null, null, null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (88, 77, '修改', null, 'core', null, 'sys:dict:edit', 2, null, 0, 0, null, null, null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (89, 77, '删除', null, 'core', null, 'sys:dict:del', 2, null, 0, 0, null, null, null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (94, 70, '菜单管理', 'sys/menu', 'core', null, 'sys:menu:view', 1, 'fa fa-th-list', 1, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (96, 94, '新增', null, 'core', null, 'sys:menu:add', 2, null, 0, 0, null, null, null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (97, 94, '修改', null, 'core', null, 'sys:menu:edit', 2, null, 0, 0, null, null, null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (98, 94, '删除', '#', 'core', null, 'sys:menu:del', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (99, 68, '用户监控', 'sys/online', 'core', null, 'sys:online:view', 1, 'fa fa-user-circle', 1, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (101, 99, '强退', '#', 'core', null, 'sys:online:forceLogout', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (103, 68, '设备监控', 'modules/tbc/monitor.html', 'bcs', '_tab', null, 1, 'fa fa-heartbeat', 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (129, 68, '服务监控', 'sys/server', 'core', '_tab', 'sys:server:view', 1, 'fa fa-server', 3, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (130, 0, '日志管理', '#', 'core,bcs', null, null, 0, 'fa fa-pencil-square-o', 2, 0, null, to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (131, 130, '操作日志', 'sys/operlog', 'core,bcs', '_tab', 'sys:log:view', 1, 'fa fa-file-image-o', 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (132, 130, '登陆日志', 'sys/logininfo', 'core,bcs', '_tab', 'sys:logininfo:view', 1, 'fa fa-address-book', 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (140, 1, '地区管理', 'sys/dept', 'core,bcs', '_tab', 'sys:dept:view', 1, 'fa fa-outdent', 1, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (141, 140, '新增', '#', 'core,bcs', null, 'sys:dept:add', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (142, 140, '查看', '#', 'core,bcs', null, 'sys:dept:list', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (143, 140, '修改', '#', 'core,bcs', null, 'sys:dept:edit', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (144, 140, '删除', '#', 'core,bcs', null, 'sys:dept:del', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (146, 62, '导出报表', null, 'bcs', null, 'tbc:poi:excel', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (158, 1, '项目测试', 'gtv/dev', 'core', '_alert', 'gtv:dev:view', 1, 'glyphicon glyphicon-copyright-mark', 0, 1, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (159, 158, '查看', '#', 'core', null, 'gtv:dev:list,gtv:dev:add,gtv:dev:edit,gtv:dev:del', 2, null, 0, 0, null, null, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (160, 77, '字典查询', null, 'core', null, 'system:dict:list', 2, null, 0, 0, null, null, null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (161, 94, '查看', null, 'core', null, 'sys:menu:list', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (162, 131, '查看', null, 'core,bcs', null, 'sys:log:list', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (163, 131, '详细信息', null, 'core,bcs', null, 'sys:log:detail', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (164, 132, '查看', null, 'core,bcs', null, 'sys:logininfo:list', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (165, 132, '删除', null, 'core,bcs', null, 'sys:logininfo:del', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (166, 132, '清空', null, 'core,bcs', null, 'sys:logininfo:clean', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (167, 99, '查看', null, 'core', null, 'sys:online:list', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (168, 73, '查看', null, 'core', null, 'sys:config:list', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (169, 73, '新增', null, 'core', null, 'sys:config:add', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (170, 73, '修改', null, 'core', null, 'sys:config:edit', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (171, 73, '删除', null, 'core', null, 'sys:config:del', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (173, 2, '密码重置', null, 'core', null, 'sys:user:resetPwd', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (174, 0, '研发工具', null, 'core', null, null, 0, 'fa fa-code', 9, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (175, 174, '代码生成', 'tool/gen', 'core', '_tab', 'tool:gen:view', 1, 'fa fa-terminal', 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (176, 175, '查看', null, 'core', null, 'tool:gen:list', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (177, 175, '代码生成', null, 'core', null, 'tool:gen:code', 2, null, 1, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (178, 175, '预览', null, 'core', null, 'tool:gen:preview', 2, null, 2, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (179, 175, '修改', null, 'core', null, 'tool:gen:edit', 2, null, 3, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (181, 175, '删除', null, 'core', null, 'tool:gen:del', 2, null, 4, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (182, 72, '详情', null, 'core', null, 'sys:job:detail', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (183, 72, '日志删除', null, 'core', null, 'sys:joblog:del', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (184, 72, '日志清空', null, 'core', null, 'sys:joblog:clean', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (186, 174, '图标样式', 'tool/icon', 'core', '_alert', 'tool:icon:view', 1, 'fa fa-hashtag', 1, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (189, 70, '模块管理', 'sys/module', 'core', '_tab', 'sys:module:view', 1, 'fa fa-cogs', 2, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (190, 189, '查看', null, 'core', null, 'sys:module:list', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (191, 189, '新增', null, 'core', null, 'sys:module:add', 2, null, 1, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (192, 189, '修改', null, 'core', null, 'sys:module:edit', 2, null, 2, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (193, 189, '删除', null, 'core', null, 'sys:module:del', 2, null, 3, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (194, 94, '授权', null, 'core', null, 'sys:role:authorization', 2, null, 5, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
commit;
prompt 100 records committed...
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (195, 3, '分配用户', null, 'core', null, 'sys:role:authUserList', 2, null, 5, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (196, 2, '授权角色', null, 'core', null, 'sys:user:authRoleList', 2, null, 6, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (197, 1, '岗位管理', 'sys/post', 'core,bcs', '_tab', 'sys:post:view', 1, 'fa fa-address-card-o', 5, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '岗位信息菜单');
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (198, 197, '查询', '#', 'core,bcs', null, 'sys:post:list', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (199, 197, '新增', '#', 'core,bcs', null, 'sys:post:add', 2, null, 1, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (200, 197, '修改', '#', 'core,bcs', null, 'sys:post:edit', 2, null, 2, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (201, 197, '删除', '#', 'core,bcs', null, 'sys:post:del', 2, null, 3, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (202, 1, '员工管理', 'bcs/user', 'bcs', '_tab', 'bcs:user:view', 1, 'fa fa-user-o', 5, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (203, 202, '查看', null, 'bcs', null, 'bcs:user:list,sys:dept:select', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (204, 202, '新增', null, 'bcs', null, 'bcs:user:add', 2, null, 1, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (205, 202, '修改', null, 'bcs', null, 'bcs:user:edit', 2, null, 2, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (206, 202, '删除', null, 'bcs', null, 'bcs:user:del', 2, null, 3, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (1241023067553239042, 70, '产品许可', 'sys/license', 'core', '_tab', 'sys:license:view', 1, 'fa fa-send-o', 13, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (1241023310755762177, 1241023067553239042, '上传许可', null, 'core', null, 'sys:license:upload', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (1241023877494312961, 1241023067553239042, '生成许可', null, 'core', null, 'sys:license:creatLicense', 2, null, 1, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (1241544436850233346, 0, '项目帮助', null, 'core', null, null, 0, 'fa icon-link', 11, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (1241551431233835010, 1241544436850233346, '在线提问', 'https://gitee.com/zhouhuanOGP/J2EEFAST/issues', 'core', '_tab', null, 1, 'fa fa-question-circle', 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (1241552388105572354, 1241544436850233346, '在线文档', 'https://gitee.com/zhouhuanOGP/J2EEFAST/wikis/pages', 'core', '_alert', null, 1, 'fa fa-wordpress', 1, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (1243014163867074562, 70, '公告管理', 'sys/notice', 'core', '_tab', 'sys:notice:view', 1, 'fa fa-bullhorn', 15, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (1243014380247023618, 1243014163867074562, '新增', null, 'core', null, 'sys:notice:add', 2, null, 0, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (1243014490183925762, 1243014163867074562, '修改', null, 'core', null, 'sys:notice:edit', 2, null, 1, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
insert into SYS_MENU (menu_id, parent_id, name, url, module_codes, target, perms, type, icon, order_num, hide, create_by, create_time, update_by, update_time, remark)
values (1243014687924387842, 1243014163867074562, '删除', null, 'core', null, 'sys:notice:del', 2, null, 3, 0, 'admin', to_timestamp('01-12-2019 02:54:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null);
commit;
prompt 122 records loaded
prompt Loading SYS_MODULE...
insert into SYS_MODULE (id, module_code, module_name, description, main_class_name, icon, current_version, upgrade_info, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (1, 'core', '核心模块', '用户、角色、组织、模块、菜单、字典、参数、系统相关功能', 'com.j2eefast.modules.sys.controller.SysLoginController', 'fa  icon-settings', '2.0.2', null, '0', '0', 'admin', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '初始版本');
insert into SYS_MODULE (id, module_code, module_name, description, main_class_name, icon, current_version, upgrade_info, del_flag, status, create_by, create_time, update_by, update_time, remark)
values (2, 'bcs', '公交系统', '公交项目系统功能 终端、交易、库房等功能', 'com.lixinos.bcs.common.config.InitializeConfig', 'fa fa-lastfm', '2.0.2', null, '0', '2', 'admin', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '初始版本');
commit;
prompt 2 records loaded
prompt Loading SYS_NOTICE...
prompt Table is empty
prompt Loading SYS_POST...
insert into SYS_POST (post_id, post_code, post_name, post_sort, post_type, status, create_by, create_time, update_by, update_time, remark)
values (1, 'CEO', '董事长', 1, '1', '0', 'admin', to_timestamp('15-03-2020 16:31:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('15-03-2020 16:31:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_POST (post_id, post_code, post_name, post_sort, post_type, status, create_by, create_time, update_by, update_time, remark)
values (2, 'SE', '项目经理', 2, '1', '0', 'admin', to_timestamp('15-03-2020 16:31:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('15-03-2020 16:31:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_POST (post_id, post_code, post_name, post_sort, post_type, status, create_by, create_time, update_by, update_time, remark)
values (3, 'HR', '人力资源', 3, '1', '0', 'admin', to_timestamp('15-03-2020 16:31:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('15-03-2020 16:31:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into SYS_POST (post_id, post_code, post_name, post_sort, post_type, status, create_by, create_time, update_by, update_time, remark)
values (4, 'USER', '普通员工', 4, '3', '0', 'admin', to_timestamp('15-03-2020 16:31:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('15-03-2020 16:31:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
commit;
prompt 4 records loaded
prompt Loading SYS_ROLE...
insert into SYS_ROLE (role_id, role_name, dept_id, create_time, role_key, data_scope, role_sort, del_flag, status, create_by, update_by, update_time, remark)
values (1, '最大管理员', 1, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ADMIN', null, 0, '0', '0', null, 'admin', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '最高级别管理员');
insert into SYS_ROLE (role_id, role_name, dept_id, create_time, role_key, data_scope, role_sort, del_flag, status, create_by, update_by, update_time, remark)
values (2, '公司管理角色', 3, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'COMP', null, 0, '0', '0', null, 'admin', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '公司权限管理');
insert into SYS_ROLE (role_id, role_name, dept_id, create_time, role_key, data_scope, role_sort, del_flag, status, create_by, update_by, update_time, remark)
values (3, '地区管理用户', null, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'DEPT', null, 0, '0', '0', null, 'admin', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '管理地区终端');
insert into SYS_ROLE (role_id, role_name, dept_id, create_time, role_key, data_scope, role_sort, del_flag, status, create_by, update_by, update_time, remark)
values (4, '终端管理角色', null, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'MACH', null, 0, '0', '0', null, 'admin', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '终端限角色');
insert into SYS_ROLE (role_id, role_name, dept_id, create_time, role_key, data_scope, role_sort, del_flag, status, create_by, update_by, update_time, remark)
values (6, '公交司机', null, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'SIJI', null, 0, '0', '0', null, 'admin', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '公交司机');
insert into SYS_ROLE (role_id, role_name, dept_id, create_time, role_key, data_scope, role_sort, del_flag, status, create_by, update_by, update_time, remark)
values (7, '库房管理', null, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'KUFANG', null, 0, '0', '0', null, 'admin', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '库房管理');
insert into SYS_ROLE (role_id, role_name, dept_id, create_time, role_key, data_scope, role_sort, del_flag, status, create_by, update_by, update_time, remark)
values (8, '测试角色', null, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'TEST', null, 0, '0', '0', 'admin', 'admin', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '测试角色');
commit;
prompt 7 records loaded
prompt Loading SYS_ROLE_DEPT...
insert into SYS_ROLE_DEPT (id, role_id, dept_id)
values (230, 2, 3);
insert into SYS_ROLE_DEPT (id, role_id, dept_id)
values (231, 2, 4);
insert into SYS_ROLE_DEPT (id, role_id, dept_id)
values (232, 2, 5);
insert into SYS_ROLE_DEPT (id, role_id, dept_id)
values (233, 1, 2);
insert into SYS_ROLE_DEPT (id, role_id, dept_id)
values (234, 1, 3);
insert into SYS_ROLE_DEPT (id, role_id, dept_id)
values (235, 1, 4);
insert into SYS_ROLE_DEPT (id, role_id, dept_id)
values (236, 1, 5);
insert into SYS_ROLE_DEPT (id, role_id, dept_id)
values (237, 1, 7);
insert into SYS_ROLE_DEPT (id, role_id, dept_id)
values (238, 1, 8);
insert into SYS_ROLE_DEPT (id, role_id, dept_id)
values (239, 1, 9);
commit;
prompt 10 records loaded
prompt Loading SYS_ROLE_MENU...
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569432977409, 1, 1);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569445560321, 1, 158);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569445560322, 1, 159);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569445560323, 1, 41);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569445560324, 1, 45);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569453948929, 1, 44);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569453948930, 1, 43);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569453948931, 1, 42);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569453948932, 1, 140);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569462337538, 1, 142);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569462337539, 1, 141);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569462337540, 1, 144);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569462337541, 1, 143);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569462337542, 1, 2);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569462337543, 1, 15);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569462337544, 1, 173);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569470726145, 1, 18);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569470726146, 1, 17);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569470726147, 1, 16);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569470726148, 1, 196);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569479114754, 1, 3);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569479114755, 1, 21);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569479114756, 1, 20);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569479114757, 1, 19);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569479114758, 1, 22);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569479114759, 1, 195);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569479114760, 1, 197);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569487503361, 1, 198);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569487503362, 1, 199);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569491697666, 1, 200);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569491697667, 1, 201);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569491697668, 1, 70);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569491697669, 1, 94);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569491697670, 1, 96);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569491697671, 1, 98);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569500086274, 1, 97);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569500086275, 1, 161);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569500086276, 1, 194);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569500086277, 1, 189);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569504280578, 1, 190);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569504280579, 1, 191);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569504280580, 1, 192);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569504280581, 1, 193);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569504280582, 1, 118);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569504280583, 1, 128);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569504280584, 1, 76);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569512669186, 1, 125);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569512669187, 1, 127);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569512669188, 1, 126);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569512669189, 1, 73);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569512669190, 1, 168);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569512669191, 1, 171);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569516863490, 1, 170);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569516863491, 1, 169);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569516863492, 1, 77);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569516863493, 1, 160);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569516863494, 1, 89);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569516863495, 1, 88);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569516863496, 1, 87);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569516863497, 1, 86);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569516863498, 1, 72);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569516863499, 1, 182);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569516863500, 1, 85);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569525252098, 1, 84);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569525252099, 1, 83);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569525252100, 1, 82);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569525252101, 1, 81);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569525252102, 1, 184);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569525252103, 1, 80);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569525252104, 1, 183);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569525252105, 1, 78);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569525252106, 1, 79);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569533640706, 1, 135);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569533640707, 1, 137);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569533640708, 1, 136);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569533640709, 1, 1241023067553239042);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569533640710, 1, 1241023310755762177);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569533640711, 1, 1241023877494312961);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569533640712, 1, 1243014163867074562);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569533640713, 1, 1243014380247023618);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569533640714, 1, 1243014490183925762);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569542029313, 1, 1243014687924387842);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569542029314, 1, 130);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569542029315, 1, 131);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569542029316, 1, 162);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569542029317, 1, 163);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569542029318, 1, 132);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569542029319, 1, 166);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569542029320, 1, 165);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569542029321, 1, 164);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569542029322, 1, 68);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569542029323, 1, 99);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569550417921, 1, 167);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569550417922, 1, 101);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569550417923, 1, 69);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569550417924, 1, 129);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569550417925, 1, 108);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569550417926, 1, 109);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569550417927, 1, 180);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569558806530, 1, 145);
commit;
prompt 100 records committed...
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569558806531, 1, 110);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569558806532, 1, 111);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569558806533, 1, 112);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569558806534, 1, 114);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569558806535, 1, 115);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569558806536, 1, 116);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569567195137, 1, 174);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569567195138, 1, 175);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569567195139, 1, 176);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569567195140, 1, 177);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569567195141, 1, 178);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569567195142, 1, 179);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569567195143, 1, 181);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569567195144, 1, 186);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569567195145, 1, 1241544436850233346);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569567195146, 1, 1241551431233835010);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1243849569575583745, 1, 1241552388105572354);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675600642050, 8, 70);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675600642051, 8, 94);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675609030657, 8, 98);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675609030658, 8, 97);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675609030659, 8, 161);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675609030660, 8, 96);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675609030661, 8, 194);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675609030662, 8, 189);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675609030663, 8, 190);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675609030664, 8, 191);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675617419265, 8, 192);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675617419266, 8, 193);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675617419267, 8, 118);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675617419268, 8, 128);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675617419269, 8, 76);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675617419270, 8, 127);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675617419271, 8, 126);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675617419272, 8, 125);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675617419273, 8, 73);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675617419274, 8, 171);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675625807874, 8, 170);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675625807875, 8, 169);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675625807876, 8, 168);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675625807877, 8, 77);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675625807878, 8, 88);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675625807879, 8, 87);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675625807880, 8, 86);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675625807881, 8, 160);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675634196481, 8, 89);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675634196482, 8, 135);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675634196483, 8, 137);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675634196484, 8, 136);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675634196485, 8, 72);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675634196486, 8, 81);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675638390785, 8, 184);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675638390786, 8, 80);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675638390787, 8, 183);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675646779394, 8, 79);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675646779395, 8, 85);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675655168002, 8, 182);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675655168003, 8, 84);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675655168004, 8, 83);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675659362306, 8, 78);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675659362307, 8, 82);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675659362308, 8, 1241023067553239042);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675659362309, 8, 1241023310755762177);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675659362310, 8, 1241023877494312961);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675667750913, 8, 1243014163867074562);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675667750914, 8, 1243014380247023618);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675667750915, 8, 1243014490183925762);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675667750916, 8, 1243014687924387842);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675667750917, 8, 130);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675667750918, 8, 132);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675667750919, 8, 165);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675667750920, 8, 164);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675667750921, 8, 166);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675667750922, 8, 131);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675667750923, 8, 162);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675676139521, 8, 163);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675676139522, 8, 68);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675676139523, 8, 99);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675676139524, 8, 167);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675676139525, 8, 101);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675676139526, 8, 69);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248143675676139527, 8, 129);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144483671056385, 7, 130);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144483683639298, 7, 131);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144483683639299, 7, 162);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144483683639300, 7, 163);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144483683639301, 7, 132);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144483683639302, 7, 166);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144483683639303, 7, 165);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144483683639304, 7, 164);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144483683639305, 7, 68);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144483683639306, 7, 99);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144483683639307, 7, 167);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144483692027905, 7, 101);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144483692027906, 7, 69);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144483692027907, 7, 129);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511634481154, 6, 1);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511642869762, 6, 2);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511642869763, 6, 16);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511647064066, 6, 15);
commit;
prompt 200 records committed...
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511647064067, 6, 68);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511647064068, 6, 99);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511647064069, 6, 167);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511647064070, 6, 101);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511647064071, 6, 69);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511647064072, 6, 129);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511647064073, 6, 108);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511647064074, 6, 109);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511647064075, 6, 180);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511647064076, 6, 145);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511655452674, 6, 110);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511655452675, 6, 111);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144511655452676, 6, 112);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561873854466, 2, 1);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243074, 2, 41);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243075, 2, 44);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243076, 2, 43);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243077, 2, 140);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243078, 2, 141);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243079, 2, 144);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243080, 2, 143);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243081, 2, 142);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243082, 2, 2);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243083, 2, 18);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243084, 2, 17);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243085, 2, 16);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243086, 2, 15);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243087, 2, 70);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243088, 2, 72);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243089, 2, 84);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243090, 2, 83);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561882243091, 2, 82);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561890631681, 2, 81);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561890631682, 2, 80);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561890631683, 2, 78);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561890631684, 2, 79);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561890631685, 2, 182);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561890631686, 2, 85);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561890631687, 2, 130);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561890631688, 2, 132);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561890631689, 2, 164);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561894825986, 2, 68);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561894825987, 2, 99);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561894825988, 2, 101);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561894825989, 2, 108);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561894825990, 2, 109);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561894825991, 2, 110);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144561894825992, 2, 111);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144591053627394, 4, 130);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144591057821697, 4, 131);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144591057821698, 4, 68);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144591057821699, 4, 108);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144591062016001, 4, 109);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144591062016002, 4, 110);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144591062016003, 4, 111);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634666000386, 3, 1);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634666000387, 3, 140);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674388993, 3, 141);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674388994, 3, 144);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674388995, 3, 143);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674388996, 3, 142);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674388997, 3, 2);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674388998, 3, 18);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674388999, 3, 17);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674389000, 3, 16);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674389001, 3, 15);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674389002, 3, 3);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674389003, 3, 21);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674389004, 3, 20);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674389005, 3, 19);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674389006, 3, 22);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674389007, 3, 130);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674389008, 3, 131);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674389009, 3, 132);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674389010, 3, 68);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634674389011, 3, 99);
insert into SYS_ROLE_MENU (id, role_id, menu_id)
values (1248144634682777602, 3, 101);
commit;
prompt 277 records loaded
prompt Loading SYS_ROLE_MODULE...
insert into SYS_ROLE_MODULE (id, role_id, module_code)
values (1, 0, 'core');
insert into SYS_ROLE_MODULE (id, role_id, module_code)
values (2, 0, 'bcs');
insert into SYS_ROLE_MODULE (id, role_id, module_code)
values (1243849569814659074, 1, 'core');
insert into SYS_ROLE_MODULE (id, role_id, module_code)
values (1248143675948769282, 8, 'core');
insert into SYS_ROLE_MODULE (id, role_id, module_code)
values (1248144483830439937, 7, 'core');
insert into SYS_ROLE_MODULE (id, role_id, module_code)
values (1248144511814836225, 6, 'core');
insert into SYS_ROLE_MODULE (id, role_id, module_code)
values (1248144562087763970, 2, 'core');
insert into SYS_ROLE_MODULE (id, role_id, module_code)
values (1248144591179456514, 4, 'core');
insert into SYS_ROLE_MODULE (id, role_id, module_code)
values (1248144634837966849, 3, 'core');
insert into SYS_ROLE_MODULE (id, role_id, module_code)
values (1248613427259342849, 1248613019166146562, 'core');
insert into SYS_ROLE_MODULE (id, role_id, module_code)
values (1248614868602560514, 1248614843285741570, 'core');
commit;
prompt 11 records loaded
prompt Loading SYS_USER...
insert into SYS_USER (user_id, username, name, password, salt, email, mobile, status, del_flag, pwd_security_level, pwd_update_date, comp_id, dept_id, create_time, card_id, avatar, update_time, update_by, create_by, remark)
values (1, 'admin', '超级管理员', '77191ae6693e6db3e9ac44d57059bc046a5e93633b6700e95829b71c3b70dbfc', '9rAjzhlpiE4cKEzJyy85', '18774995071@163.com', '18774995071', '0', '0', '2', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 1, to_timestamp('11-05-2018 11:11:11.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, '/profile/avatar/2020/03/28/9568481d5b04c22cb0805b89e5a9f869.png/', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', null, null);
insert into SYS_USER (user_id, username, name, password, salt, email, mobile, status, del_flag, pwd_security_level, pwd_update_date, comp_id, dept_id, create_time, card_id, avatar, update_time, update_by, create_by, remark)
values (1239914383204892673, '00213', '张三', '5cdfc4a3e661948a6f93f385fab4699e9ace521c4e0805420d172e5740d34341', 'HXdhsZrJjnok8iHrIB4r', '18774994072@163.com', '18774994072', '1', '0', '2', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1236303334564106350, null, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null, null, 'admin', null);
insert into SYS_USER (user_id, username, name, password, salt, email, mobile, status, del_flag, pwd_security_level, pwd_update_date, comp_id, dept_id, create_time, card_id, avatar, update_time, update_by, create_by, remark)
values (1239915009632583681, '00214', '李四', '4a47fa6905203f74987cb944b70046fec94b31a6093795cf27495730d982f866', 'ag6XnFV1GZFh9mkkK07g', '18772321312@163.com', '18772321312', '0', '0', '2', null, 1239875326212898817, null, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, to_timestamp('03-05-2020 23:44:51.048000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', 'admin', null);
insert into SYS_USER (user_id, username, name, password, salt, email, mobile, status, del_flag, pwd_security_level, pwd_update_date, comp_id, dept_id, create_time, card_id, avatar, update_time, update_by, create_by, remark)
values (1241025732404285442, '00215', '张飞', 'ce569a6848f33c6a83456cd13a01856ce87ffa20782589209baa07d46c21a5f8', 'Q5KYgikJnbyNVIfBSa7y', '751312449@qq.com', '18774994013', '0', '0', '3', null, 1241024147242586113, null, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', 'admin', null);
insert into SYS_USER (user_id, username, name, password, salt, email, mobile, status, del_flag, pwd_security_level, pwd_update_date, comp_id, dept_id, create_time, card_id, avatar, update_time, update_by, create_by, remark)
values (1241025930912305153, '00216', '刘备', '75b1edd180537cfbfda48b9804be81b49611d90fd138f4052376f932c544300c', 'xtp0Y8cmxZrPry6grUjV', '123456@qq.com', '13234234234', '1', '0', '3', null, 1241024147242586113, null, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null, null, 'admin', null);
insert into SYS_USER (user_id, username, name, password, salt, email, mobile, status, del_flag, pwd_security_level, pwd_update_date, comp_id, dept_id, create_time, card_id, avatar, update_time, update_by, create_by, remark)
values (1241026155169157122, '00217', '关羽', '9222af358bf70643870652d1a2c9b2db95c198aae6287cf42970b375c1b2ddf0', '5vSTXoPBqTQZA4hJhUEQ', '123213@QQ.com', '18742342223', '1', '0', '2', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1236303334564106350, null, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, 'admin', null);
insert into SYS_USER (user_id, username, name, password, salt, email, mobile, status, del_flag, pwd_security_level, pwd_update_date, comp_id, dept_id, create_time, card_id, avatar, update_time, update_by, create_by, remark)
values (1241026312006766594, '00219', '曹操', '94f04f2fb68f262e9879a0b4dbe04c29567b4f5932b48d6862e7cc413de68ec0', 'kXpq77QShWyQyEKpLqkY', 'zhouhuan@lixinfintech.com', '15645645645', '0', '0', '2', to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1239875326212898817, null, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, to_timestamp('21-12-2019 23:54:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', 'admin', null);
commit;
prompt 7 records loaded
prompt Loading SYS_USER_COMP...
prompt Table is empty
prompt Loading SYS_USER_DEPT...
insert into SYS_USER_DEPT (id, user_id, dept_id)
values (1256973002463166465, 1239915009632583681, 1239913616460955650);
insert into SYS_USER_DEPT (id, user_id, dept_id)
values (1239913958166708226, 1239817862977224700, 1239913616460955650);
insert into SYS_USER_DEPT (id, user_id, dept_id)
values (1239914383976644610, 1239914383204892673, 1238849577246834689);
insert into SYS_USER_DEPT (id, user_id, dept_id)
values (1241025931860217857, 1241025930912305153, 1241024945540272130);
insert into SYS_USER_DEPT (id, user_id, dept_id)
values (1241026156259676161, 1241026155169157122, 1241024822970126337);
insert into SYS_USER_DEPT (id, user_id, dept_id)
values (1241026313185366018, 1241026312006766594, 1239913616460955650);
insert into SYS_USER_DEPT (id, user_id, dept_id)
values (1241026447092715521, 1, 1241024269615599618);
insert into SYS_USER_DEPT (id, user_id, dept_id)
values (1241026447214350338, 1, 1241024322249920514);
insert into SYS_USER_DEPT (id, user_id, dept_id)
values (1248422122168197122, 1241025732404285442, 1241024879228325889);
commit;
prompt 9 records loaded
prompt Loading SYS_USER_POST...
prompt Table is empty
prompt Loading SYS_USER_ROLE...
insert into SYS_USER_ROLE (id, user_id, role_id)
values (1256973002412834818, 1239915009632583681, 2);
insert into SYS_USER_ROLE (id, user_id, role_id)
values (1256973067659427841, 1239914383204892673, 1);
insert into SYS_USER_ROLE (id, user_id, role_id)
values (1256981503113654274, 1241026312006766594, 8);
insert into SYS_USER_ROLE (id, user_id, role_id)
values (1239913957210406914, 1239817862977224700, 8);
insert into SYS_USER_ROLE (id, user_id, role_id)
values (1241025931281403905, 1241025930912305153, 4);
insert into SYS_USER_ROLE (id, user_id, role_id)
values (1241025931289792514, 1241025930912305153, 7);
insert into SYS_USER_ROLE (id, user_id, role_id)
values (1241026446119636994, 1, 1);
insert into SYS_USER_ROLE (id, user_id, role_id)
values (1244985293918126082, 1241026155169157122, 2);
insert into SYS_USER_ROLE (id, user_id, role_id)
values (1248422122013007874, 1241025732404285442, 4);
insert into SYS_USER_ROLE (id, user_id, role_id)
values (1248422122038173698, 1241025732404285442, 6);
commit;
prompt 10 records loaded
prompt Enabling triggers for SYS_COMP...
alter table SYS_COMP enable all triggers;
prompt Enabling triggers for SYS_COMP_DEPT...
alter table SYS_COMP_DEPT enable all triggers;
prompt Enabling triggers for SYS_CONFIG...
alter table SYS_CONFIG enable all triggers;
prompt Enabling triggers for SYS_DEPT...
alter table SYS_DEPT enable all triggers;
prompt Enabling triggers for SYS_DICT_DATA...
alter table SYS_DICT_DATA enable all triggers;
prompt Enabling triggers for SYS_DICT_TYPE...
alter table SYS_DICT_TYPE enable all triggers;
prompt Enabling triggers for SYS_JOB...
alter table SYS_JOB enable all triggers;
prompt Enabling triggers for SYS_MENU...
alter table SYS_MENU enable all triggers;
prompt Enabling triggers for SYS_MODULE...
alter table SYS_MODULE enable all triggers;
prompt Enabling triggers for SYS_NOTICE...
alter table SYS_NOTICE enable all triggers;
prompt Enabling triggers for SYS_POST...
alter table SYS_POST enable all triggers;
prompt Enabling triggers for SYS_ROLE...
alter table SYS_ROLE enable all triggers;
prompt Enabling triggers for SYS_ROLE_DEPT...
alter table SYS_ROLE_DEPT enable all triggers;
prompt Enabling triggers for SYS_ROLE_MENU...
alter table SYS_ROLE_MENU enable all triggers;
prompt Enabling triggers for SYS_ROLE_MODULE...
alter table SYS_ROLE_MODULE enable all triggers;
prompt Enabling triggers for SYS_USER...
alter table SYS_USER enable all triggers;
prompt Enabling triggers for SYS_USER_COMP...
alter table SYS_USER_COMP enable all triggers;
prompt Enabling triggers for SYS_USER_DEPT...
alter table SYS_USER_DEPT enable all triggers;
prompt Enabling triggers for SYS_USER_POST...
alter table SYS_USER_POST enable all triggers;
prompt Enabling triggers for SYS_USER_ROLE...
alter table SYS_USER_ROLE enable all triggers;
