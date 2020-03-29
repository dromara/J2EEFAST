package com.fast.framework.utils;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fast.framework.sys.entity.SysUserEntity;

/**
 * Controller公共组件
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SysUserEntity getUser() {
		return ShiroUtils.getUserEntity();
	}
	protected String getLoginName() {
		return ShiroUtils.getUserEntity().getUsername();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected Long getCompId(){
		return getUser().getCompId();
	}

	protected Long getDeptId() {
		return getUser().getDeptId();
	}
}
