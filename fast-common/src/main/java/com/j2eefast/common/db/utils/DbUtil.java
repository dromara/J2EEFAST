package com.j2eefast.common.db.utils;

import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.db.dao.sql.AllTableListSql;
import com.j2eefast.common.db.entity.SysDatabaseEntity;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * <p>多源数据工具类</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-20 11:34
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Slf4j
public class DbUtil {

	/**
	 * 查询某个数据库连接的所有表
	 *
	 * @author zhouzhou
	 * @Date 2019-05-04 20:30
	 */
	public static List<Map<String, Object>> selectTables(SysDatabaseEntity dbInfo) {
		List<Map<String, Object>> tables = new ArrayList<>();
		try {
			Class.forName(dbInfo.getJdbcDriver());
			Connection conn = DriverManager.getConnection(
					dbInfo.getJdbcUrl(), dbInfo.getUserName(), dbInfo.getPassword());

			//获取数据库名称
			String dbName = getDbName(dbInfo);

			//构造查询语句
			PreparedStatement preparedStatement = conn.prepareStatement(new AllTableListSql().getSql(dbInfo.getJdbcUrl()));

			//拼接设置数据库名称
			if (!dbInfo.getJdbcUrl().contains("sqlserver") && !dbInfo.getJdbcUrl().contains("postgresql")) {
				preparedStatement.setString(1, dbName);
			}

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				HashMap<String, Object> map = new HashMap<>();
				String tableName = resultSet.getString("tableName");
				String tableComment = resultSet.getString("tableComment");
				Date updateTime = resultSet.getDate("updateTime");
				Date createTime = resultSet.getDate("createTime");
				map.put("tableName", tableName);
				map.put("tableComment", tableComment);
				map.put("updateTime", updateTime);
				map.put("createTime", createTime);
				tables.add(map);
			}
			return tables;
		} catch (Exception ex) {
			log.error("查询所有表错误！", ex);
			throw new RxcException("查询所有表错误！","60001");
		}
	}

	/**
	 * 获取数据库名称
	 *
	 * @author zhouzhou
	 * @Date 2019-06-18 15:25
	 */
	private static String getDbName(SysDatabaseEntity dbInfo) {

		if (dbInfo.getJdbcUrl().contains("oracle")) {

			//如果是oracle，直接返回username
			return dbInfo.getUserName();

		} else if (dbInfo.getJdbcUrl().contains("postgresql")) {

			//postgresql，直接返回最后一个/后边的字符
			int first = dbInfo.getJdbcUrl().lastIndexOf("/") + 1;
			return dbInfo.getJdbcUrl().substring(first);

		} else if (dbInfo.getJdbcUrl().contains("sqlserver")) {

			//sqlserver，直接返回最后一个=后边的字符
			int first = dbInfo.getJdbcUrl().lastIndexOf("=") + 1;
			return dbInfo.getJdbcUrl().substring(first);

		} else {

			//mysql，返回/和?之间的字符
			String jdbcUrl = dbInfo.getJdbcUrl();
			int first = jdbcUrl.lastIndexOf("/") + 1;
			int last = jdbcUrl.indexOf("?");
			return jdbcUrl.substring(first, last);
		}
	}

}
