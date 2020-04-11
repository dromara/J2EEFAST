package com.j2eefast.system.job.controller;
import java.util.Map;

import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.framework.quartz.entity.SysJobLogEntity;
import com.j2eefast.framework.quartz.service.SysJobLogService;
/**
 * 定时任务日志控制器
 * @author zhouzhou
 * @date 2020-03-08 20:52
 */
@Controller
@RequestMapping("/sys/jobLog")
public class SysJobLogController extends BaseController {

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
	public ResponseData list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysJobLogService.findPage(params);
		return success(page);
	}

	/**
	 * 定时任务日志信息
	 */
	@RequestMapping("/info/{logId}")
	public ResponseData info(@PathVariable("logId") Long logId) {
		SysJobLogEntity log = sysJobLogService.getById(logId);
		return success().put("log", log);
	}

	@BussinessLog(title = "调度日志", businessType = BusinessType.CLEAN)
	@RequiresPermissions("sys:joblog:clean")
	@PostMapping("/clean")
	@ResponseBody
	@RepeatSubmit
	public ResponseData clean()
	{
		return sysJobLogService.cleanJobLog()?success():error("清空失败!");
	}

	/**
	 * 删除定时任务
	 */
	@BussinessLog(title = "调度日志", businessType = BusinessType.DELETE)
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@RequiresPermissions("sys:joblog:del")
	@ResponseBody
	@RepeatSubmit
	public ResponseData del(Long[] ids) {

		return sysJobLogService.deleteBatchByIds(ids)?success():error("删除失败!");

	}
}
