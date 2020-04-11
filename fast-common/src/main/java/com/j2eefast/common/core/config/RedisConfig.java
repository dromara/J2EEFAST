package com.j2eefast.common.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置
 * @author zhouzhou
 * @date 2020-03-12 15:26
 */
@Configuration
public class RedisConfig {
	
	@Autowired
	private RedisConnectionFactory factory;

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setConnectionFactory(factory);
		return redisTemplate;
	}

	@Bean
	public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForHash();
	}

	@Bean
	public ValueOperations<String, String> valueOperations(RedisTemplate<String, String> redisTemplate) {
		return redisTemplate.opsForValue();
	}

	@Bean
	public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForList();
	}

	@Bean
	public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForSet();
	}

	@Bean
	public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForZSet();
	}
}
