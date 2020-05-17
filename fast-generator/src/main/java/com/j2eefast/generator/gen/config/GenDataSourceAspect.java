package com.j2eefast.generator.gen.config;

import com.j2eefast.common.core.mutidatasource.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>代码生成器获取切换数据源</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-20 13:40
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Slf4j
@Aspect
@Component
public class GenDataSourceAspect implements Ordered {

	@Pointcut(value = "execution(* com.j2eefast.generator.gen.service.GenTableService.selectNotDbTablePage(..)) ||" +
			" execution(* com.j2eefast.generator.gen.service.GenTableService.findSlaveDbTableList(..)) ||" +
			" execution(* com.j2eefast.generator.gen.service.GenTableService.selectDbTableListByNames1(..)) || " +
			"execution(* com.j2eefast.generator.gen.service.GenTableColumnService.selectDbTableColumnsByName(..)) ")
	public void getdataSourcePointCut() {
	}

	@Before(value = "getdataSourcePointCut()")
	public void before(JoinPoint point) {
		Object dbType = point.getArgs()[0];
		if (dbType != null && dbType instanceof String) {
			DataSourceContextHolder.setDataSourceType((String) dbType);
		}
		//类方法
		log.info("before--->>> class_method={}",point.getSignature().getDeclaringTypeName() +"."+ point.getSignature().getName());
	}

	@After(value = "getdataSourcePointCut()")
	public void after(JoinPoint point) {
		DataSourceContextHolder.clearDataSourceType();
	}

	@Override
	public int getOrder() {
		return -100;
	}
}
