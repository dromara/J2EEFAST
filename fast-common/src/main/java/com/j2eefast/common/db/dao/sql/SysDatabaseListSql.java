package com.j2eefast.common.db.dao.sql;

import lombok.Getter;

/**
 * <p>数据源SQL</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 10:31
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Getter
public class SysDatabaseListSql extends AbstractSql {

	@Override
	protected String mysql() {
		return "select db_name,jdbc_driver,jdbc_url,user_name,password,enc_type from sys_database where del_flag = '0' and status = '0'";
	}

	@Override
	protected String sqlServer() {
		return "select db_name,jdbc_driver,jdbc_url,user_name,password,enc_type from sys_database where del_flag = '0' and status = '0'";
	}

	@Override
	protected String pgSql() {
		return "select db_name,jdbc_driver,jdbc_url,user_name,password,enc_type from sys_database where del_flag = '0' and status = '0'";
	}

	@Override
	protected String oracle() {
		return "select db_name,jdbc_driver,jdbc_url,user_name,password,enc_type from sys_database where del_flag = '0' and status = '0'";
	}
}
