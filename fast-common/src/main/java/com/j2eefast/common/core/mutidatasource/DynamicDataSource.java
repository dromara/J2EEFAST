package com.j2eefast.common.core.mutidatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

import javax.sql.DataSource;
/**
 * 动态数据源 
 * @author zhouzhou
 * @date 2020-03-12 09:53
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	
	public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources)
    {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }
	
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDataSourceType();
	}
	
}
