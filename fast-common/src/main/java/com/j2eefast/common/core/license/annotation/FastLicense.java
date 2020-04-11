package com.j2eefast.common.core.license.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目证书检测注解
 * @author zhouzhou
 * @date 2020-03-12 15:40
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FastLicense {

    String[] vertifys() default{};

}
