package com.fast.common.core.constants;

import com.fast.common.core.utils.HexUtil;

import java.util.List;

/**
 * 系统常量
 * @author zhouzhou
 * @date 2020-03-15 11:12
 */
public class ConfigConstant {
	
	public static final String 					DEOM_MODE_PROMPT 					= "演示模式,禁止操作!";
	public static final String					DEOM_MODE_SHOW						= "*** 演示模式，不展示数据 ***";
	public static String 						FAST_OS_SN							= "";
	public static Boolean						FAST_IPIS							= true;
	public static List<String> 					FAST_IPS							= null;
	public static byte[] 						FAST_MAC_KEY						= HexUtil.decodeHex("BF8F83A656BD75925379384E454DD174");
	public static byte[] 						FAST_KEY							= HexUtil.decodeHex("62D95F5BA8E44064231018DF8A9EE027");
	public static String 						KEY									= "D605C20574179E9F3526BC9076D77AE9";
	public static byte[] 						FAST_VERIFY_KEY						= HexUtil.decodeHex("00000000000000000000000000000000");
	public static String 						AUTHORIZATION_TIME					= "";

}
