package com.j2eefast.flowable.bpm.controller;

import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.flowable.bpm.service.FlowableProcessInstanceService;
import com.j2eefast.framework.annotation.RepeatSubmit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>流程实例</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-24 10:36
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Controller
@RequestMapping("/bpm/processInstance")
public class ProcessInstanceController extends BaseController {


	private String urlPrefix = "modules/bpm/processInstance";

	@Autowired
	private FlowableProcessInstanceService flowableProcessInstanceService;

	@RequiresPermissions("bpm:processInstance:view")
	@GetMapping()
	public String processInstance(){
		return urlPrefix + "/processInstance";
	}

	@RequestMapping("/list")
	@RequiresPermissions("bpm:processInstance:list")
	@ResponseBody
	public ResponseData list(@RequestParam Map<String, Object> params) {
		PageUtil page = flowableProcessInstanceService.findPage(params);
		return success(page);
	}

	@GetMapping("/preview/{id}")
	public String preview(@PathVariable("id") String id, ModelMap mmap) {
		mmap.put("id",id);
		return  urlPrefix + "/view";
	}

	@RepeatSubmit
	@BussinessLog(title = "流程实例", businessType = BusinessType.DELETE)
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@RequiresPermissions("bpm:processInstance:del")
	@ResponseBody
	public ResponseData deleteProcessInstanceById(String ids) {
		return flowableProcessInstanceService.deleteProcessInstanceById(ids);
	}

	/**
	 * 激活或者挂起流程定义
	 *
	 * @param id
	 * @return
	 */
	@RepeatSubmit
	@BussinessLog(title = "流程实例", businessType = BusinessType.UPDATE)
	@PostMapping(value = "/status")
	@RequiresPermissions("bpm:processInstance:activete")
	@ResponseBody
	public ResponseData saProcessInstanceById(String processInstanceId, int status) {
		return  flowableProcessInstanceService.suspendOrActivateProcessInstanceById(processInstanceId, status);
	}
}
