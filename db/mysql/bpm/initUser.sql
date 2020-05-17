/*********此SQL针对之前下载过FastOS部署过的用户,核心数据库fastdb 只需执行此SQL就可以了*************/
/**flowable 工作流数据库 为分库管理所以单独开辟数据库**/
/*建库*/
CREATE USER 'fast_flowable'@'localhost' IDENTIFIED BY 'fast_flowable@123$';
CREATE USER 'fast_flowable'@'%' IDENTIFIED BY 'fast_flowable@123$';
CREATE DATABASE fast_flowabledb DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_bin;
GRANT ALL PRIVILEGES ON `fast_flowabledb`.* TO 'fast_flowable'@'%' IDENTIFIED BY 'fast_flowable@123$';
GRANT ALL PRIVILEGES ON `fast_flowabledb`.* TO 'fast_flowable'@'localhost' IDENTIFIED BY 'fast_flowable@123$';