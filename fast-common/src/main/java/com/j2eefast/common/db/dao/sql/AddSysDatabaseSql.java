package com.j2eefast.common.db.dao.sql;

import lombok.Getter;

/**
 * <p>新增数据库源</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 10:43
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Getter
public class AddSysDatabaseSql extends AbstractSql {

	@Override
	protected String mysql() {
		return "INSERT INTO `sys_database`(`id`, `db_name`, `jdbc_driver`, `user_name`, `password`,`jdbc_url`, `status`,`del_flag`," +
				" `enc_type`, `create_time`,`create_by`,`remark`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	protected String sqlServer() {
		return "INSERT INTO [sys_database] ([id], [db_name], [jdbc_driver], [user_name], [password], [jdbc_url], [status], [del_flag]" +
				", [enc_type], [create_time], [create_by], [remark]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	protected String pgSql() {
		return "INSERT INTO sys_database(id, db_name, jdbc_driver, user_name, password, jdbc_url, status, del_flag" +
				", enc_type, create_time, create_by, remark) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'), ?, ?)";
	}

	@Override
	protected String oracle() {
		return "INSERT INTO sys_database (id, db_name, jdbc_driver, user_name, password, jdbc_url, status, del_flag" +
				", enc_type, create_time, create_by, remark) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, to_date(?,'YYYY-MM-DD HH24:MI:SS'), ?, ?)";
	}
}
