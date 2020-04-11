package com.j2eefast.common.core.enums;

/**
 * 密码安全级别
 * @author zhouzhou
 * @date 2020-03-12 14:13
 */
public enum SafeLevel {
	/**
	 * 非常弱
	 */
	VERY_WEAK("1"),
	/**
	 * 弱 
	 */
    WEAK("2"), 
    /**
     * 一般 
     */
    AVERAGE("3"), 
    /**
     * 强
     */
    STRONG("4"), 
    /**
     * 非常强 
     */
    VERY_STRONG("5"),
    /**
     * 安全
     */
    SECURE("6"), 
    /**
     * 非常安全
     */
    VERY_SECURE("7");
	
	private String value;

	SafeLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
