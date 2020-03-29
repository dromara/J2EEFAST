package com.fast.framework.sys.service.impl;

import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.exception.RxcException;
import com.fast.common.core.page.Query;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.ToolUtil;
import com.fast.framework.sys.service.SysConfigService;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fast.framework.redis.SysConfigRedis;
import com.fast.framework.sys.dao.SysConfigDao;
import com.fast.framework.sys.entity.SysConfigEntity;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

	@Autowired
	private SysConfigRedis sysConfigRedis;

	@Override
	public PageUtil queryPage(Map<String, Object> params) {
		String paramKey = (String) params.get("paramKey");
		String paramName = (String) params.get("paramName");
		Page<SysConfigEntity> page = this.baseMapper.selectPage(new Query<SysConfigEntity>(params).getPage(), 				
				new QueryWrapper<SysConfigEntity>()
				.like(ToolUtil.isNotEmpty(paramKey), "param_key", paramKey)
				.like(ToolUtil.isNotEmpty(paramName), "param_name", paramName));
		return new PageUtil(page);
	}

	@Override
	public void insert(SysConfigEntity config) {
		if(this.save(config)) {
			sysConfigRedis.saveOrUpdate(config);
		}
	}

	@Override
	public void update(SysConfigEntity config) {

		//this.updateAllColumnById(config);

		this.baseMapper.updateConfig(config);

		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String key, String value) {
		baseMapper.updateValueByKey(key, value);
		sysConfigRedis.delete(key);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		for (Long id : ids) {
			SysConfigEntity config = this.getById(id);
			sysConfigRedis.delete(config.getParamKey());
		}
		this.removeByIds(Arrays.asList(ids));
	}

	@Override
	public String getValue(String key) {
		SysConfigEntity config = sysConfigRedis.get(key);
		if (config == null) {
			config = baseMapper.queryByKey(key);
			sysConfigRedis.saveOrUpdate(config);
		}

		return config == null ? null : config.getParamValue();
	}

	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if (!StrUtil.isBlankOrUndefined(value) && JSONUtil.isJson(value) 
				&& !ClassUtil.equals(clazz, "String", false)) { //判断获取值,是否转换Bean对象
			return JSONUtil.toBean(value, clazz);
			//return new Gson().fromJson(value, clazz);
		}
		
		if(ClassUtil.equals(clazz, "String", false)) { //String 转换
			return Convert.convert(clazz, value);
		}
		
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RxcException("获取参数失败");
		}
	}

	@Override
	public boolean checkConfigKeyUnique(SysConfigEntity config) {
		Long configId = ToolUtil.isEmpty(config.getId()) ? -1L : config.getId();
		SysConfigEntity info = this.getOne(new QueryWrapper<SysConfigEntity>().eq("param_key",config.getParamKey()));
		if (ToolUtil.isNotEmpty(info) && info.getId().longValue() != configId.longValue())
		{
			return  false;
		}
		return true;
	}
}
