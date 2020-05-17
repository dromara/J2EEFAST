package com.j2eefast.common.db.dao.sql;

/**
 * <p>SQL抽象类</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 10:32
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public abstract class AbstractSql {

	/**
	 * 获取异构sql
	 */
	public String getSql(String jdbcUrl) {
		if (jdbcUrl.contains("oracle")) {
			return oracle();
		} else if (jdbcUrl.contains("postgresql")) {
			return pgSql();
		} else if (jdbcUrl.contains("sqlserver")) {
			return sqlServer();
		} else {
			return mysql();
		}
	}

	protected abstract String mysql();

	protected abstract String sqlServer();

	protected abstract String pgSql();

	protected abstract String oracle();

}
