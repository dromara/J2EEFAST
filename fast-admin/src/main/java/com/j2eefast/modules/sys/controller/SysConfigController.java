package com.j2eefast.modules.sys.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.j2eefast.common.config.entity.SysConfigEntity;
import com.j2eefast.common.config.service.SysConfigService;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.framework.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.core.utils.ValidatorUtil;
import com.j2eefast.common.core.controller.BaseController;
import cn.hutool.core.util.ArrayUtil;

/**
 * 
 * 系统配置信息控制器
 * @author zhouzhou
 * @date 2020-03-07 14:52
 */
@Controller
@RequestMapping("/sys/config")
public class SysConfigController extends BaseController {

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
	public ResponseData list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysConfigService.findPage(params);
		return success(page);
	}

	/**
	 * 保存配置
	 */
	@BussinessLog(title = "参数管理", businessType = BusinessType.INSERT)
	@RequestMapping("/add")
	@RequiresPermissions("sys:config:add")
	@ResponseBody
	public ResponseData save(@Validated SysConfigEntity config) {

		ValidatorUtil.validateEntity(config);
		if (!sysConfigService.checkConfigKeyUnique(config)){
			return error("新增参数'" + config.getParamName() + "'失败，参数键名已存在");
		}
		return sysConfigService.add(config)?success():error("新增失败!");
	}

	/**
	 * 校验参数键名
	 */
	@RequestMapping(value = "/checkConfigKeyUnique", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData checkConfigKeyUnique(SysConfigEntity config){
		return sysConfigService.checkConfigKeyUnique(config)?success():error("已经存在!");
	}

	/**
	 * 修改配置
	 */
	@BussinessLog(title = "参数管理", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequiresPermissions("sys:config:edit")
	@ResponseBody
	public ResponseData update(@Validated SysConfigEntity config) {

		ValidatorUtil.validateEntity(config);
		if (!sysConfigService.checkConfigKeyUnique(config)) {
			return error("修改参数'" + config.getParamName() + "'失败，参数键名已存在");
		}
		return sysConfigService.update(config)?success():error("修改失败!");
	}

	/**
	 * 删除配置
	 */
	@BussinessLog(title = "参数管理", businessType = BusinessType.DELETE)
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@RequiresPermissions("sys:config:del")
	@ResponseBody
	public ResponseData delete(Long[] ids) {
		List<SysConfigEntity> list= sysConfigService.list(new QueryWrapper<SysConfigEntity>().
				eq("config_type","Y").in("id",ArrayUtil.join(ids, ",")));
		if(ToolUtil.isNotEmpty(list)){
			return error("删除参数失败，系统参数不能删除");
		}
		return sysConfigService.deleteBatchByIds(ids)?success():error("删除失败!");
	}

	@BussinessLog(title = "参数管理", businessType = BusinessType.CLEAN)
	@RequestMapping(value = "/clearConfig", method = RequestMethod.GET)
	@RequiresPermissions("sys:config:clear")
	@RepeatSubmit
	@ResponseBody
	public ResponseData clearConfig(){
		return sysConfigService.clearConfigRedis()?success():error("Redis没有开启无需清理!");
	}
}
