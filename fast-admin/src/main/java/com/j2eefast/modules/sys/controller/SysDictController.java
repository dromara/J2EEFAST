package com.j2eefast.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.ValidatorUtil;

/**
 * 数据字典
 */
@RestController
@RequestMapping("sys/dict")
public class SysDictController {
//	@Autowired
//	private SysDictService sysDictService;
//
//	/**
//	 * 列表
//	 */
//	@RequestMapping("/list")
//	@RequiresPermissions("sys:dict:list")
//	public ResponseData list(@RequestParam Map<String, Object> params) {
//		PageUtil page = sysDictService.queryPage(params);
//
//		return success(page);
//	}
//
//	/**
//	 * 信息
//	 */
//	@RequestMapping("/info/{id}")
//	@RequiresPermissions("sys:dict:info")
//	public ResponseDatainfo(@PathVariable("id") Long id) {
//		SysDictEntity dict = sysDictService.selectById(id);
//
//		return success().put("dict", dict);
//	}
//
//	/**
//	 * 保存
//	 */
//	@RequestMapping("/save")
//	@RequiresPermissions("sys:dict:save")
//	public ResponseDatasave(@RequestBody SysDictEntity dict) {
//		// 校验类型
//		ValidatorUtil.validateEntity(dict);
//
//		sysDictService.insert(dict);
//
//		return success();
//	}
//
//	/**
//	 * 修改
//	 */
//	@RequestMapping("/update")
//	@RequiresPermissions("sys:dict:update")
//	public ResponseDataupdate(@RequestBody SysDictEntity dict) {
//		// 校验类型
//		ValidatorUtil.validateEntity(dict);
//
//		sysDictService.updateById(dict);
//
//		return success();
//	}
//
//	/**
//	 * 删除
//	 */
//	@RequestMapping("/delete")
//	@RequiresPermissions("sys:dict:delete")
//	public ResponseDatadelete(@RequestBody Long[] ids) {
//		sysDictService.deleteBatchIds(Arrays.asList(ids));
//
//		return success();
//	}

}
