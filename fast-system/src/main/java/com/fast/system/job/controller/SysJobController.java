package com.fast.system.job.controller;
import java.util.Map;
import com.fast.common.core.enums.BusinessType;
import com.fast.framework.utils.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.R;
import com.fast.common.core.utils.ValidatorUtil;
import com.fast.framework.annotation.RepeatSubmit;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.framework.quartz.entity.SysJobEntity;
import com.fast.framework.quartz.service.SysJobService;

/**
 * 定时任务
 */
@Controller
@RequestMapping("/sys/schedule")
public class SysJobController extends AbstractController {

	private String urlPrefix = "modules/sys/job";

	@Autowired
	private SysJobService sysJobService;

	/**
	 * 定时任务列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:job:list")
	@ResponseBody
	public R list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysJobService.findPage(params);
		return R.ok().put("page", page);
	}

	@RequiresPermissions("sys:job:view")
	@GetMapping()
	public String job() {
		return urlPrefix + "/job";
	}

	@RequiresPermissions("sys:job:add")
	@GetMapping("/cron")
	public String cron() {
		return urlPrefix + "/cron";
	}


	@RequiresPermissions("sys:job:detail")
	@GetMapping("/detail/{jobId}")
	public String detail(@PathVariable("jobId") Long jobId, ModelMap mmap)
	{
		mmap.put("name", "job");
		mmap.put("job", sysJobService.getById(jobId));
		return urlPrefix + "/detail";
	}

	/**
	 * 保存定时任务
	 */
	@BussinessLog(title = "定时任务", businessType = BusinessType.INSERT)
	@RequestMapping("/add")
	@RequiresPermissions("sys:job:add")
	@ResponseBody
	@RepeatSubmit
	public R save(@Validated SysJobEntity sysJob) {

		ValidatorUtil.validateEntity(sysJob);

		sysJobService.add(sysJob);

		return R.ok();
	}

	/**
	 * 修改调度
	 */
	@GetMapping("/edit/{jobId}")
	public String edit(@PathVariable("jobId") Long jobId, ModelMap mmap){
		mmap.put("job", sysJobService.getById(jobId));
		return urlPrefix + "/edit";
	}

	/**
	 * 修改定时任务
	 */
	@BussinessLog(title = "定时任务", businessType = BusinessType.UPDATE)
	@RequestMapping("/edit")
	@RequiresPermissions("sys:job:edit")
	@ResponseBody
	@RepeatSubmit
	public R update(SysJobEntity sysJob) {

		ValidatorUtil.validateEntity(sysJob);

		sysJobService.updateSysJob(sysJob);

		return R.ok();
	}

	/**
	 * 删除定时任务
	 */
	@BussinessLog(title = "定时任务", businessType = BusinessType.DELETE)
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@RequiresPermissions("sys:job:del")
	@ResponseBody
	@RepeatSubmit
	public R del(Long[] ids) {

		return sysJobService.deleteBatchByIds(ids)?R.ok():R.error();

	}

	/**
	 * 立即执行任务
	 */
	@BussinessLog(title = "定时任务", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/run", method = RequestMethod.POST)
	@RequiresPermissions("sys:job:run")
	@ResponseBody
	@RepeatSubmit
	public R run(Long[] jobIds) {
		sysJobService.run(jobIds);
		return R.ok();
	}

	/**
	 * 暂停定时任务
	 */
	@BussinessLog(title = "定时任务", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/pause", method = RequestMethod.POST)
	@RequiresPermissions("sys:job:changeStatus")
	@ResponseBody
	@RepeatSubmit
	public R pause(Long[] jobIds) {
		sysJobService.pause(jobIds);
		return R.ok();
	}


	/**
	 * 新增调度
	 */
	@GetMapping("/add")
	public String add()
	{
		return urlPrefix + "/add";
	}

	/**
	 * 恢复定时任务
	 */
	@BussinessLog(title = "定时任务", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/resume", method = RequestMethod.POST)
	@RequiresPermissions("sys:job:changeStatus")
	@ResponseBody
	@RepeatSubmit
	public R resume(Long[] jobIds) {
		sysJobService.resume(jobIds);
		return R.ok();
	}


	/**
	 * 校验cron表达式是否有效
	 */
	@PostMapping("/checkCronExpressionIsValid")
	@ResponseBody
	public R checkCronExpressionIsValid(SysJobEntity sysJob)
	{
		if(sysJobService.checkCronExpressionIsValid(sysJob.getCronExpression())){
			return R.ok();
		}
		return R.error();
	}

}
