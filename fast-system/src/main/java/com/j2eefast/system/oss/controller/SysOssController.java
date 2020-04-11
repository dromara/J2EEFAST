package com.j2eefast.system.oss.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.j2eefast.common.config.service.SysConfigService;
import com.j2eefast.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.ValidatorUtil;
import com.j2eefast.framework.utils.ConfigConstant;
import com.j2eefast.framework.utils.Constant;
import com.j2eefast.system.oss.cloud.CloudStorageConfig;
import com.j2eefast.system.oss.cloud.OSSFactory;
import com.j2eefast.system.oss.entity.SysOssEntity;
import com.j2eefast.system.oss.service.SysOssService;
import com.j2eefast.system.validator.group.AliyunGroup;
import com.j2eefast.system.validator.group.QcloudGroup;
import com.j2eefast.system.validator.group.QiniuGroup;

import cn.hutool.json.JSONUtil;

/**
 * 文件上传
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController extends BaseController {
	@Autowired
	private SysOssService sysOssService;
	@Autowired
	private SysConfigService sysConfigService;

	private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:oss:all")
	public ResponseData list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysOssService.queryPage(params);

		return success(page);
	}

	/**
	 * 云存储配置信息
	 */
	@RequestMapping("/config")
	@RequiresPermissions("sys:oss:all")
	public ResponseData config() {
		CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);
		return success().put("config", config);
	}

	/**
	 * 保存云存储配置信息
	 */
	@RequestMapping("/saveConfig")
	@RequiresPermissions("sys:oss:all")
	public ResponseData saveConfig(@RequestBody CloudStorageConfig config) {
		// 校验类型
		ValidatorUtil.validateEntity(config);

		if (config.getType() == Constant.CloudService.QINIU.getValue()) {
			// 校验七牛数据
			ValidatorUtil.validateEntity(config, QiniuGroup.class);
		} else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
			// 校验阿里云数据
			ValidatorUtil.validateEntity(config, AliyunGroup.class);
		} else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
			// 校验腾讯云数据
			ValidatorUtil.validateEntity(config, QcloudGroup.class);
		}

		sysConfigService.updateValueByKey(KEY,  JSONUtil.parseObj(config).toString());;//new Gson().toJson(config));

		return success();
	}

	/**
	 * 上传文件
	 */
	@RequestMapping("/upload")
	@RequiresPermissions("sys:oss:all")
	public ResponseData upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RxcException("上传文件不能为空");
		}

		// 上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

		// 保存文件信息
		SysOssEntity ossEntity = new SysOssEntity();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(new Date());
		sysOssService.save(ossEntity);

		return success().put("url", url);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:oss:all")
	public ResponseData delete(@RequestBody Long[] ids) {
		sysOssService.removeByIds(Arrays.asList(ids));
		return success();
	}

}
