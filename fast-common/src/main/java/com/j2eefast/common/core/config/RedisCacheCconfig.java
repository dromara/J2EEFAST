package com.j2eefast.common.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;

/**
 * <p>SpringBoot 缓存异常重写</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-08 13:51
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisCacheCconfig extends CachingConfigurerSupport {


	@Override
	public CacheErrorHandler errorHandler() {
		CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {

			@Override
			public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
				RedisErrorException(exception, key);
			}

			@Override
			public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
				RedisErrorException(exception, key);
			}

			@Override
			public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
				RedisErrorException(exception, key);
			}

			@Override
			public void handleCacheClearError(RuntimeException exception, Cache cache) {
				RedisErrorException(exception, null);
			}
		};
		return cacheErrorHandler;
	}

	protected void RedisErrorException(Exception exception,Object key){
		log.error("redis异常：key=[{}], exception={},  {}", key, exception.getMessage(), " 警告[若不想出现此错误请配置Redis 缓存数据库]");
	}
}
