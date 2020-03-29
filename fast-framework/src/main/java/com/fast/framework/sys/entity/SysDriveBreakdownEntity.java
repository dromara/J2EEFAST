package com.fast.framework.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @Description:设备故障表
 * @author ZhouHuan 18774995071@163.com
 * @time 2019-02-26 14:05
的
 *
 */
@TableName("sys_drive_breakdown")
public class SysDriveBreakdownEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;

	private String machNo;

	/**
	 * 公司ID
	 */
	private Long compId;

	/**
	 * 地区ID
	 */
	private Long deptId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date errorTime;

	private String ybjError;

	private String zbjError;

	private String nfcError;

	private String cdError;

	private String safeError;

	private String scanError;

	@TableField(exist = false)
	private String name;

	/**
	 * 公司名称
	 */
	@TableField(exist = false)
	private String compName;

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 线路ID
	 */
	private Long line;

	private String licenseNo;

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public Long getLine() {
		return line;
	}

	public void setLine(Long line) {
		this.line = line;
	}

	public String getScanError() {
		return scanError;
	}

	public void setScanError(String scanError) {
		this.scanError = scanError;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMachNo() {
		return machNo;
	}

	public void setMachNo(String machNo) {
		this.machNo = machNo;
	}

	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Date getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(Date errorTime) {
		this.errorTime = errorTime;
	}

	public String getYbjError() {
		return ybjError;
	}

	public void setYbjError(String ybjError) {
		this.ybjError = ybjError;
	}

	public String getZbjError() {
		return zbjError;
	}

	public void setZbjError(String zbjError) {
		this.zbjError = zbjError;
	}

	public String getNfcError() {
		return nfcError;
	}

	public void setNfcError(String nfcError) {
		this.nfcError = nfcError;
	}

	public String getCdError() {
		return cdError;
	}

	public void setCdError(String cdError) {
		this.cdError = cdError;
	}

	public String getSafeError() {
		return safeError;
	}

	public void setSafeError(String safeError) {
		this.safeError = safeError;
	}

}
