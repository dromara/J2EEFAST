package com.j2eefast.flowable.bpm.config;

import com.j2eefast.common.core.mutidatasource.DataSourceContextHolder;
import com.j2eefast.common.db.context.DataSourceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * <p>切换数据库</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-16 15:59
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Slf4j
@Aspect
@Component
public class FlowableDataSourceAspect implements Ordered {

	@Pointcut(value = "execution(* org.flowable.ui.modeler..*(..)) || execution(* com.j2eefast.flowable.bpm.service..*(..))")
	public void dataSourcePointCut() {
	}

	@Before(value = "dataSourcePointCut()")
	public void before(JoinPoint point) {
		//类方法
		log.info("before--->>> class_method={}",point.getSignature().getDeclaringTypeName() +"."+ point.getSignature().getName());
		DataSourceContextHolder.setDataSourceType(DataSourceContext.FLOWABLE_DATASOURCE_NAME);
	}

	@After(value = "dataSourcePointCut()")
	public void after(JoinPoint point) {
		log.info("after--->>> class_method={}",point.getSignature().getDeclaringTypeName() +"."+ point.getSignature().getName());
		DataSourceContextHolder.clearDataSourceType();
	}

	@Override
	public int getOrder() {
		return -100;
	}
}
