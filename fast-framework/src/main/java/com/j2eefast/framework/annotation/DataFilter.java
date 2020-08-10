package com.j2eefast.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据过滤
 * @author huanzhou
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFilter {

    /**
     * sys_comp表的别名
     */
    String compAlias() default "c";

    /**
     *
     * @return
     */
    String deptAlias() default  "u";

    /**
     * 用户表的别名
     */
    String userAlias() default "";
}
