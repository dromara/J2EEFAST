package com.j2eefast.common.core.exception;

import lombok.Data;

/**
 * 自定义异常处理
 * @author zhouzhou
 * @date 2020-03-12 15:42
 */
@Data
public class RxcException extends RuntimeException {

	private static final long 					serialVersionUID 					= 1L;
	private String 								msg;
	private String 								code 								= "50000";
	private String 								subCode;
	private String 								subMsg;
	
	public RxcException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public RxcException(String code, String msg,String subCode, String subMsg) {
		super(msg);
		this.msg = msg;
		this.code = code;
		this.subCode = subCode;
		this.subMsg = subMsg;
	}

	public RxcException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public RxcException(String msg, String code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public RxcException(String msg, String code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}
}
