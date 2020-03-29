package com.fast.framework.sys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.framework.sys.dao.SysUserCompDao;
import com.fast.framework.sys.entity.SysUserCompEntity;
import com.fast.framework.sys.service.SysUserCompService;

@Service("sysUserCompService")
public class SysUserCompServiceImpl extends ServiceImpl<SysUserCompDao,SysUserCompEntity> implements SysUserCompService {

	@Override
	public List<Long> queryCompIdList(Long userId) {
		
		return baseMapper.queryCompIdList(userId);
		
	}

	@Override
	public int deleteByUserIdBatch(Long[] userIds) {
		return this.baseMapper.deleteByUserIdBatch(userIds);
	}

}
