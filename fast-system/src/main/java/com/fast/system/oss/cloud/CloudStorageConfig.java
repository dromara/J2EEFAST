package com.fast.system.oss.cloud;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import com.fast.system.validator.group.AliyunGroup;
import com.fast.system.validator.group.BcsapiGroup;
import com.fast.system.validator.group.QcloudGroup;
import com.fast.system.validator.group.QiniuGroup;

/**
 * 云存储配置信息
 */
public class CloudStorageConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	// 类型 1：七牛 2：阿里云 3：腾讯云 4: API服务器
	@Range(min = 1, max = 4, message = "类型错误")
	private Integer type;

	// 七牛绑定的域名
	@NotBlank(message = "七牛绑定的域名不能为空", groups = QiniuGroup.class)
	@URL(message = "七牛绑定的域名格式不正确", groups = QiniuGroup.class)
	private String qiniuDomain;
	// 七牛路径前缀
	private String qiniuurlPrefix;
	// 七牛ACCESS_KEY
	@NotBlank(message = "七牛AccessKey不能为空", groups = QiniuGroup.class)
	private String qiniuAccessKey;
	// 七牛SECRET_KEY
	@NotBlank(message = "七牛SecretKey不能为空", groups = QiniuGroup.class)
	private String qiniuSecretKey;
	// 七牛存储空间名
	@NotBlank(message = "七牛空间名不能为空", groups = QiniuGroup.class)
	private String qiniuBucketName;

	// 阿里云绑定的域名
	@NotBlank(message = "阿里云绑定的域名不能为空", groups = AliyunGroup.class)
	@URL(message = "阿里云绑定的域名格式不正确", groups = AliyunGroup.class)
	private String aliyunDomain;
	// 阿里云路径前缀
	private String aliyunurlPrefix;
	// 阿里云EndPoint
	@NotBlank(message = "阿里云EndPoint不能为空", groups = AliyunGroup.class)
	private String aliyunEndPoint;
	// 阿里云AccessKeyId
	@NotBlank(message = "阿里云AccessKeyId不能为空", groups = AliyunGroup.class)
	private String aliyunAccessKeyId;
	// 阿里云AccessKeySecret
	@NotBlank(message = "阿里云AccessKeySecret不能为空", groups = AliyunGroup.class)
	private String aliyunAccessKeySecret;
	// 阿里云BucketName
	@NotBlank(message = "阿里云BucketName不能为空", groups = AliyunGroup.class)
	private String aliyunBucketName;

	// 腾讯云绑定的域名
	@NotBlank(message = "腾讯云绑定的域名不能为空", groups = QcloudGroup.class)
	@URL(message = "腾讯云绑定的域名格式不正确", groups = QcloudGroup.class)
	private String qcloudDomain;
	// 腾讯云路径前缀
	private String qcloudurlPrefix;
	// 腾讯云AppId
	@NotNull(message = "腾讯云AppId不能为空", groups = QcloudGroup.class)
	private Integer qcloudAppId;
	// 腾讯云SecretId
	@NotBlank(message = "腾讯云SecretId不能为空", groups = QcloudGroup.class)
	private String qcloudSecretId;
	// 腾讯云SecretKey
	@NotBlank(message = "腾讯云SecretKey不能为空", groups = QcloudGroup.class)
	private String qcloudSecretKey;
	// 腾讯云BucketName
	@NotBlank(message = "腾讯云BucketName不能为空", groups = QcloudGroup.class)
	private String qcloudBucketName;
	// 腾讯云COS所属地区
	@NotBlank(message = "所属地区不能为空", groups = QcloudGroup.class)
	private String qcloudRegion;

	// API 服务器
	@NotBlank(message = "链接url不能为空", groups = BcsapiGroup.class)
	@URL(message = "链接url", groups = BcsapiGroup.class)
	private String url;
	// 服务器AppId
	@NotNull(message = "AppId不能为空", groups = BcsapiGroup.class)
	private Integer app_id;
	// 腾讯云SecretId
	@NotBlank(message = "API服务器token不能为空", groups = BcsapiGroup.class)
	private String token;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getApp_id() {
		return app_id;
	}

	public void setApp_id(Integer app_id) {
		this.app_id = app_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getQiniuDomain() {
		return qiniuDomain;
	}

	public void setQiniuDomain(String qiniuDomain) {
		this.qiniuDomain = qiniuDomain;
	}

	public String getQiniuAccessKey() {
		return qiniuAccessKey;
	}

	public void setQiniuAccessKey(String qiniuAccessKey) {
		this.qiniuAccessKey = qiniuAccessKey;
	}

	public String getQiniuSecretKey() {
		return qiniuSecretKey;
	}

	public void setQiniuSecretKey(String qiniuSecretKey) {
		this.qiniuSecretKey = qiniuSecretKey;
	}

	public String getQiniuBucketName() {
		return qiniuBucketName;
	}

	public void setQiniuBucketName(String qiniuBucketName) {
		this.qiniuBucketName = qiniuBucketName;
	}

	public String getQiniuurlPrefix() {
		return qiniuurlPrefix;
	}

	public void setQiniuurlPrefix(String qiniuurlPrefix) {
		this.qiniuurlPrefix = qiniuurlPrefix;
	}

	public String getAliyunDomain() {
		return aliyunDomain;
	}

	public void setAliyunDomain(String aliyunDomain) {
		this.aliyunDomain = aliyunDomain;
	}

	public String getAliyunurlPrefix() {
		return aliyunurlPrefix;
	}

	public void setAliyunurlPrefix(String aliyunurlPrefix) {
		this.aliyunurlPrefix = aliyunurlPrefix;
	}

	public String getAliyunEndPoint() {
		return aliyunEndPoint;
	}

	public void setAliyunEndPoint(String aliyunEndPoint) {
		this.aliyunEndPoint = aliyunEndPoint;
	}

	public String getAliyunAccessKeyId() {
		return aliyunAccessKeyId;
	}

	public void setAliyunAccessKeyId(String aliyunAccessKeyId) {
		this.aliyunAccessKeyId = aliyunAccessKeyId;
	}

	public String getAliyunAccessKeySecret() {
		return aliyunAccessKeySecret;
	}

	public void setAliyunAccessKeySecret(String aliyunAccessKeySecret) {
		this.aliyunAccessKeySecret = aliyunAccessKeySecret;
	}

	public String getAliyunBucketName() {
		return aliyunBucketName;
	}

	public void setAliyunBucketName(String aliyunBucketName) {
		this.aliyunBucketName = aliyunBucketName;
	}

	public String getQcloudDomain() {
		return qcloudDomain;
	}

	public void setQcloudDomain(String qcloudDomain) {
		this.qcloudDomain = qcloudDomain;
	}

	public String getQcloudurlPrefix() {
		return qcloudurlPrefix;
	}

	public void setQcloudurlPrefix(String qcloudurlPrefix) {
		this.qcloudurlPrefix = qcloudurlPrefix;
	}

	public Integer getQcloudAppId() {
		return qcloudAppId;
	}

	public void setQcloudAppId(Integer qcloudAppId) {
		this.qcloudAppId = qcloudAppId;
	}

	public String getQcloudSecretId() {
		return qcloudSecretId;
	}

	public void setQcloudSecretId(String qcloudSecretId) {
		this.qcloudSecretId = qcloudSecretId;
	}

	public String getQcloudSecretKey() {
		return qcloudSecretKey;
	}

	public void setQcloudSecretKey(String qcloudSecretKey) {
		this.qcloudSecretKey = qcloudSecretKey;
	}

	public String getQcloudBucketName() {
		return qcloudBucketName;
	}

	public void setQcloudBucketName(String qcloudBucketName) {
		this.qcloudBucketName = qcloudBucketName;
	}

	public String getQcloudRegion() {
		return qcloudRegion;
	}

	public void setQcloudRegion(String qcloudRegion) {
		this.qcloudRegion = qcloudRegion;
	}
}
