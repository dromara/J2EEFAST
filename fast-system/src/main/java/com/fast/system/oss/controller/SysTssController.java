package com.fast.system.oss.controller;

import java.util.HashMap;
import java.util.Map;

import javax.tools.Tool;

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

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.common.utils.DateUtil;
import com.fast.common.core.exception.RxcException;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.R;
import com.fast.common.core.utils.ToolUtil;
import com.fast.common.core.utils.ValidatorUtil;
import com.fast.framework.sys.service.SysConfigService;
import com.fast.framework.utils.ConfigConstant;
import com.fast.framework.utils.Constant;
import com.fast.system.oss.cloud.CloudStorageConfig;
import com.fast.system.oss.entity.SysTssEntity;
import com.fast.system.oss.service.SysTssService;
import com.fast.system.validator.group.AliyunGroup;
import com.fast.system.validator.group.BcsapiGroup;
import com.fast.system.validator.group.QcloudGroup;
import com.fast.system.validator.group.QiniuGroup;

import cn.hutool.json.JSONUtil;
import net.sf.json.JSONObject;

/**
 * 文件上传
 */
@RestController
@RequestMapping("sys/tss")
public class SysTssController {
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
	public R list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysTssService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 云存储配置信息
	 */
	@RequestMapping("/config")
	@RequiresPermissions("sys:tss:all")
	public R config() {
		CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_CONFIG_APP,
				CloudStorageConfig.class);
		return R.ok().put("config", config);
	}

	/**
	 * 保存云存储配置信息
	 */
	@RequestMapping("/saveConfig")
	@RequiresPermissions("sys:tss:all")
	public R saveConfig(@RequestBody CloudStorageConfig config) {
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

		return R.ok();
	}

	/**
	 * 上传文件
	 */
	@PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
	@RequiresPermissions("sys:tss:upload")
	public R upload(@RequestParam("file") MultipartFile file, SysTssEntity entity) {
//		if (file.isEmpty()) {
//			throw new RxcException("上传文件不能为空");
//		}
//
//		// ValidatorUtil.validateEntity(entity,AddGroup.class);
//
//		if (ToolUtil.isEmpty(entity.getVersion())) {
//			return R.error("版本号必填!");
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
//				return R.error("版本号有重复!");
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
//				return R.error(HmObj.getString("sub_code") + "-->" + HmObj.getString("sub_msg"));
//			}
//
//		} catch (Exception e) {
//			logger.error("上传失败!", e);
//			return R.error("上传失败!");
//		}
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:tss:delete")
	public R delete(@RequestBody String version) {
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
//				return R.error(HmObj.getString("sub_code") + "-->" + HmObj.getString("sub_msg"));
//			}
//		} catch (Exception e) {
//			logger.error("删除失败!", e);
//			return R.error("删除失败!");
//		}
		return R.ok();
	}
}
