package com.fast.system.log.controller;

import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.common.core.enums.BusinessType;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.R;
import com.fast.framework.annotation.RepeatSubmit;
import com.fast.framework.log.service.SysOperLogSerice;
import com.fast.framework.utils.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 系统操作日志 
 */
@Controller
@RequestMapping("/sys/operlog")
public class SysLogController extends AbstractController {

	private String urlPrefix = "modules/log";

	@Autowired
	private SysOperLogSerice sysOperLogSerice;

	@RequiresPermissions("sys:log:view")
	@GetMapping()
	public String operlog()
	{
		return urlPrefix + "/operlog";
	}

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:log:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysOperLogSerice.findPage(params);
		return R.ok().put("page", page);
	}

	@RequiresPermissions("sys:log:detail")
	@GetMapping("/detail/{operId}")
	public String detail(@PathVariable("operId") Long operId, ModelMap mmap)
	{
		mmap.put("sysLog", sysOperLogSerice.getById(operId));
		return urlPrefix + "/detail";
	}

	@BussinessLog(title = "操作日志", businessType = BusinessType.CLEAN)
	@RequiresPermissions("sys:log:clean")
	@PostMapping("/clean")
	@ResponseBody
	@RepeatSubmit
	public R clean(){
		return sysOperLogSerice.cleanLog()?R.ok():R.error();
	}

	@RequestMapping("/del")
	@BussinessLog(title = "操作日志", businessType = BusinessType.DELETE)
	@RequiresPermissions("sys:log:del")
	@ResponseBody
	public R del(Long[] ids) {
		return sysOperLogSerice.removeByIds(Arrays.asList(ids)) ? R.ok(): R.error();
	}


}
