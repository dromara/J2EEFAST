package com.j2eefast.common.db.context;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.j2eefast.common.core.config.properties.DruidProperties;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.db.dao.SysDataBaseDao;
import com.j2eefast.common.db.factory.AtomikosFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * <p>数据源的上下文容器</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 10:56
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Slf4j
public class DataSourceContext {
	/**
	 * 主数据源名称
	 */
	public static final String MASTER_DATASOURCE_NAME = "MASTER";//master

	public static final String FLOWABLE_DATASOURCE_NAME = "FLOWABLE";

	/**
	 * 数据源容器
	 */
	private static Map<String, DataSource> DATA_SOURCES = new ConcurrentHashMap<>();

	/**
	 * 数据源的配置容器
	 */
	private static Map<String, DruidProperties> DATA_SOURCES_CONF = new ConcurrentHashMap<>();


	/**
	 * xml资源文件 用于热加载
	 */
	private static Map<String, Resource[]>  MAPPERLOCATION  = new ConcurrentHashMap<>();
	private static Map<String, Long>  MAPPERBEFORETIME = new ConcurrentHashMap<>();
	/**
	 * 初始化所有dataSource
	 *
	 * @Date 2019-06-12 13:48
	 */
	public static void initDataSource(DruidProperties masterDataSourceProperties, DataSource dataSourcePrimary,
									  DruidProperties flowableProperties, DataSource flowableSourcePrimary) {

		//清空数据库中的主数据源信息
		new SysDataBaseDao(masterDataSourceProperties).deleteDatabaseInfo(MASTER_DATASOURCE_NAME);
		new SysDataBaseDao(masterDataSourceProperties).deleteDatabaseInfo(FLOWABLE_DATASOURCE_NAME);

		//初始化主数据源信息
		new SysDataBaseDao(masterDataSourceProperties).createMasterDatabaseInfo(masterDataSourceProperties,MASTER_DATASOURCE_NAME);

		if(ToolUtil.isNotEmpty(flowableProperties) && ToolUtil.isNotEmpty(flowableSourcePrimary)){
			new SysDataBaseDao(masterDataSourceProperties).createMasterDatabaseInfo(flowableProperties,FLOWABLE_DATASOURCE_NAME);
		}

		//从数据库中获取所有的数据源信息
		SysDataBaseDao dataBaseInfoDao = new SysDataBaseDao(masterDataSourceProperties);
		Map<String, DruidProperties> allDataBaseInfo = dataBaseInfoDao.getAllDataBaseInfo();

		//赋给全局变量
		DATA_SOURCES_CONF = allDataBaseInfo;

		//根据数据源信息初始化所有的DataSource
		for (Map.Entry<String, DruidProperties> entry : allDataBaseInfo.entrySet()) {

			String dbName = entry.getKey();
			DruidProperties druidProperties = entry.getValue();

			//如果是主数据源，不用初始化第二遍，如果是其他数据源就通过property初始化
			if (dbName.equalsIgnoreCase(MASTER_DATASOURCE_NAME)) {
				DATA_SOURCES_CONF.put(dbName, masterDataSourceProperties);
				DATA_SOURCES.put(dbName, dataSourcePrimary);
			}else if(dbName.equalsIgnoreCase(FLOWABLE_DATASOURCE_NAME) ){
				DATA_SOURCES_CONF.put(dbName, flowableProperties);
				DATA_SOURCES.put(dbName, flowableSourcePrimary);
			}
			else {
				DataSource dataSource = createDataSource(dbName, druidProperties);
				DATA_SOURCES.put(dbName, dataSource);
			}
		}
	}

	/**
	 * 新增datasource
	 *
	 * @author zhouzhou
	 * @Date 2019-06-12 14:51
	 */
	public static void addDataSource(String dbName, DataSource dataSource) {
		DATA_SOURCES.put(dbName, dataSource);
	}

	public static void addMapperLocations(String dbName,Resource... mapperLocations) {
		MAPPERLOCATION.put(dbName,mapperLocations);
	}

	public static Map<String, Resource[]> getMapperLocations() {
		return MAPPERLOCATION;
	}

	public static void addBeforeTime(String dbName, Long time){
		MAPPERBEFORETIME.put(dbName,time);
	}

	public static Map<String, Long> getBeforeTime() {
		return MAPPERBEFORETIME;
	}

	public static void removeByName(String dbName){
		if(!dbName.equalsIgnoreCase(MASTER_DATASOURCE_NAME)|| !dbName.equalsIgnoreCase(FLOWABLE_DATASOURCE_NAME) ) {
			DATA_SOURCES_CONF.remove(dbName);
			DATA_SOURCES.remove(dbName);
			SqlSessionFactoryContext.getSqlSessionFactorys().remove(dbName);
		}
	}


	/**
	 * 获取数据源
	 *
	 * @author zhouzhou
	 * @Date 2019-06-12 13:50
	 */
	public static Map<String, DataSource> getDataSources() {
		return DATA_SOURCES;
	}

	/**
	 * 获取数据源的配置
	 *
	 * @author zhouzhou
	 * @Date 2019-06-18 19:26
	 */
	public static Map<String, DruidProperties> getDataSourcesConfs() {
		return DATA_SOURCES_CONF;
	}

	/**
	 * 数据源创建模板
	 */
	public static DataSource createDataSource(String dataSourceName, DruidProperties druidProperties) {

		//添加到全局配置里
		DATA_SOURCES_CONF.put(dataSourceName, druidProperties);

		return AtomikosFactory.create(dataSourceName, druidProperties);
	}
	
	
	/**
	* @Title: getDefaultDataSource 
	* @Description: 获取系统缺省的数据源
	* @return  DataSource 
	* @author mfksn001@163.com
	* @Date: 2020年5月30日
	 */
	public static DataSource getDefaultDataSource() {
		
		return DATA_SOURCES.get(MASTER_DATASOURCE_NAME);
	}
}
