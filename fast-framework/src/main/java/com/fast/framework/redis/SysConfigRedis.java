package com.fast.framework.redis;

import com.fast.framework.sys.entity.SysDictDataEntity;
import com.fast.framework.sys.entity.SysNoticeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fast.common.core.utils.RedisUtil;
import com.fast.framework.sys.entity.SysConfigEntity;
import com.fast.framework.utils.RedisKeys;
import java.util.List;

/**
 * 系统配置Redis
 */
@Component
public class SysConfigRedis {
	
	@Autowired
	private RedisUtil redisUtil;

	public void saveOrUpdate(SysConfigEntity config) {
		if (config == null) {
			return;
		}
		String key = RedisKeys.getSysConfigKey(config.getParamKey());
		redisUtil.set(key, config);
	}

	public void delete(String configKey) {
		String key = RedisKeys.getSysConfigKey(configKey);
		redisUtil.delete(key);
	}

	public SysConfigEntity get(String configKey) {
		String key = RedisKeys.getSysConfigKey(configKey);
		return redisUtil.get(key, SysConfigEntity.class);
	}

	public List<SysDictDataEntity> getRedisDict(String dictKey){
		String key = RedisKeys.getDictConfigKey(dictKey);
		return redisUtil.get(key, List.class);
	}

	public List<SysNoticeEntity> getRedisNotice(){
		String key = RedisKeys.getNoticeKey();
		return redisUtil.get(key, List.class);
	}

	public void saveOrUpdateNotice(List<SysNoticeEntity> notices, long time){
		if (notices == null) {
			return;
		}
		String key = RedisKeys.getNoticeKey();
		redisUtil.set(key, notices,time);
	}

	public void delNotice(){
		String key = RedisKeys.getNoticeKey();
		redisUtil.delete(key);
	}

	public void delRedisDict(String dictKey) {
		String key = RedisKeys.getDictConfigKey(dictKey);
		redisUtil.delete(key);
	}

	public void saveOrUpdateDict(String dictKey,List<SysDictDataEntity> dictList) {
		if (dictList == null) {
			return;
		}
		String key = RedisKeys.getDictConfigKey(dictKey);
		redisUtil.set(key, dictList);
	}
}
