package com.fast.system.oss.service.impl;

import java.util.Map;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.page.Query;
import com.fast.common.core.utils.PageUtil;
import com.fast.system.oss.dao.SysOssDao;
import com.fast.system.oss.entity.SysOssEntity;
import com.fast.system.oss.service.SysOssService;

@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

	@Override
	public PageUtil queryPage(Map<String, Object> params) {
		Page<SysOssEntity> page = this.baseMapper.selectPage(new Query<SysOssEntity>(params).getPage(),null);
		return new PageUtil(page);
	}

}
