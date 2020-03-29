package com.framework;


public class TradeForm {
	private String machno;

	private String method;
	
	private String maccode;

	private String version;
	
	private String timestamp;
	
	private String nonce_str;
	
	private String sign;
	
	
	private String biz_content;


	public String getMachno() {
		return machno;
	}


	public void setMachno(String machno) {
		this.machno = machno;
	}


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}


	public String getMaccode() {
		return maccode;
	}


	public void setMaccode(String maccode) {
		this.maccode = maccode;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
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
