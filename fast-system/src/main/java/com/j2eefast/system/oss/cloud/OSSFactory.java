package com.j2eefast.system.oss.cloud;

import com.j2eefast.common.config.service.SysConfigService;
import com.j2eefast.common.core.utils.SpringUtil;
import com.j2eefast.framework.utils.ConfigConstant;
import com.j2eefast.framework.utils.Constant;

/**
 * 文件上传Factory
 */
public final class OSSFactory {
	private static SysConfigService sysConfigService;

	static {
		OSSFactory.sysConfigService = SpringUtil.getBean(SysConfigService.class);
	}

	public static CloudStorageService build() {
		// 获取云存储配置信息
		CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY,
				CloudStorageConfig.class);

		if (config.getType() == Constant.CloudService.QINIU.getValue()) {
			return new QiniuCloudStorageService(config);
		} else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
			return new AliyunCloudStorageService(config);
		} else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
			return new QcloudCloudStorageService(config);
		}

		return null;
	}

}
