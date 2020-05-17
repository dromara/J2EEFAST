package com.j2eefast.framework.utils;

import com.j2eefast.common.core.utils.SpringUtil;
import com.j2eefast.framework.sys.entity.SysNoticeEntity;
import com.j2eefast.framework.sys.service.SysNoticeService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * 
 * @Description:系统参数
 * @author zhouzhou 18774995071@163.com
 * @time 2019-04-15 17:35
 * @version V1.0 
 *
 */
@Component
public class SysConfig {
	
	public String getVersion() {
		return Global.getVersion();
	}
	
	public String getTitle() {
		return Global.getTitle();
	}
	
	public String getCompany() {
		return Global.getCompany();
	}
	
	public String getCopyrightYear() {
		return Global.getCopyrightYear();
	}
	
	public String getIPC() {
		return Global.getIPC();
	}
	
	public String getDvVersion() {
		return Global.getVersion()+"-"+Global.getDvVersion();
	}
	
	public String getSysLang() {
		return LocaleContextHolder.getLocale().toString();
	}
	
	public String getKeyToValue(String key) {
		return Global.getDbKey(key);
	}

	public String getKey(String key) {
		return Global.getDbKey(key);
	}

	public String getKey(String key,String default0) {
		return Global.getDbKey(key,default0);
	}

	public boolean optimize(){
		return Global.optimize();
	}

	public SysNoticeEntity getSysNotice(){
		return SpringUtil.getBean(SysNoticeService.class).getNotice();
	}
}
