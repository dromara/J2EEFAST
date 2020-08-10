package com.j2eefast.framework.config;

import cn.hutool.core.util.StrUtil;
import com.j2eefast.common.core.datasources.MybatisPulsMetaObjectHandler;
import com.j2eefast.common.core.io.PropertiesUtils;
import com.j2eefast.common.core.mutidatasource.annotaion.aop.MultiSourceAop;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.utils.UserUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * mybatis-plus 配置
 * @author: zhouzhou Emall:18774995071@163.com
 * @time 2020/2/14 18:32
 * @version V1.0
 * EnableAspectJAutoProxy 此处作用是使框架暴露代理对象 使其SpringUtil.getAopProxy 能够获取
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
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
					return UserUtils.getLoginName();
				} catch (Exception e) {
					//如果获取不到当前用户就存空id
					return "/";
				}
			}
		};
	}

	/**
	 * 动态配置 mapper 的扫描路径
	 * @author: zhouzhou Emall:18774995071@163.com
	 */
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer(){
		MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
		String mapperPackage = PropertiesUtils.getInstance().getProperty("mybatis-plus.mapper-package","com.j2eefast.*.*.mapper," +
				"com.j2eefast.*.*.dao");
		if(ToolUtil.isNotEmpty(mapperPackage)){
			scannerConfigurer.setBasePackage("com.j2eefast.*.*.mapper,"+mapperPackage);
		}else{
			scannerConfigurer.setBasePackage("com.j2eefast.*.*.mapper");
		}
		return scannerConfigurer;
	}
}
