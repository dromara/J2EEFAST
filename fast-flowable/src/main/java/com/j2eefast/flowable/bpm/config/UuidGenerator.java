package com.j2eefast.flowable.bpm.config;

import org.flowable.common.engine.impl.persistence.StrongUuidGenerator;

/**
 * <p>生成主键</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-22 08:52
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class UuidGenerator extends StrongUuidGenerator {

	/**
	 * 去掉横线
	 * @return
	 */
	@Override
	public String getNextId() {
		String uuid = super.getNextId();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
}
