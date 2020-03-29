package com.fast.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.R;
import com.fast.common.core.utils.ValidatorUtil;

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
//	public R list(@RequestParam Map<String, Object> params) {
//		PageUtil page = sysDictService.queryPage(params);
//
//		return R.ok().put("page", page);
//	}
//
//	/**
//	 * 信息
//	 */
//	@RequestMapping("/info/{id}")
//	@RequiresPermissions("sys:dict:info")
//	public R info(@PathVariable("id") Long id) {
//		SysDictEntity dict = sysDictService.selectById(id);
//
//		return R.ok().put("dict", dict);
//	}
//
//	/**
//	 * 保存
//	 */
//	@RequestMapping("/save")
//	@RequiresPermissions("sys:dict:save")
//	public R save(@RequestBody SysDictEntity dict) {
//		// 校验类型
//		ValidatorUtil.validateEntity(dict);
//
//		sysDictService.insert(dict);
//
//		return R.ok();
//	}
//
//	/**
//	 * 修改
//	 */
//	@RequestMapping("/update")
//	@RequiresPermissions("sys:dict:update")
//	public R update(@RequestBody SysDictEntity dict) {
//		// 校验类型
//		ValidatorUtil.validateEntity(dict);
//
//		sysDictService.updateById(dict);
//
//		return R.ok();
//	}
//
//	/**
//	 * 删除
//	 */
//	@RequestMapping("/delete")
//	@RequiresPermissions("sys:dict:delete")
//	public R delete(@RequestBody Long[] ids) {
//		sysDictService.deleteBatchIds(Arrays.asList(ids));
//
//		return R.ok();
//	}

}
