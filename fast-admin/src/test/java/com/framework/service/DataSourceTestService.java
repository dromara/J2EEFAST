package com.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.j2eefast.framework.sys.entity.SysUserEntity;
import com.j2eefast.framework.sys.service.SysUserService;


/**
 * 测试多数据源
 */
@Service
public class DataSourceTestService {
	@Autowired
	private SysUserService sysUserService;

	public SysUserEntity queryUser(Long userId) {
		return sysUserService.getById(userId);
	}

	public SysUserEntity queryUser2(Long userId) {
		return sysUserService.getById(userId);
	}
}
