package com.j2eefast.common.core.constants;

import com.j2eefast.common.core.utils.HexUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 系统常量
 * @author zhouzhou
 * @date 2020-03-15 11:12
 */
public final class ConfigConstant {

	public static final String 					TIPS_END 							= "，若想忽略此提示，请在系统工具->参数管理，设置相关参数！";
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
	public static final String 					CONSTANT 							= "CONSTANT";
	public static final String 					CTX_STATIC							= "ctxStatic";
	public static final String 					BASE_PATH							= "basePath";
	/**
	 * 登录安全key
	 */
	public static final String 					SECRETKEY 							= "_secretKey";
	public static final String 					LANGUAGE							= "_lang";

	/**
	 * 资源映射路径 前缀
	 */
	public static final String 					RESOURCE_URLPREFIX 					= "/profile";

}
