package com.fast.framework.utils;


import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fast.common.core.utils.SpringUtil;
import com.fast.framework.sys.service.SysConfigService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.Setting;


/**
 * 获取系统配置参数
 * @author ZhouHuan 18774995071@163.com
 * @data 2019-04-05 19:44
 */
public class Global {
	
	private static final Logger log = LoggerFactory.getLogger(Global.class);
	
	/**
     * 获取配置
     */
    public static String  getConfig(String key){
    	try{
    		return SpringUtil.getBean(SysConfigService.class).getConfigObject(key, String.class);
        }catch (Exception e)
        {
        	log.error("获取配置错误!", e);
        	return null;
        }
    	
    }
    
    
    /**
     * 获取项目版本
     */
    public static String getVersion(){
    	return StrUtil.blankToDefault(getConfig(ConfigConstant.SYS_CONFIG_VERSION),"1.0.1");
    }
    
    /**
     * @Description:样式JS 版本
     * @author ZhouHuan 18774995071@163.com
     * @time 2019-04-28 22:17
     * @return
     *
     */
    public static String getDvVersion(){
    	return StrUtil.blankToDefault(getConfig(ConfigConstant.SYS_CONFIG_DV_VERSION),"20190425");
    }
    
    /**
     * 获取后台系统标题
     */
    public static String getTitle() {
    	
    	return StrUtil.blankToDefault(getConfig(ConfigConstant.SYS_CONFIG_TITLE),"FAST云系统OS");
    }
    
    
    public static String getDbKey(String key) {
    	return StrUtil.blankToDefault(getConfig(key),"默认值[获取数据库值失败]");
    }

    public static String getDbKey(String key,String default0) {
        return StrUtil.blankToDefault(getConfig(key),default0);
    }


    /**
     * 获取文件上传路径
     */
    public static String getProfile()
    {
        return StrUtil.blankToDefault(getConfig(ConfigConstant.SYS_CONFIG_PROFILE),"D:/fast/uploadPath");
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }

    public static String getConifgFile()
    {
        return StrUtil.blankToDefault(getConfig(ConfigConstant.SYS_CONFIG_FILE),"D:/fast/file");
    }

    public static boolean optimize(){
        return StrUtil.blankToDefault(getConfig(ConfigConstant.SYS_COMPRESS),"false").equals("true")?true:false;
    }

    /**
     * 登陆密码错误次数
     */
    public static int getLoginMaxCount() {
    	
    	String jsonStr = StrUtil.blankToDefault(getConfig(ConfigConstant.SYS_CONFIG_KEY),
    			"{\"login_maxCount\":5,\"lock_time\":30}");
    	
    	//JSON 格式转换
    	JSONObject array = JSONUtil.parseObj(jsonStr);
    	
    	//获取登陆最大错误次数
    	return array.getInt("login_maxCount",5);
    }
    /**
     * 登陆错误次数需要输入验证码
     */
    public static int getLoginNumCode() {
    	
    	String jsonStr = StrUtil.blankToDefault(getConfig(ConfigConstant.SYS_CONFIG_KEY),
    			"{\"login_NumCount\":5,\"lock_time\":30}");
    	
    	//JSON 格式转换
    	JSONObject array = JSONUtil.parseObj(jsonStr);
    	
    	//获取登陆最大错误次数
    	return array.getInt("login_NumCount",5);
    }
    
    
    /**
     * 单位(分钟) 禁止30分钟
     *
     */
    public static int getLockTime() {
    	
    	String jsonStr = StrUtil.blankToDefault(getConfig(ConfigConstant.SYS_CONFIG_KEY),
    			"{\"login_maxCount\":5,\"lock_time\":30}");
    	//JSON 格式转换
    	JSONObject array = JSONUtil.parseObj(jsonStr);
    	
    	//禁止登陆账户时间
    	return array.getInt("lock_time",30);
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }
    
    /**
     * 所属公司
     *
     */
    public static String getCompany() {
    	
    	String jsonStr = StrUtil.blankToDefault(getConfig(ConfigConstant.SYS_CONFIG_KEY),
    			"{\"lock_time\":30,\"company\":\"www.j2eefast.com\"}");
    	
    	//JSON 格式转换
    	JSONObject array = JSONUtil.parseObj(jsonStr);
    	
    	//所属公司
    	return array.getStr("company","www.j2eefast.com");
    }
    
    /**
     * 获取版权年份
     *
     */
    public static String getCopyrightYear() {
    	
    	String jsonStr = StrUtil.blankToDefault(getConfig(ConfigConstant.SYS_CONFIG_KEY),
    			"{\"company\":\"www.j2eefast.com\",\"copyrightYear\":\"2019\"}");
    	
    	//JSON 格式转换
    	JSONObject array = JSONUtil.parseObj(jsonStr);
    	
    	//版权年份
    	return array.getStr("copyrightYear","2019");
    }
    
    public static String getIPC() {
    	String jsonStr = StrUtil.blankToDefault(getConfig(ConfigConstant.SYS_CONFIG_KEY),
    			"{\"company\":\"www.j2eefast.com\",\"ipc\":\"\"}");
    	
    	//JSON 格式转换
    	JSONObject array = JSONUtil.parseObj(jsonStr);
    	
    	//版权年份
    	return array.getStr("ipc","沪ICP备9999999号");
    }
    
    public static String getSettingValue(String Key) {
    	
    	Setting setting = new Setting("config"+File.separator+"ueditor.setting", true);
    	
    	return setting.getStr(Key);
    }
    
    public static int getSettingValueInt(String Key) {
    	
    	Setting setting = new Setting("config"+File.separator+"ueditor.setting", true);
    	
    	return setting.getInt(Key);
    }
    
}
