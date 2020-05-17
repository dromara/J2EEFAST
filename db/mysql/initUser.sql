/********************************************************************
 * 建立用户 - 数据库
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