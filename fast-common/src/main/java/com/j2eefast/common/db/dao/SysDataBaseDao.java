package com.j2eefast.common.db.dao;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.j2eefast.common.core.config.properties.DruidProperties;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.utils.JasyptUtils;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.db.context.DataSourceContext;
import com.j2eefast.common.db.dao.sql.AddSysDatabaseSql;
import com.j2eefast.common.db.dao.sql.DelSysDatabaseSql;
import com.j2eefast.common.db.dao.sql.SysDatabaseListSql;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>数据库源操作DAO</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 10:27
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Slf4j
public class SysDataBaseDao {


	@Value("${fast.encrypt.key: ''}")
	private String key;

	private DruidProperties druidProperties;

	public SysDataBaseDao(DruidProperties druidProperties) {
		this.druidProperties = druidProperties;
	}

	/**
	 * 查询所有数据源列表
	 */
	public Map<String, DruidProperties> getAllDataBaseInfo() {

		Map<String, DruidProperties> dataSourceList = new HashMap<>();

		try {
			Class.forName(druidProperties.getDriverClassName());
			Connection conn = DriverManager.getConnection(
					druidProperties.getUrl(), druidProperties.getUsername(), druidProperties.getPassword());

			PreparedStatement preparedStatement = conn.prepareStatement(new SysDatabaseListSql().getSql(druidProperties.getUrl()));
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				DruidProperties druidProperties = createDruidProperties(resultSet);
				String dbName = resultSet.getString("db_name");
				dataSourceList.put(dbName, druidProperties);
			}
			return dataSourceList;
		} catch (Exception ex) {
			throw new RxcException("查询数据库中数据源信息错误","60000");
		}
	}

	/**
	 * 初始化主数据源，要和yml配置的数据源一致
	 * 主数据库固定已ENC 存储数据库
	 */
	public void createMasterDatabaseInfo(DruidProperties properties ,String name) {
		try {
			Class.forName(druidProperties.getDriverClassName());
			Connection conn = DriverManager.getConnection(
					druidProperties.getUrl(), druidProperties.getUsername(), druidProperties.getPassword());

			PreparedStatement preparedStatement = conn.prepareStatement(new AddSysDatabaseSql().getSql(druidProperties.getUrl()));

			preparedStatement.setLong(1, IdWorker.getId());
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, properties.getDriverClassName());
			if(ToolUtil.isEmpty(key)){
				key = ConfigConstant.KEY;
			}
			preparedStatement.setString(4, JasyptUtils.encryptPwd(properties.getUsername(),key));
			preparedStatement.setString(5, JasyptUtils.encryptPwd(properties.getPassword(),key));
			preparedStatement.setString(6, JasyptUtils.encryptPwd(properties.getUrl(),key));
			preparedStatement.setString(7, "0");
			preparedStatement.setString(8, "0");
			preparedStatement.setString(9, "ENC");
			preparedStatement.setString(10, DateUtil.formatDateTime(new Date()));
			preparedStatement.setString(11, "system");
			preparedStatement.setString(12, name.equals(DataSourceContext.MASTER_DATASOURCE_NAME)?"主数据源，项目启动数据源！": "Flowable 工作流数据源");
			int i = preparedStatement.executeUpdate();
			log.info("初始化master的databaseInfo信息！初始化" + i + "条！");
		} catch (Exception ex) {
			log.error("初始化master的databaseInfo信息错误！", ex);
			throw new RxcException("查询数据库中数据源信息错误","60000");
		}
	}

	/**
	 * 删除master的数据源信息
	 */
	public void deleteDatabaseInfo(String name) {
		try {
			Class.forName(druidProperties.getDriverClassName());
			Connection conn = DriverManager.getConnection(
					druidProperties.getUrl(), druidProperties.getUsername(), druidProperties.getPassword());

			PreparedStatement preparedStatement = conn.prepareStatement(new DelSysDatabaseSql().getSql(druidProperties.getUrl()));
			preparedStatement.setString(1, name);
			int i = preparedStatement.executeUpdate();
			log.info("删除 default 的databaseInfo信息！删除" + i + "条！");
		} catch (Exception ex) {
			log.info("删除 default 的databaseInfo信息失败！", ex);
			throw new RxcException("查询数据库中数据源信息错误","60000");
		}
	}

	/**
	 * 通过查询结果组装druidProperties
	 */
	private DruidProperties createDruidProperties(ResultSet resultSet) {

		DruidProperties druidProperties = new DruidProperties();

		druidProperties.setTestOnBorrow(true);
		druidProperties.setTestOnReturn(true);

		try {
			druidProperties.setDriverClassName(resultSet.getString("jdbc_driver"));
			druidProperties.setUrl(resultSet.getString("jdbc_url"));
			druidProperties.setUsername(resultSet.getString("user_name"));
			druidProperties.setPassword(resultSet.getString("password"));
		} catch (SQLException e) {
			throw new RxcException("查询数据库中数据源信息错误","60000");
		}

		return druidProperties;
	}
}
