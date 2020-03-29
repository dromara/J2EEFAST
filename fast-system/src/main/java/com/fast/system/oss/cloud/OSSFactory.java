package com.fast.system.oss.cloud;

import com.fast.common.core.utils.SpringUtil;
import com.fast.framework.sys.service.SysConfigService;
import com.fast.framework.utils.ConfigConstant;
import com.fast.framework.utils.Constant;

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
