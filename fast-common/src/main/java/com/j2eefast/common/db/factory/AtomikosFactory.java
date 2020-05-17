package com.j2eefast.common.db.factory;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jdbc.AtomikosSQLException;
import com.j2eefast.common.core.config.properties.DruidProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * <p>数据源工厂</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 11:10
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Slf4j
public class AtomikosFactory {

	public static AtomikosDataSourceBean create(String dataSourceName, DruidProperties druidProperties) {
		AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
		atomikosDataSourceBean.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
		atomikosDataSourceBean.setUniqueResourceName(dataSourceName);
		atomikosDataSourceBean.setMaxPoolSize(100);
		atomikosDataSourceBean.setBorrowConnectionTimeout(60);
		Properties properties = druidProperties.createProperties();
		atomikosDataSourceBean.setXaProperties(properties);
		try {
			atomikosDataSourceBean.init();
		} catch (AtomikosSQLException e) {
			log.error("数据库加载失败!",e);
		}
		return atomikosDataSourceBean;
	}
}
