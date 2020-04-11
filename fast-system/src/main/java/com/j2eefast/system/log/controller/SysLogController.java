package com.j2eefast.system.log.controller;

import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.framework.log.service.SysOperLogSerice;
import com.j2eefast.common.core.controller.BaseController;
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
public class SysLogController extends BaseController {

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
	public ResponseData list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysOperLogSerice.findPage(params);
		return success(page);
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
	public ResponseData clean(){
		return sysOperLogSerice.cleanLog()?success(): error("清空失败!");
	}

	@RequestMapping("/del")
	@BussinessLog(title = "操作日志", businessType = BusinessType.DELETE)
	@RequiresPermissions("sys:log:del")
	@ResponseBody
	public ResponseData del(Long[] ids) {
		return sysOperLogSerice.removeByIds(Arrays.asList(ids)) ? success(): error("删除失败!");
	}


}
