package com.fast.framework.sys.service.impl;

import java.util.List;
import java.util.Map;
import com.fast.framework.redis.SysConfigRedis;
import com.fast.framework.sys.dao.SysDictDataDao;
import com.fast.framework.sys.entity.SysDictDataEntity;
import com.fast.framework.sys.service.SysDictDataService;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.page.Query;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.ToolUtil;


@Service("sysDictDataService")
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataDao, SysDictDataEntity> implements SysDictDataService {

	@Autowired
	private SysConfigRedis sysConfigRedis;

	@Override
	public PageUtil queryPage(Map<String, Object> params) {
		String dictLabel = (String) params.get("dictLabel");
		String dictType = (String) params.get("dictType");
		String status = (String) params.get("status");
		Page<SysDictDataEntity> page = this.baseMapper.selectPage(new Query<SysDictDataEntity>(params).getPage(),
				new QueryWrapper<SysDictDataEntity>().like(ToolUtil.isNotEmpty(dictLabel),
						"dict_label", dictLabel).like(ToolUtil.isNotEmpty(dictType),
						"dict_type", dictType).like(ToolUtil.isNotEmpty(status),"status",status));
		return new PageUtil(page);
	}

	@Override
	public List<SysDictDataEntity> selectDictDataByType(String dictType) {
		List<SysDictDataEntity>  list = sysConfigRedis.getRedisDict(dictType);
		if(ToolUtil.isEmpty(list)){
			list =  this.list(new QueryWrapper<SysDictDataEntity>().eq("dict_type",dictType).
					eq("status","0").orderBy(true, true, "dict_sort"));
			sysConfigRedis.saveOrUpdateDict(dictType,list);
			return list;
		}else{
			return list;
		}
	}

	@Override
	public String selectDictLabel(String dictType, String dictValue) {
		List<SysDictDataEntity>  list = sysConfigRedis.getRedisDict(dictType);
		if(ToolUtil.isEmpty(list)){
			return this.baseMapper.selectDictLabel(dictType,dictValue);
		}else{
			String r = "";
			JSONArray jsonArray = JSONUtil.parseArray(list,false);
			List<SysDictDataEntity> list1 = jsonArray.toList(SysDictDataEntity.class);
			for(SysDictDataEntity dict: list1){
				if(dict.getDictValue().equals(dictValue)){
					 r = dict.getDictLabel();
				}
			}
			return r;
		}
	}

	@Override
	public int countDictDataByType(String dictType) {
		return this.count(new QueryWrapper<SysDictDataEntity>().eq("dict_type",dictType));
	}

	@Override
	public int updateDictData(SysDictDataEntity dictData) {
		int r =  this.baseMapper.updateDictData(dictData);
		List<SysDictDataEntity>  list = this.list(new QueryWrapper<SysDictDataEntity>().eq("dict_type",dictData.getDictType()).
				eq("status","0").orderBy(true, true, "dict_sort"));
		sysConfigRedis.saveOrUpdateDict(dictData.getDictType(),list);
		return r;
	}

	@Override
	public void deleteDictDataByIds(Long[] ids) {
		SysDictDataEntity dict = this.getById(ids[0]);
		this.baseMapper.deleteDictDataByIds(ids);
		List<SysDictDataEntity>  list = this.list(new QueryWrapper<SysDictDataEntity>().eq("dict_type",dict.getDictType()).
				eq("status","0").orderBy(true, true, "dict_sort"));
		if(ToolUtil.isEmpty(list)){
			sysConfigRedis.delRedisDict(dict.getDictType());
		}else{
			sysConfigRedis.saveOrUpdateDict(dict.getDictType(),list);
		}
	}

}
