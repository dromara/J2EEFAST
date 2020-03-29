package com.fast.system.job.controller;
import java.util.Map;

import com.fast.common.core.enums.BusinessType;
import com.fast.framework.annotation.RepeatSubmit;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.framework.utils.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.R;
import com.fast.framework.quartz.entity.SysJobLogEntity;
import com.fast.framework.quartz.service.SysJobLogService;
/**
 * 定时任务日志控制器
 * @author zhouzhou
 * @date 2020-03-08 20:52
 */
@Controller
@RequestMapping("/sys/jobLog")
public class SysJobLogController extends AbstractController {

	private String urlPrefix = "modules/sys/job";

	@Autowired
	private SysJobLogService sysJobLogService;


	@RequiresPermissions("sys:job:loglist")
	@GetMapping()
	public String jobLog()
	{
		return urlPrefix + "/jobLog";
	}

	/**
	 * 定时任务日志列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	@RequiresPermissions("sys:job:loglist")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysJobLogService.findPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 定时任务日志信息
	 */
	@RequestMapping("/info/{logId}")
	public R info(@PathVariable("logId") Long logId) {
		SysJobLogEntity log = sysJobLogService.getById(logId);
		return R.ok().put("log", log);
	}

	@BussinessLog(title = "调度日志", businessType = BusinessType.CLEAN)
	@RequiresPermissions("sys:joblog:clean")
	@PostMapping("/clean")
	@ResponseBody
	@RepeatSubmit
	public R clean()
	{
		return sysJobLogService.cleanJobLog()?R.ok():R.error();
	}

	/**
	 * 删除定时任务
	 */
	@BussinessLog(title = "调度日志", businessType = BusinessType.DELETE)
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@RequiresPermissions("sys:joblog:del")
	@ResponseBody
	@RepeatSubmit
	public R del(Long[] ids) {

		return sysJobLogService.deleteBatchByIds(ids)?R.ok():R.error();

	}
}
