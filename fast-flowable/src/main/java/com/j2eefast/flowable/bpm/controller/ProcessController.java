package com.j2eefast.flowable.bpm.controller;

import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.flowable.bpm.entity.RevokeProcessEntity;
import com.j2eefast.flowable.bpm.service.BpmTaskFromService;
import com.j2eefast.flowable.bpm.service.FlowableProcessInstanceService;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.framework.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>发起流程</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-27 22:03
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Controller
@RequestMapping("/bpm/process")
public class ProcessController extends BaseController {

	private String urlPrefix = "modules/bpm/process";

	@Autowired
	private BpmTaskFromService bpmTaskFromService;
	@Autowired
	private FlowableProcessInstanceService flowableProcessInstanceService;

	@RequiresPermissions("bpm:process:view")
	@GetMapping()
	public String process() {
		return urlPrefix + "/process";
	}

	@RequestMapping("/list")
	@RequiresPermissions("bpm:process:list")
	@ResponseBody
	public ResponseData list(@RequestParam Map<String, Object> params) {
		PageUtil page = bpmTaskFromService.findPage(params);
		return success(page);
	}

	@RequiresPermissions("bpm:process:view")
	@GetMapping("/myProcess")
	public String myProcess() {
		return urlPrefix + "/myProcess";
	}

	/**
	 * 我的流程
	 * @param params
	 * @return
	 */
	@RequestMapping("/myProcess/list")
	@RequiresPermissions("bpm:process:list")
	@ResponseBody
	public ResponseData myProcessList(@RequestParam Map<String, Object> params) {
		params.put("starterId", UserUtils.getUserIdToStr());
		PageUtil page = flowableProcessInstanceService.findPage(params);
		return success(page);
	}


	@RepeatSubmit
	@RequiresPermissions("bpm:process:revoke")
	@BussinessLog(title = "OA请假单", businessType = BusinessType.INSERT)
	@RequestMapping(value = "/revoke", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData revoke(@Validated RevokeProcessEntity revoke){
		revoke.setUserId(UserUtils.getUserIdToStr());
		return flowableProcessInstanceService.revokeProcess(revoke);
	}


}
