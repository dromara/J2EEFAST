package com.fast.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据过滤
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFilter {
	/** 表的别名 */
	String tableAlias() default "";

	/** true：没有本部门数据权限，也能查询本人数据 */
	boolean user() default false;

	/** true：获取sys_dept 表关联 */
	boolean subDept() default false;
	
	/** true 获取sys_comp 表*/
	boolean subComp() default false;


	/** true 获取 小数据只对公司 表*/
	boolean subData() default false;


	/** 自定义数据过滤*/
	boolean setCustom() default false;
	
	/** 设置字段 */
	String setField() default "";
	
	/** sys_dept 字段关联 */
	String deptId() default "dept_id";
	
	/** sys_comp 字段关系*/
	String compId() default "comp_id";

	/** 用户ID */
	String userId() default "user_id";
}
