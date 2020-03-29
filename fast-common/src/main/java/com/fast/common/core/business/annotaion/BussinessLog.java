package com.fast.common.core.business.annotaion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.fast.common.core.enums.BusinessType;
import com.fast.common.core.enums.OperatorType;;
/**
 * 业务日志注解
 * @author zhouzhou
 * @date 2020-03-12 14:22
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BussinessLog {

	/**
	 * 模块
	 */
	public String title() default "";

	/**
	 * 功能
	 */
	public BusinessType businessType() default BusinessType.OTHER;

	/**
	 * 操作人类别
	 */
	public OperatorType operatorType() default OperatorType.MANAGE;

	/**
	 * 是否保存请求的参数
	 */
	public boolean isSaveRequestData() default true;
}
