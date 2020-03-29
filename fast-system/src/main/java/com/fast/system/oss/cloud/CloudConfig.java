package com.fast.system.oss.cloud;

import java.io.Serializable;

/**
 * 
 * @Description:上传API存储配置
 * @author ZhouHuan 18774995071@163.com
 * @time 2019-03-15 16:47
的
 *
 */
public class CloudConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private String app_id;

	private String method;

	private String timestamp;

	private String nonce_str;

	private String version;

	private String sign;

	private String biz_content;

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBiz_content() {
		return biz_content;
	}

	public void setBiz_content(String biz_content) {
		this.biz_content = biz_content;
	}

}
