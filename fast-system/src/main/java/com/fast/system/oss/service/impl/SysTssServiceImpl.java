package com.fast.system.oss.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.page.Query;
import com.fast.system.oss.dao.SysTssDao;
import com.fast.system.oss.entity.SysTssEntity;
import com.fast.system.oss.service.SysTssService;

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
