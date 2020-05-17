package com.j2eefast.common.db.collector;

import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.db.context.DataSourceContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import org.springframework.core.io.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Consumer;
/**
 * <p>mybatis配置收集</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 14:12
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
@Slf4j
@Configuration
public class SqlSessionFactoryCreator {

	private final MybatisPlusProperties properties;

	private final Interceptor[] interceptors;

	private final TypeHandler[] typeHandlers;

	private final LanguageDriver[] languageDrivers;

	private final ResourceLoader resourceLoader;

	private final DatabaseIdProvider databaseIdProvider;

	private final List<ConfigurationCustomizer> configurationCustomizers;

	private final List<MybatisPlusPropertiesCustomizer> mybatisPlusPropertiesCustomizers;

	private final ApplicationContext applicationContext;

	public SqlSessionFactoryCreator(MybatisPlusProperties properties,
									ObjectProvider<Interceptor[]> interceptorsProvider,
									ObjectProvider<TypeHandler[]> typeHandlersProvider,
									ObjectProvider<LanguageDriver[]> languageDriversProvider,
									ResourceLoader resourceLoader,
									ObjectProvider<DatabaseIdProvider> databaseIdProvider,
									ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider,
									ObjectProvider<List<MybatisPlusPropertiesCustomizer>> mybatisPlusPropertiesCustomizerProvider,
									ApplicationContext applicationContext) {
		this.properties = properties;
		this.interceptors = interceptorsProvider.getIfAvailable();
		this.typeHandlers = typeHandlersProvider.getIfAvailable();
		this.languageDrivers = languageDriversProvider.getIfAvailable();
		this.resourceLoader = resourceLoader;
		this.databaseIdProvider = databaseIdProvider.getIfAvailable();
		this.configurationCustomizers = configurationCustomizersProvider.getIfAvailable();
		this.mybatisPlusPropertiesCustomizers = mybatisPlusPropertiesCustomizerProvider.getIfAvailable();
		this.applicationContext = applicationContext;
	}

	protected static Properties databaseTypeMappings = getDefaultDatabaseTypeMappings();

	public static final String DATABASE_TYPE_H2 = "h2";
	public static final String DATABASE_TYPE_HSQL = "hsql";
	public static final String DATABASE_TYPE_MYSQL = "mysql";
	public static final String DATABASE_TYPE_ORACLE = "oracle";
	public static final String DATABASE_TYPE_POSTGRES = "postgres";
	public static final String DATABASE_TYPE_MSSQL = "mssql";
	public static final String DATABASE_TYPE_DB2 = "db2";

	public static Properties getDefaultDatabaseTypeMappings() {
		Properties databaseTypeMappings = new Properties();
		databaseTypeMappings.setProperty("H2", DATABASE_TYPE_H2);
		databaseTypeMappings.setProperty("HSQL Database Engine", DATABASE_TYPE_HSQL);
		databaseTypeMappings.setProperty("MySQL", DATABASE_TYPE_MYSQL);
		databaseTypeMappings.setProperty("Oracle", DATABASE_TYPE_ORACLE);
		databaseTypeMappings.setProperty("PostgreSQL", DATABASE_TYPE_POSTGRES);
		databaseTypeMappings.setProperty("Microsoft SQL Server", DATABASE_TYPE_MSSQL);
		databaseTypeMappings.setProperty(DATABASE_TYPE_DB2, DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/NT", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/NT64", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2 UDP", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/LINUX", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/LINUX390", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/LINUXX8664", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/LINUXZ64", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/LINUXPPC64", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/400 SQL", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/6000", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2 UDB iSeries", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/AIX64", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/HPUX", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/HP64", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/SUN", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/SUN64", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/PTX", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2/2", DATABASE_TYPE_DB2);
		databaseTypeMappings.setProperty("DB2 UDB AS400", DATABASE_TYPE_DB2);
		return databaseTypeMappings;
	}

	/**
	 * 创建SqlSessionFactory
	 */
	public synchronized SqlSessionFactory createSqlSessionFactory(DataSource dataSource, String dbName){

		//初始化本MybatisSqlSessionFactoryBean需要的配置，如果不初始化，则所有MybatisSqlSessionFactoryBean都用一套配置，会出现mapper语句差不不进去（因为mp的缓存）
		MybatisConfiguration originConfiguration = properties.getConfiguration();
		GlobalConfig originGlobalConfig = properties.getGlobalConfig();

		//清空mapper的缓存
		originGlobalConfig.setMapperRegistryCache(new ConcurrentSkipListSet<>());

		//创建新的配置
		MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
		GlobalConfig globalConfig = GlobalConfigUtils.defaults();

		//执行拷贝操作
		BeanUtil.copyProperties(originConfiguration, mybatisConfiguration, CopyOptions.create().ignoreError());
		BeanUtil.copyProperties(originGlobalConfig, globalConfig, CopyOptions.create().ignoreError());



		mybatisConfiguration.setGlobalConfig(globalConfig);

		// TODO 使用 MybatisSqlSessionFactoryBean 而不是 SqlSessionFactoryBean
		MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		factory.setVfs(SpringBootVFS.class);
//		factory.setTypeAliasesPackage("com.baomidou.springboot.entity");
//		factory.setTypeEnumsPackage("com.baomidou.springboot.entity.enums");

		if (StringUtils.hasText(this.properties.getConfigLocation())) {
			factory.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
		}
		Properties properties = null;
		if (this.properties.getConfigurationProperties() != null) {
			properties = new Properties(this.properties.getConfigurationProperties());
		}else{
			properties = new Properties();
		}
//		try{
//			properties.put("dbType",JdbcUtils.getDbType(dataSource.getConnection().getMetaData().getURL()).getDb());
//		}catch (Exception e){
//			log.error("获取数据库类型异常",e);
//		}
		if(dbName.equals(DataSourceContext.FLOWABLE_DATASOURCE_NAME)){
			String databaseType = initDatabaseType(dataSource);
			properties.put("prefix", "");
			properties.put("blobType", "BLOB");
			properties.put("boolValue", "TRUE");
			try {
				properties.load(this.getClass().getClassLoader().getResourceAsStream("org/flowable/db/properties/" + databaseType + ".properties"));
			} catch (Exception e) {
				log.error("获取数据异常!",e);
			}
			factory.setConfigurationProperties(properties);
		}else{
			factory.setConfigurationProperties(properties);
		}

		if (!ObjectUtils.isEmpty(this.interceptors)) {
			factory.setPlugins(this.interceptors);
		}

		if (this.databaseIdProvider != null) {
			factory.setDatabaseIdProvider(this.databaseIdProvider);
		}else {
			DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
			Properties p = new Properties();
			p.setProperty("Oracle", "oracle");
			p.setProperty("MySQL", "mysql");
			p.setProperty("PostgreSQL", "postgresql");
			p.setProperty("DB2", "db2");
			p.setProperty("SQL Server", "sqlserver");
			databaseIdProvider.setProperties(p);
			factory.setDatabaseIdProvider(databaseIdProvider);
		}
		if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
			factory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
		}
		if (this.properties.getTypeAliasesSuperType() != null) {
			factory.setTypeAliasesSuperType(this.properties.getTypeAliasesSuperType());
		}
		if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
			factory.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
		}
		if (!ObjectUtils.isEmpty(this.typeHandlers)) {
			factory.setTypeHandlers(this.typeHandlers);
		}

		if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
			Resource[] resource= this.properties.resolveMapperLocations();
			if(dbName.equals(DataSourceContext.FLOWABLE_DATASOURCE_NAME)){
				Resource[] resource1 = new Resource[0];
				Resource[] resource2 = new Resource[0];
//				Resource[] resource3 = new Resource[0];
				try {
					resource1 = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).
							getResources("classpath*:/META-INF/modeler-mybatis-mappings/*.xml");
//					resource2  = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).
//							getResources("classpath*:flowable-mapper/**/*.xml");
//					resource3  = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).
//							getResources("classpath*:mapper/generator/*.xml");
					Resource[] mapperLocations = ArrayUtils.addAll(resource1, resource);
//					Resource[] mapper = ArrayUtils.addAll(mapperLocations,resource2);
					factory.setMapperLocations(mapperLocations);
				} catch (IOException e) {
					log.error("异常");
				}
			}else{
				factory.setMapperLocations(resource);
			}
		}

		// TODO 对源码做了一定的修改(因为源码适配了老旧的mybatis版本,但我们不需要适配)
		Class<? extends LanguageDriver> defaultLanguageDriver = this.properties.getDefaultScriptingLanguageDriver();
		if (!ObjectUtils.isEmpty(this.languageDrivers)) {
			factory.setScriptingLanguageDrivers(this.languageDrivers);
		}
		Optional.ofNullable(defaultLanguageDriver).ifPresent(factory::setDefaultScriptingLanguageDriver);

		// TODO 自定义枚举包
		if (StringUtils.hasLength(this.properties.getTypeEnumsPackage())) {
			factory.setTypeEnumsPackage(this.properties.getTypeEnumsPackage());
		}
		// TODO 注入填充器
		this.getBeanThen(MetaObjectHandler.class, globalConfig::setMetaObjectHandler);
		// TODO 注入主键生成器
		this.getBeanThen(IKeyGenerator.class, i -> globalConfig.getDbConfig().setKeyGenerator(i));
		// TODO 注入sql注入器
		this.getBeanThen(ISqlInjector.class, globalConfig::setSqlInjector);
		// TODO 注入ID生成器
		this.getBeanThen(IdentifierGenerator.class, globalConfig::setIdentifierGenerator);
		// TODO 设置 GlobalConfig 到 MybatisSqlSessionFactoryBean
		factory.setGlobalConfig(globalConfig);

		try {
			return factory.getObject();
		} catch (Exception e) {
			log.error("初始化SqlSessionFactory错误！", e);
			throw new RxcException("初始化SqlSessionFactory错误！","60001");
		}
	}

	/**
	 * 检查spring容器里是否有对应的bean,有则进行消费
	 *
	 * @param clazz    class
	 * @param consumer 消费
	 * @param <T>      泛型
	 */
	private <T> void getBeanThen(Class<T> clazz, Consumer<T> consumer) {
		if (this.applicationContext.getBeanNamesForType(clazz, false, false).length > 0) {
			consumer.accept(this.applicationContext.getBean(clazz));
		}
	}

	protected String initDatabaseType(DataSource dataSource) {
		String databaseType = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			String databaseProductName = databaseMetaData.getDatabaseProductName();
			log.info("database product name: '{}'", databaseProductName);
			databaseType = databaseTypeMappings.getProperty(databaseProductName);
			if (databaseType == null) {
				throw new RuntimeException("couldn't deduct database type from database product name '" + databaseProductName + "'");
			}
			log.info("using database type: {}", databaseType);

		} catch (SQLException e) {
			log.error("Exception while initializing Database connection", e);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Exception while closing the Database connection", e);
			}
		}

		return databaseType;
	}
}
