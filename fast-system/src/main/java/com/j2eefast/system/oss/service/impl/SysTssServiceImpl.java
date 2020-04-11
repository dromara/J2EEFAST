package com.j2eefast.system.oss.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.system.oss.dao.SysTssDao;
import com.j2eefast.system.oss.entity.SysTssEntity;
import com.j2eefast.system.oss.service.SysTssService;

@Service("sysTssService")
public class SysTssServiceImpl extends ServiceImpl<SysTssDao, SysTssEntity> implements SysTssService {

	@Override
    public PageUtil queryPage(Map<String, Object> params) {
		Page<SysTssEntity> page = this.baseMapper.selectPage(new Query<SysTssEntity>(params).getPage(),null);
		return new PageUtil(page);
	}

	@Override
    public SysTssEntity selectByVs(String version) {
		return this.getOne(new QueryWrapper<SysTssEntity>().eq("version", version));
	}

}
