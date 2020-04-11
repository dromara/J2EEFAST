package com.j2eefast.system.oss.controller;

import java.util.Map;

import com.j2eefast.common.config.service.SysConfigService;
import com.j2eefast.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.ValidatorUtil;
import com.j2eefast.framework.utils.ConfigConstant;
import com.j2eefast.framework.utils.Constant;
import com.j2eefast.system.oss.cloud.CloudStorageConfig;
import com.j2eefast.system.oss.entity.SysTssEntity;
import com.j2eefast.system.oss.service.SysTssService;
import com.j2eefast.system.validator.group.AliyunGroup;
import com.j2eefast.system.validator.group.BcsapiGroup;
import com.j2eefast.system.validator.group.QcloudGroup;
import com.j2eefast.system.validator.group.QiniuGroup;

import cn.hutool.json.JSONUtil;

/**
 * 文件上传
 */
@RestController
@RequestMapping("sys/tss")
public class SysTssController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(SysTssController.class);

	@Autowired
	private SysTssService sysTssService;

	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:tss:list")
	public ResponseData list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysTssService.queryPage(params);
		return success(page);
	}

	/**
	 * 云存储配置信息
	 */
	@RequestMapping("/config")
	@RequiresPermissions("sys:tss:all")
	public ResponseData config() {
		CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_CONFIG_APP,
				CloudStorageConfig.class);
		return success().put("config", config);
	}

	/**
	 * 保存云存储配置信息
	 */
	@RequestMapping("/saveConfig")
	@RequiresPermissions("sys:tss:all")
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
		} else if (config.getType() == Constant.CloudService.APPBCS.getValue()) {
			// 校验API服务器数据
			ValidatorUtil.validateEntity(config, BcsapiGroup.class);
		}
		sysConfigService.updateValueByKey(ConfigConstant.CLOUD_CONFIG_APP, JSONUtil.parseObj(config).toString());//new Gson().toJson(config));

		return success();
	}

	/**
	 * 上传文件
	 */
	@PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
	@RequiresPermissions("sys:tss:upload")
	public ResponseData upload(@RequestParam("file") MultipartFile file, SysTssEntity entity) {
//		if (file.isEmpty()) {
//			throw new RxcException("上传文件不能为空");
//		}
//
//		// ValidatorUtil.validateEntity(entity,AddGroup.class);
//
//		if (ToolUtil.isEmpty(entity.getVersion())) {
//			return error("版本号必填!");
//		}
//
//		try {
//			// 获取API服务器参数信息
//			String config = sysConfigService.getValue(ConfigConstant.CLOUD_CONFIG_APP);
//			// 解析保存数据
//			JSONObject HmObj = JSONObject.fromObject(config);
//
//			SysTssEntity sysTssEntity = sysTssService.selectByVs(entity.getVersion());
//
//			if (sysTssEntity != null) {
//				return error("版本号有重复!");
//			}
//
//			// 文件上传发送API服务器
//			String url = HmObj.getString("url") + "upload.do";
//			String fileName = file.getOriginalFilename();
//			System.out.println(fileName + "----------------");
//			Map<String, String> textMap = new HashMap<String, String>();
//			// 可以设置多个input的name，value
//			textMap.put("app_id", HmObj.getString("app_id"));
//			textMap.put("method", "pay.trade.upload");
//			textMap.put("timestamp", DateUtils.getNowYYYYMMDDHHmmss());
//			textMap.put("nonce_str", ToolUtil.getUUID());
//			textMap.put("version", "1.0.1");
//			textMap.put(PayConstants.FIELD_SIGN_TYPE, PayConstants.HMACSHA256);
//			Map<String, String> tempMap = new HashMap<String, String>();
//			tempMap.put("version", entity.getVersion());
//			tempMap.put("remark", entity.getRemark());
//			textMap.put("biz_content", JSON.toJSONString(tempMap));
//			String sign = StringUtil.generateSignature(textMap, HmObj.getString("token"),SignType.HMACSHA256);
//			textMap.put("sign", sign);
//			// 设置file的name，路径
//			String contentType = "";// image/png
//			String ref = ToolUtil.uploadFile(url, textMap, "multipartFile", fileName, file.getInputStream(),
//					contentType);
//			HmObj = JSONObject.fromObject(ref);
//			if (!HmObj.getString("code").equals("00000")) {
//				return error(HmObj.getString("sub_code") + "-->" + HmObj.getString("sub_msg"));
//			}
//
//		} catch (Exception e) {
//			logger.error("上传失败!", e);
//			return error("上传失败!");
//		}
		return success();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:tss:delete")
	public ResponseData delete(@RequestBody String version) {
//		try {
//			// 获取API服务器参数信息
//			String config = sysConfigService.getValue(ConfigConstant.CLOUD_CONFIG_APP);
//			// 解析保存数据
//			JSONObject HmObj = JSONObject.fromObject(config);
//
//			// 文件上传发送API服务器
//			String url = HmObj.getString("url") + "gateway.do";
//
//			Map<String, String> textMap = new HashMap<String, String>();
//			// 可以设置多个input的name，value
//			textMap.put("app_id", HmObj.getString("app_id"));
//			textMap.put("method", "pay.trade.packet.delete");
//			textMap.put("timestamp", DateUtils.getNowYYYYMMDDHHmmss());
//			textMap.put("nonce_str", ToolUtil.getUUID());
//			textMap.put("version", "1.0.1");
//			textMap.put(PayConstants.FIELD_SIGN_TYPE, PayConstants.HMACSHA256);
//			
//			Map<String, String> tempMap = new HashMap<String, String>();
//			tempMap.put("version", version);
//			textMap.put("biz_content", JSON.toJSONString(tempMap));
//			String sign = StringUtil.generateSignature(textMap, HmObj.getString("token"),SignType.HMACSHA256);
//			textMap.put("sign", sign);
//			// 设置file的name，路径
//			String ref = ToolUtil.HttpPost(url, textMap);
//			HmObj = JSONObject.fromObject(ref);
//			if (!HmObj.getString("code").equals("00000")) {
//				return error(HmObj.getString("sub_code") + "-->" + HmObj.getString("sub_msg"));
//			}
//		} catch (Exception e) {
//			logger.error("删除失败!", e);
//			return error("删除失败!");
//		}
		return success();
	}
}
