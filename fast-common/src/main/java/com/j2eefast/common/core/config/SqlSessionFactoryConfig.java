package com.j2eefast.common.core.config;

import com.j2eefast.common.core.config.properties.DruidProperties;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.mutidatasource.annotaion.mybatis.OptionalSqlSessionTemplate;
import com.j2eefast.common.core.utils.SpringUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.db.collector.SqlSessionFactoryCreator;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.j2eefast.common.db.context.DataSourceContext;
import com.j2eefast.common.db.context.SqlSessionFactoryContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;
/**
 * <p>多数据源配置</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 14:38
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Slf4j
@Configuration
@Import(SqlSessionFactoryCreator.class)
public class SqlSessionFactoryConfig {

	/**
	 * 主sqlSessionFactory
	 */
	@Primary
	@Bean
	public SqlSessionFactory sqlSessionFactoryPrimary(@Qualifier("dataSourcePrimary") DataSource dataSource,
													  SqlSessionFactoryCreator sqlSessionFactoryCreator) {
		return sqlSessionFactoryCreator.createSqlSessionFactory(dataSource,DataSourceContext.MASTER_DATASOURCE_NAME);
	}

	/**
	 * flowable sqlSessionFactory
	 */
	@Bean
	@ConditionalOnProperty(prefix = "fast.flowable", name = "enabled", havingValue = "true")
	public SqlSessionFactory sqlSessionfFowableFactoryPrimary(@Qualifier("flowableSourcePrimary") DataSource flowableSourcePrimary,
													  SqlSessionFactoryCreator sqlSessionFactoryCreator) {
		return sqlSessionFactoryCreator.createSqlSessionFactory(flowableSourcePrimary,DataSourceContext.FLOWABLE_DATASOURCE_NAME);
	}


	/**
	 * 多数据源sqlSessionTemplate切换模板
	 */
	@Bean(name = "fastSqlSessionTemplate")
	public OptionalSqlSessionTemplate fastSqlSessionTemplate(@Qualifier("dataSourcePrimary") DataSource dataSourcePrimary,
															 @Qualifier("sqlSessionFactoryPrimary") SqlSessionFactory sqlSessionFactoryPrimary,
															 @Qualifier("defaultProperties") DruidProperties druidProperties,
															 SqlSessionFactoryCreator sqlSessionFactoryCreator) {
		DataSource flowableSourcePrimary = null;
		SqlSessionFactory sqlSessionfFowableFactoryPrimary = null;
		DruidProperties flowableProperties = null;
		try{
			 flowableSourcePrimary = SpringUtil.getBean("flowableSourcePrimary");
			 sqlSessionfFowableFactoryPrimary = SpringUtil.getBean("sqlSessionfFowableFactoryPrimary");
			flowableProperties = SpringUtil.getBean("flowableProperties");
		}catch (Exception e){}

		//初始化数据源容器
		try {
			DataSourceContext.initDataSource(druidProperties, dataSourcePrimary,flowableProperties,flowableSourcePrimary);
		} catch (Exception e) {
			log.error("初始化数据源容器错误!", e);
			throw new RxcException("初始化数据源容器异常！","60000");
		}

		//添加主数据源的SqlSessionFactory
		SqlSessionFactoryContext.addSqlSessionFactory(DataSourceContext.MASTER_DATASOURCE_NAME, sqlSessionFactoryPrimary);

		if(ToolUtil.isNotEmpty(sqlSessionfFowableFactoryPrimary)){
			SqlSessionFactoryContext.addSqlSessionFactory(DataSourceContext.FLOWABLE_DATASOURCE_NAME, sqlSessionfFowableFactoryPrimary);
		}

		//初始化其他数据源的SqlSessionFactory的容器
		SqlSessionFactoryContext.initBaseSqlSessionFactory(sqlSessionFactoryCreator);

		//设置SqlHelper为主数据源
		SqlHelper.FACTORY = sqlSessionFactoryPrimary;

		return new OptionalSqlSessionTemplate(sqlSessionFactoryPrimary, SqlSessionFactoryContext.getSqlSessionFactorys());
	}
}
