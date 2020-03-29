package com.fast.modules.sys.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.common.core.enums.BusinessType;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.R;
import com.fast.common.core.utils.ToolUtil;
import com.fast.common.core.utils.ValidatorUtil;
import com.fast.framework.utils.AbstractController;

import cn.hutool.core.util.ArrayUtil;

import com.fast.framework.sys.entity.SysConfigEntity;
import com.fast.framework.sys.service.SysConfigService;

/**
 * 
 * 系统配置信息控制器
 * @author zhouzhou
 * @date 2020-03-07 14:52
 */
@Controller
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {

	private String urlPrefix = "modules/sys/config";

	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 系统参数
	 */
	@RequiresPermissions("sys:config:view")
	@GetMapping()
	public String config(){
		return urlPrefix + "/config";
	}


	/**
	 * 新增参数配置
	 */
	@GetMapping("/add")
	public String add(){
		return urlPrefix + "/add";
	}

	/**
	 * 修改参数配置
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap){
		mmap.put("cfg", sysConfigService.getById(id));
		return urlPrefix + "/edit";
	}

	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	@ResponseBody
	public R list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysConfigService.queryPage(params);
		return R.ok().put("page", page);
	}

//	/**
//	 * 配置信息
//	 */
//	@RequestMapping("/info/{id}")
//	@RequiresPermissions("sys:config:info")
//	public R info(@PathVariable("id") Long id) {
//		SysConfigEntity config = sysConfigService.selectById(id);
//
//		return R.ok().put("config", config);
//	}

	/**
	 * 保存配置
	 */
	@BussinessLog(title = "参数管理", businessType = BusinessType.INSERT)
	@RequestMapping("/add")
	@RequiresPermissions("sys:config:add")
	@ResponseBody
	public R save(@Validated SysConfigEntity config) {

		ValidatorUtil.validateEntity(config);
		if (!sysConfigService.checkConfigKeyUnique(config))
		{
			return R.error("新增参数'" + config.getParamName() + "'失败，参数键名已存在");
		}
		config.setCreateBy(getUser().getUsername());
		config.setCreateTime(new Date());
		sysConfigService.insert(config);
		return R.ok();
	}

	/**
	 * 校验参数键名
	 */
	@RequestMapping(value = "/checkConfigKeyUnique", method = RequestMethod.POST)
	@ResponseBody
	public R checkConfigKeyUnique(SysConfigEntity config)
	{
		if(sysConfigService.checkConfigKeyUnique(config)){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改配置
	 */
	@BussinessLog(title = "参数管理", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequiresPermissions("sys:config:edit")
	@ResponseBody
	public R update(@Validated SysConfigEntity config) {

		ValidatorUtil.validateEntity(config);
		if (!sysConfigService.checkConfigKeyUnique(config))
		{
			return R.error("修改参数'" + config.getParamName() + "'失败，参数键名已存在");
		}
		config.setUpdateBy(getLoginName());
		sysConfigService.update(config);
		return R.ok();
	}

	/**
	 * 删除配置
	 */
	@BussinessLog(title = "参数管理", businessType = BusinessType.DELETE)
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@RequiresPermissions("sys:config:del")
	@ResponseBody
	public R delete(Long[] ids) {
		List<SysConfigEntity> list= sysConfigService.list(new QueryWrapper<SysConfigEntity>().
				eq("config_type","Y").in("id",ArrayUtil.join(ids, ",")));
		if(ToolUtil.isNotEmpty(list)){
			return R.error("删除参数失败，系统参数不能删除");
		}
		sysConfigService.deleteBatch(ids);
		return R.ok();
	}

}
