package com.fast.framework.utils;

/**
 * 常量
 */
public class Constant {

	public static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

	/** 超级管理员ID */
	public static final Long SUPER_ADMIN = 1L;

	public static final  String SU_ADMIN = "ADMIN";

	/** 数据权限过滤 */
	public static final String SQL_FILTER = "sql_filter";

	/** 硬件代码 **/
	public static final String IDS_CODE_0000 = "0000";
	public static final String IDS_CODE_0001 = "0001";
	public static final String IDS_CODE_0002 = "0002";
	public static final String IDS_CODE_0003 = "0003";
	public static final String IDS_CODE_0004 = "0004";
	public static final String IDS_CODE_0005 = "0005";
	public static final String IDS_CODE_0006 = "0006";
	public static final String IDS_CODE_0007 = "0007";

	/** 公司正常状态 */
	public static final String COMP_NORMAL = "0";
	public static final String DEPT_NORMAL = "0";


	/** 登录名称是否唯一的返回结果码 */
	public final static String USER_NAME_UNIQUE = "true";
	public final static String USER_NAME_NOT_UNIQUE = "false";

	/**
	 * 资源映射路径 前缀
	 */
	public static final String RESOURCE_urlPrefix = "/profile";
	/**
     * 手机号码格式限制
     */
    public static final String MOBILE_PHONE_NUMBER_PATTERN = "^0{0,1}(13[0-9]|17[0-9]|19[0-9]|16[0-9]|15[0-9]|14[0-9]|18[0-9])[0-9]{8}$";

    /**
     * 邮箱格式限制
     */
    public static final String EMAIL_PATTERN = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?";

//	/** 添加的参数 登陆错误次数*/ 后期添加
//	public static final int LOGIN_NUM_MAX = 3;

	/**
	 * 菜单类型
	 */
	public enum MenuType {
		/**
		 * 目录
		 */
		CATALOG(0),
		/**
		 * 菜单
		 */
		MENU(1),
		/**
		 * 按钮
		 */
		BUTTON(2);

		private int value;

		MenuType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 定时任务状态
	 */
	public enum ScheduleStatus {
		/**
		 * 正常
		 */
		NORMAL("0"),
		/**
		 * 暂停
		 */
		PAUSE("1");

		private String value;

		ScheduleStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 云服务商
	 */
	public enum CloudService {
		/**
		 * 七牛云
		 */
		QINIU(1),
		/**
		 * 阿里云
		 */
		ALIYUN(2),
		/**
		 * 腾讯云
		 */
		QCLOUD(3),

		/**
		 * API服务器
		 */
		APPBCS(4);

		private int value;

		CloudService(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

}
