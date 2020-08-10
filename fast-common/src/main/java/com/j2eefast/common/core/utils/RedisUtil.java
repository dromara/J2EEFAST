package com.j2eefast.common.core.utils;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * Redis工具类
 * @author zhouzhou
 * @date 2020-03-12 15:35
 */
@Component
public class RedisUtil {

	/**
	 * 是否开启Redis
	 */
	/**
	 * 是否开启redis缓存 true开启 false关闭
	 */
	@Value("${fast.redis.enabled: true}")
	private Boolean enabled;
	@Autowired
	private StringRedisTemplate 					stringRedisTemplate;
	@Autowired
	private RedisTemplate 							redisTemplate;
	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> 		valueOperations;
	@Resource(name = "redisTemplate")
	private HashOperations<String, String, Object> 	hashOperations;
	@Resource(name = "redisTemplate")
	private ListOperations<String, Object> 			listOperations;
	@Resource(name = "redisTemplate")
	private SetOperations<String, Object> 			setOperations;
	@Resource(name = "redisTemplate")
	private ZSetOperations<String, Object> 			zSetOperations;
	/** 
	 * 默认过期时长，单位：秒
	 */
	public final static long DEFAULT_EXPIRE 		= 60 * 60 * 24;
	/**
	 *默认监控过期时长，单位：秒 
	 */
	public final static long DEFAULT_EXPIRE_MT 		= 60 * 2;
	public final static long MINUTE 				= 60;
	/**
	 *不设置过期时长 
	 */
	public final static long NOT_EXPIRE 			= -1;
	
	/**
	 * 数据存放Set集合
	 */
	public void add(String key,long expire,String... values) {
		
		if(enabled) {
			setOperations.add(key, values);
			if(expire != NOT_EXPIRE) {
				redisTemplate.expire(key,expire,TimeUnit.SECONDS);
			}
		}
		
	}
	
	public void delSet(String key,String... values) {
		
		if(enabled) {
			setOperations.remove(key,values);
		}
		
	}
	
	/**
	 * 通过Key获取之前存放的Set集合
	 */
	public Set<Object> getSets(String key){
		
		if(enabled) {
			return setOperations.members(key);
		}
		
		return null;
	}
	
	
	public void set(String key, Object value, long expire) {
		
		if(enabled) {
			valueOperations.set(key, toJson(value));
			if (expire != NOT_EXPIRE) {
				redisTemplate.expire(key, expire, TimeUnit.SECONDS);
			}
		}
		
	}
	
	public void setMillse(String key, Object value, long expire) {
		
		if(enabled) {
			valueOperations.set(key, toJson(value));
			if (expire != NOT_EXPIRE) {
				redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
			}
		}
		
	}

	public void set(String key, Object value) {
		
		if(enabled) {
			set(key, value, DEFAULT_EXPIRE);
		}
		
	}
	
	public void setSeConds(String key, Object value, long expire) {
		
		if(enabled) {
			valueOperations.set(key, toJson(value));
			if (expire != NOT_EXPIRE) {
				redisTemplate.expire(key, expire, TimeUnit.SECONDS);
			}
		}
		
	}
	
	public void set(String key, Object value,int out) {
		
		if(enabled) {
			set(key, value, NOT_EXPIRE);
		}
		
	}
	
	public void setApi(String key, Object value) {
		
		if(enabled) {
			set(key, value, DEFAULT_EXPIRE_MT);
		}
		return;
	}

	public <T> T get(String key, Class<T> clazz, long expire) {
		
		if(enabled) {
			String value = valueOperations.get(key);
			if (expire != NOT_EXPIRE) {
				redisTemplate.expire(key, expire, TimeUnit.SECONDS);
			}
			return value == null ? null : fromJson(value, clazz);
		}
		return null;
	}

	public <T> T get(String key, Class<T> clazz) {
		
		if(enabled) {
			return get(key, clazz, NOT_EXPIRE);
		}
		return null;
	}

	public String get(String key, long expire) {
		
		if(enabled) {
			String value = valueOperations.get(key);
			if (expire != NOT_EXPIRE) {
				redisTemplate.expire(key, expire, TimeUnit.SECONDS);
			}
			return value;
		}
		return null;
	}

	public String get(String key) {
		
		if(enabled) {
			return get(key, NOT_EXPIRE);
		}
		return null;
	}

	public void delete(String key) {
		
		if(enabled) {
			redisTemplate.delete(key);
		}
	}

	public boolean deletes(String key){
		if(enabled) {
			Set<String> keys = redisTemplate.keys(key);
		  	return  redisTemplate.delete(keys) > 0;
		}
		return  false;
	}

	public boolean isEnabled(){
		return enabled;
	}

	/**
	 * Object转成JSON数据
	 */
	private String toJson(Object object) {
		if (object instanceof Integer || object instanceof Long || 
				object instanceof Float || object instanceof Double ||
				object instanceof Boolean || object instanceof String) {
			return String.valueOf(object);
		}
		return JSON.toJSONString(object);
	}

	/**
	 * JSON数据，转成Object
	 */
	private <T> T fromJson(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}
}
