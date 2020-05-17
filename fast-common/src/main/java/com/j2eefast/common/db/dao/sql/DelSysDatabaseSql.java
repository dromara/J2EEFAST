package com.j2eefast.common.db.dao.sql;

import lombok.Getter;

/**
 * <p>删除数据库源</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 13:37
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Getter
public class DelSysDatabaseSql extends AbstractSql {
	@Override
	protected String mysql() {
		return "DELETE from sys_database where db_name = ?";
	}

	@Override
	protected String sqlServer() {
		return "DELETE from sys_database where db_name = ?";
	}

	@Override
	protected String pgSql() {
		return "DELETE from sys_database where db_name = ?";
	}

	@Override
	protected String oracle() {
		return "DELETE from sys_database where db_name = ?";
	}
}
