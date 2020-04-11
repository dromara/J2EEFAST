package com.j2eefast.framework.exception;

import com.j2eefast.common.core.utils.ResponseData;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.j2eefast.common.core.exception.RxcException;
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
	public ResponseData handleRxcException(RxcException e) {
		logger.error("异常",e.getMessage());
		ResponseData r = new ResponseData();
		r.put("code", e.getCode());
		r.put("msg", e.getMsg());
		return r;
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseData handleDuplicateKeyException(DuplicateKeyException e) {
		logger.error(e.getMessage(), e);
		return ResponseData.error("数据库中已存在该记录");
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseData handleAuthenticationException(AuthenticationException e) {
		logger.error(e.getMessage());
		return ResponseData.error("授权报错");
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseData handleAuthorizationException(AuthorizationException e) {
		logger.error("没有权限，请联系管理员授权---->" + e.getMessage());
		return ResponseData.error("50001","没有权限，请联系管理员授权");
	}
	
	@ExceptionHandler(InvalidReferenceException.class)
	public ResponseData handInvalidReferenceException(InvalidReferenceException e) {
		logger.error("Freemarker 模板引擎报错---->" + e.getMessage());
		return ResponseData.error("Freemarker报错,检查HTML页面标签");
	}

	@ExceptionHandler(Exception.class)
	public ResponseData ResponseDatahandleException(Exception e) {
		logger.error(e.getMessage(),e);
		if(e.getMessage().equals("Request method 'GET' not supported")) {
			return ResponseData.error("20000","请求不可用");
		}
		return ResponseData.error(e.getMessage());
	}

}
