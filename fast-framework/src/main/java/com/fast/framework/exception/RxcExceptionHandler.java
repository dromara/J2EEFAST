package com.fast.framework.exception;

import com.fast.common.core.utils.R;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fast.common.core.exception.RxcException;
import freemarker.core.InvalidReferenceException;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class RxcExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RxcException.class)
	public R handleRxcException(RxcException e) {
		logger.error("异常",e.getMessage());
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMsg());
		return r;
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e) {
		logger.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public R handleAuthenticationException(AuthenticationException e) {
		logger.error(e.getMessage());
		return R.error("授权报错");
	}

	@ExceptionHandler(AuthorizationException.class)
	public R handleAuthorizationException(AuthorizationException e) {
		logger.error("没有权限，请联系管理员授权---->" + e.getMessage());
		return R.error("50001","没有权限，请联系管理员授权");
	}
	
	@ExceptionHandler(InvalidReferenceException.class)
	public R handInvalidReferenceException(InvalidReferenceException e) {
		logger.error("Freemarker 模板引擎报错---->" + e.getMessage());
		return R.error("Freemarker报错,检查HTML页面标签");
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e) {
		logger.error(e.getMessage(),e);
		if(e.getMessage().equals("Request method 'GET' not supported")) {
			return R.error("20000","请求不可用");
		}
		return R.error();
	}

}
