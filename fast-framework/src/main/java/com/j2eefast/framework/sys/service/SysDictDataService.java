package com.j2eefast.framework.sys.service;

import java.util.List;
import java.util.Map;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.redis.SysConfigRedis;
import com.j2eefast.framework.sys.entity.SysDictDataEntity;
import com.j2eefast.framework.sys.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据字典
 */
@Service
public class SysDictDataService  extends ServiceImpl<SysDictDataMapper,SysDictDataEntity> {

	@Autowired
	private SysConfigRedis sysConfigRedis;

	/**
	 * 页面展示查询翻页
	 */
	public PageUtil findPage(Map<String, Object> params) {
		String dictLabel = (String) params.get("dictLabel");
		String dictType = (String) params.get("dictType");
		String status = (String) params.get("status");
		Page<SysDictDataEntity> page = this.baseMapper.selectPage(new Query<SysDictDataEntity>(params).getPage(),
				new QueryWrapper<SysDictDataEntity>().like(ToolUtil.isNotEmpty(dictLabel),
						"dict_label", dictLabel).like(ToolUtil.isNotEmpty(dictType),
						"dict_type", dictType).like(ToolUtil.isNotEmpty(status),"status",status));
		return new PageUtil(page);
	}

	public List<SysDictDataEntity> selectDictDataByType(String dictType) {
		List<SysDictDataEntity>  list = sysConfigRedis.getRedisDict(dictType);
		if(ToolUtil.isEmpty(list)){
			list =  this.list(new QueryWrapper<SysDictDataEntity>().eq("dict_type",dictType).
					eq("status","0").orderBy(true, true, "dict_sort"));
			sysConfigRedis.saveOrUpdateDict(dictType,list);
			return list;
		}else{
			com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
			list = JSONObject.parseArray(array.toJSONString(), SysDictDataEntity.class);
			return list;
		}
	}

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
					break;
				}
			}
			return r;
		}
	}

	public int countDictDataByType(String dictType) {
		return this.count(new QueryWrapper<SysDictDataEntity>().eq("dict_type",dictType));
	}

	public boolean updateDictData(SysDictDataEntity dictData) {
		boolean r =  this.updateById(dictData);
		List<SysDictDataEntity>  list = this.list(new QueryWrapper<SysDictDataEntity>().eq("dict_type",dictData.getDictType()).
				eq("status","0").orderBy(true, true, "dict_sort"));
		sysConfigRedis.saveOrUpdateDict(dictData.getDictType(),list);
		return r;
	}

	public boolean deleteBatchByIds(Long[] ids) {
		SysDictDataEntity dict = this.getById(ids[0]);
		boolean r = this.baseMapper.deleteDictDataByIds(ids) > 1;
		List<SysDictDataEntity>  list = this.list(new QueryWrapper<SysDictDataEntity>().eq("dict_type",dict.getDictType()).
				eq("status","0").orderBy(true, true, "dict_sort"));
		if(ToolUtil.isEmpty(list)){
			sysConfigRedis.delRedisDict(dict.getDictType());
		}else{
			sysConfigRedis.saveOrUpdateDict(dict.getDictType(),list);
		}
		return r;
	}

	public String getDictDefaultLabelValue(String dictType) {
		String r ="";
		List<SysDictDataEntity>  list = sysConfigRedis.getRedisDict(dictType);
		if(ToolUtil.isEmpty(list)){
			list = this.list(new QueryWrapper<SysDictDataEntity>().eq("dict_type",dictType).
					eq("status","0").orderBy(true, true, "dict_sort"));
			if(ToolUtil.isNotEmpty(list)){
				for(SysDictDataEntity dict: list){
					if(dict.getIsDefault().equals("Y")){
						r = dict.getDictValue();
						break;
					}
				}
			}
		}else{
			JSONArray jsonArray = JSONUtil.parseArray(list,false);
			List<SysDictDataEntity> list1 = jsonArray.toList(SysDictDataEntity.class);
			for(SysDictDataEntity dict: list1){
				if(dict.getIsDefault().equals("Y")){
					r = dict.getDictLabel();
					break;
				}
			}
		}
		return  r;
	}

}
