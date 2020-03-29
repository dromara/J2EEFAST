package com.fast.framework.config;

import com.fast.common.core.datasources.MybatisPulsMetaObjectHandler;
import com.fast.common.core.mutidatasource.annotaion.aop.MultiSourceAop;
import com.fast.framework.utils.ShiroUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;


/**
 * mybatis-plus配置
 */
@Configuration
@MapperScan(basePackages = {
        "com.fast.*.*.mapper",
        "com.fast.*.*.dao"})
public class MybatisPlusConfig {

//	@Bean
//	public PerformanceInterceptor performanceInterceptor() {
//		return new PerformanceInterceptor();
//	}

	/*
	 * 分页插件，自动识别数据库类型 多租户，请参考官网【插件扩展】
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	@Bean
	public MybatisPulsMetaObjectHandler gunsMpFieldHandler() {
		return new MybatisPulsMetaObjectHandler() {

			@Override
			protected String getUserUniqueId() {
				try {
					return ShiroUtils.getLoginName();
				} catch (Exception e) {
					//如果获取不到当前用户就存空id
					return "/";
				}
			}
		};
	}

	/**
	 * 多数据源切换的aop
	 */
	@Bean
	public MultiSourceAop multiSourceExAop() {
		return new MultiSourceAop();
	}
	
}
