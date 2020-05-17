/********************************************************************
 * 建立用户 - 数据库
 * @author zhouzhou
 * @date 2018-11-14 23:28
 ********************************************************************/
-- 数据库建用户建表
-- 已system用户登录 或者你可以已能够创建用户权限的用户登录就可以
-- 查看表空间情况
select tablespace_name, file_id, file_name,
round(bytes/(1024*1024),0) total_space
from dba_data_files
order by tablespace_name

-- 创建表空间 大小可以自行修改
create tablespace PROF
logging
datafile '/opt/oracle/oradata/obak_prof_01.dbf' -- 注意:文件路径视环境而定
size 1500m
autoextend on
next 100m maxsize 10000m
extent management local;

ALTER TABLESPACE PROF ADD
DATAFILE '/opt/oracle/oradata/obak_prof_03.dbf' SIZE 1500M -- 注意:文件路径视环境而定
AUTOEXTEND ON NEXT 100M MAXSIZE 10000M;

-- 创建用户
drop user fast cascade;
create user fast identified by "fast@123$" DEFAULT TABLESPACE PROF;
-- 授权
GRANT
　　CREATE SESSION, CREATE ANY TABLE, CREATE ANY VIEW ,CREATE ANY INDEX, CREATE ANY sequence,CREATE ANY PROCEDURE,
　　ALTER ANY TABLE, ALTER ANY sequence,ALTER ANY PROCEDURE,
　　DROP ANY TABLE, DROP ANY sequence,DROP ANY VIEW, DROP ANY INDEX, DROP ANY PROCEDURE,
　　SELECT ANY TABLE, INSERT ANY TABLE, UPDATE ANY TABLE, DELETE ANY TABLE
TO fast;

grant sysdba to fast;
grant imp_full_database to fast;
grant unlimited tablespace to fast;