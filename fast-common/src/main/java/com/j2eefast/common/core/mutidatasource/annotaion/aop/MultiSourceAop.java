package com.j2eefast.common.core.mutidatasource.annotaion.aop;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.j2eefast.common.core.mutidatasource.DataSourceContextHolder;
import com.j2eefast.common.core.mutidatasource.annotaion.DataSource;
import com.j2eefast.common.core.utils.ToolUtil;

/**
 * 多数据源切换AOP
 * 扩增 支持类上注解
 * @author zhouzhou
 * @date 2020-03-12 09:58
 */
@Slf4j
@Aspect
public class MultiSourceAop implements Ordered {

	private String[]  						names					   = null;
	private String 	  						defaultdb 				   = "DEFAULT";
	
	/**
	 * AOP的顺序要早于Spring的事务
	 */
	@Override
	public int getOrder() {
		return 1;
	}
	
	/**
     * 构造函数
     *
     * @param names 数据源的名称们，第一个为默认名称
     */
    public MultiSourceAop(String... names) {
        this.names = names;
    }

    @Pointcut(value = "@annotation(com.j2eefast.common.core.mutidatasource.annotaion.DataSource)")
    private void cut() {

    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        //获取被aop拦截的方法
//        Signature signature = point.getSignature();
//        MethodSignature methodSignature = null;
//        if (!(signature instanceof MethodSignature)) {
//            throw new IllegalArgumentException("该注解只能用于方法");
//        }
//        methodSignature = (MethodSignature) signature;
//        Object target = point.getTarget();
//        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        //支持获取类上注解
        //DataSource datasource = currentMethod.getAnnotation(DataSource.class);
		DataSource datasource = getDataSource(point);
		//如果有DataSource注解，则设置DataSourceContextHolder为注解上的名称
        if (ToolUtil.isNotEmpty(datasource)) {
            DataSourceContextHolder.setDataSourceType(datasource.name());
            log.debug("设置数据源为：" + datasource.name());
        } else {
            DataSourceContextHolder.setDataSourceType(defaultdb);
			log.debug("设置数据源为：dataSourceCurrent");
        }

        try {
            return point.proceed();
        } finally {
			log.debug("清空数据源信息！");
            DataSourceContextHolder.clearDataSourceType();
        }
    }

	/**
	 * 获取需要切换的数据源
	 */
	public DataSource getDataSource(ProceedingJoinPoint point){
		MethodSignature signature = (MethodSignature) point.getSignature();
		Class<? extends Object> targetClass = point.getTarget().getClass();
		DataSource targetDataSource = targetClass.getAnnotation(DataSource.class);
		if (ToolUtil.isNotEmpty(targetDataSource)) {
			return targetDataSource;
		}
		else{
			//获取方法上的DataSource注解
			Method method = signature.getMethod();
			DataSource dataSource = method.getAnnotation(DataSource.class);
			return dataSource;
		}
	}
	
}
