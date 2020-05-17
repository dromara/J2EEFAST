package com.j2eefast.common.core.utils;

import java.util.HashMap;

/**
 * <p>页面返回数据</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-01 19:59
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class ResponseData extends HashMap<String, Object> {

	/** 状态码 */
	public static final                     String                  CODE_TAG                    = "code";
	/** 返回内容 */
	public static final                     String                  MSG_TAG                     = "msg";
	/** 数据对象 */
	public static final                     String                  DATA_TAG                    = "data";
	public static final                     String                  DEFAULT_SUCCESS_CODE        = "00000";
	public static final                     String                  DEFAULT_SUCCESS_MSG         = "SUCCESS";

	public ResponseData(){

	}

	public static ResponseData error() {
		return error("50000", "未知异常，请联系管理员");
	}

	public static ResponseData error(String code, String msg) {
		ResponseData data = new ResponseData();
		data.put(CODE_TAG, code);
		data.put(MSG_TAG, msg);
		return data;
	}

	public static ResponseData error(String msg) {
		ResponseData data = new ResponseData();
		data.put(CODE_TAG, "50001");
		data.put(MSG_TAG, msg);
		return data;
	}

	/**
	 *初始化一个新创建的 ResponseData 对象
	 * @param code 返回代码
	 * @param msg 返回消息
	 * @param data 返回数据
	 */
	public ResponseData(String code,String msg, Object data){
		super.put(CODE_TAG, code);
		super.put(MSG_TAG, msg);
		if (ToolUtil.isNotEmpty(data))
		{
			super.put(DATA_TAG, data);
		}
	}

	public static ResponseData success(String msg, Object data){
		return new ResponseData(DEFAULT_SUCCESS_CODE, msg, data);
	}

	/**
	 * 返回成功数据
	 *
	 * @return 成功消息
	 */
	public static ResponseData success(Object data){
		return ResponseData.success(DEFAULT_SUCCESS_MSG, data);
	}

	/**
	 * 返回消息
	 * @param msg
	 * @return
	 */
	public static ResponseData success(String msg){
		return ResponseData.success(msg, null);
	}

	public static ResponseData success(){
		return success("SUCCESS");
	}

	@Override
	public ResponseData put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
