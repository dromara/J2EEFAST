package com.fast.common.core.utils;

import java.util.HashMap;


/**
 * Map 工具
 * @author zhouzhou
 * @date 2020-03-12 16:02
 */
public class MapUtil extends HashMap<String, Object> {

	private static final long 					serialVersionUID 					= 1L;

	@Override
	public MapUtil put(String key, Object value) {
		super.put(key, value);
		return this;
	}

}
