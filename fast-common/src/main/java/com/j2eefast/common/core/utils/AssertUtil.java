package com.j2eefast.common.core.utils;

import com.j2eefast.common.core.exception.RxcException;

/**
 *空校验
 * @author zhouzhou
 * @date 2020-03-13 10:08
 */
public class AssertUtil {
	
	public static void isBlank(String str, String message) {
		if (ToolUtil.isEmpty(str)) {
			throw new RxcException(message);
		}
	}

	public static void isNull(Object object, String message) {
		if (object == null) {
			throw new RxcException(message);
		}
	}
	
	public static void isNull(Object object, String message, String code) {
		if (object == null) {
			throw new RxcException(message,code);
		}
	}
}
