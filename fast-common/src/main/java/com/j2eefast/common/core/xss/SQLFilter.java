package com.j2eefast.common.core.xss;

import org.apache.commons.lang.StringUtils;
import com.j2eefast.common.core.exception.RxcException;


/**
 * SQL过滤
 */
public class SQLFilter {

	/**
	 * 仅支持字母、数字、下划线、空格、逗号（支持多个字段排序）
	 */
	public static String 					SQL_PATTERN 					= "[a-zA-Z0-9_\\ \\,]+";


	/**
	 * SQL注入过滤
	 * 
	 * @param str 待验证的字符串
	 */
	public static String sqlInject(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		// 去掉'|"|;|\字符
		str = StringUtils.replace(str, "'", "");
		str = StringUtils.replace(str, "\"", "");
		str = StringUtils.replace(str, ";", "");
		str = StringUtils.replace(str, "\\", "");

		// 转换成小写
		String temp = str.toLowerCase();

		// 非法字符 purgingTime purgingTime
		String[] keywords = { "master", "truncate", "insert", "select", "delete", "update", "declare", "alter", 
				"drop","like","limit","in","or"};

		// 判断是否包含非法字符
		for (String keyword : keywords) {
//			if (str.indexOf(keyword) != -1) {
			if (temp.equals(keyword)) {
				throw new RxcException("包含非法字符");
			}
		}
		return escapeOrderBySql(str);
	}

	/**
	 * 验证 order by 语法是否符合规范
	 */
	public static boolean isValidOrderBySql(String value)
	{
		return value.matches(SQL_PATTERN);
	}

	/**
	 * 检查字符，防止注入绕过
	 */
	public static String escapeOrderBySql(String value)
	{
		if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value))
		{
			throw new RxcException("包含非法字符");
		}
		return value;
	}
}
