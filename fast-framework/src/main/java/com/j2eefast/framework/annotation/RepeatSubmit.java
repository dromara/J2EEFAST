package com.j2eefast.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 自定义注解防止表单重复提交
 * @param:
 * @return:
 * @auther: zhouzhou 18774995071@163.com
 * @date: 2020/1/6 12:44
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit
{

}