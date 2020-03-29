package com.fast.common.core.mutidatasource.annotaion;

import java.lang.annotation.*;

/**
 * 多数据源标识
 * @author zhouzhou
 * @date 2020-03-12 09:55
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface DataSource {
	
	/**
	 * 数据源名称
	 * <p> 
	 * 对应 yml文件 fast.db.datasourceNames 名称
	 */
	String name() default "";
	
}
