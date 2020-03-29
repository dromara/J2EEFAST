package com.fast.framework.sys.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.page.Query;
import com.fast.common.core.utils.PageUtil;

import com.fast.framework.annotation.DataFilter;
import com.fast.framework.sys.dao.SysPoiDao;
import com.fast.framework.sys.entity.SysPoiEntity;
import com.fast.framework.sys.service.SysPoiService;
import com.fast.framework.utils.Constant;


import cn.hutool.core.util.StrUtil;

@Service("sysPoiService")
public class SysPoiServiceImpl extends ServiceImpl<SysPoiDao,SysPoiEntity> implements SysPoiService{

	@Override
	@DataFilter(subComp = true, subDept = false , tableAlias = "sys_poi")
	public PageUtil queryAllPage(Map<String, Object> params) {
		String key = (String) params.get("key");
		if("N".equals(key)) {
			key = "name";
		}
		String value = (String) params.get("value");
		Page<SysPoiEntity> tempPage = new Query<SysPoiEntity>(params).getPage();// ;
		Page<SysPoiEntity> page = tempPage.setRecords(this.baseMapper.selectAll(tempPage, 
				key,value,StrUtil.nullToDefault((String)params.get(Constant.SQL_FILTER),"")));
		return new PageUtil(page);
	}	
}
