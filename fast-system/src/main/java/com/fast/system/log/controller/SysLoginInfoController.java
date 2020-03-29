package com.fast.system.log.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.common.core.enums.BusinessType;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.R;
import com.fast.framework.annotation.RepeatSubmit;
import com.fast.framework.log.service.SysLoginInfoSerice;
import com.fast.framework.utils.AbstractController;

/**
 * 登陆日志控制器
 * @author zhouzhou
 * @date 2018-03-13 15:15
 */
@Controller
@RequestMapping("/sys/logininfo")
public class SysLoginInfoController extends AbstractController {
	private String urlPrefix = "modules/log";
	
	@Autowired
	private SysLoginInfoSerice  sysLoginInfoService;

	@RequiresPermissions("sys:logininfo:view")
	@GetMapping()
	public String indfo() {
		return urlPrefix + "/logininfo";
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:logininfo:list")
	@ResponseBody
	public R list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysLoginInfoService.findPage(params);
		return R.ok().put("page", page);
	}
	
	
	@RequestMapping("/del")
	@BussinessLog(title = "登陆日志", businessType = BusinessType.DELETE)
	@RequiresPermissions("sys:logininfo:del")
	@ResponseBody
	public R del(Long[] ids) {
		return sysLoginInfoService.deleteBatchByIds(ids) ? R.ok(): R.error();
	}
	
	@BussinessLog(title = "登陆日志", businessType = BusinessType.CLEAN)
	@RequiresPermissions("sys:logininfo:clean")
	@PostMapping("/clean")
	@ResponseBody
	@RepeatSubmit
	public R clean(){
		return sysLoginInfoService.cleanLog()?R.ok():R.error();
	}
}
