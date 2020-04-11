package com.j2eefast.common.core.redis.annotaion.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.j2eefast.common.core.exception.RxcException;

/**
 * Redis 切面处理
 * @author zhouzhou
 * @date 2020-03-12 15:40
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "fast.redis", name = "enabled", havingValue = "true")
public class RedisAop {

	private final static Logger 					LOG 					= LoggerFactory.getLogger(RedisAop.class);
	/**
	 * 是否开启redis缓存 true开启 false关闭
	 */
//	@Value("${fast.redis.enabled: true}")
//	private Boolean open;

	@Around("execution(* com.j2eefast.common.core.utils.RedisUtil.*(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		
		String subMsg = "Redis服务异常";
		Object result = null;
		
//		if (open) {
		try {
			result = point.proceed();
		} catch (Exception e) {
			LOG.error("redis error", e);
			throw new RxcException(subMsg);
		}
//		}
		
		return result;
	}
}
