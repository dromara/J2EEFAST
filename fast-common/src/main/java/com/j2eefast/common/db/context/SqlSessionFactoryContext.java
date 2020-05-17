package com.j2eefast.common.db.context;

import com.j2eefast.common.core.config.properties.DruidProperties;
import com.j2eefast.common.core.utils.SpringUtil;
import com.j2eefast.common.db.collector.SqlSessionFactoryCreator;
import com.j2eefast.common.db.entity.SysDatabaseEntity;
import com.j2eefast.common.db.factory.DruidFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * <p>mybatis的sqlSessionFactory的上下文容器</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 14:08
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class SqlSessionFactoryContext {

	private static Map<Object, SqlSessionFactory> sqlSessionFactories = new ConcurrentHashMap<>();

	/**
	 * 添加sqlSessionFactory
	 *
	 * @author zhouzhou
	 * @Date 2019-06-12 15:28
	 */
	public static void addSqlSessionFactory(String name, SqlSessionFactory sqlSessionFactory) {
		sqlSessionFactories.put(name, sqlSessionFactory);
	}

	/**
	 * 添加sqlSessionFactory
	 *
	 * @author zhouzhou
	 * @Date 2019-06-12 15:28
	 */
	public static void addSqlSessionFactory(String name, SysDatabaseEntity database) {

		//创建properties
		DruidProperties druidProperties = DruidFactory.createDruidProperties(database);

		//创建dataSource
		DataSource dataSource = DataSourceContext.createDataSource(name, druidProperties);
		DataSourceContext.addDataSource(name, dataSource);

		//创建sqlSessionFactory
		SqlSessionFactoryCreator sqlSessionFactoryCreator = SpringUtil.getBean(SqlSessionFactoryCreator.class);
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryCreator.createSqlSessionFactory(dataSource,name);
		SqlSessionFactoryContext.addSqlSessionFactory(name, sqlSessionFactory);

		sqlSessionFactories.put(name, sqlSessionFactory);
	}

	/**
	 * 获取所有的sqlSessionFactory
	 *
	 * @author zhouzhou
	 * @Date 2019-06-12 13:49
	 */
	public static Map<Object, SqlSessionFactory> getSqlSessionFactorys() {
		return sqlSessionFactories;
	}


	/**
	 * 初始化数据库中的数据源的SqlSessionFactory
	 *
	 * @author zhouzhou
	 * @Date 2019-06-15 19:51
	 */
	public static void initBaseSqlSessionFactory(SqlSessionFactoryCreator sqlSessionFactoryCreator) {

		//获取数据库的数据源
		Map<String, DataSource> dataSources = DataSourceContext.getDataSources();

		//创建数据库中数据源的sqlSessionFactory
		for (Map.Entry<String, DataSource> entry : dataSources.entrySet()) {
			String dbName = entry.getKey();
			DataSource dataSource = entry.getValue();

			//如果是主数据源，跳过
			if (DataSourceContext.MASTER_DATASOURCE_NAME.equals(dbName) || DataSourceContext.FLOWABLE_DATASOURCE_NAME.equals(dbName)) {
				continue;
			} else {
				SqlSessionFactory sqlSessionFactory = sqlSessionFactoryCreator.createSqlSessionFactory(dataSource,dbName);
				SqlSessionFactoryContext.addSqlSessionFactory(dbName, sqlSessionFactory);
			}
		}

	}
}
